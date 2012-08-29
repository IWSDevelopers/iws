/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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

import java.security.SecureRandom;
import java.util.Random;

/**
 * Simple Password Generator, which uses two pieces of information to generate
 * a Password, the first is a list of allowed characters (letters, numbers,
 * etc.), the second is the length.
 *
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class PasswordGenerator {

    /**
     * List of all allowed Characters, without those that can be misinterpreted,
     * like i/l, o/0, etc.
     */
    public static final String ALLOWED_CHARS = "abcdefghjkmnpqrstuvwxzy23456789";

    /**
     * Default Password Length.
     */
    public static final int DEFAULT_LENGTH = 8;

    /**
     * Private Constructor, this is a utility class.
     */
    private PasswordGenerator() {
    }

    public static String generatePassword() {
        return generatePassword(DEFAULT_LENGTH, ALLOWED_CHARS);
    }

    /**
     * Generates a simple Password, of a given length, with the characters all
     * taken from the list of allowed characters.
     *
     * @param length            The password length
     * @param allowedCharacters List of allowed Characters
     * @return New password
     */
    public static String generatePassword(final int length, final String allowedCharacters) {
        final String allowedChars = allowedCharacters == null ? ALLOWED_CHARS : allowedCharacters;
        if (length <= 0) {
            throw new IllegalArgumentException("password length must be a possitive number");
        }
        if (allowedChars.length() <= 1) {
            throw new IllegalArgumentException("allowedCharacters length must be greater than 1");
        }
        final char[] password = new char[length];
        final Random random = new SecureRandom();
        int current = 0; // We start at the first position

        // Generate first random number, so we have something to start with
        int lastRandom = random.nextInt(allowedChars.length());
        password[0] = allowedChars.charAt(lastRandom);

        // Iterate over the characters, until we have a complete password
        while (current < length) {
            final int next = random.nextInt(allowedChars.length());
            if (next != lastRandom) {
                password[current] = allowedChars.charAt(next);
                lastRandom = next;
                current++;
            }
        }

        return String.valueOf(password);
    }
}
