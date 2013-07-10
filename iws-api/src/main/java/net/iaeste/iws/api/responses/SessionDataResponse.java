/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.SessionDataResponse
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.responses;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.util.AbstractFallible;
import net.iaeste.iws.api.util.Copier;
import net.iaeste.iws.api.util.DateTime;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

/**
 * The Session Response Object contains the Session Data belonging to the users
 * current active Session. The Data is stored internally as a Blob, i.e. a
 * Byte Array (Compressed). And is deserialized to an Object of the type
 * specified by the Client.<br />
 *   Note, that the IWS only allows a single active Session for any user at the
 * time, this is a limitation to avoid data corruption.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CastToConcreteClass
 */
public final class SessionDataResponse<T extends Serializable> extends AbstractFallible {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private byte[] sessionData = null;
    private DateTime created = null;
    private DateTime modified = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public SessionDataResponse() {
    }

    /**
     * Default Constructor, for setting all the data.
     *
     * @param sessionData Client specific Session Data
     * @param created     Time of Creation for this session
     * @param modified    Last update of the Session
     */
    public SessionDataResponse(final byte[] sessionData, final DateTime created, final DateTime modified) {
        this.sessionData = Copier.copy(sessionData);
        this.created = created;
        this.modified = modified;
    }

    /**
     * Error Constructor.
     *
     * @param error    IWS Error Object
     * @param message  Error Message
     */
    public SessionDataResponse(final IWSError error, final String message) {
        super(error, message);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setSessionData(final byte[] sessionData) {
        this.sessionData = Copier.copy(sessionData);
    }

    public T getSessionData() {
        return deserialize(sessionData);
    }

    public void setCreated(final DateTime created) {
        this.created = created;
    }

    public DateTime getCreated() {
        return created;
    }

    public void setModified(final DateTime modified) {
        this.modified = modified;
    }

    public DateTime getModified() {
        return modified;
    }

    // =========================================================================
    // Standard Response Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof SessionDataResponse)) {
            return false;
        }

        final SessionDataResponse<?> that = (SessionDataResponse<?>) obj;

        if (created != null ? !created.equals(that.created) : that.created != null) {
            return false;
        }

        if (modified != null ? !modified.equals(that.modified) : that.modified != null) {
            return false;
        }

        return !(sessionData != null ? !Arrays.equals(sessionData, that.sessionData) : that.sessionData != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (sessionData != null ? Arrays.hashCode(sessionData) : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (created != null ? created.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (modified != null ? modified.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SessionDataResponse{" +
                "created=" + created +
                ", modified=" + modified +
                '}';
    }

    private T deserialize(final byte[] bytes) {
        final T result;

        if (bytes != null) {
            try (final ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
                 final GZIPInputStream zipStream = new GZIPInputStream(byteStream);
                 final ObjectInputStream objectStream = new ObjectInputStream(zipStream)) {

                result = (T) objectStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new VerificationException(e);
            }
        } else  {
            result = null;
        }

        return result;
    }
}
