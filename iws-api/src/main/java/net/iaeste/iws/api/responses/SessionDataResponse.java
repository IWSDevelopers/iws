/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.api.util.Serializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Arrays;

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
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sessionDataResponse", propOrder = { "sessionData", "modified", "created" })
public final class SessionDataResponse<T extends Serializable> extends FallibleResponse {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true) private byte[] sessionData = null;
    @XmlElement(required = true, nillable = true) private DateTime modified = null;
    @XmlElement(required = true, nillable = true) private DateTime created = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public SessionDataResponse() {
        // Required for WebServices to work. Comment added to please Sonar.
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
        this.sessionData = sessionData;
    }

    public T getSessionData() {
        return Serializer.deserialize(sessionData);
    }

    public void setModified(final DateTime modified) {
        this.modified = modified;
    }

    public DateTime getModified() {
        return modified;
    }

    public void setCreated(final DateTime created) {
        this.created = created;
    }

    public DateTime getCreated() {
        return created;
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
        if (!super.equals(obj)) {
            return false;
        }

        final SessionDataResponse<?> that = (SessionDataResponse<?>) obj;

        if (!Arrays.equals(sessionData, that.sessionData)) {
            return false;
        }
        if (modified != null ? !modified.equals(that.modified) : that.modified != null) {
            return false;
        }

        return !(created != null ? !created.equals(that.created) : that.created != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((sessionData != null) ? Arrays.hashCode(sessionData) : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((modified != null) ? modified.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((created != null) ? created.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SessionDataResponse{" +
                "modified=" + modified +
                ", created=" + created +
                '}';
    }
}
