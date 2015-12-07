/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.notifications.MessageGeneratorTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb.notifications;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.exceptions.NotificationException;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import org.junit.Test;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class MessageGeneratorTest {

    @Test(expected = NotificationException.class)
    public void testGenerateGeneral() {
        final Settings settings = new Settings();
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);

        generator.generate(fields, NotificationType.GENERAL);
    }

    @Test
    public void testGenerateUpdateUsername() {
        final Settings settings = new Settings();
        settings.setBaseUrl("BASE_URL");
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);
        fields.put(NotificationField.CODE, "RESET_CODE");

        final Map<String, String> result = generator.generate(fields, NotificationType.UPDATE_USERNAME);
        assertThat(result.size(), is(2));
        assertThat(result.get("title"), is("IntraWeb Email Address Change Confirmation"));
        assertThat(result.get("body"), containsString("BASE_URL"));
        assertThat(result.get("body"), containsString("RESET_CODE"));
    }

    @Test(expected = NotificationException.class)
    public void testGenerateUsernameUpdated() {
        final Settings settings = new Settings();
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);

        generator.generate(fields, NotificationType.USERNAME_UPDATED);
    }

    @Test
    public void testGenerateActivateNewUser() {
        final Settings settings = new Settings();
        settings.setBaseUrl("BASE_URL");
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);
        fields.put(NotificationField.CODE, "USER_CODE");
        fields.put(NotificationField.EMAIL, "USER_EMAIL");
        fields.put(NotificationField.CLEARTEXT_PASSWORD, "CLEAR_TEXT_PASSWORD");

        final Map<String, String> result = generator.generate(fields, NotificationType.ACTIVATE_NEW_USER);
        assertThat(result.size(), is(2));
        assertThat(result.get("title"), is("Your IAESTE A.s.b.l. IntraWeb Account"));
        assertThat(result.get("body"), containsString("USER_CODE"));
        assertThat(result.get("body"), containsString("USER_EMAIL"));
        assertThat(result.get("body"), containsString("CLEAR_TEXT_PASSWORD"));
    }

    @Test(expected = NotificationException.class)
    public void testGenerateActivateSuspendedUser() {
        final Settings settings = new Settings();
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);

        generator.generate(fields, NotificationType.ACTIVATE_SUSPENDED_USER);
    }

    @Test(expected = NotificationException.class)
    public void testGenerateSuspendActiveUser() {
        final Settings settings = new Settings();
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);

        generator.generate(fields, NotificationType.SUSPEND_ACTIVE_USER);
    }

    @Test(expected = NotificationException.class)
    public void testGenerateNewUser() {
        final Settings settings = new Settings();
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);

        generator.generate(fields, NotificationType.NEW_USER);
    }

    @Test
    public void testGenerateResetPassword() {
        final Settings settings = new Settings();
        settings.setBaseUrl("BASE_URL");
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);
        fields.put(NotificationField.CODE, "RESET_CODE");

        final Map<String, String> result = generator.generate(fields, NotificationType.RESET_PASSWORD);
        assertThat(result.size(), is(2));
        assertThat(result.get("title"), is("IntraWeb Password Reset Request"));
        assertThat(result.get("body"), containsString("BASE_URL"));
        assertThat(result.get("body"), containsString("RESET_CODE"));
    }

    @Test
    public void testGenerateResetSession() {
        final Settings settings = new Settings();
        settings.setBaseUrl("BASE_URL");
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);
        fields.put(NotificationField.CODE, "RESET_CODE");

        final Map<String, String> result = generator.generate(fields, NotificationType.RESET_SESSION);
        assertThat(result.size(), is(2));
        assertThat(result.get("title"), is("IntraWeb Reset Session Request"));
        assertThat(result.get("body"), containsString("BASE_URL"));
        assertThat(result.get("body"), containsString("RESET_CODE"));
    }

    @Test(expected = NotificationException.class)
    public void testGenerateNewGroup() {
        final Settings settings = new Settings();
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);

        generator.generate(fields, NotificationType.NEW_GROUP);
    }

    @Test(expected = NotificationException.class)
    public void testGenerateChangeInGroupMembers() {
        final Settings settings = new Settings();
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);

        generator.generate(fields, NotificationType.CHANGE_IN_GROUP_MEMBERS);
    }

    @Test
    public void testGenerateNewGroupOwner() {
        final Settings settings = new Settings();
        settings.setBaseUrl("BASE_URL");
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);
        fields.put(NotificationField.GROUP_NAME, "GROUP_NAME");

        final Map<String, String> result = generator.generate(fields, NotificationType.NEW_GROUP_OWNER);
        assertThat(result.size(), is(2));
        assertThat(result.get("title"), is("IntraWeb Group Owner Assignment Notification"));
        assertThat(result.get("body"), containsString("GROUP_NAME"));
    }

    @Test(expected = NotificationException.class)
    public void testGenerateProcessEmailAlias() {
        final Settings settings = new Settings();
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);

        generator.generate(fields, NotificationType.PROCESS_EMAIL_ALIAS);
    }

    @Test(expected = NotificationException.class)
    public void testGenerateProcessMailingList() {
        final Settings settings = new Settings();
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);

        generator.generate(fields, NotificationType.PROCESS_MAILING_LIST);
    }

    @Test(expected = NotificationException.class)
    public void testGenerateUserActivated() {
        final Settings settings = new Settings();
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);

        generator.generate(fields, NotificationType.USER_ACTIVATED);
    }

    @Test(expected = NotificationException.class)
    public void testGenerateNewStudent() {
        final Settings settings = new Settings();
        final MessageGenerator generator = new MessageGenerator(settings);
        final Map<NotificationField, String> fields = new EnumMap(NotificationField.class);

        generator.generate(fields, NotificationType.NEW_STUDENT);
    }
}
