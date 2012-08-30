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

import net.iaeste.iws.api.constants.IWSConstants;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author  Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class PasswordGeneratorTest {

    /**
     * The Password Generator, is an independent function, that takes known and
     * valid input data and provides a random password with a simple collision
     * check. This is a simple positive test of this functionality.
     */
    @Test
    public void testGeneratePassword() {
        final String password = PasswordGenerator.generatePassword();
        assertThat(password.length(), is(IWSConstants.DEFAULT_PASSWORD_LENGTH));

        final char[] chars = password.toCharArray();
        for (final char c : chars) {
            assertThat(IWSConstants.PASSWORD_GENERATOR_CHARACTERS.contains(Character.toString(c)), is(true));
        }
    }
}
