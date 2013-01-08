/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.persistence.monitoring.Monitored;
import net.iaeste.iws.persistence.monitoring.MonitoringLevel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
                        "where c.countryName = :name"),
        @NamedQuery(name = "country.findByCountryId",
                query = "select c from CountryEntity c " +
                        "where c.countryId = :cid")
})
@Entity
@Monitored(name = "countries", level = MonitoringLevel.MARKED)
@Table(name = "countries")
public class CountryEntity implements Mergeable<CountryEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @Column(name = "country_id", nullable = false, unique = true)
    private String countryId = null;

    @Monitored(name="country name", level = MonitoringLevel.MARKED)
    @Column(nullable = false, name = "country_name")
    private String countryName = null;

    @Monitored(name="country name full", level = MonitoringLevel.MARKED)
    @Column(name = "country_name_full")
    private String countryNameFull = null;

    @Monitored(name="country name native", level = MonitoringLevel.MARKED)
    @Column(name = "country_name_native")
    private String countryNameNative = null;

    @Monitored(name="nationality", level = MonitoringLevel.MARKED)
    @Column(name = "nationality")
    private String nationality = null;

    @Monitored(name="citizens", level = MonitoringLevel.MARKED)
    @Column(name = "citizens")
    private String citizens = null;

    @Monitored(name="phonecode", level = MonitoringLevel.MARKED)
    @Column(name = "phonecode")
    private String phonecode = null;

    @Monitored(name="currency", level = MonitoringLevel.MARKED)
    @Column(name = "currency")
    private String currency = null;

    @Monitored(name="languages", level = MonitoringLevel.MARKED)
    @Column(name = "languages")
    private String languages = null;

    @Monitored(name="membership", level = MonitoringLevel.MARKED)
    @Enumerated(EnumType.STRING)
    @Column(name = "membership")
    private Membership membership = Membership.LISTED;

    @Monitored(name="member since", level = MonitoringLevel.MARKED)
    @Column(name = "member_since")
    private Integer memberSince = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", nullable = false)
    private Date modified = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
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
     * @param countryName  The name of the Country
     */
    public CountryEntity(final String countryId, final String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setCountryId(final String countryId) {
        this.countryId = countryId;
    }

    public String getCountryId() {
        return countryId;
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

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
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

    // =========================================================================
    // Entity Standard Methods
    // =========================================================================

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

            // Merging over, let's just set the Modified date to 'now'
            modified = new Date();
        }
    }
}
