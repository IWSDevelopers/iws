/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.notifications.NotificationJobEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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
public class NotificationJobEntity implements IWSEntity {

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
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Long getId() {
        return id;
    }

    public final void setNotificationType(final NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public final NotificationType getNotificationType() {
        return notificationType;
    }

    public final void setObject(final byte[] object) {
        this.object = object;
    }

    public final byte[] getObject() {
        return object;
    }

    public final void setNotified(final boolean notified) {
        this.notified = notified;
    }

    public final boolean isNotified() {
        return notified;
    }

    public final void setModified(final Date modified) {
        this.modified = modified;
    }

    public final Date getModified() {
        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setCreated(final Date created) {
        this.created = created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Date getCreated() {
        return created;
    }
}
