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
import net.iaeste.iws.persistence.entities.exchange.StudentEntity;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface StudentDao extends BasicDao {

    /**
     * Get all students from the database.
     *
     * @return list of {@code StudentEntity}
     */
    List<UserEntity> findAllStudents(Long parentGroupId);

    /**
     * Find the entity in the database, via the provided name.
     *
     * @param name The name of the student to find
     * @return list of {@code StudentEntity}
     */
    List<StudentEntity> findByName(String name);
}
