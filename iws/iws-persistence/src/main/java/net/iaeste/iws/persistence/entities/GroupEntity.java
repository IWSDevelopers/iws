/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.GroupEntity
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

    @Column(name = "id")
    @Id
    private int id;

    @Column(name = "group_type_fk")
    @javax.persistence.Basic
    private int groupTypeFk;

    @Column(name = "modified")
    @javax.persistence.Basic
    private Timestamp modified;

    @Column(name = "created")
    @javax.persistence.Basic
    private Timestamp created;

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
