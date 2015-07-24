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

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.core.transformers.ExchangeTransformer;
import net.iaeste.iws.ejb.cdi.IWSBean;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.ExchangeJpaDao;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TimerService;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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

    //for local testing
    //@Schedule(second = "0", minute = "*/5", hour = "*", info="Every 1 hour, for testing only", persistent = false)
    //for server
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

        LOG.info("processExpiredOffers ended at " + new DateTime());
    }

    private void runExpiredOfferProcessing() {
        try {
            final UserEntity systemUser = accessDao.findUserByUsername("system.user@iaeste.net");
            if (systemUser != null) {
                final List<OfferEntity> offers = exchangeDao.findExpiredOffers(new Date(), AbstractVerification.calculateExchangeYear());
                LOG.info("Found " + offers.size() + " expired offers for exchange year " + AbstractVerification.calculateExchangeYear());
                if (!offers.isEmpty()) {

                    final List<Long> ids = new ArrayList<>(offers.size());
                    for (final OfferEntity offer : offers) {
                        ids.add(offer.getId());
                    }

                    final List<OfferGroupEntity> offerGroups = exchangeDao.findInfoForSharedOffers(ids);
                    final Map<Long, List<OfferGroupEntity>> sharingInfo = prepareOfferGroupMap(ids, offerGroups);

                    for (final OfferEntity offer : offers) {
                        final Authentication authentication = new Authentication(new AuthenticationToken(), systemUser, offer.getEmployer().getGroup(), "empty-trace-id-for-system-user");
                        for (final OfferGroupEntity offerGroup : sharingInfo.get(offer.getId())) {
                            final OfferGroupEntity modifiedOfferGroup = new OfferGroupEntity(offerGroup);
                            modifiedOfferGroup.setStatus(OfferState.EXPIRED);
                            exchangeDao.persist(authentication, offerGroup, modifiedOfferGroup);
                        }
                        OfferEntity modifiedOffer = ExchangeTransformer.transform(ExchangeTransformer.transform(offer));
                        modifiedOffer.setStatus(OfferState.EXPIRED);
                        exchangeDao.persist(authentication, offer, modifiedOffer);
                    }
                }
            } else {
                LOG.warn("System user was not found");
            }
        } catch (IllegalArgumentException | IWSException e) {
            LOG.error("Error in processing expired offers", e);
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
