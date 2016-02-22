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
package net.iaeste.iws.api;

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
 * <p>Storage Functionality.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Storage {

    /**
     * <p>Processes a folder, meaning either creating, renaming or deleting.</p>
     *
     * @param token   User Authentication Token
     * @param request Folder Request Object
     * @return Folder Response, including error information
     */
    FolderResponse processFolder(AuthenticationToken token, FolderRequest request);

    /**
     * <p>Fetches a Folder with sub Folders and Files meta data (no content).
     * The data being retrieved is both data belonging to Groups, which the
     * current User is a member of, but also data from Folders and Files, which
     * has been published by the Owning Group, provided that the Groups is
     * allowed to publish such information.</p>
     *
     * <p>The lookup of shared Folders and Files, is done with thorough sets of
     * checks, to ensure that data cannot be viewed if there is any element in
     * the tree not permitting it.</p>
     *
     * @param token   User Authentication Token
     * @param request FetchFolder Request Object
     * @return FetchFolder Response, including error information
     */
    FetchFolderResponse fetchFolder(AuthenticationToken token, FetchFolderRequest request);

    /**
     * <p>Processes a File.</p>
     *
     * @param token   User Authentication Token
     * @param request File Request Object
     * @return File Response, including error information
     */
    FileResponse processFile(AuthenticationToken token, FileRequest request);

    /**
     * <p>Fetches a File.</p>
     *
     * @param token   User Authentication Token
     * @param request Fetch File Request
     * @return File Response, including error information
     */
    FetchFileResponse fetchFile(AuthenticationToken token, FetchFileRequest request);
}
