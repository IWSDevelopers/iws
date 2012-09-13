/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.builders.BuildOffer
 * -----------------------------------------------------------------------------
 * product.setsoftware is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell product.setsoftware.
 *
 * product.setsoftware is provided "as is"); the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.fitnesse.builders;

import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.dtos.OfferTestUtility;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.LanguageOperator;
import net.iaeste.iws.api.enums.PaymentFrequency;
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.api.enums.TypeOfWork;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.common.utils.JSON;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class BuildOffer extends Builder<Offer> {
    private boolean valid;

    public void setDefaultOffer(final String offer) {
        switch (offer) {
            case "minimal":
                product = OfferTestUtility.getMinimalOffer();
                break;
            case "full":
                product = OfferTestUtility.getFullOffer();
                break;
            default:
                product = OfferTestUtility.getEmptyOffer();
                break;
        }
    }
    /**
     * Sets object in builder. Method discards all previous changes.
     *
     * @param json serialized Offer
     */
    public void setOffer(final String json) {
        product = JSON.deserialize(Offer.class, json);
    }

    @Override
    public void reset() {
        product = new Offer();
    }

    @Override
    public void execute() {
        try {
            product.verify();
            valid = true;
        } catch (VerificationException ignore) {
            valid = false;
        }
    }

    /**
     * Is offer a valid?
     *
     * @return true if offer in valid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * not null
     */
    public void setId(final Long id) {
        product.setId(id);
    }

    /**
     * not null
     */
    public void setRefNo(final String refNo) {
        product.setRefNo(refNo);
    }

    public void setNominationDeadline(final Date nominationDeadline) {
        product.setNominationDeadline(nominationDeadline);
    }

    /**
     * not null
     */
    public void setEmployerName(final String employerName) {
        product.setEmployerName(employerName);
    }

    public void setEmployerAddress(final String employerAddress) {
        product.setEmployerAddress(employerAddress);
    }

    public void setEmployerAddress2(final String employerAddress2) {
        product.setEmployerAddress2(employerAddress2);
    }

    public void setEmployerBusiness(final String employerBusiness) {
        product.setEmployerBusiness(employerBusiness);
    }

    public void setEmployerEmployeesCount(final Integer employerEmployeesCount) {
        product.setEmployerEmployeesCount(employerEmployeesCount);
    }

    public void setEmployerWebsite(final String employerWebsite) {
        product.setEmployerWebsite(employerWebsite);
    }

    /**
     * not null
     */
    public void setFieldOfStudies(final Set<FieldOfStudy> fieldOfStudies) {
        product.setFieldOfStudies(fieldOfStudies);
    }

    public void setSpecializations(final Set<String> specializations) {
        product.setSpecializations(specializations);
    }

    /**
     * not null
     */
    public void setStudyLevels(final Set<StudyLevel> studyLevels) {
        product.setStudyLevels(studyLevels);
    }

    public void setPrevTrainingRequired(final Boolean prevTrainingRequired) {
        product.setPrevTrainingRequired(prevTrainingRequired);
    }

    public void setOtherRequirements(final String otherRequirements) {
        product.setOtherRequirements(otherRequirements);
    }

    /**
     * not null
     */
    public void setLanguage1(final Language language1) {
        product.setLanguage1(language1);
    }

    /**
     * not null
     */
    public void setLanguage1Level(final LanguageLevel language1Level) {
        product.setLanguage1Level(language1Level);
    }

    public void setLanguage1Operator(final LanguageOperator language1Operator) {
        product.setLanguage1Operator(language1Operator);
    }

    public void setLanguage2(final Language language2) {
        product.setLanguage2(language2);
    }

    public void setLanguage2Level(final LanguageLevel language2Level) {
        product.setLanguage2Level(language2Level);
    }

    public void setLanguage2Operator(final LanguageOperator language2Operator) {
        product.setLanguage2Operator(language2Operator);
    }

    public void setLanguage3(final Language language3) {
        product.setLanguage3(language3);
    }

    public void setLanguage3Level(final LanguageLevel language3Level) {
        product.setLanguage3Level(language3Level);
    }

    /**
     * not null
     */
    public void setWorkDescription(final String workDescription) {
        product.setWorkDescription(workDescription);
    }

    public void setTypeOfWork(final TypeOfWork typeOfWork) {
        product.setTypeOfWork(typeOfWork);
    }

    /**
     * not null
     */
    public void setMinimumWeeks(final Integer minimumWeeks) {
        product.setMinimumWeeks(minimumWeeks);
    }

    /**
     * not null
     */
    public void setMaximumWeeks(final Integer maximumWeeks) {
        product.setMaximumWeeks(maximumWeeks);
    }

    /**
     * not null
     */
    public void setFromDate(final Date fromDate) {
        product.setFromDate(fromDate);
    }

    /**
     * not null
     */
    public void setToDate(final Date toDate) {
        product.setToDate(toDate);
    }

    public void setFromDate2(final Date fromDate2) {
        product.setFromDate2(fromDate2);
    }

    public void setToDate2(final Date toDate2) {
        product.setToDate2(toDate2);
    }

    public void setUnavailableFrom(final Date unavailableFrom) {
        product.setUnavailableFrom(unavailableFrom);
    }

    public void setUnavailableTo(final Date unavailableTo) {
        product.setUnavailableTo(unavailableTo);
    }

    public void setWorkingPlace(final String workingPlace) {
        product.setWorkingPlace(workingPlace);
    }

    public void setNearestAirport(final String nearestAirport) {
        product.setNearestAirport(nearestAirport);
    }

    public void setNearestPubTransport(final String nearestPubTransport) {
        product.setNearestPubTransport(nearestPubTransport);
    }

    /**
     * not null
     */
    public void setWeeklyHours(final Float weeklyHours) {
        product.setWeeklyHours(weeklyHours);
    }

    public void setDailyHours(final Float dailyHours) {
        product.setDailyHours(dailyHours);
    }

    public void setPayment(final BigDecimal payment) {
        product.setPayment(payment);
    }

    public void setCurrency(final Currency currency) {
        product.setCurrency(currency);
    }

    public void setPaymentFrequency(final PaymentFrequency paymentFrequency) {
        product.setPaymentFrequency(paymentFrequency);
    }

    public void setDeduction(final Integer deduction) {
        product.setDeduction(deduction);
    }

    public void setLodgingBy(final String lodgingBy) {
        product.setLodgingBy(lodgingBy);
    }

    public void setLodgingCost(final BigDecimal lodgingCost) {
        product.setLodgingCost(lodgingCost);
    }

    public void setLodgingCostFrequency(final PaymentFrequency lodgingCostFrequency) {
        product.setLodgingCostFrequency(lodgingCostFrequency);
    }

    public void setLivingCost(final BigDecimal livingCost) {
        product.setLivingCost(livingCost);
    }

    public void setLivingCostFrequency(final PaymentFrequency livingCostFrequency) {
        product.setLivingCostFrequency(livingCostFrequency);
    }

    public void setCanteen(final Boolean canteen) {
        product.setCanteen(canteen);
    }
}
