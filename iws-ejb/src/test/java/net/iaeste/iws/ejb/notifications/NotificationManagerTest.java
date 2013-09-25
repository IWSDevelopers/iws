/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.ExchangeBeanTest
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import net.iaeste.iws.ejb.ffmq.MessageServer;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.notifications.NotificationConsumerEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.NotificationJpaDao;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * This test uses FFMQ server, if it fails or is unstable, ignore it. Or to use mocking instead?
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class NotificationManagerTest {

//    Comment this test since it is using FFMQ which needs different JNDI lookups and I don't know how to mock entity managers & our DAOs
//    @Test
//    @Ignore("I can't write one code for both Glassfish and FFMQ, the lookup context used in NotificationEmailSender is different")
//    public void loadConsumsers() {
//        final NotificationDao dao = mock(NotificationJpaDao.class);
//        final AccessDao accessDao = mock(AccessJpaDao.class);
//        final NotificationConsumerEntity consumer = new NotificationConsumerEntity(new GroupEntity("dummyGroup"), "Consumer1", "net.iaeste.iws.ejb.notifications.consumers.NotificationEmailSender");
//        final List<NotificationConsumerEntity> consumers = new ArrayList<>(1);
//        consumers.add(consumer);
//        when(dao.findActiveNotificationConsumers()).thenReturn(consumers);
//
//        final NotificationManager notificationManager = new NotificationManager(dao, accessDao, null, false);
//        final MessageServer messageServer = new MessageServer();
//        messageServer.run();
//        while(!messageServer.isDeployed()) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ignored) { }
//        }
//        notificationManager.startupConsumers();
//        messageServer.shutdown();
//        assertThat(notificationManager.getObserversCount(), is(1));
//    }
}
