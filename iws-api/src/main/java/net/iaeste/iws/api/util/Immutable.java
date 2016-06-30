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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public final class Immutable {

    private Immutable() {
    }

    // =========================================================================
    // Common Object Immutability Methods
    // =========================================================================

    /**
     * Takes a Date Object, and returns a copy of it.
     *
     * @param date Date Object to copy
     * @return Copied Date Object
     */
    public static Date copy(final Date date) {
        return (date != null) ? new Date(date.getTime()) : null;
    }

    // =========================================================================
    // Collections Immutability Methods
    // =========================================================================

    /**
     * Takes a given Set, and returns an immutable variant of it.
     *
     * @param set The Set to prepare an Immutable variant for
     * @param <S> The Set type
     * @return Immutable version of the Set
     */
    public static <S> Set<S> immutableSet(final Set<S> set) {
        // IW4 has a problem with an immutable map, hence we cannot just use
        // the nifty Collections feature "unmodifiable", which will add
        // protection to the given set.
        Set<S> copy = null;

        if (set != null) {
            copy = new HashSet<>(set.size());
            copy.addAll(set);
        }

        return copy;
    }

    /**
     * Takes a given List, and returns an immutable variant of it.
     *
     * @param list The List to prepare an Immutable variant for
     * @param <S> The List type
     * @return Immutable version of the List
     */
    public static <S> List<S> immutableList(final List<S> list) {
        // IW4 has a problem with an immutable map, hence we cannot just use
        // the nifty Collections feature "unmodifiable", which will add
        // protection to the given list.
        List<S> copy = null;

        if (list != null) {
            copy = new ArrayList<>(list.size());
            copy.addAll(list);
        }

        return copy;
    }

    /**
     * Takes a given Map, and returns an immutable variant of it.
     *
     * @param map The Map to prepare an Immutable variant for
     * @param <K> The Map Key type
     * @param <V> The Map Value type
     * @return Immutable version of the Map
     */
    public static <K,V> Map<K,V> immutableMap(final Map<K,V> map) {
        // IW4 has a problem with an immutable map, hence we cannot just use
        // the nifty Collections feature "unmodifiable", which will add
        // protection to the given map.
        Map<K,V> copy = null;

        if (map != null) {
            copy = new HashMap<>(map.size());
            copy.putAll(map);
        }

        return copy;
    }
}
