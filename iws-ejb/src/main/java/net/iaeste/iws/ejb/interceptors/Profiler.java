/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.interceptors.Profiler
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.text.DecimalFormat;

/**
 * Interceptor Profiling Class.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class Profiler {

    private static final Logger log = LoggerFactory.getLogger(Profiler.class);
    private static final DecimalFormat format = new DecimalFormat("###,###.##");

    @AroundInvoke
    public Object profile(final InvocationContext invocation) throws Exception {
        final long start = System.nanoTime();

        try {
            return invocation.proceed();
        } finally {
            final double time = (double) (System.nanoTime() - start) / 1000000;
            final String name = invocation.getMethod().getName();
            final String duration = format.format(time);

            log.debug("Profile: Method " + name + " took " + duration + " ms.");
        }
    }
}
