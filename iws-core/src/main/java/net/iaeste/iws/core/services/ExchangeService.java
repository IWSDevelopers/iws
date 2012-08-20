/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.OfferService
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

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.exceptions.EntityIdentificationException;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.OfferRequest;
import net.iaeste.iws.api.requests.OfferTemplateRequest;
import net.iaeste.iws.api.requests.PublishGroupRequest;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.api.responses.OfferTemplateResponse;
import net.iaeste.iws.api.responses.PublishGroupResponse;
import net.iaeste.iws.core.transformers.OfferTransformer;
import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.entities.OfferEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class ExchangeService {

    private final OfferDao dao;

    public ExchangeService(final OfferDao dao) {
        this.dao = dao;
    }

    /**
     * @param token
     * @param request
     * @return OfferResponse contains list of Fallible Offers for which processing failed.
     */
    public OfferResponse processOffers(final AuthenticationToken token, final OfferRequest request) {
        final List<Offer> offers = new ArrayList<>(request.getUpdateOffers().size() + request.getDeleteOfferIds().size());

        // If DTO objects has an id, we're trying to update the database.
        // If DTO has an id and there is no such entity, an exception is thrown.
        for (final Offer offer : request.getUpdateOffers()) {
            final OfferEntity unmanagedEntity = OfferTransformer.transform(offer);

            if (offer.getId() == null) { // new offer
                dao.persist(unmanagedEntity);
            } else { // update offer
                final OfferEntity existingOffer = dao.findOffer(offer.getId());
                if (existingOffer != null && existingOffer.getRefNo().equals(unmanagedEntity.getRefNo())) {
                    existingOffer.merge(unmanagedEntity);
                    dao.persist(existingOffer);
                } else {
                    offers.add(new Offer(new EntityIdentificationException(offer.getId())));
                }
            }
        }

        for (final Long offer : request.getDeleteOfferIds()) {
            if (!dao.delete(offer)) { // collect all inexistent offers
                offers.add(new Offer(new EntityIdentificationException(offer)));
            }
        }

        return new OfferResponse(offers);
    }

    public OfferResponse fetchOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        final OfferResponse response;

        switch (request.getFetchType()) {
            case ALL:
                response = new OfferResponse(findAllOffers());
                break;
            case NONE:
                response = new OfferResponse(IWSErrors.NOT_IMPLEMENTED, "TBD");
                break;
            case BY_ID:
                response = new OfferResponse(findOffers(request.getOffers()));
                break;
            case LIMIT:
                response = new OfferResponse(IWSErrors.NOT_IMPLEMENTED, "TBD");
                break;
            case PROTOTYPE:
                response = new OfferResponse(IWSErrors.NOT_IMPLEMENTED, "TBD");
                break;
            default:
                response = new OfferResponse(IWSErrors.NOT_IMPLEMENTED, "TBD");
        }

        return response;
    }

    private List<Offer> findAllOffers() {
        final List<OfferEntity> found = dao.findAll();

        return convertEntityList(found);
    }

    private List<Offer> findOffers(final List<Long> ids) {
        final List<OfferEntity> found = dao.findOffers(ids);

        return convertEntityList(found);
    }

    private List<Offer> convertEntityList(final List<OfferEntity> found) {
        final List<Offer> result = new ArrayList<>(found.size());

        for (final OfferEntity entity : found) {
            result.add(OfferTransformer.transform(entity));
        }

        return result;
    }

    public void processOfferTemplates(final AuthenticationToken token, final OfferTemplateRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public OfferTemplateResponse fetchOfferTemplates(final AuthenticationToken token, final FetchOfferTemplatesRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public void processPublishGroups(final AuthenticationToken token, final PublishGroupRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public PublishGroupResponse fetchPublishGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }
}
