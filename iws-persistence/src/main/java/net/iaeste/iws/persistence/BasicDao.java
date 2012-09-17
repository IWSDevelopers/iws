/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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
     * Persist the given Entity into the database.
     *
     * @param entity Entity to persist
     */
    void persist(IWSEntity entity);

    /**
     * Persist a monitored Entity to the database. The Monitoring mechanism will
     * check the Entity, to see how it should be monitored, and then save both
     * the monitored information and the Entity in the database.
     *
     * @param authentication Information about the user invoking the request
     * @param entity         Entity to persist
     */
    void persist(Authentication authentication, IWSEntity entity);

    /**
     * Monitors and merges the new Entity values into the old Entity Object, and
     * persists both the monitored information and the old Entity in the
     * database.
     *
     * @param authentication Information about the user invoking the request
     * @param oldEntity      Entity to persist
     * @param newEntity      Changes to merge into the old Entity
     */
    <T extends Mergeable<T>> void persist(Authentication authentication, T oldEntity, T newEntity);

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
