/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.notifications.NotificationEmailDelayedSender
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
import net.iaeste.iws.api.enums.NotificationDeliveryMode;
import net.iaeste.iws.api.enums.NotificationMessageStatus;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.utils.Observable;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.ejb.emails.EmailMessage;
import net.iaeste.iws.ejb.ffmq.MessageServer;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.entities.NotificationMessageEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.timewalker.ffmq3.FFMQConstants;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.List;

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
public class NotificationEmailDelayedSender implements Observer {

//    @Resource(mappedName = "iws-EmailQueue")
    private Queue queue;

//    @Resource(mappedName = "iws-QueueConnectionFactory")
    private QueueConnectionFactory queueConnectionFactory;

    private QueueConnection queueConnection = null;
    private QueueSender sender = null;
    private QueueSession session = null;

    private final NotificationDao dao;

    public NotificationEmailDelayedSender(final NotificationDao dao) {
        this.dao = dao;

        try {
            //FFMQ specific
            final Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, FFMQConstants.JNDI_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, "vm://"+ MessageServer.engineName);
            //connection using 'vm://' protocol should have better performance, if not working, use tcp connection instead
//            env.put(Context.PROVIDER_URL, "tcp://" + MessageServer.listenAddr + ":" + MessageServer.listenPort);
            final Context context = new InitialContext(env);

            queueConnectionFactory = (QueueConnectionFactory)context.lookup(FFMQConstants.JNDI_QUEUE_CONNECTION_FACTORY_NAME);
            // end FFMQ specific

            queueConnection = queueConnectionFactory.createQueueConnection();
            queueConnection.start();

            //FFMQ specific
            queue = (Queue)context.lookup(MessageServer.queueNameForIws);
            context.close();
            // end FFMQ specific

            session = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            sender = session.createSender(queue);
            //TODO added for FFMQ, keep it for glassfish?
            sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        } catch (NamingException|JMSException e) {
            throw new IWSException(IWSErrors.ERROR, "Queue sender (NotificationEmailDelayedSender) initialization failed.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Observable subject) {
        processMessages();
    }

    private void processMessages() {
        List<NotificationMessageEntity> messages = dao.findNotificationMessages(NotificationDeliveryMode.EMAIL, NotificationMessageStatus.NEW, new java.util.Date());
//        Map<UserEntity, List<NotificationMessageEntity>> groupedMessages = groupByUser(messages);
//        String subject = "IAESTE IW notification";
//
        for(NotificationMessageEntity message : messages) {
            dao.updateNotificationMessageStatus(message, NotificationMessageStatus.PROCESSING);

            try {
                ObjectMessage msg = session.createObjectMessage();
                EmailMessage emsg = new EmailMessage();
                emsg.setTo(message.getUser().getUserName());
                emsg.setSubject(message.getMessageTitle());
                emsg.setMessage(message.getMessage());
                msg.setObject(emsg);

                sender.send(msg);
                dao.updateNotificationMessageStatus(message, NotificationMessageStatus.SENT);
            } catch (JMSException e) {
                //do something, log...
            }
        }
    }

    private static Map<UserEntity, List<NotificationMessageEntity>> groupByUser(final List<NotificationMessageEntity> messages) {
        final Map<UserEntity, List<NotificationMessageEntity>> result = new HashMap<>(0);

        for (final NotificationMessageEntity message : messages) {
            final List<NotificationMessageEntity> userMessages;
            final UserEntity user = message.getUser();

            if (result.containsKey(user)) {
                userMessages = result.get(user);
            } else {
                userMessages = new ArrayList<>(1);
            }

            userMessages.add(message);
            result.put(user, userMessages);
        }

        return result;
    }
}
