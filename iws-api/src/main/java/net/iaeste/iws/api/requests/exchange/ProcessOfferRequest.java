/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.exchange.ProcessOfferRequest
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
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.util.AbstractVerification;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessOfferRequest", propOrder = { "offer" })
public final class ProcessOfferRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The Offer Object to process. */
    @XmlElement(required = true, nillable = false)
    private Offer offer;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public ProcessOfferRequest() {
        offer = null;
    }

    /**
     * Default Constructor, sets the Offer to be processed. If the Offer exists,
     * it will be updated otherwise a new Offer will be created.
     *
     * @param offer object to create or update
     */
    public ProcessOfferRequest(final Offer offer) {
        this.offer = new Offer(offer);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setOffer(final Offer offer) {
        ensureNotNullAndVerifiable("offer", offer);
        this.offer = new Offer(offer);
    }

    public Offer getOffer() {
        return new Offer(offer);
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

        isNotNull(validation, "offer", offer);

        return validation;
    }
}
