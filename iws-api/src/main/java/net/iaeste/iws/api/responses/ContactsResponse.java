/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.ContactsResponse
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.responses;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.ContactsType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contactsResponse", propOrder = { "users", "groups", "type" })
public final class ContactsResponse extends FallibleResponse {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true) private List<User> users = null;
    @XmlElement(required = true, nillable = true) private List<Group> groups = null;
    @XmlElement(required = true, nillable = true) private ContactsType type = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public ContactsResponse() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Error Constructor.
     *
     * @param error    IWS Error Object
     * @param message  Error Message
     */
    public ContactsResponse(final IWSError error, final String message) {
        super(error, message);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setUsers(final List<User> users) {
        this.users = users;
    }

    /**
     * Will retrieve a list of Users for a Group request. If from a User
     * request, then the list will only contain a single record, the User which
     * was requested. For Other requests, the result is null.
     *
     * @return List with either 1 User, all Users or empty list
     */
    public List<User> getUsers() {
        return users;
    }

    public void setGroups(final List<Group> groups) {
        this.groups = groups;
    }

    /**
     * Will retrieve either a list with a single Group if it was from a Group
     * request, or a list of Groups if it was a User request or all Member and
     * International Groups, if it was an Other request.
     *
     * @return List with either 1 Group, All User Groups or all Top Groups
     */
    public List<Group> getGroups() {
        return groups;
    }

    public void setType(final ContactsType type) {
        this.type = type;
    }

    /**
     * The type of request is taken from the request.
     *
     * @return The requested type
     */
    public ContactsType getType() {
        return type;
    }
}
