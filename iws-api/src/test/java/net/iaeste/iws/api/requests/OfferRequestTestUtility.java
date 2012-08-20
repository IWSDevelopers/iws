/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class OfferRequestTestUtility {

    private OfferRequestTestUtility() {
    }

    public static OfferRequest getEmptyRequest() {
        return new OfferRequest();
    }

    public static OfferRequest getValidRequest() {
        final List<Offer> validList = getValidUpdateOffersList();
        validList.addAll(getValidCreateOffersList());
        return new OfferRequest(validList, getUniqueIdList());

    }

    public static List<Long> getUniqueIdList() {
        final List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        return ids;
    }

    public static List<Offer> getValidUpdateOffersList() {
        final String[] refNos = { "AT-2012-1000", "AT-2012-1001", "AT-2012-1002", "AT-2012-1003", "AT-2012-1004" };
        final List<Offer> createList = getValidCreateOffersList();
        long i = 0;
        for (final Offer offer : createList) {
            offer.setRefNo(refNos[(int) i]);
            ++i;
            offer.setId(i);
        }
        return createList;
    }

    public static List<Offer> getValidCreateOffersList() {
        final String[] refNos = { "GB-2012-2000", "GB-2012-2001", "GB-2012-2002", "GB-2012-2003", "GB-2012-2004" };
        final List<Offer> validEditOffers = new ArrayList<>(refNos.length);
        for (final String refNo : refNos) {
            final Offer minimalOffer = OfferTestUtility.getMinimalOffer();
            minimalOffer.setRefNo(refNo);
            validEditOffers.add(minimalOffer);
        }
        return validEditOffers;
    }

    public static List<Long> getEmptyIdsList() {
        return new ArrayList<>(0);
    }

    public static List<Offer> getEmptyOfferList() {
        return new ArrayList<>(0);
    }
}
