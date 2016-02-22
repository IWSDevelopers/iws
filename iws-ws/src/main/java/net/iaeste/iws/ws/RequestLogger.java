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

import net.iaeste.iws.api.util.LogUtil;
import net.iaeste.iws.api.util.Traceable;

import javax.servlet.ServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class RequestLogger {

    private final WebServiceContext context;

    public RequestLogger(final WebServiceContext context) {
        this.context = context;
    }

    public String prepareLogMessage(final Traceable trace, final String method) {
        return LogUtil.formatLogMessage(trace, "WebService Request: '" + method + "' from '" + readClientIp() + '\'');
    }

    public String prepareLogMessage(final String method) {
        return LogUtil.formatLogMessage(null, "WebService Request: '" + method + "' from '" + readClientIp() + '\'');
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

            return ((ServletRequest) request).getRemoteAddr();
        } else {
            ip = "127.0.0.1";
        }

        return ip;
    }
}
