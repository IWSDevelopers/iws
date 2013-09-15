/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.StudentTest
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
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.dtos.exchange.Student;
import net.iaeste.iws.api.exceptions.VerificationException;
import org.junit.Test;

import java.util.UUID;

/**
 * @author  Teis Lindemark / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class StudentTest {

    private static final String STUDENT_ID = UUID.randomUUID().toString();
    private static final String STUDENT_NAME = "Test Student";
    private static final Group GROUP = new Group();

    @Test
    public void testCopyConstructor() {
        final Student original = new Student(STUDENT_ID, STUDENT_NAME, GROUP);
        final Student copy = new Student(original);

        assertThat(original, is(not(nullValue())));
        assertThat(copy, is(not(nullValue())));
        assertThat(original, is(copy));
    }

    @Test
    public void testMinimalStudent() {
        final Student student = new Student(STUDENT_ID, STUDENT_NAME, GROUP);

        student.verify();

        assertThat(student, is(not(nullValue())));
        assertThat(student.getStudentId(), is(STUDENT_ID));
        assertThat(student.getStudentName(), is(STUDENT_NAME));
        assertThat(student.getGroup(), is(GROUP));
    }

    @Test(expected = VerificationException.class)
    public void testNotNullableStudentId() {
        final Student student = new Student(null, STUDENT_NAME, GROUP);

        student.verify();
    }

    @Test(expected = VerificationException.class)
    public void testNotNullableStudentName() {
        final Student student = new Student(STUDENT_ID, null, GROUP);

        student.verify();
    }

    @Test(expected = VerificationException.class)
    public void testNotNullableGroup() {
        final Student student = new Student(STUDENT_ID, STUDENT_NAME, null);

        student.verify();
    }
}
