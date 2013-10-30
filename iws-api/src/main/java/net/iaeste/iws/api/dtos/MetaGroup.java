/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
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

import static net.iaeste.iws.api.util.Copier.copy;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.util.AbstractFallible;

import java.security.Permissions;
import java.util.Set;

/**
 * The MetaGroups are the GroupTypes, with additional information, which is
 * relevant for understanding / choosing.<br />
 *   An important part of the MetaGroup information, is the set of Permissions,
 * that is associated with the GroupType. The IWS determines if a user may
 * perform a specific Action, based on the joined result of the Users Role based
 * Permissions, and the GroupType specific Permissions.<br />
 *   The Roles Object & the Authorization Object, both link the User associated
 * Permissions together with the Groups. This Object is therefore considered
 * deprecated, since the only goal is to mimic this. The Object is also not used
 * anywhere, so it should be dropped as soon as possible.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Deprecated
public final class MetaGroup extends AbstractFallible {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private GroupType groupType = null;
    private String description = null;
    private Set<Permissions> permissions = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

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
        this.permissions = copy(permissions);
    }

    /**
     * Copy Constructor.
     *
     * @param metaGroup MetaGroup Object to copy
     */
    public MetaGroup(final MetaGroup metaGroup) {
        if (metaGroup != null) {
            groupType = copy(metaGroup.groupType);
            description = metaGroup.description;
            permissions = copy(metaGroup.permissions);
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the Actual GroupType for this MetaGroup. The GroupType is also used
     * as the internal Identifier.
     *
     * @param groupType Group Type
     */
    public void setGroupType(final GroupType groupType) {
        this.groupType = copy(groupType);
    }

    /**
     * Retrieves the Actual GroupType for this MetaGroup. The GroupType is also
     * used as the internal Identifier.
     *
     * @return Group Type
     */
    public GroupType getGroupType() {
        return copy(groupType);
    }

    /**
     * Sets the description of the GroupType (MetaGroup), which correlates with
     * the information written in the JavaDoc.
     *
     * @param description MetaGroup description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Retrieves the description of the GroupType (MetaGroup), which correlates
     * with the information written in the JavaDoc.
     *
     * @return MetaGroup description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the Set of Permissions, which the GroupType is associated with.
     * Together with the Users Role, this will determine if a User may perform
     * a given action or not (from the common subset).
     *
     * @param permissions Set of Permissions
     */
    public void setPermissions(final Set<Permissions> permissions) {
        this.permissions = copy(permissions);
    }

    /**
     * Retrieves the Set of Permissions, which the GroupType is associated with.
     * Together with the Users Role, this will determine if a User may perform
     * a given action or not (from the common subset).
     *
     * @return Set of Permissions
     */
    public Set<Permissions> getPermissions() {
        return copy(permissions);
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
