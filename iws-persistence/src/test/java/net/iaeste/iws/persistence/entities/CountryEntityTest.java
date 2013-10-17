/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.CountryEntityTest
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
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

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
public class CountryEntityTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testClassflow() {
        final CountryEntity entity = new CountryEntity();
        entity.setCountryCode("my");
        entity.setCountryName("Land");
        entityManager.persist(entity);

        final Query query = entityManager.createNamedQuery("country.findByName");
        query.setParameter("name", "Land");
        final List<CountryEntity> found = query.getResultList();

        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(entity));
    }

    @Test
    public void testMerging() {
        final String countryName = "myLand";
        final Long id = 1L;

        final CountryEntity original = new CountryEntity();
        final CountryEntity merged = new CountryEntity();
        final CountryEntity failed = new CountryEntity();
        original.setId(id);
        merged.setId(id);
        original.setCountryName(countryName);
        merged.merge(original);
        failed.merge(original);

        assertThat(merged.getId(), is(original.getId()));
        assertThat(merged.getCountryName(), is(original.getCountryName()));
        assertThat(failed.getId(), is(nullValue()));
        assertThat(failed.getCountryName(), is(nullValue()));
    }
}
