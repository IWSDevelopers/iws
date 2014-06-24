/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.migrators.MailMigrator
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.migrators;

import static net.iaeste.iws.common.utils.StringUtils.toLower;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.migrate.daos.IWSDao;
import net.iaeste.iws.migrate.daos.MailDao;
import net.iaeste.iws.migrate.entities.IW3UsersEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.entities.mailing_list.MailingAliasEntity;
import net.iaeste.iws.persistence.entities.mailing_list.MailingListEntity;
import net.iaeste.iws.persistence.entities.mailing_list.MailingListMembershipEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This Class handles migtration of Mail information, which includes the default
 * listing for group mailinglists, user aliases, etc.<br />
 *   As this migrator can be run repeatedly without a problem, we it may well be
 * used so. Which is why, there's a setup method also, which resets the database
 * prior to populating the tables with data from IWS.<br />
 *   To avoid forgetting it, I'm posting the pg_dump command here so it is
 * possible to quickly regenerate the database.
 * <pre>
 *   $ pg_dump -c -O -F p -t mailing_lists -t mailing_list_membership -t mailing_aliases -t mailing_list_sequence -t mailing_list_membership_sequence -t mailing_alias_sequence IWS_DATABASE > mail_db.sql
 * </pre>
 * The result of the above listed command will be a script that can be run using
 * the standard run command from psql:
 * <pre>
 *   # \i mail_db.sql
 *   # grant all on all tables in schema public to user_iw4;
 *   # grant all on all sequences in schema public to user_iw4;
 *   # grant select on all tables in schema public to mailing_reader;
 * </pre>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class MailMigrator implements Migrator<IW3UsersEntity> {

    private static final Logger log = LoggerFactory.getLogger(MailMigrator.class);

    /**
     * This is a matrix with various static aliases that we need to have in the
     * system. A Static alias is one that is not generated, but rather added
     * upon request from the Board.<br />
     *   The format is simple, the first value is the current username and the
     * second is the alias for the username. Each row indicate a separate alias
     * to be created.
     */
    private static final String[][] STATIC_ALIASES = {
            { "India@iaeste.org", "India_KU@iaeste.org" },
            { "India@iaeste.org", "India_MIT@iaeste.org" },
            { "Bangladesh_Afzal_Management@iaeste.org", "bangladeshafm@iaeste.org" },
            { "Bangladesh_AFM@iaeste.org", "Bangladesh_Afzal_Management@iaeste.org" },
            { "Bangladesh_CAT@iaeste.org", "College_of_Aviation_Technology@iaeste.org" },
            { "bolivia_ib@iaeste.org", "boliviaib@iaeste.org" },
            { "vietnam_nu@iaeste.org", "vietnamnu@iaeste.org" },
            { "Nepal@iaeste.org", "Nepal_CI@iaeste.org" },
            { "Nepal_CI@iaeste.org", "NepalCI@iaeste.org" },
            { "Kenya_DKUT@iaeste.org", "Kenya_DeKut@iaeste.org" },
            { "board@iaeste.org", "president@iaeste.org" },
            { "idt@iaeste.net", "idt.members@iaeste.net"}};

    @Autowired
    private IWSDao iwsDao;

    @Autowired
    private MailDao mailDao;

    public MailMigrator() {
    }

    public MailMigrator(final IWSDao iwsDao, final MailDao mailDao) {
        this.iwsDao = iwsDao;
        this.mailDao = mailDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MigrationResult migrate() {
        // Wipe the mailing database prior to migrating
        mailDao.wipeDatabase();

        // Populate the mailing tables
        final MigrationResult groups = migrateGroups();
        final MigrationResult aliases = migrateAliases();
        final MigrationResult ncs = migrateNCs();

        return new MigrationResult(groups.getPersisted() + aliases.getPersisted() + ncs.getPersisted(), 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MigrationResult migrate(List<IW3UsersEntity> oldEntities) {
        throw new IllegalArgumentException("This Migrator is not allowed here.");
    }

    // =========================================================================
    // Internal Mail Migration Methods
    // =========================================================================

    @Transactional("transactionManagerMail")
    public MigrationResult migrateGroups() {
        final List<GroupEntity> groups = iwsDao.findAllGroups();
        log.info("Found {} Groups to Migrate.", groups.size());
        int persisted = 0;
        int skipped = 0;

        for (final GroupEntity group : groups) {
            if (group.getStatus() != GroupStatus.ACTIVE) {
                log.info("Skipping Mailinglist for {} (id: {}), as their status is {}", group.getGroupName(), group.getId(), group.getStatus());
            } else if (group.getListName() == null) {
                log.info("Skipping Mailinglist for {} (id: {}), as it is invalid.", group.getGroupName(), group.getId());
            } else {
                switch (group.getGroupType().getGrouptype()) {
                    case ADMINISTRATION: // Doesn't need a mailinglist
                    case PRIVATE: // Covered by aliases
                    case STUDENT: // Schema for Students is unclear
                        skipped++;
                        break;
                    case MEMBER:
                    case LOCAL:
                    case WORKGROUP:
                        createMailinglist(group, true, false);
                        persisted++;
                        break;
                    case INTERNATIONAL:
                        createMailinglist(group, false, true);
                        createMailinglist(group, true, false);
                        persisted += 2;
                        break;
                    case NATIONAL:
                        createMailinglist(group, false, false);
                        persisted++;
                        break;
                }
            }
        }

        log.info("Completed Migrating Groups, persisted {}.", persisted);
        return new MigrationResult(persisted, skipped);
    }

    @Transactional("transactionManagerMail")
    public MigrationResult migrateAliases() {
        final List<UserEntity> users = iwsDao.findAllUsers();
        log.info("Found {} Users to create Aliases for.", users.size());
        int persisted = 0;

        for (final UserEntity user : users) {
            if (user.getAlias() != null) {
                final MailingAliasEntity alias = new MailingAliasEntity();
                alias.setUserName(toLower(user.getUsername()));
                alias.setUserAlias(toLower(user.getAlias()));
                alias.setCreated(user.getCreated());

                mailDao.persist(alias);
                persisted++;
            } else {
                log.info("Skipping {} this is a student account.", user.getUsername());
            }
        }
        persisted += createStaticAliases();

        log.info("Completed Migrating user Aliases, persisted {}.", persisted);
        return new MigrationResult(persisted, 0);
    }

    @Transactional("transactionManagerMail")
    public MigrationResult migrateNCs() {
        final MailingListEntity list = new MailingListEntity();
        list.setExternalId(UUID.randomUUID().toString());
        list.setListAddress("ncs@" + IWSConstants.PRIVATE_EMAIL_ADDRESS);
        list.setPrivateList(true);
        mailDao.persist(list);
        int persisted = 1;

        final List<UserGroupEntity> members = iwsDao.findNCs();
        final Map<String, Boolean> memberMap = new HashMap<>(members.size());
        for (final UserGroupEntity user : members) {
            // As members can be part of multiple Groups that's added, we're
            // skipping already existing members
            final String address = user.getUser().getUsername();
            if (!memberMap.containsKey(address)) {
                final MailingListMembershipEntity member = new MailingListMembershipEntity();
                member.setMailingList(list);
                member.setMember(user.getUser().getUsername());
                member.setCreated(user.getCreated());
                mailDao.persist(member);
                memberMap.put(address, true);
                persisted++;
            }
        }

        return new MigrationResult(persisted, 0);
    }

    private int createStaticAliases() {
        int persisted = 0;

        for (final String[] staticAlias : STATIC_ALIASES) {
            final MailingAliasEntity alias = new MailingAliasEntity();

            alias.setUserName(toLower(staticAlias[0]));
            alias.setUserAlias(toLower(staticAlias[1]));
            alias.setCreated(new Date());

            mailDao.persist(alias);
            persisted++;
        }

        return persisted;
    }

    private void createMailinglist(final GroupEntity group, final boolean isPrivate, final boolean ownerOnly) {
        final MailingListEntity entity = new MailingListEntity();

        entity.setExternalId(group.getExternalId());
        entity.setListAddress(toLower(group.getListName() + '@' + (isPrivate ? IWSConstants.PRIVATE_EMAIL_ADDRESS : IWSConstants.PUBLIC_EMAIL_ADDRESS)));
        entity.setSubjectPrefix(group.getGroupName());
        entity.setPrivateList(isPrivate);
        entity.setActive(group.getStatus() == GroupStatus.ACTIVE);
        entity.setModified(group.getModified());
        entity.setCreated(group.getCreated());

        if (group.getListName() == null) {
            log.warn("Cannot migrate Group {}.", group);
        } else {
            mailDao.persist(entity);
        }

        addUsersToMailinglist(group, entity, ownerOnly);
    }

    private int addUsersToMailinglist(final GroupEntity group, final MailingListEntity list, final boolean ownerOnly) {
        final List<UserGroupEntity> users = iwsDao.findGroupMembers(group.getId());
        int persisted = 0;

        for (final UserGroupEntity user : users) {
            if (ownerOnly) {
                if (IWSConstants.ROLE_OWNER.equals(user.getRole().getId())) {
                    final MailingListMembershipEntity entity = new MailingListMembershipEntity();
                    entity.setMailingList(list);
                    entity.setMember(toLower(user.getUser().getUsername()));
                    entity.setCreated(user.getCreated());

                    mailDao.persist(entity);
                    persisted++;
                }
            } else {
                if ((list.isPrivateList() && user.getOnPrivateList()) ||
                    (!list.isPrivateList() && user.getOnPublicList())) {
                    final MailingListMembershipEntity entity = new MailingListMembershipEntity();
                    entity.setMailingList(list);
                    entity.setMember(toLower(user.getUser().getUsername()));
                    entity.setCreated(user.getCreated());

                    mailDao.persist(entity);
                    persisted++;
                }
            }
        }

        return persisted;
    }
}
