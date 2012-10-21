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

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import org.junit.BeforeClass;

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

//    @Test
//    public void testProcessOfferCreateMinimalOffer() {
//        final Offer offer = OfferTestUtility.getMinimalOffer();
//        offer.setId(null); // create offer
//        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
//        final Fallible response = exchange.processOffer(token, offerRequest);
//        assertThat(response.isOk(), is(true));
//
//        final EntityManager em = EntityManagerProvider.getInstance().getEntityManager();
//        final OfferDao dao = new OfferJpaDao(em);
//        final List<OfferEntity> offers = dao.findAll();
//
//        assertThat(offers.size(), is(1));
//        final OfferEntity actual = offers.get(0);
//        assertThat(actual.getId(), is(notNullValue()));
//        actual.setId(null);
//        assertThat(actual.getFieldOfStudies(), is(CollectionTransformer.concatEnumCollection(offer.getFieldOfStudies())));
//        assertThat(actual.getStudyLevels(), is(CollectionTransformer.concatEnumCollection(offer.getStudyLevels())));
//        assertThat(actual.getSpecializations(), is(CollectionTransformer.join(offer.getSpecializations())));
//        // The purpose of the exchange, is to test the API implementation, which
//        // only shows the DTO's, not the Entities. The entities are internal
//        // Objects, and should *never* be exposed!
//        //assertThat(actual, is(OfferTransformer.transform(offer)));
//    }
//
//    @Test
//    public void testProcessOfferCreateFullOffer() {
//        final Offer offer = OfferTestUtility.getFullOffer();
//        offer.setId(null); // create offer
//        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
//        final Fallible response = exchange.processOffer(token, offerRequest);
//        assertThat(response.isOk(), is(true));
//
//        final EntityManager em = EntityManagerProvider.getInstance().getEntityManager();
//        final OfferDao dao = new OfferJpaDao(em);
//        final List<OfferEntity> offers = dao.findAll();
//
//        assertThat(offers.size(), is(1));
//        final OfferEntity actual = offers.get(0);
//        assertThat(actual.getId(), is(notNullValue()));
//        assertThat(actual.getRefNo(), is(offer.getRefNo()));
//        actual.setId(null);
//        assertThat(OfferTransformer.transform(actual), is(offer));
//        assertThat(actual.getFieldOfStudies(), is(CollectionTransformer.concatEnumCollection(offer.getFieldOfStudies())));
//        assertThat(actual.getStudyLevels(), is(CollectionTransformer.concatEnumCollection(offer.getStudyLevels())));
//        assertThat(actual.getSpecializations(), is(CollectionTransformer.join(offer.getSpecializations())));
//    }

// FIXME: got org.springframework.beans.factory.NoSuchBeanDefinitionException
//    @Test
//    public void testDeleteOffer() {
//        final Offer offer = OfferTestUtility.getMinimalOffer();
//
//        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
//        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);
//
//        assertThat(saveResponse.isOk(), is(true));
//
//        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
//        final FetchOffersResponse response = exchange.fetchOffers(token, request);
//        assertThat(response.getOffers().isEmpty(), is(false));
//        final int size = response.getOffers().size();
//
//        final Offer offerToDelete = response.getOffers().get(0);
//
//        final DeleteOfferRequest deleteRequest = new DeleteOfferRequest(offerToDelete.getRefNo());
//        final OfferResponse deleteResponse = exchange.deleteOffer(token, deleteRequest);
//
//        assertThat(deleteResponse.isOk(), is(true));
//        final FetchOffersRequest fetchRequest = new FetchOffersRequest(FetchType.ALL);
//        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, fetchRequest);
//        assertThat(fetchResponse.getOffers().size(), is(size - 1));
//
//        for (final Offer o : fetchResponse.getOffers()) {
//            if (o.getRefNo().equals(offerToDelete.getRefNo())) {
//                fail("offer is supposed to be deleted");
//            }
//        }
//    }
}
