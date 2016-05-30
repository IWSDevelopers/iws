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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "offerCSVUploadRequest", propOrder = { "csv", "delimiter" })
public final class OfferCSVUploadRequest extends Verifications {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Available CSV fields delimiters.
     */
    @XmlType(name = "fieldDelimiter")
    public enum FieldDelimiter {

        COMMA(','),
        SEMICOLON(';');

        // =====================================================================
        // Private Constructor & functionality
        // =====================================================================

        private final char description;

        FieldDelimiter(final char description) {
            this.description = description;
        }

        public char getDescription() {
            return description;
        }
    }

    @XmlElement(required = true) private String csv = null;
    @XmlElement(required = true) private FieldDelimiter delimiter = FieldDelimiter.COMMA;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public OfferCSVUploadRequest() {
        this.csv = null;
    }

    public OfferCSVUploadRequest(final String csv, final FieldDelimiter delimiter) {
        setCsv(csv);
        setDelimiter(delimiter);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setCsv(final String csv) {
        ensureNotNullOrEmpty("csv", csv);
        this.csv = csv;
    }

    public String getCsv() {
        return csv;
    }

    public void setDelimiter(final FieldDelimiter delimiter) {
        ensureNotNull("delimiter", delimiter);
        this.delimiter = delimiter;
    }

    public FieldDelimiter getDelimiter() {
        return delimiter;
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

        isNotNull(validation, "csv", csv);
        isNotNull(validation, "delimiter", delimiter);

        return validation;
    }
}
