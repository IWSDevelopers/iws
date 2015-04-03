/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.FetchGroupRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.util.AbstractVerification;

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
@XmlType(name = "FetchGroupRequest", propOrder = { "groupId", "groupType", "usersToFetch", "fetchStudents", "fetchSubGroups" })
public final class FetchGroupRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true) private String groupId = null;
    @XmlElement(required = true, nillable = true) private GroupType groupType = null;
    @XmlElement(required = true, nillable = true) private FetchType usersToFetch = FetchType.NONE;
    @XmlElement(required = true, nillable = true) private boolean fetchStudents = false;
    @XmlElement(required = true, nillable = true) private boolean fetchSubGroups = false;

    public enum FetchType {

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

    public void setUsersToFetch(final FetchType usersToFetch) {
        ensureNotNull("usersToFetch", usersToFetch);

        this.usersToFetch = usersToFetch;
    }

    public FetchType getUsersToFetch() {
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
