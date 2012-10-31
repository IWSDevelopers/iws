/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.SaveOffer
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

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.dtos.Offer;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.FieldOfStudy;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.LanguageLevel;
import net.iaeste.iws.api.enums.LanguageOperator;
import net.iaeste.iws.api.enums.PaymentFrequency;
import net.iaeste.iws.api.enums.StudyLevel;
import net.iaeste.iws.api.enums.TypeOfWork;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.AuthenticationRequest;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.responses.AuthenticationResponse;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.client.AccessClient;
import net.iaeste.iws.client.ExchangeClient;
import net.iaeste.iws.core.transformers.CollectionTransformer;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;
import org.joda.time.DateMidnight;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class SaveOffer extends AbstractFixture<OfferResponse> {
    private final Access ac = new AccessClient();
    private AuthenticationToken token;
    private String username;
    private String password;

    private final Exchange exchange = new ExchangeClient();
    private Offer offer;
    private ProcessOfferRequest request;

    public SaveOffer() {
        reset();
    }

    /** alias function for execute */
    public void save() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        getToken();
        request = new ProcessOfferRequest(offer);
        response = exchange.processOffer(token, request);
    }

    @Override
    public void reset() {
        request = null;
        response = null;
        token = null;
        offer = new Offer();
    }

    public boolean verify() {
        try {
            offer.verify();
            return true;
        } catch (VerificationException ignore) {
            return false;
        }
    }

    /**
     * Sets username and password so the AuthenticationToken could be fetched.
     *
     * @param username
     * @param password
     */
    public void setUsernameAndPassword(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setToken(final String token) {
        this.token = new AuthenticationToken(token);
    }

    private void getToken() {
        if (token == null) {
            final AuthenticationRequest authRequest = new AuthenticationRequest(username, password);
            final AuthenticationResponse authResponse = ac.generateSession(authRequest);
            token = authResponse.getToken();
        }
    }

    /** not null */
    public void setRefNo(final String refNo) {
        offer.setRefNo(refNo);
    }

    public void setNominationDeadline(final Date nominationDeadline) {
        offer.setNominationDeadline(new DateMidnight(nominationDeadline));
    }

    /** not null */
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

    /** not null */
    public void setFieldOfStudies(final String fieldOfStudies) {
        offer.setFieldOfStudies(CollectionTransformer.explodeEnumSet(FieldOfStudy.class, fieldOfStudies));
    }

    public void setSpecializations(final String specializations) {
        offer.setSpecializations(new HashSet<>(CollectionTransformer.explodeStringList(specializations)));
    }

    /** not null */
    public void setStudyLevels(final String studyLevels) {
        offer.setStudyLevels(CollectionTransformer.explodeEnumSet(StudyLevel.class, studyLevels));
    }

    public void setPrevTrainingRequired(final Boolean prevTrainingRequired) {
        offer.setPrevTrainingRequired(prevTrainingRequired);
    }

    public void setOtherRequirements(final String otherRequirements) {
        offer.setOtherRequirements(otherRequirements);
    }

    /** not null */
    public void setLanguage1(final Language language1) {
        offer.setLanguage1(language1);
    }

    /** not null */
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

    /** not null */
    public void setWorkDescription(final String workDescription) {
        offer.setWorkDescription(workDescription);
    }

    public void setTypeOfWork(final TypeOfWork typeOfWork) {
        offer.setTypeOfWork(typeOfWork);
    }

    /** not null */
    public void setMinimumWeeks(final Integer minimumWeeks) {
        offer.setMinimumWeeks(minimumWeeks);
    }

    /** not null */
    public void setMaximumWeeks(final Integer maximumWeeks) {
        offer.setMaximumWeeks(maximumWeeks);
    }

    /** not null */
    public void setFromDate(final Date fromDate) {
        offer.setFromDate(new DateMidnight(fromDate));
    }

    /** not null */
    public void setToDate(final Date toDate) {
        offer.setToDate(new DateMidnight(toDate));
    }

    public void setFromDate2(final Date fromDate2) {
        offer.setFromDate2(new DateMidnight(fromDate2));
    }

    public void setToDate2(final Date toDate2) {
        offer.setToDate2(new DateMidnight(toDate2));
    }

    public void setUnavailableFrom(final Date unavailableFrom) {
        offer.setUnavailableFrom(new DateMidnight(unavailableFrom));
    }

    public void setUnavailableTo(final Date unavailableTo) {
        offer.setUnavailableTo(new DateMidnight(unavailableTo));
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

    /** not null */
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

    public void setDeduction(final Integer deduction) {
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
}
