/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.Address
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection OverlyComplexMethod
 */
public final class Address extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * All fields of type String in this Object, are allowed to be as big as
     * this number.
     */
    private static final int FIELD_LENGTH = 100;

    private String id = null;
    private String street1 = null;
    private String street2 = null;
    private String zip = null;
    private String city = null;
    private String region = null;
    private String pobox = null;
    private Country country = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Address() {
    }

    /**
     * Copy Constructor.
     *
     * @param address Address Object to copy
     */
    public Address(final Address address) {
        if (address != null) {
            id = address.id;
            street1 = address.street1;
            street2 = address.street2;
            zip = address.zip;
            city = address.city;
            region = address.region;
            pobox = address.pobox;
            country = new Country(address.country);
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setId(final String id) {
        ensureValidId("id", id);

        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setStreet1(final String street1) {
        ensureNotEmptyOrTooLong("street1", street1, FIELD_LENGTH);

        this.street1 = street1;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet2(final String street2) {
        ensureNotEmptyOrTooLong("street2", street2, FIELD_LENGTH);

        this.street2 = street2;
    }

    public String getStreet2() {
        return street2;
    }

    public void setZip(final String zip) {
        ensureNotEmptyOrTooLong("zip", zip, FIELD_LENGTH);

        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setCity(final String city) {
        ensureNotEmptyOrTooLong("city", city, FIELD_LENGTH);

        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setRegion(final String region) {
        ensureNotEmptyOrTooLong("region", region, FIELD_LENGTH);

        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setPobox(final String pobox) {
        ensureNotEmptyOrTooLong("pobox", pobox, FIELD_LENGTH);

        this.pobox = pobox;
    }

    public String getPobox() {
        return pobox;
    }

    public void setCountry(final Country country) {
        this.country = new Country(country);
    }

    public Country getCountry() {
        return new Country(country);
    }

    // =========================================================================
    // Standard DTO Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        // Since an Address is an optional Object, we're not going to make any
        // validity checks here
        return new HashMap<>(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Address)) {
            return false;
        }

        final Address address = (Address) obj;

        if (id != null ? !id.equals(address.id) : address.id != null) {
            return false;
        }
        if (street1 != null ? !street1.equals(address.street1) : address.street1 != null) {
            return false;
        }
        if (street2 != null ? !street2.equals(address.street2) : address.street2 != null) {
            return false;
        }
        if (zip != null ? !zip.equals(address.zip) : address.zip != null) {
            return false;
        }
        if (city != null ? !city.equals(address.city) : address.city != null) {
            return false;
        }
        if (region != null ? !region.equals(address.region) : address.region != null) {
            return false;
        }

        return !(country != null ? !country.equals(address.country) : address.country != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (id != null ? id.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (street1 != null ? street1.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (street2 != null ? street2.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (zip != null ? zip.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (city != null ? city.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (region != null ? region.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (country != null ? country.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", street1='" + street1 + '\'' +
                ", street2='" + street2 + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", country=" + country +
                '}';
    }
}
