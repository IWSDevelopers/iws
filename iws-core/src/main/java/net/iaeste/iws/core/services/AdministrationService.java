/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.AdministrationService
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.CountryResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.responses.UserResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.common.utils.HashcodeGenerator;
import net.iaeste.iws.common.utils.PasswordGenerator;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.notification.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

import static net.iaeste.iws.core.transformers.AdministrationTransformer.transform;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class AdministrationService {

    private static final Logger LOG = LoggerFactory.getLogger(AdministrationService.class);
    private final AccessDao dao;
    private final Notifications notifications;

    public AdministrationService(final AccessDao dao, final Notifications notifications) {
        this.dao = dao;
        this.notifications = notifications;
    }

    /**
     * Create a new User Account in the IWS. The user is automatically assigned
     * to the same Members Group, as the person invoking the request.
     *
     * @param authentication User & Group information
     * @param request        User Creation Request
     * @return Error information
     */
    public Fallible createUser(final Authentication authentication, final CreateUserRequest request) {
        final Fallible result;

        // To avoid problems, all internal handling of the username is in lowercase
        final String username = request.getUsername().toLowerCase(IWSConstants.DEFAULT_LOCALE);
        if (dao.findUserByUsername(username) == null) {
            final UserEntity user = createAndPersistUserEntity(username, request);
            final GroupEntity privateGroup = createAndPersistPrivateGroup(user);

            final RoleEntity owner = dao.findRoleById(IWSConstants.ROLE_OWNER);
            final UserGroupEntity privateUserGroup = new UserGroupEntity(user, privateGroup, owner);
            dao.persist(privateUserGroup);

            final GroupEntity group = authentication.getGroup();
            final RoleEntity member = dao.findRoleById(IWSConstants.ROLE_MEMBER);
            final UserGroupEntity userGroup = new UserGroupEntity(user, group, member);
            dao.persist(userGroup);

            notifications.notify(authentication, user);
            result = new UserResponse();
        } else {
            result = new UserResponse(IWSErrors.USER_ACCOUNT_EXISTS, "An account for the user with username " + username + " already exists.");
        }

        return result;
    }

    /**
     * Users cannot access the IWS, until their account has been activated, this
     * happens via an e-mail that is sent to their e-mail address (username),
     * with an activation link.<br />
     *   Once activation link is activated, this method should be invoked, which
     * will handle the actual activation process. Meaning, that if an account is
     * found in status "new", and with the given activation code, then it is
     * being updated to status "active", the code is removed and the updates are
     * saved.<br />
     *   If everything went well, then
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
        final UserEntity user = authentication.getUser();

        // Check if this is a personal request or not
        if (externalId.equals(providedId)) {
            if (user.getStatus() == UserStatus.DELETED) {
                deletePrivateData(user);
            } else {
                updatePrivacyAndData(user, request);
            }
        } else {
            handleMemberAccountChanges(user, request);
        }
    }

    public FetchUserResponse fetchUser(final Authentication authentication, final FetchUserRequest request) {
        final String externalId = authentication.getUser().getExternalId();
        final String userId = request.getUserId();
        final User user;

        if (userId.equals(externalId)) {
            // The user itself
            final UserEntity entity = dao.findUserByExternalId(externalId);
            user = transform(entity);
        } else {
            // First, we make an Authorization Check. If it fails, an
            // AuthorizationException is thrown
            final UserEntity administrator = authentication.getUser();
            dao.findGroupByPermission(administrator, null, Permission.FETCH_USERS);

            // Find the administrators MemberGroup, we need it for the lookup
            final GroupEntity member = dao.findMemberGroup(administrator);
            final UserGroupEntity entity = dao.findMemberByExternalId(externalId, member);
            if (entity != null) {
                user = transform(entity.getUser());

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

    /**
     * This method can handle two types if requests:
     * <ul>
     *     <li>Create new subgroup</li>
     *     <li>Update current group</li>
     * </ul>
     * If the given Group from the GroupRequest contains an Id, then it is
     * assumed that this group should be updated, otherwise it is an attempt at
     * creating a new Group.
     *
     * @param authentication User & Group information
     * @param request        Group Request information
     */
    public void processGroup(final Authentication authentication, final GroupRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public FetchGroupResponse fetchGroup(final Authentication authentication, final FetchGroupRequest request) {
        final GroupEntity entity = dao.findGroup(authentication.getUser(), request.getGroupId());

        final Group group;
        if (entity != null) {
            group = new Group();

            group.setGroupId(entity.getExternalId());
            group.setGroupName(entity.getGroupName());
            group.setGroupType(entity.getGroupType().getGrouptype());
            group.setDescription(entity.getDescription());
            group.setCountryId(entity.getCountry().getCountryId());
        } else {
            group = null;
        }

        return new FetchGroupResponse(group);
    }

    public void processUserGroupAssignment(final Authentication authentication, final UserGroupAssignmentRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public void processCountries(final Authentication authentication, final CountryRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public CountryResponse fetchCountries(final Authentication authentication, final FetchCountryRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private UserEntity createAndPersistUserEntity(final String username, final CreateUserRequest request) {
        final UserEntity user = new UserEntity();

        // First, the Password. If no password is specified, then we'll generate
        // one. Regardlessly, the password is set in the UserEntity, for the
        // Notification
        final String password;
        if (request.getPassword() == null) {
            password = PasswordGenerator.generatePassword();
        } else {
            password = request.getPassword();
        }

        // To avoid misusage all Users have a unique external Id
        user.setExternalId(UUID.randomUUID().toString());

        // Now, set all the information about the user and persist the Account
        user.setUserName(username);
        user.setTemporary(password);
        user.setPassword(HashcodeGenerator.generateSHA256(password));
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setCode(generateActivationCode(request));
        dao.persist(user);

        return user;
    }

    private GroupEntity createAndPersistPrivateGroup(final UserEntity user) {
        final GroupEntity group = new GroupEntity();

        group.setGroupName(user.getFirstname() + ' ' + user.getLastname());
        group.setGroupType(dao.findGroupType(GroupType.PRIVATE));
        group.setExternalId(UUID.randomUUID().toString());
        dao.persist(group);

        return group;
    }

    private static String generateActivationCode(final CreateUserRequest request) {
        final String clear = request.getUsername()
                           + request.getFirstname()
                           + request.getLastname()
                           + UUID.randomUUID().toString();

        return HashcodeGenerator.generateSHA512(clear);
    }

    /**
     * Handles changes to member account - meaning status changes only. If the
     * account belongs to the Owner of the Group, then no changes may be made.
     * Otherwise, it is possible to activate/deactivate an account and delete
     * it.<br />
     *   ToDo: Add state machine for the status changes
     *
     * @param administrator User that is invoking the request
     * @param request       The data Object to read the changes from
     */
    private void handleMemberAccountChanges(final UserEntity administrator, final UserRequest request) {
        final GroupEntity group = dao.findMemberGroup(administrator);
        final RoleEntity role = dao.findRoleByUserAndGrouo(request.getUser().getUserId(), group);

        // First, we need to verify if the user may access. The DAO method
        // throws an Exception, if the user is not allowed
        dao.findGroupByPermission(administrator, null, Permission.CONTROL_USER_ACCOUNT);

        if (!role.getId().equals(IWSConstants.ROLE_OWNER)) {
            final UserEntity user = dao.findUserByExternalId(request.getUser().getUserId());
            // Okay, we have a ball game - let's update the record with the
            // demanded changes
            if (request.getUser().getStatus() == UserStatus.DELETED) {
                deletePrivateData(user);
            } else {
                // Update User account with the new Status and save it
                user.setStatus(request.getUser().getStatus());
                dao.persist(user);
            }
        }
    }

    /**
     * Deletes the private data for a user. The data has to be removed, to avoid
     * breaking any privacy laws. Only thing remaining of the User Account, will
     * be the
     *
     * @param user User Entity to delete the private data of
     */
    private void deletePrivateData(final UserEntity user) {
        // First, delete the Sessions, they are linked to the User account, and
        // not the users private Group
        final int deletedSessions = dao.deleteSessions(user);

        // Secondly, delete all data associated with the user, meaning the users
        // private Group
        final GroupEntity group = dao.findPrivateGroup(user);
        dao.delete(group);

        // Now, remove and System specific data from the Account, and set the
        // Status to deleted, thus preventing the account from being used
        // anymore
        user.setCode(null);
        user.setPrivateData(Privacy.PRIVATE);
        user.setStatus(UserStatus.DELETED);
        dao.persist(user);

        LOG.info("Deleted all private data for user {}, including {} sessions,", user, deletedSessions);
    }

    private void updatePrivacyAndData(final UserEntity user, final UserRequest request) {
        // TODO Make the UserEntity mergeable!
        user.setPrivateData(request.getUser().getPrivacy());
        user.setNotifications(request.getUser().getNotifications());

        dao.persist(user);
    }
}
