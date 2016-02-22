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
package net.iaeste.iws.api.responses;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.gargoylesoftware.base.testing.EqualsTester;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import org.junit.Test;

/**
 * Test for the AuthenticationResponse Object.
 * 
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class AuthenticationResponseTest {

    private static final String TOKEN_KEY = "5a15481fe88d39be1c83c2f72796cc8a70e84272640d5c7209ad9aefa642db11ae8fa1945bc308c15c36d591ea1d047692530c95b68fcc309bbe63889dba363e";

    @Test
    public void testClassFlow() {
        // Test preconditions
        final AuthenticationToken token = new AuthenticationToken(TOKEN_KEY);
        final IWSError error = IWSErrors.VERIFICATION_ERROR;
        final String message = "ERROR";

        // Test Objects
        final AuthenticationResponse result = new AuthenticationResponse(token);
        final AuthenticationResponse same = new AuthenticationResponse();
        final AuthenticationResponse diff = new AuthenticationResponse(error, message);
        same.setToken(token);

        // Assertion Checks
        assertThat(result, is(same));
        assertThat(result, is(not(diff)));
        assertThat(result, is(notNullValue()));
        assertThat(result.isOk(), is(true));
        assertThat(result.getToken(), is(token));
        assertThat(result.getError(), is(IWSErrors.SUCCESS));
        assertThat(result.getMessage(), is(IWSConstants.SUCCESS));
        assertThat(result.toString(), is("AuthenticationResponse{token=AuthenticationToken{token='" + TOKEN_KEY + "', groupId=null}, error=IWSError{error=0, description='Success.'}, message='OK'}"));
        assertThat(result.hashCode(), is(1278121686));
        assertThat(result.hashCode(), is(same.hashCode()));
        assertThat(result.hashCode(), is(not(diff.hashCode())));
        assertThat(diff.getError(), is(error));
        assertThat(diff.getMessage(), is(message));

        new EqualsTester(result, same, diff, null);
    }
}
