/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.EmployerEntity
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Employer Entity, contains the common fields for the Offers for a given
 * Employer.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@NamedQueries({
        @NamedQuery(
                name = "employer.findAllForGroup",
                query = "select e from EmployerEntity e " +
                        "where e.group.id = :gid"),
        @NamedQuery(
                name = "employer.findByExternalId",
                query = "select e from EmployerEntity e " +
                        "where e.externalId = :eid")
})
@Entity
@Table(name = "employers")
public class EmployerEntity implements Updateable<EmployerEntity> {

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "employer_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    /**
     * The content of this Entity is exposed externally, however to avoid that
     * someone tries to spoof the system by second guessing our Sequence values,
     * An External Id is used, the External Id is a Uniqie UUID value, which in
     * all external references is referred to as the "Id". Although this can be
     * classified as StO (Security through Obscrutity), there is no need to
     * expose more information than necessary.
     */
    @Column(name = "external_id", length = 36, unique = true, nullable = false, updatable = false)
    private String externalId = null;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group = null;

    @Column(name = "name", nullable = false)
    private String name = null;

    @Column(name = "department")
    private String department = null;

    //@Column(name = "department")
    //contact_person_id         integer,

    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address = null;

    @Column(name = "business")
    private String business = null;

    @Column(name = "number_of_employees")
    private Integer numberOfEmployees = null;

    @Column(name = "website")
    private String website = null;

    /**
     * Last time the Entity was modified.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", nullable = false)
    private Date modified = new Date();

    /**
     * Timestamp when the Entity was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable = false)
    private Date created = new Date();

    // =========================================================================
    // Entity Constructors
    // =========================================================================

    /**
     * Empty Constructor, required by JPA.
     */
    public EmployerEntity() {
    }

    /**
     * Default Constructor for creating new Entities of this type.
     *
     * @param name  Name of the Employer, must be unique for the given Group
     * @param group National Group, which this Employer belongs to
     */
    public EmployerEntity(final String name, final GroupEntity group) {
        this.name = name;
        this.group = group;
    }

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExternalId() {
        return externalId;
    }

    public void setGroup(final GroupEntity group) {
        this.group = group;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDepartment(final String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setAddress(final AddressEntity address) {
        this.address = address;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setBusiness(final String business) {
        this.business = business;
    }

    public String getBusiness() {
        return business;
    }

    public void setNumberOfEmployees(final Integer numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public Integer getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModified(final Date modified) {
        this.modified = modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getModified() {
        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCreated(final Date created) {
        this.created = created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getCreated() {
        return created;
    }

    // =========================================================================
    // Standard Entity Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final EmployerEntity obj) {
        // don't merge if objects are not the same entity
        if ((id != null) && (obj != null) && externalId.equals(obj.externalId)) {
            name = obj.name;
            business = obj.business;
            numberOfEmployees = obj.numberOfEmployees;
            website = obj.website;
        }
    }
}
