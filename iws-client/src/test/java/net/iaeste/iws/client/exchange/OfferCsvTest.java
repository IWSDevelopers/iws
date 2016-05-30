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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.TestData;
import net.iaeste.iws.api.dtos.exchange.CSVProcessingErrors;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVUploadRequest;
import net.iaeste.iws.api.requests.exchange.OfferRequest;
import net.iaeste.iws.api.responses.exchange.OfferCSVDownloadResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVUploadResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.Verifications;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Testing of the CSV handling of Offers.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public final class OfferCsvTest extends AbstractOfferTest {

    private static final int exchangeYear = Verifications.calculateExchangeYear();
    private static final String AT_YEAR = "AT-" + exchangeYear;
    private static final Pattern PATTERN_ENGLISH = Pattern.compile("English", Pattern.LITERAL);

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
    public void testCsvWorkflow() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());

        final Offer offer = TestData.prepareFullOffer(AT_YEAR + "-01T453-R", "Austria A/S");
        final Date nominationDeadline = new Date().plusDays(20);

        final OfferRequest saveRequest = prepareRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(austriaTokenWithNationalGroup, saveRequest);
        assertThat(saveResponse.isOk(), is(true));

        final List<String> groupIds = new ArrayList<>(2);
        groupIds.add(findNationalGroup(croatiaToken).getGroupId());

        publishOfferWithoutCheck(token, saveResponse.getOffer(), nominationDeadline, groupIds);

        final FetchOffersRequest outboxCsvRequest = new FetchOffersRequest();
        outboxCsvRequest.setFetchType(FetchType.DOMESTIC);
        final OfferCSVDownloadResponse outboxCsvResponse = exchange.downloadOffers(austriaTokenWithNationalGroup, outboxCsvRequest);

        assertThat(outboxCsvResponse.isOk(), is(true));
        assertThat(outboxCsvResponse.getCsv(), is(not(nullValue())));

        final FetchOffersRequest inboxCsvRequest = new FetchOffersRequest();
        inboxCsvRequest.setFetchType(FetchType.SHARED);
        final OfferCSVDownloadResponse inboxCsvResponse = exchange.downloadOffers(croatiaToken, inboxCsvRequest);

        assertThat(inboxCsvResponse.isOk(), is(true));
        assertThat(inboxCsvResponse.getCsv(), is(not(nullValue())));

        final OfferCSVUploadRequest uploadRequest = new OfferCSVUploadRequest(outboxCsvResponse.getCsv(), OfferCSVUploadRequest.FieldDelimiter.COMMA);
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
    public void testInvalidRefNoInCsv() {
        // First, we need a valid offer which we can download as CSV and change
        // to a new, different Offer
        final AuthenticationToken germany = login("germany@iaeste.de", "germany");
        final String refno = "DE-" + Verifications.calculateExchangeYear() + "-00123456";
        final String invalidRefno = refno + "123";
        final Offer initialOffer = TestData.prepareFullOffer(refno, "Germany A/S");

        final OfferRequest processRequest = new OfferRequest();
        processRequest.setOffer(initialOffer);
        final OfferRequest request = prepareRequest(initialOffer);
        final OfferResponse saveResponse = exchange.processOffer(germany, request);
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest downloadRequest = new FetchOffersRequest();
        downloadRequest.setFetchType(FetchType.DOMESTIC);
        final OfferCSVDownloadResponse downloadResponse = exchange.downloadOffers(germany, downloadRequest);
        assertThat(downloadResponse.isOk(), is(true));

        // Okay, preparations is in place. Now we're replacing the refno with
        // one that exceeds the allowed size. This should result in the refno
        // Setter to throw an IllegalArgumentException
        final String originalCSV = downloadResponse.getCsv();
        final String newCSV = originalCSV.replace(refno, invalidRefno);

        final OfferCSVUploadRequest uploadRequest = new OfferCSVUploadRequest();
        uploadRequest.setCsv(newCSV);
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
    public void testInvalidLanguageInCsv() {
        // First, we need a valid offer which we can download as CSV and change
        // to a new, different Offer
        final AuthenticationToken germany = login("germany@iaeste.de", "germany");
        final String refno = "DE-" + Verifications.calculateExchangeYear() + "-00123457";
        final Offer initialOffer = TestData.prepareFullOffer(refno, "Germany A/S");

        final OfferRequest processRequest = new OfferRequest();
        processRequest.setOffer(initialOffer);
        final OfferRequest request = prepareRequest(initialOffer);
        final OfferResponse saveResponse = exchange.processOffer(germany, request);
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest downloadRequest = new FetchOffersRequest();
        downloadRequest.setFetchType(FetchType.DOMESTIC);
        final OfferCSVDownloadResponse downloadResponse = exchange.downloadOffers(germany, downloadRequest);
        assertThat(downloadResponse.isOk(), is(true));

        // Okay, preparations is in place. Now we're replacing the language with
        // one that is not allowed. This should result in the Language setter
        // throwing an IllegalArgument Exception
        final String originalCSV = downloadResponse.getCsv();
        final String newCSV = PATTERN_ENGLISH.matcher(originalCSV).replaceAll(Matcher.quoteReplacement("ENGLISCH"));

        final OfferCSVUploadRequest uploadRequest = new OfferCSVUploadRequest();
        uploadRequest.setCsv(newCSV);
        final OfferCSVUploadResponse uploadResponse = exchange.uploadOffers(germany, uploadRequest);

        assertThat(uploadResponse.getMessage(), is(IWSConstants.SUCCESS));
        final Map<String, CSVProcessingErrors> result = uploadResponse.getErrors();
        assertThat(result.size(), is(1));
        final Map<String, String> errors = result.get(refno).getCsvErrors();
        assertThat(errors.get("Language1"), is("No enum constant net.iaeste.iws.api.enums.Language.ENGLISCH"));

        logout(germany);
    }
}
