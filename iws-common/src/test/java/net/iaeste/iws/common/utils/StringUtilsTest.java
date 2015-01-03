/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.utils.StringUtilsTest
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
