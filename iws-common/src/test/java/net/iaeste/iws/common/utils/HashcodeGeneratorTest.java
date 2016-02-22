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
package net.iaeste.iws.common.utils;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.common.configuration.Settings;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class HashcodeGeneratorTest {

    private static final String USER_SALT = "";

    /**
     * Tests the SHA-2 generator 256 bit method in the HashCode Generator
     * library. The raw text, and expected information are taken from the SHA-2
     * <a href="http://en.wikipedia.org/wiki/Sha-2">Wikipedia</a> page.<br />
     *   Note, that due to the addition of salt, the expected result will differ
     * from the result of the Wikipedia page.
     */
    @Test
    public void testGenerateSHA256() {
        final Settings settings = new Settings();
        settings.setPasswordSalt("Salt");
        final HashcodeGenerator generator = new HashcodeGenerator(settings);

        // Preconditions for the test
        final String textWithoutDot = "The quick brown fox jumps over the lazy dog";
        final String textWithDot = "The quick brown fox jumps over the lazy dog.";
        final String textEmpty = "";
        final String textNull = null;
        final String expectedWithoutDot = "4e5d8cf8f8e753b80896c63c2ab24d25b4ceca01a6803ea69723ed57089196b4";
        final String expectedWithDot = "8f0af607d795c4c7573a6f7c6c7874f85f1932d91a883fbe548ddba949158ad4";
        final String expectedEmpty = "150fe5514030b1434a5deaf491ece92c0e6497447d6de7bd6ba85e8cae1e00a3";
        final String expectedNull = null;

        // Perform the testing
        final String resultWithoutDot = generator.generateSHA256(textWithoutDot, USER_SALT);
        final String resultWithDot = generator.generateSHA256(textWithDot, USER_SALT);
        final String resultEmpty = generator.generateSHA256(textEmpty, USER_SALT);
        final String resultNull = generator.generateSHA256(textNull, USER_SALT);

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
        final Settings settings = new Settings();
        settings.setPasswordSalt("salt");
        final HashcodeGenerator generator = new HashcodeGenerator(settings);

        // Preconditions for the test
        final String textWithoutDot = "The quick brown fox jumps over the lazy dog";
        final String textWithDot = "The quick brown fox jumps over the lazy dog.";
        final String textEmpty = "";
        final String textNull = null;
        final String expectedWithoutDot = "2cb059f8cc93e2c69e3c5a871392bc563422ea70de5f16896b2278c91108dc9413af716667614fc0fa546f75f487d66f";
        final String expectedWithDot = "47ab638c781df65e3fab8f256dda21d651b1caccd71426960da97590935bc683b4ed8fab6a6c1f6ea9cdc202a18a7c6d";
        final String expectedEmpty = "1b142cd342d1922d01e14c612493a8f8046502f204e71bf7c152011000cb4cf5567a34484cb3a4235856b3a16c646a76";
        final String expectedNull = null;

        // Perform the testing
        final String resultWithoutDot = generator.generateSHA384(textWithoutDot, USER_SALT);
        final String resultWithDot = generator.generateSHA384(textWithDot, USER_SALT);
        final String resultEmpty = generator.generateSHA384(textEmpty, USER_SALT);
        final String resultNull = generator.generateSHA384(textNull, USER_SALT);

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
        final Settings settings = new Settings();
        settings.setPasswordSalt("salt");
        final HashcodeGenerator generator = new HashcodeGenerator(settings);

        // Preconditions for the test
        final String textWithoutDot = "The quick brown fox jumps over the lazy dog";
        final String textWithDot = "The quick brown fox jumps over the lazy dog.";
        final String textEmpty = "";
        final String textNull = null;
        final String expectedWithoutDot = "a055ac964024767c44b8bbd31638540826f2df8ae54191d313cc0befd4b6d1e0a914e5773bf3c0c59e64f77c10731cf8e8250b95e725598d217a5f410e0b71b0";
        final String expectedWithDot = "d04aed7299824dbc4536516dba3b07241c3eac721ca1c10740b43a25dcc3b7ce89500abd56330c9376d6bca87f465fa866520cc23174d83f363a9bb71c5b3cc9";
        final String expectedEmpty = "2e3fce77cf8c4c7478a96d207c1c39715892cac84a18cbec9b634f4bc22b390b48cd30a4df2e7ebbaee65c346a662c5be2d12441322f7a4bac821a382c4af091";
        final String expectedNull = null;

        // Perform the testing
        final String resultWithoutDot = generator.generateSHA512(textWithoutDot, USER_SALT);
        final String resultWithDot = generator.generateSHA512(textWithDot, USER_SALT);
        final String resultEmpty = generator.generateSHA512(textEmpty, USER_SALT);
        final String resultNull = generator.generateSHA512(textNull, USER_SALT);

        // Assertion checks against the responses with our predefined expectations
        assertThat(resultWithoutDot, is(expectedWithoutDot));
        assertThat(resultWithDot, is(expectedWithDot));
        assertThat(resultEmpty, is(expectedEmpty));
        assertThat(resultNull, is(expectedNull));
    }
}
