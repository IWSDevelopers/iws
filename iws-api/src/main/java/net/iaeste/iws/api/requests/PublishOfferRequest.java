/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.PublishOfferRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Copier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class PublishOfferRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The Offer Object to published. */
    private Offer offer = null;

    /** The group to which the user will be added.**/
    private List<Group> groups = new ArrayList<>();

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public PublishOfferRequest() {
    }

    public PublishOfferRequest(final Offer offer, final List<Group> groups) {
        this.offer = new Offer(offer);
        this.groups = Copier.copy(groups);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setOffer(final Offer offer) {
        this.offer = new Offer(offer);
    }

    public Offer getOffer() {
        return new Offer(offer);
    }

    public void setGroups(final List<Group> groups) {
        this.groups = Copier.copy(groups);
    }

    public List<Group> getGroups() {
        return Copier.copy(groups);
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
