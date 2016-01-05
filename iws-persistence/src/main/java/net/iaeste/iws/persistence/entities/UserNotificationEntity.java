/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.UserNotificationEntity
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.common.notification.NotificationType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Entity for user's notification settings
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@NamedQueries(@NamedQuery(name = "notifications.findSettingByUserAndType",
        query = "select un from UserNotificationEntity un " +
                "where un.user.id = :id " +
                "  and un.type = :type"))
@Entity
@Table(name = "user_notifications")
public class UserNotificationEntity implements IWSEntity {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "user_notification_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user = null;

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationType type = null;

    @Column(name = "frequency")
    @Enumerated(EnumType.STRING)
    private NotificationFrequency frequency = null;

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
    public UserNotificationEntity() {
    }

    /**
     * Default Constructor, for creating Message Entity without date of processing.
     *
     * @param user      The User to receive the notification
     * @param type      Type of the notification
     * @param frequency Frequency of sending notification to the user for selected subject
     */
    public UserNotificationEntity(final UserEntity user, final NotificationType type, final NotificationFrequency frequency) {
        this.type = type;
        this.user = user;
        this.frequency = frequency;
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

    public final void setUser(final UserEntity user) {
        this.user = user;
    }

    public final UserEntity getUser() {
        return user;
    }

    public final void setType(final NotificationType type) {
        this.type = type;
    }

    public final NotificationType getType() {
        return type;
    }

    public final void setFrequency(final NotificationFrequency frequency) {
        this.frequency = frequency;
    }

    public final NotificationFrequency getFrequency() {
        return frequency;
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
