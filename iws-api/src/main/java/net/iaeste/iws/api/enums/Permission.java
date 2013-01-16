/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.Permission
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
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public enum Permission {

    // =========================================================================
    // System Control, i.e. permissions on the System or International Scale
    // =========================================================================

    /**
     * When creating a new Committee (Co-operating Institution). This
     * include creating a untested account for the Committee Owner.
     */
    CREATE_COMMITTEE,

    /**
     * When altering information related to the Co-operating Institution. This
     * includes such things as changing the name of the Committee, suspending
     * the Committee and Activating Committee, etc.
     */
    PROCESS_COMMITTEE,

    /**
     * When upgrading membership, i.e. Co-Operating Institution -> Associate
     * Member.
     */
    UPGRADE_COMMITTEE,

    /**
     * Reading a list of all committees, with some flags for pagination,
     * sorting, limiting, etc.
     */
    FETCH_COMMITTEES,

    /**
     *
     */
    CREATE_REGIONAL_GROUP,

    /**
     *
     */
    PROCESS_REGIONAL_GROUP,

    /**
     *
     */
    FETCH_REGIONALS_GROUP,

    // =========================================================================
    // Administration Permissions
    // =========================================================================

    /**
     * The Controlling User Account permission is required, to perform
     * operations against Accounts in the IWS. An Account, is defined as a mean
     * for someone to gain access to the system.<br />
     *   The permission allow for creating new user accounts and also to change
     * the accounts, i.e. change status and delete them.
     */
    CONTROL_USER_ACCOUNT,

    /**
     * To view user accounts, you must be allowed to fetch them first. The
     * viewing is limitted to the account information, and only of the user has
     * allowed that private information is also revealed (opt-in), then more
     * details will be fetched.
     */
    FETCH_USERS,

    /**
     * Process SubGroups, includes the following: Create, Update, Delete & Assign Ownership
     */
    PROCESS_SUB_GROUPS,
    FETCH_GROUPS,
    FETCH_COUNTRIES,
    PROCESS_COUNTRIES,
    PROCESS_USER_GROUP_ASSIGNMENT,
    FETCH_GROUP_MEMBERS,
    CHANGE_GROUP_OWNER,
    MANAGE_USER_GROUP_ASSOCIATION,

    /***************************
     * Exchange
     */

    // Offer
    MANAGE_OFFERS,
    LOOKUP_OFFERS,
    PROCESS_OFFER_TEMPLATES,
    LOOKUP_OFFER_TEMPLATES,
    PROCESS_OFFER_PUBLISH_GROUPS,
    LOOKUP_OFFER_PUBLISH_GROUPS,
    PROCESS_PUBLISH_OFFER,
    LOOKUP_PUBLISH_OFFER,

    LOOKUP_FACULTIES,
    PROCESS_FACULTIES,

    // Student
    PROCESS_STUDENTS,
    MANAGE_COMMITTEE, MANAGE_INTERNATIONAL_GROUP, CREATE_REGION, MANAGE_REGIONAL_GROUP, LOOKUP_STUDENTS
}
