/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.AdministrationController
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.ManageUserAccountRequest;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.responses.CountryResponse;
import net.iaeste.iws.api.responses.FacultyResponse;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.api.responses.GroupResponse;
import net.iaeste.iws.api.responses.UserResponse;
import net.iaeste.iws.core.services.AdministrationService;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.persistence.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.6
 */
public class AdministrationController extends CommonController implements Administration {

    private static final String AUTHENTICATION_TOKEN_ERROR = "The Authentication Token is undefined.";
    private static final Logger LOG = LoggerFactory.getLogger(AdministrationController.class);
    private final ServiceFactory factory;

    /**
     * Default Constructor, takes a ServiceFactory as input parameter, and uses
     * this in all the request methods, to get a new Service instance.
     *
     * @param factory  The ServiceFactory
     */
    public AdministrationController(final ServiceFactory factory) {
        super(factory.getAccessDao());

        this.factory = factory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible createUser(final AuthenticationToken token, final CreateUserRequest request) {
        LOG.trace("Starting createUser()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.MANAGE_USER_ACCOUNTS);
            verify(request, "Cannot process a null request.");

            final AdministrationService service = factory.prepareAdministrationService();
            response = service.createUser(authentication, request);
        } catch (IWSException e) {
            response = new UserResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished createUser()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible activateUser(final String activationString) {
        LOG.trace("Starting activateUser()");
        Fallible response;

        try {
            final AdministrationService service = factory.prepareAdministrationService();
            service.activateUser(activationString);
            response = new UserResponse();
        } catch (IWSException e) {
            response = new UserResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished activateUser()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible alterUserAccount(final AuthenticationToken token, final ManageUserAccountRequest request) {
        LOG.trace("Starting processUsers()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.MANAGE_USER_ACCOUNTS);
            verify(request, "To be clarified.");

            final AdministrationService service = factory.prepareAdministrationService();
            service.processUsers(authentication, request);
            response = new FacultyResponse();
        } catch (IWSException e) {
            response = new UserResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processUsers()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserResponse fetchUsers(final AuthenticationToken token, final FetchUserRequest request) {
        LOG.trace("Starting fetchUsers()");
        UserResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.FETCH_USERS);
            verify(request, "To be clarified.");

            final AdministrationService service = factory.prepareAdministrationService();
            response = service.fetchUsers(authentication, request);
        } catch (IWSException e) {
            response = new UserResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchUsers()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processGroups(final AuthenticationToken token, final GroupRequest request) {
        LOG.trace("Starting processGroups()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_SUB_GROUPS);
            verify(request, "To be clarified.");

            final AdministrationService service = factory.prepareAdministrationService();
            service.processGroups(authentication, request);
            response = new GroupResponse();
        } catch (IWSException e) {
            response = new GroupResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processGroups()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupResponse fetchGroups(final AuthenticationToken token, final FetchGroupRequest request) {
        LOG.trace("Starting fetchGroups()");
        GroupResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.FETCH_GROUPS);
            verify(request, "To be clarified.");

            final AdministrationService service = factory.prepareAdministrationService();
            response = service.fetchGroups(authentication, request);
        } catch (IWSException e) {
            response = new GroupResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchGroups()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processCountries(final AuthenticationToken token, final CountryRequest request) {
        LOG.trace("Starting processCountries()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_COUNTRIES);
            verify(request, "To be clarified.");

            final AdministrationService service = factory.prepareAdministrationService();
            service.processCountries(authentication, request);
            response = new CountryResponse();
        } catch (IWSException e) {
            response = new CountryResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processCountries()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryResponse fetchCountries(final AuthenticationToken token, final FetchCountryRequest request) {
        LOG.trace("Starting fetchCountries()");
        CountryResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.FETCH_COUNTRIES);
            verify(request, "To be clarified.");

            final AdministrationService service = factory.prepareAdministrationService();
            response = service.fetchCountries(authentication, request);
        } catch (IWSException e) {
            response = new CountryResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchCountries()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processUserGroupAssignment(final AuthenticationToken token, final UserGroupAssignmentRequest request) {
        LOG.trace("Starting processUserGroupAssignment()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_USER_GROUP_ASSIGNMENT);
            verify(request, "To be clarified.");

            final AdministrationService service = factory.prepareAdministrationService();
            service.processUserGroupAssignment(authentication, request);
            response = new GroupResponse();
        } catch (IWSException e) {
            response = new GroupResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processUserGroupAssignment()");
        return response;
    }
}
