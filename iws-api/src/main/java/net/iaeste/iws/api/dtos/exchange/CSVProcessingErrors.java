/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.exchange.CSVProcessingErrors
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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

    // =========================================================================
    // Standard Response Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSVProcessingErrors)) {
            return false;
        }

        final CSVProcessingErrors that = (CSVProcessingErrors) obj;

        if (csvErrors != null ? !csvErrors.equals(that.csvErrors) : that.csvErrors != null) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return csvErrors != null ? csvErrors.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CSVProcessingErrors{" +
                "csvErrors=" + csvErrors +
                '}';
    }
}
