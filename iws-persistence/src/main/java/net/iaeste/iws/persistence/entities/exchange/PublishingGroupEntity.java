/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.exchange.PublishingGroupEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.entities.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.persistence.monitoring.Monitored;
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.persistence.Externable;
import net.iaeste.iws.persistence.entities.AbstractUpdateable;
import net.iaeste.iws.persistence.entities.GroupEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Sharing List Entity
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@NamedQueries({
        @NamedQuery(name = "publishingGroup.findByExternalIdAndGroupId",
                query = "select pg from PublishingGroupEntity pg " +
                        "where pg.externalId = :eid" +
                        "  and pg.group.id = :gid"),
        @NamedQuery(name = "publishingGroup.findForOwner",
                query = "select pg from PublishingGroupEntity pg " +
                        "where pg.group.id = :gid")
})
@Entity
@Table(name = "publishing_groups")
@Monitored(name = "PublishingGroup", level = MonitoringLevel.DETAILED)
public class PublishingGroupEntity extends AbstractUpdateable<PublishingGroupEntity> implements Externable<PublishingGroupEntity> {

    /** {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "publishing_group_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    /**
     * The content of this Entity is exposed externally, however to avoid that
     * someone tries to spoof the system by second guessing our Sequence values,
     * An External Id is used, the External Id is a Uniqie UUID value, which in
     * all external references is referred to as the "Id". Although this can be
     * classified as StO (Security through Obscrutity), there is no need to
     * expose more information than necessary.
     */
    @Column(name = "external_id", length = 36, unique = true, nullable = false, updatable = false)
    private String externalId = null;

    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    private GroupEntity group = null;

    @Monitored(name="Sharing List name", level = MonitoringLevel.DETAILED)
    @Column(name = "name", length = 100, nullable = false)
    private String name = null;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "publishing_group_selection",
               joinColumns = { @JoinColumn(name = "publishing_group_id")},
               inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private List<GroupEntity> list = null;

//    @Monitored(name="Employer name", level = MonitoringLevel.DETAILED)
//    @Column(name = "name", length = 255, nullable = false)
//    private String name = null;

    /**
     * Last time the Entity was modified.
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
     * Empty Constructor, required by JPA.
     */
    public PublishingGroupEntity() {
    }

    /**
     * Default Constructor for creating new Entities of this type.
     *
     * @param name  Name of the Sharing List
     * @param group National Group, which this Sharing List belongs to
     */
    public PublishingGroupEntity(final String name, final GroupEntity group) {
        this.name = name;
        this.group = group;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExternalId() {
        return externalId;
    }

    public void setGroup(final GroupEntity group) {
        this.group = group;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setList(final List<GroupEntity> list) {
        this.list = list;
    }

    public List<GroupEntity> getList() {
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModified(final Date modified) {
        this.modified = modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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

    // =========================================================================
    // Standard Entity Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean diff(final PublishingGroupEntity obj) {
        int changes = 0;

        changes += different(name, obj.name);

        return changes == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final PublishingGroupEntity obj) {
        // don't merge if objects are not the same entity
        if ((id != null) && (obj != null) && externalId.equals(obj.externalId)) {
            name = obj.name;
            list = obj.list;
        }
    }
}
