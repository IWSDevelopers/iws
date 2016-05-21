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
package net.iaeste.iws.persistence.entities.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.persistence.Externable;
import net.iaeste.iws.persistence.entities.AbstractUpdateable;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.monitoring.Monitored;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Collections;
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
public final class PublishingGroupEntity extends AbstractUpdateable<PublishingGroupEntity> implements Externable<PublishingGroupEntity> {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    /**
     * The content of this Entity is exposed externally, however to avoid that
     * someone tries to spoof the system by second guessing our Sequence values,
     * An External Id is used, the External Id is a Unique UUID value, which in
     * all external references is referred to as the "Id". Although this can be
     * classified as StO (Security through Obscurity), there is no need to
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
    @JoinTable(name = "publishing_group_selection", joinColumns = @JoinColumn(name = "publishing_group_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<GroupEntity> list = new ArrayList<>(0);

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
        this.list.addAll(list);
    }

    public List<GroupEntity> getList() {
        return Collections.unmodifiableList(list);
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
        if (canMerge(obj)) {
            name = which(name, obj.name);
            list = which(list, obj.list);
        }
    }
}
