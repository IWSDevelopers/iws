/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.util.EnumHelper
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.fe.util;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * JSF Helper class for converting Enum values into
 * keys that can be used in property files
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@Named
@Singleton
public class EnumHelper {

    /**
     * Converts an Enum value into a String with the
     * format EnumClassSimpleName.EnumValueName
     *
     * @param value the Enum value that should be converted
     * @return the converted value as String
     */
    public String toMessageKey(Enum value) {
        return value.getClass().getSimpleName().concat(".").concat(value.name());
    }
}
