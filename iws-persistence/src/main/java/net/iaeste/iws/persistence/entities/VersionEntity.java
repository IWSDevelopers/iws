/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.VersionEntity
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

import net.iaeste.iws.api.constants.IWSConstants;

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
 * The IWS is planned for a longer life, meaning that a series of updates is to
 * be expected. To avoid a clash between IWS & DB versions, this Entity will
 * provide the information about which version is installed, so an automatic
 * check can be made against the Database and from this, it is possible to also
 * make an automated update.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@NamedQueries({
        @NamedQuery(name = "version.findAll",
                query = "select v from VersionEntity v " +
                        "order by id desc"),
        @NamedQuery(name = "version.findLatest",
                query = "select v from VersionEntity v " +
                        "where id = (select max(id) from VersionEntity)")
})
@Entity
@Table(name = "versions")
public class VersionEntity implements IWSEntity {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "version_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @Column(name = "db_version", unique = true, nullable = false, updatable = false)
    private Integer dbVersion = null;

    @Column(name = "iws_version", length = 10, nullable = false, updatable = false)
    private String iwsVersion = null;

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
    public VersionEntity() {
    }

    /**
     * Default Constructor.
     *
     * @param dbVersion   Database Version
     * @param iwsVersion  IWS Version
     */
    public VersionEntity(final Integer dbVersion, final String iwsVersion) {
        this.dbVersion = dbVersion;
        this.iwsVersion = iwsVersion;
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

    public void setDbVersion(final Integer dbVersion) {
        this.dbVersion = dbVersion;
    }

    public Integer getDbVersion() {
        return dbVersion;
    }

    public void setIwsVersion(final String iwsVersion) {
        this.iwsVersion = iwsVersion;
    }

    public String getIwsVersion() {
        return iwsVersion;
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
}
