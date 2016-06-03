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
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.requests.Actions;
import net.iaeste.iws.api.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "publishOfferRequest", propOrder = { "offerId", "groupIds", "nominationDeadline" })
public final class PublishOfferRequest extends Actions {

    private static final String FIELD_OFFER_ID = "offerId";

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true)                   private String offerId = null;
    @XmlElement(required = true)                   private final List<String> groupIds = new ArrayList<>(0);
    @XmlElement(required = true, nillable = true)  private Date nominationDeadline = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public PublishOfferRequest() {
        super(EnumSet.of(Action.PROCESS, Action.REMOVE), Action.PROCESS);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the Id of the Offer, which must be shared or have the existing
     * shares removed (Closed). The OfferId is mandatory, and the method will
     * throw an {code {@link IllegalArgumentException} if it is null or an
     * invalid Identifier.</p>
     *
     * @param offerId Offer Id or Reference Number
     * @throws IllegalArgumentException if null or invalid
     */
    public void setOfferId(final String offerId) {
        ensureNotNullAndValidId(FIELD_OFFER_ID, offerId);
        this.offerId = offerId;
    }

    public String getOfferId() {
        return offerId;
    }

    /**
     * <p>Sets the Id's of the Groups which the Offer should be shared with.
     * Please note that the Id's must be for Groups of type NATIONAL. Any other
     * Group Types will be ignored.</p>
     *
     * <p>The method will throw an {@code {@link IllegalArgumentException} if
     * the the argument is null, empty or contain illegal Identifiers.</p>
     *
     * @param groupIds List of National Group Id's to share the Offer with
     * @throws IllegalArgumentException if null or contain illegal Identifiers
     */
    public void setGroupIds(final List<String> groupIds) {
        ensureNotNullOrEmptyAndValidIdentifiers("groupIds", groupIds);
        this.groupIds.addAll(groupIds);
    }

    public List<String> getGroupIds() {
        return immutableList(groupIds);
    }

    /**
     * New nomination deadline for submitted offers. If NULL is passed, the
     * field is not updated.
     *
     * @param nominationDeadline Nomination Deadline
     */
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

        isNotNull(validation, "action", action);
        if (action != null) {
            switch (action) {
                case PROCESS:
                case REMOVE:
                    isNotNull(validation, FIELD_OFFER_ID, offerId);
                    break;
                default:
                    validation.put(FIELD_ACTION, "The Action '" + action + "' is not allowed");
            }
        }

        return validation;
    }
}
