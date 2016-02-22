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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "csvProcessingErrors", propOrder = { "csvErrors" })
public final class CSVProcessingErrors implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Map with the error information related to the processing. The map contain
     * the CSV Field as key, and the error information as value.
     */
    @XmlElement(required = true, nillable = true)
    private Map<String, String> csvErrors;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public CSVProcessingErrors() {
        csvErrors = new HashMap<>(1);
    }

    /**
     * Default Constructor.
     *
     * @param csvErrors Map of CSV Errors for a specific Offer
     */
    public CSVProcessingErrors(final Map<String, String> csvErrors) {
        this.csvErrors = csvErrors;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setCsvErrors(final Map<String, String> csvErrors) {
        this.csvErrors = csvErrors;
    }

    public Map<String, String> getCsvErrors() {
        return csvErrors;
    }

    // =========================================================================
    // Mapped Collection Methods
    // =========================================================================

    public void put(final String key, final String value) {
        csvErrors.put(key, value);
    }

    public void putAll(final Map<String, String> map) {
        csvErrors.putAll(map);
    }

    public boolean isEmpty() {
        return csvErrors.isEmpty();
    }

    public Set<String> keySet() {
        return csvErrors.keySet();
    }

    public String get(final String key) {
        return csvErrors.get(key);
    }
}
