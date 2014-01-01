/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.CountryView
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.views;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Maps the information from the Country Details view in. The information in
 * this Object, is primarily Keys and embedded Object parts.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CompareToUsesNonFinalVariable
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "view.findCountriesByMembership",
                query = "select v from CountryView v " +
                        "where v.country.membership = :type "),
        @NamedQuery(name = "view.findCountriesByCountryCode",
                query = "select v from CountryView v " +
                        "where upper(v.country.countryCode) in :codes")
})
@Table(name = "country_details")
public class CountryView extends AbstractView<CountryView> {

    @Id
    @Column(name = "country_id")
    private Long id = null;

    @Embedded
    private EmbeddedCountry country = null;

    @Column(name = "list_name", insertable = false, updatable = false)
    private String listname = null;

    @Column(name = "ns_firstname", insertable = false, updatable = false)
    private String nsFirstname = null;

    @Column(name = "ns_lastname", insertable = false, updatable = false)
    private String nsLastname = null;

    // =========================================================================
    // View Setters & Getters
    // =========================================================================

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCountry(final EmbeddedCountry details) {
        this.country = details;
    }

    public EmbeddedCountry getCountry() {
        return country;
    }

    public void setListname(final String listname) {
        this.listname = listname;
    }

    public String getListname() {
        return listname;
    }

    public void setNsFirstname(final String nsFirstname) {
        this.nsFirstname = nsFirstname;
    }

    public String getNsFirstname() {
        return nsFirstname;
    }

    public void setNsLastname(final String nsLastname) {
        this.nsLastname = nsLastname;
    }

    public String getNsLastname() {
        return nsLastname;
    }

    // =========================================================================
    // Standard View Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CountryView)) {
            return false;
        }

        // As the view is reading from the current data model, and the Id is
        // always unique. It is sufficient to compare against this field.
        final CountryView that = (CountryView) obj;
        return id.equals(that.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final CountryView o) {
        final int result;

        switch (sortField) {
            case NAME:
                result = country.getCountryCode().compareTo(o.country.getCountryCode());
                break;
            default:
                result = country.getCountryCode().compareTo(o.country.getCountryCode());
        }

        return sortAscending ? result : -result;
    }
}
