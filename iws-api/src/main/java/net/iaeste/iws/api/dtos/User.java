/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.requests.Verifiable;
import net.iaeste.iws.api.responses.AbstractResponse;

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
 * @noinspection CastToConcreteClass, VariableNotUsedInsideIf
 */
public final class User extends AbstractResponse implements Verifiable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String userId = null;
    private UserStatus status = null;
    private String firstname = null;
    private String lastname = null;
    private Privacy privacy = Privacy.PRIVATE;
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
            status = user.status;
            firstname = user.firstname;
            lastname = user.lastname;
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

    public void setStatus(final UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
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

    public void setPrivacy(final Privacy privacy) {
        this.privacy = privacy;
    }

    public Privacy getPrivacy() {
        return privacy;
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
        // This Object is used to either read a users information, or to update
        // same.
        // Object is used for either of these requests:
        // 1. Create new users (username, firstname, lastname must be valid)
        // 2. Update user personal details (userid, person)
        // 3. Delete user (userId)
    }

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

        if (!(obj instanceof User)) {
            return false;
        }

        final User user = (User) obj;
        return !(userId != null ? !userId.equals(user.userId) : user.userId != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + userId.hashCode();
        result = IWSConstants.HASHCODE_MULTIPLIER * result + firstname.hashCode();
        result = IWSConstants.HASHCODE_MULTIPLIER * result + lastname.hashCode();

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
