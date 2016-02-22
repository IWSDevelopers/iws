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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.dtos.Authorization;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Role;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Permission;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Listing of Permission for a user.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchPermissionResponse", propOrder = { "userId", "authorizations" })
public final class FetchPermissionResponse extends FallibleResponse {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * The UserId for the User, who requested the Permission Response.
     */
    @XmlElement(required = true, nillable = false)
    private String userId;

    /**
     * List of all the Authorizations that the user has, complete with all the
     * User Group relations.
     */
    @XmlElement(required = true, nillable = false)
    private List<Authorization> authorizations;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchPermissionResponse() {
        userId = null;
        authorizations = new ArrayList<>(0);
    }

    /**
     * Default Constructor.
     *
     * @param userId         UserId for the User who's permissions is retrieved
     * @param authorizations List of allowed Permission for a given user
     */
    public FetchPermissionResponse(final String userId, final List<Authorization> authorizations) {
        this.userId = userId;
        this.authorizations = authorizations;
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public FetchPermissionResponse(final IWSError error, final String message) {
        super(error, message);

        userId = null;
        authorizations = null;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setAuthorizations(final List<Authorization> authorizations) {
        this.authorizations = authorizations;
    }

    public List<Authorization> getAuthorizations() {
        return authorizations;
    }

    // =========================================================================
    // Additional helper methods to ease the work with finding permissions
    // =========================================================================

    /**
     * Checks if a User is allowed to perform a given Action. If so, then a True
     * is returned, otherwise a false.
     *
     * @param permission Permission to check if the User has
     * @return True if User is permitted, otherwise false
     */
    public Boolean hasPermission(final Permission permission) {
        return !getGroups(permission).isEmpty();
    }

    /**
     * Returns the Group, with the given Id, if it also has the Permission
     * requested. If not, then a null is returned.
     *
     * @param permission Permission to match the Groups for
     * @param groupId    The Id of the Group to match
     * @return Group with the given Criteria's or null
     */
    public Group getGroup(final Permission permission, final String groupId) {
        Group found = null;

        for (final Group group : getGroups(permission)) {
            if (group.getGroupId().equals(groupId)) {
                found = group;
                break;
            }
        }

        return found;
    }

    /**
     * <p>Returns a List of all Groups, which have the given GroupType. For some
     * GroupTypes, such as Members or National the user may only belong to one,
     * but for others like Local Committee, International or WorkGroup, the User
     * may belong to more.</p>
     *
     * <p>If no Groups match the Criteria's, then an empty list is returned.</p>
     *
     * @param permission Permission to match the Groups for
     * @param type       GroupType to match the Groups for
     * @return List of all Groups matching the given Type &amp; Permission
     */
    public List<Group> getGroups(final Permission permission, final GroupType type) {
        final List<Group> found = new ArrayList<>();

        for (final Group group : getGroups(permission)) {
            if (group.getGroupType() == type) {
                found.add(group);
            }
        }

        return found;
    }

    /**
     * Returns a List of all the Groups, where the user has been granted the
     * given Permission.
     *
     * @param permission Permission to find matching Groups for
     * @return List of Groups applicable to the given Permission
     */
    public List<Group> getGroups(final Permission permission) {
        final Map<Permission, List<Group>> permissionMap = convertPermissions();
        final List<Group> groups;

        if (permissionMap.containsKey(permission)) {
            groups = permissionMap.get(permission);
        } else {
            groups = new ArrayList<>(0);
        }

        return groups;
    }

    /**
     * <p>This method converts the Authorizations Object into a more usable
     * mapping of the Users permissions, by returning a Map using the individual
     * Permissions as keys and having a list of Groups, which the user belongs
     * to.</p>
     *
     * <p>By having this list, it is possible to quickly extract the actually
     * required information such as if a person is allowed to perform a given
     * action. The Actions are listed in the description of each Permission in
     * the Permission enum.</p>
     *
     * <p>Note, this method will re-generate the map each time it is invoked, and
     * other methods in this class, such as #hasPermission, #getGroup &amp;
     * #getGroups will all invoke it as well. Meaning that a careless usage of
     * this method can be costly. It was decided to leave it as such, since the
     * IWS is exposing these Objects purely as an example, and not as Maps
     * aren't Serializable, it will remain so.</p>
     *
     * @return Map with Permissions and Groups
     */
    public Map<Permission, List<Group>> convertPermissions() {
        final Map<Permission, List<Group>> permissionMap = new EnumMap<>(Permission.class);

        for (Authorization authorization : authorizations) {
            final Role role = authorization.getUserGroup().getRole();
            final Group group = authorization.getUserGroup().getGroup();

            for (Permission permission : role.getPermissions()) {
                if (!permissionMap.containsKey(permission)) {
                    permissionMap.put(permission, new ArrayList<Group>(1));
                }
                permissionMap.get(permission).add(group);
            }
        }

        return permissionMap;
    }
}
