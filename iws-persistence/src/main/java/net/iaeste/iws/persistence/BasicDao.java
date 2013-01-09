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

import net.iaeste.iws.persistence.entities.IWSEntity;
import net.iaeste.iws.persistence.entities.Mergeable;
import net.iaeste.iws.persistence.entities.MonitoringEntity;

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
    <T extends Mergeable<T>> void persist(Authentication authentication, T entityToPersist, T changesToBeMerged);

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
}
