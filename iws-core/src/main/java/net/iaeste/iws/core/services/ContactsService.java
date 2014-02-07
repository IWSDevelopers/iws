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
import net.iaeste.iws.api.dtos.Person;
import net.iaeste.iws.api.dtos.Role;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.ContactsRequest;
import net.iaeste.iws.api.responses.ContactsResponse;
import net.iaeste.iws.api.responses.EmergencyListResponse;
import net.iaeste.iws.persistence.AdminDao;
import net.iaeste.iws.persistence.entities.UserGroupEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ContactsService {

    private final AdminDao dao;

    public ContactsService(final AdminDao dao) {
        this.dao = dao;
    }

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

    public ContactsResponse fetchContacts(final ContactsRequest request) {
        throw new IWSException(IWSErrors.NOT_IMPLEMENTED, "Functionality is pending implementation.");
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
}
