/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.CommonService
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.services;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.common.utils.HashcodeGenerator;
import net.iaeste.iws.core.exceptions.PermissionException;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.GroupEntity;

import java.util.UUID;

/**
 * All Common Service funtionality is collected here. Although the Class ought
 * to be Abstract, since we should (or cat) not use it directly, it should not
 * be instantiated anywhere, but rather just extended in our Actual Services.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class CommonService {

    /**
     * Formats a given String using our default {@code Locale} and returns the
     * result.
     *
     * @param message The String to format
     * @param objects Objects to be added to the String
     * @return Formatted String
     */
    protected String format(final String message, final Object... objects) {
        return String.format(IWSConstants.DEFAULT_LOCALE, message, objects);
    }

    /**
     * Checks if the user is permitted to access the requested Object, by
     * comparing the Owning Group for the Object. If not allowed, then a
     * {@code PermissionException} is thrown.
     *
     * @param authentication Authentication Object
     * @param group          The group to check if the user is in
     */
    protected void permissionCheck(final Authentication authentication, final GroupEntity group) {
        if (!authentication.getGroup().getId().equals(group.getId())) {
            throw new PermissionException("User is not member of the group " + group.getGroupName());
        }
    }
}
