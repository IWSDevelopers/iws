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

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.exceptions.EntityIdentificationException;
import net.iaeste.iws.api.requests.OfferRequest;
import net.iaeste.iws.api.responses.OfferResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class ExchangeServiceTest {

    private EntityManager entityManager;
    private EntityTransaction transaction;
    private ExchangeService exchangeService;

    private OfferRequest emptyRequest;

    @Before
    public void before() {
        entityManager = mock(EntityManager.class);
        transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);
        exchangeService = new ExchangeService(entityManager);
    }

    @Before
    public void setUpRequests() {
        emptyRequest = new OfferRequest();
        List<Offer> invalidOfferList = new ArrayList<Offer>();
        invalidOfferList.add(new Offer(new EntityIdentificationException("id does not exist")));

        assert emptyRequest.getDeleteOfferIDs().isEmpty();
        assert emptyRequest.getUpdateOffers().isEmpty();

    }


    /**
     * Method: processOffers(final AuthenticationToken token, final OfferRequest request)
     */
    @Test
    public void testProcessOffersEmptyRequest() {
        // ToDo: pass AuthToken
        OfferResponse response = exchangeService.processOffers(null, emptyRequest);

        verify(transaction).begin();
        verify(transaction).commit();
        Assert.assertThat(response.getOffers().size(), is(0));
        Assert.assertThat(response.isOk(), is(true));
        Assert.assertThat(response.getError(), is(IWSErrors.SUCCESS));
        Assert.assertThat(response.getMessage(), is(IWSConstants.SUCCESS));
    }

    @Test
    @Ignore("TBD")
    public void testProcessOffersUpdateInexistentEntity() {
//        ArrayList<Offer> offers = new ArrayList<Offer>();
//
        OfferRequest request = new OfferRequest(new ArrayList<Offer>(), new ArrayList<Long>());
        OfferResponse response = exchangeService.processOffers(null, request);

        verify(transaction).begin();
        verify(transaction).rollback();
        Assert.assertThat(response.getOffers().size(), is(request.getUpdateOffers().size()));
        Assert.assertThat(response.isOk(), is(false));
    }

    /**
     * Method: fetchOffers(final AuthenticationToken token, final FetchOffersRequest request)
     * (...)
     */

} 
