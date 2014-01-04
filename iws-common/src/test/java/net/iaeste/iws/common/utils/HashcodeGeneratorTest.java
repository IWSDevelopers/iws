/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
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
    @Ignore("Ignored 2013-01-04 by Kim - Reason: Trac task #534 states that for a limited period we have to support the old Passwords. However, this requires that the Salting is skipped")
    public void testGenerateMD5() {
        // Preconditions for the test
        final String textWithoutDot = "The quick brown fox jumps over the lazy dog";
        final String textWithDot = "The quick brown fox jumps over the lazy dog.";
        final String textEmpty = "";
        final String textNull = null;
        final String expectedWithoutDot = "0fe8aaf6e2e9078bc52c94d770c18a20";
        final String expectedWithDot = "180268329af4c9519e462b90de2eb8a3";
        final String expectedEmpty = "25fc6113c0f927d7ca6dcfb77abb9ff1";
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
        final String expectedWithoutDot = "108e5dbbef167805880a97e448257268315ed44e73e75051d0210993b331ae85";
        final String expectedWithDot = "a5def6e4a29b92f1e8d630f0a192bcbf445091faecb3bd784301597e5ff6c0f5";
        final String expectedEmpty = "68b441793bce5e76b08585f809043f2697040fa8f52a4cdc2039a10b6ce3d93a";
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
        final String expectedWithoutDot = "10f3e96d6414e2d9a2d02a812d27b56595f38942a35456ac02e5d305360587ee69a26e4bf5d38d6d46046fb4a4dcf962";
        final String expectedWithDot = "06b3b3a7a0abef11260e2291795ba612d9122e286ec7ea4c6a9b18b140bf765014eed0dbcb24362e68a6ad5b87ad9f95";
        final String expectedEmpty = "44ff5a6416d2cabffdb5e04b2f695298cd7013680e026476d0168d6bcc4e9167fccb38c20185873386205cf8acec761d";
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
        final String expectedWithoutDot = "5a15481fe88d39be1c83c2f72796cc8a70e84272640d5c7209ad9aefa642db11ae8fa1945bc308c15c36d591ea1d047692530c95b68fcc309bbe63889dba363e";
        final String expectedWithDot = "c462bcb38867623c2f4ee18b5b1147fbd9263a0ebdf9cf9098b7d30941463c1699434b722268be2d221b80359bb2b2f7e19862f1f99e205ce835e6f38d33d4a3";
        final String expectedEmpty = "74a520c9fc7d1045edac9fd587897f6ff9f9f4896591eac8589f03a50295f7da8bb342e7dc32649bb44f2e556e179b2efb2d2fa46f5f50de63a7d6c241afe606";
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
}
