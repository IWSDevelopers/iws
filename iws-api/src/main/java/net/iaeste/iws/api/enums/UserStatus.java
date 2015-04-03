/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.UserStatus
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums;

import javax.xml.bind.annotation.XmlType;

/**
 * Users can have different status in the system, by default all users are
 * Active. However, a user can also be prevented from logging in - this is
 * a useful feature for someone who are temporarily unavailable. Finally, the
 * user can be deleted. However, as deleting normally implies that the data is
 * removed, the IWS will simply mark the account deleted, and delete all data
 * associated with it. This way, it is still possible to see the things the,
 * now former, user have made in various groups.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "UserStatus")
public enum UserStatus {

    NEW,

    ACTIVE,

    SUSPENDED,

    DELETED
}
