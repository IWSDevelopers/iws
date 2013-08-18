/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.ExchangeFetchService
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

import static net.iaeste.iws.core.transformers.OfferTransformer.transform;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.dtos.exchange.OfferGroup;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOfferTemplateResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishGroupResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.core.transformers.AdministrationTransformer;
import net.iaeste.iws.core.transformers.ViewTransformer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.ViewsDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.views.EmployerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This Service Class contains the read-only parts of the Exchange methods.
 *
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class ExchangeFetchService extends CommonService<ExchangeDao> {

    private final ViewsDao viewsDao;

    public ExchangeFetchService(final ExchangeDao dao, final ViewsDao viewsDao) {
        super(dao);
        this.viewsDao = viewsDao;
    }

    public FetchEmployerResponse fetchEmployers(final Authentication authentication, final FetchEmployerRequest request) {
        final Long groupId = authentication.getGroup().getId();
        final List<Employer> list;

        switch (request.getFetchType()) {
            case ID:
                list = findEmployerById(groupId, request.getFetchField());
                break;
            case NAME:
                list = findEmployerByName(groupId, request.getPagingInformation(), request.getFetchField());
                break;
            default:
                list = findAllEmployers(groupId, request.getPagingInformation());
        }

        return new FetchEmployerResponse(list);
    }

    private List<Employer> findEmployerById(final Long groupId, final String externalId) {
        final EmployerView view = viewsDao.findEmployer(groupId, externalId);

        final List<Employer> result;
        if (view != null) {
            final Employer employer = ViewTransformer.transform(view);
            result = new ArrayList<>(1);
            result.add(employer);
        } else {
            result = new ArrayList<>(0);
        }

        return result;
    }

    private List<Employer> findEmployerByName(final Long groupId, final Paginatable page, final String partialName) {
        final List<EmployerView> found = viewsDao.findEmployers(groupId, page, partialName);

        return convertEmployerViews(found);
    }

    private List<Employer> findAllEmployers(final Long groupId, final Paginatable page) {
        final List<EmployerView> found = viewsDao.findEmployers(groupId, page);

        return convertEmployerViews(found);
    }

    private static List<Employer> convertEmployerViews(final List<EmployerView> found) {
        final List<Employer> result = new ArrayList<>(found.size());

        for (final EmployerView view : found) {
            final Employer employer = ViewTransformer.transform(view);
            result.add(employer);
        }

        return result;
    }

    public FetchOffersResponse fetchOffers(final Authentication authentication, final FetchOffersRequest request) {
        final FetchOffersResponse response;

        switch (request.getFetchType()) {
            case ALL:
                response = new FetchOffersResponse(findAllOffers(authentication));
                break;
            case SHARED:
                response = new FetchOffersResponse(findSharedOffers(authentication));
                break;
            default:
                response = new FetchOffersResponse(IWSErrors.NOT_PERMITTED, "The search type is not permitted");
        }

        return response;
    }

    private List<Offer> findAllOffers(final Authentication authentication) {
        // Must be extended with Pagination
        final List<OfferEntity> found = dao.findAllOffers(authentication);

        return convertEntityList(found);
    }

    private List<Offer> findSharedOffers(final Authentication authentication) {
        // Must be extended with Pagination
        final List<OfferEntity> found = new ArrayList<>(10);
        final java.util.Date now = new Date().toDate();

        for (final OfferEntity offer : dao.findSharedOffers(authentication)) {
            if (!offer.getNominationDeadline().before(now)) {
                found.add(offer);
            }
        }

        return convertEntityList(found);
    }

    private static List<Offer> convertEntityList(final List<OfferEntity> found) {
        final List<Offer> result = new ArrayList<>(found.size());

        for (final OfferEntity entity : found) {
            result.add(transform(entity));
        }

        return result;
    }

    private static List<OfferGroup> convertOfferGroupEntityList(final List<OfferGroupEntity> found) {
        final List<OfferGroup> result = new ArrayList<>(found.size());

        for (final OfferGroupEntity entity : found) {
            result.add(transform(entity));
        }

        return result;
    }

    public FetchOfferTemplateResponse fetchOfferTemplates(final Authentication authentication, final FetchOfferTemplatesRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public FetchPublishGroupResponse fetchPublishGroups(final Authentication authentication, final FetchPublishGroupsRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public FetchGroupsForSharingResponse fetchGroupsForSharing(final Authentication authentication) {
        final List<GroupEntity> groups = dao.findGroupsForSharing(authentication.getGroup());
        final List<Group> groupList = AdministrationTransformer.transform(groups);

        return new FetchGroupsForSharingResponse(groupList);
    }

    private void verifyOffersOwnership(final Authentication authentication, final Set<String> offerExternalIds) {
        final List<OfferEntity> offers = dao.findOffersByExternalId(authentication, offerExternalIds);
        final Set<String> fetchedOffersExtId = new HashSet<>(offers.size());
        for (final OfferEntity offer : offers) {
            fetchedOffersExtId.add(offer.getExternalId());
        }

        for (final String externalId : offerExternalIds) {
            if (!fetchedOffersExtId.contains(externalId)) {
                throw new VerificationException("The offer with externalId '" + externalId + "' is not owned by the group '" + authentication.getGroup().getGroupName() + "'.");
            }
        }
    }

    public FetchPublishedGroupsResponse fetchPublishedOfferInfo(final Authentication authentication, final FetchPublishedGroupsRequest request) {
        //TODO distinguish somehow a request for info about offers shared 'to me' and 'by me', now it's 'by me'
        final FetchPublishedGroupsResponse response;

        verifyOffersOwnership(authentication, new HashSet<>(request.getOfferIds()));

        final List<String> externalIds = request.getOfferIds();
        final Map<String, List<OfferGroup>> result = new HashMap<>(externalIds.size()); //@Kim: is it better to use the size as parameter for Map constructor?
        for (final String externalId : externalIds) {
            result.put(externalId, convertOfferGroupEntityList(dao.findInfoForSharedOffer(externalId)));
        }

        response = new FetchPublishedGroupsResponse(result);

        return response;
    }
}
