/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.FetchPermissionResponse
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.responses;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.dtos.Authorization;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Role;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.util.AbstractFallible;

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
public final class FetchPermissionResponse extends AbstractFallible {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private String userId;
    private List<Authorization> authorizations;
    private Map<Permission, List<Group>> permissionMap = null;

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
    // Additional helper method sto ease the work with finding permissions
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
     * @return Group with the given Criterias or null
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
     * Returns a List of all Groups, which have the given GroupType. For some
     * GroupTypes, such as Members or National the user may only belong to one,
     * but for others like Local Committee, International or WorkGroup, the User
     * may belong to more.<br />
     *   If no Groups match the Criterias, then an empty list is returned.
     *
     * @param permission Permission to match the Groups for
     * @param type       GroupType to match the Groups for
     * @return List of all Groups matching the given Type & Permission
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
     *
     * @param permission
     * @return
     */
    public List<Group> getGroups(final Permission permission) {
        final List<Group> groups;

        // Before checking the map, let's ensure that it has been generated.
        // Otherwise we'll get a NullPointerException
        convertPermissions();

        if (permissionMap.containsKey(permission)) {
            groups = permissionMap.get(permission);
        } else {
            groups = new ArrayList<>(0);
        }

        return groups;
    }

    /**
     * This method converts the Authorizations Object into a more usable mapping
     * of the Users permissions, by returning a Map using the individual
     * Permissions as keys and having a list of Groups, which the user belongs
     * to.<br />
     *   By having this list, it is possible to quickly extract the actually
     * required information such as if a person is allowed to perform a given
     * action. The Actions are listed in the description of each Permission in
     * the Permission enum.
     *
     * @return Map with Permissions and Groups
     */
    public Map<Permission, List<Group>> convertPermissions() {
        if (permissionMap == null) {
            permissionMap = new EnumMap<>(Permission.class);

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
        }

        return permissionMap;
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

        if (!(obj instanceof FetchPermissionResponse)) {
            return false;
        }

        final FetchPermissionResponse that = (FetchPermissionResponse) obj;
        return !((authorizations != null) ? !authorizations.equals(that.authorizations) : (that.authorizations != null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + ((userId != null) ? userId.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + ((authorizations != null) ? authorizations.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FetchPermissionResponse{" +
                "userId=" + userId +
                "authorizations=" + authorizations +
                '}';
    }
}
