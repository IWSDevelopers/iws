/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.InternationalGroupRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.util.AbstractVerification;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "internationalGroupRequest", propOrder = { "group", "user", "status" })
public final class InternationalGroupRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = false) private Group group = null;
    @XmlElement(required = true, nillable = false) private User user = null;
    @XmlElement(required = true, nillable = false) private GroupStatus status = GroupStatus.ACTIVE;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public InternationalGroupRequest() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Simple Constructor, to use when only need to change an International
     * Group. This will not change the current Owner of the Group.
     *
     * @param group Group to update
     */
    public InternationalGroupRequest(final Group group) {
        setGroup(group);
    }

    /**
     * Full Constructor, to use when creating a new International Group or when
     * updating the current Coordinator (Owner) of the International Group.
     *
     * @param group International Group to either create or update
     * @param user  User to set as Coordinator (Owner)
     */
    public InternationalGroupRequest(final Group group, final User user) {
        setGroup(group);
        setUser(user);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the Group to be processed. The Group Object must be valid, otherwise
     * the method will throw an IllegalArgument Exception.
     *
     * @param group Group to process
     * @throws IllegalArgumentException if Group is null or nor verifiable
     */
    public void setGroup(final Group group) throws IllegalArgumentException {
        ensureNotNullAndVerifiable("group", group);
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    /**
     * Sets the User to be added as Coordinator (Owner) of the International
     * Group. The User must be verifiable, otherwise the method will throw an
     * IllegalArgument Exception.
     *
     * @param user User to set as Coordinator (Owner)
     * @throws IllegalArgumentException if not verifiable
     */
    public void setUser(final User user) throws IllegalArgumentException {
        ensureVerifiable("user", user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    /**
     * Sets the Status of the Group, if it should change. The Status must be one
     * of the known valid stats of a Group, which includes "Active", "Suspended"
     * or "Deleted". If "Deleted" is chosen, then the Group will be deleted from
     * the system.
     *
     * @param status New Status for the Group
     * @throws IllegalArgumentException if status is null
     */
    public void setStatus(final GroupStatus status) throws IllegalArgumentException {
        ensureNotNull("status", status);
        this.status = status;
    }

    public GroupStatus getStatus() {
        return status;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNullAndVerifiable(validation, "group", group);
        isVerifiable(validation, "user", user);
        isNotNull(validation, "status", status);

        return validation;
    }
}
