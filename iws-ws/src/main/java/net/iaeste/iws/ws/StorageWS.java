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

import net.iaeste.iws.api.Storage;
import net.iaeste.iws.api.constants.IWSErrors;
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
@SOAPBinding(style = SOAPBinding.Style.RPC)
//@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
@WebService(name = "storageWS", serviceName = "storageWSService", portName = "storageWS", targetNamespace = "http://ws.iws.iaeste.net/")
public class StorageWS implements Storage {

    private static final Logger LOG = LoggerFactory.getLogger(StorageWS.class);

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
        LOG.info(requestLogger.prepareLogMessage(token, "processFolder"));
        FolderResponse response;

        try {
            response = bean.processFolder(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new FolderResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
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
        LOG.info(requestLogger.prepareLogMessage(token, "fetchFolder"));
        FetchFolderResponse response;

        try {
            response = bean.fetchFolder(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new FetchFolderResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
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
        LOG.info(requestLogger.prepareLogMessage(token, "processFile"));
        FileResponse response;

        try {
            response = bean.processFile(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new FileResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
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
        LOG.info(requestLogger.prepareLogMessage(token, "fetchFile"));
        FetchFileResponse response;

        try {
            response = bean.fetchFile(token, request);
        } catch (RuntimeException e) {
            // The EJB's are all annotated with Transactional Logic, so if an
            // error is flying by - then it is caught here.
            LOG.error("Transactional Problem: " + e.getMessage(), e);
            response = new FetchFileResponse(IWSErrors.FATAL, "Internal error occurred while handling the request.");
        }

        return response;
    }
}
