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
package net.iaeste.iws.persistence.entities.notifications;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.persistence.entities.IWSEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * This entity is to be used for registering new notification job for the NotificationManager
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@NamedQueries(@NamedQuery(name = "notifications.findJobsByNotifiedAndDate",
        query = "select nj from NotificationJobEntity nj " +
                "where nj.notified = :notified " +
                "  and nj.modified <= :date"))
@Entity
@Table(name = "notification_jobs")
public final class NotificationJobEntity implements IWSEntity {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "notification_job_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    /**
     * Notification type
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", length = 100, nullable = false)
    private NotificationType notificationType = null;

    /**
     * Notifiable serialized object
     */
    @Column(name = "object")
    private byte[] object = null;

    /**
     * Notified marks whether NotificationManager notified consumers
     */
    @Column(name = "notified")
    private boolean notified = false;

    /**
     * Timestamp when the Entity was modified.
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
    public NotificationJobEntity() {
    }

    /**
     * Default Constructor
     *
     * @param notificationType    Notification type
     * @param object              Serialized Notifiable object
     */
    public NotificationJobEntity(final NotificationType notificationType, final byte[] object) {
        this.notificationType = notificationType;
        this.object = object;
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

    public void setNotificationType(final NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setObject(final byte[] object) {
        this.object = object;
    }

    public byte[] getObject() {
        return object;
    }

    public void setNotified(final boolean notified) {
        this.notified = notified;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setModified(final Date modified) {
        this.modified = modified;
    }

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
}
