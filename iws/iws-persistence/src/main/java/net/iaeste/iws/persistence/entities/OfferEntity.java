package net.iaeste.iws.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 24.07.12
 * Time: 00:35
 */
@Table(name = "offers", schema = "iws", catalog = "")
@Data
@Entity
public class OfferEntity {
    @Column(name = "id")
    @Id
    private int id;

    @Column(name = "group_id")
    @Basic
    private int groupId;

    @Column(name = "system_ref_no")
    @Basic
    private String systemRefNo;


    @Column(name = "local_ref_no")
    @Basic
    private String localRefNo;

    @Column(name = "exchange_year")
    @Basic
    private int exchangeYear;

    @Column(name = "offer_year")
    @Basic
    private int offerYear;

    @Column(name = "is_archive")
    @Basic
    private int isArchive;

    @Column(name = "deadline")
    @Basic
    private Date deadline;

    @Column(name = "expire")
    @Basic
    private Date expire;

    @Column(name = "employer_id")
    @Basic
    private int employerId;


    @Column(name = "hours_weekly")
    @Basic
    private float hoursWeekly;

    @Column(name = "hours_daily")
    @Basic
    private float hoursDaily;

    @Column(name = "study_field_id")
    @Basic
    private int studyFieldId;

    @Column(name = "faculty_other")
    @Basic
    private String facultyOther;

    @Column(name = "specialization")
    @Basic
    private String specialization;

    @Column(name = "study_completed")
    @Basic
    private int studyCompleted;

    @Column(name = "study_required")
    @Basic
    private String studyRequired;

    @Column(name = "language1")
    @Basic
    private String language1;

    @Column(name = "language1_level")
    @Basic
    private int language1Level;

    @Column(name = "language1or")
    @Basic
    private boolean language1Or;

    @Column(name = "language2")
    @Basic
    private String language2;


    @Column(name = "language2_level")
    @Basic
    private int language2Level;

    @Column(name = "language2or")
    @Basic
    private boolean language2Or;

    @Column(name = "language3")
    @Basic
    private String language3;

    @Column(name = "language3_level")
    @Basic
    private int language3Level;

    @Column(name = "other_requirements")
    @Basic
    private String otherRequirements;

    @Column(name = "training_required")
    @Basic
    private String trainingRequired;

    @Column(name = "gender")
    @Basic
    private String gender;

    @Column(name = "work_kind")
    @Basic
    private String workKind;

    @Column(name = "work_type")
    @Basic
    private int workType;

    @Column(name = "weeksmin")
    @Basic
    private int weeksmin;

    @Column(name = "weeksmax")
    @Basic
    private int weeksmax;

    @Column(name = "from_date")
    @Basic
    private Date fromDate;

    @Column(name = "to_date")
    @Basic
    private Date toDate;

    @Column(name = "from_date2")
    @Basic
    private Date fromDate2;

    @Column(name = "to_date2")
    @Basic
    private Date toDate2;

    @Column(name = "holidays_from")
    @Basic
    private Date holidaysFrom;

    @Column(name = "holidays_to")
    @Basic
    private Date holidaysTo;

    @Column(name = "payment")
    @Basic
    private int payment;

    @Column(name = "payment_frequency")
    @Basic
    private int paymentFrequency;

    @Column(name = "deduction")
    @Basic
    private String deduction;

    @Column(name = "lodging")
    @Basic
    private String lodging;

    @Column(name = "lodging_cost")
    @Basic
    private int lodgingCost;

    @Column(name = "lodging_cost_frequency")
    @Basic
    private int lodgingCostFrequency;

    @Column(name = "living_cost")
    @Basic
    private int livingCost;

    @Column(name = "living_cost_frequency")
    @Basic
    private int livingCostFrequency;

    @Column(name = "canteen")
    @Basic
    private boolean canteen;

    @Column(name = "nomination_e")
    @Basic
    private boolean nominationE;

    @Column(name = "nomination_h")
    @Basic
    private boolean nominationH;

    @Column(name = "nomination_and_or")
    @Basic
    private boolean nominationAndOr;

    @Column(name = "no_hard_copies")
    @Basic
    private int noHardCopies;

    @Column(name = "require_student")
    @Basic
    private boolean requireStudent;

    @Column(name = "comment")
    @Basic
    private String comment;

    @Column(name = "answered")
    @Basic
    private Timestamp answered;

    @Column(name = "status")
    @Basic
    private int status;

    @Column(name = "published")
    @Basic
    private Timestamp published;

    @Column(name = "published_by")
    @Basic
    private int publishedBy;

    @Column(name = "modified")
    @Basic
    private Timestamp modified;

    @Column(name = "modified_by")
    @Basic
    private int modifiedBy;

    @Column(name = "created")
    @Basic
    private Timestamp created;

    @Column(name = "created_by")
    @Basic
    private int createdBy;

    @Column(name = "country_id")
    @Basic
    private int countryId;

    @Column(name = "student_id")
    @Basic
    private int studentId;

//    private Collection<Offer2GroupEntity> offer2GroupsById;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private CountryEntity country;

    @ManyToOne
    @JoinColumn(name = "employer_id", referencedColumnName = "id", nullable = false)
    private EmployerEntity employer;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    private GroupEntity group;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "study_field_id", referencedColumnName = "id", nullable = false)
    private StudyFieldEntity studyField;

}
