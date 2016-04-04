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
import net.iaeste.iws.persistence.Externable;

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
 * @since   IWS 1.0
 */
@NamedQueries({
        @NamedQuery(name = "session.findByToken",
                query = "select s from SessionEntity s " +
                        "where s.deprecated = '0'" +
                        "  and s.sessionKey = :key"),
        @NamedQuery(name = "session.findByUser",
                query = "select s from SessionEntity s " +
                        "where s.deprecated = '0'" +
                        "  and s.user.id = :id"),
        @NamedQuery(name = "session.findActive",
                query = "select s from SessionEntity s " +
                        "where s.deprecated = '0'"),
        @NamedQuery(name = "session.deprecate",
                query = "update SessionEntity s set " +
                        "   s.deprecated = :deprecated, " +
                        "   s.sessionData = null, " +
                        "   s.modified = current_timestamp " +
                        "where s.deprecated = '0'" +
                        "  and s.id = :id"),
        @NamedQuery(name = "session.deprecateAllActiveSessions",
                query = "update SessionEntity s set " +
                        "   s.deprecated = :deprecated, " +
                        "   s.sessionData = null, " +
                        "   s.modified = current_timestamp " +
                        "where s.deprecated = '0'"),
        @NamedQuery(name = "session.deleteUserSessions",
                query = "delete from SessionEntity s " +
                        "where s.user.id = :uid" +
                        "  and id not in (" +
                        "    select r.session.id from RequestEntity r" +
                        "    where r.session.user.id = :uid)")
})
@Entity
@Table(name = "sessions")
public final class SessionEntity extends AbstractUpdateable<SessionEntity> implements Externable<SessionEntity> {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @Column(name = "session_key", length = 128, unique = true, nullable = false, updatable = false)
    private String sessionKey = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private UserEntity user = null;

    @Column(name = "deprecated", length = 32, nullable = false)
    private String deprecated = "0";

    @Column(name = "session_data")
    private byte[] sessionData = null;

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

    // =========================================================================
    // Entity Constructors
    // =========================================================================

    /**
     * Empty Constructor, JPA requirement.
     */
    public SessionEntity() {
    }

    /**
     * Default Constructor, for creating a new Session.
     *
     * @param user       The User, which this Session is for
     * @param sessionKey The SessionKey to assign to this Session
     */
    public SessionEntity(final UserEntity user, final String sessionKey) {
        this.sessionKey = sessionKey;
        this.user = user;
    }

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
        this.sessionKey = externalId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExternalId() {
        return sessionKey;
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

    public void setDeprecated(final String deprecated) {
        this.deprecated = deprecated;
    }

    public String getDeprecated() {
        return deprecated;
    }

    public void setSessionData(final byte[] sessionData) {
        this.sessionData = sessionData;
    }

    public byte[] getSessionData() {
        return sessionData;
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

    // =========================================================================
    // Entity Standard Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean diff(final SessionEntity obj) {
        // Until properly implemented, better return true to avoid that we're
        // missing updates!
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final SessionEntity obj) {
        if (canMerge(obj)) {
            deprecated = which(deprecated, obj.deprecated);
            sessionData = which(sessionData, obj.sessionData);
        }
    }
}
