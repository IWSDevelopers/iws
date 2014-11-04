/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.CommitteeJpaDao
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

import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.persistence.CommitteeDao;
import net.iaeste.iws.persistence.entities.EntityConstants;
import net.iaeste.iws.persistence.entities.UserGroupEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public class CommitteeJpaDao extends BasicJpaDao implements CommitteeDao {

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager instance to use
     */
    public CommitteeJpaDao(final EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findCommitteesByContryIds(final List<String> countryIds, final Set<GroupStatus> statuses) {
        final String jql =
                "select u " +
                "from UserGroupEntity u " +
                "where u.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_NATIONAL +
                "  and u.role.id = " + EntityConstants.ROLE_OWNER +
                "  and u.group.country.countryCode in :countryIds" +
                "  and u.group.status in :statuses";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("countryIds", countryIds);
        query.setParameter("statuses", statuses);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findCommitteesByMembership(final Membership membership, final Set<GroupStatus> statuses) {
        final String jql =
                "select u " +
                "from UserGroupEntity u " +
                "where u.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_NATIONAL +
                "  and u.role.id = " + EntityConstants.ROLE_OWNER +
                "  and u.group.country.membership = :membership" +
                "  and u.group.status in :statuses";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("membership", membership);
        query.setParameter("statuses", statuses);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findAllCommittees(final Set<GroupStatus> statuses) {
        final String jql =
                "select u " +
                "from UserGroupEntity u " +
                "where u.group.groupType.grouptype = " + EntityConstants.GROUPTYPE_NATIONAL +
                "  and u.role.id = " + EntityConstants.ROLE_OWNER +
                "  and u.group.status in :statuses";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("statuses", statuses);

        return query.getResultList();
    }
}
