/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.exchange.OfferCSVUploadRequest
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
 * @since   IWS 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OfferCSVUploadRequest", propOrder = { "data", "delimiter" })
public class OfferCSVUploadRequest extends AbstractPaginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Available CSV fields delimiters.
     */
    @XmlType(name = "FieldDelimiter")
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
    @XmlElement(required = true, nillable = false)
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
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "data", data);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSortBy(final SortingField sortBy) {
        ensureNotNull("sortBy", sortBy);

        switch (sortBy) {
            default:
                // If unsupported, we're going to revert to the default
                page.setSortBy(SortingField.CREATED);
        }
    }
}
