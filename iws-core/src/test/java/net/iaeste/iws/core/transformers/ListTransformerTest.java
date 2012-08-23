/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.OfferTransformerTest
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

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import net.iaeste.iws.api.enums.Language;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class ListTransformerTest {

    @Test
    public void testConcatEnumList() {
        final String delimiter = ListTransformer.delimiter;
        List<Language> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        list.add(Language.ENGLISH);
        sb.append(Language.ENGLISH.name());
        list.add(Language.FRENCH);
        sb.append(delimiter);
        sb.append(Language.FRENCH.name());
        String result = ListTransformer.concatEnumList(list);
        assertThat(result, is(sb.toString()));
    }

    @Test
    public void testExplodeEnumList() {
        final String delimiter = ListTransformer.delimiter;
        List<Language> expected = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        expected.add(Language.ENGLISH);
        sb.append(Language.ENGLISH.name());
        expected.add(Language.FRENCH);
        sb.append(delimiter);
        sb.append(Language.FRENCH.name());
        List<Language> result = ListTransformer.explodeEnumList(Language.class, sb.toString());
        assertThat(result, is(expected));
    }
}
