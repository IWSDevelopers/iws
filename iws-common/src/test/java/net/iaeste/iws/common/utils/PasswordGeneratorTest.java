/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.utils.PasswordGeneratorTest
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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class PasswordGeneratorTest {
    @Test(timeout = 1000)
    public void testGeneratePassword() {
        final String password = PasswordGenerator.generatePassword();
        assertThat(password.length(), is(PasswordGenerator.DEFAULT_LENGTH));
        final char[] chars = password.toCharArray();
        for (final char c : chars) {
            assertThat(PasswordGenerator.ALLOWED_CHARS.contains(Character.toString(c)), is(true));
        }
    }

    @Test(timeout = 1000)
    public void testGeneratePasswordNullArgument() {
        final String password = PasswordGenerator.generatePassword(PasswordGenerator.DEFAULT_LENGTH, null);
        final char[] chars = password.toCharArray();
        for (final char c : chars) {
            assertThat(PasswordGenerator.ALLOWED_CHARS.contains(Character.toString(c)), is(true));
        }
    }

    @Test(expected = IllegalArgumentException.class, timeout = 1000)
    public void testGeneratePasswordEmptyCharSet() {
        PasswordGenerator.generatePassword(10, "");
    }

    @Test(expected = IllegalArgumentException.class, timeout = 1000)
    public void testGeneratePasswordOneElementCharSet() {
        PasswordGenerator.generatePassword(PasswordGenerator.DEFAULT_LENGTH, "a");
    }

    @Test(expected = IllegalArgumentException.class, timeout = 1000)
    public void testGeneratePasswordNonpositiveLength() {
        PasswordGenerator.generatePassword(0, PasswordGenerator.ALLOWED_CHARS);
    }


}
