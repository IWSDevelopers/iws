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
package net.iaeste.iws.persistence.entities;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.enums.UserType;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.persistence.Externable;
import net.iaeste.iws.persistence.monitoring.Monitored;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;
import java.util.EnumMap;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@NamedQueries({
        @NamedQuery(name = "user.findById",
                query = "select u from UserEntity u " +
                        "where u.id = :id"),
        @NamedQuery(name = "user.findByUserName",
                query = "select u from UserEntity u " +
                        "where u.username = :username"),
        @NamedQuery(name = "user.findActiveByUserName",
                query = "select u2g.user from UserGroupEntity u2g " +
                        "where u2g.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_MEMBER +
                        "  and u2g.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and u2g.user.status = " + EntityConstants.USER_STATUS_ACTIVE +
                        "  and u2g.user.username = :username"),
        @NamedQuery(name = "user.findExistingByUsername",
                query = "select u from UserEntity u " +
                        "where u.status <> " + EntityConstants.USER_STATUS_DELETED +
                        "  and lower(u.username) = lower(:username)"),
        @NamedQuery(name = "user.findActiveByExternalId",
                query = "select u from UserEntity u " +
                        "where u.status = " + EntityConstants.USER_STATUS_ACTIVE +
                        "  and u.externalId = :euid"),
        @NamedQuery(name = "user.findByExternalId",
                query = "select u from UserEntity u " +
                        "where u.status <> " + EntityConstants.USER_STATUS_DELETED +
                        "  and u.externalId = :euid"),
        @NamedQuery(name = "user.findActiveByCode",
                query = "select ug.user from UserGroupEntity ug " +
                        "where ug.user.status = " + EntityConstants.USER_STATUS_ACTIVE +
                        "  and ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_MEMBER +
                        "  and ug.user.code = :code"),
        @NamedQuery(name = "user.findNewByCode",
                query = "select ug.user from UserGroupEntity ug " +
                        "where ug.user.status = " + EntityConstants.USER_STATUS_NEW +
                        "  and ug.group.status = " + EntityConstants.GROUP_STATUS_ACTIVE +
                        "  and ug.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_MEMBER +
                        "  and ug.user.code = :code"),
        @NamedQuery(name = "user.findAccountsWithStateAfterModification",
                query = "select u from UserEntity u " +
                        "where u.status = :status" +
                        "  and u.modified < :days"),
        @NamedQuery(name = "user.findNumberOfSimilarAliases",
                query = "select count(u.id) from UserEntity u " +
                        "where lower(alias) like :startOfAlias"),
        @NamedQuery(name = "user.findUsersByIds",
                query = "select u from UserEntity u " +
                        "where id in :ids")
})
@Entity
@Table(name = "users")
@Monitored(name = "User", level = MonitoringLevel.DETAILED)
public final class UserEntity extends AbstractUpdateable<UserEntity> implements Externable<UserEntity>, Notifiable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    /**
     * The content of this Entity is exposed externally, however to avoid that
     * someone tries to spoof the system by second guessing our Sequence values,
     * An External Id is used, the External Id is a unique UUID value, which in
     * all external references is referred to as the "Id". Although this can be
     * classified as StO (Security through Obscurity), there is no need to
     * expose more information than necessary.
     */
    @Column(name = "external_id", length = 36, unique = true, nullable = false, updatable = false)
    private String externalId = null;

    /**
     * The username is the users private e-mail address.
     */
    @Monitored(name="User Username", level = MonitoringLevel.DETAILED)
    @Column(name = "username", length = 100, nullable = false)
    private String username = null;

    /**
     * The generated e-mail alias, that all users receive by the system.
     */
    @Monitored(name="User Alias", level = MonitoringLevel.DETAILED)
    @Column(name = "alias", length = 125, nullable = false)
    private String alias = null;

    /**
     * The Password stored, is an SHA 256 bit Hash value of the users lower
     * cased password.
     */
    @Column(name = "password", length = 128, nullable = false)
    private String password = null;

    /**
     * The salt used for the cryptographic hashing of the password.
     */
    @Column(name = "salt", length = 36, nullable = false)
    private String salt = null;

    /**
     * The users firstname, can only be altered by the DBAs. Although the
     * firstname is not a system value, it is stored in this Entity, rather
     * than the Person Entity, since the value should exists, also when a user
     * has been removed from the system.
     */
    @Monitored(name="User Firstname", level = MonitoringLevel.DETAILED)
    @Column(name = "firstname", length = 50, nullable = false)
    private String firstname = null;

    /**
     * The users lastname, can only be altered by the DBAs. Although the
     * lastname is not a system value, it is stored in this Entity, rather than
     * the Person Entity, since the value should exists, also when a user has
     * been removed from the system.
     */
    @Monitored(name="User Lastname", level = MonitoringLevel.DETAILED)
    @Column(name = "lastname", length = 50, nullable = false)
    private String lastname = null;

    /**
     * Private information for the current User. This information may only be
     * altered by the user, and if the account is removed (status deleted), then
     * the information must be removed completely.
     */
    @Monitored(name="User Person", level = MonitoringLevel.DETAILED)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private PersonEntity person = null;

    /**
     * The EULA, End User License Agreement, Version, is the currently accepted
     * License version for this User. If it is not correct, then the User cannot
     * log in until the correct one has been approved, which is happening as
     * part of the Authentication Process (login).
     */
    @Monitored(name = "EULA Version", level = MonitoringLevel.DETAILED)
    @Column(name = "eula_version", length = 50, nullable = false)
    private String eulaVersion = "";

    /**
     * The current status for this User Account. Please note, that the usage of
     * the code is closely linked together with the users status. Same applies
     * to the possibility to log in, this can only be done for accounts where
     * the status is "Active".
     */
    @Monitored(name="User Status", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 25, nullable = false)
    private UserStatus status = UserStatus.NEW;

    /**
     * The Type of User Account. A User can have different types reflecting the
     * association with the organization. The information has no internal
     * business logic purpose, and thus only serves the purpose as being a
     * visualisation of the account.
     */
    @Monitored(name="User Type", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name="user_type", length = 10, nullable = false)
    private UserType type = UserType.VOLUNTEER;

    /**
     * Privacy is a rather important topic. A users data is only exposed to be
     * exposed to those groups where user user is a member. Only Exception, is
     * the NC's mailinglist, and the corresponding Contact list, which will
     * contain the users phone numbers.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "private_data", length = 10, nullable = false)
    private Privacy privateData = Privacy.PRIVATE;

    /**
     * Personal Notifications period. By default, all notifications are
     * delivered immediately.
     */
    @Enumerated(EnumType.STRING)
    @Monitored(name="User Notifications", level = MonitoringLevel.DETAILED)
    @Column(name = "notifications", length = 25, nullable = false)
    private NotificationFrequency notifications = NotificationFrequency.IMMEDIATELY;

    /**
     * This field is used to store the SHA-256 hashcode value of the users
     * temporary Authentication Code. This code is used, when a User account is
     * created and the current Status is "new", and again if the user forgot
     * his or her password, and have requested a new one.
     */
    @Column(name = "temporary_code", length = 128)
    private String code = null;

    /**
     * This is for temporary data, that a user may provide. It is used when a
     * user wishes to change the username (e-mail address) - where the system
     * will send a verification e-mail to the new username, with a code to
     * update the username.
     */
    @Column(name = "temporary_data", length = 128)
    private String data = null;

    /**
     * Last time the Entity was modified.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", nullable = false)
    private Date modified = new Date();

    /**
     * Timestamp when the Entity was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable = false)
    private Date created = new Date();

    /**
     * This field, is used to store information from the Business Logic, that
     * has to be used when generating a notification. The information is not
     * persisted.
     */
    @Transient
    private String temporary = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExternalId() {
        return externalId;
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

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setSalt(final String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
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

    public void setPerson(final PersonEntity person) {
        this.person = person;
    }

    public PersonEntity getPerson() {
        return person;
    }

    public void setEulaVersion(final String eulaVersion) {
        this.eulaVersion = eulaVersion;
    }

    public String getEulaVersion() {
        return eulaVersion;
    }

    public void setStatus(final UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setType(final UserType type) {
        this.type = type;
    }

    public UserType getType() {
        return type;
    }

    public void setPrivateData(final Privacy privateData) {
        this.privateData = privateData;
    }

    public Privacy getPrivateData() {
        return privateData;
    }

    public void setNotifications(final NotificationFrequency notifications) {
        this.notifications = notifications;
    }

    public NotificationFrequency getNotifications() {
        return notifications;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModified(final Date modified) {
        this.modified = modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getModified() {
        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCreated(final Date created) {
        this.created = created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getCreated() {
        return created;
    }

    /**
     * This setter is for the temporary, none-persisted information, that can be
     * added to the User Object. The information is used for the Notification
     * Generation.<br />
     *   Note, there is no getter for this value, instead the information is
     * retrieved via the Map generated for Notifications.
     *
     * @param temporary Temporary Information, not persisted
     */
    public void setTemporary(final String temporary) {
        this.temporary = temporary;
    }

    // =========================================================================
    // Other Methods required for this Entity
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean diff(final UserEntity obj) {
        // Until properly implemented, better return true to avoid that we're
        // missing updates!
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final UserEntity obj) {
        if (canMerge(obj)) {
            privateData = which(privateData, obj.privateData);
            notifications = which(notifications, obj.notifications);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnumMap<NotificationField, String> prepareNotifiableFields(final NotificationType type) {
        final EnumMap<NotificationField, String> fields = new EnumMap<>(NotificationField.class);

        // By default, all notifications need the name of the person to receive
        // it. Further, all User based notifications (Activate User, Reset
        // Password, Reset Session and Update Username) all require a unique
        // Cryptographic Hash value, or code.
        fields.put(NotificationField.FIRSTNAME, firstname);
        fields.put(NotificationField.LASTNAME, lastname);
        fields.put(NotificationField.CODE, code);

        // For the remainder, we're going to use a Switch. The Switch serves two
        // purposes. The first purpose, is to fill in the remaining fields, and
        // the second purpose is to ensure that only those Notification Types,
        // which is allowed for this Entity is being delivered.
        switch (type) {
            case ACTIVATE_NEW_USER:
                // Activating a user requires that the password is returned
                fields.put(NotificationField.CLEARTEXT_PASSWORD, temporary);
                fields.put(NotificationField.EMAIL, username);
                break;
            case RESET_PASSWORD:
            case RESET_SESSION:
            case NEW_USER:
            case USER_ACTIVATED:
                // These four types all require that we send the information
                // too the current e-mail address
                fields.put(NotificationField.EMAIL, username);
                break;
            case UPDATE_USERNAME:
                // When updating the username, we're having a new address that
                // we need to send it to
                fields.put(NotificationField.EMAIL, username);
                fields.put(NotificationField.NEW_USERNAME, data);
                break;
            case USERNAME_UPDATED:
                fields.put(NotificationField.EMAIL, temporary);
                fields.put(NotificationField.NEW_USERNAME, username);
                break;
            case PROCESS_EMAIL_ALIAS:
                fields.put(NotificationField.EMAIL, data);
                break;
            default:
        }

        return fields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "UserEntity{" +
                " firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
