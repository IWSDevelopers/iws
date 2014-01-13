/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.core.transformers.AdministrationTransformer;
import net.iaeste.iws.migrate.entities.IW3User2GroupEntity;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @since   1.7
 */
@Transactional
public final class UserGroupMigrator extends AbstractMigrator<IW3User2GroupEntity> {

    private static final Logger log = LoggerFactory.getLogger(UserGroupMigrator.class);

    /**
     * Default Constructor for the UserGroups Migration.
     *
     * @param accessDao IWS Dao for persisting the new IWS Entities
     */
    public UserGroupMigrator(final AccessDao accessDao) {
        super(accessDao);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MigrationResult migrate(final List<IW3User2GroupEntity> oldEntities) {
        int persisted = 0;
        int skipped = 0;

        for (final IW3User2GroupEntity oldUserGroupEntity : oldEntities) {
            UserGroupEntity toPersist = null;

            final UserEntity user = accessDao.findUserByIW3Id(oldUserGroupEntity.getUser().getUserid());
            final UserGroupEntity entity = convertOldEntity(oldUserGroupEntity, user);

            if (user != null) {
                toPersist = entity;
                persisted++;
            } else {
                log.info("Skipping UserGroup Entity where the user no longer exists (userid={}, groupid={}).",
                        oldUserGroupEntity.getUser().getUserid(),
                        oldUserGroupEntity.getGroup().getGroupid());
                skipped++;
            }

            doPersistEntity(toPersist);
        }

        return new MigrationResult(persisted, skipped);
    }

    private UserGroupEntity convertOldEntity(final IW3User2GroupEntity oldUserGroupEntity, final UserEntity user) {
        final GroupEntity group = accessDao.findGroupByIW3Id(oldUserGroupEntity.getGroup().getGroupid());
        final RoleEntity role = accessDao.findRoleById(0L + oldUserGroupEntity.getRole().getRoleid());

        final UserGroupEntity entity = new UserGroupEntity(user, group, role);
        entity.setTitle(AbstractMigrator.convert(oldUserGroupEntity.getUsertitle()));
        entity.setOnPublicList(oldUserGroupEntity.getOnmailinglist());
        entity.setOnPrivateList(oldUserGroupEntity.getOnmailinglist());
        entity.setModified(AbstractMigrator.convert(oldUserGroupEntity.getModified()));
        entity.setCreated(AbstractMigrator.convert(oldUserGroupEntity.getCreated(), oldUserGroupEntity.getModified()));

        return entity;
    }

    private void doPersistEntity(final UserGroupEntity toPersist) {
        if (toPersist != null) {
            UserGroup dto = null;
            try {
                dto = AdministrationTransformer.transform(toPersist);
                dto.verify();
                accessDao.persist(toPersist);
            } catch (IllegalArgumentException | VerificationException e) {
                log.error("Cannot process UserGroup {} => {}", dto, e.getMessage());
            } catch (final RuntimeException e) {
                log.error("Unknown problem while migrating UserGroup {} => {}", dto, e.getMessage());
            }
        }
    }
}
