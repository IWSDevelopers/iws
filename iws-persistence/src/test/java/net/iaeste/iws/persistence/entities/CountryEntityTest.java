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
 * @since   IWS 1.0
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
