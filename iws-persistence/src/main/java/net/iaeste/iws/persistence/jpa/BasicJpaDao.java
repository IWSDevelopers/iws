/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Field;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.common.monitoring.MonitoringLevel;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.BasicDao;
import net.iaeste.iws.persistence.Externable;
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.IWSEntity;
import net.iaeste.iws.persistence.entities.MonitoringEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.Updateable;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.monitoring.MonitoringProcessor;
import net.iaeste.iws.persistence.views.IWSView;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public BasicJpaDao(final EntityManager entityManager) throws IWSException {
        if (entityManager == null) {
            throw new IWSException(IWSErrors.FATAL, "Cannot instantiate the DAO without a valid Entity Manager instance.");
        }

        this.entityManager = entityManager;
        this.monitoringProcessor = new MonitoringProcessor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(final IWSEntity entityToPersist) {
        ensureUpdateableHasExternalId(entityToPersist);
        entityManager.persist(entityToPersist);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(final Authentication authentication, final IWSEntity entityToPersist) {
        ensureUpdateableHasExternalId(entityToPersist);

        // We have to start by persisting the entityToPersist, to have an Id
        entityManager.persist(entityToPersist);

        final MonitoringLevel level = monitoringProcessor.findClassMonitoringLevel(entityToPersist);
        if (level != MonitoringLevel.NONE) {
            final List<Field> changes = monitoringProcessor.findChanges(level, entityToPersist);
            final String className = monitoringProcessor.findClassMonitoringName(entityToPersist);
            persistMonitoredData(authentication, className, entityToPersist.getId(), changes);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Updateable<T>> void persist(final Authentication authentication, final T entityToPersist, final T changesToBeMerged) {
        final MonitoringLevel level = monitoringProcessor.findClassMonitoringLevel(entityToPersist);
        if (level != MonitoringLevel.NONE) {
            final List<Field> changes = monitoringProcessor.findChanges (level, entityToPersist, changesToBeMerged);
            final String className = monitoringProcessor.findClassMonitoringName(entityToPersist);
            persistMonitoredData(authentication, className, entityToPersist.getId(), changes);
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

    private void persistMonitoredData(final Authentication authentication, final String className, final Long recordId, final List<Field> fields) {
        final MonitoringEntity monitoringEntity = new MonitoringEntity();
        final byte[] data = monitoringProcessor.serialize(fields);

        monitoringEntity.setUser(authentication.getUser());
        monitoringEntity.setGroup(authentication.getGroup());
        monitoringEntity.setTableName(className);
        monitoringEntity.setRecordId(recordId);
        monitoringEntity.setFields(data);

        entityManager.persist(monitoringEntity);
    }

    // =========================================================================
    // Following lookup methods are added here, since they're used often
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoleEntity> findRoles(final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("role.findByGroup");
        query.setParameter("cid", group.getCountry().getId());
        query.setParameter("gid", group.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryEntity findCountry(final String countryCode) {
        final Query query = entityManager.createNamedQuery("country.findByCountryCode");
        query.setParameter("code", toLower(countryCode));

        return findUniqueResult(query, "country");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AddressEntity findAddress(final Long id) {
        final Query query = entityManager.createNamedQuery("address.findById");
        query.setParameter("id", id);

        return findUniqueResult(query, "address");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileEntity findFileByUserAndExternalId(final UserEntity user, final String externalId) {
        final Query query = entityManager.createNamedQuery("file.findByUserAndExternalId");
        query.setParameter("uid", user.getId());
        query.setParameter("efid", externalId);

        return findUniqueResult(query, "File");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findAllGroups(final GroupType type) {
        final Query query = entityManager.createNamedQuery("group.findAllGroupType");
        query.setParameter("type", type);

        return query.getResultList();
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    /**
     * First, we check if the Entity is updateable, i.e. one that is exposed
     * externally, if so. Then we must ensure that the externalId is set.
     * For some Entities, they are marked Updateable, but do not have a
     * "real" external Id, rather they're using something else. Those must
     * be defined by the invoking code, otherwise we'll get into trouble.
     *
     * @param entity Entity to check
     */
    private static void ensureUpdateableHasExternalId(final IWSEntity entity) {
        if (entity instanceof Externable) {
            if (((Externable<?>) entity).getExternalId() == null) {
                ((Externable<?>) entity).setExternalId(UUID.randomUUID().toString());
            }
        }
    }

    /**
     * Returns the lower case version of the String, using the default Locale
     * for the conversion.
     *
     * @param str String to lower case
     * @return Lower cased String
     */
    protected static String toLower(final String str) {
        return str.toLowerCase(IWSConstants.DEFAULT_LOCALE);
    }

    /**
     * Resolves the given Query, and will throw an Identification Exception, if
     * a unique result was not found.
     *
     * @param query      Query to resolve
     * @param entityName Name of the entity expected, used if exception is thrown
     * @return Unique Entity
     */
    protected <T extends IWSEntity> T findUniqueResult(final Query query, final String entityName) {
        final List<T> found = query.getResultList();

        if (found.isEmpty()) {
            throw new IdentificationException("No " + entityName + " was found.");
        }

        if (found.size() > 1) {
            throw new IdentificationException("Multiple " + entityName + "s were found.");
        }

        return found.get(0);
    }

    /**
     * Attempts to find a single result from the list. If the list is empty,
     * then a null is returned, if there is more than one record, then an
     * Exception is thrown - otherwise if only a single result was found, this
     * will be returned.
     *
     * @param query      Query to resolve
     * @param entityName Name of the entity expected, used if exception is thrown
     * @return Single Entity
     */
    protected <T extends IWSEntity> T findSingleResult(final Query query, final String entityName) {
        final List<T> found = query.getResultList();
        final T result;

        if (found.isEmpty()) {
            result = null;
        } else  if (found.size() == 1) {
            result = found.get(0);
        } else {
            throw new IdentificationException("Multiple " + entityName + "s were found.");
        }

        return result;
    }

    protected static <T extends IWSEntity> T resolveResultList(final List<T> list) {
        final T user;

        if (list.size() == 1) {
            user = list.get(0);
        } else if (list.isEmpty()) {
            user = null;
        } else {
            throw new IWSException(IWSErrors.DATABASE_CONSTRAINT_INCONSISTENCY, "Although Record should be unique, multiple records exists.");
        }

        return user;
    }
}
