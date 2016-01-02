/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-leargas) - net.iaeste.iws.leargas.Processor
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.leargas;

import net.iaeste.iws.leargas.clients.AccessWSClient;
import net.iaeste.iws.leargas.clients.ExchangeWSClient;
import net.iaeste.iws.leargas.clients.Mapper;
import net.iaeste.iws.leargas.exceptions.LeargasException;
import net.iaeste.iws.leargas.persistence.OfferDao;
import net.iaeste.iws.leargas.persistence.OfferEntity;
import net.iaeste.iws.ws.AuthenticationResponse;
import net.iaeste.iws.ws.AuthenticationToken;
import net.iaeste.iws.ws.FallibleResponse;
import net.iaeste.iws.ws.FetchOffersRequest;
import net.iaeste.iws.ws.FetchOffersResponse;
import net.iaeste.iws.ws.FetchType;
import net.iaeste.iws.ws.Offer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  Kim Jensen <kim@dawn.dk>
 * @version Leargas 1.0
 * @since   Java 1.8
 */
public final class Processor {

    private static final Logger LOG = LoggerFactory.getLogger(Processor.class);

    private final Settings settings;
    private final AccessWSClient accessWS;
    private final ExchangeWSClient exchangeWS;
    private final OfferDao dao;
    private final Mapper mapper;

    public Processor(final Settings settings, final Connection connection) {
        this.settings = settings;
        mapper = new Mapper(settings);
        dao = new OfferDao(connection);

        try {
            this.accessWS = new AccessWSClient(settings.readIWSUrl());
            exchangeWS = new ExchangeWSClient(settings.readIWSUrl());
        } catch (MalformedURLException e) {
            throw new LeargasException(e.getMessage(), e);
        }
    }

    public State start() throws LeargasException {
        // First, create a new Authentication Token, all requests and
        // responses to the IWS is wrapped with Request Objects for
        // request validation & Response Objects with Error information
        final AuthenticationResponse authResponse = login();

        if ("Ok".equals(authResponse.getMessage())) {
            // We successfully logged in, so we can save the token for further usage
            final AuthenticationToken token = authResponse.getToken();

            // The rest is being done in a try-catch block, so we can ensure
            // that the Token is deprecated in the finally part, otherwise the
            // account will be blocked for a while!
            try {
                return processFetchingOffers(token);
            } finally {
                // Don't forget to log out in the end, otherwise the account
                // is blocked for more requests for a limited period
                final FallibleResponse deprecate = accessWS.deprecateSession(token);
                LOG.info("Deprecating IWS Token gave: " + deprecate.getMessage());
            }
        } else {
            throw new LeargasException("Unable to create IWS Session Token: " + authResponse.getMessage());
        }
    }

    private AuthenticationResponse login() {
        final String username = settings.readIWSUsername();
        final String password = settings.readIWSPassword();

        return accessWS.generateSession(username, password);
    }

    private State processFetchingOffers(final AuthenticationToken token) {
        // Fetching Offers can retrieve either Shared or Domestic Offers
        final FetchOffersResponse offerResponse = fetchSharedOffers(token);

        if ("Ok".equals(offerResponse.getMessage())) {
            LOG.info("We have " + offerResponse.getOffers().size() + " Offers shared.");

            // Now, we're done with the IWS Communication for now, we still need
            // to log out later, but at this point we have a list of Offers, which
            // we can then process. Each Offer consists of quite a few pieces of
            // information, which reflects the Official requirements.
            return processOffers(offerResponse.getOffers());
        } else {
            throw new LeargasException("Error retrieving Offers: " + offerResponse.getMessage());
        }
    }

    private FetchOffersResponse fetchSharedOffers(final AuthenticationToken token) {
        final FetchOffersRequest offerRequest = new FetchOffersRequest();
        offerRequest.setFetchType(FetchType.SHARED);

        return exchangeWS.fetchOffers(token, offerRequest);
    }

    private State processOffers(final List<Offer> offers) {
        final Map<String, OfferEntity> existing = findExistingOffers();
        final State state = new State();

        // First part, we'll iterate over all the Offers we have received from
        // the IWS. Each Offer is mapped, compared to the list of Existing, so
        // we know if we should either update or insert them.
        for (final Offer offer : offers) {
            final OfferEntity entity = mapper.map(offer);
            final String refno = entity.getRefNo();

            if (existing.containsKey(refno)) {
                // The Offer already exist, so we'll just update it, in case
                // there is some changes.
                dao.updateOffer(entity);
                // As we've updated the Offer, let's set the State accordingly
                state.incUpdatedOffers();
                // And as we're done with it, we can remove this one from the
                // list of existing, so we don't have to process these later
                existing.remove(refno);
            } else {
                // New Offer, so we'll create a new one
                dao.insertOffer(entity);
                // And update the State accordingly
                state.incCreatedOffers();
            }
        }

        // During the previous stage, we've dealt with inserting & updating
        // Offers, but Offers no longer available will not necessarily be
        // present, so we also need to deal with those. We do this by simply
        // looking at what is left in our Map of Offers, and mark each of
        // them as gone.
        for (final String refno : existing.keySet()) {
            dao.deleteOffer(refno);
            state.incDeletedOffers();
        }

        return state;
    }

    /**
     * To properly delete or map no longer accessible Offers as not available,
     * will need a map of the existing Offers, so we can remove the existing and
     * mark the remaining.
     *
     * @return Map of current Offers with the Reference Number as Key
     */
    private Map<String, OfferEntity> findExistingOffers() {
        final List<OfferEntity> offers = dao.findAllOffers();
        final Map<String, OfferEntity> map = new HashMap<>();

        // Convert the list of found Offers to a Map, where the key is the
        // Reference Number
        for (final OfferEntity offer : offers) {
            map.put(offer.getRefNo(), offer);
        }

        return map;
    }
}
