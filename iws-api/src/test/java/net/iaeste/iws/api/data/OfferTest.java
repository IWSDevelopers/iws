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

import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.Specialization;
import net.iaeste.iws.api.enums.StudyLevel;
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
    private static final Float WEEKLY_HOURS = 40f;
    private static final Float DAILY_HOURS = 8f;
    private Offer offer;

    @Before
    public void before() {
        offer = getMinimalOffer();
    }

    private Offer getMinimalOffer() {
        final Offer offer = new Offer();
        offer.setRefNo(REF_NO);
        offer.setNominationDeadline(NOMINATION_DEADLINE);
        offer.setEmployerName(EMPLOYER_NAME);
        final List<StudyLevel> list = new ArrayList<StudyLevel>() {{
            add(StudyLevel.E);
        }};
        offer.setStudyLevels(list);
        offer.setGender(Gender.E);
        offer.setLanguage1(Language.ENGLISH);
        offer.setLanguage1Level(LanguageLevel.E);
        offer.setWorkDescription(WORK_DESCRIPTION);
        offer.setMaximumWeeks(MAXIMUM_WEEKS);
        offer.setMinimumWeeks(MINIMUM_WEEKS);
        offer.setWeeklyHours(WEEKLY_HOURS);
        offer.setDailyHours(DAILY_HOURS);
        return offer;
    }

    @Test
    public void testMinimalOffer() {
        Assert.assertNotNull("reference not null", offer);
        Assert.assertEquals("RefNo", REF_NO, offer.getRefNo());
        Assert.assertEquals("NominationDeadline", NOMINATION_DEADLINE, offer.getNominationDeadline());
        Assert.assertEquals("EmployerName", EMPLOYER_NAME, offer.getEmployerName());
        Assert.assertEquals("size of Study Levels collection should be 1", 1, offer.getStudyLevels().size());
        Assert.assertEquals("first Study Level should be E", StudyLevel.E, offer.getStudyLevels().get(0));
        Assert.assertEquals("Gender", Gender.E, offer.getGender());
        Assert.assertEquals("Language", Language.ENGLISH, offer.getLanguage1());
        Assert.assertEquals("LanguageLevel", LanguageLevel.E, offer.getLanguage1Level());
        Assert.assertEquals("WorkDescription", WORK_DESCRIPTION, offer.getWorkDescription());
        Assert.assertEquals("MaximumWeeks", MAXIMUM_WEEKS, offer.getMaximumWeeks());
        Assert.assertEquals("MinimumWeeks", MINIMUM_WEEKS, offer.getMinimumWeeks());
        Assert.assertEquals("WeeklyHours", WEEKLY_HOURS, offer.getWeeklyHours());
        Assert.assertEquals("DailyHours", DAILY_HOURS, offer.getDailyHours());
    }

    @Test
    public void testMutableFields() {
        final Date nominationDeadline = offer.getNominationDeadline();
        nominationDeadline.setTime(new Date().getTime() + new Random().nextLong() + 1);
        Assert.assertTrue(!nominationDeadline.equals(offer.getNominationDeadline()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddingToUnmodificableFieldOfStudyList() {
        final List<FieldOfStudy> fieldOfStudies = offer.getFieldOfStudies();
        fieldOfStudies.add(FieldOfStudy.AERONAUTIC_ENGINEERING);
        Assert.assertTrue(!fieldOfStudies.equals(offer.getFieldOfStudies()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddingToUnmodificableSpecializationList() {
        final List<Specialization> specializations = offer.getSpecializations();
        specializations.add(Specialization.ASTROPHYSICS);
        Assert.assertTrue(!specializations.equals(offer.getSpecializations()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddingToUnmodificableStudyLevelList() {
        final List<StudyLevel> studyLevels = offer.getStudyLevels();
        studyLevels.add(StudyLevel.B);
        Assert.assertTrue(!studyLevels.equals(offer.getStudyLevels()));
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
        Assert.assertEquals("HolidaysFrom", oldDate, offer.getHolidaysFrom());
        Assert.assertEquals("HolidaysTo", oldDate, offer.getHolidaysTo());
        Assert.assertEquals("ToDate", oldDate, offer.getToDate());
        Assert.assertEquals("ToDate2", oldDate, offer.getToDate2());
        Assert.assertEquals("FromDate", oldDate, offer.getFromDate());
        Assert.assertEquals("FromDate2", oldDate, offer.getFromDate2());
        Assert.assertEquals("NominationDeadline", oldDate, offer.getNominationDeadline());
    }

//    TODO: which fields are not important for the equality of an offer?
//    @Test
//    public void testEqualityOfSimilarOffers() {
//        Offer offer2 = new Offer(offer);
//        offer2.setWorkDescription("@#$#@");
//        Assert.assertEquals(offer, offer2);
//    }
}
