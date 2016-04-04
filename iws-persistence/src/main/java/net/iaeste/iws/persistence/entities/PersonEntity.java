/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
public final class PersonEntity extends AbstractUpdateable<PersonEntity> {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void setNationality(final CountryEntity nationality) {
        this.nationality = nationality;
    }

    public CountryEntity getNationality() {
        return nationality;
    }

    public void setAddress(final AddressEntity address) {
        this.address = address;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setFax(final String fax) {
        this.fax = fax;
    }

    public String getFax() {
        return fax;
    }

    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public void setUnderstoodPrivacy(final Boolean understoodPrivacy) {
        this.understoodPrivacy = understoodPrivacy;
    }

    public Boolean getUnderstoodPrivacy() {
        return understoodPrivacy;
    }

    public void setAcceptNewsletters(final Boolean acceptNewsletters) {
        this.acceptNewsletters = acceptNewsletters;
    }

    public Boolean getAcceptNewsletters() {
        return acceptNewsletters;
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
    public boolean diff(final PersonEntity obj) {
        // Until properly implemented, better return true to avoid that we're
        // missing updates!
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final PersonEntity obj) {
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
