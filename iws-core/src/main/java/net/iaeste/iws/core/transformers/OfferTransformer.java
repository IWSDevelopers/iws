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

import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.persistence.entities.OfferEntity;

/**
 * Tranformer for the Exchange module, handles transformation of the DTO Objects
 * to and from the Entity data structure.
 *
 * @author  Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class OfferTransformer {

    public static OfferEntity transform(final Offer offer) {
        OfferEntity result = null;

        if (offer != null) {
            result = new OfferEntity();

            result.setId(offer.getId());
            result.setRefNo(offer.getRefNo());
            result.setNominationDeadline(offer.getNominationDeadline());
            result.setEmployerName(offer.getEmployerName());
            result.setEmployerAddress(offer.getEmployerAddress());
            result.setEmployerAddress2(offer.getEmployerAddress2());
            result.setEmployerBusiness(offer.getEmployerBusiness());
            result.setEmployerEmployeesCount(offer.getEmployerEmployeesCount());
            result.setEmployerWebsite(offer.getEmployerWebsite());
            result.setFieldOfStudies(offer.getFieldOfStudies());
            result.setSpecializations(offer.getSpecializations());
            result.setPrevTrainingRequired(offer.getPrevTrainingRequired());
            result.setOtherRequirements(offer.getOtherRequirements());
            result.setGender(offer.getGender());
            result.setLanguage1(offer.getLanguage1());
            result.setLanguage1Level(offer.getLanguage1Level());
            result.setLanguage1Operator(offer.getLanguage1Operator());
            result.setLanguage2(offer.getLanguage2());
            result.setLanguage2Level(offer.getLanguage2Level());
            result.setLanguage2Operator(offer.getLanguage2Operator());
            result.setLanguage3(offer.getLanguage3());
            result.setLanguage3Level(offer.getLanguage3Level());
            result.setWorkDescription(offer.getWorkDescription());
            result.setTypeOfWork(offer.getTypeOfWork());
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
        }

        return result;
    }

    public static Offer transform(final OfferEntity offer) {
        Offer result = null;

        if (offer != null) {
            result = new Offer();

            result.setId(offer.getId());
            result.setRefNo(offer.getRefNo());
            result.setNominationDeadline(offer.getNominationDeadline());
            result.setEmployerName(offer.getEmployerName());
            result.setEmployerAddress(offer.getEmployerAddress());
            result.setEmployerAddress2(offer.getEmployerAddress2());
            result.setEmployerBusiness(offer.getEmployerBusiness());
            result.setEmployerEmployeesCount(offer.getEmployerEmployeesCount());
            result.setEmployerWebsite(offer.getEmployerWebsite());
            result.setFieldOfStudies(offer.getFieldOfStudies());
            result.setSpecializations(offer.getSpecializations());
            result.setPrevTrainingRequired(offer.getPrevTrainingRequired());
            result.setOtherRequirements(offer.getOtherRequirements());
            result.setGender(offer.getGender());
            result.setLanguage1(offer.getLanguage1());
            result.setLanguage1Level(offer.getLanguage1Level());
            result.setLanguage1Operator(offer.getLanguage1Operator());
            result.setLanguage2(offer.getLanguage2());
            result.setLanguage2Level(offer.getLanguage2Level());
            result.setLanguage2Operator(offer.getLanguage2Operator());
            result.setLanguage3(offer.getLanguage3());
            result.setLanguage3Level(offer.getLanguage3Level());
            result.setWorkDescription(offer.getWorkDescription());
            result.setTypeOfWork(offer.getTypeOfWork());
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
        }

        return result;
    }

//    /**
//     * @return only Entities for which conversion succeded
//     * @throws net.iaeste.iws.api.exceptions.EntityIdentificationException
//     *
//     * @todo Michal: optimalization, get rid of loop and use NamedQuery for getting all offers.
//     */
//    public List<OfferEntity> toEntity(final List<Offer> offers) {
//        final List<OfferEntity> offerEntities = new ArrayList<OfferEntity>(offers.size());
//        for (final Offer offer : offers) {
//            try {
//                offerEntities.add(toEntity(offer));
//            } catch (EntityIdentificationException ignore) {
//            }
//        }
//        return offerEntities;
//    }

//    /**
//     * Converts DTO into Entity. Checks if the Entity exists in the database and returns managed objects if it does.
//     *
//     * @param offer DTO object
//     * @return for existing offers it returns managed Entity object
//     * @throws net.iaeste.iws.api.exceptions.EntityIdentificationException
//     *          if offer for with id does not exist in the database
//     */
//    public OfferEntity toEntity(final Offer offer) throws EntityIdentificationException {
//        OfferEntity offerEntity = null;
//        final Long id = offer.getId();
//        if (id == null) {
//            offerEntity = new OfferEntity();
//            // ToDo Kim; @Michal, drop this crap - the class should only make convertions, nothing else!
////        } else {
////            offerEntity = offerDao.findOffer(id);
//        }
//        if (offerEntity == null) {
//            throw new EntityIdentificationException("No such offer in the database. Cannot update the entity.");
//        }
//        copyFieldsToEntity(offer, offerEntity);
//        return offerEntity;
//    }

//    public List<Offer> toDTO(final List<OfferEntity> offerEntities) {
//        final List<Offer> offers = new ArrayList<Offer>(offerEntities.size());
//        for (final OfferEntity offerEntity : offerEntities) {
//            offers.add(toDTO(offerEntity));
//        }
//        return offers;
//    }

//    public Offer toDTO(final OfferEntity offerEntity) {
//        final Offer offer = new Offer();
//        copyFieldsToDTO(offerEntity, offer);
//        return offer;
//    }

//    private void copyFieldsToDTO(final OfferEntity offerEntity, final Offer offer) {
//        // ToDo Kim; @Michael, please avoid updating the objects given as parameters, it can be confusing and lead to other problems. Rather the code should create a new entity based on the given Object and return that
//        offer.setId(offerEntity.getId());
//        offer.setRefNo(offerEntity.getRefNo());
//        offer.setNominationDeadline(offerEntity.getNominationDeadline());
//        offer.setEmployerName(offerEntity.getEmployerName());
//        offer.setEmployerAddress(offerEntity.getEmployerAddress());
//        offer.setEmployerAddress2(offerEntity.getEmployerAddress2());
//        offer.setEmployerBusiness(offerEntity.getEmployerBusiness());
//        offer.setEmployerEmployeesCount(offerEntity.getEmployerEmployeesCount());
//        offer.setEmployerWebsite(offerEntity.getEmployerWebsite());
//        offer.setFieldOfStudies(offerEntity.getFieldOfStudies());
//        offer.setSpecializations(offerEntity.getSpecializations());
//        offer.setPrevTrainingRequired(offerEntity.getPrevTrainingRequired());
//        offer.setOtherRequirements(offerEntity.getOtherRequirements());
//        offer.setGender(offerEntity.getGender());
//        offer.setLanguage1(offerEntity.getLanguage1());
//        offer.setLanguage1Level(offerEntity.getLanguage1Level());
//        offer.setLanguage1Operator(offerEntity.getLanguage1Operator());
//        offer.setLanguage2(offerEntity.getLanguage2());
//        offer.setLanguage2Level(offerEntity.getLanguage2Level());
//        offer.setLanguage2Operator(offerEntity.getLanguage2Operator());
//        offer.setLanguage3(offerEntity.getLanguage3());
//        offer.setLanguage3Level(offerEntity.getLanguage3Level());
//        offer.setWorkDescription(offerEntity.getWorkDescription());
//        offer.setTypeOfWork(offerEntity.getTypeOfWork());
//        offer.setMinimumWeeks(offerEntity.getMinimumWeeks());
//        offer.setMaximumWeeks(offerEntity.getMaximumWeeks());
//        offer.setFromDate(offerEntity.getFromDate());
//        offer.setToDate(offerEntity.getToDate());
//        offer.setFromDate2(offerEntity.getFromDate2());
//        offer.setToDate2(offerEntity.getToDate2());
//        offer.setUnavailableFrom(offerEntity.getUnavailableFrom());
//        offer.setUnavailableTo(offerEntity.getUnavailableTo());
//        offer.setWorkingPlace(offerEntity.getWorkingPlace());
//        offer.setNearestAirport(offerEntity.getNearestAirport());
//        offer.setNearestPubTransport(offerEntity.getNearestPubTransport());
//        offer.setWeeklyHours(offerEntity.getWeeklyHours());
//        offer.setDailyHours(offerEntity.getDailyHours());
//        offer.setPayment(offerEntity.getPayment());
//        offer.setCurrency(offerEntity.getCurrency());
//        offer.setPaymentFrequency(offerEntity.getPaymentFrequency());
//        offer.setDeduction(offerEntity.getDeduction());
//        offer.setLodgingBy(offerEntity.getLodgingBy());
//        offer.setLodgingCost(offerEntity.getLodgingCost());
//        offer.setLodgingCostFrequency(offerEntity.getLodgingCostFrequency());
//        offer.setLivingCost(offerEntity.getLivingCost());
//        offer.setLivingCostFrequency(offerEntity.getLivingCostFrequency());
//        offer.setCanteen(offerEntity.getCanteen());
//    }

//    private void copyFieldsToEntity(final Offer offer, final OfferEntity offerEntity) {
//        // ToDo Kim; @Michael, please avoid updating the objects given as parameters, it can be confusing and lead to other problems. Rather the code should create a new DTO based on the given Object and return that
//        offerEntity.setId(offer.getId());
//        offerEntity.setRefNo(offer.getRefNo());
//        offerEntity.setNominationDeadline(offer.getNominationDeadline());
//        offerEntity.setEmployerName(offer.getEmployerName());
//        offerEntity.setEmployerAddress(offer.getEmployerAddress());
//        offerEntity.setEmployerAddress2(offer.getEmployerAddress2());
//        offerEntity.setEmployerBusiness(offer.getEmployerBusiness());
//        offerEntity.setEmployerEmployeesCount(offer.getEmployerEmployeesCount());
//        offerEntity.setEmployerWebsite(offer.getEmployerWebsite());
//        offerEntity.setFieldOfStudies(offer.getFieldOfStudies());
//        offerEntity.setSpecializations(offer.getSpecializations());
//        offerEntity.setPrevTrainingRequired(offer.getPrevTrainingRequired());
//        offerEntity.setOtherRequirements(offer.getOtherRequirements());
//        offerEntity.setGender(offer.getGender());
//        offerEntity.setLanguage1(offer.getLanguage1());
//        offerEntity.setLanguage1Level(offer.getLanguage1Level());
//        offerEntity.setLanguage1Operator(offer.getLanguage1Operator());
//        offerEntity.setLanguage2(offer.getLanguage2());
//        offerEntity.setLanguage2Level(offer.getLanguage2Level());
//        offerEntity.setLanguage2Operator(offer.getLanguage2Operator());
//        offerEntity.setLanguage3(offer.getLanguage3());
//        offerEntity.setLanguage3Level(offer.getLanguage3Level());
//        offerEntity.setWorkDescription(offer.getWorkDescription());
//        offerEntity.setTypeOfWork(offer.getTypeOfWork());
//        offerEntity.setMinimumWeeks(offer.getMinimumWeeks());
//        offerEntity.setMaximumWeeks(offer.getMaximumWeeks());
//        offerEntity.setFromDate(offer.getFromDate());
//        offerEntity.setToDate(offer.getToDate());
//        offerEntity.setFromDate2(offer.getFromDate2());
//        offerEntity.setToDate2(offer.getToDate2());
//        offerEntity.setUnavailableFrom(offer.getUnavailableFrom());
//        offerEntity.setUnavailableTo(offer.getUnavailableTo());
//        offerEntity.setWorkingPlace(offer.getWorkingPlace());
//        offerEntity.setNearestAirport(offer.getNearestAirport());
//        offerEntity.setNearestPubTransport(offer.getNearestPubTransport());
//        offerEntity.setWeeklyHours(offer.getWeeklyHours());
//        offerEntity.setDailyHours(offer.getDailyHours());
//        offerEntity.setPayment(offer.getPayment());
//        offerEntity.setCurrency(offer.getCurrency());
//        offerEntity.setPaymentFrequency(offer.getPaymentFrequency());
//        offerEntity.setDeduction(offer.getDeduction());
//        offerEntity.setLodgingBy(offer.getLodgingBy());
//        offerEntity.setLodgingCost(offer.getLodgingCost());
//        offerEntity.setLodgingCostFrequency(offer.getLodgingCostFrequency());
//        offerEntity.setLivingCost(offer.getLivingCost());
//        offerEntity.setLivingCostFrequency(offer.getLivingCostFrequency());
//        offerEntity.setCanteen(offer.getCanteen());
//    }
}
