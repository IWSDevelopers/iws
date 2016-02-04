/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.persistence.StudentDao;
import net.iaeste.iws.persistence.entities.AttachmentEntity;
import net.iaeste.iws.persistence.entities.exchange.ApplicationEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.entities.exchange.StudentEntity;
import net.iaeste.iws.persistence.views.ApplicationView;

//import javax.persistence.EntityManager;
//import javax.persistence.Query;
//import java.util.List;
//import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
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

//    /**
//     * {@inheritDoc}
//     */
//    @Override
    public ApplicationEntity findApplicationByExternalId(final String externalId) {
        //TODO very stupid but effective fix for #515
        //TODO Does it mean that now it buffers OfferGroupEntity and then it works but without the extra query, it can't find OfferGroupEntities???
        final List<OfferGroupEntity> workaround = entityManager.createQuery("select og from OfferGroupEntity og").getResultList();

        //TODO ensure that only application for owned or shared offers can be retrieved
        final Query query = entityManager.createNamedQuery("application.findByExternalId");
        query.setParameter("eid", externalId);

        return findSingleResult(query, "Application");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentEntity findStudentByExternal(final Long parentGroupId, final String externalId) {
        final Query query = entityManager.createNamedQuery("students.findByExternalIdForCountry");
        query.setParameter("parentId", parentGroupId);
        query.setParameter("eid", externalId);

        return findSingleResult(query, "Student");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ApplicationView> findForeignApplicationsForOffer(final String externalOfferId, final Long offerOwnerId) {
        final Query query = entityManager.createNamedQuery("view.findForeignApplicationsByGroupAndExternalId");
        query.setParameter("eoid", externalOfferId);
        query.setParameter("gid", offerOwnerId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ApplicationView> findDomesticApplicationsForOffer(final String externalOfferId, final Long applicationGroupId) {
        final Query query = entityManager.createNamedQuery("view.findDomesticApplicationByGroupAndExternalId");
        query.setParameter("eoid", externalOfferId);
        query.setParameter("gid", applicationGroupId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AttachmentEntity> findAttachments(final String table, final Long recordId) {
        final Query query = entityManager.createNamedQuery("attachments.findForRecord");
        query.setParameter("table", table);
        query.setParameter("recordid", recordId);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttachmentEntity findAttachment(final String table, final Long recordId, final Long fileId) {
        final Query query = entityManager.createNamedQuery("attachments.findForRecordAndFile");
        query.setParameter("table", table);
        query.setParameter("recordid", recordId);
        query.setParameter("fileid", fileId);

        return findSingleResult(query, "Attachment");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean otherOfferGroupWithCertainStatus(final Long offerId, final Set<OfferState> offerStates) {
        final Query query = entityManager.createNamedQuery("offerGroup.findByOfferAndStatuses");
        query.setParameter("oid", offerId);
        query.setParameter("statuses", offerStates);

        final List<OfferGroupEntity> list = query.getResultList();

        return !list.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean otherDomesticApplicationsWithCertainStatus(final Long offerGroupId, final Set<ApplicationStatus> applicationStates) {
        final Query query = entityManager.createNamedQuery("application.findByOfferGroupIdAndStatuses");
        query.setParameter("ogid", offerGroupId);
        query.setParameter("statuses", applicationStates);

        final List<OfferGroupEntity> list = query.getResultList();

        return !list.isEmpty();
    }
}
