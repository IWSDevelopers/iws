/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.ExchangeServiceTest
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

import static org.mockito.Mockito.mock;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.OfferRequestTestUtility;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @noinspection unchecked, CastToConcreteClass
 * @since 1.7
 */
@Ignore
public class ExchangeServiceTest {

    private final ExchangeDao dao = mock(ExchangeDao.class);
    private final Notifications notifications = mock(Notifications.class);
    private final ExchangeService client = new ExchangeService(dao, notifications);
    private final List<Offer> offers = OfferRequestTestUtility.getValidOffersList();
    private Authentication auth = null;

    @Before
    public void init() {
        final AuthenticationToken token = new AuthenticationToken();
        final UserEntity user = new UserEntity();
        final GroupEntity group = new GroupEntity();
        group.setId(1L);

        auth = new Authentication(token, user, group);
    }

//    @Test
//    public void testFetchOffersAll() {
//        final List<OfferEntity> entities = new ArrayList<>(2);
//        entities.add(null);
//        entities.add(null);
//        when(dao.findAllOffers(auth)).thenReturn(entities);
//
//        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
//
//        final FetchOffersResponse result = client.fetchOffers(auth, request);
//
//        assertThat(result.isOk(), is(true));
//        assertThat(result.getOffers().size(), is(entities.size()));
//    }

    @Test(expected = VerificationException.class)
    public void testProcessingOffersEmptyRequest() {
        final ProcessOfferRequest request = new ProcessOfferRequest();
        request.verify();
    }

//    @Test
//    @Ignore("Ignored 2013-06-02 by Kim - Reason: The test is using mocking, and thus is only verifying that the mocks are correct, not the logic!")
//    public void testProcessingOffersCreateRequest() {
//        final CountryEntity country = new CountryEntity();
//        country.setCountryCode("GB");
//        final GroupEntity group = new GroupEntity();
//        group.setCountry(country);
//        final AuthenticationToken token = new AuthenticationToken();
//        final Authentication authentication = new Authentication(token, null, group);
//        final Offer offer = offers.get(0);
//        final OfferEntity entityToPersist = ExchangeTransformer.transform(offer);
//
//        final OfferEntity entityWithId = ExchangeTransformer.transform(ExchangeTransformer.transform(entityToPersist));
//        final String id = UUID.randomUUID().toString();
//        entityWithId.setExternalId(id);
//        when(dao.findOfferByRefNo(authentication, offer.getRefNo())).thenReturn(null, entityWithId);
//
//        final ProcessOfferRequest request = new ProcessOfferRequest(offer);
//        request.verify(); // make sure that request is valid
//
//        // Execute the test
//        final OfferResponse result = client.processOffer(authentication, request);
//
//        // expect correct response
//        assertThat(result.isOk(), is(true));
//        assertThat(result.getOffer(), is(notNullValue()));
//        assertThat(result.getOffer().getOfferId(), is(notNullValue()));
//        assertThat(result.getOffer().getOfferId(), is(id));
//        assertThat(result.getOffer().getRefNo(), is(offer.getRefNo()));
//        assertThat(result.getOffer(), is(ExchangeTransformer.transform(entityWithId)));
//
//        // verify that persist method was invoked
//        verify(dao, times(1)).persist(any(Authentication.class), argThat(new OfferEntityMatcher(entityToPersist)));
//    }

//    /** Correct update request with one offer. */
//    @Test
//    public void testProcessingOffersUpdateRequest() {
//        final Offer offer = offers.get(0);
//        final String id = UUID.randomUUID().toString();
//        offer.setOfferId(id);
//        // offer which currently exist in db
//        offer.setCanteen(true);
//        final OfferEntity existingEntity = ExchangeTransformer.transform(offer);
//        // offer which is to be written
//        offer.setCanteen(false);
//        final OfferEntity entityToPersist = ExchangeTransformer.transform(offer);
//
//        when(dao.findOfferByRefNo(auth, offer.getOfferId())).thenReturn(existingEntity);
//        when(dao.findOfferByRefNo(auth, offer.getRefNo())).thenReturn(existingEntity);
//        when(dao.findOfferByRefNo(auth, offer.getOfferId(), offer.getRefNo())).thenReturn(existingEntity);
//
//        final ProcessOfferRequest request = new ProcessOfferRequest(offer);
//        request.verify(); // make sure that request is valid
//
//        // Execute the test
//        final OfferResponse result = client.processOffer(auth, request);
//
//        // expect correct response
//        assertThat(result.isOk(), is(true));
//        assertThat(result.getOffer(), is(notNullValue()));
//        assertThat(result.getOffer().getOfferId(), is(id));
//        assertThat(result.getOffer().getRefNo(), is(offer.getRefNo()));
//
//        // verify that persist method was invoked
//        verify(dao, times(1)).persist(any(Authentication.class), argThat(new OfferEntityMatcher(existingEntity)),
//                argThat(new OfferEntityMatcher(entityToPersist)
//                ));
//    }

//    @Test
//    public void testDeleteOffer() {
//        final long offerId = 1L;
//        final String offerRefNo = "AT-2012-0001";
//        final OfferEntity offerForDeletion = new OfferEntity();
//        offerForDeletion.setId(offerId);
//        offerForDeletion.setRefNo(offerRefNo);
//
//        when(dao.findOfferByRefNo(auth, offerRefNo)).thenReturn(offerForDeletion);
//        final DeleteOfferRequest request = new DeleteOfferRequest(offerRefNo);
//        request.verify(); // make sure that request is valid
//
//        client.deleteOffer(auth, request);
//
//        verify(dao).delete(auth, offerId);
//    }

//    @Test(expected = IWSException.class)
//    public void testDeleteNonexistentOffer() {
//        final String offerRefNo = "AT-2012-0001";
//        when(dao.findOfferByRefNo(auth, offerRefNo)).thenReturn(null);
//
//        final DeleteOfferRequest request = new DeleteOfferRequest(offerRefNo);
//        request.verify(); // make sure that request is valid
//
//        client.deleteOffer(null, request);
//    }

//    @Test
//    public void testFetchOffersByLikeEmployerName() {
//        final List<OfferEntity> entities = new ArrayList<>(2);
//        entities.add(null);
//        entities.add(null);
//
//        when(dao.findOffersByLikeEmployerName(auth, OfferTestUtility.EMPLOYER_NAME)).thenReturn(entities);
//
//        final FetchEmployerInformationRequest request = new FetchEmployerInformationRequest(OfferTestUtility.EMPLOYER_NAME);
//        request.verify(); // make sure that request is valid
//
//        final FetchEmployerInformationResponse result = client.fetchEmployers(auth, request);
//
//        assertThat(result.getEmployers().size(), is(entities.size()));
//    }

//    @Test(expected = VerificationException.class)
//    public void testFetchOffersByLikeEmployerName_2() {
//        final List<OfferEntity> entities = new ArrayList<>(2);
//        entities.add(null);
//        entities.add(null);
//
//        when(dao.findOffersByLikeEmployerName(auth, OfferTestUtility.EMPLOYER_NAME)).thenReturn(entities);
//
//        final FetchEmployerInformationRequest request = new FetchEmployerInformationRequest("");
//        request.verify(); // make sure that request is valid
//
//        final FetchEmployerInformationResponse result = client.fetchEmployers(auth, request);
//
//        assertThat(result.getEmployers().size(), is(entities.size()));
//    }

//    private static class OfferEntityMatcher extends BaseMatcher<OfferEntity> {
//        private final OfferEntity entity;
//
//        private OfferEntityMatcher(final OfferEntity entity) {
//            this.entity = entity;
//        }
//
//        /**
//         * Check if created/updated offers are equal.
//         * Except for PK and UK only Offer#getCanteen() is checked as it's the only fields which can differ for the example data.
//         */
//        @Override
//        public boolean matches(final Object item) {
//            boolean result = false;
//
//            if (item instanceof OfferEntity) {
//                final OfferEntity e = (OfferEntity) item;
//                if (e.getOfferId() == null ? entity.getOfferId() == null : e.getOfferId().equals(entity.getOfferId())) {
//                    if (e.getRefNo().equals(entity.getRefNo())) {
//                        result = e.getCanteen() == null ? entity.getCanteen() == null : e.getCanteen().equals(entity.getCanteen());
//                    }
//                }
//            }
//            return result;
//        }
//
//        @Override
//        public void describeTo(final Description description) {
//            description.appendText(String.format("Offer{id=%s}", entity.getOfferId()));
//        }
//    }
}
