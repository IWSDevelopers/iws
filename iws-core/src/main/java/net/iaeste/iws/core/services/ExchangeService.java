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
import net.iaeste.iws.api.dtos.EmployerInformation;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.DeleteOfferRequest;
import net.iaeste.iws.api.requests.FetchEmployerInformationRequest;
import net.iaeste.iws.api.requests.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.OfferTemplateRequest;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.requests.PublishGroupRequest;
import net.iaeste.iws.api.responses.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.api.responses.OfferTemplateResponse;
import net.iaeste.iws.api.responses.PublishGroupResponse;
import net.iaeste.iws.core.notofications.NotificationCenter;
import net.iaeste.iws.core.transformers.OfferTransformer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.OfferDao;
import net.iaeste.iws.persistence.entities.OfferEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class ExchangeService extends CommonService {

    private final OfferDao dao;
    private final NotificationCenter nc;

    public ExchangeService(final OfferDao dao, NotificationCenter nc) {
        this.dao = dao;
        this.nc = nc;
    }

    /**
     * Will attempt to persist a new Offer, meaning that if the Offer already
     * exists (check against the given refno) and the user is allowed to work
     * with it, then it us updated. If no such Offer exists, then a new Offer
     * is created and assigned to the given Group.<br />
     * The method returns an OfferResponse object with error information. No
     * information about the Offer is returned.
     *
     * @param authentication User & Group information
     * @param request        Offer Request information, i.e. OfferDTO
     * @return OfferResponse with error information
     */
    public OfferResponse processOffer(final Authentication authentication, final ProcessOfferRequest request) {
        final OfferEntity existingEntity = dao.findOffer(request.getOffer().getRefNo());
        final OfferEntity newEntity = OfferTransformer.transform(request.getOffer());

        if (existingEntity == null) {
            // Persist the Object with history
            dao.persist(authentication, newEntity);
            nc.processNewOffer(request.getOffer());
        } else {
            // Check if the user is allowed to work with the Object, if not -
            // then a Permission Exception is thrown
            permissionCheck(authentication, authentication.getGroup());

            // Persist the changes, the method takes the existing and merges the
            // new values into it, and finally it also writes an entry in the
            // history table
            dao.persist(authentication, existingEntity, newEntity);
            nc.processUpdatedOffer(request.getOffer());
        }

        return new OfferResponse();
    }

    // TODO Should perform the delete operation based on the refno
    public void deleteOffer(final Authentication authentication, final DeleteOfferRequest request) {
        final OfferEntity foundOffer = dao.findOffer(request.getOfferRefNo());

        if (foundOffer != null) {
            dao.delete(foundOffer.getId());
        } else {
            throw new IWSException(IWSErrors.ENTITY_IDENTIFICATION_ERROR, "Cannot delete Offer with refNo " + request.getOfferRefNo());
        }
    }

    public FetchOffersResponse fetchOffers(final Authentication authentication, final FetchOffersRequest request) {
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

    public FetchEmployerInformationResponse fetchEmployers(final Authentication authentication, final FetchEmployerInformationRequest request) {
        final FetchEmployerInformationResponse response;

        //TODO: select only owned offers
        response = new FetchEmployerInformationResponse(convertEntityList(EmployerInformation.class, dao.findOffersByLikeEmployerName(request.getName())));

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

    private List<EmployerInformation> convertEntityList(Class<EmployerInformation> t, final List<OfferEntity> found) {
        final List<EmployerInformation> result = new ArrayList<>(found.size());

        for (final OfferEntity entity : found) {
            result.add(OfferTransformer.transform(EmployerInformation.class, entity));
        }

        return result;
    }

    public void processOfferTemplates(final Authentication authentication, final OfferTemplateRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public OfferTemplateResponse fetchOfferTemplates(final Authentication authentication, final FetchOfferTemplatesRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public void processPublishGroups(final Authentication authentication, final PublishGroupRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public PublishGroupResponse fetchPublishGroups(final Authentication authentication, final FetchPublishGroupsRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }
}
