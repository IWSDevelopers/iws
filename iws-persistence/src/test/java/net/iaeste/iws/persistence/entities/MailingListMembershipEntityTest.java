/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.MailingListMembershipEntityTest
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
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.MailingListDao;
import net.iaeste.iws.persistence.entities.mailing_list.MailingListEntity;
import net.iaeste.iws.persistence.entities.mailing_list.MailingListMembershipEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.MailingListJpaDao;
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
public class MailingListMembershipEntityTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testClassflow() {
        final AccessDao dao = new AccessJpaDao(entityManager);
        final MailingListDao mailingListDao = new MailingListJpaDao(entityManager);
        final UserEntity user = dao.findActiveUserByUsername("austria@iaeste.at");
        assertThat(user.getUsername(), is("austria@iaeste.at"));

        final GroupEntity group = dao.findNationalGroup(user);
        assertNotNull(group);

        final MailingListEntity entity = new MailingListEntity();
        entity.setExternalId(group.getExternalId());
        entity.setActive(true);
        entity.setPrivateList(true);
        entity.setListAddress(group.getGroupName() + "@iaeste.net");

        entityManager.persist(entity);

        final MailingListEntity found = mailingListDao.findPrivateMailingList(group.getExternalId());
        assertThat(found, is(notNullValue()));

        final MailingListMembershipEntity membership = new MailingListMembershipEntity();
        membership.setMailingList(found);
        membership.setMember(user.getUsername());
        entityManager.persist(membership);

        final Query query = entityManager.createQuery("select m from MailingListMembershipEntity m where m.mailingList= :mailingList");
        query.setParameter("mailingList", found);
        final List resultList = query.getResultList();
        assertThat(resultList.size(), is(1));
    }
}
