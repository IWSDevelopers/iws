/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.utils.Copier;

import java.util.List;

/**
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class FetchEmployersResponse extends AbstractResponse {

    /**
     * {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}.
     */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private final List<Employer> employers;
    // TODO: To Be Discussed
    private final List<String> errors;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchEmployersResponse() {
        super(IWSErrors.SUCCESS, IWSConstants.SUCCESS);
        employers = null;
        errors = null;
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public FetchEmployersResponse(final IWSError error, final String message) {
        super(error, message);
        employers = null;
        errors = null;
    }

    public FetchEmployersResponse(final List<Employer> employers) {
        this.employers = employers;
        this.errors = null;
    }


    public List<Employer> getEmployers() {
        return Copier.copy(employers);
    }

    /**
     * TODO
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        throw new NotImplementedException("TBD");
    }

    /**
     * TODO
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FetchEmployersResponse{" +
                "employers=" + employers +
                ", errors=" + errors +
                '}';
    }

    /**
     * TODO
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (employers != null ? employers.hashCode() : 0);
        result = 31 * result + (errors != null ? errors.hashCode() : 0);
        return result;
    }
}
