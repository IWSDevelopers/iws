/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "address", propOrder = { "street1", "street2", "postalCode", "city", "state", "pobox", "country" })
public final class Address extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Most fields of type String in this Object, are allowed to be as big as
     * this number.
     */
    public static final int FIELD_LENGTH = 100;

    /**
     * Postal Codes are used by a rather large number of countries, in fact
     * according to <a href="http://en.wikipedia.org/wiki/Postal_code">wikipedia</a>,
     * most countries are using these as an integral part of an Address. The
     * length of them is between 3 and 10 characters, with an optional Country
     * Code (two letters) additionally, meaning that the Postal Code can be up
     * to 12 Characters long.
     */
    public static final int POSTAL_CODE_LENGTH = 12;

    @XmlElement(required = true, nillable = true) private String street1 = null;
    @XmlElement(required = true, nillable = true) private String street2 = null;
    @XmlElement(required = true, nillable = true) private String postalCode = null;
    @XmlElement(required = true, nillable = true) private String city = null;
    @XmlElement(required = true, nillable = true) private String state = null;
    @XmlElement(required = true, nillable = true) private String pobox = null;
    @XmlElement(required = true, nillable = true) private Country country = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Address() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Copy Constructor.
     *
     * @param address Address Object to copy
     */
    public Address(final Address address) {
        if (address != null) {
            setStreet1(address.street1);
            setStreet2(address.street2);
            setPostalCode(address.postalCode);
            setCity(address.city);
            setState(address.state);
            setPobox(address.pobox);
            country = new Country(address.country);
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the Primary Street information for this Address. The value may be
     * null, but cannot exceed the maximum length.</p>
     *
     * <p>The method will thrown an {@code IllegalArgumentException} if the given
     * value exceeds the maximum length.</p>
     *
     * @param street1 Primary Street information
     * @throws IllegalArgumentException if the value exceeds the maximum length
     * @see #FIELD_LENGTH
     */
    public void setStreet1(final String street1) throws IllegalArgumentException {
        ensureNotTooLong("street1", street1, FIELD_LENGTH);
        this.street1 = sanitize(street1);
    }

    public String getStreet1() {
        return street1;
    }

    /**
     * <p>Sets the Secondary Street information for this Address. The value may
     * be null, but cannot exceed the maximum length.</p>
     *
     * <p>The method will thrown an {@code IllegalArgumentException} if the
     * given value exceeds the maximum length.</p>
     *
     * @param street2 Secondary Street information
     * @throws IllegalArgumentException if the value exceeds the maximum length
     * @see #FIELD_LENGTH
     */
    public void setStreet2(final String street2) throws IllegalArgumentException {
        ensureNotTooLong("street2", street2, FIELD_LENGTH);
        this.street2 = sanitize(street2);
    }

    public String getStreet2() {
        return street2;
    }

    /**
     * <p>Sets the Postal Code information for this Address. The value may be
     * null, but cannot exceed the maximum length.</p>
     *
     * <p>The method will thrown an {@code IllegalArgumentException} if the
     * given value exceeds the maximum length.</p>
     *
     * @param postalCode Postal Code
     * @throws IllegalArgumentException if the value exceeds the maximum length
     * @see #POSTAL_CODE_LENGTH
     */
    public void setPostalCode(final String postalCode) throws IllegalArgumentException {
        ensureNotTooLong("postalCode", postalCode, POSTAL_CODE_LENGTH);
        this.postalCode = sanitize(postalCode);
    }

    public String getPostalCode() {
        return postalCode;
    }

    /**
     * <p>Sets the City name for this Address. The value may be null, but cannot
     * exceed the maximum length.</p>
     *
     * <p>The method will thrown an {@code IllegalArgumentException} if the given
     * value exceeds the maximum length.</p>
     *
     * @param city City Name
     * @throws IllegalArgumentException if the value exceeds the maximum length
     * @see #FIELD_LENGTH
     */
    public void setCity(final String city) throws IllegalArgumentException {
        ensureNotTooLong("city", city, FIELD_LENGTH);
        this.city = sanitize(city);
    }

    public String getCity() {
        return city;
    }

    /**
     * <p>Sets the State Name for this Address. The value may be null, but
     * cannot exceed the maximum length.</p>
     *
     * <p>The method will thrown an {@code IllegalArgumentException} if the given
     * value exceeds the maximum length.</p>
     *
     * @param state State Name
     * @throws IllegalArgumentException if the value exceeds the maximum length
     * @see #FIELD_LENGTH
     */
    public void setState(final String state) throws IllegalArgumentException {
        ensureNotTooLong("state", state, FIELD_LENGTH);
        this.state = sanitize(state);
    }

    public String getState() {
        return state;
    }

    /**
     * <p>Sets the Post Office Box Number for this Address. The value may be
     * null, but cannot exceed the maximum length.</p>
     *
     * <p>The method will thrown an {@code IllegalArgumentException} if the
     * given value exceeds the maximum length.</p>
     *
     * @param pobox Post Office Box number
     * @throws IllegalArgumentException if the value exceeds the maximum length
     * @see #FIELD_LENGTH
     */
    public void setPobox(final String pobox) throws IllegalArgumentException {
        ensureNotTooLong("pobox", pobox, FIELD_LENGTH);
        this.pobox = sanitize(pobox);
    }

    public String getPobox() {
        return pobox;
    }

    /**
     * <p>Sets the Country for this Address. The value may be null, but must be
     * a valid Country Object.</p>
     *
     * <p>The method will thrown an {@code IllegalArgumentException} if the
     * given value is not a valid Country Object.</p>
     *
     * @param country Country Object
     * @throws IllegalArgumentException if the value is invalid
     */
    public void setCountry(final Country country) throws IllegalArgumentException {
        ensureVerifiable("country", country);
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
}
