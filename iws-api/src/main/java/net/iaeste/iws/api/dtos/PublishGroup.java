/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.PublishGroup
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
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Copier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Publishing Group, is a helping tool for the Exchange process, so it is
 * possible to save a selection of countries to make an exchange with. Examples
 * of usage, EU, Lating America, French Speaking countries, etc.<br />
 *   The Group consists of a name and the selection of countries. And it is
 * important to note, that a Publishing Group is only available for the country
 * of origin, meaning that no other Committees than the owning Committee may
 * see this Publishing Group.
 *
 * @author  Sondre Naustdal / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class PublishGroup extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The Id of the Publishing Group. If null, then a new will be created. */
    private String id = null;

    /**
     * The name of the Publishing Group, the name must be non-null and unique
     * for the Committee, and will not be shared or shown to other Committees.
     */
    private String name = null;

    /** The List of Committees, to make up this Publishing Group. */
    private List<Group> groups = null;

    /**
     * Empty Constructor, required for some communication frameworks.
     */
    public PublishGroup() {
    }

    /**
     * Default Constructor, for creating a new Publishing Group.
     *
     * @param name   Name of the Group
     * @param groups List of Countries to be part of this Publishing Group
     */
    public PublishGroup(final String name, final List<Group> groups) {
        this.name = name;
        this.groups = Copier.copy(groups);
    }

    /**
     * Default Constructor, for maintaining a Publishing Group.
     *
     * @param id     The Id of the existing Publishing Group
     * @param name   Name of the Group
     * @param groups List of Countries to be part of this Publishing Group
     */
    public PublishGroup(final String id, final String name, final List<Group> groups) {
        this.id = id;
        this.name = name;
        this.groups = Copier.copy(groups);
    }

    /**
     * Copy Constructor.
     *
     * @param publishGroup PublishGroup Object to copy
     */
    public PublishGroup(final PublishGroup publishGroup) {
        if (publishGroup != null) {
            id = publishGroup.id;
            name = publishGroup.name;
            groups = Copier.copy(publishGroup.groups);
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setId(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGroups(final List<Group> groups) {
        this.groups = Copier.copy(groups);
    }

    public List<Group> getGroups() {
        return Copier.copy(groups);
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

        // The Id is internally generated, and thus never updated. If set, it
        // should just not be empty
        isNotEmpty(validation, "id", id);
        // The Name must be a none-null and none-empty value, of max 50 chars
        isWithinLimits(validation, "name", name, 1, 50);
        // The Collection of Groups, can be empty but not null
        isNotNull(validation, "groups", groups);

        // Now, check the individual Groups in the Collection Since we only
        // care for the Id's - we'll just check those. The null check, is to
        // avoid a null pointer exception, the validation against null was made
        // earlier. It is important to note, that the checks against the type of
        // Groups, is made in the Business Logic.
        if (groups != null) {
            for (final Group group : groups) {
                isNotNullOrEmpty(validation, "groups", group.getGroupId());
            }
        }

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PublishGroup)) {
            return false;
        }

        final PublishGroup that = (PublishGroup) obj;

        if (groups != null ? !groups.equals(that.groups) : that.groups != null) {
            return false;
        }

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }

        return !(name != null ? !name.equals(that.name) : that.name != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result;

        result = IWSConstants.HASHCODE_MULTIPLIER * (id != null ? id.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (name != null ? name.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (groups != null ? groups.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "PublishGroup{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", groups=" + groups +
                '}';
    }
}
