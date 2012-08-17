/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.OfferTestUtility
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

import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.StudyLevel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class OfferTestUtility {
    public static final String REF_NO = "AT-2012-1234-AB";
    public static final Date NOMINATION_DEADLINE = new Date();
    public static final String EMPLOYER_NAME = "Test_Employer_1";
    public static final String WORK_DESCRIPTION = "nothing";
    public static final Integer MAXIMUM_WEEKS = 12;
    public static final Integer MINIMUM_WEEKS = 12;
    public static final Float WEEKLY_HOURS = 40.0f;
    public static final Date FROM_DATE = new Date();
    public static final Date TO_DATE = new Date(new Date().getTime() + 3600 * 24 * 90);
    public static final BigDecimal PAYMENT = new BigDecimal(3000);
    public static final BigDecimal LODGING_COST = new BigDecimal(1000);
    public static final BigDecimal LIVING_COST = new BigDecimal(2000);

    private OfferTestUtility() {
    }

    public static Offer getEmptyOffer() {
        return new Offer();
    }

    public static Offer getMinimalOffer() {
        final Offer minimalOffer = new Offer();
        minimalOffer.setRefNo(REF_NO);
        minimalOffer.setEmployerName(EMPLOYER_NAME);
        final List<StudyLevel> list = new ArrayList<StudyLevel>(1);
        list.add(StudyLevel.E);
        final List<FieldOfStudy> fieldOfStudies = new ArrayList<FieldOfStudy>();
        fieldOfStudies.add(FieldOfStudy.IT);
        minimalOffer.setFieldOfStudies(fieldOfStudies);
        minimalOffer.setStudyLevels(list);
        minimalOffer.setGender(Gender.E);
        minimalOffer.setLanguage1(Language.ENGLISH);
        minimalOffer.setLanguage1Level(LanguageLevel.E);
        minimalOffer.setWorkDescription(WORK_DESCRIPTION);
        minimalOffer.setMaximumWeeks(MAXIMUM_WEEKS);
        minimalOffer.setMinimumWeeks(MINIMUM_WEEKS);
        minimalOffer.setWeeklyHours(WEEKLY_HOURS);
        minimalOffer.setFromDate(FROM_DATE);
        minimalOffer.setToDate(TO_DATE);
        return minimalOffer;
    }
}
