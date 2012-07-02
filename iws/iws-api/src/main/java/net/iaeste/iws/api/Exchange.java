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

import net.iaeste.iws.api.data.AuthenticationToken;
import net.iaeste.iws.api.requests.FacultyRequest;
import net.iaeste.iws.api.requests.OfferRequest;
import net.iaeste.iws.api.requests.OfferTemplateRequest;
import net.iaeste.iws.api.requests.PublishGroupRequest;
import net.iaeste.iws.api.requests.StudentRequest;
import net.iaeste.iws.api.responses.FacultyResponse;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.api.responses.OfferTemplateResponse;
import net.iaeste.iws.api.responses.PublishGroupResponse;
import net.iaeste.iws.api.responses.StudentResponse;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface Exchange {

    Fallible processFaculties(AuthenticationToken token, FacultyRequest request);
    FacultyResponse findFaculties(AuthenticationToken token, FacultyRequest request);

    Fallible processOffers(AuthenticationToken token, OfferRequest request);
    OfferResponse findOffers(AuthenticationToken token, OfferRequest request);

    Fallible processOfferTemplates(AuthenticationToken token, OfferTemplateRequest request);
    OfferTemplateResponse findOfferTemplates(AuthenticationToken token, OfferTemplateRequest request);

    Fallible processPublishGroups(AuthenticationToken token, PublishGroupRequest request);
    PublishGroupResponse findPublishGroups(AuthenticationToken token, PublishGroupRequest request);

    Fallible processStudents(AuthenticationToken token, StudentRequest request);
    StudentResponse findStudents(AuthenticationToken token, StudentRequest request);
}
