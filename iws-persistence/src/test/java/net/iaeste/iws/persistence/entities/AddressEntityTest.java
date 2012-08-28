/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.AddressEntityTest
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {SpringConfig.class})
@TransactionConfiguration(defaultRollback = true)
public class AddressEntityTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testClassflow() {
        final String street1 = "street1";
        final String street2 = "street2";
        final String zip = "12345";
        final String city = "Ducktown";
        final CountryEntity country = findCountry("Freedonia");

        final AddressEntity address = new AddressEntity();
        address.setStreet1(street1);
        address.setStreet2(street2);
        address.setZip(zip);
        address.setCity(city);
        address.setCountry(country);
        entityManager.persist(address);
        final Query query = entityManager.createNamedQuery("address.findById");
        query.setParameter("id", 1);
        final List<AddressEntity> found = query.getResultList();

        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(address));
        assertThat(found.get(0).getStreet1(), is(street1));
        assertThat(found.get(0).getStreet2(), is(street2));
        assertThat(found.get(0).getZip(), is(zip));
        assertThat(found.get(0).getCity(), is(city));
        assertThat(found.get(0).getCountry(), is(country));
    }

    @Test
    public void testMerging() {
        final String street1 = "street1";
        final String street2 = "street2";
        final String zip = "12345";
        final String city = "Ducktown";
        final CountryEntity country = findCountry("Freedonia");

        final AddressEntity original = new AddressEntity(street1, street2, zip, city, country);
        final AddressEntity merged = new AddressEntity();
        final AddressEntity failed = new AddressEntity();

        original.setId(1);
        merged.setId(1);
        failed.setId(2);

        merged.merge(original);
        failed.merge(original);

        assertThat(merged.getStreet1(), is(street1));
        assertThat(merged.getStreet2(), is(street2));
        assertThat(merged.getZip(), is(zip));
        assertThat(merged.getCity(), is(city));
        assertThat(merged.getCountry(), is(country));
    }

    private CountryEntity findCountry(final String countryName) {
        final Query query = entityManager.createNamedQuery("country.findByName");
        query.setParameter("name", countryName);
        final List<CountryEntity> found = query.getResultList();

        return found.get(0);
    }
}
