/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        query.setParameter("type", NotificationType.ACTIVATE_NEW_USER);
        final List<UserNotificationEntity> foundBefore = query.getResultList();

        final UserNotificationEntity entity = new UserNotificationEntity();
        entity.setUser(user);
        entity.setFrequency(NotificationFrequency.IMMEDIATELY);
        entity.setType(NotificationType.ACTIVATE_NEW_USER);

        entityManager.persist(entity);

        final List<UserNotificationEntity> foundAfter = query.getResultList();
        assertThat(foundAfter.size(), is(foundBefore.size() + 1));
        assertThat(foundAfter.get(foundAfter.size() - 1), is(entity));
    }
}
