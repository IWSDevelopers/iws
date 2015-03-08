/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.exchange.OfferCSVUploadResponse
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.responses.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.responses.FallibleResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public class OfferCSVUploadResponse extends FallibleResponse {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Each CSV record to be updated will have a tri-state. Either it was added,
     * updated or there were processing errors.
     */
    public enum ProcessingResult { Added, Updated, Error }

    /**
     * Map with the result from each record from the CSV file that which was
     * processed. The map contain the Offer Reference Number as key, and the
     * result of the processing as the value.
     */
    private HashMap<String, ProcessingResult> processingResult;

    /**
     * Map with the error information related to the processing. The map contain
     * the Offer Reference Number as key, and the validation result as the
     * value.
     */
    private HashMap<String, Map<String, String>> errors;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     * Constructor is used in {@code OfferResponse} when deleteing an offer.
     */
    public OfferCSVUploadResponse() {
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public OfferCSVUploadResponse(final IWSError error, final String message) {
        super(error, message);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setProcessingResult(final HashMap<String, ProcessingResult> processingResult) {
        this.processingResult = processingResult;
    }

    public Map<String, ProcessingResult> getProcessingResult() {
        return processingResult;
    }

    public void setErrors(final HashMap<String, Map<String, String>> errors) {
        this.errors = errors;
    }

    public HashMap<String, Map<String, String>> getErrors() {
        return errors;
    }

    // =========================================================================
    // Standard Response Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        // Skipping the Error map here
        return "OfferCSVUploadResponse{" +
                "processingResult=" + processingResult +
                '}';
    }
}
