/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.utils.CheckVerification
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.Verifiable;

import java.util.IllegalFormatException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Contains the functionality to properly verify given values.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection VariableNotUsedInsideIf
 * @deprecated Please use the functionality from the AbstractVerification class
 */
@Deprecated
public final class CheckVerification {

    /** The e-mail compliance regular expression. */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(IWSConstants.EMAIL_REGEX);

    /** Private Constructor, this is a utility class. */
    private CheckVerification() {
    }

    /**
     * The method takes a value, and verifies that this value is not null. If
     * the given value is null, then a {@code VerificationException} is thrown.
     *
     * @param field The name of the field (value) to be verified
     * @param value The value to verify
     * @throws VerificationException if the value is null
     */
    @Deprecated
    public static void verifyNotNull(final String field, final Object value) throws VerificationException {
        if (value == null) {
            throw new VerificationException(format("The field '%s' may not be null.", field));
        }
    }

    /**
     * The method takes a value of type {@code Verifiable}, and verifies that
     * this value is not null, and then invokes the verification on it. If the
     * given value is null, then a {@code VerificationException} is thrown.
     *
     * @param field The name of the field (value) to be verified
     * @param value The value to verify
     * @throws VerificationException if the value is null
     */
    @Deprecated
    public static void verifyVerifiable(final String field, final Verifiable value) throws VerificationException {
        verifyNotNull(field, value);

        value.verify();
    }

    /**
     * The method takes a value, and verifies that this value is neither null,
     * nor empty. If the given value is either null or empty, then a
     * {@code VerificationException} is thrown.
     *
     * @param field The name of the field (value) to be verified
     * @param value The value to verify
     * @throws VerificationException if the value is null or empty
     */
    @Deprecated
    public static void verifyNotEmpty(final String field, final String value) throws VerificationException {
        verifyNotNull(field, value);

        if (value.isEmpty()) {
            throw new VerificationException(format("The field '%s' may not be empty.", field));
        }
    }

    /**
     * The method takes a value, and verifies that this value is neither null,
     * nor that the value is outside of the provided range of allowed values.
     * If the given value is either null, or outside of the allowed range, then
     * a {@code VerificationException} is thrown.
     *
     * @param field   The name of the field (value) to be verified
     * @param value   The value to verify
     * @param minimum The Minimal allowed value
     * @param maximum The Maximal allowed value
     * @throws VerificationException if the value is null or outside given range
     * @deprecated please use {@link net.iaeste.iws.api.requests.AbstractVerification}
     */
    @Deprecated
    public static <T extends Number> void verifyLimits(final String field, final T value, final T minimum, final T maximum) throws VerificationException {
        verifyNotNull(field, value);

        // Since the Number is an Abstract type, we need to convert the number
        // to something, which we can then actually check against
        if ((value.doubleValue() < minimum.doubleValue()) || (value.doubleValue() > maximum.doubleValue())) {
            throw new VerificationException(format("The field '%s' must be within the range %d to %d, the given value is %d.", field, minimum, maximum, value));
        }
    }

    /**
     * Checks the given value
     *
     * @param field     The name of the field (value) to be verified
     * @param value     The value to verify
     * @param maxLength The maximum allowed length for the value
     * @throws VerificationException if the value is null or too long
     * @deprecated please use {@link net.iaeste.iws.api.requests.AbstractVerification}
     */
    @Deprecated
    public static void verifyLength(final String field, final String value, final int maxLength) {
        verifyNotNull(field, value);

        if (value.length() > maxLength) {
            throw new VerificationException(format("The field '%s' may noy be longer than %d", field, maxLength));
        }
    }

    /**
     * The method takes a value, and verifies that this value is neither null,
     * nor that the value is outside of the provided range of allowed values.
     * If the given value is either null, or outside of the allowed range, then
     * a {@code VerificationException} is thrown.<br />
     * Verifies if a given e-mail address is valid or not. If the given value,
     * is not compliant with the e-mail format, then a
     * {@code VerificationException} is thrown.
     *
     * @param field The name of the field (value) to be verified
     * @param value The value to verify
     * @throws VerificationException if the e-mail addresss isn't compliant
     * @deprecated please use {@link net.iaeste.iws.api.requests.AbstractVerification}
     */
    @Deprecated
    public static void verifyEmail(final String field, final String value) throws VerificationException {
        verifyNotNull(field, value);

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new VerificationException(format("The field '%s' isn't compliant with the allowed format for e-mail's: %s.", field, IWSConstants.EMAIL_REGEX));
        }
    }

    /**
     * The method checks if the given value is null or not. If it is null, then
     * the error is written to the provided Map.
     *
     * @param errors Map with Error information
     * @param field  The name of the field (value) to be verified
     * @param value  The value to verify
     * @deprecated please use {@link net.iaeste.iws.api.requests.AbstractVerification}
     */
    @Deprecated
    public static void addNullErrorToMap(final Map<String, String> errors, final String field, final Object value) {
        if (value == null) {
            errors.put(field, "May not be null.");
        }
    }

    /**
     * The method takes a value, and verifies that this value is neither null,
     * nor empty. If the given value is either null or empty, the information is
     * added to the provided error Map
     *
     * @param errors Map with Error information
     * @param field  The name of the field (value) to be verified
     * @param value  The value to verify
     * @deprecated please use {@link net.iaeste.iws.api.requests.AbstractVerification}
     */
    @Deprecated
    public static void addEmptyErrorToMap(final Map<String, String> errors, final String field, final String value) {
        if ((value == null) || value.isEmpty()) {
            errors.put(field, "May not be null or empty.");
        }
    }

    /**
     * The method takes a value, and verifies that this value is neither null,
     * nor empty. If the given value is either null or empty, the information is
     * added to the provided error Map
     *
     * @param errors Map with Error information
     * @param field  The name of the field (value) to be verified
     * @param value  The value to verify
     * @deprecated please use {@link net.iaeste.iws.api.requests.AbstractVerification}
     */
    @Deprecated
    public static void addEmptyErrorToMap(final Map<String, String> errors, final String field, final Set<?> value) {
        if ((value == null) || value.isEmpty()) {
            errors.put(field, "May not be null or empty.");
        }
    }

    // =========================================================================
    // Internal methods
    // =========================================================================

    /**
     * Formats a given String, using the built-in String format method. If there
     * is a problem with formatting the String, then the method will throw an
     * IllegalFormatException. Otherwise, the method will return the result of
     * formatting the String.
     */
    private static String format(final String message, final Object... args) throws IllegalFormatException {
        return String.format(message, args);
    }
}
