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
import static net.iaeste.iws.common.utils.StringUtils.toLower;
import static net.iaeste.iws.core.transformers.AdministrationTransformer.transform;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Role;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.MailReply;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.exceptions.VerificationException;
import net.iaeste.iws.api.requests.AccountNameRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.common.configuration.InternalConstants;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.PermissionRoleEntity;
import net.iaeste.iws.persistence.entities.PersonEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.entities.exchange.StudentEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class AccountService extends CommonService<AccessDao> {

    private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);
    private final Notifications notifications;

    public AccountService(final Settings settings, final AccessDao dao, final Notifications notifications) {
        super(settings, dao);

        this.notifications = notifications;
    }

    /**
     * Create a new User Account in the IWS. The user is automatically assigned
     * to the same Members Group, as the person invoking the request.<br />
     *   If the request is made for a Student Account, then no private Group
     * will be added, instead the user will be added to the Country's Student
     * Group with the role "Student".
     *
     * @param authentication User & Group information
     * @param request        User Creation Request
     * @return Error information
     */
    public CreateUserResponse createUser(final Authentication authentication, final CreateUserRequest request) {
        final UserEntity user;

        if (request.isStudent()) {
            user = createStudentAccount(authentication, request);
        } else {
            user = createUserAccount(authentication, request);
            notifications.notify(authentication, user, NotificationType.ACTIVATE_NEW_USER);
        }

        return new CreateUserResponse(transform(user));
    }

    public CreateUserResponse createStudent(final Authentication authentication, final CreateUserRequest request) {
        final UserEntity user = createStudentAccount(authentication, request);
        return new CreateUserResponse(transform(user));
    }

    private UserEntity createUserAccount(final Authentication authentication, final CreateUserRequest request) {
        final RoleEntity owner = dao.findRoleById(InternalConstants.ROLE_OWNER);
        final RoleEntity member = dao.findRoleById(InternalConstants.ROLE_MEMBER);

        final String username = verifyUsernameNotInSystem(request.getUsername());
        final UserEntity user = createAndPersistUserEntity(authentication, username, request.getPassword(), request.getFirstname(), request.getLastname(), request.isStudent());
        final GroupEntity privateGroup = createAndPersistPrivateGroup(user);
        final UserGroupEntity privateUserGroup = new UserGroupEntity(user, privateGroup, owner);

        dao.persist(privateUserGroup);
        addUserToGroup(user, authentication.getGroup(), member, false);

        notifications.notify(authentication, user, NotificationType.NEW_USER);
        notifications.notify(authentication, user, NotificationType.PROCESS_EMAIL_ALIAS);

        return user;
    }

    private UserEntity createStudentAccount(final Authentication authentication, final CreateUserRequest request) {
        final String username = verifyUsernameNotInSystem(request.getUsername());
        final GroupEntity studentGroup = findOrCreateStudentGroup(authentication);

        final UserEntity user = createAndPersistUserEntity(authentication, username, request.getPassword(), request.getFirstname(), request.getLastname(), request.isStudent());
        final StudentEntity studentEntity = new StudentEntity(user);
        dao.persist(studentEntity);
        final RoleEntity student = dao.findRoleById(InternalConstants.ROLE_STUDENT);

        addUserToGroup(user, authentication.getGroup(), student, true);
        addUserToGroup(user, studentGroup, student, true);

        return user;
    }

    private GroupEntity findOrCreateStudentGroup(final Authentication authentication) {
        final GroupEntity memberGroup = dao.findMemberGroup(authentication.getUser());
        final GroupEntity studentGroup;

        final GroupEntity existingGroup = dao.findStudentGroup(memberGroup);
        if (existingGroup != null) {
            studentGroup = existingGroup;
        } else {
            final GroupType type = GroupType.STUDENT;
            studentGroup = new GroupEntity();
            studentGroup.setExternalId(UUID.randomUUID().toString());
            studentGroup.setCountry(memberGroup.getCountry());
            studentGroup.setPrivateList(type.getMayHavePrivateMailinglist());
            studentGroup.setPublicList(type.getMayHavePublicMailinglist());
            studentGroup.setGroupName(memberGroup.getGroupName() + '.' + type.getDescription());
            studentGroup.setGroupType(dao.findGroupType(type));
            studentGroup.setParentId(memberGroup.getId());
            studentGroup.setExternalParentId(memberGroup.getExternalId());
            studentGroup.setPrivateReplyTo(MailReply.NO_REPLY);
            studentGroup.setPublicReplyTo(MailReply.NO_REPLY);
            dao.persist(studentGroup);
        }

        return studentGroup;
    }

    private String verifyUsernameNotInSystem(final String toCheck) {
        // To avoid problems, all internal handling of the username is in lowercase
        final String username = toLower(toCheck);

        if (dao.findExistingUserByUsername(username) != null) {
            throw new IWSException(IWSErrors.USER_ACCOUNT_EXISTS, "An account for the user with username " + username + " already exists.");
        }

        return username;
    }

    private void addUserToGroup(final UserEntity user, final GroupEntity group, final RoleEntity role, final Boolean isStudent) {
        final UserGroupEntity userGroup = new UserGroupEntity(user, group, role);

        // By default, users are not allowed to be on neither the public nor
        // private mailing lists. We're therefore checking if it is a Student
        // account we're generating and forcing an update accordingly
        if (isStudent) {
            userGroup.setOnPublicList(false);
            userGroup.setOnPrivateList(false);
            userGroup.setWriteToPrivateList(false);
        }

        dao.persist(userGroup);
    }

    /**
     * Users cannot access the IWS, until their account has been activated, this
     * happens via an e-mail that is sent to their e-mail address (username),
     * with an activation link.<br />
     *   Once an activation link is activated, this method should be invoked,
     * which will handle the actual activation process. Meaning, that if an
     * account is found in status "new", and with the given activation code,
     * then it is being updated to status "active", the code is removed and the
     * updates are saved.<br />
     *   If everything went well, then the user can start accessing the account,
     * directly after the activation.
     *
     * @param activationString Hash value of the activation String
     */
    public void activateUser(final String activationString) {
        final UserEntity user = dao.findNewUserByCode(activationString);

        if (user != null) {
            // Update the fields in the User Entity, so the user can log in
            user.setStatus(UserStatus.ACTIVE);
            user.setCode(null);
            user.setModified(new Date());
            dao.persist(user);

            final Authentication authentication = new Authentication(user, UUID.randomUUID().toString());
            notifications.notify(authentication, user, NotificationType.USER_ACTIVATED);

            //notify all groups the user is member about change of mailing lists
            final List<UserGroupEntity> userGroups = dao.findAllUserGroups(user);
            for (final UserGroupEntity userGroup : userGroups) {
                notifications.notify(authentication, userGroup, NotificationType.CHANGE_IN_GROUP_MEMBERS);
            }
        } else {
            throw new IWSException(IWSErrors.AUTHENTICATION_ERROR, "No account for this user was found.");
        }
    }

    /**
     * Updates the users username in the
     *
     * @param updateCode Code used for updating the username for the account
     */
    public void updateUsername(final String updateCode) {
        final UserEntity user = dao.findActiveUserByCode(updateCode);

        if (user != null) {
            // Update the UserEntity with the new Username
            user.setTemporary(user.getUsername());
            user.setUsername(user.getData());
            user.setCode(null);
            user.setData(null);
            user.setModified(new Date());
            dao.persist(user);
            final Authentication authentication = new Authentication(user, UUID.randomUUID().toString());
            notifications.notify(authentication, user, NotificationType.USERNAME_UPDATED);
        } else {
            throw new IWSException(IWSErrors.AUTHENTICATION_ERROR, "No account for this user was found.");
        }
    }

    /**
     * Now, this is a tricky one - there are two usages of this method. The
     * first is for private usage, meaning that if the UserId of both the
     * Authentication Object and the Request is the same, then it means that
     * the user is trying to make updates to his or her data, or delete the
     * record.<br />
     *   If the userId's differ, then a permission check is made, to see if the
     * requesting user is allowed to perform this kind of action, and if they
     * are allowed to do it against the user.<br />
     *   Now, there are other complications to take into considerations. If the
     * user in the request is the current Owner of the Group in question
     * (members group), then the account cannot be altered. This little
     * amendment also applies to personal requests, where the owner cannot
     * delete him or herself.
     *
     * @param authentication User & Group information
     * @param request        User Request information
     */
    public void controlUserAccount(final Authentication authentication, final UserRequest request) {
        final String externalId = authentication.getUser().getExternalId();
        final String providedId = request.getUser().getUserId();

        // Check if this is a personal request or not. If it is a personal
        // request, then we'll hand over the handling to the personal handler,
        // otherwise it will be granted to the administration handler
        if (externalId.equals(providedId)) {
            handleUsersOwnChanges(authentication, request);
        } else {
            handleMemberAccountChanges(authentication, request);
        }
    }

    /**
     * Change account Name is only allowed by administrators of the system.
     * Hence, if the administrator is allowed to perform the action, they may
     * perform this on all accounts in the system.<br />
     *   Note, an exception is added to this logic, so owners and moderators
     * also can update their own members. If the given GroupType is then a
     * Member, then they may update the account, if it belongs to the same
     * member group
     *
     * @param authentication User & Group information
     * @param request        Account Request information
     */
    public void changeAccountName(final Authentication authentication, final AccountNameRequest request) {
        if ((request.getLastname() == null) && (request.getFirstname() == null)) {
            throw new VerificationException("Cannot update the Account Name for the user, as both the first and last names are missing.");
        }

        final GroupType type = authentication.getGroup().getGroupType().getGrouptype();
        final String externalId = request.getUser().getUserId();

        if (type == GroupType.MEMBER) {
            final UserGroupEntity userGroup = dao.findByGroupAndExternalUserId(authentication.getGroup(), externalId);
            if (userGroup != null) {
                updateUserAccountName(authentication, userGroup.getUser(), request);
            }
        } else if (type == GroupType.ADMINISTRATION) {
            final UserEntity user = dao.findUserByExternalId(externalId);
            updateUserAccountName(authentication, user, request);
        } else {
            throw new VerificationException("It is not allowed to perform an AccountName change with Groups of type " + type + '.');
        }
    }

    private void updateUserAccountName(final Authentication authentication, final UserEntity user, final AccountNameRequest request) {
        if (request.getLastname() != null) {
            user.setLastname(request.getLastname());
        } else {
            user.setFirstname(request.getFirstname());
        }

        dao.persist(authentication, user);
    }

    /**
     * Fetches a User Object. The result from the request depends on the person
     * who made the request, and the permissions. Please see the API description
     * for more details.
     *
     * @param authentication User & Group information
     * @param request        User Request information
     * @return User Object
     */
    public FetchUserResponse fetchUser(final Authentication authentication, final FetchUserRequest request) {
        final String externalId = authentication.getUser().getExternalId();
        final String userId = request.getUserId();
        User user = null;

        if (userId.equals(externalId)) {
            // The user itself
            final UserGroupEntity entity = dao.findMemberByExternalId(externalId);
            user = transform(entity).getUser();
        } else {
            // First, we make an Authorization Check. If it fails, an
            // AuthorizationException is thrown
            final UserEntity administrator = authentication.getUser();
            dao.findGroupByPermission(administrator, authentication.getToken().getGroupId(), Permission.FETCH_USER);

            // Find the administrators MemberGroup, we need it for the lookup
            final GroupEntity member = dao.findMemberGroup(administrator);
            final UserGroupEntity entity = dao.findMemberByExternalId(userId, member);
            if (entity != null) {
                user = transform(entity).getUser();

                // We're in the Group Context, where the Privacy flag applies,
                // meaning that if a user has set this, then the user's private
                // or personal data may not be displayed
                if (user.getPrivacy() == Privacy.PRIVATE) {
                    user.setPerson(null);
                }
            }
        }

        return new FetchUserResponse(user);
    }

    public FetchRoleResponse fetchRoles(final Authentication authentication) {
        final List<PermissionRoleEntity> entities = dao.findRoles(authentication.getGroup());
        final Map<Long, Set<Permission>> permissions = readPermissionMap(entities);
        final List<Role> roles = new ArrayList<>(10);

        for (final PermissionRoleEntity entity : entities) {
            final Role role = transform(entity.getRole());
            if (!roles.contains(role)) {
                role.setPermissions(permissions.get(entity.getRole().getId()));
                roles.add(role);
            }
        }

        return new FetchRoleResponse(roles);
    }

    private static Map<Long, Set<Permission>> readPermissionMap(final List<PermissionRoleEntity> entities) {
        final Map<Long, Set<Permission>> permissions = new HashMap<>(16);

        for (final PermissionRoleEntity entity : entities) {
            final Long id = entity.getRole().getId();
            if (!permissions.containsKey(id)) {
                permissions.put(id, EnumSet.noneOf(Permission.class));
            }
            permissions.get(id).add(entity.getPermission().getPermission());
        }

        return permissions;
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    /**
     * This method handles a users own changes. Meaning, that if a user comes in
     * and wishes to modify something, then this method will handle all aspects
     * thereof. The type of changes include:
     * <ul>
     *   <li>Update Username</li>
     *   <li>Delete self</li>
     *   <li>Update Personal information</li>
     * </ul>
     *
     * @param authentication User that is invoking the request
     * @param request        User Request Object, with Account changes
     */
    private void handleUsersOwnChanges(final Authentication authentication, final UserRequest request) {
        final UserStatus newStatus = request.getNewStatus();
        final String newUsername = request.getNewUsername();

        if (newUsername != null) {
            final String hash = hashcodeGenerator.generateHash(toLower(request.getPassword()), authentication.getUser().getSalt());
            if (hash.equals(authentication.getUser().getPassword())) {
                prepareUsernameUpdate(authentication.getUser(), newUsername);
            } else {
                throw new IWSException(IWSErrors.AUTHENTICATION_ERROR, "The update username request cannot be completed.");
            }
        } else if (newStatus == UserStatus.DELETED) {
            deletePrivateData(authentication, authentication.getUser());
        } else {
            updatePrivacyAndData(authentication, request);
        }
    }

    /**
     * Handles changes to member account - meaning status changes only. If the
     * account belongs to the Owner of the Group, then no changes may be made.
     * Otherwise, it is possible to activate/deactivate an account and delete
     * it.
     *
     * @param authentication User that is invoking the request
     * @param request        The data Object to read the changes from
     */
    private void handleMemberAccountChanges(final Authentication authentication, final UserRequest request) {
        final GroupEntity group = dao.findMemberGroup(authentication.getUser());
        final RoleEntity role = dao.findRoleByUserAndGroup(request.getUser().getUserId(), group);

        // First, we need to verify if the user may access. The DAO method
        // throws an Exception, if the user is not allowed
        dao.findGroupByPermission(authentication.getUser(), null, Permission.CONTROL_USER_ACCOUNT);

        if (role != null) {
            if (!role.getId().equals(InternalConstants.ROLE_OWNER)) {
                final UserEntity user = dao.findUserByExternalId(request.getUser().getUserId());
                final String username = request.getNewUsername();

                // Okay, we have a ball game - let's update the record with the
                // demanded changes. Note, we only allow that someone can perform
                // either if two operations: Update Username or Status
                if (username != null) {
                    prepareUsernameUpdate(user, username);
                } else if (request.getNewStatus() != null) {
                    updateUserStatus(authentication, user, request.getNewStatus());
                }
            }
        } else {
            throw new IdentificationException("Could not find the user to process[" + request.getUser() + "].");
        }
    }

    private void updateUserStatus(final Authentication authentication, final UserEntity user, final UserStatus newStatus) {
        final UserStatus current = user.getStatus();

        // Users who have status deleted cannot be fetched, so we need to check
        // a few other parameters. First accounts with status Active or Blocked
        if (current != UserStatus.NEW) {
            switch (newStatus) {
                case NEW:
                    throw new IWSException(IWSErrors.PROCESSING_FAILURE, "Illegal User state change.");
                case ACTIVE:
                case SUSPENDED:
                    checkAndUpdateStatusForSuspendedUser(authentication, user, current, newStatus);
                    break;
                case DELETED:
                    deletePrivateData(authentication, user);
                    LOG.info("Deleting private data from {}.", user);
                    break;
                default:
                    // Although all cases have been covered, if we ever add
                    // more, then it should lead to an error.
                    throw new IWSException(IWSErrors.ILLEGAL_ACTION, "Unsupported State Change.");
            }
        } else if (newStatus == UserStatus.DELETED) {
            deleteNewUser(user);
            LOG.info("Deleting new Account for {}.", user);
        }
    }

    private void checkAndUpdateStatusForSuspendedUser(final Authentication authentication, final UserEntity user, final UserStatus current, final UserStatus newStatus) {
        if (current != UserStatus.DELETED) {
            user.setStatus(newStatus);
            user.setModified(new Date());
            dao.persist(authentication, user);
            notifications.notify(authentication, user, findNotificationType(newStatus));
            LOG.info("Changed status for {} to {}.", user, newStatus);
        } else {
            throw new IWSException(IWSErrors.PROCESSING_FAILURE, "Cannot revive a deleted user, please create a new Account.");
        }
    }

    /**
     * Although the same functionality is present in the updateUserStatus()
     * method, there's a problem with invoking the Notification for Suspending
     * Users via the Cron job. The purpose of the notification is to remove the
     * User's e-mail Aliases and access to Mailing lists. However, it is the
     * long term goal to have all this functionality moved to Database Views,
     * so changes in the Users Status will immediately be reflected in the Mail
     * Access.<br />
     *   This means that we're omitting the removal of Suspended Users access
     * to Aliases & Mailing lists for now.
     *
     * @param authentication User that is invoking the request
     * @param user           The User to Suspend from the system
     */
    public void suspendUser(final Authentication authentication, final UserEntity user) {
        user.setStatus(UserStatus.SUSPENDED);
        user.setModified(new Date());
        dao.persist(authentication, user);
    }

    /**
     * Deleting Accounts with status NEW can be done either via the normal API
     * methods, or it can be done via the Cron jobs running. However, to ensure
     * that the same logic is applied to both - this method is public, so it
     * can be used by both.<br />
     *   The method will check that the current Status is NEW, and if so - it
     * will remove the data for the user completely.
     *
     * @param user UserEntity to delete
     */
    public void deleteNewUser(final UserEntity user) {
        if (user.getStatus() == UserStatus.NEW) {
            // We have a User Account, that was never activated. This we can
            // delete completely
            if (!dao.findStudentWithApplications(user).isEmpty()) {
                LOG.info("Expired Account to be deleted is a Student Account with Applications. Only private information is removed.");
                deleteAccountData(user);
            } else {
                deletePerson(user.getPerson());
                // We'll just remove the Student Account, if added - since we
                // have already checked that no data is associated with it
                dao.deleteStudent(user);
                dao.deletePrivateGroup(user);
                dao.delete(user);
            }
        } else {
            throw new IWSException(IWSErrors.PROCESSING_FAILURE, "Illegal User state change.");
        }
    }

    private static NotificationType findNotificationType(final UserStatus status) {
        final NotificationType type;

        switch (status) {
            case NEW:
                type = NotificationType.NEW_USER;
                break;
            case ACTIVE:
                type = NotificationType.ACTIVATE_SUSPENDED_USER;
                break;
            case SUSPENDED:
                type = NotificationType.SUSPEND_ACTIVE_USER;
                break;
            default:
                type = NotificationType.GENERAL;
        }

        return type;
    }

    /**
     * Deletes the private data for a user. The data has to be removed, to avoid
     * breaking any privacy laws. Only thing remaining of the User Account, will
     * be the User Object with the name.<br />
     *   Note, that the method will perform a check against ownership of the
     * Members group, since the current Owner may not be deleted, as it will
     * prevent others from taking over ownership of this Group.
     *
     * @param authentication User that is invoking the request
     */
    public void deletePrivateData(final Authentication authentication, final UserEntity user) {
        final List<UserGroupEntity> groupRelations = findGroupRelationsForDeletion(user);

        // First, delete the Sessions, they are linked to the User account, and
        // not the users private Group
        final int deletedSessions = dao.deleteSessions(user);

        // As the Person Object is an embedded Object, we cannot just delete it
        // directly, we have to delete the references to it first, which is our
        // User
        final PersonEntity person = user.getPerson();

        // Secondly, delete all data associated with the user, meaning the users
        // private Group
        final GroupEntity group = dao.findPrivateGroup(user);
        dao.delete(group);

        if (user.getStatus() == UserStatus.NEW) {
            // If the User has status "new", then there is no data associated
            // with the account, and we can thus completely remove it from the
            // system
            dao.delete(user);
            deletePerson(person);

            LOG.info(formatLogMessage(authentication, "Deleted the new user %s completely.", user));
        } else {
            deleteAccountData(user);
            for (final UserGroupEntity userGroup : groupRelations) {
                notifications.notify(authentication, userGroup, NotificationType.CHANGE_IN_GROUP_MEMBERS);
            }

            LOG.info(formatLogMessage(authentication, "Deleted all private data for user %s, including %d sessions.", user, deletedSessions));
        }
    }

    private void deleteAccountData(final UserEntity user) {
        deletePerson(user.getPerson());

        // Now, remove and System specific data from the Account, and set the
        // Status to deleted, thus preventing the account from being used
        // anymore
        user.setCode(null);
        // We remove the Username from the account as well, since it may
        // otherwise block if the user later on create a new Account. A
        // deleted account should remaining deleted - and we do not wish to
        // drop the Unique Constraint in the database.
        user.setUsername(UUID.randomUUID() + "@deleted.now");
        user.setPassword(null);
        user.setSalt(null);
        user.setPerson(null);
        // Although it can be argued that we shouldn't delete aliases, then it
        // is very rare that two users with the same name comes along. And we've
        // seen many cases where it is the same user being registered a second
        // or third time. So we're changing the policy here and deleting Aliases
        // when an Account is deleted.
        user.setAlias(null);
        user.setStatus(UserStatus.DELETED);
        dao.persist(user);
    }

    /**
     * Performs a check, to see if the user may be deleted or not. If the user
     * is currently the owner of one of the following GroupTypes, then it is not
     * permissible to delete it:
     * <ul>
     *   <li>Administration</li>
     *   <li>International</li>
     *   <li>Regional</li>
     *   <li>National</li>
     *   <li>Member</li>
     * </ul>
     *
     * @param user User to check if may be deleted
     * @return List of UserGroup relations
     */
    private List<UserGroupEntity> findGroupRelationsForDeletion(final UserEntity user) {
        final List<UserGroupEntity> userGroups = dao.findAllUserGroups(user);

        for (final UserGroupEntity entity : userGroups) {
            final GroupType type = entity.getGroup().getGroupType().getGrouptype();
            switch (type) {
                case ADMINISTRATION:
                case INTERNATIONAL:
                case NATIONAL:
                case MEMBER:
                    if ((entity.getRole().getId() == InternalConstants.ROLE_OWNER) && (entity.getGroup().getStatus() == GroupStatus.ACTIVE)) {
                        throw new IWSException(IWSErrors.PROCESSING_FAILURE, "Users who are currently the Owner of a Group with type '" + type.getDescription() + "', cannot be deleted.");
                    }
                    break;
                default:
                    // We allow that Users who are currently owner of any type
                    // to be deleted.
            }
        }

        return userGroups;
    }

    private void updatePrivacyAndData(final Authentication authentication, final UserRequest request) {
        final UserEntity user = authentication.getUser();
        user.setPrivateData(request.getUser().getPrivacy());
        user.setNotifications(request.getUser().getNotifications());

        final PersonEntity person = processPerson(authentication, user.getPerson(), request.getUser().getPerson());
        user.setPerson(person);

        dao.persist(user);
    }

    private void prepareUsernameUpdate(final UserEntity user, final String username) {
        final Authentication authentication = new Authentication(user, UUID.randomUUID().toString());

        // Set a new code for the user to reply with, and set the new username
        user.setCode(hashcodeGenerator.generateHash(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        user.setData(username);
        dao.persist(user);

        // Send notification
        notifications.notify(authentication, user, NotificationType.UPDATE_USERNAME);
    }
}
