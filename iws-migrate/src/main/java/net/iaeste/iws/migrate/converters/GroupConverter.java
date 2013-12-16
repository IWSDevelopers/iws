/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.converters.GroupConverter
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.converters;

import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.migrate.entities.IW3GroupsEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class GroupConverter extends CommonConverter {

    public GroupEntity convert(final IW3GroupsEntity entity) {
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
}
