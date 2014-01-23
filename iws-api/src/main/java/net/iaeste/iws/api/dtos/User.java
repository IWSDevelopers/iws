/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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
import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.util.AbstractDto;

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
 */
public final class User extends AbstractDto {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String userId = null;
    // The Username is the users private e-mail address
    private String username = null;
    // The Alias is an e-mail address provided by the system
    private String alias = null;
    private String firstname = null;
    private String lastname = null;
    private Person person = null;
    private UserStatus status = null;
    private Privacy privacy = Privacy.PRIVATE;
    private NotificationFrequency notifications = NotificationFrequency.IMMEDIATELY;

    // =========================================================================
    // Object Constructors
    // =========================================================================

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
     *       ACTIVE <-> SUSPENDED
     *           \      /
     *            \    /
     *            DELETED
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
            person = new Person(user.person);
            status = user.status;
            privacy = user.privacy;
            notifications = user.notifications;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the User Id. Users cannot be created with this Object, rather they
     * are created with a createUser request. Hence, this field is mandatory,
     * and must be set to a valid UserId. If the value is not then the method
     * will throw an {@code IllegalArgumentException}.
     *
     * @param userId User Id
     * @throws IllegalArgumentException if the Id is invalid
     * @see AbstractDto#UUID_FORMAT
     */
    public void setUserId(final String userId) throws IllegalArgumentException {
        ensureValidId("userId", userId);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    /**
     * Sets the Username for the user, i.e. the users e-mail address which is
     * used as identification in the IWS. The value is set from the IWS, but
     * cannot be updated via this Object. Instead it has to be provided with the
     * controlUserAccount request.
     *
     * @param username User's e-mail address used as Identification
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    /**
     * The Alias, is the e-mail address, which is generated for the User by the
     * IWS, as part of the IAESTE Corporate Identity. The alias is only
     * generated for IAESTE Members, not for Students. The alias cannot be
     * updated by normal means.
     *
     * @param alias IWS Generated IAESTE Alias (Corporate Identity e-mail)
     */
    public void setAlias(final String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    /**
     * Reads the users firstname from the IWS, the value is set as part of
     * creating an account, and the value is only read out from the IWS with
     * this Object.
     *
     * @param firstname User's firstname
     */
    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    /**
     * Reads the users lastname from the IWS, the value is set as part of
     * creating an account, and the value is only read out from the IWS with
     * this Object.
     *
     * @param lastname User's lastname
     */
    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the User's Personal details. These details are important for certain
     * things, but is generally falling under the privacy category, meaning that
     * only of the user is lowering the privacy settings, then it will be
     * displayed to others.<br />
     *   The value is optional, and only requires that the Object is valid if it
     * is set. If not, then the method will throw an
     * {@code IllegalArgumentException}.
     *
     * @param person User's personal details
     * @throws IllegalArgumentException if invalid
     */
    public void setPerson(final Person person) throws IllegalArgumentException {
        ensureVerifiable("person", person);
        this.person = new Person(person);
    }

    public Person getPerson() {
        return new Person(person);
    }

    /**
     * Reads the User's status from the IWS, the status cannot be altered via
     * this Object.
     *
     * @param status User's Status
     */
    public void setStatus(final UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
    }

    /**
     * Sets the User's privacy setting. By default, the privacy is set to max,
     * meaning that no information whatsoever will be shared with others.<br />
     *   The value is mandatory, and setting it to null will cause the method to
     * throw an {@code IllegalArgumentException}.
     *
     * @param privacy User's privacy setting
     * @throws IllegalArgumentException if set to null
     */
    public void setPrivacy(final Privacy privacy) throws IllegalArgumentException {
        ensureNotNull("privacy", privacy);
        this.privacy = privacy;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    /**
     * Sets the Notification frequency for a User. By default, the frequency is
     * set to immediately, but it may also be possible to set it to a different
     * value. Note, this value is mandatory, since it is a vital part of the
     * inner workings of the IWS.<br />
     *   The value is mandatory, and setting it to null will cause the method to
     * throw an {@code IllegalArgumentException}.
     *
     * @param notifications User Notification Frequency
     * @throws IllegalArgumentException if set to null
     */
    public void setNotifications(final NotificationFrequency notifications) throws IllegalArgumentException {
        ensureNotNull("notifications", notifications);
        this.notifications = notifications;
    }

    public NotificationFrequency getNotifications() {
        return notifications;
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

        isVerifiable(validation, "person", person);

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
        if ((userId != null) ? !userId.equals(user.userId) : (user.userId != null)) {
            return false;
        }
        if ((username != null) ? !username.equals(user.username) : (user.username != null)) {
            return false;
        }
        if ((alias != null) ? !alias.equals(user.alias) : (user.alias != null)) {
            return false;
        }
        if ((firstname != null) ? !firstname.equals(user.firstname) : (user.firstname != null)) {
            return false;
        }
        if ((lastname != null) ? !lastname.equals(user.lastname) : (user.lastname != null)) {
            return false;
        }
        if (status != user.status) {
            return false;
        }

        return privacy == user.privacy;
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
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((userId != null) ? userId.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((username != null) ? username.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((alias != null) ? alias.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((firstname != null) ? firstname.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((lastname != null) ? lastname.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((status != null) ? status.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((privacy != null) ? privacy.hashCode() : 0);

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
                '}';
    }
}
