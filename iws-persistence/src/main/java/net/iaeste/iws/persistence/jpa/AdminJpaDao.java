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
package net.iaeste.iws.persistence.jpa;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.persistence.AdminDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.EnumSet;
import java.util.List;

/**
 * Default JPA implementation of the AdminDao, which contain the functionality
 * to work with users, groups and countries.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class AdminJpaDao extends BasicJpaDao implements AdminDao {

    /**
     * Default Constructor.
     *
     * @param entityManager  Entity Manager instance to use
     */
    public AdminJpaDao(final EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findEmergencyList() {
        final Query query = entityManager.createNamedQuery("userGroup.emergencyList");

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findUserGroupsForContactsByExternalUserId(final String externalUserId) {
        final Query query = entityManager.createNamedQuery("userGroup.findContactForExternalUserId");
        query.setParameter("euid", externalUserId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findUserGroupsForContactsByExternalGroupId(final String externalGroupId) {
        final Query query = entityManager.createNamedQuery("userGroup.findContactForExternalGroupId");
        query.setParameter("egid", externalGroupId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> searchUsers(final String firstname, final String lastname) {
        final Query query = entityManager.createNamedQuery("userGroup.searchByFirstNameAndLastNameInMembers");
        // Weird, if I add the following lines directly into the setParameter,
        // then the trailing percentage sign is dropped!
        final String name1 = '%' + firstname.toLowerCase(IWSConstants.DEFAULT_LOCALE) + '%';
        final String name2 = '%' + lastname.toLowerCase(IWSConstants.DEFAULT_LOCALE) + '%';
        query.setParameter("firstname", name1);
        query.setParameter("lastname", name2);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> searchUsers(final String firstname, final String lastname, final String externalMemberGroupId) {
        final Query query = entityManager.createNamedQuery("userGroup.searchByFirstNameAndLastNameInSpecificMember");
        query.setParameter("egid", externalMemberGroupId);
        final String name1 = '%' + firstname.toLowerCase(IWSConstants.DEFAULT_LOCALE) + '%';
        final String name2 = '%' + lastname.toLowerCase(IWSConstants.DEFAULT_LOCALE) + '%';
        query.setParameter("firstname", name1);
        query.setParameter("lastname", name2);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findGroupsForContacts() {
        final String jql =
                "select g from GroupEntity g " +
                "where g.status = :status" +
                "  and g.groupType.grouptype not in :types " +
                "order by g.groupType.grouptype asc, g.groupName asc";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("status", GroupStatus.ACTIVE);
        query.setParameter("types", EnumSet.of(GroupType.PRIVATE, GroupType.STUDENT));

        return query.getResultList();
    }
}
