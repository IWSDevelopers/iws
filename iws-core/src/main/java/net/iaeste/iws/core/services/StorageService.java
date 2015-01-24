/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.StorageService
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.services;

import static net.iaeste.iws.core.transformers.StorageTransformer.transform;

import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.dtos.Folder;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.enums.StorageType;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.FetchFileRequest;
import net.iaeste.iws.api.requests.FetchFolderRequest;
import net.iaeste.iws.api.requests.FileRequest;
import net.iaeste.iws.api.requests.FolderRequest;
import net.iaeste.iws.api.responses.FetchFileResponse;
import net.iaeste.iws.api.responses.FetchFolderResponse;
import net.iaeste.iws.api.responses.FileResponse;
import net.iaeste.iws.api.responses.FolderResponse;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.FolderEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class StorageService extends CommonService<AccessDao> {

    private static final Logger log = LoggerFactory.getLogger(StorageService.class);
    final EntityManager entityManager;

    public StorageService(final Settings settings, final AccessDao accessDao, final EntityManager entityManager) {
        super(settings, accessDao);
        this.entityManager = entityManager;
    }

    /**
     * The ProcessFolder request allows users to either create new Folders,
     * update existing folder (rename it), or delete an empty folder.<br />
     *
     * The implementation of the processFolder request. The request will first
     * read the Folder from the system, and then apply checks permission checks
     * against it. It is needed to do it this way, since a Folder may be
     * published from a different Group, so it is publicly accessible.<br />
     *   There is a couple of rules which apply to the Folders. Folders  follow
     * the rule of the GroupType for the root folder, and the information is
     * inherited down the hierarchy. So a Folder belonging to a Members Group,
     * is not published, but a Folder belonging to the National Group is.<br />
     *   Files within a public folder is by default marked private, it takes an
     * active action to change the permissions to either the Group (Protected)
     * or everybody (Public).<r />
     *   Folders belong to Groups, and use the Groups publicList field, to
     * determine if it is publicly available or not, if
     *
     * @param authentication
     * @param request
     * @return
     */
    public FolderResponse processFolder(final Authentication authentication, final FolderRequest request) {
        throw new NotImplementedException("Method pending implementation, see Trac #946.");
    }

    public FetchFolderResponse fetchFolder(final Authentication authentication, final FetchFolderRequest request) {
        final String folderId = request.getFolderId();
        final Folder folder;
        final List<Folder> folders;
        final List<File> files;

        if (folderId == null) {
            // Fetch the root folder
            folder = transform(readFolder(authentication));
            folders = readRootFolders(authentication);
            files = new ArrayList<>(0);
        } else {
            // Fetch the content of the desired folder, including the files.
            folder = transform(readFolder(authentication, folderId));
            folders = readFolders(authentication, folderId);
            files = readFiles(authentication, folderId);
        }

        final FetchFolderResponse response = new FetchFolderResponse();
        folder.setFolders(folders);
        folder.setFiles(files);
        response.setFolder(folder);

        return response;
    }

    private FolderEntity readFolder(final Authentication authentication) {
        return null;
    }

    private FolderEntity readFolder(final Authentication authentication, final String folderId) {
        return null;
    }

    private List<Folder> readRootFolders(final Authentication authentication) {
        final String jql =
                "select f from FolderEntity f " +
                "where parentId = 3" +
                "  and f.group.groupType";
        final Query query = entityManager.createQuery(jql);

        return readAndTransfer(query);
    }

    private List<Folder> readFolders(final Authentication authentication, final String folderId) {
//        final List<FolderEntity> folders = dao.readFolders(authentication, folderId);
//        final List<Folder> convertedFolders = new ArrayList<>(folders.size());
//        for (final FolderEntity entity : folders) {
//            final Folder folder = transform(entity);
//            convertedFolders.add(folder);
//        }
//
//        return convertedFolders;
        return null;
    }

    private List<Folder> readAndTransfer(final Query query) {
        final List<FolderEntity> entities = query.getResultList();

        final List<Folder> folders = new ArrayList<>(entities.size());
        for (final FolderEntity entity : entities) {
            folders.add(transform(entity));
        }

        return folders;
    }

    private List<File> readFiles(final Authentication authentication, final String folderId) {
        return null;
    }

    public FileResponse processFile(final Authentication authentication, final FileRequest request) {
        final FileResponse response;

        if (request.getDeleteFile()) {
            deleteFile(authentication, request.getFile(), request.getType());
            response = new FileResponse();
        } else {
            final FolderEntity folder = handleFolder(request.getFile().getFolder());
            final FileEntity entity = processFile(authentication, request.getFile(), folder);
            final File file = transform(entity);
            response = new FileResponse(file);
        }

        return response;
    }

    private FolderEntity handleFolder(final Folder folder) {
        final FolderEntity entity;

        if ((folder == null) || (folder.getFolderId() == null)) {
            entity = null;
        } else {
            entity = lookupFolder(folder.getFolderId());
        }

        return entity;
    }

    private FolderEntity lookupFolder(final String externalId) {
        final String jql =
                "select f " +
                "from FolderEntity f " +
                "where externalId = :eid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("eid", externalId);

        final List<FolderEntity> found = query.getResultList();
        return found.isEmpty() ? null : found.get(0);
    }


    // TODO Extend the method with the possibility to read files from a public folder. Provided that the user has access to the folder (see GroupType) and the file is either public (all) or protected (member of file group).
    public FetchFileResponse fetchFile(final Authentication authentication, final FetchFileRequest request) {
        final String externalGroupId = request.getGroupId();
        final StorageType type = request.getType();
        final FileEntity entity;
        final File file;

        if (type == StorageType.OWNER) {
            if (externalGroupId == null) {
                entity = dao.findFileByUserAndExternalId(authentication.getUser(), request.getFileId());
            } else {
                // Check if the user is permitted to fetch files for the group, if
                // not then the method will thrown an Exception
                final GroupEntity group = dao.findGroupByPermission(authentication.getUser(), externalGroupId, Permission.FETCH_FILE);

                // Read the allowed file
                entity = dao.findFileByUserGroupAndExternalId(authentication.getUser(), group, request.getFileId());
            }
        } else {
            entity = dao.findAttachedFile(request.getFileId(), externalGroupId, type);
        }

        file = transform(entity);
        if (request.getReadFileData()) {
            file.setFiledata(readFile(entity));
        }

        return new FetchFileResponse(file);
    }
}
