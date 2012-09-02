/*
 * =============================================================================
 * Copyright 1998-$today.year, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services ($module.name) - $file.qualifiedClassName
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
import net.iaeste.iws.api.dtos.Employer;

import java.util.List;

/**
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class EmployerResponse extends AbstractResponse {

    /**
     * {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}.
     */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private final Employer employer;
    private final List<String> errors;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public EmployerResponse() {
        super(IWSErrors.SUCCESS, IWSConstants.SUCCESS);
        employer = null;
        errors = null;
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public EmployerResponse(final IWSError error, final String message) {
        super(error, message);
        employer = null;
        errors = null;
    }

    /**
     * Response is created when processing the employer failed.
     * <p/>
     * Incorrect Employer should never be passed to this constructor. Instead use constructor without list of errors parameter.
     *
     * @param failedEmployer list of employer for which something went wrong
     */
    public EmployerResponse(final Employer failedEmployer, final List<String> errors) {
        super(IWSErrors.PROCESSING_FAILURE, "processing of the Offer failed");
        this.employer = new Employer(failedEmployer);
        this.errors = errors;
    }

    public Employer getEmployer() {
        return new Employer(employer);
    }


    /**
     * TODO
     * {@inheritDoc}
     */
    public String toString() {
        return "EmployerResponse{" +
                "employer=" + employer +
                ", errors=" + errors +
                '}';
    }

    /**
     * TODO
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final EmployerResponse that = (EmployerResponse) o;

        if (errors != null ? !errors.equals(that.errors) : that.errors != null) {
            return false;
        }
        if (employer != null ? !employer.equals(that.employer) : that.employer != null) {
            return false;
        }

        return true;
    }

    /**
     * TODO
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (employer != null ? employer.hashCode() : 0);
        result = 31 * result + (errors != null ? errors.hashCode() : 0);
        return result;
    }
}
