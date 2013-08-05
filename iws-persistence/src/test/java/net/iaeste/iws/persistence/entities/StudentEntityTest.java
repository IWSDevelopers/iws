/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.StudentEntityTest
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

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.StudentJpaDao;
import net.iaeste.iws.persistence.setup.SpringConfig;
import org.junit.Before;
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
import java.util.UUID;

/**
 * @author  Teis Lindemark / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { SpringConfig.class })
@TransactionConfiguration(defaultRollback = true)
public class StudentEntityTest {

    @PersistenceContext
    private EntityManager entityManager;

    private StudentJpaDao studentDao = null;
    private Authentication authentication = null;
    private GroupEntity group = null;

    @Before
    public void before() {
        studentDao = new StudentJpaDao(entityManager);

        final AccessDao accessDao = new AccessJpaDao(entityManager);
        final AuthenticationToken token = new AuthenticationToken();
        final UserEntity user = accessDao.findUserByUsername("austria@iaeste.at");

        group = accessDao.findNationalGroup(user);
        authentication = new Authentication(token, user, group);
    }

    @Test
    @Transactional
    public void testFindAllStudents() {
        final StudentEntity entity = new StudentEntity();
        entity.setExternalId(UUID.randomUUID().toString());
        entity.setStudentName("Test Student");
        entity.setGroup(group);
        studentDao.persist(authentication, entity);

        final Query query = entityManager.createNamedQuery("student.findAll");
        final List<StudentEntity> list = query.getResultList();

        assertThat(list.size(), is(1));
        assertThat(list.get(0), is(entity));
    }

    @Test
    @Transactional
    public void testStudent() {
        final StudentEntity entity = new StudentEntity();
        entity.setExternalId(UUID.randomUUID().toString());
        entity.setStudentName("Test Student");
        entity.setGroup(group);
        studentDao.persist(authentication, entity);

        final Query query = entityManager.createNamedQuery("student.findByName");
        query.setParameter("name", "Test Student");
        final List<StudentEntity> found = query.getResultList();

        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(entity));
    }
}
