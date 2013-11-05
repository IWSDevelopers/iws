/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.notifications.consumers.NotificationImSender
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

import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.utils.Observable;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.jpa.NotificationJpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

/**
 * The Class requires an EJB framework to properly work. For this reason, large
 * parts of the code is commented out.
 *
 * This should probably go to the EJB module.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class NotificationImSender implements Observer {
    private Long id = null;
    private boolean initialized = false;

//    //JMS message queue
//    @Resource(mappedName = "iws-emailQueue")
//    private Queue queue;

//    @Resource(mappedName = "jms/QueueConnectionFactory")
//    private QueueConnectionFactory queueConnectionFactory;

//    QueueConnection queueConnection = null;
//    QueueSender sender = null;
//    QueueSession session = null;

    private NotificationDao dao;

    private static final Logger log = LoggerFactory.getLogger(NotificationImSender.class);

    public NotificationImSender(final EntityManager iwsEntityManager, final EntityManager mailingEntityManager, final Settings settings) {
//        //initialize jms message queue
//        QueueConnection queueConnection = null;
//        try {
//            queueConnection = queueConnectionFactory.createQueueConnection();
//            queueConnection.start();
//            session = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
//            sender = session.createSender(queue);
//        } catch (JMSException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void init(final EntityManager iwsEntityManager, final EntityManager mailingEntityManager, final Settings settings) {
        dao = new NotificationJpaDao(iwsEntityManager);
        initialized = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Observable subject) {
        if (initialized) {
            processMessages();
        } else {
            log.warn("Update called for uninitialized observer");
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

    private void processMessages() {
//        List<NotificationMessageEntity> messages = dao.findNotificationMessages(NotificationDeliveryMode.IM, NotificationMessageStatus.NEW, new Date());
//        //TODO: do the grouping somewhere/somehow
//
//        for(NotificationMessageEntity message : messages) {
//            dao.updateNotificationMessageStatus(message, NotificationMessageStatus.PROCESSING);
//
//            try {
//                ObjectMessage msg = session.createObjectMessage();
//                msg.setObjectProperty("recipient", message.getUser().getUserName());
//                msg.setStringProperty("message", message.getMessage());
//
//                sender.send(msg);
//                dao.updateNotificationMessageStatus(message, NotificationMessageStatus.SENT);
//            } catch (JMSException e) {
//                //do something, log...
//            }
//        }
    }
}
