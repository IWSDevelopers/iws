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
package net.iaeste.iws.client.errorhandling;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.client.AccessClient;
import org.junit.Test;

/**
 * The IWSError Object is causing a problem due to the fact that it is both an
 * Object and a Constant. Meaning, that comparison using either "==" or
 * ".equals()" are working. The reference comparison ("=="), is incorrect, since
 * it only checks that the pointer references are the same. For this reason, the
 * IWS Exception is using deep copying to return a true copy, which will make
 * the reference comparison fail.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ErrorTest {

    private final Access access = new AccessClient();

    @Test
    public void testErrorCodeComparison() {
        final AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("alfa@centaury.space");
        request.setPassword("Galaxy Quest");
        final AuthenticationResponse response = access.generateSession(request);

        // Now run the assert checks
        //assertThat("Test should fail, comparing addresses is not good when checking if Objects are identical", response.getError().equals(IWSErrors.AUTHENTICATION_ERROR), is(false));
        assertThat(response.getError().equals(IWSErrors.AUTHENTICATION_ERROR), is(true));
    }
}
