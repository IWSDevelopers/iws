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
import net.iaeste.iws.api.requests.FetchGroupsForSharingRequest;
import net.iaeste.iws.api.requests.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.FetchPublishOfferRequest;
import net.iaeste.iws.api.requests.OfferTemplateRequest;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.requests.PublishGroupRequest;
import net.iaeste.iws.api.requests.PublishOfferRequest;
import net.iaeste.iws.api.responses.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.FetchOfferTemplateResponse;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.api.responses.FetchPublishGroupResponse;
import net.iaeste.iws.api.responses.FetchPublishOfferResponse;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.core.transformers.AdministrationTransformer;
import net.iaeste.iws.core.transformers.OfferTransformer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.OfferEntity;
import net.iaeste.iws.persistence.entities.OfferGroupEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.notification.NotificationMessageType;
import net.iaeste.iws.persistence.notification.Notifications;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class ExchangeService extends CommonService {

    private final ExchangeDao dao;
    private final Notifications notifications;

    public ExchangeService(final ExchangeDao dao, final Notifications notifications) {
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
            final OfferEntity existingEntity = dao.findOffer(authentication, refNo);
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
            final OfferEntity existingEntity = dao.findOffer(authentication, externalId, refNo);

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

        final OfferEntity foundOffer = dao.findOffer(authentication, request.getOfferRefNo());

        if (foundOffer != null) {
            dao.delete(authentication, foundOffer.getId());
        } else {
            throw new IWSException(IWSErrors.OBJECT_IDENTIFICATION_ERROR, "Cannot delete Offer with refNo " + request.getOfferRefNo());
        }
    }

    public FetchOffersResponse fetchOffers(final Authentication authentication, final FetchOffersRequest request) {
        final FetchOffersResponse response;

        switch (request.getFetchType()) {
            case ALL:
                response = new FetchOffersResponse(findAllOffers(authentication, request));
                break;
// Commented out, the "Owned" serve the same purpose as "all", since you can only view your own offers!
//            case OWNED:
//                response = new FetchOffersResponse(findOwnedOffers(authentication.getGroup().getId()));
//                break;
            case SHARED:
                response = new FetchOffersResponse(findSharedOffers(authentication, request));
                break;
            default:
                response = new FetchOffersResponse(IWSErrors.NOT_PERMITTED, "The search type is not permitted");
        }

        return response;
    }

    public FetchEmployerInformationResponse fetchEmployers(final Authentication authentication, final FetchEmployerInformationRequest request) {
        final FetchEmployerInformationResponse response;

        response = new FetchEmployerInformationResponse(convertToEmployerInformationList(dao.findOffersByLikeEmployerName(authentication, request.getName())));

        return response;
    }

    private List<Offer> findAllOffers(final Authentication authentication, final FetchOffersRequest request) {
        // Must be extended with Pagination
        final List<OfferEntity> found = dao.findAllOffers(authentication);

        return convertEntityList(found);
    }

    private List<Offer> findOffers(final Authentication authentication, final List<Long> ids) {
        final List<OfferEntity> found = dao.findOffers(authentication, ids);

        return convertEntityList(found);
    }

    private List<Offer> findSharedOffers(final Authentication authentication, final FetchOffersRequest request) {
        // Must be extended with Pagination
        final List<OfferEntity> found = dao.findSharedOffers(authentication);

        return convertEntityList(found);
    }

    private List<Offer> findOffersByEmployerName(final Authentication authentication, final String employerName) {
        final List<OfferEntity> found = dao.findOffersByEmployerName(authentication, employerName);

        return convertEntityList(found);
    }

    private List<Offer> findOffersByLikeEmployerName(final Authentication authentication, final String employerName) {
        final List<OfferEntity> found = dao.findOffersByLikeEmployerName(authentication, employerName);

        return convertEntityList(found);
    }

    private static List<Offer> convertEntityList(final List<OfferEntity> found) {
        final List<Offer> result = new ArrayList<>(found.size());

        for (final OfferEntity entity : found) {
            result.add(OfferTransformer.transform(entity));
        }

        return result;
    }

    private static List<EmployerInformation> convertToEmployerInformationList(final List<OfferEntity> found) {
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

    public FetchOfferTemplateResponse fetchOfferTemplates(final Authentication authentication, final FetchOfferTemplatesRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public void processPublishGroups(final Authentication authentication, final PublishGroupRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public FetchPublishGroupResponse fetchPublishGroups(final Authentication authentication, final FetchPublishGroupsRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public FetchGroupsForSharingResponse fetchGroupsForSharing(final Authentication authentication, final FetchGroupsForSharingRequest request) {
        final List<Group> groupList = AdministrationTransformer.transform(dao.findGroupsForSharing(authentication.getGroup()));

        return new FetchGroupsForSharingResponse(groupList);
    }

    /**
     * Method for proccesing publishing (sharing) of offer. Passing empty list of groups means complete unsharing
     * of the offer. Otherwise the offer is share to those groups in the list.
     *
     * @param authentication
     * @param request
     */
    public void processPublishOffer(final Authentication authentication, final PublishOfferRequest request) {
        final Set<String> externalOfferIds = request.getOfferIds();

        //verify Group exist for given groupId
        final List<GroupEntity> groups = getAndVerifyGroupExist(request.getGroupIds());

        verifyPublishRequest(authentication, request, groups);

        for (final String externalOfferId : externalOfferIds) {
            dao.unshareFromAllGroups(externalOfferId);
        }

        final List<OfferEntity> offers = dao.findOffersByExternalId(authentication, request.getOfferIds());
        publishOffer(authentication, offers, groups);
    }

    private void verifyPublishRequest(final Authentication authentication, final PublishOfferRequest request, final List<GroupEntity> groupEntities) {
        //verify only allowed group type(s) are share to
        verifyGroupTypeToBeShareTo(groupEntities);
        //verify that the user's group owns all offers before performing sharing
        verifyOffersOwnership(authentication, request.getOfferIds());
        //verify that an offer is not shared to the owner of the offer
        verifyNotSharingToItself(authentication, groupEntities);
    }

    private void verifyNotSharingToItself(final Authentication authentication, final List<GroupEntity> groupEntities) {
        // All operations in the Exchange module requires that a user is a
        // member of either a National or SAR group. As it is only possible to
        // be member of one, then the Authentication/Authorization module can
        // easily extract this information, and does it as well. The Group from
        // the Authentication Object is the users National / SAR Group
        final GroupEntity nationalGroup = authentication.getGroup();

        for (final GroupEntity group : groupEntities) {
            if (group.getExternalId().equals(nationalGroup.getExternalId())) {
                // TODO 20130422 by Kim; @Michal, is it really needed to throw an Exception here ? Wouldn't it be more helpful to simply omit their own Group ?
                throw new VerificationException("Cannot publish offers to itself.");
            }
        }
    }

    private List<GroupEntity> getAndVerifyGroupExist(final List<String> groupIds) {
        final List<GroupEntity> groups = new ArrayList<>();

        for (final String groupId : groupIds) {
            final GroupEntity groupEntity = dao.findGroupByExternalId(groupId);
            if (groupEntity == null) {
                throw new VerificationException("The group with id = '" + groupId + "' does not exist.");
            }
            groups.add(groupEntity);
        }

        return groups;
    }

    private void verifyGroupTypeToBeShareTo(final List<GroupEntity> groups) {
        for(final GroupEntity group : groups) {
            if(group.getGroupType().getGrouptype() != GroupType.NATIONAL && group.getGroupType().getGrouptype() != GroupType.SAR) {
                throw new VerificationException("The group type '" + group.getGroupType().getGrouptype() + "' is not allowed to be used for publishing of offers.");
            }
        }
    }

    private void verifyOffersOwnership(final Authentication authentication, final Set<String> offerExternalIds) {
        final List<OfferEntity> offers = dao.findOffersByExternalId(authentication, offerExternalIds);
        final Set<String> fetchedOffersExtId = new HashSet<>(offers.size());
        for (final OfferEntity offer : offers) {
            fetchedOffersExtId.add(offer.getExternalId());
        }

        for (final String externalId : offerExternalIds) {
            if (!fetchedOffersExtId.contains(externalId)) {
                throw new VerificationException("The offer with externalId '" + externalId + "' is not owned by the group '" + authentication.getGroup().getGroupName() + "'.");
            }
        }
    }

    private void publishOffer(final Authentication authentication, final List<OfferEntity> offers, final List<GroupEntity> groups) {
        for (final GroupEntity group : groups) {
            for (final OfferEntity offer : offers) {
                persistPublisingGroup(authentication, offer, group);
            }
        }
    }

    private void persistPublisingGroup(final Authentication authentication, final OfferEntity offer, final GroupEntity group) {
        final OfferGroupEntity offerGroupEntity = new OfferGroupEntity(offer, group);
        offerGroupEntity.setCreatedBy(authentication.getUser());

        dao.persist(authentication, offerGroupEntity);
    }

    public FetchPublishOfferResponse fetchPublishedOfferInfo(final Authentication authentication, final FetchPublishOfferRequest request) {
        //TODO distinguish somehow a request for info about offers shared 'to me' and 'by me', now it's 'by me'
        final FetchPublishOfferResponse response;

        verifyOffersOwnership(authentication, new HashSet<String>(request.getOffersId()));

        final List<String> externalIds = request.getOffersId();
        final Map<String, List<OfferGroup>> result = new HashMap<>(externalIds.size()); //@Kim: is it better to use the size as parameter for Map constructor?
        for (final String externalId : externalIds) {
            result.put(externalId, convertOfferGroupEntityList(dao.findInfoForSharedOffer(externalId)));
        }

        response = new FetchPublishOfferResponse(result);

        return response;
    }
}
