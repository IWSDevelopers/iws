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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Teis Lindemark / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@NamedQueries({
        @NamedQuery(name = "student.findAll",
                query = "select s from StudentEntity s"),
        @NamedQuery(name = "student.findByName",
                query = "select s from StudentEntity s " +
                        "where s.studentName = :name")
})
@Entity
@Table(name = "students")
public class StudentEntity implements IWSEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @Column(nullable = false, name = "student_name")
    private String studentName = null;

    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(nullable = false, name = "group_id")
    private GroupEntity group = null;

    // =========================================================================
    // Entity Constructors
    // =========================================================================

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

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    public void setStudentName(final String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setGroup(final GroupEntity group) {
        this.group = group;
    }

    public GroupEntity getGroup() {
        return group;
    }
}
