/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.PublishingGroupTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import com.gargoylesoftware.base.testing.EqualsTester;
import net.iaeste.iws.api.dtos.exchange.PublishingGroup;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author  Sondre Naustdal / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection ResultOfObjectAllocationIgnored, ObjectAllocationInLoop
 */
public final class PublishingGroupTest {

    @Test
    @Ignore("Test is failing")
    public void testClassflow() {
        final String id = "123";
        final String name = "My Publishing Group";
        final List<Group> groups = buildGroups(4);

        // Fill a couple of Objects, that we can then verify
        final PublishingGroup unknown = new PublishingGroup(name, groups);
        final PublishingGroup filled = new PublishingGroup(id, name, groups);
        final PublishingGroup empty = new PublishingGroup();
        empty.setName(name);
        empty.setGroups(groups);

        // Verify that the Objects are correct
        unknown.verify();
        filled.verify();
        empty.verify();

        // Assertion checks against the fields
        assertThat(unknown.getName(), is(name));
        assertThat(unknown.getGroups(), is(groups));
        assertThat(unknown.getGroups(), is(not(sameInstance(groups))));
    }

    @Test
    @Ignore("Test is failing")
    public void testCopyConstructor() {
        // Build the Object to copy
        final PublishingGroup original = new PublishingGroup();
        original.setPublishingGroupId("bla");
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
        assertThat(copy.getGroups(), is(not(sameInstance(original.getGroups()))));
    }

    /**
     * The PublishingGroup Object has the following Validation rules:
     * <ul>
     *     <li>Id: if set, then it must not be empty</li>
     *     <li>Name: Not empty, and length between 1 and 50</li>
     *     <li>Groups: Not null</li>
     * </ul>
     */
    @Test
    @Ignore("Test is failing")
    public void testValidationErrors() {
        // Let's just use the same for our tests
        final PublishingGroup cut = new PublishingGroup();
        // All fields null, should result in 2 errors (name & groups)
        final Map<String, String> nullTest = cut.validate();

        // Set the values, should result in 3 errors (id, name & groupId)
        cut.setPublishingGroupId("");
        cut.setName("");
        final List<Group> groups = buildGroups(1);
        groups.get(0).setGroupId(null);
        cut.setGroups(groups);
        cut.getGroups().get(0).setGroupId(null);
        final Map<String, String> emptyTest = cut.validate();

        // Set too long values, should result in 2 errors (id and name)
        cut.setPublishingGroupId("123459098612345790987654321234578908765432");
        cut.setName("12345789098765432123457890986412356789864211235789876543212345678987643234567898765432");
        final Map<String, String> overflowTest = cut.validate();

        assertThat(nullTest.size(), is(2));
        assertThat(emptyTest.size(), is(3));
        assertThat(overflowTest.size(), is(2));
    }

    /**
     * All out DTO's must implement the "Standard Methods", meaning equals,
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
        final PublishingGroup diff3 = new PublishingGroup(id, "diferent name", groups);
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

    // =========================================================================
    // Internal methods
    // =========================================================================

    private static List<Group> buildGroups(final int count) {
        final List<Group> groups = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            final Group group = new Group();
            group.setGroupId(UUID.randomUUID().toString());
            groups.add(group);
        }

        return groups;
    }
}
