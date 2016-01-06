/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.AdminJpaDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.jpa;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.persistence.AdminDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        final Query query = entityManager.createNamedQuery("group.findAllForContacts");

        return query.getResultList();
    }
}
