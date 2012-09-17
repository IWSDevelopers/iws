/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.monitoring.FieldChange
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;

import java.io.Serializable;

/**
 * When monitoring detailed changes, the Object is used to store the information
 * about which Field, together with the old -> new information change.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class Field implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    // The field information; Name of the field with the old and new values
    private final String field;
    private final String oldValue;
    private final String newValue;

    /**
     * Marking Constructor for Fields, where the actual change (old & new)
     * values should not be stored.
     *
     * @param field  Name of the Field, which was updated
     */
    public Field(final String field) {
        this.field = field;
        oldValue = null;
        newValue = null;
    }

    /**
     * Detailed Constructor, for the case where the information about the Field
     * together with the old and new values should be stored.
     *
     * @param field    Name of the Field, which was updated
     * @param oldValue The old value of the Field
     * @param newValue The new value of the Field
     */
    public Field(final String field, final String oldValue, final String newValue) {
        this.field = field;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getField() {
        return field;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final String details;
        if ((oldValue != null) || (newValue != null)) {
            details = "', oldValue='" + oldValue + '\'' + ", newValue='" + newValue + "'}";
        } else {
            details = "'}";
        }

        return "Field{field='" + field + details;
    }
}
