/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.SessionDataRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Serializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * IWS Clients can work with Sessions in many different ways. The Data they may
 * wish to store as part of a Session can also take on many types and shapes.
 * Hence, it is possible to set the data to the desired data type. It will, upon
 * being set, be converted to a Compressed Byte Array, that can be stored as a
 * Blob. The converting takes place upon setting the data.<br />
 *   Please note, that the SessionData is being Compressed upon converting to a
 * Serialized Byte array. This means, that the actual size of data allowed may
 * differ from the size provided, depending on how much the data can be
 * compressed. It is the size of the Compressed Data, which is being verified
 * against the allowed max size.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SessionDataRequest", propOrder = { "sessionData" })
public final class SessionDataRequest<T extends Serializable> extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * The maximum allowed size to be stored for the current Session. The value
     * is used against the Compressed Data Array, so even if the original Object
     * is bigger, it may still be allowed.
     */
    public static final int MAX_SIZE = 16384;

    /**
     * The Session Data, to be stored for the current Session. The data is
     * internally converted to a Compressed Byte Array, which may not exceed
     * 16 KB.
     */
    @XmlElement(required = true, nillable = true)
    private byte[] sessionData = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public SessionDataRequest() {
    }

    /**
     * Default Constructor, takes an arbitrary datatype as session data, and
     * stores it internally as a Byte Array.
     *
     * @param sessionData Client specific Session Data
     */
    public SessionDataRequest(final T sessionData) {
        this.sessionData = Serializer.serialize(sessionData);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the Session Data, the Data can be null - which will remove any data
     * currently stored for this Session or defined. Only condition is that the
     * compressed data does not exceed the maximum allowed size, defined by
     * #MAX_SIZE. If it does exceeds this after compression, then the method
     * will throw an IllegalArgument Exception.
     *
     * @param sessionData Session Data or null to remove
     * @throws IllegalArgumentException if the Compressed Session Data exceeds 16 KB
     * @see #MAX_SIZE
     */
    public void setSessionData(final T sessionData) throws IllegalArgumentException {
        final byte[] tmp = Serializer.serialize(sessionData);
        ensureNotTooLong("sessionData", tmp, MAX_SIZE);
        this.sessionData = tmp;
    }

    public byte[] getSessionData() {
        return sessionData;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        if ((sessionData != null) && (sessionData.length > MAX_SIZE)) {
            validation.put("sessionData", "The Session Data cannot exceed " + (MAX_SIZE / 1024) + "KB.");
        }

        return validation;
    }
}
