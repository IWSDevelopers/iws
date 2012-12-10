/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.StudentJpaDao;
import net.iaeste.iws.persistence.setup.SpringConfig;
import org.junit.Assert;
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Teis Lindemark / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {SpringConfig.class})
@TransactionConfiguration(defaultRollback = true)
public class StudentEntityTest {

    private StudentJpaDao studentDao = null;
    @PersistenceContext
    private EntityManager entityManager;

    private Authentication authentication = null;
    private AuthenticationToken token = null;
    private UserEntity user = null;
    private GroupEntity group = null;

    @Before
    public void before() {
        studentDao = new StudentJpaDao(entityManager);

        final AccessDao accessDao = new AccessJpaDao(entityManager);

        token = new AuthenticationToken();
        user = accessDao.findUserByUsername("austria");
        group = accessDao.findNationalGroup(user);

        authentication = new Authentication(token, user, group);
    }

    @Test
    @Transactional
    public void testFindAllStudents() {
        final StudentEntity entity = new StudentEntity();
        entity.setStudentName("Test Student");
        entity.setGroup(group);
        //entityManager.persist(entity);
        studentDao.persist(authentication,entity);

        final Query query = entityManager.createNamedQuery("StudentEntity.findAll");
        final List<StudentEntity> list = query.getResultList();

        assertThat(list.size(), is(1));
        assertThat(list.get(0), is(entity));
    }

    @Test
    @Transactional
    public void testStudent() {
        final StudentEntity entity = new StudentEntity();
        entity.setStudentName("Test Student");
        entity.setGroup(group);
        //entityManager.persist(entity);
        studentDao.persist(authentication,entity);

        final Query query = entityManager.createNamedQuery("StudentEntity.findByName");
        query.setParameter("studentName","Test Student");
        final List<StudentEntity> found = query.getResultList();

        assertThat(found.size(),is(1));
        assertThat(found.get(0),is(entity));
    }
}
