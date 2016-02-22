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
