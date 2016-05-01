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
package net.iaeste.iws.persistence;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.persistence.entities.SessionEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.setup.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = SpringConfig.class)
public class AccessDaoTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testSession() {
        final AccessDao dao = new AccessJpaDao(entityManager, new Settings());
        final String key = "12345678901234567890123456789012";
        final UserEntity user = dao.findActiveUserByUsername("austria@iaeste.at");

        // Create a new Session for a user, and save it in the database
        final SessionEntity entity = new SessionEntity();
        entity.setSessionKey(key);
        entity.setUser(user);
        dao.persist(entity);

        final DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS", IWSConstants.DEFAULT_LOCALE);

        // Find the newly created Session, deprecate it, and save it again
        final SessionEntity found = dao.findActiveSession(user);
        assertThat(found, is(not(nullValue())));
        found.setDeprecated(formatter.format(new Date()));
        dao.persist(found);

        // Now, we should not be able to find it
        final SessionEntity notFound = dao.findActiveSession(user);
        assertThat(notFound, is(nullValue()));
    }
}
