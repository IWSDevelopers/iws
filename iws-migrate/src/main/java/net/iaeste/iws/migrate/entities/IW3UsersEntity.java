/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.entities.IW3UsersEntity
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
@Table(name = "users")
public class IW3UsersEntity implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    @Column(name = "userid", nullable = false, length = 10)
    private Integer userid = null;

    @Column(name = "username", nullable = false, length = 2147483647)
    private String username = null;

    @Column(name = "password", nullable = false, length = 32)
    private String password = null;

    @Column(name = "firstname", nullable = false, length = 2147483647)
    private String firstname = null;

    @Column(name = "lastname", nullable = false, length = 2147483647)
    private String lastname = null;

    @Column(name = "middlename", length = 2147483647)
    private String middlename = null;

    @Column(name = "nickname", length = 2147483647)
    private String nickname = null;

    @Column(name = "gender", length = 10)
    private String gender = null;

    @Column(name = "title", length = 2147483647)
    private String title = null;

    @Column(name = "pobox", length = 2147483647)
    private String pobox = null;

    @Column(name = "street1", length = 2147483647)
    private String street1 = null;

    @Column(name = "street2", length = 2147483647)
    private String street2 = null;

    @Column(name = "zip", length = 10)
    private String zip = null;

    @Column(name = "city", length = 2147483647)
    private String city = null;

    @Column(name = "country", nullable = false, length = 2)
    private String country = null;

    @Column(name = "region", length = 2147483647)
    private String region = null;

    @Column(name = "university", length = 2147483647)
    private String university = null;

    @Column(name = "subject", length = 2147483647)
    private String subject = null;

    @Column(name = "gradyear", length = 10)
    private Integer gradyear = null;

    @Column(name = "nationality", nullable = false, length = 2)
    private String nationality = null;

    @Column(name = "phone", length = 25)
    private String phone = null;

    @Column(name = "fax", length = 2147483647)
    private String fax = null;

    @Column(name = "mobile", length = 25)
    private String mobile = null;

    @Column(name = "homepage", length = 2147483647)
    private String homepage = null;

    @Column(name = "passportnumber", length = 2147483647)
    private String passportnumber = null;

    @Column(name = "work_company", length = 2147483647)
    private String workCompany = null;

    @Column(name = "work_department", length = 2147483647)
    private String workDepartment = null;

    @Column(name = "work_title", length = 2147483647)
    private String workTitle = null;

    @Column(name = "work_pobox", length = 2147483647)
    private String workPobox = null;

    @Column(name = "work_street1", length = 2147483647)
    private String workStreet1 = null;

    @Column(name = "work_street2", length = 2147483647)
    private String workStreet2 = null;

    @Column(name = "work_zip", length = 10)
    private String workZip = null;

    @Column(name = "work_city", length = 2147483647)
    private String workCity = null;

    @Column(name = "work_region", length = 2147483647)
    private String workRegion = null;

    @Column(name = "work_country", nullable = false, length = 2)
    private String workCountry = null;

    @Column(name = "work_phone", length = 25)
    private String workPhone = null;

    @Column(name = "work_mobile", length = 25)
    private String workMobile = null;

    @Column(name = "work_fax", length = 2147483647)
    private String workFax = null;

    @Column(name = "work_email", length = 2147483647)
    private String workEmail = null;

    @Column(name = "work_homepage", length = 2147483647)
    private String workHomepage = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday = null;

    @Column(name = "comments", length = 2147483647)
    private String comments = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "lastpasswordchange")
    private Date lastpasswordchange = null;

    @Column(name = "alternativemail", length = 2147483647)
    private String alternativemail = null;

    @Column(name = "im_name1", length = 2147483647)
    private String imName1 = null;

    @Column(name = "im_user1", length = 2147483647)
    private String imUser1 = null;

    @Column(name = "im_name2", length = 2147483647)
    private String imName2 = null;

    @Column(name = "im_user2", length = 2147483647)
    private String imUser2 = null;

    @Column(name = "passportissued", length = 2147483647)
    private String passportissued = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "passportvalidity")
    private Date passportvalidity = null;

    @Column(name = "type", length = 1)
    private String type = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = null;

    // =========================================================================
    // IW3 Entity Setters & Getters
    // =========================================================================

    public void setUserid(final Integer userid) {
        this.userid = userid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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

    public void setMiddlename(final String middlename) {
        this.middlename = middlename;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPobox(final String pobox) {
        this.pobox = pobox;
    }

    public String getPobox() {
        return pobox;
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

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setUniversity(final String university) {
        this.university = university;
    }

    public String getUniversity() {
        return university;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setGradyear(final Integer gradyear) {
        this.gradyear = gradyear;
    }

    public Integer getGradyear() {
        return gradyear;
    }

    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setFax(final String fax) {
        this.fax = fax;
    }

    public String getFax() {
        return fax;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setHomepage(final String homepage) {
        this.homepage = homepage;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setPassportnumber(final String passportnumber) {
        this.passportnumber = passportnumber;
    }

    public String getPassportnumber() {
        return passportnumber;
    }

    public void setWorkCompany(final String workCompany) {
        this.workCompany = workCompany;
    }

    public String getWorkCompany() {
        return workCompany;
    }

    public void setWorkDepartment(final String workDepartment) {
        this.workDepartment = workDepartment;
    }

    public String getWorkDepartment() {
        return workDepartment;
    }

    public void setWorkTitle(final String workTitle) {
        this.workTitle = workTitle;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkPobox(final String workPobox) {
        this.workPobox = workPobox;
    }

    public String getWorkPobox() {
        return workPobox;
    }

    public void setWorkStreet1(final String workStreet1) {
        this.workStreet1 = workStreet1;
    }

    public String getWorkStreet1() {
        return workStreet1;
    }

    public void setWorkStreet2(final String workStreet2) {
        this.workStreet2 = workStreet2;
    }

    public String getWorkStreet2() {
        return workStreet2;
    }

    public void setWorkZip(final String workZip) {
        this.workZip = workZip;
    }

    public String getWorkZip() {
        return workZip;
    }

    public void setWorkCity(final String workCity) {
        this.workCity = workCity;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkRegion(final String workRegion) {
        this.workRegion = workRegion;
    }

    public String getWorkRegion() {
        return workRegion;
    }

    public void setWorkCountry(final String workCountry) {
        this.workCountry = workCountry;
    }

    public String getWorkCountry() {
        return workCountry;
    }

    public void setWorkPhone(final String workPhone) {
        this.workPhone = workPhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkMobile(final String workMobile) {
        this.workMobile = workMobile;
    }

    public String getWorkMobile() {
        return workMobile;
    }

    public void setWorkFax(final String workFax) {
        this.workFax = workFax;
    }

    public String getWorkFax() {
        return workFax;
    }

    public void setWorkEmail(final String workEmail) {
        this.workEmail = workEmail;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkHomepage(final String workHomepage) {
        this.workHomepage = workHomepage;
    }

    public String getWorkHomepage() {
        return workHomepage;
    }

    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setComments(final String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setLastpasswordchange(final Date lastpasswordchange) {
        this.lastpasswordchange = lastpasswordchange;
    }

    public Date getLastpasswordchange() {
        return lastpasswordchange;
    }

    public void setAlternativemail(final String alternativemail) {
        this.alternativemail = alternativemail;
    }

    public String getAlternativemail() {
        return alternativemail;
    }

    public void setImName1(final String imName1) {
        this.imName1 = imName1;
    }

    public String getImName1() {
        return imName1;
    }

    public void setImUser1(final String imUser1) {
        this.imUser1 = imUser1;
    }

    public String getImUser1() {
        return imUser1;
    }

    public void setImName2(final String imName2) {
        this.imName2 = imName2;
    }

    public String getImName2() {
        return imName2;
    }

    public void setImUser2(final String imUser2) {
        this.imUser2 = imUser2;
    }

    public String getImUser2() {
        return imUser2;
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

    public void setType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
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
