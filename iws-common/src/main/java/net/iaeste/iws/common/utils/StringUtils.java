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
package net.iaeste.iws.common.utils;

import net.iaeste.iws.api.constants.IWSConstants;

import java.util.regex.Pattern;

/**
 * IWS String Utility
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class StringUtils {

    public static final String EMPTY_STRING = "";
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final Pattern PATTERN_NEWLINE = Pattern.compile("\\r\\n|\\r|\\n");

    /**
     * Private Constructor, this is a utility class.
     */
    private StringUtils() {
    }

    /**
     * @see String#toLowerCase(java.util.Locale)
     */
    public static String toLower(final String str) {
        return (str != null) ? str.toLowerCase(IWSConstants.DEFAULT_LOCALE) : null;
    }

    /**
     * @see String#toUpperCase(java.util.Locale)
     */
    public static String toUpper(final String str) {
        return (str != null) ? str.toUpperCase(IWSConstants.DEFAULT_LOCALE) : null;
    }

    /**
     * Converts a String with multiple words, separated by spaces to a new
     * String, where each word starts with an uppercase character and is
     * otherwise lowercase.
     *
     * @param str String to Capitalize
     * @return Capitalized String
     */
    public static String capitalizeFully(final String str) {
        // Before beginning, we must ensure that the String is completely
        // lowercase, otherwise it may end up being strange to behold the
        // result.
        final char[] buffer = str.toLowerCase(IWSConstants.DEFAULT_LOCALE).toCharArray();
        // As we wish to uppercase the first word, we'll start out positive.
        boolean capitalizeNext = true;

        for (int i = 0; i < buffer.length; ++i) {
            final char ch = buffer[i];

            if (Character.isWhitespace(ch)) {
                // Found a whitespace character, so we will turn the next
                // character to uppercase.
                capitalizeNext = true;
            } else if (capitalizeNext) {
                // We're at the beginning of a word, so we will turn this
                // character to upper case, and then let the remaining word
                // be lowercase.
                buffer[i] = Character.toTitleCase(ch);
                capitalizeNext = false;
            }
        }

        return new String(buffer);
    }

    /**
     * Joins the elements of the provided {@code Iterable} into a single String
     * containing the provided elements.<br />
     *   If the given iterable element contains two or more Objects, then they
     * are joined with the separator. If no Objects exists, the Empty String is
     * returned, if only a single Object exists, then the String representation
     * of this is returned.<br />
     *   Note, The code is a replacement of the formerly used Apache Commons
     * Join method, to avoid dragging unwanted dependencies along. From analysis
     * of the Apache Commons Join method, it seems that the logic is written for
     * pre Java 5 code, so it has been rewritten to better suit Java 5+.
     *
     * @param iterable  {@code Iterable} Object to join together
     * @param separator the separator to use, if multiple Objects exists
     * @return The joined String
     */
    public static String join(final Iterable<?> iterable, final String separator) {
        final String result;

        if (iterable != null) {
            final StringBuilder builder = new StringBuilder(16);
            boolean theFirst = true;

            for (final Object obj : iterable) {
                if (theFirst) {
                    builder.append(obj);
                    theFirst = false;
                } else {
                    builder.append(separator);
                    builder.append(obj);
                }
            }
            result = builder.toString();
        } else {
            result = EMPTY_STRING;
        }

        return result;
    }

    /**
     * <p>Splits the provided text into an array, separators specified.
     * This is an alternative to using StringTokenizer.</p>
     * <p/>
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as one separator.
     * For more control over the split use the StrTokenizer class.</p>
     * <p/>
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} separatorChars splits on whitespace.</p>
     * <p/>
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("abc def", null) = ["abc", "def"]
     * StringUtils.split("abc def", " ")  = ["abc", "def"]
     * StringUtils.split("abc  def", " ") = ["abc", "def"]
     * StringUtils.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
     * </pre>
     *
     * @param str            the String to parse, may be null
     * @param separatorChars the characters used as the delimiters,
     *                       {@code null} splits on whitespace
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] split(final String str, final String separatorChars) {
        String[] result = null;

        if (str != null) {
            if (!str.isEmpty()) {
                if (str.contains(separatorChars)) {
                    result = str.split('\\' + separatorChars);
                } else {
                    result = new String[1];
                    result[0] = str;
                }
            } else {
                result = EMPTY_STRING_ARRAY;
            }
        }

        return result;
    }

    /**
     * Converts String into its lower-case plain ASCII transcription.
     * Unknown (non-mapped) characters are replaced by '_'.
     * Keeps '_', '.' and '@' in the input string. Note, that the Normalizer is
     * not working correctly for all the character's we're using, hence we are
     * using a self-controlled replace mechanism which works on a trimmed
     * version of the String and also converts it to lower case.
     *
     * @param  input the String to be converted
     * @return String in plain ASCII
     */
    public static String convertToAsciiMailAlias(final String input) {
        if (input == null) {
            // See Trac task #984
            throw new IllegalArgumentException("Cannot process an undefined (null) e-mail address.");
        } else {
            return input.trim()
                        .replaceAll("[ \\u00a0]", "_")
                        .replaceAll("[ÀÁÂÃĀĂĄàáâãāăą]", "a")
                        .replaceAll("[Åå]", "aa")
                        .replaceAll("[ÄäÆæ]", "ae")
                        .replaceAll("[ÇĆĈĊČçćĉċč]", "c")
                        .replaceAll("[ÐĎĐďđ]", "d")
                        .replaceAll("[ÈÉÊËĒĔĖĘĚèéêëēĕėęě]", "e")
                        .replaceAll("[ĜĞĠĢĝğġģ]", "g")
                        .replaceAll("[ĤĦĥħ]", "h")
                        .replaceAll("[ÌÍÎÏĨĪĬĮİìíîïĩīĭįı]", "i")
                        .replaceAll("[Ĳĳ]", "ij")
                        .replaceAll("[Ĵĵ]", "j")
                        .replaceAll("[Ķķĸ]", "k")
                        .replaceAll("[ĹĻĽĿŁĺļľŀł]", "l")
                        .replaceAll("[ÑŃŅŇŊñńņňŉŋ]", "n")
                        .replaceAll("[ÒÓÔÕŌŎŐðòóôõōŏő]", "o")
                        .replaceAll("[ŒÖØœöø]", "oe")
                        .replaceAll("[ŔŖŘŕŗř]", "r")
                        .replaceAll("[ŚŜŞŠśŝşšſ]", "s")
                        .replaceAll("[ß]", "ss")
                        .replaceAll("[ŢŤŦţťŧ]", "t")
                        .replaceAll("[Þþ]", "th")
                        .replaceAll("[ÙÚÛŨŪŬŮŰŲùúûũūŭůűų]", "u")
                        .replaceAll("[Üü]", "ue")
                        .replaceAll("[Ŵŵ]", "w")
                        .replaceAll("[ÝŶŸýÿŷ]", "y")
                        .replaceAll("[ŹŻŽźżž]", "z")
                        .replaceAll("[×÷]", "")
                        .replaceAll("[^a-zA-Z0-9_'\\.@]", "_")
                        .toLowerCase(IWSConstants.DEFAULT_LOCALE);
        }
    }

    public static String removeLineBreaks(final String input) {
        String result = null;

        if (input != null) {
            result = PATTERN_NEWLINE.matcher(input).replaceAll(" ");
        }

        return result;
    }
}
