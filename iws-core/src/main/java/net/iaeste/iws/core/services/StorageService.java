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
import net.iaeste.iws.api.enums.Action;
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
import net.iaeste.iws.persistence.entities.UserGroupEntity;
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
    // The root folder is having the internal Id 3. However, since we're only
    // getting the ExternalId for all other folders, it makes more sense to the
    // existing ExternalId for the Root folder instead of the Id.
    private static final String ROOT_FOLDER_EID = "afec3bc0-296b-4bf2-8a9e-c2d7b74e93a0";

    // The EntityManager is temporarily present here, until the CommonService &
    // StorageService can both be cleaned up. The addition of the Library means
    // that we have two different approaches to dealing with files.
    // So, refactoring awaits once all is finalized and tested.
    //   Note; JPA Prefers Named Queries over Dynamic Queries - since Named
    // Queries can be better Cached. Criteria Queries is a more type-safe way to
    // do things, but it makes the code loads harder to read and understand - so
    // we're disencouraging the usage thereof. Also because modern IDE's like
    // IntelliJ is capable at parsing and reading the Named and Dynamic Queries
    // and thus ensuring that they're not containing mistakes.
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

        if (request.getAction() == Action.Process) {
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
        final FolderEntity folderEntity;

        if (folderId == null) {
            log.info(formatLogMessage(authentication, "Reading the Root Folder."));
            // Fetch the root folder
            folderEntity = readFolder(authentication, ROOT_FOLDER_EID);
        } else {
            log.info(formatLogMessage(authentication, "Reading the Folder with Id {}.", folderId));
            // Fetch the content of the desired folder, including the files.
            folderEntity = readFolder(authentication, folderId);
            if (folderEntity == null) {
                throw new IWSException(IWSErrors.ILLEGAL_ACTION, "User attempted to access a Folder that is not publicly available.");
            }
        }

        // Prepare the Folder to be returned with subfolders
        final Folder folder = transform(folderEntity);
        folder.setFolders(readFolders(authentication, folderEntity));
        folder.setFiles(readFiles(authentication, folderEntity));
        // For now, we're forcing an empty result, since the complexity of the
        // query requires that we add a View that can handle it properly
        folder.setFiles(new ArrayList<File>(0));
        // Now set the result
        final FetchFolderResponse response = new FetchFolderResponse();
        response.setFolder(folder);

        return response;
    }

    private FolderEntity readFolder(final Authentication authentication, final String externalId) {
        // Reading a specific folder is paramount to finding content. However,
        // we need to ensure that the user is allowed to see it. The query below
        // will handle this, by testing that the folder is either public or
        // belongs to a group that the user is a member of.
        final String jql =
                "select f from FolderEntity f, UserGroupEntity u2g " +
                "where f.externalId = :eid" +
                "  and (f.group.groupType.folderType = '" + GroupType.FolderType.Public + '\'' +
                "   or (f.group.id = u2g.group.id and u2g.user.id = :uid))";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("eid", externalId);
        query.setParameter("uid", authentication.getUser().getId());

        final List<FolderEntity> folders = query.getResultList();
        return folders.isEmpty() ? null : folders.get(0);
    }

    private List<Folder> readFolders(final Authentication authentication, final FolderEntity parentFolderEntity) {
        // Let's fetch all Folders for a given parent, and then we'll iterate
        // over them to see if the user is allowed to see the folder or not
        final String jql =
                "select f from FolderEntity f " +
                "where f.parentId = :pid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("pid", parentFolderEntity.getId());
        final List<FolderEntity> found = query.getResultList();

        final List<Folder> folders = new ArrayList<>(found.size());
        for (final FolderEntity entity : found) {
            if (entity.getGroup().getGroupType().getFolderType() == GroupType.FolderType.Public) {
                final Folder folder = transform(entity);
                folder.setParentId(parentFolderEntity.getExternalId());
                folders.add(folder);

            }
        }

        folders.addAll(resolveFolders(authentication, found, parentFolderEntity.getExternalId()));

        return folders;
    }

    private List<Folder> resolveFolders(final Authentication authentication, final List<FolderEntity> found, final String externalParentId) {
        final List<Folder> folders = new ArrayList<>(found.size());

        if (!found.isEmpty()) {
            // First, fetch the list of UserGroup relations that we have for the
            // user. It is needed to iterate over. Unfortunately, we need to do
            // this either by sorting the lists according to Groups, or simply
            // using double iterations. Sorting will be costly for very small
            // sets of data. But the double iteration should be less problematic
            // regardlessly.
            final List<UserGroupEntity> u2gList = dao.findAllUserGroups(authentication.getUser());

            // Now, let's start iterating over the two lists
            for (final FolderEntity entity : found) {
                // I know that Goto labels is a bad idea, but for the sake of
                // clarity, it makes it easier to understand where we're our
                // inner break will go to:
                startUserGroupLoop:
                for (final UserGroupEntity u2g : u2gList) {
                    if (u2g.getGroup().getId().equals(entity.getGroup().getId())) {
                        // Yeah, we found one :-)
                        final Folder folder = transform(entity);
                        folder.setParentId(externalParentId);
                        folders.add(folder);
                        // As we found one - no need to continue the run in the
                        // inner loop. Let's just break out to our label
                        break startUserGroupLoop;
                    }
                }
            }
        }

        return folders;
    }

    /**
     * Reads out the list of Files for a given Folder. The method is making the
     * assumption that the folder is one that the user is allowed to read. I.e.
     * that it is either a public folder (via GroupType) or a folder belonging
     * to a Group, which the user is a member of.
     *
     * @param authentication     User Authentication Information
     * @param parentFolderEntity Folder to read the content of
     * @return List of Files to be shown
     */
    private List<File> readFiles(final Authentication authentication, final FolderEntity parentFolderEntity) {
        // First step, read in all files that is present in the folder. Since
        // no data is read out at this point (and yes, we should move to a file
        // view) - we're simply not concerned about the amount. Rather one
        // simple query with more than required data than an extremely complex
        // and error prone query.
        final String jql =
                "select f from FileEntity f " +
                "where f.folder.id = :fid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("fid", parentFolderEntity.getId());
        final List<FileEntity> found = query.getResultList();

        // Now the checks. These are very complex as we have to follow a set of
        // rules, to ensure that the data protection and privacy requirements
        // are upheld.
        //   A) If the file is marked PRIVATE, then the current user *must* be
        //      the owner, otherwise the file is not shown
        //   B) If the file is marked PROTECTED, then the user must be a member
        //      of the Group the file belongs to
        //   C) If the file is marked PUBLIC
        final List<FileEntity> protectedFiles = new ArrayList<>();
        final List<File> files = new ArrayList<>(found.size());
        for (final FileEntity entity : found) {
            final Privacy privacy = entity.getPrivacy();
            if (privacy == Privacy.PUBLIC) {
                // Public file - add
                files.add(transform(entity));
            } else if (privacy == Privacy.PROTECTED) {
                // Protected files is being dealt with separately. If no such
                // files exists in the folder - no need to handle them, and thus
                // we can save a DB lookup. Otherwise, we'll deal with them
                // later.
                protectedFiles.add(entity);
            } else if (entity.getUser().getId().equals(authentication.getUser().getId())) {
                // We're down to the Private file, so only relevant information
                // is to compare the Owners. If they don't match, we don't add
                files.add(transform(entity));
            }
        }

        // Now, let's resolve the files and simply append the result
        files.addAll(resolveProtectedFiles(authentication, protectedFiles));

        // Done
        return files;
    }

    private List<File> resolveProtectedFiles(final Authentication authentication, final List<FileEntity> found) {
        final List<File> files = new ArrayList<>(found.size());

        if (!found.isEmpty()) {
            // First, fetch the list of UserGroup relations that we have for the
            // user. It is needed to iterate over. Unfortunately, we need to do
            // this either by sorting the lists according to Groups, or simply
            // using double iterations. Sorting will be costly for very small
            // sets of data. But the double iteration should be less problematic
            // regardlessly.
            final List<UserGroupEntity> u2gList = dao.findAllUserGroups(authentication.getUser());

            // Now, let's start iterating over the two lists
            for (final FileEntity entity : found) {
                // I know that Goto labels is a bad idea, but for the sake of
                // clarity, it makes it easier to understand where we're our
                // inner break will go to:
                startUserGroupLoop:
                for (final UserGroupEntity u2g : u2gList) {
                    if (u2g.getGroup().getId().equals(entity.getGroup().getId())) {
                        // Yeah, we found one :-)
                        files.add(transform(entity));
                        // As we found one - no need to continue the run in the
                        // inner loop. Let's just break out to our label
                        break startUserGroupLoop;
                    }
                }
            }
        }

        return files;
    }

    public FileResponse processFile(final Authentication authentication, final FileRequest request) {
        final FileResponse response;

        if (request.getAction() == Action.Delete) {
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
        } else if (type == StorageType.FOLDER) {
            entity = readFile(authentication, request.getFileId());
            // Just to ensure that the data is read out during transformation below
            request.setReadFileData(true);
        } else {
            entity = dao.findAttachedFile(request.getFileId(), externalGroupId, type);
        }

        file = transform(entity);
        if (request.getReadFileData()) {
            file.setFiledata(readFile(entity));
        }

        return new FetchFileResponse(file);
    }

    private FileEntity readFile(final Authentication authentication, final String externalFileId) {
        final String jql =
                "select f from FileEntity f " +
                "where f.id = :efid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("efid", externalFileId);

        final List<FileEntity> found = query.getResultList();
        final FileEntity entity;

        if (found.size() == 1) {
            entity = found.get(0);
        } else if (found.isEmpty()) {
            entity = null;
        } else {
            log.error(formatLogMessage(authentication, "Multiple files with the same Id '%s' was found.", externalFileId));
            entity = null;
        }

        return entity;
    }
}
