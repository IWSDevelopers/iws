/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.AbstractVerification
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.exceptions.VerificationException;

import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 * @noinspection VariableNotUsedInsideIf
 */
public abstract class AbstractVerification implements Verifiable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The e-mail compliance regular expression. */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(IWSConstants.EMAIL_REGEX);

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
            validation.put(field, "The field may not be null.");
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
    protected boolean isNotEmpty(final Map<String, String> validation, final String field, final String value) {
        boolean check = true;

        if ((value == null) || value.isEmpty()) {
            validation.put(field, "The field may not be null or empty.");
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
    protected boolean isNotVerifiable(final Map<String, String> validation, final String field, final Verifiable value) {
        boolean check = isNotNull(validation, field, value);

        if (check) {
            final Map<String, String> newValidation = value.validate();

            if (!newValidation.isEmpty()) {
                validation.putAll(newValidation);
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
                validation.put(field, format("The value is not within the range %d to %d.", minimum, maximum));
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
     * @param maxLength  The maximally allowed length
     * @return True if field is valid, otherwise false
     */
    protected boolean isWithinLimits(final Map<String, String> validation, final String field, final String value, final int maxLength) {
        boolean check = isNotNull(validation, field, value);

        if (check) {
            if (value.length() > maxLength) {
                validation.put(field, format("the value is longer than %d.", maxLength));
                check = false;
            }
        }

        return check;
    }

    /**
     * The method checks that the value is neither null, nor incompliant with
     * the general form for e-mail addresses. If an error is found, then the
     * information is added to the validation Map.<br />
     *   If an error was found, then a false is returned, otherwise the method
     * will return true.
     *
     * @param validation Map with Error information
     * @param field      The name of the field (value) to be verified
     * @param value      The value to verify
     * @return True if field is valid, otherwise false
     */
    protected boolean isValidMail(final Map<String, String> validation, final String field, final String value) {
        boolean check = isNotNull(validation, field, value);

        if (check) {
            if (!EMAIL_PATTERN.matcher(value).matches()) {
                validation.put(field, format("The e-mail address is not compliant with the allowed format %s", IWSConstants.EMAIL_REGEX));
                check = false;
            }
        }

        return check;
    }

    /**
     * Formats a given String, using the built-in String format method. If there
     * is a problem with formatting the String, then the method will throw an
     * IllegalFormatException. Otherwise, the method will return the result of
     * formatting the String.
     */
    protected String format(final String message, final Object... args) throws IllegalFormatException {
        return String.format(message, args);
    }
}
