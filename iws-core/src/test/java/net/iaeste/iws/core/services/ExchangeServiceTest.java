/*
* =============================================================================
* Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
* -----------------------------------------------------------------------------
* Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.ServiceTest
* -----------------------------------------------------------------------------
* This software is provided by the members of the IAESTE Internet Development
* Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
* redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
*
* This software is provided "as is"; the IDT or individuals within the IDT
* cannot be held legally responsible for any problems the software may cause.
* =============================================================================
*/
package net.iaeste.iws.core.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.OfferRequest;
import net.iaeste.iws.api.requests.OfferRequestTestUtility;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.core.transformers.OfferTransformer;
import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.entities.OfferEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @noinspection unchecked
 * @since 1.7
 */
public class ExchangeServiceTest {

    private final OfferDao dao = mock(OfferDao.class);
    private final ExchangeService cut = new ExchangeService(dao);

    @Test
    public void testFetchOffers() {
        // First, the test setup
        final List<OfferEntity> entities = new ArrayList<>(2);
        entities.add(null);
        entities.add(null);
        when(dao.findAll()).thenReturn(entities);
        final FetchOffersRequest request = new FetchOffersRequest();

        // Execute the test
        final OfferResponse result = cut.fetchOffers(null, request);

        assertThat(result.isOk(), is(true));
        assertThat(result.getOffers().size(), is(2));
    }

    @Test
    public void testProcessingOffersEmptyRequest() {
        // First, the test setup
        final OfferRequest request = OfferRequestTestUtility.getEmptyRequest();
        request.verify();

        // Execute the test
        final OfferResponse result = cut.processOffers(null, request);

        assertThat(result.isOk(), is(true));
        assertThat(result.getOffers().size(), is(0));
    }

    @Test
    public void testProcessingOffersCreateRequest() {
        // First, the test setup
        final List<Offer> offersList = OfferRequestTestUtility.getValidCreateOffersList().subList(0, 1);
        final List<Long> emptyIdsList = OfferRequestTestUtility.getEmptyIdsList();

        when(dao.findOffer(offersList.get(0).getRefNo())).thenReturn(null);

        final OfferRequest request = new OfferRequest(offersList, emptyIdsList);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = cut.processOffers(null, request);

        assertThat(result.isOk(), is(true));
        assertThat(result.getOffers().size(), is(0));
    }

    /**
     * Checks if processing is going to fail if the request is trying
     * to create Offer when there is an Offer with that id in the database.
     */
    @Test
    public void testProcessingOffersCreateRequestForExistingRefNo() {
        // First, the test setup
        final List<Offer> offersList = OfferRequestTestUtility.getValidCreateOffersList().subList(0, 1);
        final List<Long> emptyIdsList = OfferRequestTestUtility.getEmptyIdsList();

        final Offer offer = offersList.get(0);
        final OfferEntity existingEntity = OfferTransformer.transform(offer);
        when(dao.findOffer(offer.getRefNo())).thenReturn(existingEntity);

        final OfferRequest request = new OfferRequest(offersList, emptyIdsList);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = cut.processOffers(null, request);

        assertThat(result.isOk(), is(true));
        assertThat(result.getOffers().size(), is(1));
        assertThat(result.getOffers().get(0).isOk(), is(false));
    }

    /**
     * Correct update request with one offer.
     */
    @Test
    public void testProcessingOffersUpdateRequest() {
        // First, the test setup
        final List<Offer> offersList = OfferRequestTestUtility.getValidUpdateOffersList().subList(0, 1);
        final List<Long> emptyIdsList = OfferRequestTestUtility.getEmptyIdsList();

        final Offer offer = offersList.get(0);
        final OfferEntity existingEntity = OfferTransformer.transform(offer);
        when(dao.findOffer(offer.getRefNo())).thenReturn(existingEntity);
        when(dao.findOffer(offer.getId())).thenReturn(existingEntity);

        final OfferRequest request = new OfferRequest(offersList, emptyIdsList);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = cut.processOffers(null, request);

        assertThat(result.isOk(), is(true));
        assertThat(result.getOffers().size(), is(0));
    }

    /**
     * Checks if processing is going to fail if {@code refNo} in request does not match the one in the database.
     */
    @Test
    public void testProcessingOffersUpdateRequestWithDifferentRefNos() {
        // First, the test setup
        final List<Offer> offersList = OfferRequestTestUtility.getValidUpdateOffersList().subList(0, 1);
        final List<Long> emptyIdsList = OfferRequestTestUtility.getEmptyIdsList();

        final Offer offer = offersList.get(0);
        final OfferEntity existingOffer = OfferTransformer.transform(offer);
        offer.setRefNo("PL-2012-1004");
        existingOffer.setRefNo("CZ-2012-1004");
        when(dao.findOffer(offer.getId())).thenReturn(existingOffer);

        final OfferRequest request = new OfferRequest(offersList, emptyIdsList);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = cut.processOffers(null, request);

        assertThat(result.isOk(), is(true));
        assertThat(result.getOffers().size(), is(1)); // one offer is invalid
        assertThat(result.getOffers().get(0).isOk(), is(false));
    }

    /**
     * Checks if processing is going to fail if there is no {@code OfferEntity}
     * in the database for given {@code Offer.id} in the {@code OfferRequest}
     */
    @Test
    public void testProcessingOffersUpdateRequestForNonexistentId() {
        // First, the test setup
        final List<Offer> validUpdateOffersList = OfferRequestTestUtility.getValidUpdateOffersList().subList(0, 1);
        final List<Long> emptyIdsList = OfferRequestTestUtility.getEmptyIdsList();

        final Offer offer = validUpdateOffersList.get(0);
        final OfferEntity existingOffer = OfferTransformer.transform(offer);
        when(dao.findOffer(offer.getRefNo())).thenReturn(existingOffer);
        when(dao.findOffer(offer.getId())).thenReturn(null);

        final OfferRequest request = new OfferRequest(validUpdateOffersList, emptyIdsList);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = cut.processOffers(null, request);

        assertThat(result.isOk(), is(true));
        assertThat(result.getOffers().size(), is(1));
        assertThat(result.getOffers().get(0).isOk(), is(false));
    }

    /**
     * Checks if Response is going to have 3 problems if 2 offers are correct and 2 offers are invalid.
     */
    @Test
    public void testProcessingOffersUpdateCountingErrors() {
        // First, the test setup
        final List<Offer> offersList = OfferRequestTestUtility.getValidUpdateOffersList().subList(0, 5);
        final List<Long> emptyIdsList = OfferRequestTestUtility.getEmptyIdsList();

        final Offer offer0 = offersList.get(0);
        final Offer offer1 = offersList.get(1);
        final Offer offer2 = offersList.get(2);
        final Offer offer3 = offersList.get(3);
        final Offer offer4 = offersList.get(4);

        // 0: update is ok
        when(dao.findOffer(offer0.getId())).thenReturn(OfferTransformer.transform(offer0));
        // 1: create is ok
        offer1.setId(null);
        when(dao.findOffer(offer1.getId())).thenReturn(OfferTransformer.transform(offer1));

        // make 3 problems
        // 2: nonexistent id
        when(dao.findOffer(offer2.getRefNo())).thenReturn(OfferTransformer.transform(offer2));
        when(dao.findOffer(offer2.getId())).thenReturn(null);
        // 3: different refNo
        final OfferEntity existingOffer = OfferTransformer.transform(offer3);
        offer3.setRefNo("PL-2012-1004");
        existingOffer.setRefNo("CZ-2012-1004");
        when(dao.findOffer(offer3.getId())).thenReturn(existingOffer);
        // 4: create Offer with existing refNo in database
        offer4.setId(null);
        when(dao.findOffer(offer4.getRefNo())).thenReturn(OfferTransformer.transform(offer4));

        final OfferRequest request = new OfferRequest(offersList, emptyIdsList);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = cut.processOffers(null, request);

        assertThat(result.isOk(), is(true));
        assertThat(result.getOffers().size(), is(3)); // expecting 3 problems
    }
}
