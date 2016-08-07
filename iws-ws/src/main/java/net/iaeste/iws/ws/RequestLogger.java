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
package net.iaeste.iws.ws;

import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.responses.Response;
import net.iaeste.iws.common.utils.LogUtil;
import net.iaeste.iws.api.util.Traceable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
final class RequestLogger {

    private static final Logger LOG = LoggerFactory.getLogger(RequestLogger.class);

    private final WebServiceContext context;

    RequestLogger(final WebServiceContext context) {
        this.context = context;
    }

    String prepareLogMessage(final Traceable trace, final String method) {
        return LogUtil.formatLogMessage(trace, "WebService Request: '" + method + "' from '" + readClientIp() + '\'');
    }

    String prepareLogMessage(final String method) {
        return LogUtil.formatLogMessage(null, "WebService Request: '" + method + "' from '" + readClientIp() + '\'');
    }

    /**
     * <p>All WebService methods have the same basic error handling, and as the
     * cause of the problems at this point can only be from a non-controllable
     * source which is either Database issue or other critical things - we
     * simply have a common way of dealing with this error.</p>
     *
     * @param cause  The Exception caught with the information about the cause
     * @param clazz  The actual Fallible derived Class to use
     * @param <F>    Response must be derived from the IWS Fallible Object
     * @return Response Object instantiated with error information
     */
    static <F extends Response> F handleError(final Throwable cause, final Class<F> clazz) {
        LOG.error("External Problem: {}", cause.getMessage(), cause);
        F response;

        try {
            final Constructor<F> constructor = clazz.getConstructor(IWSError.class, String.class);
            response = constructor.newInstance(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LOG.error("Panic: {}", e.getMessage(), e);
            response = (F) new Response(IWSErrors.FATAL, "IWS Panic - please consult the IWS Developers immediately.");
        }

        return response;
    }

    // =========================================================================
    // Internal methods
    // =========================================================================

    /**
     * For logging purposes, we can read the IP address of the requesting
     * Client. This will help track usage, and also to see who is using what
     * features.
     *
     * @return Requesting Client IP Address
     */
    private String readClientIp() {
        final String ip;

        if (context != null) {
            final String servlet = MessageContext.SERVLET_REQUEST;
            final Object request = context.getMessageContext().get(servlet);

            ip = ((ServletRequest) request).getRemoteAddr();
        } else {
            ip = "localhost";
        }

        return ip;
    }
}
