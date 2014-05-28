/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.FetchUserRequest
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
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * Request Object for fetching a User Object.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class FetchUserRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String userId = null;
    private String name = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Default Constructor.
     */
    public FetchUserRequest() {
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Setting the Id of the User to retrieve. The details of the user is
     * depending on the permisions that the user have granted others via the
     * privacy settings.
     *
     * @param userId User Id
     */
    public void setUserId(final String userId) {
        ensureValidId("userId", userId);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    /**
     * When setting the name, and no User Id exists in this request Object, then
     * the IWS will user the name to find a user. The name can be partial, but
     * if special characters has been used in the name, and a lookup is made
     * with non-special characters, then the method will not find the requested
     * person.
     *
     * @param name Partial first or last name
     */
    public void setName(final String name) {
        ensureNotEmpty("name", name);
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

        if (userId == null && name == null) {
            validation.put("userId & name", "No information is present to find a user from.");
        } else if (userId != null) {
            if (userId.length() != 36) {
                validation.put("userId", "No valid UserId is present.");
            }
        }

        return validation;
    }
}
