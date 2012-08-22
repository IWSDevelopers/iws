/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.OfferTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.api.dtos;

import static net.iaeste.iws.api.dtos.OfferTestUtility.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.Specialization;
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.utils.Copier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class OfferTest {
    private Offer offer = getMinimalOffer();
    /**
     * field is used in methods for verifing dates, field is initialized in {@reference setUpDates} method
     */
    private static final Date[] dates = new Date[9];
    private static final String[] validRefNos = { "IN-2011-0001-KU", "GB-2011-0001-01", "AT-2012-1234-AB", "GB-2011-0001" };

    private static final String[] invalidRefNos = { "GB-2011-00001", "UK-2011-00001", "INE-2011-0001-KU", "GB-2011-w001", "PL-201w-0001", "GB-2011-0001-101",
            "GB-10000-00001-01", "GB-2011-a000-01", "GB-20w1-0000-01", "U-2011-0000-01", "U9-2011-a000-01", "-2011-a000-01", "XX-2011-a000-01",
            "XX-2011-0000-01" };
    static final String ERRMSG_NOT_NULL = " field cannot be null";
    static final String ERRMSG_PRESENCE = "if 'from(2)' is present then 'to(2)' should be present";

    private static String[] getValidRefNos() {
        return Copier.copy(validRefNos);
    }

    private static String[] getInvalidRefNos() {
        return Copier.copy(invalidRefNos);
    }

    private void setUpDates() {
        final long now = new Date().getTime();
        for (int i = 0; i < dates.length; ++i) {
            dates[i] = new Date(now + i * 3600 * 24);
        }
    }

    @Before
    public void before() {
        setUpDates();
        offer = getMinimalOffer();
    }

    @Test
    public void testCopyConstructor() {
        final Offer offer = getMinimalOffer();
        final Offer copy = new Offer(offer);
        Assert.assertThat(offer, is(not(nullValue())));
        Assert.assertThat(copy, is(not(nullValue())));
        Assert.assertThat(offer, is(copy));
    }

    @Test
    public void testMinimalOffer() {
        Assert.assertNotNull("reference not null", offer);
        Assert.assertThat("RefNo", REF_NO, is(offer.getRefNo()));
        Assert.assertThat("EmployerName", EMPLOYER_NAME, is(offer.getEmployerName()));
        Assert.assertThat("size of Study Levels collection should be 1", 1, is(offer.getStudyLevels().size()));
        Assert.assertThat("first Study Level should be E", StudyLevel.E, is(offer.getStudyLevels().get(0)));
        Assert.assertThat("Gender", Gender.E, is(offer.getGender()));
        Assert.assertThat("Language", Language.ENGLISH, is(offer.getLanguage1()));
        Assert.assertThat("LanguageLevel", LanguageLevel.E, is(offer.getLanguage1Level()));
        Assert.assertThat("WorkDescription", WORK_DESCRIPTION, is(offer.getWorkDescription()));
        Assert.assertThat("MaximumWeeks", MAXIMUM_WEEKS, is(offer.getMaximumWeeks()));
        Assert.assertThat("MinimumWeeks", MINIMUM_WEEKS, is(offer.getMinimumWeeks()));
        Assert.assertThat("WeeklyHours", WEEKLY_HOURS, is(offer.getWeeklyHours()));
        Assert.assertThat("fromDate", FROM_DATE, is(offer.getFromDate()));
        Assert.assertThat("toDate", TO_DATE, is(offer.getToDate()));
    }

    @Test
    public void testMutableFields() {
        offer.setNominationDeadline(NOMINATION_DEADLINE);
        final Date nominationDeadline = offer.getNominationDeadline();
        nominationDeadline.setTime(new Date().getTime() + 3600);
        Assert.assertThat(nominationDeadline, is(not(offer.getNominationDeadline())));
    }

    @Test
    public void testAddingToFieldOfStudyList() {
        final List<FieldOfStudy> fieldOfStudies = offer.getFieldOfStudies();
        final List<FieldOfStudy> primaryFieldOfStudies = offer.getFieldOfStudies();
        fieldOfStudies.add(FieldOfStudy.AERONAUTIC_ENGINEERING);
        Assert.assertThat(fieldOfStudies, is(not(offer.getFieldOfStudies())));
        Assert.assertThat(primaryFieldOfStudies, is(offer.getFieldOfStudies()));
    }

    @Test
    public void testAddingToSpecializationList() {
        final List<Specialization> specializations = offer.getSpecializations();
        final List<Specialization> primarySpecializations = offer.getSpecializations();
        specializations.add(Specialization.ASTROPHYSICS);
        Assert.assertThat(specializations, is(not(offer.getSpecializations())));
        Assert.assertThat(primarySpecializations, is(offer.getSpecializations()));
    }

    @Test
    public void testAddingToStudyLevelList() {
        final List<StudyLevel> studyLevels = offer.getStudyLevels();
        final List<StudyLevel> primaryStudyLevels = offer.getStudyLevels();
        studyLevels.add(StudyLevel.B);
        Assert.assertThat(studyLevels, is(not(offer.getStudyLevels())));
        Assert.assertThat(primaryStudyLevels, is(offer.getStudyLevels()));
    }

    @Test
    public void testDatesImmutability() {
        final Date now = new Date();
        final Date oldDate = (Date) now.clone();
        offer.setUnavailableFrom(now);
        offer.setUnavailableTo(now);
        offer.setToDate(now);
        offer.setToDate2(now);
        offer.setFromDate(now);
        offer.setFromDate2(now);
        offer.setNominationDeadline(now);

        now.setTime(now.getTime() + 3600);
        Assert.assertThat("HolidaysFrom", oldDate, is(offer.getUnavailableFrom()));
        Assert.assertThat("HolidaysTo", oldDate, is(offer.getUnavailableTo()));
        Assert.assertThat("ToDate", oldDate, is(offer.getToDate()));
        Assert.assertThat("ToDate2", oldDate, is(offer.getToDate2()));
        Assert.assertThat("FromDate", oldDate, is(offer.getFromDate()));
        Assert.assertThat("FromDate2", oldDate, is(offer.getFromDate2()));
        Assert.assertThat("NominationDeadline", oldDate, is(offer.getNominationDeadline()));
    }

    /**
     * @todo which fields are not important for the equality of an offer?
     */
    @Test
    public void testEqualityOfSimilarOffers() {
//        final Offer offer2 = new Offer(offer);
//        offer2.setWorkDescription("@#$#@");
//        Assert.assertThat(offer, is(equalTo(offer2)));
    }

    @Test
    public void testVerifyValidRefNo() {
        for (final String correctRefNo : getValidRefNos()) {
            offer.setRefNo(correctRefNo);
            Assert.assertThat(String.format("%s should be correct", correctRefNo), offer.verifyRefNo(), is(true));
            Assert.assertThat(isVerificationExceptionThrown(), is(false));
        }
    }

    @Test
    public void testVerifyInvalidRefNo() {
        for (final String incorrectRefNo : getInvalidRefNos()) {
            offer.setRefNo(incorrectRefNo);
            Assert.assertThat(String.format("%s should be incorrect", incorrectRefNo), offer.verifyRefNo(), is(false));
            Assert.assertThat(isVerificationExceptionThrown(), is(true));
        }
    }

    @Test
    public void testVerifyPresenceOfDatesNoFrom() {
        // --deadline--------------------------to-------------------------------------->
        offer.setNominationDeadline(dates[0]);
        offer.setFromDate(null);
        offer.setToDate(dates[2]);
        Assert.assertThat(ERRMSG_PRESENCE, offer.verifyDates(), is(false));
        Assert.assertThat(isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testVerifyPresenceOfDatesNoFrom2() {
        // --deadline--------------------------to2------------------------------------->
        offer.setNominationDeadline(dates[0]);
        offer.setToDate2(dates[2]);
        offer.setFromDate2(null);
        Assert.assertThat(ERRMSG_PRESENCE, offer.verifyDates(), is(false));
        Assert.assertThat(isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testVerifyPresenceOfDatesNoTo() {
        // --deadline--------------------------from------------------------------------>
        offer.setNominationDeadline(dates[0]);
        offer.setFromDate(dates[2]);
        offer.setToDate(null);
        Assert.assertThat(ERRMSG_PRESENCE, offer.verifyDates(), is(false));
        Assert.assertThat(isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testVerifyPresenceOfDatesNoTo2() {
        // --deadline--------------------------from2----------------------------------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setFromDate2(dates[2]);
        Assert.assertThat(ERRMSG_PRESENCE, offer.verifyDates(), is(false));
        Assert.assertThat(isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testVerifyPresenceOfDates1() {
        // --deadline------------------from2---------------to-------------------------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setFromDate2(dates[1]);
        offer.setToDate(dates[2]);
        offer.setToDate2(null);
        offer.setFromDate(null);
        Assert.assertThat(ERRMSG_PRESENCE, offer.verifyDates(), is(false));
        Assert.assertThat(isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testVerifyPresenceOfDates2() {
        // --deadline---------------from------------------to2-------------------------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setFromDate(dates[1]);
        offer.setToDate2(dates[2]);
        offer.setToDate(null);
        offer.setFromDate2(null);
        Assert.assertThat(ERRMSG_PRESENCE, offer.verifyDates(), is(false));
        Assert.assertThat(isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testVerifyPresenceOfDates3() {
        // --deadline------------from----------------------to--------------to2--------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setFromDate(dates[1]);
        offer.setToDate(dates[2]);
        offer.setToDate2(dates[3]);
        offer.setFromDate(null);
        Assert.assertThat(ERRMSG_PRESENCE, offer.verifyDates(), is(false));
        Assert.assertThat(isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testVerifyPresenceOfDates4() {
        // --deadline------------from----------------------to--------------from2------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setFromDate(dates[1]);
        offer.setToDate(dates[2]);
        offer.setFromDate2(dates[3]);
        offer.setToDate2(null);
        Assert.assertThat(ERRMSG_PRESENCE, offer.verifyDates(), is(false));
        Assert.assertThat(isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testVerifyPresenceOfDates5() {
        // --deadline------------from-----------------------from2----------to2--------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setFromDate(dates[1]);
        offer.setFromDate2(dates[2]);
        offer.setToDate2(dates[3]);
        Assert.assertThat(ERRMSG_PRESENCE, offer.verifyDates(), is(false));
        Assert.assertThat(isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testVerifyPresenceOfDates6() {
        // --deadline--------------to-----------------------from2----------to2--------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setToDate(dates[1]);
        offer.setFromDate2(dates[2]);
        offer.setToDate2(dates[3]);
        offer.setFromDate(null);
        Assert.assertThat(ERRMSG_PRESENCE, offer.verifyDates(), is(false));
        Assert.assertThat(isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testVerifyOrderOfDates() {
        final String error = "'from(2)' have to be before 'to(2)'";
        // --deadline--------------------------to-------from--------------------------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setToDate(dates[1]);
        offer.setFromDate(dates[2]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline--------------------------to2-------from2------------------------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setToDate2(dates[1]);
        offer.setFromDate2(dates[2]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
    }

    @Test
    public void testVerifyPresenceOfDeadlineDate() {
        final String error = "'nominationDeadline' presence is not required";
        offer = getMinimalOffer();
        offer.setUnavailableFrom(null);
        offer.setUnavailableTo(null);
        offer.setFromDate(dates[1]);
        offer.setToDate(dates[2]);
        // --deadline--------------------------from-------to--------------------------->
        offer.setNominationDeadline(dates[0]);
        Assert.assertThat(error, offer.verifyDates(), is(true));
        // ------------------------------------from-------to--------------------------->
        offer.setNominationDeadline(null);
        Assert.assertThat(error, offer.verifyDates(), is(true));

    }

    @Test
    public void testVerifyDeadlineOrder() {
        final String error = "'nominationDeadline' should be before 'from' and 'from2'";
        offer = getMinimalOffer();
        offer.setFromDate(dates[1]);
        offer.setToDate(dates[3]);
        // ----from--------------deadline-------------------to------------------------->
        offer.setNominationDeadline(dates[2]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // ---from-----------------------to-------------deadline----------------------->
        offer.setNominationDeadline(dates[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));

        offer = getMinimalOffer();
        offer.setFromDate2(dates[1]);
        offer.setToDate2(dates[3]);
        // ---from2--------------deadline-------------------to2------------------------>
        offer.setNominationDeadline(dates[2]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // ---from2-----------------------to2--------deadline-------------------------->
        offer.setNominationDeadline(dates[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));

        // ---from2-----------------------to2--------deadline--------from------to------>
        offer = getMinimalOffer();
        offer.setFromDate2(dates[1]);
        offer.setToDate2(dates[3]);
        offer.setFromDate(dates[5]);
        offer.setToDate(dates[7]);
        offer.setNominationDeadline(dates[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // ---from-----------------------to---------deadline-------from2------to2------>
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setFromDate(dates[1]);
        offer.setToDate(dates[3]);
        offer.setFromDate2(dates[5]);
        offer.setToDate2(dates[7]);
        offer.setNominationDeadline(dates[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
    }

    @Test
    public void testVerifyOrderOfDatesGroups() {
        final String error = "dates from groups 1 and 2 cannot intertwine";
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        // --deadline------------from2-----from------------to--------------to2--------->
        offer.setFromDate2(dates[1]);
        offer.setFromDate(dates[2]);
        offer.setToDate(dates[3]);
        offer.setToDate2(dates[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline------------from-----from2------------to--------------to2--------->
        offer.setFromDate(dates[1]);
        offer.setFromDate2(dates[2]);
        offer.setToDate(dates[3]);
        offer.setToDate2(dates[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline------------from-----from2------------to2--------------to--------->
        offer.setFromDate(dates[1]);
        offer.setFromDate2(dates[2]);
        offer.setToDate2(dates[3]);
        offer.setToDate(dates[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline------------from2-----from------------to--------------to2--------->
        offer.setFromDate2(dates[1]);
        offer.setFromDate(dates[2]);
        offer.setToDate(dates[3]);
        offer.setToDate2(dates[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
    }

    @Test
    public void testVerifyOrderOfHolidaysDates() {
        final String error = "holidays dates must be inside from-to or from2-to2";
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        // --deadline-----------from--------holidaysFrom---to------holidaysTo---------->
        offer.setFromDate(dates[1]);
        offer.setUnavailableFrom(dates[2]);
        offer.setToDate(dates[3]);
        offer.setUnavailableTo(dates[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline--------holidaysFrom-----------from------holidaysTo---to---------->
        offer.setUnavailableFrom(dates[1]);
        offer.setFromDate(dates[2]);
        offer.setUnavailableTo(dates[3]);
        offer.setToDate(dates[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline-----------holidaysFrom------holidaysTo------from--------to------->
        offer.setUnavailableFrom(dates[1]);
        offer.setUnavailableTo(dates[2]);
        offer.setFromDate(dates[3]);
        offer.setToDate(dates[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline-----------from--------to------holidaysTo-----holidaysFrom-------->
        offer.setFromDate(dates[1]);
        offer.setToDate(dates[2]);
        offer.setUnavailableFrom(dates[3]);
        offer.setUnavailableTo(dates[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
    }

    @Test
    public void testVerifyNumberOfWeeks() {
        offer.setMinimumWeeks(0);
        offer.setMaximumWeeks(10);
        Assert.assertThat("minimumWeeks should be greater than 0", offer.verifyNumberOfWeeks(), is(false));
        offer.setMinimumWeeks(20);
        offer.setMaximumWeeks(10);
        Assert.assertThat("maximumWeeks should be greater than or equal to minimumWeeks", offer.verifyNumberOfWeeks(), is(false));
        offer.setMinimumWeeks(10);
        offer.setMaximumWeeks(10);
        Assert.assertThat("minimumWeeks can be equal to maximumWeeks", offer.verifyNumberOfWeeks(), is(true));
        offer.setMinimumWeeks(10);
        offer.setMaximumWeeks(12);
        Assert.assertThat(offer.verifyNumberOfWeeks(), is(true));
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void testMinimalOfferShouldBeValid() {
        offer = getMinimalOffer();
        // "valid offer from helper should be valid"
        offer.verify();
    }

    @Test
    public void testNotNullableRefNo() {
        offer = getMinimalOffer();
        offer.setRefNo(null);
        Assert.assertThat(String.format("refNo%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testNotNullableEmployerName() {
        offer = getMinimalOffer();
        offer.setEmployerName(null);
        Assert.assertThat(String.format("employerName%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableFieldOfStudies() {
        offer = getMinimalOffer();
        offer.setFieldOfStudies(null);
        Assert.assertThat(String.format("fieldOfStudies%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableStudyLevels() {
        offer = getMinimalOffer();
        offer.setStudyLevels(null);
        Assert.assertThat(String.format("studyLevels%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testNotEmptyStudyLevels() {
        offer = getMinimalOffer();
        offer.setStudyLevels(new ArrayList<StudyLevel>(0));
        Assert.assertThat("studyLevels list cannot be empty", isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableGender() {
        offer = getMinimalOffer();
        offer.setGender(null);
        Assert.assertThat(String.format("gender%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableLanguage1() {
        offer = getMinimalOffer();
        offer.setLanguage1(null);
        Assert.assertThat(String.format("language1%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableLanguage1Level() {
        offer = getMinimalOffer();
        offer.setLanguage1Level(null);
        Assert.assertThat(String.format("language1Level%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableWorkDescription() {
        offer = getMinimalOffer();
        offer.setWorkDescription(null);
        Assert.assertThat(String.format("workDescription%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableMaximumWeeks() {
        offer = getMinimalOffer();
        offer.setMaximumWeeks(null);
        Assert.assertThat(String.format("maximumWeeks%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableMinimumWeeks() {
        offer = getMinimalOffer();
        offer.setMinimumWeeks(null);
        Assert.assertThat(String.format("minimumWeeks%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableWeeklyHours() {
        offer = getMinimalOffer();
        offer.setWeeklyHours(null);
        Assert.assertThat(String.format("weeklyHours%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableFromDate() {
        offer = getMinimalOffer();
        offer.setFromDate(null);
        Assert.assertThat(String.format("fromDate%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableToDate() {
        offer = getMinimalOffer();
        offer.setToDate(null);
        Assert.assertThat(String.format("toDate%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void testNullPaymentFrequency() {
        offer.setPayment(null);
        offer.setPaymentFrequency(null);
        offer.verify();
    }

    @Test(expected = VerificationException.class)
    public void testNotNullablePaymentFrequencyWhenPaymentNotNull() {
        offer.setPayment(PAYMENT);
        offer.setPaymentFrequency(null);
        offer.verify();
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void testNullLodgingCostFrequency() {
        offer.setLodgingCostFrequency(null);
        offer.setLodgingCost(null);
        offer.verify();
    }

    @Test(expected = VerificationException.class)
    public void testNotNullableLodgingCostFrequencyWhenLodgingCostNotNull() {
        offer.setLodgingCostFrequency(null);
        offer.setLodgingCost(LODGING_COST);
        offer.verify();
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void testNullLivingCostFrequency() {
        offer.setLivingCostFrequency(null);
        offer.setLivingCost(null);
        offer.verify();
    }

    @Test(expected = VerificationException.class)
    public void testNotNullableLivingCostFrequencyWhenLivingCostNotNull() {
        offer.setLivingCostFrequency(null);
        offer.setLivingCost(LIVING_COST);
        offer.verify();
    }

    public boolean isVerificationExceptionThrown() {
        try {
            offer.verify();
            return false;
        } catch (VerificationException ignore) {
            return true;
        }
    }
}
