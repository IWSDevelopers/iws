/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.StudentEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.entities;

import net.iaeste.iws.api.enums.FieldOfStudy;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;


/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 24.07.12
 * Time: 00:35
 */
@Table(name = "students", schema = "iws", catalog = "")
//@Entity
public class StudentEntity {
    /*@Getter @Setter
    @Column(name = "id")
    @Id
    private int id;  */

//    @OneToMany(mappedBy = "studentsByStudentId")
//    private Collection<Offer2GroupEntity> offer2GroupsById;
//    @OneToMany(mappedBy = "studentsByStudentId")
//    private Collection<OfferEntity> offersesById;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Basic
    @Column(nullable = false, name = "firstname")
    private String firstname;
    @Basic
    @Column(nullable = false, name = "lastname")
    private String lastname;
    @ManyToOne
    @JoinColumn(name = "adress_id", referencedColumnName = "id", nullable = false)
    private AddressEntity address;
    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private CountryEntity country;
    @Basic
    @Column(name = "phone")
    private String phone;
    @ManyToOne
    @JoinColumn(name = "termaddress_id", referencedColumnName = "id", nullable = false)
    private AddressEntity termaddress;
    @Basic
    @Column(name = "termphone")
    private String termphone;
    @ManyToOne
    @JoinColumn(name = "termcountry_id", referencedColumnName = "id", nullable = false)
    private CountryEntity termcountry;
    @Basic
    @Column(nullable = false, name = "email")
    private String email;
    @Basic
    @Column(name = "alternativemail")
    private String alternativemail;
    @Basic
    @Column(name = "birthday")
    private Date birthday;
    @Basic
    @Column(name = "birthplace")
    private String birthplace;
    @ManyToOne
    @JoinColumn(name = "nationality_id", referencedColumnName = "id", nullable = false)
    private CountryEntity nationality;
    @Basic
    @Column(name = "passportnumber")
    private String passportnumber;
    @Basic
    @Column(name = "passportissued")
    private String passportissued;
    @Basic
    @Column(name = "passportvalidity")
    private String passportvalidity;
    @Basic
    @Column(name = "gender")
    private Character gender;
    @Basic
    @Column(name = "maritalstatus")
    private Character maritalstatus;
    @Basic
    @Column(name = "medicallyfit")
    private Character medicallyfit;
    @Basic
    @Column(name = "university")
    private String university;
    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id", nullable = false)
    private FieldOfStudy studyfields;
    @Basic
    @Column(name = "specialization")
    private String specialization;
    @Basic
    @Column(name = "studycompleted")
    private Character studycompleted;
    @Basic
    @Column(name = "studyrequired")
    private Character studyrequired;
    //TODO - WORK IN PROGRESS WITH LANGUAGES
    // Language part will be implemented soon
    //TODO END
    @Basic
    @Column(name = "fromdate")
    private Date fromdate;
    @Basic
    @Column(name = "todate")
    private Date todate;
    @Basic
    @Column(name = "requireloding")
    private Boolean requireloding;
    @Basic
    @Column(name = "trainingreport")
    private Boolean trainingreport;
    @Basic
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "filepicture_id", referencedColumnName = "id", nullable = false)
    private StudentFileEntity filepicture;
    @ManyToOne
    @JoinColumn(name = "filecv_id", referencedColumnName = "id", nullable = false)
    private StudentFileEntity filecv;
    @ManyToOne
    @JoinColumn(name = "filecover_id", referencedColumnName = "id", nullable = false)
    private StudentFileEntity filecover;
    @ManyToOne
    @JoinColumn(name = "fileother_id", referencedColumnName = "id", nullable = false)
    private StudentFileEntity fileother;
    @Basic
    @Column(name = "status")
    private Character status;
    @Basic
    @Column(name = "modified")
    private Timestamp modified;
    @ManyToMany
    @JoinColumn(name = "modifiedby_id", referencedColumnName = "id", nullable = false)
    private UserEntity modifiedby;
    @Basic
    @Column(name = "created")
    private Timestamp created;
    @ManyToMany
    @JoinColumn(name = "created_id", referencedColumnName = "id", nullable = false)
    private UserEntity createdby;
    @Basic
    @Column(name = "logincode")
    private String logincode;
    @Basic
    @Column(name = "completed")
    private Boolean completed;

    public StudentEntity() {
        this.id = 0;
        this.firstname = null;
        this.lastname = null;
        this.address = null;
        this.country = null;
        this.phone = null;
        this.termaddress = null;
        this.termphone = null;
        this.termcountry = null;
        this.email = null;
        this.alternativemail = null;
        this.birthday = null;
        this.birthplace = null;
        this.nationality = null;
        this.passportnumber = null;
        this.passportissued = null;
        this.passportvalidity = null;
        this.gender = null;
        this.maritalstatus = null;
        this.medicallyfit = null;
        this.university = null;
        this.studyfields = null;
        this.specialization = null;
        this.studycompleted = null;
        this.studyrequired = null;
        this.fromdate = null;
        this.todate = null;
        this.requireloding = null;
        this.trainingreport = null;
        this.comment = null;
        this.filepicture = null;
        this.filecv = null;
        this.filecover = null;
        this.fileother = null;
        this.status = null;
        this.modified = null;
        this.modifiedby = null;
        this.created = null;
        this.createdby = null;
        this.logincode = null;
        this.completed = null;
    }

    public StudentEntity(int id,
                         String firstname,
                         String lastname,
                         AddressEntity address,
                         CountryEntity country,
                         String phone,
                         AddressEntity termaddress,
                         String termphone,
                         CountryEntity termcountry,
                         String email,
                         String alternativemail,
                         Date birthday,
                         String birthplace,
                         CountryEntity nationality,
                         String passportnumber,
                         String passportissued,
                         String passportvalidity,
                         Character gender,
                         Character maritalstatus,
                         Character medicallyfit,
                         String university,
                         FieldOfStudy studyfield,
                         String specialization,
                         Character studycompleted,
                         Character studyrequired,
                         Date fromdate,
                         Date todate,
                         Boolean requireloding,
                         Boolean trainingreport,
                         String comment,
                         StudentFileEntity filepicture,
                         StudentFileEntity filecv,
                         StudentFileEntity filecover,
                         StudentFileEntity fileother,
                         Character status,
                         Timestamp modified,
                         UserEntity modifiedby,
                         Timestamp created,
                         UserEntity createdby,
                         String logincode,
                         Boolean completed) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.country = country;
        this.phone = phone;
        this.termaddress = termaddress;
        this.termphone = termphone;
        this.termcountry = termcountry;
        this.email = email;
        this.alternativemail = alternativemail;
        this.birthday = birthday;
        this.birthplace = birthplace;
        this.nationality = nationality;
        this.passportnumber = passportnumber;
        this.passportissued = passportissued;
        this.passportvalidity = passportvalidity;
        this.gender = gender;
        this.maritalstatus = maritalstatus;
        this.medicallyfit = medicallyfit;
        this.university = university;
        this.studyfields = studyfield;
        this.specialization = specialization;
        this.studycompleted = studycompleted;
        this.studyrequired = studyrequired;
        this.fromdate = fromdate;
        this.todate = todate;
        this.requireloding = requireloding;
        this.trainingreport = trainingreport;
        this.comment = comment;
        this.filepicture = filepicture;
        this.filecv = filecv;
        this.filecover = filecover;
        this.fileother = fileother;
        this.status = status;
        this.modified = modified;
        this.modifiedby = modifiedby;
        this.created = created;
        this.createdby = createdby;
        this.logincode = logincode;
        this.completed = completed;
    }
}
