/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.CountryResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.core.services.AdministrationService;
import net.iaeste.iws.core.services.CountryService;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.persistence.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class AdministrationController extends CommonController implements Administration {

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
            final Authentication authentication = verifyAccess(token, Permission.CONTROL_USER_ACCOUNT);
            verify(request);

            final AdministrationService service = factory.prepareAdministrationService();
            response = service.createUser(authentication, request);
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
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
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished activateUser()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible controlUserAccount(final AuthenticationToken token, final UserRequest request) {
        LOG.trace("Starting controlUserAccount()");
        Fallible response;

        try {
            // The Permission check for this request, is moved into the service
            // that handles the request. The reason for this, is that users may
            // also alter their own data, but that it requires the correct
            // permissions to alter others.
            final Authentication authentication = verifyPrivateAccess(token);
            verify(request);

            final AdministrationService service = factory.prepareAdministrationService();
            service.controlUserAccount(authentication, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished controlUserAccount()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchUserResponse fetchUser(final AuthenticationToken token, final FetchUserRequest request) {
        LOG.trace("Starting fetchUser()");
        FetchUserResponse response;

        try {
            // The Permission check for this request, is moved into the service
            // that handles the request. The reason for this, is that users may
            // also request their own data.
            final Authentication authentication = verifyPrivateAccess(token);
            verify(request);

            final AdministrationService service = factory.prepareAdministrationService();
            response = service.fetchUser(authentication, request);
        } catch (IWSException e) {
            response = new FetchUserResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchUser()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processGroup(final AuthenticationToken token, final GroupRequest request) {
        LOG.trace("Starting processGroup()");
        Fallible response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_SUB_GROUPS);

            final AdministrationService service = factory.prepareAdministrationService();
            service.processGroup(authentication, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processGroup()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchGroupResponse fetchGroup(final AuthenticationToken token, final FetchGroupRequest request) {
        LOG.trace("Starting fetchGroup()");
        FetchGroupResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.FETCH_GROUPS);
            verify(request);

            final AdministrationService service = factory.prepareAdministrationService();
            response = service.fetchGroup(authentication, request);
        } catch (IWSException e) {
            response = new FetchGroupResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished fetchGroup()");
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
            verify(request);

            final CountryService service = factory.prepareCountryService();
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
            verifyAccess(token, Permission.FETCH_COUNTRIES);
            verify(request);

            final CountryService service = factory.prepareCountryService();
            response = service.fetchCountries(request);
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
            verify(request);

            final AdministrationService service = factory.prepareAdministrationService();
            service.processUserGroupAssignment(authentication, request);
            response = new FetchGroupResponse();
        } catch (IWSException e) {
            response = new FetchGroupResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished processUserGroupAssignment()");
        return response;
    }
}
