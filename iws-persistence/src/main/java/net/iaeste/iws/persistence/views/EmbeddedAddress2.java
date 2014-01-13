/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.EmbeddedAddress2
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
 * This is the second Embedded Address Object, which is used by various Views. The
 * intentions of this Object, is to have a unified way of dealing with the
 * read-only Address, so only a single DTO mapping instance is required.
 *   If any one view require more information, then all views must be extended
 * with this. All Address information must be prefixed with "address2_" in the
 * view.
 *
 * It's 1:1 copy of EmbeddedAddress.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Embeddable
public class EmbeddedAddress2 {

    @Column(name = "address2_street1", insertable = false, updatable = false)
    private String street1 = null;

    @Column(name = "address2_street2", insertable = false, updatable = false)
    private String street2 = null;

    @Column(name = "address2_postal_code", insertable = false, updatable = false)
    private String postalCode = null;

    @Column(name = "address2_city", insertable = false, updatable = false)
    private String city = null;

    @Column(name = "address2_state", insertable = false, updatable = false)
    private String state = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "address2_modified", insertable = false, updatable = false)
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "address2_created", insertable = false, updatable = false)
    private Date created = null;

    // =========================================================================
    // View Setters & Getters
    // =========================================================================

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

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
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
