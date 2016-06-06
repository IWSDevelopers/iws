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

import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.dtos.Address;
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
import net.iaeste.iws.ws.AuthenticationToken;
import net.iaeste.iws.ws.IwsError;

import java.util.EnumSet;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public final class TestUtils {

    private TestUtils() {
    }

    public static AuthenticationToken prepareToken(final String sessionKey, final String groupId) {
        final AuthenticationToken token = new AuthenticationToken();
        token.setToken(sessionKey);
        token.setGroupId(groupId);

        return token;
    }

    public static IwsError prepareIwsError(final IWSError error) {
        final IwsError ws = new IwsError();
        ws.setError(error.getError());
        ws.setDescription(error.getDescription());

        return ws;
    }

    public static UserGroup prepareUserGroup() {
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

    public static User prepareUser() {
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

    public static Group prepareGroup() {
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

    public static Role prepareRole() {
        final Role role = new Role();
        role.setRoleId(UUID.randomUUID().toString());
        role.setRoleName("Role Name");
        role.setPermissions(EnumSet.of(
                Permission.PROCESS_COMMITTEE,
                Permission.APPLY_FOR_OPEN_OFFER,
                Permission.FETCH_FILE));

        return role;
    }

    public static Person preparePerson() {
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

    public static Address prepareAddress() {
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

    public static Country prepareCountry() {
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
