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

import net.iaeste.iws.api.dtos.SurveyOfCountry;
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.requests.FetchInternationalGroupRequest;
import net.iaeste.iws.api.requests.FetchSurveyOfCountryRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.SurveyOfCountryRequest;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.api.responses.FetchInternationalGroupResponse;
import net.iaeste.iws.api.responses.FetchSurveyOfCountryRespose;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

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

            ws.getCountryIds().addAll(mapStringCollection(api.getCountryIds()));
            ws.setMembership(map(api.getMembership()));
            ws.getStatuses().addAll(mapAPIGroupStatusCollection(api.getStatuses()));
        }

        return ws;
    }

    public static FetchCommitteeResponse map(final net.iaeste.iws.ws.FetchCommitteeResponse ws) {
        FetchCommitteeResponse api = null;

        if (ws != null) {
            api = new FetchCommitteeResponse(map(ws.getError()), ws.getMessage());

            api.setCommittees(mapWSUserGroupCollection(ws.getCommittees()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.CommitteeRequest map(final CommitteeRequest api) {
        net.iaeste.iws.ws.CommitteeRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.CommitteeRequest();

            ws.setCountryId(api.getCountryId());
            ws.setInstitutionName(api.getInstitutionName());
            ws.setInstitutionAbbreviation(api.getInstitutionAbbreviation());
            ws.setFirstname(api.getFirstname());
            ws.setLastname(api.getLastname());
            ws.setUsername(api.getUsername());
            ws.setNationalCommittee(map(api.getNationalCommittee()));
            ws.setNationalSecretary(map(api.getNationalSecretary()));
            // TODO Move Actions from Request to the general Action Enum
            //ws.setAction(map(api.getAction()));
        }

        return ws;
    }

    public static net.iaeste.iws.ws.FetchInternationalGroupRequest map(final FetchInternationalGroupRequest api) {
        net.iaeste.iws.ws.FetchInternationalGroupRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchInternationalGroupRequest();

            ws.getStatuses().addAll(mapAPIGroupStatusCollection(api.getStatuses()));
        }

        return ws;
    }

    public static FetchInternationalGroupResponse map(final net.iaeste.iws.ws.FetchInternationalGroupResponse ws) {
        FetchInternationalGroupResponse api = null;

        if (ws != null) {
            api = new FetchInternationalGroupResponse(map(ws.getError()), ws.getMessage());

            api.setGroups(mapWSUserGroupCollection(ws.getGroups()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.InternationalGroupRequest map(final InternationalGroupRequest api) {
        net.iaeste.iws.ws.InternationalGroupRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.InternationalGroupRequest();

            ws.setGroup(map(api.getGroup()));
            ws.setUser(map(api.getUser()));
            ws.setStatus(map(api.getStatus()));
        }

        return ws;
    }

    public static net.iaeste.iws.ws.FetchSurveyOfCountryRequest map(final FetchSurveyOfCountryRequest api) {
        net.iaeste.iws.ws.FetchSurveyOfCountryRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchSurveyOfCountryRequest();

            ws.setGroupId(api.getGroupId());
        }

        return ws;
    }

    public static FetchSurveyOfCountryRespose map(final net.iaeste.iws.ws.FetchSurveyOfCountryRespose ws) {
        FetchSurveyOfCountryRespose api = null;

        if (ws != null) {
            api = new FetchSurveyOfCountryRespose(map(ws.getError()), ws.getMessage());

            api.setSurvey(map(ws.getSurvey()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.SurveyOfCountryRequest map(final SurveyOfCountryRequest api) {
        net.iaeste.iws.ws.SurveyOfCountryRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.SurveyOfCountryRequest();

            ws.setSurvey(map(api.getSurvey()));
        }

        return ws;
    }

    // =========================================================================
    // Internal mapping of required Collections, DTO's & Enums
    // =========================================================================

    private static Set<net.iaeste.iws.ws.GroupStatus> mapAPIGroupStatusCollection(final Collection<GroupStatus> api) {
        final Set<net.iaeste.iws.ws.GroupStatus> ws = EnumSet.noneOf(net.iaeste.iws.ws.GroupStatus.class);

        if (api != null) {
            for (final GroupStatus groupStatus : api) {
                ws.add(map(groupStatus));
            }
        }

        return ws;
    }

    private static SurveyOfCountry map(final net.iaeste.iws.ws.SurveyOfCountry ws) {
        SurveyOfCountry api = null;

        if (ws != null) {
            api = new SurveyOfCountry();
        }

        return api;
    }

    private static net.iaeste.iws.ws.SurveyOfCountry map(final SurveyOfCountry api) {
        net.iaeste.iws.ws.SurveyOfCountry ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.SurveyOfCountry();
        }

        return ws;
    }

    private static net.iaeste.iws.ws.GroupStatus map(final GroupStatus api) {
        return api != null ? net.iaeste.iws.ws.GroupStatus.valueOf(api.name()) : null;
    }
}
