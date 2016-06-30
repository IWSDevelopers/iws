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

import static net.iaeste.iws.api.util.Immutable.immutableList;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.ReflectiveStandardMethods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>If requested, all changes can be monitored internally. The monitoring
 * means that the changes are stored, either complete or just the individual
 * fields, in a history.</p>
 *
 * <p>This Object contain the list of differences for a single update. Complete
 * with who and when. Note, that the control what is to be stored, is set by the
 * Group. By default. the IWS will try to uphold the strictest privacy
 * guidelines, meaning that the initial settings is to not store any such
 * information.</p>
 *
 * <p>It is important to note, that this Object is reading out information that
 * is stored as part of a previous change, meaning that it is not possible to
 * make any changes using this Object.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class Change implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private User user = null;
    private Group group = null;
    private List<Field> fields = new ArrayList<>(0);
    private Date changed = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Change() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Default Constructor.
     *
     * @param user    The user who made this change
     * @param group   The group, which the Object belongs to
     * @param fields  The changed fields
     * @param changed The date of the change
     */
    public Change(final User user, final Group group, final List<Field> fields, final Date changed) {
        this.user = user;
        this.group = group;
        setFields(fields);
        setChanged(changed);
    }

    /**
     * Copy Constructor.
     *
     * @param change Change Object to copy
     */
    public Change(final Change change) {
        if (change != null) {
            user = change.user;
            group = change.group;
            fields = change.fields;
            changed = change.changed;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * The User who made the change.
     *
     * @param user User
     */
    public void setUser(final User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    /**
     * The Group, which the Data belongs too.
     *
     * @param group Owning Group
     */
    public void setGroup(final Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    /**
     * List of all Fields which was changed.
     *
     * @param fields Changed Fields
     */
    public void setFields(final List<Field> fields) {
        this.fields.addAll(fields);
    }

    public List<Field> getFields() {
        return immutableList(fields);
    }

    /**
     * The Date of the change.
     *
     * @param changed Date of Change
     */
    public void setChanged(final Date changed) {
        this.changed = new Date(changed.getTime());
    }

    public Date getChanged() {
        return new Date(changed.getTime());
    }

    // =========================================================================
    // Standard DTO Methods
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
