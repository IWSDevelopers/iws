/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.entities.IW3OffersEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.entities;

import net.iaeste.iws.api.constants.IWSConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@NamedQueries(@NamedQuery(name = "offers.findAll",
        query = "select o from IW3OffersEntity o " +
                "order by o.offerid asc"))
@Entity
@Table(name = "offers")
public class IW3OffersEntity implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @Column(name = "offerid", nullable = false, length = 10)
    private Integer offerid = null;

    @Column(name = "groupid", nullable = false, length = 10)
    private Integer groupid = null;

    @Column(name = "archive", length = 10)
    private Integer archive = null;

    @Column(name = "countryid", nullable = false, length = 2)
    private String countryid = null;

    @Column(name = "systemrefno", length = 10)
    private String systemrefno = null;

    @Column(name = "localrefno", length = 2147483647)
    private String localrefno = null;

    @Column(name = "exchangeyear", length = 10)
    private Integer exchangeyear = null;

    //@Temporal(TemporalType.DATE)
    @Column(name = "deadline")
    private Date deadline = null;

    //@Temporal(TemporalType.DATE)
    @Column(name = "expire")
    private Date expire = null;

    @Column(name = "employername", length = 2147483647)
    private String employername = null;

    @Column(name = "employeraddress1", length = 2147483647)
    private String employeraddress1 = null;

    @Column(name = "employeraddress2", length = 2147483647)
    private String employeraddress2 = null;

    @Column(name = "workplace", length = 2147483647)
    private String workplace = null;

    @Column(name = "business", length = 2147483647)
    private String business = null;

    @Column(name = "responsible", length = 2147483647)
    private String responsible = null;

    @Column(name = "airport", length = 2147483647)
    private String airport = null;

    @Column(name = "transport", length = 2147483647)
    private String transport = null;

    @Column(name = "old_employees", length = 10)
    private Integer oldEmployees = null;

    @Column(name = "hoursweekly", length = 8, precision = 8)
    private Float hoursweekly = null;

    @Column(name = "hoursdaily", length = 8, precision = 8)
    private Float hoursdaily = null;

    @ManyToOne
    @JoinColumn(name = "facultyid", referencedColumnName = "facultyid", nullable = false)
    private IW3FacultiesEntity faculty = null;

    @Column(name = "facultyother", length = 2147483647)
    private String facultyother = null;

    @Column(name = "specialization", length = 2147483647)
    private String specialization = null;

    @Column(name = "language1", length = 2147483647)
    private String language1 = null;

    @Column(name = "language1level", length = 10)
    private Integer language1Level = null;

    @Column(name = "language1or", length = 1)
    private Boolean language1Or = null;

    @Column(name = "language2", length = 2147483647)
    private String language2 = null;

    @Column(name = "language2level", length = 10)
    private Integer language2Level = null;

    @Column(name = "language2or", length = 1)
    private Boolean language2Or = null;

    @Column(name = "language3", length = 2147483647)
    private String language3 = null;

    @Column(name = "language3level", length = 10)
    private Integer language3Level = null;

    @Column(name = "otherrequirements", length = 2147483647)
    private String otherrequirements = null;

    @Column(name = "trainingrequired", length = 2147483647)
    private String trainingrequired = null;

    @Column(name = "gender", length = 1)
    private String gender = null;

    @Column(name = "workkind", length = 2147483647)
    private String workkind = null;

    @Column(name = "worktype", length = 1)
    private String worktype = null;

    @Column(name = "weeksmin", length = 10)
    private Integer weeksmin = null;

    @Column(name = "weeksmax", length = 10)
    private Integer weeksmax = null;

    //@Temporal(TemporalType.DATE)
    @Column(name = "fromdate")
    private Date fromdate = null;

    //@Temporal(TemporalType.DATE)
    @Column(name = "todate")
    private Date todate = null;

    @Column(name = "studycompleted", length = 1)
    private String studycompleted = null;

    @Column(name = "studyrequired", length = 1)
    private String studyrequired = null;

    @Column(name = "payment", length = 10)
    private Integer payment = null;

    @Column(name = "paymentfrequency", length = 1)
    private String paymentfrequency = null;

    @Column(name = "deduction", length = 2147483647)
    private String deduction = null;

    @Column(name = "lodging", length = 2147483647)
    private String lodging = null;

    @Column(name = "canteen", length = 1)
    private Boolean canteen = null;

    @Column(name = "lodgingcost", length = 10)
    private Integer lodgingcost = null;

    @Column(name = "lodgingcostfrequency", length = 1)
    private String lodgingcostfrequency = null;

    @Column(name = "livingcost", length = 10)
    private Integer livingcost = null;

    @Column(name = "livingcostfrequency", length = 1)
    private String livingcostfrequency = null;

    @Column(name = "comment", length = 2147483647)
    private String comment = null;

    @Column(name = "worktype_p", length = 1)
    private Boolean worktypeP = null;

    @Column(name = "worktype_r", length = 1)
    private Boolean worktypeR = null;

    @Column(name = "worktype_w", length = 1)
    private Boolean worktypeW = null;

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "answered")
    private Date answered = null;

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "published")
    private Date published = null;

    @Column(name = "publishedby", length = 10)
    private Integer publishedby = null;

    @Column(name = "employees", length = 2147483647)
    private String employees = null;

    @Column(name = "nomination", length = 1)
    private Boolean nomination = null;

    @Column(name = "requirestudent", length = 1)
    private Boolean requirestudent = null;

    @Column(name = "website", length = 2147483647)
    private String website = null;

    @Column(name = "worktype_n", length = 1)
    private Boolean worktypeN = null;

    @Column(name = "nomination_e", length = 1)
    private Boolean nominationE = null;

    @Column(name = "nomination_h", length = 1)
    private Boolean nominationH = null;

    @Column(name = "nominationandor", length = 1)
    private Boolean nominationandor = null;

    @Column(name = "nohardcopies", length = 10)
    private Integer nohardcopies = null;

    @Column(name = "status", length = 1)
    private String status = null;

    @Column(name = "offeryear", length = 10)
    private Integer offeryear = null;

    @Column(name = "isarchived", length = 10)
    private Integer isarchived = null;

    @Column(name = "studycompleted_b", nullable = false, length = 1)
    private Boolean studycompletedB = null;

    @Column(name = "studycompleted_m", nullable = false, length = 1)
    private Boolean studycompletedM = null;

    @Column(name = "studycompleted_e", nullable = false, length = 1)
    private Boolean studycompletedE = null;

    //@Temporal(TemporalType.DATE)
    @Column(name = "fromdate2")
    private Date fromdate2 = null;

    //@Temporal(TemporalType.DATE)
    @Column(name = "todate2")
    private Date todate2 = null;

    //@Temporal(TemporalType.DATE)
    @Column(name = "holidaysfromdate")
    private Date holidaysfromdate = null;

    //@Temporal(TemporalType.DATE)
    @Column(name = "holidaystodate")
    private Date holidaystodate = null;

    @Column(name = "modifiedby", length = 10)
    private Integer modifiedby = null;

    @Column(name = "createdby", length = 10)
    private Integer createdby = null;

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = null;

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = null;

    // =========================================================================
    // IW3 Entity Setters & Getters
    // =========================================================================

    public void setOfferid(final Integer offerid) {
        this.offerid = offerid;
    }

    public Integer getOfferid() {
        return offerid;
    }

    public void setGroupid(final Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setArchive(final Integer archive) {
        this.archive = archive;
    }

    public Integer getArchive() {
        return archive;
    }

    public void setCountryid(final String countryid) {
        this.countryid = countryid;
    }

    public String getCountryid() {
        return countryid;
    }

    public void setSystemrefno(final String systemrefno) {
        this.systemrefno = systemrefno;
    }

    public String getSystemrefno() {
        return systemrefno;
    }

    public void setLocalrefno(final String localrefno) {
        this.localrefno = localrefno;
    }

    public String getLocalrefno() {
        return localrefno;
    }

    public void setExchangeyear(final Integer exchangeyear) {
        this.exchangeyear = exchangeyear;
    }

    public Integer getExchangeyear() {
        return exchangeyear;
    }

    public void setDeadline(final Date deadline) {
        this.deadline = deadline;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setExpire(final Date expire) {
        this.expire = expire;
    }

    public Date getExpire() {
        return expire;
    }

    public void setEmployername(final String employername) {
        this.employername = employername;
    }

    public String getEmployername() {
        return employername;
    }

    public void setEmployeraddress1(final String employeraddress1) {
        this.employeraddress1 = employeraddress1;
    }

    public String getEmployeraddress1() {
        return employeraddress1;
    }

    public void setEmployeraddress2(final String employeraddress2) {
        this.employeraddress2 = employeraddress2;
    }

    public String getEmployeraddress2() {
        return employeraddress2;
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

    public void setOldEmployees(final Integer oldEmployees) {
        this.oldEmployees = oldEmployees;
    }

    public Integer getOldEmployees() {
        return oldEmployees;
    }

    public void setHoursweekly(final Float hoursweekly) {
        this.hoursweekly = hoursweekly;
    }

    public Float getHoursweekly() {
        return hoursweekly;
    }

    public void setHoursdaily(final Float hoursdaily) {
        this.hoursdaily = hoursdaily;
    }

    public Float getHoursdaily() {
        return hoursdaily;
    }

    public void setFaculty(final IW3FacultiesEntity faculty) {
        this.faculty = faculty;
    }

    public IW3FacultiesEntity getFaculty() {
        return faculty;
    }

    public void setFacultyother(final String facultyother) {
        this.facultyother = facultyother;
    }

    public String getFacultyother() {
        return facultyother;
    }

    public void setSpecialization(final String specialization) {
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setLanguage1(final String language1) {
        this.language1 = language1;
    }

    public String getLanguage1() {
        return language1;
    }

    public void setLanguage1Level(final Integer language1Level) {
        this.language1Level = language1Level;
    }

    public Integer getLanguage1Level() {
        return language1Level;
    }

    public void setLanguage1Or(final Boolean language1Or) {
        this.language1Or = language1Or;
    }

    public Boolean getLanguage1Or() {
        return language1Or;
    }

    public void setLanguage2(final String language2) {
        this.language2 = language2;
    }

    public String getLanguage2() {
        return language2;
    }

    public void setLanguage2Level(final Integer language2Level) {
        this.language2Level = language2Level;
    }

    public Integer getLanguage2Level() {
        return language2Level;
    }

    public void setLanguage2Or(final Boolean language2Or) {
        this.language2Or = language2Or;
    }

    public Boolean getLanguage2Or() {
        return language2Or;
    }

    public void setLanguage3(final String language3) {
        this.language3 = language3;
    }

    public String getLanguage3() {
        return language3;
    }

    public void setLanguage3Level(final Integer language3Level) {
        this.language3Level = language3Level;
    }

    public Integer getLanguage3Level() {
        return language3Level;
    }

    public void setOtherrequirements(final String otherrequirements) {
        this.otherrequirements = otherrequirements;
    }

    public String getOtherrequirements() {
        return otherrequirements;
    }

    public void setTrainingrequired(final String trainingrequired) {
        this.trainingrequired = trainingrequired;
    }

    public String getTrainingrequired() {
        return trainingrequired;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setWorkkind(final String workkind) {
        this.workkind = workkind;
    }

    public String getWorkkind() {
        return workkind;
    }

    public void setWorktype(final String worktype) {
        this.worktype = worktype;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWeeksmin(final Integer weeksmin) {
        this.weeksmin = weeksmin;
    }

    public Integer getWeeksmin() {
        return weeksmin;
    }

    public void setWeeksmax(final Integer weeksmax) {
        this.weeksmax = weeksmax;
    }

    public Integer getWeeksmax() {
        return weeksmax;
    }

    public void setFromdate(final Date fromdate) {
        this.fromdate = fromdate;
    }

    public Date getFromdate() {
        return fromdate;
    }

    public void setTodate(final Date todate) {
        this.todate = todate;
    }

    public Date getTodate() {
        return todate;
    }

    public void setStudycompleted(final String studycompleted) {
        this.studycompleted = studycompleted;
    }

    public String getStudycompleted() {
        return studycompleted;
    }

    public void setStudyrequired(final String studyrequired) {
        this.studyrequired = studyrequired;
    }

    public String getStudyrequired() {
        return studyrequired;
    }

    public void setPayment(final Integer payment) {
        this.payment = payment;
    }

    public Integer getPayment() {
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

    public void setCanteen(final Boolean canteen) {
        this.canteen = canteen;
    }

    public Boolean getCanteen() {
        return canteen;
    }

    public void setLodgingcost(final Integer lodgingcost) {
        this.lodgingcost = lodgingcost;
    }

    public Integer getLodgingcost() {
        return lodgingcost;
    }

    public void setLodgingcostfrequency(final String lodgingcostfrequency) {
        this.lodgingcostfrequency = lodgingcostfrequency;
    }

    public String getLodgingcostfrequency() {
        return lodgingcostfrequency;
    }

    public void setLivingcost(final Integer livingcost) {
        this.livingcost = livingcost;
    }

    public Integer getLivingcost() {
        return livingcost;
    }

    public void setLivingcostfrequency(final String livingcostfrequency) {
        this.livingcostfrequency = livingcostfrequency;
    }

    public String getLivingcostfrequency() {
        return livingcostfrequency;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setWorktypeP(final Boolean worktypeP) {
        this.worktypeP = worktypeP;
    }

    public Boolean getWorktypeP() {
        return worktypeP;
    }

    public void setWorktypeR(final Boolean worktypeR) {
        this.worktypeR = worktypeR;
    }

    public Boolean getWorktypeR() {
        return worktypeR;
    }

    public void setWorktypeW(final Boolean worktypeW) {
        this.worktypeW = worktypeW;
    }

    public Boolean getWorktypeW() {
        return worktypeW;
    }

    public void setAnswered(final Date answered) {
        this.answered = answered;
    }

    public Date getAnswered() {
        return answered;
    }

    public void setPublished(final Date published) {
        this.published = published;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublishedby(final Integer publishedby) {
        this.publishedby = publishedby;
    }

    public Integer getPublishedby() {
        return publishedby;
    }

    public void setEmployees(final String employees) {
        this.employees = employees;
    }

    public String getEmployees() {
        return employees;
    }

    public void setNomination(final Boolean nomination) {
        this.nomination = nomination;
    }

    public Boolean getNomination() {
        return nomination;
    }

    public void setRequirestudent(final Boolean requirestudent) {
        this.requirestudent = requirestudent;
    }

    public Boolean getRequirestudent() {
        return requirestudent;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setWorktypeN(final Boolean worktypeN) {
        this.worktypeN = worktypeN;
    }

    public Boolean getWorktypeN() {
        return worktypeN;
    }

    public void setNominationE(final Boolean nominationE) {
        this.nominationE = nominationE;
    }

    public Boolean getNominationE() {
        return nominationE;
    }

    public void setNominationH(final Boolean nominationH) {
        this.nominationH = nominationH;
    }

    public Boolean getNominationH() {
        return nominationH;
    }

    public void setNominationandor(final Boolean nominationandor) {
        this.nominationandor = nominationandor;
    }

    public Boolean getNominationandor() {
        return nominationandor;
    }

    public void setNohardcopies(final Integer nohardcopies) {
        this.nohardcopies = nohardcopies;
    }

    public Integer getNohardcopies() {
        return nohardcopies;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setOfferyear(final Integer offeryear) {
        this.offeryear = offeryear;
    }

    public Integer getOfferyear() {
        return offeryear;
    }

    public void setIsarchived(final Integer isarchived) {
        this.isarchived = isarchived;
    }

    public Integer getIsarchived() {
        return isarchived;
    }

    public void setStudycompletedB(final Boolean studycompletedB) {
        this.studycompletedB = studycompletedB;
    }

    public Boolean getStudycompletedB() {
        return studycompletedB;
    }

    public void setStudycompletedM(final Boolean studycompletedM) {
        this.studycompletedM = studycompletedM;
    }

    public Boolean getStudycompletedM() {
        return studycompletedM;
    }

    public void setStudycompletedE(final Boolean studycompletedE) {
        this.studycompletedE = studycompletedE;
    }

    public Boolean getStudycompletedE() {
        return studycompletedE;
    }

    public void setFromdate2(final Date fromdate2) {
        this.fromdate2 = fromdate2;
    }

    public Date getFromdate2() {
        return fromdate2;
    }

    public void setTodate2(final Date todate2) {
        this.todate2 = todate2;
    }

    public Date getTodate2() {
        return todate2;
    }

    public void setHolidaysfromdate(final Date holidaysfromdate) {
        this.holidaysfromdate = holidaysfromdate;
    }

    public Date getHolidaysfromdate() {
        return holidaysfromdate;
    }

    public void setHolidaystodate(final Date holidaystodate) {
        this.holidaystodate = holidaystodate;
    }

    public Date getHolidaystodate() {
        return holidaystodate;
    }

    public void setModifiedby(final Integer modifiedby) {
        this.modifiedby = modifiedby;
    }

    public Integer getModifiedby() {
        return modifiedby;
    }

    public void setCreatedby(final Integer createdby) {
        this.createdby = createdby;
    }

    public Integer getCreatedby() {
        return createdby;
    }

    public void setModified(final Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }
}
