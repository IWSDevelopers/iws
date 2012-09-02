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
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.exceptions.VerificationException;

/**
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @noinspection RedundantNoArgConstructor
 * @since 1.7
 */
public final class FetchEmployersRequest extends AbstractRequest {
    private final FetchType fetchType;
    private final String name;

    public enum FetchType {
        OWNED
    }

    /**
     * {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}.
     */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchEmployersRequest(final FetchType fetchType, final String name) {
        this.fetchType = fetchType;
        this.name = name;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void verify() throws VerificationException {
        if (fetchType == null) {
            throw new VerificationException("Unexpected null value for fetchType");
        }
    }

    public FetchType getFetchType() {
        return fetchType;
    }

    public String getName() {
        return name;
    }
}