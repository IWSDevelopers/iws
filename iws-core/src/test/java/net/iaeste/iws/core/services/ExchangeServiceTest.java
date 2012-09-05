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

import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.OfferTestUtility;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.DeleteOfferRequest;
import net.iaeste.iws.api.requests.FetchEmployersRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.OfferRequestTestUtility;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.responses.FetchEmployersResponse;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.core.transformers.OfferTransformer;
import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.entities.OfferEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @noinspection unchecked
 * @since 1.7
 */
public class ExchangeServiceTest {

    private final OfferDao dao = mock(OfferDao.class);
    private final ExchangeService client = new ExchangeService(dao);
    private final List<Offer> offers = OfferRequestTestUtility.getValidCreateOffersList();

    @Test
    public void testFetchOffersAll() {
        final List<OfferEntity> entities = new ArrayList<>(2);
        entities.add(null);
        entities.add(null);
        when(dao.findAll()).thenReturn(entities);

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);

        final FetchOffersResponse result = client.fetchOffers(null, request);

        assertThat(result.isOk(), is(true));
        assertThat(result.getOffers().size(), is(entities.size()));
    }

    @Test(expected = VerificationException.class)
    public void testProcessingOffersEmptyRequest() {
        final ProcessOfferRequest request = new ProcessOfferRequest();
        request.verify();
    }

    @Test
    public void testProcessingOffersCreateRequest() {
        final Offer offer = offers.get(0);
        when(dao.findOffer(offer.getRefNo())).thenReturn(null);

        final ProcessOfferRequest request = new ProcessOfferRequest(offer);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = client.processOffer(null, request);

        assertThat(result.isOk(), is(true));
        assertThat(result.getOffer(), is(new Offer()));
    }

    /**
     * Checks if processing is going to fail if the request is trying
     * to create Offer when there is an Offer with that id in the database.
     */
    @Test
    public void testProcessingOffersCreateRequestForExistingRefNo() {
        final Offer offer = offers.get(0);
        final Offer offerInDatabase = new Offer(offer);
        offerInDatabase.setId(543L);
        final OfferEntity existingEntity = OfferTransformer.transform(offerInDatabase);
        when(dao.findOffer(offer.getRefNo())).thenReturn(existingEntity);

        final ProcessOfferRequest request = new ProcessOfferRequest(offerInDatabase);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = client.processOffer(null, request);

        assertThat(result.isOk(), is(false));
        assertThat(result.getOffer(), is(offerInDatabase));
    }

// Either write your mocking correctly, or use the database for your test. This
// is a crap test
//    /**
//     * Correct update request with one offer.
//     */
//    @Test
//    public void testProcessingOffersUpdateRequest() {
//        final Offer offer = offers.get(0);
//        offer.setId(234L);
//        final OfferEntity existingEntity = OfferTransformer.transform(offer);
//        when(dao.findOffer(offer.getRefNo())).thenReturn(existingEntity);
//        when(dao.findOffer(offer.getId())).thenReturn(existingEntity);
//
//        final ProcessOfferRequest request = new ProcessOfferRequest(offer);
//        request.verify(); // make sure that request is valid
//
//        // Execute the test
//        final OfferResponse result = client.processOffer(null, request);
//
//        assertThat(result.isOk(), is(true));
//        assertThat(result.getOffer(), is(new Offer()));
//        verify(dao).persist(OfferTransformer.transform(offer));
//    }

    /**
     * Checks if processing is going to fail if {@code refNo} in request does not match the one in the database.
     */
    @Test
    public void testProcessingOffersUpdateRequestWithDifferentRefNos() {
        final Offer offer = offers.get(0);
        offer.setId(234L);
        final Offer offerInDatabase = new Offer(offer);
        offerInDatabase.setRefNo("CZ-2012-1004");
        final OfferEntity existingOfferEntity = OfferTransformer.transform(offerInDatabase);
        offer.setRefNo("PL-2012-1004");
        when(dao.findOffer(offer.getId())).thenReturn(existingOfferEntity);

        final ProcessOfferRequest request = new ProcessOfferRequest(offer);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = client.processOffer(null, request);

        assertThat(result.isOk(), is(false));
        assertThat(result.getOffer(), is(offer)); // one offer is invalid
    }

    /**
     * Checks if processing is going to fail if there is no {@code OfferEntity}
     * in the database for given {@code Offer.id} in the {@code OfferRequest}
     */
    @Test
    public void testProcessingOffersUpdateRequestForNonexistentId() {
        final Offer offer = offers.get(0);
        final OfferEntity existingOffer = OfferTransformer.transform(offer);
        when(dao.findOffer(offer.getRefNo())).thenReturn(existingOffer);
        when(dao.findOffer(offer.getId())).thenReturn(null);

        final ProcessOfferRequest request = new ProcessOfferRequest(offer);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = client.processOffer(null, request);

        assertThat(result.isOk(), is(false));
        assertThat(result.getOffer(), is(offer));
    }

    @Test
    public void testDeleteOffer() {
        final long offerId = 1L;
        final OfferEntity offerForDeletion = new OfferEntity();
        offerForDeletion.setId(offerId);

        when(dao.findOffer(offerId)).thenReturn(offerForDeletion);
        final DeleteOfferRequest request = new DeleteOfferRequest(offerId);
        request.verify(); // make sure that request is valid

        client.deleteOffer(null, request);

        verify(dao).delete(offerId);
    }

    @Test(expected = IWSException.class)
    public void testDeleteNonexistentOffer() {
        final long offerId = 1L;
        when(dao.findOffer(offerId)).thenReturn(null);

        final DeleteOfferRequest request = new DeleteOfferRequest(offerId);
        request.verify(); // make sure that request is valid

        client.deleteOffer(null, request);
    }

    @Test
    public void testFetchOffersByLikeEmployerName() {
        final List<OfferEntity> entities = new ArrayList<>(2);
        entities.add(null);
        entities.add(null);

        when(dao.findOffersByLikeEmployerName(OfferTestUtility.EMPLOYER_NAME)).thenReturn(entities);

        final FetchEmployersRequest request = new FetchEmployersRequest(FetchType.OWNED, OfferTestUtility.EMPLOYER_NAME);
        request.verify(); // make sure that request is valid

        final FetchEmployersResponse result = client.fetchEmployers(null, request);

        assertThat(result.getEmployers().size(), is(entities.size()));
    }

    @Test(expected = VerificationException.class)
    public void testFetchOffersByLikeEmployerName_2() {
        final List<OfferEntity> entities = new ArrayList<>(2);
        entities.add(null);
        entities.add(null);

        when(dao.findOffersByLikeEmployerName(OfferTestUtility.EMPLOYER_NAME)).thenReturn(entities);

        final FetchEmployersRequest request = new FetchEmployersRequest(FetchType.OWNED, "");
        request.verify(); // make sure that request is valid

        final FetchEmployersResponse result = client.fetchEmployers(null, request);

        assertThat(result.getEmployers().size(), is(entities.size()));
    }

}
