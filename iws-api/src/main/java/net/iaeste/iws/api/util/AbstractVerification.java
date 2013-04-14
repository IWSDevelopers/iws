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
import net.iaeste.iws.api.exceptions.VerificationException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * This class contains the methods that are used to check if the provided
 * values are correct or not.<br />
 *   There is two kinds of check methods, the "ensure" and the "validate":
 * <ul>
 *   <li><b></b>ensure</b>:<br />
 *     These methods are suppose to be used by the Request & DTO setter methods,
 *     since they make a check and throw an {@code IllegalArgumentException}, if
 *     the given valus is invalid. The checks should be made against all fields
 *     where potential problems may occur. Problems are identified as problems
 *     which will prevent the logic from properly process the request with the
 *     given data or may cause problems as data exceed allowed values for the
 *     database.<br />
 *       Please note, that the setters can set either single field values or
 *     paired values - for example from to date that needs to be checked to
 *     avoid a to date that occurs before an from date.</li>
 *   <li><b>validate</b>:<br />
 *     The validation methods take as first parameter an Error map, and rather
 *     than throwing an Exception, the methods will add the error information
 *     to the map. Thus allowing the response Object to reveal all errors.<br />
 *       The errors that are caught here is only those that will prevent the
 *     business logic from properly process the request, as it is assumed that
 *     all setters are correctly invoking the "ensure" methods. Thus, for most
 *     fields is will suffice to check to ensure that the values are not null.
 *   </li>
 * </ul>
 * The main purpose of these checks, is to ensure that the IWS Objects fails as
 * early as possible, so no unneeded requests are made to the IWS.
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
    private static final String ERROR_INVALID = "The field %s is invalid.";

    // Our internal constants to verify the Id
    private static final String UUID_FORMAT = "[\\da-z]{8}-[\\da-z]{4}-[\\da-z]{4}-[\\da-z]{4}-[\\da-z]{12}";
    private static final Pattern UUID_PATTERN = Pattern.compile(UUID_FORMAT);

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

    // =========================================================================
    // Assertion methods for setters, that throws IllegalArgumentExceptions
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
     * @param value The value for the field
     * @throws IllegalArgumentException if the value is empty
     */
    protected static void ensureNotEmpty(final String field, final String value) throws IllegalArgumentException {
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

    // =========================================================================
    // Internal Validation methods, that assign errors to the given Error Map
    // TODO Rename the following methods to validate, and drop the return part!
    // =========================================================================

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
     * The method takes a value, and verifies that if it is not null, that it
     * is then also not empty. If the value is not null but empty, then the
     * information is added to the validation Map.<br />
     *   If an error was found, then a false is returned, otherwise the method
     * will return true.
     *
     * @param validation Map with Error information
     * @param field      The name of the field (value) to be verified
     * @param value      The value to verify
     * @return True if field is valid, otherwise false
     */
    protected boolean isNotEmpty(final Map<String, String> validation, final String field, final String value) {
        boolean check = true;

        if ((value != null) && value.isEmpty()) {
            addError(validation, field, "the field may not be empty.");
            check = false;
        }

        return check;
    }

    /**
     * The method takes a value, and verifies that if it is not null, that it
     * is then also not empty or exceed the maximum length. If the value is not
     * allowed, then the information is added to the validation Map.<br />
     *   If an error was found, then a false is returned, otherwise the method
     * will return true.
     *
     * @param validation Map with Error information
     * @param field      The name of the field (value) to be verified
     * @param value      The value to verify
     * @param length     The maximum length of the field
     * @return True if field is valid, otherwise false
     */
    protected boolean isNotEmptyOrTooLong(final Map<String, String> validation, final String field, final String value, final int length) {
        boolean check = true;

        if ((value != null) && (value.isEmpty() || (value.length() > length))) {
            addError(validation, field, "The field may neither be empty or exceed the max length of " + length);
            check = false;
        }

        return check;
    }

    /**
     * The method takes a value, and verifies that this value is neither null,
     * nor empty. If an error is found, then the information is added to the
     * validation Map.<br />
     *   If an error was found, then a false is returned, otherwise the method
     * will return true.
     *
     * @param validation Map with Error information
     * @param field      The name of the field (value) to be verified
     * @param value      The value to verify
     * @return True if field is valid, otherwise false
     */
    protected boolean isNotNullOrEmpty(final Map<String, String> validation, final String field, final String value) {
        boolean check = true;

        if ((value == null) || value.isEmpty()) {
            addError(validation, field, "The field may not be null or empty.");
            check = false;
        }

        return check;
    }

    /**
     * The method takes a value, and verifies that this value is neither null,
     * nor empty. If an error is found, then the information is added to the
     * validation Map.<br />
     *   If an error was found, then a false is returned, otherwise the method
     * will return true.
     *
     * @param validation Map with Error information
     * @param field      The name of the field (value) to be verified
     * @param value      The value to verify
     * @return True if field is valid, otherwise false
     */
    protected boolean isNotNullOrEmpty(final Map<String, String> validation, final String field, final Set<?> value) {
        boolean check = true;

        if ((value == null) || value.isEmpty()) {
            addError(validation, field, "The field may not be null or empty.");
            check = false;
        }

        return check;
    }

    /**
     * The method takes a value of type {@code Verifiable}, and verifies that
     * this value is not null, and then invokes the validation on it. If an
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
    protected boolean isVerifiable(final Map<String, String> validation, final String field, final Verifiable value) {
        boolean check = isNotNull(validation, field, value);

        if (check) {
            final Map<String, String> validationResult = value.validate();

            if (!validationResult.isEmpty()) {
                addAllErrors(validation, validationResult, field);
                check = false;
            }
        }

        return check;
    }

    /**
     * The method takes a value of type {@code Verifiable}, and verifies that
     * this value is either null or if not null then invokes the validation on
     * it. If an error is found, then the information is added to the validation
     * Map.<br />
     *   If an error was found, then a false is returned, otherwise the method
     * will return true.
     *
     * @param validation Map with Error information
     * @param field      The name of the field (value) to be verified
     * @param value      The value to verify
     * @return True if field is valid, otherwise false
     */
    protected boolean isNullOrVerifiable(final Map<String, String> validation, final String field, final Verifiable value) {
        boolean check = true;

        if (value != null) {
            final Map<String, String> newValidation = value.validate();

            if (!newValidation.isEmpty()) {
                addAllErrors(validation, newValidation, field);
                check = false;
            }
        }

        return check;
    }

    /**
     * The method checks that the value is neither null nor outside of the
     * required range of values. If an error is found, then the information is
     * added to the validation Map.<br />
     *   If an error was found, then a false is returned, otherwise the method
     * will return true.
     *
     * @param validation Map with Error information
     * @param field      The name of the field (value) to be verified
     * @param value      The value to verify
     * @param minimum    The minimally allowed value
     * @param maximum    The maximally allowed value
     * @return True if field is valid, otherwise false
     */
    protected <T extends Number> boolean isWithinLimits(final Map<String, String> validation, final String field, final T value, final T minimum, final T maximum) {
        boolean check = isNotNull(validation, field, value);

        if (check) {
            // Since the Number is an Abstract type, we need to convert the number
            // to something, which we can then actually check against
            if ((value.doubleValue() < minimum.doubleValue()) || (value.doubleValue() > maximum.doubleValue())) {
                addError(validation, field, format("The value is not within the range %d to %d.", minimum, maximum));
                check = false;
            }
        }

        return check;
    }

    /**
     * The method checks that the value is neither null nor outside of the
     * required range of values. If an error is found, then the information is
     * added to the validation Map.<br />
     * If an error was found, then a false is returned, otherwise the method
     * will return true.
     *
     * @param validation Map with Error information
     * @param field      The name of the field (value) to be verified
     * @param value      The value to verify
     * @param minLength  The minimally allowed length
     * @param maxLength  The maximally allowed length
     * @return True if field is valid, otherwise false
     */
    protected boolean isWithinLimits(final Map<String, String> validation, final String field, final String value, final int minLength, final int maxLength) {
        boolean check = isNotNull(validation, field, value);

        if (check) {
            if (value.length() > maxLength || value.length() < minLength) {
                addError(validation, field, format("The value is not within the range %d to %d.", minLength, maxLength));
                check = false;
            }
        }

        return check;
    }

    /**
     * The method checks that the value is neither null nor has in invalid
     * length. If an error is found, then the information is added to the
     * validation Map.<br />
     *   If an error was found, then a false is returned, otherwise the method
     * will return true.
     *
     * @param validation Map with Error information
     * @param field      The name of the field (value) to be verified
     * @param value      The value to verify
     * @param size       The exact length to allow
     * @return True if field is valid, otherwise false
     */
    protected boolean hasExactSize(final Map<String, String> validation, final String field, final String value, final int size) {
        boolean check = isNotNull(validation, field, value);

        if (check) {
            // Since the Number is an Abstract type, we need to convert the number
            // to something, which we can then actually check against
            if (value.length() != size) {
                addError(validation, field, format("The value is not exactly %d characters long.", size));
                check = false;
            }
        }

        return check;
    }

    /**
     * The method checks that the value is neither null nor too long. If an
     * error is found, then the information is added to the validation
     * Map.<br />
     *   If an error was found, then a false is returned, otherwise the method
     * will return true.
     *
     * @param validation Map with Error information
     * @param field      The name of the field (value) to be verified
     * @param value      The value to verify
     * @param maxSize    The maximally allowed length
     * @return True if field is valid, otherwise false
     */
    protected boolean isWithinLimits(final Map<String, String> validation, final String field, final Collection<?> value, final int maxSize) {
        boolean check = isNotNull(validation, field, value);

        if (check) {
            if (value.size() > maxSize) {
                addError(validation, field, format("the value size is bigger than %d.", maxSize));
                check = false;
            }
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
