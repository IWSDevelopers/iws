/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.CollectionTransformerTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.transformers;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.enums.Language;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class CollectionTransformerTest {

    @Test
    public void testConcatEnumCollection() {
        // Collection is unordered
        final Language[] languages = { Language.FRENCH, Language.ENGLISH };
        final String expectedString1 = String.format("%s%s%s", languages[0], CollectionTransformer.DELIMITER, languages[1]);
        final String expectedString2 = String.format("%s%s%s", languages[1], CollectionTransformer.DELIMITER, languages[0]);

        final List<Language> list = Arrays.asList(languages);
        final Set<Language> set = EnumSet.copyOf(list);

        final String resultFromList = CollectionTransformer.concatEnumCollection(list);
        final String resultFromSet = CollectionTransformer.concatEnumCollection(set);

        assertThat(resultFromList.equals(expectedString1) || resultFromList.equals(expectedString2), is(true));
        assertThat(resultFromSet.equals(expectedString1) || resultFromSet.equals(expectedString2), is(true));
    }

    @Test
    public void testExplodeEnumListAndSet() {
        final List<Language> expectedList = new ArrayList<>(2);
        expectedList.add(Language.ENGLISH);
        expectedList.add(Language.FRENCH);
        final Set<Language> expectedSet = EnumSet.copyOf(expectedList);

        final String stringArgument = String.format("%s%s%s", Language.ENGLISH.name(), CollectionTransformer.DELIMITER, Language.FRENCH.name());

        final List<Language> resultList = CollectionTransformer.explodeEnumList(Language.class, stringArgument);
        final Set<Language> resultSet = CollectionTransformer.explodeEnumSet(Language.class, stringArgument);

        assertThat(resultList, is(expectedList));
        assertThat(resultSet, is(expectedSet));
    }

    @Test
    public void testJoin() {
        final String[] elementsToJoin = { "str1", "str2" };
        // Collection is unordered
        final String expectedString1 = String.format("%s%s%s", elementsToJoin[0], CollectionTransformer.DELIMITER, elementsToJoin[1]);
        final String expectedString2 = String.format("%s%s%s", elementsToJoin[1], CollectionTransformer.DELIMITER, elementsToJoin[0]);
        final List<String> stringList = Arrays.asList(elementsToJoin);

        final String result = CollectionTransformer.join(stringList);

        assertThat(result.equals(expectedString1) || result.equals(expectedString2), is(true));
    }

    @Test
    public void testExplodeStringListAndSet() {
        final String[] strings = { "str1", "str2" };
        final String stringToExplode = String.format("%s%s%s", strings[0], CollectionTransformer.DELIMITER, strings[1]);
        final List<String> expectedList = Arrays.asList(strings);
        final Set<String> expectedSet = new HashSet<>(expectedList);

        final List<String> resultList = CollectionTransformer.explodeStringList(stringToExplode);
        final Set<String> resultSet = CollectionTransformer.explodeStringSet(stringToExplode);

        assertThat(resultList, is(expectedList));
        assertThat(resultSet, is(expectedSet));
    }

    @Test
    public void testExplodeOneItemStringTicket873() {
        final String[] strings = { "str1" };
        final String stringToExplode = strings[0];
        final List<String> expectedList = Arrays.asList(strings);
        final Set<String> expectedSet = new HashSet<>(expectedList);

        final List<String> resultList = CollectionTransformer.explodeStringList(stringToExplode);
        final Set<String> resultSet = CollectionTransformer.explodeStringSet(stringToExplode);

        assertThat(resultList, is(expectedList));
        assertThat(resultSet, is(expectedSet));
    }

}
