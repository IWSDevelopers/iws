/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.ejb.emails.MessageField;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class MessageGenerator {

    private static final String DIR = "/templates/";
    private static final String MESSAGE = "Message.tpl";
    private static final String SUBJECT = "Subject.tpl";
    private static final Pattern PATTER_NEWLINE = Pattern.compile("\\n");

    private final Settings settings;

    public MessageGenerator(final Settings settings) {
        this.settings = settings;
    }

    public Map<MessageField, String> generate(final Map<NotificationField, String> fields, final NotificationType type) {
        final Map<MessageField, String> result;

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

    private Map<MessageField, String> generate(final String template, final Map<NotificationField, String> input) {
        final Map<String, String> parseMap = generateParseMap(input);
        final Map<MessageField, String> result = new EnumMap<>(MessageField.class);

        try {
            final String subjectTemplate = PATTER_NEWLINE.matcher(readTemplate(template + SUBJECT)).replaceAll("");
            final String messageTemplate = readTemplate(template + MESSAGE);

            result.put(MessageField.SUBJECT, parseTemplate(subjectTemplate, parseMap));
            result.put(MessageField.MESSAGE, parseTemplate(messageTemplate, parseMap));
        } catch (IOException | RuntimeException e) {
            throw new IWSException(IWSErrors.ERROR, "Error during processing template", e);
        }

        return result;
    }

    /**
     * Simplified template reader, which is simply reading the content byte for
     * byte and returning it.
     *
     * @param name The name of the template to read
     * @return Template
     * @throws IOException if unable to read the template
     */
    private static String readTemplate(final String name) throws IOException {
        // Third iteration of the template reader. NIO won't work, as the
        // default Java VFS for dealing with Zip files (JAR's) is rather bad.
        // And the Path Object cannot differentiate between Files & Jar's.
        // Using a Stream, which is then the only option left - requires that
        // the lines must be correctly read, but using a buffered reader will
        // result in the loss of newline information. Adding a third party
        // library to deal with this simply thing seems is overkill. So
        // instead, the old Scanner trick is applied.
        // See: http://web.archive.org/web/20140531042945/https://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        try (InputStream stream = MessageGenerator.class.getResourceAsStream(DIR + name);
             Scanner streamScanner = new Scanner(stream);
             Scanner scanner = streamScanner.useDelimiter("\\A")) {
            return scanner.next();
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
