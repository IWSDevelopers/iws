/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.BasicDao;
import net.iaeste.iws.persistence.entities.IWSEntity;
import net.iaeste.iws.persistence.entities.Mergeable;
import net.iaeste.iws.persistence.entities.MonitoringEntity;
import net.iaeste.iws.common.monitoring.MonitoringLevel;
import net.iaeste.iws.persistence.monitoring.MonitoringProcessor;
import net.iaeste.iws.persistence.views.IWSView;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class BasicJpaDao implements BasicDao {

    protected EntityManager entityManager;
    private final MonitoringProcessor monitoringProcessor;

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
    public void persist(final IWSEntity entityToPersist) {
        entityManager.persist(entityToPersist);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(final Authentication authentication, final IWSEntity entityToPersist) {
        // We have to start by persisting the entityToPersist, to have an Id
        entityManager.persist(entityToPersist);

        final MonitoringLevel level = monitoringProcessor.findClassMonitoringLevel(entityToPersist);
        if (level != MonitoringLevel.NONE) {
            final List<Field> changes = monitoringProcessor.findChanges(level, entityToPersist);
            persistMonitoredData(authentication, changes);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Mergeable<T>> void persist(final Authentication authentication, final T entityToPersist, final T changesToBeMerged) {
        final MonitoringLevel level = monitoringProcessor.findClassMonitoringLevel(entityToPersist);
        if (level != MonitoringLevel.NONE) {
            final List<Field> changes = monitoringProcessor.findChanges (level, entityToPersist, changesToBeMerged);
            persistMonitoredData(authentication, changes);
        }

        entityToPersist.merge(changesToBeMerged);
        entityToPersist.setModified(new Date());
        entityManager.persist(entityToPersist);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final IWSEntity entity) {
        // First, let's drop all Objects matching the entityToPersist. Since the record
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
    public List<MonitoringEntity> findHistory(final IWSEntity entity) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IWSView<T>> List<T> fetchList(final Query query, final Paginatable page) {
        query.setFirstResult(page.pageNumber() * page.pageSize());
        query.setMaxResults(page.pageSize());

        final List<T> found = query.getResultList();
        for (final T view : found) {
            view.setSorting(page.sortBy(), page.sortAscending());
        }
        Collections.sort(found);

        return found;
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
