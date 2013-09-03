/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.UserNotificationEntityTest
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.entities.notifications.NotificationConsumerEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.setup.SpringConfig;
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
import java.util.List;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {SpringConfig.class})
@TransactionConfiguration(defaultRollback = true)
public class NotificationConsumerEntityTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testClassflow() {
        final AccessDao dao = new AccessJpaDao(entityManager);
        final UserEntity user = dao.findUserByUsername("austria@iaeste.at");
        assertThat(user.getUsername(), is("austria@iaeste.at"));

        final GroupEntity group = dao.findNationalGroup(user);
        assertNotNull(group);

        final NotificationConsumerEntity entity = new NotificationConsumerEntity();
        entity.setGroup(group);
        entity.setActive(true);
        entity.setName("Test consumer");
        entity.setClassName("net.iaeste.iws.ejb.notifications.consumer");

        final Query query = entityManager.createNamedQuery("notifications.findConsumersByActive");
        query.setParameter("active", true);
        final List<NotificationConsumerEntity> foundBefore = query.getResultList();

        entityManager.persist(entity);

        final List<NotificationConsumerEntity> foundAfter = query.getResultList();

        assertThat(foundAfter.size(), is(foundBefore.size()+1));
        assertThat(foundAfter.get(foundAfter.size()-1), is(entity));
    }
}
