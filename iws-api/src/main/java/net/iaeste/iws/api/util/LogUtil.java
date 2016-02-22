/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
