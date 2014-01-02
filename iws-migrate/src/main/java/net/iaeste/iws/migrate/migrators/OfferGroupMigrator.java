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
            final OfferGroupEntity entity = convert(oldEntity);
            entity.setOffer(exchangeDao.findOfferByOldOfferId(oldEntity.getOffer().getOfferid()));
            entity.setGroup(accessDao.findGroupByIW3Id(oldEntity.getGroup().getGroupid()));

            try {
                // Need to add support for duplicate entries! However, before
                // doing anything in this direction, I need to clarify how
                // Henrik is going to handle the request for changes...
                final OfferGroup offerGroup = ExchangeTransformer.transform(entity);
                offerGroup.verify();
                accessDao.persist(entity);
                persisted++;
            } catch (IllegalArgumentException | VerificationException e) {
                log.error("Cannot process OfferGroup with refno:{} and Group:{} => {}", entity.getOffer().getRefNo(), entity.getGroup().getId(), e.getMessage());
                skipped++;
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
     *
     * @param status Old IW3 Status
     * @return IWS Status
     */
    private static OfferState convertOfferStatus(final String status) {
        final OfferState state;

        switch (status) {
            case "e": // Exchanged
                state = OfferState.EXCHANGED;
                break;
            case "n": // New
                state = OfferState.NEW;
                break;
            // Help... :-(
            case "a": // Accepted
            case "c": // Cancelled
            case "o": // Nomination Rejected
            case "p": // Nomination
            case "q": // Not Accepted
            case "r": // Declined
            case "t": // Taken
            case "v": // Viewed
            case "w": // Waiting SN
            case "x": // AC Exchanged
            default:
                state = OfferState.NEW;
        }

        return state;
    }
}
