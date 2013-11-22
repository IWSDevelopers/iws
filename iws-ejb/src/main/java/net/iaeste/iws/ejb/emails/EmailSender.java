/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.emails.EmailSender
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb.emails;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.ejb.IwsSystemSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.Properties;

/**
 *   The sending information (port, addresses) will be injected using JNDI.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CastToConcreteClass, AccessOfSystemProperties, MethodMayBeStatic
 */
@MessageDriven(
        mappedName = "queue/iwsEmailQueue",
        activationConfig = @ActivationConfigProperty(
                propertyName = "destinationType",
                propertyValue = "javax.jms.Queue")
)
public class EmailSender implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(EmailSender.class);
    private final IwsSystemSetting iwsSystemSetting;

    /**
     * Default constructor
     *
     * Log message could be delated once we are sure it's working properly
     */
    public EmailSender() {
        log.info("starting EmailSender");
        iwsSystemSetting = IwsSystemSetting.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMessage(final Message message) {
        if (message instanceof ObjectMessage) {
            final ObjectMessage msg = (ObjectMessage) message;
            try {
                final Serializable object = msg.getObject();
                if (object instanceof EmailMessage) {
                    send((EmailMessage) object);
                } else {
                    throw new IWSException(IWSErrors.ERROR, "Not a proper e-mail message.");
                }
            } catch (JMSException e) {
                throw new IWSException(IWSErrors.ERROR, "Sending the email message failed.", e);
            }
        }
    }

    private void send(final EmailMessage msg) {
        final Session session = prepareSession();

        try {
            // Create a default MimeMessage object.
            final MimeMessage message = new MimeMessage(session);
            message.setFrom(prepareAddress(iwsSystemSetting.getSendingEmailAddress()));
            message.addRecipient(javax.mail.Message.RecipientType.TO, prepareAddress(msg.getTo()));
            message.setSubject(msg.getSubject());
            message.setText(msg.getMessage());

            log.info("Sending email message to " + msg.getTo() + " with body " + msg.getMessage());
            Transport.send(message);
        } catch (MessagingException e) {
            throw new IWSException(IWSErrors.ERROR, "Sending to '" + msg.getTo() + "' failed.", e);
        }
    }

    private Session prepareSession() {
        // Get system properties
        final Properties properties = System.getProperties();
        // Setup mail server
        properties.setProperty("mail.smtp.host", iwsSystemSetting.getSmtpAddress());
        properties.setProperty("mail.smtp.port", iwsSystemSetting.getSmtpPort());

        // Get the default Session object.
        return Session.getDefaultInstance(properties);
    }

    private InternetAddress prepareAddress(final String address) {
        try {
            return new InternetAddress(address);
        } catch (AddressException e) {
            throw new IWSException(IWSErrors.ERROR, "Invalid Internet Address.", e);
        }
    }
}
