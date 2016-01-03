/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.Storage;
import net.iaeste.iws.api.Students;

import javax.xml.bind.annotation.XmlType;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "permission")
public enum Permission {

    // =========================================================================
    // Administration Permissions
    // =========================================================================

    FETCH_COUNTRIES("Fetch Countries", Administration.class, "fetchCountries"),
    PROCESS_COUNTRY("Process Country", Administration.class, "processCountry"),

    /**
     * <p>The Controlling User Account permission is required, to perform
     * operations against Accounts in the IWS. An Account, is defined as a mean
     * for someone to gain access to the system.</p>
     *
     * <p>The permission allow for creating new user accounts and also to change
     * the accounts, i.e. change status and delete them.</p>
     */
    CONTROL_USER_ACCOUNT("createUser", Administration.class, "createUser", "controlUserAccount"),

    /**
     * <p>The Create Student Account permission is required, to create new
     * Student Accounts in the IWS. An Account, is defined as a mean for someone to gain
     * access to the system.</p>
     *
     * <p>The permission allow for creating a new student accounts.</p>
     */
    CREATE_STUDENT_ACCOUNT("createStudent", Administration.class, "createStudent"),

    /**
     * <p>To view user accounts, you must be allowed to fetch them first. The
     * viewing is limited to the account information, and only of the user has
     * allowed that private information is also revealed (opt-in), then more
     * details will be fetched.</p>
     */
    FETCH_USER("Fetch User", Administration.class, "fetchUser"),

    /**
     * <p>If the name (firstname or lastname) is incorrect, then it requires
     * this permission to update it. It is extracted as a separate request, to
     * try to minimize the abuse of Accounts, i.e. that users simply hand over
     * accounts rather than create and delete accounts, which will safeguard
     * the user history in the system.</p>
     */
    CHANGE_ACCOUNT_NAME("Change Account Name", Administration.class, "changeAccountName"),

    /**
     * <p>Process SubGroups, includes the following: Create, Update, Delete and
     * Assign Ownership.</p>
     */
    PROCESS_GROUP("Process Group", Administration.class, "processGroup"),
    CHANGE_GROUP_OWNER("Change Group Owner", Administration.class, "changeGroupOwner"),
    DELETE_GROUP("Delete Group", Administration.class, "deleteSubGroup"),
    PROCESS_USER_GROUP_ASSIGNMENT("Process UserGroup Assignment", Administration.class, "processUserGroupAssignment", "fetchRoles"),

    /**
     * <p>For retrieving a list of all National Committee Owners &amp;
     * Moderators, who are also on the NC's mailinglist. This is needed to
     * create the Emergency List.</p>
     */
    FETCH_EMERGENCY_LIST("Fetch NC's List", Administration.class, "fetchEmergencyList"),

    // =========================================================================
    // Committee related Permissions
    // =========================================================================

    FETCH_COMMITTEES("Fetch Committees", Committees.class, "fetchCommittees"),
    PROCESS_COMMITTEE("Process Committee", Committees.class, "processCommittee"),
    FETCH_INTERNATIONAL_GROUPS("Fetch International Groups", Committees.class, "fetchInternationalGroups"),
    PROCESS_INTERNATIONAL_GROUP("Process International Group", Committees.class, "processInternationalGroup"),
    FETCH_SURVEY_OF_COUNTRIES("Fetch Survey of Countries", Committees.class, "fetchCountrySurvey"),
    PROCESS_SURVEY_OF_COUNTRIES("Process Survey of Countries", Committees.class, "processCountrySurvey"),

    // =========================================================================
    // File related Permissions
    // =========================================================================

    PROCESS_FILE("Process File", Storage.class, "processFile"),
    FETCH_FILE("Fetch File", Storage.class, "fetchFile"),
    PROCESS_FOLDER("Process Folder", Storage.class, "processFolder"),
    FETCH_FOLDER("Fetch Folder", Storage.class, "fetchFolder"),

    // =========================================================================
    // Exchange related Permissions
    // =========================================================================

    FETCH_OFFER_STATISTICS("Fetch Offer Statistics", Exchange.class, "fetchOfferStatistics"),
    PROCESS_EMPLOYER("Process Employer", Exchange.class, "processEmployer"),
    FETCH_EMPLOYERS("Fetch Employers", Exchange.class, "fetchEmployers"),
    PROCESS_OFFER("Process Offer", Exchange.class, "processOffer", "deleteOffer", "uploadOffers"),
    FETCH_OFFERS("Fetch Offers", Exchange.class, "fetchOffers", "fetchGroupsForSharing", "downloadOffers"),
    FETCH_GROUPS_FOR_SHARING("Fetch Groups for Sharing", Exchange.class, "fetchGroupsForSharing"),
    PROCESS_OFFER_TEMPLATES("processOfferTemplate", Exchange.class, "processOfferTemplate"),
    FETCH_OFFER_TEMPLATES("fetchOfferTemplates", Exchange.class, "fetchOfferTemplates"),
    PROCESS_PUBLISH_OFFER("Process Publish Offer", Exchange.class, "processPublishOffer", "processPublishGroup", "processHideForeignOffers"),
    FETCH_PUBLISH_GROUPS("Fetch Published Groups", Exchange.class, "fetchPublishedGroups", "fetchPublishGroups"),
    APPLY_FOR_OPEN_OFFER("Apply for Open Offer", Exchange.class),

    // =========================================================================
    // Student Related Permissions
    // =========================================================================

    PROCESS_STUDENT("Process Student", Students.class, "processStudent"),
    FETCH_STUDENTS("Fetch Students", Students.class, "fetchStudents"),
    FETCH_STUDENT_APPLICATION("Fetch Student Application", Students.class, "fetchStudentApplications"),
    PROCESS_STUDENT_APPLICATION("Process Student Application", Students.class, "processStudentApplication", "processApplicationStatus");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String name;
    private final Class<?> module;
    private final String[] requests;

    Permission(final String name, final Class<?> module, final String... requests) {
        this.name = name;
        this.module = module;
        this.requests = requests;
    }

    /**
     * Returns the name of the permission in a printable way.
     *
     * @return Printable version of the Permission
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the actual Module, which the current Permission is associated
     * with.
     *
     * @return Module which the Permission is associated with
     */
    public Class<?> getModule() {
        return module;
    }

    /**
     * Returns the list of requests, which this Permission is associated with.
     *
     * @return List of requests for this Permission
     */
    public String[] getRequests() {
        return requests;
    }
}
