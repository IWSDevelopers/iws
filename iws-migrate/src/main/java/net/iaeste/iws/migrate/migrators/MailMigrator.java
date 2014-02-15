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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This Class handles migtration of Mail information, which includes the default
 * listing for group mailinglists, user aliases, etc.<br />
 *   As this migrator can be run repeatedly without a problem, we it may well be
 * used so. Which is why, there's a setup method also, which resets the database
 * prior to populating the tables with data from IWS.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class MailMigrator implements Migrator<IW3UsersEntity> {

    private static final Logger log = LoggerFactory.getLogger(MailMigrator.class);

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
            final MailingAliasEntity alias = new MailingAliasEntity();
            alias.setUserName(user.getUsername());
            alias.setUserAlias(user.getAlias());
            alias.setCreated(user.getCreated());

            mailDao.persist(alias);
            persisted++;
        }

        log.info("Completed Migrating user Aliases, persisted {}.", persisted);
        return new MigrationResult(persisted, 0);
    }

    @Transactional("transactionManagerMail")
    public MigrationResult migrateNCs() {
        final MailingListEntity list = new MailingListEntity();
        list.setExternalId(UUID.randomUUID().toString());
        list.setListAddress("ncs");
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

    private void createMailinglist(final GroupEntity group, final boolean isPrivate, final boolean ownerOnly) {
        final MailingListEntity entity = new MailingListEntity();
        entity.setExternalId(group.getExternalId());
        entity.setListAddress(group.getListName());
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
                    entity.setMember(user.getUser().getUsername());
                    entity.setCreated(user.getCreated());

                    mailDao.persist(entity);
                    persisted++;
                }
            } else {
                final MailingListMembershipEntity entity = new MailingListMembershipEntity();
                entity.setMailingList(list);
                entity.setMember(user.getUser().getUsername());
                entity.setCreated(user.getCreated());

                mailDao.persist(entity);
                persisted++;
            }
        }

        return persisted;
    }
}
