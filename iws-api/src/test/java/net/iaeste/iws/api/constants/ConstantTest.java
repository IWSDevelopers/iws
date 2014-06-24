/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.constants.ConstantTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.constants;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.util.Date;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ConstantTest {

    @Test
    public void testDateFormat() {
        // According to trac bug report #412, the date format used in IW4, is
        // the same as in IWS. The requested format is on the ISO Format
        // yyyy-MM-dd, but dd-MMM-yyyy. This test just verifies that.
        final String oct14 = "14-OCT-2010";
        final Date date = new Date(oct14);
        assertThat(date.toString(), is(oct14));
    }

    /**
     * This test verifies the e-mail pattern we're using. The Wikipedia page
     * for e-mail addresses contains a list of known valid and invalid addresses
     * which is used as a base for this test.
     *   <a href="http://en.wikipedia.org/wiki/Email_address">See</a> for more
     * information.<br />
     *   Note; the goal with this test is not to verify that the IWS is 100%
     * compliant, but that it supports the most common cases and that none of
     * the noncompliant addresses is falling through.
     */
    @Test
    public void testCompliantEmailAddresses() {
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("niceandsimple@example.com").matches(), is(true));
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("very.common@example.com").matches(), is(true));
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("a.little.lengthy.but.fine@dept.example.com").matches(), is(true));
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("disposable.style.email.with+symbol@example.com").matches(), is(true));
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("other.email-with-dash@example.com").matches(), is(true));
        //assertThat(IWSConstants.EMAIL_PATTERN.matcher("user@[IPv6:2001:db8:1ff::a0b:dbd0]").matches(), is(true));
        //assertThat(IWSConstants.EMAIL_PATTERN.matcher("\"much.more unusual\"@example.com").matches(), is(true));
        //assertThat(IWSConstants.EMAIL_PATTERN.matcher("\"very.unusual.@.unusual.com\"@example.com").matches(), is(true));
        //assertThat(IWSConstants.EMAIL_PATTERN.matcher("\"very.(),:;<>[]\".VERY.\"very@\\ \"very\".unusual\"@strange.example.com").matches(), is(true));
        // (top-level domains are valid hostnames)
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("postbox@com").matches(), is(true));
        // (local domain name with no TLD)
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("admin@mailserver1").matches(), is(true));
        assertThat(IWSConstants.EMAIL_PATTERN.matcher(" !#$%&'*+-/=?^_`{}|~@example.org").matches(), is(true));
        //assertThat(IWSConstants.EMAIL_PATTERN.matcher("\"()<>[]:,;@\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org").matches(), is(true));
        // (space between the quotes)
        //assertThat(IWSConstants.EMAIL_PATTERN.matcher("\" \"@example.org").matches(), is(true));
        // (Unicode characters in local part)
        //assertThat(IWSConstants.EMAIL_PATTERN.matcher("üñîçøðé@example.com").matches(), is(true));
        // (Unicode characters in domain part)
        //assertThat(IWSConstants.EMAIL_PATTERN.matcher("üñîçøðé@üñîçøðé.com").matches(), is(true));

        // Explicitly testing the case from Trac ticket #725
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("marko@iaeste.mit").matches(), is(true));
        // Testing e-mail which was rejected April 17th
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("Monika.KUMMEL@ensam.eu").matches(), is(true));
        // Testing e-mail change which was requested by the Board on 2014-05-19
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("Lorna.O'Kane@britishcouncil.org").matches(), is(true));
    }

    /**
     * This is the negative test for the e-mail address Pattern being used. The
     * example have all been taken from the Wikipedia page about e-mail
     * <a href="http://en.wikipedia.org/wiki/Email_address">addresses</a>, and
     * is just here to verify that nomatter what, our regex may not allow any of
     * these.
     */
    @Test
    public void testNonCompliantEmailAddresses() {
        // (an @ character must separate the local and domain parts)
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("Abc.example.com").matches(), is(false));
        // (only one @ is allowed outside quotation marks)
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("A@b@c@example.com").matches(), is(false));
        // (none of the special characters in this local part is allowed outside quotation marks)
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("a\"b(c)d,e:f;g<h>i[j\\k]l@example.com").matches(), is(false));
        // (quoted strings must be dot separated, or the only element making up the local-part)
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("just\"not\"right@example.com").matches(), is(false));
        // (spaces, quotes, and backslashes may only exist when within quoted strings and preceded by a backslash)
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("this is\"not\\allowed@example.com").matches(), is(false));
        // (even if escaped (preceded by a backslash), spaces, quotes, and backslashes must still be contained by quotes)
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("this\\ still\\\"not\\allowed@example.com").matches(), is(false));
        // (top-level domain following up a dot require minimum two alphabetic characters (ISO_3166-1_alpha-2 code), for Brazil e.g. it must be br according to ISO 3166-1).
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("email@brazil.b").matches(), is(false));
    }
}
