package net.iaeste.iws.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 24.07.12
 * Time: 00:35
 */
@Table(name = "groups", schema = "iws", catalog = "")
@Entity
public class GroupEntity {
    @Getter @Setter
    @Column(name = "id")
    @Id
    private int id;

    @Getter @Setter
    @Column(name = "group_type_fk")
    @javax.persistence.Basic
    private int groupTypeFk;

    @Getter @Setter
    @Column(name = "modified")
    @javax.persistence.Basic
    private Timestamp modified;

    @Getter @Setter
    @Column(name = "created")
    @javax.persistence.Basic
    private Timestamp created;

    @Getter @Setter
    @javax.persistence.ManyToOne
    @javax.persistence.JoinColumn(name = "group_type_fk", referencedColumnName = "id", nullable = false)
    private GroupTypeEntity groupType;
//    @javax.persistence.OneToMany(mappedBy = "groupsByGroupId")
//    private Collection<Offer2GroupEntity> offer2GroupsById;
//    @javax.persistence.OneToMany(mappedBy = "groupsByGroupId")
//    private Collection<OfferEntity> offersesById;
//    @javax.persistence.OneToMany(mappedBy = "groupsByGroupId")
//    private Collection<StudyField2GroupEntity> studyField2GroupsById;
//    @javax.persistence.OneToMany(mappedBy = "groupsByGroupIdFk")
//    private Collection<User2GroupEntity> user2GroupsById;
}
