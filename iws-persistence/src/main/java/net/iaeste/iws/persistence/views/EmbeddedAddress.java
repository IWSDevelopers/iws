/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.AddressView
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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * This is the Embedded Address Object, which is used by various Views. The
 * intentions of this Object, is to have a unified way of dealing with the
 * read-only Group, so only a single DTO mapping instance is required.
 *   If any one view require more information, then all views must be extended
 * with this. All Address information must be prefixed with "address_" in the
 * view.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Embeddable
public class EmbeddedAddress {

    @Column(name = "address_external_id", insertable = false, updatable = false)
    private String externalId = null;

    @Column(name = "address_street1", insertable = false, updatable = false)
    private String street1 = null;

    @Column(name = "address_street2", insertable = false, updatable = false)
    private String street2 = null;

    @Column(name = "address_zip", insertable = false, updatable = false)
    private String zip = null;

    @Column(name = "address_city", insertable = false, updatable = false)
    private String city = null;

    @Column(name = "address_state", insertable = false, updatable = false)
    private String state = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "address_modified", insertable = false, updatable = false)
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "address_created", insertable = false, updatable = false)
    private Date created = null;

    // =========================================================================
    // View Setters & Getters
    // =========================================================================

    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

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

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getState() {
        return state;
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
