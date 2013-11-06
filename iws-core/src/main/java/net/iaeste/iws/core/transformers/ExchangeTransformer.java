/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.OfferTransformer
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

import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.dtos.exchange.OfferGroup;
import net.iaeste.iws.api.dtos.exchange.StudentApplication;
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.persistence.entities.exchange.ApplicationEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;

/**
 * Tranformer for the Exchange module, handles transformation of the DTO Objects
 * to and from the Entity data structure.
 *
 * @author  Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection OverlyLongMethod
 */
public final class ExchangeTransformer {

    private ExchangeTransformer() {
    }

    public static OfferEntity transform(final Offer offer) {
        OfferEntity result = null;

        if (offer != null) {
            result = new OfferEntity();

            result.setExternalId(offer.getOfferId());
            result.setGroup(CommonTransformer.transform(offer.getGroup()));
            result.setRefNo(offer.getRefNo());
            result.setEmployer(transform(offer.getEmployer()));
            result.setWorkDescription(offer.getWorkDescription());
            result.setTypeOfWork(offer.getTypeOfWork());
            result.setStudyLevels(CollectionTransformer.concatEnumCollection(offer.getStudyLevels()));
            result.setFieldOfStudies(CollectionTransformer.concatEnumCollection(offer.getFieldOfStudies()));
            result.setSpecializations(CollectionTransformer.join(offer.getSpecializations()));
            result.setPrevTrainingRequired(offer.getPreviousTrainingRequired());
            result.setOtherRequirements(offer.getOtherRequirements());
            result.setMinimumWeeks(offer.getMinimumWeeks());
            result.setMaximumWeeks(offer.getMaximumWeeks());
            result.setFromDate(CommonTransformer.readFromDateFromPeriod(offer.getPeriod1()));
            result.setToDate(CommonTransformer.readToDateFromPeriod(offer.getPeriod1()));
            result.setFromDate2(CommonTransformer.readFromDateFromPeriod(offer.getPeriod2()));
            result.setToDate2(CommonTransformer.readToDateFromPeriod(offer.getPeriod2()));
            result.setUnavailableFrom(CommonTransformer.readFromDateFromPeriod(offer.getUnavailable()));
            result.setUnavailableTo(CommonTransformer.readToDateFromPeriod(offer.getUnavailable()));
            result.setLanguage1(offer.getLanguage1());
            result.setLanguage1Level(offer.getLanguage1Level());
            result.setLanguage1Operator(offer.getLanguage1Operator());
            result.setLanguage2(offer.getLanguage2());
            result.setLanguage2Level(offer.getLanguage2Level());
            result.setLanguage2Operator(offer.getLanguage2Operator());
            result.setLanguage3(offer.getLanguage3());
            result.setLanguage3Level(offer.getLanguage3Level());
            result.setPayment(offer.getPayment());
            result.setPaymentFrequency(offer.getPaymentFrequency());
            result.setCurrency(offer.getCurrency());
            result.setDeduction(offer.getDeduction());
            result.setLivingCost(offer.getLivingCost());
            result.setLivingCostFrequency(offer.getLivingCostFrequency());
            result.setLodgingBy(offer.getLodgingBy());
            result.setLodgingCost(offer.getLodgingCost());
            result.setLodgingCostFrequency(offer.getLodgingCostFrequency());
            result.setNumberOfHardCopies(offer.getNumberOfHardCopies());
            result.setAdditionalInformation(offer.getAdditionalInformation());
            result.setStatus(offer.getStatus());
            result.setNumberOfHardCopies(offer.getNumberOfHardCopies());
            result.setNominationDeadline(CommonTransformer.convert(offer.getNominationDeadline()));
        }

        return result;
    }

    public static Offer transform(final OfferEntity entity) {
        Offer result = null;

        if (entity != null) {
            result = new Offer();

            result.setOfferId(entity.getExternalId());
            result.setGroup(CommonTransformer.transform(entity.getGroup()));
            result.setRefNo(entity.getRefNo());
            result.setEmployer(transform(entity.getEmployer()));
            result.setWorkDescription(entity.getWorkDescription());
            result.setTypeOfWork(entity.getTypeOfWork());
            result.setStudyLevels(CollectionTransformer.explodeEnumSet(StudyLevel.class, entity.getStudyLevels()));
            result.setFieldOfStudies(CollectionTransformer.explodeEnumSet(FieldOfStudy.class, entity.getFieldOfStudies()));
            result.setSpecializations(CollectionTransformer.explodeStringSet(entity.getSpecializations()));
            result.setPreviousTrainingRequired(entity.getPrevTrainingRequired());
            result.setOtherRequirements(entity.getOtherRequirements());
            result.setLanguage1(entity.getLanguage1());
            result.setLanguage1Level(entity.getLanguage1Level());
            result.setLanguage1Operator(entity.getLanguage1Operator());
            result.setLanguage2(entity.getLanguage2());
            result.setLanguage2Level(entity.getLanguage2Level());
            result.setLanguage2Operator(entity.getLanguage2Operator());
            result.setLanguage3(entity.getLanguage3());
            result.setLanguage3Level(entity.getLanguage3Level());
            result.setMinimumWeeks(entity.getMinimumWeeks());
            result.setMaximumWeeks(entity.getMaximumWeeks());
            result.setPeriod1(CommonTransformer.transform(entity.getFromDate(), entity.getToDate()));
            result.setPeriod2(CommonTransformer.transform(entity.getFromDate2(), entity.getToDate2()));
            result.setUnavailable(CommonTransformer.transform(entity.getUnavailableFrom(), entity.getUnavailableTo()));
            result.setPayment(entity.getPayment());
            result.setPaymentFrequency(entity.getPaymentFrequency());
            result.setCurrency(entity.getCurrency());
            result.setDeduction(entity.getDeduction());
            result.setLivingCost(entity.getLivingCost());
            result.setLivingCostFrequency(entity.getLivingCostFrequency());
            result.setLodgingBy(entity.getLodgingBy());
            result.setLodgingCost(entity.getLodgingCost());
            result.setLodgingCostFrequency(entity.getLodgingCostFrequency());
            result.setNominationDeadline(CommonTransformer.convert(entity.getNominationDeadline()));
            result.setNumberOfHardCopies(entity.getNumberOfHardCopies());
            result.setAdditionalInformation(entity.getAdditionalInformation());
            result.setStatus(entity.getStatus());
            result.setModified(new DateTime(entity.getModified()));
            result.setCreated(new DateTime(entity.getCreated()));
        }

        return result;
    }

    public static Employer transform(final EmployerEntity entity) {
        Employer result = null;

        if (entity != null) {
            result = new Employer();

            result.setEmployerId(entity.getExternalId());
            result.setGroup(CommonTransformer.transform(entity.getGroup()));
            result.setName(entity.getName());
            result.setDepartment(entity.getDepartment());
            result.setBusiness(entity.getBusiness());
            result.setAddress(CommonTransformer.transform(entity.getAddress()));
            result.setEmployeesCount(entity.getNumberOfEmployees());
            result.setWebsite(entity.getWebsite());
            result.setWorkingPlace(entity.getWorkingPlace());
            result.setNearestAirport(entity.getNearestAirport());
            result.setNearestPublicTransport(entity.getNearestPublicTransport());
            result.setWeeklyHours(entity.getWeeklyHours());
            result.setDailyHours(entity.getDailyHours());
            result.setCanteen(entity.getCanteen());
        }

        return result;
    }

    public static EmployerEntity transform(final Employer employer) {
        EmployerEntity result = null;

        if (employer != null) {
            result = new EmployerEntity();

            result.setExternalId(employer.getEmployerId());
            result.setGroup(CommonTransformer.transform(employer.getGroup()));
            result.setName(employer.getName());
            result.setDepartment(employer.getDepartment());
            result.setBusiness(employer.getBusiness());
            result.setAddress(CommonTransformer.transform(employer.getAddress()));
            result.setNumberOfEmployees(employer.getEmployeesCount());
            result.setWebsite(employer.getWebsite());
            result.setWorkingPlace(employer.getWorkingPlace());
            result.setNearestAirport(employer.getNearestAirport());
            result.setNearestPublicTransport(employer.getNearestPublicTransport());
            result.setWeeklyHours(employer.getWeeklyHours());
            result.setDailyHours(employer.getDailyHours());
        }

        return result;
    }

    public static OfferGroup transform(final OfferGroupEntity entity) {
        OfferGroup result = null;

        if (entity != null) {
            result = new OfferGroup();

            result.setOfferRefNo(entity.getOffer().getRefNo());
            result.setGroupId(entity.getGroup().getExternalId());
            result.setModified(new DateTime(entity.getModified()));
            result.setCreated(new DateTime(entity.getCreated()));
        }

        return result;
    }

    public static StudentApplication transform(final ApplicationEntity entity) {
        StudentApplication result = null;

        if (entity != null) {
            result = new StudentApplication();

            result.setApplicationId(entity.getExternalId());
            result.setOffer(transform(entity.getOffer()));
            result.setStudent(AdministrationTransformer.transform(entity.getStudent()));
            result.setStatus(entity.getStatus());
            result.setModified(new DateTime(entity.getModified()));
            result.setCreated(new DateTime(entity.getCreated()));
        }

        return result;
    }

    public static ApplicationEntity transform(final StudentApplication application) {
        ApplicationEntity result = null;

        if (application != null) {
            result = new ApplicationEntity();

            result.setExternalId(application.getApplicationId());
            result.setOffer(transform(application.getOffer()));
            result.setStudent(AdministrationTransformer.transform(application.getStudent()));
            result.setStatus(application.getStatus());
        }

        return result;
    }
}
