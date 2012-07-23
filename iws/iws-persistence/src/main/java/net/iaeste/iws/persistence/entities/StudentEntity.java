package net.iaeste.iws.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 24.07.12
 * Time: 00:35
 */
@Table(name = "students", schema = "iws", catalog = "")
@Entity
public class StudentEntity {
    @Getter @Setter
    @Column(name = "id")
    @Id
    private int id;

//    @OneToMany(mappedBy = "studentsByStudentId")
//    private Collection<Offer2GroupEntity> offer2GroupsById;
//    @OneToMany(mappedBy = "studentsByStudentId")
//    private Collection<OfferEntity> offersesById;

}
