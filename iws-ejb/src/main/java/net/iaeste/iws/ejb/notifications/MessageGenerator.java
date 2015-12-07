/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.notifications.MessageGenerator
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

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.exceptions.NotificationException;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class MessageGenerator {

    private static final String DIR = "templates/";
    private static final String MESSAGE = "Message.tpl";
    private static final String SUBJECT = "Subject.tpl";

    private final Settings settings;

    public MessageGenerator(final Settings settings) {
        this.settings = settings;
    }

    public Map<String, String> generate(final Map<NotificationField, String> fields, final NotificationType type) {
        final Map<String, String> result;

        switch (type) {
            case ACTIVATE_NEW_USER:
                result = generate("activateNewUser", fields);
                break;
            case RESET_PASSWORD:
                result = generate("resetPassword", fields);
                break;
            case RESET_SESSION:
                result = generate("resetSession", fields);
                break;
            case UPDATE_USERNAME:
                result = generate("updateUsername", fields);
                break;
            case NEW_GROUP_OWNER:
                result = generate("newGroupOwner", fields);
                break;
            default:
                throw new NotificationException("NotificationType " + type + " is not supported in this context.");
        }

        return result;
    }

    private Map<String, String> generate(final String template, final Map<NotificationField, String> input) {
        final Map<String, String> parseMap = generateParseMap(input);
        final Map<String, String> result = new HashMap<>(2);

        try {
            final String messageTemplate = readTemplate(template + MESSAGE);
            final String subjectTemplate = readTemplate(template + SUBJECT);

            result.put("title", parseTemplate(subjectTemplate, parseMap));
            result.put("body", parseTemplate(messageTemplate, parseMap));
        } catch (IOException | RuntimeException e) {
            throw new IWSException(IWSErrors.ERROR, "Error during processing template", e);
        }

        return result;
    }

    private static String readTemplate(final String name) throws IOException {
        try (InputStream stream = ClassLoader.getSystemResourceAsStream(DIR + name);
             InputStreamReader streamReader = new InputStreamReader(stream);
             BufferedReader reader = new BufferedReader(streamReader)) {

            final StringBuilder builder = new StringBuilder(16);
            String line = reader.readLine();

            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }

            return builder.toString();
        }
    }

    private Map<String, String> generateParseMap(final Map<NotificationField, String> input) {
        final Map<String, String> parseMap = new HashMap<>(input.size() + 1);

        for (final Map.Entry<NotificationField, String> entry : input.entrySet()) {
            parseMap.put(entry.getKey().name(), entry.getValue());
        }
        parseMap.put("baseUrl", settings.getBaseUrl());

        return parseMap;
    }

    private static String parseTemplate(final String template, final Map<String, String> parseMap) {
        String result = template;

        for (final Map.Entry<String, String> entry : parseMap.entrySet()) {
            result = result.replaceAll("\\$\\{" + entry.getKey() + "\\}", entry.getValue());
        }

        return result;
    }
}
