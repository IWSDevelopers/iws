/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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

import net.iaeste.iws.persistence.monitoring.Monitored;
import net.iaeste.iws.persistence.monitoring.MonitoringLevel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
 * @since   1.7
 * @noinspection UnusedDeclaration, AssignmentToCollectionOrArrayFieldFromParameter, AssignmentToDateFieldFromParameter
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user = null;

    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(nullable = false, name = "group_id")
    private GroupEntity group = null;

    @Column(name = "tablename")
    private String tableName = null;

    @Column(name = "record_id")
    private Long recordId = null;

    @Column(name = "fields")
    private byte[] fields = null;

    @Temporal(TemporalType.TIMESTAMP)
    private Date changed = new Date();

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

    public void setChanged(final Date changed) {
        this.changed = changed;
    }

    public Date getChanged() {
        return changed;
    }
}
