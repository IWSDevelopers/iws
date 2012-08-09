/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.data.OfferTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.api.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.Specialization;
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.api.exceptions.EntityIdentificationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class OfferTest {
    private static final String REF_NO = "AT-2012-1234-AB";
    private static final Date NOMINATION_DEADLINE = new Date();
    private static final String EMPLOYER_NAME = "Test_Employer_1";
    private static final String WORK_DESCRIPTION = "nothing";
    private static final Integer MAXIMUM_WEEKS = 12;
    private static final Integer MINIMUM_WEEKS = 12;
    private static final Float WEEKLY_HOURS = 40.0f;
    private static final Float DAILY_HOURS = 8.0f;
    private static final Date FROM_DATE = new Date();
    private static final Date TO_DATE = new Date(new Date().getTime() + 3600 * 24 * 90);
    private Offer offer;
    /**
     * field is used in methods for verifing dates, field is initialized in {@reference setUpDates} method
     */
    private static final Date[] d = new Date[9];
    private static final String[] validRefNos = { "IN-2011-0001-KU", "GB-2011-0001-01", "AT-2012-1234-AB", "GB-2011-0001" };

    private static final String[] invalidRefNos = { "GB-2011-00001", "UK-2011-00001", "INE-2011-0001-KU", "GB-2011-w001", "PL-201w-0001", "GB-2011-0001-101",
            "GB-10000-00001-01", "GB-2011-a000-01", "GB-20w1-0000-01", "U-2011-0000-01", "U9-2011-a000-01", "-2011-a000-01", "XX-2011-a000-01",
            "XX-2011-0000-01" };

    public static String[] getValidRefNos() {
        return validRefNos;
    }

    public static String[] getInvalidRefNos() {
        return invalidRefNos;
    }

    @Before
    public void before() {
        offer = getMinimalOffer();
    }

    public static Offer getEmptyOffer() {
        return new Offer();
    }

    public static Offer getMinimalOffer() {
        final Offer minimalOffer = new Offer();
        minimalOffer.setRefNo(REF_NO);
        minimalOffer.setNominationDeadline(NOMINATION_DEADLINE);
        minimalOffer.setEmployerName(EMPLOYER_NAME);
        final List<StudyLevel> list = new ArrayList<StudyLevel>(1);
        list.add(StudyLevel.E);
        minimalOffer.setStudyLevels(list);
        minimalOffer.setGender(Gender.E);
        minimalOffer.setLanguage1(Language.ENGLISH);
        minimalOffer.setLanguage1Level(LanguageLevel.E);
        minimalOffer.setWorkDescription(WORK_DESCRIPTION);
        minimalOffer.setMaximumWeeks(MAXIMUM_WEEKS);
        minimalOffer.setMinimumWeeks(MINIMUM_WEEKS);
        minimalOffer.setWeeklyHours(WEEKLY_HOURS);
        minimalOffer.setDailyHours(DAILY_HOURS);
        minimalOffer.setFromDate(d[1]);
        minimalOffer.setToDate(d[2]);
        return minimalOffer;
    }

    @Test
    public void testFallibleOffer() {
        offer = new Offer();
        offer.setId(1L);
        final String msg = "Id does not exists.";
        final EntityIdentificationException e = new EntityIdentificationException(msg);
        final Offer fallenOffer = new Offer(offer, e);
        Assert.assertThat(fallenOffer.isOk(), is(false));
        Assert.assertThat(fallenOffer.getMessage(), is(msg));
        Assert.assertThat(fallenOffer.getError(), is(e.getError()));
    }

    @Test
    public void testFallibleOfferOk() {
        offer = getMinimalOffer();
        Assert.assertThat(offer.isOk(), is(true));
        Assert.assertThat(offer.getMessage(), is(IWSConstants.SUCCESS));
        Assert.assertThat(offer.getError(), is(IWSErrors.SUCCESS));
        offer = getEmptyOffer();
        Assert.assertThat(offer.isOk(), is(true));
        Assert.assertThat(offer.getMessage(), is(IWSConstants.SUCCESS));
        Assert.assertThat(offer.getError(), is(IWSErrors.SUCCESS));
    }

    @Test
    public void testMinimalOffer() {
        Assert.assertNotNull("reference not null", offer);
        Assert.assertThat("RefNo", REF_NO, is(offer.getRefNo()));
        Assert.assertThat("NominationDeadline", NOMINATION_DEADLINE, is(offer.getNominationDeadline()));
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
        Assert.assertThat("DailyHours", DAILY_HOURS, is(offer.getDailyHours()));
    }

    @Test
    public void testMutableFields() {
        final Date nominationDeadline = offer.getNominationDeadline();
        nominationDeadline.setTime(new Date().getTime() + new Random().nextLong() + 1);
        Assert.assertThat(nominationDeadline, is(not(offer.getNominationDeadline())));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddingToUnmodificableFieldOfStudyList() {
        final List<FieldOfStudy> fieldOfStudies = offer.getFieldOfStudies();
        fieldOfStudies.add(FieldOfStudy.AERONAUTIC_ENGINEERING);
        Assert.assertThat(fieldOfStudies, is(not(offer.getFieldOfStudies())));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddingToUnmodificableSpecializationList() {
        final List<Specialization> specializations = offer.getSpecializations();
        specializations.add(Specialization.ASTROPHYSICS);
        Assert.assertThat(specializations, is(not(offer.getSpecializations())));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddingToUnmodificableStudyLevelList() {
        final List<StudyLevel> studyLevels = offer.getStudyLevels();
        studyLevels.add(StudyLevel.B);
        Assert.assertThat(studyLevels, is(not(offer.getStudyLevels())));
    }

    @Test
    public void testDatesImmutability() {
        final Date now = new Date();
        final Date oldDate = (Date) now.clone();
        offer.setHolidaysFrom(now);
        offer.setHolidaysTo(now);
        offer.setToDate(now);
        offer.setToDate2(now);
        offer.setFromDate(now);
        offer.setFromDate2(now);
        offer.setNominationDeadline(now);

        now.setTime(now.getTime() + 1 + new Random().nextLong());
        Assert.assertThat("HolidaysFrom", oldDate, is(offer.getHolidaysFrom()));
        Assert.assertThat("HolidaysTo", oldDate, is(offer.getHolidaysTo()));
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
        }
    }

    @Test
    public void testVerifyInvalidRefNo() {
        for (final String incorrectRefNo : getInvalidRefNos()) {
            offer.setRefNo(incorrectRefNo);
            Assert.assertThat(String.format("%s should be incorrect", incorrectRefNo), offer.verifyRefNo(), is(false));
        }
    }

    @Before
    public void setUpDates() {
        final long now = new Date().getTime();
        for (int i = 0; i < d.length; ++i) {
            d[i] = new Date(now + i * 3600 * 24);
        }

    }

    /**
     * @todo refactor
     */
    @Test
    public void testVerifyPresenceOfDates() {
        final String error = "if 'from(2)' is present then 'to(2)' should be present";
        // --deadline--------------------------to-------------------------------------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(d[0]);
        offer.setFromDate(null);
        offer.setToDate(d[2]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline--------------------------to2------------------------------------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(d[0]);
        offer.setToDate2(d[2]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline--------------------------from------------------------------------>
        offer = getMinimalOffer();
        offer.setNominationDeadline(d[0]);
        offer.setFromDate(d[2]);
        offer.setToDate(null);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline--------------------------from2----------------------------------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(d[0]);
        offer.setFromDate2(d[2]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline------------------from2---------------to-------------------------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(d[0]);
        offer.setFromDate2(d[1]);
        offer.setToDate(d[2]);
        offer.setToDate2(null);
        offer.setFromDate(null);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline---------------from------------------to2-------------------------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(d[0]);
        offer.setFromDate(d[1]);
        offer.setToDate2(d[2]);
        offer.setToDate(null);
        offer.setFromDate2(null);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline------------from----------------------to--------------to2--------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(d[0]);
        offer.setFromDate(d[1]);
        offer.setToDate(d[2]);
        offer.setToDate2(d[3]);
        offer.setFromDate(null);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline------------from----------------------to--------------from2------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(d[0]);
        offer.setFromDate(d[1]);
        offer.setToDate(d[2]);
        offer.setFromDate2(d[3]);
        offer.setToDate2(null);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline------------from-----------------------from2----------to2--------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(d[0]);
        offer.setFromDate(d[1]);
        offer.setFromDate2(d[2]);
        offer.setToDate2(d[3]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline--------------to-----------------------from2----------to2--------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(d[0]);
        offer.setToDate(d[1]);
        offer.setFromDate2(d[2]);
        offer.setToDate2(d[3]);
        offer.setFromDate(null);
        Assert.assertThat(error, offer.verifyDates(), is(false));
    }

    @Test
    public void testVerifyOrderOfDates() {
        final String error = "'from(2)' have to be before 'to(2)'";
        // --deadline--------------------------to-------from--------------------------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(d[0]);
        offer.setToDate(d[1]);
        offer.setFromDate(d[2]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline--------------------------to2-------from2------------------------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(d[0]);
        offer.setToDate2(d[1]);
        offer.setFromDate2(d[2]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
    }

    /**
     * @todo is presence of deadline required?
     */
    @Test
    public void testVerifyPresenceOfDeadlineDate() {
        final String error = "'nominationDeadline' presence";
        offer = getMinimalOffer();
        offer.setHolidaysFrom(null);
        offer.setHolidaysTo(null);
        offer.setFromDate(d[1]);
        offer.setToDate(d[2]);
        // --deadline--------------------------from-------to--------------------------->
        offer.setNominationDeadline(d[0]);
        Assert.assertThat(error, offer.verifyDates(), is(true));
        // ------------------------------------from-------to--------------------------->
        offer.setNominationDeadline(null);
        Assert.assertThat(error, offer.verifyDates(), is(false));

    }

    @Test
    public void testVerifyDeadlineOrder() {
        final String error = "'nominationDeadline' should be before 'from' and 'from2'";
        offer = getMinimalOffer();
        offer.setFromDate(d[1]);
        offer.setToDate(d[3]);
        // ----from--------------deadline-------------------to------------------------->
        offer.setNominationDeadline(d[2]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // ---from-----------------------to-------------deadline----------------------->
        offer.setNominationDeadline(d[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));

        offer = getMinimalOffer();
        offer.setFromDate2(d[1]);
        offer.setToDate2(d[3]);
        // ---from2--------------deadline-------------------to2------------------------>
        offer.setNominationDeadline(d[2]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // ---from2-----------------------to2--------deadline-------------------------->
        offer.setNominationDeadline(d[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));

        // ---from2-----------------------to2--------deadline--------from------to------>
        offer = getMinimalOffer();
        offer.setFromDate2(d[1]);
        offer.setToDate2(d[3]);
        offer.setFromDate(d[5]);
        offer.setToDate(d[7]);
        offer.setNominationDeadline(d[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // ---from-----------------------to---------deadline-------from2------to2------>
        offer = getMinimalOffer();
        offer.setNominationDeadline(d[0]);
        offer.setFromDate(d[1]);
        offer.setToDate(d[3]);
        offer.setFromDate2(d[5]);
        offer.setToDate2(d[7]);
        offer.setNominationDeadline(d[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
    }

    @Test
    public void testVerifyOrderOfDatesGroups() {
        final String error = "dates from groups 1 and 2 cannot intertwine";
        offer = getMinimalOffer();
        offer.setNominationDeadline(d[0]);
        // --deadline------------from2-----from------------to--------------to2--------->
        offer.setFromDate2(d[1]);
        offer.setFromDate(d[2]);
        offer.setToDate(d[3]);
        offer.setToDate2(d[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline------------from-----from2------------to--------------to2--------->
        offer.setFromDate(d[1]);
        offer.setFromDate2(d[2]);
        offer.setToDate(d[3]);
        offer.setToDate2(d[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline------------from-----from2------------to2--------------to--------->
        offer.setFromDate(d[1]);
        offer.setFromDate2(d[2]);
        offer.setToDate2(d[3]);
        offer.setToDate(d[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline------------from2-----from------------to--------------to2--------->
        offer.setFromDate2(d[1]);
        offer.setFromDate(d[2]);
        offer.setToDate(d[3]);
        offer.setToDate2(d[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
    }

    @Test
    public void testVerifyOrderOfHolidaysDates() {
        final String error = "holidays dates must be inside from-to or from2-to2";
        offer = getMinimalOffer();
        offer.setNominationDeadline(d[0]);
        // --deadline-----------from--------holidaysFrom---to------holidaysTo---------->
        offer.setFromDate(d[1]);
        offer.setHolidaysFrom(d[2]);
        offer.setToDate(d[3]);
        offer.setHolidaysTo(d[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline--------holidaysFrom-----------from------holidaysTo---to---------->
        offer.setHolidaysFrom(d[1]);
        offer.setFromDate(d[2]);
        offer.setHolidaysTo(d[3]);
        offer.setToDate(d[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline-----------holidaysFrom------holidaysTo------from--------to------->
        offer.setHolidaysFrom(d[1]);
        offer.setHolidaysTo(d[2]);
        offer.setFromDate(d[3]);
        offer.setToDate(d[4]);
        Assert.assertThat(error, offer.verifyDates(), is(false));
        // --deadline-----------from--------to------holidaysTo-----holidaysFrom-------->
        offer.setFromDate(d[1]);
        offer.setToDate(d[2]);
        offer.setHolidaysFrom(d[3]);
        offer.setHolidaysTo(d[4]);
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
    public void testGetValidOfferShouldBeValid() {
        offer = getMinimalOffer();
        // "valid offer from helper should be valid"
        offer.verify();
    }

}
