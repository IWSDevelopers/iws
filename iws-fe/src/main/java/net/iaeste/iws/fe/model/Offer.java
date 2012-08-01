/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.model.Offer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.fe.model;


import net.iaeste.iws.api.enums.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * A class that stores values from the offer form and making it available for the backend
 *
 * @author Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class Offer {


    @Length(min = 7, max = 15)
    private String refNo;
    @NotNull
    private Date nominationDeadline;
    private Integer numberOfHardCopies;
    @Valid
    private Employer employer = new Employer();
    @Valid
    private Faculty faculty;
    private String specialization;
    private List<StudyLevel> selectedLevelOfStudy;
    private boolean previousTrainingRequired;
    @Length(min = 0, max = 500)
    private String otherRequirements;
    private Gender selectedGender = Gender.E;
    private String otherFaculty;
    @Length(min = 0, max = 1000)
    private String workDescription;
    private List<TypeOfWork> selectedTypesOfWork;
    @Min(1)
    private Integer minNumberOfWeeksOffered;
    @Min(1)
    private Integer maxNumberOfWeeksOffered;
    private Date dateFrom;
    private Date dateTo;
    private boolean date1Required;
    private Date dateFrom2;
    private Date dateTo2;
    private boolean holidaysRequired;
    private Date dateFromHolidays;
    private Date dateToHolidays;
    private BigDecimal grossPay;
    private Currency selectedCurrency;
    private PaymentFrequency selectedGrossPayPaymentFrequency;
    private Integer deductionsExpected;
    private String lodgingArrangedBy;
    private BigDecimal estimatedCostOfLodging;
    private PaymentFrequency selectedCostOfLodgingPeriod;
    private BigDecimal estimatedCostOfLiving;
    private PaymentFrequency selectedCostOfLivingPeriod;
    private boolean canteenFacilities;
    private TableView selectedTableView;
    private List<LanguageWrapper> languages = new ArrayList<>(3);
    private List<LanguageOperator> languageOperators = new ArrayList<>(2);


    public Offer() {
        languages.add(new LanguageWrapper());
    }

    public boolean canAddAnotherLanguage() {
        for (LanguageWrapper l : languages) {
            if (l.getLanguage() == null) {
                return false;
            }
        }
        return this.languages.size() < 3;
    }

    public void addLanguageWithOperator() {
        this.languages.add(new LanguageWrapper());
        this.languageOperators.add(LanguageOperator.A);
    }

    public void removeLanguageWithOperator(int position) {
        languages.remove(position);
        int operatorPos = position > 1 ? 1 : position;
        languageOperators.remove(operatorPos);
    }

    public boolean isLanguageSelected(Language languageToCheck, Integer ignoreIndex) {
        int ignore = ignoreIndex == null ? -1 : ignoreIndex.intValue();
        for (int i = 0; i < languages.size(); i++) {
            if (i != ignore && languages.get(i).getLanguage() == languageToCheck) {
                return true;
            }
        }
        return false;
    }

    public boolean allLanguageOperatorsMatch() {
        return new HashSet(languageOperators).size() <= 1;
    }

    public String changeDateFormat(Date complexDate) {
        SimpleDateFormat change = new SimpleDateFormat("MMMM dd.yyyy");
        return change.format(complexDate);
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public Date getNominationDeadline() {
        return nominationDeadline;
    }

    public void setNominationDeadline(Date nominationDeadline) {
        this.nominationDeadline = nominationDeadline;
    }

    public Integer getNumberOfHardCopies() {
        return numberOfHardCopies;
    }

    public void setNumberOfHardCopies(Integer numberOfHardCopies) {
        this.numberOfHardCopies = numberOfHardCopies;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<StudyLevel> getSelectedLevelOfStudy() {
        return selectedLevelOfStudy;
    }

    public void setSelectedLevelOfStudy(List<StudyLevel> selectedLevelOfStudy) {
        this.selectedLevelOfStudy = selectedLevelOfStudy;
    }

    public boolean isPreviousTrainingRequired() {
        return previousTrainingRequired;
    }

    public void setPreviousTrainingRequired(boolean previousTrainingRequired) {
        this.previousTrainingRequired = previousTrainingRequired;
    }

    public String getOtherRequirements() {
        return otherRequirements;
    }

    public void setOtherRequirements(String otherRequirements) {
        this.otherRequirements = otherRequirements;
    }

    public Gender getSelectedGender() {
        return selectedGender;
    }

    public void setSelectedGender(Gender selectedGender) {
        this.selectedGender = selectedGender;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    public List<TypeOfWork> getSelectedTypesOfWork() {
        return selectedTypesOfWork;
    }

    public void setSelectedTypesOfWork(List<TypeOfWork> selectedTypesOfWork) {
        this.selectedTypesOfWork = selectedTypesOfWork;
    }

    public Integer getMinNumberOfWeeksOffered() {
        return minNumberOfWeeksOffered;
    }

    public void setMinNumberOfWeeksOffered(Integer minNumberOfWeeksOffered) {
        this.minNumberOfWeeksOffered = minNumberOfWeeksOffered;
    }

    public Integer getMaxNumberOfWeeksOffered() {
        return maxNumberOfWeeksOffered;
    }

    public void setMaxNumberOfWeeksOffered(Integer maxNumberOfWeeksOffered) {
        this.maxNumberOfWeeksOffered = maxNumberOfWeeksOffered;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public boolean isDate1Required() {
        return date1Required;
    }

    public void setDate1Required(boolean date1Required) {
        this.date1Required = date1Required;
    }

    public Date getDateFrom2() {
        return dateFrom2;
    }

    public void setDateFrom2(Date dateFrom2) {
        this.dateFrom2 = dateFrom2;
    }

    public Date getDateTo2() {
        return dateTo2;
    }

    public void setDateTo2(Date dateTo2) {
        this.dateTo2 = dateTo2;
    }

    public boolean isHolidaysRequired() {
        return holidaysRequired;
    }

    public void setHolidaysRequired(boolean holidaysRequired) {
        this.holidaysRequired = holidaysRequired;
    }

    public Date getDateFromHolidays() {
        return dateFromHolidays;
    }

    public void setDateFromHolidays(Date dateFromHolidays) {
        this.dateFromHolidays = dateFromHolidays;
    }

    public Date getDateToHolidays() {
        return dateToHolidays;
    }

    public void setDateToHolidays(Date dateToHolidays) {
        this.dateToHolidays = dateToHolidays;
    }

    public BigDecimal getGrossPay() {
        return grossPay;
    }

    public void setGrossPay(BigDecimal grossPay) {
        this.grossPay = grossPay;
    }

    public Currency getSelectedCurrency() {
        return selectedCurrency;
    }

    public void setSelectedCurrency(Currency selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
    }

    public PaymentFrequency getSelectedGrossPayPaymentFrequency() {
        return selectedGrossPayPaymentFrequency;
    }

    public void setSelectedGrossPayPaymentFrequency(PaymentFrequency selectedGrossPayPaymentFrequency) {
        this.selectedGrossPayPaymentFrequency = selectedGrossPayPaymentFrequency;
    }

    public Integer getDeductionsExpected() {
        return deductionsExpected;
    }

    public void setDeductionsExpected(Integer deductionsExpected) {
        this.deductionsExpected = deductionsExpected;
    }

    public String getLodgingArrangedBy() {
        return lodgingArrangedBy;
    }

    public void setLodgingArrangedBy(String lodgingArrangedBy) {
        this.lodgingArrangedBy = lodgingArrangedBy;
    }

    public BigDecimal getEstimatedCostOfLodging() {
        return estimatedCostOfLodging;
    }

    public void setEstimatedCostOfLodging(BigDecimal estimatedCostOfLodging) {
        this.estimatedCostOfLodging = estimatedCostOfLodging;
    }

    public PaymentFrequency getSelectedCostOfLodgingPeriod() {
        return selectedCostOfLodgingPeriod;
    }

    public void setSelectedCostOfLodgingPeriod(PaymentFrequency selectedCostOfLodgingPeriod) {
        this.selectedCostOfLodgingPeriod = selectedCostOfLodgingPeriod;
    }

    public BigDecimal getEstimatedCostOfLiving() {
        return estimatedCostOfLiving;
    }

    public void setEstimatedCostOfLiving(BigDecimal estimatedCostOfLiving) {
        this.estimatedCostOfLiving = estimatedCostOfLiving;
    }

    public PaymentFrequency getSelectedCostOfLivingPeriod() {
        return selectedCostOfLivingPeriod;
    }

    public void setSelectedCostOfLivingPeriod(PaymentFrequency selectedCostOfLivingPeriod) {
        this.selectedCostOfLivingPeriod = selectedCostOfLivingPeriod;
    }

    public boolean isCanteenFacilities() {
        return canteenFacilities;
    }

    public void setCanteenFacilities(boolean canteenFacilities) {
        this.canteenFacilities = canteenFacilities;
    }

    public TableView getSelectedTableView() {
        return selectedTableView;
    }

    public void setSelectedTableView(TableView selectedTableView) {
        this.selectedTableView = selectedTableView;
    }

    public List<LanguageWrapper> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LanguageWrapper> languages) {
        this.languages = languages;
    }

    public List<LanguageOperator> getLanguageOperators() {
        return languageOperators;
    }

    public void setLanguageOperators(List<LanguageOperator> languageOperators) {
        this.languageOperators = languageOperators;
    }
}