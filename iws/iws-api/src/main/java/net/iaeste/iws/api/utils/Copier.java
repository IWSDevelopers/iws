/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.utils.Copier
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility Class, containing a single method called "copy". This method is
 * written in different versions, depending on the given input data. The main
 * reason for using this, is to ensure that object, which is not immutable is
 * properly copied - to avoid leaking internal data references.
 *
 * @author  Kim Jensen
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class Copier {

    // Note: Java Generics has a flaw, which means that it doesn't support
    // normal arrays (i.e. Object[])!
    // See: http://forums.sun.com/thread.jspa?threadID=530823 (link doesn't work after Oracle took over Sun)
    // See: http://stackoverflow.com/questions/529085/java-how-to-generic-array-creation
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    /**
     * Private Constructor, this is a utility class.
     */
    private Copier() {
    }

    /**
     * Copies the given List, to ensure that the new list is not exposing any
     * references. If the given list is null, then a new empty list is returned,
     * to avoid a potential {@code NullPointerException}.
     *
     * @param  original  The List to copy
     * @return Copy of the given List, or an empty List
     */
    public static <T> List<T> copy(final List<T> original) {
        final List<T> copy;

        if (original != null) {
            copy = new ArrayList<>(original.size());
            for (final T t : original) {
                copy.add(t);
            }
        } else {
            copy = new ArrayList<>(0);
        }

        return copy;
    }

    /**
     * Copies the given Map, to ensure that the new Map is not exposing any
     * references. If the given Map is null, then a new empty Map is returned,
     * to avoid a potential {@code NullPointerException}.
     *
     * @param  original  The Map to copy
     * @return Copy of the given Map, or an empty Map
     */
    public static <T, V> Map<T, V> copy(final Map<T, V> original) {
        final Map<T, V> copy;

        if (original != null) {
            copy = new HashMap<>(original.size());
            for (final Map.Entry<T, V> entry : original.entrySet()) {
                copy.put(entry.getKey(), entry.getValue());
            }
        } else {
            copy = new HashMap<>(0);
        }

        return copy;
    }

    /**
     * Copies the given Array, to ensure that the new Array is not exposing any
     * references. If the given Array is null, then a new empty Array is
     * returned, to avoid a potential {@code NullPointerException}.
     *
     * @param  original  The Array to copy
     * @return Copy of the given Array, or an empty Array
     */
    public static String[] copy(final String[] original) {
        final String[] copy;

        if (original != null) {
            copy = new String[original.length];
            System.arraycopy(original, 0, copy, 0, original.length);
        } else {
            copy = EMPTY_STRING_ARRAY;
        }

        return copy;
    }

    /**
     * Copies the given Array, to ensure that the new Array is not exposing any
     * references. If the given Array is null, then a new empty Array is
     * returned, to avoid a potential {@code NullPointerException}.
     *
     * @param  original  The Array to copy
     * @return Copy of the given Array, or an empty Array
     */
    public static byte[] copy(final byte[] original) {
        final byte[] copy;

        if (original != null) {
            copy = new byte[original.length];
            System.arraycopy(original, 0, copy, 0, original.length);
        } else {
            copy = EMPTY_BYTE_ARRAY;
        }

        return copy;
    }

    /**
     * Copies the given Date object, as the original Date object is not
     * immutable. If the given Date is null, then a null value is returned,
     * since it makes little sense to return a new Date object.
     *
     * @param  original  The Date to copy
     * @return Copy of the given Date, or null
     */
    public static Date copy(final Date original) {
        final Date copy;

        if (original != null) {
            copy = new Date(original.getTime());
        } else {
            copy = null;
        }

        return copy;
    }
}
