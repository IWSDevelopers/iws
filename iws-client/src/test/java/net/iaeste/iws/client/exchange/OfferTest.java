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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.TestData;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.ExchangeType;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.LanguageOperator;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.enums.exchange.OfferType;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse;
import net.iaeste.iws.api.util.Verifications;
import net.iaeste.iws.client.ExchangeClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class OfferTest extends AbstractOfferTest {

    private static final int exchangeYear = Verifications.calculateExchangeYear();
    private static final String PL_YEAR = "PL-" + exchangeYear;

    private final Exchange exchange = new ExchangeClient();
    private AuthenticationToken austriaToken = null;
    private AuthenticationToken croatiaToken = null;

    @Override
    public void setUp() {
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

    @Test
    public void testChangingOfferAndExchangeType() {
        final String refno = "PL-" + Verifications.calculateExchangeYear() + "-00634743";
        final Offer initialOffer = TestData.prepareFullOffer(refno, "Poland A/S");

        // Save our offer.
        final ProcessOfferRequest request = prepareRequest(initialOffer);
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

        final ProcessOfferRequest offerRequest = prepareRequest(minimalOffer);
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

        final ProcessOfferRequest offerRequest = prepareRequest(minimalOffer);
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

        final ProcessOfferRequest offerRequest = prepareRequest(fullOffer);
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

        final ProcessOfferRequest offerRequest = prepareRequest(offer);
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
    public void testNumberOfHardCopies() {
        final String refNo = PL_YEAR + "-000042";
        final Offer newOffer = TestData.prepareFullOffer(refNo, "Employer");
        newOffer.setRefNo(refNo);
        newOffer.setNumberOfHardCopies(2);

        // Persist Offer, verify that everything went well
        final ProcessOfferRequest offerRequest = prepareRequest(newOffer);
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
        final ProcessOfferRequest updateOfferRequest = prepareRequest(readOffer);
        assertThat(exchange.processOffer(token, updateOfferRequest).isOk(), is(true));

        // Update the Offer, and verify that the changes are saved.
        final FetchOffersRequest findupdatedRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse findUpdatedResponse = exchange.fetchOffers(token, findupdatedRequest);
        final Offer updatedOffer = findOfferFromResponse(refNo, findUpdatedResponse);
        assertThat(updatedOffer, is(not(nullValue())));
        assertThat(updatedOffer.getNumberOfHardCopies(), is(3));
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

    /**
     * Simple test to verify that Offers are corrected by the IWS, so missing
     * Language information is presented.
     */
    @Test
    public void testLanguageFieldsMissing() {
        final Offer offer = TestData.prepareMinimalOffer(PL_YEAR + "-LANG01", "Language Employer");
        offer.setLanguage1Operator(null);
        offer.setLanguage2(Language.BELARUSIAN);
        offer.setLanguage2Level(null);
        offer.setLanguage2Operator(null);
        offer.setLanguage3(Language.LATVIAN);
        offer.setLanguage3Level(null);

        final ProcessOfferRequest request = prepareRequest(offer);
        final OfferResponse response = exchange.processOffer(token, request);
        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));
        final Offer saved = response.getOffer();
        assertThat(saved.getLanguage1(), is(Language.ENGLISH));
        assertThat(saved.getLanguage1Level(), is(LanguageLevel.E));
        assertThat(saved.getLanguage1Operator(), is(LanguageOperator.A));
        assertThat(saved.getLanguage2(), is(Language.BELARUSIAN));
        assertThat(saved.getLanguage2Level(), is(LanguageLevel.G));
        assertThat(saved.getLanguage2Operator(), is(LanguageOperator.A));
        assertThat(saved.getLanguage3(), is(Language.LATVIAN));
        assertThat(saved.getLanguage3Level(), is(LanguageLevel.G));

        final FetchOffersRequest fetchRequest = new FetchOffersRequest();
        final List<String> offerIds = new ArrayList<>();
        offerIds.add(offer.getRefNo());
        fetchRequest.setIdentifiers(offerIds);
        fetchRequest.setFetchType(FetchType.DOMESTIC);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, fetchRequest);
        assertThat(fetchResponse.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(fetchResponse.getOffers().size(), is(1));
        final Offer found = fetchResponse.getOffers().get(0);
        assertThat(found.getLanguage1(), is(Language.ENGLISH));
        assertThat(found.getLanguage1Level(), is(LanguageLevel.E));
        assertThat(found.getLanguage1Operator(), is(LanguageOperator.A));
        assertThat(found.getLanguage2(), is(Language.BELARUSIAN));
        assertThat(found.getLanguage2Level(), is(LanguageLevel.G));
        assertThat(found.getLanguage2Operator(), is(LanguageOperator.A));
        assertThat(found.getLanguage3(), is(Language.LATVIAN));
        assertThat(found.getLanguage3Level(), is(LanguageLevel.G));
    }

    /**
     * Simple test to verify that Offers are corrected by the IWS, so invalid
     * Language information is no longer presented.
     */
    @Test
    public void testLanguage2Missing() {
        final Offer offer = TestData.prepareMinimalOffer(PL_YEAR + "-LANG02", "Language Employer");
        offer.setLanguage1Operator(LanguageOperator.A);
        offer.setLanguage2(null);
        offer.setLanguage2Level(LanguageLevel.E);
        offer.setLanguage2Operator(LanguageOperator.A);
        offer.setLanguage3(Language.LATVIAN);
        offer.setLanguage3Level(LanguageLevel.E);

        final ProcessOfferRequest request = prepareRequest(offer);
        final OfferResponse response = exchange.processOffer(token, request);
        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));
        final Offer saved = response.getOffer();
        assertThat(saved.getLanguage1(), is(Language.ENGLISH));
        assertThat(saved.getLanguage1Level(), is(LanguageLevel.E));
        assertThat(saved.getLanguage1Operator(), is(nullValue()));
        assertThat(saved.getLanguage2(), is(nullValue()));
        assertThat(saved.getLanguage2Level(), is(nullValue()));
        assertThat(saved.getLanguage2Operator(), is(nullValue()));
        assertThat(saved.getLanguage3(), is(nullValue()));
        assertThat(saved.getLanguage3Level(), is(nullValue()));

        final FetchOffersRequest fetchRequest = new FetchOffersRequest();
        final List<String> offerIds = new ArrayList<>();
        offerIds.add(offer.getRefNo());
        fetchRequest.setIdentifiers(offerIds);
        fetchRequest.setFetchType(FetchType.DOMESTIC);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, fetchRequest);
        assertThat(fetchResponse.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(fetchResponse.getOffers().size(), is(1));
        final Offer found = fetchResponse.getOffers().get(0);
        assertThat(found.getLanguage1(), is(Language.ENGLISH));
        assertThat(found.getLanguage1Level(), is(LanguageLevel.E));
        assertThat(found.getLanguage1Operator(), is(nullValue()));
        assertThat(found.getLanguage2(), is(nullValue()));
        assertThat(found.getLanguage2Level(), is(nullValue()));
        assertThat(found.getLanguage2Operator(), is(nullValue()));
        assertThat(found.getLanguage3(), is(nullValue()));
        assertThat(found.getLanguage3Level(), is(nullValue()));
    }

    /**
     * Simple test to verify that Offers are corrected by the IWS, so invalid
     * Language information is no longer presented.
     */
    @Test
    public void testLanguage3Missing() {
        final Offer offer = TestData.prepareMinimalOffer(PL_YEAR + "-LANG03", "Language Employer");
        offer.setLanguage1Operator(LanguageOperator.A);
        offer.setLanguage2(Language.BELARUSIAN);
        offer.setLanguage2Level(LanguageLevel.E);
        offer.setLanguage2Operator(LanguageOperator.A);
        offer.setLanguage3(null);
        offer.setLanguage3Level(LanguageLevel.E);

        final ProcessOfferRequest request = prepareRequest(offer);
        final OfferResponse response = exchange.processOffer(token, request);
        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));
        final Offer saved = response.getOffer();
        assertThat(saved.getLanguage1(), is(Language.ENGLISH));
        assertThat(saved.getLanguage1Level(), is(LanguageLevel.E));
        assertThat(saved.getLanguage1Operator(), is(LanguageOperator.A));
        assertThat(saved.getLanguage2(), is(Language.BELARUSIAN));
        assertThat(saved.getLanguage2Level(), is(LanguageLevel.E));
        assertThat(saved.getLanguage2Operator(), is(nullValue()));
        assertThat(saved.getLanguage3(), is(nullValue()));
        assertThat(saved.getLanguage3Level(), is(nullValue()));

        final FetchOffersRequest fetchRequest = new FetchOffersRequest();
        final List<String> offerIds = new ArrayList<>();
        offerIds.add(offer.getRefNo());
        fetchRequest.setIdentifiers(offerIds);
        fetchRequest.setFetchType(FetchType.DOMESTIC);
        final FetchOffersResponse fetchResponse = exchange.fetchOffers(token, fetchRequest);
        assertThat(fetchResponse.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(fetchResponse.getOffers().size(), is(1));
        final Offer found = fetchResponse.getOffers().get(0);
        assertThat(found.getLanguage1(), is(Language.ENGLISH));
        assertThat(found.getLanguage1Level(), is(LanguageLevel.E));
        assertThat(found.getLanguage1Operator(), is(LanguageOperator.A));
        assertThat(found.getLanguage2(), is(Language.BELARUSIAN));
        assertThat(found.getLanguage2Level(), is(LanguageLevel.E));
        assertThat(found.getLanguage2Operator(), is(nullValue()));
        assertThat(found.getLanguage3(), is(nullValue()));
        assertThat(found.getLanguage3Level(), is(nullValue()));
    }
}
