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
     * Returns the Users TraceId, which is build uniquely for each Session. The
     * information is build uniquely for each Session, and is purely used for
     * logging, so it is possible to trace actions that led to errors. The
     * TraceId is not saved in the database.
     *
     * @return The Users Session based TraceId
     */
    String getTraceId();
}
