/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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
import net.iaeste.iws.api.exceptions.VerificationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/**
 * IWS Clients can work with Sessions in many different ways. The Data they may
 * wish to store as part of a Session can also take on many types and shapes.
 * Hence, it is possible to set the data to the desired data type. It will, upon
 * being set, be converted to a Byte Array, that can be stored as a Blob. The
 * converting takes place upon setting the data.<br />
 *   Please note, that the SessionData is being compressed upon converting to a
 * Serialized Byte array. This means, that the actual size of data allowed may
 * differ from the size provided, depending on how much the data can be
 * compressed.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class SessionDataRequest<T extends Serializable> extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private byte[] sessionData = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public SessionDataRequest() {
    }

    /**
     * Default Constructor, takes an arbitrary datatype as sesseion data, and
     * stores it internally as a Byte Array.
     *
     * @param sessionData Client specific Session Data
     */
    public SessionDataRequest(final T sessionData) {
        this.sessionData = serialize(sessionData);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setSessionData(final T sessionData) {
        this.sessionData = serialize(sessionData);
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

        if ((sessionData != null) && (sessionData.length > 16384)) {
            validation.put("sessionData", "The Session Data cannot exceed 16Kb.");
        }

        return validation;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    private <T extends Serializable> byte[] serialize(final T data) {
        final byte[] result;

        if (data != null) {
            try (final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                 final GZIPOutputStream zipStream = new GZIPOutputStream(byteStream);
                 final ObjectOutputStream objectStream = new ObjectOutputStream(zipStream)) {

                objectStream.writeObject(data);
                objectStream.close();

                result = byteStream.toByteArray();
            } catch (IOException e) {
                throw new VerificationException(e);
            }
        } else {
            result = null;
        }

        return result;
    }
}
