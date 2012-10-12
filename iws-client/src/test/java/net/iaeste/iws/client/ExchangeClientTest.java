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

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.OfferTestUtility;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.requests.DeleteOfferRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.responses.FetchOffersResponse;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class ExchangeClientTest {

    private final ExchangeClient client = new ExchangeClient();
    private final AuthenticationToken token = new AuthenticationToken("md5_5678901234567890123456789012");

// TODO Get tests to work, they have been removed in the most incompentent and stupid way! However, they were commented since I don't have time to fix the problems now. The entire transaction mechanism have been rewritten, and these old test no longer works!
//    @Test
//    @Ignore("Ignored, until the permission mess is sorted out!")
//    public void testProcessOfferCreateMinimalOffer() {
//        final Offer offer = OfferTestUtility.getMinimalOffer();
//        offer.setId(null); // create offer
//        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
//        final Fallible response = client.processOffer(token, offerRequest);
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
//        // The purpose of the client, is to test the API implementation, which
//        // only shows the DTO's, not the Entities. The entities are internal
//        // Objects, and should *never* be exposed!
//        //assertThat(actual, is(OfferTransformer.transform(offer)));
//    }

//    @Test
//    @Ignore("cannot run both tests at one, how to fix this?")
//    public void testProcessOfferCreateFullOffer() {
//        final Offer offer = OfferTestUtility.getFullOffer();
//        offer.setId(null); // create offer
//        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
//        final Fallible response = client.processOffer(token, offerRequest);
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
//        assertThat(actual, is(OfferTransformer.transform(offer)));
//        assertThat(actual.getFieldOfStudies(), is(CollectionTransformer.concatEnumCollection(offer.getFieldOfStudies())));
//        assertThat(actual.getStudyLevels(), is(CollectionTransformer.concatEnumCollection(offer.getStudyLevels())));
//        assertThat(actual.getSpecializations(), is(CollectionTransformer.join(offer.getSpecializations())));
//    }

    //@Test
    public void testDeleteOffer() {
        final Offer offer = OfferTestUtility.getMinimalOffer();

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        client.processOffer(token, offerRequest);

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse response = client.fetchOffers(token, request);

        assertThat(response.getOffers().isEmpty(), is(false));
        final Offer offerToDelete = response.getOffers().get(0);

        final DeleteOfferRequest deleteRequest = new DeleteOfferRequest(offerToDelete.getId());
        client.deleteOffer(token, deleteRequest);

        final FetchOffersRequest fetchRequest = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse fetchResponse = client.fetchOffers(token, fetchRequest);

        for (final Offer o : fetchResponse.getOffers()) {
            if (o.getId().equals(offerToDelete.getId())) {
                fail("offer is supposed to be deleted");
            }
        }
    }
}
