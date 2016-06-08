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
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.persistence.Externable;
import net.iaeste.iws.persistence.entities.AbstractUpdateable;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.monitoring.Monitored;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 * @noinspection AssignmentToDateFieldFromParameter, ClassWithTooManyFields
 */
@NamedQueries({
        @NamedQuery(name = "offerGroup.findSharedToGroup",
                query = "select og.offer from OfferGroupEntity og " +
                        "where og.group.id = :gid" +
                        "  and og.offer.exchangeYear = :year"),
        @NamedQuery(name = "offerGroup.findByOffer",
                query = "select og from OfferGroupEntity og " +
                        "where og.offer.id = :oid"),
        @NamedQuery(name = "offerGroup.findByOffers",
                query = "select og from OfferGroupEntity og " +
                        "where og.offer.id in (:oids)"),
        @NamedQuery(name = "offerGroup.findByOfferAndStatuses",
                query = "select og from OfferGroupEntity og " +
                        "where og.offer.id = :oid " +
                        "  and og.status in :statuses"),
        @NamedQuery(name = "offerGroup.findByOfferAndGroup",
                query = "select og from OfferGroupEntity og " +
                        "where og.offer.id = :oid " +
                        "  and og.group.id = :gid"),
        @NamedQuery(name = "offerGroup.findByOfferAndGroupList",
                query = "select og from OfferGroupEntity og " +
                        "where og.offer.id = :oid " +
                        "  and og.group.id in :gids"),
        @NamedQuery(name = "offerGroup.findByExternalOfferId",
                query = "select og from OfferGroupEntity og " +
                        "where og.offer.externalId = :eoid"),
        @NamedQuery(name = "offerGroup.findByGroupAndExternalId",
                query = "select og from OfferGroupEntity og " +
                        "where og.offer.externalId = :eoid" +
                        "  and og.group.id = :gid"),
        @NamedQuery(name = "offerGroup.findByGroupAndExternalIds",
                query = "select og from OfferGroupEntity og " +
                        "where og.offer.externalId in :eoids" +
                        "  and og.group.id = :gid"),
        @NamedQuery(name = "offerGroup.findUnexpiredByExternalOfferId",
                query = "select og from OfferGroupEntity og " +
                        "where og.offer.externalId = :eoid" +
                        "  and og.offer.nominationDeadline >= :date"),
        @NamedQuery(name = "offerGroup.deleteByOffer",
                query = "delete from OfferGroupEntity og " +
                        "where og.offer.id = :oid"),
        @NamedQuery(name = "offerGroup.deleteByIds",
                query = "delete from OfferGroupEntity og " +
                        "where og.id in :ids"),
        @NamedQuery(name = "offerGroup.deleteByOfferIdAndGroups",
                query = "delete from OfferGroupEntity og " +
                        "where og.offer.id = :oid" +
                        "  and og.group.id in :gids"),
        // The HSQLDB has an annoying issues with delete queries, hence we have
        // to create the query with a sub select, see:
        // http://docs.jboss.org/hibernate/orm/4.1/devguide/en-US/html_single/#d5e1041
        @NamedQuery(name = "offerGroup.deleteByExternalOfferId",
                query = "delete from OfferGroupEntity og " +
                        "where og.offer.id = (select o.id from OfferEntity o where o.externalId = :eoid)"),
        @NamedQuery(name = "offerGroup.deleteByOfferExternalIdAndGroups",
                query = "delete from OfferGroupEntity og " +
                        "where og.group.id in :gids " +
                        " and og.offer.id = (select o.id from OfferEntity o where o.externalId = :eoid)"),
        @NamedQuery(name = "offerGroup.updateStateByIds",
                query = "update OfferGroupEntity og " +
                        "set og.status = :status " +
                        "where og.id in :ids"),
        @NamedQuery(name = "offerGroup.hideByIds",
        query = "update OfferGroupEntity og " +
                "set og.hidden = true " +
                "where og.id in :ids")
})
@Entity
@Table(name = "offer_to_group")
@Monitored(name = "Offer2Group", level = MonitoringLevel.DETAILED)
public final class OfferGroupEntity extends AbstractUpdateable<OfferGroupEntity> implements Externable<OfferGroupEntity> {

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

    @ManyToOne(targetEntity = OfferEntity.class)
    @JoinColumn(name = "offer_id", referencedColumnName = "id", nullable = false)
    private OfferEntity offer = null;

    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    private GroupEntity group = null;

    @Monitored(name="Offer2Group comment", level = MonitoringLevel.DETAILED)
    @Column(name = "comment", length = 500)
    private String comment = null;

    @Monitored(name="Offer2Group status", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 25)
    private OfferState status = OfferState.SHARED;

    @Column(name = "has_application")
    private Boolean hasApplication = false;

    @Column(name = "hidden")
    private Boolean hidden = false;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "modified_by", referencedColumnName = "id", nullable = false)
    private UserEntity modifiedBy = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    private UserEntity createdBy = null;

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
     * Empty Constructor, JPA requirement.
     */
    public OfferGroupEntity() {
        // Empty Constructor required by JPA, comment to please Sonar.
    }

    /**
     * Default Constructor, for creating a new Offer Group Relation.
     *
     * @param offer  The Offer to be shared
     * @param group  The Group to which the offer is shared
     */
    public OfferGroupEntity(final OfferEntity offer, final GroupEntity group) {
        this.offer = offer;
        this.group = group;
    }

    /**
     * Copy Constructor
     *
     * @param entity OfferGroup entity to copy
     */
    public OfferGroupEntity(final OfferGroupEntity entity) {
        if (entity != null) {
            externalId = entity.externalId;
            offer = entity.offer;
            group = entity.group;
            comment = entity.comment;
            status = entity.status;
            hasApplication = entity.hasApplication;
            hidden = entity.hidden;
            modifiedBy = entity.modifiedBy;
            createdBy = entity.createdBy;
            modified = entity.modified;
            created = entity.created;
        }
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

    public void setOffer(final OfferEntity offer) {
        this.offer = offer;
    }

    public OfferEntity getOffer() {
        return offer;
    }

    public void setGroup(final GroupEntity group) {
        this.group = group;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public OfferState getStatus() {
        return status;
    }

    public void setStatus(final OfferState status) {
        this.status = status;
    }

    public void setModifiedBy(final UserEntity modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public UserEntity getModifiedBy() {
        return modifiedBy;
    }

    public void setCreatedBy(final UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setHasApplication(final Boolean hasApplication) {
        this.hasApplication = hasApplication;
    }

    public Boolean getHasApplication() {
        return hasApplication;
    }

    public void setHidden(final Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getHidden() {
        return hidden;
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
    public boolean diff(final OfferGroupEntity obj) {
        int changes = 0;

        changes += different(comment, obj.comment);
        changes += different(status, obj.status);

        return changes == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final OfferGroupEntity obj) {
        if (canMerge(obj)) {
            comment = which(comment, obj.comment);
            status = which(status, obj.status);
        }
    }
}
