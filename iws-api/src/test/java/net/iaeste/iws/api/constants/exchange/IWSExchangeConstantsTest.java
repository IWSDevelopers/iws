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
package net.iaeste.iws.api.constants.exchange;

import static net.iaeste.iws.api.constants.exchange.IWSExchangeConstants.REFNO_PATTERN;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public final class IWSExchangeConstantsTest {

    /**
     * <p>Private methods should never be tested, as they are part of an
     * internal workflow. Classes should always be tested via their contract,
     * i.e. public methods.</p>
     *
     * <p>However, for Utility Classes, with a Private Constructor, the contract
     * disallows instantiation, so the constructor is thus not testable via
     * normal means. This little Test method will verify that the contract is
     * kept, and that the Constructor is not made public.</p>
     */
    @Test
    public void testPrivateConstructor() {
        try {
            final Constructor<IWSExchangeConstants> constructor = IWSExchangeConstants.class.getDeclaredConstructor();
            assertThat(constructor.isAccessible(), is(false));
            constructor.setAccessible(true);
            final IWSExchangeConstants mapper = constructor.newInstance();
            assertThat(mapper, is(not(nullValue())));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            fail("Could not invoke Private Constructor: " + e.getMessage());
        }
    }

    @Test
    public void testRefnoPattern() {
        assertThat(REFNO_PATTERN.matcher("DE-2016-12345678").matches(), is(true));
        assertThat(REFNO_PATTERN.matcher("DE-16-12345678").matches(), is(false));
        assertThat(REFNO_PATTERN.matcher("DE-2016").matches(), is(false));
        assertThat(REFNO_PATTERN.matcher("DE2016-12345678").matches(), is(false));
        assertThat(REFNO_PATTERN.matcher("DE-201612345678").matches(), is(false));
        assertThat(REFNO_PATTERN.matcher("DE-2016-123456781").matches(), is(false));
        assertThat(REFNO_PATTERN.matcher("DE-2016-12345678-1").matches(), is(false));
    }
}
