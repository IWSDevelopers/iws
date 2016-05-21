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
package net.iaeste.iws.api;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.exchange.EmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.requests.exchange.HideForeignOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVUploadRequest;
import net.iaeste.iws.api.requests.exchange.OfferRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.requests.exchange.ProcessPublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.RejectOfferRequest;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishingGroupResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVDownloadResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVUploadResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;

/**
 * <p>Exchange related functionality is covered with this interface. Only
 * exception here, the handling of students is done vai the {@link Students}
 * interface.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Exchange {

    /**
     * <p>Retrieves the Offer Statistics information.</p>
     *
     * @param token   User Authentication Token
     * @param request Offer Statistics Request Object
     * @return Response Object with Error and Statistics information
     */
    OfferStatisticsResponse fetchOfferStatistics(AuthenticationToken token, OfferStatisticsRequest request);

    /**
     * <p>Processes a given Employer, meaning that if it exists, then it us
     * updated, otherwise it is being created.</p>
     *
     * @param token   User Authentication Token
     * @param request Request Object, with the Employer
     * @return Persisted Employer Object
     */
    EmployerResponse processEmployer(AuthenticationToken token, EmployerRequest request);

    /**
     * <p>Fetches a list of Employers, belonging to the requesting User, i.e.
     * which is associated with the Users National Group. The request can be
     * made either for a single Object (by providing the Id), for a partial
     * list (by providing a partial name) or for all Employers.</p>
     *
     * @param token   User Authentication Token
     * @param request Employer Request Object
     * @return List of requested Employers
     */
    FetchEmployerResponse fetchEmployers(AuthenticationToken token, FetchEmployerRequest request);

    /**
     * <p>Creates or updates an Offer, dependent on the {@code id}. If the id
     * is set, an update is assumed, otherwise a create will be performed.</p>
     *
     * <p>On error the {@link OfferResponse} object contains only error
     * information. No information about the Offer is returned.</p>
     *
     * @param token The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request contains a {@link net.iaeste.iws.api.dtos.exchange.Offer}
     * @return the persisted {@link net.iaeste.iws.api.dtos.exchange.Offer} including the generated Id
     */
    OfferResponse processOffer(AuthenticationToken token, OfferRequest request);

    /**
     * <p>IW3, IAESTE IntraWeb version 3, allowed users to upload their Offers
     * directly as a CSV file, to avoid typing it all again. Although IWS
     * provides better mechanisms in the form of a published API which will
     * allow a much greater control - it is still requested by Users, that they
     * can perform this action, as some countries do not have the capacity to
     * implement an IWS API Client otherwise.</p>
     *
     * @param token   The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request Offer CSV Upload Request
     * @return Response Object with error information from the uploading
     */
    OfferCSVUploadResponse uploadOffers(AuthenticationToken token, OfferCSVUploadRequest request);

    /**
     * <p>Retrieves a list of offers. This can be either the list of owned
     * offers or offers which are shared with your country.</p>
     *
g     * <dl>
     *   <dt>{@link net.iaeste.iws.api.enums.FetchType#DOMESTIC}</dt>
     *     <dd>Requests all own/domestic offers.</dd>
     *   <dt>{@link net.iaeste.iws.api.enums.FetchType#SHARED}</dt>
     *     <dd>Means that all shared offers are requested.</dd>
     * </dl>
     *
     * @param token   The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request contains a {@link net.iaeste.iws.api.enums.FetchType} which indicates which type of offers
     *                should be returned
     * @return contains a list of {@link net.iaeste.iws.api.dtos.exchange.Offer}
     */
    FetchOffersResponse fetchOffers(AuthenticationToken token, FetchOffersRequest request);

    /**
     * <p>The IAESTE IntraWeb version 3, IW3, provided a simple way whereby
     * Offers could be downloaded. The download was primarily of the countries
     * incoming or foreign Offers. This functionality mimics this service in
     * IWS, so the request, will fetch Offers, though with a few more options
     * as described in the Request Object.</p>
     *
     * @param token   The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request Offer CSV Download Request
     * @return Response Object with standard error information and CSV Data
     */
    OfferCSVDownloadResponse downloadOffers(AuthenticationToken token, FetchOffersRequest request);

    /**
     * <p>Retrieves a list of all groups to which an offer can be shared to.
     * The group types of the groups are:</p>
     * <ul>
     *     <li>{@link net.iaeste.iws.api.enums.GroupType#NATIONAL}</li>
     * </ul>
     *
     * <p>This is mainly needed in the front-end to display a list of groups
     * (members) to which a offer can be shared to.</p>
     *
     * @param token The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @return Response Object with the current national groups ordered by name
     *                  and error information
     */
    FetchGroupsForSharingResponse fetchGroupsForSharing(AuthenticationToken token);

    /**
     *
     * @param token   User Authentication Token
     * @param request contains name of the Publishing Group and list of Groups the Publishing Group consists of
     * @return Standard Error Object
     */
    FallibleResponse processPublishingGroup(AuthenticationToken token, ProcessPublishingGroupRequest request);

    /**
     * Retrieve tha Publishing Groups for user's national group
     *
     * @param token   User Authentication Token
     * @param request Request Object
     * @return Response Object with National group's Publishing Groups
     */
    FetchPublishingGroupResponse fetchPublishingGroups(AuthenticationToken token, FetchPublishGroupsRequest request);

    /**
     * <p>Retrieves the list of groups to which offers are shared to. A list of
     * offers is submitted and for each offer the groups are returned, to which
     * the offer is shared to.</p>
     *
     * @param token The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request contains a list of {@link net.iaeste.iws.api.dtos.exchange.Offer#offerId}s
     * @return contains a map for each requested offer and the list of {@link net.iaeste.iws.api.dtos.exchange.OfferGroup}
     *  to which the offer is shared to
     */
    FetchPublishedGroupsResponse fetchPublishedGroups(AuthenticationToken token, FetchPublishedGroupsRequest request);

    /**
     * <p>Shares a list of offers to a list of members and defines the
     * nomination deadline for the specified offers. From this very moment he
     * offers are visible to the list of members until the
     * {@code PublishOfferRequest#nominationDeadline} is reached. The
     * {@link net.iaeste.iws.api.dtos.exchange.Offer#nominationDeadline} of
     * each specified offer is updated to the specified
     * {@code PublishOfferRequest#nominationDeadline}.</p>
     *
     * <p>The list of offers is identified by the
     * {@link net.iaeste.iws.api.dtos.exchange.Offer#offerId}. The list of
     * members are identified by the
     * {@link net.iaeste.iws.api.dtos.Group#groupId}.</p>
     *
     * @param token The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request contains a list of offer, a list of members and a nomination deadline
     * @return contains no data
     */
    PublishOfferResponse processPublishOffer(AuthenticationToken token, PublishOfferRequest request);

    // Note, the following two should be merged into a single method called "processSharedOffer"
    /**
     * <p>Hides specified foreign offer for given group.</p>
     *
     * @param token   User Authentication Token
     * @param request contains a list of offer
     * @return Response Object
     */
    FallibleResponse processHideForeignOffers(AuthenticationToken token, HideForeignOffersRequest request);

    /**
     * <p>Performs a rejection of the shared offer.</p>
     *
     * @param token The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request contains a field with the offer id
     * @return Response Object
     */
    FallibleResponse rejectOffer(AuthenticationToken token, RejectOfferRequest request);
}
