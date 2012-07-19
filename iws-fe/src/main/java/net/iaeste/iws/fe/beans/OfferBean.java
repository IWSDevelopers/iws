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

    private dummyOffer[] dummyOfferAll = new dummyOffer[]{
     new dummyOffer("AT-2012-1111-VI", "22/12/2012", "Employer1", "employer@emp.com", "8-16", "550 €", "TU WIEN", "Electrical engineering", "Middle", "M"),
            new dummyOffer("AT-2012-1111-VI", "22/12/2012", "Employer1", "employer@emp.com", "8-16", "550 €", "TU WIEN", "Electrical engineering", "Middle", "M"),
            new dummyOffer("AT-2012-1111-VI", "22/12/2012", "Employer1", "employer@emp.com", "8-16", "550 €", "TU WIEN", "Electrical engineering", "Middle", "M"),
            new dummyOffer("AT-2012-1111-VI", "22/12/2012", "Specific", "employer@emp.com", "8-16", "550 €", "TU WIEN", "Electrical engineering", "Middle", "M"),
            new dummyOffer("AT-2012-1111-VI", "22/12/2012", "Employer1", "employer@emp.com", "8-16", "550 €", "TU WIEN", "Electrical engineering", "Middle", "M"),
            new dummyOffer("AT-2012-1111-VI", "28/1/2010", "Employer1", "employer@emp.com", "8-16", "550 €", "TU WIEN", "Electrical engineering", "Middle", "M"),
            new dummyOffer("AT-2012-1111-VI", "22/12/2012", "Employer1", "employer@emp.com", "8-16", "550 €", "TU WIEN", "Electrical engineering", "Middle", "M"),
            new dummyOffer("AT-2012-1111-VI", "22/12/2012", "Employer1", "employer@emp.com", "8-16", "550 €", "TU WIEN", "Electrical engineering", "Middle", "M"),
            new dummyOffer("AT-2012-1111-VI", "22/12/2012", "Employer1", "employer@emp.com", "8-16", "550 €", "TU WIEN", "Electrical engineering", "Middle", "M"),
            new dummyOffer("AT-2012-1111-VI", "22/12/2012", "Employer1", "employer@emp.com", "8-16", "550 €", "TU WIEN", "Electrical engineering", "Middle", "M"),
    };

    public dummyOffer[] getDummyOfferAll() {
        return dummyOfferAll;
    }

    public void setDummyOfferAll(dummyOffer[] dummyOfferAll) {
        this.dummyOfferAll = dummyOfferAll;
    }

    private dummyOffer selectedOffer;

    public dummyOffer getSelectedOffer() {
        return selectedOffer;
    }

    public void setSelectedOffer(dummyOffer selectedOffer) {
        this.selectedOffer = selectedOffer;
    }

    public class dummyOffer {
        String refNo;
        String nominationDeadline;
        String employer;
        String website;
        String weeksOffered;
        String grossPay;
        String faculty;
        String specialization;
        String studyLevel;
        String gender;

        public dummyOffer(String refNo, String nominationDeadline, String employer, String website,
                          String weeksOffered, String grossPay, String faculty, String specialization, String studyLevel, String gender) {
            this.refNo = refNo;
            this.nominationDeadline = nominationDeadline;
            this.employer = employer;
            this.website = website;
            this.weeksOffered = weeksOffered;
            this.grossPay = grossPay;
            this.faculty = faculty;
            this.specialization = specialization;
            this.studyLevel = studyLevel;
            this.gender = gender;
        }

        public String getRefNo() {
            return refNo;
        }

        public void setRefNo(String refNo) {
            this.refNo = refNo;
        }

        public String getNominationDeadline() {
            return nominationDeadline;
        }

        public void setNominationDeadline(String nominationDeadline) {
            this.nominationDeadline = nominationDeadline;
        }

        public String getEmployer() {
            return employer;
        }

        public void setEmployer(String employer) {
            this.employer = employer;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getWeeksOffered() {
            return weeksOffered;
        }

        public void setWeeksOffered(String weeksOffered) {
            this.weeksOffered = weeksOffered;
        }

        public String getGrossPay() {
            return grossPay;
        }

        public void setGrossPay(String grossPay) {
            this.grossPay = grossPay;
        }

        public String getFaculty() {
            return faculty;
        }

        public void setFaculty(String faculty) {
            this.faculty = faculty;
        }

        public String getSpecialization() {
            return specialization;
        }

        public void setSpecialization(String specialization) {
            this.specialization = specialization;
        }

        public String getStudyLevel() {
            return studyLevel;
        }

        public void setStudyLevel(String studyLevel) {
            this.studyLevel = studyLevel;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }


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
    private Offer.TableView[] tableViews = Offer.TableView.values();

    public Offer.TableView[] getTableViews() {
        return tableViews;
    }

    public void setTableViews(Offer.TableView[] tableViews) {
        this.tableViews = tableViews;
    }

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
