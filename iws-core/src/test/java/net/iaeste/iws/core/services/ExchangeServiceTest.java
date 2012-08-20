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
        final List<Offer> validCreateOffersList = OfferRequestTestUtility.getValidCreateOffersList();
        final List<Long> emptyIdsList = OfferRequestTestUtility.getEmptyIdsList();
        for (final Offer offer : validCreateOffersList) {
            when(dao.findOffer(offer.getRefNo())).thenReturn(null);
        }

        final OfferRequest request = new OfferRequest(validCreateOffersList, emptyIdsList);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = cut.processOffers(null, request);

        assertThat(result.isOk(), is(true));
        assertThat(result.getOffers().size(), is(0));
    }

    @Test
    public void testProcessingOffersCreateRequestForExistingRefNo() {
        // First, the test setup
        final List<Offer> validCreateOffersList = OfferRequestTestUtility.getValidCreateOffersList();
        final List<Long> emptyIdsList = OfferRequestTestUtility.getEmptyIdsList();
        for (final Offer offer : validCreateOffersList) {
            final OfferEntity existingEntity = OfferTransformer.transform(offer);
            when(dao.findOffer(offer.getRefNo())).thenReturn(existingEntity);
        }

        final OfferRequest request = new OfferRequest(validCreateOffersList, emptyIdsList);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = cut.processOffers(null, request);

        assertThat(result.isOk(), is(true));
        assertThat(result.getOffers().size(), is(validCreateOffersList.size()));
        for (final Offer offer : result.getOffers()) {
            assertThat(offer.isOk(), is(false));
        }
    }

    @Test
    public void testProcessingOffersUpdateRequest() {
        // First, the test setup
        final List<Offer> validUpdateOffersList = OfferRequestTestUtility.getValidUpdateOffersList();
        final List<Long> emptyIdsList = OfferRequestTestUtility.getEmptyIdsList();

        for (final Offer offer : validUpdateOffersList) {
            final OfferEntity existingEntity = OfferTransformer.transform(offer);
            when(dao.findOffer(offer.getRefNo())).thenReturn(existingEntity);
            when(dao.findOffer(offer.getId())).thenReturn(existingEntity);
        }

        final OfferRequest request = new OfferRequest(validUpdateOffersList, emptyIdsList);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = cut.processOffers(null, request);

        assertThat(result.isOk(), is(true));
        assertThat(result.getOffers().size(), is(0));
    }

    @Test
    public void testProcessingOffersUpdateRequestWithDifferentRefNos() {
        // First, the test setup
        final List<Offer> validUpdateOffersList = OfferRequestTestUtility.getValidUpdateOffersList();
        final List<Long> emptyIdsList = OfferRequestTestUtility.getEmptyIdsList();

        for (final Offer offer : validUpdateOffersList) {
            final OfferEntity existingEntity = OfferTransformer.transform(offer);
            // change the refNo
            final String refNo = existingEntity.getRefNo();
            final Integer newNumberInRefNo = new Integer(refNo.substring(refNo.length() - 1 - 4, refNo.length() - 1)) + 5;
            existingEntity.setRefNo(refNo.substring(0, refNo.length() - 2) + newNumberInRefNo.toString());

            when(dao.findOffer(offer.getId())).thenReturn(existingEntity);
        }

        final OfferRequest request = new OfferRequest(validUpdateOffersList, emptyIdsList);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = cut.processOffers(null, request);

        assertThat(result.isOk(), is(true));
        assertThat(result.getOffers().size(), is(validUpdateOffersList.size()));
        for (final Offer offer : result.getOffers()) {
            assertThat(offer.isOk(), is(false));
        }
    }

    @Test
    public void testProcessingOffersUpdateRequestForNonexistentId() {
        // First, the test setup
        final List<Offer> validUpdateOffersList = OfferRequestTestUtility.getValidUpdateOffersList();
        final List<Long> emptyIdsList = OfferRequestTestUtility.getEmptyIdsList();

        for (final Offer offer : validUpdateOffersList) {
            final OfferEntity existingEntity = OfferTransformer.transform(offer);
            when(dao.findOffer(offer.getRefNo())).thenReturn(existingEntity);
            when(dao.findOffer(offer.getId())).thenReturn(null);
        }

        final OfferRequest request = new OfferRequest(validUpdateOffersList, emptyIdsList);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = cut.processOffers(null, request);

        assertThat(result.isOk(), is(true));
        assertThat(result.getOffers().size(), is(validUpdateOffersList.size()));
    }
}
