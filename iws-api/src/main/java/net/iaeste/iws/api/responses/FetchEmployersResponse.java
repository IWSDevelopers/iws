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
import net.iaeste.iws.api.dtos.EmployerInformation;
import net.iaeste.iws.api.utils.Copier;

import java.util.List;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CastToConcreteClass
 */
public final class FetchEmployersResponse extends AbstractResponse {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private List<EmployerInformation> employersInformation;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchEmployersResponse() {
        super(IWSErrors.SUCCESS, IWSConstants.SUCCESS);
        employersInformation = null;
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public FetchEmployersResponse(final IWSError error, final String message) {
        super(error, message);
        employersInformation = null;
    }

    /**
     * Default Constructor.
     *
     * @param employersInformation List of EmployerInformation
     */
    public FetchEmployersResponse(final List<EmployerInformation> employersInformation) {
        this.employersInformation = Copier.copy(employersInformation);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setEmployersInformation(final List<EmployerInformation> employersInformation) {
        this.employersInformation = Copier.copy(employersInformation);
    }

    public List<EmployerInformation> getEmployersInformation() {
        return Copier.copy(employersInformation);
    }

    // =========================================================================
    // Standard Response Methods
    // =========================================================================

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof FetchEmployersResponse)) {
            return false;
        }

        final FetchEmployersResponse that = (FetchEmployersResponse) obj;
        return employersInformation.equals(that.employersInformation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (employersInformation != null ? employersInformation.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FetchEmployersResponse{" +
                "employersInformation=" + employersInformation +
                '}';
    }
}
