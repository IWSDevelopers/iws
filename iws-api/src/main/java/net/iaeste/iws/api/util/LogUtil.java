/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.LogUtil
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
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class LogUtil {

    /**
     * Private Constructor, this is a utility class.
     */
    private LogUtil() {
    }

    /**
     * <p>Prepares the log message, by adding the required traceId information
     * to the given Message, and returns the formatted result. Note, that the
     * traceId is part of the Authentication Object.</p>
     *
     * <p>The method uses the String format method to prepare the log
     * message.</p>
     *
     * @param trace   The User's trace Id
     * @param message String to format with the provided arguments
     * @param args    Arguments for the String to format
     * @return Formatted Log message
     */
    public static String formatLogMessage(final Traceable trace, final String message, final Object... args) {
        // The default format for our log messages starts with a traceId
        final String rawMessage = "[traceId = %s] " + message;

        // Now, we need to prepend the traceId to the formatting parameters
        final Object[] parameters = new Object[1 + ((args != null) ? args.length : 0)];
        parameters[0] = (trace != null) ? trace.getTraceId() : "none";

        // Expand the Parameters with the provided arguments
        if (args != null) {
            int i = 1;
            for (final Object obj : args) {
                parameters[i] = obj;
                i++;
            }
        }

        // Return the formatted string with the parameter arguments
        return String.format(rawMessage, parameters);
    }
}
