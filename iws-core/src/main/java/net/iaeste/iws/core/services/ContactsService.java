/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.ContactsService
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

import static net.iaeste.iws.core.transformers.CommonTransformer.transform;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Person;
import net.iaeste.iws.api.dtos.Role;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.enums.ContactsType;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.ContactsRequest;
import net.iaeste.iws.api.requests.SearchUserRequest;
import net.iaeste.iws.api.responses.ContactsResponse;
import net.iaeste.iws.api.responses.EmergencyListResponse;
import net.iaeste.iws.api.responses.SearchUserResponse;
import net.iaeste.iws.core.transformers.AdministrationTransformer;
import net.iaeste.iws.persistence.AdminDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ContactsService {

    private static final Pattern SPACE_PATTERN = Pattern.compile("/ +/");
    private static final int MAX_DEPTH_CONTACT_GROUPS = 3;

    private final AdminDao dao;

    public ContactsService(final AdminDao dao) {
        this.dao = dao;
    }

    public SearchUserResponse searchUsers(final SearchUserRequest request) {
        final String[] names = SPACE_PATTERN.split(request.getName());
        final String firstname;
        final String lastname;

        if (names.length == 1) {
            firstname = names[0];
            lastname = names[0];
        } else if (names.length >= 2) {
            firstname = names[0];
            lastname = names[1];
        } else {
            throw new IWSException(IWSErrors.ERROR, "Cannot determine search criterias.");
        }

        final List<UserGroupEntity> entities;
        final String externalGroupId = request.getGroup().getGroupId();
        if (externalGroupId == null) {
            entities = dao.searchUsers(firstname, lastname);
        } else {
            entities = dao.searchUsers(firstname, lastname, externalGroupId);
        }

        final List<UserGroup> result = new ArrayList<>(entities.size());

        for (final UserGroupEntity entity : entities) {
            final User user = AdministrationTransformer.transform(entity.getUser());
            // No private information is needed for this request
            user.setPerson(null);

            final UserGroup userGroup = new UserGroup();
            userGroup.setUser(user);
            userGroup.setGroup(transform(entity.getGroup()));
            userGroup.setRole(AdministrationTransformer.transform(entity.getRole()));
            result.add(userGroup);
        }

        return new SearchUserResponse(result);
    }

    /**
     * Retrieves the IAESTE Emergency Contact List, which is the list that is
     * used by administrators, if an emergency arises and they need to quickly
     * get in touch with someone from a specific country.<br />
     *   Note, that the emergency list is only for administrators, and it is
     * also only administrators who can see it and appear on it. For this reason
     * no information is required to retrieve it.<br />
     *   The Emergency List ignores the individual privacy settings, and display
     * all contact information. Address and other things are omitted.
     *
     * @return Response with the IAESTE Emergency Contact List
     */
    public EmergencyListResponse fetchEmergencyList() {
        final List<UserGroupEntity> ncs = dao.findEmergencyList();
        final List<UserGroup> result = new ArrayList<>(ncs.size());

        for (final UserGroupEntity entity : ncs) {
            // The Emergency List is a special case, so we're omitting the
            // standard transformation, and reading out the needed values
            final UserGroup userGroup = readUserGroupForEmergencyList(entity);
            result.add(userGroup);
        }

        final EmergencyListResponse response = new EmergencyListResponse();
        response.setEmergencyContacts(result);
        return response;
    }

    /**
     * The Conacts is used to retrieve the information about Groups and Users.
     * All information about Groups are fetched, and for users, the Privacy
     * rules apply.<br />
     *   By default all user information is private, meaning that only if a user
     * explicitly changes the privacy settings, will the data be visible to
     * others.
     *
     * @param request Information about what should be retrieved
     * @return Response with the retrieved information
     */
    public ContactsResponse fetchContacts(final ContactsRequest request) {
        ContactsResponse response = null;

        switch (request.getType()) {
            case USER:
                response = retrieveContactForUser(request.getUserId());
                break;
            case GROUP:
                response = retrieveContactForGroup(request.getGroupId());
                break;
            case OTHER:
                // Select all Member Groups & International Groups, since these
                // are considered the "master" groups
                response = retrieveContactGroups();
                break;
        }

        return response;
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private static UserGroup readUserGroupForEmergencyList(final UserGroupEntity entity) {
        final UserGroup userGroup = new UserGroup();
        userGroup.setUserGroupId(entity.getExternalId());
        userGroup.setUser(readUserForEmergencyList(entity));
        userGroup.setGroup(transform(entity.getGroup()));
        userGroup.setRole(readRoleForEmergencyList(entity));
        userGroup.setTitle(entity.getTitle());

        return userGroup;
    }

    private static User readUserForEmergencyList(final UserGroupEntity entity) {
        final User user = new User();
        user.setUserId(entity.getUser().getExternalId());
        user.setPerson(readPersonForEmergencyList(entity));
        user.setUsername(entity.getUser().getUsername());
        user.setFirstname(entity.getUser().getFirstname());
        user.setLastname(entity.getUser().getLastname());
        user.setAlias(entity.getUser().getAlias());

        return user;
    }

    private static Person readPersonForEmergencyList(final UserGroupEntity entity) {
        final Person person = new Person();

        // Person Object is optional
        if (entity.getUser().getPerson() != null) {
            person.setPhone(entity.getUser().getPerson().getPhone());
            person.setMobile(entity.getUser().getPerson().getMobile());
        }

        return person;
    }

    private static Role readRoleForEmergencyList(final UserGroupEntity entity) {
        final Role role = new Role();
        role.setRoleId(entity.getExternalId());
        role.setRoleName(entity.getRole().getRole());

        return role;
    }

    /**
     * Select the User with complete details if the Privacy level is set to
     * Public. Additionally, retrieve a list of all Groups which the user is
     * associated with.
     *
     * @param externalUserId The External User Id
     * @return Contact Response with User details and associated Groups
     */
    private ContactsResponse retrieveContactForUser(final String externalUserId) {
        final List<UserGroupEntity> groupEntities = dao.findUserGroups(externalUserId);

        if (!groupEntities.isEmpty()) {
            final ContactsResponse response = new ContactsResponse();
            response.setType(ContactsType.USER);
            response.setUsers(new ArrayList<User>(extractUsers(groupEntities).subList(0, 1)));
            response.setGroups(extractGroups(groupEntities));

            return response;
        } else {
            throw new IWSException(IWSErrors.OBJECT_IDENTIFICATION_ERROR, "No details were found for User with Id " + externalUserId);
        }
    }

    /**
     * Retrieves the Group with complete details, and the list of Users
     * currently linked to it. The Users are checked for pricavy, so only those
     * with the privacy setting Public is being shown fully.
     *
     * @param externalGroupId External Group Id
     * @return Contact Response with Groups details and associated Users
     */
    private ContactsResponse retrieveContactForGroup(final String externalGroupId) {
        final List<UserGroupEntity> userEntities = dao.findGroupMembers(externalGroupId);

        if (!userEntities.isEmpty()) {
            final ContactsResponse response = new ContactsResponse();
            response.setType(ContactsType.GROUP);
            // Martin: I think we should add all groups were this group is the
            //         parent group
            // Kim:      Although fetching all subgroups may be a good idea, we
            //         need to alter the code a bit, so it is possible to know
            //         which Group is the requested and which is the subgroups.
            //         For this reason, we'll leave these comments here for now
            //         so we can update the code later to provide this feature.
            //           A possible solution is to have the UserGroup extracted
            //         as the initial value, and have the two lists containing
            //         members and subgroups.
            response.setGroups(new ArrayList<Group>(extractGroups(userEntities).subList(0,1)));
            response.setUsers(extractUsers(userEntities));

            return response;
        } else {
            throw new IWSException(IWSErrors.OBJECT_IDENTIFICATION_ERROR, "No details were found for Group with Id " + externalGroupId);
        }
    }

    /**
     * Our default case, retrieves a list of all International and Members
     * groups.
     *
     * @return List of all International & Members Groups
     */
    private ContactsResponse retrieveContactGroups() {
        final List<GroupEntity> entities = dao.findGroupsForContacts();

        final List<Group> groups = new ArrayList<>(entities.size());
        for (final GroupEntity entity : entities) {
            groups.add(transform(entity));
            List<Group> subGroups = retrieveSubGroups(entity.getId(), MAX_DEPTH_CONTACT_GROUPS);
            for(Group subGroup : subGroups)
            {
                groups.add(subGroup);
            }
        }

        final ContactsResponse response = new ContactsResponse();
        response.setType(ContactsType.OTHER);
        response.setGroups(groups);

        return response;
    }

    private List<Group> retrieveSubGroups(Long groupId, int depth) {
        if(depth <= 0) {
            return new ArrayList();
        }

        final List<GroupEntity> entities = dao.findSubGroupsByParentId(groupId);

        final List<Group> groups = new ArrayList<>(entities.size());
        for (final GroupEntity entity : entities) {
            groups.add(transform(entity));
            List<Group> subGroups = retrieveSubGroups(entity.getId(), depth-1);
            for(Group subGroup : subGroups) {
                groups.add(subGroup);
            }
        }

        return groups;
    }


    private static List<User> extractUsers(final List<UserGroupEntity> entities) {
        final List<User> users = new ArrayList<>(entities.size());

        for (final UserGroupEntity entity : entities) {
            final User user = AdministrationTransformer.transform(entity.getUser());
            if (entity.getUser().getPrivateData() != Privacy.PUBLIC) {
                user.setPerson(null);
            }
            users.add(user);
        }

        return users;
    }

    private static List<Group> extractGroups(final List<UserGroupEntity> entities) {
        final List<Group> groups = new ArrayList<>(entities.size());

        for (final UserGroupEntity entity : entities) {
            groups.add(transform(entity.getGroup()));
        }

        return groups;
    }
}
