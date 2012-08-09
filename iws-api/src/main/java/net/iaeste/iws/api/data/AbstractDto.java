/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.data.AbstractDto
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.api.data;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.Verifiable;
import net.iaeste.iws.api.responses.Fallible;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public abstract class AbstractDto implements Fallible, Verifiable {
    /**
     * {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}.
     */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * IWBase Error Object.
     */
    protected final IWSError error;

    /**
     * IWBase Error Message
     */
    protected final String message;

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
     * @param error   IWS Error Object
     * @param message Error Message
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
    public abstract void verify() throws VerificationException;
}

