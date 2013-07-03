/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.notifications.NotificationEmailSender
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
import net.iaeste.iws.common.utils.Observable;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.ejb.emails.EmailMessage;
import net.iaeste.iws.ejb.ffmq.MessageServer;
import net.iaeste.iws.persistence.entities.NotificationMessageEntity;
import net.timewalker.ffmq3.FFMQConstants;
import org.apache.log4j.Logger;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * The Class requires an EJB framework to properly work. For this reason, large
 * parts of the code is commented out.
 *
 * This should probably go to the EJB module. It needs to subscribe to the NotificationManager.
 * If the instance of the NotificationManager can be access from EJB, there is no reason against moving it.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection ObjectAllocationInLoop
 */
public class NotificationEmailSender implements Observer, NotificationDirectEmailSender {

    private static final Logger LOG = Logger.getLogger(NotificationEmailSender.class);

//    @Resource(mappedName = "iws-EmailQueue")
    private Queue queue;

//    @Resource(mappedName = "iws-QueueConnectionFactory")
    private QueueConnectionFactory queueConnectionFactory;

    private QueueConnection queueConnection = null;
    private QueueSender queueSender = null;
    private QueueSession queueSession = null;

    public NotificationEmailSender() {
        try {
            //FFMQ specific
            final Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, FFMQConstants.JNDI_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, "vm://"+ MessageServer.engineName);
            //connection using 'vm://' protocol should have better performance, if not working, use tcp connection instead
//            env.put(Context.PROVIDER_URL, "tcp://" + MessageServer.listenAddr + ":" + MessageServer.listenPort);
            final Context context = new InitialContext(env);

            queueConnectionFactory = (QueueConnectionFactory)context.lookup(FFMQConstants.JNDI_QUEUE_CONNECTION_FACTORY_NAME);
            queue = (Queue)context.lookup(MessageServer.queueNameForIws);
            context.close();
            // end FFMQ specific

            queueConnection = queueConnectionFactory.createQueueConnection();
            queueConnection.start();
            queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queueSender = queueSession.createSender(queue);
            queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        } catch (NamingException|JMSException e) {
            throw new IWSException(IWSErrors.ERROR, "Queue sender (NotificationEmailSender) initialization failed.", e);
        }
    }

    /**
     * Method for unsubscibing from queue and closing connection
     */
    public void stop() {
        try {
            queueSender.close();
            queueSession.close();
            queueConnection.stop();
        } catch (JMSException e) {
            throw new IWSException(IWSErrors.ERROR, "Queue recipient stopping failed.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Observable subject) {
        processMessages();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(final NotificationMessageEntity message) {
        try {
            final ObjectMessage msg = queueSession.createObjectMessage();
            final EmailMessage emsg = new EmailMessage();
            emsg.setTo(message.getUser().getUserName());
            emsg.setSubject(message.getMessageTitle());
            emsg.setMessage(message.getMessage());
            msg.setObjectProperty("emailMessage", emsg);

            queueSender.send(msg);
        } catch (JMSException ignored) {
            LOG.error("Error when sending message to the queue");
        }
    }

    private void processMessages() {
        //TODO pavel: I tried two approaches: one is the current implematation with the NotificationDirectEmailSender
        //            the second is to implement public method in NotificationManager which returns a list with messages
        //            to be sent. The NotificationManager's instance is the parameter of the update(Observable) method.
        //            None of these approaches is ideal, I kept the one with interface for now.
        //get list of messages to be sent immediately, this needs a connection to the notification manager
        //which instance I have as the parameter of the update(Observable) method
    }
}
