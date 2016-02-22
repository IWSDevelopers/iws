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
package net.iaeste.iws.api.requests.student;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.SortingField;
import net.iaeste.iws.api.util.AbstractPaginatable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchStudentApplicationsRequest", propOrder = { "offerId" })
public final class FetchStudentApplicationsRequest extends AbstractPaginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = false)
    private String offerId = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchStudentApplicationsRequest() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * <p>Default Constructor.</p>
     *
     * @param offerId Offer Id
     */
    public FetchStudentApplicationsRequest(final String offerId) {
        this.offerId = offerId;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the OfferId, if the value is null, then an
     * IllegalArgument exception is thrown.
     *
     * @param offerId OfferId
     * @throws IllegalArgumentException if the token is invalid
     */
    public void setOfferId(final String offerId) throws IllegalArgumentException {
        ensureValidId("offerId", offerId);

        this.offerId = offerId;
    }

    public String getOfferId() {
        return offerId;
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

        isNotNull(validation, "offerId", offerId);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSortBy(final SortingField sortBy) {
        ensureNotNull("sortBy", sortBy);

        switch (sortBy) {
            //case CREATED:
            case NAME:
                page.setSortBy(sortBy);
                break;
            default:
                // If unsupported, we're going to revert to the default
                page.setSortBy(SortingField.CREATED);
        }
    }
}
