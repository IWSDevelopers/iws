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

import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;

import java.util.List;

/**
 * Data Access Object with the functionality to perform the most basic
 * operations on all Users, Groups and Countries.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface AdminDao extends BasicDao {

    // =========================================================================
    // County specific DAO functionality
    // =========================================================================

    /**
     * Retrieves the Emergency Phone list, which is the list of all Owners and
     * Moderators from the National Committees.
     *
     * @return List of all Owners and Moderators of National Groups
     */
    List<UserGroupEntity> findEmergencyList();

    List<UserGroupEntity> findUserGroupsForContactsByExternalUserId(String externalUserId);

    List<UserGroupEntity> findUserGroupsForContactsByExternalGroupId(String externalGroupId);

    List<UserGroupEntity> searchUsers(String firstname, String lastname);

    List<UserGroupEntity> searchUsers(String firstname, String lastname, String externalMemberGroupId);

    List<GroupEntity> findGroupsForContacts();
}
