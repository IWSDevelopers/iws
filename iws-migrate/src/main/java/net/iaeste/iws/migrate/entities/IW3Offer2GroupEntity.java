/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.entities.IW3Offer2GroupEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.entities;

import net.iaeste.iws.api.constants.IWSConstants;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *   begin;
 *   create sequence offer2group_sequence start with 1 increment by 1;
 *   alter table offer2group add id integer not null primary key;
 *   update offer2group set id = nextval('offer2group_sequence');
 *   commit;
 * </pre>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@NamedQueries(
        @NamedQuery(name = "offergroup.findAll",
                query = "select og from IW3Offer2GroupEntity og")
)
@Entity
@Table(name = "offer2group")
public class IW3Offer2GroupEntity implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @EmbeddedId
    private IW3Offer2GroupId id = new IW3Offer2GroupId();

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "visible", length = 10)
    private Integer visible;

    @Column(name = "comment", length = 2147483647)
    private String comment;

    @Column(name = "studentid", length = 10)
    private Integer studentid;

    @Column(name = "isarchived", nullable = false, length = 1)
    private Boolean isarchived;

    @Column(name = "isacexchanged", length = 1)
    private Boolean isacexchanged;

    @Column(name = "exchanged", length = 1)
    private Boolean exchanged;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "answered")
    private Date answered;

    @Column(name = "answeredby", length = 10)
    private Integer answeredby;

    @Column(name = "createdby", length = 10)
    private Integer createdby;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    // =========================================================================
    // IW3 Entity Setters & Getters
    // =========================================================================

    public void setId(final IW3Offer2GroupId id) {
        this.id = id;
    }

    public IW3Offer2GroupId getId() {
        return id;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setVisible(final Integer visible) {
        this.visible = visible;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setStudentid(final Integer studentid) {
        this.studentid = studentid;
    }

    public Integer getStudentid() {
        return studentid;
    }

    public void setIsarchived(final Boolean isarchived) {
        this.isarchived = isarchived;
    }

    public Boolean getIsarchived() {
        return isarchived;
    }

    public void setIsacexchanged(final Boolean isacexchanged) {
        this.isacexchanged = isacexchanged;
    }

    public Boolean getIsacexchanged() {
        return isacexchanged;
    }

    public void setExchanged(final Boolean exchanged) {
        this.exchanged = exchanged;
    }

    public Boolean getExchanged() {
        return exchanged;
    }

    public void setAnswered(final Date answered) {
        this.answered = answered;
    }

    public Date getAnswered() {
        return answered;
    }

    public void setAnsweredby(final Integer answeredby) {
        this.answeredby = answeredby;
    }

    public Integer getAnsweredby() {
        return answeredby;
    }

    public void setCreatedby(final Integer createdby) {
        this.createdby = createdby;
    }

    public Integer getCreatedby() {
        return createdby;
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
}
