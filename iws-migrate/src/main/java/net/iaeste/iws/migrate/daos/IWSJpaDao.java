/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.daos.IWSJpaDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.daos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.persistence.Externable;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import net.iaeste.iws.persistence.entities.IWSEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class IWSJpaDao implements IWSDao {

    private final EntityManager entityManager;

    public IWSJpaDao(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // =========================================================================
    // Implementation of the MailDao functionality
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(final Object entity) {
        if (entity instanceof Externable && ((Externable) entity).getExternalId() == null) {
            ((Externable) entity).setExternalId(UUID.randomUUID().toString());
        }

        entityManager.persist(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryEntity findCountry(final String countryCode) {
        final Query query = entityManager.createNamedQuery("country.findByCountryCode");
        query.setParameter("code", toUpper(countryCode));

        return findUniqueResult(query, "country");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleEntity findRoleById(final Long id) {
        final Query query = entityManager.createNamedQuery("role.findById");
        query.setParameter("id", id);

        return findSingleResult(query, "Role");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findAllGroups() {
        return entityManager.createNamedQuery("group.findAll").getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupEntity> findAllGroups(final GroupType type) {
        final Query query = entityManager.createNamedQuery("group.findAllGroupType");
        query.setParameter("type", type);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupTypeEntity findGroupType(final GroupType groupType) {
        final Query query = entityManager.createNamedQuery("grouptype.findByName");
        // Query runs a String lower check on the value
        query.setParameter("name", groupType.name());

        return findSingleResult(query, "GroupType");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity findUserByIW3Id(final Integer oldId) {
        final Query query = entityManager.createNamedQuery("user.findByIW3Id");
        query.setParameter("oldid", oldId);

        return findSingleResult(query, "User");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupEntity findGroupByIW3Id(final Integer oldId) {
        final Query query = entityManager.createNamedQuery("group.findByIW3Id");
        query.setParameter("oldid", oldId);

        return findSingleResult(query, "Group");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupEntity findIw3UserGroup(final Integer iw3UserId, final Integer iw3GroupId) {
        final Query query = entityManager.createNamedQuery("usergroup.findByIw3UserAndGroup");
        query.setParameter("iw3User", iw3UserId);
        query.setParameter("iw3Group", iw3GroupId);

        return findSingleResult(query, "UserGroup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployerEntity findUniqueEmployer(final GroupEntity group, final EmployerEntity employer) {
        final Query query = entityManager.createNamedQuery("employer.findEmployerByValues");
        query.setParameter("gid", group.getId());
        query.setParameter("name", employer.getName());
        // Note by Kim; If someone can explain why PostgreSQL throws an
        // exception with function 'lower(bytea) unknown', when we're trying to
        // make a lowercase comparison of the Department field, then let me know
        //query.setParameter("department", employer.getDepartment());
        query.setParameter("workingPlace", employer.getWorkingPlace());

        return findSingleResult(query, "Employer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferEntity findOfferByOldOfferId(final Integer oldOfferId) {
        final Query query = entityManager.createNamedQuery("offer.findByOldOfferId");
        query.setParameter("ooid", oldOfferId);

        return findSingleResult(query, "Offer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long countRefNos(final String countryCode, final String year, final String serialNumber) {
        final Query query = entityManager.createQuery("select count(refNo) from OfferEntity where refNo like '" + countryCode + "-20" + year + '%' + serialNumber + '\'');
        final Long count = (Long) query.getResultList().get(0);
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserEntity> findAllUsers() {
        return entityManager.createNamedQuery("user.findAll").getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findNCs() {
        return entityManager.createNamedQuery("usergroup.findncs").getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserGroupEntity> findGroupMembers(final Long groupId) {
        final Query query = entityManager.createNamedQuery("usergroup.findGroupMembers");
        query.setParameter("gid", groupId);

        return query.getResultList();
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    /**
     * Returns the lower case version of the String, using the default Locale
     * for the conversion.
     *
     * @param str String to lower case
     * @return Lower cased String
     */
    protected static String toLower(final String str) {
        return str.toLowerCase(IWSConstants.DEFAULT_LOCALE);
    }

    /**
     * Returns the lower case version of the String, using the default Locale
     * for the conversion.
     *
     * @param str String to lower case
     * @return Lower cased String
     */
    protected static String toUpper(final String str) {
        return str.toUpperCase(IWSConstants.DEFAULT_LOCALE);
    }

    /**
     * Resolves the given Query, and will throw an Identification Exception, if
     * a unique result was not found.
     *
     * @param query      Query to resolve
     * @param entityName Name of the entity expected, used if exception is thrown
     * @return Unique Entity
     */
    protected <T extends IWSEntity> T findUniqueResult(final Query query, final String entityName) {
        final List<T> found = query.getResultList();

        if (found.isEmpty()) {
            throw new IdentificationException("No " + entityName + " was found.");
        }

        if (found.size() > 1) {
            throw new IdentificationException("Multiple " + entityName + "s were found.");
        }

        return found.get(0);
    }

    /**
     * Attempts to find a single result from the list. If the list is empty,
     * then a null is returned, if there is more than one record, then an
     * Exception is thrown - otherwise if only a single result was found, this
     * will be returned.
     *
     * @param query      Query to resolve
     * @param entityName Name of the entity expected, used if exception is thrown
     * @return Single Entity
     */
    protected <T extends IWSEntity> T findSingleResult(final Query query, final String entityName) {
        final List<T> found = query.getResultList();
        final T result;

        if (found.isEmpty()) {
            result = null;
        } else  if (found.size() == 1) {
            result = found.get(0);
        } else {
            throw new IdentificationException("Multiple " + entityName + "s were found.");
        }

        return result;
    }

    protected static <T extends IWSEntity> T resolveResultList(final List<T> list) {
        final T user;

        if (list.size() == 1) {
            user = list.get(0);
        } else if (list.isEmpty()) {
            user = null;
        } else {
            throw new IWSException(IWSErrors.DATABASE_CONSTRAINT_INCONSISTENCY, "Although Record should be unique, multiple records exists.");
        }

        return user;
    }
}
