/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.StorageDao
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

import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.exceptions.PersistenceException;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface StorageDao extends BasicDao {

    /**
     * Finds a file based on the internal Id.
     *
     * @param id File Id
     * @return Found File or null if no such file exists
     */
    FileEntity findFileById(Long id);

    /**
     * Finds a file for a given User with the provided External File Id. This
     * method is used to find a file that the user owns.
     *
     * @param user       User
     * @param externalId External File Id
     * @return File
     * @throws PersistenceException if a single file could not be found
     */
    FileEntity findFileByUserAndExternalId(UserEntity user, String externalId) throws PersistenceException;

    /**
     * Finds a file for a given Group with the given External File Id, which the
     * user is associated with.
     *
     * @param user       The User
     * @param group      The Group that the file belongs to
     * @param externalId External File Id
     * @return File
     * @throws PersistenceException if a single file could not be found
     */
    FileEntity findFileByUserGroupAndExternalId(UserEntity user, GroupEntity group, String externalId);
}
