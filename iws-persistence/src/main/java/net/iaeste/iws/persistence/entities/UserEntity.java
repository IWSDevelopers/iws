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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@NamedQueries({
        @NamedQuery(
                name = "user.findAll",
                query = "select u from UserEntity u"),
        @NamedQuery(
                name = "user.loginCredentials",
                query = "select u from UserEntity u " +
                        "where u.userName = :username" +
                        "  and u.password = :password"),
        @NamedQuery(
                name = "user.findById",
                query = "select u from UserEntity u " +
                        "where u.id = :id")
})
@Entity
@Table(name = "users")
public class UserEntity implements IWSEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    /**
     * Empty Constructor, JPA requirement.
     */
    public UserEntity() {
        id = null;
        userName = null;
        password = null;
    }

    /**
     * Default Constructor, for creating new Entity.
     *
     * @param userName  User name (e-mail address)
     * @param password  Password (hash value, not clear text)
     */
    public UserEntity(final String userName, final String password) {
        id = null;
        this.userName = userName;
        this.password = password;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
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
}
