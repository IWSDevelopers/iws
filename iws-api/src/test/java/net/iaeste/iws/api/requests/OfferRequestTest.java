/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.OfferRequestTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.api.requests;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.exceptions.EntityIdentificationException;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.utils.Copier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class OfferRequestTest {

    private List<Offer> validUpdateOffers;
    private List<Offer> validCreateOffers;
    private List<Offer> emptyUpdateList;
    private OfferRequest validRequest;
    private List<Long> idList;
    private ArrayList<Long> emptyIdList;

    @Before
    public void setUp() {
        this.validRequest = OfferRequestTestUtility.getValidRequest();
        this.validUpdateOffers = validRequest.getUpdateOffers();
        this.validCreateOffers = OfferRequestTestUtility.getValidCreateOffersList();
        this.emptyIdList = new ArrayList<>();
        this.emptyUpdateList = new ArrayList<>();
        this.idList = OfferRequestTestUtility.getUniqueIdList();
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void testVerifyEmptyRequest() {
        final OfferRequest requestWithInvalidOffer = new OfferRequest(emptyUpdateList, emptyIdList);
        requestWithInvalidOffer.verify();
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void testVerifyEmptyDeleteRequest() {
        final OfferRequest requestWithInvalidOffer = new OfferRequest(validRequest.getUpdateOffers(), emptyIdList);
        requestWithInvalidOffer.verify();
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void testVerifyEmptyUpdateRequest() {
        final OfferRequest requestWithInvalidOffer = new OfferRequest(emptyUpdateList, validRequest.getDeleteOfferIds());
        requestWithInvalidOffer.verify();
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void testVerifyValidDeleteRequest() {
        final List<Long> deleteList = Copier.copy(idList);
        final OfferRequest requestWithInvalidOffer = new OfferRequest(emptyUpdateList, deleteList);
        requestWithInvalidOffer.verify();
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void testVerifyValidUpdateRequest() {
        final List<Offer> updateOffers = Copier.copy(validUpdateOffers);
        final OfferRequest request = new OfferRequest(updateOffers, emptyIdList);
        request.verify();
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void testVerifyValidCreateRequest() {
        final List<Offer> createOffers = Copier.copy(validCreateOffers);
        final OfferRequest requestWithInvalidOffer = new OfferRequest(createOffers, emptyIdList);
        requestWithInvalidOffer.verify();
    }

    @Test(expected = VerificationException.class)
    public void testRequestWithFallibleOffer() {
        final List<Offer> createOffers = Copier.copy(validCreateOffers);
        createOffers.add(new Offer(new EntityIdentificationException("")));
        final OfferRequest requestWithInvalidOffer = new OfferRequest(createOffers, emptyIdList);
        requestWithInvalidOffer.verify();
    }

    @Test(expected = VerificationException.class)
    public void testVerifyEditRequestWithInvalidOffer() {
        final List<Offer> updateOffers = Copier.copy(validUpdateOffers);
        updateOffers.get(0).setRefNo(null); // dto is not valid
        final OfferRequest requestWithInvalidOffer = new OfferRequest(updateOffers, emptyIdList);
        requestWithInvalidOffer.verify();
    }

    @Test(expected = VerificationException.class)
    public void testVerifyEditRequestWithDuplicatedRefNo() {
        final List<Offer> editOffers = Copier.copy(validUpdateOffers);
        editOffers.get(0).setRefNo(editOffers.get(1).getRefNo()); // trying to update same entity twice
        final OfferRequest requestWithInvalidOffer = new OfferRequest(editOffers, emptyIdList);
        requestWithInvalidOffer.verify();
    }

    @Test(expected = VerificationException.class)
    public void testVerifyCreateRequestWithDuplicatedId() {
        final List<Offer> createOffers = Copier.copy(validCreateOffers);
        createOffers.get(0).setId(createOffers.get(1).getId()); // trying to update same entity twice
        final OfferRequest requestWithInvalidOffer = new OfferRequest(createOffers, emptyIdList);
        requestWithInvalidOffer.verify();
    }

    @Test(expected = VerificationException.class)
    public void testVerifyDeleteRequestWithDuplicatedId() {
        final List<Long> deleteList = Copier.copy(emptyIdList);
        deleteList.add(1L);
        deleteList.add(1L);
        final OfferRequest requestWithInvalidOffer = new OfferRequest(emptyUpdateList, deleteList);
        requestWithInvalidOffer.verify();
    }

    @Test
    public void testGetters() {
        final List<Offer> initialUpdateOffers = validRequest.getUpdateOffers();
        final List<Long> initialDeleteOfferIds = validRequest.getDeleteOfferIds();

        final List<Offer> editOffers = validRequest.getUpdateOffers();
        final List<Long> deleteOfferIDs = validRequest.getDeleteOfferIds();
        editOffers.remove(0);
        editOffers.remove(editOffers.size() - 1);
        deleteOfferIDs.add(5L);
        deleteOfferIDs.add(10L);

        Assert.assertThat(editOffers, is(not(nullValue())));
        Assert.assertThat(deleteOfferIDs, is(not(nullValue())));
        Assert.assertThat(validRequest, is(not(nullValue())));
        Assert.assertThat(validRequest.getUpdateOffers(), is(not(nullValue())));
        Assert.assertThat(validRequest.getUpdateOffers(), is(initialUpdateOffers));
        Assert.assertThat(validRequest.getDeleteOfferIds(), is(not(nullValue())));
        Assert.assertThat(validRequest.getDeleteOfferIds(), is(initialDeleteOfferIds));
    }
}
