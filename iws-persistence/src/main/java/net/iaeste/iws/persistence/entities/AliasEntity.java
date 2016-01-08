/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.AliasEntity
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
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.persistence.Externable;
import net.iaeste.iws.persistence.monitoring.Monitored;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * <p>If a Group is changing name, then the old name should exist for a period
 * of time, which can be either indefinite or finite. If finite, then the
 * expires flag must be set, and when it expires, it will change from active to
 * a timestamp. This way, a unique constraint can ensure that the same address
 * cannot exist multiple times for the same Group at the same time.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@Entity
@Table(name = "aliases")
@Monitored(name = "Aliases", level = MonitoringLevel.DETAILED)
public final class AliasEntity extends AbstractUpdateable<AliasEntity> implements Externable<AliasEntity> {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "alias_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
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

    /**
     * The group for which the alias exist. If the GroupType is private, then
     * the alias is a user alias, otherwise it is a mailing list alias.
     */
    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false, updatable = false)
    private GroupEntity group = null;

    /**
     * The alternative address, i.e. the alias which must be kept alive.
     */
    @Column(name = "alias_address", length = 100, nullable = false)
    @Monitored(name = "Mailing List Alias Address", level = MonitoringLevel.DETAILED)
    private String aliasAddress = null;

    /**
     * If an alias is no longer active, then this flag is updated with a
     * timestamp to reflect this. This way, it is not possible to have the
     * same address active twice, as there is a unique constraint on the
     * combined address and status flag.
     */
    @Column(name = "deprecated", length = 36, nullable = false)
    @Monitored(name = "Mailing List Deprecated", level = MonitoringLevel.DETAILED)
    private String deprecated = "active";

    @Temporal(TemporalType.DATE)
    @Column(name = "expires", nullable = false)
    @Monitored(name = "Mailing List Expires", level = MonitoringLevel.DETAILED)
    private Date expires = null;

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

    public void setAliasAddress(final String aliasAddress) {
        this.aliasAddress = aliasAddress;
    }

    public String getAliasAddress() {
        return aliasAddress;
    }

    public void setDeprecated(final String deprecated) {
        this.deprecated = deprecated;
    }

    public String getDeprecated() {
        return deprecated;
    }

    public void setExpires(final Date expires) {
        this.expires = expires;
    }

    public Date getExpires() {
        return expires;
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
    // Entity Standard Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean diff(final AliasEntity obj) {
        // Until properly implemented, better return true to avoid that we're
        // missing updates!
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final AliasEntity obj) {
        if (canMerge(obj)) {
            aliasAddress = obj.aliasAddress;
            deprecated = obj.deprecated;
            expires = obj.expires;
        }
    }
}
