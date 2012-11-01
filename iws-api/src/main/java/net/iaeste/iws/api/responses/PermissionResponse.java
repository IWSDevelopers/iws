/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.PermissionResponse
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
import net.iaeste.iws.api.dtos.Authorization;
import net.iaeste.iws.api.util.Copier;

import java.util.ArrayList;
import java.util.List;

/**
 * Listing of Permission for a user.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection SuppressionAnnotation, CastToConcreteClass
 */
public final class PermissionResponse extends AbstractResponse {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private List<Authorization> authorizations;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public PermissionResponse() {
        authorizations = new ArrayList<>(0);
    }

    /**
     * Default Constructor.
     *
     * @param authorizations List of allowed Permission for a given user
     */
    public PermissionResponse(final List<Authorization> authorizations) {
        this.authorizations = Copier.copy(authorizations);
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public PermissionResponse(final IWSError error, final String message) {
        super(error, message);
        authorizations = null;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setAuthorizations(final List<Authorization> authorizations) {
        this.authorizations = Copier.copy(authorizations);
    }

    public List<Authorization> getAuthorizations() {
        return Copier.copy(authorizations);
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

        if (!(obj instanceof PermissionResponse)) {
            return false;
        }

        final PermissionResponse that = (PermissionResponse) obj;
        return !(authorizations != null ? !authorizations.equals(that.authorizations) : that.authorizations != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (authorizations != null ? authorizations.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "PermissionResponse{" +
                "authorizations=" + authorizations +
                '}';
    }
}
