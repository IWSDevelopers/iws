/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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

import junit.framework.Assert;
import net.iaeste.iws.api.exceptions.VerificationException;
import org.junit.Before;
import org.junit.Test;

import static net.iaeste.iws.api.dtos.StudentTestUtility.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Teis Lindemark / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class StudentTest {
    private Student student = getMinimalStudent();

    static final String ERRMSG_NOT_NULL = " field cannot be null";

    @Before
    public void before() {
        this.student = getMinimalStudent();
    }

    @Test
    public void testCopyConstructor() {
        final Student studentToCopy = getMinimalStudent();
        final Student copiedStudent = new Student(studentToCopy);
        assertThat(studentToCopy, is(not(nullValue())));
        assertThat(copiedStudent, is(not(nullValue())));
        assertThat(studentToCopy, is(copiedStudent));
    }

    @Test
    public void testMinimalStudent() {
        Assert.assertNotNull("reference not null",student);
        assertThat("StudentId",STUDENT_ID,is(student.getStudentId()));
        assertThat("StudentName",STUDENT_NAME,is(student.getStudentName()));
        assertThat("Group",GROUP,is(student.getGroup()));
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public void testMinimalStudentShouldBeValid() {
        student = getMinimalStudent();
        student.verify();
    }

    @Test
    public void testNotNullableStudentId() {
        student = getMinimalStudent();
        student.setStudentId(null);
        assertThat(String.format("studentId%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testNotNullableStudentName() {
        student = getMinimalStudent();
        student.setStudentName(null);
        assertThat(String.format("studentName%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));
    }

    @Test
    public void testNotNullableGroup() {
        student = getMinimalStudent();
        student.setGroup(null);
        assertThat(String.format("studentGroup%s", ERRMSG_NOT_NULL), isVerificationExceptionThrown(), is(true));
    }

    public boolean isVerificationExceptionThrown() {
        try {
            student.verify();
            return false;
        } catch (VerificationException ignore) {
            return true;
        }
    }
}
