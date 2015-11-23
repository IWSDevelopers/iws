/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import com.gargoylesoftware.base.testing.EqualsTester;
import net.iaeste.iws.api.exceptions.VerificationException;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class AuthenticationTokenTest {

    @Test
    public void testClassflow() {
        final String key = "5a15481fe88d39be1c83c2f72796cc8a70e84272640d5c7209ad9aefa642db11ae8fa1945bc308c15c36d591ea1d047692530c95b68fcc309bbe63889dba363e";
        final String groupId = "12qw43er-43wq-65tr-78ui-09qw87er65rt";

        final AuthenticationToken token = new AuthenticationToken();
        token.setToken(key);
        token.setGroupId(groupId);
        token.verify();

        assertThat(token.getToken(), is(key));
        assertThat(token.getGroupId(), is(groupId));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGroupId() {
        final AuthenticationToken token = new AuthenticationToken();
        token.setGroupId("123");
    }

    @Test
    public void testClass() {
        final String mainValue = "5a15481fe88d39be1c83c2f72796cc8a70e84272640d5c7209ad9aefa642db11ae8fa1945bc308c15c36d591ea1d047692530c95b68fcc309bbe63889dba363e";
        final String diffValue = "5a15481fe88d39be1c83c2f72796cc8a70e84272640d5c7209ad9aefa642db11ae8fa1945bc308c15c36d591ea1d047692530c95b68fccxxxxbe63889dba363e";
        final String groupId = "12345678-1324-2341-3244-123443211234";

        final AuthenticationToken result = new AuthenticationToken(mainValue);
        final AuthenticationToken same = new AuthenticationToken();
        final AuthenticationToken diff1 = new AuthenticationToken(diffValue);
        final AuthenticationToken diff2 = new AuthenticationToken(diffValue, groupId);
        same.setToken(mainValue);

        result.verify();
        assertThat(result, is(same));
        assertThat(result, is(not(diff1)));
        assertThat(result.getToken(), is(mainValue));
        assertThat(result.hashCode(), is(-967988839));
        assertThat(diff1.hashCode(), is(-1164423489));
        assertThat(diff2.hashCode(), is(-1854131232));
        assertThat(result.toString(), is("AuthenticationToken{token='" + mainValue + "', groupId='null'}"));
        assertThat(diff1.toString(), is("AuthenticationToken{token='" + diffValue + "', groupId='null'}"));
        assertThat(diff2.toString(), is("AuthenticationToken{token='" + diffValue + "', groupId='" + groupId + "'}"));

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
        final String mainValue = "5a15481fe88d39be1c83c2f72796cc8a70e84272640d5c7209ad9aefa642db11ae8fa1945bc308c15c36d591ea1d047692530c95b68fcc309bbe63889dba363e";
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
