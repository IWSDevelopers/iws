/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.*;
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DatePeriod;
import net.iaeste.iws.fitnesse.callers.ExchangeCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ProcessOffer extends AbstractFixture<OfferResponse> {

    private final Exchange exchange = new ExchangeCaller();
    private Offer offer = new Offer();
    private ProcessOfferRequest request = null;
    private Set<StudyLevel> studyLevels = new HashSet<>();
    private Set<FieldOfStudy> fieldOfStudies = new HashSet<>();
    private Set<String> specializations = new HashSet<>();
    private Date fromDate1 = null;
    private Date toDate1 = null;
    private Date fromDate2 = null;
    private Date toDate2 = null;

    public void setRefNo(final String refNo) {
        offer.setRefNo(refNo);
    }

    public void setEmployer(final String employerId) {
        FetchEmployerRequest feRequest =  new FetchEmployerRequest();
        feRequest.setFetchById(employerId);
        FetchEmployerResponse feResponse = exchange.fetchEmployers(getToken(), feRequest);

        if(!feResponse.getEmployers().isEmpty()) {
            offer.setEmployer(feResponse.getEmployers().get(0));
        }
    }

    public void setWorkDescription(final String workDescription) {
        offer.setWorkDescription(workDescription);
    }

    public void setTypeOfWork(final String typeOfWork) {
        offer.setTypeOfWork(TypeOfWork.valueOf(typeOfWork));
    }

    public void setStudyLevels(final String studyLevels) {
        this.studyLevels.add(StudyLevel.valueOf(studyLevels));
    }

    public void setFieldOfStudies(final String fieldOfStudies)  {
        this.fieldOfStudies.add(FieldOfStudy.valueOf(fieldOfStudies));
    }

    public void setSpecializations(final String specializations) {
        this.specializations.add(specializations);
    }

    public void setPreviousTrainingRequired(final Boolean previousTrainingRequired) {
        offer.setPreviousTrainingRequired(previousTrainingRequired);
    }

    public void setOtherRequirements(final String otherRequirements) {
        offer.setOtherRequirements(otherRequirements);
    }

    public void setMinimumWeeks(final Integer minimumWeeks) {
        offer.setMinimumWeeks(minimumWeeks);
    }

    public void setMaximumWeeks(final Integer maximumWeeks) {
        offer.setMaximumWeeks(maximumWeeks);
    }

    public void setPeriod1From(final String fromDate) {
        fromDate1 = new Date(fromDate);
    }

    public void setPeriod1To(final String toDate) {
        toDate1 = new Date(toDate);
    }

    public void setPeriod2From(final String fromDate) {
        fromDate2 = new Date(fromDate);
    }

    public void setPeriod2To(final String toDate) {
        toDate2 = new Date(toDate);
    }

    public void setUnavailable(final String fromDate, final String toDate) {
        offer.setUnavailable(new DatePeriod(new Date(fromDate), new Date(toDate)));
    }

    public void setLanguage1(final String language1) {
        offer.setLanguage1(Language.valueOf(language1));
    }

    public void setLanguage1Level(final String language1Level) {
        offer.setLanguage1Level(LanguageLevel.valueOf(language1Level));
    }

    public void setLanguage1Operator(final String language1Operator) {
        offer.setLanguage1Operator(LanguageOperator.valueOf(language1Operator));
    }

    public void setLanguage2(final String language2) {
        offer.setLanguage2(Language.valueOf(language2));
    }

    public void setLanguage2Level(final String language2Level) {
        offer.setLanguage2Level(LanguageLevel.valueOf(language2Level));
    }

    public void setLanguage2Operator(final String language2Operator) {
        offer.setLanguage2Operator(LanguageOperator.valueOf(language2Operator));
    }

    public void setLanguage3(final String language3) {
        offer.setLanguage3(Language.valueOf(language3));
    }

    public void setLanguage3Level(final String language3Level) {
        offer.setLanguage3Level(LanguageLevel.valueOf(language3Level));
    }

    public void setPayment(final String payment) {

        offer.setPayment(new BigDecimal(payment));
    }

    public void setPaymentFrequency(final String paymentFrequency) {
        offer.setPaymentFrequency(PaymentFrequency.valueOf(paymentFrequency));
    }

    public void setCurrency(final String currency) {
        offer.setCurrency(Currency.valueOf(currency));
    }

    public void setDeduction(final String deduction) {
        offer.setDeduction(deduction);
    }

    public void setLivingCost(final String livingCost) {
        offer.setLivingCost(new BigDecimal(livingCost));
    }

    public void setLivingCostFrequency(final String livingCostFrequency) {
        offer.setLivingCostFrequency(PaymentFrequency.valueOf(livingCostFrequency));
    }

    public void setLodgingBy(final String lodgingBy) {
        offer.setLodgingBy(lodgingBy);
    }

    public void setLodgingCost(final String lodgingCost) {
        offer.setLodgingCost(new BigDecimal(lodgingCost));
    }

    public void setLodgingCostFrequency(final String lodgingCostFrequency) {
        offer.setLodgingCostFrequency(PaymentFrequency.valueOf(lodgingCostFrequency));
    }

    public void setNominationDeadline(final String nominationDeadline) {
        offer.setNominationDeadline(new Date(nominationDeadline));
    }

    public void setNumberOfHardCopies(final Integer numberOfHardCopies) {
        offer.setNumberOfHardCopies(numberOfHardCopies);
    }

    public void setAdditionalInformation(final String additionalInformation)  {
        offer.setAdditionalInformation(additionalInformation);
    }

    public void setStatus(final String status) {
        offer.setStatus(OfferState.valueOf(status));
    }

    public void printOffer() {
        getResponse().getOffer().toString();
    }

    public String getOfferId() {
        return getResponse().getOffer().getOfferId();
    }

    public void processOffer() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();

        if(fromDate1 != null && toDate1 != null) {
            offer.setPeriod1(new DatePeriod(fromDate1, toDate1));
        }

        if(fromDate2 != null && toDate2 != null) {
            offer.setPeriod2(new DatePeriod(fromDate2, toDate2));
        }

        offer.setStudyLevels(studyLevels);
        offer.setFieldOfStudies(fieldOfStudies);
        offer.setSpecializations(specializations);
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
