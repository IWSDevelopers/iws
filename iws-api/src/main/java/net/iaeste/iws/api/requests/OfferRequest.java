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
import java.util.Set;
import java.util.TreeSet;

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

    private final List<Offer> updateOffers;
    private final List<Long> deleteOfferIds;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public OfferRequest() {
        this(new ArrayList<Offer>(), new ArrayList<Long>());
    }

    public OfferRequest(final List<Offer> updateOffers, final List<Long> deleteOfferIDs) {
        this.updateOffers = Copier.copy(updateOffers);
        this.deleteOfferIds = Copier.copy(deleteOfferIDs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verify() throws VerificationException {
        final Set<String> refNoSet = new TreeSet<>();
        final Set<Long> idSet = new TreeSet<>();
        final Set<Long> deleteIdSet = new TreeSet<>();

        for (final Offer offer : updateOffers) {
            if (offer == null) {
                throw new VerificationException("DTO cannot be null.");
            }
            offer.verify();

            if (offer.getId() != null) { // check for duplicated Ids
                if (idSet.contains(offer.getId())) {
                    throw new VerificationException("Duplicated DTOs' ids in the request.");
                }
                idSet.add(offer.getId());
            }
            if (refNoSet.contains(offer.getRefNo())) { // check for duplicated refNos
                throw new VerificationException("Duplicated DTOs' refNos in the request.");
            }
            refNoSet.add(offer.getRefNo());
        }
        for (final Long offerId : deleteOfferIds) {
            if (offerId == null) {
                throw new VerificationException("Id cannot be null.");
            }
            if (idSet.contains(offerId)) {
                throw new VerificationException("Cannot edit and delete same entity in one request.");
            }
            if (deleteIdSet.contains(offerId)) {
                throw new VerificationException("Duplicated ids for deletion.");
            }
            deleteIdSet.add(offerId);
        }
    }

    public List<Offer> getUpdateOffers() {
        return Copier.copy(updateOffers);
    }

    public List<Long> getDeleteOfferIds() {
        return Copier.copy(deleteOfferIds);
    }
}
