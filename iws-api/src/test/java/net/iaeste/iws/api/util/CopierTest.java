/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.CopierTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.util;

import net.iaeste.iws.api.requests.AuthenticationRequest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class CopierTest {

    @Test
    public void testCopyValidObject() {
        final AuthenticationRequest request = new AuthenticationRequest("Alfa", "Beta");
        final AuthenticationRequest copy = Copier.copy(request);

        assertThat(copy, is(request));
        assertThat(copy, is(not(sameInstance(request))));
    }

    @Test
    public void testCopyInvalidObject() {
        final AuthenticationRequest request = null;
        final AuthenticationRequest copy = Copier.copy(request);

        assertThat(copy, is(request));
    }

    @Test
    public void testCopyList() {
        final Date date1 = new Date(123456789);
        final Date date2 = new Date(987654321);
        final List<Date> list = new ArrayList<>(2);
        list.add(date1);
        list.add(date2);

        final List<Date> copy = Copier.copy(list);

        assertThat(copy, is(list));
        assertThat(copy, is(not(sameInstance(list))));
        assertThat(copy.get(0), is(list.get(0)));
        assertThat(copy.get(0), is(not(sameInstance(list.get(0)))));
    }

    @Test
    public void testCopyEmptyList() {
        final List<Date> list = new ArrayList<>(0);

        final List<Date> copy = Copier.copy(list);

        assertThat(copy, is(list));
        assertThat(copy, is(not(sameInstance(list))));
    }

    @Test
    public void testCopyNullList() {
        final List<Date> list = null;

        final List<Date> copy = Copier.copy(list);

        assertThat(copy, is(not(nullValue())));
        assertThat(copy.isEmpty(), is(true));
    }

    @Test
    public void testCopyDateMap() {
        final Date key = new Date(1);
        final Date val = new Date(3);

        final Map<Date, Date> map = new HashMap<>(1);
        map.put(key, val);

        final Map<Date, Date> copy = Copier.copy(map);

        assertThat(copy, is(map));
        assertThat(copy, is(not(sameInstance(map))));
        assertThat(copy.containsKey(key), is(true));
        assertThat(copy.get(key), is(val));
        assertThat(copy.get(key), is(not(sameInstance(val))));
    }

    @Test
    public void testCopyStringMap() {
        final String key = "key";
        final String val = "value";

        final Map<String, String> map = new HashMap<>(1);
        map.put(key, val);

        final Map<String, String> copy = Copier.copy(map);

        assertThat(copy, is(map));
        assertThat(copy, is(not(sameInstance(map))));
        assertThat(copy.containsKey(key), is(true));
        assertThat(copy.get(key), is(val));
        assertThat(copy.get(key), is(not(sameInstance(val))));
    }

    @Test
    public void testCopyEmptyMap() {
        final Map<String, String> map = new HashMap<>(0);

        final Map<String, String> copy = Copier.copy(map);

        assertThat(copy, is(map));
        assertThat(copy, is(not(sameInstance(map))));
        assertThat(copy.isEmpty(), is(true));
    }

    @Test
    public void testCopyNullMap() {
        final Map<String, String> map = null;

        final Map<String, String> copy = Copier.copy(map);

        assertThat(copy, is(not(nullValue())));
        assertThat(copy.isEmpty(), is(true));
    }
}
