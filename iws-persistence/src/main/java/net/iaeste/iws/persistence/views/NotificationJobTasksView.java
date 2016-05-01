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
public final class NotificationJobTasksView extends AbstractView {

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
        return (id != null) ? id.equals(view.id) : (view.id == null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : 0;
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
}
