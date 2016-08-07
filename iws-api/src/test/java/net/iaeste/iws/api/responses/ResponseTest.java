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

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ResponseTest {

    @Test
    public void testClassflow() {
        final Response response = new Response();

        assertThat(response.isOk(), is(true));
        assertThat(response.getError(), is(IWSErrors.SUCCESS));
        assertThat(response.getMessage(), is(IWSConstants.SUCCESS));
    }

    @Test
    public void testClassWithError() {
        final IWSError error = IWSErrors.AUTHORIZATION_ERROR;
        final String message = "Error message";
        final Response response = new Response(error, message);

        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(error));
        assertThat(response.getMessage(), is(message));
    }

    @Test
    public void testStandardMethods() {
        final Response obj = new Response(IWSErrors.FATAL, "fatal");
        final Response same = new Response(IWSErrors.FATAL, "fatal");
        final Response diff = new Response();

        // Test Equality
        assertThat(obj, is(same));
        assertThat(obj, is(not(diff)));

        // Test HashCodes
        assertThat(obj.hashCode(), is(-1585682235));
        assertThat(same.hashCode(), is(-1585682235));
        assertThat(diff.hashCode(), is(-100086673));

        // Test ToString
        assertThat(obj.toString(), is("Response{error=IWSError{error=300, description='A fatal error occurred, which will prevent the IWS from working properly.'}, message='fatal'}"));
        assertThat(same.toString(), is("Response{error=IWSError{error=300, description='A fatal error occurred, which will prevent the IWS from working properly.'}, message='fatal'}"));
        assertThat(diff.toString(), is("Response{error=IWSError{error=0, description='Success.'}, message='OK'}"));
    }
}
