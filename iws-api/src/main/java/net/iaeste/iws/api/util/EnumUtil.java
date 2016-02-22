/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.api.util;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.Descriptable;

import java.util.regex.Pattern;

/**
 * Utility Class for the IWS Enumerated Objects.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class EnumUtil {

    private static final Pattern PATTERN_SPACE = Pattern.compile("[\\s]");

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
     * @param <E>  The {@code Descriptable} Enum type
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
        return PATTERN_SPACE.matcher(str.trim()).replaceAll("_").toUpperCase(IWSConstants.DEFAULT_LOCALE);
    }
}
