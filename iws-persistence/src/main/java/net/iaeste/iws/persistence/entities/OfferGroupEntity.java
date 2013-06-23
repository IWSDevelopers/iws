/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.OfferGroupEntity
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
        @NamedQuery(
                name = "offerGroup.findSharedToGroup",
                query = "select og.offer from OfferGroupEntity og " +
                        "where og.group.id = :gid"),
        @NamedQuery(name = "offerGroup.findByOffer",
                query = "select og from OfferGroupEntity og " +
                        "where og.offer.id = :oid"),
        @NamedQuery(name = "offerGroup.findByExternalOfferId",
                query = "select og from OfferGroupEntity og " +
                        "where og.offer.externalId = :eoid"),
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
public class OfferGroupEntity implements IWSEntity {

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "offer_to_group_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Long id = null;

    @ManyToOne(targetEntity = OfferEntity.class)
    @JoinColumn(nullable = false, name = "offer_id")
    private OfferEntity offer = null;

    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(nullable = false, name = "group_id")
    private GroupEntity group = null;

    @Column(name = "comment", length = 100)
    private String comment = null;

    /** Last time the User Account was modified. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = new Date();

    /** Timestamp when the user was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = new Date();

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(nullable = false, name = "modified_by")
    private UserEntity modifiedBy = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(nullable = false, name = "created_by")
    private UserEntity createdBy = null;

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

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
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

    public void setModified(final Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
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
}
