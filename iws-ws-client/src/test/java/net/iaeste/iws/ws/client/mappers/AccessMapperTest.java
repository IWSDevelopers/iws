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

import static net.iaeste.iws.ws.client.mappers.AccessMapper.map;
import static net.iaeste.iws.ws.client.mappers.TestUtils.prepareIwsError;
import static net.iaeste.iws.ws.client.mappers.TestUtils.prepareToken;
import static net.iaeste.iws.ws.client.mappers.TestUtils.prepareUserGroup;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Password;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.SessionDataRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.api.responses.SessionDataResponse;
import net.iaeste.iws.api.responses.VersionResponse;
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.api.util.Serializer;
import net.iaeste.iws.ws.Authorization;
import org.junit.Test;

import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public final class AccessMapperTest {

    @Test
    public void testNullVersionResponse() {
        final net.iaeste.iws.ws.VersionResponse ws = null;
        final VersionResponse mapped = map(ws);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testVersionResponse() {
        final String hostname = "the Hostname";
        final String address = "123.321.213.231";
        final String version = "Test Version";

        final net.iaeste.iws.ws.VersionResponse ws = new net.iaeste.iws.ws.VersionResponse();
        ws.setError(prepareIwsError(IWSErrors.SUCCESS));
        ws.setMessage(IWSConstants.SUCCESS);
        ws.setHostname(hostname);
        ws.setAddress(address);
        ws.setVersion(version);

        final VersionResponse mapped = map(ws);

        assertThat(mapped.getError().getError(), is(IWSErrors.SUCCESS.getError()));
        assertThat(mapped.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(mapped.getHostname(), is(hostname));
        assertThat(mapped.getAddress(), is(address));
        assertThat(mapped.getVersion(), is(version));
    }

    @Test
    public void testNullAuthenticationRequest() {
        final AuthenticationRequest api = null;
        final net.iaeste.iws.ws.AuthenticationRequest mapped = map(api);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testAuthenticationRequest() {
        final String username = "user@name.com";
        final String password = "SuperSecretUserPassword";
        final String eulaVersion = UUID.randomUUID().toString();

        final AuthenticationRequest api = new AuthenticationRequest();
        api.setUsername(username);
        api.setPassword(password);
        api.setEulaVersion(eulaVersion);

        final net.iaeste.iws.ws.AuthenticationRequest mapped = map(api);
        assertThat(mapped.getUsername(), is(username));
        assertThat(mapped.getPassword(), is(password));
        assertThat(mapped.getEulaVersion(), is(eulaVersion));
    }

    @Test
    public void testNullAuthenticationResponse() {
        final net.iaeste.iws.ws.AuthenticationResponse ws = null;
        final AuthenticationResponse mapped = map(ws);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testAuthenticationResponse() {
        final String sessionKey = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678";
        final String groupId = UUID.randomUUID().toString();

        final net.iaeste.iws.ws.AuthenticationResponse ws = new net.iaeste.iws.ws.AuthenticationResponse();
        ws.setError(prepareIwsError(IWSErrors.SUCCESS));
        ws.setMessage(IWSConstants.SUCCESS);
        ws.setToken(prepareToken(sessionKey, groupId));

        final AuthenticationResponse mapped = map(ws);

        assertThat(mapped.getError().getError(), is(IWSErrors.SUCCESS.getError()));
        assertThat(mapped.getMessage(), is(IWSConstants.SUCCESS));
    }

    @Test
    public void testNullSessionDataRequest() {
        final SessionDataRequest<?> api = null;
        final net.iaeste.iws.ws.SessionDataRequest mapped = map(api);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testSessionDataRequest() {
        final String rawData = UUID.randomUUID().toString();
        final byte[] savedData = Serializer.serialize(rawData);

        final SessionDataRequest<String> api = new SessionDataRequest();
        api.setSessionData(rawData);

        final net.iaeste.iws.ws.SessionDataRequest mapped = map(api);
        assertThat(mapped.getSessionData(), is(savedData));
    }

    @Test
    public void testNullSessionDataResponse() {
        final net.iaeste.iws.ws.SessionDataResponse ws = null;
        final SessionDataResponse<?> mapped = map(ws);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testSessionDataResponse() {
        final String rawData = UUID.randomUUID().toString();
        final byte[] savedData = Serializer.serialize(rawData);
        final DateTime modified = new DateTime();
        final DateTime created = new DateTime();

        final net.iaeste.iws.ws.SessionDataResponse ws = new net.iaeste.iws.ws.SessionDataResponse();
        ws.setError(prepareIwsError(IWSErrors.SUCCESS));
        ws.setMessage(IWSConstants.SUCCESS);
        ws.setSessionData(savedData);
        ws.setModified(map(modified));
        ws.setCreated(map(created));

        final SessionDataResponse<String> mapped = map(ws);

        assertThat(mapped.getError().getError(), is(IWSErrors.SUCCESS.getError()));
        assertThat(mapped.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(mapped.getSessionData(), is(rawData));
        assertThat(mapped.getModified(), is(modified));
        assertThat(mapped.getCreated(), is(created));
    }

    @Test
    public void testNullFetchPermissionResponse() {
        final net.iaeste.iws.ws.FetchPermissionResponse ws = null;
        final FetchPermissionResponse mapped = map(ws);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testFetchPermissionResponseNullPermissions() {
        final String userId = UUID.randomUUID().toString();

        final net.iaeste.iws.ws.FetchPermissionResponse ws = new net.iaeste.iws.ws.FetchPermissionResponse();
        ws.setError(prepareIwsError(IWSErrors.SUCCESS));
        ws.setMessage(IWSConstants.SUCCESS);
        ws.setUserId(userId);

        final FetchPermissionResponse mapped = map(ws);

        assertThat(mapped.getError().getError(), is(IWSErrors.SUCCESS.getError()));
        assertThat(mapped.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(mapped.getUserId(), is(userId));
        assertThat(mapped.getAuthorizations().isEmpty(), is(true));
    }

    private static Authorization prepareAuthorization() {
        final Authorization authorization = new Authorization();
        authorization.setUserGroup(map(prepareUserGroup()));

        return authorization;
    }

    @Test
    public void testFetchPermissionResponseWithPermissions() {
        final String userId = UUID.randomUUID().toString();
        final Authorization authorization = prepareAuthorization();

        final net.iaeste.iws.ws.FetchPermissionResponse ws = new net.iaeste.iws.ws.FetchPermissionResponse();
        ws.setError(prepareIwsError(IWSErrors.SUCCESS));
        ws.setMessage(IWSConstants.SUCCESS);
        ws.setUserId(userId);
        ws.getAuthorizations().add(authorization);

        final FetchPermissionResponse mapped = map(ws);

        assertThat(mapped.getError().getError(), is(IWSErrors.SUCCESS.getError()));
        assertThat(mapped.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(mapped.getUserId(), is(userId));
        assertThat(mapped.getAuthorizations().size(), is(1));
        assertThat(mapped.getAuthorizations().get(0).getUserGroup(), is(map(authorization.getUserGroup())));
    }

    @Test
    public void testNullPassword() {
        final Password api = null;
        final net.iaeste.iws.ws.Password mapped = map(api);

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testPassword() {
        final String newPassword = "new Password";
        final String identification = UUID.randomUUID().toString();

        final Password api = new Password();
        api.setNewPassword(newPassword);
        api.setIdentification(identification);

        final net.iaeste.iws.ws.Password mapped = map(api);

        assertThat(mapped.getNewPassword(), is(newPassword));
        assertThat(mapped.getIdentification(), is(identification));
    }
}
