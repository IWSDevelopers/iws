/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.EnumUtil
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.util;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.Descriptable;

/**
 * Utility Class for the IWS Enumerated Objects.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class EnumUtil {

    /** Private Constructor, this is a utility Class. */
    private EnumUtil() {}

    /**
     * The default Enumerated method valueOf, is very restrictive regarding what
     * it will accept. To minimize the number of errors we're seeing in the IWS
     * log files, a more lenient approach is required. So we're looking at the
     * value case insensitive and also comparing the value with the description
     * that it may have. Hopefully this will reduce the amount of User errors.
     *
     * @param type The Enumerated Type to check for values of
     * @param str  String to convert to an Enumerated value
     * @return The result of the check
     * @throws IllegalArgumentException if no match was found
     */
    public static <E extends Enum<E> & Descriptable<E>> E valueOf(final Class<E> type, final String str) throws IllegalArgumentException {
        E value = null;

        if (str != null) {
            final String given = convertToEnum(str.trim());
            try {
                value = Enum.valueOf(type, given);
            } catch (IllegalArgumentException e) {
                for (final E field : type.getEnumConstants()) {
                    final String current = convertToEnum(field.getDescription());
                    if (given.equals(current)) {
                        value = field;
                    }
                }

                if (value == null) {
                    throw e;
                }
            }
        }

        return value;
    }

    /**
     * To help the comparison along - we're cleaning the string to be checked
     * using this little method. The given String is trimmed, having all white
     * space replaced with underscores and finally turned to Upper Case. This
     * will ensure that it by default will look as close as possible to the
     * enum values. And if no value was found, we will traverse the
     * descriptions.
     *
     * @param str String to "enumerate"
     * @return Converted String
     */
    private static String convertToEnum(final String str) {
        return str.trim().replaceAll("[\\s]", "_").toUpperCase(IWSConstants.DEFAULT_LOCALE);
    }
}
