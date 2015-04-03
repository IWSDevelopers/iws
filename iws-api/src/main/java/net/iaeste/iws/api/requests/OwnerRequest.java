/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.OwnerRequest
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
@XmlType(name = "OwnerRequest", propOrder = { "group", "user", "title" })
public final class OwnerRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = false) private Group group = null;
    @XmlElement(required = true, nillable = false) private User user = null;
    @XmlElement(required = true, nillable = true)  private String title = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public OwnerRequest() {
    }

    /**
     * Default Constructor,
     *
     * @param group Group Object to change Owner of
     * @param user  User to grant the Ownership to
     */
    public OwnerRequest(final Group group, final User user) {
        setGroup(group);
        setUser(user);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setGroup(final Group group) {
        ensureNotNullAndVerifiable("group", group);
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public void setUser(final User user) {
        ensureVerifiable("user", user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    /**
     * Sets the title for the former owner to this.
     *
     * @param title New title for the former owner
     */
    public void setTitle(final String title) {
        ensureNotTooLong("title", title, 50);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(1);

        isNotNull(validation, "group", group);
        isNotNull(validation, "user", user);

        return validation;
    }
}
