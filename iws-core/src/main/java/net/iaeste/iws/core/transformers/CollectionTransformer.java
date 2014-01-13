/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.CollectionTransformer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.transformers;

import net.iaeste.iws.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Tranformer for the Collections of values, handles various transformations
 * to/from a string value
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class CollectionTransformer {

    public static final String DELIMITER = "|";
    private static final String DELIMITER_REG_EXP = "\\|";
    private static final Pattern SPLIT_PATTERN = Pattern.compile(DELIMITER_REG_EXP);

    /**
     * Private Constructor, this is a utility class.
     */
    private CollectionTransformer() {
    }

    /**
     * Concatenates a collection of enum values into the one string
     *
     * @param collection Collection of values to be concatenated
     * @return concatenated String
     */
    public static <T extends Enum<T>> String concatEnumCollection(final Collection<T> collection) {
        final StringBuilder sb = new StringBuilder(10);

        if (collection != null && !collection.isEmpty()) {
            final Iterator<T> iterator = collection.iterator();

            while (iterator.hasNext()) {
                final T item = iterator.next();
                sb.append(item.name());
                if (iterator.hasNext()) {
                    sb.append(DELIMITER);
                }
            }
        }

        return sb.toString();
    }

    /**
     * Split a string value into a list of enum values
     *
     * @param enumType The Class object of the enum type from which to return a constant
     * @param value    String which is splited into the list of enum values
     * @return List of enum values
     */
    public static <T extends Enum<T>> List<T> explodeEnumList(final Class<T> enumType, final String value) {
        final List<T> result = new ArrayList<>(10);
        if (value != null) {
            final String[] array = SPLIT_PATTERN.split(value);
            for (final String s : array) {
                try {
                    final T v = Enum.valueOf(enumType, s);
                    result.add(v);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
        return result;
    }

    /**
     * Split a string value into a set of enum values
     *
     * @param enumType The Class object of the enum type from which to return a constant
     * @param value    String which is split into the list of enum values
     * @return Set of enum values
     */
    public static <T extends Enum<T>> Set<T> explodeEnumSet(final Class<T> enumType, final String value) {
        final List<T> list = explodeEnumList(enumType, value);
        final Set<T> result;

        if (list.isEmpty()) {
            result = EnumSet.noneOf(enumType);
        } else {
            result = EnumSet.copyOf(list);
        }

        return result;
    }

    /**
     * Concatenates a list of values into the one String
     *
     * @param collection Collection of Strings to be concatenated
     * @return concatenated String
     */
    public static String join(final Collection<String> collection) {
        return StringUtils.join(collection, DELIMITER);
    }

    /**
     * Splits a string into a list of String values
     *
     * @param value String which is split into the list of String values
     * @return List of Strings values
     */
    public static List<String> explodeStringList(final String value) {
        List<String> result = new ArrayList<>(10);

        if (value != null) {
            result = Arrays.asList(StringUtils.split(value, DELIMITER));
        }

        return result;
    }

    /**
     * Splits a string into a set of String values
     *
     * @param value String which is split into the set of String values
     * @return Set of Strings values
     */
    public static Set<String> explodeStringSet(final String value) {
        return new HashSet<>(explodeStringList(value));
    }
}
