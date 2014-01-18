/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.util.GroupUtil
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.util;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.enums.GroupType;

/**
 * Common functionality for the Groups. The Class contain methods which is used
 * to generate the Group Names. It is extracted, so it can also be used for
 * other purposes besides the GroupService, such as the Migration.
 *
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class GroupUtil {

    /**
     * Private Constructor, this is a utility class.
     */
    private GroupUtil() {
    }

    /**
     * Preparing the Base name for a Group. The basename is prepared via a set
     * of information.
     *
     * @param parentType      Parent GroupType
     * @param country         Parent Country
     * @param parentGroupName Parent Group Name
     * @param parentFullname  Parent full Group Name
     * @return Basename
     */
    public static String prepareBaseGroupName(final GroupType parentType, final String country, final String parentGroupName, final String parentFullname) {
        final String basename;

        switch (parentType) {
            case MEMBER:
                // Following is required to avoid problems with test data. As
                // most countries already uses the countryname as the base -
                // the solution is production safe
                if (parentFullname == null) {
                    basename = country + '.';
                } else {
                    basename = parentFullname.substring(0, parentFullname.lastIndexOf('.')) + '.';
                }
                break;
            case INTERNATIONAL:
                basename = parentGroupName + '.';
                break;
            case LOCAL:
            case NATIONAL:
            case STUDENT:
            case WORKGROUP:
                basename = parentFullname;
                break;
            case ADMINISTRATION:
            case PRIVATE:
            default:
                basename = "";
        }

        return basename;
    }

    /**
     * Prepares the mailinglist name for the Group. The name depends on a number
     * of different information, which as a result should give a more consistent
     * name for all Groups.
     *
     * @param type     The GroupType
     * @param fullname The full name of the Group
     * @param country  The Country of the Group
     * @return Listname
     */
    public static String prepareListName(final GroupType type, final String fullname, final String country) {
        final String listname;

        switch (type) {
            case MEMBER:
            case NATIONAL:
                if (fullname == null) {
                    listname = country;
                } else {
                    listname = fullname.substring(0, fullname.lastIndexOf('.'));
                }
                break;
            case INTERNATIONAL:
            case LOCAL:
            case WORKGROUP:
                listname = fullname.replace(' ', '_');
                break;
            case ADMINISTRATION:
            case PRIVATE:
            case STUDENT:
            default:
                listname = "";
        }

        return listname;
    }

    /**
     * For some Groups, the Groupname is the default granted name. But for
     * others, the name may be depending upon the functionality. This method
     * will try to prepare the proper GroupName for the Group.
     *
     * @param type  The GroupType
     * @param group The Group
     * @return GroupName for the Group
     */
    public static String prepareGroupName(final GroupType type, final Group group) {
        final String groupName;

        switch (type) {
            // Our Primary Groups have very distinct names
            case MEMBER:   // Example; denmark.members or austria.members
            case NATIONAL: // Example; germany.staff or poland.staff
            case STUDENT:  // Example: united kingdom.students
                groupName = type.getDescription();
                break;
            case LOCAL:
            case WORKGROUP:
            case ADMINISTRATION:
            case INTERNATIONAL:
            case PRIVATE:
            default:
                groupName = group.getGroupName();
        }

        return groupName.toLowerCase(IWSConstants.DEFAULT_LOCALE);
    }

    /**
     * The full GroupName for a Group, means the name of the parents combined
     * with the current Group. The rules which the name must follow is distinct,
     * and depends upon the type of the given Group and its parent.<br />
     *   For National Groups, Member Groups and Student Groups, the name of the
     * parent is the name of the Committee. Although the default is the Country
     * name, this cannot be applied generally, since Local Committees all share
     * the Country name.
     *
     * @param type     The GroupType
     * @param group    The Group
     * @param basename The basename for the group
     * @return Full Group Name
     */
    public static String prepareFullGroupName(final GroupType type, final Group group, final String basename) {
        final String fullGroupName;

        switch (type) {
            // Our Primary Groups have very distinct names
            case MEMBER:   // Example; denmark.members or austria.members
            case NATIONAL: // Example; germany.staff or poland.staff
            case STUDENT:  // Example: united kingdom.students
                fullGroupName = basename + type.getDescription();
                break;
            case LOCAL:
            case WORKGROUP:
                fullGroupName = basename + group.getGroupName();
                break;
            case ADMINISTRATION:
            case INTERNATIONAL:
            case PRIVATE:
            default:
                fullGroupName = group.getGroupName();
        }

        return fullGroupName.toLowerCase(IWSConstants.DEFAULT_LOCALE);
    }
}
