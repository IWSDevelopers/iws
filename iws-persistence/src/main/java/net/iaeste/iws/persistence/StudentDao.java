/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import net.iaeste.iws.api.enums.exchange.ApplicationStatus;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.persistence.entities.AttachmentEntity;
import net.iaeste.iws.persistence.entities.exchange.ApplicationEntity;
import net.iaeste.iws.persistence.entities.exchange.StudentEntity;
import net.iaeste.iws.persistence.views.ApplicationView;
import java.util.*;
import java.util.Set;
/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface StudentDao extends BasicDao {
    ///**
     * Find the application by its external Id.
     *
     * @param externalId application external Id
     * @return {@code ApplicationEntity} if exists, otherwise null
     */
    ApplicationEntity findApplicationByExternalId(String externalId);

    /**
     * Finds a student in the database by given external id and owning group.
     *
     * @param parentGroupId owning group Id
     * @param externalId student external Id
     * @return {@code StudentEntity} if student exists withing given group, otherwise null
     */
    StudentEntity findStudentByExternal(Long parentGroupId, String externalId);

    /**
     * Finds all applications for a specific Offer owned by specified group.
     *
     * @param externalOfferId External Offer Id
     * @param offerOwnerId    Group Id of the Group who owns the Offer
     * @return list of {@code ApplicationView}
     */
    List<ApplicationView> findForeignApplicationsForOffer(String externalOfferId, Long offerOwnerId);

    /**
     * Finds all applications owned by specified group for a specific Offer.
     *
     *
     * @param externalOfferId    External Offer Id
     * @param applicationGroupId Group Id of the Group it is shared to
     * @return list of {@code ApplicationView}
     */
    List<ApplicationView> findDomesticApplicationsForOffer(String externalOfferId, Long applicationGroupId);

    List<AttachmentEntity> findAttachments(String table, Long recordId);

    AttachmentEntity findAttachment(String table, Long recordId, Long fileId);

    /**
     * Checks if there is any OfferGroup for a specific Offer with any of
     * specified states.
     *
     * @param offerId offer id
     * @param offerStates set of OfferState which should be checked for
     * @return true if there is any other application with any state from specified set
     */
    Boolean otherOfferGroupWithCertainStatus(Long offerId, Set<OfferState> offerStates);

    /**
     * Checks if there is any domestic application with any of specified states.
     *
     * @param offerGroupId offer group id
     * @param applicationStates set of OfferState which should be checked for
     * @return true if there is any other application with any state from specified set
     */
    Boolean otherDomesticApplicationsWithCertainStatus(Long offerGroupId, Set<ApplicationStatus> applicationStates);
}
