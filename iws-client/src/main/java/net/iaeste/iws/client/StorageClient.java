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
package net.iaeste.iws.client;

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

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class StorageClient implements Storage {

    private final Storage client;

    /**
     * Default Constructor.
     */
    public StorageClient() {
        client = ClientFactory.getInstance().getStorageImplementation();
    }

    // =========================================================================
    // Implementation of methods from Storage in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public FolderResponse processFolder(final AuthenticationToken token, final FolderRequest request) {
        return client.processFolder(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchFolderResponse fetchFolder(final AuthenticationToken token, final FetchFolderRequest request) {
        return client.fetchFolder(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileResponse processFile(final AuthenticationToken token, final FileRequest request) {
        return client.processFile(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchFileResponse fetchFile(final AuthenticationToken token, final FetchFileRequest request) {
        return client.fetchFile(token, request);
    }
}
