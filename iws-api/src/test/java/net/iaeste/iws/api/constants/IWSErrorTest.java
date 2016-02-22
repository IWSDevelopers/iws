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
package net.iaeste.iws.api.constants;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.gargoylesoftware.base.testing.EqualsTester;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 * @noinspection ResultOfObjectAllocationIgnored
 */
public class IWSErrorTest {

    @Test
    public void testClassFlow() {
        // Test preconditions
        final int code = 1;
        final String description = "Description";

        // Test Objects
        final IWSError result = new IWSError(code, description);
        final IWSError same = new IWSError(code, description);
        final IWSError diff = new IWSError(2, "Different Description");

        // Assertion Checks
        assertThat(result, is(same));
        assertThat(result, is(not(diff)));
        assertThat(result.getError(), is(code));
        assertThat(result.getDescription(), is(description));
        assertThat(result.toString(), is("IWSError{error=1, description='Description'}"));
        assertThat(result.toString(), is(same.toString()));
        assertThat(result.toString(), is(not(diff.toString())));
        assertThat(result.hashCode(), is(-314798750));
        assertThat(result.hashCode(), is(same.hashCode()));
        assertThat(result.hashCode(), is(not(diff.hashCode())));
        new EqualsTester(result, same, diff, null);
    }

    @Test
    public void testIWBaseErrors() {
        final IWSError result = new IWSError(1, "Description");

        assertThat(result, is(not(IWSErrors.SUCCESS)));
        assertThat(result, is(not(IWSErrors.ERROR)));
        assertThat(result, is(not(IWSErrors.FATAL)));
        assertThat(result, is(not(IWSErrors.NOT_IMPLEMENTED)));
        assertThat(result, is(not(IWSErrors.DATABASE_UNREACHABLE)));
        assertThat(result, is(not(IWSErrors.VERIFICATION_ERROR)));
        assertThat(result, is(not(IWSErrors.AUTHENTICATION_ERROR)));
        assertThat(result, is(not(IWSErrors.AUTHORIZATION_ERROR)));
    }
}
