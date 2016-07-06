/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.persistence;

import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.util.Page;
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import net.iaeste.iws.persistence.entities.IWSEntity;
import net.iaeste.iws.persistence.entities.PermissionRoleEntity;
import net.iaeste.iws.persistence.entities.Updateable;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.exceptions.PersistenceException;
import net.iaeste.iws.persistence.views.IWSView;

import javax.persistence.Query;
import java.util.List;

/**
 * Contains the most basic database functionality, persisting and deleting.
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
     * @param page  Paging information
     * @return List of results from the Query
     */
    <T extends IWSView> List<T> fetchList(Query query, Page page);

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
     * Returns a list of all known Countries in the database.
     *
     * @return List of Countries
     */
    List<CountryEntity> findAllCountries();

    /**
     * Find Address from the Id. If no such entity exists, then an exception is
     * thrown, otherwise the found Address Entity is returned.
     *
     * @param id Address Id
     * @return Unique Address Entity
     */
    AddressEntity findAddress(Long id);

    GroupTypeEntity findGroupType(GroupType groupType);

    Long findNumberOfAliasesForName(String name);

    /**
     * Finds a file for a given User with the provided External File Id. This
     * method is used to find a file that the user owns.
     *
     * @param user       User
     * @param externalId External File Id
     * @return File
     * @throws PersistenceException if a single file could not be found
     */
    FileEntity findFileByUserAndExternalId(UserEntity user, String externalId);
    FileEntity findAttachedFileByUserAndExternalId(GroupEntity group, String externalId);

    int deleteAttachmentRecord(FileEntity file);

    /**
     * Finds a Users Member Group and returns it. If no Member Group was found,
     * then an {@link net.iaeste.iws.persistence.exceptions.IdentificationException} is thrown.
     *
     * @param user User to find the Member Group for
     * @return Found Member Group
     * @throws net.iaeste.iws.persistence.exceptions.IdentificationException if no Member Group was found
     */
    GroupEntity findMemberGroup(UserEntity user);

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
     * @param fileId  External File Id
     * @param groupId The Group that the file is shared to
     * @return File with data
     * @throws PersistenceException if a single file could not be found
     */
    FileEntity findAttachedFile(String fileId, String groupId);

    List<UserGroupEntity> findGroupMembers(GroupEntity group);
    /**
     * Finds all groups the user is member
     *
     * @param  user UserEntity
     * @return List of GroupEntity
     */
    List<UserGroupEntity> findAllUserGroups(UserEntity user);

}
