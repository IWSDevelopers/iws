/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ws-client) - net.iaeste.iws.ws.client.mappers.StudentMapper
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ws.client.mappers;

import net.iaeste.iws.api.dtos.exchange.Student;
import net.iaeste.iws.api.dtos.exchange.StudentAcceptance;
import net.iaeste.iws.api.dtos.exchange.StudentAcceptanceConfirmation;
import net.iaeste.iws.api.dtos.exchange.StudentApplication;
import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.enums.exchange.Specialization;
import net.iaeste.iws.api.enums.exchange.TransportationType;
import net.iaeste.iws.api.requests.student.FetchStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.FetchStudentsRequest;
import net.iaeste.iws.api.requests.student.ProcessStudentApplicationsRequest;
import net.iaeste.iws.api.requests.student.StudentApplicationRequest;
import net.iaeste.iws.api.requests.student.StudentRequest;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.responses.student.FetchStudentsResponse;
import net.iaeste.iws.api.responses.student.StudentApplicationResponse;
import net.iaeste.iws.api.responses.student.StudentResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class StudentMapper extends CommonMapper {

    public static net.iaeste.iws.ws.StudentRequest map(final StudentRequest api) {
        net.iaeste.iws.ws.StudentRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.StudentRequest();

            ws.setStudent(map(api.getStudent()));
        }

        return ws;
    }

    public static StudentResponse map(final net.iaeste.iws.ws.StudentResponse ws) {
        StudentResponse api = null;

        if (ws != null) {
            api = new StudentResponse(map(ws.getError()), ws.getMessage());

            api.setStudent(map(ws.getStudent()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.FetchStudentsRequest map(final FetchStudentsRequest api) {
        net.iaeste.iws.ws.FetchStudentsRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchStudentsRequest();

            ws.setPage(map(api.getPagingInformation()));
        }

        return ws;
    }

    public static FetchStudentsResponse map(final net.iaeste.iws.ws.FetchStudentsResponse ws) {
        FetchStudentsResponse api = null;

        if (ws != null) {
            api = new FetchStudentsResponse(map(ws.getError()), ws.getMessage());

            api.setStudents(mapWSStudentCollection(ws.getStudents()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.ProcessStudentApplicationsRequest map(final ProcessStudentApplicationsRequest api) {
        net.iaeste.iws.ws.ProcessStudentApplicationsRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.ProcessStudentApplicationsRequest();

            ws.setStudentApplication(map(api.getStudentApplication()));
        }

        return ws;
    }

    public static StudentApplicationResponse map(final net.iaeste.iws.ws.StudentApplicationResponse ws) {
        StudentApplicationResponse api = null;

        if (ws != null) {
            api = new StudentApplicationResponse(map(ws.getError()), ws.getMessage());

            api.setStudentApplication(map(ws.getStudentApplication()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.FetchStudentApplicationsRequest map(final FetchStudentApplicationsRequest api) {
        net.iaeste.iws.ws.FetchStudentApplicationsRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchStudentApplicationsRequest();

            ws.setPage(map(api.getPagingInformation()));
            ws.setOfferId(api.getOfferId());
        }

        return ws;
    }

    public static FetchStudentApplicationsResponse map(final net.iaeste.iws.ws.FetchStudentApplicationsResponse ws) {
        FetchStudentApplicationsResponse api = null;

        if (ws != null) {
            api = new FetchStudentApplicationsResponse(map(ws.getError()), ws.getMessage());

            api.setStudentApplications(mapWSStudentApplicationCollection(ws.getStudentApplications()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.StudentApplicationRequest map(final StudentApplicationRequest api) {
        net.iaeste.iws.ws.StudentApplicationRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.StudentApplicationRequest();

            ws.setApplicationId(api.getApplicationId());
            ws.setStatus(map(api.getStatus()));
            ws.setRejectByEmployerReason(api.getRejectByEmployerReason());
            ws.setRejectDescription(api.getRejectDescription());
            ws.setRejectInternalComment(api.getRejectInternalComment());
        }

        return ws;
    }

    // =========================================================================
    // Internal mapping of required Collections, DTO's & Enums
    // =========================================================================

    private static List<Student> mapWSStudentCollection(final List<net.iaeste.iws.ws.Student> ws) {
        final List<Student> api;

        if (ws != null) {
            api = new ArrayList<>(ws.size());

            for (final net.iaeste.iws.ws.Student student : ws) {
                api.add(map(student));
            }
        } else {
            api = new ArrayList<>(0);
        }

        return api;
    }

    private static List<StudentApplication> mapWSStudentApplicationCollection(final List<net.iaeste.iws.ws.StudentApplication> ws) {
        final List<StudentApplication> api;

        if (ws != null) {
            api = new ArrayList<>(ws.size());

            for (final net.iaeste.iws.ws.StudentApplication studentApplication : ws) {
                api.add(map(studentApplication));
            }
        } else {
            api = new ArrayList<>(0);
        }

        return api;
    }

    private static Set<Specialization> mapWSSpecializationCollection(final Collection<net.iaeste.iws.ws.Specialization> ws) {
        final Set<Specialization> api = EnumSet.noneOf(Specialization.class);

        if (ws != null) {
            for (final net.iaeste.iws.ws.Specialization specialization : ws) {
                api.add(map(specialization));
            }
        }

        return api;
    }

    private static Set<net.iaeste.iws.ws.Specialization> mapAPISpecializationCollection(final Collection<Specialization> api) {
        final Set<net.iaeste.iws.ws.Specialization> ws = EnumSet.noneOf(net.iaeste.iws.ws.Specialization.class);

        if (api != null) {
            for (final Specialization specialization : api) {
                ws.add(map(specialization));
            }
        }

        return ws;
    }

    private static Student map(final net.iaeste.iws.ws.Student ws) {
        Student api = null;

        if (ws != null) {
            api = new Student();

            api.setUser(map(ws.getUser()));
            api.setStudyLevel(map(ws.getStudyLevel()));
            api.setFieldOfStudies(mapFieldOfStudyCollection(ws.getFieldOfStudies()));
            api.setSpecializations(mapWSSpecializationCollection(ws.getSpecializations()));
            api.setAvailable(map(ws.getAvailable()));
            api.setLanguage1(map(ws.getLanguage1()));
            api.setLanguage1Level(map(ws.getLanguage1Level()));
            api.setLanguage2(map(ws.getLanguage2()));
            api.setLanguage2Level(map(ws.getLanguage2Level()));
            api.setLanguage3(map(ws.getLanguage3()));
            api.setLanguage3Level(map(ws.getLanguage3Level()));
            api.setModified(map(ws.getModified()));
            api.setCreated(map(ws.getCreated()));
        }

        return api;
    }

    private static net.iaeste.iws.ws.Student map(final Student api) {
        net.iaeste.iws.ws.Student ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Student();

            ws.setUser(map(api.getUser()));
            ws.setStudyLevel(map(api.getStudyLevel()));
            ws.getFieldOfStudies().addAll(mapAPIFieldOfStudyCollection(api.getFieldOfStudies()));
            ws.getSpecializations().addAll(mapAPISpecializationCollection(api.getSpecializations()));
            ws.setAvailable(map(api.getAvailable()));
            ws.setLanguage1(map(api.getLanguage1()));
            ws.setLanguage1Level(map(api.getLanguage1Level()));
            ws.setLanguage2(map(api.getLanguage2()));
            ws.setLanguage2Level(map(api.getLanguage2Level()));
            ws.setLanguage3(map(api.getLanguage3()));
            ws.setLanguage3Level(map(api.getLanguage3Level()));
            ws.setModified(map(api.getModified()));
            ws.setCreated(map(api.getCreated()));
        }

        return ws;
    }

    private static StudentApplication map(final net.iaeste.iws.ws.StudentApplication ws) {
        StudentApplication api = null;

        if (ws != null) {
            api = new StudentApplication();

            api.setApplicationId(ws.getApplicationId());
            api.setOfferId(ws.getOfferId());
            api.setOfferState(map(ws.getOfferState()));
            api.setStudent(map(ws.getStudent()));
            api.setStatus(map(ws.getStatus()));
            api.setHomeAddress(map(ws.getHomeAddress()));
            api.setEmail(ws.getEmail());
            api.setPhoneNumber(ws.getPhoneNumber());
            api.setAddressDuringTerms(map(ws.getAddressDuringTerms()));
            api.setDateOfBirth(map(ws.getDateOfBirth()));
            api.setUniversity(ws.getUniversity());
            api.setPlaceOfBirth(ws.getPlaceOfBirth());
            api.setNationality(map(ws.getNationality()));
            api.setGender(map(ws.getGender()));
            api.setCompletedYearsOfStudy(ws.getCompletedYearsOfStudy());
            api.setTotalYearsOfStudy(ws.getTotalYearsOfStudy());
            api.setIsLodgingByIaeste(ws.isLodgingByIaeste());
            api.setLanguage1(map(ws.getLanguage1()));
            api.setLanguage1Level(map(ws.getLanguage1Level()));
            api.setLanguage2(map(ws.getLanguage2()));
            api.setLanguage2Level(map(ws.getLanguage2Level()));
            api.setLanguage3(map(ws.getLanguage3()));
            api.setLanguage3Level(map(ws.getLanguage3Level()));
            api.setAvailable(map(ws.getAvailable()));
            api.setFieldOfStudies(mapFieldOfStudyCollection(ws.getFieldOfStudies()));
            api.setSpecializations(mapStringCollection(ws.getSpecializations()));
            api.setPassportNumber(ws.getPassportNumber());
            api.setPassportPlaceOfIssue(ws.getPassportPlaceOfIssue());
            api.setPassportValidUntil(ws.getPassportValidUntil());
            api.setRejectByEmployerReason(ws.getRejectByEmployerReason());
            api.setRejectDescription(ws.getRejectDescription());
            api.setRejectInternalComment(ws.getRejectInternalComment());
            api.setAcceptance(map(ws.getAcceptance()));
            api.setTravelInformation(map(ws.getTravelInformation()));
            api.setNominatedAt(map(ws.getNominatedAt()));
            api.setAttachments(mapWSFileCollection(ws.getAttachments()));
            api.setModified(map(ws.getModified()));
            api.setCreated(map(ws.getCreated()));
        }

        return api;
    }

    private static net.iaeste.iws.ws.StudentApplication map(final StudentApplication api) {
        net.iaeste.iws.ws.StudentApplication ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.StudentApplication();

            ws.setApplicationId(api.getApplicationId());
            ws.setOfferId(api.getOfferId());
            ws.setOfferState(map(api.getOfferState()));
            ws.setStudent(map(api.getStudent()));
            ws.setStatus(map(api.getStatus()));
            ws.setHomeAddress(map(api.getHomeAddress()));
            ws.setEmail(api.getEmail());
            ws.setPhoneNumber(api.getPhoneNumber());
            ws.setAddressDuringTerms(map(api.getAddressDuringTerms()));
            ws.setDateOfBirth(map(api.getDateOfBirth()));
            ws.setUniversity(api.getUniversity());
            ws.setPlaceOfBirth(api.getPlaceOfBirth());
            ws.setNationality(map(api.getNationality()));
            ws.setGender(map(api.getGender()));
            ws.setCompletedYearsOfStudy(api.getCompletedYearsOfStudy());
            ws.setTotalYearsOfStudy(api.getTotalYearsOfStudy());
            ws.setLodgingByIaeste(api.getIsLodgingByIaeste());
            ws.setLanguage1(map(api.getLanguage1()));
            ws.setLanguage1Level(map(api.getLanguage1Level()));
            ws.setLanguage2(map(api.getLanguage2()));
            ws.setLanguage2Level(map(api.getLanguage2Level()));
            ws.setLanguage3(map(api.getLanguage3()));
            ws.setLanguage3Level(map(api.getLanguage3Level()));
            ws.setAvailable(map(api.getAvailable()));
            ws.getFieldOfStudies().addAll(mapAPIFieldOfStudyCollection(api.getFieldOfStudies()));
            ws.getSpecializations().addAll(mapStringCollection(api.getSpecializations()));
            ws.setPassportNumber(api.getPassportNumber());
            ws.setPassportPlaceOfIssue(api.getPassportPlaceOfIssue());
            ws.setPassportValidUntil(api.getPassportValidUntil());
            ws.setRejectByEmployerReason(api.getRejectByEmployerReason());
            ws.setRejectDescription(api.getRejectDescription());
            ws.setRejectInternalComment(api.getRejectInternalComment());
            ws.setAcceptance(map(api.getAcceptance()));
            ws.setTravelInformation(map(api.getTravelInformation()));
            ws.setNominatedAt(map(api.getNominatedAt()));
            ws.getAttachments().addAll(mapAPIFileCollection(api.getAttachments()));
            ws.setModified(map(api.getModified()));
            ws.setCreated(map(api.getCreated()));
        }

        return ws;
    }

    private static StudentAcceptance map(final net.iaeste.iws.ws.StudentAcceptance ws) {
        StudentAcceptance api = null;

        if (ws != null) {
            api = new StudentAcceptance();

            api.setApplicationId(ws.getApplicationId());
            api.setFirstWorkingDay(map(ws.getFirstWorkingDay()));
            api.setWorkingPlace(ws.getWorkingPlace());
            api.setContactPerson(ws.getContactPerson());
            api.setContactPersonEmail(ws.getContactPersonEmail());
            api.setContactPersonPhone(ws.getContactPersonPhone());
            api.setConfirmedPeriod(map(ws.getConfirmedPeriod()));
            api.setAdditionalInformation(ws.getAdditionalInformation());
        }

        return api;
    }

    private static net.iaeste.iws.ws.StudentAcceptance map(final StudentAcceptance api) {
        net.iaeste.iws.ws.StudentAcceptance ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.StudentAcceptance();

            ws.setApplicationId(api.getApplicationId());
            ws.setFirstWorkingDay(map(api.getFirstWorkingDay()));
            ws.setWorkingPlace(api.getWorkingPlace());
            ws.setContactPerson(api.getContactPerson());
            ws.setContactPersonEmail(api.getContactPersonEmail());
            ws.setContactPersonPhone(api.getContactPersonPhone());
            ws.setConfirmedPeriod(map(api.getConfirmedPeriod()));
            ws.setAdditionalInformation(api.getAdditionalInformation());
        }

        return ws;
    }

    private static StudentAcceptanceConfirmation map(final net.iaeste.iws.ws.StudentAcceptanceConfirmation ws) {
        StudentAcceptanceConfirmation api = null;

        if (ws != null) {
            api = new StudentAcceptanceConfirmation();

            api.setApplicationId(ws.getApplicationId());
            api.setDeparture(map(ws.getDeparture()));
            api.setTransportationType(map(ws.getTransportationType()));
            api.setDepartureFrom(ws.getDepartureFrom());
            api.setTransportNumber(ws.getTransportNumber());
            api.setArrivalDateTime(map(ws.getArrivalDateTime()));
            api.setPhoneNumberDuringTravel(ws.getPhoneNumberDuringTravel());
            api.setLodgingRequiredFrom(map(ws.getLodgingRequiredFrom()));
            api.setLodgingRequiredTo(map(ws.getLodgingRequiredTo()));
            api.setOtherInformation(ws.getOtherInformation());
            api.setInsuranceCompany(ws.getInsuranceCompany());
            api.setInsuranceReceiptNumber(ws.getInsuranceReceiptNumber());
        }

        return api;
    }

    private static net.iaeste.iws.ws.StudentAcceptanceConfirmation map(final StudentAcceptanceConfirmation api) {
        net.iaeste.iws.ws.StudentAcceptanceConfirmation ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.StudentAcceptanceConfirmation();

            ws.setApplicationId(api.getApplicationId());
            ws.setDeparture(map(api.getDeparture()));
            ws.setTransportationType(map(api.getTransportationType()));
            ws.setDepartureFrom(api.getDepartureFrom());
            ws.setTransportNumber(api.getTransportNumber());
            ws.setArrivalDateTime(map(api.getArrivalDateTime()));
            ws.setPhoneNumberDuringTravel(api.getPhoneNumberDuringTravel());
            ws.setLodgingRequiredFrom(map(api.getLodgingRequiredFrom()));
            ws.setLodgingRequiredTo(map(api.getLodgingRequiredTo()));
            ws.setOtherInformation(api.getOtherInformation());
            ws.setInsuranceCompany(api.getInsuranceCompany());
            ws.setInsuranceReceiptNumber(api.getInsuranceReceiptNumber());
        }

        return ws;
    }

    private static ApplicationStatus map(final net.iaeste.iws.ws.ApplicationStatus ws) {
        return ws != null ? ApplicationStatus.valueOf(ws.value()) : null;
    }

    private static net.iaeste.iws.ws.ApplicationStatus map(final ApplicationStatus api) {
        return api != null ? net.iaeste.iws.ws.ApplicationStatus.valueOf(api.name()) : null;
    }

    private static TransportationType map(final net.iaeste.iws.ws.TransportationType ws) {
        return ws != null ? TransportationType.valueOf(ws.value()) : null;
    }

    private static net.iaeste.iws.ws.TransportationType map(final TransportationType api) {
        return api != null ? net.iaeste.iws.ws.TransportationType.valueOf(api.name()) : null;
    }

    private static Specialization map(final net.iaeste.iws.ws.Specialization ws) {
        return ws != null ? Specialization.valueOf(ws.value()) : null;
    }

    private static net.iaeste.iws.ws.Specialization map(final Specialization api) {
        return api != null ? net.iaeste.iws.ws.Specialization.valueOf(api.name()) : null;
    }
}
