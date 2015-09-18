/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.ejb.cdi.IWSBean;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.ExchangeJpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TimerService;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Singleton
public class ScheduleJobsBean {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleJobsBean.class);
    @Inject @IWSBean private EntityManager iwsEntityManager;
    private ExchangeDao exchangeDao = null;
    private AccessDao accessDao = null;

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
    public void setIwsEntityManager(final EntityManager iwsEntityManager) {
        this.iwsEntityManager = iwsEntityManager;
    }

    /**
     * {@inheritDoc}
     */
    @PostConstruct
    public void postConstruct() {
        LOG.info("post construct");
        exchangeDao = new ExchangeJpaDao(iwsEntityManager);
        accessDao = new AccessJpaDao(iwsEntityManager);
    }

    @Schedule(second = "0", minute = "1", hour = "0", info = "Every day at 0:01 AM (server time)", persistent = false)
    private void processExpiredOffers() {
        LOG.info("processExpiredOffers started at " + new DateTime());
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
            // We invoke the logic in a try-finally block, so we can switch of
            // the processing regardless of the outcome

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

        LOG.info("processExpiredOffers ended at " + new DateTime());
    }

    /**
     * Runs the Offer Expiration. Although the state for an Offer is part of
     * both the Offer & Offer Shares, only the Offer itself is updated, thus
     * avoiding that any information is lost.<br />
     *   Offers expire if the Nomination Deadline passed. Please see Trac Ticket
     * #1020 for more on the discussion.
     */
    private void runExpiredOfferProcessing() {
        try {
            final List<OfferEntity> offers = exchangeDao.findExpiredOffers(new Date());
            LOG.info("Found " + offers.size() + " expired offers.");

            for (final OfferEntity offer : offers) {
                offer.setStatus(OfferState.EXPIRED);
                exchangeDao.persist( offer);
            }
        } catch (IllegalArgumentException | IWSException e) {
            LOG.error("Error in processing expired offers", e);
        }
    }
}
