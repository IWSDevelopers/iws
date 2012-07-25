package net.iaeste.iws.persistence.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 24.07.12
 * Time: 00:34
 *
 */
@Table(name = "group_types", schema = "iws", catalog = "")
//@Entity
public class GroupTypeEntity {

    @Column(name = "id")
    @Id
    private int id;

    @Column(name = "group_type")
    @Basic
    private String groupType;

    @Column(name = "modified")
    @Basic
    private Timestamp modified;

    @Column(name = "created")
    @Basic
    private Timestamp created;

//    @javax.persistence.OneToMany(mappedBy = "groupTypesByGroupTypeIdFk")
//    private Collection<Function2GroupTypeEntity> function2GroupTypesById;
//    @javax.persistence.OneToMany(mappedBy = "groupTypesByGroupTypeFk")
//    private Collection<GroupEntity> groupsesById;

}
