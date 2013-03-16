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
import net.iaeste.iws.api.dtos.EmployerInformation;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.OfferTestUtility;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.requests.DeleteOfferRequest;
import net.iaeste.iws.api.requests.FetchEmployerInformationRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.FetchPublishOfferRequest;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.requests.PublishOfferRequest;
import net.iaeste.iws.api.responses.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.api.responses.FetchPublishOfferResponse;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.api.responses.PublishOfferResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
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

    @Override
    public void before() {
        token = login("poland", "poland");
    }

    @Override
    public void after() {
        logout(token);
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
        final Offer offer = OfferTestUtility.getMinimalOffer();
        offer.setRefNo("PL-2012-0004");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);

        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.OWNED);
        final FetchOffersResponse response = exchange.fetchOffers(token, request);
        assertThat(response.getOffers().isEmpty(), is(false));

        final Set<String> offersToShare = new HashSet<>();
        offersToShare.add(response.getOffers().get(0).getId());
        final List<Group> groups = new ArrayList<>(1);
        Group group = new Group();
        group.setGroupId("c7b15f81-4f83-48e8-9ffb-9e73255f5e5e");
        group.setGroupType(GroupType.NATIONAL);
        groups.add(group);
        group = new Group();
        group.setGroupId("17eb00ac-1386-4852-9934-e3dce3f57c13");
        group.setGroupType(GroupType.NATIONAL);
        groups.add(group);

        PublishOfferRequest publishRequest = new PublishOfferRequest(offersToShare, groups);
        PublishOfferResponse publishResponse = exchange.processPublishOffer(token, publishRequest);

        assertThat(publishResponse.getError(), is(IWSErrors.SUCCESS));
        assertThat(publishResponse.isOk(), is(true));

        final FetchPublishOfferRequest fetchPublishRequest = new FetchPublishOfferRequest(response.getOffers().get(0).getId());
        FetchPublishOfferResponse fetchPublishResponse = exchange.fetchPublishedOfferInfo(token, fetchPublishRequest);

        //is it shared to two groups?
        assertThat(2, is(fetchPublishResponse.getOfferGroups().size()));

        groups.clear();
        publishRequest = new PublishOfferRequest(offersToShare, groups);
        publishResponse = exchange.processPublishOffer(token, publishRequest);

        //is it shared to two groups?
        assertThat(publishResponse.isOk(), is(true));
        fetchPublishResponse = exchange.fetchPublishedOfferInfo(token, fetchPublishRequest);

        //is it shared to nobody?
        assertThat(fetchPublishResponse.getOfferGroups().size(), is(0));
    }

    @Test
    public void testGetEmployerInformation() {
        final String refNo = "PL-2012-0005";
        final Offer offer = OfferTestUtility.getFullOffer();
        offer.setRefNo(refNo);

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);

        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest request = new FetchOffersRequest(FetchType.OWNED);
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
        final FetchOffersRequest findSavedRequest = new FetchOffersRequest(FetchType.OWNED);
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
        final FetchOffersRequest findupdatedRequest = new FetchOffersRequest(FetchType.OWNED);
        final FetchOffersResponse findUpdatedResponse = exchange.fetchOffers(token, findupdatedRequest);
        final Offer updatedOffer = findOfferFromResponse(refNo, findUpdatedResponse);
        assertThat(updatedOffer, is(not(nullValue())));
        assertThat(updatedOffer.getNumberOfHardCopies(), is(3));
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
}
