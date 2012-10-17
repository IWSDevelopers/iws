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
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.Verifiable;
import net.iaeste.iws.api.responses.AbstractResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * User Object, contain the system specific information related to a user, and
 * the personal details in the Person Object. Please note, that a user is
 * considered a vital Object in the system and cannot be deleted. The personal
 * details can be deleted.<br />
 * Since the username is the e-mail of a user which is subject to changes, it
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

    /** The e-mail compliance regular expression. */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(IWSConstants.EMAIL_REGEX);

    private Long userId = null;
    private UserStatus status = UserStatus.ACTIVE;
    private String username = null;
    private String password = null;
    private String firstname = null;
    private String lastname = null;
    private Person person = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public User() {
    }

    /**
     * Constructor to be used to create a new User account. The username must
     * be a valid e-mail address. Likewise, the first and last name must also
     * be valid, since these cannot be altered later!
     *
     * @param username  Username (e-mail address)
     * @param firstname User First (Given) name
     * @param lastname  User Last (Family) name
     */
    public User(final String username, final String firstname, final String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    /**
     * Constructor for an existing user, where the personal details should be
     * updated.
     *
     * @param userId The internal Id of the user
     * @param person The personal details
     */
    public User(final Long userId, final Person person) {
        this.userId = userId;
        this.person = person;
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
            username = user.username;
            password = user.password;
            firstname = user.firstname;
            lastname = user.lastname;
            person = new Person(user.person);
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setStatus(final UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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
        // Object is used for either of these requests:
        // 1. Create new users (username, firstname, lastname must be valid)
        // 2. Update user personal details (userid, person)
        // 3. Delete user (userId)

        if (userId != null) {
            if (person != null) {
                person.verify();
            }
            if ((password != null) && password.isEmpty()) {
                throw new VerificationException("Passwords cannot be empty.");
            }
        } else if (username != null) {
            if (!EMAIL_PATTERN.matcher(username).matches()) {
                throw new VerificationException("The username is not a valid e-mail address.");
            }

            if ((firstname == null) || (lastname == null) || firstname.isEmpty() || lastname.isEmpty()) {
                throw new VerificationException("New users must have both a first and last name.");
            }
        }
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
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + userId.hashCode();
        result = IWSConstants.HASHCODE_MULTIPLIER * result + status.hashCode();
        result = IWSConstants.HASHCODE_MULTIPLIER * result + username.hashCode();
        result = IWSConstants.HASHCODE_MULTIPLIER * result + firstname.hashCode();
        result = IWSConstants.HASHCODE_MULTIPLIER * result + lastname.hashCode();

        return result;
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

        if (userId != null ? !userId.equals(user.userId) : user.userId != null) {
            return false;
        }

        return !(username != null ? !username.equals(user.username) : user.username != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                "status='" + status + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
