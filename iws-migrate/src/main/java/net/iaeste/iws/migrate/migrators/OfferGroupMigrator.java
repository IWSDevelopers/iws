/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.migrators.OfferGroupMigrator
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.migrators;

import net.iaeste.iws.api.dtos.exchange.OfferGroup;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.core.transformers.ExchangeTransformer;
import net.iaeste.iws.migrate.daos.IWSDao;
import net.iaeste.iws.migrate.entities.IW3Offer2GroupEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IW4 does not implement the request/answer functionality of iw3, thus
 * following rules for migration apply:<br />
 *   An offer is shared, if the an Offer2Group entity exists AND the nomination
 * deadline is not passed.<br />
 *   Info: If an offer gets unshared in IW4, the offer_to_group entry will be
 * removed.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class OfferGroupMigrator implements Migrator<IW3Offer2GroupEntity> {

    private static final Logger log = LoggerFactory.getLogger(OfferGroupMigrator.class);

    private final Map<Integer, GroupEntity> nationalGroups = new HashMap<>(80);

    @Autowired
    private IWSDao iwsDao;

    public OfferGroupMigrator() {
    }

    public OfferGroupMigrator(final IWSDao iwsDao) {
        this.iwsDao = iwsDao;
    }

    /**
     * We have 150.000+ records in the database, but less than 100 groups. We
     * cache all Groups so we can make a direct Id comparison rather than
     * trusting that the JPA implementation is caching things properly.
     */
    private void init() {
        final List<GroupEntity> groups = iwsDao.findAllGroups(GroupType.NATIONAL);
        for (final GroupEntity entity : groups) {
            nationalGroups.put(entity.getOldId(), entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MigrationResult migrate() {
        throw new IllegalArgumentException("This Migrator is not allowed here.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = "transactionManagerIWS", propagation = Propagation.REQUIRES_NEW)
    public MigrationResult migrate(final List<IW3Offer2GroupEntity> oldEntities) {
        init();
        int persisted = 0;
        int skipped = 0;

        for (final IW3Offer2GroupEntity oldEntity : oldEntities) {
            final GroupEntity group = findCachedGroup(oldEntity.getId().getGroupId());

            if (group != null) {
                final OfferEntity offer = iwsDao.findOfferByOldOfferId(oldEntity.getId().getOfferId());
                if (skipThis(offer)) {
                    log.info("Skipping Offer {} to Group {}.", offer.getRefNo(), group.getGroupName());
                    skipped++;
                } else {
                    final OfferGroupEntity entity = convert(oldEntity);
                    entity.setOffer(offer);
                    entity.setGroup(group);
                    // Those offer which are shared, need to change the state to SHARED
                    offer.setStatus(OfferState.SHARED);

                    try {
                        final OfferGroup offerGroup = ExchangeTransformer.transform(entity);
                        offerGroup.verify();
                        iwsDao.persist(entity);
                        persisted++;
                    } catch (IllegalArgumentException | VerificationException e) {
                        log.error("Cannot process OfferGroup with refno:{} and Group:{} => {}",
                                entity.getOffer().getRefNo(),
                                entity.getGroup().getId(),
                                e.getMessage());
                        skipped++;
                    }
                }
            } else {
                log.info("Failed to migrate OfferGroup for Offer = {} and Group = {}, as the Group doesn't exist.",
                        oldEntity.getId().getOfferId(),
                        oldEntity.getId().getGroupId());
                skipped++;
            }
        }

        return new MigrationResult(persisted, skipped);
    }

    // =========================================================================
    // Internal OfferGroup Migration Methods
    // =========================================================================

    private GroupEntity findCachedGroup(final Integer groupId) {
        final GroupEntity entity;

        if (nationalGroups.containsKey(groupId)) {
            entity = nationalGroups.get(groupId);
        } else {
            entity = null;
        }

        return entity;
    }

    private static boolean skipThis(final OfferEntity offer) {
        final boolean result;

        switch (Integer.valueOf(offer.getId().toString())) {
            case 115:
            case 463:
            case 514:
            case 539:
            case 578:
            case 17968: // Failed in Jenkins
            case 25762: // Failed Locally
                result = true;
                break;
            default:
                result = false;
        }

        return result;
    }

    private static OfferGroupEntity convert(final IW3Offer2GroupEntity oldEntity) {
        final OfferGroupEntity entity = new OfferGroupEntity();

        entity.setStatus(convertOfferStatus(oldEntity.getStatus()));
        entity.setComment(Helpers.convert(oldEntity.getComment()));
        entity.setModified(Helpers.convert(oldEntity.getModified()));
        entity.setCreated(Helpers.convert(oldEntity.getCreated(), oldEntity.getModified()));

        return entity;
    }

    /**
     * IW3 has some different status values than IWS, meaning that the mapping
     * is getting more complicated. The following Query will list the different
     * types of status values:<br />
     * <pre>
     *   select
     *     count(*) as records,
     *     status
     *   from offer2group
     *   group by status;
     * </pre>
     * From the IW3 method "getOfferStatus" (php/exhange/exchange.php), we have
     * the following:
     * <pre>
     * function getOfferStatus ($c)
     * {
     *     switch ($c) {
     *         case 'a' : return "Accepted";
     *         case 'c' : return "Cancelled";
     *         case 'e' : return "Exchanged";
     *         case 'n' : return "New";
     *         case 'o' : return "Nomination Rejected";
     *         case 'p' : return "Nomination";
     *         case 'q' : return "Not Accepted";
     *         case 'r' : return "Declined";
     *         case 's' : return "SN Complete";
     *         case 't' : return "Taken";
     *         case 'u' : return "Nomination Accepted";
     *         case 'v' : return "Viewed";
     *         case 'w' : return "Waiting SN";
     *         case 'x' : return "AC Exchanged";
     *         default  : return "Unknown";
     *     }
     * }
     * </pre>
     * Following was suggested by Matej, in mail to idt.iws mailinglist on
     * 2013-01-02:<br />
     * <pre>
     * 'code' -> iw3 -> iw4
     * 'a' -> accepted -> shared
     * 'c' -> cancelled -> closed
     * 'e' -> exchanged -> shared
     * 'n' -> new -> new
     * 'o' -> nomination rejected -> application rejected
     * 'p' -> nomination -> nominations
     * 'q' -> not accepted -> shared
     * 'r' -> declined -> shared
     * 's' -> sn complete -> shared
     * 't' -> taken -> shared
     * 'u' -> nomination accepted -> nominations
     * 'v' -> viewed -> shared
     * 'w' -> waiting sn -> shared
     * 'x' -> ac exchanged -> shared
     * 'default' -> new
     * </pre>
     *
     * @param status Old IW3 Status
     * @return IWS Status
     */
    private static OfferState convertOfferStatus(final String status) {
        final OfferState state;

        switch (status) {
            case "a": // Accepted
            case "e": // Exchanged
            case "q": // Not Accepted
            case "r": // Declined
            case "s": // SN Complete
            case "t": // Taken
            case "v": // Viewed
            case "w": // Waiting SN
            case "x": // AC Exchanged
            case "o": // Nomination Rejected
                state = OfferState.SHARED;
                break;
            // can only happen, if the offer gets unshared after the the status
            // APPLIED" passed as the IW3 did never used the nomination
            // functionality, this can never be a valid state
            case "c": // Cancelled
                state = OfferState.CLOSED;
                break;
            // See above explanation
            case "p": // Nomination
            case "u": // Nomination Accepted
                state = OfferState.SHARED;
                break;
            case "n": // New
            default:
                // Trac task #615, Offers will start in state Shared
                state = OfferState.SHARED;
        }

        return state;
    }
}
