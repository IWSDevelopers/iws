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
package net.iaeste.iws.api.requests.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.Verifications;
import net.iaeste.iws.api.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "publishOfferRequest", propOrder = { "offerIds", "groupIds", "nominationDeadline" })
public final class PublishOfferRequest extends Verifications {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The Offer Object to published. */
    @XmlElement(required = true, nillable = true)
    private Set<String> offerIds = null;

    /** The group to which the offer will be published. */
    @XmlElement(required = true, nillable = true)
    private List<String> groupIds = null;

    /**
     * New nomination deadline for submitted offers.
     * If NULL is passed, the field is not updated.
     * */
    @XmlElement(required = true, nillable = true)
    private Date nominationDeadline = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public PublishOfferRequest() {
        groupIds = new ArrayList<>(10);
    }

    public PublishOfferRequest(final Set<String> offerIds, final List<String> groupIds, final Date nominationDeadline) {
        setOfferIds(offerIds);
        setGroupIds(groupIds);
        setNominationDeadline(nominationDeadline);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setOfferIds(final Set<String> offerIds) {
        ensureValidIdentifiers("offerIds", offerIds);
        this.offerIds = offerIds;
    }

    public Set<String> getOfferIds() {
        return offerIds;
    }

    public void setGroupIds(final List<String> groupIds) {
        ensureNotNullAndValidIdentifiers("groupIds", groupIds);
        this.groupIds = groupIds;
    }

    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setNominationDeadline(final Date nominationDeadline) {
        this.nominationDeadline = nominationDeadline;
    }

    public Date getNominationDeadline() {
        return nominationDeadline;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        if ((offerIds == null) && ((groupIds == null) || groupIds.isEmpty())) {
            validation.put("Ids", "OfferIds and groupIds are both missing");
        }

        return validation;
    }
}
