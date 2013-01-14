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

import net.iaeste.iws.api.enums.OfferSharingStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 * @noinspection AssignmentToDateFieldFromParameter
 */
@NamedQueries({
        @NamedQuery(
                name = "offergroup.findById",
                query = "select og from OfferGroupEntity og " +
                        "where og.id = :id"),
        @NamedQuery(
                name = "offergroup.findGroupsByOffer",
                query = "select og from OfferGroupEntity og " +
                        "where og.group.id = :gid" +
                        "  and og.offer.id = :oid")
})
@Entity
@Table(name = "offer_to_group")
public class OfferGroupEntity implements IWSEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @ManyToOne(targetEntity = OfferEntity.class)
    @JoinColumn(nullable = false, name = "offer_id")
    private OfferEntity offer = null;

    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(nullable = false, name = "group_id")
    private GroupEntity group = null;

    @ManyToOne(targetEntity = StudentEntity.class)
    @JoinColumn(nullable = false, name = "student_id")
    private StudentEntity student = null;

    @Column(nullable = true, name = "status")
    private OfferSharingStatus status = null;

    @Column(nullable = false, name = "is_archived")
    private Boolean archived = false;

    @Column(nullable = false, name = "visible")
    private Boolean visible = true;

    @Column(name = "comment", length = 100)
    private String comment = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "answered")
    private Date answered = new Date();

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(nullable = false, name = "answered_by")
    private UserEntity answeredBy = null;

    /** Last time the User Account was modified. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = new Date();

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(nullable = false, name = "modified_by")
    private UserEntity modifiedBy = null;

    /** Timestamp when the user was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = new Date();

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

    public void setStudent(final StudentEntity student) {
        this.student = student;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStatus(final OfferSharingStatus status) {
        this.status = status;
    }

    public OfferSharingStatus getStatus() {
        return status;
    }

    public void setArchived(final Boolean archived) {
        this.archived = archived;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setVisible(final Boolean visible) {
        this.visible = visible;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setAnswered(final Date answered) {
        this.answered = answered;
    }

    public Date getAnswered() {
        return answered;
    }

    public void setAnsweredBy(final UserEntity answeredBy) {
        this.answeredBy = answeredBy;
    }

    public UserEntity getAnsweredBy() {
        return answeredBy;
    }

    public void setModified(final Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    public void setModifiedBy(final UserEntity modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public UserEntity getModifiedBy() {
        return modifiedBy;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public UserEntity getCreatedBy(final UserEntity createdBy) {
        return createdBy;
    }

    public Date getCreated() {
        return created;
    }
}
