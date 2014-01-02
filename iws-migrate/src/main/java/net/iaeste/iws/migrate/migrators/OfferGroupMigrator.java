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
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.core.transformers.ExchangeTransformer;
import net.iaeste.iws.migrate.entities.IW3Offer2GroupEntity;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class OfferGroupMigrator extends AbstractMigrator<IW3Offer2GroupEntity> {

    private static final Logger log = LoggerFactory.getLogger(OfferGroupMigrator.class);

    private final ExchangeDao exchangeDao;

    public OfferGroupMigrator(final AccessDao accessDao, final ExchangeDao exchangeDao) {
        super(accessDao);
        this.exchangeDao = exchangeDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MigrationResult migrate(final List<IW3Offer2GroupEntity> oldEntities) {
        int persisted = 0;
        int skipped = 0;

        for (final IW3Offer2GroupEntity oldEntity : oldEntities) {
            final OfferEntity offer = exchangeDao.findOfferByOldOfferId(oldEntity.getId().getOfferId());
            final GroupEntity group = accessDao.findGroupByIW3Id(oldEntity.getId().getGroupId());

            if (offer != null && group != null) {
                final OfferGroupEntity entity = convert(oldEntity);
                entity.setOffer(offer);
                entity.setGroup(group);

                try {
                    final OfferGroup offerGroup = ExchangeTransformer.transform(entity);
                    offerGroup.verify();
                    accessDao.persist(entity);
                    persisted++;
                } catch (IllegalArgumentException | VerificationException e) {
                    log.error("Cannot process OfferGroup with refno:{} and Group:{} => {}", entity.getOffer().getRefNo(), entity.getGroup().getId(), e.getMessage());
                    skipped++;
                } catch (Exception e) {
                    log.error("Fuck! " + e.getMessage(), e);
                }
            } else {
                if ((offer == null) && (group == null)) {
                    log.info("Offer with Id = {} and Group with Id = {}, could not be found.", oldEntity.getId().getOfferId(), oldEntity.getId().getGroupId());
                } else if (offer == null) {
                    log.info("Offer with Id = {} could not be found.", oldEntity.getId().getOfferId());
                } else {
                    log.info("Group with Id = {}, could not be found.", oldEntity.getId().getGroupId());
                }
            }
        }

        return new MigrationResult(persisted, skipped);
    }

    private OfferGroupEntity convert(final IW3Offer2GroupEntity oldEntity) {
        final OfferGroupEntity entity = new OfferGroupEntity();

        entity.setStatus(convertOfferStatus(oldEntity.getStatus()));
        entity.setComment(convert(oldEntity.getComment()));
        entity.setModified(convert(oldEntity.getModified()));
        entity.setCreated(convert(oldEntity.getCreated(), oldEntity.getModified()));

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
     * 'a' - > accepted -> shared
     * 'c' - > cancelled -> closed
     * 'e' - > exchanged -> shared
     * 'n' - > new -> new
     * 'o' - > nomination rejected -> application rejected
     * 'p' -> nomination -> nominations
     * 'q' -> not accepted -> shared
     * 'r' -> declined -> shared
     * 's' -> sn complete -> shared
     * 't' -> taken -> shared
     * 'u' -> nomination accepted -> nominations
     * 'v' - > viewed -> shared
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
                state = OfferState.SHARED;
                break;
            case "c": // Cancelled
                state = OfferState.CLOSED;
                break;
            case "o": // Nomination Rejected
                state = OfferState.APPLICATION_REJECTED;
                break;
            case "p": // Nomination
            case "u": // Nomination Accepted
                state = OfferState.NOMINATIONS;
                break;
            case "n": // New
            default:
                state = OfferState.NEW;
        }

        return state;
    }
}
