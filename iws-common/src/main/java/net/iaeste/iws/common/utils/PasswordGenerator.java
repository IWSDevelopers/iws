/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.utils.PasswordGenerator
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

import java.security.SecureRandom;
import java.util.Random;

/**
 * Simple Password Generator, which uses two pieces of information to generate
 * a Password, the first is a list of allowed characters (letters, numbers,
 * etc.), the second is the length.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class PasswordGenerator {

    /**
     * Private Constructor, this is a utility class.
     */
    private PasswordGenerator() {
    }

    /**
     * Generates a new random password. This should be used for all new
     * accounts, and accounts where a reset password has been requested.
     *
     * @return New random password
     * @see IWSConstants#PASSWORD_GENERATOR_CHARACTERS
     * @see IWSConstants#DEFAULT_PASSWORD_LENGTH
     */
    public static String generatePassword() {
        final String allowedCharacters = IWSConstants.PASSWORD_GENERATOR_CHARACTERS;
        final int length = IWSConstants.DEFAULT_PASSWORD_LENGTH;

        final char[] password = new char[length];
        final Random random = new SecureRandom();
        int current = 0; // We start at the first position

        // Generate first random number, so we have something to start with
        int lastRandom = random.nextInt(allowedCharacters.length());
        password[0] = allowedCharacters.charAt(lastRandom);

        // Iterate over the characters, until we have a complete password
        while (current < length) {
            final int next = random.nextInt(allowedCharacters.length());
            if (next != lastRandom) {
                password[current] = allowedCharacters.charAt(next);
                lastRandom = next;
                current++;
            }
        }

        return String.valueOf(password);
    }
}
