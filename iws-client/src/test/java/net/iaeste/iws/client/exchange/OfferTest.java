/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.exchange.ExchangeClientTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.exchange;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.OfferTestUtility;
import net.iaeste.iws.api.dtos.TestData;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.dtos.exchange.OfferGroup;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.ExchangeClient;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class OfferTest extends AbstractTest {

    private final Exchange exchange = new ExchangeClient();
    private AuthenticationToken austriaToken = null;
    private AuthenticationToken croatiaToken = null;

    @Override
    public void setup() {
        token = login("poland@iaeste.pl", "poland");
        austriaToken = login("austria@iaeste.at", "austria");
        croatiaToken = login("croatia@iaeste.hr", "croatia");
    }

    @Override
    public void tearDown() {
        logout(token);
        logout(austriaToken);
        logout(croatiaToken);
    }

    @Test
    public void testDuplicateOffer() {
        final Offer offer = TestData.prepareFullOffer("PL-2013-1234-XX", "Poland A/S", "PL");
        final Offer duplicate = TestData.prepareFullOffer("PL-2013-1234-XX", "Poland A/S", "PL");
        final ProcessOfferRequest request = new ProcessOfferRequest();

        // Save our first offer.
        request.setOffer(offer);
        final OfferResponse initial = exchange.processOffer(token, request);
        assertThat(initial.isOk(), is(true));
        assertThat(initial.getOffer(), is(not(nullValue())));
        assertThat(initial.getOffer().getId(), is(not(nullValue())));

        // Now, attempt to save an identical offer.
        request.setOffer(duplicate);
        final OfferResponse failing = exchange.processOffer(token, request);
        assertThat(failing.isOk(), is(false));
        assertThat(failing.getError(), is(IWSErrors.OBJECT_IDENTIFICATION_ERROR));
        assertThat(failing.getMessage(), containsString("An Offer with the Reference Number PL-2014-1234-XX already exists."));
    }

    /**
     * Trac Bug report #418, indicates that the Exchange Transformer was faulty,
     * and thereby causing problems with updating an existing Offer.
     */
    @Test
    public void testUpdateExistingOffer() {
        final Offer initialOffer = TestData.prepareFullOffer("PL-2013-4321-XX", "Poland GmbH", "PL");
        final ProcessOfferRequest request = new ProcessOfferRequest();
        request.setOffer(initialOffer);
        final OfferResponse saveResponse = exchange.processOffer(token, request);
        assertThat(saveResponse, is(not(nullValue())));
        assertThat(saveResponse.isOk(), is(true));

        // Now, retrieve the saved offer, and update it
        final Offer savedOffer = saveResponse.getOffer();
        savedOffer.setWorkDescription("Whatever");
        request.setOffer(savedOffer);
        final OfferResponse updateResponse = exchange.processOffer(token, request);
        assertThat(updateResponse, is(not(nullValue())));
        assertThat(updateResponse.isOk(), is(true));
        final Offer updatedOffer = updateResponse.getOffer();

        // Now, we have the inial Offer, and the updated Offer
        assertThat(updatedOffer.getId(), is(savedOffer.getId()));
        assertThat(updatedOffer.getModified().isAfter(savedOffer.getModified()), is(true));
        assertThat(updatedOffer.getCreated().toString(), is(savedOffer.getCreated().toString()));
        assertThat(updatedOffer.getWorkDescription(), is(not(initialOffer.getWorkDescription())));
        assertThat(updatedOffer.getWorkDescription(), is("Whatever"));
    }

    @Test
    public void testProcessOfferWithInvalidRefno() {
        final Offer minimalOffer = OfferTestUtility.getMinimalOffer();
        // We're logged in as Poland, so the Offer must start with "PL".
        minimalOffer.setRefNo("GB-2012-0001");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(minimalOffer);
        final OfferResponse processResponse = exchange.processOffer(token, offerRequest);

        // verify processResponse
        assertThat(processResponse.isOk(), is(false));
        assertThat(processResponse.getError(), is(IWSErrors.VERIFICATION_ERROR));
        assertThat(processResponse.getMessage(), is("The reference number is not valid for this country. Received 'GB' but expected 'PL'."));
    }

    @Test
    public void testProcessOfferCreateMinimalOffer() {
        final Offer minimalOffer = OfferTestUtility.getMinimalOffer();
        minimalOffer.setRefNo("PL-2012-0001");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(minimalOffer);
        final OfferResponse processResponse = exchange.processOffer(token, offerRequest);
        final String refNo = processResponse.getOffer().getRefNo();

        // verify processResponse
        assertThat(processResponse.isOk(), is(true));

        // check if minimalOffer is persisted
        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, request);
        final Offer readOffer = findOfferFromResponse(refNo, fetchResponse);

        assertThat(readOffer, is(not(nullValue())));
    }

    @Test
    public void testProcessOfferCreateFullOffer() {
        final Offer fullOffer = OfferTestUtility.getFullOffer();
        fullOffer.setRefNo("PL-2012-0002");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(fullOffer);
        final OfferResponse processResponse = exchange.processOffer(token, offerRequest);
        final String refNo = processResponse.getOffer().getRefNo();

        // verify processResponse
        assertThat(processResponse.isOk(), is(true));

        // check if fullOffer is persisted
        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, request);
        final Offer readOffer = findOfferFromResponse(refNo, fetchResponse);

        assertThat(readOffer, is(not(nullValue())));
    }

    @Test
    public void testDeleteOffer() {
        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setRefNo("PL-2012-0003");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);

        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse response = exchange.fetchOffers(token, request);
        assertThat(response.getOffers().isEmpty(), is(false));
        final int size = response.getOffers().size();

        final Offer offerToDelete = response.getOffers().get(0);

        final DeleteOfferRequest deleteRequest = new DeleteOfferRequest(offerToDelete.getId());
        final OfferResponse deleteResponse = exchange.deleteOffer(token, deleteRequest);

        assertThat(deleteResponse.isOk(), is(true));
        final FetchOffersRequest fetchRequest = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, fetchRequest);
        assertThat(fetchResponse.getOffers().size(), is(size - 1));

        for (final Offer o : fetchResponse.getOffers()) {
            if (o.getRefNo().equals(offerToDelete.getRefNo())) {
                fail("offer is supposed to be deleted");
            }
        }
    }

    @Test
    public void testShareOffer() {
        final Date nominationDeadline = new Date().plusDays(20);

        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setRefNo("PL-2012-0004");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);

        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest allOffersRequest = new FetchOffersRequest(FetchType.ALL);
        FetchOffersResponse allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        Offer sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        // Following assertion is now deprecated, see trac task #372
        //assertThat(sharedOffer.getRefNo(), is(offer.getRefNo()));
        assertThat(sharedOffer.getStatus(), is(OfferState.NEW));
        assertThat(sharedOffer.getNominationDeadline(), is(not(nominationDeadline)));

        final Set<String> offersToShare = new HashSet<>(1);
        offersToShare.add(sharedOffer.getId());

        final List<String> groupIds = new ArrayList<>(2);
        groupIds.add("c7b15f81-4f83-48e8-9ffb-9e73255f5e5e"); // Austria National
        groupIds.add("17eb00ac-1386-4852-9934-e3dce3f57c13"); // Germany National

        final PublishOfferRequest publishRequest1 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse1 = exchange.processPublishOffer(token, publishRequest1);

        assertThat(publishResponse1.getError(), is(IWSErrors.SUCCESS));
        assertThat(publishResponse1.isOk(), is(true));

        final List<String> offersExternalId = new ArrayList<>(1);
        offersExternalId.add(sharedOffer.getId());
        final FetchPublishedGroupsRequest fetchPublishRequest = new FetchPublishedGroupsRequest(offersExternalId);
        final FetchPublishedGroupsResponse fetchPublishResponse1 = exchange.fetchPublishedGroups(token, fetchPublishRequest);

        //is it shared to two groups?
        assertThat(fetchPublishResponse1.isOk(), is(true));
        List<OfferGroup> offerGroupsSharedTo = fetchPublishResponse1.getOffersGroups().get(offersExternalId.get(0));
        assertThat(2, is(offerGroupsSharedTo.size()));

        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat(sharedOffer.getRefNo(), is(saveResponse.getOffer().getRefNo()));
        assertThat("The offer is shared now, the status has to be SHARED", sharedOffer.getStatus(), is(OfferState.SHARED));
        assertThat(sharedOffer.getNominationDeadline(), is(nominationDeadline));

        groupIds.clear();
        final PublishOfferRequest publishRequest2 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse2 = exchange.processPublishOffer(token, publishRequest2);

        assertThat(publishResponse2.isOk(), is(true));
        final FetchPublishedGroupsResponse fetchPublishResponse2 = exchange.fetchPublishedGroups(token, fetchPublishRequest);
        assertThat(fetchPublishResponse2.isOk(), is(true));
        offerGroupsSharedTo = fetchPublishResponse2.getOffersGroups().get(offersExternalId.get(0));

        //is it shared to nobody?
        assertThat(offerGroupsSharedTo.size(), is(0));
        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat("The offer is shared to nobody, the status has to be NEW", sharedOffer.getStatus(), is(OfferState.NEW));
    }

    @Test
    public void testFailShareNonOwnedOffer() {
        final Date nominationDeadline = new Date().plusDays(20);

        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setRefNo("PL-2012-0005");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse response = exchange.fetchOffers(token, request);
        assertThat(response.getOffers().isEmpty(), is(false));

        final Set<String> offersToShare = new HashSet<>(1);
        final String offerIdToBeShared = response.getOffers().get(0).getId();
        offersToShare.add(offerIdToBeShared);

        final String austriaNationalGroupId = "c7b15f81-4f83-48e8-9ffb-9e73255f5e5e";
        final List<String> groupIds = new ArrayList<>(1);
        groupIds.add(austriaNationalGroupId);

        final PublishOfferRequest publishRequest1 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        //try to share Polish offer by Austrian user
        final PublishOfferResponse publishResponse1 = exchange.processPublishOffer(austriaToken, publishRequest1);

        //the request cannot be OK here
        assertThat(publishResponse1.isOk(), is(false));
        assertThat("The request has to fail with verification error here", publishResponse1.getError(), is(IWSErrors.VERIFICATION_ERROR));
        assertThat(publishResponse1.getMessage(), is("The offer with externalId '" + offerIdToBeShared + "' is not owned by the group 'Austria'."));
    }

    @Test
    public void testFailShareOfferToNonNationalGroupType() {
        final Date nominationDeadline = new Date().plusDays(20);

        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setRefNo("PL-2012-0006");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse response = exchange.fetchOffers(token, request);
        assertThat(response.getOffers().isEmpty(), is(false));

        final Set<String> offersToShare = new HashSet<>(1);
        final String offerIdToBeShared = response.getOffers().get(0).getId();
        offersToShare.add(offerIdToBeShared);

        final String austriaMemberGroupId = "2cc7e1bb-01e8-43a2-9643-2e964cbd41c5";
        final GroupType austriaMemberGroupType = GroupType.MEMBER;
        final List<String> groupIds = new ArrayList<>(1);
        groupIds.add(austriaMemberGroupId);

        final PublishOfferRequest publishRequest1 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        //try to share to non-National group type
        final PublishOfferResponse publishResponse1 = exchange.processPublishOffer(token, publishRequest1);

        //the request is supposed to fail here
        assertThat(publishResponse1.isOk(), is(false));
        assertThat("The request has to fail with verification error here", publishResponse1.getError(), is(IWSErrors.VERIFICATION_ERROR));
        assertThat(publishResponse1.getMessage(), is("The group type '" + austriaMemberGroupType + "' is not allowed to be used for publishing of offers."));
    }

    @Test
    public void testFailShareOfferToSelf() {
        final Date nominationDeadline = new Date().plusDays(20);

        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setRefNo("PL-2012-0007");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse response = exchange.fetchOffers(token, request);
        assertThat(response.getOffers().isEmpty(), is(false));

        final Set<String> offersToShare = new HashSet<>(1);
        final String offerIdToBeShared = response.getOffers().get(0).getId();
        offersToShare.add(offerIdToBeShared);

        final String polandNationalGroupId = "e60f9897-864b-4d1b-9c1a-1681fd35e97a";
        final List<String> groupIds = new ArrayList<>(1);
        groupIds.add(polandNationalGroupId);

        //try to share to the owner of the offer
        final PublishOfferRequest publishRequest = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse = exchange.processPublishOffer(token, publishRequest);

        //the request cannot be OK here
        assertThat(publishResponse.isOk(), is(false));
        assertThat("The request has to fail with verification error here", publishResponse.getError(), is(IWSErrors.VERIFICATION_ERROR));
        assertThat(publishResponse.getMessage(), is("Cannot publish offers to itself."));
    }

    @Test
    @Ignore
    public void testNumberOfHardCopies() {
        final String refNo = "PL-2012-0042";
        final Offer newOffer = OfferTestUtility.getFullOffer();
        newOffer.setRefNo(refNo);
        newOffer.setNumberOfHardCopies(2);

        // Persist Offer, verify that everything went well
        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(newOffer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);
        assertThat(saveResponse.isOk(), is(true));

        // Read Offer, and verify that the NumberOfHardCopies is present
        final FetchOffersRequest findSavedRequest = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse findSavedResponse = exchange.fetchOffers(token, findSavedRequest);
        final Offer readOffer = findOfferFromResponse(refNo, findSavedResponse);
        assertThat(readOffer, is(not(nullValue())));
        assertThat(readOffer.getNumberOfHardCopies(), is(2));
        assertThat(readOffer.getId(), is(not(nullValue())));

        // Update the Offer, with a new value for NumberOfHardCopies
        readOffer.setNumberOfHardCopies(3);
        final ProcessOfferRequest updateOfferRequest = new ProcessOfferRequest(readOffer);
        assertThat(exchange.processOffer(token, updateOfferRequest).isOk(), is(true));

        // Update the Offer, and verify that the changes are saved.
        final FetchOffersRequest findupdatedRequest = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse findUpdatedResponse = exchange.fetchOffers(token, findupdatedRequest);
        final Offer updatedOffer = findOfferFromResponse(refNo, findUpdatedResponse);
        assertThat(updatedOffer, is(not(nullValue())));
        assertThat(updatedOffer.getNumberOfHardCopies(), is(3));
    }

    @Test
    public void testFetchForeignOffer() {
        final String refNo = "AT-2013-0001";
        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setRefNo(refNo);

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse processResponse = exchange.processOffer(austriaToken, offerRequest);

        assertThat("verify that the offer was persisted", processResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(croatiaToken, request);
        final Offer readOffer = findOfferFromResponse(refNo, fetchResponse);

        assertThat("as the Austrian offer was not shared with Croatia, it shouldn't be loaded", readOffer, is(nullValue()));
    }

    @Test
    public void testFetchSharedOfferAfterDeadline() {
        final Date nominationDeadlineInThePast = new Date().plusDays(-20);

        final String refNo = "PL-2012-0010";
        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setRefNo(refNo);

        final ProcessOfferRequest saveRequest1 = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse1 = exchange.processOffer(token, saveRequest1);

        final FetchOffersRequest fetchSharedRequest = new FetchOffersRequest(FetchType.SHARED);
        final FetchOffersResponse fetchSharedResponse = exchange.fetchOffers(austriaToken, fetchSharedRequest);
        final int size = fetchSharedResponse.getOffers().size();

        assertThat("verify that the offer was persisted", saveResponse1.isOk(), is(true));

        final Offer offerToShare = saveResponse1.getOffer();
        assertThat(offerToShare, is(not(nullValue())));

        final Set<String> offersToShare1 = new HashSet<>(1);
        offersToShare1.add(offerToShare.getId());

        final List<String> groupIds = new ArrayList<>(1);
        groupIds.add("c7b15f81-4f83-48e8-9ffb-9e73255f5e5e"); // Austria National

        final PublishOfferRequest publishRequest = new PublishOfferRequest(offersToShare1, groupIds, nominationDeadlineInThePast);
        final PublishOfferResponse publishResponse = exchange.processPublishOffer(token, publishRequest);

        assertThat("verify that the offer has been shared to Austria", publishResponse.getError(), is(IWSErrors.SUCCESS));

        final FetchOffersRequest fetchSharedRequest2 = new FetchOffersRequest(FetchType.SHARED);
        final FetchOffersResponse fetchSharedResponse2 = exchange.fetchOffers(austriaToken, fetchSharedRequest2);
        final Offer readOffer = findOfferFromResponse(refNo, fetchSharedResponse2);

        assertThat(fetchSharedResponse2.getOffers().size(), is(size));
        assertThat("Polish offer was shared with Croatia but it's after the nomination deadline, so it should not be loaded", readOffer, is(nullValue()));
    }

    @Test
    public void testFetchSharedOfferDeadlineToday() {
        final Date nominationDeadlineToday = new Date();

        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setRefNo("PL-2012-0011");

        final ProcessOfferRequest saveRequest2 = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse2 = exchange.processOffer(token, saveRequest2);
        final String refNo = saveResponse2.getOffer().getRefNo();

        final FetchOffersRequest fetchSharedRequest = new FetchOffersRequest(FetchType.SHARED);
        final FetchOffersResponse fetchSharedResponse = exchange.fetchOffers(austriaToken, fetchSharedRequest);
        final int size = fetchSharedResponse.getOffers().size();

        assertThat("verify that the offer was persisted", saveResponse2.isOk(), is(true));

        final Offer offerToShare = saveResponse2.getOffer();
        assertThat(offerToShare, is(not(nullValue())));

        final Set<String> offersToShare2 = new HashSet<>(1);
        offersToShare2.add(offerToShare.getId());

        final List<String> groupIds = new ArrayList<>(1);
        groupIds.add("c7b15f81-4f83-48e8-9ffb-9e73255f5e5e"); // Austria National

        final PublishOfferRequest publishRequest = new PublishOfferRequest(offersToShare2, groupIds, nominationDeadlineToday);
        final PublishOfferResponse publishResponse = exchange.processPublishOffer(token, publishRequest);

        assertThat("verify that the offer has been shared to Austria", publishResponse.getError(), is(IWSErrors.SUCCESS));

        final FetchOffersRequest fetchSharedRequest2 = new FetchOffersRequest(FetchType.SHARED);
        final FetchOffersResponse fetchSharedResponse2 = exchange.fetchOffers(austriaToken, fetchSharedRequest2);
        final Offer readOffer = findOfferFromResponse(refNo, fetchSharedResponse2);

        assertThat(fetchSharedResponse2.getOffers().size(), is(size + 1));
        assertThat("Polish offer was shared with Croatia and today is the nomination deadline, so it should be loaded", readOffer, is(not(nullValue())));
    }

    @Test
    public void testFetchPublishedGroupsAfterDeadline() {
        final Date deadlineInThePast = new Date().plusDays(-20);

        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setRefNo("PL-2012-0014");

        final ProcessOfferRequest saveRequest1 = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse1 = exchange.processOffer(token, saveRequest1);

        assertThat("verify that the offer was persisted", saveResponse1.isOk(), is(true));

        final Offer savedOffer = saveResponse1.getOffer();
        final Set<String> offersToShare = Collections.singleton(savedOffer.getId());
        final List<String> groupIds = Collections.singletonList("c7b15f81-4f83-48e8-9ffb-9e73255f5e5e"); // Austria National
        final PublishOfferRequest publishRequest = new PublishOfferRequest(offersToShare, groupIds, deadlineInThePast);
        final PublishOfferResponse publishResponse = exchange.processPublishOffer(token, publishRequest);

        assertThat("verify that the offer has been shared to Austria", publishResponse.getError(), is(IWSErrors.SUCCESS));

        final List<String> offersToShareList = Collections.singletonList(savedOffer.getId());
        final FetchPublishedGroupsRequest request = new FetchPublishedGroupsRequest(offersToShareList);
        final FetchPublishedGroupsResponse fetchPublishedGroupsResponse = exchange.fetchPublishedGroups(token, request);

        assertThat(fetchPublishedGroupsResponse.getOffersGroups(), hasKey(savedOffer.getId()));
        assertThat("it's still before the nomination deadline so OfferGroup should be fetched",
                fetchPublishedGroupsResponse.getOffersGroups().get(savedOffer.getId()), empty());
    }

    @Test
    public void testFetchPublishedGroupsDeadlineToday() {
        final Date nominationDeadlineToday = new Date();

        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setRefNo("PL-2012-0012");

        final ProcessOfferRequest saveRequest2 = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse2 = exchange.processOffer(token, saveRequest2);

        assertThat("verify that the offer was persisted", saveResponse2.isOk(), is(true));

        final Offer savedOffer = saveResponse2.getOffer();
        final Set<String> offersToShare2 = Collections.singleton(savedOffer.getId());

        final List<String> groupIds = Collections.singletonList("c7b15f81-4f83-48e8-9ffb-9e73255f5e5e"); // Austria National

        final PublishOfferRequest publishRequest = new PublishOfferRequest(offersToShare2, groupIds, nominationDeadlineToday);
        final PublishOfferResponse publishResponse = exchange.processPublishOffer(token, publishRequest);

        assertThat("verify that the offer has been shared to Austria", publishResponse.getError(), is(IWSErrors.SUCCESS));

        final List<String> offersToShareList = Collections.singletonList(savedOffer.getId());
        final FetchPublishedGroupsRequest request = new FetchPublishedGroupsRequest(offersToShareList);
        final FetchPublishedGroupsResponse fetchPublishedGroupsResponse = exchange.fetchPublishedGroups(token, request);

        assertThat(fetchPublishedGroupsResponse.getOffersGroups(), hasKey(savedOffer.getId()));
        assertThat("it's still before the nomination deadline so OfferGroup should be fetched",
                fetchPublishedGroupsResponse.getOffersGroups().get(savedOffer.getId()), hasSize(1));
    }

    /**
     * The code has been altered, we're now making the primary validation checks
     * in the setters, and throwin a standard Illegal Argument Exception if the
     * value is crap.<br />
     *   Secondly, the test was renamed, the API is only referring to one kind
     * of Id's, this is internally known as the "External Id", but outside this
     * distinction is not made.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFetchSharedOfferBadIdFormat() {
        final List<String> offerIds = new ArrayList<>(0);
        offerIds.add("pl-2012-0001");
        final FetchPublishedGroupsRequest fetchPublishRequest = new FetchPublishedGroupsRequest(offerIds);
        final FetchPublishedGroupsResponse fetchPublishResponse = exchange.fetchPublishedGroups(token, fetchPublishRequest);

        assertThat(fetchPublishResponse.isOk(), is(false));
        assertThat("The request has to fail with the error here", fetchPublishResponse.getError(), is(IWSErrors.ERROR));
        assertThat(fetchPublishResponse.getMessage(), is("The field Offer Id is invalid."));
    }

    private static Offer findOfferFromResponse(final String refno, final FetchOffersResponse response) {
        final String refNoLowerCase = refno.toLowerCase(IWSConstants.DEFAULT_LOCALE);
        Offer offer = null;

        for (final Offer found : response.getOffers()) {
            final String foundRefNo = found.getRefNo().toLowerCase(IWSConstants.DEFAULT_LOCALE);
            if (foundRefNo.equals(refNoLowerCase)) {
                offer = found;
            }
        }

        return offer;
    }

    @Test
    public void testFetchGroupsForSharing() {
        austriaToken.setGroupId("c7b15f81-4f83-48e8-9ffb-9e73255f5e5e");
        final FetchGroupsForSharingResponse response = exchange.fetchGroupsForSharing(austriaToken);

        assertThat(response.isOk(), is(true));
        // 6 countries are entered in the test data, minus the own country (austria)
        assertThat("Expect from test data to get all groups minus the own -> 5", response.getGroups().size(), is(6 - 1));

        final GroupType[] groupTypes = {GroupType.NATIONAL, GroupType.SAR};
        for (final Group group : response.getGroups()) {
            assertThat(group.getGroupType(), Matchers.isIn(groupTypes));
            assertThat(group.getCountry().getCountryCode(), is(not("AT")));
        }
    }
}
