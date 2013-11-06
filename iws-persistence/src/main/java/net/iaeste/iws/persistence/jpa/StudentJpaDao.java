/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.jpa.StudentJpaDao
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

import net.iaeste.iws.persistence.StudentDao;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.exchange.ApplicationEntity;
import net.iaeste.iws.persistence.entities.exchange.StudentEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class StudentJpaDao extends BasicJpaDao implements StudentDao {

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager instance to use
     */
    public StudentJpaDao(final EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentEntity> findAllStudents(final Long parentGroupId) {
        final Query query = entityManager.createNamedQuery("students.findAllForCountry");
        query.setParameter("parentId", parentGroupId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationEntity findApplicationByExternalId(final String externalId) {
        //TODO ensure that only application for owned or shared offers can be retrieved
        final Query query = entityManager.createNamedQuery("application.findByExternalId");
        query.setParameter("eid", externalId);

        return findSingleResult(query, "Application");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findStudentByExternal(final Long parentGroupId, final String externalId) {
        final Query query = entityManager.createNamedQuery("students.findByExternalIdForCountry");
        query.setParameter("parentId", parentGroupId);
        query.setParameter("eid", externalId);

        return findSingleResult(query, "Student");
    }
}
