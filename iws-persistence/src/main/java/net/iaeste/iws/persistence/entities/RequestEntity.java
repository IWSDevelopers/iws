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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@Entity
@Table(name = "requests")
public final class RequestEntity implements IWSEntity {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @ManyToOne(targetEntity = SessionEntity.class)
    @JoinColumn(name = "session_id", referencedColumnName = "id", nullable = false, updatable = false)
    private SessionEntity session = null;

    @Column(name = "request", length = 100, updatable = false)
    private String request = null;

    @Column(name = "errorcode", updatable = false)
    private Long errorcode = null;

    @Column(name = "errormessage", length = 512, updatable = false)
    private String errormessage = null;

    @Column(name = "request_object")
    private byte[] requestObject = null;

    /**
     * Timestamp when the Entity was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable = false)
    private Date created = new Date();

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

    public void setSession(final SessionEntity session) {
        this.session = session;
    }

    public SessionEntity getSession() {
        return session;
    }

    public void setRequest(final String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setErrorcode(final Long errorcode) {
        this.errorcode = errorcode;
    }

    public Long getErrorcode() {
        return errorcode;
    }

    public void setErrormessage(final String errormessage) {
        this.errormessage = errormessage;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setRequestObject(final byte[] requestObject) {
        this.requestObject = requestObject;
    }

    public byte[] getRequestObject() {
        return requestObject;
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
}
