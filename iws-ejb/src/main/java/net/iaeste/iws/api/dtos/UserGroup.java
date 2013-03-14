/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.UserGroup
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
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.util.AbstractFallible;

/**
 * This Object contains the information about a User in a Group relation. It is
 * used for fetching user information. If a user has chosen to allow private
 * information to be displayed, then it is also set - otherwise it isn't.<br />
 *   Note, this Object is purely for reading information about a User, it does
 * not provide details about Permissions, or anything else.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection OverlyComplexMethod
 */
public final class UserGroup extends AbstractFallible {

    /** {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String userId = null;
    private String title = null;
    private String firstname = null;
    private String lastname = null;
    private UserStatus status = null;
    private Person person = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public UserGroup() {
    }

    public UserGroup(final String userId, final String title, final String firstname, final String lastname, final UserStatus status) {
        this.userId = userId;
        this.title = title;
        this.firstname = firstname;
        this.lastname = lastname;
        this.status = status;
    }

    /**
     * Copy Constructor.
     *
     * @param userGroup User Group Object to copy
     */
    public UserGroup(final UserGroup userGroup) {
        if (userGroup != null) {
            userId = userGroup.userId;
            title = userGroup.title;
            firstname = userGroup.firstname;
            lastname = userGroup.lastname;
            status = userGroup.status;
            person = userGroup.person;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setStatus(final UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof UserGroup)) {
            return false;
        }

        final UserGroup userGroup = (UserGroup) obj;

        if (firstname != null ? !firstname.equals(userGroup.firstname) : userGroup.firstname != null) {
            return false;
        }

        if (lastname != null ? !lastname.equals(userGroup.lastname) : userGroup.lastname != null) {
            return false;
        }

        if (status != userGroup.status) {
            return false;
        }

        if (title != null ? !title.equals(userGroup.title) : userGroup.title != null) {
            return false;
        }

        if (person != null ? !person.equals(userGroup.person) : userGroup.person != null) {
            return false;
        }

        return !(userId != null ? !userId.equals(userGroup.userId) : userGroup.userId != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (userId != null ? userId.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (title != null ? title.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (firstname != null ? firstname.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (lastname != null ? lastname.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (status != null ? status.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (person != null ? person.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "UserGroup{" +
                "userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", status=" + status +
                ", person=" + person +
                '}';
    }
}
