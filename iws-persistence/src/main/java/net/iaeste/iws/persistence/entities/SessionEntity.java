/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.SessionEntity
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * The Session is the system authentication mechanism, a user can only have one
 * active session at the time.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection AssignmentToDateFieldFromParameter, AssignmentToCollectionOrArrayFieldFromParameter
 */
@NamedQueries({
        @NamedQuery(name = "session.findByToken",
                query = "select s from SessionEntity s " +
                        "where s.active = true " +
                        "  and s.sessionKey = :key"),
        @NamedQuery(name = "session.findByUser",
                query = "select s from SessionEntity s " +
                        "where s.active = true " +
                        "  and s.user.id = :id"),
        @NamedQuery(name = "session.deprecate",
                query = "update SessionEntity s set " +
                        "   s.active = :status, " +
                        "   s.sessionData = null, " +
                        "   s.modified = current_timestamp " +
                        "where s.active = true " +
                        "  and s.user.id = :id")
})
@Entity
@Table(name = "sessions")
public class SessionEntity implements IWSEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @Column(nullable = false, name = "session_key")
    private String sessionKey = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user = null;

    @Column(nullable = false, name = "active")
    private Boolean active = true;

    @Column(name = "session_data")
    private byte[] sessionData = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = new Date();

    public SessionEntity() {
    }

    public SessionEntity(final UserEntity user, final String sessionKey) {
        this.sessionKey = sessionKey;
        this.user = user;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setSessionKey(final String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setSessionData(final byte[] sessionData) {
        this.sessionData = sessionData;
    }

    public byte[] getSessionData() {
        return sessionData;
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
