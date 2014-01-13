/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.entities.IW3StudentsEntity
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Entity
@Table(name = "students")
public class IW3StudentsEntity implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @Column(name = "studentid", nullable = false, length = 10)
    private Integer studentid;

    @Column(name = "refno", nullable = false, length = 2147483647)
    private String refno;

    @Column(name = "groupid", length = 10)
    private Integer groupid;

    @Column(name = "firstname", nullable = false, length = 2147483647)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 2147483647)
    private String lastname;

    @Column(name = "street1", length = 2147483647)
    private String street1;

    @Column(name = "street2", length = 2147483647)
    private String street2;

    @Column(name = "zip", length = 2147483647)
    private String zip;

    @Column(name = "city", length = 2147483647)
    private String city;

    @Column(name = "countryid", nullable = false, length = 2)
    private String countryid;

    @Column(name = "phone", length = 2147483647)
    private String phone;

    @Column(name = "termstreet1", length = 2147483647)
    private String termstreet1;

    @Column(name = "termstreet2", length = 2147483647)
    private String termstreet2;

    @Column(name = "termzip", length = 2147483647)
    private String termzip;

    @Column(name = "termcity", length = 2147483647)
    private String termcity;

    @Column(name = "termcountry", length = 2)
    private String termcountry;

    @Column(name = "termphone", length = 2147483647)
    private String termphone;

    @Column(name = "email", length = 2147483647)
    private String email;

    @Column(name = "birthday", length = 13)
    private Date birthday;

    @Column(name = "birthplace", length = 2147483647)
    private String birthplace;

    @Column(name = "nationality", nullable = false, length = 2)
    private String nationality;

    @Column(name = "passportnumber", length = 2147483647)
    private String passportnumber;

    @Column(name = "passportissued", length = 2147483647)
    private String passportissued;

    @Column(name = "passportvalidity", length = 13)
    private Date passportvalidity;

    @Column(name = "gender", length = 1)
    private String gender;

    @Column(name = "maritalstatus", length = 1)
    private String maritalstatus;

    @Column(name = "medicallyfit", length = 2147483647)
    private String medicallyfit;

    @Column(name = "university", length = 2147483647)
    private String university;

    @Column(name = "facultyid", length = 10)
    private Integer facultyid;

    @Column(name = "specialization", length = 2147483647)
    private String specialization;

    @Column(name = "studycompleted", length = 1)
    private String studycompleted;

    @Column(name = "studyrequired", length = 1)
    private String studyrequired;

    @Column(name = "language1", length = 2147483647)
    private String language1;

    @Column(name = "language1level", length = 10)
    private Integer language1Level;

    @Column(name = "language2", length = 2147483647)
    private String language2;

    @Column(name = "language2level", length = 10)
    private Integer language2Level;

    @Column(name = "language3", length = 2147483647)
    private String language3;

    @Column(name = "language3level", length = 10)
    private Integer language3Level;

    @Column(name = "fromdate", length = 13)
    private Date fromdate;

    @Column(name = "todate", length = 13)
    private Date todate;

    @Column(name = "requirelodging", length = 1)
    private Boolean requirelodging;

    @Column(name = "trainingreport", length = 1)
    private Boolean trainingreport;

    @Column(name = "comment", length = 2147483647)
    private String comment;

    @Column(name = "filepicture", length = 10)
    private Integer filepicture;

    @Column(name = "filecv", length = 10)
    private Integer filecv;

    @Column(name = "filecover", length = 10)
    private Integer filecover;

    @Column(name = "fileother", length = 2147483647)
    private String fileother;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "logincode", length = 2147483647)
    private String logincode;

    @Column(name = "complete", length = 1)
    private Boolean complete;

    @Column(name = "alternativeemail", length = 2147483647)
    private String alternativeemail;

    @Column(name = "modifiedby", length = 10)
    private Integer modifiedby;

    @Column(name = "createdby", length = 10)
    private Integer createdby;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    // =========================================================================
    // IW3 Entity Setters & Getters
    // =========================================================================

    public void setStudentid(final Integer studentid) {
        this.studentid = studentid;
    }

    public Integer getStudentid() {
        return studentid;
    }

    public void setRefno(final String refno) {
        this.refno = refno;
    }

    public String getRefno() {
        return refno;
    }

    public void setGroupid(final Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setStreet1(final String street1) {
        this.street1 = street1;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet2(final String street2) {
        this.street2 = street2;
    }

    public String getStreet2() {
        return street2;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCountryid(final String countryid) {
        this.countryid = countryid;
    }

    public String getCountryid() {
        return countryid;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setTermstreet1(final String termstreet1) {
        this.termstreet1 = termstreet1;
    }

    public String getTermstreet1() {
        return termstreet1;
    }

    public void setTermstreet2(final String termstreet2) {
        this.termstreet2 = termstreet2;
    }

    public String getTermstreet2() {
        return termstreet2;
    }

    public void setTermzip(final String termzip) {
        this.termzip = termzip;
    }

    public String getTermzip() {
        return termzip;
    }

    public void setTermcity(final String termcity) {
        this.termcity = termcity;
    }

    public String getTermcity() {
        return termcity;
    }

    public void setTermcountry(final String termcountry) {
        this.termcountry = termcountry;
    }

    public String getTermcountry() {
        return termcountry;
    }

    public void setTermphone(final String termphone) {
        this.termphone = termphone;
    }

    public String getTermphone() {
        return termphone;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthplace(final String birthplace) {
        this.birthplace = birthplace;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setPassportnumber(final String passportnumber) {
        this.passportnumber = passportnumber;
    }

    public String getPassportnumber() {
        return passportnumber;
    }

    public void setPassportissued(final String passportissued) {
        this.passportissued = passportissued;
    }

    public String getPassportissued() {
        return passportissued;
    }

    public void setPassportvalidity(final Date passportvalidity) {
        this.passportvalidity = passportvalidity;
    }

    public Date getPassportvalidity() {
        return passportvalidity;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setMaritalstatus(final String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMedicallyfit(final String medicallyfit) {
        this.medicallyfit = medicallyfit;
    }

    public String getMedicallyfit() {
        return medicallyfit;
    }

    public void setUniversity(final String university) {
        this.university = university;
    }

    public String getUniversity() {
        return university;
    }

    public void setFacultyid(final Integer facultyid) {
        this.facultyid = facultyid;
    }

    public Integer getFacultyid() {
        return facultyid;
    }

    public void setSpecialization(final String specialization) {
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
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

    public void setRequirelodging(final Boolean requirelodging) {
        this.requirelodging = requirelodging;
    }

    public Boolean getRequirelodging() {
        return requirelodging;
    }

    public void setTrainingreport(final Boolean trainingreport) {
        this.trainingreport = trainingreport;
    }

    public Boolean getTrainingreport() {
        return trainingreport;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setFilepicture(final Integer filepicture) {
        this.filepicture = filepicture;
    }

    public Integer getFilepicture() {
        return filepicture;
    }

    public void setFilecv(final Integer filecv) {
        this.filecv = filecv;
    }

    public Integer getFilecv() {
        return filecv;
    }

    public void setFilecover(final Integer filecover) {
        this.filecover = filecover;
    }

    public Integer getFilecover() {
        return filecover;
    }

    public void setFileother(final String fileother) {
        this.fileother = fileother;
    }

    public String getFileother() {
        return fileother;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setLogincode(final String logincode) {
        this.logincode = logincode;
    }

    public String getLogincode() {
        return logincode;
    }

    public void setComplete(final Boolean complete) {
        this.complete = complete;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setAlternativeemail(final String alternativeemail) {
        this.alternativeemail = alternativeemail;
    }

    public String getAlternativeemail() {
        return alternativeemail;
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
