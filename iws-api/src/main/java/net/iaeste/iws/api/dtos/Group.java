/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.Group
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class Group extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String groupId = null;
    private String groupName = null;
    private GroupType groupType = null;
    private String description = null;
    private Country country = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Group() {
    }

    /**
     * Copy Constructor.
     *
     * @param group Group Object to copy
     */
    public Group(final Group group) {
        if (group != null) {
            groupId = group.groupId;
            groupName = group.groupName;
            groupType = group.groupType;
            description = group.description;
            country = group.country;
        }
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

    public void setGroupName(final String groupName) {
        ensureNotNullOrEmptyOrTooLong("name", groupName, 50);
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupType(final GroupType groupType) {
        ensureNotNull("groupType", groupType);
        this.groupType = groupType;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setDescription(final String description) {

        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCountry(final Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "groupName", groupName);
        isNotNull(validation, "groupType", groupType);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Group)) {
            return false;
        }

        final Group group = (Group) obj;

        if (groupId != null ? !groupId.equals(group.groupId) : group.groupId != null) {
            return false;
        }
        if (groupName != null ? !groupName.equals(group.groupName) : group.groupName != null) {
            return false;
        }
        if (groupType != group.groupType) {
            return false;
        }
        if (description != null ? !description.equals(group.description) : group.description != null) {
            return false;
        }

        return !(country != null ? !country.equals(group.country) : group.country != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (groupId != null ? groupId.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (groupName != null ? groupName.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (groupType != null ? groupType.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (description != null ? description.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (country != null ? country.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Group{" +
                "groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupType=" + groupType +
                ", description='" + description + '\'' +
                ", country=" + country +
                '}';
    }
}
