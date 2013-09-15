/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.AddressEntity
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

import net.iaeste.iws.common.monitoring.Monitored;
import net.iaeste.iws.common.monitoring.MonitoringLevel;

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
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection AssignmentToDateFieldFromParameter
 */
@NamedQueries({
        @NamedQuery(name = "address.findById",
                query = "select a from AddressEntity a " +
                        "where a.id = :id"),
        @NamedQuery(name = "address.findByExternalId",
                query = "select a from AddressEntity a where a.externalId = :eid"),
        @NamedQuery(name = "address.findByValues",
                query = "select a from AddressEntity a " +
                        "where lower(a.street1) = lower(:street1)" +
                        "  and lower(a.zip) = lower(:zip)" +
                        "  and lower(a.city) = lower(:city)" +
                        "  and lower(a.state) = lower(:state)")
})
@Entity
@Table(name = "addresses")
@Monitored(name = "Address", level = MonitoringLevel.DETAILED)
public class AddressEntity implements Updateable<AddressEntity> {

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "address_sequence")
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

    @Monitored(name="Address Street 2", level = MonitoringLevel.DETAILED)
    @Column(name = "street1", length = 100)
    private String street1 = null;

    @Monitored(name="Address Street 2", level = MonitoringLevel.DETAILED)
    @Column(name = "street2", length = 100)
    private String street2 = null;

    @Monitored(name="Address ZIP (Postal Code)", level = MonitoringLevel.DETAILED)
    @Column(name = "zip", length = 100)
    private String zip = null;

    @Monitored(name="Address City", level = MonitoringLevel.DETAILED)
    @Column(name = "city", length = 100)
    private String city = null;

    @Monitored(name="Address State", level = MonitoringLevel.DETAILED)
    @Column(name = "state", length = 100)
    private String state = null;

    @Monitored(name="Address POBox", level = MonitoringLevel.DETAILED)
    @Column(name = "pobox", length = 100)
    private String pobox = null;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false, updatable = false)
    private CountryEntity country = null;

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
    public AddressEntity() {
    }

    /**
     * Default Constructor, for creating new entity.
     *
     * @param street1 First Street information
     * @param street2 Second Street information
     * @param zip     ZIP code
     * @param city    City
     * @param country Country
     */
    public AddressEntity(final String street1, final String street2, final String zip, final String city, final CountryEntity country) {
        this.street1 = street1;
        this.street2 = street2;
        this.zip = zip;
        this.city = city;
        this.country = country;
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

    public void setStreet1(final String street1) {
        this.street1 = street1;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet2(final String street2) {
        this.street2 = street2;
    }

    public String getStreet2() {
        return street2;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(final String region) {
        this.state = region;
    }

    public String getState() {
        return state;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setPobox(final String pobox) {
        this.pobox = pobox;
    }

    public String getPobox() {
        return pobox;
    }

    public void setCountry(final CountryEntity country) {
        this.country = country;
    }

    public CountryEntity getCountry() {
        return country;
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
    public boolean diff(final AddressEntity obj) {
        // Until properly implemented, better return true to avoid that we're
        // missing updates!
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final AddressEntity obj) {
        if ((obj != null) && externalId.equals(obj.externalId)) {
            street1 = obj.street1;
            street2 = obj.street2;
            zip = obj.zip;
            city = obj.city;
            state = obj.state;
            pobox = obj.pobox;
        }
    }
}
