/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.ContactsRequest
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
import net.iaeste.iws.api.enums.ContactsType;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ContactsRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String userId = null;
    private String groupId = null;
    private ContactsType type = ContactsType.OTHER;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public ContactsRequest() {
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the UserId, so the User with a list of all Group associations can be
     * retrieved. This will also set the ContactType to User.<br />
     *   The method will throw an {@code IllegalArgumentException} if the given
     * UserID is invalid.
     *
     * @param groupId Id of the User to fetch details for
     * @throws IllegalArgumentException if the UserId is invalid
     */
    public void setUserId(final String userId) throws IllegalArgumentException {
        ensureNotNullAndValidId("userId", userId);
        this.type = ContactsType.USER;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    /**
     * Sets the GroupId, so the Group with a list of the associated Users can be
     * retrieved. This will also set the ContactType to Group.<br />
     *   The method will throw an {@code IllegalArgumentException} if the given
     * GroupId is invalid.
     *
     * @param groupId Id of the Group to fetch details for
     * @throws IllegalArgumentException if the GroupId is invalid
     */
    public void setGroupId(final String groupId) throws IllegalArgumentException {
        ensureNotNullAndValidId("groupId", groupId);
        this.type = ContactsType.GROUP;
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public ContactsType getType() {
        return type;
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

        if ((type == ContactsType.USER) && (userId == null)) {
            validation.put("userId", "Invalid User Request, userId is null.");
        } else if ((type == ContactsType.GROUP) && (groupId == null)) {
            validation.put("userId", "Invalid Group Request, groupID is null.");
        }

        return validation;
    }
}
