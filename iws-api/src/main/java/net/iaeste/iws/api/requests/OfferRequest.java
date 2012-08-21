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

    private final Offer offer;
    private final boolean forDeletion;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public OfferRequest() {
        this.offer = null;
        this.forDeletion = false;
    }

    /**
     * If the ID in the offer DTO is null, it is an insert operation otherwise it is an update.
     *
     * @param offer object to create or update
     */
    public OfferRequest(final Offer offer) {
        this.offer = new Offer(offer);
        this.forDeletion = false;
    }

    /**
     * @param deleteOfferId id for Offer to delete
     */
    public OfferRequest(final Long deleteOfferId) {
        final Offer offerForDeletion = new Offer();
        offerForDeletion.setId(deleteOfferId);

        this.offer = offerForDeletion;
        this.forDeletion = true;
    }

    /**
     * <li>If the flag {@code forDeletion = true},
     * only the ID is retrieved from the offer DTO and it is not checked for validity.</li>
     * <li>
     * In these two cases, the offer DTO is checked for validity.</li>
     * <p/>
     * {@inheritDoc}
     */
    @Override
    public void verify() throws VerificationException {
        if (offer == null) {
            throw new VerificationException("no offer to update or delete");
        }
        if (forDeletion) {
            if (offer.getId() == null) {
                throw new VerificationException("id of Offer for deletion was not passed");
            }
            if (offer.getId() <= 0) {
                throw new VerificationException("primary keys start from 1");
            }
        } else {
            offer.verify();
        }
    }

    public Offer getOffer() {
        return new Offer(offer);
    }

    public boolean isForDeletion() {
        return forDeletion;
    }
}
