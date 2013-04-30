/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.exchange.EmployerInformation;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.dtos.exchange.OfferGroup;
import net.iaeste.iws.api.dtos.OfferTestUtility;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.OfferState;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerInformationRequest;
import net.iaeste.iws.api.requests.exchange.FetchGroupsForSharingRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.responses.exchange.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishOfferResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.util.Date;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class ExchangeClientTest extends AbstractClientTest {

    private final Exchange exchange = new ExchangeClient();
    private AuthenticationToken austriaToken = null;
    private AuthenticationToken croatiaToken = null;

    @Override
    public void before() {
        token = login("poland", "poland");
        austriaToken = login("austria", "austria");
        croatiaToken = login("croatia", "croatia");
    }

    @Override
    public void after() {
        logout(token);
        logout(austriaToken);
        logout(croatiaToken);
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
        final String refNo = "PL-2012-0001";
        final Offer minimalOffer = OfferTestUtility.getMinimalOffer();
        minimalOffer.setRefNo(refNo);

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(minimalOffer);
        final OfferResponse processResponse = exchange.processOffer(token, offerRequest);

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
        final String refNo = "PL-2012-0002";
        final Offer fullOffer = OfferTestUtility.getFullOffer();
        fullOffer.setRefNo(refNo);

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(fullOffer);
        final OfferResponse processResponse = exchange.processOffer(token, offerRequest);

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

        final DeleteOfferRequest deleteRequest = new DeleteOfferRequest(offerToDelete.getRefNo());
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
        net.iaeste.iws.api.util.Date nominationDeadline = new Date().plusDays(20);

        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setRefNo("PL-2012-0004");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);

        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest allOffersRequest = new FetchOffersRequest(FetchType.ALL);
        FetchOffersResponse allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        Offer sharedOffer = findOfferFromResponse(offer.getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(notNullValue()));
        assertThat(sharedOffer.getRefNo(), is(offer.getRefNo()));
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

        final List<String> offersExternalId = new ArrayList<>();
        offersExternalId.add(sharedOffer.getId());
        final FetchPublishOfferRequest fetchPublishRequest = new FetchPublishOfferRequest(offersExternalId);
        final FetchPublishOfferResponse fetchPublishResponse1 = exchange.fetchPublishedOfferInfo(token, fetchPublishRequest);

        //is it shared to two groups?
        assertThat(fetchPublishResponse1.isOk(), is(true));
        List<OfferGroup> offerGroupsSharedTo = fetchPublishResponse1.getOffersGroups().get(offersExternalId.get(0));
        assertThat(2, is(offerGroupsSharedTo.size()));

        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(offer.getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(notNullValue()));
        assertThat(sharedOffer.getRefNo(), is(offer.getRefNo()));
        assertThat("The offer is shared now, the status has to be SHARED", sharedOffer.getStatus(), is(OfferState.SHARED));
        assertThat(sharedOffer.getNominationDeadline(), is(nominationDeadline));

        groupIds.clear();
        final PublishOfferRequest publishRequest2 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse2 = exchange.processPublishOffer(token, publishRequest2);

        assertThat(publishResponse2.isOk(), is(true));
        final FetchPublishOfferResponse fetchPublishResponse2 = exchange.fetchPublishedOfferInfo(token, fetchPublishRequest);
        assertThat(fetchPublishResponse2.isOk(), is(true));
        offerGroupsSharedTo = fetchPublishResponse2.getOffersGroups().get(offersExternalId.get(0));

        //is it shared to nobody?
        assertThat(offerGroupsSharedTo.size(), is(0));
        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(offer.getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(notNullValue()));
        assertThat("The offer is shared to nobody, the status has to be NEW", sharedOffer.getStatus(), is(OfferState.NEW));
    }

    @Test
    public void testFailShareNonOwnedOffer() {
        net.iaeste.iws.api.util.Date nominationDeadline = new Date().plusDays(20);

        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setRefNo("PL-2012-0005");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse response = exchange.fetchOffers(token, request);
        assertThat(response.getOffers().isEmpty(), is(false));

        final Set<String> offersToShare = new HashSet<>(1);
        final String offerIdToBeShared =response.getOffers().get(0).getId();
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
        net.iaeste.iws.api.util.Date nominationDeadline = new Date().plusDays(20);

        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setRefNo("PL-2012-0006");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse response = exchange.fetchOffers(token, request);
        assertThat(response.getOffers().isEmpty(), is(false));

        final Set<String> offersToShare = new HashSet<>(1);
        final String offerIdToBeShared =response.getOffers().get(0).getId();
        offersToShare.add(offerIdToBeShared);

        final String austriaMemberGroupId = "2cc7e1bb-01e8-43a2-9643-2e964cbd41c5";
        final GroupType austriaMemberGroupType = GroupType.MEMBER;
        final List<String> groupIds = new ArrayList<>(1);
        groupIds.add(austriaMemberGroupId);

        final PublishOfferRequest publishRequest1 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        //try to share to non-National group type
        final PublishOfferResponse publishResponse1 = exchange.processPublishOffer(token, publishRequest1);

        //the request cannot be OK here
        assertThat(publishResponse1.isOk(), is(false));
        assertThat("The request has to fail with verification error here", publishResponse1.getError(), is(IWSErrors.VERIFICATION_ERROR));
        assertThat(publishResponse1.getMessage(), is("The group type '" + austriaMemberGroupType + "' is not allowed to be used for publishing of offers."));
    }

    @Test
    public void testFailShareOfferToSelf() {
        net.iaeste.iws.api.util.Date nominationDeadline = new Date().plusDays(20);

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
    public void testGetEmployerInformation() {
        final String refNo = "PL-2012-0008";
        final Offer offer = OfferTestUtility.getFullOffer();
        offer.setRefNo(refNo);

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);

        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.ALL);
        final FetchOffersResponse response = exchange.fetchOffers(token, request);
        final Offer readOffer = findOfferFromResponse(refNo, response);

        assertThat(readOffer, is(not(nullValue())));

        final FetchEmployerInformationRequest employerRequest = new FetchEmployerInformationRequest(offer.getEmployerName());
        final FetchEmployerInformationResponse employerResponse = exchange.fetchEmployers(token, employerRequest);

        assertThat(employerResponse.getEmployers().isEmpty(), is(false));
        final EmployerInformation employerInformation = employerResponse.getEmployers().get(0);
        assertThat(employerInformation.getAddress(), is(offer.getEmployerAddress()));
        assertThat(employerInformation.getAddress2(), is(offer.getEmployerAddress2()));
        assertThat(employerInformation.getBusiness(), is(offer.getEmployerBusiness()));
        assertThat(employerInformation.getDailyHours(), is(offer.getDailyHours()));
        assertThat(employerInformation.getEmployeesCount(), is(offer.getEmployerEmployeesCount()));
        assertThat(employerInformation.getName(), is(offer.getEmployerName()));
        assertThat(employerInformation.getNearestAirport(), is(offer.getNearestAirport()));
        assertThat(employerInformation.getNearestPubTransport(), is(offer.getNearestPubTransport()));
        assertThat(employerInformation.getWebsite(), is(offer.getEmployerWebsite()));
        assertThat(employerInformation.getWeeklyHours(), is(offer.getWeeklyHours()));
        assertThat(employerInformation.getWorkingPlace(), is(offer.getWorkingPlace()));
    }

    @Test
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
        final FetchGroupsForSharingResponse response = exchange.fetchGroupsForSharing(austriaToken, new FetchGroupsForSharingRequest());

        assertThat(response.isOk(), is(true));
        // 6 countries are entered in the test data, minus the own country (austria)
        assertThat("Expect from test data to get all groups minus the own -> 5", response.getGroups().size(), is(6 - 1));

        final GroupType[] groupTypes = { GroupType.NATIONAL, GroupType.SAR };
        for (final Group group : response.getGroups()) {
            assertThat(group.getGroupType(), Matchers.isIn(groupTypes));
            assertThat(group.getCountryId(), Matchers.is(not("AT")));
        }
    }
}
