/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.api.enums.UserStatus;

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
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection AssignmentToDateFieldFromParameter
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
                        "where lower(u.userName) = lower(:username)" +
                        "  and u.password = :password"),
        @NamedQuery(
                name = "user.findByExternalId",
                query = "select u from UserEntity u " +
                        "where u.externalId = :id"),
        @NamedQuery(
                name = "user.findByCode",
                query = "select u from UserEntity u " +
                        "where u.code = :code")
})
@Entity
@Table(name = "users")
public class UserEntity implements IWSEntity {

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

    /** The username is the users private e-mail address. */
    @Column(nullable = false, name = "username")
    private String userName = null;

    /**
     * The Password stored, is an SHA 256 bit Hashvalue of the users lowercased
     * password.
     */
    @Column(nullable = false, name = "password")
    private String password = null;

    /**
     * The users firstname, can only be altered by the DBA's. Although the
     * firstname is not a system value, it is stored in this Entity, rather
     * than the Person Entity, since the value should exists, also when a user
     * has been removed from the system.
     */
    @Column(nullable = false, name = "firstname")
    private String firstname = null;

    /** The users lastname, can only be altered by the DBA's. Although the
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
     * Privacy is a rather important topic. A users data is only exposed to
     */
    @Column(nullable = false, name = "private_data")
    private Boolean privateData = true;

    /**
     * This field is used to store the SHA-256 hashcode value of the users
     * temporary Authentication Code. This code is used, when a User account is
     * created and the current Status is "new", and again if the user forgot
     * his or her password, and have requested a new one.
     */
    @Column(name = "temporary_code")
    private String code= null;

    /** Last time the User Account was modified. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = new Date();

    /** Timestamp when the user was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = new Date();

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

    public void setStatus(final UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setPrivateData(final Boolean privateData) {
        this.privateData = privateData;
    }

    public Boolean getPrivateData() {
        return privateData;
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
}
