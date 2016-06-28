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
        // According to Trac bug report #412, the date format used in IW4, is
        // the same as in IWS. The requested format is on the ISO Format
        // yyyy-MM-dd, but dd-MMM-yyyy. This test just verifies that.
        final String oct14 = "14-OCT-2010";
        final Date date = new Date(oct14);
        assertThat(date.toString(), is(oct14));
    }

    /**
     * ReDoS or Regular expression Denial of Services, is a way to exploit the
     * complexity of a regular expression engine. It is an advise from
     * <a href="https://www.owasp.org/index.php/Regular_expression_Denial_of_Service_-_ReDoS">OWASP</a>.
     * As the e-mail addresses are used as an input parameter, it is added here,
     * to ensure that any such will not cause the IWS to become unstable.
     */
    @Test
    public void testReDoSResilience() {
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("aaaaaaaaaaaaaaaaaaaaaaaa!").matches(), is(false));
    }

    /**
     * <p>This test verifies the e-mail pattern we're using. The Wikipedia page
     * for e-mail addresses contains a list of known valid and invalid addresses
     * which is used as a base for this test.</p>
     *
     * <p><a href="http://en.wikipedia.org/wiki/Email_address">See</a> for more
     * information.</p>
     *
     * <p>Note; the goal with this test is not to verify that the IWS is 100%
     * compliant, but that it supports the most common cases and that none of
     * the noncomplying addresses is falling through.</p>
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
        // (top-level domains are valid hostname's)
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
        // To protect user privacy, the e-mail address is being updated to
        // uuid @ deleted.now.
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("someone@deleted.now").matches(), is(true));
    }

    /**
     * This is the negative test for the e-mail address Pattern being used. The
     * example have all been taken from the Wikipedia page about e-mail
     * <a href="http://en.wikipedia.org/wiki/Email_address">addresses</a>, and
     * is just here to verify that no matter what, our regex may not allow any
     * of these.
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
        // (top-level domains following up a dot requires minimum two alphabetic characters (ISO_3166-1_alpha-2 code), for Brazil e.g. it must be br according to ISO 3166-1).
        assertThat(IWSConstants.EMAIL_PATTERN.matcher("email@brazil.b").matches(), is(false));
    }
}
