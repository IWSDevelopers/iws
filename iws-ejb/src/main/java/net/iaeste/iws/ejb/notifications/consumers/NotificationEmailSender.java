/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.notifications.consumers.NotificationEmailSender
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb.notifications.consumers;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.common.utils.Observable;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.ejb.emails.EmailMessage;
import net.iaeste.iws.ejb.ffmq.MessageServer;
import net.iaeste.iws.ejb.notifications.NotificationMessageGenerator;
import net.iaeste.iws.ejb.notifications.NotificationMessageGeneratorFreemarker;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserNotificationEntity;
import net.iaeste.iws.persistence.views.NotificationJobTasksView;
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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * The Class requires an EJB framework to properly work. For this reason, large
 * parts of the code is commented out.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection ObjectAllocationInLoop
 */
public class NotificationEmailSender implements Observer {
    private Long id = null;
    private final NotificationMessageGenerator messageGenerator;
    private final NotificationDao dao;

    private static final Logger LOG = Logger.getLogger(NotificationEmailSender.class);

//    @Resource(mappedName = "iws-EmailQueue")
    private Queue queue;

//    @Resource(mappedName = "iws-QueueConnectionFactory")
    private QueueConnectionFactory queueConnectionFactory;

    private QueueConnection queueConnection = null;
    private QueueSender sender = null;
    private QueueSession session = null;

    public NotificationEmailSender(final NotificationDao dao) {
        this.dao = dao;
        //TODO inject message generator?
        this.messageGenerator = new NotificationMessageGeneratorFreemarker();

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
            throw new IWSException(IWSErrors.ERROR, "Queue sender (NotificationEmailSender) initialization failed.", e);
        }
    }

    /**
     * Method for unsubscibing from queue and closing connection
     */
    public void stop() {
        try {
            sender.close();
            session.close();
            queueConnection.stop();
        } catch (JMSException e) {
            throw new IWSException(IWSErrors.ERROR, "Queue recipient stopping failed.", e);
        }
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Observable subject) {
        processMessages();
    }

    private void processMessages() {
        final List<NotificationJobTasksView> jobTasks = dao.findUnprocessedNotificationJobTaskByConsumerId(id);
        for (final NotificationJobTasksView jobTask : jobTasks) {
            try {
                final ByteArrayInputStream inputStream = new ByteArrayInputStream(jobTask.getObject());
                final ObjectInputStream objectStream = new ObjectInputStream(inputStream);
                final Notifiable notifiable = (Notifiable) objectStream.readObject();
                boolean processed = false;
                if (notifiable != null) {
                    processTask(notifiable, jobTask.getNotificationType());
                    processed = true;
                }
                dao.updateNotificationJobTask(jobTask.getId(), processed, jobTask.getattempts()+1);
            } catch (IOException |ClassNotFoundException ignored) {
                //TODO write to log and skip the task or throw an exception?
            }
        }
    }

    private boolean processTask(final Notifiable notifiable, final NotificationType type) {
        //TODO marking task as processed depending on the successful sending to all recepients might be a problem. if it
        //     fails for one user, even those already sent users will be included during next processing. Just failed user
        //     NotificationType and message could be saved for furthere processing/investigation

        boolean ret = false;
        final List<UserEntity> recipients = getRecipients(notifiable, type);
        for (final UserEntity recipient : recipients) {
            final UserNotificationEntity userSetting = dao.findUserNotificationSetting(recipient, type);
            //Processing of other notification than 'IMMEDIATELY' ones will be triggered by a timer and all required information
            //should be get from DB directly according to the NotificationType
            if (userSetting.getFrequency() == NotificationFrequency.IMMEDIATELY) {
                try {
                    final ObjectMessage msg = session.createObjectMessage();
                    final EmailMessage emsg = new EmailMessage();
                    emsg.setTo(recipient.getUserName());
                    final Map<String, String> messageData = messageGenerator.generateFromTemplate(notifiable, type);
                    emsg.setSubject(messageData.get("title"));
                    emsg.setMessage(messageData.get("body"));
                    msg.setObject(emsg);

                    sender.send(msg);
                    ret = true;
                } catch (JMSException e) {
                    //do something, log or exception?
                }
            }
        }
        return ret;
    }

    //TODO probably not necessary to have the whole UserEntity, maybe just List<string> (emails) would be enough
    //     for the current notification processing
    private List<UserEntity> getRecipients(final Notifiable obj, final NotificationType type) {
        final List<UserEntity> result = new ArrayList<>();
        switch (type) {
            case ACTIVATE_USER:
            case RESET_PASSWORD:
            case RESET_SESSION:
            case UPDATE_USERNAME:
                result.add((UserEntity)obj);
                break;
        }
        return result;
    }

}
