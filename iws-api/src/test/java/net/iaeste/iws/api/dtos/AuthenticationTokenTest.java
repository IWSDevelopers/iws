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
package net.iaeste.iws.api.dtos;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import com.gargoylesoftware.base.testing.EqualsTester;
import org.junit.Test;

import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class AuthenticationTokenTest {

    @Test
    public void testClassflow() {
        final String key = "5a15481fe88d39be1c83c2f72796cc8a70e84272640d5c7209ad9aefa642db11ae8fa1945bc308c15c36d591ea1d047692530c95b68fcc309bbe63889dba363e";
        final String groupId = "12qw43er-43wq-65tr-78ui-09qw87er65rt";

        final AuthenticationToken token = new AuthenticationToken();
        token.setToken(key);
        token.setGroupId(groupId);

        assertThat(token.getToken(), is(key));
        assertThat(token.getGroupId(), is(groupId));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGroupId() {
        final AuthenticationToken token = new AuthenticationToken();
        token.setGroupId("123");
    }

    @Test
    public void testClass() {
        final String mainValue = "5a15481fe88d39be1c83c2f72796cc8a70e84272640d5c7209ad9aefa642db11ae8fa1945bc308c15c36d591ea1d047692530c95b68fcc309bbe63889dba363e";
        final String diffValue = "5a15481fe88d39be1c83c2f72796cc8a70e84272640d5c7209ad9aefa642db11ae8fa1945bc308c15c36d591ea1d047692530c95b68fccxxxxbe63889dba363e";
        final String groupId = "12345678-1324-2341-3244-123443211234";

        final AuthenticationToken result = new AuthenticationToken(mainValue);
        final AuthenticationToken same = new AuthenticationToken();
        final AuthenticationToken diff1 = new AuthenticationToken(diffValue);
        final AuthenticationToken diff2 = new AuthenticationToken(diffValue, groupId);
        same.setToken(mainValue);

        assertThat(result, is(same));
        assertThat(result, is(not(diff1)));
        assertThat(result.getToken(), is(mainValue));
        assertThat(result.hashCode(), is(-1978452439));
        assertThat(diff1.hashCode(), is(522008003));
        assertThat(diff2.hashCode(), is(-129248990));
        assertThat(result.toString(), is("AuthenticationToken{token='" + mainValue + "', groupId=null}"));
        assertThat(diff1.toString(), is("AuthenticationToken{token='" + diffValue + "', groupId=null}"));
        assertThat(diff2.toString(), is("AuthenticationToken{token='" + diffValue + "', groupId='" + groupId + "'}"));

        new EqualsTester(result, same, diff1, null);
        new EqualsTester(result, same, diff2, null);
    }

    @Test
    public void testCopyConstructor() {
        final String mainValue = "5a15481fe88d39be1c83c2f72796cc8a70e84272640d5c7209ad9aefa642db11ae8fa1945bc308c15c36d591ea1d047692530c95b68fcc309bbe63889dba363e";
        final AuthenticationToken original = new AuthenticationToken(mainValue);
        final AuthenticationToken result = new AuthenticationToken(original);

        assertThat(result, is(original));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyConstructorNull() {
        new AuthenticationToken((AuthenticationToken) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptytoken() {
        final String key = "";
        new AuthenticationToken(key);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectToken() {
        final String key = "invalid";
        new AuthenticationToken(key);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullToken() {
        final String key = null;
        new AuthenticationToken(key);
    }

    @Test
    public void testGetTraceId() {
        final AuthenticationToken token = new AuthenticationToken();
        final String traceIdEmpty = token.getTraceId();
        assertThat(traceIdEmpty, is("none"));

        token.setToken("5a15481fe88d39be1c83c2f72796cc8a70e84272640d5c7209ad9aefa642db11ae8fa1945bc308c15c36d591ea1d047692530c95b68fcc309bbe63889dba363e");
        final String traceIdDefined = token.getTraceId();
        assertThat(traceIdDefined, is("5a15481f"));
    }

    @Test
    public void testValidation() {
        final AuthenticationToken token = new AuthenticationToken();
        final Map<String, String> errors = token.validate();

        assertThat(errors.size(), is(1));
        assertThat(errors.containsKey("token"), is(true));
        assertThat(errors.get("token"), is("The field may not be null."));
    }
}
