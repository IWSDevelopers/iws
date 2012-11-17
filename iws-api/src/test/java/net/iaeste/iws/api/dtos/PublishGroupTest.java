/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.AuthenticationTokenTest
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

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import net.iaeste.iws.api.exceptions.VerificationException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
/**
 * All requests (with the exception of the initial Authorization request) is
 * made with an Object if this type as the first parameter. The Token contains
 * enough information to positively identify the user, who initiated a given
 * Request.
 *
 * @author  Sondre Naustdal / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection SuppressionAnnotation, CastToConcreteClass
 */
public final class PublishGroupTest {
    private static final Long STUDENT_ID = 1L;
    private static final String STUDENT_NAME = "Test Student";
    private static final Group GROUP = new Group();
    
    private static final List<Group> GROUP_LIST = new ArrayList<Group>();
    
    @Test
    public void testCopyConstructor() {
        final PublishGroup original = new PublishGroup(GROUP_LIST);
        final PublishGroup copy = new PublishGroup(original);

        assertThat(original, is(not(nullValue())));
        assertThat(copy, is(not(nullValue())));
        assertThat(original, is(copy));
    }
    
}
