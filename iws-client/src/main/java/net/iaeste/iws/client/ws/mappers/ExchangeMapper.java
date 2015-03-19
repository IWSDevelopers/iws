/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.ws.mappers.ExchangeMapper
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.ws.mappers;

import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.ProcessEmployerRequest;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class ExchangeMapper extends CommonMapper {

    public static net.iaeste.iws.ws.ProcessEmployerRequest map(final ProcessEmployerRequest api) {
        net.iaeste.iws.ws.ProcessEmployerRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.ProcessEmployerRequest();
            ws.setEmployer(map(api.getEmployer()));
        }

        return ws;
    }

    public static EmployerResponse map(final net.iaeste.iws.ws.EmployerResponse ws) {
        EmployerResponse api = null;

        if (ws != null) {
            api = new EmployerResponse(map(ws.getError()), ws.getMessage());

            api.setEmployer(map(ws.getEmployer()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.FetchEmployerRequest map(final FetchEmployerRequest api) {
        net.iaeste.iws.ws.FetchEmployerRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchEmployerRequest();

            //ws.setEmployerFetchType(map(api.getFetchType()));
            //ws.setField(field);
        }

        return ws;
    }

    public static FetchEmployerResponse map(final net.iaeste.iws.ws.FetchEmployerResponse ws) {
        FetchEmployerResponse api = null;

        if (ws != null) {
            api = new FetchEmployerResponse(map(ws.getError()), ws.getMessage());
        }

        return api;
    }

    private static Employer map(final net.iaeste.iws.ws.Employer ws) {
        Employer api = null;

        if (ws != null) {
            api = new Employer();

            api.setEmployerId(ws.getEmployerId());
            api.setGroup(map(ws.getGroup()));
            api.setName(ws.getName());
            api.setDepartment(ws.getDepartment());
            api.setBusiness(ws.getBusiness());
            api.setAddress(map(ws.getAddress()));
            api.setEmployeesCount(ws.getEmployeesCount());
            api.setWebsite(ws.getWebsite());
            api.setWorkingPlace(ws.getWorkingPlace());
            api.setCanteen(ws.isCanteen());
            api.setNearestAirport(ws.getNearestAirport());
            api.setNearestPublicTransport(ws.getNearestPublicTransport());
        }

        return api;
    }

    private static net.iaeste.iws.ws.Employer map(final Employer api) {
        net.iaeste.iws.ws.Employer ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Employer();

            ws.setEmployerId(api.getEmployerId());
            ws.setGroup(map(api.getGroup()));
            ws.setName(api.getName());
            ws.setDepartment(api.getDepartment());
            ws.setBusiness(api.getBusiness());
            ws.setAddress(map(api.getAddress()));
            ws.setEmployeesCount(api.getEmployeesCount());
            ws.setWebsite(api.getWebsite());
            ws.setWorkingPlace(api.getWorkingPlace());
            ws.setCanteen(api.getCanteen());
            ws.setNearestAirport(api.getNearestAirport());
            ws.setNearestPublicTransport(api.getNearestPublicTransport());
        }

        return ws;
    }

    public static net.iaeste.iws.ws.FetchOffersRequest map(final FetchOffersRequest api) {
        net.iaeste.iws.ws.FetchOffersRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchOffersRequest();

            ws.setFetchType(map(api.getFetchType()));
            ws.setExchangeYear(api.getExchangeYear());
        }

        return ws;
    }

    public static FetchOffersResponse map(final net.iaeste.iws.ws.FetchOffersResponse ws) {
        FetchOffersResponse api = null;

        if (ws != null) {
            api = new FetchOffersResponse(map(ws.getError()), ws.getMessage());
        }

        return api;
    }

    private static net.iaeste.iws.ws.FetchType map(final FetchType api) {
        return api != null ? net.iaeste.iws.ws.FetchType.valueOf(api.name()) : null;
    }
}
