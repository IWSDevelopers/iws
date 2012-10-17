/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.BasicJpaDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.jpa;

import net.iaeste.iws.api.dtos.Field;
import net.iaeste.iws.persistence.BasicDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.*;
import net.iaeste.iws.persistence.entities.Object;
import net.iaeste.iws.persistence.monitoring.MonitoringLevel;
import net.iaeste.iws.persistence.monitoring.MonitoringProcessor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class BasicJpaDao implements BasicDao {

    protected EntityManager entityManager;
    protected MonitoringProcessor monitoringProcessor;

    /**
     * Default Constructor.
     *
     * @param entityManager  Entity Manager instance to use
     */
    public BasicJpaDao(final EntityManager entityManager) {
        this.entityManager = entityManager;
        monitoringProcessor = new MonitoringProcessor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    public void persist(final Object entity) {
        entityManager.persist(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(final Authentication authentication, final Object entity) {
        // We have to start by persisting the entity, to have an Id
        entityManager.persist(entity);

        final MonitoringLevel level = monitoringProcessor.findClassMonitoringLevel(entity);
        if (level != MonitoringLevel.NONE) {
            final List<Field> changes = monitoringProcessor.findChanges(level, entity);
            persistMonitoredData(authentication, changes);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Mergeable<T>> void persist(final Authentication authentication, final T oldEntity, final T newEntity) {
        final MonitoringLevel level = monitoringProcessor.findClassMonitoringLevel(oldEntity);
        if (level != MonitoringLevel.NONE) {
            final List<Field> changes = monitoringProcessor.findChanges (level, oldEntity, newEntity);
            persistMonitoredData(authentication, changes);
        }

        oldEntity.merge(newEntity);
        entityManager.persist(newEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final Object entity) {
        // First, let's drop all Objects matching the entity. Since the record
        // Id in the history table cannot be set up as a foreign key, we must
        // do this manually
        final String tableName = monitoringProcessor.findClassMonitoringName(entity);
        final Query query = entityManager.createNamedQuery("monitoring.deleteChanges");
        query.setParameter("table", tableName);
        query.setParameter("record", entity.getId());
        query.executeUpdate();

        entityManager.remove(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MonitoringEntity> findHistory(final net.iaeste.iws.persistence.entities.Object entity) {
        final String name = monitoringProcessor.findClassMonitoringName(entity);
        final Long id = entity.getId();
        final List<MonitoringEntity> result;

        if ((name != null) && (id != null)) {
            final Query query = entityManager.createNamedQuery("monitoring.findChanges");
            query.setParameter("table", name);
            query.setParameter("record", id);

            result = query.getResultList();
        } else {
            result = new ArrayList<>(0);
        }

        return result;
    }

    private void persistMonitoredData(final Authentication authentication, final List<Field> fields) {
        final MonitoringEntity monitoringEntity = new MonitoringEntity();
        final byte[] data = monitoringProcessor.serialize(fields);

        monitoringEntity.setUser(authentication.getUser());
        monitoringEntity.setGroup(authentication.getGroup());
        monitoringEntity.setFields(data);

        entityManager.persist(monitoringEntity);
    }
}
