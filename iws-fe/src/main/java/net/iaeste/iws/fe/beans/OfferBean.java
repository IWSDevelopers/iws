/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.beans.OFormBean
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.fe.beans;

import net.iaeste.iws.fe.model.Employer;
import net.iaeste.iws.fe.model.Faculty;
import net.iaeste.iws.fe.model.Offer;
import org.primefaces.event.FlowEvent;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * storing variables for form O
 *
 * @author Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@Named
@SessionScoped
public class OfferBean implements Serializable {

    private Offer emptyOffer = new Offer();
    private boolean language2Required;
    private boolean language3Required;
    private Offer.PaymentPeriod[] paymentPeriods = Offer.PaymentPeriod.values();
    private Offer.StudyLevel[] studyLevels = Offer.StudyLevel.values();
    private Offer.Gender[] genders = Offer.Gender.values();
    private Offer.LanguageLevel[] languageLevels = Offer.LanguageLevel.values();
    private Offer.TypeOfWork[] categories = Offer.TypeOfWork.values();
    private Offer.GrossPayPeriod[] grossPayPeriods = Offer.GrossPayPeriod.values();
    private Offer.Currency[] currencies = Offer.Currency.values();

    public Offer.Currency[] getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Offer.Currency[] currencies) {
        this.currencies = currencies;
    }

    public Offer.GrossPayPeriod[] getGrossPayPeriods() {
        return grossPayPeriods;
    }

    public void setGrossPayPeriods(Offer.GrossPayPeriod[] grossPayPeriods) {
        this.grossPayPeriods = grossPayPeriods;
    }

    public Offer.TypeOfWork[] getCategories() {
        return categories;
    }

    public void setCategories(Offer.TypeOfWork[] categories) {
        this.categories = categories;
    }

    public Offer.LanguageLevel[] getLanguageLevels() {
        return languageLevels;
    }

    public void setLanguageLevels(Offer.LanguageLevel[] languageLevels) {
        this.languageLevels = languageLevels;
    }

    public Offer.Gender[] getGenders() {
        return genders;
    }

    public void setGenders(Offer.Gender[] genders) {
        this.genders = genders;
    }

    public Offer.StudyLevel[] getStudyLevels() {
        return studyLevels;
    }

    public void setStudyLevels(Offer.StudyLevel[] studyLevels) {
        this.studyLevels = studyLevels;
    }

    public boolean isLanguage2Required() {
        return language2Required;
    }

    public void setLanguage2Required(boolean language2Required) {
        this.language2Required = language2Required;
    }

    public Offer getEmptyOffer() {
        return emptyOffer;
    }

    public void setEmptyOffer(Offer emptyOffer) {
        this.emptyOffer = emptyOffer;
    }

    public boolean isLanguage3Required() {
        return language3Required;
    }

    public void setLanguage3Required(boolean language3Required) {
        this.language3Required = language3Required;
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();

    }

    public Offer.PaymentPeriod[] getPaymentPeriods() {
        return paymentPeriods;
    }

    public void setPaymentPeriods(Offer.PaymentPeriod[] paymentPeriods) {
        this.paymentPeriods = paymentPeriods;
    }

    public List<Employer> complete(String query) {
        List<Employer> results = new ArrayList<>();
        for (Employer e : Employer.getDummyEmployers()) {
            if (e.nameMatches(query)) {
                results.add(e);
            }
        }
        return results;
    }

    public List<Faculty> completeFaculty(String query) {
        List<Faculty> resultsFaculty = new ArrayList<>();
        for (Faculty e : Faculty.getDummyFaculties()) {
            if (e.nameMatches(query)) {
                resultsFaculty.add(e);
            }
        }
        return resultsFaculty;
    }

}
