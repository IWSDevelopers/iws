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

import static net.iaeste.iws.ws.client.mappers.CommonMapper.map;
import static net.iaeste.iws.ws.client.mappers.TestUtils.prepareAddress;
import static net.iaeste.iws.ws.client.mappers.TestUtils.prepareCountry;
import static net.iaeste.iws.ws.client.mappers.TestUtils.prepareGroup;
import static net.iaeste.iws.ws.client.mappers.TestUtils.preparePerson;
import static net.iaeste.iws.ws.client.mappers.TestUtils.prepareRole;
import static net.iaeste.iws.ws.client.mappers.TestUtils.prepareUser;
import static net.iaeste.iws.ws.client.mappers.TestUtils.prepareUserGroup;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Person;
import net.iaeste.iws.api.dtos.Role;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.UserGroup;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public final class CommonMapperTest {

    /**
     * <p>Private methods should never be tested, as they are part of an
     * internal workflow. Classes should always be tested via their contract,
     * i.e. public methods.</p>
     *
     * <p>However, for Utility Classes, with a Private Constructor, the contract
     * disallows instantiation, so the constructor is thus not testable via
     * normal means. This little Test method will just do that.</p>
     */
    @Test
    public void testPrivateConstructor() {
        try {
            final Constructor<CommonMapper> constructor = CommonMapper.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            final CommonMapper mapper = constructor.newInstance();
            assertThat(mapper, is(not(nullValue())));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            fail("Could not invoke Private Constructor: " + e.getMessage());
        }
    }

    @Test
    public void testNullAuthenticationToken() {
        final AuthenticationToken token = null;
        final AuthenticationToken mapped = map(map(token));

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getToken(), is(nullValue()));
        assertThat(mapped.getGroupId(), is(nullValue()));
    }

    @Test
    public void testAuthenticationToken() {
        final String key = "1345678901345678901345678901345678901345678901345678901345678901";
        final String groupId = UUID.randomUUID().toString();

        final AuthenticationToken token = new AuthenticationToken();
        token.setToken(key);
        token.setGroupId(groupId);

        final AuthenticationToken mapped = map(map(token));
        assertThat(mapped.getToken(), is(key));
        assertThat(mapped.getGroupId(), is(groupId));
    }

    @Test
    public void testNullCountry() {
        final Country country = null;
        final Country mapped = map(map(country));

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testCountry() {
        final Country country = prepareCountry();
        final Country mapped = map(map(country));

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getCountryCode(), is(country.getCountryCode()));
        assertThat(mapped.getCountryName(), is(country.getCountryName()));
        assertThat(mapped.getCountryNameFull(), is(country.getCountryNameFull()));
        assertThat(mapped.getCountryNameNative(), is(country.getCountryNameNative()));
        assertThat(mapped.getNationality(), is(country.getNationality()));
        assertThat(mapped.getPhonecode(), is(country.getPhonecode()));
        assertThat(mapped.getCurrency(), is(country.getCurrency()));
        assertThat(mapped.getLanguages(), is(country.getLanguages()));
        assertThat(mapped.getMembership(), is(country.getMembership()));
        assertThat(mapped.getMemberSince(), is(country.getMemberSince()));
        assertThat(mapped, is(country));
    }

    @Test
    public void testNullAddress() {
        final Address address = null;
        final Address mapped = map(map(address));

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testAddress() {
        final Address address = prepareAddress();
        final Address mapped = map(map(address));

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getStreet1(), is(address.getStreet1()));
        assertThat(mapped.getStreet2(), is(address.getStreet2()));
        assertThat(mapped.getPostalCode(), is(address.getPostalCode()));
        assertThat(mapped.getCity(), is(address.getCity()));
        assertThat(mapped.getState(), is(address.getState()));
        assertThat(mapped.getPobox(), is(address.getPobox()));
        assertThat(mapped.getCountry(), is(address.getCountry()));
    }

    @Test
    public void testNullPerson() {
        final Person person = null;
        final Person mapped = map(map(person));

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testPerson() {
        final Person person = preparePerson();
        final Person mapped = map(map(person));

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getNationality(), is(person.getNationality()));
        assertThat(mapped.getAddress(), is(person.getAddress()));
        assertThat(mapped.getAlternateEmail(), is(person.getAlternateEmail()));
        assertThat(mapped.getPhone(), is(person.getPhone()));
        assertThat(mapped.getMobile(), is(person.getMobile()));
        assertThat(mapped.getFax(), is(person.getFax()));
        assertThat(mapped.getBirthday(), is(person.getBirthday()));
        assertThat(mapped.getGender(), is(person.getGender()));
        assertThat(mapped.getUnderstoodPrivacySettings(), is(person.getUnderstoodPrivacySettings()));
        assertThat(mapped.getAcceptNewsletters(), is(person.getAcceptNewsletters()));
    }

    @Test
    public void testNullUser() {
        final User user = null;
        final User mapped = map(map(user));

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testUser() {
        final User user = prepareUser();
        final User mapped = map(map(user));

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getUserId(), is(user.getUserId()));
        assertThat(mapped.getUsername(), is(user.getUsername()));
        assertThat(mapped.getAlias(), is(user.getAlias()));
        assertThat(mapped.getFirstname(), is(user.getFirstname()));
        assertThat(mapped.getLastname(), is(user.getLastname()));
        assertThat(mapped.getPerson(), is(user.getPerson()));
        assertThat(mapped.getStatus(), is(user.getStatus()));
        assertThat(mapped.getType(), is(user.getType()));
        assertThat(mapped.getPrivacy(), is(user.getPrivacy()));
        assertThat(mapped.getNotifications(), is(user.getNotifications()));
    }

    @Test
    public void testNullGroup() {
        final Group group = null;
        final Group mapped = map(map(group));

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testGroup() {
        final Group group = prepareGroup();
        final Group mapped = map(map(group));

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getGroupId(), is(group.getGroupId()));
        assertThat(mapped.getParentId(), is(group.getParentId()));
        assertThat(mapped.getGroupName(), is(group.getGroupName()));
        assertThat(mapped.getFullName(), is(group.getFullName()));
        assertThat(mapped.getListName(), is(group.getListName()));
        assertThat(mapped.getPrivateList(), is(group.getPrivateList()));
        assertThat(mapped.getPrivateListReplyTo(), is(group.getPrivateListReplyTo()));
        assertThat(mapped.getPublicList(), is(group.getPublicList()));
        assertThat(mapped.getPublicListReplyTo(), is(group.getPublicListReplyTo()));
        assertThat(mapped.getGroupType(), is(group.getGroupType()));
        assertThat(mapped.getDescription(), is(group.getDescription()));
        assertThat(mapped.getMonitoringLevel(), is(group.getMonitoringLevel()));
        assertThat(mapped.getCountry(), is(group.getCountry()));
    }

    @Test
    public void testNullRole() {
        final Role role = null;
        final Role mapped = map(map(role));

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testRole() {
        final Role role = prepareRole();
        final Role mapped = map(map(role));

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getRoleId(), is(role.getRoleId()));
        assertThat(mapped.getRoleName(), is(role.getRoleName()));
        assertThat(mapped.getPermissions(), is(role.getPermissions()));
    }

    @Test
    public void testNullUserGroup() {
        final UserGroup userGroup = null;
        final UserGroup mapped = map(map(userGroup));

        assertThat(mapped, is(nullValue()));
    }

    @Test
    public void testUserGroup() {
        final UserGroup userGroup = prepareUserGroup();
        final UserGroup mapped = map(map(userGroup));

        assertThat(mapped, is(not(nullValue())));
        assertThat(mapped.getUserGroupId(), is(userGroup.getUserGroupId()));
        assertThat(mapped.getUser(), is(userGroup.getUser()));
        assertThat(mapped.getGroup(), is(userGroup.getGroup()));
        assertThat(mapped.getRole(), is(userGroup.getRole()));
        assertThat(mapped.getTitle(), is(userGroup.getTitle()));
        assertThat(mapped.isOnPublicList(), is(userGroup.isOnPublicList()));
        assertThat(mapped.isOnPrivateList(), is(userGroup.isOnPrivateList()));
        assertThat(mapped.mayWriteToPrivateList(), is(userGroup.mayWriteToPrivateList()));
        assertThat(mapped.getMemberSince(), is(userGroup.getMemberSince()));
    }
}
