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
package net.iaeste.iws.core.services;

import static net.iaeste.iws.api.util.LogUtil.formatLogMessage;
import static net.iaeste.iws.core.transformers.AdministrationTransformer.transform;
import static net.iaeste.iws.core.transformers.AdministrationTransformer.transformMembers;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.MailReply;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.OwnerRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.ProcessGroupResponse;
import net.iaeste.iws.api.responses.ProcessUserGroupResponse;
import net.iaeste.iws.common.configuration.InternalConstants;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.core.exceptions.PermissionException;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.core.transformers.CommonTransformer;
import net.iaeste.iws.core.util.GroupUtil;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class GroupService {

    private static final Logger LOG = LoggerFactory.getLogger(GroupService.class);

    private static final Long GENERAL_SECRETARY_GROUP = 2L;

    private final AccessDao dao;
    private final Notifications notifications;

    /**
     * Default Constructor.
     *
     * @param dao           Access Dao
     * @param notifications Notification system
     */
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
     * @return Response Object
     */
    public ProcessGroupResponse processGroup(final Authentication authentication, final GroupRequest request) {
        final String externalGroupId = request.getGroup().getGroupId();
        final GroupEntity entity;

        if (externalGroupId == null) {
            entity = processNewGroup(authentication, request);
        } else {
            entity = processExistingGroup(authentication, request, externalGroupId);
        }

        return new ProcessGroupResponse(CommonTransformer.transform(entity));
    }

    private GroupEntity processNewGroup(final Authentication authentication, final GroupRequest request) {
        final GroupEntity entity;
        final GroupType type = request.getGroup().getGroupType();
        final GroupType parentType = authentication.getGroup().getGroupType().getGrouptype();

        // Create new subgroup for the current group. We allow that the
        // following GroupTypes can be created using this feature: Local
        // Committee or WorkGroup. A Local Committee can again have
        // sub-groups of type WorkGroup. However, a WorkGroup cannot have
        // further sub-groups. If it is a Local Committee, then it belongs
        // under the Members Group, and is parallel to the National Group
        if ((parentType != GroupType.WORKGROUP) && (type == GroupType.WORKGROUP)) {
            entity = createGroup(authentication, GroupType.WORKGROUP, request.getGroup(), authentication.getGroup());
            setGroupOwner(authentication, entity);
        } else if ((parentType == GroupType.MEMBER) && (type == GroupType.LOCAL)) {
            // Create new Local Committee
            entity = createGroup(authentication, GroupType.LOCAL, request.getGroup(), authentication.getGroup());
            setGroupOwner(authentication, entity);
        } else {
            throw new PermissionException("Not allowed to create a sub-group of type '" + type.getDescription() + "'.");
        }
        notifications.notify(authentication, entity, NotificationType.NEW_GROUP);
        return entity;
    }

    private GroupEntity processExistingGroup(final Authentication authentication, final GroupRequest request, final String externalGroupId) {
        final GroupEntity entity;// We're fetching the Group with a permission check, to ensure that
        // a user is not attempting to force update different groups. The
        // lookup will throw an Exception, if no such Group exists that the
        // user is permitted to process.
        entity = dao.findGroupByPermission(authentication.getUser(), externalGroupId, Permission.PROCESS_GROUP);
        final GroupType type = entity.getGroupType().getGrouptype();

        if ((type == GroupType.LOCAL) || (type == GroupType.WORKGROUP)) {
            final String name = request.getGroup().getGroupName();
            if (!dao.hasGroupsWithSimilarName(entity, name)) {
                updateGroup(authentication, entity, request.getGroup());
            } else {
                throw new IdentificationException("Another Group exist with a similar name " + name);
            }
        } else {
            final Group theGroup = request.getGroup();
            LOG.info(formatLogMessage(authentication, "Group Updated was made for the restricted group %s of type %s. Only selected fields have been updated!", theGroup.getGroupName(), theGroup.getGroupType().getDescription()));
            limitedGroupUpdate(authentication, entity, theGroup);
        }
        return entity;
    }

    /**
     * When updating a Group, we need to make sure that the fields are properly
     * updated as well. Some fields reflect their value on others, so changes
     * must also be reflected in the same way. The following fields are allowed
     * to be updated:<br />
     * <ul>
     *   <li><b>groupName</b> - If provided</li>
     *   <li><b>fullName</b> - Updated</li>
     *   <li><b>description</b> - If provided</li>
     *   <li><b>listName</b> - Generated</li>
     *   <li><b>PrivateList</b> - If provided & Allowed</li>
     *   <li><b>PublicList</b> - If provided & Allowed</li>
     *   <li><b>MonitoringLevel</b> - If provided</li>
     * </ul>
     *
     * @param authentication Authentication information for requesting user
     * @param entity         Entity to update
     * @param group          Group Object from the requesting user
     */
    private void updateGroup(final Authentication authentication, final GroupEntity entity, final Group group) {
        final String oldFullname = entity.getFullName();
        final String basename = oldFullname.substring(0, entity.getFullName().lastIndexOf('.')) + '.';

        // Create the new Entity
        final GroupType type = entity.getGroupType().getGrouptype();
        final GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupName(GroupUtil.prepareGroupName(type, group));
        groupEntity.setDescription(group.getDescription());
        groupEntity.setFullName(GroupUtil.prepareFullGroupName(type, group, basename));
        groupEntity.setListName(GroupUtil.prepareListName(type, groupEntity.getFullName(), (entity.getCountry() != null) ? entity.getCountry().getCountryName() : null));
        groupEntity.setPrivateList(group.hasPrivateList() && group.getGroupType().getMayHavePrivateMailinglist());
        groupEntity.setPublicList(group.hasPublicList() && group.getGroupType().getMayHavePublicMailinglist());
        groupEntity.setMonitoringLevel(group.getMonitoringLevel());

        dao.persist(authentication, entity, groupEntity);
    }

    /**
     * For some groups, we are only allowing to change the monitoring level,
     * nothing else.
     *
     * @param authentication Authentication information for requesting user
     * @param entity         Entity to update
     * @param group          Group Object from the requesting user
     */
    private void limitedGroupUpdate(final Authentication authentication, final GroupEntity entity, final Group group) {
        final GroupEntity groupEntity = new GroupEntity();
        // Most fields are not updatable, so we set them to the ones we already
        // have stored in the database
        groupEntity.setGroupName(entity.getGroupName());
        groupEntity.setDescription(entity.getDescription());
        groupEntity.setFullName(entity.getFullName());
        groupEntity.setListName(entity.getListName());
        groupEntity.setPrivateList(group.hasPrivateList() && group.getGroupType().getMayHavePrivateMailinglist());
        groupEntity.setPublicList(group.hasPublicList() && group.getGroupType().getMayHavePublicMailinglist());
        // Monitoring is allowed to be updated
        groupEntity.setMonitoringLevel(group.getMonitoringLevel());

        dao.persist(authentication, entity, groupEntity);
    }

    /**
     * This method will delete a Subgroup of the one from the Authentication
     * Object. If the Group contains users and data, then the users and the
     * data will also be removed. However, if the Group has a subgroup, the
     * subgroup must be deleted first. We do not support deleting group trees.
     * Another thing, only Groups of type Local Committee or WorkGroup can be
     * deleted with this method.
     *
     * @param authentication User & Group information
     * @param request        Group Request information
     */
    public void deleteGroup(final Authentication authentication, final GroupRequest request) {
        final GroupEntity parentGroup = authentication.getGroup();
        final Group toBeDeleted = request.getGroup();
        final GroupEntity group = dao.findGroupByExternalId(toBeDeleted.getGroupId());

        if (group.getParentId().equals(parentGroup.getId())) {
            final GroupType type = group.getGroupType().getGrouptype();
            if ((type != GroupType.LOCAL) && (type != GroupType.WORKGROUP)) {
                throw new IWSException(IWSErrors.NOT_PERMITTED, "It is not allowed to remove groups of type " + type + " with this request.");
            }

            if (!dao.findSubGroups(group.getId()).isEmpty()) {
                throw new IWSException(IWSErrors.NOT_PERMITTED, "The Group being deleted contains SubGroups.");
            }

            LOG.info(formatLogMessage(authentication, "The Group '%s' with Id '%s', is being deleted by '%s'.", group.getGroupName(), group.getExternalId(), authentication.getUser().getExternalId()));
            dao.delete(group);
        } else {
            throw new IWSException(IWSErrors.NOT_PERMITTED, "The Group is not associated with the requesting Group.");
        }
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
            final List<UserGroup> users = findGroupMembers(entity, request);
            final List<UserGroup> students = findStudents(entity, request.isFetchStudents());
            final List<Group> groups = findSubGroups(entity, request.isFetchSubGroups());

            response = new FetchGroupResponse(group, users, groups);
            response.setStudents(students);
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
     * Updates the Owner of a Group to the one provided, under the conditions
     * that the new Owner is an existing and active user. The current owner is
     * then demoted to Moderator of the Group.
     *
     * @param authentication User & Group information
     * @param request        User Group Request information
     */
    public void changeUserGroupOwner(final Authentication authentication, final OwnerRequest request) {
        if (!authentication.getUser().getExternalId().equals(request.getUser().getUserId())) {
            final UserEntity user = dao.findActiveUserByExternalId(request.getUser().getUserId());

            if (user != null) {
                changeUserGroupOwner(authentication, request, user);
            } else {
                throw new PermissionException("Cannot reassign ownership to an inactive person.");
            }
        } else {
            throw new PermissionException("Cannot reassign ownership to the current Owner.");
        }
    }

    private void changeUserGroupOwner(final Authentication authentication, final OwnerRequest request, final UserEntity user) {
        final GroupEntity group = authentication.getGroup();
        final GroupType type = group.getGroupType().getGrouptype();

        // If we have a National/General Secretary change, we
        // additionally have to change the Owner of the (Parent)
        // Member Group
        if ((type == GroupType.NATIONAL) || group.getId().equals(GENERAL_SECRETARY_GROUP)) {
            final GroupEntity memberGroup = dao.findMemberGroup(user);
            LOG.debug(formatLogMessage(authentication, "Changing owner for Member Group '%s' with Id '%s'.", memberGroup.getGroupName(), memberGroup.getExternalId()));
            if (memberGroup.getId().equals(group.getParentId())) {
                changeGroupOwner(authentication, user, memberGroup, request.getTitle());
            } else {
                final String secretary = group.getId().equals(GENERAL_SECRETARY_GROUP) ? "General" : "National";
                throw new PermissionException("Cannot reassign " + secretary + " Secretary to a person who is not a member from " + group.getGroupName() + '.');
            }
        }

        // As the NS/GS aspects are gone, we can deal with the actual
        // change just as with any other group
        LOG.debug(formatLogMessage(authentication, "Changing owner for the Group '%s' with Id '%s'.", group.getGroupName(), group.getExternalId()));
        changeGroupOwner(authentication, user, group, request.getTitle());
    }

    /**
     * This method will change the owner from the existing that is part of the
     * given {@code Authentication} Object to the provided for the Group given.
     * All of the current Owners information is copied over to the new Owner,
     * and the current (now old) Owner, will be downgraded to Moderator and have
     * the existing title altered.
     *
     * @param authentication Authentication Object for the existing Owner
     * @param user           The new Owner
     * @param group          The Group to change the Owner of
     * @param title          The new title for the former owner
     */
    private void changeGroupOwner(final Authentication authentication, final UserEntity user, final GroupEntity group, final String title) {
        final UserGroupEntity oldOwner = dao.findByGroupAndUser(group, authentication.getUser());
        final UserGroupEntity newOwner;

        // Check if the person already is a member of the Group, if not then
        // we'll create a new Record
        final UserGroupEntity existing = dao.findByGroupAndUser(group, user);
        if (existing == null) {
            newOwner = new UserGroupEntity();
            newOwner.setGroup(group);
            newOwner.setUser(user);
        } else {
            newOwner = existing;
        }

        // First we update the new Owner. By default, the Owner is on all lists
        newOwner.setRole(dao.findRoleById(InternalConstants.ROLE_OWNER));
        newOwner.setTitle(oldOwner.getTitle());
        newOwner.setOnPublicList(true);
        newOwner.setOnPrivateList(true);
        newOwner.setWriteToPrivateList(true);
        LOG.debug(formatLogMessage(authentication, "New Owner: %s gets the role %s for group %s.", user.getFirstname() + ' ' + user.getLastname(), newOwner.getRole().getRole(), group.getGroupName()));
        dao.persist(authentication, newOwner);

        // The old Owner will get the Moderator Role and have the title
        // removed, since it may no longer be valid
        oldOwner.setRole(dao.findRoleById(InternalConstants.ROLE_MODERATOR));
        oldOwner.setTitle(title);
        LOG.debug(formatLogMessage(authentication, "Old Owner: %s gets the role %s for group %s.", authentication.getUser().getFirstname() + ' ' + authentication.getUser().getLastname(), oldOwner.getRole().getRole(), group.getGroupName()));
        dao.persist(authentication, oldOwner);
        LOG.debug(formatLogMessage(authentication, "Ownership changes have been persisted."));
    }

    /**
     * Assigning or updating a given users access to a specific group. To ensure
     * that the logic is as simple as possible, all special cases from IW3 have
     * currently been dropped, and will only be added, if they are explicitly
     * requested.<br />
     *   Note, this request will not allow changing of the Owner, that is dealt
     * with in a different request.
     *
     * @param authentication User & Group information
     * @param request        User Group Request information
     * @return Processed result
     */
    public ProcessUserGroupResponse processUserGroupAssignment(final Authentication authentication, final UserGroupAssignmentRequest request) {
        final UserGroupEntity invokingUser = dao.findMemberGroupByUser(authentication.getUser());
        final ProcessUserGroupResponse response;

        if (shouldChangeSelf(authentication, request)) {
            response = updateSelf(authentication, invokingUser, request);
        } else if (shouldDeleteMember(request)) {
            throw new IWSException(IWSErrors.WARNING, "It is not permitted to remove a User from a Member Group with this request. Please delete the user instead.");
        } else if (shouldChangeOwner(invokingUser, request)) {
            throw new PermissionException("It is not permitted to change ownership with this request. Please use the correct request.");
        } else {
            // We have the case with bug #482, that it is possible for someone
            // to change the role of the NS in error! If the invoking request is
            // setting the role to member but it is in actuality owner, then the
            // logic will force the correction!
            final String roleExternalId = request.getUserGroup().getRole().getRoleId();
            final String externalUserId = request.getUserGroup().getUser().getUserId();

            final RoleEntity role = dao.findRoleByExternalIdAndGroup(roleExternalId, authentication.getGroup());
            final UserGroupEntity existingEntity = dao.findByGroupAndExternalUserId(authentication.getGroup(), externalUserId);

            if ((existingEntity != null) && (existingEntity.getRole().getId() == InternalConstants.ROLE_OWNER)) {
                throw new PermissionException("It is not permitted to change the current Owner.");
            } else if (request.getAction() == Action.DELETE) {
                response = deleteUserGroupRelation(authentication, role, existingEntity);
            } else {
                response = processUserGroupRelation(authentication, invokingUser, request, externalUserId, role, existingEntity);
            }
        }

        return response;
    }

    private static boolean shouldChangeSelf(final Authentication authentication, final UserGroupAssignmentRequest request) {
        final String invokingExternalUserId = authentication.getUser().getExternalId();
        final String requestedExternalUserId = request.getUserGroup().getUser().getUserId();

        return invokingExternalUserId.equals(requestedExternalUserId);
    }

    /**
     * If a user is invoking this Process UserGroup Assignement on their own
     * relation, then it is possible to change a couple of minor things. Users
     * are generally only allowed to alter the private mailing list settings and
     * their title. Only if they're also having role Moderator, is it allowed to
     * change public settings and the Write to Private List flag.
     *
     * @param authentication User & Group information
     * @param request        User Group Request information
     * @return Processed UserGroup relation
     */
    private ProcessUserGroupResponse updateSelf(final Authentication authentication, final UserGroupEntity currentEntity, final UserGroupAssignmentRequest request) {
        // Update non critical settings, title & private list
        currentEntity.setTitle(request.getUserGroup().getTitle());
        currentEntity.setOnPrivateList(request.getUserGroup().isOnPrivateList());

        // Changing other settings, is only allowed if the User is having a
        // specific relation to the Group
        if (currentEntity.getRole().getId() == InternalConstants.ROLE_OWNER) {
            // If the User is the current Owner, we're overridding the settings,
            // Owners must always be on the lists!
            currentEntity.setOnPublicList(true);
            currentEntity.setOnPrivateList(true);
            currentEntity.setWriteToPrivateList(true);
        } else if (currentEntity.getRole().getId() == InternalConstants.ROLE_MODERATOR) {
            // If the User is a Moderator, they may control their private
            // mailing list access themselves - the public mailing list setting
            // is only allowed to be controlled by the Owner! As the Private
            // list flag is set above, we're just forcing the Write to Private
            // list, since this is a more generic flag.
            currentEntity.setWriteToPrivateList(true);
        }

        // Save the changes.
        dao.persist(authentication, currentEntity);

        return new ProcessUserGroupResponse(transform(currentEntity));
    }

    /**
     * Returns true, if the requesting User attempts to delete someone from a
     * Member Group. Such actions is not permitted, since it is the equivalent
     * to deleting a User!
     *
     * @param request User Group Request Object
     * @return True if attempting to delete a user from a Member Group
     */
    private static boolean shouldDeleteMember(final UserGroupAssignmentRequest request) {
        final Action action = request.getAction();
        final GroupType type = request.getUserGroup().getGroup().getGroupType();

        return (action == Action.DELETE) && (type == GroupType.MEMBER);
    }

    /**
     * Changing the Owner is a special functionality, which is only permitted
     * via the {@link #changeGroupOwner} request.
     *
     * @param invokingUser The User making the request
     * @param request      Request information
     * @return True if attempting to change the Owner
     */
    private boolean shouldChangeOwner(final UserGroupEntity invokingUser, final UserGroupAssignmentRequest request) {
        final RoleEntity requestedRole = dao.findRoleByExternalId(request.getUserGroup().getRole().getRoleId());
        final boolean result;

        if (requestedRole.getId() == InternalConstants.ROLE_OWNER) {
            if (invokingUser.getRole().getId() == InternalConstants.ROLE_OWNER) {
                result = true;
            } else {
                throw new PermissionException("Illegal attempt at changing Ownership.");
            }
        } else {
            result = false;
        }

        return result;
    }

    private ProcessUserGroupResponse deleteUserGroupRelation(final Authentication authentication, final RoleEntity role, final UserGroupEntity existingEntity) {
        if (existingEntity != null) {
            if (role.getId() == 1) {
                // We're attempting to delete the owner, major no-no
                throw new PermissionException("It is not allowed to delete the current Owner of the Group.");
            } else {
                dao.delete(existingEntity);
                notifications.notify(authentication, existingEntity, NotificationType.CHANGE_IN_GROUP_MEMBERS);
                // We're just returning an empty response Object, since the User
                // has now been deleted
                return new ProcessUserGroupResponse();
            }
        } else {
            throw new IdentificationException("No user were found to be deleted.");
        }
    }

    private ProcessUserGroupResponse processUserGroupRelation(final Authentication authentication, final UserGroupEntity invokingUser, final UserGroupAssignmentRequest request, final String externalUserId, final RoleEntity role, final UserGroupEntity existingEntity) {
        final UserGroupEntity given = transform(request.getUserGroup());
        final ProcessUserGroupResponse response;

        if (existingEntity == null) {
            // Throws an exception if no User was found
            final UserEntity user = dao.findUserByExternalId(externalUserId);

            // Now, fill in persisted Entities to the new relation
            given.setUser(user);
            given.setGroup(authentication.getGroup());
            given.setRole(role);

            // Now set the information from the request
            final UserGroup information = request.getUserGroup();
            given.setTitle(information.getTitle());
            // Only the Owner of a Group may control who is on the public list.
            if (invokingUser.getRole().getId() == InternalConstants.ROLE_OWNER) {
                given.setOnPublicList(information.isOnPublicList());
            }
            given.setOnPrivateList(information.isOnPrivateList());
            given.setWriteToPrivateList(information.mayWriteToPrivateList());

            // And save...
            dao.persist(given);

            notifications.notify(authentication, given, NotificationType.CHANGE_IN_GROUP_MEMBERS);
            response = new ProcessUserGroupResponse(transform(given));
        } else {
            // We're adding the new role here, and won't have history of the
            // changes, since the normal merge method is a general purpose
            // method. The role is not something we should allow being handled
            // via a general purpose method, since it critical information.
            existingEntity.setRole(role);

            // Following two lines are set by the merge method, so this is duplication.
            existingEntity.setOnPublicList(request.getUserGroup().isOnPublicList());
            existingEntity.setOnPrivateList(request.getUserGroup().isOnPrivateList());
            existingEntity.setWriteToPrivateList(request.getUserGroup().mayWriteToPrivateList());
            dao.persist(authentication, existingEntity, given);

            notifications.notify(authentication, existingEntity, NotificationType.CHANGE_IN_GROUP_MEMBERS);
            response = new ProcessUserGroupResponse(transform(existingEntity));
        }

        return response;
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private GroupEntity createGroup(final Authentication authentication, final GroupType type, final Group group, final GroupEntity parent) {
        // Before we begin, let's just make sure that there are no other groups
        // with the same name
        throwIfGroupnameIsUsed(parent, group.getGroupName());
        // Find pre-requisites
        final CountryEntity country = (parent.getCountry() != null) ? dao.findCountryByCode(parent.getCountry().getCountryCode()) : null;
        final GroupTypeEntity groupType = dao.findGroupTypeByType(type);
        final String basename = GroupUtil.prepareBaseGroupName(
                parent.getGroupType().getGrouptype(),
                (parent.getCountry() != null) ? parent.getCountry().getCountryName() : null,
                parent.getGroupName(),
                parent.getFullName());

        // Create the new Entity
        final GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupName(GroupUtil.prepareGroupName(type, group));
        groupEntity.setGroupType(groupType);
        groupEntity.setCountry(country);
        groupEntity.setDescription(group.getDescription());
        groupEntity.setFullName(GroupUtil.prepareFullGroupName(type, group, basename));
        groupEntity.setParentId(parent.getId());
        groupEntity.setExternalParentId(parent.getExternalId());
        groupEntity.setPrivateList(type.getMayHavePrivateMailinglist());
        groupEntity.setPublicList(type.getMayHavePublicMailinglist());
        groupEntity.setPrivateReplyTo(MailReply.REPLY_TO_LIST);
        groupEntity.setPublicReplyTo(MailReply.REPLY_TO_SENDER);
        groupEntity.setListName(GroupUtil.prepareListName(type, groupEntity.getFullName(), (country != null) ? country.getCountryName() : null));

        // Save the new Group in the database
        dao.persist(authentication, groupEntity);

        // And return it, so the remainder of the processing can use it
        return groupEntity;
    }

    private void throwIfGroupnameIsUsed(final GroupEntity parent, final String groupName) {
        final List<GroupEntity> found = dao.findGroupByNameAndParent(groupName, parent);
        if (!found.isEmpty()) {
            throw new PersistenceException("Group cannot be created, another Group with the same name exists.");
        }
    }

    private void setGroupOwner(final Authentication authentication, final GroupEntity group) {
        final RoleEntity role = dao.findRoleById(InternalConstants.ROLE_OWNER);

        final UserGroupEntity userGroup = new UserGroupEntity();
        userGroup.setGroup(group);
        userGroup.setUser(authentication.getUser());
        userGroup.setRole(role);
        userGroup.setTitle(role.getRole());

        dao.persist(authentication, userGroup);
    }

    private List<UserGroup> findGroupMembers(final GroupEntity entity, final FetchGroupRequest request) {
        final List<UserGroupEntity> members;

        switch (request.getUsersToFetch()) {
            case ACTIVE:
                members = dao.findActiveGroupMembers(entity);
                break;
            case ALL:
                members = dao.findAllGroupMembers(entity);
                break;
            case NONE:
            default:
                members = new ArrayList<>(0);
        }

        return transformMembers(members);
    }

    private List<UserGroup> findStudents(final GroupEntity entity, final boolean fetchStudents) {
        final List<UserGroup> result;

        if (fetchStudents && (entity.getGroupType().getGrouptype() == GroupType.NATIONAL)) {
            final List<UserGroupEntity> members = dao.findStudents(entity.getParentId());
            result = transformMembers(members);
        } else {
            result = new ArrayList<>(0);
        }

        return result;
    }

    private List<Group> findSubGroups(final GroupEntity entity, final boolean fetchSubGroups) {
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
