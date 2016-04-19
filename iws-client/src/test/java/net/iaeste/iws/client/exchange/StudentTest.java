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
import net.iaeste.iws.api.Students;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.GroupList;
import net.iaeste.iws.api.dtos.TestData;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.dtos.exchange.Student;
import net.iaeste.iws.api.dtos.exchange.StudentApplication;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.enums.Gender;
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
import net.iaeste.iws.api.requests.student.StudentRequest;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.responses.student.FetchStudentsResponse;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;
import net.iaeste.iws.api.responses.student.StudentResponse;
import net.iaeste.iws.api.util.Verifications;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DatePeriod;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.ExchangeClient;
import net.iaeste.iws.client.StudentClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class StudentTest extends AbstractTest {

    private static final int exchangeYear = Verifications.calculateExchangeYear();
    private final Exchange exchange = new ExchangeClient();
    private final Students students = new StudentClient();
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
    public void testProcessStudentApplication() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Date nominationDeadline = new Date().plusDays(20);
        final Offer offer = TestData.prepareMinimalOffer("PL-" + exchangeYear + "-001001", "Employer");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);

        // verify processResponse
        assertThat(saveResponse.isOk(), is(true));

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
        application.setOfferId(sharedOffer.getOfferId());
        application.setStudent(student);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setHomeAddress(TestData.prepareAddress("DE"));
        application.setAddressDuringTerms(TestData.prepareAddress("AT"));

        final ProcessStudentApplicationsRequest createStudentApplicationsRequest = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createStudentApplicationResponse = students.processStudentApplication(austriaTokenWithNationalGroup, createStudentApplicationsRequest);
        assertThat(createStudentApplicationResponse.isOk(), is(true));
    }

    @Test
    public void testFetchStudentApplications() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Date nominationDeadline = new Date().plusDays(20);
        final Offer offer = TestData.prepareMinimalOffer("PL-" + exchangeYear + "-001003", "Employer");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);
        final String refNo = saveResponse.getOffer().getRefNo();

        // verify processResponse
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest allOffersRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        FetchOffersResponse allOffersResponse = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse.getOffers().isEmpty(), is(false));

        Offer sharedOffer = findOfferFromResponse(refNo, allOffersResponse);
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
        final GroupList offerGroupsSharedTo = fetchPublishResponse1.getOffersGroups().get(offersExternalId.get(0));
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
        application.setOfferId(sharedOffer.getOfferId());
        application.setStudent(student);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setHomeAddress(TestData.prepareAddress("DE"));
        application.setAddressDuringTerms(TestData.prepareAddress("AT"));
        application.setGender(Gender.FEMALE);
        application.setNationality(TestData.prepareCountry("DE"));

        final ProcessStudentApplicationsRequest createStudentApplicationsRequest = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createStudentApplicationResponse = students.processStudentApplication(austriaTokenWithNationalGroup, createStudentApplicationsRequest);
        assertThat(createStudentApplicationResponse.isOk(), is(true));

        final FetchStudentApplicationsRequest fetchStudentApplicationsRequest = new FetchStudentApplicationsRequest(sharedOffer.getOfferId());
        final FetchStudentApplicationsResponse fetchStudentApplicationsResponse = students.fetchStudentApplications(austriaTokenWithNationalGroup, fetchStudentApplicationsRequest);
        assertThat(fetchStudentApplicationsResponse.isOk(), is(true));
        assertThat(fetchStudentApplicationsResponse.getStudentApplications().size(), is(1));
        final StudentApplication fetchedApplication = fetchStudentApplicationsResponse.getStudentApplications().get(0);
        assertThat(fetchedApplication.getGender(), is(application.getGender()));
        assertThat(fetchedApplication.getGender(), is(application.getGender()));
        assertThat(fetchedApplication.getNationality(), is(application.getNationality()));
    }

    @Test
    public void testUpdateStudentApplication() {
        final Date nominationDeadline = new Date().plusDays(20);
        final Offer offer = TestData.prepareMinimalOffer("PL-" + exchangeYear + "-001002", "Employer");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse saveResponse = exchange.processOffer(token, offerRequest);

        // verify processResponse
        assertThat(saveResponse.isOk(), is(true));

        final FetchOffersRequest allOffersRequest = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse allOffersResponse1 = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse1.getOffers().isEmpty(), is(false));

        final Offer sharedOffer1 = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse1);
        assertThat(sharedOffer1, is(not(nullValue())));
        assertThat(sharedOffer1.getStatus(), is(OfferState.NEW));
        assertThat(sharedOffer1.getNominationDeadline(), is(not(nominationDeadline)));

        final Set<String> offersToShare = new HashSet<>(1);
        offersToShare.add(sharedOffer1.getOfferId());

        final List<String> groupIds = new ArrayList<>(2);
        groupIds.add(findNationalGroup(austriaToken).getGroupId());
        groupIds.add(findNationalGroup(croatiaToken).getGroupId());

        final PublishOfferRequest publishRequest1 = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse1 = exchange.processPublishOffer(token, publishRequest1);

        assertThat(publishResponse1.getError(), is(IWSErrors.SUCCESS));
        assertThat(publishResponse1.isOk(), is(true));

        final List<String> offersExternalId = new ArrayList<>(1);
        offersExternalId.add(sharedOffer1.getOfferId());
        final FetchPublishedGroupsRequest fetchPublishRequest = new FetchPublishedGroupsRequest(offersExternalId);
        final FetchPublishedGroupsResponse fetchPublishResponse1 = exchange.fetchPublishedGroups(token, fetchPublishRequest);

        //is it shared to two groups?
        assertThat(fetchPublishResponse1.isOk(), is(true));
        final GroupList offerGroupsSharedTo = fetchPublishResponse1.getOffersGroups().get(offersExternalId.get(0));
        assertThat(2, is(offerGroupsSharedTo.size()));

        final FetchOffersResponse allOffersResponse2 = exchange.fetchOffers(token, allOffersRequest);
        assertThat(allOffersResponse2.getOffers().isEmpty(), is(false));
        final Offer sharedOffer2 = findOfferFromResponse(saveResponse.getOffer().getRefNo(), allOffersResponse2);
        assertThat(sharedOffer2, is(not(nullValue())));
        assertThat(sharedOffer2.getRefNo(), is(saveResponse.getOffer().getRefNo()));
        assertThat("The offer is shared now, the status has to be SHARED", sharedOffer2.getStatus(), is(OfferState.SHARED));
        assertThat(sharedOffer2.getNominationDeadline(), is(nominationDeadline));

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

        final StudentApplication application1 = new StudentApplication();
        application1.setOfferId(sharedOffer2.getOfferId());
        application1.setStudent(student);
        application1.setStatus(ApplicationStatus.APPLIED);
        application1.setHomeAddress(TestData.prepareAddress("DE"));
        application1.setAddressDuringTerms(TestData.prepareAddress("AT"));

        final ProcessStudentApplicationsRequest createStudentApplicationsRequest = new ProcessStudentApplicationsRequest(application1);
        // We have two Austrian Groups with the same permission; Staff & LC1.
        // The problem is that the permission check is failing, so we need to
        // add the Staff Group explicitly
        // Setting the Staff Group, to avoid any permission error
        austriaToken.setGroupId("9f2c4db6-38c9-4a2f-bdaf-141bd1eb4c13");
        final StudentApplicationResponse createStudentApplicationResponse = students.processStudentApplication(austriaToken, createStudentApplicationsRequest);
        assertThat(createStudentApplicationResponse.isOk(), is(true));

        //test updating existing application
        final StudentApplication application2 = createStudentApplicationResponse.getStudentApplication();
        application2.setUniversity("MyUniversity");
        final ProcessStudentApplicationsRequest createStudentApplicationsRequest2 = new ProcessStudentApplicationsRequest(application2);
        final StudentApplicationResponse createStudentApplicationResponse2 = students.processStudentApplication(austriaToken, createStudentApplicationsRequest2);
        assertThat(createStudentApplicationResponse2.isOk(), is(true));
        assertThat(createStudentApplicationResponse2.getStudentApplication().getUniversity(), is(application2.getUniversity()));
    }

    @Test
    public void testNominatingApplication() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Date nominationDeadline = new Date().plusDays(20);
        final Offer offer = TestData.prepareMinimalOffer("PL-" + exchangeYear + "-001004", "Employer");

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
        application.setOfferId(saveResponse.getOffer().getOfferId());
        application.setStudent(student);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setHomeAddress(TestData.prepareAddress("DE"));
        application.setAddressDuringTerms(TestData.prepareAddress("AT"));

        final ProcessStudentApplicationsRequest createApplicationsRequest = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createApplicationResponse = students.processStudentApplication(austriaTokenWithNationalGroup, createApplicationsRequest);
        final StudentApplication studentApplication = createApplicationResponse.getStudentApplication();
        assertThat("Student application has been created", createApplicationResponse.isOk(), is(true));

        final StudentApplicationRequest nominateStudentRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.NOMINATED);
        final StudentApplicationResponse nominateStudentResponse = students.processApplicationStatus(austriaTokenWithNationalGroup, nominateStudentRequest);

        assertThat("Student has been nominated by Austria to Poland", nominateStudentResponse.isOk(), is(true));
        assertThat("Application state is NOMINATED", nominateStudentResponse.getStudentApplication().getStatus(), is(ApplicationStatus.NOMINATED));
        assertThat(nominateStudentResponse.getStudentApplication().getNominatedAt(), not(nullValue()));

        final FetchStudentApplicationsRequest fetchApplicationsRequest = new FetchStudentApplicationsRequest(offerId);
        final FetchStudentApplicationsResponse fetchApplicationsResponse = students.fetchStudentApplications(austriaTokenWithNationalGroup, fetchApplicationsRequest);

        assertThat(fetchApplicationsResponse.isOk(), is(true));
        final StudentApplication foundApplication = findApplicationFromResponse(studentApplication.getApplicationId(), fetchApplicationsResponse);

        assertThat("Make sure that new application state has been persisted", foundApplication.getStatus(), is(ApplicationStatus.NOMINATED));
        assertThat("Nomination date is set", foundApplication.getNominatedAt(), not(nullValue()));
    }

    @Test
    public void testProcessStudent() {
        final CreateUserRequest createUserRequest = new CreateUserRequest("student_app005@university.edu", "password1", "Student1", "Graduate1");
        createUserRequest.setStudentAccount(true);
        final CreateUserResponse createStudentResponse = administration.createUser(austriaToken, createUserRequest);
        assertThat(createStudentResponse.isOk(), is(true));

        final Student newStudent = new Student();
        newStudent.setUser(createStudentResponse.getUser());

        final StudentRequest processStudentRequest = new StudentRequest(newStudent);
        final StudentResponse processStudentResponse = students.processStudent(austriaToken, processStudentRequest);
        assertThat(processStudentResponse.isOk(), is(true));

        final FetchStudentsRequest fetchStudentsRequest = new FetchStudentsRequest();
        final FetchStudentsResponse fetchStudentsResponse = students.fetchStudents(austriaToken, fetchStudentsRequest);
        assertThat(fetchStudentsResponse.isOk(), is(true));
    }

    @Test
    public void testRejectNominatedApplication() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Date nominationDeadline = new Date().plusDays(20);
        final Offer offer = TestData.prepareMinimalOffer("PL-" + exchangeYear + "-001005", "Employer");

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

        final CreateUserRequest createUserRequest1 = new CreateUserRequest("student_app006@university.edu", "password1", "Student1", "Graduate1");
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
        application.setOfferId(saveResponse.getOffer().getOfferId());
        application.setStudent(student);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setHomeAddress(TestData.prepareAddress("DE"));
        application.setAddressDuringTerms(TestData.prepareAddress("AT"));

        final ProcessStudentApplicationsRequest createApplicationsRequest = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createApplicationResponse = students.processStudentApplication(austriaTokenWithNationalGroup, createApplicationsRequest);
        final StudentApplication studentApplication = createApplicationResponse.getStudentApplication();
        assertThat("Student application has been created", createApplicationResponse.isOk(), is(true));

        final StudentApplicationRequest nominateStudentRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.NOMINATED);
        final StudentApplicationResponse nominateStudentResponse = students.processApplicationStatus(austriaTokenWithNationalGroup, nominateStudentRequest);

        assertThat("Student has been nominated by Austria to Poland", nominateStudentResponse.isOk(), is(true));
        assertThat("Application state is NOMINATED", nominateStudentResponse.getStudentApplication().getStatus(), is(ApplicationStatus.NOMINATED));

        final StudentApplicationRequest rejectStudentRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.REJECTED);
        rejectStudentRequest.setRejectInternalComment("reject internal comment");
        rejectStudentRequest.setRejectDescription("reject description");
        rejectStudentRequest.setRejectByEmployerReason("reject employer reason");
        final StudentApplicationResponse rejectStudentResponse = students.processApplicationStatus(token, rejectStudentRequest);

        assertThat("Student has been rejected by Poland", rejectStudentResponse.isOk(), is(true));
        final StudentApplication rejectedApplication = rejectStudentResponse.getStudentApplication();
        assertThat(rejectedApplication.getRejectByEmployerReason(), is(rejectStudentRequest.getRejectByEmployerReason()));
        assertThat(rejectedApplication.getRejectDescription(), is(rejectStudentRequest.getRejectDescription()));
        assertThat(rejectedApplication.getRejectInternalComment(), is(rejectStudentRequest.getRejectInternalComment()));
    }

    @Test
    public void testRejectAppliedApplicationBySendingCountry() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        //Sending country is only allowed to reject applied application
        final Date nominationDeadline = new Date().plusDays(20);
        final Offer offer = TestData.prepareMinimalOffer("PL-" + exchangeYear + "-001065", "Employer");

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

        final CreateUserRequest createUserRequest1 = new CreateUserRequest("student_app065@university.edu", "password1", "Student1", "Graduate1");
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
        application.setOfferId(saveResponse.getOffer().getOfferId());
        application.setStudent(student);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setHomeAddress(TestData.prepareAddress("DE"));
        application.setAddressDuringTerms(TestData.prepareAddress("AT"));

        final ProcessStudentApplicationsRequest createApplicationsRequest = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createApplicationResponse = students.processStudentApplication(austriaTokenWithNationalGroup, createApplicationsRequest);
        final StudentApplication studentApplication = createApplicationResponse.getStudentApplication();
        assertThat("Student application has been created", createApplicationResponse.isOk(), is(true));

        final StudentApplicationRequest rejectStudentRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.REJECTED_BY_SENDING_COUNTRY);
        rejectStudentRequest.setRejectInternalComment("reject internal comment");
        rejectStudentRequest.setRejectDescription("reject description");
        final StudentApplicationResponse rejectStudentResponse = students.processApplicationStatus(austriaTokenWithNationalGroup, rejectStudentRequest);

        assertThat("Student has been rejected by Austria (sending country)", rejectStudentResponse.isOk(), is(true));
        final StudentApplication rejectedApplication = rejectStudentResponse.getStudentApplication();
        assertThat(rejectedApplication.getRejectDescription(), is(rejectStudentRequest.getRejectDescription()));
        assertThat(rejectedApplication.getRejectInternalComment(), is(rejectStudentRequest.getRejectInternalComment()));
    }

    @Test
    public void testRejectApplicationBySendingCountryForClosedOffer() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        //Sending country is only allowed to reject applied application
        final Date nominationDeadline = new Date().plusDays(20);
        final Offer offer = TestData.prepareMinimalOffer("PL-" + exchangeYear + "-001064", "Employer");

        final OfferResponse saveResponse = exchange.processOffer(token, new ProcessOfferRequest(offer));
        assertThat("Offer has been saved", saveResponse.isOk(), is(true));

        final Set<String> offersToShare = new HashSet<>(1);
        final String offerId = saveResponse.getOffer().getOfferId();
        offersToShare.add(offerId);

        List<String> groupIds = new ArrayList<>(2);
        groupIds.add(findNationalGroup(austriaToken).getGroupId());
        groupIds.add(findNationalGroup(croatiaToken).getGroupId());

        final PublishOfferResponse publishResponse1 = exchange.processPublishOffer(token, new PublishOfferRequest(offersToShare, groupIds, nominationDeadline));

        assertThat("Offer has been shared to 2 countries", publishResponse1.isOk(), is(true));

        final CreateUserRequest createUserRequest1 = new CreateUserRequest("student_app064@university.edu", "password1", "Student1", "Graduate1");
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
        application.setOfferId(saveResponse.getOffer().getOfferId());
        application.setStudent(student);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setHomeAddress(TestData.prepareAddress("DE"));
        application.setAddressDuringTerms(TestData.prepareAddress("AT"));

        final ProcessStudentApplicationsRequest createApplicationsRequest = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createApplicationResponse = students.processStudentApplication(austriaTokenWithNationalGroup, createApplicationsRequest);
        final StudentApplication studentApplication = createApplicationResponse.getStudentApplication();
        assertThat("Student application has been created", createApplicationResponse.isOk(), is(true));

        //usnhare offer
        groupIds.clear();
        final PublishOfferResponse unshareResponse = exchange.processPublishOffer(token, new PublishOfferRequest(offersToShare, groupIds, nominationDeadline));
        assertThat("Offer has been unshared", unshareResponse.isOk(), is(true));

        final StudentApplicationRequest rejectStudentRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.REJECTED_BY_SENDING_COUNTRY);
        rejectStudentRequest.setRejectInternalComment("reject internal comment");
        rejectStudentRequest.setRejectDescription("reject description");
        final StudentApplicationResponse rejectStudentResponse = students.processApplicationStatus(austriaTokenWithNationalGroup, rejectStudentRequest);

        assertThat("Student has been rejected by Austria (sending country)", rejectStudentResponse.isOk(), is(true));
        final StudentApplication rejectedApplication = rejectStudentResponse.getStudentApplication();
        assertThat(rejectedApplication.getRejectDescription(), is(rejectStudentRequest.getRejectDescription()));
        assertThat(rejectedApplication.getRejectInternalComment(), is(rejectStudentRequest.getRejectInternalComment()));
    }

    @Test
    public void testCancelNominatedApplication() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Date nominationDeadline = new Date().plusDays(20);
        final Offer offer = TestData.prepareMinimalOffer("PL-" + exchangeYear + "-001006", "Employer");

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

        final CreateUserRequest createUserRequest1 = new CreateUserRequest("student_app008@university.edu", "password1", "Student1", "Graduate1");
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
        application.setOfferId(saveResponse.getOffer().getOfferId());
        application.setStudent(student);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setHomeAddress(TestData.prepareAddress("DE"));
        application.setAddressDuringTerms(TestData.prepareAddress("AT"));

        final ProcessStudentApplicationsRequest createApplicationsRequest = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createApplicationResponse = students.processStudentApplication(austriaTokenWithNationalGroup, createApplicationsRequest);
        final StudentApplication studentApplication = createApplicationResponse.getStudentApplication();
        assertThat("Student application has been created", createApplicationResponse.isOk(), is(true));

        final StudentApplicationRequest nominateStudentRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.NOMINATED);
        final StudentApplicationResponse nominateStudentResponse = students.processApplicationStatus(austriaTokenWithNationalGroup, nominateStudentRequest);

        assertThat("Student has been nominated by Austria to Poland", nominateStudentResponse.isOk(), is(true));
        assertThat("Application state is NOMINATED", nominateStudentResponse.getStudentApplication().getStatus(), is(ApplicationStatus.NOMINATED));

        final StudentApplicationRequest cancelStudentRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.CANCELLED);
        final StudentApplicationResponse rejectStudentResponse = students.processApplicationStatus(austriaTokenWithNationalGroup, cancelStudentRequest);

        assertThat("Student has been canceled by Austria", rejectStudentResponse.isOk(), is(true));
        final StudentApplication rejectedApplication = rejectStudentResponse.getStudentApplication();
        assertThat(rejectedApplication.getStatus(), is(ApplicationStatus.CANCELLED));
    }

    @Test
    public void testApplyCancelledApplication() {
        final Date nominationDeadline = new Date().plusDays(20);
        final Offer offer = TestData.prepareMinimalOffer("PL-" + exchangeYear + "-001007", "Employer");

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

        final CreateUserRequest createUserRequest1 = new CreateUserRequest("student_app009@university.edu", "password1", "Student1", "Graduate1");
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
        application.setOfferId(saveResponse.getOffer().getOfferId());
        application.setStudent(student);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setHomeAddress(TestData.prepareAddress("DE"));
        application.setAddressDuringTerms(TestData.prepareAddress("AT"));

        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final ProcessStudentApplicationsRequest createApplicationsRequest = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createApplicationResponse = students.processStudentApplication(austriaTokenWithNationalGroup, createApplicationsRequest);
        final StudentApplication studentApplication = createApplicationResponse.getStudentApplication();
        assertThat("Student application has been created", createApplicationResponse.isOk(), is(true));

        final StudentApplicationRequest cancelStudentApplicationRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.CANCELLED);
        final StudentApplicationResponse cancelStudentApplicationResponse = students.processApplicationStatus(austriaTokenWithNationalGroup, cancelStudentApplicationRequest);

        assertThat("Student has been cancelled", cancelStudentApplicationResponse.isOk(), is(true));
        assertThat("Application state is CANCELLED", cancelStudentApplicationResponse.getStudentApplication().getStatus(), is(ApplicationStatus.CANCELLED));

        final StudentApplicationRequest applyStudentRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.APPLIED);
        final StudentApplicationResponse rejectStudentResponse = students.processApplicationStatus(austriaTokenWithNationalGroup, applyStudentRequest);

        assertThat("Student has been canceled by Austria", rejectStudentResponse.isOk(), is(true));
        final StudentApplication rejectedApplication = rejectStudentResponse.getStudentApplication();
        assertThat(rejectedApplication.getStatus(), is(ApplicationStatus.APPLIED));
    }

    @Test
    public void testAcceptAtEmployerApplication() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Date nominationDeadline = new Date().plusDays(20);
        final Offer offer = TestData.prepareMinimalOffer("PL-" + exchangeYear + "-001008", "Employer");

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

        final CreateUserRequest createUserRequest1 = new CreateUserRequest("student_app010@university.edu", "password1", "Student1", "Graduate1");
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
        application.setOfferId(saveResponse.getOffer().getOfferId());
        application.setStudent(student);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setHomeAddress(TestData.prepareAddress("DE"));
        application.setAddressDuringTerms(TestData.prepareAddress("AT"));

        final ProcessStudentApplicationsRequest createApplicationsRequest = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createApplicationResponse = students.processStudentApplication(austriaTokenWithNationalGroup, createApplicationsRequest);
        final StudentApplication studentApplication = createApplicationResponse.getStudentApplication();
        assertThat("Student application has been created", createApplicationResponse.isOk(), is(true));

        final StudentApplicationRequest nominateStudentApplicationRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.NOMINATED);
        final StudentApplicationResponse nominateStudentApplicationResponse = students.processApplicationStatus(austriaTokenWithNationalGroup, nominateStudentApplicationRequest);

        assertThat("Student has been nominated by Austria to Poland", nominateStudentApplicationResponse.isOk(), is(true));
        assertThat("Application state is NOMINATED", nominateStudentApplicationResponse.getStudentApplication().getStatus(), is(ApplicationStatus.NOMINATED));

        final StudentApplicationRequest forwardStudentApplicationRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.FORWARDED_TO_EMPLOYER);
        final StudentApplicationResponse forwardStudentApplicationResponse = students.processApplicationStatus(token, forwardStudentApplicationRequest);

        assertThat("Student has been forwarded to employer", forwardStudentApplicationResponse.isOk(), is(true));
        assertThat("Application state is FORWARDED_TO_EMPLOYER", forwardStudentApplicationResponse.getStudentApplication().getStatus(), is(ApplicationStatus.FORWARDED_TO_EMPLOYER));

        final StudentApplicationRequest acceptStudentApplicationRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.ACCEPTED);
        final StudentApplicationResponse acceptStudentApplicationResponse = students.processApplicationStatus(token, acceptStudentApplicationRequest);

        assertThat("Student has been accepted", acceptStudentApplicationResponse.isOk(), is(true));
        assertThat(acceptStudentApplicationResponse.getStudentApplication().getStatus(), is(ApplicationStatus.ACCEPTED));
    }

    @Test
    public void testCancelAcceptedApplication() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Date nominationDeadline = new Date().plusDays(20);
        final Offer offer = TestData.prepareMinimalOffer("PL-" + exchangeYear + "-001009", "Employer");

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

        final CreateUserRequest createUserRequest1 = new CreateUserRequest("student_app011@university.edu", "password1", "Student1", "Graduate1");
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
        application.setOfferId(saveResponse.getOffer().getOfferId());
        application.setStudent(student);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setHomeAddress(TestData.prepareAddress("DE"));
        application.setAddressDuringTerms(TestData.prepareAddress("AT"));

        final ProcessStudentApplicationsRequest createApplicationsRequest = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createApplicationResponse = students.processStudentApplication(austriaTokenWithNationalGroup, createApplicationsRequest);
        final StudentApplication studentApplication = createApplicationResponse.getStudentApplication();
        assertThat("Student application has been created", createApplicationResponse.isOk(), is(true));

        final StudentApplicationRequest nominateStudentApplicationRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.NOMINATED);
        final StudentApplicationResponse nominateStudentApplicationResponse = students.processApplicationStatus(austriaTokenWithNationalGroup, nominateStudentApplicationRequest);

        assertThat("Student has been nominated by Austria to Poland", nominateStudentApplicationResponse.isOk(), is(true));
        assertThat("Application state is NOMINATED", nominateStudentApplicationResponse.getStudentApplication().getStatus(), is(ApplicationStatus.NOMINATED));

        final StudentApplicationRequest forwardStudentApplicationRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.FORWARDED_TO_EMPLOYER);
        final StudentApplicationResponse forwardStudentApplicationResponse = students.processApplicationStatus(token, forwardStudentApplicationRequest);

        assertThat("Student has been forwarded to employer", forwardStudentApplicationResponse.isOk(), is(true));
        assertThat("Application state is FORWARDED_TO_EMPLOYER", forwardStudentApplicationResponse.getStudentApplication().getStatus(), is(ApplicationStatus.FORWARDED_TO_EMPLOYER));

        final StudentApplicationRequest acceptStudentApplicationRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.ACCEPTED);
        final StudentApplicationResponse acceptStudentApplicationResponse = students.processApplicationStatus(token, acceptStudentApplicationRequest);

        assertThat("Student has been accepted", acceptStudentApplicationResponse.isOk(), is(true));
        assertThat(acceptStudentApplicationResponse.getStudentApplication().getStatus(), is(ApplicationStatus.ACCEPTED));

        final StudentApplicationRequest cancelStudentApplicationRequest = new StudentApplicationRequest(
                studentApplication.getApplicationId(),
                ApplicationStatus.CANCELLED);
        final StudentApplicationResponse cancelStudentApplicationResponse = students.processApplicationStatus(austriaTokenWithNationalGroup, cancelStudentApplicationRequest);

        assertThat("Student has been cancelled", cancelStudentApplicationResponse.isOk(), is(true));
        assertThat("Application state is CANCELLED", cancelStudentApplicationResponse.getStudentApplication().getStatus(), is(ApplicationStatus.CANCELLED));
    }

    @Test
    public void testFetchSharedDomesticOfferWithApplication() {
        final AuthenticationToken austriaTokenWithNationalGroup = new AuthenticationToken(austriaToken);
        if (austriaTokenNationallGroup != null) {
            austriaTokenWithNationalGroup.setGroupId(austriaTokenNationallGroup.getGroupId());
        }

        final Date nominationDeadline = new Date().plusDays(20);
        final String refNo = "AT-" + exchangeYear + "-000003";
        final Offer offer = TestData.prepareMinimalOffer(refNo, "Employer");

        offer.setPrivateComment("austria");

        final ProcessOfferRequest offerRequest = new ProcessOfferRequest(offer);
        final OfferResponse processResponse = exchange.processOffer(austriaTokenWithNationalGroup, offerRequest);

        assertThat("verify that the offer was persisted", processResponse.isOk(), is(true));
        assertThat(processResponse.getOffer().getStatus(), is(OfferState.NEW));

        final Set<String> offersToShare = new HashSet<>(1);
        offersToShare.add(processResponse.getOffer().getOfferId());

        final List<String> groupIds = new ArrayList<>(2);
        groupIds.add(findNationalGroup(croatiaToken).getGroupId());

        final PublishOfferRequest publishRequest = new PublishOfferRequest(offersToShare, groupIds, nominationDeadline);
        final PublishOfferResponse publishResponse = exchange.processPublishOffer(austriaTokenWithNationalGroup, publishRequest);

        assertThat("verify that there was no error during sharing the offer", publishResponse.getError(), is(IWSErrors.SUCCESS));
        assertThat("verify that the offer was successfully shared with Croatia", publishResponse.isOk(), is(true));

        final FetchOffersRequest requestHr1 = new FetchOffersRequest(FetchType.SHARED);
        final FetchOffersResponse fetchResponseHr1 = exchange.fetchOffers(croatiaToken, requestHr1);
        final Offer readOfferHr1 = findOfferFromResponse(refNo, fetchResponseHr1);

        assertThat("Foreign offer was loaded", readOfferHr1, is(not(nullValue())));
        assertThat("Foreign offer has correct state", readOfferHr1.getStatus(), is(OfferState.SHARED));

        final CreateUserRequest createUserRequest = new CreateUserRequest("student_app007@university.edu", "password1", "Student1", "Graduate1");
        createUserRequest.setStudentAccount(true);

        final CreateUserResponse createStudentResponse = administration.createUser(croatiaToken, createUserRequest);

        assertThat("Student has been saved", createStudentResponse.isOk(), is(true));

        final FetchStudentsRequest fetchStudentsRequest = new FetchStudentsRequest();
        final FetchStudentsResponse fetchStudentsResponse = students.fetchStudents(croatiaToken, fetchStudentsRequest);
        assertThat("At least one student has been fetched", fetchStudentsResponse.isOk(), is(true));
        assertThat("At least one student has been fetched", fetchStudentsResponse.getStudents().isEmpty(), is(false));

        final Student student = fetchStudentsResponse.getStudents().get(0);
        student.setAvailable(new DatePeriod(new Date(), nominationDeadline));

        final StudentApplication application = new StudentApplication();
        application.setOfferId(readOfferHr1.getOfferId());
        application.setStudent(student);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setHomeAddress(TestData.prepareAddress("DE"));
        application.setAddressDuringTerms(TestData.prepareAddress("DE"));

        final ProcessStudentApplicationsRequest createApplicationsRequest = new ProcessStudentApplicationsRequest(application);
        final StudentApplicationResponse createApplicationResponse = students.processStudentApplication(croatiaToken, createApplicationsRequest);
        assertThat("Student application has been created", createApplicationResponse.isOk(), is(true));

        final FetchOffersRequest requestHr = new FetchOffersRequest(FetchType.SHARED);
        final FetchOffersResponse fetchResponseHr = exchange.fetchOffers(croatiaToken, requestHr);
        final Offer readOfferHr = findOfferFromResponse(refNo, fetchResponseHr);

        assertThat("Foreign offer was loaded", readOfferHr, is(not(nullValue())));
        assertThat("Foreign offer has correct state", readOfferHr.getStatus(), is(OfferState.APPLICATIONS));
        assertThat("Foreign offer should not see private comment", readOfferHr.getPrivateComment(), is(nullValue()));

        final FetchOffersRequest requestAt = new FetchOffersRequest(FetchType.DOMESTIC);
        final FetchOffersResponse fetchResponseAt = exchange.fetchOffers(austriaTokenWithNationalGroup, requestAt);
        final Offer readOfferAt = findOfferFromResponse(refNo, fetchResponseAt);

        assertThat("Domestic offer was loaded", readOfferAt, is(not(nullValue())));
        assertThat("Domestic offer has correct state", readOfferAt.getStatus(), is(OfferState.SHARED));
        assertThat("Domestic offer should see private comment", readOfferAt.getPrivateComment(), is("austria"));
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
