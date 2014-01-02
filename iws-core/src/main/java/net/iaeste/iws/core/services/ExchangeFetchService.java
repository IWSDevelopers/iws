/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import static net.iaeste.iws.core.transformers.ViewTransformer.transform;

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
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOfferTemplateResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishGroupResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.core.exceptions.PermissionException;
import net.iaeste.iws.core.transformers.AdministrationTransformer;
import net.iaeste.iws.core.transformers.CommonTransformer;
import net.iaeste.iws.core.transformers.ExchangeTransformer;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.ViewsDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.views.EmployerView;
import net.iaeste.iws.persistence.views.OfferView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This Service Class contains the read-only parts of the Exchange methods.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class ExchangeFetchService extends CommonService<ExchangeDao> {

    private final ViewsDao viewsDao;
    private final AccessDao accessDao;

    public ExchangeFetchService(final ExchangeDao dao, final ViewsDao viewsDao, final AccessDao accessDao) {
        super(dao);
        this.viewsDao = viewsDao;
        this.accessDao = accessDao;
    }

    public OfferStatisticsResponse fetchOfferStatistics(final Authentication authentication, final OfferStatisticsRequest request) {
        return new OfferStatisticsResponse();
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
            final Employer employer = transform(view);
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
            final Employer employer = transform(view);
            result.add(employer);
        }

        return result;
    }

    public FetchOffersResponse fetchOffers(final Authentication authentication, final FetchOffersRequest request) {
        final FetchOffersResponse response;
        final int year = request.getExchangeYear();

        switch (request.getFetchType()) {
            case ALL:
                response = new FetchOffersResponse(findAllOffers(authentication, year));
                break;
            case SHARED:
                response = new FetchOffersResponse(findSharedOffers(authentication, year));
                break;
            default:
                throw new PermissionException("The search type is not permitted.");
        }

        return response;
    }

    private List<Offer> findAllOffers(final Authentication authentication, final int exchangeYear) {
        // Must be extended with Pagination
        final List<OfferView> found = viewsDao.findAllOffers(authentication, exchangeYear);

        return convertViewList(found);
    }

    private static List<Offer> convertViewList(final List<OfferView> found) {
        final List<Offer> result = new ArrayList<>(found.size());

        for (final OfferView view : found) {
            result.add(transform(view));
        }

        return result;
    }

    private List<Offer> findSharedOffers(final Authentication authentication, final Integer exchangeYear) {
        final java.util.Date now = new Date().toDate();
        final List<OfferEntity> offerEntities = dao.findSharedOffers(authentication, exchangeYear);
        final List<Offer> offers = new ArrayList<>(offerEntities.size());

        for (final OfferEntity offerEntity : offerEntities) {
            if (!offerEntity.getNominationDeadline().before(now)) {
                OfferGroupEntity og = dao.findInfoForSharedOfferAndGroup(offerEntity.getId(), authentication.getGroup().getId());
                //overwrite status for each country - it's independent for each country in sharing process

                final Offer offer = ExchangeTransformer.transform(offerEntity);
                offer.setStatus(og.getStatus());

                final UserEntity nationalSecretary = accessDao.findOwnerByGroup(CommonTransformer.transform(offer.getEmployer().getGroup()));
                offer.setNsFirstname(nationalSecretary.getFirstname());
                offer.setNsLastname(nationalSecretary.getLastname());

                offers.add(offer);
            }
        }

        return offers;
    }

    private static List<OfferGroup> convertOfferGroupEntityList(final List<OfferGroupEntity> found) {
        final List<OfferGroup> result = new ArrayList<>(found.size());

        for (final OfferGroupEntity entity : found) {
            result.add(ExchangeTransformer.transform(entity));
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
                throw new VerificationException("The offer with id '" + externalId + "' is not owned by the group '" + authentication.getGroup().getGroupName() + "'.");
            }
        }
    }

    public FetchPublishedGroupsResponse fetchPublishedOfferInfo(final Authentication authentication, final FetchPublishedGroupsRequest request) {
        //TODO distinguish somehow a request for info about offers shared 'to me' and 'by me', now it's 'by me'
        final java.util.Date now = new Date().toDate();
        final FetchPublishedGroupsResponse response;

        verifyOffersOwnership(authentication, new HashSet<>(request.getOfferIds()));

        final List<String> externalIds = request.getOfferIds();
        final Map<String, List<OfferGroup>> result = new HashMap<>(externalIds.size()); //@Kim: is it better to use the size as parameter for Map constructor?

        for (final String externalId : externalIds) {
            result.put(externalId, convertOfferGroupEntityList(dao.findInfoForUnexpiredSharedOffer(externalId, now)));
        }

        response = new FetchPublishedGroupsResponse(result);

        return response;
    }
}
