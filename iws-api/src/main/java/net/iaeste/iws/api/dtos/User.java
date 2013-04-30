/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.User
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
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.util.AbstractFallible;
import net.iaeste.iws.api.util.Verifiable;

import java.util.HashMap;
import java.util.Map;

/**
 * User Object, contain the system specific information related to a user, and
 * the personal details in the Person Object. Please note, that a user is
 * considered a vital Object in the system and cannot be deleted. The personal
 * details can be deleted.<br />
 *   Since the username is the e-mail of a user which is subject to changes, it
 * is, of course, possible to change it - just as it is possible to change the
 * password. However, the name of a user cannot be altered. The system is
 * designed as a multi-user system, where it is possible to assign rights to
 * others. However, giving an account to another user by altering the name of
 * the user, can be very confusing, since older records are now suddently owned
 * by a different person, who may not have had anything to do with the original
 * user.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection OverlyComplexMethod
 */
public final class User extends AbstractFallible implements Verifiable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String userId = null;
    // The Username is the users private e-mail address
    private String username = null;
    // The Alias is an e-mail address provided by the system
    private String alias = null;
    private String firstname = null;
    private String lastname = null;
    private UserStatus status = null;
    private Privacy privacy = Privacy.PRIVATE;
    private String notifications = null;
    private String memberCountryId = null;
    private Person person = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public User() {
    }

    /**
     * Constructor for an existing user, where the personal details should be
     * updated.
     *
     * @param userId The internal Id of the user
     * @param person The personal details
     */
    public User(final String userId, final Person person) {
        this.userId = userId;
        this.person = person;
    }

    /**
     * Constructor for an existing user, where the personal details should be
     * updated. Please note, that there is a state machine checking the current
     * status against the new.<br />
     *   Below is the State machine presented. A User always starts with Status
     * "New", and can from there either get the status "Active" or "Blocked".
     * If the user is removed, the status is then set to "Deleted" - meaning
     * that all private data is removed from the system, and the user account
     * is deactivated. However, it is important to note that the User Object in
     * the system will remain there - the reason for this, is that the User may
     * also have worked with Group data, and thus the information about the
     * person must be preserved in the history.
     * <pre>
     *              NEW
     *             /   \
     *            /     \
     *       ACTIVE <-> BLOCKED
     *           \      /
     *            \    /
     *           DELETED
     * </pre>
     *
     * @param userId The internal Id of the user
     * @param status The new Status
     */
    public User(final String userId, final UserStatus status) {
        this.userId = userId;
        this.status = status;
    }

    /**
     * Copy Constructor.
     *
     * @param user User Object to copy
     */
    public User(final User user) {
        if (user != null) {
            userId = user.userId;
            username = user.username;
            alias = user.alias;
            firstname = user.firstname;
            lastname = user.lastname;
            status = user.status;
            privacy = user.privacy;
            memberCountryId = user.memberCountryId;
            person = new Person(user.person);
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

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setAlias(final String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
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

    public void setPrivacy(final Privacy privacy) {
        this.privacy = privacy;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setNotifications(final String notifications) {
        this.notifications = notifications;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setMemberCountryId(final String memberCountryId) {
        this.memberCountryId = memberCountryId;
    }

    public String getMemberCountryId() {
        return memberCountryId;
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
    public void verify() {
        final Map<String, String> validationResult = validate();

        if (!validationResult.isEmpty()) {
            throw new VerificationException("Validation failed: " + validationResult.toString());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        if ((userId == null) || (userId.length() != 36)) {
            validation.put("userId", "Invalid UserId.");
        }

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
        if (!(obj instanceof User)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }

        final User user = (User) obj;

        // Note, the Notifications & Person Objects are omitted, since they are
        // not set for all views of this Object, and we need to verify that two
        // instances are identical, regardless of who is viewing them
        if (userId != null ? !userId.equals(user.userId) : user.userId != null) {
            return false;
        }
        if (username != null ? !username.equals(user.username) : user.username != null) {
            return false;
        }
        if (alias != null ? !alias.equals(user.alias) : user.alias != null) {
            return false;
        }
        if (firstname != null ? !firstname.equals(user.firstname) : user.firstname != null) {
            return false;
        }
        if (lastname != null ? !lastname.equals(user.lastname) : user.lastname != null) {
            return false;
        }
        if (status != user.status) {
            return false;
        }
        if (privacy != user.privacy) {
            return false;
        }

        return !(memberCountryId != null ? !memberCountryId.equals(user.memberCountryId) : user.memberCountryId != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        // Note, the Notifications & Person Objects are omitted, since they are
        // not set for all views of this Object, and we need to verify that two
        // instances are identical, regardless of who is viewing them
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (userId != null ? userId.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (username != null ? username.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (alias != null ? alias.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (firstname != null ? firstname.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (lastname != null ? lastname.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (status != null ? status.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (privacy != null ? privacy.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (memberCountryId != null ? memberCountryId.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        // Note, the Notifications & Person Objects are omitted, since they are
        // not set for all views of this Object, and we need to verify that two
        // instances are identical, regardless of who is viewing them
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", alias='" + alias + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", status=" + status +
                ", privacy=" + privacy +
                ", memberCountryId='" + memberCountryId + '\'' +
                '}';
    }
}
