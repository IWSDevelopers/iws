/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.persistence.entities.CountryEntity;
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
public class AdminJpaDao extends BasicJpaDao implements AdminDao {

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
    public CountryEntity findCountryByName(final String countryName) {
        final Query query = entityManager.createNamedQuery("country.findByName");
        query.setParameter("name", countryName);
        final List<CountryEntity> found = query.getResultList();

        return found.size() == 1 ? found.get(0) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findEmergencyList() {
        final Query query = entityManager.createNamedQuery("usergroup.emergencylist");

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findGroupMembers(final String externalGroupId) {
        final Query query = entityManager.createNamedQuery("usergroup.findForExternalGroupId");
        query.setParameter("egid", externalGroupId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findUserGroups(final String externalUserId) {
        final Query query = entityManager.createNamedQuery("usergroup.findForExternalUserId");
        query.setParameter("euid", externalUserId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findUserGroupsForContactsByExternalUserId(final String externalUserId) {
        final Query query = entityManager.createNamedQuery("usergroup.findContactForExternalUserId");
        query.setParameter("euid", externalUserId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findUserGroupsForContactsByExternalGroupId(final String externalGroupId) {
        final Query query = entityManager.createNamedQuery("usergroup.findContactForExternalGroupId");
        query.setParameter("egid", externalGroupId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> searchUsers(String firstname, String lastname) {
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
    public List<UserGroupEntity> searchUsers(String firstname, String lastname, String externalMemberGroupId) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findSubGroupsByParentId(Long parentGroupId) {
        final Query query = entityManager.createNamedQuery("group.findSubGroupsByParentId");
        query.setParameter("pid", parentGroupId);

        return query.getResultList();
    }
}
