/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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
import net.iaeste.iws.api.util.Copier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class PublishOfferRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The Offer Object to published. */
    private Set<String> offerIds = null;

    /** The group to which the user will be added.**/
    private List<String> groupIds;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public PublishOfferRequest() {
        groupIds = new ArrayList<>(10);
    }

    public PublishOfferRequest(final Set<String> offerIds, final List<String> groupIds) {
        this.offerIds = Copier.copy(offerIds);
        this.groupIds = Copier.copy(groupIds);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setOfferIds(final Set<String> offerIds) {
        this.offerIds = Copier.copy(offerIds);
    }

    public Set<String> getOfferIds() {
        return Copier.copy(offerIds);
    }

    public void setGroupIds(final List<String> groupIds) {
        this.groupIds = Copier.copy(groupIds);
    }

    public List<String> getGroupIds() {
        return Copier.copy(groupIds);
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
