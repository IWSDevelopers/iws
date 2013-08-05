/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.GroupService
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.services;

import static net.iaeste.iws.core.transformers.AdministrationTransformer.transform;
import static net.iaeste.iws.core.transformers.AdministrationTransformer.transformMembers;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.core.exceptions.PermissionException;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class GroupService {

    private static final Logger log = Logger.getLogger(GroupService.class);
    private final AccessDao dao;
    private final Notifications notifications;

    public GroupService(final AccessDao dao, final Notifications notifications) {
        this.dao = dao;
        this.notifications = notifications;
    }

    /**
     * This method can handle three types if requests:
     * <ul>
     *     <li>Create new subgroup</li>
     *     <li>Update current group</li>
     *     <li>Block group</li>
     * </ul>
     * If the given Group from the GroupRequest contains an Id, then it is
     * assumed that this group should be updated, otherwise it is an attempt at
     * creating a new Group.
     *
     * @param authentication User & Group information
     * @param request        Group Request information
     */
    public void processGroup(final Authentication authentication, final GroupRequest request) {
        final String externalGroupId = request.getGroup().getGroupId();

        if (externalGroupId == null) {
            final GroupType type = request.getGroup().getGroupType();
            final GroupType parentType = authentication.getGroup().getGroupType().getGrouptype();

            // Create new subgroup for the current group. We allow that the
            // following GroupTypes can be created using this feature: Local
            // Committe or WorkGroup. A Local Committee can again have
            // sub-groups of type WorkGroup. However, a WorkGroup cannot have
            // further sub-groups. If it is a Local Committee, then it belongs
            // under the Members Group, and is parallel to the National Group
            if ((parentType != GroupType.WORKGROUP) && (type == GroupType.WORKGROUP)) {
                final GroupEntity groupEntity = createGroup(GroupType.WORKGROUP, request.getGroup(), authentication.getGroup());
                setGroupOwner(groupEntity, authentication.getUser());
            } else if ((parentType == GroupType.MEMBER) && (type == GroupType.LOCAL)) {
                // Create new Local Committee
                final GroupEntity groupEntity = createGroup(GroupType.LOCAL, request.getGroup(), authentication.getGroup());
                setGroupOwner(groupEntity, authentication.getUser());
            } else {
                throw new PermissionException("Not allowed to create a sub-group of type " + type);
            }
        } else {
            final GroupEntity groupEntity = dao.findGroup(authentication.getUser(), externalGroupId);

            // Update existing Group. We allow that the name can be altered,
            // provided no other Groups exists with the same name in this scope
            if (groupEntity != null) {
                final String name = request.getGroup().getGroupName();
                if (!dao.hasGroupsWithSimilarName(groupEntity.getParentId(), name)) {
                    groupEntity.setGroupName(name);
                    dao.persist(groupEntity);
                } else {
                    throw new IdentificationException("Another Group exist with a similar name " + name);
                }
            } else {
                throw new IdentificationException("No Group exist with the Id " + externalGroupId);
            }
        }
    }

    public void deleteGroup(final Authentication authentication, final GroupRequest request) {
        throw new NotImplementedException("Awaiting implementation, see ticket #278.");
    }

    /**
     * Retrieves the requested Group, and returns it. Although the Request
     * Object is containing the GroupId, we're ignoring it and using the one
     * from the Authentication Object, since the Controller (that invoked this
     * method), is altering the Authorisation Token to use the Request provided
     * GroupId. This is done to avoid that any attempts are made at spoofing the
     * system, i.e. use one GroupId in the token, and validate against that, and
     * then a different in the Request, after our permission checks are
     * completed.
     *
     * @param authentication User & Group information
     * @param request        Group Request information
     * @return Response Object with the requested Group (or null)
     */
    public FetchGroupResponse fetchGroup(final Authentication authentication, final FetchGroupRequest request) {
        final GroupEntity entity = dao.findGroup(authentication.getUser(), request.getGroupId());
        final FetchGroupResponse response;

        if (entity != null) {
            final Group group = transform(entity);
            final List<User> users = findGroupMembers(request.isFetchUsers(), entity);
            final List<Group> groups = findSubGroups(request.isFetchSubGroups(), entity);

            response = new FetchGroupResponse(group, users, groups);
        } else {
            response = new FetchGroupResponse(IWSErrors.OBJECT_IDENTIFICATION_ERROR, "No Group was found matching the requested Id.");
        }

        return response;
    }

    /**
     * Assigning or updating a given users access to a specific group. If we
     * are talking about updating a users relation, then the owner role is a
     * special case.
     *
     * @param authentication User & Group information
     * @param request        Group Request information
     */
    public void processUserGroupAssignment(final Authentication authentication, final UserGroupAssignmentRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private GroupEntity createGroup(final GroupType type, final Group group, final GroupEntity parent) {
        // Find pre-requisites
        final CountryEntity country = dao.findCountryByCode(group.getCountryId());
        final GroupTypeEntity groupType = dao.findGroupTypeByType(type);

        // Create the new Entity
        final GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupName(group.getGroupName());
        groupEntity.setGroupType(groupType);
        groupEntity.setCountry(country);
        groupEntity.setDescription(group.getDescription());
        groupEntity.setFullName(parent.getFullName() + '.' + group.getGroupName());
        groupEntity.setParentId(parent.getParentId());
        groupEntity.setListName(groupEntity.getFullName());

        // Save the new Group in the database
        dao.persist(groupEntity);

        // And return it, so the remainder of the processing can use it
        return groupEntity;
    }

    private void setGroupOwner(final GroupEntity group, final UserEntity user) {
        final RoleEntity role = dao.findRoleById(IWSConstants.ROLE_OWNER);

        final UserGroupEntity userGroup = new UserGroupEntity();
        userGroup.setGroup(group);
        userGroup.setUser(user);
        userGroup.setRole(role);
        userGroup.setTitle(role.getRole());

        dao.persist(userGroup);
    }

    private List<User> findGroupMembers(final boolean fetchUsers, final GroupEntity entity) {
        final List<User> result;

        if (fetchUsers) {
            final List<UserGroupEntity> members = dao.findGroupUsers(entity);
            result = transformMembers(members);
        } else {
            result = new ArrayList<>(0);
        }

        return result;
    }

    private List<Group> findSubGroups(final boolean fetchSubGroups, final GroupEntity entity) {
        final List<Group> result;

        if (fetchSubGroups) {
            final List<GroupEntity> subGroups = dao.findSubGroups(entity.getParentId());
            result = transform(subGroups);
        } else {
            result = new ArrayList<>(0);
        }

        return result;
    }
}
