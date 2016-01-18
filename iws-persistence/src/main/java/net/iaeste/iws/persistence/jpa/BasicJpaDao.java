/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
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

import static net.iaeste.iws.common.utils.StringUtils.toUpper;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Field;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.api.enums.StorageType;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.api.util.Serializer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.BasicDao;
import net.iaeste.iws.persistence.Externable;
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import net.iaeste.iws.persistence.entities.IWSEntity;
import net.iaeste.iws.persistence.entities.MonitoringEntity;
import net.iaeste.iws.persistence.entities.PermissionRoleEntity;
import net.iaeste.iws.persistence.entities.Updateable;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.exceptions.PersistenceException;
import net.iaeste.iws.persistence.monitoring.MonitoringProcessor;
import net.iaeste.iws.persistence.views.IWSView;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
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
    public final void persist(final IWSEntity entityToPersist) {
        ensureUpdateableHasExternalId(entityToPersist);
        entityManager.persist(entityToPersist);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void persist(final Authentication authentication, final IWSEntity entityToPersist) {
        ensureUpdateableHasExternalId(entityToPersist);

        if ((entityToPersist instanceof Updateable<?>) && (entityToPersist.getId() != null)) {
            ((Updateable<?>) entityToPersist).setModified(new Date());
        }

        // We have to start by persisting the entityToPersist, to have an Id
        entityManager.persist(entityToPersist);

        final MonitoringLevel level = findMonitoringLevel(entityToPersist, authentication.getGroup());
        if (level != MonitoringLevel.NONE) {
            final ArrayList<Field> changes = monitoringProcessor.findChanges(level, entityToPersist);
            final String className = monitoringProcessor.findClassMonitoringName(entityToPersist);
            persistMonitoredData(authentication, className, entityToPersist.getId(), changes);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final <T extends Updateable<T>> void persist(final Authentication authentication, final T entityToPersist, final T changesToBeMerged) {
        final MonitoringLevel level = findMonitoringLevel(entityToPersist, authentication.getGroup());
        if (level != MonitoringLevel.NONE) {
            final ArrayList<Field> changes = monitoringProcessor.findChanges(level, entityToPersist, changesToBeMerged);
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
    public final void delete(final IWSEntity entity) {
        // First, let's drop all Objects matching the entityToPersist. Since
        // the record Id in the history table cannot be set up as a foreign
        // key, we must do this manually.
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
    public final <T extends IWSView<T>> List<T> fetchList(final Query query, final Paginatable page) {
        // The Pagination starts with page 1, so we have to subtract one here,
        // to ensure that we read out the correct data from the database.
        query.setFirstResult((page.pageNumber() - 1) * page.pageSize());
        query.setMaxResults(page.pageSize());

        final List<T> found = query.getResultList();
        for (final T view : found) {
            view.setSorting(page.sortBy(), page.sortAscending());
        }
        Collections.sort(found);

        return found;
    }

    private void persistMonitoredData(final Authentication authentication, final String className, final Long recordId, final ArrayList<Field> fields) {
        final MonitoringEntity monitoringEntity = new MonitoringEntity();
        final byte[] data = Serializer.serialize(fields);

        monitoringEntity.setUser(authentication.getUser());
        monitoringEntity.setGroup(authentication.getGroup());
        monitoringEntity.setTableName(className);
        monitoringEntity.setRecordId(recordId);
        monitoringEntity.setFields(data);

        entityManager.persist(monitoringEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findGroupMembers(final GroupEntity group) {
        final Query query = entityManager.createQuery("select u from UserGroupEntity u where u.group = :group");
        query.setParameter("group", group);

        return query.getResultList();
    }

    // =========================================================================
    // Following lookup methods are added here, since they're used often
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<PermissionRoleEntity> findRoles(final GroupEntity group) {
        final Query query = entityManager.createNamedQuery("permissionRole.findByRoleToGroup");
        //final Query query = entityManager.createNamedQuery("role.findByGroup");
        final Long cid = (group.getCountry() != null) ? group.getCountry().getId() : 0;
        query.setParameter("cid", cid);
        query.setParameter("gid", group.getId());

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final CountryEntity findCountry(final String countryCode) {
        final Query query = entityManager.createNamedQuery("country.findByCountryCode");
        query.setParameter("code", toUpper(countryCode));

        return findUniqueResult(query, "country");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<CountryEntity> findAllCountries() {
        final Query query = entityManager.createNamedQuery("country.findAll");

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final AddressEntity findAddress(final Long id) {
        final Query query = entityManager.createNamedQuery("address.findById");
        query.setParameter("id", id);

        return findUniqueResult(query, "address");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Long findNumberOfAliasesForName(final String name) {
        final Query query = entityManager.createNamedQuery("user.findNumberOfSimilarAliases");
        query.setParameter("startOfAlias", name.toLowerCase(IWSConstants.DEFAULT_LOCALE) + '%');

        return (Long) query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GroupTypeEntity findGroupType(final GroupType groupType) {
        final Query query = entityManager.createNamedQuery("grouptype.findByName");
        // Query runs a String lower check on the value
        query.setParameter("name", groupType.name());

        return findSingleResult(query, "GroupType");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FileEntity findFileByUserAndExternalId(final UserEntity user, final String externalId) {
        final Query query = entityManager.createNamedQuery("file.findByUserAndExternalId");
        query.setParameter("uid", user.getId());
        query.setParameter("efid", externalId);

        return findUniqueResult(query, "File");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FileEntity findAttachedFileByUserAndExternalId(final GroupEntity group, final String externalId) throws PersistenceException {
        final Query query = entityManager.createNamedQuery("file.findApplicationBySendingGroupAndExternalFileId");
        query.setParameter("gid", group.getId());
        query.setParameter("efid", externalId);

        return findUniqueResult(query, "File");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int deleteAttachmentRecord(final FileEntity file) {
        final Query query = entityManager.createNamedQuery("attachments.deleteByFile");
        query.setParameter("fid", file.getId());

        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GroupEntity findMemberGroup(final UserEntity user) {
        final Query query = entityManager.createNamedQuery("group.findGroupByUserAndType");
        query.setParameter("uid", user.getId());
        query.setParameter("type", GroupType.MEMBER);

        return findSingleResult(query, "User");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FileEntity findFileByUserGroupAndExternalId(final UserEntity user, final GroupEntity group, final String externalId) {
        final Query query = entityManager.createNamedQuery("file.findByUserGroupAndExternalId");
        query.setParameter("uid", user.getId());
        query.setParameter("gid", group.getId());
        query.setParameter("efid", externalId);

        return findUniqueResult(query, "File");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FileEntity findAttachedFile(final String externalFileId, final String externalGroupId, final StorageType type) {
        final Query query;

        switch (type) {
            case ATTACHED_TO_APPLICATION:
                query = entityManager.createNamedQuery("file.findApplicationByReceivingGroupAndExternalFileId");
                break;
            default:
                throw new IWSException(IWSErrors.NOT_IMPLEMENTED, "Retrieving Attachments of type " + type.getDescription() + " is not yet supported.");
        }
        query.setParameter("egid", externalGroupId);
        query.setParameter("efid", externalFileId);

        return findUniqueResult(query, "File");
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    /**
     * The JPA Documentation is vague when it comes to Empty lists used in the
     * SQL 'in' expression. If the database doesn't support the empty list, then
     * we may see an 'unexpected end of subtree' Exception if an empty list is
     * passed.<br />
     *   For more details, please see the Hibernate bug report
     * <a href="https://hibernate.atlassian.net/browse/HHH-8091">8091</a>, which
     * also explains this in details. Note at the time of writing this comment,
     * there is no solution pending. Hence we're having this method which will
     * provide a work-around.<br />
     *   Basically, what the method does, is that it takes a list and expands
     * it, if it is empty with the given value.
     *
     * @param collection Collection of values to expand if empty
     * @param emptyValue new value to be added if the list is empty
     * @return Collection with at least 1 element
     */
    protected final <T> Collection<T> expandEmptyCollection(final Collection<T> collection, final T emptyValue) {
        if (collection.isEmpty()) {
            collection.add(emptyValue);
        }

        return collection;
    }

    /**
     * Monitoring of data is made based on the common monitoring level of both
     * the Entity & Group. If both have detailed level - then all information is
     * monitored. Otherwise if either is having either marked or detailed, then
     * the level is marked and if one or both doesn't support monitoring, then
     * nothing is monitored.<br />
     *   By default, all entities support detailed monitoring but the groups
     * doesn't support monitoring. Monitoring must be explicitly added by the
     * group moderators.
     *
     * @param entity Entity to check for monitoring
     * @param group  Group to check for monitoring
     * @return Combined result for the monitoring
     */
    private MonitoringLevel findMonitoringLevel(final IWSEntity entity, final GroupEntity group) {
        final MonitoringLevel entityLevel = monitoringProcessor.findClassMonitoringLevel(entity);
        final MonitoringLevel groupLevel;
        if ((group != null) && (group.getMonitoringLevel() != null)) {
            groupLevel = group.getMonitoringLevel();
        } else {
            groupLevel = MonitoringLevel.NONE;
        }

        final MonitoringLevel level;
        if ((entityLevel == MonitoringLevel.NONE) || (groupLevel == MonitoringLevel.NONE)) {
            level = MonitoringLevel.NONE;
        } else if ((entityLevel == MonitoringLevel.MARKED) || (groupLevel == MonitoringLevel.MARKED)) {
            level = MonitoringLevel.MARKED;
        } else {
            level = MonitoringLevel.DETAILED;
        }

        return level;
    }

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
                // Just to make sure that the modification date is always set
                ((Updateable<?>) entity).setModified(new Date());
            }
        }
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
    protected final <T extends IWSEntity> T findSingleResult(final Query query, final String entityName) {
        final List<T> found = query.getResultList();
        T result = null;

        if (found.size() == 1) {
            result = found.get(0);
        } else if (found.size() > 1) {
            throw new IdentificationException("Multiple " + entityName + "s were found.");
        }

        return result;
    }
}
