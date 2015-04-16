/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ws-client) - net.iaeste.iws.ws.client.mappers.CommitteeMapper
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

import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.requests.FetchInternationalGroupRequest;
import net.iaeste.iws.api.requests.FetchSurveyOfCountryRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.SurveyOfCountryRequest;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.api.responses.FetchInternationalGroupResponse;
import net.iaeste.iws.api.responses.FetchSurveyOfCountryRespose;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class CommitteeMapper extends CommonMapper {

    public static net.iaeste.iws.ws.FetchCommitteeRequest map(final FetchCommitteeRequest api) {
        net.iaeste.iws.ws.FetchCommitteeRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchCommitteeRequest();
            // TODO!!!
        }

        return ws;
    }

    public static FetchCommitteeResponse map(final net.iaeste.iws.ws.FetchCommitteeResponse ws) {
        FetchCommitteeResponse api = null;

        if (ws != null) {
            api = new FetchCommitteeResponse(map(ws.getError()), ws.getMessage());
            // TODO!!!
        }

        return api;
    }

    public static net.iaeste.iws.ws.CommitteeRequest map(final CommitteeRequest api) {
        net.iaeste.iws.ws.CommitteeRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.CommitteeRequest();
            // TODO!!!
        }

        return ws;
    }

    public static net.iaeste.iws.ws.FetchInternationalGroupRequest map(final FetchInternationalGroupRequest api) {
        net.iaeste.iws.ws.FetchInternationalGroupRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchInternationalGroupRequest();
            // TODO!!!
        }

        return ws;
    }

    public static FetchInternationalGroupResponse map(final net.iaeste.iws.ws.FetchInternationalGroupResponse ws) {
        FetchInternationalGroupResponse api = null;

        if (ws != null) {
            api = new FetchInternationalGroupResponse(map(ws.getError()), ws.getMessage());
            // TODO!!!
        }

        return api;
    }

    public static net.iaeste.iws.ws.InternationalGroupRequest map(final InternationalGroupRequest api) {
        net.iaeste.iws.ws.InternationalGroupRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.InternationalGroupRequest();
            // TODO!!!
        }

        return ws;
    }

    public static net.iaeste.iws.ws.FetchSurveyOfCountryRequest map(final FetchSurveyOfCountryRequest api) {
        net.iaeste.iws.ws.FetchSurveyOfCountryRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchSurveyOfCountryRequest();
            // TODO!!!
        }

        return ws;
    }

    public static FetchSurveyOfCountryRespose map(final net.iaeste.iws.ws.FetchSurveyOfCountryRespose ws) {
        FetchSurveyOfCountryRespose api = null;

        if (ws != null) {
            api = new FetchSurveyOfCountryRespose(map(ws.getError()), ws.getMessage());
            // TODO!!!
        }

        return api;
    }

    public static net.iaeste.iws.ws.SurveyOfCountryRequest map(final SurveyOfCountryRequest api) {
        net.iaeste.iws.ws.SurveyOfCountryRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.SurveyOfCountryRequest();
            // TODO!!!
        }

        return ws;
    }
}
