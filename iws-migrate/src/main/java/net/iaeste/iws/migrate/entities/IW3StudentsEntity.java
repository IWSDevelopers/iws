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

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@Entity
@javax.persistence.Table(name = "students", schema = "public", catalog = "iw3")
public class IW3StudentsEntity {
    private Integer studentid;

    @Id
    @javax.persistence.Column(name = "studentid", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getStudentid() {
        return studentid;
    }

    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }

    private String refno;

    @Basic
    @javax.persistence.Column(name = "refno", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    private Integer groupid;

    @Basic
    @javax.persistence.Column(name = "groupid", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    private String firstname;

    @Basic
    @javax.persistence.Column(name = "firstname", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    private String lastname;

    @Basic
    @javax.persistence.Column(name = "lastname", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    private String street1;

    @Basic
    @javax.persistence.Column(name = "street1", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    private String street2;

    @Basic
    @javax.persistence.Column(name = "street2", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    private String zip;

    @Basic
    @javax.persistence.Column(name = "zip", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    private String city;

    @Basic
    @javax.persistence.Column(name = "city", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String countryid;

    @Basic
    @javax.persistence.Column(name = "countryid", nullable = false, insertable = true, updatable = true, length = 2, precision = 0)
    public String getCountryid() {
        return countryid;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid;
    }

    private String phone;

    @Basic
    @javax.persistence.Column(name = "phone", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String termstreet1;

    @Basic
    @javax.persistence.Column(name = "termstreet1", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getTermstreet1() {
        return termstreet1;
    }

    public void setTermstreet1(String termstreet1) {
        this.termstreet1 = termstreet1;
    }

    private String termstreet2;

    @Basic
    @javax.persistence.Column(name = "termstreet2", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getTermstreet2() {
        return termstreet2;
    }

    public void setTermstreet2(String termstreet2) {
        this.termstreet2 = termstreet2;
    }

    private String termzip;

    @Basic
    @javax.persistence.Column(name = "termzip", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getTermzip() {
        return termzip;
    }

    public void setTermzip(String termzip) {
        this.termzip = termzip;
    }

    private String termcity;

    @Basic
    @javax.persistence.Column(name = "termcity", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getTermcity() {
        return termcity;
    }

    public void setTermcity(String termcity) {
        this.termcity = termcity;
    }

    private String termcountry;

    @Basic
    @javax.persistence.Column(name = "termcountry", nullable = true, insertable = true, updatable = true, length = 2, precision = 0)
    public String getTermcountry() {
        return termcountry;
    }

    public void setTermcountry(String termcountry) {
        this.termcountry = termcountry;
    }

    private String termphone;

    @Basic
    @javax.persistence.Column(name = "termphone", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getTermphone() {
        return termphone;
    }

    public void setTermphone(String termphone) {
        this.termphone = termphone;
    }

    private String email;

    @Basic
    @javax.persistence.Column(name = "email", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private Date birthday;

    @Basic
    @javax.persistence.Column(name = "birthday", nullable = true, insertable = true, updatable = true, length = 13, precision = 0)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    private String birthplace;

    @Basic
    @javax.persistence.Column(name = "birthplace", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    private String nationality;

    @Basic
    @javax.persistence.Column(name = "nationality", nullable = false, insertable = true, updatable = true, length = 2, precision = 0)
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    private String passportnumber;

    @Basic
    @javax.persistence.Column(name = "passportnumber", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getPassportnumber() {
        return passportnumber;
    }

    public void setPassportnumber(String passportnumber) {
        this.passportnumber = passportnumber;
    }

    private String passportissued;

    @Basic
    @javax.persistence.Column(name = "passportissued", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getPassportissued() {
        return passportissued;
    }

    public void setPassportissued(String passportissued) {
        this.passportissued = passportissued;
    }

    private Date passportvalidity;

    @Basic
    @javax.persistence.Column(name = "passportvalidity", nullable = true, insertable = true, updatable = true, length = 13, precision = 0)
    public Date getPassportvalidity() {
        return passportvalidity;
    }

    public void setPassportvalidity(Date passportvalidity) {
        this.passportvalidity = passportvalidity;
    }

    private String gender;

    @Basic
    @javax.persistence.Column(name = "gender", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String maritalstatus;

    @Basic
    @javax.persistence.Column(name = "maritalstatus", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    private String medicallyfit;

    @Basic
    @javax.persistence.Column(name = "medicallyfit", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getMedicallyfit() {
        return medicallyfit;
    }

    public void setMedicallyfit(String medicallyfit) {
        this.medicallyfit = medicallyfit;
    }

    private String university;

    @Basic
    @javax.persistence.Column(name = "university", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    private Integer facultyid;

    @Basic
    @javax.persistence.Column(name = "facultyid", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getFacultyid() {
        return facultyid;
    }

    public void setFacultyid(Integer facultyid) {
        this.facultyid = facultyid;
    }

    private String specialization;

    @Basic
    @javax.persistence.Column(name = "specialization", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    private String studycompleted;

    @Basic
    @javax.persistence.Column(name = "studycompleted", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    public String getStudycompleted() {
        return studycompleted;
    }

    public void setStudycompleted(String studycompleted) {
        this.studycompleted = studycompleted;
    }

    private String studyrequired;

    @Basic
    @javax.persistence.Column(name = "studyrequired", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    public String getStudyrequired() {
        return studyrequired;
    }

    public void setStudyrequired(String studyrequired) {
        this.studyrequired = studyrequired;
    }

    private String language1;

    @Basic
    @javax.persistence.Column(name = "language1", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getLanguage1() {
        return language1;
    }

    public void setLanguage1(String language1) {
        this.language1 = language1;
    }

    private Integer language1Level;

    @Basic
    @javax.persistence.Column(name = "language1level", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getLanguage1Level() {
        return language1Level;
    }

    public void setLanguage1Level(Integer language1Level) {
        this.language1Level = language1Level;
    }

    private String language2;

    @Basic
    @javax.persistence.Column(name = "language2", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getLanguage2() {
        return language2;
    }

    public void setLanguage2(String language2) {
        this.language2 = language2;
    }

    private Integer language2Level;

    @Basic
    @javax.persistence.Column(name = "language2level", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getLanguage2Level() {
        return language2Level;
    }

    public void setLanguage2Level(Integer language2Level) {
        this.language2Level = language2Level;
    }

    private String language3;

    @Basic
    @javax.persistence.Column(name = "language3", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getLanguage3() {
        return language3;
    }

    public void setLanguage3(String language3) {
        this.language3 = language3;
    }

    private Integer language3Level;

    @Basic
    @javax.persistence.Column(name = "language3level", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getLanguage3Level() {
        return language3Level;
    }

    public void setLanguage3Level(Integer language3Level) {
        this.language3Level = language3Level;
    }

    private Date fromdate;

    @Basic
    @javax.persistence.Column(name = "fromdate", nullable = true, insertable = true, updatable = true, length = 13, precision = 0)
    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    private Date todate;

    @Basic
    @javax.persistence.Column(name = "todate", nullable = true, insertable = true, updatable = true, length = 13, precision = 0)
    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    private Boolean requirelodging;

    @Basic
    @javax.persistence.Column(name = "requirelodging", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    public Boolean getRequirelodging() {
        return requirelodging;
    }

    public void setRequirelodging(Boolean requirelodging) {
        this.requirelodging = requirelodging;
    }

    private Boolean trainingreport;

    @Basic
    @javax.persistence.Column(name = "trainingreport", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    public Boolean getTrainingreport() {
        return trainingreport;
    }

    public void setTrainingreport(Boolean trainingreport) {
        this.trainingreport = trainingreport;
    }

    private String comment;

    @Basic
    @javax.persistence.Column(name = "comment", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private Integer filepicture;

    @Basic
    @javax.persistence.Column(name = "filepicture", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getFilepicture() {
        return filepicture;
    }

    public void setFilepicture(Integer filepicture) {
        this.filepicture = filepicture;
    }

    private Integer filecv;

    @Basic
    @javax.persistence.Column(name = "filecv", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getFilecv() {
        return filecv;
    }

    public void setFilecv(Integer filecv) {
        this.filecv = filecv;
    }

    private Integer filecover;

    @Basic
    @javax.persistence.Column(name = "filecover", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getFilecover() {
        return filecover;
    }

    public void setFilecover(Integer filecover) {
        this.filecover = filecover;
    }

    private String fileother;

    @Basic
    @javax.persistence.Column(name = "fileother", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getFileother() {
        return fileother;
    }

    public void setFileother(String fileother) {
        this.fileother = fileother;
    }

    private Timestamp modified;

    @Basic
    @javax.persistence.Column(name = "modified", nullable = true, insertable = true, updatable = true, length = 29, precision = 6)
    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    private Integer modifiedby;

    @Basic
    @javax.persistence.Column(name = "modifiedby", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(Integer modifiedby) {
        this.modifiedby = modifiedby;
    }

    private Timestamp created;

    @Basic
    @javax.persistence.Column(name = "created", nullable = true, insertable = true, updatable = true, length = 29, precision = 6)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    private Integer createdby;

    @Basic
    @javax.persistence.Column(name = "createdby", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Integer createdby) {
        this.createdby = createdby;
    }

    private String status;

    @Basic
    @javax.persistence.Column(name = "status", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String logincode;

    @Basic
    @javax.persistence.Column(name = "logincode", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getLogincode() {
        return logincode;
    }

    public void setLogincode(String logincode) {
        this.logincode = logincode;
    }

    private Boolean complete;

    @Basic
    @javax.persistence.Column(name = "complete", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    private String alternativeemail;

    @Basic
    @javax.persistence.Column(name = "alternativeemail", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getAlternativeemail() {
        return alternativeemail;
    }

    public void setAlternativeemail(String alternativeemail) {
        this.alternativeemail = alternativeemail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IW3StudentsEntity that = (IW3StudentsEntity) o;

        if (alternativeemail != null ? !alternativeemail.equals(that.alternativeemail) : that.alternativeemail != null)
            return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null)
            return false;
        if (birthplace != null ? !birthplace.equals(that.birthplace) : that.birthplace != null)
            return false;
        if (city != null ? !city.equals(that.city) : that.city != null)
            return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null)
            return false;
        if (complete != null ? !complete.equals(that.complete) : that.complete != null)
            return false;
        if (countryid != null ? !countryid.equals(that.countryid) : that.countryid != null)
            return false;
        if (created != null ? !created.equals(that.created) : that.created != null)
            return false;
        if (createdby != null ? !createdby.equals(that.createdby) : that.createdby != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null)
            return false;
        if (facultyid != null ? !facultyid.equals(that.facultyid) : that.facultyid != null)
            return false;
        if (filecover != null ? !filecover.equals(that.filecover) : that.filecover != null)
            return false;
        if (filecv != null ? !filecv.equals(that.filecv) : that.filecv != null)
            return false;
        if (fileother != null ? !fileother.equals(that.fileother) : that.fileother != null)
            return false;
        if (filepicture != null ? !filepicture.equals(that.filepicture) : that.filepicture != null)
            return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null)
            return false;
        if (fromdate != null ? !fromdate.equals(that.fromdate) : that.fromdate != null)
            return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null)
            return false;
        if (groupid != null ? !groupid.equals(that.groupid) : that.groupid != null)
            return false;
        if (language1 != null ? !language1.equals(that.language1) : that.language1 != null)
            return false;
        if (language1Level != null ? !language1Level.equals(that.language1Level) : that.language1Level != null)
            return false;
        if (language2 != null ? !language2.equals(that.language2) : that.language2 != null)
            return false;
        if (language2Level != null ? !language2Level.equals(that.language2Level) : that.language2Level != null)
            return false;
        if (language3 != null ? !language3.equals(that.language3) : that.language3 != null)
            return false;
        if (language3Level != null ? !language3Level.equals(that.language3Level) : that.language3Level != null)
            return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null)
            return false;
        if (logincode != null ? !logincode.equals(that.logincode) : that.logincode != null)
            return false;
        if (maritalstatus != null ? !maritalstatus.equals(that.maritalstatus) : that.maritalstatus != null)
            return false;
        if (medicallyfit != null ? !medicallyfit.equals(that.medicallyfit) : that.medicallyfit != null)
            return false;
        if (modified != null ? !modified.equals(that.modified) : that.modified != null)
            return false;
        if (modifiedby != null ? !modifiedby.equals(that.modifiedby) : that.modifiedby != null)
            return false;
        if (nationality != null ? !nationality.equals(that.nationality) : that.nationality != null)
            return false;
        if (passportissued != null ? !passportissued.equals(that.passportissued) : that.passportissued != null)
            return false;
        if (passportnumber != null ? !passportnumber.equals(that.passportnumber) : that.passportnumber != null)
            return false;
        if (passportvalidity != null ? !passportvalidity.equals(that.passportvalidity) : that.passportvalidity != null)
            return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null)
            return false;
        if (refno != null ? !refno.equals(that.refno) : that.refno != null)
            return false;
        if (requirelodging != null ? !requirelodging.equals(that.requirelodging) : that.requirelodging != null)
            return false;
        if (specialization != null ? !specialization.equals(that.specialization) : that.specialization != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null)
            return false;
        if (street1 != null ? !street1.equals(that.street1) : that.street1 != null)
            return false;
        if (street2 != null ? !street2.equals(that.street2) : that.street2 != null)
            return false;
        if (studentid != null ? !studentid.equals(that.studentid) : that.studentid != null)
            return false;
        if (studycompleted != null ? !studycompleted.equals(that.studycompleted) : that.studycompleted != null)
            return false;
        if (studyrequired != null ? !studyrequired.equals(that.studyrequired) : that.studyrequired != null)
            return false;
        if (termcity != null ? !termcity.equals(that.termcity) : that.termcity != null)
            return false;
        if (termcountry != null ? !termcountry.equals(that.termcountry) : that.termcountry != null)
            return false;
        if (termphone != null ? !termphone.equals(that.termphone) : that.termphone != null)
            return false;
        if (termstreet1 != null ? !termstreet1.equals(that.termstreet1) : that.termstreet1 != null)
            return false;
        if (termstreet2 != null ? !termstreet2.equals(that.termstreet2) : that.termstreet2 != null)
            return false;
        if (termzip != null ? !termzip.equals(that.termzip) : that.termzip != null)
            return false;
        if (todate != null ? !todate.equals(that.todate) : that.todate != null)
            return false;
        if (trainingreport != null ? !trainingreport.equals(that.trainingreport) : that.trainingreport != null)
            return false;
        if (university != null ? !university.equals(that.university) : that.university != null)
            return false;
        if (zip != null ? !zip.equals(that.zip) : that.zip != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentid != null ? studentid.hashCode() : 0;
        result = 31 * result + (refno != null ? refno.hashCode() : 0);
        result = 31 * result + (groupid != null ? groupid.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (street1 != null ? street1.hashCode() : 0);
        result = 31 * result + (street2 != null ? street2.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (countryid != null ? countryid.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (termstreet1 != null ? termstreet1.hashCode() : 0);
        result = 31 * result + (termstreet2 != null ? termstreet2.hashCode() : 0);
        result = 31 * result + (termzip != null ? termzip.hashCode() : 0);
        result = 31 * result + (termcity != null ? termcity.hashCode() : 0);
        result = 31 * result + (termcountry != null ? termcountry.hashCode() : 0);
        result = 31 * result + (termphone != null ? termphone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (birthplace != null ? birthplace.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (passportnumber != null ? passportnumber.hashCode() : 0);
        result = 31 * result + (passportissued != null ? passportissued.hashCode() : 0);
        result = 31 * result + (passportvalidity != null ? passportvalidity.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (maritalstatus != null ? maritalstatus.hashCode() : 0);
        result = 31 * result + (medicallyfit != null ? medicallyfit.hashCode() : 0);
        result = 31 * result + (university != null ? university.hashCode() : 0);
        result = 31 * result + (facultyid != null ? facultyid.hashCode() : 0);
        result = 31 * result + (specialization != null ? specialization.hashCode() : 0);
        result = 31 * result + (studycompleted != null ? studycompleted.hashCode() : 0);
        result = 31 * result + (studyrequired != null ? studyrequired.hashCode() : 0);
        result = 31 * result + (language1 != null ? language1.hashCode() : 0);
        result = 31 * result + (language1Level != null ? language1Level.hashCode() : 0);
        result = 31 * result + (language2 != null ? language2.hashCode() : 0);
        result = 31 * result + (language2Level != null ? language2Level.hashCode() : 0);
        result = 31 * result + (language3 != null ? language3.hashCode() : 0);
        result = 31 * result + (language3Level != null ? language3Level.hashCode() : 0);
        result = 31 * result + (fromdate != null ? fromdate.hashCode() : 0);
        result = 31 * result + (todate != null ? todate.hashCode() : 0);
        result = 31 * result + (requirelodging != null ? requirelodging.hashCode() : 0);
        result = 31 * result + (trainingreport != null ? trainingreport.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (filepicture != null ? filepicture.hashCode() : 0);
        result = 31 * result + (filecv != null ? filecv.hashCode() : 0);
        result = 31 * result + (filecover != null ? filecover.hashCode() : 0);
        result = 31 * result + (fileother != null ? fileother.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        result = 31 * result + (modifiedby != null ? modifiedby.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (createdby != null ? createdby.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (logincode != null ? logincode.hashCode() : 0);
        result = 31 * result + (complete != null ? complete.hashCode() : 0);
        result = 31 * result + (alternativeemail != null ? alternativeemail.hashCode() : 0);
        return result;
    }
}
