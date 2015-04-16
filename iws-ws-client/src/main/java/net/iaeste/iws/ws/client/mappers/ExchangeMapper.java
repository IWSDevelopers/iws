/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.ws.mappers.ExchangeMapper
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ws.client.mappers;

import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.dtos.exchange.OfferStatistics;
import net.iaeste.iws.api.dtos.exchange.PublishingGroup;
import net.iaeste.iws.api.enums.FetchType;
import net.iaeste.iws.api.enums.exchange.EmployerFetchType;
import net.iaeste.iws.api.enums.exchange.ExchangeType;
import net.iaeste.iws.api.enums.exchange.LanguageOperator;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.enums.exchange.OfferType;
import net.iaeste.iws.api.enums.exchange.PaymentFrequency;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import net.iaeste.iws.api.requests.exchange.DeletePublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.HideForeignOffersRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVDownloadRequest;
import net.iaeste.iws.api.requests.exchange.OfferStatisticsRequest;
import net.iaeste.iws.api.requests.exchange.ProcessEmployerRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.ProcessPublishingGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.RejectOfferRequest;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishingGroupResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVDownloadResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.OfferStatisticsResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class ExchangeMapper extends CommonMapper {

    public static net.iaeste.iws.ws.OfferStatisticsRequest map(final OfferStatisticsRequest api) {
        net.iaeste.iws.ws.OfferStatisticsRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.OfferStatisticsRequest();

            ws.setExchangeYear(api.getExchangeYear());
        }

        return ws;
    }

    public static OfferStatisticsResponse map(final net.iaeste.iws.ws.OfferStatisticsResponse ws) {
        OfferStatisticsResponse api = null;

        if (ws != null) {
            api = new OfferStatisticsResponse(map(ws.getError()), ws.getMessage());

            api.setDommesticStatistics(map(ws.getDommesticStatistics()));
            api.setForeignStatistics(map(ws.getForeignStatistics()));
        }

        return api;
    }

    private static OfferStatistics map(final net.iaeste.iws.ws.OfferStatistics ws) {
        OfferStatistics api = null;

        if (ws != null) {
            api = new OfferStatistics();

            api.setStatistics(mapStatisticsMap(ws.getStatistics()));
            api.setExchangeYear(ws.getExchangeYear());
        }

        return api;
    }

    private static HashMap<OfferState, Integer> mapStatisticsMap(final net.iaeste.iws.ws.OfferStatistics.Statistics ws) {
        HashMap<OfferState, Integer> api = null;

        if (ws != null) {
            api = new HashMap<>();
            for (final net.iaeste.iws.ws.OfferStatistics.Statistics.Entry entry : ws.getEntry()) {
                api.put(map(entry.getKey()), entry.getValue());
            }
        }

        return api;
    }

    public static net.iaeste.iws.ws.ProcessEmployerRequest map(final ProcessEmployerRequest api) {
        net.iaeste.iws.ws.ProcessEmployerRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.ProcessEmployerRequest();
            ws.setEmployer(map(api.getEmployer()));
        }

        return ws;
    }

    public static EmployerResponse map(final net.iaeste.iws.ws.EmployerResponse ws) {
        EmployerResponse api = null;

        if (ws != null) {
            api = new EmployerResponse(map(ws.getError()), ws.getMessage());

            api.setEmployer(map(ws.getEmployer()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.FetchEmployerRequest map(final FetchEmployerRequest api) {
        net.iaeste.iws.ws.FetchEmployerRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchEmployerRequest();

            ws.setType(map(api.getFetchType()));
            ws.setField(api.getFetchField());
        }

        return ws;
    }

    public static FetchEmployerResponse map(final net.iaeste.iws.ws.FetchEmployerResponse ws) {
        FetchEmployerResponse api = null;

        if (ws != null) {
            api = new FetchEmployerResponse(map(ws.getError()), ws.getMessage());
        }

        return api;
    }

    public static net.iaeste.iws.ws.FetchOffersRequest map(final FetchOffersRequest api) {
        net.iaeste.iws.ws.FetchOffersRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchOffersRequest();

            ws.setFetchType(map(api.getFetchType()));
            ws.setExchangeYear(api.getExchangeYear());
        }

        return ws;
    }

    public static FetchOffersResponse map(final net.iaeste.iws.ws.FetchOffersResponse ws) {
        FetchOffersResponse api = null;

        if (ws != null) {
            api = new FetchOffersResponse(map(ws.getError()), ws.getMessage());
        }

        return api;
    }

    public static net.iaeste.iws.ws.ProcessOfferRequest map(final ProcessOfferRequest api) {
        net.iaeste.iws.ws.ProcessOfferRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.ProcessOfferRequest();
        }

        return ws;
    }

    public static net.iaeste.iws.ws.DeleteOfferRequest map(final DeleteOfferRequest api) {
        net.iaeste.iws.ws.DeleteOfferRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.DeleteOfferRequest();

            ws.setOfferId(api.getOfferId());
        }

        return ws;
    }

    public static OfferResponse map(final net.iaeste.iws.ws.OfferResponse ws) {
        OfferResponse api = null;

        if (ws != null) {
            api = new OfferResponse(map(ws.getError()), ws.getMessage());
        }

        return api;
    }

    public static PublishOfferResponse map(final net.iaeste.iws.ws.PublishOfferResponse ws) {
        return new PublishOfferResponse(map(ws.getError()), ws.getMessage());
    }

    public static net.iaeste.iws.ws.ProcessPublishingGroupRequest map(final ProcessPublishingGroupRequest api) {
        net.iaeste.iws.ws.ProcessPublishingGroupRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.ProcessPublishingGroupRequest();

            ws.setDeletePublishingGroup(api.getDeletePublishingGroup());
            ws.setPublishingGroup(map(api.getPublishingGroup()));
        }

        return ws;
    }

    public static FetchGroupsForSharingResponse map(final net.iaeste.iws.ws.FetchGroupsForSharingResponse ws) {
        FetchGroupsForSharingResponse api = null;

        if (ws != null) {
            api = new FetchGroupsForSharingResponse(map(ws.getError()), ws.getMessage());

            api.getGroups().addAll(mapWSGroupCollection(ws.getGroups()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.OfferCSVDownloadRequest map(final OfferCSVDownloadRequest api) {
        net.iaeste.iws.ws.OfferCSVDownloadRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.OfferCSVDownloadRequest();

            ws.setFetchType(map(api.getFetchType()));
            ws.getOfferIds().addAll(mapStringCollection(api.getOfferIds()));
            ws.setExchangeYear(api.getExchangeYear());
        }

        return ws;
    }

    public static OfferCSVDownloadResponse map(final net.iaeste.iws.ws.OfferCSVDownloadResponse ws) {
        OfferCSVDownloadResponse api = null;

        if (ws != null) {
            api = new OfferCSVDownloadResponse(map(ws.getError()), ws.getMessage());

            api.setData(ws.getData());
        }

        return api;
    }

    public static net.iaeste.iws.ws.FetchPublishGroupsRequest map(final FetchPublishGroupsRequest api) {
        net.iaeste.iws.ws.FetchPublishGroupsRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchPublishGroupsRequest();
            ws.setPage(map(api.getPagingInformation()));
        }

        return ws;
    }

    public static FetchPublishingGroupResponse map(final net.iaeste.iws.ws.FetchPublishingGroupResponse ws) {
        FetchPublishingGroupResponse api = null;

        if (ws != null) {
            api = new FetchPublishingGroupResponse(map(ws.getError()), ws.getMessage());

            final List<PublishingGroup> list = new ArrayList<>(ws.getPublishingGroups().size());
            for (final net.iaeste.iws.ws.PublishingGroup group : ws.getPublishingGroups()) {
                list.add(map(group));
            }
            api.setPublishingGroups(list);
        }

        return api;
    }

    private static net.iaeste.iws.ws.PublishingGroup map(final PublishingGroup api) {
        net.iaeste.iws.ws.PublishingGroup ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.PublishingGroup();

            ws.setPublishingGroupId(api.getPublishingGroupId());
            ws.setName(api.getName());
            ws.getGroups().addAll(mapAPIGroupCollection(api.getGroups()));
        }

        return ws;
    }

    private static PublishingGroup map(final net.iaeste.iws.ws.PublishingGroup ws) {
        PublishingGroup api = null;

        if (ws != null) {
            api = new PublishingGroup();

            api.setPublishingGroupId(api.getPublishingGroupId());
            api.setName(api.getName());
            api.getGroups().addAll(mapWSGroupCollection(ws.getGroups()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.DeletePublishingGroupRequest map(final DeletePublishingGroupRequest api) {
        net.iaeste.iws.ws.DeletePublishingGroupRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.DeletePublishingGroupRequest();

            ws.setPublishingGroupId(api.getPublishingGroupId());
        }

        return ws;
    }

    public static net.iaeste.iws.ws.PublishOfferRequest map(final PublishOfferRequest api) {
        net.iaeste.iws.ws.PublishOfferRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.PublishOfferRequest();

            ws.getGroupIds().addAll(mapStringCollection(api.getGroupIds()));
            ws.getOfferIds().addAll(mapStringCollection(api.getOfferIds()));
            ws.setNominationDeadline(map(api.getNominationDeadline()));
        }

        return ws;
    }

    public static net.iaeste.iws.ws.HideForeignOffersRequest map(final HideForeignOffersRequest api) {
        net.iaeste.iws.ws.HideForeignOffersRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.HideForeignOffersRequest();

            ws.getOffers().addAll(mapStringCollection(api.getOffers()));
        }

        return ws;
    }

    public static net.iaeste.iws.ws.RejectOfferRequest map(final RejectOfferRequest api) {
        net.iaeste.iws.ws.RejectOfferRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.RejectOfferRequest();

            ws.setOfferId(api.getOfferId());
        }

        return ws;
    }

    // =========================================================================
    // Internal mapping of Exchange relevant Objects
    // =========================================================================

    private static Employer map(final net.iaeste.iws.ws.Employer ws) {
        Employer api = null;

        if (ws != null) {
            api = new Employer();

            api.setEmployerId(ws.getEmployerId());
            api.setGroup(map(ws.getGroup()));
            api.setName(ws.getName());
            api.setDepartment(ws.getDepartment());
            api.setBusiness(ws.getBusiness());
            api.setAddress(map(ws.getAddress()));
            api.setEmployeesCount(ws.getEmployeesCount());
            api.setWebsite(ws.getWebsite());
            api.setWorkingPlace(ws.getWorkingPlace());
            api.setCanteen(ws.isCanteen());
            api.setNearestAirport(ws.getNearestAirport());
            api.setNearestPublicTransport(ws.getNearestPublicTransport());
        }

        return api;
    }

    private static net.iaeste.iws.ws.Employer map(final Employer api) {
        net.iaeste.iws.ws.Employer ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Employer();

            ws.setEmployerId(api.getEmployerId());
            ws.setGroup(map(api.getGroup()));
            ws.setName(api.getName());
            ws.setDepartment(api.getDepartment());
            ws.setBusiness(api.getBusiness());
            ws.setAddress(map(api.getAddress()));
            ws.setEmployeesCount(api.getEmployeesCount());
            ws.setWebsite(api.getWebsite());
            ws.setWorkingPlace(api.getWorkingPlace());
            ws.setCanteen(api.getCanteen());
            ws.setNearestAirport(api.getNearestAirport());
            ws.setNearestPublicTransport(api.getNearestPublicTransport());
        }

        return ws;
    }

    public static net.iaeste.iws.ws.Offer map(final Offer api) {
        net.iaeste.iws.ws.Offer ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Offer();

            ws.setOfferId(api.getOfferId());
            ws.setRefNo(api.getRefNo());
            ws.setOfferType(map(api.getOfferType()));
            ws.setExchangeType(map(api.getExchangeType()));
            ws.setOldRefNo(api.getOldRefNo());
            ws.setEmployer(map(api.getEmployer()));
            ws.setWorkDescription(api.getWorkDescription());
            ws.setTypeOfWork(map(api.getTypeOfWork()));
            ws.setWeeklyHours(api.getWeeklyHours());
            ws.setDailyHours(api.getDailyHours());
            ws.setWeeklyWorkDays(api.getWeeklyWorkDays());
            ws.getStudyLevels().addAll(mapStudyLevelCollection(api.getStudyLevels()));
            ws.getFieldOfStudies().addAll(mapAPIFieldOfStudyCollection(api.getFieldOfStudies()));
            ws.getSpecializations().addAll(mapStringCollectionToList(api.getSpecializations()));
            ws.setPreviousTrainingRequired(api.getPreviousTrainingRequired());
            ws.setOtherRequirements(api.getOtherRequirements());
            ws.setMinimumWeeks(api.getMinimumWeeks());
            ws.setMaximumWeeks(api.getMaximumWeeks());
            ws.setPeriod1(map(api.getPeriod1()));
            ws.setPeriod2(map(api.getPeriod2()));
            ws.setUnavailable(map(api.getUnavailable()));
            ws.setLanguage1(map(api.getLanguage1()));
            ws.setLanguage1Level(map(api.getLanguage1Level()));
            ws.setLanguage1Operator(map(api.getLanguage1Operator()));
            ws.setLanguage2(map(api.getLanguage2()));
            ws.setLanguage2Level(map(api.getLanguage2Level()));
            ws.setLanguage2Operator(map(api.getLanguage2Operator()));
            ws.setLanguage3(map(api.getLanguage3()));
            ws.setLanguage3Level(map(api.getLanguage3Level()));
            ws.setPayment(api.getPayment());
            ws.setPaymentFrequency(map(api.getPaymentFrequency()));
            ws.setCurrency(map(api.getCurrency()));
            ws.setDeduction(api.getDeduction());
            ws.setLivingCost(api.getLivingCost());
            ws.setLivingCostFrequency(map(api.getLivingCostFrequency()));
            ws.setLodgingBy(api.getLodgingBy());
            ws.setLodgingCost(api.getLodgingCost());
            ws.setPaymentFrequency(map(api.getPaymentFrequency()));
            ws.setNominationDeadline(map(api.getNominationDeadline()));
            ws.setNumberOfHardCopies(api.getNumberOfHardCopies());
            ws.setAdditionalInformation(api.getAdditionalInformation());
            ws.setPrivateComment(api.getPrivateComment());
            ws.setStatus(map(api.getStatus()));
            ws.setModified(map(api.getModified()));
            ws.setCreated(map(api.getCreated()));
            ws.setNsFirstname(api.getNsFirstname());
            ws.setNsLastname(api.getNsLastname());
            ws.setShared(map(api.getShared()));
            ws.setHidden(api.isHidden());
        }

        return ws;
    }

    public static Offer map(final net.iaeste.iws.ws.Offer ws) {
        Offer api = null;

        if (ws != null) {
            api = new Offer();

            api.setOfferId(ws.getOfferId());
            api.setRefNo(ws.getRefNo());
            api.setOfferType(map(ws.getOfferType()));
            api.setExchangeType(map(ws.getExchangeType()));
            api.setOldRefNo(ws.getOldRefNo());
            api.setEmployer(map(ws.getEmployer()));
            api.setWorkDescription(ws.getWorkDescription());
            api.setTypeOfWork(map(ws.getTypeOfWork()));
            api.setWeeklyHours(ws.getWeeklyHours());
            api.setDailyHours(ws.getDailyHours());
            api.setWeeklyWorkDays(ws.getWeeklyWorkDays());
            api.setStudyLevels(mapStudyLevelCollectionToSet(ws.getStudyLevels()));
            api.setFieldOfStudies(mapFieldOfStudyCollection(ws.getFieldOfStudies()));
            api.setSpecializations(mapStringCollectionToSet(ws.getSpecializations()));
            api.setPreviousTrainingRequired(ws.isPreviousTrainingRequired());
            api.setOtherRequirements(ws.getOtherRequirements());
            api.setMinimumWeeks(ws.getMinimumWeeks());
            api.setMaximumWeeks(ws.getMaximumWeeks());
            api.setPeriod1(map(ws.getPeriod1()));
            api.setPeriod2(map(ws.getPeriod2()));
            api.setUnavailable(map(ws.getUnavailable()));
            api.setLanguage1(map(ws.getLanguage1()));
            api.setLanguage1Level(map(ws.getLanguage1Level()));
            api.setLanguage1Operator(map(ws.getLanguage1Operator()));
            api.setLanguage2(map(ws.getLanguage2()));
            api.setLanguage2Level(map(ws.getLanguage2Level()));
            api.setLanguage2Operator(map(ws.getLanguage2Operator()));
            api.setLanguage3(map(ws.getLanguage3()));
            api.setLanguage3Level(map(ws.getLanguage3Level()));
            api.setPayment(ws.getPayment());
            api.setPaymentFrequency(map(ws.getPaymentFrequency()));
            api.setCurrency(map(ws.getCurrency()));
            api.setDeduction(ws.getDeduction());
            api.setLivingCost(ws.getLivingCost());
            api.setLivingCostFrequency(map(ws.getLivingCostFrequency()));
            api.setLodgingBy(ws.getLodgingBy());
            api.setLodgingCost(ws.getLodgingCost());
            api.setPaymentFrequency(map(ws.getPaymentFrequency()));
            api.setNominationDeadline(map(ws.getNominationDeadline()));
            api.setNumberOfHardCopies(ws.getNumberOfHardCopies());
            api.setAdditionalInformation(ws.getAdditionalInformation());
            api.setPrivateComment(ws.getPrivateComment());
            api.setStatus(map(ws.getStatus()));
            api.setModified(map(ws.getModified()));
            api.setCreated(map(ws.getCreated()));
            api.setNsFirstname(ws.getNsFirstname());
            api.setNsLastname(ws.getNsLastname());
            api.setShared(map(ws.getShared()));
            api.setHidden(ws.isHidden());
        }

        return api;
    }

    // =========================================================================
    // Convertion of Exchange Enums
    // =========================================================================

    private static net.iaeste.iws.ws.EmployerFetchType map(final EmployerFetchType api) {
        return api != null ? net.iaeste.iws.ws.EmployerFetchType.valueOf(api.name()) : null;
    }

    private static net.iaeste.iws.ws.FetchType map(final FetchType api) {
        return api != null ? net.iaeste.iws.ws.FetchType.valueOf(api.name()) : null;
    }

    private static LanguageOperator map(final net.iaeste.iws.ws.LanguageOperator ws) {
        return ws != null ? LanguageOperator.valueOf(ws.value()) : null;
    }

    private static net.iaeste.iws.ws.LanguageOperator map(final LanguageOperator api) {
        return api != null ? net.iaeste.iws.ws.LanguageOperator.valueOf(api.name()) : null;
    }

    private static OfferType map(final net.iaeste.iws.ws.OfferType ws) {
        return ws != null ? OfferType.valueOf(ws.value()) : null;
    }

    private static net.iaeste.iws.ws.OfferType map(final OfferType api) {
        return api != null ? net.iaeste.iws.ws.OfferType.valueOf(api.name()) : null;
    }

    private static ExchangeType map(final net.iaeste.iws.ws.ExchangeType ws) {
        return ws != null ? ExchangeType.valueOf(ws.value()) : null;
    }

    private static net.iaeste.iws.ws.ExchangeType map(final ExchangeType api) {
        return api != null ? net.iaeste.iws.ws.ExchangeType.valueOf(api.name()) : null;
    }

    private static TypeOfWork map(final net.iaeste.iws.ws.TypeOfWork ws) {
        return ws != null ? TypeOfWork.valueOf(ws.value()) : null;
    }

    private static net.iaeste.iws.ws.TypeOfWork map(final TypeOfWork api) {
        return api != null ? net.iaeste.iws.ws.TypeOfWork.valueOf(api.name()) : null;
    }

    private static PaymentFrequency map(final net.iaeste.iws.ws.PaymentFrequency ws) {
        return ws != null ? PaymentFrequency.valueOf(ws.value()) : null;
    }

    private static net.iaeste.iws.ws.PaymentFrequency map(final PaymentFrequency api) {
        return api != null ? net.iaeste.iws.ws.PaymentFrequency.valueOf(api.name()) : null;
    }
}
