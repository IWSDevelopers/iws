/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.StorageType;
import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.IWSEntity;
import net.iaeste.iws.persistence.entities.MonitoringEntity;
import net.iaeste.iws.persistence.entities.PermissionRoleEntity;
import net.iaeste.iws.persistence.entities.Updateable;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.exceptions.PersistenceException;
import net.iaeste.iws.persistence.views.IWSView;

import javax.persistence.Query;
import java.util.List;

/**
 * Contains the most basic database functionality, perstisting and deleting.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
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
     * @param authentication  Information about the user invoking the request
     * @param entityToPersist Entity to persist
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
     * Retrieves a list of available Roles, that belongs to a given Group. As
     * Roles can be customized, it means that some countries may have some
     * overlap in names, and to prevent that a Role is incorrectly retrieved,
     * only roles which are customized for the given country is retrieved as
     * well as the standard roles.
     *
     * @param group Group to find Roles for
     * @return List of available Roles for this Group
     */
    List<PermissionRoleEntity> findRoles(GroupEntity group);

    /**
     * Finds a Country based on the given Id (ISO_3166-1_alpha-2).
     *
     * @param countryCode  Two letter CountryId, i.e. DE for Germany
     * @return Found Country or null
     */
    CountryEntity findCountry(String countryCode);

    /**
     * Find Address from the Id. If no such entity exists, then an exception is
     * thrown, otherwise the found Address Entity is returned.
     *
     * @param id Address Id
     * @return Unique Address Entity
     */
    AddressEntity findAddress(Long id);

    /**
     * Finds a file for a given User with the provided External File Id. This
     * method is used to find a file that the user owns.
     *
     * @param user       User
     * @param externalId External File Id
     * @return File
     * @throws net.iaeste.iws.persistence.exceptions.PersistenceException if a single file could not be found
     */
    FileEntity findFileByUserAndExternalId(UserEntity user, String externalId) throws PersistenceException;
    FileEntity findAttachedFileByUserAndExternalId(GroupEntity group, String externalId) throws PersistenceException;

    GroupEntity findMemberGroup(UserEntity user);

    List<GroupEntity> findAllGroups(GroupType type);

    /**
     * Finds a file based on the internal Id.
     *
     * @param id File Id
     * @return Found File or null if no such file exists
     */
    FileEntity findFileById(Long id);

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

    /**
     * Finds a file for a given Group, which is attached to a Object of the
     * given type.
     *
     * @param externalId      External File Id
     * @param externalGroupId The Group that the file is shared to
     * @param type            Storage Type
     * @return File
     * @throws PersistenceException if a single file could not be found
     */
    FileEntity findAttachedFile(String externalFileId, String externalGroupId, StorageType type);
}
