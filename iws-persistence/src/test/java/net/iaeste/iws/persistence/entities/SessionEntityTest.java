/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence)
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {SpringConfig.class})
public class SessionEntityTest {

    private AccessDao dao;
    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void before() {
        dao = new AccessJpaDao(entityManager);
    }

    @Test
    @Transactional
    public void testSessionEntity() {
        final UserEntity user = new UserEntity();
        final SessionEntity session = new SessionEntity();
        final String key = HashcodeGenerator.generateSHA512("User Password, Date, IPNumber, and more");
        user.setUserName("alfa");
        user.setPassword(HashcodeGenerator.generateSHA256("beta"));
        session.setSessionKey(key);
        session.setUser(user);
        dao.persist(user);
        dao.persist(session);
    }

    @Test
    @Transactional
    public void testSessionJPAStorage() throws Exception {
        final String key = HashcodeGenerator.generateSHA512("This is the test string to build the SHA 512 on.");
        final SessionEntity entity = new SessionEntity();
        entity.setSessionKey(key);
        entityManager.persist(entity);

        final Query query = entityManager.createQuery("select s from SessionEntity s where sessionKey = :key");
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
