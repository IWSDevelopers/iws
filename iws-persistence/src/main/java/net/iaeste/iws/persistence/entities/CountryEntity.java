/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.CountryEntity
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@NamedQueries({
        @NamedQuery(name = "country.findAll",
                query = "SELECT c FROM CountryEntity c"),
        @NamedQuery(name = "country.findByName",
                query = "select c from CountryEntity c where c.countryName = :name")
})
@Entity
@Table(name = "countries")
public class CountryEntity implements Mergeable<CountryEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id = null;

    @Column(nullable = false, name = "country_name")
    private String countryName = null;

    /**
     * Empty Constructor, required by JPA.
     */
    public CountryEntity() {
    }

    /**
     * Default Constructor.
     *
     * @param countryName  The name of the Country
     */
    public CountryEntity(final String countryName) {
        this.countryName = countryName;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCountryName(final String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final CountryEntity obj) {
        if ((obj != null) && (id != null) && id.equals(obj.id)) {
            countryName = obj.countryName;
        }
    }
}
