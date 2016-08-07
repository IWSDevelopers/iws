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
import net.iaeste.iws.api.dtos.UserGroup;

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
@XmlType(name = "fetchGroupResponse", propOrder = { "group", "members", "students", "subGroups" })
public final class FetchGroupResponse extends Response {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true) private Group group = null;
    @XmlElement(required = true, nillable = true) private final List<UserGroup> members = new ArrayList<>(0);
    @XmlElement(required = true, nillable = true) private final List<UserGroup> students = new ArrayList<>(0);
    @XmlElement(required = true, nillable = true) private final List<Group> subGroups = new ArrayList<>(0);

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchGroupResponse() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Default Constructor, for creating this Object with a Group and all known
     * Users and Subgroups.
     *
     * @param group      Requested Group
     * @param members List of Users belonging to the Group, with relation details
     * @param subGroups  List of SubGroups belonging to the Group
     */
    public FetchGroupResponse(final Group group, final List<UserGroup> members, final List<Group> subGroups) {
        this.group = group;
        setMembers(members);
        setSubGroups(subGroups);
    }

    /**
     * Error Constructor.
     *
     * @param error    IWS Error Object
     * @param message  Error Message
     */
    public FetchGroupResponse(final IWSError error, final String message) {
        super(error, message);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setGroup(final Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public void setMembers(final List<UserGroup> members) {
        this.members.addAll(members);
    }

    public List<UserGroup> getMembers() {
        return immutableList(members);
    }

    public void setStudents(final List<UserGroup> students) {
        this.students.addAll(students);
    }

    public List<UserGroup> getStudents() {
        return immutableList(students);
    }

    public void setSubGroups(final List<Group> subGroups) {
        this.subGroups.addAll(subGroups);
    }

    public List<Group> getSubGroups() {
        return immutableList(subGroups);
    }
}
