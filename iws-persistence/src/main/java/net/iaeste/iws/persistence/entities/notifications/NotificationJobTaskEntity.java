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
import net.iaeste.iws.persistence.entities.IWSEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
 * This entity is to be used for registering new task for notification job
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@NamedQueries({
        @NamedQuery(name = "notifications.findJobTasksByConsumerIdAndProcessed",
                query = "select njt from NotificationJobTaskEntity njt " +
                        "where njt.consumer.id = :consumerId " +
                        "      and njt.processed = :processed"),
        @NamedQuery(name = "notifications.updateJobTaskProcessedAndAttempts",
                query = "update NotificationJobTaskEntity njt set" +
                        "   njt.processed = :processed, " +
                        "   njt.attempts = :attempts, " +
                        "   njt.modified = current_timestamp " +
                        "where njt.id = :id ")
})
@Entity
@Table(name = "notification_job_tasks")
public final class NotificationJobTaskEntity implements IWSEntity {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "notification_job_task_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    /**
     * Job Id
     */
    @ManyToOne(targetEntity = NotificationJobEntity.class)
    @JoinColumn(name = "job_id", nullable = false, updatable = false)
    private NotificationJobEntity job = null;

    /**
     * Consumer Id
     */
    @ManyToOne(targetEntity = NotificationConsumerEntity.class)
    @JoinColumn(name = "consumer_id", nullable = false, updatable = false)
    private NotificationConsumerEntity consumer = null;

    /**
     * Processed - flag if the task is processed
     */
    @Column(name = "processed")
    private boolean processed = false;

    /**
     * Attempts - number of processing attempts
     */
    @Column(name = "attempts")
    private int attempts = 0;

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
    public NotificationJobTaskEntity() {
    }

    /**
     * Default Constructor
     *
     * @param job       Notification Job
     * @param consumer  Notification Consumer
     */
    public NotificationJobTaskEntity(final NotificationJobEntity job, final NotificationConsumerEntity consumer) {
        this.job = job;
        this.consumer = consumer;
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

    public void setJob(final NotificationJobEntity job) {
        this.job = job;
    }

    public NotificationJobEntity getJob() {
        return job;
    }

    public void setConsumer(final NotificationConsumerEntity consumer) {
        this.consumer = consumer;
    }

    public NotificationConsumerEntity getConsumer() {
        return consumer;
    }

    public void setProcessed(final boolean processed) {
        this.processed = processed;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setAttempts(final int attempts) {
        this.attempts = attempts;
    }

    public int getAttempts() {
        return attempts;
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
