/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.FetchOffersRequest
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
import net.iaeste.iws.api.data.Offer;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.utils.Copier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @noinspection RedundantNoArgConstructor
 * @since 1.7
 */
public final class FetchOffersRequest extends AbstractRequest {
    private final FetchType fetchType;
    private final List<Long> offers;
    private final Offer offerPrototype;

    public enum FetchType {
        NONE, BY_ID, PROTOTYPE, LIMIT, ALL
    }

    /**
     * {@link IWSConstants#SERIAL_VERSION_UID}.
     */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchOffersRequest() {
        this.fetchType = FetchType.ALL;
        this.offers = new ArrayList<>();
        this.offerPrototype = null;
    }

    public FetchOffersRequest(final List<Long> offerIds) {
        this.fetchType = FetchType.BY_ID;
        this.offers = Copier.copy(offerIds);
        this.offerPrototype = null;
    }

    /**
     * Fetch all offers which matches the fields in Offer object which is only a prototype.
     *
     * @param offerPrototype
     */
    public FetchOffersRequest(final Offer offerPrototype) {
        this.fetchType = FetchType.PROTOTYPE;
        this.offers = new ArrayList<>();
        this.offerPrototype = offerPrototype;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verify() throws VerificationException {
        if (offers == null) {
            throw new VerificationException("Unexpected null value for offers' list");
        }
        if (fetchType == null) {
            throw new VerificationException("Unexpected null value for fetchType");
        }
    }

    public FetchType getFetchType() {
        return fetchType;
    }

    public List<Long> getOffers() {
        return Collections.unmodifiableList(offers);
    }
}