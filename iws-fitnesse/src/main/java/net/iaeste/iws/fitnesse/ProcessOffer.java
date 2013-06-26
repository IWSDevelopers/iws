/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.ProcessOffer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.*;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.client.ExchangeClient;
import net.iaeste.iws.core.transformers.CollectionTransformer;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

import java.math.BigDecimal;
import java.util.HashSet;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class ProcessOffer extends AbstractFixture<OfferResponse> {

    private final Exchange exchange = new ExchangeClient();
    private Offer offer = new Offer();
    private ProcessOfferRequest request = null;

    public void setRefNo(final String refNo) {
        offer.setRefNo(refNo);
    }

    public void setNominationDeadline(final String nominationDeadline) {
        offer.setNominationDeadline(new Date(nominationDeadline));
    }

    public void setEmployerName(final String employerName) {
        offer.setEmployerName(employerName);
    }

    public void setEmployerAddress(final String employerAddress) {
        offer.setEmployerAddress(employerAddress);
    }

    public void setEmployerAddress2(final String employerAddress2) {
        offer.setEmployerAddress2(employerAddress2);
    }

    public void setEmployerBusiness(final String employerBusiness) {
        offer.setEmployerBusiness(employerBusiness);
    }

    public void setEmployerEmployeesCount(final Integer employerEmployeesCount) {
        offer.setEmployerEmployeesCount(employerEmployeesCount);
    }

    public void setEmployerWebsite(final String employerWebsite) {
        offer.setEmployerWebsite(employerWebsite);
    }

    public void setFieldOfStudies(final String fieldOfStudies) {
        offer.setFieldOfStudies(CollectionTransformer.explodeEnumSet(FieldOfStudy.class, fieldOfStudies));
    }

    public void setSpecializations(final String specializations) {
        offer.setSpecializations(new HashSet<>(CollectionTransformer.explodeStringList(specializations)));
    }

    public void setStudyLevels(final String studyLevels) {
        offer.setStudyLevels(CollectionTransformer.explodeEnumSet(StudyLevel.class, studyLevels));
    }

    public void setPrevTrainingRequired(final Boolean prevTrainingRequired) {
        offer.setPrevTrainingRequired(prevTrainingRequired);
    }

    public void setOtherRequirements(final String otherRequirements) {
        offer.setOtherRequirements(otherRequirements);
    }

    public void setLanguage1(final Language language1) {
        offer.setLanguage1(language1);
    }

    public void setLanguage1Level(final LanguageLevel language1Level) {
        offer.setLanguage1Level(language1Level);
    }

    public void setLanguage1Operator(final LanguageOperator language1Operator) {
        offer.setLanguage1Operator(language1Operator);
    }

    public void setLanguage2(final Language language2) {
        offer.setLanguage2(language2);
    }

    public void setLanguage2Level(final LanguageLevel language2Level) {
        offer.setLanguage2Level(language2Level);
    }

    public void setLanguage2Operator(final LanguageOperator language2Operator) {
        offer.setLanguage2Operator(language2Operator);
    }

    public void setLanguage3(final Language language3) {
        offer.setLanguage3(language3);
    }

    public void setLanguage3Level(final LanguageLevel language3Level) {
        offer.setLanguage3Level(language3Level);
    }

    public void setWorkDescription(final String workDescription) {
        offer.setWorkDescription(workDescription);
    }

    public void setTypeOfWork(final TypeOfWork typeOfWork) {
        offer.setTypeOfWork(typeOfWork);
    }

    public void setMinimumWeeks(final Integer minimumWeeks) {
        offer.setMinimumWeeks(minimumWeeks);
    }

    public void setMaximumWeeks(final Integer maximumWeeks) {
        offer.setMaximumWeeks(maximumWeeks);
    }

    public void setFromDate(final String fromDate) {
        offer.setFromDate(new Date(fromDate));
    }

    public void setToDate(final String toDate) {
        offer.setToDate(new Date(toDate));
    }

    public void setFromDate2(final String fromDate2) {
        offer.setFromDate2(new Date(fromDate2));
    }

    public void setToDate2(final String toDate2) {
        offer.setToDate2(new Date(toDate2));
    }

    public void setUnavailableFrom(final String unavailableFrom) {
        offer.setUnavailableFrom(new Date(unavailableFrom));
    }

    public void setUnavailableTo(final String unavailableTo) {
        offer.setUnavailableTo(new Date(unavailableTo));
    }

    public void setWorkingPlace(final String workingPlace) {
        offer.setWorkingPlace(workingPlace);
    }

    public void setNearestAirport(final String nearestAirport) {
        offer.setNearestAirport(nearestAirport);
    }

    public void setNearestPubTransport(final String nearestPubTransport) {
        offer.setNearestPubTransport(nearestPubTransport);
    }

    public void setWeeklyHours(final Float weeklyHours) {
        offer.setWeeklyHours(weeklyHours);
    }

    public void setDailyHours(final Float dailyHours) {
        offer.setDailyHours(dailyHours);
    }

    public void setPayment(final BigDecimal payment) {
        offer.setPayment(payment);
    }

    public void setCurrency(final Currency currency) {
        offer.setCurrency(currency);
    }

    public void setPaymentFrequency(final PaymentFrequency paymentFrequency) {
        offer.setPaymentFrequency(paymentFrequency);
    }

    public void setDeduction(final String deduction) {
        offer.setDeduction(deduction);
    }

    public void setLodgingBy(final String lodgingBy) {
        offer.setLodgingBy(lodgingBy);
    }

    public void setLodgingCost(final BigDecimal lodgingCost) {
        offer.setLodgingCost(lodgingCost);
    }

    public void setLodgingCostFrequency(final PaymentFrequency lodgingCostFrequency) {
        offer.setLodgingCostFrequency(lodgingCostFrequency);
    }

    public void setLivingCost(final BigDecimal livingCost) {
        offer.setLivingCost(livingCost);
    }

    public void setLivingCostFrequency(final PaymentFrequency livingCostFrequency) {
        offer.setLivingCostFrequency(livingCostFrequency);
    }

    public void setCanteen(final Boolean canteen) {
        offer.setCanteen(canteen);
    }

    public void printOffer() {
        getResponse().getOffer().toString();
    }

    public void processOffer() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        request = new ProcessOfferRequest(offer);
        setResponse(exchange.processOffer(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
