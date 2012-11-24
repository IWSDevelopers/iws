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

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.OfferTestUtility;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.DeleteOfferRequest;
import net.iaeste.iws.api.requests.FetchEmployerInformationRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.OfferRequestTestUtility;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.responses.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.core.transformers.OfferTransformer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.OfferEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.notification.Notifications;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author  Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection unchecked, CastToConcreteClass
 */
public class ExchangeServiceTest {

    private final OfferDao dao = mock(OfferDao.class);
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
        final CountryEntity country = new CountryEntity();
        country.setCountryId("GB");
        final GroupEntity group = new GroupEntity();
        group.setCountry(country);
        final AuthenticationToken token = new AuthenticationToken();
        final Authentication authentication = new Authentication(token, null, group);
        final Offer offer = offers.get(0);
        final OfferEntity entityToPersist = OfferTransformer.transform(offer);

        when(dao.findOffer(offer.getRefNo())).thenReturn(null);

        final ProcessOfferRequest request = new ProcessOfferRequest(offer);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = client.manageOffer(authentication, request);

        // expect correct response
        assertThat(result.isOk(), is(true));
        assertThat(result.getOffer(), is(new Offer()));

        // verify that persist method was invoked
        verify(dao, times(1)).persist(any(Authentication.class), argThat(new OfferEntityMatcher(entityToPersist)));
    }

    /** Correct update request with one offer. */
    @Test
    public void testProcessingOffersUpdateRequest() {
        final Offer offer = offers.get(0);
        // offer which currently exist in db
        offer.setCanteen(true);
        final OfferEntity existingEntity = OfferTransformer.transform(offer);
        // offer which is to be written
        offer.setCanteen(false);
        final OfferEntity entityToPersist = OfferTransformer.transform(offer);

        when(dao.findOffer(offer.getRefNo())).thenReturn(existingEntity);

        final ProcessOfferRequest request = new ProcessOfferRequest(offer);
        request.verify(); // make sure that request is valid

        // Execute the test
        final OfferResponse result = client.manageOffer(auth, request);

        // expect correct response
        assertThat(result.isOk(), is(true));
        assertThat(result.getOffer(), is(new Offer()));

        // verify that persist method was invoked
        verify(dao, times(1)).persist(any(Authentication.class), argThat(new OfferEntityMatcher(existingEntity)),
                argThat(new OfferEntityMatcher(entityToPersist)
                ));
    }

    @Test
    public void testDeleteOffer() {
        final long offerId = 1L;
        final String offerRefNo = "AT-2012-0001";
        final OfferEntity offerForDeletion = new OfferEntity();
        offerForDeletion.setId(offerId);
        offerForDeletion.setRefNo(offerRefNo);

        when(dao.findOffer(offerRefNo)).thenReturn(offerForDeletion);
        final DeleteOfferRequest request = new DeleteOfferRequest(offerRefNo);
        request.verify(); // make sure that request is valid

        client.deleteOffer(null, request);

        verify(dao).delete(offerId);
    }

    @Test(expected = IWSException.class)
    public void testDeleteNonexistentOffer() {
        final String offerRefNo = "AT-2012-0001";
        when(dao.findOffer(offerRefNo)).thenReturn(null);

        final DeleteOfferRequest request = new DeleteOfferRequest(offerRefNo);
        request.verify(); // make sure that request is valid

        client.deleteOffer(null, request);
    }

    @Test
    public void testFetchOffersByLikeEmployerName() {
        final List<OfferEntity> entities = new ArrayList<>(2);
        entities.add(null);
        entities.add(null);

        when(dao.findOffersByLikeEmployerName(OfferTestUtility.EMPLOYER_NAME, auth.getGroup().getId())).thenReturn(entities);

        final FetchEmployerInformationRequest request = new FetchEmployerInformationRequest(OfferTestUtility.EMPLOYER_NAME);
        request.verify(); // make sure that request is valid

        final FetchEmployerInformationResponse result = client.fetchEmployers(auth, request);

        assertThat(result.getEmployers().size(), is(entities.size()));
    }

    @Test(expected = VerificationException.class)
    public void testFetchOffersByLikeEmployerName_2() {
        final List<OfferEntity> entities = new ArrayList<>(2);
        entities.add(null);
        entities.add(null);

        when(dao.findOffersByLikeEmployerName(OfferTestUtility.EMPLOYER_NAME, auth.getGroup().getId())).thenReturn(entities);

        final FetchEmployerInformationRequest request = new FetchEmployerInformationRequest("");
        request.verify(); // make sure that request is valid

        final FetchEmployerInformationResponse result = client.fetchEmployers(auth, request);

        assertThat(result.getEmployers().size(), is(entities.size()));
    }

    private static class OfferEntityMatcher extends BaseMatcher<OfferEntity> {
        private final OfferEntity entity;

        OfferEntityMatcher(final OfferEntity entity) {
            this.entity = entity;
        }

        /**
         * Check if created/updated offers are equal.
         * Except for PK and UK only Offer#getCanteen() is checked as it's the only fields which can differ for the example data.
         */
        @Override
        public boolean matches(final Object o) {
            boolean result = false;

            if (o instanceof OfferEntity) {
                final OfferEntity e = (OfferEntity) o;
                if (e.getId() == null ? entity.getId() == null : e.getId().equals(entity.getId())) {
                    if (e.getRefNo().equals(entity.getRefNo())) {
                        result = e.getCanteen() == null ? entity.getCanteen() == null : e.getCanteen().equals(entity.getCanteen());
                    }
                }
            }
            return result;
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText(String.format("Offer{id=%s}", entity.getId()));
        }
    }
}
