/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-leargas) - net.iaeste.iws.leargas.persistence.Offer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.leargas.persistence;

/**
 * Mapping between IWS WebService Objects and the Leargas Database is best left
 * out of the main processing Logic. This Class is therefore designed to be used
 * as the DTO or Data Transfer Object.
 *
 * @author  Kim Jensen <kim@dawn.dk>
 * @version Leargas 1.0
 * @since   Java 1.8
 */
public final class OfferEntity {

    // =========================================================================
    // DTO Fields from the Leargas Database
    // =========================================================================

    private String refNo = null;
    private String deadline = null;
    private String comment = null;
    private String employer = null;
    private String address1 = null;
    private String address2 = null;
    private String postbox = null;
    private String postalcode = null;
    private String city = null;
    private String state = null;
    private String country = null;
    private String website = null;
    private String workplace = null;
    private String business = null;
    private String responsible = null;
    private String airport = null;
    private String transport = null;
    private String employees = null;
    private String hoursweekly = null;
    private String hoursdaily = null;
    private String canteen = null;
    private String faculty = null;
    private String specialization = null;
    private String trainingrequired = null;
    private String otherrequirements = null;
    private String workkind = null;
    private String weeksmin = null;
    private String weeksmax = null;
    private String from = null;
    private String to = null;
    private String studycompletedBeginning = null;
    private String studycompletedMiddle = null;
    private String studycompletedEnd = null;
    private String worktypeP = null;
    private String worktypeR = null;
    private String worktypeW = null;
    private String worktypeN = null;
    private String language1 = null;
    private String language1level = null;
    private String language1or = null;
    private String language2 = null;
    private String language2level = null;
    private String language2or = null;
    private String language3 = null;
    private String language3level = null;
    private String currency = null;
    private String payment = null;
    private String paymentfrequency = null;
    private String deduction = null;
    private String lodging = null;
    private String lodgingcost = null;
    private String lodgingcostfrequency = null;
    private String livingcost = null;
    private String livingcostfrequency = null;
    private String nohardcopies = null;
    private String status = null;
    private String period2From = null;
    private String period2To = null;
    private String holidaysFrom = null;
    private String holidaysTo = null;
    private String additionalInfo = null;
    private String shared = null;
    private String lastModified = null;
    private String nsFirstName = null;
    private String nsLastName = null;

    // =========================================================================
    // DTO Setters & Getters
    // =========================================================================

    public void setRefNo(final String refNo) {
        this.refNo = refNo;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setDeadline(final String deadline) {
        this.deadline = deadline;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setEmployer(final String employer) {
        this.employer = employer;
    }

    public String getEmployer() {
        return employer;
    }

    public void setAddress1(final String address1) {
        this.address1 = address1;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress2(final String address2) {
        this.address2 = address2;
    }

    public String getAddress2() {
        return address2;
    }

    public void setPostbox(final String postbox) {
        this.postbox = postbox;
    }

    public String getPostbox() {
        return postbox;
    }

    public void setPostalcode(final String postalcode) {
        this.postalcode = postalcode;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setWorkplace(final String workplace) {
        this.workplace = workplace;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setBusiness(final String business) {
        this.business = business;
    }

    public String getBusiness() {
        return business;
    }

    public void setResponsible(final String responsible) {
        this.responsible = responsible;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setAirport(final String airport) {
        this.airport = airport;
    }

    public String getAirport() {
        return airport;
    }

    public void setTransport(final String transport) {
        this.transport = transport;
    }

    public String getTransport() {
        return transport;
    }

    public void setEmployees(final String employees) {
        this.employees = employees;
    }

    public String getEmployees() {
        return employees;
    }

    public void setHoursweekly(final String hoursweekly) {
        this.hoursweekly = hoursweekly;
    }

    public String getHoursweekly() {
        return hoursweekly;
    }

    public void setHoursdaily(final String hoursdaily) {
        this.hoursdaily = hoursdaily;
    }

    public String getHoursdaily() {
        return hoursdaily;
    }

    public void setCanteen(final String canteen) {
        this.canteen = canteen;
    }

    public String getCanteen() {
        return canteen;
    }

    public void setFaculty(final String faculty) {
        this.faculty = faculty;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setSpecialization(final String specialization) {
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setTrainingrequired(final String trainingrequired) {
        this.trainingrequired = trainingrequired;
    }

    public String getTrainingrequired() {
        return trainingrequired;
    }

    public void setOtherrequirements(final String otherrequirements) {
        this.otherrequirements = otherrequirements;
    }

    public String getOtherrequirements() {
        return otherrequirements;
    }

    public void setWorkkind(final String workkind) {
        this.workkind = workkind;
    }

    public String getWorkkind() {
        return workkind;
    }

    public void setWeeksmin(final String weeksmin) {
        this.weeksmin = weeksmin;
    }

    public String getWeeksmin() {
        return weeksmin;
    }

    public void setWeeksmax(final String weeksmax) {
        this.weeksmax = weeksmax;
    }

    public String getWeeksmax() {
        return weeksmax;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setTo(final String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setStudycompletedBeginning(final String studycompletedBeginning) {
        this.studycompletedBeginning = studycompletedBeginning;
    }

    public String getStudycompletedBeginning() {
        return studycompletedBeginning;
    }

    public void setStudycompletedMiddle(final String studycompletedMiddle) {
        this.studycompletedMiddle = studycompletedMiddle;
    }

    public String getStudycompletedMiddle() {
        return studycompletedMiddle;
    }

    public void setStudycompletedEnd(final String studycompletedEnd) {
        this.studycompletedEnd = studycompletedEnd;
    }

    public String getStudycompletedEnd() {
        return studycompletedEnd;
    }

    public void setWorktypeP(final String worktypeP) {
        this.worktypeP = worktypeP;
    }

    public String getWorktypeP() {
        return worktypeP;
    }

    public void setWorktypeR(final String worktypeR) {
        this.worktypeR = worktypeR;
    }

    public String getWorktypeR() {
        return worktypeR;
    }

    public void setWorktypeW(final String worktypeW) {
        this.worktypeW = worktypeW;
    }

    public String getWorktypeW() {
        return worktypeW;
    }

    public void setWorktypeN(final String worktypeN) {
        this.worktypeN = worktypeN;
    }

    public String getWorktypeN() {
        return worktypeN;
    }

    public void setLanguage1(final String language1) {
        this.language1 = language1;
    }

    public String getLanguage1() {
        return language1;
    }

    public void setLanguage1level(final String language1level) {
        this.language1level = language1level;
    }

    public String getLanguage1level() {
        return language1level;
    }

    public void setLanguage1or(final String language1or) {
        this.language1or = language1or;
    }

    public String getLanguage1or() {
        return language1or;
    }

    public void setLanguage2(final String language2) {
        this.language2 = language2;
    }

    public String getLanguage2() {
        return language2;
    }

    public void setLanguage2level(final String language2level) {
        this.language2level = language2level;
    }

    public String getLanguage2level() {
        return language2level;
    }

    public void setLanguage2or(final String language2or) {
        this.language2or = language2or;
    }

    public String getLanguage2or() {
        return language2or;
    }

    public void setLanguage3(final String language3) {
        this.language3 = language3;
    }

    public String getLanguage3() {
        return language3;
    }

    public void setLanguage3level(final String language3level) {
        this.language3level = language3level;
    }

    public String getLanguage3level() {
        return language3level;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setPayment(final String payment) {
        this.payment = payment;
    }

    public String getPayment() {
        return payment;
    }

    public void setPaymentfrequency(final String paymentfrequency) {
        this.paymentfrequency = paymentfrequency;
    }

    public String getPaymentfrequency() {
        return paymentfrequency;
    }

    public void setDeduction(final String deduction) {
        this.deduction = deduction;
    }

    public String getDeduction() {
        return deduction;
    }

    public void setLodging(final String lodging) {
        this.lodging = lodging;
    }

    public String getLodging() {
        return lodging;
    }

    public void setLodgingcost(final String lodgingcost) {
        this.lodgingcost = lodgingcost;
    }

    public String getLodgingcost() {
        return lodgingcost;
    }

    public void setLodgingcostfrequency(final String lodgingcostfrequency) {
        this.lodgingcostfrequency = lodgingcostfrequency;
    }

    public String getLodgingcostfrequency() {
        return lodgingcostfrequency;
    }

    public void setLivingcost(final String livingcost) {
        this.livingcost = livingcost;
    }

    public String getLivingcost() {
        return livingcost;
    }

    public void setLivingcostfrequency(final String livingcostfrequency) {
        this.livingcostfrequency = livingcostfrequency;
    }

    public String getLivingcostfrequency() {
        return livingcostfrequency;
    }

    public void setNohardcopies(final String nohardcopies) {
        this.nohardcopies = nohardcopies;
    }

    public String getNohardcopies() {
        return nohardcopies;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setPeriod2From(final String period2From) {
        this.period2From = period2From;
    }

    public String getPeriod2From() {
        return period2From;
    }

    public void setPeriod2To(final String period2To) {
        this.period2To = period2To;
    }

    public String getPeriod2To() {
        return period2To;
    }

    public void setHolidaysFrom(final String holidaysFrom) {
        this.holidaysFrom = holidaysFrom;
    }

    public String getHolidaysFrom() {
        return holidaysFrom;
    }

    public void setHolidaysTo(final String holidaysTo) {
        this.holidaysTo = holidaysTo;
    }

    public String getHolidaysTo() {
        return holidaysTo;
    }

    public void setAdditionalInfo(final String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setShared(final String shared) {
        this.shared = shared;
    }

    public String getShared() {
        return shared;
    }

    public void setLastModified(final String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setNsFirstName(final String nsFirstName) {
        this.nsFirstName = nsFirstName;
    }

    public String getNsFirstName() {
        return nsFirstName;
    }

    public void setNsLastName(final String nsLastName) {
        this.nsLastName = nsLastName;
    }

    public String getNsLastName() {
        return nsLastName;
    }
}
