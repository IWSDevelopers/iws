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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {SpringConfig.class})
public class AccessDaoTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Ignore("Ignored, until the permission mess is sorted out!")
    @Test
    @Transactional
    public void testAccess() {
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
