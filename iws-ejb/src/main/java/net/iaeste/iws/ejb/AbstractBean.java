/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.AbstractBean
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb;

import net.iaeste.iws.api.util.Fallible;

import javax.annotation.PostConstruct;

/**
 * Common functionality for all IWS Beans.
 *
 * @author  Kim Jensen / last $Author: $
 * @version $Revision: $ / $Date:$
 * @since   1.7
 * @noinspection MethodMayBeStatic
 */
public abstract class AbstractBean {

    /**
     * The index value to use when looking up the calling method name from the
     * stack trace, 0 = this point, 1 = lookup method, 2 = log method,
     * 3 = calling method.
     */
    private static final int STACKTRACE_INDEX = 3;

    /**
     * Common method for all beans, contain the logic, which should otherwise
     * exists in a POJO constructor.
     */
    @PostConstruct
    public abstract void postConstruct();

    /**
     * Creates the String to put into the log, based on the given result object,
     * and optionally the start time, which is used to calculate how long it
     * took the method to complete.
     *
     * @param fallible Result object to base the log entry on
     * @return Log entry string
     */
    protected final String generateResponseLog(final Fallible fallible) {
        final String method = getCallingMethod();
        final String logMessage;

        if (fallible.isOk()) {
            logMessage = method + " completed.";
        } else {
            final String message = fallible.getMessage();
            logMessage = "Error in  " + method + "  " + message;
        }

        return logMessage;
    }

    /**
     * Creates the String to put into the log, based on the given error cause.
     *
     * @param cause The error cause
     * @return Log entry string
     */
    protected final String generateErrorLog(final Throwable cause) {
        final String method = getCallingMethod();

        // For debugging purposes, the following line can be uncommented, but
        // under if someone checks it in uncommented, the person will be
        // tortured, roasted over a slow fire and finally be subjected to
        // ridicule. So in short - don't do it!
        // cause.printStackTrace();
        return "Error in  " + method + ": " + cause.getMessage();
    }

    /**
     * Retrieves the method used to call the logmethods in this class, by
     * looking at the stacktrace from the current thread.
     *
     * @return Name of the calling method
     */
    private String getCallingMethod() {
        final StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        return trace[STACKTRACE_INDEX].getMethodName();
    }
}
