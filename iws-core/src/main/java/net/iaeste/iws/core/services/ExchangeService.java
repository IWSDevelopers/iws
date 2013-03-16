/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.ExchangeService
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.services;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.EmployerInformation;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.OfferGroup;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.DeleteOfferRequest;
import net.iaeste.iws.api.requests.FetchEmployerInformationRequest;
import net.iaeste.iws.api.requests.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.FetchPublishOfferRequest;
import net.iaeste.iws.api.requests.OfferTemplateRequest;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.requests.PublishGroupRequest;
import net.iaeste.iws.api.requests.PublishOfferRequest;
import net.iaeste.iws.api.responses.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.api.responses.FetchPublishOfferResponse;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.api.responses.OfferTemplateResponse;
import net.iaeste.iws.api.responses.PublishGroupResponse;
import net.iaeste.iws.core.transformers.OfferTransformer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.OfferEntity;
import net.iaeste.iws.persistence.entities.OfferGroupEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.notification.NotificationMessageType;
import net.iaeste.iws.persistence.notification.Notifications;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class ExchangeService extends CommonService {

    private final OfferDao dao;
    private final Notifications notifications;

    public ExchangeService(final OfferDao dao, final Notifications notifications) {
        this.dao = dao;
        this.notifications = notifications;
    }

    /**
     * Will attempt to persist a new Offer, meaning that if the Offer already
     * exists (check against the given refno) and the user is allowed to work
     * with it, then it us updated. If no such Offer exists, then a new Offer
     * is created and assigned to the given Group.<br />
     * The method returns an OfferResponse object with error information. No
     * information about the Offer is returned.
     *
     * @param authentication User & Group information
     * @param request        Offer Request information, i.e. OfferDTO
     * @return OfferResponse with error information
     */
    public OfferResponse processOffer(final Authentication authentication, final ProcessOfferRequest request) {
        final OfferEntity newEntity = OfferTransformer.transform(request.getOffer());
        final Offer givenOffer = request.getOffer();
        final String externalId = givenOffer.getId();
        final String refNo = givenOffer.getRefNo();

        if (externalId == null) {
            final OfferEntity existingEntity = dao.findOffer(refNo);
            if (existingEntity == null) {
                // Create a new Offer
                // Add the Group to the Offer
                newEntity.setGroup(authentication.getGroup());
                // Before we can persist the Offer, we need to check that the refno
                // is valid. Since the Country is part of the Group, we can simply
                // compare the refno with that
                verifyRefnoValidity(newEntity);
                // Set the ExternalId of the Offer
                newEntity.setExternalId(UUID.randomUUID().toString());
                // Persist the Offer with history
                dao.persist(authentication, newEntity);
            } else {
                // An Offer exists with this RefNo, but the Id was not provided,
                // hence we have the case where someone tries to create a new
                // Offer using an existing RefNo, this is not allowed
                throw new IdentificationException(String.format("An Offer with the Reference Number %s already exists.", refNo));
            }
        } else {
            // Check if the user is allowed to work with the Object, if not -
            // then a Permission Exception is thrown
            permissionCheck(authentication, authentication.getGroup());

            // Okay, user is permitted. Let's check if we can find this Offer
            final OfferEntity existingEntity = dao.findOffer(externalId, refNo);

            if (existingEntity == null) {
                // We could not find an Offer matching the given criterias,
                // hence we have a case, where the user have not provided the
                // correct information, we cannot process this Offer
                throw new IdentificationException(String.format("No Offer could be found with the Id %s and Refrefence Number %s.", externalId, refNo));
            }

            // Persist the changes, the method takes the existing and merges the
            // new values into it, and finally it also writes an entry in the
            // history table
            dao.persist(authentication, existingEntity, newEntity);
        }

        // Send a notification to the users who so desire. Via the Notifiable
        // Interface, can the Object handle it itself
        notifications.notify(authentication, newEntity, NotificationMessageType.GENERAL);

        return new OfferResponse();
    }

    private static void verifyRefnoValidity(final OfferEntity offer) {
        final String countryCode = offer.getGroup().getCountry().getCountryId();
        final String refno = offer.getRefNo();

        if (!refno.startsWith(countryCode)) {
            throw new VerificationException("The reference number is not valid for this country. Received '" + refno.substring(0, 2) + "' but expected '" + countryCode + "'.");
        }
    }

    // TODO Should perform the delete operation based on the refno
    public void deleteOffer(final Authentication authentication, final DeleteOfferRequest request) {
//        // Check if the user is allowed to work with the Object, if not -
//        // then a Permission Exception is thrown
//        permissionCheck(authentication, authentication.getGroup());

        final OfferEntity foundOffer = dao.findOffer(request.getOfferRefNo());

        if (foundOffer != null) {
            dao.delete(foundOffer.getId());
        } else {
            throw new IWSException(IWSErrors.ENTITY_IDENTIFICATION_ERROR, "Cannot delete Offer with refNo " + request.getOfferRefNo());
        }
    }

    public FetchOffersResponse fetchOffers(final Authentication authentication, final FetchOffersRequest request) {
        final FetchOffersResponse response;

        switch (request.getFetchType()) {
            case ALL:
                response = new FetchOffersResponse(findAllOffers());
                break;
            case OWNED:
                response = new FetchOffersResponse(findOwnedOffers(authentication.getGroup().getId()));
                break;
            case SHARED:
                response = new FetchOffersResponse(findSharedOffers());
                break;
            default:
                response = new FetchOffersResponse(IWSErrors.NOT_IMPLEMENTED, "TBD");
        }

        return response;
    }

    public FetchEmployerInformationResponse fetchEmployers(final Authentication authentication, final FetchEmployerInformationRequest request) {
        final FetchEmployerInformationResponse response;

        response = new FetchEmployerInformationResponse(convertEntityListToEmployerInformationList(dao.findOffersByLikeEmployerName(request.getName(), authentication.getGroup().getId())));

        return response;
    }

    private List<Offer> findAllOffers() {
        final List<OfferEntity> found = dao.findAll();

        return convertEntityList(found);
    }

    private List<Offer> findOffers(final List<Long> ids) {
        final List<OfferEntity> found = dao.findOffers(ids);

        return convertEntityList(found);
    }

    private List<Offer> findOwnedOffers(final Long ownerId) {
        final List<OfferEntity> found = dao.findOffersByOwnerId(ownerId);

        return convertEntityList(found);
    }

    private List<Offer> findSharedOffers() {
        final List<OfferEntity> found = dao.findSharedOffers();

        return convertEntityList(found);
    }

    private List<Offer> findOffersByEmployerName(final String employerName, final Long ownerId) {
        final List<OfferEntity> found = dao.findOffersByEmployerName(employerName, ownerId);

        return convertEntityList(found);
    }

    private List<Offer> findOffersByLikeEmployerName(final String employerName, final Long ownerId) {
        final List<OfferEntity> found = dao.findOffersByLikeEmployerName(employerName, ownerId);

        return convertEntityList(found);
    }

    private static List<Offer> convertEntityList(final List<OfferEntity> found) {
        final List<Offer> result = new ArrayList<>(found.size());

        for (final OfferEntity entity : found) {
            result.add(OfferTransformer.transform(entity));
        }

        return result;
    }

    private static List<EmployerInformation> convertEntityListToEmployerInformationList(final List<OfferEntity> found) {
        final List<EmployerInformation> result = new ArrayList<>(found.size());

        for (final OfferEntity entity : found) {
            result.add(OfferTransformer.transform(EmployerInformation.class, entity));
        }

        return result;
    }

    private static List<OfferGroup> convertOfferGroupEntityList(final List<OfferGroupEntity> found) {
        final List<OfferGroup> result = new ArrayList<>(found.size());

        for (final OfferGroupEntity entity : found) {
            result.add(OfferTransformer.transform(OfferGroupEntity.class, entity));
        }

        return result;
    }

    public void processOfferTemplates(final Authentication authentication, final OfferTemplateRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public OfferTemplateResponse fetchOfferTemplates(final Authentication authentication, final FetchOfferTemplatesRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public void processPublishGroups(final Authentication authentication, final PublishGroupRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public PublishGroupResponse fetchPublishGroups(final Authentication authentication, final FetchPublishGroupsRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    /**
     * Method for proccesing publishing (sharing) of offer. Passing empty list of groups means complete unsharing
     * of the offer. Otherwise the offer is share to those groups in the list.
     *
     * @param authentication
     * @param request
     */
    public void processPublishOffer(final Authentication authentication, final PublishOfferRequest request) {
        final Offer offer = request.getOffer();
        dao.findAll();
        final List<OfferGroupEntity> groupsForSharedOffer = dao.findGroupsForSharedOffer(offer.getRefNo());

        if(!groupsForSharedOffer.isEmpty()) {
            dao.unshareFromAllGroups(offer.getRefNo());
        }

        publishOffer(authentication, request);
    }

    private void publishOffer(final Authentication authentication, final PublishOfferRequest request) {
        final OfferEntity offer = dao.findOffer(request.getOffer().getRefNo());

        for (final Group group : request.getGroups()) {
            if (group.getGroupType() == GroupType.NATIONAL) {
                persistPublisingGroup(authentication, offer, group);
            }
        }
    }

    private void persistPublisingGroup(final Authentication authentication, final OfferEntity offer, final Group group) {
        final GroupEntity groupEntity = dao.findGroupByExternalId(group.getGroupId());
        final OfferGroupEntity offerGroupEntity = new OfferGroupEntity(offer, groupEntity);
        offerGroupEntity.setCreatedBy(authentication.getUser());

        dao.persist(authentication, offerGroupEntity);
    }

    public FetchPublishOfferResponse fetchPublishedOfferInfo(final Authentication authentication, final FetchPublishOfferRequest request) {
        final FetchPublishOfferResponse response;

        response = new FetchPublishOfferResponse(convertOfferGroupEntityList(dao.findGroupsForSharedOffer(request.getRefNo())));

        return response;
    }
}
