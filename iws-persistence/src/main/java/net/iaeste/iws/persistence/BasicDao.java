/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.BasicDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence;

import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.IWSEntity;
import net.iaeste.iws.persistence.entities.Updateable;
import net.iaeste.iws.persistence.entities.MonitoringEntity;
import net.iaeste.iws.persistence.views.IWSView;

import javax.persistence.Query;
import java.util.List;

/**
 * Contains the most basic database functionality, perstisting and deleting.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface BasicDao {

    /**
     * Persist the given Entity into the database. Note, this method should be
     * used only, if no monitoring is required. Examples, when creating a new
     * session, changing password, etc.
     *
     * @param entityToPersist Entity to persist
     */
    void persist(IWSEntity entityToPersist);

    /**
     * Persist a monitored Entity to the database. The Monitoring mechanism will
     * check the Entity, to see how it should be monitored, and then save both
     * the monitored information and the Entity in the database.
     *
     * @param authentication Information about the user invoking the request
     * @param entityToPersist         Entity to persist
     */
    void persist(Authentication authentication, IWSEntity entityToPersist);

    /**
     * Monitors and merges the given Entity with the values from the second
     * Entity, that contains the changes provided by the user. The changes are
     * using the monitoring mechanism and if required, also saved in the
     * database together with the updated entityToPersist.
     *
     * @param authentication    Information about the user invoking the request
     * @param entityToPersist   Entity to persist
     * @param changesToBeMerged Changes to merge into the existing Entity
     */
    <T extends Updateable<T>> void persist(Authentication authentication, T entityToPersist, T changesToBeMerged);

    /**
     * Deletes the given Entity from the database.
     *
     * @param entity Entity to delete
     */
    void delete(IWSEntity entity);

    /**
     * Finds the monitored history for a given Entity, and returns it.
     *
     * @param entity The Entity to find the history for
     * @return List of the Entity history
     */
    List<MonitoringEntity> findHistory(IWSEntity entity);

    /**
     * IWSViews should be used for all listings, since a View can be optimized
     * in the database, and further - we need to add paginating information
     * like page number, size, sorting by and sorting direction. This method
     * takes a View, and extends the given Query with the pagination
     * information.<br />
     *   Note; that the queries given <b>must</b> also have the two fields
     * sortBy and sortOrder available as parameters, otherwise an Exception
     * will be thrown, as it is not possible to check the query by the logic
     * before using it.
     *
     * @param query Query to invoke with the paging information
     * @param page  Paginf information
     * @return List of results from the Query
     */
    <T extends IWSView<T>> List<T> fetchList(Query query, Paginatable page);

    // =========================================================================
    // Following lookup methods are added here, since they're used often
    // =========================================================================

    /**
     * Find Address from the Id. If no such entity exists, then an exception is
     * thrown, otherwise the found Address Entity is returned.
     *
     * @param id Address Id
     * @return Unique Address Entity
     */
    AddressEntity findAddress(Long id);

    /**
     * Find Address from the Id. If no such entity exists, then an exception is
     * thrown, otherwise the found Address Entity is returned.
     *
     * @param id Address Id
     * @return Unique Address Entity
     */
    AddressEntity findAddress(String externalId);

    /**
     * Attempts to find a Unique Address Entity, based on the provided
     * parameters. If no such address exists, then a null is returned, otherwise
     * the unique address entity is returned.
     *
     * @param newAddress New unpersisted Address Entity
     * @return Unique Address Entity or null
     */
    AddressEntity findUniqueAddress(AddressEntity newAddress);
}
