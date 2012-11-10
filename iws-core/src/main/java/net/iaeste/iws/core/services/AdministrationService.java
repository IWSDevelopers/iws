/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.ManageUserAccountRequest;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.responses.CountryResponse;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.api.responses.GroupResponse;
import net.iaeste.iws.api.responses.UserResponse;
import net.iaeste.iws.common.utils.HashcodeGenerator;
import net.iaeste.iws.common.utils.PasswordGenerator;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.notification.Notifications;

import java.util.Date;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class AdministrationService {

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

        group.setGroupname(user.getFirstname() + ' ' + user.getLastname());
        group.setGroupType(dao.findGroupType(GroupType.PRIVATE));
        group.setExternalId(UUID.randomUUID().toString());
        dao.persist(group);

        return group;
    }

    private String generateActivationCode(final CreateUserRequest request) {
        final String clear = request.getUsername()
                           + request.getFirstname()
                           + request.getLastname()
                           + UUID.randomUUID().toString();

        return HashcodeGenerator.generateSHA512(clear);
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

    public void processUsers(final Authentication authentication, final ManageUserAccountRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public UserResponse fetchUsers(final Authentication authentication, final FetchUserRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public void processGroups(final Authentication authentication, final GroupRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public GroupResponse fetchGroups(final Authentication authentication, final FetchGroupRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public void processCountries(final Authentication authentication, final CountryRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public CountryResponse fetchCountries(final Authentication authentication, final FetchCountryRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public void processUserGroupAssignment(final Authentication authentication, final UserGroupAssignmentRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }
}
