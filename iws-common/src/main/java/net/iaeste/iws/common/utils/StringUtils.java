/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.utils.StringUtils
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.common.utils;

import net.iaeste.iws.api.constants.IWSConstants;

/**
 * Wrapper for the Apache Commons Lang3 StringUtils.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class StringUtils {

    public static final String EMPTY_STRING = "";
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * Private Constructor, this is a utility class.
     */
    private StringUtils() {
    }

    /**
     * @see String#toLowerCase(java.util.Locale)
     */
    public static String toLower(final String str) {
        return str != null ? str.toLowerCase(IWSConstants.DEFAULT_LOCALE) : null;
    }

    /**
     * @see String#toUpperCase(java.util.Locale)
     */
    public static String toUpper(final String str) {
        return str != null ? str.toUpperCase(IWSConstants.DEFAULT_LOCALE) : null;
    }

    /**
     * Joins the elements of the provided {@code Iterable} into a single String
     * containing the provided elements.<br />
     *   If the given iterable element contains two or more Objects, then they
     * are joined with the separator. If no Objects exists, the Empty String is
     * returned, if only a single Object exists, then the String represenation
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
        //return org.apache.commons.lang3.StringUtils.join(iterable, separator);
        final String result;

        if (iterable != null) {
            final StringBuilder builder = new StringBuilder(16);
            boolean theFirst = true;

            for (final Object obj : iterable) {
                if (theFirst) {
                    builder.append(obj.toString());
                    theFirst = false;
                } else {
                    builder.append(separator);
                    builder.append(obj.toString());
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
        //return org.apache.commons.lang3.StringUtils.split(str, separatorChars);
        final String[] result;

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
        } else {
            result = null;
        }

        return result;
    }

    /**
     * Converts String into its lower-case plain ASCII transcription.
     * Unknown (non-mapped) characters are replaced by '_'.
     * Keeps '_', '.' and '@' in the input string.
     *
     * @param  input the String to be converted
     * @return String in plain ASCII
     */
    public static String convertToAsciiMailAlias(final String input) {
        //Normalizer doesn't touch e.g. Norwegaian character
        //return Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").replaceAll(" ", "_");
        //TODO: what is the representation of &nbsp; in Java's String? simple space, &nbsp;, \u00a0, ...?
        return input.replaceAll("[ \\u00a0]", "_")
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
                          .replaceAll("[ŹŻŽŽźżž]", "z")
                          .replaceAll("[×÷]", "")
                          .replaceAll("[^a-zA-Z0-9_'\\.@]", "_")
                          .toLowerCase();
    }

    public static String removeLineBreaks(final String input) {
        String result = null;
        if (input != null) {
            result = input.replaceAll("\\r\\n|\\r|\\n", " ");
        }
        return result;
    }


}
