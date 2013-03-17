/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.FetchApplicationResponse
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
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class FetchApplicationsResponse extends AbstractFallible {

    /**
     * {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}.
     */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private List<Application> applications;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchApplicationsResponse() {
        super(IWSErrors.SUCCESS, IWSConstants.SUCCESS);
        applications = null;
    }

    /**
     * Default Constructor.
     *
     * @param applications List of Applications found
     */
    public FetchApplicationsResponse(final List<Application> applications) {
        this.applications = Copier.copy(applications);
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public FetchApplicationsResponse(final IWSError error, final String message) {
        super(error, message);
        applications = null;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
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

        if (!(obj instanceof FetchApplicationsResponse)) {
            return false;
        }

        final FetchApplicationsResponse that = (FetchApplicationsResponse) obj;
        return !(applications != null ? !applications.equals(that.applications) : that.applications != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (applications != null ? applications.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "FetchApplicationsResponse{" +
                "applications=" + applications +
                '}';
    }
}