/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.Storage
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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
 * Storage Functionality.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Storage {

    /**
     * Processes a folder, meaning either creating, renaming or deleting.
     *
     * @param token   User Authentication Token
     * @param request Folder Request Object
     * @return Folder Response, including error information
     */
    FolderResponse processFolder(AuthenticationToken token, FolderRequest request);

    /**
     * Fetches a Folder with sub Folders and Files meta data (no content). The
     * data being retrieved is both data belonging to Groups, which the current
     * User is a member of, but also data from Folders and Files, which has been
     * published by the Owning Group, provided that the Groups is allowed to
     * publish such information.<br />
     *   The lookup of shared Folders and Files, is done with thorough sets of
     * checks, to ensure that data cannot be viewed if there is any element in
     * the tree not permitting it.
     *
     * @param token   User Authentication Token
     * @param request FetchFolder Request Object
     * @return FetchFolder Response, including error information
     */
    FetchFolderResponse fetchFolder(AuthenticationToken token, FetchFolderRequest request);

    /**
     * Processes a File.
     *
     * @param token   User Authentication Token
     * @param request File Request Object
     * @return File Response, including error information
     */
    FileResponse processFile(AuthenticationToken token, FileRequest request);

    /**
     * Fetches a File
     *
     * @param token   User Authentication Token
     * @param request Fetch File Request
     * @return File Response, including error information
     */
    FetchFileResponse fetchFile(AuthenticationToken token, FetchFileRequest request);
}
