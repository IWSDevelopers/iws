/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.ExceptionHandlingTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.errorhandling;

/**
 * As we have different levels of error handling, it is good to try to properly
 * test some of these.<br />
 *   First level of Error handling, is our normal logic handling, meaning that
 * all known issues should be wrapped into an IWS Exception, these errors are
 * caught by our Controllers in the Core module.<br />
 *   Second level of Error handling, is in our Beans. The beans are catching all
 * Runtime Exceptions, not previously processed. As Runtime exceptions can be
 * thrown for various reasons, we consider these Fatal Errors, as we have
 * entered a state where the system cannot continue, but the system itself is
 * fine - meaning that the error can be corrected by the administrators.<br />
 *   Third level of Error handling, is for Fatal issues. Here, we're relying on
 * the Appication Server to properly log the issues. As a Fatal issue is
 * normally an unrecoverable state where we may not even be able to instantiate
 * a Bean, it makes little sense to try to cover this also. Errors such as
 * OutOfMemory or internal Application Server issues.<br />
 *   Although it may seem crazy to run these 3 types of tests for all our
 * requests, it is done deliberately, as we expect all our requests to behave
 * the same way regarding error handling. Otherwise, a programmer have made a
 * mistake internally. The type of Exception thrown is done for our Persistency
 * layer, since this is the deepest layer where problems may occur.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class AccessExceptionHandlingTest {
}
