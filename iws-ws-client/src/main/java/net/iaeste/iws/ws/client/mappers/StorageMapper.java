/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ws-client) - net.iaeste.iws.ws.client.mappers.StorageMapper
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ws.client.mappers;

import net.iaeste.iws.api.dtos.Folder;
import net.iaeste.iws.api.enums.StorageType;
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
 * @since   IWS 1.1
 */
public final class StorageMapper extends CommonMapper {

    public static net.iaeste.iws.ws.FolderRequest map(final FolderRequest api) {
        net.iaeste.iws.ws.FolderRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FolderRequest();

            ws.setAction(map(api.getAction()));
            ws.setParentId(api.getParentId());
            ws.setFolder(map(api.getFolder()));
        }

        return ws;
    }

    public static FolderResponse map(final net.iaeste.iws.ws.FolderResponse ws) {
        FolderResponse api = null;

        if (ws != null) {
            api = new FolderResponse(map(ws.getError()), ws.getMessage());

            api.setFolder(map(ws.getFolder()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.FetchFolderRequest map(final FetchFolderRequest api) {
        net.iaeste.iws.ws.FetchFolderRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchFolderRequest();

            ws.setFolderId(api.getFolderId());
        }

        return ws;
    }

    public static FetchFolderResponse map(final net.iaeste.iws.ws.FetchFolderResponse ws) {
        FetchFolderResponse api = null;

        if (ws != null) {
            api = new FetchFolderResponse(map(ws.getError()), ws.getMessage());

            api.setFolder(map(ws.getFolder()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.FileRequest map(final FileRequest api) {
        net.iaeste.iws.ws.FileRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FileRequest();

            ws.setAction(map(api.getAction()));
            ws.setFile(map(api.getFile()));
            ws.setType(map(api.getType()));
        }

        return ws;
    }

    public static FileResponse map(final net.iaeste.iws.ws.FileResponse ws) {
        FileResponse api = null;

        if (ws != null) {
            api = new FileResponse(map(ws.getError()), ws.getMessage());

            api.setFile(map(ws.getFile()));
        }

        return api;
    }

    public static net.iaeste.iws.ws.FetchFileRequest map(final FetchFileRequest api) {
        net.iaeste.iws.ws.FetchFileRequest ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.FetchFileRequest();

            ws.setFileId(api.getFileId());
            ws.setGroupId(api.getGroupId());
            ws.setType(map(api.getType()));
            ws.setReadFileData(api.getReadFileData());
        }

        return ws;
    }

    public static FetchFileResponse map(final net.iaeste.iws.ws.FetchFileResponse ws) {
        FetchFileResponse api = null;

        if (ws != null) {
            api = new FetchFileResponse(map(ws.getError()), ws.getMessage());

            api.setFile(map(ws.getFile()));
        }

        return api;
    }

    // =========================================================================
    // Internal mapping of Storage relevant Objects
    // =========================================================================

    private static net.iaeste.iws.ws.StorageType map(final StorageType api) {
        return api != null ? net.iaeste.iws.ws.StorageType.valueOf(api.name()) : null;
    }

    private static Folder map(final net.iaeste.iws.ws.Folder ws) {
        Folder api = null;

        if (ws != null) {
            api = new Folder();

            api.setFolderId(ws.getFolderId());
            api.setParentId(ws.getParentId());
            api.setGroup(map(ws.getGroup()));
            api.setFoldername(ws.getFoldername());
            //TODO List<Folder> folders = null;
            //TODO List<File> files = null;
            api.setModified(map(ws.getModified()));
            api.setCreated(map(ws.getCreated()));
        }

        return api;
    }

    private static net.iaeste.iws.ws.Folder map(final Folder api) {
        net.iaeste.iws.ws.Folder ws = null;

        if (api != null) {
            ws = new net.iaeste.iws.ws.Folder();

            ws.setFolderId(api.getFolderId());
            ws.setParentId(api.getParentId());
            ws.setGroup(map(api.getGroup()));
            ws.setFoldername(api.getFoldername());
            //TODO List<Folder> folders = null;
            //TODO List<File> files = null;
            ws.setModified(map(api.getModified()));
            ws.setCreated(map(api.getCreated()));
        }

        return ws;
    }
}
