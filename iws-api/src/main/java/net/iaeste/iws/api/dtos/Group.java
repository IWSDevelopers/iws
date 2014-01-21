/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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
    private String fullName = null;
    private String listName = null;
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
            fullName = group.fullName;
            listName = group.listName;
            groupType = group.groupType;
            description = group.description;
            country = new Country(group.country);
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the Group Id, which is the internally generated key for this Object.
     * Note, that the presence of the value will determine if the IWS should
     * process this record as if it exist or not. If the Id is set, but no
     * record exists, then the system will reply with an error. Likewise, if no
     * Id is provided, but the record exists, the system will reply with an
     * error.<br />
     *   The value must be a valid Id, otherwise the method will throw an
     * {@code IllegalArgumentException}.
     *
     * @param groupId Group Id
     * @throws IllegalArgumentException if the Id is set but invalid
     * @see AbstractVerification#UUID_FORMAT
     */
    public void setGroupId(final String groupId) throws IllegalArgumentException {
        ensureValidId("groupId", groupId);
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    /**
     * Sets the name of the Group. The name may neither be null nor empty or too
     * long, the maximum allowed length is 50 characters. Further, the name of
     * the Group must be unique in the context, i.e. among other groups with the
     * same parent.<br />
     *   The method will throw an {@code IllegalArgumentException} if the given
     * value is invalid, i.e. null, empty or too long.
     *
     * @param groupName The name of the Group
     * @throws IllegalArgumentException if the given value is invalid
     */
    public void setGroupName(final String groupName) throws IllegalArgumentException {
        ensureNotNullOrEmptyOrTooLong("name", groupName, 50);
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    /**
     * This method is setting the Fullname of the Group. The fullname is
     * depending on the Group hierarchy. Note, this value is controlled
     * internally, so any changes will be ignored.
     *
     * @param fullName Group Fullname
     */
    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    /**
     * This method is setting the Group mailinglist name, meaning the e-mail
     * address which is associated with the Group. Note, this value is
     * controlled internally, so any changes will be ignored.
     *
     * @param listName Group Mailinglist name
     */
    public void setListName(final String listName) {
        this.listName = listName;
    }

    public String getListName() {
        final String list;

        if ((groupType != null) && (listName != null)) {
            switch (groupType) {
                case INTERNATIONAL:
                case LOCAL:
                case MEMBER:
                case WORKGROUP:
                    list = listName + '@' + IWSConstants.PRIVATE_EMAIL_ADDRESS;
                    break;
                case NATIONAL:
                    list = listName + '@' + IWSConstants.PUBLIC_EMAIL_ADDRESS;
                    break;
                case ADMINISTRATION:
                case PRIVATE:
                case STUDENT:
                default:
                    list = null;
            }
        } else {
            list = null;
        }

        return list;
    }

    /**
     * Retrieve committee name of this group, if it is of type {@link GroupType#NATIONAL} or {@link GroupType#MEMBER}
     * Otherwise return the name itself
     *
     * @return committee name of this group
     */
    public String getCommitteeName() {
        final String committeeName;

        if (groupType == GroupType.NATIONAL) {
            committeeName = groupName.replace("Staff", "").trim();
        } else if (groupType == GroupType.MEMBER) {
            committeeName = groupName;
        } else if (groupType == GroupType.LOCAL) {
            committeeName = fullName.replace(groupName, "").trim();
        } else {
            committeeName = "";
        }

        return committeeName;
    }

    /**
     * Sets the type of Group. The type is used to determine its internal set
     * of permissions. Hence, the type may not be null.<br />
     *   The method will throw an {@code IllegalArgumentException} if the given
     * value is invalid, i.e. null.
     *
     * @param groupType The Type of Group
     * @throws IllegalArgumentException if the given value is invalid
     */
    public void setGroupType(final GroupType groupType) throws IllegalArgumentException {
        ensureNotNull("groupType", groupType);
        this.groupType = groupType;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    /**
     * Sets the Group Description. This is a way to describe the purpose of the
     * Group for others. The given value may be max 250 characters long.<br />
     *   The method will throw an {@code IllegalArgumentException} if the given
     * value is invalid, i.e. too long.
     *
     * @param description The Description of the Group
     * @throws IllegalArgumentException if the given value is invalid
     */
    public void setDescription(final String description) {
        ensureNotTooLong("description", description, 250);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Sets the Group Country, if the Group is having a Country relation.
     *
     * @param country The Country for this Group
     */
    public void setCountry(final Country country) {
        this.country = new Country(country);
    }

    public Country getCountry() {
        return new Country(country);
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
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Group)) {
            return false;
        }

        final Group group = (Group) o;

        if (groupId != null ? !groupId.equals(group.groupId) : group.groupId != null) {
            return false;
        }
        if (groupName != null ? !groupName.equals(group.groupName) : group.groupName != null) {
            return false;
        }
        if (fullName != null ? !fullName.equals(group.fullName) : group.fullName != null) {
            return false;
        }
        if (listName != null ? !listName.equals(group.listName) : group.listName != null) {
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
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (fullName != null ? fullName.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (listName != null ? listName.hashCode() : 0);
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
                ", fullName='" + fullName + '\'' +
                ", listName='" + getListName() + '\'' +
                ", groupType=" + groupType +
                ", description='" + description + '\'' +
                ", country=" + country +
                '}';
    }
}
