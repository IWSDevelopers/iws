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

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public abstract class AbstractVerification implements Verifiable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * {@inheritDoc}
     */
    @Override
    public final void verify() {
        final Map<String, String> validationResult = validate();

        if (!validationResult.isEmpty()) {
            throw new VerificationException("Validation failed: " + validationResult.toString());
        }
    }

    // =========================================================================
    // Internal Verification methods
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
            final Map<String, String> newValidation = value.validate();

            if (!newValidation.isEmpty()) {
                addAllErrors(validation, newValidation, field);
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
    protected String format(final String message, final Object... args) {
        return String.format(message, args);
    }
}
