/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.convert.Offer
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

import net.iaeste.iws.api.dtos.EmployerInformation;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.api.enums.TypeOfWork;
import net.iaeste.iws.persistence.entities.OfferEntity;

/**
 * Tranformer for the Exchange module, handles transformation of the DTO Objects
 * to and from the Entity data structure.
 *
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class OfferTransformer {
    private OfferTransformer() {
    }

    @SuppressWarnings("OverlyLongMethod")
    public static OfferEntity transform(final Offer offer) {
        OfferEntity result = null;

        if (offer != null) {
            result = new OfferEntity();

            result.setRefNo(offer.getRefNo());
            result.setNominationDeadline(offer.getNominationDeadline());
            result.setEmployerName(offer.getEmployerName());
            result.setEmployerAddress(offer.getEmployerAddress());
            result.setEmployerAddress2(offer.getEmployerAddress2());
            result.setEmployerBusiness(offer.getEmployerBusiness());
            result.setEmployerEmployeesCount(offer.getEmployerEmployeesCount());
            result.setEmployerWebsite(offer.getEmployerWebsite());
            result.setFieldOfStudies(CollectionTransformer.concatEnumCollection(offer.getFieldOfStudies()));
            result.setSpecializations(CollectionTransformer.join(offer.getSpecializations()));
            result.setPrevTrainingRequired(offer.getPrevTrainingRequired());
            result.setOtherRequirements(offer.getOtherRequirements());
            result.setLanguage1(offer.getLanguage1());
            result.setLanguage1Level(offer.getLanguage1Level());
            result.setLanguage1Operator(offer.getLanguage1Operator());
            result.setLanguage2(offer.getLanguage2());
            result.setLanguage2Level(offer.getLanguage2Level());
            result.setLanguage2Operator(offer.getLanguage2Operator());
            result.setLanguage3(offer.getLanguage3());
            result.setLanguage3Level(offer.getLanguage3Level());
            result.setWorkDescription(offer.getWorkDescription());
            result.setTypeOfWork(offer.getTypeOfWork() != null ? offer.getTypeOfWork().toString() : null);
            result.setMinimumWeeks(offer.getMinimumWeeks());
            result.setMaximumWeeks(offer.getMaximumWeeks());
            result.setFromDate(offer.getFromDate());
            result.setToDate(offer.getToDate());
            result.setFromDate2(offer.getFromDate2());
            result.setToDate2(offer.getToDate2());
            result.setUnavailableFrom(offer.getUnavailableFrom());
            result.setUnavailableTo(offer.getUnavailableTo());
            result.setWorkingPlace(offer.getWorkingPlace());
            result.setNearestAirport(offer.getNearestAirport());
            result.setNearestPubTransport(offer.getNearestPubTransport());
            result.setWeeklyHours(offer.getWeeklyHours());
            result.setDailyHours(offer.getDailyHours());
            result.setPayment(offer.getPayment());
            result.setCurrency(offer.getCurrency());
            result.setPaymentFrequency(offer.getPaymentFrequency());
            result.setDeduction(offer.getDeduction());
            result.setLodgingBy(offer.getLodgingBy());
            result.setLodgingCost(offer.getLodgingCost());
            result.setLodgingCostFrequency(offer.getLodgingCostFrequency());
            result.setLivingCost(offer.getLivingCost());
            result.setLivingCostFrequency(offer.getLivingCostFrequency());
            result.setCanteen(offer.getCanteen());
            result.setStudyLevels(CollectionTransformer.concatEnumCollection(offer.getStudyLevels()));
        }

        return result;
    }

    @SuppressWarnings("OverlyLongMethod")
    public static Offer transform(final OfferEntity offer) {
        Offer result = null;

        if (offer != null) {
            result = new Offer();

            result.setRefNo(offer.getRefNo());
            result.setNominationDeadline(offer.getNominationDeadline());
            result.setEmployerName(offer.getEmployerName());
            result.setEmployerAddress(offer.getEmployerAddress());
            result.setEmployerAddress2(offer.getEmployerAddress2());
            result.setEmployerBusiness(offer.getEmployerBusiness());
            result.setEmployerEmployeesCount(offer.getEmployerEmployeesCount());
            result.setEmployerWebsite(offer.getEmployerWebsite());
            result.setFieldOfStudies(CollectionTransformer.explodeEnumSet(FieldOfStudy.class, offer.getFieldOfStudies()));
            result.setSpecializations(CollectionTransformer.explodeStringSet(offer.getSpecializations()));
            result.setPrevTrainingRequired(offer.getPrevTrainingRequired());
            result.setOtherRequirements(offer.getOtherRequirements());
            result.setLanguage1(offer.getLanguage1());
            result.setLanguage1Level(offer.getLanguage1Level());
            result.setLanguage1Operator(offer.getLanguage1Operator());
            result.setLanguage2(offer.getLanguage2());
            result.setLanguage2Level(offer.getLanguage2Level());
            result.setLanguage2Operator(offer.getLanguage2Operator());
            result.setLanguage3(offer.getLanguage3());
            result.setLanguage3Level(offer.getLanguage3Level());
            result.setWorkDescription(offer.getWorkDescription());
            result.setTypeOfWork(TypeOfWork.toValue(offer.getTypeOfWork()));
            result.setMinimumWeeks(offer.getMinimumWeeks());
            result.setMaximumWeeks(offer.getMaximumWeeks());
            result.setFromDate(offer.getFromDate());
            result.setToDate(offer.getToDate());
            result.setFromDate2(offer.getFromDate2());
            result.setToDate2(offer.getToDate2());
            result.setUnavailableFrom(offer.getUnavailableFrom());
            result.setUnavailableTo(offer.getUnavailableTo());
            result.setWorkingPlace(offer.getWorkingPlace());
            result.setNearestAirport(offer.getNearestAirport());
            result.setNearestPubTransport(offer.getNearestPubTransport());
            result.setWeeklyHours(offer.getWeeklyHours());
            result.setDailyHours(offer.getDailyHours());
            result.setPayment(offer.getPayment());
            result.setCurrency(offer.getCurrency());
            result.setPaymentFrequency(offer.getPaymentFrequency());
            result.setDeduction(offer.getDeduction());
            result.setLodgingBy(offer.getLodgingBy());
            result.setLodgingCost(offer.getLodgingCost());
            result.setLodgingCostFrequency(offer.getLodgingCostFrequency());
            result.setLivingCost(offer.getLivingCost());
            result.setLivingCostFrequency(offer.getLivingCostFrequency());
            result.setCanteen(offer.getCanteen());
            result.setStudyLevels(CollectionTransformer.explodeEnumSet(StudyLevel.class, offer.getStudyLevels()));
        }

        return result;
    }

    /**
     * Transform OfferEntity employer into the EmployerInformation DTO
     *
     * @param t     The Class object to overloading transform method
     * @param offer Source OfferEntity which is to be transformed
     * @return EmployerInformation Object
     */
    public static EmployerInformation transform(final Class<EmployerInformation> t, final OfferEntity offer) {
        EmployerInformation result = null;

        if (offer != null) {
            result = new EmployerInformation();

            result.setName(offer.getEmployerName());
            result.setAddress(offer.getEmployerAddress());
            result.setAddress2(offer.getEmployerAddress2());
            result.setBusiness(offer.getEmployerBusiness());
            result.setEmployeesCount(offer.getEmployerEmployeesCount());
            result.setWebsite(offer.getEmployerWebsite());
            result.setWorkingPlace(offer.getWorkingPlace());
            result.setNearestAirport(offer.getNearestAirport());
            result.setNearestPubTransport(offer.getNearestPubTransport());
            result.setWeeklyHours(offer.getWeeklyHours());
            result.setDailyHours(offer.getDailyHours());
        }

        return result;
    }
}
