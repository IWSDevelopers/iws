/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.MonitoringEntity
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
import net.iaeste.iws.common.monitoring.Monitored;
import net.iaeste.iws.common.monitoring.MonitoringLevel;

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
 * The monitoring of changes in the IWS, is handled via the Monitoring
 * mechanism. The data is stored in the history table, via this Entity.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@NamedQueries({
        @NamedQuery(
                name = "monitoring.findChanges",
                query = "select m from MonitoringEntity m " +
                        "where m.tableName = :table" +
                        "  and m.recordId = :record"),
        @NamedQuery(name = "monitoring.deleteChanges",
                query = "delete from MonitoringEntity m " +
                        "where m.tableName = :table" +
                        "  and m.recordId = :record")
})
@Entity
@Table(name = "history")
@Monitored(name = "history", level = MonitoringLevel.NONE)
public class MonitoringEntity implements IWSEntity {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "history_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private UserEntity user = null;

    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false, updatable = false)
    private GroupEntity group = null;

    @Column(name = "tablename", length = 50, nullable = false, updatable = false)
    private String tableName = null;

    @Column(name = "record_id", nullable = false, updatable = false)
    private Long recordId = null;

    @Column(name = "fields", nullable = false, updatable = false)
    private byte[] fields = null;

    /**
     * Timestamp when the Entity was created (changed).
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
    public MonitoringEntity() {
    }

    /**
     * Default Constructor, for creating new Monitoring Enttiy without the
     * changed field values
     *
     * @param user      The User making the change
     * @param group     The Group, which the Entity belongs to
     * @param tableName The name of the Entity to store the change for
     * @param recordId  The Id of the changed Entity
     */
    public MonitoringEntity(final UserEntity user, final GroupEntity group, final String tableName, final Long recordId) {
        this.user = user;
        this.group = group;
        this.tableName = tableName;
        this.recordId = recordId;
    }

    /**
     * Default Constructor, for creating new Monitoring Enttiy without the
     * changed field values
     *
     * @param user      The User making the change
     * @param group     The Group, which the Entity belongs to
     * @param tableName The name of the Entity to store the change for
     * @param recordId  The Id of the changed Entity
     * @param fields    Field values in a serialized, compressed byte array form
     */
    public MonitoringEntity(final UserEntity user, final GroupEntity group, final String tableName, final Long recordId, final byte[] fields) {
        this.user = user;
        this.group = group;
        this.tableName = tableName;
        this.recordId = recordId;
        this.fields = fields;
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

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setGroup(final GroupEntity group) {
        this.group = group;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setTableName(final String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setRecordId(final Long recordId) {
        this.recordId = recordId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setFields(final byte[] fields) {
        this.fields = fields;
    }

    public byte[] getFields() {
        return fields;
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
