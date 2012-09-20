/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.requests.DeleteOfferRequest;
import net.iaeste.iws.api.requests.FacultyRequest;
import net.iaeste.iws.api.requests.FetchEmployerInformationRequest;
import net.iaeste.iws.api.requests.FetchFacultiesRequest;
import net.iaeste.iws.api.requests.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.FetchStudentsRequest;
import net.iaeste.iws.api.requests.OfferTemplateRequest;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.requests.PublishGroupRequest;
import net.iaeste.iws.api.requests.StudentRequest;
import net.iaeste.iws.api.responses.FacultyResponse;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.api.responses.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.api.responses.OfferTemplateResponse;
import net.iaeste.iws.api.responses.PublishGroupResponse;
import net.iaeste.iws.api.responses.StudentResponse;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public interface Exchange {

    FetchEmployerInformationResponse fetchEmployers(AuthenticationToken token, FetchEmployerInformationRequest request);

    Fallible processFaculties(AuthenticationToken token, FacultyRequest request);

    FacultyResponse fetchFaculties(AuthenticationToken token, FetchFacultiesRequest request);

    OfferResponse processOffer(AuthenticationToken token, ProcessOfferRequest request);

    Fallible deleteOffer(AuthenticationToken token, DeleteOfferRequest request);

    FetchOffersResponse fetchOffers(AuthenticationToken token, FetchOffersRequest request);

    Fallible processOfferTemplates(AuthenticationToken token, OfferTemplateRequest request);

    OfferTemplateResponse fetchOfferTemplates(AuthenticationToken token, FetchOfferTemplatesRequest request);

    Fallible processPublishGroups(AuthenticationToken token, PublishGroupRequest request);

    PublishGroupResponse fetchPublishGroups(AuthenticationToken token, FetchPublishGroupsRequest request);

    Fallible processStudents(AuthenticationToken token, StudentRequest request);

    StudentResponse fetchStudents(AuthenticationToken token, FetchStudentsRequest request);
}
