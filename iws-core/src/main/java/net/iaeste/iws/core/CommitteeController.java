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
package net.iaeste.iws.core;

import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.CountrySurveyRequest;
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.requests.FetchCountrySurveyRequest;
import net.iaeste.iws.api.requests.FetchInternationalGroupRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.responses.CommitteeResponse;
import net.iaeste.iws.api.responses.Response;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.api.responses.FetchCountrySurveyResponse;
import net.iaeste.iws.api.responses.FetchInternationalGroupResponse;
import net.iaeste.iws.core.services.CommitteeService;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.persistence.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class CommitteeController extends CommonController implements Committees {

    private static final Logger LOG = LoggerFactory.getLogger(CommitteeController.class);

    /**
     * Default Constructor, takes a ServiceFactory as input parameter, and uses
     * this in all the request methods, to get a new Service instance.
     *
     * @param factory  The ServiceFactory
     */
    public CommitteeController(final ServiceFactory factory) {
        super(factory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchCommitteeResponse fetchCommittees(final AuthenticationToken token, final FetchCommitteeRequest request) {
        FetchCommitteeResponse response;

        try {
            verify(request);
            verifyAccess(token, Permission.FETCH_COMMITTEES);

            final CommitteeService service = factory.prepareCommitteeService();
            response = service.fetchCommittees(request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FetchCommitteeResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitteeResponse processCommittee(final AuthenticationToken token, final CommitteeRequest request) {
        CommitteeResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_COMMITTEE);

            final CommitteeService service = factory.prepareCommitteeService();
            response = service.processCommittee(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new CommitteeResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchInternationalGroupResponse fetchInternationalGroups(final AuthenticationToken token, final FetchInternationalGroupRequest request) {
        FetchInternationalGroupResponse response;

        try {
            verify(request);
            verifyAccess(token, Permission.FETCH_INTERNATIONAL_GROUPS);

            final CommitteeService service = factory.prepareCommitteeService();
            response = service.fetchInternationalGroups(request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FetchInternationalGroupResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response processInternationalGroup(final AuthenticationToken token, final InternationalGroupRequest request) {
        Response response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_INTERNATIONAL_GROUP);

            final CommitteeService service = factory.prepareCommitteeService();
            service.processInternationalGroup(authentication, request);
            response = new Response();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new Response(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchCountrySurveyResponse fetchCountrySurvey(final AuthenticationToken token, final FetchCountrySurveyRequest request) {
        FetchCountrySurveyResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.FETCH_SURVEY_OF_COUNTRIES);

            final CommitteeService service = factory.prepareCommitteeService();
            response = service.fetchCountrySurvey(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FetchCountrySurveyResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response processCountrySurvey(final AuthenticationToken token, final CountrySurveyRequest request) {
        Response response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_SURVEY_OF_COUNTRIES);

            final CommitteeService service = factory.prepareCommitteeService();
            service.processCountrySurvey(authentication, request);
            response = new Response();
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also LOG.ed
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new Response(e.getError(), e.getMessage());
        }

        return response;
    }
}
