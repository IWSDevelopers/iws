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
package net.iaeste.iws.api.responses;

import static net.iaeste.iws.api.util.Immutable.immutableList;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.ContactsType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contactsResponse", propOrder = { "users", "groups", "type" })
public final class ContactsResponse extends FallibleResponse {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true) private final List<User> users = new ArrayList<>(0);
    @XmlElement(required = true, nillable = true) private final List<Group> groups = new ArrayList<>(0);
    @XmlElement(required = true, nillable = true) private ContactsType type = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public ContactsResponse() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Error Constructor.
     *
     * @param error    IWS Error Object
     * @param message  Error Message
     */
    public ContactsResponse(final IWSError error, final String message) {
        super(error, message);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setUsers(final List<User> users) {
        this.users.addAll(users);
    }

    /**
     * Will retrieve a list of Users for a Group request. If from a User
     * request, then the list will only contain a single record, the User which
     * was requested. For Other requests, the result is null.
     *
     * @return List with either 1 User, all Users or empty list
     */
    public List<User> getUsers() {
        return immutableList(users);
    }

    public void setGroups(final List<Group> groups) {
        this.groups.addAll(groups);
    }

    /**
     * Will retrieve either a list with a single Group if it was from a Group
     * request, or a list of Groups if it was a User request or all Member and
     * International Groups, if it was an Other request.
     *
     * @return List with either 1 Group, All User Groups or all Top Groups
     */
    public List<Group> getGroups() {
        return immutableList(groups);
    }

    public void setType(final ContactsType type) {
        this.type = type;
    }

    /**
     * The type of request is taken from the request.
     *
     * @return The requested type
     */
    public ContactsType getType() {
        return type;
    }
}
