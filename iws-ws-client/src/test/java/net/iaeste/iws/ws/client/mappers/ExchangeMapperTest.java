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
package net.iaeste.iws.ws.client.mappers;

import static net.iaeste.iws.ws.client.mappers.ExchangeMapper.map;
import static net.iaeste.iws.ws.client.mappers.TestUtils.prepareIwsError;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.TestData;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.dtos.exchange.PublishingGroup;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.requests.exchange.EmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.requests.exchange.HideForeignOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVUploadRequest;
import net.iaeste.iws.api.requests.exchange.OfferRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.requests.exchange.ProcessPublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.RejectOfferRequest;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishingGroupResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVDownloadResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVUploadResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.ws.EmployerFetchType;
import net.iaeste.iws.ws.OfferStatistics;
import net.iaeste.iws.ws.SortingField;
import net.iaeste.iws.ws.SortingOrder;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public final class ExchangeMapperTest {

    /**
     * <p>Private methods should never be tested, as they are part of an
     * internal workflow. Classes should always be tested via their contract,
     * i.e. public methods.</p>
     *
     * <p>However, for Utility Classes, with a Private Constructor, the contract
     * disallows instantiation, so the constructor is thus not testable via
     * normal means. This little Test method will just do that.</p>
     */
    @Test
    public void testPrivateConstructor() {
        try {
            final Constructor<ExchangeMapper> constructor = ExchangeMapper.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            final ExchangeMapper mapper = constructor.newInstance();
            assertThat(mapper, is(not(nullValue())));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            fail("Could not invoke Private Constructor: " + e.getMessage());
        }
    }

    @Test
    public void testMappingEmployer() {
        final Employer fullEmployer = TestData.prepareEmployer("EmployerName", "AT");
        final Employer nullEmployer = null;

        final Employer mappedFullEmployer = map(map(fullEmployer));
        final Employer mappedNullEmployer = map(map(nullEmployer));

        assertThat(mappedNullEmployer, is(nullEmployer));
        assertThat(mappedFullEmployer, is(not(nullValue())));
        assertThat(mappedFullEmployer.getEmployerId(), is(fullEmployer.getEmployerId()));
        // Testing Groups is done elsewhere, here we just need to ensure that it is mapped over
        assertThat(mappedFullEmployer.getGroup().getFullName(), is(fullEmployer.getGroup().getFullName()));
        assertThat(mappedFullEmployer.getName(), is(fullEmployer.getName()));
        assertThat(mappedFullEmployer.getDepartment(), is(fullEmployer.getDepartment()));
        assertThat(mappedFullEmployer.getBusiness(), is(fullEmployer.getBusiness()));
        // Testing Addresses is done elsewhere, here we just need to ensure that it is mapped over
        assertThat(mappedFullEmployer.getAddress().getStreet1(), is(fullEmployer.getAddress().getStreet1()));
        assertThat(mappedFullEmployer.getEmployeesCount(), is(fullEmployer.getEmployeesCount()));
        assertThat(mappedFullEmployer.getWebsite(), is(fullEmployer.getWebsite()));
        assertThat(mappedFullEmployer.getWorkingPlace(), is(fullEmployer.getWorkingPlace()));
        assertThat(mappedFullEmployer.getCanteen(), is(fullEmployer.getCanteen()));
        assertThat(mappedFullEmployer.getNearestAirport(), is(fullEmployer.getNearestAirport()));
        assertThat(mappedFullEmployer.getNearestPublicTransport(), is(fullEmployer.getNearestPublicTransport()));
        // the modified & created timestamps is skipped, as their implementation must be altered.
    }

    @Test
    public void testMappingOffer() {
        final Offer fullOffer = TestData.prepareFullOffer("AT-2016-123456", "Austrian Employer");
        final Offer nullOffer = null;

        final Offer mappedFullOffer = map(map(fullOffer));
        final Offer mappedNullOffer = map(map(nullOffer));

        assertThat(mappedNullOffer, is(nullOffer));
        assertThat(mappedFullOffer, is(not(nullValue())));
        assertThat(mappedFullOffer.getOfferId(), is(fullOffer.getOfferId()));
        assertThat(mappedFullOffer.getRefNo(), is(fullOffer.getRefNo()));
        assertThat(mappedFullOffer.getOfferType(), is(fullOffer.getOfferType()));
        assertThat(mappedFullOffer.getExchangeType(), is(fullOffer.getExchangeType()));
        assertThat(mappedFullOffer.getOldRefNo(), is(fullOffer.getOldRefNo()));
        // Testing Employers is done above, here we just need to ensure that it is mapped over
        assertThat(mappedFullOffer.getEmployer().getName(), is(fullOffer.getEmployer().getName()));
        assertThat(mappedFullOffer.getWorkDescription(), is(fullOffer.getWorkDescription()));
        assertThat(mappedFullOffer.getTypeOfWork(), is(fullOffer.getTypeOfWork()));
        assertThat(mappedFullOffer.getWeeklyHours(), is(fullOffer.getWeeklyHours()));
        assertThat(mappedFullOffer.getDailyHours(), is(fullOffer.getDailyHours()));
        assertThat(mappedFullOffer.getWeeklyWorkDays(), is(fullOffer.getWeeklyWorkDays()));
        assertThat(mappedFullOffer.getStudyLevels(), is(fullOffer.getStudyLevels()));
        assertThat(mappedFullOffer.getFieldOfStudies(), is(fullOffer.getFieldOfStudies()));
        assertThat(mappedFullOffer.getSpecializations(), is(fullOffer.getSpecializations()));
        assertThat(mappedFullOffer.getPreviousTrainingRequired(), is(fullOffer.getPreviousTrainingRequired()));
        assertThat(mappedFullOffer.getOtherRequirements(), is(fullOffer.getOtherRequirements()));
        assertThat(mappedFullOffer.getMinimumWeeks(), is(fullOffer.getMinimumWeeks()));
        assertThat(mappedFullOffer.getMaximumWeeks(), is(fullOffer.getMaximumWeeks()));
        assertThat(mappedFullOffer.getPeriod1(), is(fullOffer.getPeriod1()));
        assertThat(mappedFullOffer.getPeriod2(), is(fullOffer.getPeriod2()));
        assertThat(mappedFullOffer.getUnavailable(), is(fullOffer.getUnavailable()));
        assertThat(mappedFullOffer.getLanguage1(), is(fullOffer.getLanguage1()));
        assertThat(mappedFullOffer.getLanguage1Level(), is(fullOffer.getLanguage1Level()));
        assertThat(mappedFullOffer.getLanguage1Operator(), is(fullOffer.getLanguage1Operator()));
        assertThat(mappedFullOffer.getLanguage2(), is(fullOffer.getLanguage2()));
        assertThat(mappedFullOffer.getLanguage2Level(), is(fullOffer.getLanguage2Level()));
        assertThat(mappedFullOffer.getLanguage2Operator(), is(fullOffer.getLanguage2Operator()));
        assertThat(mappedFullOffer.getLanguage3(), is(fullOffer.getLanguage3()));
        assertThat(mappedFullOffer.getLanguage3Level(), is(fullOffer.getLanguage3Level()));
        assertThat(mappedFullOffer.getPayment(), is(fullOffer.getPayment()));
        assertThat(mappedFullOffer.getPaymentFrequency(), is(fullOffer.getPaymentFrequency()));
        assertThat(mappedFullOffer.getCurrency(), is(fullOffer.getCurrency()));
        assertThat(mappedFullOffer.getDeduction(), is(fullOffer.getDeduction()));
        assertThat(mappedFullOffer.getLivingCost(), is(fullOffer.getLivingCost()));
        assertThat(mappedFullOffer.getLivingCostFrequency(), is(fullOffer.getLivingCostFrequency()));
        assertThat(mappedFullOffer.getLodgingBy(), is(fullOffer.getLodgingBy()));
        assertThat(mappedFullOffer.getLodgingCost(), is(fullOffer.getLodgingCost()));
        assertThat(mappedFullOffer.getLodgingCostFrequency(), is(fullOffer.getLodgingCostFrequency()));
        assertThat(mappedFullOffer.getNominationDeadline(), is(fullOffer.getNominationDeadline()));
        assertThat(mappedFullOffer.getNumberOfHardCopies(), is(fullOffer.getNumberOfHardCopies()));
        assertThat(mappedFullOffer.getPrivateComment(), is(fullOffer.getPrivateComment()));
        assertThat(mappedFullOffer.getStatus(), is(fullOffer.getStatus()));
        assertThat(mappedFullOffer.getOfferId(), is(fullOffer.getOfferId()));
        assertThat(mappedFullOffer.getNsFirstname(), is(fullOffer.getNsFirstname()));
        assertThat(mappedFullOffer.getNsLastname(), is(fullOffer.getNsLastname()));
        assertThat(mappedFullOffer.getShared(), is(fullOffer.getShared()));
        assertThat(mappedFullOffer.isHidden(), is(fullOffer.isHidden()));
        // the modified & created timestamps is skipped, as their implementation must be altered.
    }

    @Test
    public void testNullOfferStatisticsRequest() {
        final OfferStatisticsRequest api = null;
        final net.iaeste.iws.ws.OfferStatisticsRequest mapped = map(api);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testOfferStatisticsRequest() {
        final int year = 1999;
        final OfferStatisticsRequest api = new OfferStatisticsRequest();
        api.setExchangeYear(year);

        final net.iaeste.iws.ws.OfferStatisticsRequest mapped = map(api);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getExchangeYear(), is(year));
    }

    @Test
    public void testNullEmployerRequest() {
        final EmployerRequest api = null;
        final net.iaeste.iws.ws.EmployerRequest mapped = map(api);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testOfferStatisticsResponse() {
        final net.iaeste.iws.ws.OfferStatisticsResponse ws = new net.iaeste.iws.ws.OfferStatisticsResponse();
        ws.setError(prepareIwsError(IWSErrors.SUCCESS));
        ws.setMessage(IWSConstants.SUCCESS);
        ws.setDomesticStatistics(prepareStatistics());
        ws.setForeignStatistics(prepareStatistics());

        final OfferStatisticsResponse mapped = map(ws);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getError(), is(IWSErrors.SUCCESS));
        assertThat(mapped.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(mapped.getDomesticStatistics().getStatistics().size(), is(5));
        assertThat(mapped.getForeignStatistics().getStatistics().size(), is(5));
    }

    @Test
    public void testEmployerRequest() {
        final String employerId = UUID.randomUUID().toString();
        final Action action = Action.DELETE;
        final Employer employer = prepareEmployer();
        final EmployerRequest api = new EmployerRequest();
        api.setEmployerId(employerId);
        api.setEmployer(employer);
        api.setAction(action);

        final net.iaeste.iws.ws.EmployerRequest mapped = map(api);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getEmployerId(), is(employerId));
        // Testing Employers is done above, here we just need to ensure that it is mapped over
        assertThat(mapped.getEmployer().getName(), is(employer.getName()));
        assertThat(mapped.getAction().name(), is(action.name()));
    }

    @Test
    public void testNullOfferStatisticsResponse() {
        final net.iaeste.iws.ws.OfferStatisticsResponse ws = null;
        final OfferStatisticsResponse mapped = map(ws);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testNullEmployerResponse() {
        final net.iaeste.iws.ws.EmployerResponse ws = null;
        final EmployerResponse mapped = map(ws);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testEmployerResponse() {
        final net.iaeste.iws.ws.Employer employer = map(prepareEmployer());
        final net.iaeste.iws.ws.EmployerResponse ws = new net.iaeste.iws.ws.EmployerResponse();
        ws.setError(prepareIwsError(IWSErrors.SUCCESS));
        ws.setMessage(IWSConstants.SUCCESS);
        ws.setEmployer(employer);

        final EmployerResponse mapped = map(ws);
        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getError(), is(IWSErrors.SUCCESS));
        assertThat(mapped.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(mapped.getEmployer().getName(), is(employer.getName()));
    }

    @Test
    public void testNullFetchEmployerRequest() {
        final FetchEmployerRequest api = null;
        final net.iaeste.iws.ws.FetchEmployerRequest mapped = map(api);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testFetchEmployerRequest() {
        final FetchEmployerRequest api = new FetchEmployerRequest();
        api.setFetchOfferReferenceNumbers(true);
        api.setFetchByPartialName("name");

        final net.iaeste.iws.ws.FetchEmployerRequest mapped = map(api);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getPage().getPageSize(), is(100));
        assertThat(mapped.getPage().getPageNumber(), is(1));
        assertThat(mapped.getPage().getSortOrder(), is(SortingOrder.DESC));
        assertThat(mapped.getPage().getSortBy(), is(SortingField.CREATED));
        assertThat(mapped.getField(), is("name"));
        assertThat(mapped.getType(), is(EmployerFetchType.NAME));
    }

    @Test
    public void testNullFetchEmployerResponse() {
        final net.iaeste.iws.ws.FetchEmployerResponse ws = null;
        final FetchEmployerResponse mapped = map(ws);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testFetchEmployerResponse() {
        final net.iaeste.iws.ws.FetchEmployerResponse ws = new net.iaeste.iws.ws.FetchEmployerResponse();
        ws.setMessage(IWSConstants.SUCCESS);
        ws.setError(prepareIwsError(IWSErrors.SUCCESS));
        ws.getEmployers().addAll(Collections.singletonList(map(prepareEmployer())));
        ws.getOfferRefNos().addAll(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        final FetchEmployerResponse mapped = map(ws);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(mapped.getError(), is(IWSErrors.SUCCESS));
        assertThat(mapped.getEmployers().size(), is(1));
        assertThat(mapped.getOfferRefNos().size(), is(2));
    }

    @Test
    public void testNullFetchOffersRequest() {
        final FetchOffersRequest api = null;
        final net.iaeste.iws.ws.FetchOffersRequest mapped = map(api);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testFetchOffersRequest() {
        final FetchOffersRequest api = new FetchOffersRequest();
        api.setFetchType(FetchType.DOMESTIC);
        api.setIdentifiers(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        api.setExchangeYear(2016);
        api.setStates(EnumSet.of(OfferState.NEW, OfferState.REJECTED));
        api.setRetrieveCurrentAndNextExchangeYear(true);

        final net.iaeste.iws.ws.FetchOffersRequest mapped = map(api);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getPage().getPageSize(), is(100));
        assertThat(mapped.getPage().getPageNumber(), is(1));
        assertThat(mapped.getPage().getSortBy(), is(SortingField.CREATED));
        assertThat(mapped.getPage().getSortOrder(), is(SortingOrder.DESC));
        assertThat(mapped.getFetchType(), is(net.iaeste.iws.ws.FetchType.DOMESTIC));
        assertThat(mapped.getIdentifiers().size(), is(2));
        assertThat(mapped.getExchangeYear(), is(2016));
        assertThat(mapped.getStates().size(), is(2));
        assertThat(mapped.isRetrieveCurrentAndNextExchangeYear(), is(true));
    }

    @Test
    public void testNullFetchOffersResponse() {
        final net.iaeste.iws.ws.FetchOffersResponse ws = null;
        final FetchOffersResponse mapped = map(ws);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testFetchOffersResponse() {
        final net.iaeste.iws.ws.FetchOffersResponse ws = new net.iaeste.iws.ws.FetchOffersResponse();
        ws.setMessage(IWSConstants.SUCCESS);
        ws.setError(prepareIwsError(IWSErrors.SUCCESS));
        ws.getOffers().addAll(Arrays.asList(prepareWSOffer(), prepareWSOffer()));
        final FetchOffersResponse mapped = map(ws);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(mapped.getError(), is(IWSErrors.SUCCESS));
        assertThat(mapped.getOffers().size(), is(2));
    }

    @Test
    public void testNullOfferRequest() {
        final OfferRequest api = null;
        final net.iaeste.iws.ws.OfferRequest mapped = map(api);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testOfferRequest() {
        final String offerId = UUID.randomUUID().toString();
        final String refno = "DE-2016-123123";

        final OfferRequest api = new OfferRequest();
        api.setOffer(TestData.prepareFullOffer(refno, "German Employer"));
        api.setOfferId(offerId);
        api.setAction(Action.PROCESS);

        final net.iaeste.iws.ws.OfferRequest mapped = map(api);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getOfferId(), is(offerId));
        assertThat(mapped.getOffer().getRefNo(), is(refno));
        assertThat(mapped.getAction(), is(net.iaeste.iws.ws.Action.PROCESS));
    }

    @Test
    public void testNullOfferResponse() {
        final net.iaeste.iws.ws.OfferResponse ws = null;
        final OfferResponse mapped = map(ws);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testOfferResponse() {
        final net.iaeste.iws.ws.OfferResponse ws = new net.iaeste.iws.ws.OfferResponse();
        ws.setMessage(IWSConstants.SUCCESS);
        ws.setError(prepareIwsError(IWSErrors.SUCCESS));
        ws.setOffer(prepareWSOffer());

        final OfferResponse mapped = map(ws);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(mapped.getError(), is(IWSErrors.SUCCESS));
        assertThat(mapped.getOffer(), is(not(nullValue())));
    }

    @Test
    public void testNullOfferCSVUploadRequest() {
        final OfferCSVUploadRequest api = null;
        final net.iaeste.iws.ws.OfferCSVUploadRequest mapped = map(api);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testOfferCSVUploadRequest() {
        final String csv = "The CSV Data";
        final OfferCSVUploadRequest.FieldDelimiter delimiter = OfferCSVUploadRequest.FieldDelimiter.COMMA;

        final OfferCSVUploadRequest api = new OfferCSVUploadRequest();
        api.setCsv(csv);
        api.setDelimiter(delimiter);

        final net.iaeste.iws.ws.OfferCSVUploadRequest mapped = map(api);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getCsv(), is(csv));
        assertThat(mapped.getDelimiter().name(), is(delimiter.name()));
    }

    @Test
    public void testNullOfferCSVUploadResponse() {
        final net.iaeste.iws.ws.OfferCSVUploadResponse ws = null;
        final OfferCSVUploadResponse mapped = map(ws);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Test not yet completed")
    public void testOfferCSVUploadResponse() {
        final net.iaeste.iws.ws.OfferCSVUploadResponse ws = new net.iaeste.iws.ws.OfferCSVUploadResponse();
        ws.setMessage(IWSConstants.SUCCESS);
        ws.setError(prepareIwsError(IWSErrors.SUCCESS));

        final OfferCSVUploadResponse mapped = map(ws);
        // TODO

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testNullPublishOfferResponse() {
        final net.iaeste.iws.ws.PublishOfferResponse ws = null;
        final PublishOfferResponse mapped = map(ws);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testPublishOfferResponse() {
        final net.iaeste.iws.ws.PublishOfferResponse ws = new net.iaeste.iws.ws.PublishOfferResponse();
        ws.setMessage(IWSConstants.SUCCESS);
        ws.setError(prepareIwsError(IWSErrors.SUCCESS));

        final PublishOfferResponse mapped = map(ws);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(mapped.getError(), is(IWSErrors.SUCCESS));
    }

    @Test
    public void testNullProcessPublishingGroupRequest() {
        final ProcessPublishingGroupRequest api = null;
        final net.iaeste.iws.ws.ProcessPublishingGroupRequest mapped = map(api);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testProcessPublishingGroupRequest() {
        final String publishingGroupId = UUID.randomUUID().toString();
        final PublishingGroup publishingGroup = new PublishingGroup();
        publishingGroup.setPublishingGroupId(publishingGroupId);
        publishingGroup.setGroups(Arrays.asList(TestUtils.prepareGroup(), TestUtils.prepareGroup()));
        publishingGroup.setName("Name");

        final ProcessPublishingGroupRequest api = new ProcessPublishingGroupRequest();
        api.setPublishingGroupId(publishingGroupId);
        api.setAction(Action.DELETE);
        api.setPublishingGroup(publishingGroup);

        final net.iaeste.iws.ws.ProcessPublishingGroupRequest mapped = map(api);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getPublishingGroupId(), is(publishingGroupId));
        assertThat(mapped.getAction(), is(net.iaeste.iws.ws.Action.DELETE));
        assertThat(mapped.getPublishingGroup().getName(), is("Name"));
        assertThat(mapped.getPublishingGroup().getGroups().size(), is(2));
    }

    @Test
    public void testNullFetchGroupsForSharingResponse() {
        final net.iaeste.iws.ws.FetchGroupsForSharingResponse ws = null;
        final FetchGroupsForSharingResponse mapped = map(ws);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testFetchGroupsForSharingResponse() {
        final net.iaeste.iws.ws.FetchGroupsForSharingResponse ws = new net.iaeste.iws.ws.FetchGroupsForSharingResponse();
        ws.setMessage(IWSConstants.SUCCESS);
        ws.setError(prepareIwsError(IWSErrors.SUCCESS));
        ws.getGroups().addAll(Arrays.asList(map(TestUtils.prepareGroup()), map(TestUtils.prepareGroup())));

        final FetchGroupsForSharingResponse mapped = map(ws);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(mapped.getError(), is(IWSErrors.SUCCESS));
        assertThat(mapped.getGroups().size(), is(2));
    }

    @Test
    public void testNullOfferCSVDownloadResponse() {
        final net.iaeste.iws.ws.OfferCSVDownloadResponse ws = null;
        final OfferCSVDownloadResponse mapped = map(ws);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testOfferCSVDownloadResponse() {
        final String csv = "CSV,FILE,For,Test";
        final net.iaeste.iws.ws.OfferCSVDownloadResponse ws = new net.iaeste.iws.ws.OfferCSVDownloadResponse();
        ws.setMessage(IWSConstants.SUCCESS);
        ws.setError(prepareIwsError(IWSErrors.SUCCESS));
        ws.setCsv(csv);

        final OfferCSVDownloadResponse mapped = map(ws);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(mapped.getError(), is(IWSErrors.SUCCESS));
        assertThat(mapped.getCsv(), is(csv));
    }

    @Test
    public void testNullFetchPublishGroupsRequest() {
        final FetchPublishGroupsRequest api = null;
        final net.iaeste.iws.ws.FetchPublishGroupsRequest mapped = map(api);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testFetchPublishGroupsRequest() {
        final FetchPublishGroupsRequest api = new FetchPublishGroupsRequest();

        final net.iaeste.iws.ws.FetchPublishGroupsRequest mapped = map(api);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getPage().getPageNumber(), is(1));
    }

    @Test
    public void testNullFetchPublishingGroupResponse() {
        final net.iaeste.iws.ws.FetchPublishingGroupResponse ws = null;
        final FetchPublishingGroupResponse mapped = map(ws);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    @Ignore("Test not yet completed")
    public void testFetchPublishingGroupResponse() {
        fail("not yet implemented");
    }

    @Test
    public void testNullFetchPublishedGroupsRequest() {
        final FetchPublishedGroupsRequest api = null;
        final net.iaeste.iws.ws.FetchPublishedGroupsRequest mapped = map(api);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testFetchPublishedGroupsRequest() {
        final FetchPublishedGroupsRequest api = new FetchPublishedGroupsRequest();
        api.setIdentifiers(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        api.setExchangeYear(2016);

        final net.iaeste.iws.ws.FetchPublishedGroupsRequest mapped = map(api);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getPage().getPageNumber(), is(1));
        assertThat(mapped.getExchangeYear(), is(2016));
        assertThat(mapped.getIdentifiers().size(), is(2));
    }

    @Test
    public void testNullFetchPublishedGroupsResponse() {
        final net.iaeste.iws.ws.FetchPublishedGroupsResponse ws = null;
        final FetchPublishedGroupsResponse mapped = map(ws);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testFetchPublishedGroupsResponse() {
        final net.iaeste.iws.ws.FetchPublishedGroupsResponse ws = new net.iaeste.iws.ws.FetchPublishedGroupsResponse();
        ws.setMessage(IWSConstants.SUCCESS);
        ws.setError(prepareIwsError(IWSErrors.SUCCESS));
        // TODO Complete mapping of the OfferGroups

        final FetchPublishedGroupsResponse mapped = map(ws);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(mapped.getError(), is(IWSErrors.SUCCESS));
    }

    @Test
    public void testNullPublishOfferRequest() {
        final PublishOfferRequest api = null;
        final net.iaeste.iws.ws.PublishOfferRequest mapped = map(api);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testPublishOfferRequest() {
        final String offerId = UUID.randomUUID().toString();
        final List<String> list = new ArrayList<>(2);
        list.add(UUID.randomUUID().toString());
        list.add(UUID.randomUUID().toString());
        final Date deadline = new Date();

        final PublishOfferRequest api = new PublishOfferRequest();
        api.setNominationDeadline(deadline);
        api.setGroupIds(list);
        api.setAction(Action.REMOVE);
        api.setOfferId(offerId);

        final net.iaeste.iws.ws.PublishOfferRequest mapped = map(api);

        assertThat(mapped, is(not(nullValue())));
        // Manually tested
        assertThat(mapped.getNominationDeadline(), is(not(nullValue())));
        assertThat(mapped.getGroupIds().size(), is(2));
        assertThat(mapped.getAction(), is(net.iaeste.iws.ws.Action.REMOVE));
        assertThat(mapped.getOfferId(), is(offerId));
    }

    @Test
    public void testNullHideForeignOffersRequest() {
        final HideForeignOffersRequest api = null;
        final net.iaeste.iws.ws.HideForeignOffersRequest mapped = map(api);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testHideForeignOffersRequest() {
        final Set<String> set = new HashSet<>(2);
        set.add(UUID.randomUUID().toString());
        set.add(UUID.randomUUID().toString());

        final HideForeignOffersRequest api = new HideForeignOffersRequest();
        api.setOffers(set);
        api.setAction(Action.PROCESS);

        final net.iaeste.iws.ws.HideForeignOffersRequest mapped = map(api);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getOffers().size(), is(2));
        assertThat(mapped.getAction(), is(net.iaeste.iws.ws.Action.PROCESS));
    }

    @Test
    public void testNullRejectOfferRequest() {
        final RejectOfferRequest api = null;
        final net.iaeste.iws.ws.RejectOfferRequest mapped = map(api);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testRejectOfferRequest() {
        final String offerId = UUID.randomUUID().toString();
        final RejectOfferRequest api = new RejectOfferRequest();
        api.setOfferId(offerId);

        final net.iaeste.iws.ws.RejectOfferRequest mapped = map(api);

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getOfferId(), is(offerId));
    }

    // =========================================================================
    // Internal methods
    // =========================================================================

    private static Employer prepareEmployer() {
        return TestData.prepareEmployer("WebService SOAP Employer", "AT");
    }

    private static net.iaeste.iws.ws.Offer prepareWSOffer() {
        return map(TestData.prepareFullOffer("AT-2016-123456", "Employer Company."));
    }

    private static OfferStatistics prepareStatistics() {
        final List<OfferStatistics.Statistics.Entry> entries = new ArrayList<>(5);
        entries.add(prepareEntry(net.iaeste.iws.ws.OfferState.CANCELLED, 123));
        entries.add(prepareEntry(net.iaeste.iws.ws.OfferState.ACCEPTED, 112));
        entries.add(prepareEntry(net.iaeste.iws.ws.OfferState.DECLINED, 132));
        entries.add(prepareEntry(net.iaeste.iws.ws.OfferState.NEW, 312));
        entries.add(prepareEntry(net.iaeste.iws.ws.OfferState.REJECTED, 321));

        final OfferStatistics.Statistics statistics = new OfferStatistics.Statistics();
        statistics.getEntry().addAll(entries);

        final OfferStatistics offerStatisticsstatistics = new OfferStatistics();
        offerStatisticsstatistics.setExchangeYear(2016);
        offerStatisticsstatistics.setStatistics(statistics);

        return offerStatisticsstatistics;
    }

    private static OfferStatistics.Statistics.Entry prepareEntry(final net.iaeste.iws.ws.OfferState state, final int number) {
        final OfferStatistics.Statistics.Entry entry = new OfferStatistics.Statistics.Entry();
        entry.setKey(state);
        entry.setValue(number);

        return entry;
    }
}
