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
