/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.EmployerView
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
 * @since   1.7
 * @noinspection CompareToUsesNonFinalVariable
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "view.findEmployerByGroup",
                query = "select e from EmployerView e " +
                        "where e.groupId = :gid "),
        @NamedQuery(name = "view.findEmployerByGroupAndId",
                query = "select e from EmployerView e " +
                        "where e.groupId = :gid" +
                        "  and e.employer.externalId = :id"),
        @NamedQuery(name = "view.findEmployerByGroupAndPartialName",
                query = "select e from EmployerView e " +
                        "where e.groupId = :gid" +
                        "  and lower(e.employer.name) like :name")
})
@Table(name = "employer_view")
public class EmployerView extends AbstractView<EmployerView> {

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private Long id = null;

    @Column(name = "group_id", insertable = false, updatable = false)
    private Long groupId = null;

    @Embedded
    private EmbeddedEmployer employer = null;

    @Embedded
    private EmbeddedGroup group = null;

    @Embedded
    private EmbeddedAddress address = null;

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

    public void setGroupId(final Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setEmployer(final EmbeddedEmployer employer) {
        this.employer = employer;
    }

    public EmbeddedEmployer getEmployer() {
        return employer;
    }

    public void setGroup(final EmbeddedGroup group) {
        this.group = group;
    }

    public EmbeddedGroup getGroup() {
        return group;
    }

    public void setAddress(final EmbeddedAddress address) {
        this.address = address;
    }

    public EmbeddedAddress getAddress() {
        return address;
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

        if (!(obj instanceof EmployerView)) {
            return false;
        }

        // As the view is reading from the current data model, and the Id is
        // always unique. It is sufficient to compare against this field.
        final EmployerView that = (EmployerView) obj;
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
    public int compareTo(final EmployerView o) {
        final int result = id.compareTo(o.id);

        return sortAscending ? result : -result;
    }
}
