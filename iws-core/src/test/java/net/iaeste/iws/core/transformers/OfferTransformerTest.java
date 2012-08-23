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

import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.OfferTestUtility;
import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.api.utils.Copier;
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
    public void testCopingMinimalOfferToEntity() {
        final Offer offer = OfferTestUtility.getMinimalOffer();
        final OfferEntity entity = OfferTransformer.transform(offer);
        // TODO: check field by field
        Assert.assertThat(offer.getId(), is(entity.getId()));
        Assert.assertThat(offer.getTypeOfWork(), is(entity.getTypeOfWork()));

        Assert.assertThat(offer.getStudyLevels(), is(entity.getStudyLevels()));
        Assert.assertThat(offer.getSpecializations(), is(entity.getSpecializations()));
        Assert.assertThat(offer.getFieldOfStudies(), is(entity.getFieldOfStudies()));

    }

    @Test
    public void testCopingMinimalOfferToDto() {
        final OfferEntity entity = getMinimalOfferEntity();
        final Offer offer = OfferTransformer.transform(entity);
        // TODO: check field by field
        Assert.assertThat(offer.getId(), is(entity.getId()));
        Assert.assertThat(offer.getTypeOfWork(), is(entity.getTypeOfWork()));

        Assert.assertThat(offer.getStudyLevels(), is(entity.getStudyLevels()));
        Assert.assertThat(offer.getSpecializations(), is(Copier.copy(entity.getSpecializations()))); // Copier to get rid of null
        Assert.assertThat(offer.getFieldOfStudies(), is(entity.getFieldOfStudies()));
    }

    @Test
    public void testCopingBackAndForthFromDto() {
        final Offer offer = OfferTestUtility.getMinimalOffer();
        final OfferEntity entity = OfferTransformer.transform(offer);
        final Offer newOffer = OfferTransformer.transform(entity);
        // we rely on equals method
        Assert.assertThat(offer, is(newOffer));
    }

    @Test
    public void testCopingBackAndForthFromEntity() {
        final OfferEntity entity = getMinimalOfferEntity();
        final Offer offer = OfferTransformer.transform(entity);
        final OfferEntity newEntity = OfferTransformer.transform(offer);
        // we rely on equals method
        Assert.assertThat(entity, is(newEntity));
    }

    private OfferEntity getMinimalOfferEntity() {
        final OfferEntity minimalOffer = new OfferEntity();
        minimalOffer.setRefNo(OfferTestUtility.REF_NO);
        minimalOffer.setEmployerName(OfferTestUtility.EMPLOYER_NAME);
        final List<StudyLevel> list = new ArrayList<StudyLevel>(1);
        list.add(StudyLevel.E);
        final List<FieldOfStudy> fieldOfStudies = new ArrayList<FieldOfStudy>();
        fieldOfStudies.add(FieldOfStudy.IT);
        minimalOffer.setFieldOfStudies(fieldOfStudies);
        minimalOffer.setStudyLevels(list);
        minimalOffer.setGender(Gender.E);
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
}