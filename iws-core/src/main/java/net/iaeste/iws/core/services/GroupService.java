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
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.ProcessGroupResponse;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.core.exceptions.PermissionException;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.core.transformers.CommonTransformer;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.exceptions.PersistenceException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class GroupService {

    private final AccessDao dao;
    private final Notifications notifications;

    /** TODO Find the correct Id for the General Secretary Group. */
    private static final Long GENERAL_SECRETARY_GROUP = -1L;

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
    public ProcessGroupResponse processGroup(final Authentication authentication, final GroupRequest request) {
        final String externalGroupId = request.getGroup().getId();
        final GroupEntity entity;

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
                entity = createGroup(authentication, GroupType.WORKGROUP, request.getGroup(), authentication.getGroup());
                setGroupOwner(entity, authentication.getUser());
            } else if ((parentType == GroupType.MEMBER) && (type == GroupType.LOCAL)) {
                // Create new Local Committee
                entity = createGroup(authentication, GroupType.LOCAL, request.getGroup(), authentication.getGroup());
                setGroupOwner(entity, authentication.getUser());
            } else {
                throw new PermissionException("Not allowed to create a sub-group of type " + type);
            }
        } else {
            entity = dao.findGroup(authentication.getUser(), externalGroupId);

            // Update existing Group. We allow that the name can be altered,
            // provided no other Groups exists with the same name in this scope
            if (entity != null) {
                final String name = request.getGroup().getGroupName();
                if (!dao.hasGroupsWithSimilarName(entity, name)) {
                    dao.persist(authentication, entity, CommonTransformer.transform(request.getGroup()));
                } else {
                    throw new IdentificationException("Another Group exist with a similar name " + name);
                }
            } else {
                throw new IdentificationException("No Group exist with the Id " + externalGroupId);
            }
        }

        return new ProcessGroupResponse(CommonTransformer.transform(entity));
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
        final GroupEntity entity = findGroupFromRequest(authentication, request);
        final FetchGroupResponse response;

        if (entity != null) {
            final Group group = CommonTransformer.transform(entity);
            final List<User> users = findGroupMembers(request.isFetchUsers(), entity);
            final List<Group> groups = findSubGroups(request.isFetchSubGroups(), entity);

            response = new FetchGroupResponse(group, users, groups);
        } else {
            response = new FetchGroupResponse(IWSErrors.OBJECT_IDENTIFICATION_ERROR, "No Group was found matching the requested Id.");
        }

        return response;
    }

    private GroupEntity findGroupFromRequest(final Authentication authentication, final FetchGroupRequest request) {
        final UserEntity user = authentication.getUser();
        final GroupEntity entity;

        if (request.getGroupId() != null) {
            entity = dao.findGroup(user, request.getGroupId());
        } else {
            entity = dao.findGroupByUserAndType(user, request.getGroupType());
        }

        return entity;
    }

    /**
     * Assigning or updating a given users access to a specific group. The
     * action is fairly complex, since it consist of a number of special cases,
     * with variants for both owner and non-owner roles.
     *
     * @param authentication User & Group information
     * @param request        User Group Request information
     */
    public void processUserGroupAssignment(final Authentication authentication, final UserGroupAssignmentRequest request) {
        // First, check if we are allowed to make any changes at all
        throwIfNotIllegalGroupAction(authentication.getGroup().getGroupType().getGrouptype());

        final UserGroupEntity invokingUser = dao.findMemberGroupByUser(authentication.getUser());
        if (shouldChangeSelf(authentication, request)) {
            updateSelf(authentication, invokingUser, request);
        } else if (shouldChangeOwner(invokingUser, request)) {
            updateOwner(authentication, invokingUser, request);
        } else {
            final GroupType type = invokingUser.getGroup().getGroupType().getGrouptype();
            final String roleExternalId = request.getUserGroup().getRole().getRoleId();
            final RoleEntity role = dao.findRoleByExternalIdAndGroup(roleExternalId, authentication.getGroup());


            // Throws an exception if no Role was found

            final String externalUserId = request.getUserGroup().getUser().getUserId();
            final UserGroupEntity existingEntity = dao.findMemberByExternalId(externalUserId, authentication.getGroup());
            final UserGroupEntity given = transform(request.getUserGroup());

            // Special Case: User is member of the General Secretary Group, and thus
            // any changes to this Group, also have to reflect on the Global Members
            // Group
            if (GENERAL_SECRETARY_GROUP.equals(authentication.getGroup().getId())) {
                throw new NotImplementedException("The IWS currently do not support changing Group Membership for the General Secretary Group.");
            } else

            // Special Case: Adding / Updating a member of the National Group, means
            // additionally Updating to the Country Members Group. However, the user
            // must be a a Member of this Group - if not, then we do not permit this
            // action
            if (authentication.getGroup().getGroupType().getGrouptype() == GroupType.NATIONAL) {
                throw new NotImplementedException("Not yet implemented.");
            } else

            // Standard Case: "Ordinary" Group, and no Ownership - but no existing relationship. We're creating a new one
            if (existingEntity == null) {
                // Throws an exception if no User was found
                final UserEntity user = dao.findUserByExternalId(externalUserId);

                // Now, fill in persisted Entities to the new relation
                given.setUser(user);
                given.setGroup(authentication.getGroup());
                given.setRole(role);

                dao.persist(given);
                notifications.notify(authentication, existingEntity, NotificationType.CHANGE_IN_GROUP_MEMBERS);
                notifications.notify(authentication, existingEntity, NotificationType.PROCESS_MAILING_LIST);
            } else

            // Finally, we're done with special cases, and the account already
            // exists, meaning that we're going to update it.
            {
                dao.persist(authentication, existingEntity, given);
                notifications.notify(authentication, existingEntity, NotificationType.CHANGE_IN_GROUP_MEMBERS);
            }
        }
    }

    private static boolean shouldChangeSelf(final Authentication authentication, final UserGroupAssignmentRequest request) {
        final String invokingExternalUserId = authentication.getUser().getExternalId();
        final String requestedExternalUserId = request.getUserGroup().getUser().getUserId();

        return invokingExternalUserId.equals(requestedExternalUserId);
    }

    /**
     * If a person updates his or her own record, it means that they can change
     * their title,
     *
     * @param authentication
     * @param request
     */
    private void updateSelf(final Authentication authentication, final UserGroupEntity currentEntity, final UserGroupAssignmentRequest request) {
        final GroupType type = authentication.getGroup().getGroupType().getGrouptype();
        final Long groupId = authentication.getGroup().getId();

        if ((type == GroupType.NATIONAL) || (type == GroupType.SAR) || GENERAL_SECRETARY_GROUP.equals(groupId)) {
            // Update the parent Member UserGoup relation as well
        }

        // Update the UserGroup relation
        currentEntity.setTitle(request.getUserGroup().getTitle());
        dao.persist(authentication, currentEntity);
    }

    private boolean shouldChangeOwner(final UserGroupEntity invokingUser, final UserGroupAssignmentRequest request) {
        final RoleEntity requestedRole = dao.findRoleByExternalId(request.getUserGroup().getRole().getRoleId());
        final boolean result;

        if (IWSConstants.ROLE_OWNER.equals(requestedRole.getId())) {
            if (IWSConstants.ROLE_OWNER.equals(invokingUser.getRole().getId())) {
                result = true;
            } else {
                throw new IWSException(IWSErrors.NOT_PERMITTED, "Illegal attempt at changing Ownership.");
            }
        } else {
            result = false;
        }

        return result;
    }

    /**
     * As there can only be a single owner of a Group, it means that changing
     * ownership must be dealt with as a special case, where the invoking person
     * must be the current owner, and the provided user will then be the new
     * Owner.<br />
     *   There exist two special cases and the general case for this Operation,
     * changing Ownership of a National (or SAR) Group, and changing Ownership
     * of the General Secretary Group. These special cases exists, since these
     * groups also control the National Members and the Global Members. The
     * special cases follow the same pattern, updating the parent Member Group,
     * and then let the general case take over.<br />
     *   The General Case, simply involves demoting the invoking user, and
     * promoting the Changing Ownership of a Group means demoting the requested
     * user.
     *
     * @param authentication User & Group information
     */
    private void updateOwner(final Authentication authentication, final UserGroupEntity currentOwner, final UserGroupAssignmentRequest request) {
        final UserGroupEntity ownerUserGroupEntity = dao.findMemberByGroupAndUser(authentication.getGroup(), authentication.getUser());

        if (IWSConstants.ROLE_OWNER.equals(ownerUserGroupEntity.getRole().getId())) {
            final GroupType type = authentication.getGroup().getGroupType().getGrouptype();
            final Long groupId = authentication.getGroup().getId();

            // First the special cases, i.e. those cases where we have to also
            // update the parent Member Group. Although we are dealing with
            // different kinds of Groups, they all share the same basic
            // characteristics - they have a Parent Group, which is defined by
            // the given Id
//            if (GENERAL_SECRETARY_GROUP.equals(groupId) || (type == GroupType.NATIONAL) || (type == GroupType.SAR)) {
//                final UserGroupEntity memberUserGroupEntity = dao.findMemberGroupByUser(authentication.getUser());
//                changeGroupOwnership(authentication, memberUserGroupEntity, newOwnerEntity);
//            }
//
//            changeGroupOwnership(authentication, ownerUserGroupEntity, newOwnerEntity);
        } else {
            throw new IWSException(IWSErrors.AUTHORIZATION_ERROR, "The user is not authorized to change ownership.");
        }
    }

    private void changeGroupOwnership(final Authentication authentication, final UserGroupEntity currentOwner, final UserEntity newOwner) {
        final UserGroupEntity existingEntity = dao.findMemberByGroupAndUser(currentOwner.getGroup(), newOwner);

        if (existingEntity != null) {
            // The new owner is already a member of the Group
            changeGroupOwnership(authentication, currentOwner, existingEntity);
        } else {
            // Create a new relation, and set the basic information, the rest
            // will be set in the following method.
            final UserGroupEntity newEntity = new UserGroupEntity();
            newEntity.setUser(newOwner);
            newEntity.setGroup(currentOwner.getGroup());

            changeGroupOwnership(authentication, currentOwner, newEntity);
        }
    }

    private void changeGroupOwnership(final Authentication authentication, final UserGroupEntity currentOwner, final UserGroupEntity newOwner) {
        final RoleEntity moderator = dao.findRoleById(IWSConstants.ROLE_MODERATOR);

        // First, let's update the new Owner with the information from the existing
        newOwner.setRole(currentOwner.getRole());
        newOwner.setTitle(currentOwner.getTitle());
        newOwner.setOnPublicList(currentOwner.getOnPublicList());
        newOwner.setOnPrivateList(currentOwner.getOnPrivateList());

        // Second, change the role & title for the existing Owner
        currentOwner.setRole(moderator);
        currentOwner.setTitle("");

        // Third, save the changes
        dao.persist(authentication, currentOwner);
        dao.persist(authentication, newOwner);
    }

    /**
     * When changing UserGroup settings, there are certain GroupTypes, where it
     * is not permitted to perform such changes.<br />
     *   The Private Group is one such case, where it is not allowed to make any
     * changes, since this Group is purely used as an internal control to ensure
     * that users can have their own data. Which also will be deleted if or when
     * the user is removed from the system.<br />
     *   The Member group is the second special case, since the Member Group is
     * purely managed from the Staff (either National or General Secretary
     * Groups). Changes to Member Groups is managed in parallel with changes to
     * their Staffs.
     *
     * @param groupType The GroupType to check for
     * @throws IWSException if not permitted to make changes to this GroupType
     */
    private static void throwIfNotIllegalGroupAction(final GroupType groupType) {
        if ((groupType == GroupType.MEMBER) || (groupType == GroupType.PRIVATE)) {
            throw new IWSException(IWSErrors.NOT_PERMITTED, "It is not allowed to change the roles for members of GroupType " + groupType);
        }
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private GroupEntity createGroup(final Authentication authentication, final GroupType type, final Group group, final GroupEntity parent) {
        // Before we begin, let's just make sure that there are no other groups
        // with the same name
        throwIfGroupnameIsUsed(parent, group.getGroupName());
        // Find pre-requisites
        final CountryEntity country = dao.findCountryByCode(parent.getCountry().getCountryCode());
        final GroupTypeEntity groupType = dao.findGroupTypeByType(type);

        // Create the new Entity
        final GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupName(group.getGroupName());
        groupEntity.setGroupType(groupType);
        groupEntity.setCountry(country);
        groupEntity.setDescription(group.getDescription());
        groupEntity.setFullName(parent.getFullName() + '.' + group.getGroupName());
        groupEntity.setParentId(parent.getId());
        groupEntity.setListName(groupEntity.getFullName());

        // Save the new Group in the database
        dao.persist(authentication, groupEntity);

        // And return it, so the remainder of the processing can use it
        return groupEntity;
    }

    private void throwIfGroupnameIsUsed(final GroupEntity parent, final String groupName) {
        final List<GroupEntity> found = dao.findGroupByNameAndParent(groupName, parent);
        if (!found.isEmpty()) {
            throw new PersistenceException(IWSErrors.PERSISTENCE_ERROR, "Group cannot be created, another Group with the same name exists.");
        }
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
