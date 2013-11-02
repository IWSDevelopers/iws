/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity
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

import net.iaeste.iws.common.monitoring.Monitored;
import net.iaeste.iws.common.monitoring.MonitoringLevel;
import net.iaeste.iws.persistence.Externable;
import net.iaeste.iws.persistence.entities.AbstractUpdateable;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;

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
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection AssignmentToDateFieldFromParameter, ClassWithTooManyFields
 */
@NamedQueries({
        @NamedQuery(name = "offerGroup.findSharedToGroup",
                query = "select og.offer from OfferGroupEntity og " +
                        "where og.group.id = :gid"),
        @NamedQuery(name = "offerGroup.findByOffer",
                query = "select og from OfferGroupEntity og " +
                        "where og.offer.id = :oid"),
        @NamedQuery(name = "offerGroup.findByExternalOfferId",
                query = "select og from OfferGroupEntity og " +
                        "where og.offer.externalId = :eoid"),
        @NamedQuery(name = "offerGroup.findUnexpiredByExternalOfferId",
                query = "select og from OfferGroupEntity og " +
                        "where og.offer.externalId = :eoid and og.offer.nominationDeadline >= :date"),
        @NamedQuery(name = "offerGroup.deleteByOffer",
                query = "delete from OfferGroupEntity og " +
                        "where og.offer.id = :oid"),
        @NamedQuery(name = "offerGroup.deleteByOfferIdAndGroups",
                query = "delete from OfferGroupEntity og " +
                        "where og.offer.id = :oid" +
                        "  and og.group.id in :gids"),
        // The HSQLDB has an annoying issues with delete queries, hence we have
        // to create the query with a subselect, see:
        // http://docs.jboss.org/hibernate/orm/4.1/devguide/en-US/html_single/#d5e1041
        @NamedQuery(name = "offerGroup.deleteByExternalOfferId",
                query = "delete from OfferGroupEntity og " +
                        "where og.offer.id = (select o.id from OfferEntity o where o.externalId = :eoid)"),
        @NamedQuery(name = "offerGroup.deleteByOfferExternalIdAndGroups",
                query = "delete from OfferGroupEntity og " +
                        "where og.group.id in :gids " +
                        " and og.offer.id = (select o.id from OfferEntity o where o.externalId = :eoid)")
})
@Entity
@Table(name = "offer_to_group")
@Monitored(name = "Offer2Group", level = MonitoringLevel.DETAILED)
public class OfferGroupEntity extends AbstractUpdateable<OfferGroupEntity> implements Externable<OfferGroupEntity> {

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "offer_to_group_sequence")
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

    @ManyToOne(targetEntity = OfferEntity.class)
    @JoinColumn(name = "offer_id", nullable = false)
    private OfferEntity offer = null;

    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group = null;

    @Monitored(name="Offer2Group comment", level = MonitoringLevel.DETAILED)
    @Column(name = "comment", length = 1000)
    private String comment = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "modified_by", nullable = false)
    private UserEntity modifiedBy = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "created_by", nullable = false)
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

        return changes == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final OfferGroupEntity obj) {
        // don't merge if objects are not the same entity
        if ((id != null) && (obj != null) && externalId.equals(obj.externalId)) {
            comment = obj.comment;
        }
    }
}
