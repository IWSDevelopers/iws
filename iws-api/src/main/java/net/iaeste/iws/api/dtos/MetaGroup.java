/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.MetaGroup
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
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.responses.AbstractResponse;
import net.iaeste.iws.api.util.Copier;

import java.security.Permissions;
import java.util.Set;

/**
 * The MetaGroups are the GroupTypes, with additional information, which is
 * relevant for understanding / choosing.<br />
 *   An important part of the MetaGroup information, is the set of Permissions,
 * that is associated with the GroupType. The IWS determines if a user may
 * perform a specific Action, based on the joined result of the Users Role based
 * Permissions, and the GroupType specific Permissions.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CastToConcreteClass
 */
public final class MetaGroup extends AbstractResponse {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * The Actual GroupType for this MetaGroup. The GroupType is also used as
     * the internal Identifier.
     */
    private GroupType groupType = null;

    /**
     * A description of the GroupType (MetaGroup), which correlates with the
     * information written in the JavaDoc.
     */
    private String description = null;

    /**
     * A GroupType is associated with a set of Permissions, which together with
     * a Users Role will determine if a User may perform a given action or not.
     */
    private Set<Permissions> permissions = null;

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public MetaGroup() {
    }

    /**
     * Default Constructor.
     *
     * @param groupType   The GroupType for this MetaGroup
     * @param description The description for the GroupType, same as JavaDoc
     * @param permissions The Set of Permissions for the Group
     */
    public MetaGroup(final GroupType groupType, final String description, final Set<Permissions> permissions) {
        this.groupType = groupType;
        this.description = description;
        this.permissions = Copier.copy(permissions);
    }

    /**
     * Copy Constructor.
     *
     * @param metaGroup MetaGroup Object to copy
     */
    public MetaGroup(final MetaGroup metaGroup) {
        if (metaGroup != null) {
            groupType = metaGroup.groupType;
            description = metaGroup.description;
            permissions = Copier.copy(metaGroup.permissions);
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setGroupType(final GroupType groupType) {
        this.groupType = groupType;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPermissions(final Set<Permissions> permissions) {
        this.permissions = Copier.copy(permissions);
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof MetaGroup)) {
            return false;
        }

        final MetaGroup metaGroup = (MetaGroup) obj;

        if (description != null ? !description.equals(metaGroup.description) : metaGroup.description != null) {
            return false;
        }

        if (groupType != metaGroup.groupType) {
            return false;
        }

        return !(permissions != null ? !permissions.equals(metaGroup.permissions) : metaGroup.permissions != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (groupType != null ? groupType.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (description != null ? description.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (permissions != null ? permissions.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "MetaGroup{" +
                "groupType=" + groupType +
                ", description='" + description + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
