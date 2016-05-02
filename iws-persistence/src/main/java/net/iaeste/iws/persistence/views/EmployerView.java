/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
public final class EmployerView implements IWSView {

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
}
