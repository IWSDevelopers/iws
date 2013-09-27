/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.AccountService
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

import static net.iaeste.iws.common.utils.HashcodeGenerator.generateHash;
import static net.iaeste.iws.core.transformers.AdministrationTransformer.transform;
import static net.iaeste.iws.core.transformers.AdministrationTransformer.transformRoleEntities;
import static net.iaeste.iws.core.util.LogUtil.formatLogMessage;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.common.utils.PasswordGenerator;
import net.iaeste.iws.common.utils.StringUtils;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class AccountService extends CommonService<AccessDao> {

    private static final Logger log = Logger.getLogger(AccountService.class);
    private final Notifications notifications;

    public AccountService(final AccessDao dao, final Notifications notifications) {
        super(dao);

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
        final CreateUserResponse result;

        // To avoid problems, all internal handling of the username is in lowercase
        final String username = request.getUsername().toLowerCase(IWSConstants.DEFAULT_LOCALE);
        if (dao.findUserByUsername(username) == null) {
            final UserEntity user;
            if (request.isStudent()) {
                final GroupEntity studentGroup = dao.findStudentGroup(authentication.getGroup());
                if (studentGroup != null) {
                    // The data model needs to be extended with Student Role,
                    // Student Group & Permissions
                    user = createAndPersistUserEntity(authentication, username, request);
                    final RoleEntity student = dao.findRoleById(IWSConstants.ROLE_STUDENT);

                    addUserToGroup(user, authentication.getGroup(), student);
                    addUserToGroup(user, studentGroup, student);
                } else {
                    throw new IWSException(IWSErrors.FATAL, "No StudentGroup exists, which can be used.");
                }
            } else {
                final RoleEntity owner = dao.findRoleById(IWSConstants.ROLE_OWNER);
                final RoleEntity member = dao.findRoleById(IWSConstants.ROLE_MEMBER);

                user = createAndPersistUserEntity(authentication, username, request);
                final GroupEntity privateGroup = createAndPersistPrivateGroup(user);
                final UserGroupEntity privateUserGroup = new UserGroupEntity(user, privateGroup, owner);
                dao.persist(privateUserGroup);
                addUserToGroup(user, authentication.getGroup(), member);

                notifications.notify(authentication, user, NotificationType.NEW_USER);
                notifications.notify(authentication, user, NotificationType.PROCESS_EMAIL_ALIAS);
            }

            notifications.notify(authentication, user, NotificationType.ACTIVATE_USER);
            result = new CreateUserResponse(transform(user));
        } else {
            result = new CreateUserResponse(IWSErrors.USER_ACCOUNT_EXISTS, "An account for the user with username " + username + " already exists.");
        }

        return result;
    }

    private void addUserToGroup(final UserEntity user, final GroupEntity group, final RoleEntity role) {
        final UserGroupEntity userGroup = new UserGroupEntity(user, group, role);
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
     * @param activationString Hashvalue of the activation String
     */
    public void activateUser(final String activationString) {
        final UserEntity user = dao.findUserByCodeAndStatus(activationString, UserStatus.NEW);

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
        final UserEntity user = dao.findUserByCodeAndStatus(updateCode, UserStatus.ACTIVE);

        if (user != null) {
            // Update the UserEntity with the new Username
            user.setUsername(user.getData());
            user.setCode(null);
            user.setData(null);
            user.setModified(new Date());
            dao.persist(user);
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
        final User user;

        if (userId.equals(externalId)) {
            // The user itself
            final UserGroupEntity entity = dao.findMemberByExternalId(externalId);
            user = transform(entity).getUser();
        } else {
            // First, we make an Authorization Check. If it fails, an
            // AuthorizationException is thrown
            final UserEntity administrator = authentication.getUser();
            dao.findGroupByPermission(administrator, null, Permission.FETCH_USERS);

            // Find the administrators MemberGroup, we need it for the lookup
            final GroupEntity member = dao.findMemberGroup(administrator);
            final UserGroupEntity entity = dao.findMemberByExternalId(externalId, member);
            if (entity != null) {
                user = transform(entity).getUser();

                // We're in the Group Context, where the Privacy flag applies,
                // meaning that if a user has set this, then the user's private
                // or personal data may not be displayed
                if (user.getPrivacy() == Privacy.PRIVATE) {
                    user.setNotifications(null);
                    user.setPerson(null);
                }
            } else {
                user = null;
            }
        }

        return new FetchUserResponse(user);
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    /**
     * Creates and persists a new UserEntity in the database. The Entity is
     * based on the User Credentials, that is stored in the Request Object, with
     * the exception of the Username, that due to a pre-processing, is provided
     * as a parameter.<br />
     *   The creation process will run some checks, and also generate some
     * information by default. First, the user alias will be generated, if no
     * alias can be generated (user provided information was not unique enough),
     * then the create processs will fail.<br />
     *   If no password was provided, then a random password is generated and
     * returned to the user in the activation e-mail. Regardless, a salt is
     * generated and used together with the password to create a cryptographical
     * hashvalue that is then stored. The Salt is also stored in the database
     * for verification when the user attempts to login.<br />
     *   Finally, an Activation Code is generated, this is required for the user
     * to activate the account, if an account is not activated, then it cannot
     * be used.<br />
     *   If no errors occurred during the creation, the new {@code UserEntity}
     * is returned, otherwise an {@code IWSException} is thrown.
     *
     * @param authentication Authentication information from the requesting user
     * @param username       Pre-processed username
     * @param request        Request Object with remaining user information
     * @return Newly created {@code UserEntity} Object
     * @throws IWSException if unable to create the user
     */
    private UserEntity createAndPersistUserEntity(final Authentication authentication, final String username, final CreateUserRequest request) throws IWSException {
        final String alias = generateUserAlias(request);
        final UserEntity user = new UserEntity();

        // First, the Password. If no password is specified, then we'll generate
        // one. Regardlessly, the password is set in the UserEntity, for the
        // Notification
        final String password;
        if (request.getPassword() == null) {
            password = PasswordGenerator.generatePassword();
        } else {
            password = request.getPassword().toLowerCase(IWSConstants.DEFAULT_LOCALE);
        }

        // As we doubt that a user will provide enough entropy to enable us to
        // generate a hash value that cannot be looked up in rainbow tables,
        // we're "salting" it, and additionally storing the the random part of
        // the salt in the Entity as well, the hardcoded part of the Salt is
        // stored in the Hashcode Generator
        final String salt = UUID.randomUUID().toString();

        // Now, set all the information about the user and persist the Account
        user.setUsername(username);
        user.setTemporary(password);
        user.setPassword(generateHash(password, salt));
        user.setSalt(salt);
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setAlias(alias);
        user.setCode(generateActivationCode(request));
        dao.persist(authentication, user);

        return user;
    }

    private String generateUserAlias(final CreateUserRequest request) throws IWSException {
        final String name = StringUtils.convertToAsciiMailAlias(request.getFirstname() + '.' + request.getLastname());
        final String address = '@' + IWSConstants.PUBLIC_EMAIL_ADDRESS;
        final String alias;

        final Long serialNumber = dao.findNumberOfAliasesForName(name);
        if ((serialNumber != null) && (serialNumber > 0)) {
            alias = name + (serialNumber + 1) + address;
        } else {
            alias = name + address;
        }

        return alias;
    }

    private static String generateActivationCode(final CreateUserRequest request) {
        final String clear = request.getUsername()
                           + request.getFirstname()
                           + request.getLastname()
                           + UUID.randomUUID().toString();

        return generateHash(clear);
    }

    private GroupEntity createAndPersistPrivateGroup(final UserEntity user) {
        final GroupEntity group = new GroupEntity();

        group.setGroupName(user.getFirstname() + ' ' + user.getLastname());
        group.setGroupType(dao.findGroupType(GroupType.PRIVATE));
        dao.persist(group);

        return group;
    }

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
        final String username = request.getNewUsername();

        if (username != null) {
            final String hash = generateHash(request.getPassword(), authentication.getUser().getSalt());
            if (hash.equals(authentication.getUser().getPassword())) {
                prepareUsernameUpdate(authentication.getUser(), username);
            } else {
                throw new IWSException(IWSErrors.AUTHENTICATION_ERROR, "The initiate update password request cannot be completed.");
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

        if (!role.getId().equals(IWSConstants.ROLE_OWNER)) {
            final UserEntity user = dao.findUserByExternalId(request.getUser().getUserId());
            final String username = request.getNewUsername();

            // Okay, we have a ball game - let's update the record with the
            // demanded changes
            if (username != null) {
                prepareUsernameUpdate(user, username);
            } else if (request.getNewStatus() != null) {
                updateUserStatus(authentication, user, request.getNewStatus());
            } else {
                // Update User account with the new Status and save it
                user.setStatus(request.getUser().getStatus());
                dao.persist(user);
            }
        }
    }

    private void updateUserStatus(final Authentication authentication, final UserEntity user, final UserStatus newStatus) {
        final UserStatus current = user.getStatus();

        // Users who have status deleted cannot be fetched, so we need to check
        // a few other parameters. First accounts with status Active or Blocked
        if (current != UserStatus.NEW) {
            switch (newStatus) {
                case ACTIVE:
                case BLOCKED:
                    user.setStatus(newStatus);
                    user.setModified(new Date());
                    dao.persist(authentication, user);
                    break;
                case DELETED:
                    deletePrivateData(authentication, user);
                    break;
                default:
                    throw new IWSException(IWSErrors.PROCESSING_FAILURE, "Illegal User state change.");
            }
        } else if (newStatus == UserStatus.DELETED) {
            // We have a User Account, that was never activated. This we can
            // delete completely
            dao.delete(user);
        }
    }

    /**
     * Deletes the private data for a user. The data has to be removed, to avoid
     * breaking any privacy laws. Only thing remaining of the User Account, will
     * be the
     *
     * @param authentication User that is invoking the request
     */
    private void deletePrivateData(final Authentication authentication, final UserEntity user) {
        // First, delete the Sessions, they are linked to the User account, and
        // not the users private Group
        final int deletedSessions = dao.deleteSessions(user);

        // Secondly, delete all data associated with the user, meaning the users
        // private Group
        final GroupEntity group = dao.findPrivateGroup(user);
        dao.delete(group);

        if (user.getStatus() == UserStatus.NEW) {
            // If the User has status "new", then there is no data associated
            // with the account, and we can thus completely remove it from the
            // system
            dao.delete(user);

            log.info(formatLogMessage(authentication, "Deleted the new user %s completely.", user));
        } else {
            // Now, remove and System specific data from the Account, and set the
            // Status to deleted, thus preventing the account from being used
            // anymore
            user.setCode(null);
            user.setPrivateData(Privacy.PRIVATE);
            user.setStatus(UserStatus.DELETED);
            dao.persist(user);

            final List<UserGroupEntity> userGroups = dao.findAllUserGroups(user);
            for (final UserGroupEntity userGroup : userGroups) {
                notifications.notify(authentication, userGroup, NotificationType.CHANGE_IN_GROUP_MEMBERS);
            }

            log.info(formatLogMessage(authentication, "Deleted all private data for user %s, including %d sessions.", user, deletedSessions));
        }
    }

    private void updatePrivacyAndData(final Authentication authentication, final UserRequest request) {
        // TODO Make the UserEntity mergeable!
        authentication.getUser().setPrivateData(request.getUser().getPrivacy());
        authentication.getUser().setNotifications(request.getUser().getNotifications());

        dao.persist(authentication.getUser());
    }

    private void prepareUsernameUpdate(final UserEntity user, final String username) {
        final Authentication authentication = new Authentication(user, UUID.randomUUID().toString());

        // Set a new code for the user to reply with, and set the new username
        user.setCode(generateHash(UUID.randomUUID().toString()));
        user.setData(username);
        dao.persist(user);

        // Send notification
        notifications.notify(authentication, user, NotificationType.UPDATE_USERNAME);
    }

    public FetchRoleResponse fetchRoles(final Authentication authentication) {
        final List<RoleEntity> roles = dao.findRoles(authentication.getGroup());
        final FetchRoleResponse response = new FetchRoleResponse(transformRoleEntities(roles));

        return response;
    }
}
