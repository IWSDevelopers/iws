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
import net.iaeste.iws.api.dtos.OfferTestUtility;
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


    private static OfferRequest getEmptyRequest() {
        return new OfferRequest();
    }

    private static OfferRequest getValidRequest() {
        final List<Offer> validList = getValidUpdateOffersList();
        validList.addAll(getValidCreateOffersList());
        return new OfferRequest(validList, getUniqueIdList());

    }

    private static List<Long> getUniqueIdList() {
        final List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        return ids;
    }

    private static List<Offer> getValidCreateOffersList() {
        final String[] refNos = { "GB-2012-2000", "GB-2012-2001", "GB-2012-2002", "GB-2012-2003", "GB-2012-2004" };
        final List<Offer> createList = getValidUpdateOffersList();
        long i = 0;
        for (final Offer offer : createList) {
            offer.setRefNo(refNos[(int) i]);
            offer.setId(++i);
        }
        return createList;
    }

    private static List<Offer> getValidUpdateOffersList() {
        final String[] refNos = { "AT-2012-1000", "AT-2012-1001", "AT-2012-1002", "AT-2012-1003", "AT-2012-1004" };
        final List<Offer> validEditOffers = new ArrayList<>(refNos.length);
        for (final String refNo : refNos) {
            final Offer minimalOffer = OfferTestUtility.getMinimalOffer();
            minimalOffer.setRefNo(refNo);
            validEditOffers.add(minimalOffer);
        }
        return validEditOffers;
    }

    @Before
    public void setUp() {
        this.validRequest = getValidRequest();
        this.validUpdateOffers = validRequest.getUpdateOffers();
        this.validCreateOffers = getValidCreateOffersList();
        this.emptyIdList = new ArrayList<>();
        this.emptyUpdateList = new ArrayList<>();
        this.idList = getUniqueIdList();
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
        final OfferRequest requestWithInvalidOffer = new OfferRequest(emptyUpdateList, validRequest.getDeleteOfferIDs());
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
        final List<Offer> editOffers = validRequest.getUpdateOffers();
        final List<Long> deleteOfferIDs = validRequest.getDeleteOfferIDs();
        editOffers.remove(0);
        editOffers.remove(editOffers.size() - 1);
        deleteOfferIDs.add(5L);
        deleteOfferIDs.add(10L);
        Assert.assertThat(editOffers, is(not(nullValue())));
        Assert.assertThat(validRequest, is(not(nullValue())));
        Assert.assertThat(validRequest.getUpdateOffers(), is(not(nullValue())));
        Assert.assertThat(validRequest.getUpdateOffers().size(), is(not(editOffers.size())));
        Assert.assertNotSame(validRequest.getUpdateOffers().get(0), editOffers.get(0));
        Assert.assertNotSame(validRequest.getUpdateOffers(), editOffers);
    }
}
