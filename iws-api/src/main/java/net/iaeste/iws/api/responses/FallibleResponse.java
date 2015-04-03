/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.FallibleResponse
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
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.util.Fallible;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Default Response Object, for those methods, that only return the error
 * information from IWS.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FallibleResponse", propOrder = { "error", "message" })
public class FallibleResponse implements Fallible {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** IWS Error Object. */
    @XmlElement(required = true, nillable = false)
    private final IWSError error;

    /** IWS Error Message. */
    @XmlElement(required = true, nillable = false)
    private final String message;

    /**
     * Default Constructor.
     */
    public FallibleResponse() {
        error = IWSErrors.SUCCESS;
        message = IWSConstants.SUCCESS;
    }

    /**
     * Error Constructor, for all Result Objects.
     *
     * @param error    IWS Error Object
     * @param message  Error Message
     */
    public FallibleResponse(final IWSError error, final String message) {
        this.error = error;
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isOk() {
        return IWSErrors.SUCCESS.getError() == error.getError();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getMessage() {
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final IWSError getError() {
        return error;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FallibleResponse)) {
            return false;
        }

        final FallibleResponse that = (FallibleResponse) obj;

        if ((error != null) ? !error.equals(that.error) : (that.error != null)) {
            return false;
        }

        return !((message != null) ? !message.equals(that.message) : (that.message != null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = IWSConstants.HASHCODE_INITIAL_VALUE;

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + ((error != null) ? error.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + ((message != null) ? message.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FallibleResponse{" +
                "error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
