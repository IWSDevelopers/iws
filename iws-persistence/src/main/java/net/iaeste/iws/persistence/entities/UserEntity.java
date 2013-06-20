/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.UserEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.entities;

import net.iaeste.iws.api.enums.NotificationSubject;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.persistence.exceptions.NotificationException;
import net.iaeste.iws.persistence.notification.Notifiable;
import net.iaeste.iws.persistence.notification.NotificationMessageType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @noinspection AssignmentToDateFieldFromParameter
 * @since 1.7
 */
@NamedQueries({
        @NamedQuery(
                name = "user.findAll",
                query = "select u from UserEntity u"),
        @NamedQuery(
                name = "user.findById",
                query = "select u from UserEntity u " +
                        "where u.id = :id"),
        @NamedQuery(
                name = "user.loginCredentials",
                query = "select u from UserEntity u " +
                        "where u.status = 'ACTIVE'" +
                        "  and u.userName = :username" +
                        "  and u.password = :password"),
        @NamedQuery(
                name = "user.findByUserName",
                query = "select u from UserEntity u " +
                        "where u.status <> 'DELETED'" +
                        "  and u.userName = :username"),
        @NamedQuery(
                name = "user.findByExternalId",
                query = "select u from UserEntity u " +
                        "where u.externalId = :euid"),
        @NamedQuery(
                name = "user.findByCodeAndStatus",
                query = "select u from UserEntity u " +
                        "where u.code = :code" +
                        "  and u.status = :status"),
        @NamedQuery(
                name = "user.findByAlias",
                query = "select u from UserEntity u " +
                        "where u.alias = :alias" +
                        "  and u.status <> 'DELETED'")
})
@Entity
@Table(name = "users")
public class UserEntity implements IWSEntity, Notifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    /**
     * The User Accounts are used for many perposes, and to protect the
     * internally used Id, an external is generated as a UUID value. All public
     * exposure of the Object will be with the External Id.
     */
    @Column(nullable = false, name = "external_id")
    private String externalId = null;

    /**
     * The username is the users private e-mail address.
     */
    @Column(nullable = false, name = "username")
    private String userName = null;

    /**
     * The generated e-mail alias, that all users receive by the system.
     */
    @Column(nullable = false, name = "alias")
    private String alias = null;

    /**
     * The Password stored, is an SHA 256 bit Hashvalue of the users lowercased
     * password.
     */
    @Column(nullable = false, name = "password")
    private String password = null;

    @Column(nullable = false, name = "salt")
    private String salt = null;

    /**
     * The users firstname, can only be altered by the DBA's. Although the
     * firstname is not a system value, it is stored in this Entity, rather
     * than the Person Entity, since the value should exists, also when a user
     * has been removed from the system.
     */
    @Column(nullable = false, name = "firstname")
    private String firstname = null;

    /**
     * The users lastname, can only be altered by the DBA's. Although the
     * lastname is not a system value, it is stored in this Entity, rather than
     * the Person Entity, since the value should exists, also when a user has
     * been removed from the system.
     */
    @Column(nullable = false, name = "lastname")
    private String lastname = null;

    /**
     * The current status for this User Account. Please note, that the usage of
     * the code is closely linked together with the users status. Same applies
     * to the possibility to log in, this can only be done for accounts where
     * the status is "Active".
     */
    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.NEW;

    /**
     * Privacy is a rather important topic. A users data is only exposed to be
     * exposed to those groups where user user is a member. Only Exception, is
     * the NC's mailinglist, and the corresponding Contact list, which will
     * contain the users phonenumbers.
     */
    @Column(nullable = true, name = "private_data")
    @Enumerated(EnumType.STRING)
    private Privacy privateData = Privacy.PRIVATE;

    /**
     * Personal Notifications period. By default, all notifications are
     * delivered immediately.
     */
    @Column(nullable = false, name = "notifications")
    private String notifications = "immediately";

    /**
     * This field is used to store the SHA-256 hashcode value of the users
     * temporary Authentication Code. This code is used, when a User account is
     * created and the current Status is "new", and again if the user forgot
     * his or her password, and have requested a new one.
     */
    @Column(nullable = true, name = "temporary_code")
    private String code = null;

    /**
     * Last time the User Account was modified.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = new Date();

    /**
     * Timestamp when the user was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = new Date();

    /**
     * This field, is used to store information from the Business Logic, that
     * has to be used when generating a notification. The information is not
     * persisted.
     */
    @Transient
    private String temporary = null;

    // =========================================================================
    // Entity Constructors
    // =========================================================================

    /**
     * Empty Constructor, JPA requirement.
     */
    public UserEntity() {
    }

    /**
     * Default Constructor, for creating new User Accounts.
     *
     * @param userName  User name (e-mail address)
     * @param password  Password (hash value, not clear text)
     * @param firstname The users firstname, can only be altered by DBA's
     * @param lastname  The users lastname, can only be altered by DBA's
     */
    public UserEntity(final String userName, final String password, final String firstname, final String lastname) {
        this.userName = userName;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
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

    public void setStatus(final UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setPrivateData(final Privacy privateData) {
        this.privateData = privateData;
    }

    public Privacy getPrivateData() {
        return privateData;
    }

    public void setNotifications(final String notifications) {
        this.notifications = notifications;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setModified(final Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    /**
     * This setter is for the temporary, none-persisted information, that can be
     * added to the User Object. The information is used for the Notifcation
     * Generation.
     *
     * @param temporary Temporary Information, not persisted
     */
    public void setTemporary(final String temporary) {
        this.temporary = temporary;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateNotificationMessage(final NotificationMessageType type) {
        final String message;

        switch (type) {
            case ACTIVATE_USER:
                message = "New User Account generated, with password = '" + temporary + "' and Activation Code = " + code;
                break;
            case RESET_PASSWORD:
                message = "Reset Password Code = " + code;
                break;
            case RESET_SESSION:
                message = "Reset Session Code = " + code;
                break;
            default:
                throw new NotificationException("NotificationType " + type + " is not supported in this context.");
        }

        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NotificationSubject getNotificationSubject() {
        return NotificationSubject.USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserEntity> getRecipients() {
        final List<UserEntity> entities = new ArrayList<>(1);
        entities.add(this);

        return entities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + externalId + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
