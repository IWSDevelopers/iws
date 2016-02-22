/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.common.utils;

import net.iaeste.iws.common.configuration.InternalConstants;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Simple Password Generator, which uses two pieces of information to generate
 * a Password, the first is a list of allowed characters (letters, numbers,
 * etc.), the second is the length.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
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
     * @see InternalConstants#ALLOWED_GENERATOR_CHARACTERS
     * @see InternalConstants#GENERATED_PASSWORD_LENGTH
     */
    public static String generatePassword() {
        final String allowedCharacters = InternalConstants.ALLOWED_GENERATOR_CHARACTERS;
        final int length = InternalConstants.GENERATED_PASSWORD_LENGTH;

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
