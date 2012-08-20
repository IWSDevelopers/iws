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

import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.entities.OfferEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 * @noinspection unchecked
 */
public class ExchangeServiceTest {

    private final OfferDao dao = mock(OfferDao.class);
    private final ExchangeService cut = new ExchangeService(dao);
//    private ExchangeService exchangeService;
//
//    private OfferRequest emptyRequest;

//    @Before
//    public void before() {
//        dao = mock(OfferDao.class);
//        entityManager = mock(EntityManager.class);
//        transaction = mock(EntityTransaction.class);
//        when(entityManager.getTransaction()).thenReturn(transaction);
//        exchangeService = new ExchangeService(entityManager);
//    }

//    @Before
//    public void setUpRequests() {
//        emptyRequest = new OfferRequest();
//        List<Offer> invalidOfferList = new ArrayList<Offer>();
//        invalidOfferList.add(new Offer(new EntityIdentificationException("id does not exist")));
//
////        assert emptyRequest.getDeleteOfferIDs().isEmpty();
//        assert emptyRequest.getUpdateOffers().isEmpty();
//
//    }

    @Test
    public void testProcessingOffers() {
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

//    /**
//     * Method: processOffers(final AuthenticationToken token, final OfferRequest request)
//     */
//    @Test
//    public void testProcessOffersEmptyRequest() {
//        // ToDo: pass AuthToken
//        OfferResponse response = exchangeService.processOffers(null, emptyRequest);
//
//        Assert.assertThat(response.getOffers().size(), is(0));
//        Assert.assertThat(response.isOk(), is(true));
//        Assert.assertThat(response.getError(), is(IWSErrors.SUCCESS));
//        Assert.assertThat(response.getMessage(), is(IWSConstants.SUCCESS));
//    }
//
//    @Test
//    @Ignore("TBD")
//    public void testProcessOffersUpdateInexistentEntity() {
////        ArrayList<Offer> offers = new ArrayList<Offer>();
////
//        OfferRequest request = new OfferRequest(new ArrayList<Offer>(), new ArrayList<Long>());
//        OfferResponse response = exchangeService.processOffers(null, request);
//
//        Assert.assertThat(response.getOffers().size(), is(request.getUpdateOffers().size()));
//        Assert.assertThat(response.isOk(), is(false));
//    }
}
