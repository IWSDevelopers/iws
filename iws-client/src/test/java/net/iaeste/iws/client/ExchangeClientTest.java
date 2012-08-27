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
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.OfferTestUtility;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.client.spring.EntityManagerProvider;
import net.iaeste.iws.core.transformers.ListTransformer;
import net.iaeste.iws.core.transformers.OfferTransformer;
import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.entities.OfferEntity;
import net.iaeste.iws.persistence.jpa.OfferJpaDao;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class ExchangeClientTest {

    private final ExchangeClient client = new ExchangeClient();
    private final AuthenticationToken token = new AuthenticationToken("md5_5678901234567890123456789012");

    @Test
    public void testProcessOfferCreateMinimalOffer() {
        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setId(null); // create offer
        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final Fallible response = client.processOffer(token, offerRequest);
        Assert.assertThat(response.isOk(), is(true));

        final EntityManager em = EntityManagerProvider.getInstance().getEntityManager();
        final OfferDao dao = new OfferJpaDao(em);
        final List<OfferEntity> offers = dao.findAll();

        assertThat(offers.size(), is(1));
        final OfferEntity actual = offers.get(0);
        assertThat(actual.getId(), is(notNullValue()));
        actual.setId(null);
        assertThat(actual.getFieldOfStudies(), is(ListTransformer.concatEnumList(offer.getFieldOfStudies())));
        assertThat(actual.getStudyLevels(), is(ListTransformer.concatEnumList(offer.getStudyLevels())));
        assertThat(actual.getSpecializations(), is(ListTransformer.concatStringList(offer.getSpecializations())));
        assertThat(actual, is(OfferTransformer.transform(offer)));
    }

    @Test
    @Ignore("cannot run both tests at one, how to fix this?")
    public void testProcessOfferCreateFullOffer() {
        final Offer offer = OfferTestUtility.getFullOffer();
        offer.setId(null); // create offer
        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final Fallible response = client.processOffer(token, offerRequest);
        Assert.assertThat(response.isOk(), is(true));

        final EntityManager em = EntityManagerProvider.getInstance().getEntityManager();
        final OfferDao dao = new OfferJpaDao(em);
        final List<OfferEntity> offers = dao.findAll();

        assertThat(offers.size(), is(1));
        final OfferEntity actual = offers.get(0);
        assertThat(actual.getId(), is(notNullValue()));
        actual.setId(null);
        assertThat(actual, is(OfferTransformer.transform(offer)));
        assertThat(actual.getFieldOfStudies(), is(ListTransformer.concatEnumList(offer.getFieldOfStudies())));
        assertThat(actual.getStudyLevels(), is(ListTransformer.concatEnumList(offer.getStudyLevels())));
        assertThat(actual.getSpecializations(), is(ListTransformer.concatStringList(offer.getSpecializations())));
    }
}
