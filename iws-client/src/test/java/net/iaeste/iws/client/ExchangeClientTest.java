/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.ExchangeClientTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.OfferTestUtility;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.DeleteOfferRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.api.responses.OfferResponse;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class ExchangeClientTest {

    private final Exchange exchange = new ExchangeClient();
    private static final Access access = new AccessClient();
    private static AuthenticationToken token;

    @BeforeClass
    public static void registerSession() {
        final AuthenticationResponse response =
                access.generateSession(new AuthenticationRequest("Michl", "frodo"));
        assertThat(response.isOk(), is(true));
        token = new AuthenticationToken(response.getToken());
    }

    @Test
    public void testProcessOfferCreateMinimalOffer() {
        final Offer minimalOffer = OfferTestUtility.getMinimalOffer();
        minimalOffer.setRefNo("PL-2012-0001");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(minimalOffer);
        final OfferResponse processResponse = exchange.processOffer(token, offerRequest);

        // verify processResponse
        assertThat(processResponse.isOk(), is(true));

        // check if minimalOffer is persisted
        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, request);

        assertThat(fetchResponse.getOffers().isEmpty(), is(false));
        assertThat(fetchResponse.getOffers().contains(minimalOffer), is(true));
    }

    @Test
    public void testProcessOfferCreateFullOffer() {
        final Offer fullOffer = OfferTestUtility.getFullOffer();
        fullOffer.setRefNo("PL-2012-0002");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(fullOffer);
        final OfferResponse processResponse = exchange.processOffer(token, offerRequest);

        // verify processResponse
        assertThat(processResponse.isOk(), is(true));

        // check if fullOffer is persisted
        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, request);

        assertThat(fetchResponse.getOffers().isEmpty(), is(false));
        assertThat(fetchResponse.getOffers().contains(fullOffer), is(true));
    }

    @Test
    public void testDeleteOffer() {
        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setRefNo("PL-2012-0003");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);

        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse response = exchange.fetchOffers(token, request);
        assertThat(response.getOffers().isEmpty(), is(false));
        final int size = response.getOffers().size();

        final Offer offerToDelete = response.getOffers().get(0);

        final DeleteOfferRequest deleteRequest = new DeleteOfferRequest(offerToDelete.getRefNo());
        final OfferResponse deleteResponse = exchange.deleteOffer(token, deleteRequest);

        assertThat(deleteResponse.isOk(), is(true));
        final FetchOffersRequest fetchRequest = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, fetchRequest);
        assertThat(fetchResponse.getOffers().size(), is(size - 1));

        for (final Offer o : fetchResponse.getOffers()) {
            if (o.getRefNo().equals(offerToDelete.getRefNo())) {
                fail("offer is supposed to be deleted");
            }
        }
    }
}
