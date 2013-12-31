/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.StudentDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence;

import net.iaeste.iws.persistence.entities.AttachmentEntity;
import net.iaeste.iws.persistence.entities.exchange.ApplicationEntity;
import net.iaeste.iws.persistence.entities.exchange.StudentEntity;
import net.iaeste.iws.persistence.views.ApplicationView;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface StudentDao extends BasicDao {

    /**
     * Find the application by its external ID
     *
     * @param externalId application external ID
     * @return {@code ApplicationEntity} if exists, otherwise null
     */
    ApplicationEntity findApplicationByExternalId(String externalId);

    /**
     * Finds a student in the database by given external id and owning group
     * @param parentGroupId owning group ID
     * @param externalId student external ID
     * @return {@code StudentEntity} if student exists withing given group, otherwise null
     */
    StudentEntity findStudentByExternal(Long parentGroupId, String externalId);

    /**
     * Finds all applications for a specific Offer owned by specified group
     * @param offerExternalId Offer ID
     * @param offerOwnerId Offer Owner Group ID
     * @return list of {@code ApplicationView}
     */
    List<ApplicationView> findForeignApplicationsForOffer(String offerExternalId, Long offerOwnerId);

    /**
     * Finds all applications owned by specified group for a specific Offer
     * @param offerExternalId Offer ID
     * @param applicationOwnerId Offer Owner Group ID
     * @return list of {@code ApplicationView}
     */
    List<ApplicationView> findDomesticApplicationsForOffer(String offerExternalId, Long applicationOwnerId);

    List<AttachmentEntity> findAttachments(String table, Long recordId);

    AttachmentEntity findAttachment(String table, Long recordId, Long fileId);

    /**
     * Checks if there is any nominated application for a specific Offer
     *
     * @param offerId application id
     * @return true if there is any other nominated application
     */
    Boolean otherNominatedApplications(Long offerId);
}
