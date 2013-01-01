/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.OfferTransformerTest
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

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.dtos.EmployerInformation;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.OfferTestUtility;
import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.LanguageOperator;
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.api.enums.TypeOfWork;
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.persistence.entities.OfferEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author  Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection OverlyLongMethod
 */
public class OfferTransformerTest {

    @Test
    public void testCopyingMinimalOfferToEntity() {
        final Offer offer = OfferTestUtility.getMinimalOffer();

        // set modified and created (should be ignored on transformation)
        final Date modifiedDate = new Date(new Date().getTime() - 3600);
        final Date createdDate = new Date(new Date().getTime() - 7200);
        offer.setModified(new DateTime(modifiedDate));
        offer.setCreated(new DateTime(createdDate));

        final OfferEntity entity = OfferTransformer.transform(offer);

        assertThat(offer.getRefNo(), is(entity.getRefNo()));
        assertThat(offer.getEmployerName(), is(entity.getEmployerName()));
        assertThat(offer.getEmployerAddress(), is(entity.getEmployerAddress()));
        assertThat(offer.getEmployerAddress2(), is(entity.getEmployerAddress2()));
        assertThat(offer.getEmployerBusiness(), is(entity.getEmployerBusiness()));
        assertThat(offer.getEmployerEmployeesCount(), is(entity.getEmployerEmployeesCount()));
        assertThat(offer.getEmployerWebsite(), is(entity.getEmployerWebsite()));
        assertThat(offer.getPrevTrainingRequired(), is(entity.getPrevTrainingRequired()));
        assertThat(offer.getOtherRequirements(), is(entity.getOtherRequirements()));
        assertThat(offer.getLanguage1(), is(entity.getLanguage1()));
        assertThat(offer.getLanguage1Level(), is(entity.getLanguage1Level()));
        assertThat(offer.getLanguage1Operator(), is(entity.getLanguage1Operator()));
        assertThat(offer.getLanguage2(), is(entity.getLanguage2()));
        assertThat(offer.getLanguage2Level(), is(entity.getLanguage2Level()));
        assertThat(offer.getLanguage2Operator(), is(entity.getLanguage2Operator()));
        assertThat(offer.getLanguage3(), is(entity.getLanguage3()));
        assertThat(offer.getLanguage3Level(), is(entity.getLanguage3Level()));
        assertThat(offer.getWorkDescription(), is(entity.getWorkDescription()));
        assertThat(offer.getMinimumWeeks(), is(entity.getMinimumWeeks()));
        assertThat(offer.getMaximumWeeks(), is(entity.getMaximumWeeks()));
        assertThat(offer.getWorkingPlace(), is(entity.getWorkingPlace()));
        assertThat(offer.getNearestAirport(), is(entity.getNearestAirport()));
        assertThat(offer.getNearestPubTransport(), is(entity.getNearestPubTransport()));
        assertThat(offer.getWeeklyHours(), is(entity.getWeeklyHours()));
        assertThat(offer.getDailyHours(), is(entity.getDailyHours()));
        assertThat(offer.getPayment(), is(entity.getPayment()));
        assertThat(offer.getCurrency(), is(entity.getCurrency()));
        assertThat(offer.getPaymentFrequency(), is(entity.getPaymentFrequency()));
        assertThat(offer.getDeduction(), is(entity.getDeduction()));
        assertThat(offer.getLodgingBy(), is(entity.getLodgingBy()));
        assertThat(offer.getLodgingCost(), is(entity.getLodgingCost()));
        assertThat(offer.getLodgingCostFrequency(), is(entity.getLodgingCostFrequency()));
        assertThat(offer.getLivingCost(), is(entity.getLivingCost()));
        assertThat(offer.getLivingCostFrequency(), is(entity.getLivingCostFrequency()));
        assertThat(offer.getCanteen(), is(entity.getCanteen()));
        assertThat(offer.getNominationDeadline(), is(nullValue()));
        assertThat(offer.getFromDate().toDate(), is(entity.getFromDate()));
        assertThat(offer.getToDate().toDate(), is(entity.getToDate()));
        assertThat(offer.getFromDate2(), is(nullValue()));
        assertThat(offer.getToDate2(), is(nullValue()));
        assertThat(offer.getUnavailableFrom(), is(nullValue()));
        assertThat(offer.getUnavailableTo(), is(nullValue()));

        assertThat(offer.getTypeOfWork(), is(TypeOfWork.toValue(entity.getTypeOfWork())));

        assertThat(offer.getStudyLevels(), is(CollectionTransformer.explodeEnumSet(StudyLevel.class, entity.getStudyLevels())));
        assertThat(offer.getSpecializations(), is(CollectionTransformer.explodeStringSet(entity.getSpecializations())));
        assertThat(offer.getFieldOfStudies(), is(CollectionTransformer.explodeEnumSet(FieldOfStudy.class, entity.getFieldOfStudies())));

        // modified and created are readonly so they shouldn't be copied to Entity
        assertThat(entity.getModified(), is(not(modifiedDate)));
        assertThat(entity.getCreated(), is(not(createdDate)));
    }

    @Test
    public void testCopyingMinimalOfferToDto() {
        final OfferEntity entity = getMinimalOfferEntity();
        final Offer offer = OfferTransformer.transform(entity);

        assertThat(offer.getRefNo(), is(entity.getRefNo()));
        assertThat(offer.getEmployerName(), is(entity.getEmployerName()));
        assertThat(offer.getEmployerAddress(), is(entity.getEmployerAddress()));
        assertThat(offer.getEmployerAddress2(), is(entity.getEmployerAddress2()));
        assertThat(offer.getEmployerBusiness(), is(entity.getEmployerBusiness()));
        assertThat(offer.getEmployerEmployeesCount(), is(entity.getEmployerEmployeesCount()));
        assertThat(offer.getEmployerWebsite(), is(entity.getEmployerWebsite()));
        assertThat(offer.getPrevTrainingRequired(), is(entity.getPrevTrainingRequired()));
        assertThat(offer.getOtherRequirements(), is(entity.getOtherRequirements()));
        assertThat(offer.getLanguage1(), is(entity.getLanguage1()));
        assertThat(offer.getLanguage1Level(), is(entity.getLanguage1Level()));
        assertThat(offer.getLanguage1Operator(), is(entity.getLanguage1Operator()));
        assertThat(offer.getLanguage2(), is(entity.getLanguage2()));
        assertThat(offer.getLanguage2Level(), is(entity.getLanguage2Level()));
        assertThat(offer.getLanguage2Operator(), is(entity.getLanguage2Operator()));
        assertThat(offer.getLanguage3(), is(entity.getLanguage3()));
        assertThat(offer.getLanguage3Level(), is(entity.getLanguage3Level()));
        assertThat(offer.getWorkDescription(), is(entity.getWorkDescription()));
        assertThat(offer.getMinimumWeeks(), is(entity.getMinimumWeeks()));
        assertThat(offer.getMaximumWeeks(), is(entity.getMaximumWeeks()));
        assertThat(offer.getWorkingPlace(), is(entity.getWorkingPlace()));
        assertThat(offer.getNearestAirport(), is(entity.getNearestAirport()));
        assertThat(offer.getNearestPubTransport(), is(entity.getNearestPubTransport()));
        assertThat(offer.getWeeklyHours(), is(entity.getWeeklyHours()));
        assertThat(offer.getDailyHours(), is(entity.getDailyHours()));
        assertThat(offer.getPayment(), is(entity.getPayment()));
        assertThat(offer.getCurrency(), is(entity.getCurrency()));
        assertThat(offer.getPaymentFrequency(), is(entity.getPaymentFrequency()));
        assertThat(offer.getDeduction(), is(entity.getDeduction()));
        assertThat(offer.getLodgingBy(), is(entity.getLodgingBy()));
        assertThat(offer.getLodgingCost(), is(entity.getLodgingCost()));
        assertThat(offer.getLodgingCostFrequency(), is(entity.getLodgingCostFrequency()));
        assertThat(offer.getLivingCost(), is(entity.getLivingCost()));
        assertThat(offer.getLivingCostFrequency(), is(entity.getLivingCostFrequency()));
        assertThat(offer.getCanteen(), is(entity.getCanteen()));
        assertThat(offer.getNominationDeadline(), is(nullValue()));
        assertThat(offer.getFromDate().toDate(), is(entity.getFromDate()));
        assertThat(offer.getToDate().toDate(), is(entity.getToDate()));
        assertThat(offer.getFromDate2(), is(nullValue()));
        assertThat(offer.getToDate2(), is(nullValue()));
        assertThat(offer.getUnavailableFrom(), is(nullValue()));
        assertThat(offer.getUnavailableTo(), is(nullValue()));

        assertThat(offer.getTypeOfWork(), is(TypeOfWork.toValue(entity.getTypeOfWork())));

        assertThat(offer.getStudyLevels(), is(CollectionTransformer.explodeEnumSet(StudyLevel.class, entity.getStudyLevels())));
        assertThat(offer.getSpecializations(), is(CollectionTransformer.explodeStringSet(entity.getSpecializations())));
        assertThat(offer.getFieldOfStudies(), is(CollectionTransformer.explodeEnumSet(FieldOfStudy.class, entity.getFieldOfStudies())));

        assertThat(offer.getModified().toDate(), is(entity.getModified()));
        assertThat(offer.getCreated().toDate(), is(entity.getCreated()));

    }

    @Test
    public void testCopyingBackAndForthFromDto() {
        final Offer offer = OfferTestUtility.getMinimalOffer();
        final OfferEntity entity = OfferTransformer.transform(offer);
        final Offer newOffer = OfferTransformer.transform(entity);
        // we rely on equals method
        assertThat(offer, is(newOffer));
    }

    @Test
    public void testCopyingBackAndForthFromEmptyDto() {
        final Offer offer = new Offer();
        final OfferEntity entity = OfferTransformer.transform(offer);
        final Offer newOffer = OfferTransformer.transform(entity);
        // we rely on equals method
        assertThat(offer, is(newOffer));
    }

    @Test
    public void testCopyingFullOfferToEmployerDto() {
        final OfferEntity entity = getFullOfferEntity();

        final EmployerInformation employer = new EmployerInformation();
        employer.setAddress(entity.getEmployerAddress());
        employer.setAddress2(entity.getEmployerAddress2());
        employer.setBusiness(entity.getEmployerBusiness());
        employer.setDailyHours(entity.getDailyHours());
        employer.setEmployeesCount(entity.getEmployerEmployeesCount());
        employer.setName(entity.getEmployerName());
        employer.setNearestAirport(entity.getNearestAirport());
        employer.setNearestPubTransport(entity.getNearestPubTransport());
        employer.setWebsite(entity.getEmployerWebsite());
        employer.setWeeklyHours(entity.getWeeklyHours());
        employer.setWorkingPlace(entity.getWorkingPlace());

        final EmployerInformation newEmployer = OfferTransformer.transform(EmployerInformation.class, entity);

        assertThat(newEmployer, is(employer));
    }

    private OfferEntity getMinimalOfferEntity() {
        final OfferEntity minimalOffer = new OfferEntity();
        minimalOffer.setRefNo(OfferTestUtility.REF_NO);
        minimalOffer.setEmployerName(OfferTestUtility.EMPLOYER_NAME);
        final List<StudyLevel> list = new ArrayList<>(1);
        list.add(StudyLevel.E);
        final List<FieldOfStudy> fieldOfStudies = new ArrayList<>(1);
        fieldOfStudies.add(FieldOfStudy.IT);
        minimalOffer.setFieldOfStudies(CollectionTransformer.concatEnumCollection(fieldOfStudies));
        minimalOffer.setStudyLevels(CollectionTransformer.concatEnumCollection(list));
        minimalOffer.setLanguage1(Language.ENGLISH);
        minimalOffer.setLanguage1Level(LanguageLevel.E);
        minimalOffer.setWorkDescription(OfferTestUtility.WORK_DESCRIPTION);
        minimalOffer.setMaximumWeeks(OfferTestUtility.MAXIMUM_WEEKS);
        minimalOffer.setMinimumWeeks(OfferTestUtility.MINIMUM_WEEKS);
        minimalOffer.setWeeklyHours(OfferTestUtility.WEEKLY_HOURS);
        minimalOffer.setFromDate(OfferTestUtility.FROM_DATE.toDate());
        minimalOffer.setToDate(OfferTestUtility.TO_DATE.toDate());
        return minimalOffer;
    }

    private OfferEntity getFullOfferEntity() {
        final OfferEntity offer = getMinimalOfferEntity();
        offer.setNominationDeadline(OfferTestUtility.NOMINATION_DEADLINE.toDate());
        offer.setEmployerAddress(OfferTestUtility.EMPLOYER_ADDRESS);
        offer.setEmployerAddress2(OfferTestUtility.EMPLOYER_ADDRESS2);
        offer.setEmployerBusiness(OfferTestUtility.EMPLOYER_BUSINESS);
        offer.setEmployerEmployeesCount(OfferTestUtility.EMPLOYER_EMPLOYEES_COUNT);
        offer.setEmployerWebsite(OfferTestUtility.EMPLOYER_WEBSITE);
        offer.setPrevTrainingRequired(true);
        offer.setOtherRequirements(OfferTestUtility.OTHER_REQUIREMENTS);
        offer.setLanguage1Operator(LanguageOperator.A);
        offer.setLanguage2(Language.FRENCH);
        offer.setLanguage2Level(LanguageLevel.E);
        offer.setLanguage2Operator(LanguageOperator.O);
        offer.setLanguage3(Language.GERMAN);
        offer.setLanguage3Level(LanguageLevel.E);
        offer.setTypeOfWork(OfferTestUtility.TYPE_OF_WORK.toString());
        offer.setFromDate2(OfferTestUtility.FROM_DATE2.toDate());
        offer.setToDate2(OfferTestUtility.TO_DATE2.toDate());
        offer.setUnavailableFrom(OfferTestUtility.UNAVAIABLE_FROM.toDate());
        offer.setUnavailableTo(OfferTestUtility.UNAVAIABLE_TO.toDate());
        offer.setWorkingPlace(OfferTestUtility.WORKING_PLACE);
        offer.setNearestAirport(OfferTestUtility.NEAREST_AIRPORT);
        offer.setNearestPubTransport(OfferTestUtility.NEAREST_PUBLIC_TRANSPORT);
        offer.setDailyHours(OfferTestUtility.DAILY_HOURS);
        offer.setCurrency(OfferTestUtility.CURRENCY);
        offer.setPaymentFrequency(OfferTestUtility.PAYMENT_FREQUENCY);
        offer.setDeduction(OfferTestUtility.DEDUCTION);
        offer.setLodgingBy(OfferTestUtility.LODGING_BY);
        offer.setLodgingCost(OfferTestUtility.LODGING_COST);
        offer.setLodgingCostFrequency(OfferTestUtility.LODGING_COST_FREQUENCY);
        offer.setLivingCost(OfferTestUtility.LIVING_COST);
        offer.setLivingCostFrequency(OfferTestUtility.LIVING_COST_FREQUENCY);
        offer.setCanteen(OfferTestUtility.CANTEEN);
        offer.setSpecializations(CollectionTransformer.join(OfferTestUtility.SPECIALIZATIONS));

        return offer;
    }
}
