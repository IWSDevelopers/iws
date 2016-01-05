/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.notifications.NotificationConsumerEntity
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
import net.iaeste.iws.persistence.entities.GroupEntity;
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
 * This entity is to be used for storing registerd notification consumers
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@NamedQueries({
        @NamedQuery(name = "notifications.findConsumersByActive",
                query = "select nc from NotificationConsumerEntity nc " +
                        "where nc.active = :active"),
        @NamedQuery(name = "notifications.findConsumersById",
                query = "select nc from NotificationConsumerEntity nc " +
                        "where nc.id = :id")
})
@Entity
@Table(name = "notification_consumers")
public class NotificationConsumerEntity implements IWSEntity {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "notification_consumer_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    /**
     * Group that owns this consumer
     */
    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group = null;

    /**
     * Consumer name
     */
    @Column(name = "name", length = 100)
    private String name = null;

    /**
     * Consumer class name
     */
    @Column(name = "class_name", length = 100)
    private String className = null;

    /**
     * Is activate - do we want to load this consumer?
     */
    @Column(name = "active")
    private Boolean active = false;

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

    public final void setGroup(final GroupEntity group) {
        this.group = group;
    }

    public final GroupEntity getGroup() {
        return group;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public final void setClassName(final String className) {
        this.className = className;
    }

    public final String getClassName() {
        return className;
    }

    public final void setActive(final Boolean active) {
        this.active = active;
    }

    public final Boolean getActive() {
        return active;
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
