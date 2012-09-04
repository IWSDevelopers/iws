/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.FetchEmployersRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.exceptions.VerificationException;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection RedundantNoArgConstructor
 */
public final class FetchEmployersRequest extends AbstractRequest {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private FetchType fetchType;
    private String name;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchEmployersRequest() {
        fetchType = null;
        name = null;
    }

    /**
     * Default Constructor, for setting all values.
     *
     * @param fetchType The Employer Fetch Type
     * @param name      The Employer Name
     */
    public FetchEmployersRequest(final FetchType fetchType, final String name) {
        this.fetchType = fetchType;
        this.name = name;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setFetchType(final FetchType fetchType) {
        this.fetchType = fetchType;
    }

    public FetchType getFetchType() {
        return fetchType;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void verify() throws VerificationException {
        verifyNotNull("fetchType", fetchType);
        verifyNotEmpty("name", name);
    }
}
