package net.iaeste.iws.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;


/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 24.07.12
 * Time: 00:35
 */
@Table(name = "students", schema = "iws", catalog = "")
@Entity
public class StudentEntity {
    /*@Getter @Setter
    @Column(name = "id")
    @Id
    private int id;  */

//    @OneToMany(mappedBy = "studentsByStudentId")
//    private Collection<Offer2GroupEntity> offer2GroupsById;
//    @OneToMany(mappedBy = "studentsByStudentId")
//    private Collection<OfferEntity> offersesById;
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Getter @Setter
    @Basic
    @Column(nullable = false, name = "firstname")
    private String firstname;
    @Getter @Setter
    @Basic
    @Column(nullable = false, name = "lastname")
    private String lastname;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "adress_id", referencedColumnName = "id", nullable = false)
    private AddressEntity address;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private CountryEntity country;
    @Getter @Setter
    @Basic
    @Column(name = "phone")
    private String phone;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "termaddress_id", referencedColumnName = "id", nullable = false)
    private AddressEntity termaddress;
    @Getter @Setter
    @Basic
    @Column(name = "termphone")
    private String termphone;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "termcountry_id", referencedColumnName = "id", nullable = false)
    private CountryEntity termcountry;
    @Getter @Setter
    @Basic
    @Column(nullable = false, name = "email")
    private String email;
    @Getter @Setter
    @Basic
    @Column(name = "alternativemail")
    private String alternativemail;
    @Getter @Setter
    @Basic
    @Column(name = "birthday")
    private Date birthday;
    @Getter @Setter
    @Basic
    @Column(name = "birthplace")
    private String birthplace;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "nationality_id", referencedColumnName = "id", nullable = false)
    private CountryEntity nationality;
    @Getter @Setter
    @Basic
    @Column(name = "passportnumber")
    private String passportnumber;
    @Getter @Setter
    @Basic
    @Column(name = "passportissued")
    private String passportissued;
    @Getter @Setter
    @Basic
    @Column(name = "passportvalidity")
    private String passportvalidity;
    @Getter @Setter
    @Basic
    @Column(name = "gender")
    private Character gender;
    @Getter @Setter
    @Basic
    @Column(name = "maritalstatus")
    private Character maritalstatus;
    @Getter @Setter
    @Basic
    @Column(name = "medicallyfit")
    private Character medicallyfit;
    @Getter @Setter
    @Basic
    @Column(name = "university")
    private String university;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id", nullable = false)
    private StudyFieldEntity studyfields;
    @Getter @Setter
    @Basic
    @Column(name = "specialization")
    private String specialization;
    @Getter @Setter
    @Basic
    @Column(name = "studycompleted")
    private Character studycompleted;
    @Getter @Setter
    @Basic
    @Column(name = "studyrequired")
    private Character studyrequired;
    //TODO - WORK IN PROGRESS WITH LANGUAGES
    // Language part will be implemented soon
    //TODO END
    @Getter @Setter
    @Basic
    @Column(name = "fromdate")
    private Date fromdate;
    @Getter @Setter
    @Basic
    @Column(name = "todate")
    private Date todate;
    @Getter @Setter
    @Basic
    @Column(name = "requireloding")
    private Boolean requireloding;
    @Getter @Setter
    @Basic
    @Column(name = "trainingreport")
    private Boolean trainingreport;
    @Getter @Setter
    @Basic
    @Column(name = "comment")
    private String comment;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "filepicture_id", referencedColumnName = "id", nullable = false)
    private StudentFileEntity filepicture;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "filecv_id", referencedColumnName = "id", nullable = false)
    private StudentFileEntity filecv;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "filecover_id", referencedColumnName = "id", nullable = false)
    private StudentFileEntity filecover;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "fileother_id", referencedColumnName = "id", nullable = false)
    private StudentFileEntity fileother;
    @Getter @Setter
    @Basic
    @Column(name = "status")
    private Character status;
    @Getter @Setter
    @Basic
    @Column(name = "modified")
    private Timestamp modified;
    @Getter @Setter
    @ManyToMany
    @JoinColumn(name = "modifiedby_id", referencedColumnName = "id", nullable = false)
    private UserEntity modifiedby;
    @Getter @Setter
    @Basic
    @Column(name = "created")
    private Timestamp created;
    @Getter @Setter
    @ManyToMany
    @JoinColumn(name = "created_id", referencedColumnName = "id", nullable = false)
    private UserEntity createdby;
    @Getter @Setter
    @Basic
    @Column(name = "logincode")
    private String logincode;
    @Getter @Setter
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
                         StudyFieldEntity studyfield,
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
