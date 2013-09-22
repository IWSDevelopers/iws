/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.mailing_list.MailingListMembershipEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.entities.mailing_list;

import net.iaeste.iws.persistence.entities.IWSEntity;
import net.iaeste.iws.persistence.entities.Updateable;

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
 */
@NamedQueries({
        @NamedQuery(name = "mailing_list.clearPublicSubsriptionByExternalId",
                query = "delete from MailingListMembershipEntity mlm " +
                        "where mlm.mailingList.id = (select ml from MailingListEntity ml where ml.externalId = :eid and ml.privateList = false) "),
        @NamedQuery(name = "mailing_list.clearPrivateSubsriptionByExternalId",
                query = "delete from MailingListMembershipEntity mlm " +
                        "where mlm.mailingList.id = (select ml from MailingListEntity ml where ml.externalId = :eid and ml.privateList = true) ")
})
@Entity
@Table(name = "mailing_list_membership")
public class MailingListMembershipEntity implements IWSEntity {

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "mailing_list_membership_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @ManyToOne(targetEntity = MailingListEntity.class)
    @JoinColumn(name = "mailing_list_id", nullable = false)
    private MailingListEntity mailingList = null;

    @Column(name = "member", length = 100)
    private String member = null;

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

    public void setMailingList(final MailingListEntity mailingList) {
        this.mailingList = mailingList;
    }

    public MailingListEntity getMailingList() {
        return mailingList;
    }

    public void setMember(final String member) {
        this.member = member;
    }

    public String getMember() {
        return member;
    }

    @Override
    public void setCreated(final Date created) {
        this.created = created;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    // =========================================================================
    // Other Methods required for this Entity
    // =========================================================================

}
