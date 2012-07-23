package net.iaeste.iws.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 24.07.12
 * Time: 00:35
 */
@Table(name = "roles", schema = "iws", catalog = "")
@Entity
public class RoleEntity {

    @Getter @Setter
    @Column(name = "id")
    @Id
    private int id;

    @Getter @Setter
    @Column(name = "modified")
    @Basic
    private Timestamp modified;

    @Getter @Setter
    @Column(name = "created")
    @Basic
    private Timestamp created;

//    @javax.persistence.OneToMany(mappedBy = "rolesByRoleIdFk")
//    private Collection<Function2RoleEntity> function2RolesById;
//    @javax.persistence.OneToMany(mappedBy = "rolesByRolesIdFk")
//    private Collection<User2GroupEntity> user2GroupsById;

}
