/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import static net.iaeste.iws.core.transformers.ExchangeTransformer.transform;
import static net.iaeste.iws.core.util.LogUtil.formatLogMessage;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.dtos.exchange.OfferGroup;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import net.iaeste.iws.api.requests.exchange.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.OfferTemplateRequest;
import net.iaeste.iws.api.requests.exchange.ProcessEmployerRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.PublishGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOfferTemplateResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishGroupResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.core.transformers.AdministrationTransformer;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.StudentDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ExchangeService extends CommonService<ExchangeDao> {

    private static final Logger log = LoggerFactory.getLogger(ExchangeService.class);

    private final Notifications notifications;

    private final AccessDao accessDao;
    private final StudentDao studentDao;

    public ExchangeService(final Settings settings, final ExchangeDao dao, final AccessDao accessDao, final StudentDao studentDao, final Notifications notifications) {
        super(settings, dao);

        this.notifications = notifications;
        this.accessDao = accessDao;
        this.studentDao = studentDao;
    }

    public EmployerResponse processEmployer(final Authentication authentication, final ProcessEmployerRequest request) {
        final EmployerEntity entity = process(authentication, request.getEmployer());
        return new EmployerResponse(transform(entity));
    }

    private EmployerEntity process(final Authentication authentication, final Employer employer) {
        final String externalId = employer.getEmployerId();
        EmployerEntity entity;

        if (externalId == null) {
            entity = dao.findUniqueEmployer(authentication, employer);

            if (entity == null) {
                entity = transform(employer);
                GroupEntity nationalGroup = accessDao.findNationalGroup(authentication.getUser());
                entity.setGroup(nationalGroup);
                processAddress(authentication, entity.getAddress());
                dao.persist(authentication, entity);
            } else {
                processAddress(authentication, entity.getAddress(), employer.getAddress());
                dao.persist(authentication, entity, transform(employer));
            }
        } else {
            final EmployerEntity updated = transform(employer);
            entity = dao.findEmployer(externalId);
            processAddress(authentication, entity.getAddress(), employer.getAddress());
            dao.persist(authentication, entity, updated);
        }

        return entity;
    }

    /**
     * Will attempt to persist a new Offer, meaning that if the Offer already
     * exists (check against the given externalId) and the user is allowed to work
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
        final EmployerEntity employer = process(authentication, request.getOffer().getEmployer());
        final OfferEntity newEntity = transform(request.getOffer());
        final Offer givenOffer = request.getOffer();
        final String externalId = givenOffer.getOfferId();

        if (externalId == null) {
            // Add the Group to the Offer, otherwise our refno checks will fail
            newEntity.getEmployer().setGroup(authentication.getGroup());
            // Before we can persist the Offer, we need to check that the refno
            // is valid. Since the Country is part of the Group, we can simply
            // compare the refno with that
            verifyRefnoValidity(authentication, newEntity);

            final OfferEntity existingEntity = dao.findOfferByRefNo(authentication, newEntity.getRefNo());
            if (existingEntity == null) {
                // Create a new Offer

                newEntity.setExchangeYear(calculateCurrentExchangeYear());
                // Add the employer to the Offer
                newEntity.setEmployer(employer);
                // Set the Offer status to New
                newEntity.setStatus(OfferState.NEW);

                // Persist the Offer with history
                dao.persist(authentication, newEntity);
            } else {
                // An Offer exists with this RefNo, but the Id was not provided,
                // hence we have the case where someone tries to create a new
                // Offer using an existing RefNo, this is not allowed
                throw new IdentificationException(formatLogMessage(authentication, "An Offer with the Reference Number %s already exists.", newEntity.getRefNo()));
            }
        } else {
            // Check if the user is allowed to work with the Object, if not -
            // then a Permission Exception is thrown
            permissionCheck(authentication, authentication.getGroup());

            // Okay, user is permitted. Let's check if we can find this Offer
            final OfferEntity existingEntity = dao.findOfferByExternalIdAndRefNo(authentication, externalId, newEntity.getRefNo());

            if (existingEntity == null) {
                // We could not find an Offer matching the given criterias,
                // hence we have a case, where the user have not provided the
                // correct information, we cannot process this Offer
                throw new IdentificationException(formatLogMessage(authentication, "No Offer could be found with the Id %s and Refefence Number %s.", externalId, newEntity.getRefNo()));
            }

            // Persist the changes, the method takes the existing and merges the
            // new values into it, and finally it also writes an entry in the
            // history table
            dao.persist(authentication, existingEntity, newEntity);
        }

        // Send a notification to the users who so desire. Via the Notifiable
        // Interface, can the Object handle it itself
        notifications.notify(authentication, newEntity, NotificationType.GENERAL);

        final Offer offer = transform(newEntity);

        final UserEntity nationalSecretary = accessDao.findNationalSecretaryByMemberGroup(authentication.getGroup());
        offer.setNsFirstname(nationalSecretary.getFirstname());
        offer.setNsLastname(nationalSecretary.getLastname());

        return new OfferResponse(offer);
    }

    private static Integer calculateCurrentExchangeYear() {
        final Date date = new Date();

        return date.getCurrentYear() + (date.getCurrentMonth() >= Calendar.SEPTEMBER ? 1 : 0);
    }

    private static void verifyRefnoValidity(final Authentication authentication, final OfferEntity offer) {
        final String countryCode = offer.getEmployer().getGroup().getCountry().getCountryCode();
        final String refno = offer.getRefNo();

        if (!refno.startsWith(countryCode)) {
            throw new VerificationException("The reference number is not valid for this country. Received '" + refno.substring(0, 2) + "' but expected '" + countryCode + "'.");
        }

        final Date today = new Date();
        final int year = today.getCurrentYear();
        final int month = today.getCurrentMonth();
        if (month >= Calendar.SEPTEMBER) {
            // According to Trac task #372, refno's must be set to the following
            // year, after September
            if (!refno.startsWith(countryCode + '-' + (year + 1))) {
                final String newRefno = refno.replaceFirst("[A-Z]{2}-\\d{4}", countryCode + '-' + (year + 1));
                log.info(formatLogMessage(authentication, "The refno '%s' is invalid, have replaced it with '%s'.", refno, newRefno));
                offer.setRefNo(newRefno);
            }
        } else {
            // Let's add a check, which will allow both this and next year. As
            // we do not know when countries may start to prepare Offers for
            // next year
            if (!refno.startsWith(countryCode + '-' + year) && !refno.startsWith(countryCode + '-' + (year + 1))) {
                final String newRefno = refno.replaceFirst("[A-Z]{2}-\\d{4}", countryCode + '-' + year);
                log.info(formatLogMessage(authentication, "The refno '%s' is invalid, have replaced it with '%s'.", refno, newRefno));
                offer.setRefNo(newRefno);
            }
        }
    }

    public void deleteOffer(final Authentication authentication, final DeleteOfferRequest request) {
        final OfferEntity foundOffer = dao.findOfferByExternalId(authentication, request.getOfferId());

        if (foundOffer != null) {
            dao.delete(foundOffer);
        } else {
            throw new IWSException(IWSErrors.OBJECT_IDENTIFICATION_ERROR, "Cannot delete Offer with OfferId " + request.getOfferId());
        }
    }

    private static List<OfferGroup> convertOfferGroupEntityList(final List<OfferGroupEntity> found) {
        final List<OfferGroup> result = new ArrayList<>(found.size());

        for (final OfferGroupEntity entity : found) {
            result.add(transform(entity));
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

    public FetchGroupsForSharingResponse fetchGroupsForSharing(final Authentication authentication) {
        final List<Group> groupList = AdministrationTransformer.transform(dao.findGroupsForSharing(authentication.getGroup()));

        return new FetchGroupsForSharingResponse(groupList);
    }

    /**
     * Method for processing publishing (sharing) of offer. Passing empty list of groups means complete unsharing
     * of the offer. Otherwise the offer is unshared for groups that are not present in the request and shared to
     * such new groups in request for which there is no OfferGroupEntity.
     *
     * @param authentication
     * @param request
     */
    public void processPublishOffer(final Authentication authentication, final PublishOfferRequest request) {
        //verify Group exist for given groupId
        final List<GroupEntity> groups = getAndVerifyGroupExist(request.getGroupIds());

        verifyPublishRequest(authentication, request, groups);
        final List<OfferEntity> offers = dao.findOffersByExternalId(authentication, request.getOfferIds());

        for (final OfferEntity offer : offers) {
            //TODO tune the number of DB queries, get everything at once
            final List<OfferGroupEntity> allOfferGroups = dao.findInfoForSharedOffer(offer.getId());

            final List<OfferGroupEntity> unshareOfferGroups = new ArrayList<>();
            final List<GroupEntity> keepSharing = new ArrayList<>();
            final List<OfferGroupEntity> keepOfferGroups = new ArrayList<>();
            final List<GroupEntity> resharing = new ArrayList<>();
            final List<OfferGroupEntity> reshareGroups = new ArrayList<>();

            for(final OfferGroupEntity offerGroup : allOfferGroups) {
                if (groups.contains(offerGroup.getGroup())) {
                    if (offerGroup.getStatus() == OfferState.CLOSED) {
                        resharing.add(offerGroup.getGroup());
                        reshareGroups.add(offerGroup);
                    } else {
                        keepSharing.add(offerGroup.getGroup());
                        keepOfferGroups.add(offerGroup);
                    }
                } else {
                    if (offerGroup.getStatus() != OfferState.CLOSED) {
                        unshareOfferGroups.add(offerGroup);
                    }
                }
            }

            boolean updateOfferState = false;

            final List<GroupEntity> newSharing = new ArrayList<>(groups);
            newSharing.removeAll(keepSharing);
            newSharing.removeAll(resharing);

            if (!newSharing.isEmpty()) {
                updateOfferState = keepOfferGroups.isEmpty() ? true : updateOfferState;
                keepOfferGroups.addAll(publishOffer(authentication, offer, newSharing));
            }

            if (!resharing.isEmpty()) {
                updateOfferState = keepOfferGroups.isEmpty() ? true : updateOfferState;
                keepOfferGroups.addAll(republishOffer(authentication, offer, reshareGroups));
            }

            if (!unshareOfferGroups.isEmpty()) {
                final OfferState removedState = unshareOfferFromGroups(offer, unshareOfferGroups);
                if (offer.getStatus() == removedState) {
                    updateOfferState = true;
                }
            }

            if (updateOfferState) {
                final OfferState oldOfferState = offer.getStatus();
                OfferState newOfferState = isOtherCurrentOfferGroupStateHigher(oldOfferState, OfferState.SHARED) ? OfferState.SHARED : oldOfferState;

                if (!keepOfferGroups.isEmpty()) {
                    for (final OfferGroupEntity offerGroup : keepOfferGroups) {
                        if (offerGroup.getStatus() != oldOfferState && isOtherCurrentOfferGroupStateHigher(newOfferState, offerGroup.getStatus())) {
                            newOfferState = offerGroup.getStatus();
                        }
                    }
                } else if (newSharing.isEmpty()) {
                    newOfferState = OfferState.OPEN;
                }


                if (oldOfferState != newOfferState) {
                    offer.setStatus(newOfferState);
                }
            }

            if (request.getNominationDeadline() != null) {
                offer.setNominationDeadline(request.getNominationDeadline().toDate());
            }
            dao.persist(authentication, offer);
        }
    }

    /**
     * Returns highest OfferGroup state from all unshared OfferGroups
     * @param offer
     * @param offerGroups
     * @return OfferState
     */
    private OfferState unshareOfferFromGroups(final OfferEntity offer, final List<OfferGroupEntity> offerGroups) {
        OfferState result = OfferState.NEW; //first possible state for offer

        for(final OfferGroupEntity offerGroup : offerGroups) {
            if (offerGroup.getStatus() != result && (offer.getStatus() == offerGroup.getStatus() || isOtherCurrentOfferGroupStateHigher(offer.getStatus(), offerGroup.getStatus()))) {
                result = offerGroup.getStatus();
            }

            //final Boolean keepOfferGroup = studentDao.otherDomesticApplicationsWithCertainStatus(offerGroup.getId(), EnumSet.allOf(ApplicationStatus.class));
            if (offerGroup.getHasApplication()) {
                offerGroup.setStatus(OfferState.CLOSED);
                dao.persist(offerGroup);
            } else {
                dao.delete(offerGroup);
            }
        }
        return result;
    }

    private static Boolean isOtherCurrentOfferGroupStateHigher(final OfferState oldState, final OfferState otherState) {
        Boolean result = false;
        switch (oldState) {
            case NEW:
                result = true;
                break;
            case EXPIRED:
            case OPEN:
                switch (otherState) {
                    case SHARED:
                    case NOMINATIONS:
                    case AT_EMPLOYER:
                    case ACCEPTED:
                    case COMPLETED:
                        result = true;
                        break;
                }
                break;
            case SHARED:
                switch (otherState) {
                    case NOMINATIONS:
                    case AT_EMPLOYER:
                    case ACCEPTED:
                    case COMPLETED:
                        result = true;
                        break;
                }
                break;
            case NOMINATIONS:
                switch (otherState) {
                    case AT_EMPLOYER:
                    case ACCEPTED:
                    case COMPLETED:
                        result = true;
                        break;
                }
                break;
            case AT_EMPLOYER:
                switch (otherState) {
                    case ACCEPTED:
                    case COMPLETED:
                        result = true;
                        break;
                }
                break;
            case ACCEPTED:
                switch (otherState) {
                    case COMPLETED:
                        result = true;
                        break;
                }
                break;
            case COMPLETED:
                break;
        }
        return result;
    }

    private void verifyPublishRequest(final Authentication authentication, final PublishOfferRequest request, final List<GroupEntity> groupEntities) {
        //verify only allowed group type(s) are share to
        verifyGroupTypeToBeShareTo(groupEntities);
        //verify that the user's group owns all offers before performing sharing
        verifyOffersOwnership(authentication, request.getOfferIds());
        //verify that an offer is not shared to the owner of the offer
        verifyNotSharingToItself(authentication, groupEntities);
    }

    private static void verifyNotSharingToItself(final Authentication authentication, final List<GroupEntity> groupEntities) {
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
        final List<GroupEntity> groups = new ArrayList<>(groupIds.size());

        for (final String groupId : groupIds) {
            final GroupEntity groupEntity = dao.findGroupByExternalId(groupId);
            if (groupEntity == null) {
                throw new VerificationException("The group with id = '" + groupId + "' does not exist.");
            }
            groups.add(groupEntity);
        }

        return groups;
    }

    private static void verifyGroupTypeToBeShareTo(final List<GroupEntity> groups) {
        for (final GroupEntity group : groups) {
            if (group.getGroupType().getGrouptype() != GroupType.NATIONAL) {
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

    private List<OfferGroupEntity> publishOffer(final Authentication authentication, final OfferEntity offer, final List<GroupEntity> groups) {
        final List<OfferGroupEntity> result = new ArrayList<>(groups.size());
        for (final GroupEntity group : groups) {
            result.add(persistPublishingGroup(authentication, offer, group));
        }
        return result;
    }

    private List<OfferGroupEntity> republishOffer(final Authentication authentication, final OfferEntity offer, final List<OfferGroupEntity> offerGroups) {
        final List<OfferGroupEntity> result = new ArrayList<>(offerGroups.size());
        for (final OfferGroupEntity offerGroup : offerGroups) {
            //TODO reset application states?
            offerGroup.setStatus(OfferState.SHARED);
            dao.persist(authentication, offerGroup);
            result.add(offerGroup);
        }
        return result;
    }

    private OfferGroupEntity persistPublishingGroup(final Authentication authentication, final OfferEntity offer, final GroupEntity group) {
        final OfferGroupEntity offerGroupEntity = new OfferGroupEntity(offer, group);
        offerGroupEntity.setCreatedBy(authentication.getUser());

        dao.persist(authentication, offerGroupEntity);
        return offerGroupEntity;
    }

}
