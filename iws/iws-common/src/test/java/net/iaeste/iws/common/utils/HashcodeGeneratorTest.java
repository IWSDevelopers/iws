/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class HashcodeGeneratorTest {

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
        final String expectedWithoutDot = "9e107d9d372bb6826bd81d3542a419d6";
        final String expectedWithDot = "e4d909c290d0fb1ca068ffaddf22cbd0";
        final String expectedEmpty = "d41d8cd98f00b204e9800998ecf8427e";
        final String expectedNull = null;

        // Perform the testing
        final String resultWithoutDot = HashcodeGenerator.generateMD5(textWithoutDot);
        final String resultWithDot = HashcodeGenerator.generateMD5(textWithDot);
        final String resultEmpty = HashcodeGenerator.generateMD5(textEmpty);
        final String resultNull = HashcodeGenerator.generateMD5(textNull);

        // Assertion checks against the responses with our predefined expectations
        assertThat(resultWithoutDot, is(expectedWithoutDot));
        assertThat(resultWithDot, is(expectedWithDot));
        assertThat(resultEmpty, is(expectedEmpty));
        assertThat(resultNull, is(expectedNull));
    }

    /**
     * Tests the SHA-2 generator 256 bit method in the HashCode Generator
     * library. The raw text, and expected information are taken from the SHA-2
     * Wikipedia page.
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
        final String expectedWithoutDot = "d7a8fbb307d7809469ca9abcb0082e4f8d5651e46d3cdb762d02d0bf37c9e592";
        final String expectedWithDot = "ef537f25c895bfa782526529a9b63d97aa631564d5d789c2b765448c8635fb6c";
        final String expectedEmpty = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        final String expectedNull = null;

        // Perform the testing
        final String resultWithoutDot = HashcodeGenerator.generateSHA256(textWithoutDot);
        final String resultWithDot = HashcodeGenerator.generateSHA256(textWithDot);
        final String resultEmpty = HashcodeGenerator.generateSHA256(textEmpty);
        final String resultNull = HashcodeGenerator.generateSHA256(textNull);

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
        final String expectedWithoutDot = "ca737f1014a48f4c0b6dd43cb177b0afd9e5169367544c494011e3317dbf9a509cb1e5dc1e85a941bbee3d7f2afbc9b1";
        final String expectedWithDot = "ed892481d8272ca6df370bf706e4d7bc1b5739fa2177aae6c50e946678718fc67a7af2819a021c2fc34e91bdb63409d7";
        final String expectedEmpty = "38b060a751ac96384cd9327eb1b1e36a21fdb71114be07434c0cc7bf63f6e1da274edebfe76f65fbd51ad2f14898b95b";
        final String expectedNull = null;

        // Perform the testing
        final String resultWithoutDot = HashcodeGenerator.generateSHA384(textWithoutDot);
        final String resultWithDot = HashcodeGenerator.generateSHA384(textWithDot);
        final String resultEmpty = HashcodeGenerator.generateSHA384(textEmpty);
        final String resultNull = HashcodeGenerator.generateSHA384(textNull);

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
        final String expectedWithoutDot = "07e547d9586f6a73f73fbac0435ed76951218fb7d0c8d788a309d785436bbb642e93a252a954f23912547d1e8a3b5ed6e1bfd7097821233fa0538f3db854fee6";
        final String expectedWithDot = "91ea1245f20d46ae9a037a989f54f1f790f0a47607eeb8a14d12890cea77a1bbc6c7ed9cf205e67b7f2b8fd4c7dfd3a7a8617e45f3c463d481c7e586c39ac1ed";
        final String expectedEmpty = "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e";
        final String expectedNull = null;

        // Perform the testing
        final String resultWithoutDot = HashcodeGenerator.generateSHA512(textWithoutDot);
        final String resultWithDot = HashcodeGenerator.generateSHA512(textWithDot);
        final String resultEmpty = HashcodeGenerator.generateSHA512(textEmpty);
        final String resultNull = HashcodeGenerator.generateSHA512(textNull);

        // Assertion checks against the responses with our predefined expectations
        assertThat(resultWithoutDot, is(expectedWithoutDot));
        assertThat(resultWithDot, is(expectedWithDot));
        assertThat(resultEmpty, is(expectedEmpty));
        assertThat(resultNull, is(expectedNull));
    }
}
