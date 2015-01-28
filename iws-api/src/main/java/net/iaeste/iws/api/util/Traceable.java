/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.Traceable
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.util;

/**
 * For logging purposes, it is needed to provide a mechanism to see who were
 * doing what, without logging private information about the user. For this,
 * the traceId is needed, and this Interface is used by the logging methods
 * to fetch the Id.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public interface Traceable {

    /**
     * Returns a unique Session based TraceId for a user. The traceId will
     * together with other information be able to see what a User has been
     * doing, and thus help with resolving production problems. All IWS log
     * requests is made with the TraceId, if possible.
     *
     * @return The Users Session based TraceId
     */
    String getTraceId();
}
