/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.constants.IWSError
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.constants;

import java.io.Serializable;

/**
 * All error codes, which are sent externally accessible, should be of this
 * type.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection SuppressionAnnotation
 */
public final class IWSError implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private final int error;
    private final String description;

    /**
     * Default Constructor.
     *
     * @param error        IWBase Error code
     * @param description  IWBase Error description
     */
    public IWSError(final int error, final String description) {
        this.error = error;
        this.description = description;
    }

    /**
     * Returns the Error Code.
     *
     * @return Error Code
     */
    public int getError() {
        return error;
    }

    /**
     * Returns the Error Description.
     *
     * @return Error Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        boolean result = false;

        if (this == obj) {
            result = true;
        } else if (obj != null && getClass() == obj.getClass()) {
            //noinspection CastToConcreteClass
            result = error == ((IWSError) obj).error;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = IWSConstants.HASHCODE_INITIAL_VALUE;

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + error;
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (description != null ? description.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IWSError[error=" + error + ",description=" + description + ']';
    }
}