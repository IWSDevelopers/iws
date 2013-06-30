/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.ejb.notifications.NotificationMessageGenerator
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

import freemarker.template.Configuration;
import freemarker.template.Template;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.core.notifications.NotificationMessageGenerator;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.exceptions.NotificationException;
import net.iaeste.iws.persistence.notification.Notifiable;
import net.iaeste.iws.persistence.notification.NotificationType;

import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class NotificationMessageGeneratorFreemarker implements NotificationMessageGenerator {
    /*
     * TEMPLATE_DIR refers to the place where our templates are located, i.e. in the freemarker_templates package,
     * relative to the root of the current class
     */
    private final String TEMPLATE_DIR = "freemarker_templates";
    private final String USER_TEMPLATE_DIR = TEMPLATE_DIR + "/user";

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> generateFromTemplate(final Notifiable obj, final NotificationType type) {
//        switch (obj.getNotificationSubject()) {
//            case USER:
//                return processUser((UserEntity)obj, type);
//            case GROUP:
//                throw new NotImplementedException("Notifications for group are not implemented");
//            case OFFER:
//                throw new NotImplementedException("Notifications for offer are not implemented");
//            default:
                throw new IWSException(IWSErrors.ERROR, "Unsupported object for notification");
//        }
    }

    private Map<String, String> generate(final String dir, final String templateName, final String titleTemplateName, final Map<String, Object> input) {
        try {
            final Map<String, String> result = new HashMap<>();

            final Configuration cfg = new Configuration();
            cfg.setClassForTemplateLoading(this.getClass(), dir);

            Template template = cfg.getTemplate(templateName);

            Writer output = new StringWriter();
            template.process(input, output);
            output.flush();
            result.put("body", output.toString());

            template = cfg.getTemplate(titleTemplateName);

            output = new StringWriter();
            template.process(input, output);
            output.flush();
            result.put("title", output.toString());

            return result;
        } catch (FileNotFoundException e) {
            throw new IWSException(IWSErrors.ERROR, "Can't find one of these templates: " + templateName + ", " + titleTemplateName, e);
        } catch (Exception e) {
            throw new IWSException(IWSErrors.ERROR, "Error during processing template", e);
        }
    }

    private Map<String, String> processUser(final UserEntity user, final NotificationType type) {
        final String templateName;
        final String titleTemplateName;
        switch (type) {
            case ACTIVATE_USER:
                templateName = "activateUser.ftl";
                titleTemplateName = "activateUserTitle.ftl";
                break;
            case RESET_PASSWORD:
                templateName = "resetPassword.ftl";
                titleTemplateName = "resetPasswordTitle.ftl";
                break;
            case RESET_SESSION:
                templateName = "resetSession.ftl";
                titleTemplateName = "resetSessionTitle.ftl";
                break;
            default:
                throw new NotificationException("NotificationType " + type + " is not supported in this context.");
        }

        final Map<String, Object> input = new HashMap<>();
        input.put("userObject", user);

        return generate(USER_TEMPLATE_DIR, templateName, titleTemplateName, input);
    }
}
