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

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
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
    public MigrationResult migrate(final List<IW3GroupsEntity> oldEntities) {
        int persisted = 0;
        int skipped = 0;

        for (final IW3GroupsEntity oldGroup : oldEntities) {
            final GroupEntity converted = convertGroup(oldGroup);
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
            } else {
                final CountryEntity country = findExistingCountry(findCorrectCountry(oldGroup.getCountryid(), oldGroup.getRealcountryid()));
                final GroupEntity parent = accessDao.findGroupByIW3Id(oldGroup.getParentid());
                if (parent == null) {
                    // For Holland, we have the problem that Group 629 exists, but 628 (the parent) doesn't.
                    log.info("Couldn't find a parent for {} with id {}", oldGroup.getGroupname(), oldGroup.getGroupid());
                    converted.setParentId(0L);
                } else {
                    converted.setParentId(parent.getId());
                }
                final GroupTypeEntity groupType = accessDao.findGroupType(convertGroupType(oldGroup.getGrouptype().getGrouptype()));
                converted.setCountry(country);
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

    public GroupEntity convertGroup(final IW3GroupsEntity entity) {
        final GroupEntity group = new GroupEntity();

        group.setOldId(entity.getGroupid());
        group.setGroupName(convert(entity.getGroupname()));
        group.setDescription(convert(entity.getGroupdescription()));
        group.setFullName(convert(entity.getFullname()));
        group.setListName(convert(entity.getMailinglistname()));
        group.setParentId(0L + entity.getParentid());
        group.setStatus(convertGroupStatus(entity.getStatus()));
        group.setModified(convert(entity.getModified()));
        group.setCreated(convert(entity.getCreated(), entity.getModified()));

        return group;
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
