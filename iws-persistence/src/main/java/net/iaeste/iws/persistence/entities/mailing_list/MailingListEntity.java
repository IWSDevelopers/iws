/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.mailing_list.MailingListEntity
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.persistence.entities.AbstractUpdateable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @since   IWS 1.0
 */
@NamedQueries({
        @NamedQuery(name = "mailing_list.findPublicListByExternalId",
                query = "select ml from MailingListEntity ml " +
                        "where ml.externalId = :eid " +
                        "  and ml.privateList = false "),
        @NamedQuery(name = "mailing_list.findPrivateListByExternalId",
                query = "select ml from MailingListEntity ml " +
                        "where ml.externalId = :eid " +
                        "  and ml.privateList = true "),
        @NamedQuery(name = "mailing_list.findByMailingListAddress",
                query = "select ml from MailingListEntity ml " +
                        "where ml.listAddress = :address ")
})
@Entity
@Table(name = "mailing_lists")
public class MailingListEntity extends AbstractUpdateable<MailingListEntity> {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "mailing_list_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @Column(name = "external_id", length = 36, unique = true, nullable = false, updatable = false)
    private String externalId = null;

    @Column(name = "private")
    private boolean privateList = true;

    @Column(name = "list_address", length = 100, unique = true)
    private String listAddress = null;

    @Column(name = "subject_prefix", length = 50)
    private String subjectPrefix = null;

    @Column(name = "active")
    private boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", nullable = false)
    private Date modified = new Date();

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
    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    /**
     * {@inheritDoc}
     */
    public String getExternalId() {
        return externalId;
    }

    public void setPrivateList(final boolean privateList) {
        this.privateList = privateList;
    }

    public boolean isPrivateList() {
        return privateList;
    }

    public void setListAddress(final String listAddress) {
        this.listAddress = listAddress;
    }

    public String getListAddress() {
        return listAddress;
    }

    public void setSubjectPrefix(final String subjectPrefix) {
        this.subjectPrefix = subjectPrefix;
    }

    public String getSubjectPrefix() {
        return subjectPrefix;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
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
    // Other Methods required for this Entity
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean diff(final MailingListEntity obj) {
        // Until properly implemented, better return true to avoid that we're
        // missing updates!
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final MailingListEntity obj) {
        if ((obj != null) && id.equals(obj.id)) {
            listAddress = which(listAddress, obj.listAddress);
            active = which(active, obj.active);
        }
    }
}
