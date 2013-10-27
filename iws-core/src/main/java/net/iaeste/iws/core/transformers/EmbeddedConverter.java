/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.EmbeddedTransformer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.transformers;

import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.persistence.views.EmbeddedAddress;
import net.iaeste.iws.persistence.views.EmbeddedCountry;
import net.iaeste.iws.persistence.views.EmbeddedEmployer;
import net.iaeste.iws.persistence.views.EmbeddedGroup;
import net.iaeste.iws.persistence.views.EmbeddedOffer;

/**
 * Transforms all Embedded View, which is used by the read-only queries. The
 * embedded classes are written, so they only contain the primary information,
 * i.e. no Id's (except our externalId) or foreign keys.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class EmbeddedConverter {

    /**
     * Private Constructor, this is a utility class.
     */
    private EmbeddedConverter() {
    }

    public static Address convert(final EmbeddedAddress address) {
        final Address result = new Address();

        result.setStreet1(address.getStreet1());
        result.setStreet2(address.getStreet2());
        result.setZip(address.getZip());
        result.setCity(address.getCity());
        result.setState(address.getState());

        return result;
    }

    public static Country convert(final EmbeddedCountry country) {
        final Country result = new Country();

        result.setCountryCode(country.getCountryCode());
        result.setCountryName(country.getCountryName());
        result.setNationality(country.getNationality());
        result.setPhonecode(country.getPhonecode());
        result.setCurrency(country.getCurrency());
        result.setMembership(country.getMembership());
        result.setMemberSince(country.getMemberSince());

        return result;
    }

    public static Group convert(final EmbeddedGroup view) {
        final Group group = new Group();

        group.setGroupId(view.getExternalId());
        group.setGroupName(view.getGroupName());
        group.setGroupType(view.getGroupType());

        return group;
    }

    public static Employer convert(final EmbeddedEmployer view) {
        final Employer employer = new Employer();

        // First, read out the the common Employer fields
        employer.setEmployerId(view.getExternalId());
        employer.setName(view.getName());
        employer.setDepartment(view.getDepartment());
        employer.setBusiness(view.getBusiness());
        employer.setEmployeesCount(view.getNumberOfEmployees());
        employer.setWebsite(view.getWebsite());
        employer.setWorkingPlace(view.getWorkingPlace());
        employer.setCanteen(view.getCanteen());
        employer.setNearestAirport(view.getNearestAirport());
        employer.setNearestPublicTransport(view.getNearestPublicTransport());
        employer.setWeeklyHours(view.getWeeklyHours());
        employer.setDailyHours(view.getDailyHours());

        return employer;
    }

    public static Offer convert(final EmbeddedOffer view) {
        final Offer result = new Offer();

        result.setOfferId(view.getExternalId());
        result.setRefNo(view.getRefNo());
        result.setWorkDescription(view.getWorkDescription());
        result.setTypeOfWork(view.getTypeOfWork());
        result.setStudyLevels(CollectionTransformer.explodeEnumSet(StudyLevel.class, view.getStudyLevels()));
        result.setFieldOfStudies(CollectionTransformer.explodeEnumSet(FieldOfStudy.class, view.getFieldOfStudies()));
        result.setSpecializations(CollectionTransformer.explodeStringSet(view.getSpecializations()));
        result.setPreviousTrainingRequired(view.getPrevTrainingRequired());
        result.setOtherRequirements(view.getOtherRequirements());
        result.setLanguage1(view.getLanguage1());
        result.setLanguage1Level(view.getLanguage1Level());
        result.setLanguage1Operator(view.getLanguage1Operator());
        result.setLanguage2(view.getLanguage2());
        result.setLanguage2Level(view.getLanguage2Level());
        result.setLanguage2Operator(view.getLanguage2Operator());
        result.setLanguage3(view.getLanguage3());
        result.setLanguage3Level(view.getLanguage3Level());
        result.setMinimumWeeks(view.getMinimumWeeks());
        result.setMaximumWeeks(view.getMaximumWeeks());
        result.setPeriod1(CommonTransformer.transform(view.getFromDate(), view.getToDate()));
        result.setPeriod2(CommonTransformer.transform(view.getFromDate2(), view.getToDate2()));
        result.setUnavailable(CommonTransformer.transform(view.getUnavailableFrom(), view.getUnavailableTo()));
        result.setPayment(view.getPayment());
        result.setPaymentFrequency(view.getPaymentFrequency());
        result.setCurrency(view.getCurrency());
        result.setDeduction(view.getDeduction());
        result.setLivingCost(view.getLivingCost());
        result.setLivingCostFrequency(view.getLivingCostFrequency());
        result.setLodgingBy(view.getLodgingBy());
        result.setLodgingCost(view.getLodgingCost());
        result.setLodgingCostFrequency(view.getLodgingCostFrequency());
        result.setNominationDeadline(CommonTransformer.convert(view.getNominationDeadline()));
        result.setNumberOfHardCopies(view.getNumberOfHardCopies());
        result.setAdditionalInformation(view.getAdditionalInformation());
        result.setStatus(view.getStatus());
        result.setModified(new DateTime(view.getModified()));
        result.setCreated(new DateTime(view.getCreated()));

        return result;
    }
}
