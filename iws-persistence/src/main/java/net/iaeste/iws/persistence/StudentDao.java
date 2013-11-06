/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.StudentDao
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

import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.exchange.ApplicationEntity;
import net.iaeste.iws.persistence.entities.exchange.StudentEntity;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface StudentDao extends BasicDao {

    /**
     * Get all students from the database, for a specific Committee.
     *
     * @return list of {@code StudentEntity}
     */
    List<StudentEntity> findAllStudents(Long parentGroupId);

    /**
     * Find the application by its external ID
     *
     * @param externalId application external ID
     * @return {@code ApplicationEntity} if exists, otherwise null
     */
    ApplicationEntity findApplicationByExternalId(String externalId);

    /**
     * Finds a student in the database by given external id and owning group
     * @param parentGroupId owning group ID
     * @param externalId student external ID
     * @return {@code UserEntity} if students exists withing given group, otherwise null
     */
    UserEntity findStudentByExternal(Long parentGroupId, String externalId);
}
