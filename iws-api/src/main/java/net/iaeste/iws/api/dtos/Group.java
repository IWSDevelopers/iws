/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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
    private String countryId = null;

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
            countryId = group.countryId;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupType(final GroupType groupType) {
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

    public void setCountryId(final String countryId) {
        this.countryId = countryId;
    }

    public String getCountryId() {
        return countryId;
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

        if (countryId != null ? !countryId.equals(group.countryId) : group.countryId != null) {
            return false;
        }

        if (groupType != null ? groupType != group.groupType : group.groupType != null) {
            return false;
        }

        if (description != null ? !description.equals(group.description) : group.description != null) {
            return false;
        }

        return !(countryId != null ? !countryId.equals(group.countryId) : group.countryId != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = IWSConstants.HASHCODE_INITIAL_VALUE;

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (groupId != null ? groupId.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (groupName != null ? groupName.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (groupType != null ? groupType.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (description != null ? description.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (countryId != null ? countryId.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Group{" +
                "groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupType='" + groupType + '\'' +
                ", description='" + description + '\'' +
                ", countryId='" + countryId + '\'' +
                '}';
    }
}
