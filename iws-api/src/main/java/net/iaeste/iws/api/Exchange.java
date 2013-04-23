/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.Exchange
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import net.iaeste.iws.api.requests.exchange.FacultyRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerInformationRequest;
import net.iaeste.iws.api.requests.exchange.FetchFacultiesRequest;
import net.iaeste.iws.api.requests.exchange.FetchGroupsForSharingRequest;
import net.iaeste.iws.api.requests.exchange.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.FetchStudentsRequest;
import net.iaeste.iws.api.requests.exchange.OfferTemplateRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.PublishGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.StudentRequest;
import net.iaeste.iws.api.responses.exchange.FetchFacultyResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOfferTemplateResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishGroupResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishOfferResponse;
import net.iaeste.iws.api.responses.exchange.FetchStudentResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.util.Fallible;

import javax.ejb.Remote;

/**
 * ToDo Kim; Add JavaDoc for all requests.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Remote
public interface Exchange {

    FetchEmployerInformationResponse fetchEmployers(AuthenticationToken token, FetchEmployerInformationRequest request);

    Fallible manageFaculties(AuthenticationToken token, FacultyRequest request);

    FetchFacultyResponse fetchFaculties(AuthenticationToken token, FetchFacultiesRequest request);

    OfferResponse processOffer(AuthenticationToken token, ProcessOfferRequest request);

    OfferResponse deleteOffer(AuthenticationToken token, DeleteOfferRequest request);

    FetchOffersResponse fetchOffers(AuthenticationToken token, FetchOffersRequest request);

    Fallible manageOfferTemplate(AuthenticationToken token, OfferTemplateRequest request);

    FetchOfferTemplateResponse fetchOfferTemplates(AuthenticationToken token, FetchOfferTemplatesRequest request);

    Fallible managePublishGroup(AuthenticationToken token, PublishGroupRequest request);

    FetchPublishGroupResponse fetchPublishGroups(AuthenticationToken token, FetchPublishGroupsRequest request);

    Fallible manageStudent(AuthenticationToken token, StudentRequest request);

    FetchStudentResponse fetchStudents(AuthenticationToken token, FetchStudentsRequest request);

    /**
     * Retrieves a list of all national groups. This is mainly needed in the
     * front-end to display a list of groups (countries).
     *
     * @param token     Authentication information about the user invoking the request
     * @param request   Fetch National Groups Request Object
     * @return Response Object with the current national groups ordered by name
     *                  and error information
     */
    FetchGroupsForSharingResponse fetchGroupsForSharing(AuthenticationToken token, FetchGroupsForSharingRequest request);

    PublishOfferResponse processPublishOffer(AuthenticationToken token, PublishOfferRequest request);

    FetchPublishOfferResponse fetchPublishedOfferInfo(AuthenticationToken token, FetchPublishOfferRequest request);
}
