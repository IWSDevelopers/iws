package net.iaeste.iws.persistence.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
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
