/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.ApplicationResponse
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
import net.iaeste.iws.api.dtos.Application;
import net.iaeste.iws.api.util.AbstractFallible;
import net.iaeste.iws.api.util.Copier;

import java.util.List;

/**
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class ApplicationResponse extends AbstractFallible {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private final Application application;
    private final List<String> errors;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public ApplicationResponse() {
        super(IWSErrors.SUCCESS, IWSConstants.SUCCESS);
        application = null;
        errors = null;
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public ApplicationResponse(final IWSError error, final String message) {
        super(error, message);
        application = null;
        errors = null;
    }

    /**
     * Response is created when processing the application failed.
     * <p/>
     * Incorrect Applications should never be passed to this constructor. Instead use
     * constructor without list of errors parameter.
     *
     * @param failedApplication list of applications for which something went wrong
     */
    public ApplicationResponse(final Application failedApplication, final List<String> errors) {
        super(IWSErrors.PROCESSING_FAILURE, "processing of the Application failed");

        application = new Application(failedApplication);
        this.errors = Copier.copy(errors);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public Application getApplication() {
        return application;
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

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final ApplicationResponse that = (ApplicationResponse) obj;

        if (errors != null ? !errors.equals(that.errors) : that.errors != null) {
            return false;
        }

        return !(application != null ? !application.equals(that.application) : that.application != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (application != null ? application.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (errors != null ? errors.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "ApplicationResponse{" +
                "application=" + application +
                ", errors=" + errors +
                '}';
    }
}
