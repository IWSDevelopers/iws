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
import net.iaeste.iws.api.util.Paginatable;

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
@XmlType(name = "offerCSVUploadRequest", propOrder = { "data", "delimiter" })
public final class OfferCSVUploadRequest extends Paginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Available CSV fields delimiters.
     */
    @XmlType(name = "fieldDelimiter")
    public enum FieldDelimiter {

        COMMA(','),
        SEMICOLON(';');

        // =========================================================================
        // Private Constructor & functionality
        // =========================================================================

        private final char description;

        FieldDelimiter(final char description) {
            this.description = description;
        }

        public char getDescription() {
            return description;
        }
    }

    /**
     * CSV file content
     */
    @XmlElement(required = true)
    private byte[] data;

    /**
     * CSV field delimiter
     */
    @XmlElement(required = true, nillable = true)
    private FieldDelimiter delimiter;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public OfferCSVUploadRequest() {
        this.data = null;
        this.delimiter = null;
    }

    public OfferCSVUploadRequest(final byte[] data, final FieldDelimiter delimiter) {
        this.data = data;
        this.delimiter = delimiter;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setData(final byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setDelimiter(final FieldDelimiter delimiter) {
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
        return new HashMap<>(0);
    }
}
