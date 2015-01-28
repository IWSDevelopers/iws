/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import static net.iaeste.iws.api.util.LogUtil.formatLogMessage;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.AccountNameRequest;
import net.iaeste.iws.api.requests.ContactsRequest;
import net.iaeste.iws.api.requests.CountryRequest;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.requests.FetchCountryRequest;
import net.iaeste.iws.api.requests.FetchGroupRequest;
import net.iaeste.iws.api.requests.FetchRoleRequest;
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.requests.GroupRequest;
import net.iaeste.iws.api.requests.OwnerRequest;
import net.iaeste.iws.api.requests.SearchUserRequest;
import net.iaeste.iws.api.requests.UserGroupAssignmentRequest;
import net.iaeste.iws.api.requests.UserRequest;
import net.iaeste.iws.api.responses.ContactsResponse;
import net.iaeste.iws.api.responses.CreateUserResponse;
import net.iaeste.iws.api.responses.EmergencyListResponse;
import net.iaeste.iws.api.responses.FallibleResponse;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.responses.ProcessGroupResponse;
import net.iaeste.iws.api.responses.ProcessUserGroupResponse;
import net.iaeste.iws.api.responses.SearchUserResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.core.services.AccountService;
import net.iaeste.iws.core.services.ContactsService;
import net.iaeste.iws.core.services.CountryService;
import net.iaeste.iws.core.services.GroupService;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.persistence.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class AdministrationController extends CommonController implements Administration {

    private static final Logger log = LoggerFactory.getLogger(AdministrationController.class);

    /**
     * Default Constructor, takes a ServiceFactory as input parameter, and uses
     * this in all the request methods, to get a new Service instance.
     *
     * @param factory  The ServiceFactory
     */
    public AdministrationController(final ServiceFactory factory) {
        super(factory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchCountryResponse fetchCountries(final AuthenticationToken token, final FetchCountryRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting fetchCountries()"));
        }
        FetchCountryResponse response;

        try {
            // This request is "for free", there's no need for permission
            // checks, since all it does is read from a non-protected table
            verify(request);
            verifyPrivateAccess(token);

            final CountryService service = factory.prepareCountryService();
            response = service.fetchCountries(request);
        } catch (IWSException e) {
            response = new FetchCountryResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished fetchCountries()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible processCountry(final AuthenticationToken token, final CountryRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting processCountry()"));
        }
        Fallible response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_COUNTRY);

            final CountryService service = factory.prepareCountryService();
            service.processCountries(authentication, request);
            response = new FetchCountryResponse();
        } catch (IWSException e) {
            response = new FetchCountryResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished processCountry()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateUserResponse createUser(final AuthenticationToken token, final CreateUserRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting createUser()"));
        }
        CreateUserResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.CONTROL_USER_ACCOUNT);

            final AccountService service = factory.prepareAccountService();
            response = service.createUser(authentication, request);
        } catch (IWSException e) {
            response = new CreateUserResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished createUser()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible activateUser(final String activationString) {
        if (log.isTraceEnabled()) {
            log.trace("Starting activateUser()");
        }
        Fallible response;

        try {
            verifyCode(activationString, "Provided Activation String is invalid.");

            final AccountService service = factory.prepareAccountService();
            service.activateUser(activationString);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace("Finished activateUser()");
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible updateUsername(final String updateCode) {
        if (log.isTraceEnabled()) {
            log.trace("Starting updateUsername()");
        }
        Fallible response;

        try {
            verifyCode(updateCode, "The UpdateCode is invalid.");

            final AccountService service = factory.prepareAccountService();
            service.updateUsername(updateCode);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace("Finished updateUsername()");
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible controlUserAccount(final AuthenticationToken token, final UserRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting controlUserAccount()"));
        }
        Fallible response;

        try {
            // The Permission check for this request, is moved into the service
            // that handles the request. The reason for this, is that users may
            // also alter their own data, but that it requires the correct
            // permissions to alter others.
            verify(request);
            final Authentication authentication = verifyPrivateAccess(token);

            final AccountService service = factory.prepareAccountService();
            service.controlUserAccount(authentication, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished controlUserAccount()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible changeAccountName(final AuthenticationToken token, final AccountNameRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting changeAccountName()"));
        }
        Fallible response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.CHANGE_ACCOUNT_NAME);

            final AccountService service = factory.prepareAccountService();
            service.changeAccountName(authentication, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished changeAccountName()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchUserResponse fetchUser(final AuthenticationToken token, final FetchUserRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting fetchUser()"));
        }
        FetchUserResponse response;

        try {
            // The Permission check for this request, is moved into the service
            // that handles the request. The reason for this, is that users may
            // also request their own data.
            verify(request);
            final Authentication authentication = verifyPrivateAccess(token);

            final AccountService service = factory.prepareAccountService();
            response = service.fetchUser(authentication, request);
        } catch (IWSException e) {
            response = new FetchUserResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished fetchUser()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchRoleResponse fetchRoles(final AuthenticationToken token, final FetchRoleRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting fetchRoles()"));
        }
        FetchRoleResponse response;

        try {
            verify(request);
            token.setGroupId(request.getGroupId());
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_USER_GROUP_ASSIGNMENT);

            final AccountService service = factory.prepareAccountService();
            response = service.fetchRoles(authentication);
        } catch (IWSException e) {
            response = new FetchRoleResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished fetchRoles()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessGroupResponse processGroup(final AuthenticationToken token, final GroupRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting processGroup()"));
        }
        ProcessGroupResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_GROUP);

            final GroupService service = factory.prepareGroupService();
            response = service.processGroup(authentication, request);
        } catch (IWSException e) {
            response = new ProcessGroupResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished processGroup()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible deleteSubGroup(final AuthenticationToken token, final GroupRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting deleteSubGroup()"));
        }
        Fallible response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.DELETE_GROUP);

            final GroupService service = factory.prepareGroupService();
            service.deleteGroup(authentication, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FallibleResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished deleteSubGroup()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchGroupResponse fetchGroup(final AuthenticationToken token, final FetchGroupRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting fetchGroup()"));
        }
        FetchGroupResponse response;

        try {
            // First we verify the Object, where the mandatory information is.
            // And then we can use this to extend the Token. As the request
            // Object holds the GroupId that should be used for the later
            // processing, we simply overwrite the Token provided GroupId, to
            // ensure that they are identical and no spoofing attempts can be
            // made against this request!
            verify(request);
            token.setGroupId(request.getGroupId());
            // Note, we're skipping the permission check, since the request will
            // be made with checking the users connection to the group.
            final Authentication authentication = verifyPrivateAccess(token);

            final GroupService service = factory.prepareGroupService();
            response = service.fetchGroup(authentication, request);
        } catch (IWSException e) {
            response = new FetchGroupResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished fetchGroup()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible changeGroupOwner(final AuthenticationToken token, final OwnerRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting changeGroupOwner()"));
        }
        Fallible response;

        try {
            verify(request);
            token.setGroupId(request.getGroup().getGroupId());
            final Authentication authentication = verifyAccess(token, Permission.CHANGE_GROUP_OWNER);

            final GroupService service = factory.prepareGroupService();
            service.changeUserGroupOwner(authentication, request);
            response = new FallibleResponse();
        } catch (IWSException e) {
            response = new FetchGroupResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished changeGroupOwner()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessUserGroupResponse processUserGroupAssignment(final AuthenticationToken token, final UserGroupAssignmentRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting processUserGroupAssignment()"));
        }
        ProcessUserGroupResponse response;

        try {
            verify(request);
            token.setGroupId(request.getUserGroup().getGroup().getGroupId());
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_USER_GROUP_ASSIGNMENT);

            final GroupService service = factory.prepareGroupService();
            response = service.processUserGroupAssignment(authentication, request);
        } catch (IWSException e) {
            response = new ProcessUserGroupResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished processUserGroupAssignment()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchUserResponse searchUsers(final AuthenticationToken token, final SearchUserRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting searchUsers()"));
        }
        SearchUserResponse response;

        try {
            verify(request);
            verifyAccess(token, Permission.PROCESS_USER_GROUP_ASSIGNMENT);

            final ContactsService service = factory.prepareContacsService();
            response = service.searchUsers(request);
        } catch (IWSException e) {
            response = new SearchUserResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished searchUsers()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmergencyListResponse fetchEmergencyList(final AuthenticationToken token) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting fetchEmergencyList()"));
        }
        EmergencyListResponse response;

        try {
            verifyAccess(token, Permission.FETCH_EMERGENCY_LIST);

            final ContactsService service = factory.prepareContacsService();
            response = service.fetchEmergencyList();
        } catch (IWSException e) {
            response = new EmergencyListResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished fetchEmergencyList()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContactsResponse fetchContacts(final AuthenticationToken token, final ContactsRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting fetchContacts()"));
        }
        ContactsResponse response;

        try {
            verify(request);
            verifyPrivateAccess(token);

            final ContactsService service = factory.prepareContacsService();
            response = service.fetchContacts(request);
        } catch (IWSException e) {
            response = new ContactsResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished fetchContacts()"));
        }
        return response;
    }
}
