/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.PersonEntity
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
import net.iaeste.iws.api.enums.Gender;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Entity
@Table(name = "persons")
public class PersonEntity extends AbstractUpdateable<PersonEntity> {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "person_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @ManyToOne(targetEntity = CountryEntity.class)
    @JoinColumn(name = "nationality", referencedColumnName = "id")
    private CountryEntity nationality = null;

    @ManyToOne(targetEntity = AddressEntity.class)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address = null;

    @Column(name = "email", length = 100)
    private String email = null;

    @Column(name = "phone", length = 25)
    private String phone = null;

    @Column(name = "mobile", length = 25)
    private String mobile = null;

    @Column(name = "fax", length = 25)
    private String fax = null;

    @Column(name = "birthday")
    private Date birthday = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender = null;

    @Column(name = "understood_privacy", nullable = false)
    private Boolean understoodPrivacy = null;

    @Column(name = "accept_newsletters", nullable = false)
    private Boolean acceptNewsletters = null;

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
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Long getId() {
        return id;
    }

    public final void setNationality(final CountryEntity nationality) {
        this.nationality = nationality;
    }

    public final CountryEntity getNationality() {
        return nationality;
    }

    public final void setAddress(final AddressEntity address) {
        this.address = address;
    }

    public final AddressEntity getAddress() {
        return address;
    }

    public final void setEmail(final String email) {
        this.email = email;
    }

    public final String getEmail() {
        return email;
    }

    public final void setPhone(final String phone) {
        this.phone = phone;
    }

    public final String getPhone() {
        return phone;
    }

    public final void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    public final String getMobile() {
        return mobile;
    }

    public final void setFax(final String fax) {
        this.fax = fax;
    }

    public final String getFax() {
        return fax;
    }

    public final void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    public final Date getBirthday() {
        return birthday;
    }

    public final void setGender(final Gender gender) {
        this.gender = gender;
    }

    public final Gender getGender() {
        return gender;
    }

    public final void setUnderstoodPrivacy(final Boolean understoodPrivacy) {
        this.understoodPrivacy = understoodPrivacy;
    }

    public final Boolean getUnderstoodPrivacy() {
        return understoodPrivacy;
    }

    public final void setAcceptNewsletters(final Boolean acceptNewsletters) {
        this.acceptNewsletters = acceptNewsletters;
    }

    public final Boolean getAcceptNewsletters() {
        return acceptNewsletters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setModified(final Date modified) {
        this.modified = modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Date getModified() {
        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setCreated(final Date created) {
        this.created = created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Date getCreated() {
        return created;
    }

    // =========================================================================
    // Other Methods required for this Entity
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean diff(final PersonEntity obj) {
        // Until properly implemented, better return true to avoid that we're
        // missing updates!
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void merge(final PersonEntity obj) {
        if (canMerge(obj)) {
            // We're skipping the Address here, since the Address is another
            // Entity, which must be dealt with separately. It should be noted,
            // that also merging the Address in here was the source of a nasty
            // error, which was hard to find, see Trac ticket #1028.
            email = which(email, obj.email);
            phone = which(phone, obj.phone);
            mobile = which(mobile, obj.mobile);
            fax = which(fax, obj.fax);
            birthday = which(birthday, obj.birthday);
            gender = which(gender, obj.gender);
            understoodPrivacy = which(understoodPrivacy, obj.understoodPrivacy);
            acceptNewsletters = which(acceptNewsletters, obj.acceptNewsletters);
        }
    }
}
