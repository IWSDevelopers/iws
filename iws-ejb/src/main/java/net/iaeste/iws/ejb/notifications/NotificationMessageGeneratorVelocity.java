/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.notifications.NotificationMessageGeneratorVelocity
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
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.common.exceptions.NotificationException;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationType;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * There is a tool for validating Velocity's templates. Could we add it to our toolchain to validate them?
 * http://code.google.com/p/velocity-validator/
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class NotificationMessageGeneratorVelocity implements NotificationMessageGenerator {

    private final String TEMPLATE_DIR = "velocity_templates";
    private final String USER_TEMPLATE_DIR = TEMPLATE_DIR + "/user";
    private Settings settings;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSettings(final Settings settings) {
        this.settings = settings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> generateFromTemplate(final Map<NotificationField, String> fields, final NotificationType type) {
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

    private static Map<String, String> generate(final String dir, final String templateName, final String titleTemplateName, final VelocityContext input) {
        try {
            final Map<String, String> result = new HashMap<>(2);
            final VelocityEngine ve = new VelocityEngine();

            final Properties p = new Properties();
            //setting VelocityEngine properties; p.setProperty(prop, value);
            //p.setProperty("file.resource.loader.path", dir);
            //TODO verify it can load template
            p.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
//            p.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            p.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init(p);

            Template template = ve.getTemplate(dir + '/' + templateName);

            StringWriter output = new StringWriter();
            template.merge(input, output);
            output.flush();
            result.put("body", output.toString());

            //TODO not the fastest generating of message title
            template = ve.getTemplate(dir + '/' + titleTemplateName);
            output = new StringWriter();
            template.merge(input, output);
            output.flush();
            result.put("title", output.toString());

            return result;
        } catch (ResourceNotFoundException e) {
            throw new IWSException(IWSErrors.ERROR, "The requested template can't be found", e);
        } catch (ParseErrorException e) {
            throw new IWSException(IWSErrors.ERROR, "Error occured when parsing the template", e);
        } catch (Exception e) {
            throw new IWSException(IWSErrors.ERROR, "Error during processing template", e);
        }
    }

    private Map<String, String> processUser(final UserEntity user, final NotificationType type) {
        final String templateName;
        final String titleTemplateName;
        switch (type) {
            case ACTIVATE_USER:
                templateName = "activateUser.vm";
                titleTemplateName = "activateUserTitle.vm";
                break;
            case RESET_PASSWORD:
                templateName = "resetPassword.vm";
                titleTemplateName = "resetPasswordTitle.vm";
                break;
            case RESET_SESSION:
                templateName = "resetSession.vm";
                titleTemplateName = "resetSessionTitle.vm";
                break;
            default:
                throw new NotificationException("NotificationType " + type + " is not supported in this context.");
        }

        final VelocityContext input = new VelocityContext();
        input.put("userObject", user);

        return generate(USER_TEMPLATE_DIR, templateName, titleTemplateName, input);
    }
}
