/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.notifications.NotificationManager
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

import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.notifications.NotificationConsumerEntity;
import net.iaeste.iws.persistence.entities.notifications.NotificationJobEntity;
import net.iaeste.iws.persistence.entities.notifications.NotificationJobTaskEntity;
import net.iaeste.iws.persistence.jpa.NotificationJpaDao;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Notes; It is good to see the notification system evolving. There is just a
 * couple of things that has to be clarified. One is that the actual handling
 * of sending, should be done in different ways. If users request that
 * information is send at specific intervals, daily, weekly, etc. then the
 * handling should be done via a "cron" job. That is a Timer job in EJB, or
 * perhaps via an external queuing system like Quartz.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CastToConcreteClass, ChainOfInstanceofChecks, ObjectAllocationInLoop
 */
public final class NotificationManager implements Notifications {
    private static final Logger LOG = Logger.getLogger(NotificationManager.class);

    private final EntityManager iwsEntityManager;
    private final EntityManager mailingEntityManager;
    private final Settings settings;

    private final NotificationDao dao;

    private final NotificationMessageGenerator messageGenerator;
    private final List<Observer> observers = new ArrayList<>(10);

    private final boolean hostedInBean;

    public NotificationManager(final EntityManager iwsEntityManager, final EntityManager mailingEntityManager, final Settings settings, final NotificationMessageGenerator messageGenerator, final boolean hostedInBean) {
        this.iwsEntityManager = iwsEntityManager;
        this.mailingEntityManager = mailingEntityManager;
        this.settings = settings;
        this.messageGenerator = messageGenerator;
        this.hostedInBean = hostedInBean;

        dao = new NotificationJpaDao(iwsEntityManager);
    }

    /**
     * Startup notification manager - load from DB registered notification consumers and subcribe them to the manager
     */
    public void startupConsumers() {
        final List<NotificationConsumerEntity> consumers = dao.findActiveNotificationConsumers();
        final NotificationConsumerClassLoader classLoader = new NotificationConsumerClassLoader();

        for (final NotificationConsumerEntity consumer : consumers) {
            final Observer observer = classLoader.findConsumerClass(consumer.getClassName(), iwsEntityManager, mailingEntityManager, settings);
            observer.setId(consumer.getId());
            addObserver(observer);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processJobs() {
        final List<NotificationJobEntity> unprocessedJobs = dao.findUnprocessedNotificationJobs();
        if (!unprocessedJobs.isEmpty()) {
            for (final NotificationJobEntity job : unprocessedJobs) {
                prepareJobTasks(job);

                job.setNotified(true);
                job.setModified(new Date());
                dao.persist(job);
            }
//            notifyObservers();
        }
        //TODO call observers even there is no new job. however, there is probably some tasks in the db... can be removed once the db (transaction?) issue is solved
        notifyObservers();
    }

    private void prepareJobTasks(final NotificationJobEntity job) {
        for (final Observer observer : observers) {
            final NotificationConsumerEntity consumer = dao.findNotificationConsumerById(observer.getId());
            final NotificationJobTaskEntity task = new NotificationJobTaskEntity(job, consumer);
            dao.persist(task);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final Authentication authentication, final Notifiable obj, final NotificationType type) {
        // Save the general information about the Object to be notified.
//        final List<UserEntity> users = getRecipients(obj, type);

//        final Map<String, String> messageTexts = messageGenerator.generateFromTemplate(obj, type);
//        for (final UserEntity user : users) {
            //get user settings
            //TODO user should have permanent entry in the notification setting to receive messages about NotificationSubject.USER immediately
//            final UserNotificationEntity userNotification = dao.findUserNotificationSetting(user, type);
//
//            if (userNotification != null) {
//                final NotificationMessageEntity message = new NotificationMessageEntity();
//                message.setUser(user);
//                message.setStatus(NotificationMessageStatus.NEW);
//                message.setProcessAfter(getNotificationTime(userNotification.getFrequency()));
//                only immediate messages for now
//                message.setProcessAfter(new Date().toDate());
//                message.setNotificationDeliveryMode(NotificationDeliveryMode.EMAIL);

//                message.setMessage(messageTexts.get("body"));
//                message.setMessageTitle(messageTexts.get("title"));
//
//                dao.persist(message);
//            }
//        }

//        notifyObservers();
        if (obj != null) {
            try {
                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                final ObjectOutputStream objectStream = new ObjectOutputStream(outputStream);
                final Map<NotificationField, String> fields = obj.prepareNotifiableFields(type);
                objectStream.writeObject(fields);
                final byte[] bytes = outputStream.toByteArray();
                final NotificationJobEntity job = new NotificationJobEntity(type, bytes);
                dao.persist(job);
                if (!hostedInBean) {
                    processJobs();
                }
            } catch (IWSException e) {
                LOG.error("Preparing notification job failed", e);
            } catch (IOException ignored) {
                //TODO write to log and skip the task or throw an exception?
//                LOG.warn("Serializing of Notifiable instance for NotificationType " + type + " failed", ignored);
                LOG.warn("Serializing of Notifiable instance for NotificationType " + type + " failed");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final UserEntity user) {
//        final List<UserEntity> users = getRecipients(user, NotificationType.RESET_PASSWORD);
//        if(users.size()==1) {
//            final Map<String, String> messageTexts = messageGenerator.generateFromTemplate(user, NotificationType.RESET_PASSWORD);
//            final NotificationMessageEntity message = new NotificationMessageEntity();
//            message.setStatus(NotificationMessageStatus.NEW);
//                message.setProcessAfter(getNotificationTime(userNotification.getFrequency()));
            //only immediate messages for now
//            message.setProcessAfter(new Date().toDate());
//
//            message.setMessage(messageTexts.get("body"));
//            message.setMessageTitle(messageTexts.get("title"));
//
//            dao.persist(message);
//            notifyObservers();
//        } else {
            //write to log?
//        }

        if (user != null) {
            try {
                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                final ObjectOutputStream objectStream = new ObjectOutputStream(outputStream);
                final Map<NotificationField, String> fields = user.prepareNotifiableFields(NotificationType.RESET_PASSWORD);
                objectStream.writeObject(fields);
                final byte[] bytes = outputStream.toByteArray();
                final NotificationJobEntity job = new NotificationJobEntity(NotificationType.RESET_PASSWORD, bytes);
                dao.persist(job);
                if (!hostedInBean) {
                    processJobs();
                }
            } catch (IWSException e) {
                LOG.error("Preparing notification job failed", e);
            } catch (IOException ignored) {
                //TODO write to log and skip the task or throw an exception?
    //            LOG.warn("Serializing of Notifiable instance for NotificationType " + type + " failed", ignored);
                LOG.warn("Serializing of Notifiable instance for NotificationType.RESET_PASSWORD failed");
            }
        }
    }

    @Override
    public void addObserver(final Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(final Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (final Observer observer : observers) {
            observer.update(this);
        }
    }

    public int getObserversCount() {
        return observers.size();
    }


}
