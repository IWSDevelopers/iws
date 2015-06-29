/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.monitoring.MonitoringProcessorTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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
