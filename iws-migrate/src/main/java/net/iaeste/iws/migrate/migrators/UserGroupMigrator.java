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

import java.util.Date;
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
    public MigrationResult migrate(final List<IW3User2GroupEntity> oldEntities) {
        int persisted = 0;
        int skipped = 0;
        int updated = 0;

        for (final IW3User2GroupEntity oldUserGroupEntity : oldEntities) {
            final UserGroupEntity userGroup = accessDao.findIw3UserGroup(oldUserGroupEntity.getUser().getUserid(), oldUserGroupEntity.getGroup().getGroupid());
            UserGroupEntity toPersist = null;

            if (userGroup == null) {
                final UserEntity user = accessDao.findUserByIW3Id(oldUserGroupEntity.getUser().getUserid());
                final UserGroupEntity entity = prepareCreateEntity(oldUserGroupEntity, user);

                if (user != null) {
                    toPersist = entity;
                    persisted++;
                } else {
                    skipped++;
                    log.info("Skipping UserGroup Entity where the user no longer exists (userid={}).", oldUserGroupEntity.getUser().getUserid());
                }
            } else {
                log.info("Duplicate UserGroup Entity found, ");
                final Date modified = oldUserGroupEntity.getModified();

                if ((modified != null) && modified.after(userGroup.getModified())) {
                    log.info("changes are more recent than existing record - Merging.");
                    toPersist = prepareUpdateEntity(oldUserGroupEntity, userGroup);
                    updated++;
                } else {
                    skipped++;
                    log.info("Changes are older than the existing record - Skipping.");
                }
            }

            doPersistEntity(toPersist);
        }

        return new MigrationResult(persisted, skipped, updated);
    }

    private UserGroupEntity prepareCreateEntity(final IW3User2GroupEntity oldUserGroupEntity, final UserEntity user) {
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

    private UserGroupEntity prepareUpdateEntity(final IW3User2GroupEntity oldUserGroupEntity, final UserGroupEntity userGroup) {
        userGroup.setTitle(oldUserGroupEntity.getUsertitle());
        userGroup.setOnPublicList(oldUserGroupEntity.getOnmailinglist());
        userGroup.setOnPrivateList(oldUserGroupEntity.getOnmailinglist());
        userGroup.setModified(AbstractMigrator.convert(oldUserGroupEntity.getModified()));

        return userGroup;
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
