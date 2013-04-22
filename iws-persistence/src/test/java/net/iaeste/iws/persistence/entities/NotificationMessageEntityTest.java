/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.NotificationMessageEntityTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.entities;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.enums.NotificationMessageStatus;
import net.iaeste.iws.api.enums.NotificationType;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.setup.SpringConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {SpringConfig.class})
@TransactionConfiguration(defaultRollback = true)
public class NotificationMessageEntityTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    @Ignore("Ignored 20130422 by Kim - Reason: The test is unstable!")
    public void testClassflow() {
        final AccessDao dao = new AccessJpaDao(entityManager);
        final UserEntity user = dao.findUserByCredentials("austria", "7112733729f24775a6e82d0a6ad7c8106643ad438fef97e33e069f23a2167266");
        assertThat(user.getUserName(), is("austria"));

        final NotificationMessageEntity entity = new NotificationMessageEntity();
        entity.setMessage("message text");
        entity.setProcessAfter(new Date());
        entity.setStatus(NotificationMessageStatus.NEW);
        entity.setType(NotificationType.EMAIL);

        entity.setUser(user);
        entityManager.persist(entity);

        final Query query = entityManager.createNamedQuery("notifications.findMessagesByTypeStatusAndDate");
        query.setParameter("type", NotificationType.EMAIL);
        query.setParameter("status", NotificationMessageStatus.NEW);
        query.setParameter("date", new Date());
        final List<NotificationMessageEntity> found = query.getResultList();

        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(entity));
    }

    @Test
    @Transactional
    public void testUpdateQuery() {
        final AccessDao dao = new AccessJpaDao(entityManager);
        final UserEntity user = dao.findUserByCredentials("austria", "7112733729f24775a6e82d0a6ad7c8106643ad438fef97e33e069f23a2167266");
        assertThat(user.getUserName(), is("austria"));

        final NotificationMessageEntity entity = new NotificationMessageEntity();
        entity.setMessage("message text");
        entity.setProcessAfter(new Date());
        entity.setStatus(NotificationMessageStatus.NEW);
        entity.setType(NotificationType.EMAIL);

        entity.setUser(user);
        entityManager.persist(entity);

        final Query query1 = entityManager.createNamedQuery("notifications.updateStatus");
        query1.setParameter("id", entity.getId());
        query1.setParameter("status", NotificationMessageStatus.PROCESSING);
        final int affectedRows = query1.executeUpdate();
        assertThat(affectedRows, is(1));

        final Query query2 = entityManager.createNamedQuery("notifications.findMessagesByTypeStatusAndDate");
        query2.setParameter("type", NotificationType.EMAIL);
        query2.setParameter("status", NotificationMessageStatus.PROCESSING);
        query2.setParameter("date", new Date());

        final List<NotificationMessageEntity> found = query2.getResultList();
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(entity));
    }
}
