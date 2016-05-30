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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Person;
import net.iaeste.iws.api.dtos.Role;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.MailReply;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.enums.UserType;
import net.iaeste.iws.api.util.Date;
import org.junit.Test;

import java.util.EnumSet;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public final class CommonMapperTest {

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

    // =========================================================================
    // Internal Preparation methods
    // =========================================================================

    private static UserGroup prepareUserGroup() {
        final UserGroup userGroup = new UserGroup();
        userGroup.setUserGroupId(UUID.randomUUID().toString());
        userGroup.setUser(prepareUser());
        userGroup.setGroup(prepareGroup());
        userGroup.setRole(prepareRole());
        userGroup.setTitle("Custom Title");
        userGroup.setOnPublicList(true);
        userGroup.setOnPrivateList(true);
        userGroup.setWriteToPrivateList(true);
        userGroup.setMemberSince(new Date("01-FEB-2014"));

        return userGroup;
    }

    private static User prepareUser() {
        final User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername("Username@domain.com");
        user.setAlias("Firstname.Lastname@system.com");
        user.setFirstname("First/Given Name");
        user.setLastname("Last/Family Name");
        user.setPerson(preparePerson());
        user.setStatus(UserStatus.ACTIVE);
        user.setType(UserType.UNKNOWN);
        user.setPrivacy(Privacy.PUBLIC);
        user.setNotifications(NotificationFrequency.DAILY);

        return user;
    }

    private static Group prepareGroup() {
        final Group group = new Group();
        group.setGroupId(UUID.randomUUID().toString());
        group.setParentId(UUID.randomUUID().toString());
        group.setGroupName("Group Name");
        group.setFullName("Full Group Name");
        group.setListName("Mailing.List@group.com");
        group.setPrivateList(false);
        group.setPrivateListReplyTo(MailReply.NO_REPLY);
        group.setPublicList(false);
        group.setPublicListReplyTo(MailReply.REPLY_TO_LIST);
        group.setGroupType(GroupType.ADMINISTRATION);
        group.setDescription("Group Description");
        group.setMonitoringLevel(MonitoringLevel.DETAILED);
        group.setCountry(prepareCountry());

        return group;
    }

    private static Role prepareRole() {
        final Role role = new Role();
        role.setRoleId(UUID.randomUUID().toString());
        role.setRoleName("Role Name");
        role.setPermissions(EnumSet.of(
                Permission.PROCESS_COMMITTEE,
                Permission.APPLY_FOR_OPEN_OFFER,
                Permission.FETCH_FILE));

        return role;
    }

    private static Person preparePerson() {
        final Person person = new Person();
        person.setNationality(prepareCountry());
        person.setAddress(prepareAddress());
        person.setAlternateEmail("Alternative@email.com");
        person.setPhone("1234567890");
        person.setMobile("9876543210");
        person.setFax("123487590");
        person.setBirthday(new Date("04-JAN-1966"));
        person.setGender(Gender.MALE);
        person.setUnderstoodPrivacySettings(true);
        person.setAcceptNewsletters(false);

        return person;
    }

    private static Address prepareAddress() {
        final Address address = new Address();
        address.setStreet1("First Street");
        address.setStreet2("Second Street");
        address.setPostalCode("12345");
        address.setCity("The City");
        address.setState("The State");
        address.setPobox("PO Box");
        address.setCountry(prepareCountry());

        return address;
    }

    private static Country prepareCountry() {
        final Country country = new Country();
        country.setCountryCode("CC");
        country.setCountryName("Country Name");
        country.setCountryNameFull("Full Country Name");
        country.setCountryNameNative("Native Country Name");
        country.setNationality("Nationality");
        country.setCitizens("Citizens");
        country.setPhonecode("+123");
        country.setCurrency(Currency.ALL);
        country.setLanguages("Language & Dialect");
        country.setMembership(Membership.LISTED);
        country.setMemberSince(1975);

        return country;
    }
}
