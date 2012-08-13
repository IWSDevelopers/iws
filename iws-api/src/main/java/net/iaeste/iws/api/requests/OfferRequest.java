/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.OfferRequest
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
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.utils.Copier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @noinspection RedundantNoArgConstructor
 * @since 1.7
 */
public final class OfferRequest extends AbstractRequest {

    /**
     * {@link IWSConstants#SERIAL_VERSION_UID}.
     */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private final List<Offer> editOffers;
    private final List<Long> deleteOfferIDs;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public OfferRequest() {
        this(new ArrayList<Offer>(), new ArrayList<Long>());
    }

    public OfferRequest(final List<Offer> editOffers, final List<Long> deleteOfferIDs) {
        this.editOffers = Copier.copy(editOffers);
        this.deleteOfferIDs = Copier.copy(deleteOfferIDs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verify() throws VerificationException {
        for (final Offer offer : editOffers) {
            offer.verify();
        }
    }

    public List<Offer> getEditOffers() {
        return Copier.copy(editOffers);
    }

    public List<Long> getDeleteOfferIDs() {
        return Copier.copy(deleteOfferIDs);
    }
}
