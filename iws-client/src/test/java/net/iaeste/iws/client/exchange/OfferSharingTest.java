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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

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
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.Verifications;
import net.iaeste.iws.client.ExchangeClient;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
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
    public void testDeleteSharedOffer() {
        final Date nominationDeadline = new Date().plusDays(20);

        final String refno = PL_YEAR + "-000099";
        final Offer offer = TestData.prepareMinimalOffer(refno, "Polish Employer");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
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
    public void testShareOffer() {
        final Date nominationDeadline = new Date().plusDays(20);

        final String refno = PL_YEAR + "-000004";
        final Offer offer = TestData.prepareMinimalOffer(refno, "Polish Employer");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
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

    @Test
    public void testExtendSharingOffer() {
        final Date nominationDeadline = new Date().plusDays(20);

        final String refno = PL_YEAR + "-BUG669-R";
        final Offer offer = TestData.prepareMinimalOffer(refno, "Polish Employer");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
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

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
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

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
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

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
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
