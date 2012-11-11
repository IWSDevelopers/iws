/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.Student
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.requests.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * Standard IAESTE Student.
 *
 * @author  Teis Lindemark / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CastToConcreteClass
 */
public final class Student extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private Long studentId = null;
    private String studentName = null;
    private Group group = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Student() {
    }

    /**
     * Default Constructor.
     *
     * @param studentId    Student Id
     * @param studentName  Name of the Student
     * @param group        National Group, which the student belongs to
     */
    public Student(final Long studentId, final String studentName, final Group group) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.group = group;
    }

    /**
     * Copy Constructor.
     *
     * @param student Student Object to copy
     */
    public Student(final Student student) {
        if(student != null) {
            studentId = student.studentId;
            studentName = student.studentName;
            group = student.group;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setStudentId(final Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentName(final String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setGroup(final Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "studentId", studentId);
        isNotNullOrEmpty(validation, "studentName", studentName);
        isNotNull(validation, "group", group);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if(this == obj) {
            return true;
        }

        if(!(obj instanceof Student)) {
            return false;
        }

        final Student student = (Student) obj;

        if(studentId != null ? !studentId.equals(student.studentId) : student.studentId != null) {
            return false;
        }

        if(group != null ? !group.equals(student.group) : student.group != null) {
            return false;
        }

        return !(studentName != null ? !studentName.equals(student.studentName) : student.studentName != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = IWSConstants.HASHCODE_INITIAL_VALUE;

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + studentId.hashCode();
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + studentName.hashCode();
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (group != null ? group.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                "studentName=" + studentName +
                "group=" + group +
                '}';
    }
}
