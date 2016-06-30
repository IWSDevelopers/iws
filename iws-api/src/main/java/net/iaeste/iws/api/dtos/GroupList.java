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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This Object serves as a WebService Wrapper for Group Lists. As Collections
 * of Collections cannot be mapped over correctly by the auto-generated
 * WebService Classes.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "groupList", propOrder = "groups")
public final class GroupList implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true)
    private List<Group> groups = new ArrayList<>(0);

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public GroupList() {
        groups = new ArrayList<>();
    }

    /**
     * Default Constructor.
     *
     * @param groups List of Groups
     */
    public GroupList(final List<Group> groups) {
        setGroups(groups);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setGroups(final List<Group> groups) {
        this.groups.addAll(groups);
    }

    public List<Group> getGroups() {
        return immutableList(groups);
    }

    // =========================================================================
    // Mapped Collection Methods
    // =========================================================================

    /**
     * Adds a Group to the internal Group List.
     *
     * @param group Group to add
     */
    public void add(final Group group) {
        groups.add(group);
    }

    /**
     * Retrieves the Group from a given position in the internal Group List,
     * specified by the given Index.
     *
     * @param index Index of the Group to retrieve
     * @return Group
     */
    public Group get(final int index) {
        return groups.get(index);
    }

    /**
     * Retrieves the size of the internal Group Listing.
     *
     * @return Size of the internal Group List
     */
    public int size() {
        return groups.size();
    }
}
