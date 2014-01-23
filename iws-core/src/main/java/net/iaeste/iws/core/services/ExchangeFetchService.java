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
import net.iaeste.iws.api.dtos.exchange.OfferStatistics;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.exceptions.NotImplementedException;
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
import net.iaeste.iws.common.configuration.Settings;
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
import net.iaeste.iws.persistence.views.DomesticOfferStatisticsView;
import net.iaeste.iws.persistence.views.EmployerView;
import net.iaeste.iws.persistence.views.ForeignOfferStatisticsView;
import net.iaeste.iws.persistence.views.OfferSharedToGroupView;
import net.iaeste.iws.persistence.views.OfferView;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public ExchangeFetchService(final Settings settings, final ExchangeDao dao, final ViewsDao viewsDao, final AccessDao accessDao) {
        super(settings, dao);
        this.viewsDao = viewsDao;
        this.accessDao = accessDao;
    }

    public OfferStatisticsResponse fetchOfferStatistics(final Authentication authentication, final OfferStatisticsRequest request) {
        final Integer year = request.getExchangeYear();
        final GroupEntity nationalGroup = findNationalGroup(authentication);

        final OfferStatisticsResponse response = new OfferStatisticsResponse();
        response.setDommesticStatistics(readDomesticStatistics(nationalGroup, year));
        response.setForeignStatistics(readForeignStatistics(nationalGroup, year));

        return response;
    }

    private OfferStatistics readDomesticStatistics(final GroupEntity nationalGroup, final Integer year) {
        final List<DomesticOfferStatisticsView> records = dao.findDomesticOfferStatistics(nationalGroup, year);

        final Map<OfferState, Integer> statistics = new EnumMap<>(OfferState.class);
        for (final DomesticOfferStatisticsView stats : records) {
            statistics.put(stats.getId().getStatus(), stats.getRecords());
        }

        return new OfferStatistics(statistics, year);
    }

    private OfferStatistics readForeignStatistics(final GroupEntity nationalGroup, final Integer year) {
        final List<ForeignOfferStatisticsView> records = dao.findForeignOfferStatistics(nationalGroup, year);

        final Map<OfferState, Integer> statistics = new EnumMap<>(OfferState.class);
        for (final ForeignOfferStatisticsView stats : records) {
            statistics.put(stats.getId().getStatus(), stats.getRecords());
        }

        return new OfferStatistics(statistics, year);
    }

    private GroupEntity findNationalGroup(final Authentication authentication) {
        final GroupEntity group;

        if (authentication.getGroup().getGroupType().getGrouptype() == GroupType.NATIONAL) {
            group = authentication.getGroup();
        } else {
            group = accessDao.findNationalGroup(authentication.getUser());
        }

        return group;
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
            case DOMESTIC:
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

        return convertViewList(authentication, found);
    }

    private static List<Offer> convertViewList(final Authentication authentication, final List<OfferView> found) {
        final List<Offer> result = new ArrayList<>(found.size());

        for (final OfferView view : found) {
            final Offer offer = transform(view);
            // do not expose private comment to foreign offers
            if (!view.getGroupId().equals(authentication.getGroup().getId())) {
                offer.setPrivateComment(null);
            }
            result.add(offer);
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

                // do not expose private comment to foreign offers
                if (!offerEntity.getEmployer().getGroup().getId().equals(authentication.getGroup().getId())) {
                    offer.setPrivateComment(null);
                }

                offers.add(offer);
            }
        }

        return offers;
    }

    //private static List<OfferGroup> convertOfferGroupEntityList(final List<OfferGroupEntity> found) {
    //    final List<OfferGroup> result = new ArrayList<>(found.size());
    //
    //    for (final OfferGroupEntity entity : found) {
    //        result.add(ExchangeTransformer.transform(entity));
    //    }
    //
    //    return result;
    //}

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

    /**
     * Retrieves a map with a list of Groups an Offer was shared to. The lookup
     * is made using a View, where the keys are the Parent Group Id (the request
     * is allowed for both National & Local Committees, where the only shared
     * information is the parent Id), and with the provided Exchange Year. The
     * result is then processed and a Map with each OfferId and a list of
     * matching Groups is then returned in the Response Object.<br />
     *   Note, that the Database is entrusted with only reading out the relevant
     * information, i.e. only information about Offers which the user is allowed
     * to work with.
     *
     * @param authentication User Authentication Object
     * @param request        Request Object
     * @return Response Object
     */
    public FetchPublishedGroupsResponse fetchPublishedOfferInfo(final Authentication authentication, final FetchPublishedGroupsRequest request) {
        // Extract required information and fetch the ... LONG ... list of results
        final Long parentId = authentication.getGroup().getParentId();
        final Integer exchangeYear = request.getExchangeYear();
        final List<String> externalOfferIds = request.getOfferIds();
        final List<OfferSharedToGroupView> shared = viewsDao.findSharedToGroup(parentId, exchangeYear, externalOfferIds);

        // Prepare resulting map, and iterate over the list and fill in the details
        final Map<String, List<Group>> result = prepareResultingMap(externalOfferIds);
        for (final OfferSharedToGroupView view : shared) {
            final String offerId = view.getOfferExternalId();
            final Group group = transform(view);
            result.get(offerId).add(group);
        }

        // Done, return result
        return new FetchPublishedGroupsResponse(result);

        // Note, according to Trac ticket #635, the old method was way slow,
        // looking at the code, it is understandably, since we're running n + 1
        // queries, where n is the number of OfferIds.
        //verifyOffersOwnership(authentication, new HashSet<>(request.getOfferIds()));
        //
        //final List<String> externalIds = request.getOfferIds();
        //final Map<String, List<Group>> result = new HashMap<>(externalIds.size());
        //
        //for (final String externalId : externalIds) {
        //    result.put(externalId, convertOfferGroupEntityList(dao.findInfoForUnexpiredSharedOffer(externalId, now)));
        //}
        //
        //response = new FetchPublishedGroupsResponse(result);
        //
        //return response;
    }

    /**
     * Prerating the Resulting map for the #fetchPublishedOfferInfo method. The
     * result is a Map with a List of of Groups for each key. The default size
     * of the lists is set to 80, since this is the assumed number of Groups
     * that an Offer is shared too.
     *
     * @param externalOfferIds List of ExternalOfferIds to find results for
     * @return Result Map with empty data structure
     */
    private static Map<String, List<Group>> prepareResultingMap(final List<String> externalOfferIds) {
        final Map<String, List<Group>> result = new HashMap<>(externalOfferIds.size());

        for (final String externalOfferId : externalOfferIds) {
            final List<Group> groups = new ArrayList<>(80);
            result.put(externalOfferId, groups);
        }

        return result;
    }
}
