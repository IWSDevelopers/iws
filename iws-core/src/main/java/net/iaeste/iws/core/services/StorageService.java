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
import net.iaeste.iws.core.exceptions.UnsupportedOperationException;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.FolderEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.IWSEntity;
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
                final FolderEntity foundRootEntity = findRootFolder(request.getFolder().getGroup());

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
                final FolderEntity existingFolder = findFolder(folderExternalId);
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
            final FolderEntity foundFolder = findFolder(request.getFolder().getFolderId());
            if (foundFolder == null) {
                throw new StorageException("The requested folder with Id '" + folderExternalId + "' could not be found.");
            }
            if (!Objects.equals(foundFolder.getGroup(), authentication.getGroup())) {
                throw new StorageException("Cannot delete a Folder belonging to someone else.");
            }
            // Now, let's see if it contains data. Only empty folders can be
            // deleted from the system
            final List<FolderEntity> subfolders = readSubFolders(foundFolder);
            final List<FileEntity> files = findFiles(foundFolder);
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

    public FetchFolderResponse fetchFolder(final Authentication authentication, final FetchFolderRequest request) {
        final String folderId = request.getFolderId();
        final FolderEntity folderEntity;

        // First, fetch the list of UserGroup relations that we have for the
        // user. It is needed to iterate over. Unfortunately, we need to do
        // this either by sorting the lists according to Groups, or simply
        // using double iterations. Sorting will be costly for very small
        // sets of data. But the double iteration should be less problematic
        // regardlessly.
        final List<UserGroupEntity> u2gList = dao.findAllUserGroups(authentication.getUser());

        if (folderId == null) {
            log.info(formatLogMessage(authentication, "Reading the Root Folder."));
            // Fetch the root folder
            folderEntity = findFolder(ROOT_FOLDER_EID);
        } else {
            log.info(formatLogMessage(authentication, "Reading the Folder with Id {}.", folderId));
            // Fetch the content of the desired folder, including the files.
            folderEntity = findFolder(folderId);

            if (folderEntity == null) {
                throw new IWSException(IWSErrors.ILLEGAL_ACTION, "User attempted to access a Folder that doesn't exist.");
            } else if (folderEntity.getGroup().getGroupType().getFolderType() == GroupType.FolderType.Private) {
                FolderEntity allowedEntity = null;
                final long fid = folderEntity.getGroup().getId();
                for (final UserGroupEntity entity : u2gList) {
                    if (fid == entity.getGroup().getId()) {
                        allowedEntity = folderEntity;
                    }
                }
                if (allowedEntity == null) {
                    throw new IWSException(IWSErrors.ILLEGAL_ACTION, "User attempted to gain access a Folder without permissions.");
                }
            } else if (folderEntity.getGroup().getGroupType().getFolderType() != GroupType.FolderType.None) {
                throw new IWSException(IWSErrors.ILLEGAL_ACTION, "User attempted to gain access an illegal Folder.");
            }
            // We're only having Public folders left :-)
        }

        // Prepare the Folder to be returned with subfolders
        final Folder folder = transform(folderEntity);
        folder.setFolders(readFolders(u2gList, folderEntity));
        folder.setFiles(readFiles(authentication, u2gList, folderEntity));
        // For now, we're forcing an empty result, since the complexity of the
        // query requires that we add a View that can handle it properly
        folder.setFiles(new ArrayList<File>(0));
        // Now set the result
        final FetchFolderResponse response = new FetchFolderResponse();
        response.setFolder(folder);

        return response;
    }

    public FileResponse processFile(final Authentication authentication, final FileRequest request) {
        final FileResponse response;
        // TODO Current processing needs to be rethought, since the Library will allow users who have protected access to a file that is marked public or protected, to also make changes.

        if (request.getAction() == Action.Delete) {
            deleteFile(authentication, request.getFile(), request.getType());
            response = new FileResponse();
        } else {
            final String folderId = request.getFile().getFolderId();
            final FolderEntity folderEntity;

            if (folderId == null) {
                folderEntity = null;
            } else {
                folderEntity = findFolder(folderId);
            }

            final FileEntity entity = processFile(authentication, request.getFile(), folderEntity);
            final File file = transform(entity);
            response = new FileResponse(file);
        }

        return response;
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
            FileEntity toCheck = readFile(request.getFileId());
            final List<UserGroupEntity> u2gList = dao.findAllUserGroups(authentication.getUser());
            entity = checkFile(authentication, toCheck, u2gList);

            // Just to ensure that the data is read out during transformation below
            request.setReadFileData(true);
        } else if (type == StorageType.ATTACHED_TO_APPLICATION) {
            entity = dao.findAttachedFile(request.getFileId(), externalGroupId, type);
        } else {
            // Just in case...
            throw new UnsupportedOperationException("This operation is not implemented.");
        }

        file = transform(entity);
        if (request.getReadFileData()) {
            file.setFiledata(readFile(entity));
        }

        return new FetchFileResponse(file);
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    /**
     * Reads out a list of Sub-folders for a given Folder. All folders belonging
     * to the current User can be read, as can all folders which belong Groups
     * with Public folders.
     *
     * @param u2gList User Group Relations
     * @param folder  Parent Folder to find sub folders for
     * @return
     */
    private List<Folder> readFolders(final List<UserGroupEntity> u2gList, final FolderEntity folder) {
        final List<FolderEntity> found = readSubFolders(folder);

        final List<Folder> folders = new ArrayList<>(found.size());
        for (final FolderEntity entity : found) {
            final GroupType.FolderType type = entity.getGroup().getGroupType().getFolderType();
            if (type == GroupType.FolderType.Public) {
                final Folder subFolder = transform(entity);
                subFolder.setParentId(folder.getExternalId());
                folders.add(subFolder);
            } else if (type == GroupType.FolderType.Private) {
                final long gid = entity.getGroup().getId();
                for (final UserGroupEntity u2g : u2gList) {
                    if (gid == u2g.getGroup().getId()) {
                        final Folder subFolder = transform(entity);
                        subFolder.setParentId(folder.getExternalId());
                        folders.add(subFolder);
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
    private List<File> readFiles(final Authentication authentication, final List<UserGroupEntity> u2gList, final FolderEntity parentFolderEntity) {
        final List<FileEntity> found = findFiles(parentFolderEntity);

        // Now the checks. These are very complex as we have to follow a set of
        // rules, to ensure that the data protection and privacy requirements
        // are upheld.
        //   A) If the file is marked PRIVATE, then the current user *must* be
        //      the owner, otherwise the file is not shown
        //   B) If the file is marked PROTECTED, then the user must be a member
        //      of the Group the file belongs to
        //   C) If the file is marked PUBLIC
        final List<File> files = new ArrayList<>(found.size());
        for (final FileEntity entity : found) {
            final FileEntity file = checkFile(authentication, entity, u2gList);
            if (file != null) {
                files.add(transform(file));
            }
        }

        return files;
    }

    /**
     * This method applies the rules regarding Public, Private and Protected to
     * a particular File Entity. The check follows the following Rules:
     * <ul>
     *   <li><b>File is Public</b>
     *     <ul>
     *       <li>The file must belong to a Public Folder, or</li>
     *       <li>The file must belong to a Group the User is member of, or</li>
     *       <li>The file must belong to the current User</li>
     *     </ul>
     *   </li>
     *   <li><b>B</b> File is Protected
     *     <ul>
     *       <li>The file must belong to a Group the User is member of, or</li>
     *       <li>The file must belong to the current User</li>
     *     </ul>
     *   </li>
     *   <li><b>C</b> File is Private
     *     <ul>
     *       <li>The file must belong to the current User</li>
     *     </ul>
     *   </li>
     * </ul>
     *
     * @param authentication User Authentication Information
     * @param entity         Current File Entity to check
     * @param u2gList        User Group Relations
     * @return
     */
    private FileEntity checkFile(final Authentication authentication, final FileEntity entity, final List<UserGroupEntity> u2gList) {
        FileEntity file = null;

        if (entity != null) {
            // Per the documentation for the Data Privacy rules, which is also
            // part of the JavaDoc for this method. We can either follow the
            // documented rules or reverse the Order checks, and thus have
            // clearer rules. We're doing the latter to make our method easier
            // to read and understand.
            if (entity.getUser().getId().equals(authentication.getUser().getId())) {
                // If the current user is also the owner of the file, then there
                // is no need to perform any other checks. Owners can always do
                // whatever they wish with their files.
                file = entity;
            } else if (entity.getPrivacy() != Privacy.PRIVATE) {
                // Private files is for owners only. And as the Owners were
                // handled in the first check above - we can focus on files
                // which have either status Public or Protected.
                //   For all non-private files the rule applies, that if the
                // user is member of the Group, which owns the file, then the
                // file can be viewed.
                for (final UserGroupEntity u2g : u2gList) {
                    if (u2g.getGroup().getId().equals(entity.getGroup().getId())) {
                        file = entity;
                    }
                }

                // If still not allowed, then we're down to the last option,
                // that the file is public and belongs to a public folder.
                if ((entity == null) && (entity.getPrivacy() == Privacy.PUBLIC)) {
                    // First, check that the Parent Folder exists
                    if (entity.getFolder() != null) {
                        // check that the file belongs to a Group with a public Folder.
                        final GroupType.FolderType folderType = entity.getFolder().getGroup().getGroupType().getFolderType();
                        if (folderType == GroupType.FolderType.Public) {
                            file = entity;
                        }
                    }
                }
            }
        }

        return file;
    }

    // =========================================================================
    //  DAO Methods
    // =========================================================================

    private FolderEntity findFolder(final String externalId) {
        // Reading a specific folder is paramount to finding content. However,
        // we need to ensure that the user is allowed to see it. The query below
        // will handle this, by testing that the folder is either public or
        // belongs to a group that the user is a member of.
        final String jql =
                "select f from FolderEntity f " +
                "where f.externalId = :eid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("eid", externalId);

        return getSingleResultWithException(query, "Could not uniquely identify the folder by its Id.");
    }

    private FolderEntity findParentFolder(final String parentId, final GroupEntity group) {
        final String jql =
                "select f from FolderEntity f " +
                        "where f.id = :fid" +
                        "  and f.group.id = :gid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("fid", parentId);
        query.setParameter("gid", group.getId());

        return getSingleResultWithException(query, "Could not uniquely identify the folder by its Id.");
    }

    private FolderEntity findRootFolder(final Group group) {
        final String jql =
                "select f from FolderEntity f " +
                        "where f.group.parentId is null" +
                        "  and f.group.externalId = :egid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("egid", group.getGroupId());

        return getSingleResultWithException(query, "Multiple Root folders exists for Group " + group.getGroupName() + ", only one is allowed.");
    }

    private FolderEntity findSimilarFolder(final String foldername, final Long parentId) {
        final String jql =
                "select f from FolderEntity f " +
                "where lower(f.foldername) = lower(:name)" +
                "  and f.parentId = :pid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("name", foldername.trim());
        query.setParameter("pid", parentId);

        return getSingleResultWithException(query, "Multiple folders exists with name '" + foldername + "', only one is allowed.");
    }

    private List<FolderEntity> readSubFolders(final FolderEntity parentFolder) {
        // Let's fetch all Folders for a given parent, and then we'll iterate
        // over them to see if the user is allowed to see the folder or not
        final String jql =
                "select f from FolderEntity f " +
                        "where f.parentId = :pid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("pid", parentFolder.getId());

        return query.getResultList();
    }

    private List<FileEntity> findFiles(final FolderEntity parentFolder) {
        // First step, read in all files that is present in the folder. Since
        // no data is read out at this point (and yes, we should move to a file
        // view) - we're simply not concerned about the amount. Rather one
        // simple query with more than required data than an extremely complex
        // and error prone query.
        final String jql =
                "select f from FileEntity f " +
                "where f.folder.id = :fid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("fid", parentFolder.getId());
        return query.getResultList();
    }

    private FileEntity readFile(final String externalFileId) {
        final String jql =
                "select f from FileEntity f " +
                "where f.id = :efid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("efid", externalFileId);

        return getSingleResultWithException(query, "Could not uniquely identify the file by its Id.");
    }

    private <T extends IWSEntity> T getSingleResultWithException(final Query query, final String name) {
        final List<T> found = query.getResultList();
        final T entity;

        if (found.isEmpty()) {
            entity = null;
        } else if (found.size() == 1) {
            entity = found.get(0);
        } else {
            throw new StorageException("Could not uniquely identify the " + name + " by its Id.");
        }

        return entity;
    }
}
