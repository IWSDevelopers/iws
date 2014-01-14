/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.entities.IW3CountriesEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.entities;

import net.iaeste.iws.api.constants.IWSConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@NamedQueries(
        @NamedQuery(name = "countries.findAll",
                query = "select c from IW3CountriesEntity c " +
                        "order by c.countryid asc")
)
@Entity
@Table(name = "countries")
public class IW3CountriesEntity implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @Column(name = "countryid", nullable = false, length = 2)
    private String countryid = null;

    @Column(name = "countryname", nullable = false, length = 100)
    private byte[] countryname = null;

    @Column(name = "countrynamefull", length = 100)
    private byte[] countrynamefull = null;

    @Column(name = "countrynamenative", length = 100)
    private byte[] countrynamenative = null;

    @Column(name = "nationality", nullable = false, length = 100)
    private byte[] nationality = null;

    @Column(name = "phonecode", length = 5)
    private byte[] phonecode = null;

    @Column(name = "currency", length = 3)
    private byte[] currency = null;

    @Column(name = "languages", length = 100)
    private byte[] languages = null;

    @Column(name = "membership", length = 10)
    private Integer membership = null;

    @Column(name = "membershipyear", length = 10)
    private Integer membershipyear = null;

    @Column(name = "citizens", length = 100)
    private byte[] citizens = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = null;

    // =========================================================================
    // IW3 Entity Setters & Getters
    // =========================================================================

    public void setCountryid(final String countryid) {
        this.countryid = countryid;
    }

    public String getCountryid() {
        return countryid;
    }

    public void setCountryname(final byte[] countryname) {
        this.countryname = countryname;
    }

    public byte[] getCountryname() {
        return countryname;
    }

    public void setCountrynamefull(final byte[] countrynamefull) {
        this.countrynamefull = countrynamefull;
    }

    public byte[] getCountrynamefull() {
        return countrynamefull;
    }

    public void setCountrynamenative(final byte[] countrynamenative) {
        this.countrynamenative = countrynamenative;
    }

    public byte[] getCountrynamenative() {
        return countrynamenative;
    }

    public void setNationality(final byte[] nationality) {
        this.nationality = nationality;
    }

    public byte[] getNationality() {
        return nationality;
    }

    public void setPhonecode(final byte[] phonecode) {
        this.phonecode = phonecode;
    }

    public byte[] getPhonecode() {
        return phonecode;
    }

    public void setCurrency(final byte[] currency) {
        this.currency = currency;
    }

    public byte[] getCurrency() {
        return currency;
    }

    public void setLanguages(final byte[] languages) {
        this.languages = languages;
    }

    public byte[] getLanguages() {
        return languages;
    }

    public void setMembership(final Integer membership) {
        this.membership = membership;
    }

    public Integer getMembership() {
        return membership;
    }

    public void setMembershipyear(final Integer membershipyear) {
        this.membershipyear = membershipyear;
    }

    public Integer getMembershipyear() {
        return membershipyear;
    }

    public void setCitizens(final byte[] citizens) {
        this.citizens = citizens;
    }

    public byte[] getCitizens() {
        return citizens;
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
}
