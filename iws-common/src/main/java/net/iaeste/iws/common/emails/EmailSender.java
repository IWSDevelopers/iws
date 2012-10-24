/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.emails.EmailSender
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.common.emails;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.*;
import javax.jms.Message;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@MessageDriven(mappedName="iws-EmailQueue",
        activationConfig = { @ActivationConfigProperty(
                propertyName="destinationType", propertyValue="javax.jms.Queue")})
public class EmailSender implements MessageListener {
    String from = "iaeste@iaeste.net";
    String host = "localhost"; //TODO do we have a configuration file from which it's possible to get current host name
    String smtpServer = "localhost"; //TODO do we have a configuration file from which it's possible to get smtp server
    String smtpPort = "25"; //TODO do we have a configuration file from which it's possible to get smtp server

    @Override
    public void onMessage(Message message) {
        //To change body of implemented methods use File | Settings | File Templates.
        ObjectMessage msg = null;
        if(message instanceof ObjectMessage) {
            msg = (ObjectMessage)message;
            try {
                Object o = msg.getObjectProperty("emailMessage");
                if(!(o instanceof EmailMessage)) {
                    //TODO invalid message type, log it?
                } else {
                    send((EmailMessage)o);
                }
            } catch (JMSException e) {
                //TODO log that getting EmailMessage failed
            } catch (Exception e) {
                //TODO log or throw our exception
            }
        }
    }

    private void send(EmailMessage msg) {
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.setProperty("mail.smtp.host", smtpServer);
        properties.setProperty("mail.smtp.port", smtpPort);

        // Get the default Session object.
        javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties);

        try{
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            InternetAddress fromAddress = null;
            InternetAddress toAddress = null;
            try {
                fromAddress = new InternetAddress(from);
                toAddress = new InternetAddress(msg.getTo());
            } catch (AddressException e) {
                //TODO log error while preparing addresses
                return;
            }

            message.setFrom(fromAddress);
            message.addRecipient(javax.mail.Message.RecipientType.TO, toAddress);

            message.setSubject(msg.getSubject());
            message.setText(msg.getMessage());

            //Transport.send(message);
            //Log - message sent
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}