/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.dtos.exchange.CSVProcessingErrors;
import net.iaeste.iws.api.responses.FallibleResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "offerCSVUploadResponse", propOrder = { "processingResult", "errors" })
public final class OfferCSVUploadResponse extends FallibleResponse {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Each CSV record to be updated will have a tri-state. Either it was added,
     * updated or there were processing errors.
     */
    @XmlType(name = "processingResult")
    public enum ProcessingResult { ADDED, UPDATED, ERROR }

    /**
     * Map with the result from each record from the CSV file that which was
     * processed. The map contain the Offer Reference Number as key, and the
     * result of the processing as the value.
     */
    @XmlElement(required = true, nillable = true)
    private Map<String, ProcessingResult> processingResult;

    /**
     * Map with the error information related to the processing. The map contain
     * the Offer Reference Number as key, and the validation result as the
     * value.
     */
    @XmlElement(required = true, nillable = true)
    private Map<String, CSVProcessingErrors> errors;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     * Constructor is used in {@code OfferResponse} when deleting an offer.
     */
    public OfferCSVUploadResponse() {
        // Required for WebServices to work. Comment added to please Sonar.
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

    public void setProcessingResult(final Map<String, ProcessingResult> processingResult) {
        this.processingResult = processingResult;
    }

    public Map<String, ProcessingResult> getProcessingResult() {
        return processingResult;
    }

    public void setErrors(final Map<String, CSVProcessingErrors> errors) {
        this.errors = errors;
    }

    public Map<String, CSVProcessingErrors> getErrors() {
        return errors;
    }
}
