/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.GroupType
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums;

/**
 * All Groups in the IW has to be assigned an overall type, which determines its
 * basic functionality (permissions). Please note, that certain GroupTypes, are
 * designed so any given user may only be member of 1 (one), others are open,
 * so users can be part of many. The restricted groups are: Administration,
 * Members and National - In fact, a user can only be member of National Group.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public enum GroupType {

    /**
     * The Administration Group is present to ensure that certain users may
     * perform special tasks, that is otherwise not allowed. For example, to
     * avoid data corruption, it is not allowed for users to change their first
     * and lastnames. However, there are cases where you wish to change them,
     * such as marriage where the family name is changed.<br />
     *   There can only exists 1 Administration Group.
     */
    ADMINISTRATION("Administration"),

    /**
     * All user accounts have a private group assigned, with this type. It is
     * there to ensure that all data is only linked with Groups, and allow a
     * simpler logic when handling data.<br />
     *   The Private group is also there to ensure that if a user is removed
     * from the system, then the private data can be easily removed as
     * well.
     */
    PRIVATE("Private"),

    /**
     * All members are assigned to this type, which gives the rights to the
     * basic functionality in the system.<br />
     *   Each member country have a designated Members group, where all their
     * members are added. However, as some members may not be a member of a
     * specific country, to avoid conflicts between their work and their
     * national organization - another group exists called "Global", for all
     * other members. Mostly this consists of the General Secretary, Ombudsman,
     * IDT members, etc.<br />
     *   Note; users can only be member of 1 Members Group!
     */
    MEMBER("Member"),

    /**
     * A number of Groups exists, which are truly "International", meaning that
     * members of these groups may come from anywhere and there are no
     * restrictions to this.<br />
     *   International Groups serve very specific needs, which means that only
     * the International Groups can post information in the system of general
     * nature, i.e. accessible to all.<br />
     *   The list of pre-defined International Groups include the following:
     * <ul>
     *   <li><b>GS</b><br />
     *   General Secretary, and assigned assistents.
     *   </li>
     *   <li><b>Board</b><br />
     *   Members of the IAESTE A.s.b.l. Board.
     *   </li>
     *   <li><b>SID</b><br />
     *   Members who participate in the annual Seminar on IAESTE Development.
     *   </li>
     *   <li><b>IDT</b><br />
     *   Members of the IAESTE Internet Development Team.
     *   </li>
     *   <li><b>Alumni</b><br />
     *   The members of the IAESTE Alumni organization, formerly known as FoIN,
     *   The Friends of IAESTE Network.
     *   </li>
     *   <li><b>Jump</b><br />
     *   Participants in the annual Jump event, a training forum for members
     *   who wishes to participate in International IAESTE work.
     *   </li>
     *   <li><b>Ombudsmand</b><br />
     *   The IAESTE Ombudsmand.
     *   </li>
     * </ul>
     */
    INTERNATIONAL("International"),

    /**
     * Regional Groups are for a collection of Countries creating a region of
     * the world. This can be anything like the Americas, Asia, Nordic
     * Countries, Central European Countries, EU, etc. These types of groups
     * have some similarities with International Groups, with some important
     * difference. Members can only come from the region itself, and Regional
     * Groups cannot share information for all to review.
     */
    REGIONAL("Regional"),

    /**
     * All Countries have both a Members group, where all the people who are a
     * part of the Organization in that country are listed. However, for the
     * staff, certain other functionality is required. The National Group will
     * make up for that.<br />
     *   The type of functionality will consists of access to certain sections
     * of the IntraWeb, and only some of the members of the Staff group will
     * be allowed to join the NC's Mailinglist.<br />
     *   Note; users can only be member of 1 National Group!
     */
    NATIONAL("National Committee"),

    /**
     * Local Groups are for Local Committees around the Country. Local Groups
     * will have a National Group as parent Group.
     */
    LOCAL("Local Committee"),

    /**
     * For Groups, where you need only to have a common mailinglist as well as
     * some other means of sharing information, the Workgroups will serve this
     * purpose well.<br />
     *   Workgroups can be assigned as a sub-group to any of the other groups.
     */
    WORKGROUP("WorkGroup"),

    /**
     * Alumni Group. Currently unassigned.
     */
    ALUMNI("Alumni"),

    /**
     * The Student Group is for Offer Applicants, meaning that if a person
     * wishes to apply for an Open Offer for a given Country, the person must be
     * a member of the Country Student Group.<br />
     *   Students who have been accepted for an Offer cannot be removed from the
     * Student Group, only those accounts that are currently unassigned can be
     * removed.<br />
     *   When creating "new" Student Accounts, the user is automatically
     * assigned to the Country's Student Group, and additionally to the Members
     * group (with role Student). Normal members who wishes to apply for Offers,
     * must also be added to the Student Group.
     */
    STUDENTS("Students");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    GroupType(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
