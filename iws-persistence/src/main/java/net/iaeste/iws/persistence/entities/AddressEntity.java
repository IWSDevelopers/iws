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

import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection AssignmentToDateFieldFromParameter
 */
@NamedQueries({
        @NamedQuery(name = "address.findAll",
                query = "SELECT a FROM AddressEntity a"),
        @NamedQuery(name = "address.findById",
                query = "select a from AddressEntity a where a.id = :id")
})
@Entity
@Table(name = "addresses")
public class AddressEntity implements Mergeable<AddressEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @Column(name = "street1")
    private String street1 = null;

    @Column(name = "street2")
    private String street2 = null;

    @Column(name = "zip")
    private String zip = null;

    @Column(name = "city")
    private String city = null;

    @Column(name = "region")
    private String region = null;

    @ManyToOne(targetEntity = CountryEntity.class)
    @JoinColumn(nullable = false, name = "country_id")
    private CountryEntity country = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "modified")
    private Date modified;

    @Temporal(TemporalType.DATE)
    @Column(name = "created")
    private Date created;

    public AddressEntity() {
    }

    public AddressEntity(final String street1, final String street2, final String zip, final String city, final CountryEntity country) {
        this.street1 = street1;
        this.street2 = street2;
        this.zip = zip;
        this.city = city;
        this.country = country;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
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

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setCountry(final CountryEntity country) {
        this.country = country;
    }

    public CountryEntity getCountry() {
        return country;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final AddressEntity obj) {
        if ((obj != null) && id.equals(obj.id)) {
            street1 = obj.street1;
            street2 = obj.street2;
            zip = obj.zip;
            city = obj.city;
            country = obj.country;
        }
    }
}
