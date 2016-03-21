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
package net.iaeste.iws.api.requests;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import com.gargoylesoftware.base.testing.EqualsTester;
import org.junit.Test;

/**
 * Test for the AuthenticationRequest Object.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class AuthenticationRequestTest {

    @Test
    public void testClassFlow() {
        // Test preconditions
        final String username = "username";
        final String password = "password";

        // Test Objects
        final AuthenticationRequest result = new AuthenticationRequest(username, password);
        final AuthenticationRequest same = new AuthenticationRequest();
        final AuthenticationRequest diff = new AuthenticationRequest(null, null);
        same.setUsername(username);
        same.setPassword(password);

        // Assertion Checks
        assertThat(result, is(same));
        assertThat(result, is(not(diff)));
        assertThat(result.getUsername(), is(username));
        assertThat(result.getPassword(), is(password));
        assertThat(result.toString(), is("AuthenticationRequest{username='username'}"));
        assertThat(result.toString(), is(same.toString()));
        assertThat(result.toString(), is(not(diff.toString())));
        assertThat(result.hashCode(), is(1278726105));
        assertThat(result.hashCode(), is(same.hashCode()));
        assertThat(result.hashCode(), is(not(diff.hashCode())));

        new EqualsTester(result, same, diff, null);
    }
}
