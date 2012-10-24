/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.enums.NotificationType;

import javax.persistence.*;
import java.util.Date;

/**
 * This entity is to be used for storing user notification messages
 *
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@NamedQueries({
        @NamedQuery(
                name = "notifications.findMessagesByTypeStatusAndDate",
                query = "select nm from NotificationMessageEntity nm " +
                        "where nm.type = :type" +
                        "  and nm.status = :status" +
                        "  and nm.processAfter < :date"),
        @NamedQuery(name = "notifications.updateStatus",
                query = "update NotificationMessageEntity nm set " +
                        "nm.status = :status " +
                        "where nm.id = :id")
})
@Entity
@Table(name = "notification_messages")
public class NotificationMessageEntity implements IWSEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user = null;

    /**
     * Notification channel (e-mail, instant message, ...)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "subject")
    private NotificationType type;

    /**
     * Text of the message
     */
    @Column(name = "message")
    private String message;

    /**
     * Status of the message (new, processing, ...)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NotificationMessageStatus status;

    /**
     * Date after which the message should be processed (after this date, the message should be sent)md
     */
    @Column(name = "process_after")
    private Date processAfter;

    /**
     * Empty Constructor, JPA requirement.
     */
    public NotificationMessageEntity() {
    }

    /**
     * Default Constructor, for creating Message Entity without date of processing.
     *
     * @param user    The User to receive the notification
     * @param type    Type of notification channel (i.e. e-mail, instant message, ...)
     * @param message Text of the message to be sent
     * @param status  Current status of the notification message
     */
    public NotificationMessageEntity(final UserEntity user, final NotificationType type, final String message, final NotificationMessageStatus status) {
        this.user = user;
        this.type = type;
        this.message = message;
        this.status = status;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
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

    public void setType(final NotificationType type) {
        this.type = type;
    }

    public NotificationType getType() {
        return type;
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

}