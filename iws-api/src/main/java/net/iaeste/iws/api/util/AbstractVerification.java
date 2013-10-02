/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.AbstractVerification
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
import net.iaeste.iws.api.constants.exchange.IWSExchangeConstants;
import net.iaeste.iws.api.exceptions.VerificationException;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * All Input validation is handled via this class. It contains a number of
 * "ensure..." Methods, which all throw {@code IllegalArgumentException} if the
 * input is not allowed.<br />
 *   The main purpose of these checks, is to ensure that the IWS Objects fails
 * as early as possible, so no unneeded requests are made to the IWS.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public abstract class AbstractVerification implements Verifiable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    // Our internal error strings
    private static final String ERROR_NOT_NULL = "The field %s may not be null.";
    private static final String ERROR_NOT_EMPTY = "The field %s may not be empty.";
    private static final String ERROR_NOT_LONGER = "The field %s may not be longer than %d";
    private static final String ERROR_COLLECTION_LONGER = "The field %s may not containt more than %d Objects.";
    private static final String ERROR_TOO_SHORT = "The field %s must be at least %d characters long";
    private static final String ERROR_NOT_EXACT_LENGTH = "The field %s is not matching the required length %d.";
    private static final String ERROR_NOT_WITHIN_LIMITS = "The field %s is not within the required limits from %s to %d.";
    private static final String ERROR_INVALID = "The field %s is invalid.";
    private static final String ERROR_INVALID_REGEX = "The field %s does not follow the required format %s.";
    private static final String ERROR_NOT_VERIFABLE = "The field %s is not verifiable.";
    private static final String ERROR_INVALID_EMAIL = "The e-mail address %s (%s) is invalid.";
    private static final String ERROR_INVALID_REFNO = "The provided reference number (refno) %s is invalid.";

    // Our internal constants to verify the Id
    private static final String UUID_FORMAT = "[\\da-z]{8}-[\\da-z]{4}-[\\da-z]{4}-[\\da-z]{4}-[\\da-z]{12}";
    private static final Pattern UUID_PATTERN = Pattern.compile(UUID_FORMAT);
    //  Internal pattern for verifying Offer reference numbers
    private static final Pattern REFNO_PATTERN = Pattern.compile(IWSExchangeConstants.REFNO_FORMAT);

    /**
     * {@inheritDoc}
     */
    @Override
    public final void verify() throws VerificationException {
        final Map<String, String> validationResult = validate();

        if (!validationResult.isEmpty()) {
            throw new VerificationException("Validation failed: " + validationResult.toString());
        }
    }

    // =========================================================================
    // Ensuring methods for setters, that throws IllegalArgumentExceptions
    // =========================================================================

    /**
     * Throws an {@code IllegalArgumentException} if the given value is null.
     *
     * @param field Name of the field
     * @param value The value for the field
     * @throws IllegalArgumentException if the value is null
     */
    protected static void ensureNotNull(final String field, final Object value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException(format(ERROR_NOT_NULL, field));
        }
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is empty.
     *
     * @param field Name of the field
     * @param value The value for the fieldERROR_TOO_SHORT
     * @throws IllegalArgumentException if the value is empty
     */
    protected static void ensureNotEmpty(final String field, final String value) throws IllegalArgumentException {
        if (value != null && value.isEmpty()) {
            throw new IllegalArgumentException(format(ERROR_NOT_EMPTY, field));
        }
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is empty.
     *
     * @param field Name of the field
     * @param value The value for the fieldERROR_TOO_SHORT
     * @throws IllegalArgumentException if the value is empty
     */
    protected static void ensureNotEmpty(final String field, final Collection<?> value) throws IllegalArgumentException {
        if (value != null && value.isEmpty()) {
            throw new IllegalArgumentException(format(ERROR_NOT_EMPTY, field));
        }
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is null or
     * empty.
     *
     * @param field Name of the field
     * @param value The value of the field
     * @throws IllegalArgumentException if the value is null or empty
     */
    protected static void ensureNotNullOrEmpty(final String field, final String value) throws IllegalArgumentException {
        ensureNotNull(field, value);
        ensureNotEmpty(field, value);
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is null or
     * empty.
     *
     * @param field Name of the field
     * @param value The value of the field
     * @throws IllegalArgumentException if the value is null or empty
     */
    protected static void ensureNotNullOrEmpty(final String field, final Collection<?> value) throws IllegalArgumentException {
        ensureNotNull(field, value);
        ensureNotEmpty(field, value);
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is either
     * empty or too long.
     *
     * @param field  Name of the field
     * @param value  The value of the field
     * @param length The maximum length for the field
     * @throws IllegalArgumentException if the value is empty or too long
     */
    protected static void ensureNotEmptyOrTooLong(final String field, final String value, final int length) throws IllegalArgumentException {
        ensureNotEmpty(field, value);
        ensureNotTooLong(field, value, length);
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is either
     * null or too short.
     *
     * @param field  Name of the field
     * @param value  The value of the field
     * @param length The minimal length for the field
     * @throws IllegalArgumentException if the value is empty or too long
     */
    protected static void ensureNotNullOrTooShort(final String field, final String value, final int length) throws IllegalArgumentException {
        ensureNotNull(field, value);

        if (value.length() < length) {
            throw new IllegalArgumentException(format(ERROR_TOO_SHORT, field, length));
        }
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is too
     * long.
     *
     * @param field  Name of the field
     * @param value  The value of the field
     * @param length The maximum length for the field
     * @throws IllegalArgumentException if the value is too long
     */
    protected static void ensureNotTooLong(final String field, final Collection<?> value, final int length) throws IllegalArgumentException {
        if ((value != null) && (value.size() > length)) {
            throw new IllegalArgumentException(format(ERROR_COLLECTION_LONGER, field, length));
        }
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is too
     * long.
     *
     * @param field  Name of the field
     * @param value  The value of the field
     * @param length The maximum length for the field
     * @throws IllegalArgumentException if the value is too long
     */
    protected static void ensureNotTooLong(final String field, final String value, final int length) throws IllegalArgumentException {
        if ((value != null) && (value.length() > length)) {
            throw new IllegalArgumentException(format(ERROR_NOT_LONGER, field, length));
        }
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is either
     * null or too long.
     *
     * @param field  Name of the field
     * @param value  The value of the field
     * @param length The maximum length for the field
     * @throws IllegalArgumentException if the value is null or too long
     */
    protected static void ensureNotNullOrTooLong(final String field, final Collection<?> value, final int length) throws IllegalArgumentException {
        ensureNotNull(field, value);
        ensureNotTooLong(field, value, length);
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is either
     * null or too long.
     *
     * @param field  Name of the field
     * @param value  The value of the field
     * @param length The maximum length for the field
     * @throws IllegalArgumentException if the value is null or too long
     */
    protected static void ensureNotNullOrTooLong(final String field, final String value, final int length) throws IllegalArgumentException {
        ensureNotNull(field, value);
        ensureNotTooLong(field, value, length);
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is either
     * null, empty or too long.
     *
     * @param field  Name of the field
     * @param value  The value of the field
     * @param length The maximum length for the field
     * @throws IllegalArgumentException if the value is null or empty or too long
     */
    protected static void ensureNotNullOrEmptyOrTooLong(final String field, final String value, final int length) throws IllegalArgumentException {
        ensureNotNullOrEmpty(field, value);
        ensureNotTooLong(field, value, length);
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is either
     * null or not the exact length.
     *
     * @param field  Name of the field
     * @param value  The value of the field
     * @param length The exact length of the field
     * @throws IllegalArgumentException if the value is null not of exact length
     */
    protected static void ensureNotNullAndExactLength(final String field, final String value, final int length) throws IllegalArgumentException {
        ensureNotNull(field, value);
        if (value.length() != length) {
            throw new IllegalArgumentException(format(ERROR_NOT_EXACT_LENGTH, field, length));
        }
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is defined
     * and not the exact length.
     *
     * @param field  Name of the field
     * @param value  The value of the field
     * @param length The exact length of the field
     * @throws IllegalArgumentException if the value is not of exact length
     */
    protected static void ensureExactLength(final String field, final String value, final int length) throws IllegalArgumentException {
        if ((value != null) && (value.length() != length)) {
            throw new IllegalArgumentException(format(ERROR_NOT_EXACT_LENGTH, field, length));
        }
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is either
     * null or not within the given limits.
     *
     * @param field   Name of the field
     * @param value   The value of the field
     * @param minimum The minimally allowed value for the field
     * @param maximum The maximally allowed value for the field
     * @throws IllegalArgumentException if the value is null not of exact length
     */
    protected <T extends Number>void ensureNotNullAndWithinLimits(final String field, final T value, final T minimum, final T maximum) throws IllegalArgumentException {
        ensureNotNull(field, value);

        if ((value.doubleValue() < minimum.doubleValue()) || (value.doubleValue() > maximum.doubleValue())) {
            throw new IllegalArgumentException(format(ERROR_NOT_WITHIN_LIMITS, field, minimum, maximum));
        }
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is either
     * null or doesn't follow the provided regular expression.
     *
     * @param field   Name of the field
     * @param value   The value of the field
     * @param pattern The Pattern for the Regular Expression
     * @param regex   The Regular Expression
     */
    protected static void ensureNotNullAndFollowRegex(final String field, final String value, final Pattern pattern, final String regex) {
        ensureNotNull(field, value);

        if (!pattern.matcher(value).matches()) {
            throw new IllegalArgumentException(format(ERROR_INVALID_REGEX, field, regex));
        }
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is either
     * null or not verifiable.
     *
     * @param field  Name of the field
     * @param value  The value of the field
     * @throws IllegalArgumentException if the value is either null or not verifiable
     */
    protected static void ensureVerifiable(final String field, final Verifiable value) throws IllegalArgumentException {
        if ((value != null) && !value.validate().isEmpty()) {
            throw new IllegalArgumentException(format(ERROR_NOT_VERIFABLE, field));
        }
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is either
     * null or not verifiable.
     *
     * @param field  Name of the field
     * @param value  The value of the field
     * @throws IllegalArgumentException if the value is either null or not verifiable
     */
    protected static void ensureNotNullAndVerifiable(final String field, final Verifiable value) throws IllegalArgumentException {
        ensureNotNull(field, value);
        ensureVerifiable(field, value);
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given Id is invalid,
     * i.e. if it not null and the format doesn't match the required format.
     *
     * @param field Name of the Id
     * @param value The value for the Id
     * @throws IllegalArgumentException if the Id doesn't follow the correct format
     */
    protected static void ensureValidId(final String field, final String value) throws IllegalArgumentException {
        if ((value != null) && !UUID_PATTERN.matcher(value).matches()) {
            // The error message is deliberately not showing the format of our Id
            // type - no need to grant hackers too much information, since all
            // legal requests should not have a problem obtaining legal Id's
            throw new IllegalArgumentException(format(ERROR_INVALID, field));
        }
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given Id is invalid,
     * i.e. if it is either null or doesn't match the required format.
     *
     * @param field Name of the Id
     * @param value The value for the Id
     * @throws IllegalArgumentException if the Id is invalid
     */
    protected static void ensureNotNullAndValidId(final String field, final String value) throws IllegalArgumentException {
        ensureNotNull(field, value);
        ensureValidId(field, value);
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is not a
     * valid e-mail address, i.e. if it is either null or doesn't match the
     * required format.
     *
     * @param field Name of the field, value is ignored in this method
     * @param value The value to verify
     * @throws IllegalArgumentException if the e-mail address is invalid
     */
    protected static void ensureValidEmail(final String field, final String value) throws IllegalArgumentException {
        if (!IWSConstants.EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(format(ERROR_INVALID_EMAIL, value, field));
        }
    }

    /**
     * Throws an {@code IllegalArgumentException} if the given value is not a
     * valid e-mail address, i.e. if it is either null or doesn't match the
     * required format.
     *
     * @param field Name of the field
     * @param value The value to verify
     * @throws IllegalArgumentException if the e-mail address is invalid
     */
    protected static void ensureNotNullAndValidEmail(final String field, final String value) throws IllegalArgumentException {
        ensureNotNullOrTooLong(field, value, 100);
        ensureValidEmail(field, value);
    }

    protected static void ensureValidRefno(final String refno) {
        if (!REFNO_PATTERN.matcher(refno).matches()) {
            throw new IllegalArgumentException(format(ERROR_INVALID_REFNO, refno));
        }
    }

    // =========================================================================
    // Other Methods
    // =========================================================================

    /**
     * For those cases where an {@code IllegalArgumentException} should be
     * thrown, but with a generic value, this method is used.
     *
     * @param field Name of the field
     * @throws IllegalArgumentException as the field is invalid
     */
    protected static void throwIllegalArgumentException(final String field) throws IllegalArgumentException {
        throw new IllegalArgumentException(format(ERROR_INVALID, field));
    }

    /**
     * The method takes a value, and verifies that this value is not null. If an
     * error is found, then the information is added to the validation
     * Map.<br />
     *   If an error was found, then a false is returned, otherwise the method
     * will return true.
     *
     * @param validation Map with Error information
     * @param field      The name of the field (value) to be verified
     * @param value      The value to verify
     * @return True if field is valid, otherwise false
     */
    protected boolean isNotNull(final Map<String, String> validation, final String field, final Object value) {
        boolean check = true;

        if (value == null) {
            addError(validation, field, "The field may not be null.");
            check = false;
        }

        return check;
    }

    /**
     * The method adds error messages for fields with checks for existing
     * messages.<br />
     *   If the field in validation Map already had an error, then the error
     * messages are concatenated.
     *
     * @param validation   Map with Error information
     * @param field        The name of the field to add error
     * @param errorMessage The error message for the field
     */
    protected void addError(final Map<String, String> validation, final String field, final String errorMessage) {
        final String message;

        if (validation.containsKey(field)) {
            message = format("%s\n%s", validation.get(field), errorMessage);
        } else {
            message = errorMessage;
        }

        validation.put(field, message);
    }

    /**
     * The method add error messages from {@code errors} to validation
     * Map.<br />
     *   If the field in validation Map already had an error, then the error
     * messages are concatenated.
     *
     * @param validation Map with Error information to which errors will be added
     * @param errors     Map with Error information to be added
     * @param field      The field of the first field, as a prefix
     * @see #addError(java.util.Map, String, String)
     */
    protected void addAllErrors(final Map<String, String> validation, final Map<String, String> errors, final String field) {
        for (final Map.Entry<String, String> stringStringEntry : errors.entrySet()) {
            final String fieldName = field + '.' + stringStringEntry.getKey();
            addError(validation, fieldName, stringStringEntry.getValue());
        }
    }

    /**
     * Formats a given String, using the built-in String format method. If there
     * is a problem with formatting the String, then the method will throw an
     * IllegalFormatException. Otherwise, the method will return the result of
     * formatting the String.
     */
    protected static String format(final String message, final Object... args) {
        return String.format(message, args);
    }
}
