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
package net.iaeste.iws.client;

import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.requests.FetchInternationalGroupRequest;
import net.iaeste.iws.api.requests.FetchCountrySurveyRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.CountrySurveyRequest;
import net.iaeste.iws.api.responses.CommitteeResponse;
import net.iaeste.iws.api.responses.Response;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.api.responses.FetchInternationalGroupResponse;
import net.iaeste.iws.api.responses.FetchCountrySurveyResponse;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class CommitteeClient implements Committees {

    private final Committees client;

    /**
     * Default Constructor.
     */
    public CommitteeClient() {
        client = ClientFactory.getInstance().getCommitteeImplementation();
    }

    // =========================================================================
    // Implementation of methods from Committees in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchCommitteeResponse fetchCommittees(final AuthenticationToken token, final FetchCommitteeRequest request) {
        return client.fetchCommittees(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitteeResponse processCommittee(final AuthenticationToken token, final CommitteeRequest request) {
        return client.processCommittee(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchInternationalGroupResponse fetchInternationalGroups(final AuthenticationToken token, final FetchInternationalGroupRequest request) {
        return client.fetchInternationalGroups(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response processInternationalGroup(final AuthenticationToken token, final InternationalGroupRequest request) {
        return client.processInternationalGroup(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchCountrySurveyResponse fetchCountrySurvey(final AuthenticationToken token, final FetchCountrySurveyRequest request) {
        return client.fetchCountrySurvey(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response processCountrySurvey(final AuthenticationToken token, final CountrySurveyRequest request) {
        return client.processCountrySurvey(token, request);
    }
}
