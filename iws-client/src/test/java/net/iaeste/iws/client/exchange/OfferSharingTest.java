/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.client.exchange;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.GroupList;
import net.iaeste.iws.api.dtos.TestData;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.requests.exchange.HideForeignOffersRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.RejectOfferRequest;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.api.util.Verifications;
import net.iaeste.iws.client.ExchangeClient;
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
 * @since   IWS 1.2
 */
public final class OfferSharingTest extends AbstractOfferTest {

    private static final int exchangeYear = Verifications.calculateExchangeYear();
    private static final String PL_YEAR = "PL-" + exchangeYear;
    private static final String AT_YEAR = "AT-" + exchangeYear;

    private final Exchange exchange = new ExchangeClient();
    private AuthenticationToken austriaToken = null;
    private AuthenticationToken croatiaToken = null;

    private Group austriaTokenNationallGroup = null;

    @Override
    public void setUp() {
        token = login("poland@iaeste.pl", "poland");
        austriaToken = login("austria@iaeste.at", "austria");
        croatiaToken = login("croatia@iaeste.hr", "croatia");

        austriaTokenNationallGroup = findNationalGroup(austriaToken);
    }

    @Override
    public void tearDown() {
        logout(token);
        logout(austriaToken);
        logout(croatiaToken);
    }

    @Test
    public void testShareOffer() {
        final Date nominationDeadline = new Date().plusDays(20);

        final String refno = PL_YEAR + "-000004";
        final Offer offer = TestData.prepareMinimalOffer(refno, "Polish Employer");

        final ProcessOfferRequest offerRequest = prepareRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);

        assertThat(saveResponse.isOk(), is(true));

        assertThat(saveResponse.getOffer().getNsFirstname(), is(not(nullValue())));
        assertThat(saveResponse.getOffer().getNsLastname(), is(not(nullValue())));

        final FetchOffersRequest allOffersRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        FetchOffersResponse allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        Offer sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        // Following assertion is now deprecated, see trac task #372
        //assertThat(sharedOffer.getRefNo(), is(offer.getRefNo()));
        assertThat(sharedOffer.getStatus(), is(OfferState.NEW));
        assertThat(sharedOffer.getNominationDeadline(), is(not(nominationDeadline)));

        final Set<String> offersToShare = new HashSet<>(1);
        offersToShare.add(sharedOffer.getOfferId());

        final List<String> groupIds = new ArrayList<>(2);
        groupIds.add(findNationalGroup(austriaToken).getGroupId());
        groupIds.add(findNationalGroup(croatiaToken).getGroupId());

        final PublishOfferRequest publishRequest1 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse1 = exchange.processPublishOffer(token, publishRequest1);

        assertThat(publishResponse1.getError(), is(IWSErrors.SUCCESS));
        assertThat(publishResponse1.isOk(), is(true));

        final List<String> offersExternalId = new ArrayList<>(1);
        offersExternalId.add(sharedOffer.getOfferId());
        final FetchPublishedGroupsRequest fetchPublishRequest = new FetchPublishedGroupsRequest(offersExternalId);
        final FetchPublishedGroupsResponse fetchPublishResponse1 = exchange.fetchPublishedGroups(token, fetchPublishRequest);

        //is it shared to two groups?
        assertThat(fetchPublishResponse1.isOk(), is(true));
        GroupList offerGroupsSharedTo = fetchPublishResponse1.getOffersGroups().get(offersExternalId.get(0));
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
        assertThat("The offer is shared to nobody, the status has to be OPEN", sharedOffer.getStatus(), is(OfferState.OPEN));
    }

    /**
     * Numerous NPEs were found in the production logs on 2016-01-24. The cause
     * was that the request was empty. This test was created to replicate the
     * error and ensure that the fix is in place.
     */
    @Test
    public void testPublishOffersWithEmptyRequest() {
        final PublishOfferRequest request = new PublishOfferRequest();
        final PublishOfferResponse response = exchange.processPublishOffer(token, request);

        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.VERIFICATION_ERROR));
        assertThat(response.getMessage(), is("Validation failed: {Ids=OfferIds and groupIds are both missing}"));
    }

    @Test
    public void testFetchForeignOffer() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final String refNo = AT_YEAR + "-000001";
        final Offer offer = TestData.prepareMinimalOffer(refNo, "Austrian Employer");

        final ProcessOfferRequest offerRequest = prepareRequest(offer);
        final OfferResponse processResponse = exchange.processOffer(austriaTokenWithNationalGroup, offerRequest);

        assertThat("verify that the offer was persisted", processResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(croatiaToken, request);
        final Offer readOffer = findOfferFromResponse(refNo, fetchResponse);

        assertThat("as the Austrian offer was not shared with Croatia, it shouldn't be loaded", readOffer, is(nullValue()));
    }

    @Test
    public void testDeleteSharedOffer() {
        final Date nominationDeadline = new Date().plusDays(20);

        final String refno = PL_YEAR + "-000099";
        final Offer offer = TestData.prepareMinimalOffer(refno, "Polish Employer");

        final ProcessOfferRequest offerRequest = prepareRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);

        assertThat(saveResponse.isOk(), is(true));

        assertThat(saveResponse.getOffer().getNsFirstname(), is(not(nullValue())));
        assertThat(saveResponse.getOffer().getNsLastname(), is(not(nullValue())));

        final FetchOffersRequest allOffersRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        FetchOffersResponse allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        Offer sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        // Following assertion is now deprecated, see trac task #372
        //assertThat(sharedOffer.getRefNo(), is(offer.getRefNo()));
        assertThat(sharedOffer.getStatus(), is(OfferState.NEW));
        assertThat(sharedOffer.getNominationDeadline(), is(not(nominationDeadline)));

        final Set<String> offersToShare = new HashSet<>(1);
        offersToShare.add(sharedOffer.getOfferId());

        final List<String> groupIds = new ArrayList<>(2);
        groupIds.add(findNationalGroup(austriaToken).getGroupId());
        groupIds.add(findNationalGroup(croatiaToken).getGroupId());

        final PublishOfferRequest publishRequest1 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse1 = exchange.processPublishOffer(token, publishRequest1);

        assertThat(publishResponse1.getError(), is(IWSErrors.SUCCESS));
        assertThat(publishResponse1.isOk(), is(true));

        final List<String> offersExternalId = new ArrayList<>(1);
        offersExternalId.add(sharedOffer.getOfferId());
        final FetchPublishedGroupsRequest fetchPublishRequest = new FetchPublishedGroupsRequest(offersExternalId);
        final FetchPublishedGroupsResponse fetchPublishResponse1 = exchange.fetchPublishedGroups(token, fetchPublishRequest);

        //is it shared to two groups?
        assertThat(fetchPublishResponse1.isOk(), is(true));
        final GroupList offerGroupsSharedTo = fetchPublishResponse1.getOffersGroups().get(offersExternalId.get(0));
        assertThat(2, is(offerGroupsSharedTo.size()));

        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat(sharedOffer.getRefNo(), is(saveResponse.getOffer().getRefNo()));
        assertThat("The offer is shared now, the status has to be SHARED", sharedOffer.getStatus(), is(OfferState.SHARED));
        assertThat(sharedOffer.getNominationDeadline(), is(nominationDeadline));

        final DeleteOfferRequest deleteRequest = new DeleteOfferRequest(saveResponse.getOffer().getOfferId());
        final OfferResponse deleteResponse = exchange.deleteOffer(token, deleteRequest);

        assertThat(deleteResponse.isOk(), is(false));
        assertThat(deleteResponse.getError(), is(IWSErrors.CANNOT_DELETE_OFFER));
        assertThat(deleteResponse.getMessage().contains("It is not permitted to delete the offer"), is(true));
    }

    @Test
    public void testExtendSharingOffer() {
        final Date nominationDeadline = new Date().plusDays(20);

        final String refno = PL_YEAR + "-BUG669-R";
        final Offer offer = TestData.prepareMinimalOffer(refno, "Polish Employer");

        final ProcessOfferRequest offerRequest = prepareRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);

        assertThat(saveResponse.isOk(), is(true));

        assertThat(saveResponse.getOffer().getNsFirstname(), is(not(nullValue())));
        assertThat(saveResponse.getOffer().getNsLastname(), is(not(nullValue())));

        final FetchOffersRequest allOffersRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        FetchOffersResponse allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        Offer sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat(sharedOffer.getStatus(), is(OfferState.NEW));
        assertThat(sharedOffer.getNominationDeadline(), is(not(nominationDeadline)));

        final Set<String> offersToShare = new HashSet<>(1);
        offersToShare.add(sharedOffer.getOfferId());

        final List<String> groupIds = new ArrayList<>(2);
        groupIds.add(findNationalGroup(austriaToken).getGroupId());

        final PublishOfferRequest publishRequest1 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse1 = exchange.processPublishOffer(token, publishRequest1);

        assertThat(publishResponse1.getError(), is(IWSErrors.SUCCESS));
        assertThat(publishResponse1.isOk(), is(true));

        final List<String> offersExternalId = new ArrayList<>(1);
        offersExternalId.add(sharedOffer.getOfferId());
        final FetchPublishedGroupsRequest fetchPublishRequest = new FetchPublishedGroupsRequest(offersExternalId);
        final FetchPublishedGroupsResponse fetchPublishResponse1 = exchange.fetchPublishedGroups(token, fetchPublishRequest);

        //is it shared to one groups?
        assertThat(fetchPublishResponse1.isOk(), is(true));
        GroupList offerGroupsSharedTo = fetchPublishResponse1.getOffersGroups().get(offersExternalId.get(0));
        assertThat(offerGroupsSharedTo.size(), is(1));

        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat(sharedOffer.getRefNo(), is(saveResponse.getOffer().getRefNo()));
        assertThat("The offer is shared now, the status has to be SHARED", sharedOffer.getStatus(), is(OfferState.SHARED));
        assertThat(sharedOffer.getNominationDeadline(), is(nominationDeadline));

        groupIds.add(findNationalGroup(croatiaToken).getGroupId());
        final PublishOfferRequest publishRequest2 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse2 = exchange.processPublishOffer(token, publishRequest2);

        assertThat(publishResponse2.isOk(), is(true));
        final FetchPublishedGroupsResponse fetchPublishResponse2 = exchange.fetchPublishedGroups(token, fetchPublishRequest);
        assertThat(fetchPublishResponse2.isOk(), is(true));
        offerGroupsSharedTo = fetchPublishResponse2.getOffersGroups().get(offersExternalId.get(0));

        //is it shared to two groups?
        assertThat(offerGroupsSharedTo.size(), is(2));
        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat("The offer is shared now, the status has to be SHARED", sharedOffer.getStatus(), is(OfferState.SHARED));
        assertThat("Shared timestamp in domestic offers is always null at the moment", sharedOffer.getShared(), is(nullValue()));
    }

    @Test
    public void testFailShareNonOwnedOffer() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Date nominationDeadline = new Date().plusDays(20);

        final String refno = PL_YEAR + "-000005";
        final Offer offer = TestData.prepareMinimalOffer(refno, "Polish Employer");

        final ProcessOfferRequest offerRequest = prepareRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse response = exchange.fetchOffers(token, request);
        assertThat(response.getOffers().isEmpty(), is(false));

        final Set<String> offersToShare = new HashSet<>(1);
        final String offerIdToBeShared = response.getOffers().get(0).getOfferId();
        offersToShare.add(offerIdToBeShared);

        final String austriaNationalGroupId = findNationalGroup(austriaToken).getGroupId();
        final List<String> groupIds = new ArrayList<>(1);
        groupIds.add(austriaNationalGroupId);

        final PublishOfferRequest publishRequest1 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        //try to share Polish offer by Austrian user
        final PublishOfferResponse publishResponse1 = exchange.processPublishOffer(austriaTokenWithNationalGroup, publishRequest1);

        //the request cannot be OK here
        assertThat(publishResponse1.isOk(), is(false));
        assertThat("The request has to fail with verification error here", publishResponse1.getError(), is(IWSErrors.VERIFICATION_ERROR));
        assertThat(publishResponse1.getMessage(), is("The offer with externalId '" + offerIdToBeShared + "' is not owned by the group 'Austria'."));
    }

    @Test
    public void testFailShareOfferToNonNationalGroupType() {
        final Date nominationDeadline = new Date().plusDays(20);

        final String refno = PL_YEAR + "-000006";
        final Offer offer = TestData.prepareMinimalOffer(refno, "Polish Employer");

        final ProcessOfferRequest offerRequest = prepareRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse response = exchange.fetchOffers(token, request);
        assertThat(response.getOffers().isEmpty(), is(false));

        final Set<String> offersToShare = new HashSet<>(1);
        final String offerIdToBeShared = response.getOffers().get(0).getOfferId();
        offersToShare.add(offerIdToBeShared);

        final String austriaMemberGroupId = findMemberGroup(austriaToken).getGroupId();
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

        final String refno = PL_YEAR + "-000007";
        final Offer offer = TestData.prepareMinimalOffer(refno, "Polish Employer");

        final ProcessOfferRequest offerRequest = prepareRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse response = exchange.fetchOffers(token, request);
        assertThat(response.getOffers().isEmpty(), is(false));

        final Set<String> offersToShare = new HashSet<>(1);
        final String offerIdToBeShared = response.getOffers().get(0).getOfferId();
        offersToShare.add(offerIdToBeShared);

        final String polandNationalGroupId = findNationalGroup(token).getGroupId();
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
    public void testFetchSharedForeignOffer() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Date nominationDeadline = new Date().plusDays(20);

        final String refNo = AT_YEAR + "-000002";
        final Offer offer = TestData.prepareMinimalOffer(refNo, "Austrian Employer");

        final ProcessOfferRequest offerRequest = prepareRequest(offer);
        final OfferResponse processResponse = exchange.processOffer(austriaTokenWithNationalGroup, offerRequest);

        assertThat("verify that the offer was persisted", processResponse.isOk(), is(true));

        final String austriaNsFirstName = processResponse.getOffer().getNsFirstname();
        final String austriaNsLastName = processResponse.getOffer().getNsLastname();

        final Set<String> offersToShare = new HashSet<>(1);
        offersToShare.add(processResponse.getOffer().getOfferId());

        final List<String> groupIds = new ArrayList<>(2);
        groupIds.add(findNationalGroup(croatiaToken).getGroupId());

        final PublishOfferRequest publishRequest = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse = exchange.processPublishOffer(austriaTokenWithNationalGroup, publishRequest);

        assertThat("verify that there was no error during sharing the offer", publishResponse.getError(), is(IWSErrors.SUCCESS));
        assertThat("verify that the offer was successfully shared with Croatia", publishResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.SHARED);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(croatiaToken, request);
        final Offer readOffer = findOfferFromResponse(refNo, fetchResponse);

        assertThat("as the Austrian offer was shared with Croatia, it should be loaded", readOffer, is(not(nullValue())));
        assertThat("status of the shared offer must not be null", readOffer.getStatus(), is(not(nullValue())));
        assertThat("shared timestamp must not be null", readOffer.getShared(), is(not(nullValue())));

        assertThat(readOffer.getNsFirstname(), is(austriaNsFirstName));
        assertThat(readOffer.getNsLastname(), is(austriaNsLastName));
    }

    @Test
    public void testFetchSharedOfferDeadlineToday() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Date nominationDeadlineToday = new Date();

        final String refno = PL_YEAR + "-000011";
        final Offer offer = TestData.prepareMinimalOffer(refno, "Polish Employer");

        final ProcessOfferRequest saveRequest2 = prepareRequest(offer);
        final OfferResponse saveResponse2 = exchange.processOffer(token, saveRequest2);
        final String refNo = saveResponse2.getOffer().getRefNo();

        final FetchOffersRequest fetchSharedRequest = new FetchOffersRequest(FetchType.SHARED);
        final FetchOffersResponse fetchSharedResponse = exchange.fetchOffers(austriaTokenWithNationalGroup, fetchSharedRequest);
        final int size = fetchSharedResponse.getOffers().size();

        assertThat("verify that the offer was persisted", saveResponse2.isOk(), is(true));

        final Offer offerToShare = saveResponse2.getOffer();
        assertThat(offerToShare, is(not(nullValue())));

        final Set<String> offersToShare2 = new HashSet<>(1);
        offersToShare2.add(offerToShare.getOfferId());

        final List<String> groupIds = new ArrayList<>(1);
        groupIds.add(findNationalGroup(austriaToken).getGroupId());

        final PublishOfferRequest publishRequest = new PublishOfferRequest(offersToShare2, groupIds, nominationDeadlineToday);
        final PublishOfferResponse publishResponse = exchange.processPublishOffer(token, publishRequest);

        assertThat("verify that the offer has been shared to Austria", publishResponse.getError(), is(IWSErrors.SUCCESS));

        final FetchOffersRequest fetchSharedRequest2 = new FetchOffersRequest(FetchType.SHARED);
        final FetchOffersResponse fetchSharedResponse2 = exchange.fetchOffers(austriaTokenWithNationalGroup, fetchSharedRequest2);
        final Offer readOffer = findOfferFromResponse(refNo, fetchSharedResponse2);

        assertThat(fetchSharedResponse2.getOffers().size(), is(size + 1));
        assertThat("Polish offer was shared with Croatia and today is the nomination deadline, so it should be loaded", readOffer, is(not(nullValue())));
    }

    @Test
    public void testFetchPublishedGroupsAfterDeadline() {
        final Date deadlineInThePast = new Date().plusDays(-20);

        final String refno = PL_YEAR + "-000014";
        final Offer offer = TestData.prepareMinimalOffer(refno, "Polish Employer");

        final ProcessOfferRequest saveRequest1 = prepareRequest(offer);
        final OfferResponse saveResponse1 = exchange.processOffer(token, saveRequest1);

        assertThat("verify that the offer was persisted", saveResponse1.isOk(), is(true));

        final Offer savedOffer = saveResponse1.getOffer();
        final Set<String> offersToShare = Collections.singleton(savedOffer.getOfferId());
        final List<String> groupIds = Collections.singletonList(findNationalGroup(austriaToken).getGroupId());
        final PublishOfferRequest publishRequest = new PublishOfferRequest(offersToShare, groupIds, deadlineInThePast);
        final PublishOfferResponse publishResponse = exchange.processPublishOffer(token, publishRequest);

        assertThat("verify that the offer has been shared to Austria", publishResponse.getError(), is(IWSErrors.SUCCESS));

        final List<String> offersToShareList = Collections.singletonList(savedOffer.getOfferId());
        final FetchPublishedGroupsRequest request = new FetchPublishedGroupsRequest(offersToShareList);
        final FetchPublishedGroupsResponse fetchPublishedGroupsResponse = exchange.fetchPublishedGroups(token, request);

        assertThat(fetchPublishedGroupsResponse.getOffersGroups(), hasKey(savedOffer.getOfferId()));
        assertThat("it's after the nomination deadline and OfferGroup should be fetched",
                fetchPublishedGroupsResponse.getOffersGroups().get(savedOffer.getOfferId()).size(), is(1));
    }

    @Test
    public void testFetchPublishedGroupsDeadlineToday() {
        final Date nominationDeadlineToday = new Date().plusDays(1);

        final String refno = PL_YEAR + "-000012";
        final Offer offer = TestData.prepareMinimalOffer(refno, "Polish Employer");

        final ProcessOfferRequest saveRequest2 = prepareRequest(offer);
        final OfferResponse saveResponse2 = exchange.processOffer(token, saveRequest2);

        assertThat("verify that the offer was persisted", saveResponse2.isOk(), is(true));

        final Offer savedOffer = saveResponse2.getOffer();
        final Set<String> offersToShare2 = Collections.singleton(savedOffer.getOfferId());

        final List<String> groupIds = Collections.singletonList(findNationalGroup(austriaToken).getGroupId());

        final PublishOfferRequest publishRequest = new PublishOfferRequest(offersToShare2, groupIds, nominationDeadlineToday);
        final PublishOfferResponse publishResponse = exchange.processPublishOffer(token, publishRequest);

        assertThat("verify that the offer has been shared to Austria", publishResponse.getError(), is(IWSErrors.SUCCESS));

        final List<String> offersToShareList = Collections.singletonList(savedOffer.getOfferId());
        final FetchPublishedGroupsRequest request = new FetchPublishedGroupsRequest(offersToShareList);
        final FetchPublishedGroupsResponse fetchPublishedGroupsResponse = exchange.fetchPublishedGroups(token, request);

        assertThat(fetchPublishedGroupsResponse.getOffersGroups(), hasKey(savedOffer.getOfferId()));
        assertThat("it's still before the nomination deadline so OfferGroup should be fetched",
                fetchPublishedGroupsResponse.getOffersGroups().get(savedOffer.getOfferId()).size(), is(1));
    }

    /**
     * The code has been altered, we're now making the primary validation checks
     * in the setters, and throws a standard Illegal Argument Exception if the
     * value is crap.<br />
     *   Secondly, the test was renamed, the API is only referring to one kind
     * of Id's, this is internally known as the "External Id", but outside this
     * distinction is not made.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFetchSharedOfferBadIdFormat() {
        final List<String> offerIds = new ArrayList<>(1);
        // The Identifiers allowed can be either IWS Id's (UUID) or an Offer
        // Reference Number.
        offerIds.add("invalid Id.");
        final FetchPublishedGroupsRequest fetchPublishRequest = new FetchPublishedGroupsRequest(offerIds);
        final FetchPublishedGroupsResponse fetchPublishResponse = exchange.fetchPublishedGroups(token, fetchPublishRequest);

        assertThat(fetchPublishResponse.isOk(), is(false));
        assertThat("The request has to fail with the error here", fetchPublishResponse.getError(), is(IWSErrors.ERROR));
        assertThat(fetchPublishResponse.getMessage(), is("The field Offer Id is invalid."));
    }

    @Test
    public void testHideForeignOffer() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());

        final Offer offer = TestData.prepareMinimalOffer(PL_YEAR + "-TIC772-R", "Poland A/S");
        final Date nominationDeadline = new Date().plusDays(20);

        final ProcessOfferRequest saveRequest = prepareRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, saveRequest);
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest allOffersRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        FetchOffersResponse allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        Offer sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));

        final Set<String> offersToShare = new HashSet<>(1);
        offersToShare.add(sharedOffer.getOfferId());

        final List<String> groupIds = new ArrayList<>(1);
        groupIds.add(findNationalGroup(austriaToken).getGroupId());

        final PublishOfferRequest publishRequest1 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse1 = exchange.processPublishOffer(token, publishRequest1);

        assertThat(publishResponse1.getError(), is(IWSErrors.SUCCESS));
        assertThat(publishResponse1.isOk(), is(true));

        final List<String> offersExternalId = new ArrayList<>(1);
        offersExternalId.add(sharedOffer.getOfferId());
        final FetchPublishedGroupsRequest fetchPublishRequest = new FetchPublishedGroupsRequest(offersExternalId);
        final FetchPublishedGroupsResponse fetchPublishResponse = exchange.fetchPublishedGroups(token, fetchPublishRequest);

        //is it shared to two groups?
        assertThat(fetchPublishResponse.isOk(), is(true));
        final GroupList offerGroupsSharedTo = fetchPublishResponse.getOffersGroups().get(offersExternalId.get(0));
        assertThat(1, is(offerGroupsSharedTo.size()));

        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat(sharedOffer.getRefNo(), is(saveResponse.getOffer().getRefNo()));
        assertThat("The offer is shared now, the status has to be SHARED", sharedOffer.getStatus(), is(OfferState.SHARED));

        final FetchOffersRequest fetchSharedRequest = new FetchOffersRequest(FetchType.SHARED);
        FetchOffersResponse fetchSharedResponse = exchange.fetchOffers(austriaTokenWithNationalGroup, fetchSharedRequest);
        Offer foreignOffer = findOfferFromResponse(offer.getRefNo(), fetchSharedResponse);
        assertThat(foreignOffer, is(not(nullValue())));

        final Set<String> offersToHide = new HashSet<>(1);
        //offersToHide.add(offer.getOfferId());
        offersToHide.add(sharedOffer.getOfferId());
        final HideForeignOffersRequest hideOfferRequest = new HideForeignOffersRequest(offersToHide);
        final Fallible hideOfferResponse = exchange.processHideForeignOffers(austriaTokenWithNationalGroup, hideOfferRequest);
        assertThat(hideOfferResponse.getError(), is(IWSErrors.SUCCESS));
        assertThat(hideOfferResponse.isOk(), is(true));

        fetchSharedResponse = exchange.fetchOffers(austriaTokenWithNationalGroup, fetchSharedRequest);
        foreignOffer = findOfferFromResponse(offer.getRefNo(), fetchSharedResponse);
        assertThat(foreignOffer, is(not(nullValue())));
        assertThat(foreignOffer.isHidden(), is(true));
    }

    @Test
    public void testRejectForeignOffer() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());

        final Offer offer = TestData.prepareMinimalOffer(PL_YEAR + "-TI806A-R", "Poland A/S");
        final Date nominationDeadline = new Date().plusDays(20);

        final ProcessOfferRequest saveRequest = prepareRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, saveRequest);
        assertThat(saveResponse.isOk(), is(true));

        final String offerId = saveResponse.getOffer().getOfferId();

        final FetchOffersRequest allOffersRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        FetchOffersResponse allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        Offer sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));

        final Set<String> offersToShare = new HashSet<>(1);
        offersToShare.add(sharedOffer.getOfferId());

        final List<String> groupIds = new ArrayList<>(1);
        groupIds.add(findNationalGroup(austriaToken).getGroupId());

        final PublishOfferRequest publishRequest1 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse1 = exchange.processPublishOffer(token, publishRequest1);

        assertThat(publishResponse1.getError(), is(IWSErrors.SUCCESS));
        assertThat(publishResponse1.isOk(), is(true));

        final List<String> offersExternalId = new ArrayList<>(1);
        offersExternalId.add(sharedOffer.getOfferId());
        final FetchPublishedGroupsRequest fetchPublishRequest = new FetchPublishedGroupsRequest(offersExternalId);
        final FetchPublishedGroupsResponse fetchPublishResponse = exchange.fetchPublishedGroups(token, fetchPublishRequest);

        //is it shared to one groups?
        assertThat(fetchPublishResponse.isOk(), is(true));
        final GroupList offerGroupsSharedTo = fetchPublishResponse.getOffersGroups().get(offersExternalId.get(0));
        assertThat(1, is(offerGroupsSharedTo.size()));

        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat(sharedOffer.getRefNo(), is(saveResponse.getOffer().getRefNo()));
        assertThat("The offer is shared now, the status has to be SHARED", sharedOffer.getStatus(), is(OfferState.SHARED));

        final FetchOffersRequest fetchSharedRequest = new FetchOffersRequest(FetchType.SHARED);
        final FetchOffersResponse fetchSharedResponse = exchange.fetchOffers(austriaTokenWithNationalGroup, fetchSharedRequest);
        final Offer foreignOffer = findOfferFromResponse(offer.getRefNo(), fetchSharedResponse);
        assertThat(foreignOffer, is(not(nullValue())));

        final RejectOfferRequest rejectOfferRequest = new RejectOfferRequest(foreignOffer.getOfferId());
        final Fallible rejectOfferResponse = exchange.rejectOffer(austriaTokenWithNationalGroup, rejectOfferRequest);
        assertThat(rejectOfferResponse.getError(), is(IWSErrors.SUCCESS));
        assertThat(rejectOfferResponse.isOk(), is(true));

        final List<String> sharedOffers = new ArrayList<>(offersToShare);
        final FetchPublishedGroupsRequest fetchPublishedGroupsRequest = new FetchPublishedGroupsRequest(sharedOffers);
        final FetchPublishedGroupsResponse fetchPublishedGroupsResponse = exchange.fetchPublishedGroups(token, fetchPublishedGroupsRequest);
        assertTrue("OfferGroup map has to contain our offer id", fetchPublishedGroupsResponse.getOffersGroups().containsKey(offerId));

        final Group sharingPartner = findGroupFromResponse(offerId, austriaTokenNationallGroup.getGroupId(), fetchPublishedGroupsResponse);
        assertThat(sharingPartner, is(nullValue()));

        //Since the testing offer was shared to only one group, offer state has to be rejected
        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat(sharedOffer.getRefNo(), is(saveResponse.getOffer().getRefNo()));
        assertThat("The offer is shared now, the status has to be REJECTED", sharedOffer.getStatus(), is(OfferState.REJECTED));
    }

    @Test
    public void testShareRejectedOffer() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());

        final Offer offer = TestData.prepareMinimalOffer(PL_YEAR + "-TI806B-R", "Poland A/S");
        final Date nominationDeadline = new Date().plusDays(20);

        final ProcessOfferRequest saveRequest = prepareRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, saveRequest);
        assertThat(saveResponse.isOk(), is(true));

        final String offerId = saveResponse.getOffer().getOfferId();

        final FetchOffersRequest allOffersRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        FetchOffersResponse allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        Offer sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));

        final Set<String> offersToShare = new HashSet<>(1);
        offersToShare.add(sharedOffer.getOfferId());

        final List<String> groupIds = new ArrayList<>(1);
        groupIds.add(findNationalGroup(austriaToken).getGroupId());

        final PublishOfferRequest publishRequest1 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse1 = exchange.processPublishOffer(token, publishRequest1);

        assertThat(publishResponse1.getError(), is(IWSErrors.SUCCESS));
        assertThat(publishResponse1.isOk(), is(true));

        final List<String> offersExternalId = new ArrayList<>(1);
        offersExternalId.add(sharedOffer.getOfferId());
        FetchPublishedGroupsRequest fetchPublishRequest = new FetchPublishedGroupsRequest(offersExternalId);
        FetchPublishedGroupsResponse fetchPublishResponse = exchange.fetchPublishedGroups(token, fetchPublishRequest);

        //is it shared to one groups?
        assertThat(fetchPublishResponse.isOk(), is(true));
        GroupList offerGroupsSharedTo = fetchPublishResponse.getOffersGroups().get(offersExternalId.get(0));
        assertThat(1, is(offerGroupsSharedTo.size()));

        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat(sharedOffer.getRefNo(), is(saveResponse.getOffer().getRefNo()));
        assertThat("The offer is shared now, the status has to be SHARED", sharedOffer.getStatus(), is(OfferState.SHARED));

        final FetchOffersRequest fetchSharedRequest = new FetchOffersRequest(FetchType.SHARED);
        final FetchOffersResponse fetchSharedResponse = exchange.fetchOffers(austriaTokenWithNationalGroup, fetchSharedRequest);
        final Offer foreignOffer = findOfferFromResponse(offer.getRefNo(), fetchSharedResponse);
        assertThat(foreignOffer, is(not(nullValue())));

        final RejectOfferRequest rejectOfferRequest = new RejectOfferRequest(foreignOffer.getOfferId());
        final Fallible rejectOfferResponse = exchange.rejectOffer(austriaTokenWithNationalGroup, rejectOfferRequest);
        assertThat(rejectOfferResponse.getError(), is(IWSErrors.SUCCESS));
        assertThat(rejectOfferResponse.isOk(), is(true));

        final List<String> sharedOffers = new ArrayList<>(offersToShare);
        final FetchPublishedGroupsRequest fetchPublishedGroupsRequest = new FetchPublishedGroupsRequest(sharedOffers);
        final FetchPublishedGroupsResponse fetchPublishedGroupsResponse = exchange.fetchPublishedGroups(token, fetchPublishedGroupsRequest);
        assertTrue("OfferGroup map has to contain our offer id", fetchPublishedGroupsResponse.getOffersGroups().containsKey(offerId));

        final Group sharingPartner = findGroupFromResponse(offerId, austriaTokenNationallGroup.getGroupId(), fetchPublishedGroupsResponse);
        assertThat(sharingPartner, is(nullValue()));

        //Since the testing offer was shared to only one group, offer state has to be rejected
        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat(sharedOffer.getRefNo(), is(saveResponse.getOffer().getRefNo()));
        assertThat("The offer is rejected by exchange partner, the status has to be REJECTED", sharedOffer.getStatus(), is(OfferState.REJECTED));

        final PublishOfferRequest publishRequest2 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        exchange.processPublishOffer(token, publishRequest2);

        fetchPublishRequest = new FetchPublishedGroupsRequest(offersExternalId);
        fetchPublishResponse = exchange.fetchPublishedGroups(token, fetchPublishRequest);

        //is it shared to one groups?
        assertThat(fetchPublishResponse.isOk(), is(true));
        offerGroupsSharedTo = fetchPublishResponse.getOffersGroups().get(offersExternalId.get(0));
        assertThat(1, is(offerGroupsSharedTo.size()));

        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat(sharedOffer.getRefNo(), is(saveResponse.getOffer().getRefNo()));
        assertThat("The offer is shared now, the status has to be SHARED", sharedOffer.getStatus(), is(OfferState.SHARED));
    }

    @Test
    public void testShareRejectedOffer2() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());

        final Offer offer = TestData.prepareMinimalOffer(PL_YEAR + "-TI806C-R", "Poland A/S");
        final Date nominationDeadline = new Date().plusDays(20);

        final ProcessOfferRequest saveRequest = prepareRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, saveRequest);
        assertThat(saveResponse.isOk(), is(true));

        final String offerId = saveResponse.getOffer().getOfferId();

        final FetchOffersRequest allOffersRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        FetchOffersResponse allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        Offer sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));

        final Set<String> offersToShare = new HashSet<>(1);
        offersToShare.add(sharedOffer.getOfferId());

        final List<String> groupIds = new ArrayList<>(2);
        groupIds.add(findNationalGroup(austriaToken).getGroupId());
        groupIds.add(findNationalGroup(croatiaToken).getGroupId());

        final PublishOfferRequest publishRequest1 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse1 = exchange.processPublishOffer(token, publishRequest1);

        assertThat(publishResponse1.getError(), is(IWSErrors.SUCCESS));
        assertThat(publishResponse1.isOk(), is(true));

        final List<String> offersExternalId = new ArrayList<>(1);
        offersExternalId.add(sharedOffer.getOfferId());
        FetchPublishedGroupsRequest fetchPublishRequest = new FetchPublishedGroupsRequest(offersExternalId);
        FetchPublishedGroupsResponse fetchPublishResponse = exchange.fetchPublishedGroups(token, fetchPublishRequest);

        //is it shared to two groups?
        assertThat(fetchPublishResponse.isOk(), is(true));
        GroupList offerGroupsSharedTo = fetchPublishResponse.getOffersGroups().get(offersExternalId.get(0));
        assertThat(2, is(offerGroupsSharedTo.size()));

        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat(sharedOffer.getRefNo(), is(saveResponse.getOffer().getRefNo()));
        assertThat("The offer is shared now, the status has to be SHARED", sharedOffer.getStatus(), is(OfferState.SHARED));

        final FetchOffersRequest fetchSharedRequest = new FetchOffersRequest(FetchType.SHARED);
        final FetchOffersResponse fetchSharedResponse = exchange.fetchOffers(austriaTokenWithNationalGroup, fetchSharedRequest);
        final Offer foreignOffer = findOfferFromResponse(offer.getRefNo(), fetchSharedResponse);
        assertThat(foreignOffer, is(not(nullValue())));

        final RejectOfferRequest rejectOfferRequest = new RejectOfferRequest(foreignOffer.getOfferId());
        final Fallible rejectOfferResponse = exchange.rejectOffer(austriaTokenWithNationalGroup, rejectOfferRequest);
        assertThat(rejectOfferResponse.getError(), is(IWSErrors.SUCCESS));
        assertThat(rejectOfferResponse.isOk(), is(true));

        final List<String> sharedOffers = new ArrayList<>(offersToShare);
        final FetchPublishedGroupsRequest fetchPublishedGroupsRequest = new FetchPublishedGroupsRequest(sharedOffers);
        final FetchPublishedGroupsResponse fetchPublishedGroupsResponse = exchange.fetchPublishedGroups(token, fetchPublishedGroupsRequest);
        assertTrue("OfferGroup map has to contain our offer id", fetchPublishedGroupsResponse.getOffersGroups().containsKey(offerId));

        final Group sharingPartner = findGroupFromResponse(offerId, austriaTokenNationallGroup.getGroupId(), fetchPublishedGroupsResponse);
        assertThat(sharingPartner, is(nullValue()));

        //Since the testing offer was shared to only one group, offer state has to be rejected
        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat(sharedOffer.getRefNo(), is(saveResponse.getOffer().getRefNo()));
        assertThat("The offer is still shared, the status has to be SHARED", sharedOffer.getStatus(), is(OfferState.SHARED));

        fetchPublishRequest = new FetchPublishedGroupsRequest(offersExternalId);
        fetchPublishResponse = exchange.fetchPublishedGroups(token, fetchPublishRequest);

        //is it shared to one groups?
        assertThat(fetchPublishResponse.isOk(), is(true));
        offerGroupsSharedTo = fetchPublishResponse.getOffersGroups().get(offersExternalId.get(0));
        assertThat(1, is(offerGroupsSharedTo.size()));

        final PublishOfferRequest publishRequest2 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        exchange.processPublishOffer(token, publishRequest2);

        fetchPublishRequest = new FetchPublishedGroupsRequest(offersExternalId);
        fetchPublishResponse = exchange.fetchPublishedGroups(token, fetchPublishRequest);

        //is it shared to two groups again?
        assertThat(fetchPublishResponse.isOk(), is(true));
        offerGroupsSharedTo = fetchPublishResponse.getOffersGroups().get(offersExternalId.get(0));
        assertThat(2, is(offerGroupsSharedTo.size()));
    }

    @Test
    @Ignore("Ignored 2013-09-17 by Kim - Reason: The listing retrieved contains 86 records, we need to check why, as there should only be 86 records in the database, so the found result should be 85!")
    public void testFetchGroupsForSharing() {
        austriaToken.setGroupId(findMemberGroup(austriaToken).getGroupId());
        final FetchGroupsForSharingResponse response = exchange.fetchGroupsForSharing(austriaToken);

        assertThat(response.isOk(), is(true));
        // 86 countries are entered in the test data, minus the own country (austria)
        assertThat("Expect from test data to get all groups minus the own -> 85", response.getGroups().size(), is(85));

        for (final Group group : response.getGroups()) {
            assertThat(group.getGroupType(), is(GroupType.NATIONAL));
            assertThat(group.getCountry().getCountryCode(), is(not("AT")));
        }
    }
}
