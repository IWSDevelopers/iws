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
import net.iaeste.iws.api.requests.*;
import net.iaeste.iws.api.responses.*;
import net.iaeste.iws.api.util.Fallible;

/**
 * ToDo Kim; Add JavaDoc for all requests.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface Exchange {

    FetchEmployerInformationResponse fetchEmployers(AuthenticationToken token, FetchEmployerInformationRequest request);

    Fallible manageFaculties(AuthenticationToken token, FacultyRequest request);

    FacultyResponse fetchFaculties(AuthenticationToken token, FetchFacultiesRequest request);

    OfferResponse processOffer(AuthenticationToken token, ProcessOfferRequest request);

    OfferResponse deleteOffer(AuthenticationToken token, DeleteOfferRequest request);

    FetchOffersResponse fetchOffers(AuthenticationToken token, FetchOffersRequest request);

    Fallible manageOfferTemplate(AuthenticationToken token, OfferTemplateRequest request);

    OfferTemplateResponse fetchOfferTemplates(AuthenticationToken token, FetchOfferTemplatesRequest request);

    Fallible managePublishGroup(AuthenticationToken token, PublishGroupRequest request);

    PublishGroupResponse fetchPublishGroups(AuthenticationToken token, FetchPublishGroupsRequest request);

    Fallible manageStudent(AuthenticationToken token, StudentRequest request);

    StudentResponse fetchStudents(AuthenticationToken token, FetchStudentsRequest request);

    PublishOfferResponse processPublishOffer(AuthenticationToken token, PublishOfferRequest request);

    FetchPublishOfferResponse fetchPublishedOfferInfo(AuthenticationToken token, FetchPublishOfferRequest request);
}
