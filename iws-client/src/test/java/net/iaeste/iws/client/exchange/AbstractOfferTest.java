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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.client.AbstractTest;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public abstract class AbstractOfferTest extends AbstractTest {

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

    protected static Group findGroupFromResponse(final String offerId, final String groupId, final FetchPublishedGroupsResponse response) {
        Group group = null;

        for (final Group found : response.getOffersGroups().get(offerId).getGroups()) {
            if (found.getGroupId().equals(groupId)) {
                group = found;
            }
        }

        return group;
    }
}
