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

import net.iaeste.iws.api.constants.IWSExchangeConstants;
import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.Specialization;
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.util.Copier;
import net.iaeste.iws.api.util.Date;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.iaeste.iws.api.dtos.OfferTestUtility.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertThat;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class OfferTest {

    private Offer offer = getMinimalOffer();
    /** field is used in methods for verifing dates, field is initialized in {@link #setUpDates} method */
    private static final Date[] dates = new Date[10];
    private static final String[] validRefNos = { "IN-2011-0001-KU", "GB-2011-0001-01", "AT-2012-1234-AB", "GB-2011-0001" };

    private static final String[] invalidRefNos = { "GB-2011-00001", "UK-2011-00001", "INE-2011-0001-KU", "GB-2011-w001", "PL-201w-0001", "GB-2011-0001-101",
            "GB-10000-00001-01", "GB-2011-a000-01", "GB-20w1-0000-01", "U-2011-0000-01", "U9-2011-a000-01", "-2011-a000-01" };
    static final String ERRMSG_NOT_NULL = " field cannot be null";
    static final String ERRMSG_PRESENCE = "if 'from(2)' is present then 'to(2)' should be present";
    private static final String ERRMSG_LENGTH = " incorrect length";

    private static String[] getValidRefNos() {
        return Copier.copy(validRefNos);
    }

    private static String[] getInvalidRefNos() {
        return Copier.copy(invalidRefNos);
    }

    private void setUpDates() {
        final Date now = new Date();

        for (int i = 1; i < dates.length; ++i) {
            dates[i] = now.plusDays(i);
        }
    }

    @Before
    public void before() {
        setUpDates();
        offer = getMinimalOffer();
    }

    @Test
    public void testCopyConstructor() {
        final Offer offerToCopy = getMinimalOffer();
        final Offer copiedOffer = new Offer(offerToCopy);
        assertThat(offerToCopy, is(not(nullValue())));
        assertThat(copiedOffer, is(not(nullValue())));
        assertThat(offerToCopy, is(copiedOffer));
    }

    @Test
    public void testMinimalOffer() {
        Assert.assertNotNull("reference not null", offer);
        assertThat("RefNo", REF_NO, is(offer.getRefNo()));
        assertThat("EmployerName", EMPLOYER_NAME, is(offer.getEmployerName()));
        assertThat("size of Study Levels collection should be 2", STUDY_LEVELS.size(), is(offer.getStudyLevels().size()));
        assertThat("Study Levels should contain E",
                STUDY_LEVELS.contains(StudyLevel.E) && offer.getStudyLevels().contains(StudyLevel.E), is(true));
        assertThat("Language", Language.ENGLISH, is(offer.getLanguage1()));
        assertThat("LanguageLevel", LanguageLevel.E, is(offer.getLanguage1Level()));
        assertThat("WorkDescription", WORK_DESCRIPTION, is(offer.getWorkDescription()));
        assertThat("MaximumWeeks", MAXIMUM_WEEKS, is(offer.getMaximumWeeks()));
        assertThat("MinimumWeeks", MINIMUM_WEEKS, is(offer.getMinimumWeeks()));
        assertThat("WeeklyHours", WEEKLY_HOURS, is(offer.getWeeklyHours()));
        assertThat("fromDate", FROM_DATE, is(offer.getFromDate()));
        assertThat("toDate", TO_DATE, is(offer.getToDate()));
    }

    @Test
    public void testAddingToFieldOfStudyList() {
        final Set<FieldOfStudy> fieldOfStudies = offer.getFieldOfStudies();
        final Set<FieldOfStudy> primaryFieldOfStudies = offer.getFieldOfStudies();
        fieldOfStudies.add(FieldOfStudy.AERONAUTIC_ENGINEERING);
        assertThat(fieldOfStudies, is(not(offer.getFieldOfStudies())));
        assertThat(primaryFieldOfStudies, is(offer.getFieldOfStudies()));
    }

    @Test
    public void testAddingToSpecializationList() {
        final Set<String> specializations = offer.getSpecializations();
        final Set<String> primarySpecializations = offer.getSpecializations();
        specializations.add(Specialization.ASTROPHYSICS.toString());
        assertThat(specializations, is(not(offer.getSpecializations())));
        assertThat(primarySpecializations, is(offer.getSpecializations()));
    }

    @Test
    public void testAddingToStudyLevelList() {
        final Set<StudyLevel> studyLevels = offer.getStudyLevels();
        final Set<StudyLevel> primaryStudyLevels = offer.getStudyLevels();
        studyLevels.add(StudyLevel.B);
        assertThat(studyLevels, is(not(offer.getStudyLevels())));
        assertThat(primaryStudyLevels, is(offer.getStudyLevels()));
    }

    @Test
    public void testVerifyValidRefNo() {
        for (final String correctRefNo : getValidRefNos()) {
            offer.setRefNo(correctRefNo);
            assertThat(String.format("%s should be correct", correctRefNo), offer.validate().isEmpty(), Matchers.is(true));
            assertThat(isVerificationExceptionThrown(), is(false));
        }
    }

    @Test
    public void testVerifyInvalidRefNo() {
        for (final String incorrectRefNo : getInvalidRefNos()) {
            offer.setRefNo(incorrectRefNo);
            assertThat(String.format("%s should be incorrect", incorrectRefNo), offer.validate(), hasKey("refNo"));
            assertThat(isVerificationExceptionThrown(), is(true));
        }
    }

    @Test
    public void testVerifyPresenceOfDatesNoFrom() {
        // --deadline--------------------------to-------------------------------------->
        offer.setNominationDeadline(dates[0]);
        offer.setFromDate(null);
        offer.setToDate(dates[2]);
        assertThat(ERRMSG_PRESENCE, offer.validate(), hasKey("fromDate"));
        assertThat(isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testVerifyPresenceOfDatesNoFrom2() {
        // --deadline--------------------------to2------------------------------------->
        offer.setNominationDeadline(dates[0]);
        offer.setToDate2(dates[2]);
        offer.setFromDate2(null);
        assertThat(ERRMSG_PRESENCE, offer.validate(), hasKey("fromDate2"));
        assertThat(isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testVerifyPresenceOfDatesNoTo() {
        // --deadline--------------------------from------------------------------------>
        offer.setNominationDeadline(dates[0]);
        offer.setFromDate(dates[2]);
        offer.setToDate(null);
        assertThat(ERRMSG_PRESENCE, offer.validate(), hasKey("toDate"));
        assertThat(isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testVerifyPresenceOfDatesNoTo2() {
        // --deadline--------------------------from2----------------------------------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setFromDate2(dates[2]);
        assertThat(ERRMSG_PRESENCE, offer.validate(), hasKey("toDate2"));
        assertThat(isVerificationExceptionThrown(), is(true));
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
        assertThat(ERRMSG_PRESENCE, offer.validate(), hasKey("toDate2"));
        assertThat(ERRMSG_PRESENCE, offer.validate(), hasKey("fromDate"));
        assertThat(isVerificationExceptionThrown(), is(true));
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
        assertThat(ERRMSG_PRESENCE, offer.validate(), hasKey("toDate"));
        assertThat(ERRMSG_PRESENCE, offer.validate(), hasKey("fromDate2"));
        assertThat(isVerificationExceptionThrown(), is(true));
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
        assertThat(ERRMSG_PRESENCE, offer.validate(), hasKey("fromDate2"));
        assertThat(isVerificationExceptionThrown(), is(true));
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
        assertThat(ERRMSG_PRESENCE, offer.validate(), hasKey("toDate2"));
        assertThat(isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testVerifyPresenceOfDates5() {
        // --deadline------------from-----------------------from2----------to2--------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setFromDate(dates[1]);
        offer.setToDate(null);
        offer.setFromDate2(dates[2]);
        offer.setToDate2(dates[3]);
        assertThat(ERRMSG_PRESENCE, offer.validate(), hasKey("toDate"));
        assertThat(isVerificationExceptionThrown(), is(true));
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
        assertThat(ERRMSG_PRESENCE, offer.validate(), hasKey("fromDate"));
        assertThat(isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testVerifyOrderOfDates() {
        final String error = "'from(2)' have to be before 'to(2)'";
        // --deadline--------------------------to-------from--------------------------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setToDate(dates[1]);
        offer.setFromDate(dates[2]);
        assertThat(error, offer.validate(), hasKey("fromDate"));
        // --deadline--------------------------to2-------from2------------------------->
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setToDate2(dates[1]);
        offer.setFromDate2(dates[2]);
        assertThat(error, offer.validate(), hasKey("fromDate"));
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
        assertThat(error, offer.validate(), not(hasKey("nominationDeadline")));
        // ------------------------------------from-------to--------------------------->
        offer.setNominationDeadline(null);
        assertThat(error, offer.validate(), not(hasKey("nominationDeadline")));

    }

    @Test
    public void testVerifyDeadlineOrder() {
        final String error = "'nominationDeadline' should be before 'from' and 'from2'";
        offer = getMinimalOffer();
        offer.setFromDate(dates[1]);
        offer.setToDate(dates[3]);
        // ----from--------------deadline-------------------to------------------------->
        offer.setNominationDeadline(dates[2]);
        assertThat(error, offer.validate(), hasKey("nominationDeadline"));
        // ---from-----------------------to-------------deadline----------------------->
        offer.setNominationDeadline(dates[4]);
        assertThat(error, offer.validate(), hasKey("nominationDeadline"));

        offer = getMinimalOffer();
        offer.setFromDate2(dates[1]);
        offer.setToDate2(dates[3]);
        // ---from2--------------deadline-------------------to2------------------------>
        offer.setNominationDeadline(dates[2]);
        assertThat(error, offer.validate(), hasKey("nominationDeadline"));
        // ---from2-----------------------to2--------deadline-------------------------->
        offer.setNominationDeadline(dates[4]);
        assertThat(error, offer.validate(), hasKey("nominationDeadline"));

        // ---from2-----------------------to2--------deadline--------from------to------>
        offer = getMinimalOffer();
        offer.setFromDate2(dates[1]);
        offer.setToDate2(dates[3]);
        offer.setFromDate(dates[5]);
        offer.setToDate(dates[7]);
        offer.setNominationDeadline(dates[4]);
        assertThat(error, offer.validate(), hasKey("nominationDeadline"));
        // ---from-----------------------to---------deadline-------from2------to2------>
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setFromDate(dates[1]);
        offer.setToDate(dates[3]);
        offer.setFromDate2(dates[5]);
        offer.setToDate2(dates[7]);
        offer.setNominationDeadline(dates[4]);
        assertThat(error, offer.validate(), hasKey("nominationDeadline"));
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
        assertThat(error, offer.validate(), hasKey("fromDate2"));
        assertThat(error, offer.validate(), hasKey("fromDate"));
        // --deadline------------from-----from2------------to--------------to2--------->
        offer.setFromDate(dates[1]);
        offer.setFromDate2(dates[2]);
        offer.setToDate(dates[3]);
        offer.setToDate2(dates[4]);
        assertThat(error, offer.validate(), hasKey("fromDate2"));
        assertThat(error, offer.validate(), hasKey("fromDate"));
        // --deadline------------from-----from2------------to2--------------to--------->
        offer.setFromDate(dates[1]);
        offer.setFromDate2(dates[2]);
        offer.setToDate2(dates[3]);
        offer.setToDate(dates[4]);
        assertThat(error, offer.validate(), hasKey("fromDate2"));
        assertThat(error, offer.validate(), hasKey("fromDate"));
        // --deadline------------from2-----from------------to--------------to2--------->
        offer.setFromDate2(dates[1]);
        offer.setFromDate(dates[2]);
        offer.setToDate(dates[3]);
        offer.setToDate2(dates[4]);
        assertThat(error, offer.validate(), hasKey("fromDate2"));
        assertThat(error, offer.validate(), hasKey("fromDate"));
    }

    @Test
    public void testVerifyOrderOfUnavailableDatesValid() {
        offer = getMinimalOffer();
        offer.setNominationDeadline(dates[0]);
        offer.setFromDate(dates[2]);
        offer.setToDate(dates[4]);

        // --deadline---unavailbleFrom----from---unavailbleTo------to------------------>
        offer.setUnavailableFrom(dates[1]);
        offer.setUnavailableTo(dates[3]);
        assertThat(offer.validate(), hasKey("unavailableFrom"));
        // --deadline---unavailbleFrom----from---------to----unavailbleTo-------------->
        offer.setUnavailableFrom(dates[1]);
        offer.setUnavailableTo(dates[5]);
        assertThat(offer.validate(), hasKey("unavailableFrom"));
        // --deadline---from--unavailbleFrom-----------to----unavailbleTo-------------->
        offer.setUnavailableFrom(dates[3]);
        offer.setUnavailableTo(dates[5]);
        assertThat(offer.validate(), hasKey("unavailableFrom"));

        offer.setFromDate2(dates[6]);
        offer.setToDate2(dates[8]);
        // --deadline---from---to---unavailbleFrom----from2---unavailbleTo----to2------>
        offer.setUnavailableFrom(dates[5]);
        offer.setUnavailableTo(dates[7]);
        assertThat(offer.validate(), hasKey("unavailableFrom"));
        // --deadline---from---to---unavailbleFrom----from2----to2---unavailbleTo------>
        offer.setUnavailableFrom(dates[5]);
        offer.setUnavailableTo(dates[9]);
        assertThat(offer.validate(), hasKey("unavailableFrom"));
        // --deadline---from---to-------from2--unavailbleFrom---to2----unavailbleTo---->
        offer.setUnavailableFrom(dates[7]);
        offer.setUnavailableTo(dates[9]);
        assertThat(offer.validate(), hasKey("unavailableFrom"));

    }

    @Test
    public void testVerifyNumberOfWeeks() {
        offer.setMinimumWeeks(0);
        offer.setMaximumWeeks(10);
        assertThat(String.format("minimumWeeks should be greater or equal to %s", IWSExchangeConstants.MIN_OFFER_MINIMUM_WEEKS),
                offer.validate(), hasKey("minimumWeeks"));
        offer.setMinimumWeeks(20);
        offer.setMaximumWeeks(-2);
        assertThat("maximumWeeks should be greater than or equal to minimumWeeks", offer.validate(), hasKey("maximumWeeks"));
        offer.setMinimumWeeks(6);
        offer.setMaximumWeeks(-10);
        assertThat("maximumWeeks should be greater than or equal to minimumWeeks", offer.validate(), hasKey("maximumWeeks"));
        offer.setMinimumWeeks(10);
        offer.setMaximumWeeks(10);
        assertThat("minimumWeeks can be equal to maximumWeeks", offer.validate(), not(hasKey("minimumWeeks")));
        assertThat("minimumWeeks can be equal to maximumWeeks", offer.validate(), not(hasKey("maximumWeeks")));
        offer.setMinimumWeeks(10);
        offer.setMaximumWeeks(12);
        assertThat(isVerificationExceptionThrown(), is(false));
        assertThat("minimumWeeks can be equal to maximumWeeks", offer.validate(), not(hasKey("minimumWeeks")));
        assertThat("minimumWeeks can be equal to maximumWeeks", offer.validate(), not(hasKey("maximumWeeks")));
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
        assertThat(String.format("refNo%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testNotNullableEmployerName() {
        offer = getMinimalOffer();
        offer.setEmployerName(null);
        assertThat(String.format("employerName%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableFieldOfStudies() {
        offer = getMinimalOffer();
        offer.setFieldOfStudies(null);
        assertThat(String.format("fieldOfStudies%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));
    }

    @Test(expected = VerificationException.class)
    public void testSizeOfFieldOfStudies() {
        offer = getMinimalOffer();
        final List<FieldOfStudy> fieldOfStudiesList = new ArrayList<>(
                EnumSet.allOf(FieldOfStudy.class)).subList(0, IWSExchangeConstants.MAX_OFFER_FIELDS_OF_STUDY + 1);

        offer.setFieldOfStudies(EnumSet.copyOf(fieldOfStudiesList));
        offer.verify();
    }

    @Test(expected = VerificationException.class)
    public void testSizeOfSpecializations() {
        offer = getMinimalOffer();
        final List<Specialization> specializationList = new ArrayList<>(
                EnumSet.allOf(Specialization.class)).subList(0, IWSExchangeConstants.MAX_OFFER_SPECIALIZATIONS + 1);
        final Set<String> specializations = new HashSet<>();
        for (final Specialization specialization : specializationList) {
            specializations.add(specialization.toString());
        }

        offer.setSpecializations(specializations);

        offer.verify();
    }

    @Test
    public void testNotNullableStudyLevels() {
        offer = getMinimalOffer();
        offer.setStudyLevels(null);
        assertThat(String.format("studyLevels%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testNotEmptyStudyLevels() {
        offer = getMinimalOffer();
        offer.setStudyLevels(EnumSet.noneOf(StudyLevel.class));
        assertThat("studyLevels list cannot be empty", isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableLanguage1() {
        offer = getMinimalOffer();
        offer.setLanguage1(null);
        assertThat(String.format("language1%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableLanguage1Level() {
        offer = getMinimalOffer();
        offer.setLanguage1Level(null);
        assertThat(String.format("language1Level%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableWorkDescription() {
        offer = getMinimalOffer();
        offer.setWorkDescription(null);
        assertThat(String.format("workDescription%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testLengthOfWorkDescription() {
        offer = getMinimalOffer();
        final StringBuilder sb = new StringBuilder(IWSExchangeConstants.MAX_OFFER_WORK_DESCRIPTION_SIZE + 1);
        for (int i = 0; i < IWSExchangeConstants.MAX_OFFER_WORK_DESCRIPTION_SIZE + 1; i++) {
            sb.append('1');
        }
        offer.setWorkDescription(sb.toString());
        assertThat(String.format("workDescription%s", ERRMSG_LENGTH), isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testNullableOtherRequirements() {
        offer = getFullOffer();
        offer.setOtherRequirements(null);
        // allow to save offers with no other requirements specified
        assertThat(isVerificationExceptionThrown(), is(false));
    }

    @Test
    public void testLengthOfOtherRequirements() {
        offer = getMinimalOffer();
        final StringBuilder sb = new StringBuilder(IWSExchangeConstants.MAX_OFFER_OTHER_REQUIREMENTS_SIZE + 1);
        for (int i = 0; i < IWSExchangeConstants.MAX_OFFER_OTHER_REQUIREMENTS_SIZE + 1; i++) {
            sb.append('1');
        }
        offer.setOtherRequirements(sb.toString());
        assertThat(String.format("otherRequirements%s", ERRMSG_LENGTH), isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testNotNullableMaximumWeeks() {
        offer = getMinimalOffer();
        offer.setMaximumWeeks(null);
        assertThat(String.format("maximumWeeks%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableMinimumWeeks() {
        offer = getMinimalOffer();
        offer.setMinimumWeeks(null);
        assertThat(String.format("minimumWeeks%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableWeeklyHours() {
        offer = getMinimalOffer();
        offer.setWeeklyHours(null);
        assertThat(String.format("weeklyHours%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableFromDate() {
        offer = getMinimalOffer();
        offer.setFromDate(null);
        assertThat(String.format("fromDate%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

    }

    @Test
    public void testNotNullableToDate() {
        offer = getMinimalOffer();
        offer.setToDate(null);
        assertThat(String.format("toDate%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));

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
