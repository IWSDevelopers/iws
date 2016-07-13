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
import net.iaeste.iws.api.enums.exchange.EmployerFetchType;
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
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchEmployerRequest", propOrder = { "type", "field", "fetchOfferReferenceNumbers" })
public final class FetchEmployerRequest extends Paginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true)                   private EmployerFetchType type = EmployerFetchType.ALL;
    @XmlElement(required = true, nillable = true)  private String field = null;
    @XmlElement(required = true, nillable = true)  private Boolean fetchOfferReferenceNumbers = Boolean.FALSE;

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the FetchType to ALL, meaning that ALL Employers should be
     * retrieved.
     */
    public void setFetchAll() {
        this.type = EmployerFetchType.ALL;
        this.field = null;
    }

    public void setFetchById(final String id) {
        this.type = EmployerFetchType.ID;
        this.field = id;
    }

    public void setFetchByPartialName(final String partialName) {
        this.type = EmployerFetchType.NAME;
        this.field = partialName;
    }

    public EmployerFetchType getFetchType() {
        return type;
    }

    public String getFetchField() {
        return field;
    }

    /**
     * <p>If the list of Offer Reference Numbers which is currently using a given
     * Employer should also be fetched. The result is stored together with the
     * Employer Object.</p>
     *
     * <p>Note, that fetching the Offer Reference Numbers is only allowed, when
     * fetching a single Employer.</p>
     *
     * @param fetchOfferReferenceNumbers If Offer Reference Numbers should be fetched
     */
    public void setFetchOfferReferenceNumbers(final Boolean fetchOfferReferenceNumbers) {
        this.fetchOfferReferenceNumbers = fetchOfferReferenceNumbers;
    }

    public Boolean getFetchOfferReferenceNumbers() {
        return fetchOfferReferenceNumbers;
    }

    // =========================================================================
    // Standard Response Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "type", type);
        if (type != EmployerFetchType.ALL) {
            isNotNull(validation, "field", field);
        }

        return validation;
    }
}
