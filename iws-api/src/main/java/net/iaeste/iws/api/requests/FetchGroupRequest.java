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
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.util.Verifications;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchGroupRequest", propOrder = { "groupId", "groupType", "usersToFetch", "fetchStudents", "fetchSubGroups" })
public final class FetchGroupRequest extends Verifications {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true) private String groupId = null;
    @XmlElement(required = true, nillable = true) private GroupType groupType = null;
    @XmlElement(required = true, nillable = true) private UserFetchType usersToFetch = UserFetchType.NONE;
    @XmlElement(required = true, nillable = true) private boolean fetchStudents = false;
    @XmlElement(required = true, nillable = true) private boolean fetchSubGroups = false;

    public enum UserFetchType {

        /**
         * If set to this value, no users will be retrieved for this Group.
         */
        NONE,

        /**
         * If set to this value, all currently active users are retrieves for
         * this Group.
         */
        ACTIVE,

        /**
         * If set to this value, all users currently associated with the Group
         * is fetched, this includes Active, New and Suspended users.
         */
        ALL
    }

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchGroupRequest() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Default Constructor.
     *
     * @param groupId Id of the Group to fetch
     */
    public FetchGroupRequest(final String groupId) {
        this.groupId = groupId;
    }

    /**
     * GroupType Constructor, for fetching Groups if which a person can only be
     * member of once. For example, MEMBERS, NATIONAL, LOCAL.
     *
     * @param groupType The Type of the Group
     */
    public FetchGroupRequest(final GroupType groupType) {
        this.groupType = groupType;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setGroupId(final String groupId) {
        ensureValidId("groupId", groupId);

        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupType(final GroupType groupType) {
        this.groupType = groupType;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setUsersToFetch(final UserFetchType usersToFetch) {
        ensureNotNull("usersToFetch", usersToFetch);

        this.usersToFetch = usersToFetch;
    }

    public UserFetchType getUsersToFetch() {
        return usersToFetch;
    }

    public void setFetchStudents(final boolean fetchStudents) {
        this.fetchStudents = fetchStudents;
    }

    public boolean isFetchStudents() {
        return fetchStudents;
    }

    public void setFetchSubGroups(final boolean fetchSubGroups) {
        this.fetchSubGroups = fetchSubGroups;
    }

    public boolean isFetchSubGroups() {
        return fetchSubGroups;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(1);

        if ((groupId == null) && (groupType == null)) {
            validation.put("groupId", "No valid groupId is present.");
            validation.put("groupType", "No valid groupType is present.");
        }

        return validation;
    }
}
