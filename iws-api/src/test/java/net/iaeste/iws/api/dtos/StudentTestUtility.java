/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.StudentTestUtility
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

import net.iaeste.iws.api.util.DateTime;

/**
 * @author Teis Lindemark / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class StudentTestUtility {
    public static final String STUDENT_ID = "1";
    public static final String STUDENT_NAME = "Test Student";
    public static final Group GROUP = new Group();
    public static final DateTime CREATED = new DateTime();

    private StudentTestUtility() {}

    public static Student getMinimalStudent() {
        final Student minimalStudent = new Student();

        minimalStudent.setStudentId(STUDENT_ID);
        minimalStudent.setStudentName(STUDENT_NAME);
        minimalStudent.setGroup(GROUP);

        return minimalStudent;
    }
}
