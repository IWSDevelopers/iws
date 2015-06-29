/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.monitoring.MonitoringEntityTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { SpringConfig.class })
public class MonitoringEntityTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testEntity() {
        final MonitoringProcessor monitoring = new MonitoringProcessor();
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
