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

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@WebService(name = "storageWS", serviceName = "storageWSService", portName = "storageWS", targetNamespace = "http://ws.iws.iaeste.net/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class StorageWS implements Storage {

    /**
     * Injection of the IWS Storage Bean Instance, which embeds the
     * Transactional logic and itself invokes the actual Implemenation.
     */
    @Inject @IWSBean private Storage bean = null;

    /**
     * Setter for the JNDI injected Bean context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param bean IWS Storage Bean instance
     */
    @WebMethod(exclude = true)
    public void setStorageBean(final StorageBean bean) {
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
        return bean.fetchFile(token, request);
    }
}
