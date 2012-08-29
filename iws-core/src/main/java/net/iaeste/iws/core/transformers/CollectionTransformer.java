/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.ListTransformer
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

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Tranformer for the Collections of values, handles various transformations
 * to/from a string value
 *
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class CollectionTransformer {
    public static final String delimiter = "|";
    private static final String delimiterRegExp = "\\|";

    private CollectionTransformer() {
    }

    /**
     * Concatenates a collection of enum values into the one string
     *
     * @param collection Collection of values to be concatenated
     * @return concatenated String
     */
    public static <T extends Enum<T>> String concatEnumCollection(final Collection<T> collection) {
        final StringBuilder sb = new StringBuilder();
        if (collection != null && !collection.isEmpty()) {
            //noinspection ForLoopWithMissingComponent
            for (Iterator<T> iterator = collection.iterator(); iterator.hasNext(); ) {
                final T item = iterator.next();
                sb.append(item.name());
                if (iterator.hasNext()) {
                    sb.append(delimiter);
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
        final List<T> result = new ArrayList<>();
        if (value != null) {
            final String[] array = value.split(delimiterRegExp);
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
        if (list.isEmpty()) {
            return EnumSet.noneOf(enumType);
        } else {
            return EnumSet.copyOf(list);
        }
    }

    /**
     * Concatenates a list of values into the one String
     *
     * @param collection Collection of Strings to be concatenated
     * @return concatenated String
     */
    public static String join(final Collection<?> collection) {
        return StringUtils.join(collection, delimiter);
    }

    /**
     * Splits a string into a list of String values
     *
     * @param value String which is split into the list of String values
     * @return List of Strings values
     */
    public static List<String> explodeStringList(final String value) {
        List<String> result = new ArrayList<>();
        if (value != null) {
            result = Arrays.asList(StringUtils.split(value, delimiter));
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
