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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.GroupList;
import net.iaeste.iws.api.dtos.TestData;
import net.iaeste.iws.api.dtos.exchange.CSVProcessingErrors;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.exchange.ExchangeType;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.enums.exchange.OfferType;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.requests.exchange.HideForeignOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVDownloadRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVUploadRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.RejectOfferRequest;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVDownloadResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVUploadResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.ExchangeClient;
import org.junit.Ignore;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class OfferTest extends AbstractTest {

    private static final int exchangeYear = AbstractVerification.calculateExchangeYear();
    private static final String PL_YEAR = "PL-" + exchangeYear;
    private static final String AT_YEAR = "AT-" + exchangeYear;

    private final Exchange exchange = new ExchangeClient();
    private AuthenticationToken austriaToken = null;
    private AuthenticationToken croatiaToken = null;

    private Group austriaTokenNationallGroup = null;

    @Override
    public void setup() {
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
     * Numerous NPE's were found in the production logs on 2016-01-24. The cause
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

    /**
     * Preliminary test to verify that the new method is not causing a meltdown.
     * The Statistics View in the Database needs refinement, once we have a
     * clarification of the Statistics data, considering the current
     * (2014-01-05) mail discussion on how to deal with IW3 offers.
     *
     * Resolution is part of Trac task #547.
     */
    @Test
    public void testGettingStatistics() {
        final OfferStatisticsRequest request = new OfferStatisticsRequest();
        final OfferStatisticsResponse response = exchange.fetchOfferStatistics(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
    }

    /**
     * See trac bug report #451.
     */
    @Test
    public void testSavingOfferWithoutCountry() {
        final String refno = PL_YEAR + "-BUG451-R";
        final Offer offer = TestData.prepareFullOffer(refno, "Poland A/S");
        // Now setting the value to null, we need to go through some hoops here,
        // as our defensive copying will otherwise prevent the null from being
        // set properly!
        final Employer employer = offer.getEmployer();
        final Address address = employer.getAddress();
        address.setCountry(null);
        employer.setAddress(address);
        offer.setEmployer(employer);
        final ProcessOfferRequest request = new ProcessOfferRequest(offer);

        // Invoking the IWS with our null-country request
        final OfferResponse response = exchange.processOffer(token, request);
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
        assertThat(response.getOffer().getEmployer().getAddress().getCountry().getCountryCode(), is("PL"));

        // Find All offers, should review this one.
        final FetchOffersRequest fetchRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, fetchRequest);
        assertThat(fetchResponse.getOffers().isEmpty(), is(false));
        final Offer offerWithNS = fetchResponse.getOffers().get(0);
        assertThat(offerWithNS.getNsFirstname(), is("NS"));
        assertThat(offerWithNS.getNsLastname(), is("Poland"));
    }

    @Test
    public void testChangingOfferAndExchangeType() {
        final String refno = "PL-" + AbstractVerification.calculateExchangeYear() + "-00634743";
        final Offer initialOffer = TestData.prepareFullOffer(refno, "Poland A/S");

        // Save our offer.
        final ProcessOfferRequest request = new ProcessOfferRequest(initialOffer);
        final OfferResponse saveResponse = exchange.processOffer(token, request);
        assertThat(saveResponse.isOk(), is(true));
        assertThat(saveResponse.getOffer(), is(not(nullValue())));

        // Now, let's update the Offer & Exchange Types
        final Offer savedOffer = saveResponse.getOffer();
        savedOffer.setOfferType(OfferType.RESERVED);
        savedOffer.setExchangeType(ExchangeType.AC);
        request.setOffer(savedOffer);
        final OfferResponse updateResponse = exchange.processOffer(token, request);
        assertThat(updateResponse.isOk(), is(true));

        final Offer updatedOffer = updateResponse.getOffer();
        assertThat(initialOffer.getOfferType(), is(TestData.OFFER_TYPE));
        assertThat(updatedOffer.getOfferType(), is(not(TestData.OFFER_TYPE)));
        assertThat(updatedOffer.getOfferType(), is(OfferType.RESERVED));
        assertThat(initialOffer.getExchangeType(), is(TestData.OFFER_EXCHANGE_TYPE));
        assertThat(updatedOffer.getExchangeType(), is(not(TestData.OFFER_EXCHANGE_TYPE)));
        assertThat(updatedOffer.getExchangeType(), is(ExchangeType.AC));
        assertThat(updatedOffer.getRefNo(), is(refno));
        assertThat(updatedOffer.printableRefNo(), is(refno + OfferType.RESERVED.getType()));
    }

    @Test
    public void testDuplicateOffer() {
        final String refno = "PL-" + exchangeYear + "-123ABC-R";
        final Offer offer = TestData.prepareFullOffer(refno, "Poland A/S");
        final Offer duplicate = TestData.prepareFullOffer(refno, "Poland A/S");
        final ProcessOfferRequest request = new ProcessOfferRequest();

        // Save our first offer.
        request.setOffer(offer);
        final OfferResponse initial = exchange.processOffer(token, request);
        assertThat(initial.isOk(), is(true));
        assertThat(initial.getOffer(), is(not(nullValue())));
        assertThat(initial.getOffer().getOfferId(), is(not(nullValue())));

        // Now, attempt to save an identical offer.
        request.setOffer(duplicate);
        final OfferResponse failing = exchange.processOffer(token, request);
        assertThat(failing.isOk(), is(false));
        assertThat(failing.getError(), is(IWSErrors.OBJECT_IDENTIFICATION_ERROR));
        // We're just checking part of the message, since the trace id & refno
        // are not fixed values.
        assertThat(failing.getMessage(), containsString("An Offer with the Reference Number"));
    }

    @Test
    public void testDuplicatingOffer() {
        final Offer offer = TestData.prepareFullOffer("PL-" + exchangeYear + "-123457-C", "Poland A/S");
        final ProcessOfferRequest request = new ProcessOfferRequest();

        // Save our first offer.
        request.setOffer(offer);
        final OfferResponse initial = exchange.processOffer(token, request);
        assertThat(initial.isOk(), is(true));
        assertThat(initial.getOffer(), is(not(nullValue())));
        assertThat(initial.getOffer().getOfferId(), is(not(nullValue())));

        // Now, let's duplicate the Offer, and give it a new RefNo
        final Offer duplicate = initial.getOffer();
        duplicate.setRefNo("PL-" + exchangeYear + "-123458-L");
        duplicate.setOfferId(null);
        request.setOffer(duplicate);
        final OfferResponse duplication = exchange.processOffer(token, request);
        assertThat(duplication.isOk(), is(true));
    }

    /**
     * Trac Bug report #418, indicates that the Exchange Transformer was faulty,
     * and thereby causing problems with updating an existing Offer.
     */
    @Test
    public void testUpdateExistingOffer() {
        final Offer initialOffer = TestData.prepareFullOffer("PL-" + exchangeYear + "-654321-C", "Poland GmbH");
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

        // Now, we have the initial Offer, and the updated Offer
        assertThat(updatedOffer.getOfferId(), is(savedOffer.getOfferId()));
        assertThat(updatedOffer.getModified().isAfter(savedOffer.getModified()), is(true));
        // In the database, we're storing the created time as a Timestamp, in
        // the Entity, it is a Date Object. Since there's a precision loss
        // between the two. When being read out, it can happen that the
        // timestamp is rounded up to the next second, so the result causes a
        // test failure. In other words, please don't compare the dates
        // directly - after all, the timestamps are just there to make sure we
        // know when something happened and for this, it is good enough.
        //assertThat(updatedOffer.getCreated().toString(), is(savedOffer.getCreated().toString()));
        assertThat(updatedOffer.getWorkDescription(), is(not(initialOffer.getWorkDescription())));
        assertThat(updatedOffer.getWorkDescription(), is("Whatever"));

        assertThat(updatedOffer.getNsFirstname(), is(not(nullValue())));
        assertThat(updatedOffer.getNsLastname(), is(not(nullValue())));
    }

    /**
     * <p>Trac Bug report #1100, there is a problem with the processOffer
     * method, whereby it is possible to change the state of an existing Offer.
     * Changing the State should never be possible via processing, only via
     * the State Scheduler or Publishing of an Offer.</p>
     *
     * <p>Note, that after having written this test, a problem was found, and
     * that was that the Offer returned was a copy of the given Object, which
     * was not the same as the updated Object in the Database, that flaw has
     * now been fixed, but it doesn't answer why some Offers have been set to
     * Expired in Production!</p>
     */
    @Test
    public void testUpdateOfferState() {
        final Offer initialOffer = TestData.prepareFullOffer("PL-" + exchangeYear + "-754321-C", "Poland GmbH");
        final ProcessOfferRequest request = new ProcessOfferRequest();
        request.setOffer(initialOffer);
        final OfferResponse saveResponse = exchange.processOffer(token, request);
        assertThat(saveResponse, is(not(nullValue())));
        assertThat(saveResponse.isOk(), is(true));

        // Verify that the Offer was saved and has state NEW.
        final Offer savedOffer = saveResponse.getOffer();
        assertThat(savedOffer, is(not(nullValue())));
        assertThat(savedOffer.getStatus(), is(OfferState.NEW));

        // Update the State to Expired, and save it again.
        savedOffer.setStatus(OfferState.EXPIRED);
        request.setOffer(savedOffer);
        final OfferResponse updateResponse = exchange.processOffer(token, request);

        // Now, let's verify that everything is as expected.
        assertThat(updateResponse, is(not(nullValue())));
        assertThat(updateResponse.isOk(), is(true));
        final Offer updatedOffer = updateResponse.getOffer();
        assertThat(updatedOffer, is(not(nullValue())));
        assertThat(updatedOffer.getStatus(), is(OfferState.NEW));

        // Let's also try to fetch the Offer via the Id, so we can check it. As
        // one flaw was that the given record was returned.
        final FetchOffersRequest fetchRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        fetchRequest.setIdentifiers(Collections.singletonList(initialOffer.getRefNo()));
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, fetchRequest);
        assertThat(fetchResponse.isOk(), is(true));
        assertThat(fetchResponse.getOffers().size(), is(1));
        assertThat(fetchResponse.getOffers().get(0).getStatus(), is(OfferState.NEW));
    }

    @Test
    public void testProcessOfferWithInvalidRefno() {
        // We're logged in as Poland, so the Offer must start with "PL".
        final String refno = "GB-" + exchangeYear + "-000001";
        final Offer minimalOffer = TestData.prepareMinimalOffer(refno, "British Employer", "AT");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(minimalOffer);
        final OfferResponse processResponse = exchange.processOffer(token, offerRequest);

        // verify processResponse
        assertThat(processResponse.isOk(), is(false));
        assertThat(processResponse.getError(), is(IWSErrors.VERIFICATION_ERROR));
        assertThat(processResponse.getMessage(), is("The reference number is not valid for this country. Received 'GB' but expected 'PL'."));
    }

    @Test
    public void testProcessOfferCreateMinimalOffer() {
        final String refno = PL_YEAR + "-000001";
        final Offer minimalOffer = TestData.prepareMinimalOffer(refno, "Polish Employer");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(minimalOffer);
        final OfferResponse processResponse = exchange.processOffer(token, offerRequest);
        final String refNo = processResponse.getOffer().getRefNo();

        // verify processResponse
        assertThat(processResponse.isOk(), is(true));

        // check if minimalOffer is persisted
        final FetchOffersRequest request = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, request);
        final Offer readOffer = findOfferFromResponse(refNo, fetchResponse);

        assertThat(readOffer, is(not(nullValue())));
    }

    @Test
    public void testProcessOfferCreateFullOffer() {
        final String refno = PL_YEAR + "-000002";
        final Offer fullOffer = TestData.prepareFullOffer(refno, "Polish Employer");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(fullOffer);
        final OfferResponse processResponse = exchange.processOffer(token, offerRequest);
        final String refNo = processResponse.getOffer().getRefNo();

        // verify processResponse
        assertThat(processResponse.isOk(), is(true));

        // check if fullOffer is persisted
        final FetchOffersRequest request = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, request);
        final Offer readOffer = findOfferFromResponse(refNo, fetchResponse);

        assertThat(readOffer, is(not(nullValue())));
    }

    @Test
    public void testDeleteNewOffer() {
        final String refno = PL_YEAR + "-000003";
        final Offer offer = TestData.prepareMinimalOffer(refno, "Polish Employer");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);

        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse response = exchange.fetchOffers(token, request);
        assertThat(response.getOffers().isEmpty(), is(false));
        final int size = response.getOffers().size();

        final Offer offerToDelete = findOfferFromResponse(saveResponse.getOffer().getRefNo(), response);

        final DeleteOfferRequest deleteRequest = new DeleteOfferRequest(offerToDelete.getOfferId());
        final OfferResponse deleteResponse = exchange.deleteOffer(token, deleteRequest);

        assertThat(deleteResponse.isOk(), is(true));
        final FetchOffersRequest fetchRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, fetchRequest);
        assertThat(fetchResponse.getOffers().size(), is(size - 1));

        for (final Offer o : fetchResponse.getOffers()) {
            if (o.getRefNo().equals(offerToDelete.getRefNo())) {
                fail("offer is supposed to be deleted");
            }
        }
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
    public void testNumberOfHardCopies() {
        final String refNo = PL_YEAR + "-000042";
        final Offer newOffer = TestData.prepareFullOffer(refNo, "Employer");
        newOffer.setRefNo(refNo);
        newOffer.setNumberOfHardCopies(2);

        // Persist Offer, verify that everything went well
        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(newOffer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);
        assertThat(saveResponse.isOk(), is(true));

        // Read Offer, and verify that the NumberOfHardCopies is present
        final FetchOffersRequest findSavedRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse findSavedResponse = exchange.fetchOffers(token, findSavedRequest);
        final Offer readOffer = findOfferFromResponse(refNo, findSavedResponse);
        assertThat(readOffer, is(not(nullValue())));
        assertThat(readOffer.getNumberOfHardCopies(), is(2));
        assertThat(readOffer.getOfferId(), is(not(nullValue())));

        // Update the Offer, with a new value for NumberOfHardCopies
        readOffer.setNumberOfHardCopies(3);
        final ProcessOfferRequest updateOfferRequest = new ProcessOfferRequest(readOffer);
        assertThat(exchange.processOffer(token, updateOfferRequest).isOk(), is(true));

        // Update the Offer, and verify that the changes are saved.
        final FetchOffersRequest findupdatedRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse findUpdatedResponse = exchange.fetchOffers(token, findupdatedRequest);
        final Offer updatedOffer = findOfferFromResponse(refNo, findUpdatedResponse);
        assertThat(updatedOffer, is(not(nullValue())));
        assertThat(updatedOffer.getNumberOfHardCopies(), is(3));
    }

    @Test
    public void testFetchForeignOffer() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final String refNo = AT_YEAR + "-000001";
        final Offer offer = TestData.prepareMinimalOffer(refNo, "Austrian Employer");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse processResponse = exchange.processOffer(austriaTokenWithNationalGroup, offerRequest);

        assertThat("verify that the offer was persisted", processResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(croatiaToken, request);
        final Offer readOffer = findOfferFromResponse(refNo, fetchResponse);

        assertThat("as the Austrian offer was not shared with Croatia, it shouldn't be loaded", readOffer, is(nullValue()));
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

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
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

        final ProcessOfferRequest saveRequest2 = new ProcessOfferRequest(offer);
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

        final ProcessOfferRequest saveRequest1 = new ProcessOfferRequest(offer);
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

        final ProcessOfferRequest saveRequest2 = new ProcessOfferRequest(offer);
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

    @Test
    public void testAdditionalInformation() {
        final String additionalInformatin = "My Additional stuff.";
        final Offer offer = TestData.prepareFullOffer(PL_YEAR + "-456457-C", "Poland A/S");
        offer.setAdditionalInformation(additionalInformatin);
        final ProcessOfferRequest request = new ProcessOfferRequest();

        request.setOffer(offer);
        final OfferResponse response = exchange.processOffer(token, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getOffer(), is(not(nullValue())));
        assertThat(response.getOffer().getOfferId(), is(not(nullValue())));
        assertThat(response.getOffer().getAdditionalInformation(), is(additionalInformatin));
    }

    @Test
    public void testHideForeignOffer() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Offer offer = TestData.prepareMinimalOffer(PL_YEAR + "-TIC772-R", "Poland A/S");
        final Date nominationDeadline = new Date().plusDays(20);

        final ProcessOfferRequest saveRequest = new ProcessOfferRequest(offer);
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
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Offer offer = TestData.prepareMinimalOffer(PL_YEAR + "-TI806A-R", "Poland A/S");
        final Date nominationDeadline = new Date().plusDays(20);

        final ProcessOfferRequest saveRequest = new ProcessOfferRequest(offer);
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
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Offer offer = TestData.prepareMinimalOffer(PL_YEAR + "-TI806B-R", "Poland A/S");
        final Date nominationDeadline = new Date().plusDays(20);

        final ProcessOfferRequest saveRequest = new ProcessOfferRequest(offer);
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
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Offer offer = TestData.prepareMinimalOffer(PL_YEAR + "-TI806C-R", "Poland A/S");
        final Date nominationDeadline = new Date().plusDays(20);

        final ProcessOfferRequest saveRequest = new ProcessOfferRequest(offer);
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
    public void testCsvWorkflow() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Offer offer = TestData.prepareFullOffer(AT_YEAR + "-01T453-R", "Austria A/S");
        final Date nominationDeadline = new Date().plusDays(20);

        final ProcessOfferRequest saveRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(austriaTokenWithNationalGroup, saveRequest);
        assertThat(saveResponse.isOk(), is(true));

        final Set<String> offersToShare = new HashSet<>(1);
        offersToShare.add(saveResponse.getOffer().getOfferId());

        final List<String> groupIds = new ArrayList<>(2);
        groupIds.add(findNationalGroup(croatiaToken).getGroupId());

        final PublishOfferRequest publishRequest = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse = exchange.processPublishOffer(austriaTokenWithNationalGroup, publishRequest);
        assertThat(publishResponse.getError(), is(IWSErrors.SUCCESS));
        assertThat(publishResponse.isOk(), is(true));

        final OfferCSVDownloadRequest outboxCsvRequest = new OfferCSVDownloadRequest(FetchType.DOMESTIC, new ArrayList<String>(0), AbstractVerification.calculateExchangeYear());
        final OfferCSVDownloadResponse outboxCsvResponse = exchange.downloadOffers(austriaTokenWithNationalGroup, outboxCsvRequest);

        assertThat(outboxCsvResponse.isOk(), is(true));
        assertThat(outboxCsvResponse.getData(), is(not(nullValue())));

        final OfferCSVDownloadRequest inboxCsvRequest = new OfferCSVDownloadRequest(FetchType.SHARED, new ArrayList<String>(0), AbstractVerification.calculateExchangeYear());
        final OfferCSVDownloadResponse inboxCsvResponse = exchange.downloadOffers(croatiaToken, inboxCsvRequest);

        assertThat(inboxCsvResponse.isOk(), is(true));
        assertThat(inboxCsvResponse.getData(), is(not(nullValue())));

        final OfferCSVUploadRequest uploadRequest = new OfferCSVUploadRequest(outboxCsvResponse.getData(), OfferCSVUploadRequest.FieldDelimiter.COMMA);
        final OfferCSVUploadResponse uploadResponse = exchange.uploadOffers(austriaTokenWithNationalGroup, uploadRequest);
        assertThat(uploadResponse.isOk(), is(true));
    }

    /**
     * <p>On January 12, 2016 - Germany tried to upload an Offer with an invalid
     * Reference Number, however - it resulted in a Stack trace in the logs,
     * which was unexpected, as the error handling should've prevented it. So,
     * it seems that internally in the Reflection mechanism - the expected
     * IllegalArgument Exception is converted to an InvocationException.</p>
     *
     * <p>Test is written to ensure that we get the correct error information
     * in the end.</p>
     */
    @Test
    public void testInvalidRefNoInCsv() throws UnsupportedEncodingException {
        // First, we need a valid offer which we can download as CSV and change
        // to a new, different Offer
        final AuthenticationToken germany = login("germany@iaeste.de", "germany");
        final String refno = "DE-" + AbstractVerification.calculateExchangeYear() + "-00123456";
        final String invalidRefno = refno + "123";
        final Offer initialOffer = TestData.prepareFullOffer(refno, "Germany A/S");

        final ProcessOfferRequest processRequest = new ProcessOfferRequest();
        processRequest.setOffer(initialOffer);
        final ProcessOfferRequest request = new ProcessOfferRequest(initialOffer);
        final OfferResponse saveResponse = exchange.processOffer(germany, request);
        assertThat(saveResponse.isOk(), is(true));

        final OfferCSVDownloadRequest downloadRequest = new OfferCSVDownloadRequest();
        downloadRequest.setFetchType(FetchType.DOMESTIC);
        final OfferCSVDownloadResponse downloadResponse = exchange.downloadOffers(germany, downloadRequest);
        assertThat(downloadResponse.isOk(), is(true));

        // Okay, preparations is in place. Now we're replacing the refno with
        // one that exceeds the allowed size. This should result in the refno
        // Setter to throw an IllegalArgumentException
        final String originalCSV = new String(downloadResponse.getData(), IWSConstants.DEFAULT_ENCODING);
        final String newCSV = originalCSV.replace(refno, invalidRefno);

        final OfferCSVUploadRequest uploadRequest = new OfferCSVUploadRequest();
        uploadRequest.setData(newCSV.getBytes(IWSConstants.DEFAULT_ENCODING));
        final OfferCSVUploadResponse uploadResponse = exchange.uploadOffers(germany, uploadRequest);

        assertThat(uploadResponse.isOk(), is(true));
        final Map<String, CSVProcessingErrors> result = uploadResponse.getErrors();
        assertThat(result.size(), is(1));
        final Map<String, String> errors = result.get(invalidRefno).getCsvErrors();
        assertThat(errors.get("Ref.No"), is("The provided reference number (refno) " + invalidRefno + " is invalid."));

        logout(germany);
    }

    /**
     * <p>On January 12, 2016 - Germany tried to upload an Offer with an invalid
     * Language reference, however - it resulted in a Stack trace in the logs,
     * which was unexpected, as the error handling should've prevented it. So,
     * it seems that internally in the Reflection mechanism - the expected
     * IllegalArgument Exception is converted to an InvocationException.</p>
     *
     * <p>Test is written to ensure that we get the correct error information
     * in the end.</p>
     */
    @Test
    public void testInvalidLanguageInCsv() throws UnsupportedEncodingException {
        // First, we need a valid offer which we can download as CSV and change
        // to a new, different Offer
        final AuthenticationToken germany = login("germany@iaeste.de", "germany");
        final String refno = "DE-" + AbstractVerification.calculateExchangeYear() + "-00123457";
        final Offer initialOffer = TestData.prepareFullOffer(refno, "Germany A/S");

        final ProcessOfferRequest processRequest = new ProcessOfferRequest();
        processRequest.setOffer(initialOffer);
        final ProcessOfferRequest request = new ProcessOfferRequest(initialOffer);
        final OfferResponse saveResponse = exchange.processOffer(germany, request);
        assertThat(saveResponse.isOk(), is(true));

        final OfferCSVDownloadRequest downloadRequest = new OfferCSVDownloadRequest();
        downloadRequest.setFetchType(FetchType.DOMESTIC);
        final OfferCSVDownloadResponse downloadResponse = exchange.downloadOffers(germany, downloadRequest);
        assertThat(downloadResponse.isOk(), is(true));

        // Okay, preparations is in place. Now we're replacing the language with
        // one that is not allowed. This should result in the Language setter
        // throwing an IllegalArgument Exception
        final String originalCSV = new String(downloadResponse.getData(), IWSConstants.DEFAULT_ENCODING);
        final String newCSV = originalCSV.replace("English", "ENGLISCH");

        final OfferCSVUploadRequest uploadRequest = new OfferCSVUploadRequest();
        uploadRequest.setData(newCSV.getBytes(IWSConstants.DEFAULT_ENCODING));
        final OfferCSVUploadResponse uploadResponse = exchange.uploadOffers(germany, uploadRequest);

        assertThat(uploadResponse.isOk(), is(true));
        final Map<String, CSVProcessingErrors> result = uploadResponse.getErrors();
        assertThat(result.size(), is(1));
        final Map<String, String> errors = result.get(refno).getCsvErrors();
        assertThat(errors.get("Language1"), is("No enum constant net.iaeste.iws.api.enums.Language.ENGLISCH"));

        logout(germany);
    }

    /**
     * During the 2015 Annual Conference an Offer was inserted in into the IW,
     * and caused subsequent problems as it contained 5 Specializations,
     * although only 3 is allowed. After inquiring the delegation who created
     * the Offer, they informed that they had read the CSV upload documentation
     * stating that the Specializations must be delimited by pipes "|". So
     * instead of using the normal Form in IW4 to upload the offers, they added
     * all of them with pipes.<br />
     *   The result was that the Offer DTO had a set of Strings with only one
     * value (the delimited specializations), thus omitting the standard checks
     * in the Offer DTO. When inserted. the Set is expanded into a pipe "|"
     * delimited String and that all worked fine, but reading it out again
     * caused an error.<br />
     *   This test is replicating the issue and will demonstrate the flaw, and
     * also the fix. For more information, see Trac ticket #971 (#966).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLongStringSetsTicket971() {
        final Offer offer = TestData.prepareMinimalOffer(PL_YEAR + "-SPECIAL", "Specialization Employer");
        final Set<String> specializations = new HashSet<>();
        final String specialization = "First | Second | Third | Fourth | Fifth";
        specializations.add(specialization);
        offer.setSpecializations(specializations);

        final ProcessOfferRequest request = new ProcessOfferRequest(offer);
        final OfferResponse response = exchange.processOffer(token, request);
        assertThat(response.isOk(), is(true));
    }

    private static Offer findOfferFromResponse(final String refno, final FetchOffersResponse response) {
        // As the IWS is replacing the new Reference Number with the correct
        // year, the only valid information to go on is the running number.
        // Hence, we're skipping everything before that
        final String refNoLowerCase = refno.toLowerCase(IWSConstants.DEFAULT_LOCALE).substring(8);
        Offer offer = null;

        for (final Offer found : response.getOffers()) {
            final String foundRefNo = found.getRefNo().toLowerCase(IWSConstants.DEFAULT_LOCALE);
            if (foundRefNo.contains(refNoLowerCase)) {
                offer = found;
            }
        }

        return offer;
    }

    private static Group findGroupFromResponse(final String offerId, final String groupId, final FetchPublishedGroupsResponse response) {
        Group group = null;

        for (final Group found : response.getOffersGroups().get(offerId).getGroups()) {
            if (found.getGroupId().equals(groupId)) {
                group = found;
            }
        }

        return group;
    }
}
