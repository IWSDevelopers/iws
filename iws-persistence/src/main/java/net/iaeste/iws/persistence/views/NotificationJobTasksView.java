/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.NotificationJobTasksView
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.views;

import net.iaeste.iws.common.notification.NotificationType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The View lists the NotificationJobTask entity, the Notifiable object
 * and Notificaiton type needed to process a notification
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Entity
@NamedQueries(@NamedQuery(name = "view.NotificationJobTasksByConsumerId",
        query = "select v from NotificationJobTasksView v " +
                "where v.consumerId = :consumerId " +
                "      and v.attempts < :attempts " +
                "order by v.id"))
@Table(name = "notification_job_task_details")
public final class NotificationJobTasksView extends AbstractView<NotificationJobTasksView> {

    @Id
    @Column(name = "id")
    private Long id = null;

    @Column(name = "attempts")
    private Integer attempts = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private NotificationType notificationType = null;

    @Column(name = "object")
    private byte[] object = null;

    @Column(name = "consumer_id")
    private Long consumerId = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setAttempts(final Integer attempts) {
        this.attempts = attempts;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setNotificationType(final NotificationType notificaitonType) {
        this.notificationType = notificaitonType;
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

    public void setConsumerId(final Long consumerId) {
        this.consumerId = consumerId;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        // As the View is reading unique records from the database, it is
        // enough to simply look at their unique Id
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotificationJobTasksView)) {
            return false;
        }

        final NotificationJobTasksView view = (NotificationJobTasksView) obj;
        return !(id != null ? !id.equals(view.id) : view.id != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "NotificationJobTasksView{" +
                "id=" + id +
                ", attempts=" + attempts +
                ", notificationType=" + notificationType +
                ", consumerId=" + consumerId +
                '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final NotificationJobTasksView o) {
        final int result = id.compareTo(o.id);

        return sortAscending ? result : -result;
    }
}
