/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.dtos.GroupList;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.dtos.exchange.OfferStatistics;
import net.iaeste.iws.api.dtos.exchange.PublishingGroup;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishingGroupResponse;
import net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse;
import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.exceptions.PermissionException;
import net.iaeste.iws.core.transformers.AdministrationTransformer;
import net.iaeste.iws.core.transformers.ExchangeTransformer;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.ViewsDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.exchange.PublishingGroupEntity;
import net.iaeste.iws.persistence.views.DomesticOfferStatisticsView;
import net.iaeste.iws.persistence.views.EmployerView;
import net.iaeste.iws.persistence.views.ForeignOfferStatisticsView;
import net.iaeste.iws.persistence.views.OfferSharedToGroupView;
import net.iaeste.iws.persistence.views.OfferView;
import net.iaeste.iws.persistence.views.SharedOfferView;

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
 * @since   IWS 1.0
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
        final List<DomesticOfferStatisticsView> records = viewsDao.findDomesticOfferStatistics(nationalGroup, year);

        final Map<OfferState, Integer> statistics = new EnumMap<>(OfferState.class);
        for (final DomesticOfferStatisticsView stats : records) {
            statistics.put(stats.getId().getStatus(), stats.getRecords());
        }

        return new OfferStatistics(statistics, year);
    }

    private OfferStatistics readForeignStatistics(final GroupEntity nationalGroup, final Integer year) {
        final List<ForeignOfferStatisticsView> records = viewsDao.findForeignOfferStatistics(nationalGroup, year);

        final Map<OfferState, Integer> statistics = new EnumMap<>(OfferState.class);
        for (final ForeignOfferStatisticsView stats : records) {
            statistics.put(stats.getId().getStatus(), stats.getRecords());
        }

        return new OfferStatistics(statistics, year);
    }

    /**
     * We need to find the National Group for a User, but necessarily a Group
     * which the User is a member of (see bug #902, where a Local Committee
     * member cannot fetch Statistics). So, if the given Groun in the
     * Authentication, isn't a National Group, we will assume that it is a Local
     * Committee. In which case, we can find it via the Parent.
     *
     * @param authentication User Authentication Object
     * @return National Group Entity
     */
    private GroupEntity findNationalGroup(final Authentication authentication) {
        final GroupEntity group;

        if (authentication.getGroup().getGroupType().getGrouptype() == GroupType.NATIONAL) {
            group = authentication.getGroup();
        } else {
            group = accessDao.findNationalGroupByLocalGroup(authentication);
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
        final Paginatable page = request.getPagingInformation();
        final int year = request.getExchangeYear();

        final FetchOffersResponse response;
        switch (request.getFetchType()) {
            case DOMESTIC:
                response = new FetchOffersResponse(findDomesticOffers(authentication, year, page));
                break;
            case SHARED:
                response = new FetchOffersResponse(findSharedOffers(authentication, year, page));
                break;
            default:
                throw new PermissionException("The search type is not permitted.");
        }

        return response;
    }

    private List<Offer> findDomesticOffers(final Authentication authentication, final int exchangeYear, final Paginatable page) {
        final List<OfferView> found = viewsDao.findDomesticOffers(authentication, exchangeYear, page);
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

    private List<Offer> findSharedOffers(final Authentication authentication, final int exchangeYear, final Paginatable page) {
        final List<SharedOfferView> found = viewsDao.findSharedOffers(authentication, exchangeYear, page);
        final List<Offer> result = new ArrayList<>(found.size());

        for (final SharedOfferView view : found) {
            result.add(transform(view));
        }

        return result;
    }

    public FetchPublishingGroupResponse fetchPublishGroups(final Authentication authentication, final FetchPublishGroupsRequest request) {
        final List<PublishingGroupEntity> sharingLists = dao.getSharingListForOwner(authentication.getGroup().getId());
        final List<PublishingGroup> publishingGroups = new ArrayList<>(sharingLists.size());
        for (final PublishingGroupEntity sharingList : sharingLists) {
            publishingGroups.add(ExchangeTransformer.transform(sharingList));
        }

        return new FetchPublishingGroupResponse(publishingGroups);
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
        final Map<String, GroupList> result = prepareResultingMap(externalOfferIds);
        for (final OfferSharedToGroupView view : shared) {
            final String offerId = view.getOfferExternalId();
            final Group group = transform(view);
            result.get(offerId).add(group);
        }

        // Done, return result
        return new FetchPublishedGroupsResponse(result);
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
    private static Map<String, GroupList> prepareResultingMap(final List<String> externalOfferIds) {
        final Map<String, GroupList> result = new HashMap<>(externalOfferIds.size());

        for (final String externalOfferId : externalOfferIds) {
            final ArrayList<Group> groups = new ArrayList<>(80);
            final GroupList list = new GroupList(groups);
            result.put(externalOfferId, list);
        }

        return result;
    }
}
