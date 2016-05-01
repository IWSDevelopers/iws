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

import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.persistence.StudentDao;
import net.iaeste.iws.persistence.entities.AttachmentEntity;
import net.iaeste.iws.persistence.entities.exchange.ApplicationEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.entities.exchange.StudentEntity;
import net.iaeste.iws.persistence.views.ApplicationView;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

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
     * @param settings      IWS System Settings
     */
    public StudentJpaDao(final EntityManager entityManager, final Settings settings) {
        super(entityManager, settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationEntity findApplicationByExternalId(final String externalId) {
        //TODO very stupid but effective fix for #515
        //TODO Does it mean that now it buffers OfferGroupEntity and then it works but without the extra query, it can't find OfferGroupEntities???
        entityManager.createQuery("select og from OfferGroupEntity og").getResultList();

        final String jql =
                "select a " +
                "from ApplicationEntity a " +
                "where a.externalId = :eid";
        final Query query = entityManager.createQuery(jql);
        //TODO ensure that only application for owned or shared offers can be retrieved
        //final Query query = entityManager.createNamedQuery("application.findByExternalId");
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
