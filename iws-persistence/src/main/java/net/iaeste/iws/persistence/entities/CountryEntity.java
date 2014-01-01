/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.common.monitoring.Monitored;
import net.iaeste.iws.common.monitoring.MonitoringLevel;
import net.iaeste.iws.persistence.Externable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * Entity for the Countries table. Contains all known countries, including their
 * relationship with IAESTE.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection AssignmentToDateFieldFromParameter
 */
@NamedQueries({
        @NamedQuery(name = "country.findAll",
                query = "select c from CountryEntity c"),
        @NamedQuery(name = "country.findByName",
                query = "select c from CountryEntity c " +
                        "where lower(c.countryName) = lower(:name)"),
        @NamedQuery(name = "country.findByCountryCode",
                query = "select c from CountryEntity c " +
                        "where lower(c.countryCode) = lower(:code)")
})
@Entity
@Table(name = "countries")
@Monitored(name = "Country", level = MonitoringLevel.DETAILED)
public class CountryEntity implements Externable<CountryEntity> {

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "country_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @Column(name = "country_code", length = 2, unique = true, nullable = false, updatable = false)
    private String countryCode = null;

    @Monitored(name="Country name", level = MonitoringLevel.DETAILED)
    @Column(name = "country_name", length = 100, unique = true, nullable = false)
    private String countryName = null;

    @Monitored(name="Country name full", level = MonitoringLevel.DETAILED)
    @Column(name = "country_name_full", length = 100)
    private String countryNameFull = null;

    @Monitored(name="Country name native", level = MonitoringLevel.DETAILED)
    @Column(name = "country_name_native", length = 100)
    private String countryNameNative = null;

    @Monitored(name="Country nationality", level = MonitoringLevel.DETAILED)
    @Column(name = "nationality", length = 100)
    private String nationality = null;

    @Monitored(name="Country citizens", level = MonitoringLevel.DETAILED)
    @Column(name = "citizens")
    private String citizens = null;

    @Monitored(name="Country phonecode", level = MonitoringLevel.DETAILED)
    @Column(name = "phonecode")
    private String phonecode = null;

    @Monitored(name="Country currency", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency = null;

    @Monitored(name="Country languages", level = MonitoringLevel.DETAILED)
    @Column(name = "languages")
    private String languages = null;

    @Monitored(name="Country membership", level = MonitoringLevel.DETAILED)
    @Enumerated(EnumType.STRING)
    @Column(name = "membership")
    private Membership membership = Membership.LISTED;

    @Monitored(name="Country member since", level = MonitoringLevel.DETAILED)
    @Column(name = "member_since")
    private Integer memberSince = null;

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
     * Empty Constructor, required by JPA.
     */
    public CountryEntity() {
    }

    /**
     * Default Constructor.
     *
     * @param countryCode The two-letter UN specificed Country Code
     * @param countryName The name of the Country
     */
    public CountryEntity(final String countryCode, final String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
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

    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExternalId(final String externalId) {
        this.countryCode = externalId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExternalId() {
        return countryCode;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryName(final String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryNameFull(final String countryNameFull) {
        this.countryNameFull = countryNameFull;
    }

    public String getCountryNameFull() {
        return countryNameFull;
    }

    public void setCountryNameNative(final String countryNameNative) {
        this.countryNameNative = countryNameNative;
    }

    public String getCountryNameNative() {
        return countryNameNative;
    }

    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setCitizens(final String citizens) {
        this.citizens = citizens;
    }

    public String getCitizens() {
        return citizens;
    }

    public void setPhonecode(final String phonecode) {
        this.phonecode = phonecode;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setLanguages(final String languages) {
        this.languages = languages;
    }

    public String getLanguages() {
        return languages;
    }

    public void setMembership(final Membership membership) {
        this.membership = membership;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMemberSince(final Integer memberSince) {
        this.memberSince = memberSince;
    }

    public Integer getMemberSince() {
        return memberSince;
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
    public boolean diff(final CountryEntity obj) {
        // Until properly implemented, better return true to avoid that we're
        // missing updates!
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final CountryEntity obj) {
        if ((obj != null) && (id != null) && id.equals(obj.id)) {
            countryName = obj.countryName;
            countryNameFull = obj.countryNameFull;
            countryNameNative = obj.countryNameNative;
            nationality = obj.nationality;
            citizens = obj.citizens;
            phonecode = obj.phonecode;
            currency = obj.currency;
            languages = obj.languages;
            membership = obj.membership;
            memberSince = obj.memberSince;
        }
    }
}
