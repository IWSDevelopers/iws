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

import net.iaeste.iws.api.enums.*;
import net.iaeste.iws.fe.model.*;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

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
    private PaymentFrequency[] paymentPeriods = new PaymentFrequency[] {PaymentFrequency.W, PaymentFrequency.M};
    private StudyLevel[] studyLevels = StudyLevel.values();
    private Gender[] genders = Gender.values();
    private TypeOfWork[] typesOfWork = TypeOfWork.values();
    private PaymentFrequency[] grossPayPeriods = new PaymentFrequency[] {PaymentFrequency.W, PaymentFrequency.M};
    private Currency[] currencies = Currency.values();
    private TableView[] tableViews = TableView.values();
    private Language[] languages = Language.values();
    private LanguageLevel[] languageLevels = LanguageLevel.values();
    private LanguageOperator[] languageOperators = LanguageOperator.values();
    private List<Offer> offerList = new ArrayList<>();
    private Offer selectedOffer;

    public Offer getSelectedOffer() {
        return selectedOffer;
    }

    public void setSelectedOffer(Offer selectedOffer) {
        this.selectedOffer = selectedOffer;
    }


    public OfferBean() {
    }

    public String saveOffer() {
        offerList.add(emptyOffer);
        emptyOffer = new Offer();
        return "listOffers?faces-redirect=true";
    }

    public String updateOffer() {
        return "listOffers?faces-redirect=true";
    }

    public String deleteOffer() {
        offerList.remove(selectedOffer);
        return "listOffers?faces-redirect=true";
    }

    public List<Offer> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<Offer> offerList) {
        this.offerList = offerList;
    }

    public LanguageOperator[] getLanguageOperators() {
        return languageOperators;
    }

    public void setLanguageOperators(LanguageOperator[] languageOperators) {
        this.languageOperators = languageOperators;
    }

    public Language[] getLanguages() {
        return languages;
    }

    public void setLanguages(Language[] languages) {
        this.languages = languages;
    }

    public TableView[] getTableViews() {
        return tableViews;
    }

    public void setTableViews(TableView[] tableViews) {
        this.tableViews = tableViews;
    }

    public Currency[] getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Currency[] currencies) {
        this.currencies = currencies;
    }

    public PaymentFrequency[] getGrossPayPeriods() {
        return grossPayPeriods;
    }

    public void setGrossPayPeriods(PaymentFrequency[] grossPayPeriods) {
        this.grossPayPeriods = grossPayPeriods;
    }

    public TypeOfWork[] getTypesOfWork() {
        return typesOfWork;
    }

    public void setTypesOfWork(TypeOfWork[] typesOfWork) {
        this.typesOfWork = typesOfWork;
    }

    public LanguageLevel[] getLanguageLevels() {
        return languageLevels;
    }

    public void setLanguageLevels(LanguageLevel[] languageLevels) {
        this.languageLevels = languageLevels;
    }

    public Gender[] getGenders() {
        return genders;
    }

    public void setGenders(Gender[] genders) {
        this.genders = genders;
    }

    public StudyLevel[] getStudyLevels() {
        return studyLevels;
    }

    public void setStudyLevels(StudyLevel[] studyLevels) {
        this.studyLevels = studyLevels;
    }

    public Offer getEmptyOffer() {
        return emptyOffer;
    }

    public void setEmptyOffer(Offer emptyOffer) {
        this.emptyOffer = emptyOffer;
    }

    public PaymentFrequency[] getPaymentPeriods() {
        return paymentPeriods;
    }

    public void setPaymentPeriods(PaymentFrequency[] paymentPeriods) {
        this.paymentPeriods = paymentPeriods;
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
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
