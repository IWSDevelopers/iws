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
package net.iaeste.iws.persistence.monitoring;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.dtos.Field;
import net.iaeste.iws.api.util.Serializer;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.MonitoringEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.setup.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 * @noinspection ObjectAllocationInLoop
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = SpringConfig.class)
public class MonitoringEntityTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testEntity() {
        final ArrayList<Field> data = createMonitoringData(5);
        final MonitoringEntity entity = new MonitoringEntity();
        entity.setTableName("Offer");
        entity.setRecordId(1L);
        entity.setUser(findUser(1L));
        entity.setGroup(findGroup(1L));
        entity.setFields(Serializer.serialize(data));
        entityManager.persist(entity);

        final Query q = entityManager.createNamedQuery("monitoring.findChanges");
        q.setParameter("record", 1L);
        q.setParameter("table", "Offer");
        final List<MonitoringEntity> result = q.getResultList();

        assertThat(result, is(not(nullValue())));
        final MonitoringEntity found = result.get(0);
        final List<Field> read = Serializer.deserialize(found.getFields());
        assertThat(data.toString(), is(read.toString()));
    }

    private static ArrayList<Field> createMonitoringData(final int count) {
        final ArrayList<Field> list = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            final String field = "field" + i;
            final String oldValue = "old FieldValue for " + i;
            final String newValue = "new FieldValue for " + i;
            final Field data = new Field(field, oldValue, newValue);
            list.add(data);
        }

        return list;
    }

    private UserEntity findUser(final Long id) {
        final Query query = entityManager.createNamedQuery("user.findById");
        query.setParameter("id", id);
        final List<UserEntity> found = query.getResultList();

        return found.get(0);
    }

    private GroupEntity findGroup(final Long id) {
        final Query query = entityManager.createNamedQuery("group.findById");
        query.setParameter("id", id);
        final List<GroupEntity> found = query.getResultList();

        return found.get(0);
    }
}
