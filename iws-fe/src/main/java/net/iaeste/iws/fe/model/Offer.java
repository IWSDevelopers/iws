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


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * A class that stores values from the offer form and making it available for the backend
 *
 * @author Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class Offer {

    public enum PaymentPeriod {
        MONTHLY("paymentPeriod.monthly"),
        WEEKLY("paymentPeriod.weekly");

        private String nameProperty;

        private PaymentPeriod(String nameProperty) {
            this.nameProperty = nameProperty;
        }

        public String getNameProperty() {
            return nameProperty;
        }
    }

    public enum GrossPayPeriod {
        MONTHLY("paymentPeriod.monthly"),
        WEEKLY("paymentPeriod.weekly");

        private String nameProperty;

        private GrossPayPeriod(String nameProperty) {
            this.nameProperty = nameProperty;
        }

        public String getNameProperty() {
            return nameProperty;
        }
    }

    public enum StudyLevel {
        BEGINNING("beginningLvl"),
        MIDDLE("middleLvl"),
        END("endLvl");

        private String nameProperty;

        private StudyLevel(String nameProperty) {
            this.nameProperty = nameProperty;
        }

        public String getNameProperty() {
            return nameProperty;
        }
    }

    public enum Gender {
        MALE("maleGender"),
        FEMALE("femaleGender"),
        EITHER("eitherGender");

        private String nameProperty;

        private Gender(String nameProperty) {
            this.nameProperty = nameProperty;
        }

        public String getNameProperty() {
            return nameProperty;
        }
    }

    public enum LanguageLevel {
        FAIR("fair"),
        GOOD("good"),
        EXCELLENT("excellent");

        private String nameProperty;

        private LanguageLevel(String nameProperty) {
            this.nameProperty = nameProperty;
        }

        public String getNameProperty() {
            return nameProperty;
        }
    }

    public enum TypeOfWork {
        RESEARCHANDDEVELOPMENT("researchAndDevelopment"),
        PROFESSIONAL("professional"),
        WORKINGENVIRONMENT("workingEnvironment"),
        NONSPECIFIC("nonSpecific");

        private String nameProperty;

        private TypeOfWork(String nameProperty) {
            this.nameProperty = nameProperty;
        }

        public String getNameProperty() {
            return nameProperty;
        }
    }

    public enum Currency {
        EUR("currencyEURO"),
        HRK("currencyHRK"),
        AUD("currencyAUD");

        private String nameProperty;

        private Currency(String nameProperty) {
            this.nameProperty = nameProperty;
        }

        public String getNameProperty() {
            return nameProperty;
        }
    }

    @Length(min = 7, max = 15)
    private String refNo;
    @NotNull
    private Date nominationDeadline;

    private Employer employer;
    private String address;
    private String address2;
    private String businessOrProducts;
    @Min(1)
    private Integer numberOfEmployees;
    private String website = "http://";
    private String workingPlace;
    private String nearestInternationalAirport;
    private String nearestPublicTransport;
    private Float weeklyWorkingHours;
    private Float dailyWorkingHours;

    private Faculty faculty;
    private String specialization;
    private List<StudyLevel> selectedLevelOfStudy;
    private boolean previousTrainingRequired;
    private String otherRequirements;
    private Gender selectedGender;
    private String otherFaculty;
    private String language1;
    private String language2;
    private String language3;
    private LanguageLevel languageOneLevel;
    private LanguageLevel languageTwoLevel;
    private LanguageLevel languageThreeLevel;
    private boolean language1Required = true;
    private boolean language2Required;
    private boolean language3Required;

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
    private GrossPayPeriod selectedGrossPayPaymentPeriod;
    private Integer deductionsExpected;

    private String lodgingArrangedBy;
    private BigDecimal estimatedCostOfLodging;
    private PaymentPeriod selectedCostOfLodgingPeriod;
    private BigDecimal estimatedCostOfLiving;
    private PaymentPeriod selectedCostOfLivingPeriod;
    private boolean canteenFacilities;

    public void employerChanged() {
        this.address = employer.getAddress();
        this.address2 = employer.getAddress2();
        this.businessOrProducts = employer.getBusinessOrProducts();
        this.dailyWorkingHours = employer.getDailyWorkingHours();
        this.nearestInternationalAirport = employer.getNearestInternationalAirport();
        this.nearestPublicTransport = employer.getNearestPublicTransport();
        this.numberOfEmployees = employer.getNumberOfEmployees();
        this.website = employer.getWebsite();
        this.weeklyWorkingHours = employer.getWeeklyWorkingHours();
        this.workingPlace = employer.getWorkingPlace();
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

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getBusinessOrProducts() {
        return businessOrProducts;
    }

    public void setBusinessOrProducts(String businessOrProducts) {
        this.businessOrProducts = businessOrProducts;
    }

    public Integer getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(Integer numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public String getNearestInternationalAirport() {
        return nearestInternationalAirport;
    }

    public void setNearestInternationalAirport(String nearestInternationalAirport) {
        this.nearestInternationalAirport = nearestInternationalAirport;
    }

    public String getNearestPublicTransport() {
        return nearestPublicTransport;
    }

    public void setNearestPublicTransport(String nearestPublicTransport) {
        this.nearestPublicTransport = nearestPublicTransport;
    }

    public Float getWeeklyWorkingHours() {
        return weeklyWorkingHours;
    }

    public void setWeeklyWorkingHours(Float weeklyWorkingHours) {
        this.weeklyWorkingHours = weeklyWorkingHours;
    }

    public Float getDailyWorkingHours() {
        return dailyWorkingHours;
    }

    public void setDailyWorkingHours(Float dailyWorkingHours) {
        this.dailyWorkingHours = dailyWorkingHours;
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

    public String getOtherFaculty() {
        return otherFaculty;
    }

    public void setOtherFaculty(String otherFaculty) {
        this.otherFaculty = otherFaculty;
    }

    public String getLanguage1() {
        return language1;
    }

    public void setLanguage1(String language1) {
        this.language1 = language1;
    }

    public String getLanguage2() {
        return language2;
    }

    public void setLanguage2(String language2) {
        this.language2 = language2;
    }

    public String getLanguage3() {
        return language3;
    }

    public void setLanguage3(String language3) {
        this.language3 = language3;
    }

    public LanguageLevel getLanguageOneLevel() {
        return languageOneLevel;
    }

    public void setLanguageOneLevel(LanguageLevel languageOneLevel) {
        this.languageOneLevel = languageOneLevel;
    }

    public LanguageLevel getLanguageTwoLevel() {
        return languageTwoLevel;
    }

    public void setLanguageTwoLevel(LanguageLevel languageTwoLevel) {
        this.languageTwoLevel = languageTwoLevel;
    }

    public LanguageLevel getLanguageThreeLevel() {
        return languageThreeLevel;
    }

    public void setLanguageThreeLevel(LanguageLevel languageThreeLevel) {
        this.languageThreeLevel = languageThreeLevel;
    }

    public boolean isLanguage1Required() {
        return language1Required;
    }

    public void setLanguage1Required(boolean language1Required) {
        this.language1Required = language1Required;
    }

    public boolean isLanguage2Required() {
        return language2Required;
    }

    public void setLanguage2Required(boolean language2Required) {
        this.language2Required = language2Required;
    }

    public boolean isLanguage3Required() {
        return language3Required;
    }

    public void setLanguage3Required(boolean language3Required) {
        this.language3Required = language3Required;
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

    public GrossPayPeriod getSelectedGrossPayPaymentPeriod() {
        return selectedGrossPayPaymentPeriod;
    }

    public void setSelectedGrossPayPaymentPeriod(GrossPayPeriod selectedGrossPayPaymentPeriod) {
        this.selectedGrossPayPaymentPeriod = selectedGrossPayPaymentPeriod;
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

    public PaymentPeriod getSelectedCostOfLodgingPeriod() {
        return selectedCostOfLodgingPeriod;
    }

    public void setSelectedCostOfLodgingPeriod(PaymentPeriod selectedCostOfLodgingPeriod) {
        this.selectedCostOfLodgingPeriod = selectedCostOfLodgingPeriod;
    }

    public BigDecimal getEstimatedCostOfLiving() {
        return estimatedCostOfLiving;
    }

    public void setEstimatedCostOfLiving(BigDecimal estimatedCostOfLiving) {
        this.estimatedCostOfLiving = estimatedCostOfLiving;
    }

    public PaymentPeriod getSelectedCostOfLivingPeriod() {
        return selectedCostOfLivingPeriod;
    }

    public void setSelectedCostOfLivingPeriod(PaymentPeriod selectedCostOfLivingPeriod) {
        this.selectedCostOfLivingPeriod = selectedCostOfLivingPeriod;
    }

    public boolean isCanteenFacilities() {
        return canteenFacilities;
    }

    public void setCanteenFacilities(boolean canteenFacilities) {
        this.canteenFacilities = canteenFacilities;
    }
}
