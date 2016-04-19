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
package net.iaeste.iws.api.dtos.exchange;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import com.gargoylesoftware.base.testing.EqualsTester;
import net.iaeste.iws.api.dtos.Group;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author  Sondre Naustdal / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class PublishingGroupTest {

    @Test
    public void testClassflow() {
        final String name = "My Publishing Group";
        final List<Group> groups = buildGroups(4);

        // Fill a couple of Objects, that we can then verify
        final PublishingGroup unknown = new PublishingGroup(name, groups);
        final PublishingGroup empty = new PublishingGroup();
        empty.setName(name);
        empty.setGroups(groups);

        // Assertion checks against the fields
        assertThat(unknown.getName(), is(name));
        assertThat(unknown.getGroups(), is(groups));
    }

    @Test
    public void testCopyConstructor() {
        // Build the Object to copy
        final PublishingGroup original = new PublishingGroup();
        original.setPublishingGroupId(UUID.randomUUID().toString());
        original.setName("my name");
        original.setGroups(buildGroups(5));

        // Create a Copy of the original
        final PublishingGroup copy = new PublishingGroup(original);

        // Run checks, most importantly, is verify that the mutable fields are
        // not the same instance, meaning that they cannot be altered
        assertThat(copy, is(original));
        assertThat(copy, is(not(sameInstance(original))));
        assertThat(copy.getPublishingGroupId(), is(original.getPublishingGroupId()));
        assertThat(copy.getName(), is(original.getName()));
        assertThat(copy.getGroups(), is(original.getGroups()));
    }

    /**
     * All out DTOs must implement the "Standard Methods", meaning equals,
     * hashCode and toString. The purpose of this test, is to ensure that all
     * three is working, and not causing any strange problems, i.e. Exceptions.
     */
    @Test
    public void testStandardMethods() {
        // Test preconditions
        final String id = UUID.randomUUID().toString();
        final String name = "name";
        final List<Group> groups = buildGroups(2);

        // Test Objects
        final PublishingGroup result = new PublishingGroup(id, name, groups);
        final PublishingGroup same = new PublishingGroup(id, name, groups);
        final PublishingGroup empty = new PublishingGroup();
        final PublishingGroup diff1 = new PublishingGroup(name, groups);
        final PublishingGroup diff2 = new PublishingGroup(UUID.randomUUID().toString(), name, groups);
        final PublishingGroup diff3 = new PublishingGroup(id, "different name", groups);
        final PublishingGroup diff4 = new PublishingGroup(id, name, buildGroups(4));

        // Assertion Checks
        assertThat(result.hashCode(), is(same.hashCode()));
        assertThat(result.hashCode(), is(not(empty.hashCode())));
        assertThat(result.hashCode(), is(not(diff1.hashCode())));
        assertThat(result.toString(), is(same.toString()));
        assertThat(result.toString(), is(not(empty.toString())));
        assertThat(result.toString(), is(not(diff1.toString())));

        new EqualsTester(result, same, empty, null);
        new EqualsTester(result, same, diff1, null);
        new EqualsTester(result, same, diff2, null);
        new EqualsTester(result, same, diff3, null);
        new EqualsTester(result, same, diff4, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyId() {
        final String id = "";
        final String name = "name";
        final List<Group> groups = buildGroups(2);

        // Test Objects
        new PublishingGroup(id, name, groups);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidId() {
        final String id = "Alpha Beta Gamma 123";
        final String name = "name";
        final List<Group> groups = buildGroups(2);

        // Test Objects
        new PublishingGroup(id, name, groups);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullName() {
        final String id = UUID.randomUUID().toString();
        final String name = null;
        final List<Group> groups = buildGroups(2);

        // Test Objects
        new PublishingGroup(id, name, groups);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyName() {
        final String id = UUID.randomUUID().toString();
        final String name = "";
        final List<Group> groups = buildGroups(2);

        // Test Objects
        new PublishingGroup(id, name, groups);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLongName() {
        final String id = UUID.randomUUID().toString();
        final String name = "This name is exceeding the max allowed length of 50 chars.";
        final List<Group> groups = buildGroups(2);

        // Test Objects
        new PublishingGroup(id, name, groups);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullGroups() {
        final String id = UUID.randomUUID().toString();
        final String name = "name";
        final List<Group> groups = null;

        // Test Objects
        new PublishingGroup(id, name, groups);
    }

    // =========================================================================
    // Internal methods
    // =========================================================================

    private static List<Group> buildGroups(final int count) {
        final List<Group> groups = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            groups.add(buildGroup());
        }

        return groups;
    }

    private static Group buildGroup() {
        final Group group = new Group();
        group.setGroupId(UUID.randomUUID().toString());

        return group;
    }
}
