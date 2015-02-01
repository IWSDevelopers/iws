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

import static net.iaeste.iws.api.util.LogUtil.formatLogMessage;
import static net.iaeste.iws.core.transformers.StorageTransformer.transform;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.dtos.Folder;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.StorageType;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.FetchFileRequest;
import net.iaeste.iws.api.requests.FetchFolderRequest;
import net.iaeste.iws.api.requests.FileRequest;
import net.iaeste.iws.api.requests.FolderRequest;
import net.iaeste.iws.api.responses.FetchFileResponse;
import net.iaeste.iws.api.responses.FetchFolderResponse;
import net.iaeste.iws.api.responses.FileResponse;
import net.iaeste.iws.api.responses.FolderResponse;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.exceptions.StorageException;
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
import java.util.Objects;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class StorageService extends CommonService<AccessDao> {

    private static final Logger log = LoggerFactory.getLogger(StorageService.class);
    private static final int ROOT_FOLDER_ID = 3;
    private static final String ROOT_FOLDER_EID = "afec3bc0-296b-4bf2-8a9e-c2d7b74e93a0";
    // The EntityManager is temporarily present here, until the CommonService &
    // StorageService can both be cleaned up. The addition of the Library means
    // that we have two different approaches to dealing with files.
    // So, refactoring awaits once all is finalized and tested.
    private final EntityManager entityManager;

    public StorageService(final Settings settings, final AccessDao accessDao, final EntityManager entityManager) {
        super(settings, accessDao);
        this.entityManager = entityManager;
    }

    /**
     * It is possible to create/update and delete folders, provided that a few
     * criterias have been met.<br />
     *   Creating or renaming folders can only be done, if no other folders
     * exists with the same name. This is to ensure that it is easier for users
     * to distinguish between folders, as similar names will only cause
     * confusion.<br />
     *   Deleting folders can only be done for folders which is empty, meaning
     * that there is no sub folders or files associated with it.<br />
     *   Additionally, ownership checks are also being made, so it shouldn't be
     * possible to spoof the system by sending different Id's along.
     *
     * @param authentication User Authentication Object
     * @param request        User Processing Reuqest Object
     * @return Processing response, folder is null if deleted
     */
    public FolderResponse processFolder(final Authentication authentication, final FolderRequest request) {
        final String folderExternalId = request.getFolder().getFolderId();
        final Folder folder;

        if (request.getAction() == FolderRequest.Action.PROCESS) {
            if (folderExternalId == null) {
                // Check the parentId, if it is null - it means we're about to
                // create a new root folder for a Group. Only one such Group may
                // exist, but as Unique Constraints in databases ignores rows
                // where one of the unique fields is null, we need to make this
                // check additionally.
                final FolderEntity foundRootEntity = findRootFolderForGroup(request.getFolder().getGroup());

                if (foundRootEntity != null) {
                    if (request.getParentId() == null) {
                        // Attempted to work with the existing Root folder, not
                        // good. So we're going to throw an Exception...
                        throw new StorageException("Cannot process existing Root folder for group " + request.getFolder().getGroup().getGroupName());
                    }
                    final FolderEntity foundParentEntity = findParentFolder(request.getParentId(), authentication.getGroup());
                    if (foundParentEntity == null) {
                        // Not good - we cannot find any parent matching
                        // the given Parent Folder Id
                        throw new StorageException("Cannot create new subfolder for illegal Parent");
                    }

                    // Processing a new Subfolder. Let's just make sure that
                    // a similar name doesn't exist, if so - we'll throw an
                    // Error. Although the IWS doesn't really care for it,
                    // the old IW3 style was that the folders were building
                    // up a tree structure that was case insensitive, so
                    // we're keeping the same functionality here.
                    final FolderEntity foundFolderEntity = findSimilarFolder(request.getFolder().getFoldername(), foundParentEntity.getId());
                    if (foundFolderEntity != null) {
                        throw new StorageException("Attempting to create new Folder with similar name, which is not allowed.");
                    }
                    // Phew, all pre-checks are in place. Now we can create the new Folder.
                    final FolderEntity entity = new FolderEntity();
                    entity.setFoldername(request.getFolder().getFoldername().trim());
                    entity.setParentId(foundParentEntity.getId());
                    entity.setGroup(authentication.getGroup());
                    dao.persist(authentication, entity);
                    folder = transform(entity);
                } else {
                    // No root folder was found, let's create a new one. The
                    // data from the Request is ignored in this case
                    final FolderEntity entity = new FolderEntity();
                    entity.setFoldername(request.getFolder().getGroup().getGroupName());
                    entity.setGroup(authentication.getGroup());

                    // Now, let's save it and return the converted Folder
                    dao.persist(authentication, entity);
                    folder = transform(entity);
                }
            } else {
                // Requesting to process an existing Folder, with a given
                // External Folder Id
                final FolderEntity existingFolder = findByExternalId(folderExternalId);
                if (existingFolder == null) {
                    throw new StorageException("The requested folder with Id '" + folderExternalId + "' could not be found.");
                }
                // Now, let's just check that there wasn't any fiddling with the
                // Id's. The simplest check that the Groups given match.
                if (authentication.getGroup().equals(existingFolder.getGroup())) {
                    // Now to the final check. It is only possible to rename a
                    // folder, nothing else. However, folders should have
                    // distinctive names, to avoid confusion
                    final FolderEntity similarFolder = findSimilarFolder(request.getFolder().getFoldername(), existingFolder.getParentId());
                    if ((similarFolder == null) || Objects.equals(similarFolder.getId(), existingFolder.getId())) {
                        // Okay, we've reached the point where we can finally
                        // merge the folders
                        final FolderEntity toMerge = transform(request.getFolder());
                        dao.persist(authentication, existingFolder, toMerge);
                    } else {
                        throw new StorageException("Cannot process the folder, a different folder exist with a similar name.");
                    }
                } else {
                    throw new StorageException("Cannot process the Folder, as it doesn't belong to the given Group.");
                }
                folder = transform(existingFolder);
            }
        } else {
            // We only support two actions, Process and Delete. Above is the
            // handling of the processing (either create or update), and here
            // we simple deal with deleting. Deleting Folders is allowed,
            // provided that the Folder is empty, meaning that we have no
            // references from other Folders or Files.
            final FolderEntity foundFolder = findByExternalId(request.getFolder().getFolderId());
            if (foundFolder == null) {
                throw new StorageException("The requested folder with Id '" + folderExternalId + "' could not be found.");
            }
            if (!Objects.equals(foundFolder.getGroup(), authentication.getGroup())) {
                throw new StorageException("Cannot delete a Folder belonging to someone else.");
            }
            // Now, let's see if it contains data. Only empty folders can be
            // deleted from the system
            final List<FolderEntity> subfolders = findSubFolders(foundFolder.getId());
            final List<FileEntity> files = findFiles(foundFolder.getId());
            if (subfolders.isEmpty() && files.isEmpty()) {
                log.info(formatLogMessage(authentication, "Deleting folder %s from the system.", foundFolder));
                dao.delete(foundFolder);
                folder = null;
            } else {
                throw new StorageException("Cannot delete a folder that contains data.");
            }
        }

        return new FolderResponse(folder);
    }

    private List<FileEntity> findFiles(final Long folderId) {
        final String jql = "select f from FileEntity f where f.folder.id = :folderId";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("folderId", folderId);

        return query.getResultList();
    }

    private List<FolderEntity> findSubFolders(final Long parentId) {
        final String jql = "select f from FolderEntity f where f.parentId = :parentId";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("parentId", parentId);

        return query.getResultList();
    }

    private FolderEntity findByExternalId(final String externalId) {
        final String jql = "select f from FolderEntity f where f.externalId = :eid";
        final Query query = entityManager.createQuery(jql);
        final List<FolderEntity> found = query.getResultList();
        final FolderEntity entity;

        if (found.isEmpty()) {
            entity = null;
        } else if (found.size() == 1) {
            entity = found.get(0);
        } else {
            // Showing to the world that we have a database error is not
            // necessarily a good idea. Instead, we log it an throw a more
            // neutral error message
            log.error("Fatal error, Unique Constraint violation against Folder table for External Id " + externalId);
            throw new StorageException("Could not uniquely identify the folder by its Id.");
        }

        return entity;
    }

    private FolderEntity findRootFolderForGroup(final Group group) {
        final String jql =
                "select f from FolderEntity f " +
                "where f.group.parentId is null" +
                "  and f.group.externalId = :egid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("egid", group.getGroupId());

        final List<FolderEntity> found = query.getResultList();
        final FolderEntity entity;

        if (found.isEmpty()) {
            entity = null;
        } else if (found.size() == 1) {
            entity = found.get(0);
        } else {
            throw new StorageException("Multiple Root folders exists for Group " + group.getGroupName() + ", only one is allowed.");
        }

        return entity;
    }

    private FolderEntity findParentFolder(final String parentId, final GroupEntity group) {
        final String jql =
                "select f from FolderEntity f " +
                "where f.id = :fid" +
                "  and f.group.id = :gid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("fid", parentId);
        query.setParameter("gid", group.getId());

        final List<FolderEntity> found = query.getResultList();
        final FolderEntity entity;

        if (found.isEmpty()) {
            entity = null;
        } else if (found.size() == 1) {
            entity = found.get(0);
        } else {
            throw new StorageException("Multiple Parent folders exists for Group " + group.getGroupName() + ", only one is allowed.");
        }

        return entity;
    }

    private FolderEntity findSimilarFolder(final String foldername, final Long parentId) {
        final String jql =
                "select f from FolderEntity f " +
                "where lower(f.foldername) = lower(:name)" +
                "  and f.parentId = :pid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("name", foldername.trim());
        query.setParameter("pid", parentId);

        final List<FolderEntity> found = query.getResultList();
        final FolderEntity entity;

        if (found.isEmpty()) {
            entity = null;
        } else if (found.size() == 1) {
            entity = found.get(0);
        } else {
            throw new StorageException("Multiple folders exists for Folder " + foldername + ", only one is allowed.");
        }

        return entity;
    }

    public FetchFolderResponse fetchFolder(final Authentication authentication, final FetchFolderRequest request) {
        final String folderId = request.getFolderId();
        final Folder folder;
        final List<Folder> folders;
        final List<File> files;

        if (folderId == null) {
            log.info(formatLogMessage(authentication, "Reading the Root Folder."));
            // Fetch the root folder
            folder = transform(readFolder(authentication, ROOT_FOLDER_EID));
            folders = readRootFolders();
            files = new ArrayList<>(0);
        } else {
            log.info(formatLogMessage(authentication, "Reading the Folder with Id {}.", folderId));
            // Fetch the content of the desired folder, including the files.
            final FolderEntity folderEntity = readFolder(authentication, folderId);
            if (folderEntity != null) {
                folder = transform(folderEntity);
                folders = readFolders(folderEntity.getId());
                files = readFiles(authentication, folderEntity.getId());
            } else {
                throw new IWSException(IWSErrors.ILLEGAL_ACTION, "User attempted to access a Folder that is not publicly available.");
            }
        }

        final FetchFolderResponse response = new FetchFolderResponse();
        folder.setFolders(folders);
        folder.setFiles(files);
        response.setFolder(folder);

        return response;
    }

    private FolderEntity readFolder(final Authentication authentication, final String externalId) {
        final String jql =
                "select f from FolderEntity f, UserGroupEntity u2g " +
                "where f.externalId = :eid" +
                "  and (f.group.groupType.folderType = " + GroupType.FolderType.Public +
                "   or (f.group.id = u2g.group.id and u2g.user.id = :uid))";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("eid", externalId);
        query.setParameter("uid", authentication.getUser().getId());

        final List<FolderEntity> folders = query.getResultList();
        return folders.isEmpty() ? null : folders.get(0);
    }

    private List<Folder> readRootFolders() {
        final String jql =
                "select f from FolderEntity f " +
                "where parentId = " + ROOT_FOLDER_ID +
                "  and f.group.groupType = " + GroupType.FolderType.Public;
        final Query query = entityManager.createQuery(jql);

        return readAndConvertEntities(query);
    }

    private List<Folder> readFolders(final Long folderId) {
        final String jql =
                "select f from FolderEntity f, UserGroupEntity u2g " +
                "where f.externalId = :id";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("id", folderId);

        return readAndConvertEntities(query);
    }

    private List<Folder> readAndConvertEntities(final Query query) {
        final List<FolderEntity> entities = query.getResultList();

        final List<Folder> folders = new ArrayList<>(entities.size());
        for (final FolderEntity entity : entities) {
            folders.add(transform(entity));
        }

        return folders;
    }

    private List<File> readFiles(final Authentication authentication, final Long folderId) {
        final String jql =
                "select f from FileEntity f, UserGroupEntity u2g " +
                "where f.folder.id = :fid" +
                "  and (f.privacy = " + Privacy.PUBLIC +
                "    or (f.privacy = " + Privacy.PROTECTED +
                "      and f.group.id = u2g.group.id" +
                "      and u2g.user.id = :uid)" +
                "    or (f.privacy = " + Privacy.PRIVATE +
                "      and f.user.id = :uid))";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("fid", folderId);
        query.setParameter("uid", authentication.getUser().getId());

        return query.getResultList();
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
