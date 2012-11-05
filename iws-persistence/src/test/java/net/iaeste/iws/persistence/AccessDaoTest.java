/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.AccessDaoTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.persistence.entities.SessionEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.setup.SpringConfig;
import net.iaeste.iws.persistence.views.UserPermissionView;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {SpringConfig.class})
public class AccessDaoTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testSession() {
        final AccessDao dao = new AccessJpaDao(entityManager);
        final String key = "12345678901234567890123456789012";
        final UserEntity user = dao.findUserByCredentials("austria", "7112733729f24775a6e82d0a6ad7c8106643ad438fef97e33e069f23a2167266");

        // Create a new Session for a user, and save it in the database
        final SessionEntity entity = new SessionEntity();
        entity.setSessionKey(key);
        entity.setUser(user);
        entity.setActive(true);
        dao.persist(entity);

        // Find the newly created Session, deprecate it, and save it again
        final SessionEntity found = dao.findActiveSession(user);
        assertThat(found, is(not(nullValue())));
        found.setActive(false);
        dao.persist(found);

        // Now, we should not be able to find it
        final SessionEntity notFound = dao.findActiveSession(user);
        assertThat(notFound, is(nullValue()));
    }

    @Ignore("Ignored, until the permission mess is sorted out!")
    @Test
    @Transactional
    public void oldTestAccess() {
        final AccessDao dao = new AccessJpaDao(entityManager);
        //Michl: Nase, NC Member           in Austria
        final List<UserPermissionView> result = dao.findPermissions(1);

        assertThat(result, is(not(nullValue())));
        assertThat(result.size(), is(14));

        // Now, lets check the first permission
        assertThat(result.get(0).getGroupName(), is("Austria"));
        assertThat(result.get(0).getGroupType(), is("Country"));
        assertThat(result.get(0).getPermission(), is("PROCESS_USERS"));

        // Now, lets check the second permission
        assertThat(result.get(1).getGroupName(), is("Austria"));
        assertThat(result.get(1).getGroupType(), is("Country"));
        assertThat(result.get(1).getPermission(), is("FETCH_USERS"));
    }
}
