/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.migrators.UserGroupMigrator
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

import static net.iaeste.iws.migrate.migrators.Helpers.convert;

import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.core.transformers.AdministrationTransformer;
import net.iaeste.iws.migrate.daos.IWSDao;
import net.iaeste.iws.migrate.entities.IW3User2GroupEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Migrateds the User2Group from IW3 to UserGroup in IWS. The IW3 database
 * contains inconsistencies which makes it hard to just migrate, hence a simple
 * trick was to check if an Entity exists or not, and if one exists then it is
 * only updated if the latest changes are newer than what already has been
 * saved.<br />
 *   The methods are made error tolerant, meaning that no exceptions should be
 * escaping the migration. Errors are all logged, so later analysis of the
 * errors can help improve any incorrect migration entries.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class UserGroupMigrator implements Migrator<IW3User2GroupEntity> {

    private static final Logger LOG = LoggerFactory.getLogger(UserGroupMigrator.class);

    @Autowired
    private IWSDao iwsDao;

    public UserGroupMigrator() {
    }

    public UserGroupMigrator(final IWSDao iwsDao) {
        this.iwsDao = iwsDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MigrationResult migrate() {
        throw new IllegalArgumentException("This Migrator is not allowed here.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = "transactionManagerIWS", propagation = Propagation.REQUIRES_NEW)
    public MigrationResult migrate(final List<IW3User2GroupEntity> oldEntities) {
        int persisted = 0;
        int skipped = 0;

        for (final IW3User2GroupEntity oldUserGroupEntity : oldEntities) {

            if ((oldUserGroupEntity.getUser().getUserid() == 3005) && (oldUserGroupEntity.getGroup().getGroupid() == 30)) {
                LOG.info("Skipping the relation between {} and {}, it is an error.",
                        convert(oldUserGroupEntity.getUser().getUsername()),
                        convert(oldUserGroupEntity.getGroup().getFullname()));
                skipped++;
            } else {
                final UserEntity user = iwsDao.findUserByIW3Id(oldUserGroupEntity.getUser().getUserid());
                final UserGroupEntity entity = convertOldEntity(oldUserGroupEntity, user);
                UserGroupEntity toPersist = null;

                if (user != null) {
                    toPersist = entity;
                    persisted++;
                } else {
                    LOG.info("Skipping UserGroup Entity where the user no longer exists (userid={}, groupid={}).",
                            oldUserGroupEntity.getUser().getUserid(),
                            oldUserGroupEntity.getGroup().getGroupid());
                    skipped++;
                }

                doPersistEntity(toPersist);
            }
        }

        return new MigrationResult(persisted, skipped);
    }

    // =========================================================================
    // Internal UserGroup Migration Methods
    // =========================================================================

    private UserGroupEntity convertOldEntity(final IW3User2GroupEntity oldUserGroupEntity, final UserEntity user) {
        final GroupEntity group = iwsDao.findGroupByIW3Id(oldUserGroupEntity.getGroup().getGroupid());
        final RoleEntity role = iwsDao.findRoleById(Long.valueOf(oldUserGroupEntity.getRole().getRoleid()));

        final UserGroupEntity entity = new UserGroupEntity(user, group, role);
        entity.setTitle(convert(oldUserGroupEntity.getUsertitle()));
        entity.setOnPublicList(oldUserGroupEntity.getOnmailinglist());
        entity.setOnPrivateList(oldUserGroupEntity.getOnmailinglist());
        entity.setWriteToPrivateList(oldUserGroupEntity.getOnmailinglist());
        entity.setModified(convert(oldUserGroupEntity.getModified()));
        entity.setCreated(convert(oldUserGroupEntity.getCreated(), oldUserGroupEntity.getModified()));

        return entity;
    }

    private void doPersistEntity(final UserGroupEntity toPersist) {
        if (toPersist != null) {
            UserGroup dto = null;
            try {
                dto = AdministrationTransformer.transform(toPersist);
                dto.verify();
                iwsDao.persist(toPersist);
            } catch (IllegalArgumentException | VerificationException e) {
                LOG.error("Cannot process UserGroup {} => {}", dto, e.getMessage());
            } catch (RuntimeException e) {
                LOG.error("Unknown problem while migrating UserGroup {} => {}", dto, e.getMessage());
            }
        }
    }
}
