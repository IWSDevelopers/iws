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
import net.iaeste.iws.ejb.notifications.NotificationManager;
import net.iaeste.iws.ejb.notifications.NotificationMessageGenerator;
import net.iaeste.iws.ejb.notifications.NotificationMessageGeneratorFreemarker;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.ExchangeJpaDao;
import net.iaeste.iws.persistence.jpa.NotificationJpaDao;
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
 * @since   1.7
 */
@Singleton
public class ScheduleJobsBean {
    private static final Logger log = LoggerFactory.getLogger(ScheduleJobsBean.class);

    private EntityManager iwsEntityManager = null;
    private AccessDao accessDao = null;
    private ExchangeDao exchangeDao = null;
    @Resource
    private TimerService timerService;
    private final Object LOCK = new Object();

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
        accessDao = new AccessJpaDao(iwsEntityManager);
        exchangeDao =new ExchangeJpaDao(iwsEntityManager);
    }

    //for local testing
    //@Schedule(second = "*/30", minute = "*", hour = "*", info="Every 30 seconds")
    //for server
    @Schedule(second = "0", minute = "1", hour = "0", info="Every day at 0:01 AM (server time)")
    private void processExpiredOffers() {
        //TODO remove log messages when the processing works correctly, i.e. after testing. Or, leave it?
        log.info("processExpiredOffers started at " + new DateTime());
        final boolean run;
        synchronized (LOCK) {
            run = !processExpiredOfferIsRunning;
        }

        if (run) {
            synchronized (LOCK) {
                processExpiredOfferIsRunning = true;
            }

            try {
                final List<OfferEntity> offers = exchangeDao.findExpiredOffers(new Date());
                final List<Long> ids = new ArrayList<>(offers.size());
                for (final OfferEntity offer : offers) {
                    ids.add(offer.getId());
                }

                final List<OfferGroupEntity> offerGroups = exchangeDao.findInfoForSharedOffers(ids);
                final Map<Long, List<OfferGroupEntity>> sharingInfo = prepareOfferGroupMap(ids, offerGroups);

                for (final OfferEntity offer : offers) {
                    offer.setStatus(OfferState.EXPIRED);
                    exchangeDao.persist(offer);

                    for (final OfferGroupEntity offerGroup : sharingInfo.get(offer.getId())) {
                        if (offerGroup.getHasApplication()) {
                            offerGroup.setStatus(OfferState.CLOSED);
                            exchangeDao.persist(offerGroup);
                        } else {
                            iwsEntityManager.remove(offerGroup);
                        }
                    }
                }
            } catch (IllegalArgumentException|IWSException e) {
                log.error("Error in processing expired offers", e);
            }

            synchronized (LOCK) {
                processExpiredOfferIsRunning = false;
            }
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