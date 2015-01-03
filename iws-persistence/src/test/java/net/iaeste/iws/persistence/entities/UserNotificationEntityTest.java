/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.persistence.AccessDao;
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
 * @since   IWS 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { SpringConfig.class })
@TransactionConfiguration(defaultRollback = true)
public class UserNotificationEntityTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testClassflow() {
        final AccessDao dao = new AccessJpaDao(entityManager);
        final UserEntity user = dao.findActiveUserByUsername("austria@iaeste.at");
        assertThat(user.getUsername(), is("austria@iaeste.at"));
        final Query query = entityManager.createNamedQuery("notifications.findSettingByUserAndType");
        query.setParameter("id", user.getId());
        query.setParameter("type", NotificationType.ACTIVATE_USER);
        final List<UserNotificationEntity> foundBefore = query.getResultList();

        final UserNotificationEntity entity = new UserNotificationEntity();
        entity.setUser(user);
        entity.setFrequency(NotificationFrequency.IMMEDIATELY);
        entity.setType(NotificationType.ACTIVATE_USER);

        entityManager.persist(entity);

        final List<UserNotificationEntity> foundAfter = query.getResultList();
        assertThat(foundAfter.size(), is(foundBefore.size() + 1));
        assertThat(foundAfter.get(foundAfter.size() - 1), is(entity));
    }
}
