/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.Field
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
 * @since   IWS 1.0
 */
public final class Field implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    // The field information; Name of the field with the old and new values
    private String field = null;
    private String oldValue = null;
    private String newValue = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Field() {
    }

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

    /**
     * Copy Constructor.
     *
     * @param field Field Object to copy
     */
    public Field(final Field field) {
        if (field != null) {
            this.field = field.field;
            this.oldValue = field.oldValue;
            this.newValue = field.newValue;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the name of the field.
     *
     * @param field Field name
     */
    public void setField(final String field) {
        this.field = field;
    }

    /**
     * Retrieves the name of the Field.
     *
     * @return Field name
     */
    public String getField() {
        return field;
    }

    /**
     * Sets the old value for the field.
     *
     * @param oldValue  Old field value
     */
    public void setOldValue(final String oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * Retrieves the old value for the field.
     *
     * @return Old field value
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * Sets the new value for the field.
     *
     * @param newValue New field value
     */
    public void setNewValue(final String newValue) {
        this.newValue = newValue;
    }

    /**
     * Retrieves the new value for the field.
     *
     * @return New field value
     */
    public String getNewValue() {
        return newValue;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Field)) {
            return false;
        }

        final Field other = (Field) obj;

        if ((field != null) ? !field.equals(other.field) : (other.field != null)) {
            return false;
        }

        if ((newValue != null) ? !newValue.equals(other.newValue) : (other.newValue != null)) {
            return false;
        }

        return !((oldValue != null) ? !oldValue.equals(other.oldValue) : (other.oldValue != null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((field != null) ? field.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((oldValue != null) ? oldValue.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((newValue != null) ? newValue.hashCode() : 0);

        return result;
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
