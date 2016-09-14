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
package net.iaeste.iws.core;

import net.iaeste.iws.api.Storage;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.FetchFileRequest;
import net.iaeste.iws.api.requests.FetchFolderRequest;
import net.iaeste.iws.api.requests.FileRequest;
import net.iaeste.iws.api.requests.FolderRequest;
import net.iaeste.iws.api.responses.FetchFileResponse;
import net.iaeste.iws.api.responses.FetchFolderResponse;
import net.iaeste.iws.api.responses.FileResponse;
import net.iaeste.iws.api.responses.FolderResponse;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.core.services.StorageService;
import net.iaeste.iws.persistence.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class StorageController extends CommonController implements Storage {

    private static final Logger LOG = LoggerFactory.getLogger(StorageController.class);

    /**
     * Default Constructor, takes a ServiceFactory as input parameter, and uses
     * this in all the request methods, to get a new Service instance.
     *
     * @param factory The ServiceFactory
     */
    public StorageController(final ServiceFactory factory) {
        super(factory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FolderResponse processFolder(final AuthenticationToken token, final FolderRequest request) {
        FolderResponse response;

        try {
            verify(request);
            // Although we have to make a permission check, we will make it
            // once we have resolved the ownership of the folder. So the
            // permission check can be made against the correct Group, and
            // not an assumed Group.
            token.setGroupId(request.getFolder().getGroup().getGroupId());
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_FOLDER);

            final StorageService service = factory.prepareStorageService();
            response = service.processFolder(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FolderResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchFolderResponse fetchFolder(final AuthenticationToken token, final FetchFolderRequest request) {
        FetchFolderResponse response;

        try {
            verify(request);
            // Reading Folders is granted to the Member Groups, so if a GroupId
            // is set here, it will lead to an error.
            token.setGroupId(null);
            final Authentication authentication = verifyAccess(token, Permission.FETCH_FOLDER);

            final StorageService service = factory.prepareStorageService();
            response = service.fetchFolder(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FetchFolderResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileResponse processFile(final AuthenticationToken token, final FileRequest request) {
        FileResponse response;

        try {
            verify(request);
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_FILE);

            final StorageService service = factory.prepareStorageService();
            response = service.processFile(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FileResponse(e.getError(), e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchFileResponse fetchFile(final AuthenticationToken token, final FetchFileRequest request) {
        FetchFileResponse response;

        try {
            verify(request);
            // A user may always fetch their own files, so we make the
            // permission check in the Service class, if the GroupId is defined
            // in the Request. Otherwise, it is assumed that the is private and
            // treated thus.
            final Authentication authentication = verifyPrivateAccess(token);

            final StorageService service = factory.prepareStorageService();
            response = service.fetchFile(authentication, request);
        } catch (IWSException e) {
            // Generally, Exceptions should always be either logged or rethrown.
            // In our case, we're transforming the Exception into an Error
            // Object which can be returned to the User. However, to ensure
            // that we're not loosing anything - the Exception is also logged
            // here as a debug message
            LOG.debug(e.getMessage(), e);
            response = new FetchFileResponse(e.getError(), e.getMessage());
        }

        return response;
    }
}
