/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.utils.HashcodeGeneratorTest
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class HashcodeGeneratorTest {

    private static final String USER_SALT = "";

    /**
     * Tests the MD5 generator method in the HashCode Generator library. The
     * raw text, and expected information are taken from the MD5 Wikipedia
     * page.
     *
     * @see <a href="http://en.wikipedia.org/wiki/MD5">MD5</a>
     */
    @Test
    public void testGenerateMD5() {
        // Preconditions for the test
        final String textWithoutDot = "The quick brown fox jumps over the lazy dog";
        final String textWithDot = "The quick brown fox jumps over the lazy dog.";
        final String textEmpty = "";
        final String textNull = null;
        final String expectedWithoutDot = "5db5bb9b3580b9f48f92edeef5e2d2db";
        final String expectedWithDot = "bfea007164e4532e89402307c26f7304";
        final String expectedEmpty = "e72842c87d534d45937e7b0ae0f9df11";
        final String expectedNull = null;

        // Perform the testing
        final String resultWithoutDot = HashcodeGenerator.generateMD5(textWithoutDot, USER_SALT);
        final String resultWithDot = HashcodeGenerator.generateMD5(textWithDot, USER_SALT);
        final String resultEmpty = HashcodeGenerator.generateMD5(textEmpty, USER_SALT);
        final String resultNull = HashcodeGenerator.generateMD5(textNull, USER_SALT);

        // Assertion checks against the responses with our predefined expectations
        assertThat(resultWithoutDot, is(expectedWithoutDot));
        assertThat(resultWithDot, is(expectedWithDot));
        assertThat(resultEmpty, is(expectedEmpty));
        assertThat(resultNull, is(expectedNull));
    }

    /**
     * Tests the SHA-2 generator 256 bit method in the HashCode Generator
     * library. The raw text, and expected information are taken from the SHA-2
     * Wikipedia page.<br />
     *   Note, that due to the addition of salt, the expected result will differ
     * from the result of the wikipedia page.
     *
     * @see <a href="http://en.wikipedia.org/wiki/Sha-2">SHA-2</a>
     */
    @Test
    public void testGenerateSHA256() {
        // Preconditions for the test
        final String textWithoutDot = "The quick brown fox jumps over the lazy dog";
        final String textWithDot = "The quick brown fox jumps over the lazy dog.";
        final String textEmpty = "";
        final String textNull = null;
        final String expectedWithoutDot = "4d1c414c460ed037f175ca887b1bc49c60e6b9b9b26196b2e481b28e532be5a2";
        final String expectedWithDot = "16e05355a6e334fc4598ec349f8eeb1e975e0786eca7cf9780e713f5c8ff709b";
        final String expectedEmpty = "d43dafe0ff3f8a1f81d2b39b6a13e63414b28882872a9b818d37c7d93a4497b3";
        final String expectedNull = null;

        // Perform the testing
        final String resultWithoutDot = HashcodeGenerator.generateSHA256(textWithoutDot, USER_SALT);
        final String resultWithDot = HashcodeGenerator.generateSHA256(textWithDot, USER_SALT);
        final String resultEmpty = HashcodeGenerator.generateSHA256(textEmpty, USER_SALT);
        final String resultNull = HashcodeGenerator.generateSHA256(textNull, USER_SALT);

        // Assertion checks against the responses with our predefined expectations
        assertThat(resultWithoutDot, is(expectedWithoutDot));
        assertThat(resultWithDot, is(expectedWithDot));
        assertThat(resultEmpty, is(expectedEmpty));
        assertThat(resultNull, is(expectedNull));
    }

    /**
     * Tests the SHA-2 generator 384 bit method in the HashCode Generator
     * library. The raw text, and expected information are taken from the SHA-2
     * Wikipedia page.
     *
     * @see <a href="http://en.wikipedia.org/wiki/Sha-2">SHA-2</a>
     */
    @Test
    public void testGenerateSHA384() {
        // Preconditions for the test
        final String textWithoutDot = "The quick brown fox jumps over the lazy dog";
        final String textWithDot = "The quick brown fox jumps over the lazy dog.";
        final String textEmpty = "";
        final String textNull = null;
        final String expectedWithoutDot = "31d428bca45e9c0c10e7f60b25fa2f73f007c3ae5ada9404e20394861c80a984c316d45667f4a585397508213b2898ec";
        final String expectedWithDot = "210accc9d0dfcd84d8bb9146e6bb3ce6a76444adcfc2c4147fdf2dbc7b5050eeed72e3d6c84bc417bf0564e9c85cc452";
        final String expectedEmpty = "267bec1477cabf4c700165e7d801472e13dbccabe136c6628252d92259e8f2eed7061d275a3c414ff73d1a64800cdc89";
        final String expectedNull = null;

        // Perform the testing
        final String resultWithoutDot = HashcodeGenerator.generateSHA384(textWithoutDot, USER_SALT);
        final String resultWithDot = HashcodeGenerator.generateSHA384(textWithDot, USER_SALT);
        final String resultEmpty = HashcodeGenerator.generateSHA384(textEmpty, USER_SALT);
        final String resultNull = HashcodeGenerator.generateSHA384(textNull, USER_SALT);

        // Assertion checks against the responses with our predefined expectations
        assertThat(resultWithoutDot, is(expectedWithoutDot));
        assertThat(resultWithDot, is(expectedWithDot));
        assertThat(resultEmpty, is(expectedEmpty));
        assertThat(resultNull, is(expectedNull));
    }

    /**
     * Tests the SHA-2 generator 512 bit method in the HashCode Generator
     * library. The raw text, and expected information are taken from the SHA-2
     * Wikipedia page.
     *
     * @see <a href="http://en.wikipedia.org/wiki/Sha-2">SHA-2</a>
     */
    @Test
    public void testGenerateSHA512() {
        // Preconditions for the test
        final String textWithoutDot = "The quick brown fox jumps over the lazy dog";
        final String textWithDot = "The quick brown fox jumps over the lazy dog.";
        final String textEmpty = "";
        final String textNull = null;
        final String expectedWithoutDot = "51dac5d4ac42d315e16421c34f6e95213e0ad43a732e04d6cc7bd2b29a4049c68e7e4b2574385db735c985bbdea47ebfcfb51e82a20b395d11ef10411f036b62";
        final String expectedWithDot = "cb2e94f53ec8a45683cef5d18248a83de019ae3cfd7d6f9ff2638ca46eb175a02323f2d16050b3b68fbe76374ed6e8fee24feb62b9ef81878ff1d5d8336a3e52";
        final String expectedEmpty = "3723e235ab2e8113200a3d9b0ad800f2a3cf49b25004b03fa2f1820d6640c04af10bd9e1d73fb3bfce53e883e22431a188258518e98a0f4dcc421382f8f02e4e";
        final String expectedNull = null;

        // Perform the testing
        final String resultWithoutDot = HashcodeGenerator.generateSHA512(textWithoutDot, USER_SALT);
        final String resultWithDot = HashcodeGenerator.generateSHA512(textWithDot, USER_SALT);
        final String resultEmpty = HashcodeGenerator.generateSHA512(textEmpty, USER_SALT);
        final String resultNull = HashcodeGenerator.generateSHA512(textNull, USER_SALT);

        // Assertion checks against the responses with our predefined expectations
        assertThat(resultWithoutDot, is(expectedWithoutDot));
        assertThat(resultWithDot, is(expectedWithDot));
        assertThat(resultEmpty, is(expectedEmpty));
        assertThat(resultNull, is(expectedNull));
    }

//Following is not really test code, but used to generage User accounts.
//    private static final String USER_INSERT = "insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('";
//    private static final String USER_FORMAT = USER_INSERT + "%s', 'ACTIVE', '%s', '%s', '%s', '%s', '%s', '%s');";
//
//    @Test
//    public void testPasswords() {
//        final String country = "Austria";
//
//        for (int i = 1; i <= 5; i++) {
//            final String euid = UUID.randomUUID().toString();
//            final String salt = UUID.randomUUID().toString();
//            final String username = country.toLowerCase(IWSConstants.DEFAULT_LOCALE) + i;
//            final String password = HashcodeGenerator.generateSHA256(username, salt);
//            final String firstname = "User" + i;
//            final String alias = firstname + '.' + country + '@' + IWSConstants.PUBLIC_EMAIL_ADDRESS;
//            final String query = String.format(USER_FORMAT, euid, username, alias, password, salt, firstname, country);
//
//            System.out.println(query);
//        }
//    }
//
//    @Test
//    public void generateStandardTestAccounts() {
//        generatePasswords("Austria", "Croatia", "Denmark", "Germany", "Poland", "Hungary");
//    }
//
//    private static void generatePasswords(final String... countries) {
//        for (final String country : countries) {
//            final String euid = UUID.randomUUID().toString();
//            final String salt = UUID.randomUUID().toString();
//            final String username = country.toLowerCase(IWSConstants.DEFAULT_LOCALE);
//            final String alias = username + ".ns@" + IWSConstants.PUBLIC_EMAIL_ADDRESS;
//            final String password = HashcodeGenerator.generateSHA256(username, salt);
//            final String query = String.format(USER_FORMAT, euid, username, alias, password, salt, "NS", country);
//
//            System.out.println(query);
//        }
//    }
}
