/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.SearchUserRequest
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
@XmlType(name = "searchUserRequest", propOrder = { "group", "name" })
public final class SearchUserRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true)  private Group group = null;
    @XmlElement(required = true, nillable = false) private String name = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public SearchUserRequest() {
    }

    /**
     * Default Constructor,
     *
     * @param name The Partial name of the user to search for
     */
    public SearchUserRequest(final Group group, final String name) {
        setGroup(group);
        setName(name);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the Member Group to search for members of. If the Group is not a
     * Members Group, then the result will be empty. If no Group is defined,
     * then the search is made among all Users.
     *
     * @param group Member Group to search in
     * @throws IllegalArgumentException if not a valid Group
     */
    public void setGroup(final Group group) throws IllegalArgumentException {
        ensureVerifiable("group", group);
        this.group = new Group(group);
    }

    public Group getGroup() {
        return new Group(group);
    }

    /**
     * Sets the (partial) name of the user to search for. If the name only
     * consists of one part (no spaces), then the search will look for matching
     * first and lastnames. If the name is two part (separated by space), then
     * the search will use the first part as the potential firstname and second
     * part as potential lastname. If the name consists of more blocks, then
     * these will be ignored.<br />
     *   If nothing is provided, i.e. if the field is set to null or an empty
     * String, then the method will throw an {@code IllegalArgumentException}.
     *
     * @param name Partial name to search for
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setName(final String name) throws IllegalArgumentException {
        ensureNotNullOrEmpty("name", name);
        this.name = name;
    }

    public String getName() {
        return name;
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

        isNotNull(validation, "name", name);

        return validation;
    }
}
