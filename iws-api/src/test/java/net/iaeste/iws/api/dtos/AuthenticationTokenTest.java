/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.AuthenticationTokenTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import com.gargoylesoftware.base.testing.EqualsTester;
import net.iaeste.iws.api.exceptions.VerificationException;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CastToConcreteClass, ResultOfObjectAllocationIgnored
 */
public final class AuthenticationTokenTest {

    @Test
    public void testClassflow() {
        final String key = "1234567890ABCDEF1234567890ABCDEF";
        final String groupId = "123";

        final AuthenticationToken token = new AuthenticationToken();
        token.setToken(key);
        token.setGroupId(groupId);
        token.verify();

        assertThat(token.getToken(), is(key));
        assertThat(token.getGroupId(), is(groupId));
    }

    @Test
    public void testClass() {
        final String mainValue = "1234567890ABCDEF1234567890ABCDEF";
        final String diffValue = "ABCDEF1234567890ABCDEF1234567890";

        final AuthenticationToken result = new AuthenticationToken(mainValue);
        final AuthenticationToken same = new AuthenticationToken();
        final AuthenticationToken diff1 = new AuthenticationToken(diffValue);
        final AuthenticationToken diff2 = new AuthenticationToken(diffValue, "groupId");
        same.setToken(mainValue);

        result.verify();
        assertThat(result, is(same));
        assertThat(result, is(not(diff1)));
        assertThat(result.getToken(), is(mainValue));
        assertThat(result.hashCode(), is(1503585749));
        assertThat(diff1.hashCode(), is(117867733));
        assertThat(diff2.hashCode(), is(411295951));
        assertThat(result.toString(), is("AuthenticationToken{token='" + mainValue + "', groupId='null'}"));
        assertThat(diff1.toString(), is("AuthenticationToken{token='" + diffValue + "', groupId='null'}"));
        assertThat(diff2.toString(), is("AuthenticationToken{token='" + diffValue + "', groupId='groupId'}"));

        new EqualsTester(result, same, diff1, null);
        new EqualsTester(result, same, diff2, null);
    }

    @Test(expected = VerificationException.class)
    public void testEmptyConstructor() {
        final AuthenticationToken token = new AuthenticationToken();
        token.verify();
    }

    @Test
    public void testCopyConstructor() {
        final String mainValue = "1234567890ABCDEF1234567890ABCDEF";
        final AuthenticationToken original = new AuthenticationToken(mainValue);
        final AuthenticationToken result = new AuthenticationToken(original);

        assertThat(result, is(original));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyConstructorNull() {
        new AuthenticationToken((AuthenticationToken) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptytoken() {
        final String key = "";
        new AuthenticationToken(key);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectToken() {
        final String key = "invalid";
        new AuthenticationToken(key);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullToken() {
        final String key = null;
        new AuthenticationToken(key);
    }
}
