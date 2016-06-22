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
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.persistence.monitoring.Monitored;

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
 * @since   IWS 1.0
 */
@NamedQueries(@NamedQuery(name = "address.findById",
        query = "select a from AddressEntity a " +
                "where a.id = :id"))
@Entity
@Table(name = "addresses")
@Monitored(name = "Address", level = MonitoringLevel.DETAILED)
public final class AddressEntity extends AbstractUpdateable<AddressEntity> {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @Monitored(name="Address Street 1", level = MonitoringLevel.DETAILED)
    @Column(name = "street1", length = 100)
    private String street1 = null;

    @Monitored(name="Address Street 2", level = MonitoringLevel.DETAILED)
    @Column(name = "street2", length = 100)
    private String street2 = null;

    @Monitored(name="Address Postal Code", level = MonitoringLevel.DETAILED)
    @Column(name = "postal_code", length = 12)
    private String postalCode = null;

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
    @JoinColumn(name = "country_id", referencedColumnName = "id")
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

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
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
        int changes = 0;

        changes += different(street1, obj.street1);
        changes += different(street2, obj.street2);
        changes += different(postalCode, obj.postalCode);
        changes += different(city, obj.city);
        changes += different(state, obj.state);
        changes += different(pobox, obj.pobox);

        return changes == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final AddressEntity obj) {
        if (canMerge(obj)) {
            street1 = which(street1, obj.street1);
            street2 = which(street2, obj.street2);
            postalCode = which(postalCode, obj.postalCode);
            city = which(city, obj.city);
            state = which(state, obj.state);
            pobox = which(pobox, obj.pobox);
        }
    }
}
