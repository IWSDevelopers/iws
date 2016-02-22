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
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.utils.HashcodeGenerator;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.setup.SpringConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {SpringConfig.class})
public class SessionEntityTest {

    private AccessDao dao = null;
    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void before() {
        dao = new AccessJpaDao(entityManager);
    }

    @Test
    @Transactional
    public void testSessionEntity() {
        final Settings settings = new Settings();
        final HashcodeGenerator generator = new HashcodeGenerator(settings);
        final UserEntity user = new UserEntity();
        final SessionEntity session = new SessionEntity();
        final String key = generator.generateHash("User Password, Date, IPNumber, and more", "");
        user.setUsername("alfa");
        user.setAlias("alias");
        user.setPassword(generator.generateHash("beta", ""));
        user.setSalt(UUID.randomUUID().toString());
        user.setFirstname("Alpha");
        user.setLastname("Beta");
        session.setSessionKey(key);
        session.setUser(user);
        dao.persist(user);
        dao.persist(session);

        final AuthenticationToken token = new AuthenticationToken(key);
        final SessionEntity found = dao.findActiveSession(token);
        assertThat(found, is(not(nullValue())));
    }

    @Test
    @Transactional
    public void testSessionJPAStorage() throws Exception {
        final Settings settings = new Settings();
        final HashcodeGenerator generator = new HashcodeGenerator(settings);
        final String key = generator.generateHash("This is the test string to build the SHA Hash on.", "");
        final SessionEntity entity = new SessionEntity();
        final Query userQuery = entityManager.createNamedQuery("user.findById");
        userQuery.setParameter("id", 1L);
        final List<UserEntity> found = userQuery.getResultList();
        entity.setSessionKey(key);
        entity.setUser(found.get(0));
        entityManager.persist(entity);

        final Query query = entityManager.createNamedQuery("session.findByToken");
        query.setParameter("key", key);
        final List<SessionEntity> entities = (List<SessionEntity>) query.getResultList();

        if (entities.isEmpty()) {
            fail("This should not happen!");
        } else {
            final SessionEntity result = entities.get(0);
            assertThat(result.getSessionKey(), is(key));
        }
    }
}
