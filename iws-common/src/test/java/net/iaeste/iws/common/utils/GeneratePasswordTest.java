/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.utils.GeneratePasswordTest
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.util.UUID;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class GeneratePasswordTest {

    private static final String info = "-- Updating Password for user '%s' to '%s'.";
    private static final String sql = "update users set password = '%s', salt = '%s', modified = now(), status = 'ACTIVE' where username = '%s';";
    private static final String[] usernames = { };

    @Test
    public void testGeneratePassword() {
        for (final String username : usernames) {
            final String salt = UUID.randomUUID().toString();
            final String password = PasswordGenerator.generatePassword();
            final String generateHashedPassword = HashcodeGenerator.generateHash(password, salt);
            System.out.println(String.format(info, username, password));
            System.out.println(String.format(sql, generateHashedPassword, salt, username));
        }
        assertThat(true, is(Boolean.TRUE));
    }
}
