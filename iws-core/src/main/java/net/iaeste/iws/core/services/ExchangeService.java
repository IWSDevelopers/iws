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
import net.iaeste.iws.api.dtos.Employer;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.DeleteOfferRequest;
import net.iaeste.iws.api.requests.FetchEmployersRequest;
import net.iaeste.iws.api.requests.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.OfferTemplateRequest;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.requests.PublishGroupRequest;
import net.iaeste.iws.api.responses.FetchEmployersResponse;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.api.responses.OfferTemplateResponse;
import net.iaeste.iws.api.responses.PublishGroupResponse;
import net.iaeste.iws.core.transformers.OfferTransformer;
import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.entities.OfferEntity;

import javax.persistence.PersistenceException;
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
     * if {@code id = null} then new {@code Offer} should be created.
     * Otherwise, the {@code Offer} for the specified {@code id} is updated.
     * <p/>
     * If {@code id = null} and {@code refNo} exists, then the request is invalid.
     * If (@code id != null} and {@code refNo} in the database and request don't match, then the request is invalid.
     *
     * @param token   TODO
     * @param request
     * @return OfferResponse contains list of Fallible Offers for which processing failed.
     */
    public OfferResponse processOffer(final AuthenticationToken token, final ProcessOfferRequest request) {
        OfferResponse response = null;
        final List<String> processingErrors = new ArrayList<>();

        final Offer offer = request.getOffer();
        // If DTO objects has an id, we're trying to update the database.
        // If DTO has an id and there is no such entity, an exception is thrown.
        final OfferEntity unmanagedEntity = OfferTransformer.transform(offer);

        if (offer.getId() == null) {
//        if (request.isCreateRequest()) {
            final OfferEntity offerByRefNo = dao.findOffer(offer.getRefNo());
            if (offerByRefNo == null) {
                try {
                    dao.persist(unmanagedEntity);
                } catch (PersistenceException e) {
                    // some constraints have been violated, this shouldn't ever happened!
                    processingErrors.add(e.toString());
                }
            } else {
                // user can't create new Offer for existing refNo
                processingErrors.add(String.format("create error: Offer with refNo=%s already exists.", offer.getRefNo()));
            }
        } else {
            final OfferEntity existingOffer = dao.findOffer(offer.getId());
            if (existingOffer == null) {
                // trying to update nonexistent Entity
                processingErrors.add(String.format("update error: there is no offer for given id=%d", offer.getId()));
            } else if (!existingOffer.getRefNo().equals(unmanagedEntity.getRefNo())) {
                // trying to update and refNos don't match
                processingErrors.add("update error: cannot change refNo");
            } else {
                existingOffer.merge(unmanagedEntity);
                try {
                    dao.persist(existingOffer);
                } catch (PersistenceException e) {
                    // some constraints have been violated, this shouldn't ever happened!
                    processingErrors.add(e.toString());
                }
            }
        }

        if (processingErrors.isEmpty()) {
            response = new OfferResponse();
        } else {
            response = new OfferResponse(offer, processingErrors);
        }

        return response;
    }

    public void deleteOffer(final AuthenticationToken token, final DeleteOfferRequest request) {
        final OfferEntity foundOffer = dao.findOffer(request.getOfferId());

        if (foundOffer != null) {
            dao.delete(foundOffer.getId());
        } else {
            throw new IWSException(IWSErrors.ENTITY_IDENTIFICATION_ERROR, "Cannot delete Offer with Id " + request.getOfferId());
        }
    }

    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        final FetchOffersResponse response;

        switch (request.getFetchType()) {
            case ALL:
                response = new FetchOffersResponse(findAllOffers());
                break;
            case OWNED:
                // TODO: select only owned offers
                response = new FetchOffersResponse(findAllOffers());
                break;
            case SHARED:
                // TODO: select only shared offers
                response = new FetchOffersResponse(findAllOffers());
                break;
            default:
                response = new FetchOffersResponse(IWSErrors.NOT_IMPLEMENTED, "TBD");
        }

        return response;
    }

    public FetchEmployersResponse fetchEmployers(final AuthenticationToken token, final FetchEmployersRequest request) {
        final FetchEmployersResponse response;

        switch (request.getFetchType()) {
            case OWNED:
                // TODO: select only owned offers
                response = new FetchEmployersResponse(convertEntityList(Employer.class, dao.findOffersByLikeEmployerName(request.getName())));
                break;
            default:
                response = new FetchEmployersResponse(IWSErrors.NOT_IMPLEMENTED, "TBD");
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

    private List<Offer> findOffersByEmployerName(final String employerName) {
        final List<OfferEntity> found = dao.findOffersByEmployerName(employerName);

        return convertEntityList(found);
    }

    private List<Offer> findOffersByLikeEmployerName(final String employerName) {
        final List<OfferEntity> found = dao.findOffersByLikeEmployerName(employerName);

        return convertEntityList(found);
    }

    private List<Offer> convertEntityList(final List<OfferEntity> found) {
        final List<Offer> result = new ArrayList<>(found.size());

        for (final OfferEntity entity : found) {
            result.add(OfferTransformer.transform(entity));
        }

        return result;
    }

    private List<Employer> convertEntityList(Class<Employer> t, final List<OfferEntity> found) {
        final List<Employer> result = new ArrayList<>(found.size());

        for (final OfferEntity entity : found) {
            result.add(OfferTransformer.transform(Employer.class, entity));
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
