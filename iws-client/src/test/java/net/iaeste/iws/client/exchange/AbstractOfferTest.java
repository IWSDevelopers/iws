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
package net.iaeste.iws.client.exchange;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.TestData;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.dtos.exchange.StudentApplication;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.responses.student.FetchStudentApplicationsResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.client.AbstractTest;
import net.iaeste.iws.client.ExchangeClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public abstract class AbstractOfferTest extends AbstractTest {

    protected Exchange exchange = new ExchangeClient();

    // =========================================================================
    // Simple Static Methods to create Requests or read Responses
    // =========================================================================

    protected static OfferRequest prepareRequest(final Offer offer) {
        final OfferRequest request = new OfferRequest();
        request.setOffer(offer);

        return request;
    }

    protected static StudentApplication findApplicationFromResponse(final String applicationId, final FetchStudentApplicationsResponse response) {
        StudentApplication application = null;

        for (final StudentApplication found : response.getStudentApplications()) {
            if (found.getApplicationId().equals(applicationId)) {
                application = found;
                break;
            }
        }

        return application;
    }

    protected static Group findGroupFromResponse(final String offerId, final String groupId, final FetchPublishedGroupsResponse response) {
        Group group = null;

        for (final Group found : response.getOffersGroups().get(offerId).getGroups()) {
            if (found.getGroupId().equals(groupId)) {
                group = found;
            }
        }

        return group;
    }

    protected static Offer findOfferFromResponse(final String refno, final FetchOffersResponse response) {
        // As the IWS is replacing the new Reference Number with the correct
        // year, the only valid information to go on is the running number.
        // Hence, we're skipping everything before that
        final String refNoLowerCase = refno.toLowerCase(IWSConstants.DEFAULT_LOCALE).substring(8);
        Offer offer = null;

        for (final Offer found : response.getOffers()) {
            final String foundRefNo = found.getRefNo().toLowerCase(IWSConstants.DEFAULT_LOCALE);
            if (foundRefNo.contains(refNoLowerCase)) {
                offer = found;
            }
        }

        return offer;
    }

    // =========================================================================
    // Invoking IWS to perform various operations and checks response
    // =========================================================================

    /**
     * <p>Creates a new Offer by invoking the Exchange#processOffer() method.
     * The method takes two parameters, a unique Reference Number and the name
     * of the Employer.</p>
     *
     * <p>The Method will return the newly created Offer, after ensuring that
     * the Offer was correctly created.</p>
     *
     * @param refno     Offer Reference Number
     * @param employer  Offer Employer name
     * @return Newly created Offer
     */
    protected Offer createOffer(final AuthenticationToken authentication, final String refno, final String employer) {
        // First, create a new Offer, which we can use
        final Offer offer = TestData.prepareFullOffer(refno, employer);

        // Prepare the Offer Request, to create an Offer.
        final OfferRequest request = new OfferRequest();
        request.setOffer(offer);

        // Invoke IWS, and ensure that the request was successful
        final OfferResponse response = exchange.processOffer(authentication, request);
        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));

        return response.getOffer();
    }

    /**
     * <p>Publishes an Offer with the given Deadline to the given Groups, via
     * Authentication Tokens - which requires that they are logged in.</p>
     *
     * <p>The method will return the {@link PublishOfferResponse} Object, after
     * it has been verified that the Publish Offer Request was successful. If no
     * NC's AuthenticationToken Objects is given, then an empty list is sent to
     * the IWS, meaning that all shares for this Offer will be removed.</p>
     *
     * @param authentication Authentication Token
     * @param offer          The Offer to publish
     * @param deadline       The nomination Deadline
     * @param tokens         The Tokens for the NC's to be published to
     * @return The Publish Offer Response Object
     */
    protected PublishOfferResponse publishOffer(final AuthenticationToken authentication, final Offer offer, final Date deadline, final AuthenticationToken... tokens) {
        final PublishOfferRequest request = new PublishOfferRequest();
        request.setOfferId(offer.getOfferId());
        request.setNominationDeadline(deadline);
        final List<String> groupIds;

        if ((tokens != null) && (tokens.length > 0)) {
            groupIds = new ArrayList<>(tokens.length);
            for (final AuthenticationToken groupToken : tokens) {
                groupIds.add(findNationalGroup(groupToken).getGroupId());
            }
            request.setGroupIds(groupIds);
        } else {
            groupIds = new ArrayList<>(0);
        }

        final PublishOfferResponse response = exchange.processPublishOffer(authentication, request);
        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(response.getOfferId(), is(offer.getOfferId()));
        assertThat(response.getGroupIds().size(), is(groupIds.size()));

        return response;
    }

    protected Offer fetchOffer(final AuthenticationToken authentication, final FetchType type, final String refno) {
        final FetchOffersRequest request = new FetchOffersRequest(type);
        final FetchOffersResponse response = exchange.fetchOffers(authentication, request);
        Offer offer = null;

        if (!response.getOffers().isEmpty()) {
            offer = findOfferFromResponse(refno, response);
            assertThat(offer, is(not(nullValue())));
            assertThat(offer.getRefNo(), is(refno));
        }

        return offer;
    }
}
