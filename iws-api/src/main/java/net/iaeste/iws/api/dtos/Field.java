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
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.ReflectiveStandardMethods;

import java.io.Serializable;

/**
 * When monitoring detailed changes, the Object is used to store the information
 * about which Field, together with the old -&gt; new information change.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class Field implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    // The field information; Name of the field with the old and new values
    private String theField = null;
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
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Marking Constructor for Fields, where the actual change (old &amp; new)
     * values should not be stored.
     *
     * @param field  Name of the Field, which was updated
     */
    public Field(final String field) {
        this.theField = field;
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
        this.theField = field;
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
            theField = field.theField;
            oldValue = field.oldValue;
            newValue = field.newValue;
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
        theField = field;
    }

    /**
     * Retrieves the name of the Field.
     *
     * @return Field name
     */
    public String getField() {
        return theField;
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
        return ReflectiveStandardMethods.reflectiveEquals(this, obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return ReflectiveStandardMethods.reflectiveHashCode(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ReflectiveStandardMethods.reflectiveToString(this);
    }
}
