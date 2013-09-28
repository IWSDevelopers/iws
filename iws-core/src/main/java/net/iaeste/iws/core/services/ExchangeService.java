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
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.core.transformers.AdministrationTransformer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class ExchangeService extends CommonService<ExchangeDao> {

    private static final Logger log = Logger.getLogger(ExchangeService.class);

    private final Notifications notifications;

    public ExchangeService(final ExchangeDao dao, final Notifications notifications) {
        super(dao);

        this.notifications = notifications;
    }

    public EmployerResponse processEmployer(final Authentication authentication, final ProcessEmployerRequest request) {
        final EmployerEntity entity = process(authentication, request.getEmployer());
        return new EmployerResponse(transform(entity));
    }

    private EmployerEntity process(final Authentication authentication, final Employer employer) {
        final String externalId = employer.getId();
        EmployerEntity entity;

        if (externalId == null) {
            entity = dao.findUniqueEmployer(authentication, employer);

            if (entity == null) {
                entity = transform(employer);
                entity.setGroup(authentication.getGroup());
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
        final String externalId = givenOffer.getId();

        if (externalId == null) {
            // Add the Group to the Offer, otherwise our refno checks will fail
            newEntity.setGroup(authentication.getGroup());
            // Before we can persist the Offer, we need to check that the refno
            // is valid. Since the Country is part of the Group, we can simply
            // compare the refno with that
            verifyRefnoValidity(authentication, newEntity);

            final OfferEntity existingEntity = dao.findOfferByRefNo(authentication, newEntity.getRefNo());
            if (existingEntity == null) {
                // Create a new Offer

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
        return new OfferResponse(offer);
    }

    private static void verifyRefnoValidity(final Authentication authentication, final OfferEntity offer) {
        final String countryCode = offer.getGroup().getCountry().getCountryCode();
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
     * Method for proccesing publishing (sharing) of offer. Passing empty list of groups means complete unsharing
     * of the offer. Otherwise the offer is share to those groups in the list.
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
            offer.setStatus(OfferState.NEW);
            dao.persist(authentication, offer);
            dao.unshareFromAllGroups(offer.getExternalId());
        }

        publishOffer(authentication, offers, groups, request.getNominationDeadline());
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
            if (group.getGroupType().getGrouptype() != GroupType.NATIONAL && group.getGroupType().getGrouptype() != GroupType.SAR) {
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

    private void publishOffer(final Authentication authentication, final List<OfferEntity> offers, final List<GroupEntity> groups, final Date nominationDeadline) {
        for (final OfferEntity offer : offers) {
            if (!groups.isEmpty()) {
                offer.setStatus(OfferState.SHARED);
                if (nominationDeadline != null) {
                    offer.setNominationDeadline(nominationDeadline.toDate());
                }

                dao.persist(authentication, offer);
            }
            for (final GroupEntity group : groups) {
                persistPublisingGroup(authentication, offer, group);
            }
        }
    }

    private void persistPublisingGroup(final Authentication authentication, final OfferEntity offer, final GroupEntity group) {
        final OfferGroupEntity offerGroupEntity = new OfferGroupEntity(offer, group);
        offerGroupEntity.setCreatedBy(authentication.getUser());

        dao.persist(authentication, offerGroupEntity);
    }

}
