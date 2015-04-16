/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ws-client) - net.iaeste.iws.ws.client.mappers.AdministrationMapper
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ws.client.mappers;

import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.enums.ContactsType;
import net.iaeste.iws.api.enums.CountryType;
import net.iaeste.iws.api.requests.AccountNameRequest;
import net.iaeste.iws.api.requests.ContactsRequest;
import net.iaeste.iws.api.requests.CountryRequest;
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
import net.iaeste.iws.api.responses.EmergencyListResponse;
import net.iaeste.iws.api.responses.FetchCountryResponse;
import net.iaeste.iws.api.responses.FetchGroupResponse;
import net.iaeste.iws.api.responses.FetchRoleResponse;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.api.responses.ProcessGroupResponse;
import net.iaeste.iws.api.responses.ProcessUserGroupResponse;
import net.iaeste.iws.api.responses.SearchUserResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class AdministrationMapper extends CommonMapper {

    public static net.iaeste.iws.ws.CountryRequest map(final CountryRequest api) {
        net.iaeste.iws.ws.CountryRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.CountryRequest();

            ws.setCountry(map(api.getCountry()));
        }

        return ws;
    }

    public static net.iaeste.iws.ws.FetchCountryRequest map(final FetchCountryRequest api) {
        net.iaeste.iws.ws.FetchCountryRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchCountryRequest();

            ws.setPage(map(api.getPagingInformation()));
            ws.getCountryIds().addAll(mapStringCollection(api.getCountryIds()));
            ws.setMembership(map(api.getMembership()));
            ws.setCountryType(map(api.getCountryType()));
        }

        return ws;
    }

    public static FetchCountryResponse map(final net.iaeste.iws.ws.FetchCountryResponse ws) {
        FetchCountryResponse api = null;

        if (ws != null) {
            api = new FetchCountryResponse(map(ws.getError()), ws.getMessage());

            api.setCountries(mapCountryList(ws.getCountries()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.UserRequest map(final UserRequest api) {
        net.iaeste.iws.ws.UserRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.UserRequest();
            // TODO!!!
        }

        return ws;
    }

    public static net.iaeste.iws.ws.AccountNameRequest map(final AccountNameRequest api) {
        net.iaeste.iws.ws.AccountNameRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.AccountNameRequest();
            // TODO!!!
        }

        return ws;
    }

    public static net.iaeste.iws.ws.FetchUserRequest map(final FetchUserRequest api) {
        net.iaeste.iws.ws.FetchUserRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchUserRequest();
            // TODO!!!
        }

        return ws;
    }

    public static FetchUserResponse map(final net.iaeste.iws.ws.FetchUserResponse ws) {
        FetchUserResponse api = null;

        if (ws != null) {
            api = new FetchUserResponse(map(ws.getError()), ws.getMessage());
            // TODO!!!
        }

        return api;
    }

    public static net.iaeste.iws.ws.FetchRoleRequest map(final FetchRoleRequest api) {
        net.iaeste.iws.ws.FetchRoleRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchRoleRequest();
            // TODO!!!
        }

        return ws;
    }

    public static FetchRoleResponse map(final net.iaeste.iws.ws.FetchRoleResponse ws) {
        FetchRoleResponse api = null;

        if (ws != null) {
            api = new FetchRoleResponse(map(ws.getError()), ws.getMessage());
            // TODO!!!
        }

        return api;
    }

    public static net.iaeste.iws.ws.GroupRequest map(final GroupRequest api) {
        net.iaeste.iws.ws.GroupRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.GroupRequest();
            // TODO!!!
        }

        return ws;
    }

    public static ProcessGroupResponse map(final net.iaeste.iws.ws.ProcessGroupResponse ws) {
        ProcessGroupResponse api = null;

        if (ws != null) {
            api = new ProcessGroupResponse(map(ws.getError()), ws.getMessage());
            // TODO!!!
        }

        return api;
    }

    public static net.iaeste.iws.ws.FetchGroupRequest map(final FetchGroupRequest api) {
        net.iaeste.iws.ws.FetchGroupRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchGroupRequest();

            ws.setGroupId(api.getGroupId());
            ws.setGroupType(map(api.getGroupType()));
            // TODO Ensure that this enum is properly mapped in WS, currently it conflicts with a similarly named enum!
            //ws.setUsersToFetch(map(api.getUsersToFetch()));
            ws.setFetchStudents(api.isFetchStudents());
            ws.setFetchSubGroups(api.isFetchSubGroups());
        }

        return ws;
    }

    public static FetchGroupResponse map(final net.iaeste.iws.ws.FetchGroupResponse ws) {
        FetchGroupResponse api = null;

        if (ws != null) {
            api = new FetchGroupResponse(map(ws.getError()), ws.getMessage());

            api.setGroup(map(ws.getGroup()));
            api.setMembers(mapUserGroupList(ws.getMembers()));
            api.setStudents(mapUserGroupList(ws.getStudents()));
            api.setSubGroups(new ArrayList<>(mapWSGroupCollection(ws.getSubGroups())));
        }

        return api;
    }

    public static net.iaeste.iws.ws.OwnerRequest map(final OwnerRequest api) {
        net.iaeste.iws.ws.OwnerRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.OwnerRequest();

            ws.setGroup(map(api.getGroup()));
            ws.setUser(map(api.getUser()));
            ws.setTitle(api.getTitle());
        }

        return ws;
    }

    public static net.iaeste.iws.ws.UserGroupAssignmentRequest map(final UserGroupAssignmentRequest api) {
        net.iaeste.iws.ws.UserGroupAssignmentRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.UserGroupAssignmentRequest();

            ws.setUserGroup(map(api.getUserGroup()));
            ws.setAction(map(api.getAction()));
        }

        return ws;
    }

    public static ProcessUserGroupResponse map(final net.iaeste.iws.ws.ProcessUserGroupResponse ws) {
        ProcessUserGroupResponse api = null;

        if (ws != null) {
            api = new ProcessUserGroupResponse(map(ws.getError()), ws.getMessage());
            // TODO!!!
        }

        return api;
    }

    public static net.iaeste.iws.ws.SearchUserRequest map(final SearchUserRequest api) {
        net.iaeste.iws.ws.SearchUserRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.SearchUserRequest();

            ws.setGroup(map(api.getGroup()));
            ws.setName(api.getName());
        }

        return ws;
    }

    public static SearchUserResponse map(final net.iaeste.iws.ws.SearchUserResponse ws) {
        SearchUserResponse api = null;

        if (ws != null) {
            api = new SearchUserResponse(map(ws.getError()), ws.getMessage());
            // TODO!!!
        }

        return api;
    }

    public static EmergencyListResponse map(final net.iaeste.iws.ws.EmergencyListResponse ws) {
        EmergencyListResponse api = null;

        if (ws != null) {
            api = new EmergencyListResponse(map(ws.getError()), ws.getMessage());
            // TODO!!!
        }

        return api;
    }

    public static net.iaeste.iws.ws.ContactsRequest map(final ContactsRequest api) {
        net.iaeste.iws.ws.ContactsRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.ContactsRequest();

            ws.setUserId(api.getUserId());
            ws.setGroupId(api.getGroupId());
            ws.setType(map(api.getType()));
        }

        return ws;
    }

    public static ContactsResponse map(final net.iaeste.iws.ws.ContactsResponse ws) {
        ContactsResponse api = null;

        if (ws != null) {
            api = new ContactsResponse(map(ws.getError()), ws.getMessage());
            // TODO!!!
        }

        return api;
    }

    // =========================================================================
    // Internal mapping of Administration relevant Objects
    // =========================================================================

    private static net.iaeste.iws.ws.CountryType map(final CountryType api) {
        return api != null ? net.iaeste.iws.ws.CountryType.valueOf(api.name()) : null;
    }

    private static net.iaeste.iws.ws.ContactsType map(final ContactsType api) {
        return api != null ? net.iaeste.iws.ws.ContactsType.valueOf(api.name()) : null;
    }

    private static List<Country> mapCountryList(final List<net.iaeste.iws.ws.Country> ws) {
        List<Country> api = null;

        if (ws != null) {
            api = new ArrayList<>(ws.size());

            for (final net.iaeste.iws.ws.Country country : ws) {
                api.add(map(country));
            }
        }

        return api;
    }

    private static List<UserGroup> mapUserGroupList(final List<net.iaeste.iws.ws.UserGroup> ws) {
        List<UserGroup> api = null;

        if (ws != null) {
            api = new ArrayList<>(ws.size());

            for (final net.iaeste.iws.ws.UserGroup userGroup : ws) {
                api.add(map(userGroup));
            }
        }

        return api;
    }
}
