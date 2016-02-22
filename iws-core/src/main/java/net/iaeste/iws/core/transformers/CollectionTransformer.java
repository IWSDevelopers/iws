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
package net.iaeste.iws.core.transformers;

import net.iaeste.iws.api.constants.exchange.IWSExchangeConstants;
import net.iaeste.iws.api.enums.Descriptable;
import net.iaeste.iws.api.util.EnumUtil;
import net.iaeste.iws.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Transformer for the Collections of values, handles various transformations
 * to/from a string value
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class CollectionTransformer {

    public static final Logger LOG = LoggerFactory.getLogger(CollectionTransformer.class);

    private static final String DELIMITER_REG_EXP = ",|\\" + IWSExchangeConstants.SET_DELIMITER;
    public static final Pattern SPLIT_PATTERN = Pattern.compile(DELIMITER_REG_EXP);

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

        if ((collection != null) && !collection.isEmpty()) {
            final Iterator<T> iterator = collection.iterator();

            while (iterator.hasNext()) {
                final T item = iterator.next();
                sb.append(item.name());
                if (iterator.hasNext()) {
                    sb.append(IWSExchangeConstants.SET_DELIMITER);
                }
            }
        }

        return sb.toString();
    }

    /**
     * Split a string value into a list of enum values
     *
     * @param enumType The Class object of the enum type from which to return a constant
     * @param value    String which is split into the list of enum values
     * @return List of enum values
     */
    public static <E extends Enum<E> & Descriptable<E>> List<E> explodeEnumList(final Class<E> enumType, final String value) {
        final List<E> result = new ArrayList<>(10);

        if (value != null) {
            final String[] array = SPLIT_PATTERN.split(value);

            for (final String str : array) {
                if (!str.isEmpty()) {
                    final E enumValue = EnumUtil.valueOf(enumType, str);
                    result.add(enumValue);
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
    public static <E extends Enum<E> & Descriptable<E>> HashSet<E> explodeEnumSet(final Class<E> enumType, final String value) {
        final List<E> list = explodeEnumList(enumType, value);
        final HashSet<E> result = new HashSet<>();

        if (!list.isEmpty()) {
            result.addAll(list);
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
        return StringUtils.join(collection, IWSExchangeConstants.SET_DELIMITER);
    }

    /**
     * Splits a string into a list of String values
     *
     * @param value String which is split into the list of String values
     * @return List of Strings values
     */
    public static List<String> explodeStringList(final String value) {
        final List<String> result = new ArrayList<>(10);

        if (value != null) {
            // It is amazing how often we have whitespace around the given
            // values, so we're trimming them here.
            final String[] rawList = SPLIT_PATTERN.split(value);
            for (final String str : rawList) {
                result.add(str.trim());
            }
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
