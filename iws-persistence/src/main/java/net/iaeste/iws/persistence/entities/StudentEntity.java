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

import net.iaeste.iws.api.enums.NotificationSubject;
import net.iaeste.iws.persistence.notification.Notifiable;

import javax.persistence.*;


/**
 * @author Teis Lindemark / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@NamedQueries({
        @NamedQuery(name = "StudentEntity.findAll", query = "select s from StudentEntity s"),
        @NamedQuery(name = "StudentEntity.findByName", query = "select s from StudentEntity s where s.studentName = :studentName")
})
@Entity
@Table(name = "students")
public class StudentEntity implements IWSEntity, Notifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @Column(nullable = false, name = "student_name")
    private String studentName = null;

    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(nullable = false, name = "group_id")
    private GroupEntity group = null;

    /**
     * Empty Constructor
     */
    public StudentEntity() {

    }

    public StudentEntity(final Long id, final String studentName, final GroupEntity group) {
        this.id = id;
        this.studentName = studentName;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    /**
     * The Notification relies on a message being sent in some sort of format.
     * However, this is not yet clarified exactly how it should be done. So
     * for now - this is just a simple placeholder. So we at least can test
     * those parts of the System, that relies on Notifications as part of the
     * flow, i.e. Create User Account, Forgot Password, etc.
     *
     * @return Simple Message
     */
    @Override
    public String generateNotificationMessage() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NotificationSubject getNotificationSubject() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
