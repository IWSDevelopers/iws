/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.RoleEntity
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Roles are "Hats", that a user can wear in a specific context. For example, a
 * user can have the Role "Owner"
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection AssignmentToDateFieldFromParameter
 */
@NamedQueries({
        @NamedQuery(
                name = "role.findAll",
                query = "select r from RoleEntity r"),
        @NamedQuery(
                name = "role.findById",
                query = "select r from RoleEntity  r " +
                        "where r.id = :id"),
        @NamedQuery(
                name = "role.findRoleByName",
                query = "select r from RoleEntity r " +
                        "where r.role = :role")
})
@Entity
@Table(name = "roles")
public class RoleEntity implements IWSEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    /**
     * The name of the Role. The default Roles have the names "Owner",
     * "Moderator", "Member" and "Guest". It is possible to also create custom
     * roles, to fit more specific needs.
     */
    @Column(nullable = false, name = "role")
    private String role = null;

    /**
     * Customized Role are either assigned to a Country or to a Group. Meaning
     * that the members of either the assigned Country or Group can use this
     * Role to their liking.<br />
     *   Customized Roles cannot be deleted - the reason for this is that the
     * system have no way of determining which Role to assign to the affected
     * users otherwise.
     */
    @ManyToOne(targetEntity = CountryEntity.class)
    @JoinColumn(nullable = true, name = "country_id")
    private CountryEntity country = null;

    /**
     * Customized Role are either assigned to a Country or to a Group. Meaning
     * that the members of either the assigned Country or Group can use this
     * Role to their liking.<br />
     *   Customized Roles cannot be deleted - the reason for this is that the
     * system have no way of determining which Role to assign to the affected
     * users otherwise.
     */
    @ManyToOne(targetEntity = GroupEntity.class)
    @JoinColumn(nullable = true, name = "group_id")
    private GroupEntity group = null;

    /** Longer description of the Role. */
    @Column(nullable = false, name = "description")
    private String description = null;

    /** Last time the Role was modified. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified = new Date();

    /** Timestamp when the Role was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = new Date();

    // =========================================================================
    // Entity Constructors
    // =========================================================================

    /**
     * Empty Constructor, JPA requirement.
     */
    public RoleEntity() {
    }

    /**
     * Default Constructor, when creating new Roles to a country.
     *
     * @param role         The name of the role
     * @param country      Customr Roles requires either a Group or Country
     * @param description  Description of the role
     */
    public RoleEntity(final String role, final CountryEntity country, final String description) {
        this.role = role;
        this.country = country;
        this.description = description;
    }

    /**
     * Default Constructor, when creating new Roles for a specific Group.
     *
     * @param role         The name of the role
     * @param group        Customr Roles requires either a Group or Country
     * @param description  Description of the role
     */
    public RoleEntity(final String role, final GroupEntity group, final String description) {
        this.role = role;
        this.group = group;
        this.description = description;
    }

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setCountry(final CountryEntity country) {
        this.country = country;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setGroup(final GroupEntity group) {
        this.group = group;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setModified(final Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }
}
