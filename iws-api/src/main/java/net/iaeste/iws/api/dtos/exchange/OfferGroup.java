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
package net.iaeste.iws.api.dtos.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Sharing info for the offer
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class OfferGroup extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String offerRefNo = null;
    private String groupId = null;
    private OfferState status = OfferState.SHARED;
    private String comment = null;
    private DateTime modified = null;
    private DateTime created = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * <p>Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.</p>
     */
    public OfferGroup() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * <p>Copy constructor.</p>
     *
     * @param offerGroup OfferGroup to copy
     */
    public OfferGroup(final OfferGroup offerGroup) {
        if (offerGroup != null) {
            offerRefNo = offerGroup.offerRefNo;
            groupId = offerGroup.groupId;
            modified = offerGroup.modified;
            created = offerGroup.created;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the Offer Reference Number, for the Offer which the Group is
     * granted access to.</p>
     *
     * <p>If the value is illegal, i.e. null or not a valid Reference Number,
     * then the method will thrown an {@code IllegalArgumentException}.</p>
     *
     * @param offerRefNo Offer Reference Number.
     * @throws IllegalArgumentException if value is either null or invalid
     */
    public void setOfferRefNo(final String offerRefNo) throws IllegalArgumentException {
        ensureNotNullAndValidRefno("offerRefNo", offerRefNo);
        this.offerRefNo = offerRefNo;
    }

    public String getOfferRefNo() {
        return offerRefNo;
    }

    /**
     * Sets the Id of the Group that is granted access to an Offer. The GroupId
     * must be valid, otherwise the method will thrown an
     * {@code IllegalArgumentException}.
     *
     * @param groupId Group Id
     * @throws IllegalArgumentException if value is null or invalid
     */
    public void setGroupId(final String groupId) throws IllegalArgumentException {
        ensureNotNullAndValidId("groupId", groupId);
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    /**
     * <p>Sets the Status for this Offer Group relation. The status must be set,
     * and will by default be set to New.</p>
     *
     * <p>The method will thrown an {@code IllegalArgumentException} if the
     * Offer State is set to null.</p>
     *
     * @param status Offer Status
     * @throws IllegalArgumentException if the value is null
     */
    public void setStatus(final OfferState status) throws IllegalArgumentException {
        ensureNotNull("status", status);
        this.status = status;
    }

    public OfferState getStatus() {
        return status;
    }

    public void setComment(final String comment) {
        ensureNotTooLong("comment", comment, 500);
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    /**
     * For internal use only.
     *
     * @param modified DateTime of last modification of the OfferGroup
     */
    public void setModified(final DateTime modified) {
        this.modified = modified;
    }

    public DateTime getModified() {
        return modified;
    }

    /**
     * For internal use only.
     *
     * @param created DateTime of the creation of the Offer
     */
    public void setCreated(final DateTime created) {
        this.created = created;
    }

    public DateTime getCreated() {
        return created;
    }

    // =========================================================================
    // Standard DTO Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "offerRefNo", offerRefNo);
        isNotNull(validation, "groupId", groupId);

        return validation;
    }
}
