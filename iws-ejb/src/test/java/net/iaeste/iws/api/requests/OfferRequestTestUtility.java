/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.OfferRequestTestUtility
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

import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.OfferTestUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class OfferRequestTestUtility {

    private OfferRequestTestUtility() {
    }

    public static List<Offer> getValidOffersList() {
        final String[] refNos = { "GB-2012-2000", "GB-2012-2001", "GB-2012-2002", "GB-2012-2003", "GB-2012-2004" };
        final List<Offer> validEditOffers = new ArrayList<>(refNos.length);
        for (final String refNo : refNos) {
            final Offer minimalOffer = OfferTestUtility.getMinimalOffer();
            minimalOffer.setRefNo(refNo);
            validEditOffers.add(minimalOffer);
        }
        return validEditOffers;
    }

}
