/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.exchange.PublishOfferRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Date;

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
public final class PublishOfferRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The Offer Object to published. */
    private Set<String> offerIds = null;

    /** The group to which the offer will be published. */
    private List<String> groupIds;

    /**
     * New nomination deadline for submitted offers.
     * If NULL is passed, the field is not updated.
     * */
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
        this.offerIds = offerIds;
        this.groupIds = groupIds;
        this.nominationDeadline = nominationDeadline;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setOfferIds(final Set<String> offerIds) {
        this.offerIds = offerIds;
    }

    public Set<String> getOfferIds() {
        return offerIds;
    }

    public void setGroupIds(final List<String> groupIds) {
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
        return validation;
    }
}
