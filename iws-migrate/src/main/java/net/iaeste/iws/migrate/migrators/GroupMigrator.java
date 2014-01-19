/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.migrators.GroupMigrator
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

import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.core.transformers.CommonTransformer;
import net.iaeste.iws.migrate.entities.IW3GroupsEntity;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Transactional
public final class GroupMigrator extends AbstractMigrator<IW3GroupsEntity> {

    private static final Logger log = LoggerFactory.getLogger(GroupMigrator.class);

    /**
     * Default Constructor for the Groups Migration.
     *
     * @param accessDao IWS Dao for persisting the new IWS Entities
     */
    public GroupMigrator(final AccessDao accessDao) {
        super(accessDao);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MigrationResult migrate(final List<IW3GroupsEntity> oldEntities) {
        int persisted = 0;
        int skipped = 0;

        for (final IW3GroupsEntity oldGroup : oldEntities) {
            final GroupTypeEntity groupType = accessDao.findGroupType(convertGroupType(oldGroup.getGrouptype().getGrouptype()));
            final GroupEntity parent = accessDao.findGroupByIW3Id(oldGroup.getParentid());
            final GroupEntity converted = convertGroup(oldGroup, groupType, parent);
            GroupEntity toPersist = null;

            // Handling standard Groups, these already exists, so we need to
            // properly map the old information into the new Groups - hence
            // we're dealing with them in a special way
            if (converted.getOldId() < 10) {
                final GroupEntity existing = accessDao.findGroupByIW3Id(converted.getOldId());
                if (existing != null) {
                    existing.merge(converted);
                    toPersist = existing;
                } else {
                    log.info("The standard group {} (id {}), is dropped.", converted.getGroupName(), converted.getOldId());
                    skipped++;
                }
            } else if ((converted.getOldId() == 749) || (converted.getOldId() == 839)) {
                log.info("The Group {} (id {}), is dropped, since it's an unused duplicate.", converted.getGroupName(), converted.getOldId());
                skipped++;
            } else if (converted.getOldId() == 672) {
                log.info("The Group {} (id 672), is dropped - there is a name clash, and it is unused.", converted.getGroupName());
                skipped++;
            } else {
                if (parent == null) {
                    // For Holland, we have the problem that Group 629 exists, but 628 (the parent) doesn't.
                    log.info("Couldn't find a parent for {} with id {}", convert(oldGroup.getGroupname()), oldGroup.getGroupid());
                    converted.setParentId(0L);
                } else {
                    converted.setParentId(parent.getId());
                }
                converted.setCountry(convertCountry(parent, oldGroup));
                converted.setGroupType(groupType);
                toPersist = converted;
            }

            // Done with the preparations, now we're going to verify the entity
            // and then persist it
            if (toPersist != null) {
                Group group = null;
                try {
                    group = CommonTransformer.transform(toPersist);
                    group.verify();
                    accessDao.persist(toPersist);

                    persisted++;
                } catch (IllegalArgumentException | VerificationException e) {
                    log.error("Cannot process Group {} => {}", group, e.getMessage());
                } catch (final RuntimeException e) {
                    log.error("Unknown problem while migrating Group {} => {}", group, e.getMessage());
                }
            }
        }

        return new MigrationResult(persisted, skipped);
    }

    private CountryEntity convertCountry(final GroupEntity parent, final IW3GroupsEntity oldGroup) {
        final CountryEntity entity;

        if ((parent != null) && (parent.getCountry() != null)) {
            entity = parent.getCountry();
        } else {
            entity = findExistingCountry(findCorrectCountry(oldGroup.getCountryid(), oldGroup.getRealcountryid()));
        }
        return entity;
    }

    public GroupEntity convertGroup(final IW3GroupsEntity entity, final GroupTypeEntity groupTypeEntity, final GroupEntity parent) {
        final GroupEntity group = new GroupEntity();
        final GroupType type = groupTypeEntity.getGrouptype();

        group.setOldId(entity.getGroupid());
        group.setGroupName(convertGroupName(type, convert(entity.getGroupname()), parent));
        group.setDescription(convert(entity.getGroupdescription()));
        group.setFullName(convertFullName(type, convert(entity.getGroupname()), parent));
        group.setListName(convertListName(type, convert(entity.getGroupname()), parent));
        group.setParentId(0L + entity.getParentid());
        group.setStatus(convertGroupStatus(entity.getStatus()));
        group.setModified(convert(entity.getModified()));
        group.setCreated(convert(entity.getCreated(), entity.getModified()));

        return group;
    }

    private String convertGroupName(final GroupType type, final String committee, final GroupEntity parent) {
        final String result;

        switch (type) {
            case NATIONAL:
                if (parent == null) {
                    // Special case for IWS Group 629, Holland Staff, where
                    // Members are missing. We need it migrated, since it is
                    // needed for Exchanged Offers
                    result = "Holland " + type.getDescription();
                } else {
                    result = parent.getGroupName() + ' ' + type.getDescription();
                }
                break;
            case STUDENT:
                result = parent.getGroupName() + ' ' + type.getDescription();
                break;
            case LOCAL:
            case WORKGROUP:
                result = committee;
                break;
            case ADMINISTRATION:
            case INTERNATIONAL:
            case MEMBER:
            case PRIVATE:
            default:
                result = committee;
        }

        return result;
    }

    private String convertFullName(final GroupType type, final String committee, final GroupEntity parent) {
        final String result;

        switch (type) {
            case MEMBER:
                result = committee + ' ' + type.getDescription();
                break;
            case NATIONAL:
                if (parent == null) {
                    // Special case for IWS Group 629, Holland Staff, where
                    // Members are missing. We need it migrated, since it is
                    // needed for Exchanged Offers
                    result = "Holland " + type.getDescription();
                } else {
                    result = parent.getGroupName() + ' ' + type.getDescription();
                }
                break;
            case STUDENT:
                result = parent.getGroupName() + ' ' + type.getDescription();
                break;
            case LOCAL:
                result = parent.getGroupName() + ' ' + committee;
                break;
            case WORKGROUP:
                // Work Groups can be subgrouped by Local Committees & Staff
                // Groups alike, However, we would like the full name to display
                // the correct hierarchy. Members are special here, since they
                // are the Base for all other groups, but have a different
                // fullname. For the rest we're just using the fullname.
                if (parent.getGroupType().getGrouptype() == GroupType.MEMBER) {
                    result = parent.getGroupName() + ' ' + committee;
                } else {
                    result = parent.getFullName() + ' ' + committee;
                }
                break;
            case ADMINISTRATION:
            case INTERNATIONAL:
            case PRIVATE:
            default:
                result = committee;
        }

        return result;
    }

    private String convertListName(final GroupType type, final String committee, final GroupEntity parent) {
        final String result;

        switch (type) {
            case MEMBER:
                result = committee;
                break;
            case NATIONAL:
                if (parent == null) {
                    // Special case for IWS Group 629, Holland Staff, where
                    // Members are missing. We need it migrated, since it is
                    // needed for Exchanged Offers. Since the Group is obviously
                    // no longer active, we're setting the list to null (none)
                    result = null;
                } else {
                    result = parent.getGroupName();
                }
                break;
            case STUDENT:
                result = parent.getGroupName() + '.' + type.getDescription();
                break;
            case LOCAL:
                result = parent.getListName() + '.' + committee;
                break;
            case WORKGROUP:
                // Work Groups can be subgrouped by Local Committees & Staff
                // Groups alike, However, we would like the full name to display
                // the correct hierarchy. Members are special here, since they
                // are the Base for all other groups, but have a different
                // fullname. For the rest we're just using the fullname.
                if (parent.getGroupType().getGrouptype() == GroupType.NATIONAL) {
                    result = parent.getListName() + ".NC." + committee;
                } else {
                    result = parent.getListName() + '.' + committee;
                }
                break;
            case ADMINISTRATION:
            case INTERNATIONAL:
            case PRIVATE:
            default:
                result = committee;
        }

        return result != null ? result.replace(", ", "_").replace(' ', '_') : null;
    }

    public static GroupType convertGroupType(final String type) {
        final GroupType groupType;

        switch (upper(type)) {
            case "ADMINISTRATION" :
                groupType = GroupType.ADMINISTRATION;
                break;
            case "MEMBERS" :
                groupType = GroupType.MEMBER;
                break;
            case "INTERNATIONAL" :
                groupType = GroupType.INTERNATIONAL;
                break;
            case "NATIONAL" :
            case "SAR" :
                // SAR's have been discontinued, instead we're only refering to
                // National Groups.
                groupType = GroupType.NATIONAL;
                break;
            case "LOCAL" :
                groupType = GroupType.LOCAL;
                break;
            case "WORKGROUP" :
            default:
                // According to the following Query: The above listing is those
                // with a relevance. So we simply just let the WorkGroup fall
                // through as our default.
                //
                // select
                //     gt.grouptypeid,
                //     gt.grouptype,
                //     count(g.groupid) as used
                // from grouptypes gt
                //     left join groups g on gt.grouptypeid=g.grouptypeid
                // group by gt.grouptypeid, gt.grouptype
                // order by grouptypeid;
                groupType = GroupType.WORKGROUP;
        }

        return groupType;
    }

    private static GroupStatus convertGroupStatus(final String status) {
        final GroupStatus groupStatus;

        switch (upper(status)) {
            case "ACTIVE" :
                groupStatus = GroupStatus.ACTIVE;
                break;
            case "SUSPENDED" :
                groupStatus = GroupStatus.SUSPENDED;
                break;
            default:
                groupStatus = GroupStatus.DELETED;
        }

        return groupStatus;
    }

    private static String findCorrectCountry(final String countryid, final String realcountryid) {
        final String result;

        if (countryid.equals(realcountryid)) {
            result = countryid;
        } else if (!"$$".equals(realcountryid)) {
            result = realcountryid;
        } else {
            result = countryid;
        }

        return result;
    }
}
