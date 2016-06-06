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
package net.iaeste.iws.ws.client.mappers;

import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.Role;
import net.iaeste.iws.api.dtos.User;
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
import net.iaeste.iws.api.requests.RoleRequest;
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
import net.iaeste.iws.api.responses.ProcessRoleResponse;
import net.iaeste.iws.api.responses.ProcessUserGroupResponse;
import net.iaeste.iws.api.responses.SearchUserResponse;
import net.iaeste.iws.ws.UserFetchType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class AdministrationMapper extends CommonMapper {

    /** Private Constructor, this is a Utility Class. */
    private AdministrationMapper() {
    }

    public static net.iaeste.iws.ws.CountryRequest map(final CountryRequest api) {
        net.iaeste.iws.ws.CountryRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.CountryRequest();

            ws.setCountry(map(api.getCountry()));
            ws.setAction(map(api.getAction()));
        }

        return ws;
    }

    public static net.iaeste.iws.ws.FetchCountryRequest map(final FetchCountryRequest api) {
        net.iaeste.iws.ws.FetchCountryRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchCountryRequest();

            ws.setPage(map(api.getPage()));
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

            api.setCountries(mapWSCountryCollection(ws.getCountries()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.UserRequest map(final UserRequest api) {
        net.iaeste.iws.ws.UserRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.UserRequest();

            ws.setUser(map(api.getUser()));
            ws.setNewStatus(map(api.getNewStatus()));
            ws.setNewUsername(api.getNewUsername());
            ws.setNewPassword(api.getNewPassword());
            ws.setPassword(api.getPassword());
        }

        return ws;
    }

    public static net.iaeste.iws.ws.AccountNameRequest map(final AccountNameRequest api) {
        net.iaeste.iws.ws.AccountNameRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.AccountNameRequest();

            ws.setUser(map(api.getUser()));
            ws.setFirstname(api.getFirstname());
            ws.setLastname(api.getLastname());
        }

        return ws;
    }

    public static net.iaeste.iws.ws.FetchUserRequest map(final FetchUserRequest api) {
        net.iaeste.iws.ws.FetchUserRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchUserRequest();

            ws.setUserId(api.getUserId());
        }

        return ws;
    }

    public static FetchUserResponse map(final net.iaeste.iws.ws.FetchUserResponse ws) {
        FetchUserResponse api = null;

        if (ws != null) {
            api = new FetchUserResponse(map(ws.getError()), ws.getMessage());

            api.setUser(map(ws.getUser()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.RoleRequest map(final RoleRequest api) {
        net.iaeste.iws.ws.RoleRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.RoleRequest();

            ws.setRole(map(api.getRole()));
            ws.setRoleId(api.getRoleId());
            ws.setAction(map(api.getAction()));
        }

        return ws;
    }

    public static ProcessRoleResponse map(final net.iaeste.iws.ws.ProcessRoleResponse ws) {
        ProcessRoleResponse api = null;

        if (ws != null) {
            api = new ProcessRoleResponse(map(ws.getError()), ws.getMessage());

            api.setRole(map(ws.getRole()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.FetchRoleRequest map(final FetchRoleRequest api) {
        net.iaeste.iws.ws.FetchRoleRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchRoleRequest();

            ws.setGroupId(api.getGroupId());
        }

        return ws;
    }

    public static FetchRoleResponse map(final net.iaeste.iws.ws.FetchRoleResponse ws) {
        FetchRoleResponse api = null;

        if (ws != null) {
            api = new FetchRoleResponse(map(ws.getError()), ws.getMessage());

            api.setRoles(mapWSRoleCollection(ws.getRoles()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.GroupRequest map(final GroupRequest api) {
        net.iaeste.iws.ws.GroupRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.GroupRequest();

            ws.setGroup(map(api.getGroup()));
            ws.setUser(map(api.getUser()));
        }

        return ws;
    }

    public static ProcessGroupResponse map(final net.iaeste.iws.ws.ProcessGroupResponse ws) {
        ProcessGroupResponse api = null;

        if (ws != null) {
            api = new ProcessGroupResponse(map(ws.getError()), ws.getMessage());

            api.setGroup(map(ws.getGroup()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.FetchGroupRequest map(final FetchGroupRequest api) {
        net.iaeste.iws.ws.FetchGroupRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchGroupRequest();

            ws.setGroupId(api.getGroupId());
            ws.setGroupType(map(api.getGroupType()));
            ws.setUsersToFetch(map(api.getUsersToFetch()));
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
            api.setMembers(mapWSUserGroupCollection(ws.getMembers()));
            api.setStudents(mapWSUserGroupCollection(ws.getStudents()));
            api.setSubGroups(mapWSGroupCollection(ws.getSubGroups()));
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

            api.setUserGroup(map(ws.getUserGroup()));
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

            api.setUsers(mapWSUserGroupCollection(ws.getUsers()));
        }

        return api;
    }

    public static EmergencyListResponse map(final net.iaeste.iws.ws.EmergencyListResponse ws) {
        EmergencyListResponse api = null;

        if (ws != null) {
            api = new EmergencyListResponse(map(ws.getError()), ws.getMessage());

            api.setEmergencyContacts(mapWSUserGroupCollection(ws.getEmergencyContacts()));
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

            api.setUsers(mapWSUserCollection(ws.getUsers()));
            api.setGroups(mapWSGroupCollection(ws.getGroups()));
            api.setType(map(ws.getType()));
        }

        return api;
    }

    // =========================================================================
    // Internal mapping of required Collections, DTOs & Enums
    // =========================================================================

    private static List<Country> mapWSCountryCollection(final Collection<net.iaeste.iws.ws.Country> ws) {
        final List<Country> api;

        if (ws != null) {
            api = new ArrayList<>(ws.size());

            for (final net.iaeste.iws.ws.Country country : ws) {
                api.add(map(country));
            }
        } else {
            api = new ArrayList<>(0);
        }

        return api;
    }

    private static List<User> mapWSUserCollection(final Collection<net.iaeste.iws.ws.User> ws) {
        final List<User> api;

        if (ws != null) {
            api = new ArrayList<>(ws.size());

            for (final net.iaeste.iws.ws.User user : ws) {
                api.add(map(user));
            }
        } else {
            api = new ArrayList<>(0);
        }

        return api;
    }

    private static List<Role> mapWSRoleCollection(final Collection<net.iaeste.iws.ws.Role> ws) {
        final List<Role> api;

        if (ws != null) {
            api = new ArrayList<>(ws.size());

            for (final net.iaeste.iws.ws.Role role : ws) {
                api.add(map(role));
            }
        } else {
            api = new ArrayList<>(0);
        }

        return api;
    }

    private static net.iaeste.iws.ws.CountryType map(final CountryType api) {
        return (api != null) ? net.iaeste.iws.ws.CountryType.valueOf(api.name()) : null;
    }

    private static ContactsType map(final net.iaeste.iws.ws.ContactsType ws) {
        return (ws != null) ? ContactsType.valueOf(ws.value()) : null;
    }

    private static net.iaeste.iws.ws.ContactsType map(final ContactsType api) {
        return (api != null) ? net.iaeste.iws.ws.ContactsType.valueOf(api.name()) : null;
    }

    private static UserFetchType map(final FetchGroupRequest.UserFetchType api) {
        return (api != null) ? UserFetchType.valueOf(api.name()) : null;
    }
}
