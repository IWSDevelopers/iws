/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.BasicDaoTest
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

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.jpa.BasicJpaDao;
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
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { SpringConfig.class })
@TransactionConfiguration(defaultRollback = true)
public class BasicDaoTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Test(expected = IdentificationException.class)
    public void testSimpleFunctionality() {
        // The BasicDao contains the core functionality to persist & delete
        final BasicDao dao = new BasicJpaDao(entityManager);

        // Prepare an Entity to work with. The Address is a good example, since
        // it is a fundamental Entity, and thus used many places.
        final AddressEntity entity = new AddressEntity();
        final CountryEntity country = findCountryEntity("AT");
        entity.setCountry(country);

        // First, let's try to persist it
        dao.persist(entity);

        // Now, we should have both an Internal & External Id
        assertThat(entity.getId(), is(not(nullValue())));

        // Now, delete and see if we can find it again
        dao.delete(entity);
        dao.findAddress(entity.getId());
    }

    private CountryEntity findCountryEntity(final String code) {
        final Query query = entityManager.createNamedQuery("country.findByCountryCode");
        query.setParameter("code", code);
        final List<CountryEntity> found = query.getResultList();

        return found.get(0);
    }
}
