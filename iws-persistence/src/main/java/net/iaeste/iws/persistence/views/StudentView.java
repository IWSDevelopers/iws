/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.StudentView
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.views;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 * @noinspection CompareToUsesNonFinalVariable
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "view.findStudentById",
                query = "select s from StudentView s " +
                        "where s.id = :id"),
        @NamedQuery(name = "view.findStudentsForMemberGroup",
                query = "select s from StudentView s " +
                        "where s.group.parentId = :parentId")
})
@Table(name = "student_view")
public class StudentView extends AbstractView<StudentView> {

    @Id
    @Column(name = "student_id", insertable = false, updatable = false)
    private Long id = null;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId = null;

    @Column(name = "group_id", insertable = false, updatable = false)
    private Long groupId = null;

    @Embedded
    private EmbeddedStudent student = null;

    @Embedded
    private EmbeddedUser user = null;

    @Embedded
    private EmbeddedGroup group = null;

    @Embedded
    private EmbeddedCountry country = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setGroupId(final Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setStudent(final EmbeddedStudent student) {
        this.student = student;
    }

    public EmbeddedStudent getStudent() {
        return student;
    }

    public void setUser(final EmbeddedUser user) {
        this.user = user;
    }

    public EmbeddedUser getUser() {
        return user;
    }

    public void setGroup(final EmbeddedGroup group) {
        this.group = group;
    }

    public EmbeddedGroup getGroup() {
        return group;
    }

    public void setCountry(final EmbeddedCountry country) {
        this.country = country;
    }

    public EmbeddedCountry getCountry() {
        return country;
    }

    // =========================================================================
    // Standard View Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof StudentView)) {
            return false;
        }

        // As the view is reading from the current data model, and the Id is
        // always unique. It is sufficient to compare against this field.
        final StudentView that = (StudentView) obj;
        return id.equals(that.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final StudentView o) {
        final int result = id.compareTo(o.id);

        return sortAscending ? result : -result;
    }
}
