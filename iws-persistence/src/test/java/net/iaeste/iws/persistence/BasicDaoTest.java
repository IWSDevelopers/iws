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
package net.iaeste.iws.persistence;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.common.configuration.Settings;
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
 * @since   IWS 1.0
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
        final BasicDao dao = new BasicJpaDao(entityManager, new Settings());

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
