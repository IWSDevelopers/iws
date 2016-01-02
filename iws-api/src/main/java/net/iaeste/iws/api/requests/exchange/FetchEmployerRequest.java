/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.exchange.FetchEmployerRequest
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
import net.iaeste.iws.api.enums.SortingField;
import net.iaeste.iws.api.enums.exchange.EmployerFetchType;
import net.iaeste.iws.api.util.AbstractPaginatable;

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
public final class FetchEmployerRequest extends AbstractPaginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = false) private EmployerFetchType type = EmployerFetchType.ALL;
    @XmlElement(required = true, nillable = true)  private String field = null;
    @XmlElement(required = true, nillable = true)  private Boolean fetchOfferReferenceNumbers = false;

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

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
     * If the list of Offer Reference Numbers which is currently using a given
     * Employer should also be fetched. The result is stored together with the
     * Employer Object.
     *
     * @param fetchOfferReferenceNumbers If Offer Reference Numbers should be fetched
     */
    public void setFetchOfferReferenceNumbers(final Boolean fetchOfferReferenceNumbers) {
        this.fetchOfferReferenceNumbers = fetchOfferReferenceNumbers;
    }

    public Boolean getFetchOfferReferenceNumbers() {
        return fetchOfferReferenceNumbers == null ? false : fetchOfferReferenceNumbers;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSortBy(final SortingField sortBy) {
        ensureNotNull("sortBy", sortBy);

        switch (sortBy) {
            case NAME:
                page.setSortBy(sortBy);
                break;
            default:
                // If unsupported, we're going to revert to the default
                page.setSortBy(SortingField.CREATED);
        }
    }
}
