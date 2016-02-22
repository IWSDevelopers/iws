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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class StringUtilsTest {

    @Test
    public void testConvertToAsciiMailAlias() {
        String testString = "Dønałd Dück";
        String expected = "doenald_dueck";
        assertThat(StringUtils.convertToAsciiMailAlias(testString), is(expected));

        testString = "D`onald Duck";
        expected = "d_onald_duck";
        assertThat(StringUtils.convertToAsciiMailAlias(testString), is(expected));

        testString = "D'onald Duck";
        expected = "d'onald_duck";
        assertThat(StringUtils.convertToAsciiMailAlias(testString), is(expected));

        testString = "test = ÀÁÂÃĀĂĄàáâãāăą" + "Åå" + "ÄäÆæ" + "ÇĆĈĊČçćĉċč" + "ÐĎĐďđ" + "ÈÉÊËĒĔĖĘĚèéêëēĕėęě"
                     + "ĜĞĠĢĝğġģ" + "ĤĦĥħ" + "ÌÍÎÏĨĪĬĮİìíîïĩīĭįı" + "Ĳĳ" + "Ĵĵ" + "Ķķĸ" + "ĹĻĽĿŁĺļľŀł" + "ÑŃŅŇŊñńņňŉŋ"
                     + "ÒÓÔÕŌŎŐðòóôõōŏő" + "ŒÖØœöø" + "ŔŖŘŕŗř" + "ŚŜŞŠśŝşšſ" + "ß" + "ŢŤŦţťŧ" + "Þþ"
                     + "ÙÚÛŨŪŬŮŰŲùúûũūŭůűų" + "Üü" + "Ŵŵ" + "ÝŶŸýÿŷ" + "ŹŻŽŽźżž" + "×÷";
        expected =   "test___aaaaaaaaaaaaaa" + "aaaa" + "aeaeaeae" + "cccccccccc" + "ddddd" + "eeeeeeeeeeeeeeeeee"
                     + "gggggggg" + "hhhh" + "iiiiiiiiiiiiiiiiii" + "ijij" + "jj" + "kkk" + "llllllllll" + "nnnnnnnnnnn"
                     + "ooooooooooooooo" + "oeoeoeoeoeoe" + "rrrrrr" + "sssssssssss" + "tttttt" + "thth"
                     + "uuuuuuuuuuuuuuuuuu" + "ueue" + "ww" + "yyyyyy" + "zzzzzzz";
        assertThat(StringUtils.convertToAsciiMailAlias(testString), is(expected));

        testString = "Dønałd Dück@iaeste.net";
        expected = "doenald_dueck@iaeste.net";
        assertThat(StringUtils.convertToAsciiMailAlias(testString), is(expected));
    }
}
