/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.NotificationMessageEntity
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

import net.iaeste.iws.api.enums.NotificationMessageStatus;
import net.iaeste.iws.api.enums.NotificationDeliveryMode;

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
 * This entity is to be used for storing user notification messages
 *
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 * @noinspection AssignmentToDateFieldFromParameter
 */
@NamedQueries(@NamedQuery(
        name = "notifications.findMessagesByTypeStatusAndDate",
        query = "select nm from NotificationMessageEntity nm " +
                "where nm.notificationDeliveryMode = :deliveryMode" +
                "  and nm.status = :status" +
                "  and nm.processAfter < :date"))
@Entity
@Table(name = "notification_messages")
public class NotificationMessageEntity implements IWSEntity {

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "notification_message_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user = null;

    /**
     * Notification channel (e-mail, instant message, ...)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", length = 100, nullable = false)
    private NotificationDeliveryMode notificationDeliveryMode = null;

    /**
     * Message title
     */
    @Column(name = "title", length = 100)
    private String messageTitle = null;

    /**
     * Text of the message
     */
    @Column(name = "message", length = 100)
    private String message = null;

    /**
     * Status of the message (new, processing, ...)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 100)
    private NotificationMessageStatus status = null;

    /**
     * Date after which the message should be processed (after this date, the message should be sent)md
     */
    @Column(name = "process_after", nullable = false)
    private Date processAfter = null;

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
    public NotificationMessageEntity() {
    }

    /**
     * Default Constructor, for creating Message Entity without date of processing.
     *
     * @param user    The User to receive the notification
     * @param notificationDeliveryMode    Type of notification channel (i.e. e-mail, instant message, ...)
     * @param message Text of the message to be sent
     * @param status  Current status of the notification message
     */
    public NotificationMessageEntity(final UserEntity user, final NotificationDeliveryMode notificationDeliveryMode, final String message, final NotificationMessageStatus status) {
        this.user = user;
        this.notificationDeliveryMode = notificationDeliveryMode;
        this.message = message;
        this.status = status;
    }

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

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

    public void setMessageTitle(final String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setNotificationDeliveryMode(final NotificationDeliveryMode notificationDeliveryMode) {
        this.notificationDeliveryMode = notificationDeliveryMode;
    }

    public NotificationDeliveryMode getNotificationDeliveryMode() {
        return notificationDeliveryMode;
    }

    public void setStatus(final NotificationMessageStatus status) {
        this.status = status;
    }

    public NotificationMessageStatus getStatus() {
        return  status;
    }

    public void setProcessAfter(final Date processAfter) {
        this.processAfter = processAfter;
    }

    public Date getProcessAfter() {
        return processAfter;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }
}
