/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.api.dtos.CountrySurvey;
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.CountrySurveyRequest;
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.requests.FetchCountrySurveyRequest;
import net.iaeste.iws.api.requests.FetchInternationalGroupRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.responses.CommitteeResponse;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.api.responses.FetchCountrySurveyResponse;
import net.iaeste.iws.api.responses.FetchInternationalGroupResponse;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class CommitteeMapper extends CommonMapper {

    /** Private Constructor, this is a Utility Class. */
    private CommitteeMapper() {
    }

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

            ws.setCountryCode(api.getCountryCode());
            ws.setInstitutionName(api.getInstitutionName());
            ws.setInstitutionAbbreviation(api.getInstitutionAbbreviation());
            ws.setFirstname(api.getFirstname());
            ws.setLastname(api.getLastname());
            ws.setUsername(api.getUsername());
            ws.setNationalCommittee(map(api.getNationalCommittee()));
            ws.setNationalSecretary(map(api.getNationalSecretary()));
            ws.setAction(map(api.getAction()));
        }

        return ws;
    }

    public static CommitteeResponse map(final net.iaeste.iws.ws.CommitteeResponse ws) {
        CommitteeResponse api = null;

        if (ws != null) {
            api = new CommitteeResponse(map(ws.getError()), ws.getMessage());

            api.setCommittee(map(ws.getCommittee()));
        }

        return api;
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

    public static net.iaeste.iws.ws.FetchCountrySurveyRequest map(final FetchCountrySurveyRequest api) {
        net.iaeste.iws.ws.FetchCountrySurveyRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchCountrySurveyRequest();

            ws.setGroupId(api.getGroupId());
        }

        return ws;
    }

    public static FetchCountrySurveyResponse map(final net.iaeste.iws.ws.FetchCountrySurveyRespose ws) {
        FetchCountrySurveyResponse api = null;

        if (ws != null) {
            api = new FetchCountrySurveyResponse(map(ws.getError()), ws.getMessage());

            api.setSurvey(map(ws.getSurvey()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.CountrySurveyRequest map(final CountrySurveyRequest api) {
        net.iaeste.iws.ws.CountrySurveyRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.CountrySurveyRequest();

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

    private static CountrySurvey map(final net.iaeste.iws.ws.CountrySurvey ws) {
        CountrySurvey api = null;

        if (ws != null) {
            api = new CountrySurvey();
        }

        return api;
    }

    private static net.iaeste.iws.ws.CountrySurvey map(final CountrySurvey api) {
        net.iaeste.iws.ws.CountrySurvey ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.CountrySurvey();
        }

        return ws;
    }

    private static net.iaeste.iws.ws.GroupStatus map(final GroupStatus api) {
        return api != null ? net.iaeste.iws.ws.GroupStatus.valueOf(api.name()) : null;
    }
}
