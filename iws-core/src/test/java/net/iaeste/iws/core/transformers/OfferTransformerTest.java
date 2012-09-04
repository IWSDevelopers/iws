package net.iaeste.iws.core.transformers;/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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

import static org.hamcrest.core.Is.is;

import net.iaeste.iws.api.dtos.Employer;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.OfferTestUtility;
import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.LanguageOperator;
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.api.enums.TypeOfWork;
import net.iaeste.iws.persistence.entities.OfferEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class OfferTransformerTest {
    @Test
    public void testCopyingMinimalOfferToEntity() {
        final Offer offer = OfferTestUtility.getMinimalOffer();
        final OfferEntity entity = OfferTransformer.transform(offer);
        // TODO: check field by field
        Assert.assertThat(offer.getId(), is(entity.getId()));
        Assert.assertThat(offer.getTypeOfWork(), is(TypeOfWork.toValue(entity.getTypeOfWork())));

        Assert.assertThat(offer.getStudyLevels(), is(CollectionTransformer.explodeEnumSet(StudyLevel.class, entity.getStudyLevels())));
        Assert.assertThat(offer.getSpecializations(), is(CollectionTransformer.explodeStringSet(entity.getSpecializations())));
        Assert.assertThat(offer.getFieldOfStudies(), is(CollectionTransformer.explodeEnumSet(FieldOfStudy.class, entity.getFieldOfStudies())));
    }

    @Test
    public void testCopyingMinimalOfferToDto() {
        final OfferEntity entity = getMinimalOfferEntity();
        final Offer offer = OfferTransformer.transform(entity);
        // TODO: check field by field
        Assert.assertThat(offer.getTypeOfWork(), is(TypeOfWork.toValue(entity.getTypeOfWork())));

        Assert.assertThat(offer.getStudyLevels(), is(CollectionTransformer.explodeEnumSet(StudyLevel.class, entity.getStudyLevels())));
        Assert.assertThat(offer.getSpecializations(), is(CollectionTransformer.explodeStringSet(entity.getSpecializations())));
        Assert.assertThat(offer.getFieldOfStudies(), is(CollectionTransformer.explodeEnumSet(FieldOfStudy.class, entity.getFieldOfStudies())));

    }

    @Test
    public void testCopyingBackAndForthFromDto() {
        final Offer offer = OfferTestUtility.getMinimalOffer();
        final OfferEntity entity = OfferTransformer.transform(offer);
        final Offer newOffer = OfferTransformer.transform(entity);
        // we rely on equals method
        Assert.assertThat(offer, is(newOffer));
    }

    @Test
    public void testCopyingBackAndForthFromEntity() {
        final OfferEntity entity = getMinimalOfferEntity();
        final Offer offer = OfferTransformer.transform(entity);
        final OfferEntity newEntity = OfferTransformer.transform(offer);
        // we rely on equals method
        Assert.assertThat(entity, is(newEntity));
    }

    @Test
    public void testCopyingBackAndForthFromEmptyEntity() {
        final OfferEntity entity = new OfferEntity();
        final Offer offer = OfferTransformer.transform(entity);
        final OfferEntity newEntity = OfferTransformer.transform(offer);
        // we rely on equals method
        Assert.assertThat(entity, is(newEntity));
    }

    @Test
    public void testCopyingBackAndForthFromEmptyDto() {
        final Offer offer = new Offer();
        final OfferEntity entity = OfferTransformer.transform(offer);
        final Offer newOffer = OfferTransformer.transform(entity);
        // we rely on equals method
        Assert.assertThat(offer, is(newOffer));
    }

    @Test
    public void testCopyingFullOfferToEmployerDto() {
        final OfferEntity entity = getFullOfferEntity();

        final Employer employer = new Employer();
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

        final Employer newEmployer = OfferTransformer.transform(Employer.class, entity);

        Assert.assertThat(newEmployer, is(employer));
    }

    private OfferEntity getMinimalOfferEntity() {
        final OfferEntity minimalOffer = new OfferEntity();
        minimalOffer.setRefNo(OfferTestUtility.REF_NO);
        minimalOffer.setEmployerName(OfferTestUtility.EMPLOYER_NAME);
        final List<StudyLevel> list = new ArrayList<>(1);
        list.add(StudyLevel.E);
        final List<FieldOfStudy> fieldOfStudies = new ArrayList<>();
        fieldOfStudies.add(FieldOfStudy.IT);
        minimalOffer.setFieldOfStudies(CollectionTransformer.concatEnumCollection(fieldOfStudies));
        minimalOffer.setStudyLevels(CollectionTransformer.concatEnumCollection(list));
        minimalOffer.setLanguage1(Language.ENGLISH);
        minimalOffer.setLanguage1Level(LanguageLevel.E);
        minimalOffer.setWorkDescription(OfferTestUtility.WORK_DESCRIPTION);
        minimalOffer.setMaximumWeeks(OfferTestUtility.MAXIMUM_WEEKS);
        minimalOffer.setMinimumWeeks(OfferTestUtility.MINIMUM_WEEKS);
        minimalOffer.setWeeklyHours(OfferTestUtility.WEEKLY_HOURS);
        minimalOffer.setFromDate(OfferTestUtility.FROM_DATE);
        minimalOffer.setToDate(OfferTestUtility.TO_DATE);
        return minimalOffer;
    }

    @SuppressWarnings("OverlyLongMethod")
    private OfferEntity getFullOfferEntity() {
        final OfferEntity offer = getMinimalOfferEntity();
        offer.setNominationDeadline(OfferTestUtility.NOMINATION_DEADLINE);
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
        offer.setFromDate2(OfferTestUtility.FROM_DATE2);
        offer.setToDate2(OfferTestUtility.TO_DATE2);
        offer.setUnavailableFrom(OfferTestUtility.UNAVAIABLE_FROM);
        offer.setUnavailableTo(OfferTestUtility.UNAVAIABLE_TO);
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
        offer.setSpecializations(OfferTestUtility.SPECIALIZATIONS);

        return offer;
    }
}
