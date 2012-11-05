/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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

import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.api.enums.NotificationSubject;

import javax.persistence.*;

/**
 * Entity for user's notification settings
 *
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@NamedQueries({
        @NamedQuery(name = "notifications.findSettingByUserAndSubject",
                query = "select un from UserNotificationEntity un " +
                        "where un.user.id = :id " +
                        "  and un.subject = :subject")
})
@Entity
@Table(name = "user_notifications")
public class UserNotificationEntity implements IWSEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user = null;

    @Column(name = "subject")
    private NotificationSubject subject = null;

    @Column(name = "frequency")
    private NotificationFrequency frequency = null;

    /**
     * Empty Constructor, JPA requirement.
     */
    public UserNotificationEntity() {
    }

    /**
     * Default Constructor, for creating Message Entity without date of processing.
     *
     * @param user      The User to receive the notification
     * @param subject   Subject of the notification
     * @param frequency Frequency of sending notification to the user for selected subject
     */
    public UserNotificationEntity(final UserEntity user, final NotificationSubject subject, final NotificationFrequency frequency) {
        this.subject = subject;
        this.user = user;
        this.frequency = frequency;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setSubject(final NotificationSubject subject) {
        this.subject = subject;
    }

    public NotificationSubject getSubject() {
        return subject;
    }

    public void setFrequency(final NotificationFrequency frequency) {
        this.frequency = frequency;
    }

    public NotificationFrequency getFrequency() {
        return frequency;
    }

}