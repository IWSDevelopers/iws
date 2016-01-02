/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.AbstractDto
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.util;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;

/**
 * This Abstract Class contains the combined Verifiable & Fallible logic.
 * Unfortunately, Java doesn't allow that a Class can inherit from 2 Super
 * classes, hence we need to have some duplicate logic in this class :-(<br />
 *   To minimize the amount of duplication, the Verification is extended, since
 * this class contains all the check methods, whereas the AbstractFallible class
 * only contain the rather simple error functionality.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public abstract class AbstractDto extends AbstractVerification implements Fallible {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** IWS Error Object. */
    private final IWSError error;

    /** IWS Error Message. */
    private final String message;

    /**
     * Default Constructor.
     */
    protected AbstractDto() {
        error = IWSErrors.SUCCESS;
        message = IWSConstants.SUCCESS;
    }

    /**
     * Error Constructor, for all Result Objects.
     *
     * @param error    IWS Error Object
     * @param message  Error Message
     */
    protected AbstractDto(final IWSError error, final String message) {
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
    public String getMessage() {
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IWSError getError() {
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
        if (!(obj instanceof AbstractDto)) {
            return false;
        }

        final AbstractDto that = (AbstractDto) obj;

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
    public abstract String toString();
}
