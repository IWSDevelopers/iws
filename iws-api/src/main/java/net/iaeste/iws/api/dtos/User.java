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
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.enums.UserType;
import net.iaeste.iws.api.util.Verifications;
import net.iaeste.iws.api.util.StandardMethods;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>User Object, contain the system specific information related to a user,
 * and the personal details in the Person Object. Please note, that a user is
 * considered a vital Object in the system and cannot be deleted. The personal
 * details can be deleted.</p>
 *
 * <p>Since the username is the e-mail of a user which is subject to changes, it
 * is, of course, possible to change it - just as it is possible to change the
 * password. However, the name of a user cannot be altered. The system is
 * designed as a multi-user system, where it is possible to assign rights to
 * others. However, giving an account to another user by altering the name of
 * the user, can be very confusing, since older records are now suddenly owned
 * by a different person, who may not have had anything to do with the original
 * user.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = { "userId", "username", "alias", "firstname", "lastname", "person", "status", "type", "privacy", "notifications" })
public final class User extends Verifications {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true)  @StandardMethods(StandardMethods.For.ALL)  private String userId = null;
    @XmlElement(required = true, nillable = true)  @StandardMethods(StandardMethods.For.ALL)  private String username = null;
    @XmlElement(required = true, nillable = true)  @StandardMethods(StandardMethods.For.ALL)  private String alias = null;
    @XmlElement(required = true, nillable = true)  @StandardMethods(StandardMethods.For.ALL)  private String firstname = null;
    @XmlElement(required = true, nillable = true)  @StandardMethods(StandardMethods.For.ALL)  private String lastname = null;
    @XmlElement(required = true, nillable = true)  @StandardMethods(StandardMethods.For.NONE) private Person person = null;
    @XmlElement(required = true, nillable = true)  @StandardMethods(StandardMethods.For.ALL)  private UserStatus status = null;
    @XmlElement(required = true, nillable = false) @StandardMethods(StandardMethods.For.ALL)  private UserType type = UserType.VOLUNTEER;
    @XmlElement(required = true, nillable = false) @StandardMethods(StandardMethods.For.NONE) private Privacy privacy = Privacy.PRIVATE;
    @XmlElement(required = true, nillable = false) @StandardMethods(StandardMethods.For.NONE) private NotificationFrequency notifications = NotificationFrequency.IMMEDIATELY;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * <p>Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.</p>
     */
    public User() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * <p>Constructor for an existing user, where the personal details should be
     * updated.</p>
     *
     * @param userId The internal Id of the user
     * @param person The personal details
     */
    public User(final String userId, final Person person) {
        this.userId = userId;
        this.person = person;
    }

    /**
     * <p>Constructor for an existing user, where the personal details should be
     * updated. Please note, that there is a state machine checking the current
     * status against the new.</p>
     *
     * <p>Below is the State machine presented. A User always starts with Status
     * "New", and can from there either get the status "Active" or "Blocked".
     * If the user is removed, the status is then set to "Deleted" - meaning
     * that all private data is removed from the system, and the user account
     * is deactivated. However, it is important to note that the User Object in
     * the system will remain there - the reason for this, is that the User may
     * also have worked with Group data, and thus the information about the
     * person must be preserved in the history.</p>
     * <pre>
     *              NEW
     *             /   \
     *            /     \
     *       ACTIVE &lt;-&gt; SUSPENDED
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
     * <p>Copy Constructor.</p>
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
            type = user.type;
            privacy = user.privacy;
            notifications = user.notifications;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the User Id. Users cannot be created with this Object, rather
     * they are created with a createUser request. Hence, this field is
     * mandatory, and must be set to a valid UserId. If the value is not then
     * the method will throw an {@code IllegalArgumentException}.</p>
     *
     * @param userId User Id
     * @throws IllegalArgumentException if the Id is invalid
     * @see Verifications#UUID_FORMAT
     */
    public void setUserId(final String userId) throws IllegalArgumentException {
        ensureValidId("userId", userId);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    /**
     * <p>Sets the Username for the user, i.e. the users e-mail address which is
     * used as identification in the IWS. The value is set from the IWS, but
     * cannot be updated via this Object. Instead it has to be provided with the
     * controlUserAccount request.</p>
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
     * <p>The Alias, is the e-mail address, which is generated for the User by
     * the IWS, as part of the IAESTE Corporate Identity. The alias is only
     * generated for IAESTE Members, not for Students. The alias cannot be
     * updated by normal means.</p>
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
     * <p>Reads the users firstname from the IWS, the value is set as part of
     * creating an account, and the value is only read out from the IWS with
     * this Object.</p>
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
     * <p>Reads the users lastname from the IWS, the value is set as part of
     * creating an account, and the value is only read out from the IWS with
     * this Object.</p>
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
     * <p>Sets the User's Personal details. These details are important for
     * certain things, but is generally falling under the privacy category,
     * meaning that only of the user is lowering the privacy settings, then it
     * will be displayed to others.</p>
     *
     * <p>The value is optional, and only requires that the Object is valid if
     * it is set. If not, then the method will throw an
     * {@code IllegalArgumentException}.</p>
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
     * <p>Reads the User's status from the IWS, the status cannot be altered via
     * this Object.</p>
     *
     * @param status User's Status
     */
    public void setStatus(final UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setType(final UserType type) {
        ensureNotNull("type", type);
        this.type = type;
    }

    public UserType getType() {
        return type;
    }

    /**
     * <p>Sets the User's privacy setting. By default, the privacy is set to
     * max, meaning that no information whatsoever will be shared with
     * others.</p>
     *
     * <p>The value is mandatory, and setting it to null will cause the method
     * to throw an {@code IllegalArgumentException}.</p>
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
     * <p>Sets the Notification frequency for a User. By default, the frequency
     * is set to immediately, but it may also be possible to set it to a
     * different value. Note, this value is mandatory, since it is a vital part
     * of the inner workings of the IWS.</p>
     *
     * <p>The value is mandatory, and setting it to null will cause the method
     * to throw an {@code IllegalArgumentException}.</p>
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
}
