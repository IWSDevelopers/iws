/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ws) - net.iaeste.iws.ws.RequestLogger
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ws;

import net.iaeste.iws.api.util.LogUtil;
import net.iaeste.iws.api.util.Traceable;
import net.iaeste.iws.ejb.cdi.IWSBean;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@IWSBean
public class RequestLogger {

    @Resource
    private WebServiceContext context = null;

    public String prepareLogMessage(final Traceable trace, final String method) {
        return LogUtil.formatLogMessage(trace, "Incoming WebService Request for '" + method + "' from '" + readClientIp() + '\'');
    }

    public String prepareLogMessage(final String method) {
        return LogUtil.formatLogMessage(null, "Incoming WebService Request for '" + method + "' from " + readClientIp() + '\'');
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
