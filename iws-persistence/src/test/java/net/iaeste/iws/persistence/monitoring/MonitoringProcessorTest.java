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
package net.iaeste.iws.persistence.monitoring;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.dtos.Field;
import net.iaeste.iws.api.util.Serializer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class MonitoringProcessorTest {

    @Test
    public void testSerialization() {
        // First, our test data
        final ArrayList<Field> list = new ArrayList<>(5);
        final Field field1 = new Field("field1");
        final Field field2 = new Field("field2", "This stink", "What a wonderful world");
        final Field field3 = new Field("field3", "Apple", "Android");
        final Field field4 = new Field("field4", null, "bla");
        final Field field5 = new Field("field5", "bla", null);
        list.add(field1);
        list.add(field2);
        list.add(field3);
        list.add(field4);
        list.add(field5);

        // Now, we instantiate the Monitoring and performs the serialization
        final MonitoringProcessor cut = new MonitoringProcessor();
        final byte[] serialized = Serializer.serialize(list);
        final List<Field> deserialized = Serializer.deserialize(serialized);

        assertThat(serialized, is(not(nullValue())));
        assertThat(deserialized, is(not(nullValue())));
        assertThat(list.size(), is(deserialized.size()));
        assertThat(list.toString(), is(deserialized.toString()));
    }
}
