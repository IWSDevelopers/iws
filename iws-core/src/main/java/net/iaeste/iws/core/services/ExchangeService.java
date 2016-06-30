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
package net.iaeste.iws.core.services;

import static net.iaeste.iws.api.enums.exchange.OfferState.AT_EMPLOYER;
import static net.iaeste.iws.api.enums.exchange.OfferState.OPEN;
import static net.iaeste.iws.api.enums.exchange.OfferState.VIEWED;
import static net.iaeste.iws.common.utils.LogUtil.formatLogMessage;
import static net.iaeste.iws.api.util.Verifications.calculateExchangeYear;
import static net.iaeste.iws.common.utils.StringUtils.toUpper;
import static net.iaeste.iws.core.transformers.ExchangeTransformer.transform;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.exchange.EmployerRequest;
import net.iaeste.iws.api.requests.exchange.HideForeignOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferRequest;
import net.iaeste.iws.api.requests.exchange.ProcessPublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.RejectOfferRequest;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.exceptions.ExchangeException;
import net.iaeste.iws.common.exceptions.VerificationException;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.entities.exchange.PublishingGroupEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ExchangeService extends CommonService<ExchangeDao> {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeService.class);

    private final Notifications notifications;

    private final AccessDao accessDao;

    ExchangeService(final Settings settings, final ExchangeDao dao, final AccessDao accessDao, final Notifications notifications) {
        super(settings, dao);

        this.notifications = notifications;
        this.accessDao = accessDao;
    }

    public EmployerResponse processEmployer(final Authentication authentication, final EmployerRequest request) {
        final EmployerEntity entity = process(authentication, request.getEmployer());
        return new EmployerResponse(transform(entity));
    }

    private EmployerEntity process(final Authentication authentication, final Employer employer) {
        final String externalId = employer.getEmployerId();
        EmployerEntity entity = dao.findUniqueEmployer(authentication, employer);

        if (externalId == null) {
            if (entity == null) {
                final GroupEntity nationalGroup = accessDao.findNationalGroup(authentication.getUser());
                entity = transform(employer);
                entity.setGroup(nationalGroup);
                processAddress(authentication, entity.getAddress());
                dao.persist(authentication, entity);
            } else {
                processAddress(authentication, entity.getAddress(), employer.getAddress());
                dao.persist(authentication, entity, transform(employer));
            }
        } else {
            if (entity == null) {
                entity = dao.findEmployer(authentication, externalId);
                final EmployerEntity updated = transform(employer);
                processAddress(authentication, entity.getAddress(), employer.getAddress());
                dao.persist(authentication, entity, updated);
            } else if (externalId.equals(entity.getExternalId())) {
                processAddress(authentication, entity.getAddress(), employer.getAddress());
                dao.persist(authentication, entity, transform(employer));
            } else {
                // We now have the absurd case, that we have two "identical"
                // Objects, but with different Id's. This error handling was
                // added as there is a problem with IW4 Offer Duplication, where
                // a list of n similar Employers may be fetched, but for some
                // strange reason, the IW4 always picks the most recent Id for
                // the Employer - regardless if this is the correct Id or not.
                throw new IdentificationException("Processing failed, as this Employer already exist with a different Id than provided.");
            }
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
    public OfferResponse processOffer(final Authentication authentication, final OfferRequest request) {
        final OfferResponse response = new OfferResponse();
        final Action action = request.getAction();

        if (action == Action.PROCESS) {
            final EmployerEntity employer = process(authentication, request.getOffer().getEmployer());
            final String externalId = request.getOffer().getOfferId();
            final OfferEntity entity;

            if (externalId == null) {
                entity = processNewOffer(authentication, employer, request);
            } else {
                entity = processExistingOffer(authentication, request);
            }

            final Offer offer = transform(entity);
            final UserEntity nationalSecretary = accessDao.findNationalSecretaryByMemberGroup(authentication.getGroup());
            offer.setNsFirstname(nationalSecretary.getFirstname());
            offer.setNsLastname(nationalSecretary.getLastname());
            response.setOffer(cleanOfferLanguage(offer));

            // Send a notification to the users who so desire. Via the Notifiable
            // Interface, can the Object handle it itself
            notifications.notify(authentication, entity, NotificationType.GENERAL);
        } else if (action == Action.DELETE) {
            deleteOffer(authentication, request);
        } else {
            throw new IWSException(IWSErrors.ILLEGAL_ACTION, "The action " + action + " is not supported in this context.");
        }

        return response;
    }

    private OfferEntity processNewOffer(final Authentication authentication, final EmployerEntity employer, final OfferRequest request) {
        final Offer givenOffer = request.getOffer();
        final OfferEntity newEntity = transform(givenOffer);

        // Add the Group to the Offer, otherwise our ref-no checks will fail
        newEntity.getEmployer().setGroup(authentication.getGroup());
        // Before we can persist the Offer, we need to check that the ref-no
        // is valid. Since the Country is part of the Group, we can simply
        // compare the ref-no with that.
        verifyRefnoValidity(newEntity);

        final OfferEntity existingEntity = dao.findOfferByRefNo(authentication, newEntity.getRefNo());
        if (existingEntity == null) {
            // Create a new Offer

            // We're setting the Exchange Year to the one from the Offer
            // ref-no. Since this should be the controlling number.
            newEntity.setExchangeYear(givenOffer.getExchangeYear());
            // Add the employer to the Offer
            newEntity.setEmployer(employer);
            // Set the Offer status to New
            newEntity.setStatus(OfferState.NEW);

            // Persist the Offer with history
            dao.persist(authentication, newEntity);

            return newEntity;
        } else {
            // An Offer exists with this RefNo, but the Id was not provided,
            // hence we have the case where someone tries to create a new
            // Offer using an existing RefNo, this is not allowed
            throw new IdentificationException(formatLogMessage(authentication, "An Offer with the Reference Number %s already exists.", newEntity.getRefNo()));
        }
    }

    private OfferEntity processExistingOffer(final Authentication authentication, final OfferRequest request) {
        final Offer givenOffer = request.getOffer();
        final OfferEntity newEntity = transform(givenOffer);
        final String externalId = givenOffer.getOfferId();

        // Check if the user is allowed to work with the Object, if not -
        // then a Permission Exception is thrown
        permissionCheck(authentication, authentication.getGroup());

        // Okay, user is permitted. Let's check if we can find this Offer
        final OfferEntity existingEntity = dao.findOfferByExternalIdAndRefNo(authentication, externalId, newEntity.getRefNo());

        if (existingEntity == null) {
            // We could not find an Offer matching the given criteria's,
            // hence we have a case, where the user have not provided the
            // correct information, we cannot process this Offer
            throw new IdentificationException(formatLogMessage(authentication, "No Offer could be found with the Id %s and Reference Number %s.", externalId, newEntity.getRefNo()));
        }

        // Persist the changes, the method takes the existing and merges the
        // new values into it, and finally it also writes an entry in the
        // history table
        dao.persist(authentication, existingEntity, newEntity);

        // Now transform the existing entity for the result
        return existingEntity;
    }

    /**
     * It is allowed to delete Objects from the database, provided that their
     * state indicates that the Offer has never been shared or exchanged, or
     * anything, i.e. that the state is NEW and NEW only.
     *
     * @param authentication User & Group information
     * @param request        Offer Request information, i.e. OfferId
     */
    private void deleteOffer(final Authentication authentication, final OfferRequest request) {
        final OfferEntity foundOffer = dao.findOfferByOwnerAndExternalId(authentication, request.getOfferId());

        if (foundOffer != null) {
            if (foundOffer.getStatus() == OfferState.NEW) {
                dao.delete(foundOffer);
            } else {
                throw new ExchangeException(IWSErrors.CANNOT_DELETE_OFFER, "It is not permitted to delete the offer with OfferId " + request.getOfferId() + " because it has been shared already");
            }
        } else {
            throw new IdentificationException("Cannot delete Offer with OfferId " + request.getOfferId());
        }
    }
    /**
     * This method is checking the Offer Reference Number, and verifying that
     * new Offers follow the requirements:
     * <ul>
     * <li>Offer must follow the Regex: [A-Z]{2}-[0-9]{4}-[A-Z0-9\-]{1,8}</li>
     * <li>The Country Code must be the official one for the given Committee.</li>
     * <li>The Year must be either current Year (prior to September 1st) or next year (enforced after September 1st)</li>
     * </ul>
     * If either of these is failing, then the method will throw a new
     * ${code VerificationException}.
     *
     * @param offer Offer to verify
     * @throws VerificationException if the Reference Number is invalid
     * @see <a href="https://trac.iaeste.net/ticket/1020">Ticket #1020</a>
     */
    static void verifyRefnoValidity(final OfferEntity offer) {
        final String countryCode = offer.getEmployer().getGroup().getCountry().getCountryCode();
        final String refno = offer.getRefNo();
        final String[] parts = toUpper(refno).split("-");

        // First, we're checking that the CountryCode is correct. Since we've
        // split the Reference Number into at least 3 parts ("-" is allowed
        // in the running number part), we can just look at the first one.
        if (!countryCode.equals(parts[0])) {
            throw new VerificationException("The reference number is not valid for this country. Received '" + parts[0] + "' but expected '" + countryCode + "'.");
        }

        // Now, we're running two checks, one for the current year and one for
        // the next Exchange Year. Some countries may start adding Offers for
        // the following Exchange Year before the fixed change date. So for all
        // Offers created prior to September 1st, we're allowing both this and
        // next year. But all offers created after September 1st, it must be the
        // new Exchange Year.
        final Date today = new Date();
        final int currentYear = today.getCurrentYear();
        final int exchangeYear = calculateExchangeYear();
        final int foundYear = Integer.parseInt(parts[1]);

        if (today.getCurrentMonth() >= Calendar.SEPTEMBER) {
            // We're looking at offers after the Exchange Year change, so we
            // only allow this.
            if (foundYear != exchangeYear) {
                throw new VerificationException("The Exchange Year for the Reference Number '" + refno + "' is invalid, expected is " + exchangeYear + '.');
            }
        } else {
            // Prior to September 1st, we're allowing both the current and next
            // year.
            if ((foundYear != currentYear) || (foundYear != exchangeYear)) {
                throw new VerificationException("The Exchange Year for the Reference Number '" + refno + "- is invalid, expected is " + currentYear + " or " + exchangeYear + '.');
            }
        }
    }

    public void processPublishingGroups(final Authentication authentication, final ProcessPublishingGroupRequest request) {
        if (request.getAction() == Action.DELETE) {
            final String externalId = request.getPublishingGroupId();
            final PublishingGroupEntity existingEntity = dao.getSharingListByExternalIdAndOwnerId(externalId, authentication.getGroup().getId());

            if (existingEntity != null) {
                dao.delete(existingEntity);
            }
        } else {
            final PublishingGroupEntity newEntity = transform(request.getPublishingGroup());

            final List<String> groupIds = new ArrayList<>();
            // Server logs showed that we have a NullPointer Exception in the next line. Meaning that our Validation failed !!!
            for (final Group group : request.getPublishingGroup().getGroups()) {
                if (!group.getGroupId().equals(authentication.getGroup().getExternalId())) {
                    groupIds.add(group.getGroupId());
                }
            }
            final List<GroupEntity> countryList = dao.findNationalGroupsById(groupIds);
            newEntity.setList(countryList);
            newEntity.setGroup(authentication.getGroup());
            final String externalId = newEntity.getExternalId();

            if (externalId == null) {
                dao.persist(authentication, newEntity);
            } else {
                final PublishingGroupEntity existingEntity = dao.getSharingListByExternalIdAndOwnerId(externalId, authentication.getGroup().getId());
                if (existingEntity == null) {
                    throw new IdentificationException(formatLogMessage(authentication, "No Sharing List could be found with the Id %s.", externalId));
                }

                dao.persist(authentication, existingEntity, newEntity);
            }
        }
    }

    /**
     * Method for processing publishing (sharing) of offer. Passing empty list
     * of groups means complete un-sharing of the offer. Otherwise the offer is
     * unshared for groups that are not present in the request and shared to
     * such new groups in request for which there is no OfferGroupEntity.
     *
     * @param authentication User & Group information
     * @param request        Publish Offer Request information
     */
    public PublishOfferResponse processPublishOffer(final Authentication authentication, final PublishOfferRequest request) {
        final OfferEntity offer = dao.findOfferByOwnerAndIdentifier(authentication, request.getOfferId());
        // Before continuing, first we must check the Offer.
        checkOfferForReSharing(offer, request);

        final PublishOfferResponse response = new PublishOfferResponse();
        response.setOfferId(request.getOfferId());
        final List<OfferGroupEntity> shares = new ArrayList<>(0);

        if (request.getAction() == Action.PROCESS) {
            final List<OfferGroupEntity> processed = processOfferSharing(authentication, offer, request);
            for (final OfferGroupEntity entity : processed) {
                shares.add(entity);
            }
        } else {
            // Since no GroupId's has been provided, we're going to remove
            // all Shares by Closing them, however it can only be done for
            // Shares in a non-final state.
            final Set<OfferState> currentStates = EnumSet.of(OfferState.NEW, VIEWED);
            final int records = updateOfferGroupStates(authentication, offer, currentStates, OfferState.CLOSED);
            LOG.info("Closed {} Shares for the Offer {}.", records, offer.getRefNo());
        }

        // Update the status of the Offer, based on the request & states of the
        // shares, and set the list of GroupIds in the response. So it is clear
        // with whom the Offer was shared to.
        updateSharedOffer(authentication, request, offer, shares);
        response.setGroupIds(readGroupIds(shares));

        return response;
    }

    private int updateOfferGroupStates(final Authentication authentication, final OfferEntity offer, final Set<OfferState> currentStates, final OfferState newState) {
        final List<OfferGroupEntity> entities = dao.findInfoForSharedOffer(offer.getId());
        int count = 0;

        for (final OfferGroupEntity entity : entities) {
            boolean update = true;
            for (final OfferState state : currentStates) {
                if (entity.getStatus() == state) {
                    update = false;
                }
            }

            if (update) {
                entity.setStatus(newState);
                dao.persist(authentication, entity);
                count++;
            }
        }

        return count;
    }

    /**
     * Performs some initial checks on the Offer we have found (if any). If no
     * Offer matching the lookup criteria's, i.e. Existing Offer which the User
     * may process which is not in a non-sharable state.
     *
     * @param offer   The Offer to check
     * @param request Publish Offer Request Object
     * @throws VerificationException if not processable
     */
    private static void checkOfferForReSharing(final OfferEntity offer, final PublishOfferRequest request) {
        // Check if the Offer is null, i.e. it is either not existing, deleted
        // or the User is not allowed to work with it (it belongs to a
        // different Group).
        if (offer == null) {
            throw new VerificationException("The offer with Id '" + request.getOfferId() + "' cannot be shared.");
        }

        // Offers which belongs to a different Exchange Year, cannot be shared.
        if (offer.getExchangeYear() != calculateExchangeYear()) {
            throw new VerificationException("Cannot share Offers from '" + offer.getExchangeYear() + "'.");
        }
    }

    private static List<String> readGroupIds(final List<OfferGroupEntity> shares) {
        final List<String> list = new ArrayList<>(shares.size());

        for (final OfferGroupEntity entity : shares) {
            list.add(entity.getGroup().getExternalId());
        }

        return list;
    }

    /**
     * Handles the re-sharing of Offers. Meaning adding, deleting or updating
     * shares. Please note that this handling is not following an approved
     * process, and should be replaced with something better. But for now, it
     * is left as it is. As it is at least more understandable than the old
     * process.
     *
     * @param authentication User Authentication information
     * @param offer          Offer to handling sharing for
     * @param request        Request information, with Groups & Deadline
     * @return Updated share listing
     */
    private List<OfferGroupEntity> processOfferSharing(final Authentication authentication, final OfferEntity offer, final PublishOfferRequest request) {
        final List<String> groupIds = request.getGroupIds();
        final GroupEntity owner = authentication.getGroup();
        final List<GroupEntity> groups = dao.findNationalGroupsByIdForSharing(owner, groupIds);
        // Share or re-share the Offer
        final List<OfferGroupEntity> allOfferGroups = dao.findInfoForSharedOffer(offer.getId());

        final List<OfferGroupEntity> unshareOfferGroups = new ArrayList<>();
        final List<GroupEntity> keepSharing = new ArrayList<>();
        final List<OfferGroupEntity> keepOfferGroups = new ArrayList<>();
        final List<GroupEntity> resharing = new ArrayList<>();
        final List<OfferGroupEntity> reshareGroups = new ArrayList<>();

        for (final OfferGroupEntity offerGroup : allOfferGroups) {
            if (groups.contains(offerGroup.getGroup())) {
                if (EnumSet.of(OfferState.CLOSED, OfferState.EXPIRED, OfferState.REJECTED).contains(offerGroup.getStatus())) {
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

        final List<GroupEntity> newSharing = new ArrayList<>(groups);
        newSharing.removeAll(keepSharing);
        newSharing.removeAll(resharing);

        if (!newSharing.isEmpty()) {
            keepOfferGroups.addAll(publishOffer(authentication, offer, newSharing));
        }

        if (!resharing.isEmpty()) {
            keepOfferGroups.addAll(republishOffer(authentication, offer, reshareGroups));
        }

        if (!unshareOfferGroups.isEmpty()) {
            unshareOfferFromGroups(offer, unshareOfferGroups);
        }

        return keepOfferGroups;
    }

    private void updateSharedOffer(final Authentication authentication, final PublishOfferRequest request, final OfferEntity offer, final List<OfferGroupEntity> shares) {
        final OfferState oldOfferState = offer.getStatus();
        OfferState newOfferState = isOtherCurrentOfferGroupStateHigher(oldOfferState, OfferState.SHARED) ? OfferState.SHARED : oldOfferState;

        if (shares.isEmpty()) {
            newOfferState = OPEN;
        } else {
            for (final OfferGroupEntity offerGroup : shares) {
                if ((offerGroup.getStatus() != oldOfferState) && isOtherCurrentOfferGroupStateHigher(newOfferState, offerGroup.getStatus())) {
                    newOfferState = offerGroup.getStatus();
                }
            }
        }

        // Now, we're done dealing with the Offer Shares - it is time to
        // update the Offer also, to reflect this.
        if (request.getNominationDeadline() != null) {
            offer.setNominationDeadline(request.getNominationDeadline().toDate());
        }
        offer.setStatus(evaluateOfferState(request.getNominationDeadline(), newOfferState, offer.getRefNo()));
        dao.persist(authentication, offer);
    }

    private static OfferState evaluateOfferState(final Date deadline, final OfferState current, final String refno) {
        OfferState state = current;

        if (deadline != null) {
            final Date now = new Date();

            if ((current == OfferState.SHARED) && deadline.isBefore(now)) {
                LOG.info("The Offer {} is currently shared but the deadline is {}. Changing Offer State to Expired.", refno, deadline);
                state = OfferState.EXPIRED;
            } else if ((current == OfferState.EXPIRED) && deadline.isAfter(now)) {
                LOG.info("The Offer {} is currently Expired but the deadline is {}. Changing Offer State to Shared.", refno, deadline);
                state = OfferState.SHARED;
            }
        }

        return state;
    }

    public void processHideForeignOffers(final Authentication authentication, final HideForeignOffersRequest request) {
        if (!request.getOffers().isEmpty()) {
            final List<OfferGroupEntity> offerGroups = dao.findInfoForSharedOffers(authentication.getGroup(), request.getOffers());
            final List<Long> ids = new ArrayList<>(offerGroups.size());
            for (final OfferGroupEntity offerGroup : offerGroups) {
                ids.add(offerGroup.getId());
            }

            dao.hideOfferGroups(ids);
        }
    }

    public void rejectOffer(final Authentication authentication, final RejectOfferRequest request) {
        final List<OfferGroupEntity> offerGroups = dao.findInfoForSharedOffer(request.getOfferId());
        final OfferGroupEntity offerGroupToReject = findOfferGroupByGroup(authentication.getGroup(), offerGroups);

        if (offerGroupToReject != null) {
            final OfferGroupEntity updatedOfferGroup = new OfferGroupEntity(offerGroupToReject);
            updatedOfferGroup.setStatus(OfferState.REJECTED);
            dao.persist(authentication, offerGroupToReject, updatedOfferGroup);

            offerGroups.remove(offerGroupToReject);

            final EnumSet<OfferState> activeStates = EnumSet.of(OfferState.SHARED,
                    AT_EMPLOYER,
                    OfferState.ACCEPTED,
                    OfferState.APPLICATIONS,
                    OfferState.COMPLETED,
                    OfferState.NOMINATIONS);
            boolean updateOfferState = true;
            for (final OfferGroupEntity offerGroup : offerGroups) {
                if (activeStates.contains(offerGroup.getStatus())) {
                    updateOfferState = false;
                    break;
                }
            }

            if (updateOfferState) {
                final List<Long> rejectedOfferIds = new ArrayList<>(1);
                rejectedOfferIds.add(offerGroupToReject.getOffer().getId());

                dao.updateOfferState(rejectedOfferIds, OfferState.REJECTED);
            }
        }
    }

    /**
     * Returns highest OfferGroup state from all unshared OfferGroups
     *
     * @param offer       Offer
     * @param offerGroups List of Groups to remove sharing to
     * @return OfferState
     */
    private OfferState unshareOfferFromGroups(final OfferEntity offer, final List<OfferGroupEntity> offerGroups) {
        OfferState result = OfferState.NEW; //first possible state for offer

        for (final OfferGroupEntity offerGroup : offerGroups) {
            if ((offerGroup.getStatus() != result) && ((offer.getStatus() == offerGroup.getStatus()) || isOtherCurrentOfferGroupStateHigher(offer.getStatus(), offerGroup.getStatus()))) {
                result = offerGroup.getStatus();
            }

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
        final boolean result;

        switch (oldState) {
            case NEW:
                result = true;
                break;
            case EXPIRED:
            case OPEN:
                result = stateCheckForOpen(otherState);
                break;
            case SHARED:
                result = stateCheckForShared(otherState);
                break;
            case NOMINATIONS:
                result = stateCheckForNominations(otherState);
                break;
            case AT_EMPLOYER:
                result = stateCheckForAtEmployer(otherState);
                break;
            case ACCEPTED:
                result = otherState == OfferState.COMPLETED;
                break;
            case REJECTED:
                result = otherState == OfferState.SHARED;
                break;
            default:
                result = false;
        }

        return result;
    }

    private static boolean stateCheckForOpen(final OfferState newState) {
        final boolean result;

        switch (newState) {
            case SHARED:
            case NOMINATIONS:
            case AT_EMPLOYER:
            case ACCEPTED:
            case COMPLETED:
                result = true;
                break;
            default:
                result = false;
        }

        return result;
    }

    private static boolean stateCheckForShared(final OfferState newState) {
        final boolean result;

        switch (newState) {
            case NOMINATIONS:
            case AT_EMPLOYER:
            case ACCEPTED:
            case COMPLETED:
                result = true;
                break;
            default:
                result = false;
        }

        return result;
    }

    private static boolean stateCheckForNominations(final OfferState newState) {
        final boolean result;

        switch (newState) {
            case AT_EMPLOYER:
            case ACCEPTED:
            case COMPLETED:
                result = true;
                break;
            default:
                result = false;
        }

        return result;
    }

    private static boolean stateCheckForAtEmployer(final OfferState newState) {
        final boolean result;

        switch (newState) {
            case ACCEPTED:
            case COMPLETED:
                result = true;
                break;
            default:
                result = false;
        }

        return result;
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

        offer.setStatus(OfferState.SHARED);
        dao.persist(authentication, offer);

        for (final OfferGroupEntity offerGroup : offerGroups) {
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

    private static OfferGroupEntity findOfferGroupByGroup(final GroupEntity group, final List<OfferGroupEntity> offerGroups) {
        OfferGroupEntity result = null;

        for (final OfferGroupEntity offerGroup : offerGroups) {
            if (offerGroup.getGroup().equals(group)) {
                result = offerGroup;
                break;
            }
        }

        return result;
    }
}
