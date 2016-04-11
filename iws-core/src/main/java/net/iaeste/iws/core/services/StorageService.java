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
import net.iaeste.iws.api.util.Verifications;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.exceptions.StorageException;
import net.iaeste.iws.core.exceptions.UnsupportedOperationException;
import net.iaeste.iws.core.transformers.StorageTransformer;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.FolderEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.IWSEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.exceptions.PersistenceException;
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

    private static final Logger LOG = LoggerFactory.getLogger(StorageService.class);

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
    // we're discouraging the usage thereof. Also because modern IDEs like
    // IntelliJ is capable at parsing and reading the Named and Dynamic Queries
    // and thus ensuring that they're not containing mistakes.
    private final EntityManager entityManager;

    public StorageService(final Settings settings, final AccessDao accessDao, final EntityManager entityManager) {
        super(settings, accessDao);
        this.entityManager = entityManager;
    }

    /**
     * It is possible to create/update and delete folders, provided that a few
     * criteria's have been met.<br />
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
     * @param request        User Processing Request Object
     * @return Processing response, folder is null if deleted
     */
    public FolderResponse processFolder(final Authentication authentication, final FolderRequest request) {
        final String folderExternalId = request.getFolder().getFolderId();
        final Folder folder;

        if (request.getAction() == Action.PROCESS) {
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
                        throw new StorageException("Cannot create new sub Folder for illegal Parent");
                    }

                    // Processing a new sub Folder. Let's just make sure that
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
                final FolderEntity existingFolder = findFolders(folderExternalId).get(0);
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
            final FolderEntity foundFolder = findFolders(request.getFolder().getFolderId()).get(0);
            if (foundFolder == null) {
                throw new StorageException("The requested folder with Id '" + folderExternalId + "' could not be found.");
            }
            if (!Objects.equals(foundFolder.getGroup(), authentication.getGroup())) {
                throw new StorageException("Cannot delete a Folder belonging to someone else.");
            }
            // Now, let's see if it contains data. Only empty folders can be
            // deleted from the system
            final List<FolderEntity> subFolders = readSubFolders(foundFolder);
            final List<FileEntity> files = findFiles(foundFolder);
            if (subFolders.isEmpty() && files.isEmpty()) {
                LOG.info(formatLogMessage(authentication, "Deleting folder %s from the system.", foundFolder));
                dao.delete(foundFolder);
                folder = null;
            } else {
                throw new StorageException("Cannot delete a folder that contains data.");
            }
        }

        return new FolderResponse(folder);
    }

    /**
     * This method will retrieve the information
     *
     * @param authentication User Authentication Object
     * @param request        User Processing Request Object
     * @return Fetching Response, with sub Folders & Files
     */
    public FetchFolderResponse fetchFolder(final Authentication authentication, final FetchFolderRequest request) {
        // First, let's see if we can find the Folder, with Permission checks
        final FolderEntity folderEntity = retrieveFolderWithPermissionChecks(authentication, request.getFolderId());

        // Next, prepare the Response Object, which will contain the Folder
        // and additionally all sub Folders & Files belonging to it.
        final FetchFolderResponse response = new FetchFolderResponse();
        final Folder folder = transform(folderEntity);
        response.setFolder(folder);

        // Read and set the sub Folders
        final List<FolderEntity> folderEntitiess = readFolders(authentication, folderEntity);
        final List<Folder> folders = StorageTransformer.transformFolders(folderEntitiess);
        folder.setFolders(folders);

        // Read and set the Files belonging to the Folder
        final List<FileEntity> fileEntities = readFiles(authentication, folderEntity);
        final List<File> files = StorageTransformer.transformFiles(fileEntities);
        folder.setFiles(files);

        // That's it - we're done
        return response;
    }

    private FolderEntity retrieveFolderWithPermissionChecks(final Authentication authentication, final String externalFolderId) {
        // Next step, we're retrieving the Folder hierarchy from the Database,
        // based on the Folder Id from the Request, or if none were set in the
        // Request, we're just using the Root folder Id
        final String folderId = (externalFolderId == null) ? ROOT_FOLDER_EID : externalFolderId;
        LOG.debug(formatLogMessage(authentication, "Reading the Folder with Id {}.", folderId));

        // Before continuing, we have to verify that the User is permitted to
        // view the Folder in question, if not permitted then we're expecting
        // an Exception, otherwise the requested Folder.
        return findAndVerifyFolderPermissions(authentication, folderId);
    }

    private FolderEntity findAndVerifyFolderPermissions(final Authentication authentication, final String externalFolderId) {
        final List<FolderEntity> folders = findFolders(externalFolderId);

        // Run the pre-checks, to verify that there is no problems and that the
        // User is permitted to read the Folder.
        if (folders.isEmpty()) {
            // Simple error case, the User have requested a not existing Folder.
            throw new IdentificationException("No Folders were found, matching the Id " + externalFolderId + '.');
        } else if (!ROOT_FOLDER_EID.equals(folders.get(folders.size() - 1).getExternalId())) {
            // Very strange error case. The last Folder in the Structure should
            // be the Root folder, if not - then it is an error case, since we
            // then cannot traverse the tree. This specific case must then be
            // corrected in the database!
            throw new PersistenceException("Error in the Database, the Folder Tree is incorrect.");
        } else if (folders.size() > 1) {
            // Now we just have to run the Permission Check for the Folder, this
            // is done if the size of the Folder Tree found is larger than 1,
            // since a List with only a single Element must be the root.
            throwIfNotPermittedToAccessFolder(authentication, folders);
        }

        // That was it, we have completed the checks - now we can return the
        // first element from the List, since this is the Requested Folder.
        return folders.get(0);
    }

    private void throwIfNotPermittedToAccessFolder(final Authentication authentication, final List<FolderEntity> folders) {
        // Database calls are costly, iterating over internal things is cheap
        // in comparison. So we start by checking if the Folder is Public
        // and that all Folders in the tree are also Public. If that is the case,
        // then we don't need to make any further checks.
        if (isFolderPrivate(folders)) {
            // If the Folder is not Public, then we need to check the user
            // permissions, meaning that we have to verify that the User is a
            // member of the Group, which the Folder belongs to.
            if (!isUserGroupMember(authentication, folders)) {
                throw new IWSException(IWSErrors.ILLEGAL_ACTION, "Unauthorized attempt at accessing the Folder " + folders.get(0).getExternalId() + '.');
            }
        }
    }

    private static boolean isFolderPrivate(final List<FolderEntity> folders) {
        boolean isGroupPublic = true;

        for (final FolderEntity folder : folders) {
            // The Root folder is Public, and all sub Folders must also be
            // Public, otherwise a different check must be made.
            if (folder.getPrivacy() != Privacy.PUBLIC) {
                isGroupPublic = false;
            }
        }

        return !isGroupPublic;
    }

    private boolean isUserGroupMember(final Authentication authentication, final List<FolderEntity> folders) {
        // Now we need the Group of the folder, so we can compare if the User a
        // member of the Group, and thus we can omit further checks
        final GroupEntity folderGroup = folders.get(0).getGroup();

        boolean groupMember = false;
        // Now, iterate over all the UserGroup relations that the User has, to
        // ensure that the User is permitted to access the Files.
        for (final UserGroupEntity userGroup : dao.findAllUserGroups(authentication.getUser())) {
            if (userGroup.getGroup().getId().equals(folderGroup.getId())) {
                groupMember = true;
            }
        }

        return groupMember;
    }

    public FileResponse processFile(final Authentication authentication, final FileRequest request) {
        final FileResponse response;
        // TODO Current processing needs to be rethought, since the Library will allow users who have protected access to a file that is marked public or protected, to also make changes.

        if (request.getAction() == Action.DELETE) {
            deleteFile(authentication, request.getFile(), request.getType());
            response = new FileResponse();
        } else {
            final String folderId = request.getFile().getFolderId();
            FolderEntity folderEntity = null;

            if (folderId != null) {
                folderEntity = findFolders(folderId).get(0);
            }

            final FileEntity entity = processFile(authentication, request.getFile(), folderEntity);
            final File file = transform(entity);
            response = new FileResponse(file);
        }

        return response;
    }

    public FetchFileResponse fetchFile(final Authentication authentication, final FetchFileRequest request) {
        final String externalGroupId = request.getGroupId();
        final FileEntity entity;
        final File file;

        switch (request.getType()) {
            case OWNER:
                if (externalGroupId == null) {
                    entity = dao.findFileByUserAndExternalId(authentication.getUser(), request.getFileId());
                } else {
                    // Check if the user is permitted to fetch files for the group, if
                    // not then the method will thrown an Exception
                    final GroupEntity group = dao.findGroupByPermission(authentication.getUser(), externalGroupId, Permission.FETCH_FILE);

                    // Read the allowed file
                    entity = dao.findFileByUserGroupAndExternalId(authentication.getUser(), group, request.getFileId());
                }
                break;
            case FOLDER:
                final FileEntity toCheck = readFile(request.getFileId());
                final List<UserGroupEntity> u2gList = dao.findAllUserGroups(authentication.getUser());
                entity = checkFile(authentication, toCheck, u2gList);

                // Just to ensure that the data is read out during transformation below
                request.setReadFileData(true);
                break;
            case ATTACHED_TO_APPLICATION:
                entity = dao.findAttachedFile(request.getFileId(), externalGroupId, StorageType.ATTACHED_TO_APPLICATION);
                break;
            default:
                // Just in case...
                throw new UnsupportedOperationException("This operation is not implemented.");
        }

        file = transform(entity);
        if (request.getReadFileData()) {
            file.setFiledata(readFile(entity));
        }

        return new FetchFileResponse(file);
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
     * @return Found file or null
     */
    private static FileEntity checkFile(final Authentication authentication, final FileEntity entity, final List<UserGroupEntity> u2gList) {
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
                if ((file == null) && (entity.getPrivacy() == Privacy.PUBLIC)) {
                    // First, check that the Parent Folder exists
                    if (entity.getFolder() != null) {
                        // check that the file belongs to a Group with a public Folder.
                        final GroupType.FolderType folderType = entity.getFolder().getGroup().getGroupType().getFolderType();
                        if (folderType == GroupType.FolderType.PUBLIC) {
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

    private List<FolderEntity> findFolders(final String eid) {
        final List<FolderEntity> folders;

        if (ROOT_FOLDER_EID.equals(eid)) {
            folders = new ArrayList<>(1);
            folders.add(findRootFolder());
        } else {
            folders = findFoldersRecursively(eid);
        }

        return folders;
    }

    /**
     * Reads out a list of Sub-folders for a given Folder. All folders belonging
     * to the current User can be read, as can all folders which belong Groups
     * with Public folders.
     *
     * @param authentication User Authentication information
     * @param folder  Parent Folder to find sub folders for
     * @return Folder List
     */
    private List<FolderEntity> readFolders(final Authentication authentication, final FolderEntity folder) {
        final String jql =
                "select f FROM FolderEntity f " +
                "where f.parentId = :pid" +
                "  and (" +
                "    (f.group.groupType.folderType = :type" +
                "      and f.privacy = :privacy)" +
                "    or (f.group.id in (" +
                "      select u2g.group.id" +
                "      from UserGroupEntity u2g" +
                "      where u2g.user.id = :uid)))";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("type", GroupType.FolderType.PUBLIC);
        query.setParameter("privacy", Privacy.PUBLIC);
        query.setParameter("uid", authentication.getUser().getId());
        query.setParameter("pid", folder.getId());

        return query.getResultList();
    }

    /**
     * Reads out the list of Files for a given Folder. The method is making the
     * assumption that the folder is one that the user is allowed to read. I.e.
     * that it is either a public folder (via GroupType) or a folder belonging
     * to a Group, which the user is a member of.
     *
     * @param authentication User Authentication Information
     * @param folder         Folder to read the content of
     * @return List of Files to be shown
     */
    private List<FileEntity> readFiles(final Authentication authentication, final FolderEntity folder) {
        final String jql =
                "select f FROM FileEntity f " +
                "where f.folder.id = :fid" +
                "  and ((f.group.groupType.folderType = '" + GroupType.FolderType.PUBLIC + '\'' +
                "    and f.privacy = '" + Privacy.PUBLIC + "')" +
                "  or (f.group.id in (" +
                "      select u2g.group.id" +
                "      from UserGroupEntity u2g" +
                "      where u2g.user.id = :uid)))";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("uid", authentication.getUser().getId());
        query.setParameter("fid", folder.getId());

        return query.getResultList();
    }

    private FolderEntity findRootFolder() {
        final String jql =
                "select f from FolderEntity f " +
                "where f.externalId = :eid";
        final Query query = entityManager.createQuery(jql);
        query.setParameter("eid", ROOT_FOLDER_EID);

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

    /**
     * The IWS Folder Structure is based on the Adjacency Tree Model [1]. This
     * is a fairly simple construction, where each node is having a reference
     * to a parent.<br />
     *   Problem here, is to find all the nodes from a branch and down to the
     * root, so we can verify the permissions. To do so, we need to recursively
     * lookup the Tree using the given Id. Doing a recursive lookup by iterating
     * over the elements is not a good idea, it is very performance costly, so
     * instead a solution with a minimal amount of Database Activity is
     * required.<br />
     *   SQL:1999 is having a nice solution ready called Common Table Expression
     * or CTE [2]. This is supported by the majority of DBMS, but is not mapped
     * into JPA! So, rather than using JPA to have an elegant solution, we must
     * instead use a native Query. Only problem with using a Native Query is
     * that we cannot utilize the JPA based prepared statement, instead we have
     * to revert to injecting the Id directly into the Query :-(<br />
     *   Luckily we're saved by the fact that all input validation is in place
     * and the given Id must be valid - this way we can safely run the Query,
     * but with the hope that next generation JPA will have support for Common
     * Table Expressions. It should be noted that some JPA tricks exist [3], but
     * they do not seem to work with our data model.<br />
     *   Note; that although PostgreSQL have had support for CTE for quite a
     * while, HyperSQL is a different story [4].
     *   For more information, please see the following list of Links:
     * <ul>
     *   <li>[1] <a href="http://kawoolutions.com/SQL_Database_Design/15._Advanced_Data_Structures/15.1_Trees">SQL Tree Structures</a></li>
     *   <li>[2] <a href="https://en.wikipedia.org/wiki/Hierarchical_and_recursive_queries_in_SQL#Common_table_expression">CTE Queries</a></li>
     *   <li>[3] <a href="http://www.tikalk.com/java/load-tree-jpa-and-hibernate/">Potential JPA solution</a></li>
     *   <li>[4] <a href="http://sourceforge.net/p/hsqldb/discussion/73674/thread/33731466/">CTE in HyperSQL</a></li>
     * </ul>
     *
     * @param externalId External Id of the Folder to find the structure for
     * @return Sorted list of Folders - first matches given Id and last is the root
     */
    private List<FolderEntity> findFoldersRecursively(final String externalId) {
        // Following JPA Query will fetch the Folders for the Id's, which we
        // can find via the Native Query call. We're doing it this way, with two
        // selects to minimize the complexity of the logic here.
        final List<Long> ids = findFolderIds(externalId);
        final List<FolderEntity> folders;

        if (ids.isEmpty()) {
            folders = new ArrayList<>(0);
        } else {
            final String jql =
                    "select f " +
                    "from FolderEntity f " +
                    "where f.id in :ids " +
                    "order by f.id desc";
            final Query query = entityManager.createQuery(jql);
            query.setParameter("ids", ids);

            folders = query.getResultList();
        }

        return folders;
    }

    /**
     * This method will use a Native SQL Query, based on the SQL:1999 Common
     * Table Expression, CTE, functionality, which is implemented in most
     * Databases, but is not mapped over to JPA.
     *
     * @param externalId External Id of the Folder to find the structure for
     * @return Sorted list of Folders - first matches given Id and last is the root
     */
    private List<Long> findFolderIds(final String externalId) {
        // Despite the fact that the Id's should be verified before we're
        // coming so deep in, there may be other ways to invoke this
        // functionality. So we're enforcing a check on the Id before using
        // it in the Native Query.
        Verifications.ensureValidId("Folder Id", externalId);

        // Native Query to retrieve the Id's for the Tree starting with the
        // deepest (requested Id) and going up to the root. Query utilizes
        // the SQL:1999 Common Table Expression to achieve a Recursive lookup.
        final String nativeSQL =
                "with recursive tree (id, parent_id) as (\n" +
                "    select\n" +
                "      f1.id,\n" +
                "      f1.parent_id\n" +
                "    from folders f1\n" +
                "    where f1.external_id = '" + externalId + "'\n" +
                "  union\n" +
                "    select\n" +
                "      f2.id,\n" +
                "      f2.parent_id\n" +
                "    from folders f2\n" +
                "    inner join tree t on f2.id = t.parent_id)\n" +
                "select id from tree " +
                "order by id desc";
        final Query nativeQuery = entityManager.createNativeQuery(nativeSQL);

        // The Native Query is returning a list of Integers, but the JPA Query
        // requires a list of Longs. So we have to convert the list.
        return toLong(nativeQuery.getResultList());
    }

    private static <T extends IWSEntity> T getSingleResultWithException(final Query query, final String name) {
        final List<T> found = query.getResultList();
        T entity = null;

        if (found.size() == 1) {
            entity = found.get(0);
        } else if (found.size() > 1){
            throw new StorageException("Could not uniquely identify the " + name + " by its Id.");
        }

        return entity;
    }

    private static List<Long> toLong(final List<Integer> integers) {
        final List<Long> longs = new ArrayList<>(integers.size());

        for (final Integer id : integers) {
            longs.add(id.longValue());
        }

        return longs;
    }
}
