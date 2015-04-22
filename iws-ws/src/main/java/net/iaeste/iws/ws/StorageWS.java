/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ws) - net.iaeste.iws.ws.StorageWS
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

import net.iaeste.iws.api.Storage;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.FetchFileRequest;
import net.iaeste.iws.api.requests.FetchFolderRequest;
import net.iaeste.iws.api.requests.FileRequest;
import net.iaeste.iws.api.requests.FolderRequest;
import net.iaeste.iws.api.responses.FetchFileResponse;
import net.iaeste.iws.api.responses.FetchFolderResponse;
import net.iaeste.iws.api.responses.FileResponse;
import net.iaeste.iws.api.responses.FolderResponse;
import net.iaeste.iws.ejb.StorageBean;
import net.iaeste.iws.ejb.cdi.IWSBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.WebServiceContext;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@WebService(name = "storageWS", serviceName = "storageWSService", portName = "storageWS", targetNamespace = "http://ws.iws.iaeste.net/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class StorageWS implements Storage {

    private static final Logger log = LoggerFactory.getLogger(StorageWS.class);

    /**
     * Injection of the IWS Storage Bean Instance, which embeds the
     * Transactional logic and itself invokes the actual Implementation.
     */
    @Inject @IWSBean private Storage bean = null;

    /**
     * The WebService Context is only available for Classes, which are annotated
     * with @WebService. So, we need it injected and then in the PostConstruct
     * method, we can create a new RequestLogger instance with it.
     */
    @Resource
    private WebServiceContext context = null;

    private RequestLogger requestLogger = null;

    /**
     * Post Construct method, to initialize our Request Logger instance.
     */
    @PostConstruct
    @WebMethod(exclude = true)
    public void postConstruct() {
        requestLogger = new RequestLogger(context);
    }

    /**
     * Setter for the JNDI injected Bean context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param bean IWS Exchange Bean instance
     */
    @WebMethod(exclude = true)
    public void setStorageBean(final StorageBean bean) {
        this.requestLogger = new RequestLogger(null);
        this.bean = bean;
    }

    // =========================================================================
    // Implementation of methods from Storage in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FolderResponse processFolder(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FolderRequest request) {
        log.info(requestLogger.prepareLogMessage(token, "processFolder"));
        return bean.processFolder(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchFolderResponse fetchFolder(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchFolderRequest request) {
        log.info(requestLogger.prepareLogMessage(token, "fetchFolder"));
        return bean.fetchFolder(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FileResponse processFile(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FileRequest request) {
        log.info(requestLogger.prepareLogMessage(token, "processFile"));
        return bean.processFile(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @WebResult(name = "response")
    public FetchFileResponse fetchFile(
            @WebParam(name = "token") final AuthenticationToken token,
            @WebParam(name = "request") final FetchFileRequest request) {
        log.info(requestLogger.prepareLogMessage(token, "fetchFile"));
        return bean.fetchFile(token, request);
    }
}
