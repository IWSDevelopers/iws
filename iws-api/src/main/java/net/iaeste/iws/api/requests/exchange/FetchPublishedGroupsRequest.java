/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests.exchange;

import static net.iaeste.iws.api.util.Copier.copy;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Request Object for fetching sharing info for posted list of offers' Id.
 * The offers have to be owned by the group to which the user is
 * currently logged in otherwise the exception is thrown.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FetchPublishedGroupsRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private List<String> offerIds = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchPublishedGroupsRequest() {
    }

    /**
     * Default Constructor.
     *
     * @param offerIds offerIds of the offer for which the sharing info is to be fetched
     */
    public FetchPublishedGroupsRequest(final List<String> offerIds) {
        setOfferIds(offerIds);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     *
     * @param offerIds List of Offer Ids
     * @throws IllegalArgumentException if the given argument is invalid
     */
    public void setOfferIds(final List<String> offerIds) {
        for (final String offerId : offerIds) {
            ensureValidId("Offer Id", offerId);
        }

        this.offerIds = copy(offerIds);
    }

    public List<String> getOfferIds() {
        return offerIds;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        for (final String offerId : offerIds) {
            isNotNull(validation, "Offer Id", offerId);
        }

        return validation;
    }
}
