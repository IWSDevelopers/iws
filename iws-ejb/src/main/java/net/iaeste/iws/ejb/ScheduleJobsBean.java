/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.ScheduleJobsBean
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb;

import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.jpa.ExchangeJpaDao;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Singleton
public class ScheduleJobsBean {

    private static final Logger log = LoggerFactory.getLogger(ScheduleJobsBean.class);

    private EntityManager iwsEntityManager = null;
    private ExchangeDao exchangeDao = null;

    @Resource
    private TimerService timerService;

    private final Object lock = new Object();
    private boolean processExpiredOfferIsRunning = false;

    /**
     * Setter for the JNDI injected persistence context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param iwsEntityManager Transactional Entity Manager instance
     */
    @PersistenceContext(unitName = "iwsDatabase")
    public void setIwsEntityManager(final EntityManager iwsEntityManager) {
        this.iwsEntityManager = iwsEntityManager;
    }

    /**
     * {@inheritDoc}
     */
    @PostConstruct
    public void postConstruct() {
        log.info("post construct");
        exchangeDao = new ExchangeJpaDao(iwsEntityManager);
    }

    //for local testing
    //@Schedule(second = "*/30", minute = "*", hour = "*", info="Every 30 seconds")
    //for server
    @Schedule(second = "0", minute = "1", hour = "0", info = "Every day at 0:01 AM (server time)")
    private void processExpiredOffers() {
        log.info("processExpiredOffers started at " + new DateTime());
        final boolean run;

        synchronized (lock) {
            if (!processExpiredOfferIsRunning) {
                run = true;
                processExpiredOfferIsRunning = true;
            } else {
                run = false;
            }
        }

        if (run) {
            // We invcoke the logic in a try-finally block, so we can switch of
            // the processing regardlessly of the outcome

            try {
                // Now we invoke the actual logic, outside of the sync block,
                // as to not block anything
                runExpiredOfferProcessing();
            } finally {
                // Now the worker has completed the job, we can set the
                // processing flag back to false, so another instance can start
                synchronized (lock) {
                    processExpiredOfferIsRunning = false;
                }
            }
        }

        log.info("processExpiredOffers ended at " + new DateTime());
    }

    private void runExpiredOfferProcessing() {
        try {
            final List<OfferEntity> offers = exchangeDao.findExpiredOffers(new Date());
            final List<Long> ids = new ArrayList<>(offers.size());
            for (final OfferEntity offer : offers) {
                ids.add(offer.getId());
            }

            final List<OfferGroupEntity> offerGroups = exchangeDao.findInfoForSharedOffers(ids);
            final Map<Long, List<OfferGroupEntity>> sharingInfo = prepareOfferGroupMap(ids, offerGroups);

            final List<Long> removeOfferGroup = new ArrayList<>();
            final List<Long> closeOfferGroup = new ArrayList<>();

            for (final Long id : ids) {
                for (final OfferGroupEntity offerGroup : sharingInfo.get(id)) {
                    if (offerGroup.getHasApplication()) {
                        closeOfferGroup.add(offerGroup.getId());
                    } else {
                        removeOfferGroup.add(offerGroup.getId());
                    }
                }
            }

            exchangeDao.updateOfferState(ids, OfferState.EXPIRED);
            exchangeDao.deleteOfferGroup(removeOfferGroup);
            exchangeDao.updateOfferGroupState(closeOfferGroup, OfferState.CLOSED);
        } catch (IllegalArgumentException | IWSException e) {
            log.error("Error in processing expired offers", e);
        }
    }

    private Map<Long, List<OfferGroupEntity>> prepareOfferGroupMap(final List<Long> ids, final List<OfferGroupEntity> offerGroups) {
        final Map<Long, List<OfferGroupEntity>> result = new HashMap<>(ids.size());

        for (final Long id : ids) {
            result.put(id, new ArrayList<OfferGroupEntity>());
        }

        for (final OfferGroupEntity offerGroup : offerGroups) {
            result.get(offerGroup.getOffer().getId()).add(offerGroup);
        }

        return result;
    }
}
