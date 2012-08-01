/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.data.AuthenticationTokenTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.data;

import com.gargoylesoftware.base.testing.EqualsTester;
import net.iaeste.iws.api.exceptions.VerificationException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection SuppressionAnnotation
 */
public final class AuthenticationTokenTest {

    @Test
    public void testClass() {
        final String mainValue = "1234567890ABCDEF1234567890ABCDEF";
        final String diffValue = "ABCDEF1234567890ABCDEF1234567890";

        final AuthenticationToken result = new AuthenticationToken(mainValue);
        final AuthenticationToken same = new AuthenticationToken();
        final AuthenticationToken diff = new AuthenticationToken(diffValue);
        same.setToken(mainValue);

        result.verify();
        assertThat(result, is(same));
        assertThat(result, is(not(diff)));
        assertThat(result.getToken(), is(mainValue));
        assertThat(result.hashCode(), is(1018334091));
        assertThat(diff.hashCode(), is(-2074407797));
        assertThat(result.toString(), is("AuthenticationToken[token=" + mainValue + ']'));
        assertThat(diff.toString(), is("AuthenticationToken[token=" + diffValue + ']'));

        //noinspection ResultOfObjectAllocationIgnored
        new EqualsTester(result, same, diff, null);
    }

    @Test
    public void testCopyConstructor() {
        final String mainValue = "1234567890ABCDEF1234567890ABCDEF";
        final AuthenticationToken original = new AuthenticationToken(mainValue);
        final AuthenticationToken result = new AuthenticationToken(original);
        //noinspection CastToConcreteClass
        final AuthenticationToken nullResult = new AuthenticationToken((AuthenticationToken) null);

        assertThat(result, is(original));
        assertThat(result, is(not(nullResult)));
    }

    @Test(expected = VerificationException.class)
    public void testEmptytoken() {
        final String key = "";
        final AuthenticationToken token = new AuthenticationToken(key);
        token.verify();
    }

    @Test(expected = VerificationException.class)
    public void testIncorrectToken() {
        final String key = "invalid";
        final AuthenticationToken token = new AuthenticationToken(key);
        token.verify();
    }

    @Test(expected = VerificationException.class)
    public void testNullToken() {
        final String key = null;
        final AuthenticationToken token = new AuthenticationToken(key);
        token.verify();
    }
}
