/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.AbstractRequest
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
import java.util.regex.Pattern;

/**
 * Contains the functionality to properly verify given values.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
abstract class AbstractRequest implements Verifiable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The e-mail compliance regular expression. */
    private static final String EMAIL_REGEX = "^[a-z0-9_\\-]+(\\.[_a-z0-9\\-]+)*@([_a-z0-9\\-]+\\.)+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel|eu|mobi)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * The method takes a value, and verifies that this value is not null. If
     * the given value is null, then a {@code VerificationException} is thrown.
     *
     * @param field    The name of the field (value) to be verified
     * @param value    The value to verify
     * @throws VerificationException if the value is null
     */
    protected <T> void verify(final String field, final T value) throws VerificationException {
        if (value == null) {
            throw new VerificationException(format("The field %s may not be null.", field));
        }
    }

    /**
     * The method takes a value, and verifies that this value is neither null,
     * nor that the value is outside of the provided range of allowed values.
     * If the given value is either null, or outside of the allowed range, then
     * a {@code VerificationException} is thrown.
     *
     * @param field    The name of the field (value) to be verified
     * @param value    The value to verify
     * @param minimum  The Minimal allowed value
     * @param maximum  The Maximal allowed value
     * @throws VerificationException if the value is null or outside given range
     */
    protected <T extends Number> void verify(final String field, final T value, final T minimum, final T maximum) throws VerificationException {
        verify(field, value);

        // Since the Number is an Abstract type, we need to convert the number
        // to something, which we can then actually check against
        if ((value .doubleValue() < minimum.doubleValue()) || (value.doubleValue() > maximum.doubleValue())) {
            throw new VerificationException(format("The field %s must be within the range %d to %d, the given value is %d.", field, minimum, maximum, value));
        }
    }

    /**
     * Checks the given value
     *
     * @param field     The name of the field (value) to be verified
     * @param value     The value to verify
     * @param maxLength The maximum allowed length for the value
     * @throws VerificationException if the value is null or too long
     */
    protected void verify(final String field, final String value, final int maxLength) {
        verify(field, value);

        if (value.length() > maxLength) {
            throw new VerificationException(format("The field %s may noy be longer than %d", field, maxLength));
        }
    }

    /**
     * The method takes a value, and verifies that this value is neither null,
     * nor that the value is outside of the provided range of allowed values.
     * If the given value is either null, or outside of the allowed range, then
     * a {@code VerificationException} is thrown.
     *
     * Verifies if a given e-mail address is valid or not. If the given value,
     * is not compliant with the e-mail format, then a
     * {@code VerificationException} is thrown.
     *
     * @param field    The name of the field (value) to be verified
     * @param value    The value to verify
     * @throws VerificationException if the e-mail addresss isn't compliant
     */
    protected void verifyEmail(final String field, final String value) throws VerificationException {
        if (value == null || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new VerificationException(format("The field %s isn't compliant with the allowed format for e-mail's: %s.", field, EMAIL_REGEX));
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
    private String format(final String message, final Object... args) throws IllegalFormatException {
        return String.format(message, args);
    }
}
