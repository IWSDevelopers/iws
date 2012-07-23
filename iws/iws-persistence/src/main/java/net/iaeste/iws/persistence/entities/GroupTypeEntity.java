package net.iaeste.iws.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 24.07.12
 * Time: 00:34
 *
 */
@Table(name = "group_types", schema = "iws", catalog = "")
@Entity
public class GroupTypeEntity {

    @Getter @Setter
    @Column(name = "id")
    @Id
    private int id;

    @Getter @Setter
    @Column(name = "group_type")
    @Basic
    private String groupType;

    @Getter @Setter
    @Column(name = "modified")
    @Basic
    private Timestamp modified;

    @Getter @Setter
    @Column(name = "created")
    @Basic
    private Timestamp created;

//    @javax.persistence.OneToMany(mappedBy = "groupTypesByGroupTypeIdFk")
//    private Collection<Function2GroupTypeEntity> function2GroupTypesById;
//    @javax.persistence.OneToMany(mappedBy = "groupTypesByGroupTypeFk")
//    private Collection<GroupEntity> groupsesById;

}
