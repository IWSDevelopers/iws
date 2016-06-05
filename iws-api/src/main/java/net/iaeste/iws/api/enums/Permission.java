/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.api.enums;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.Storage;
import net.iaeste.iws.api.Students;

import javax.xml.bind.annotation.XmlType;
import java.util.Arrays;

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

    FETCH_COUNTRIES("Fetch Countries", Administration.class, false, "fetchCountries"),
    PROCESS_COUNTRY("Process Country", Administration.class, true, "processCountry"),

    /**
     * <p>The Controlling User Account permission is required, to perform
     * operations against Accounts in the IWS. An Account, is defined as a mean
     * for someone to gain access to the system.</p>
     *
     * <p>The permission allow for creating new user accounts and also to change
     * the accounts, i.e. change status and delete them.</p>
     */
    CONTROL_USER_ACCOUNT("createUser", Administration.class, false, "createUser", "controlUserAccount"),

    /**
     * <p>The Create Student Account permission is required, to create new
     * Student Accounts in the IWS. An Account, is defined as a mean for someone to gain
     * access to the system.</p>
     *
     * <p>The permission allow for creating a new student accounts.</p>
     */
    CREATE_STUDENT_ACCOUNT("createStudent", Administration.class, false, "createStudent"),

    /**
     * <p>To view user accounts, you must be allowed to fetch them first. The
     * viewing is limited to the account information, and only of the user has
     * allowed that private information is also revealed (opt-in), then more
     * details will be fetched.</p>
     */
    FETCH_USER("Fetch User", Administration.class, false, "fetchUser"),

    /**
     * <p>If the name (firstname or lastname) is incorrect, then it requires
     * this permission to update it. It is extracted as a separate request, to
     * try to minimize the abuse of Accounts, i.e. that users simply hand over
     * accounts rather than create and delete accounts, which will safeguard
     * the user history in the system.</p>
     */
    CHANGE_ACCOUNT_NAME("Change Account Name", Administration.class, true, "changeAccountName"),

    /**
     * <p>Allows to control custom Roles, and assign them a different set of
     * Permissions.</p>
     */
    PROCESS_ROLE("Process Role", Administration.class, false, "processRole"),

    /**
     * <p>Process SubGroups, includes the following: Create, Update, Delete and
     * Assign Ownership.</p>
     */
    PROCESS_GROUP("Process Group", Administration.class, false, "processGroup"),
    CHANGE_GROUP_OWNER("Change Group Owner", Administration.class, false, "changeGroupOwner"),
    DELETE_GROUP("Delete Group", Administration.class, false, "deleteSubGroup"),
    PROCESS_USER_GROUP_ASSIGNMENT("Process UserGroup Assignment", Administration.class, false, "processUserGroupAssignment", "fetchRoles"),

    /**
     * <p>For retrieving a list of all National Committee Owners &amp;
     * Moderators, who are also on the NC's mailinglist. This is needed to
     * create the Emergency List.</p>
     */
    FETCH_EMERGENCY_LIST("Fetch NC's List", Administration.class, false, "fetchEmergencyList"),

    // =========================================================================
    // Committee related Permissions
    // =========================================================================

    FETCH_COMMITTEES("Fetch Committees", Committees.class, false, "fetchCommittees"),
    PROCESS_COMMITTEE("Process Committee", Committees.class, true, "processCommittee"),
    FETCH_INTERNATIONAL_GROUPS("Fetch International Groups", Committees.class, false, "fetchInternationalGroups"),
    PROCESS_INTERNATIONAL_GROUP("Process International Group", Committees.class, true, "processInternationalGroup"),
    FETCH_SURVEY_OF_COUNTRIES("Fetch Survey of Countries", Committees.class, false, "fetchCountrySurvey"),
    PROCESS_SURVEY_OF_COUNTRIES("Process Survey of Countries", Committees.class, false, "processCountrySurvey"),

    // =========================================================================
    // File related Permissions
    // =========================================================================

    PROCESS_FILE("Process File", Storage.class, false, "processFile"),
    FETCH_FILE("Fetch File", Storage.class, false, "fetchFile"),
    PROCESS_FOLDER("Process Folder", Storage.class, false, "processFolder"),
    FETCH_FOLDER("Fetch Folder", Storage.class, false, "fetchFolder"),

    // =========================================================================
    // Exchange related Permissions
    // =========================================================================

    FETCH_OFFER_STATISTICS("Fetch Offer Statistics", Exchange.class, false, "fetchOfferStatistics"),
    PROCESS_EMPLOYER("Process Employer", Exchange.class, false, "processEmployer"),
    FETCH_EMPLOYERS("Fetch Employers", Exchange.class, false, "fetchEmployers"),
    PROCESS_OFFER("Process Offer", Exchange.class, false, "processOffer", "deleteOffer", "uploadOffers"),
    FETCH_OFFERS("Fetch Offers", Exchange.class, false, "fetchOffers", "fetchGroupsForSharing", "downloadOffers"),
    FETCH_GROUPS_FOR_SHARING("Fetch Groups for Sharing", Exchange.class, false, "fetchGroupsForSharing"),
    PROCESS_OFFER_TEMPLATES("processOfferTemplate", Exchange.class, false, "processOfferTemplate"),
    FETCH_OFFER_TEMPLATES("fetchOfferTemplates", Exchange.class, false, "fetchOfferTemplates"),
    PROCESS_PUBLISH_OFFER("Process Publish Offer", Exchange.class, false, "processPublishOffer", "processPublishGroup", "processHideForeignOffers"),
    FETCH_PUBLISH_GROUPS("Fetch Published Groups", Exchange.class, false, "fetchPublishedGroups", "fetchPublishGroups"),
    APPLY_FOR_OPEN_OFFER("Apply for Open Offer", Exchange.class, false),

    // =========================================================================
    // Student Related Permissions
    // =========================================================================

    PROCESS_STUDENT("Process Student", Students.class, false, "processStudent"),
    FETCH_STUDENTS("Fetch Students", Students.class, false, "fetchStudents"),
    FETCH_STUDENT_APPLICATION("Fetch Student Application", Students.class, false, "fetchStudentApplications"),
    PROCESS_STUDENT_APPLICATION("Process Student Application", Students.class, false, "processStudentApplication", "processApplicationStatus");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String name;
    private final Class<?> module;
    private final boolean restricted;
    private final String[] requests;

    Permission(final String name, final Class<?> module, final boolean restricted, final String... requests) {
        this.name = name;
        this.module = module;
        this.restricted = restricted;
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
     * Returns true, if this Permission is restricted, meaning that it cannot
     * be used for creating customized roles.
     *
     * @return True if restricted, otherwise false
     */
    public boolean isRestricted() {
        return restricted;
    }

    /**
     * Returns the list of requests, which this Permission is associated with.
     *
     * @return List of requests for this Permission
     */
    public String[] getRequests() {
        return Arrays.copyOf(requests, requests.length);
    }
}
