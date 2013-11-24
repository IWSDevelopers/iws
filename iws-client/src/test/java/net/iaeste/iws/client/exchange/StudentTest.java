/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.exchange.StudentTest
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

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.Students;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.TestData;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.dtos.exchange.OfferGroup;
import net.iaeste.iws.api.dtos.exchange.Student;
import net.iaeste.iws.api.dtos.exchange.StudentApplication;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.student.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.StudentApplicationRequest;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.responses.student.FetchStudentsResponse;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;
import net.iaeste.iws.api.util.Copier;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DatePeriod;
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.AdministrationClient;
import net.iaeste.iws.client.ExchangeClient;
import net.iaeste.iws.client.StudentClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class StudentTest extends AbstractTest {

    private final Administration administration = new AdministrationClient();
    private final Exchange exchange = new ExchangeClient();
    private final Students students = new StudentClient();
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
    public void testProcessStudentApplication() {
        final Date nominationDeadline = new Date().plusDays(20);
        final Offer offer = TestData.prepareMinimalOffer("PL-2014-001001", "Employer", "PL");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);
        final String refNo = saveResponse.getOffer().getRefNo();

        // verify processResponse
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest allOffersRequest = new FetchOffersRequest(FetchType.ALL);
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
        List<OfferGroup> offerGroupsSharedTo = fetchPublishResponse1.getOffersGroups().get(offersExternalId.get(0));
        assertThat(2, is(offerGroupsSharedTo.size()));

        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat(sharedOffer.getRefNo(), is(saveResponse.getOffer().getRefNo()));
        assertThat("The offer is shared now, the status has to be SHARED", sharedOffer.getStatus(), is(OfferState.SHARED));
        assertThat(sharedOffer.getNominationDeadline(), is(nominationDeadline));

        final CreateUserRequest createUserRequest1 = new CreateUserRequest("student_app001@university.edu", "password1", "Student1", "Graduate1");
        createUserRequest1.setStudentAccount(true);

        final CreateUserResponse createStudentResponse1 = administration.createUser(austriaToken, createUserRequest1);
        assertThat(createStudentResponse1.isOk(), is(true));
        final FetchStudentsRequest fetchStudentsRequest = new FetchStudentsRequest();
        final FetchStudentsResponse fetchStudentsResponse = students.fetchStudents(austriaToken, fetchStudentsRequest);
        assertThat(fetchStudentsResponse.isOk(), is(true));
        assertThat(fetchStudentsResponse.getStudents().isEmpty(), is(false));
        final Student student = fetchStudentsResponse.getStudents().get(0);
        student.setAvailable(new DatePeriod(new Date(), nominationDeadline));

        final StudentApplication application = new StudentApplication();
        application.setOffer(sharedOffer);
        application.setStudent(student);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setHomeAddress(TestData.prepareAddress("DE"));
        application.setAddressDuringTerms(TestData.prepareAddress("AT"));

        final ProcessStudentApplicationsRequest createStudentApplicationsRequest = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createStudentApplicationResponse = students.processStudentApplication(austriaToken, createStudentApplicationsRequest);
        assertThat(createStudentApplicationResponse.isOk(), is(true));
    }

    @Test
    public void testFetchStudentApplications() {
        final Date nominationDeadline = new Date().plusDays(20);
        final Offer offer = TestData.prepareMinimalOffer("PL-2014-001003", "Employer", "PL");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);
        final String refNo = saveResponse.getOffer().getRefNo();

        // verify processResponse
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest allOffersRequest = new FetchOffersRequest(FetchType.ALL);
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
        final List<OfferGroup> offerGroupsSharedTo = fetchPublishResponse1.getOffersGroups().get(offersExternalId.get(0));
        assertThat(2, is(offerGroupsSharedTo.size()));

        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat(sharedOffer.getRefNo(), is(saveResponse.getOffer().getRefNo()));
        assertThat("The offer is shared now, the status has to be SHARED", sharedOffer.getStatus(), is(OfferState.SHARED));
        assertThat(sharedOffer.getNominationDeadline(), is(nominationDeadline));

        final CreateUserRequest createUserRequest1 = new CreateUserRequest("student_app003@university.edu", "password1", "Student1", "Graduate1");
        createUserRequest1.setStudentAccount(true);

        final CreateUserResponse createStudentResponse1 = administration.createUser(austriaToken, createUserRequest1);
        assertThat(createStudentResponse1.isOk(), is(true));
        final FetchStudentsRequest fetchStudentsRequest = new FetchStudentsRequest();
        final FetchStudentsResponse fetchStudentsResponse = students.fetchStudents(austriaToken, fetchStudentsRequest);
        assertThat(fetchStudentsResponse.isOk(), is(true));
        assertThat(fetchStudentsResponse.getStudents().isEmpty(), is(false));
        final Student student = fetchStudentsResponse.getStudents().get(0);
        student.setAvailable(new DatePeriod(new Date(), nominationDeadline));

        final StudentApplication application = new StudentApplication();
        application.setOffer(sharedOffer);
        application.setStudent(student);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setHomeAddress(TestData.prepareAddress("DE"));
        application.setAddressDuringTerms(TestData.prepareAddress("AT"));

        final ProcessStudentApplicationsRequest createStudentApplicationsRequest = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createStudentApplicationResponse = students.processStudentApplication(austriaToken, createStudentApplicationsRequest);
        assertThat(createStudentApplicationResponse.isOk(), is(true));

        final FetchStudentApplicationsRequest fetchStudentApplicationsRequest = new FetchStudentApplicationsRequest(sharedOffer.getOfferId());
        final FetchStudentApplicationsResponse fetchStudentApplicationsResponse = students.fetchStudentApplications(austriaToken, fetchStudentApplicationsRequest);
        assertThat(fetchStudentApplicationsResponse.isOk(), is(true));
        assertThat(fetchStudentApplicationsResponse.getStudentApplications().size(), is(1));
    }

    @Test
    public void testUpdateStudentApplication() {
        final Date nominationDeadline = new Date().plusDays(20);
        final Offer offer = TestData.prepareMinimalOffer("PL-2014-001002", "Employer", "PL");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);
        final String refNo = saveResponse.getOffer().getRefNo();

        // verify processResponse
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest allOffersRequest = new FetchOffersRequest(FetchType.ALL);
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
        final List<OfferGroup> offerGroupsSharedTo = fetchPublishResponse1.getOffersGroups().get(offersExternalId.get(0));
        assertThat(2, is(offerGroupsSharedTo.size()));

        allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));
        sharedOffer = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse);
        assertThat(sharedOffer, is(not(nullValue())));
        assertThat(sharedOffer.getRefNo(), is(saveResponse.getOffer().getRefNo()));
        assertThat("The offer is shared now, the status has to be SHARED", sharedOffer.getStatus(), is(OfferState.SHARED));
        assertThat(sharedOffer.getNominationDeadline(), is(nominationDeadline));

        final CreateUserRequest createUserRequest1 = new CreateUserRequest("student_app002@university.edu", "password1", "Student1", "Graduate1");
        createUserRequest1.setStudentAccount(true);

        final CreateUserResponse createStudentResponse1 = administration.createUser(austriaToken, createUserRequest1);
        assertThat(createStudentResponse1.isOk(), is(true));
        final FetchStudentsRequest fetchStudentsRequest = new FetchStudentsRequest();
        final FetchStudentsResponse fetchStudentsResponse = students.fetchStudents(austriaToken, fetchStudentsRequest);
        assertThat(fetchStudentsResponse.isOk(), is(true));
        assertThat(fetchStudentsResponse.getStudents().isEmpty(), is(false));
        final Student student = fetchStudentsResponse.getStudents().get(0);
        student.setAvailable(new DatePeriod(new Date(), nominationDeadline));

        StudentApplication application = new StudentApplication();
        application.setOffer(sharedOffer);
        application.setStudent(student);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setHomeAddress(TestData.prepareAddress("DE"));
        application.setAddressDuringTerms(TestData.prepareAddress("AT"));

        final ProcessStudentApplicationsRequest createStudentApplicationsRequest = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createStudentApplicationResponse = students.processStudentApplication(austriaToken, createStudentApplicationsRequest);
        assertThat(createStudentApplicationResponse.isOk(), is(true));

        //test updating existing application
        application = Copier.copy(createStudentApplicationResponse.getStudentApplication());
        application.setStatus(ApplicationStatus.NOMINATED);
        final ProcessStudentApplicationsRequest createStudentApplicationsRequest2 = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createStudentApplicationResponse2 = students.processStudentApplication(austriaToken, createStudentApplicationsRequest2);
        assertThat(createStudentApplicationResponse2.isOk(), is(true));
        assertThat(createStudentApplicationResponse2.getStudentApplication().getStatus(), is(application.getStatus()));
    }

    @Test
    public void testNominatingApplication() {
        final Date nominationDeadline = new Date().plusDays(20);
        final Offer offer = TestData.prepareMinimalOffer("PL-2014-001004", "Employer", "PL");

        final OfferResponse saveResponse = exchange.processOffer(token, new ProcessOfferRequest(offer));
        assertThat("Offer has been saved", saveResponse.isOk(), is(true));

        final Set<String> offersToShare = new HashSet<>(1);
        final String offerId = saveResponse.getOffer().getOfferId();
        offersToShare.add(offerId);

        final List<String> groupIds = new ArrayList<>(2);
        groupIds.add(findNationalGroup(austriaToken).getGroupId());
        groupIds.add(findNationalGroup(croatiaToken).getGroupId());

        final PublishOfferResponse publishResponse1 = exchange.processPublishOffer(token, new PublishOfferRequest(offersToShare, groupIds, nominationDeadline));

        assertThat("Offer has been shared to 2 countries", publishResponse1.isOk(), is(true));

        final CreateUserRequest createUserRequest1 = new CreateUserRequest("student_app004@university.edu", "password1", "Student1", "Graduate1");
        createUserRequest1.setStudentAccount(true);

        final CreateUserResponse createStudentResponse1 = administration.createUser(austriaToken, createUserRequest1);

        assertThat("Student has been saved", createStudentResponse1.isOk(), is(true));

        final FetchStudentsRequest fetchStudentsRequest = new FetchStudentsRequest();
        final FetchStudentsResponse fetchStudentsResponse = students.fetchStudents(austriaToken, fetchStudentsRequest);
        assertThat("At least one student has been fetched", fetchStudentsResponse.isOk(), is(true));
        assertThat("At least one student has been fetched", fetchStudentsResponse.getStudents().isEmpty(), is(false));

        final Student student = fetchStudentsResponse.getStudents().get(0);
        student.setAvailable(new DatePeriod(new Date(), nominationDeadline));

        final StudentApplication application = new StudentApplication();
        application.setOffer(saveResponse.getOffer());
        application.setStudent(student);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setHomeAddress(TestData.prepareAddress("DE"));
        application.setAddressDuringTerms(TestData.prepareAddress("AT"));

        final ProcessStudentApplicationsRequest createApplicationsRequest = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createApplicationResponse = students.processStudentApplication(austriaToken, createApplicationsRequest);
        final StudentApplication studentApplication = createApplicationResponse.getStudentApplication();
        assertThat("Student application has been created", createApplicationResponse.isOk(), is(true));

        final StudentApplicationRequest nominateStudentRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.NOMINATED);
        final StudentApplicationResponse nominateStudentResponse = students.processApplicationStatus(austriaToken, nominateStudentRequest);

        assertThat("Student has been nominated by Austria to Poland", nominateStudentResponse.isOk(), is(true));
        assertThat("Application state is NOMINATED", nominateStudentResponse.getStudentApplication().getStatus(), is(ApplicationStatus.NOMINATED));
        assertThat(nominateStudentResponse.getStudentApplication().getNominatedAt(), not(nullValue()));

        final DateTime beforeNominationDate = new DateTime();
        final FetchStudentApplicationsRequest fetchApplicationsRequest = new FetchStudentApplicationsRequest(offerId);
        final FetchStudentApplicationsResponse fetchApplicationsResponse = students.fetchStudentApplications(austriaToken, fetchApplicationsRequest);

        assertThat(fetchApplicationsResponse.isOk(), is(true));
        final StudentApplication foundApplication = findApplicationFromResponse(studentApplication.getApplicationId(), fetchApplicationsResponse);

        assertThat("Make sure that new application state has been persisted", foundApplication.getStatus(), is(ApplicationStatus.NOMINATED));
        assertThat("Nomination date is set", foundApplication.getNominatedAt(), not(nullValue()));
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

    private static StudentApplication findApplicationFromResponse(final String applicationId, final FetchStudentApplicationsResponse response) {
        for (final StudentApplication found : response.getStudentApplications()) {
            if (found.getApplicationId().equals(applicationId)) {
                return found;
            }
        }
        return null;
    }
}
