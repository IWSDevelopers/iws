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

import static net.iaeste.iws.common.configuration.InternalConstants.ROOT_FOLDER_EID;
import static net.iaeste.iws.common.utils.LogUtil.formatLogMessage;
import static net.iaeste.iws.core.transformers.StorageTransformer.transform;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.dtos.Folder;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Privacy;
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
import net.iaeste.iws.core.transformers.StorageTransformer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.StorageDao;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.FiledataEntity;
import net.iaeste.iws.persistence.entities.FolderEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.exceptions.PersistenceException;
import net.iaeste.iws.persistence.exceptions.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class StorageService extends CommonService<StorageDao> {

    private static final Logger LOG = LoggerFactory.getLogger(StorageService.class);

    /**
     * Default Constructor for the Storage Service.
     *
     * @param settings   IWS Settings
     * @param storageDao Default DAO for
     */
    public StorageService(final Settings settings, final StorageDao storageDao) {
        super(settings, storageDao);
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
        final Action action = request.getAction();
        Folder folder = null;

        if (action == Action.PROCESS) {
            if (folderExternalId == null) {
                folder = processNewFolder(authentication, request);
            } else {
                folder = processExistingFolder(authentication, request, folderExternalId);
            }
        } else if (action == Action.DELETE) {
            deleteFolder(authentication, request, folderExternalId);
        } else {
            throw new IWSException(IWSErrors.ILLEGAL_ACTION, "The requested action " + action + " is not yet supported.");
        }

        return new FolderResponse(folder);
    }

    private Folder processNewFolder(final Authentication authentication, final FolderRequest request) {
        final Folder folder;

        // Check the parentId, if it is null - it means we're about to
        // create a new root folder for a Group. Only one such Group may
        // exist, but as Unique Constraints in databases ignores rows
        // where one of the unique fields is null, we need to make this
        // check additionally.
        final FolderEntity foundRootEntity = dao.findRootFolder(request.getFolder().getGroup());

        if (foundRootEntity != null) {
            if (request.getParentId() == null) {
                // Attempted to work with the existing Root folder, not
                // good. So we're going to throw an Exception...
                throw new StorageException("Cannot process existing Root folder for group " + request.getFolder().getGroup().getGroupName());
            }
            final FolderEntity foundParentEntity = dao.findParentFolder(request.getParentId(), authentication.getGroup());
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
            final FolderEntity foundFolderEntity = dao.findSimilarFolder(request.getFolder().getFoldername(), foundParentEntity.getId());
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

        return folder;
    }

    private Folder processExistingFolder(final Authentication authentication, final FolderRequest request, final String folderExternalId) {
        // Requesting to process an existing Folder, with a given Folder Id
        final FolderEntity existingFolder = dao.findFolders(folderExternalId).get(0);
        if (existingFolder == null) {
            throw new StorageException("The requested folder with Id '" + folderExternalId + "' could not be found.");
        }

        // Now, let's just check that there wasn't any fiddling with the
        // Id's. The simplest check that the Groups given match.
        if (authentication.getGroup().equals(existingFolder.getGroup())) {
            // Now to the final check. It is only possible to rename a
            // folder, nothing else. However, folders should have
            // distinctive names, to avoid confusion
            final FolderEntity similarFolder = dao.findSimilarFolder(request.getFolder().getFoldername(), existingFolder.getParentId());
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

        return transform(existingFolder);
    }

    private void deleteFolder(final Authentication authentication, final FolderRequest request, final String folderExternalId) {
        final FolderEntity foundFolder = dao.findFolders(request.getFolder().getFolderId()).get(0);

        if (foundFolder == null) {
            throw new StorageException("The requested folder with Id '" + folderExternalId + "' could not be found.");
        }

        if (!Objects.equals(foundFolder.getGroup(), authentication.getGroup())) {
            throw new StorageException("Cannot delete a Folder belonging to someone else.");
        }

        // Now, let's see if it contains data. Only empty folders can be
        // deleted from the system
        final List<FolderEntity> subFolders = dao.readSubFolders(foundFolder);
        final List<FileEntity> files = dao.findFiles(foundFolder);

        if (subFolders.isEmpty() && files.isEmpty()) {
            LOG.info(formatLogMessage(authentication, "Deleting folder %s from the system.", foundFolder));
            dao.delete(foundFolder);
        } else {
            throw new StorageException("Cannot delete a folder that contains data.");
        }
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
        final List<FolderEntity> folderEntitiess = dao.readFolders(authentication, folderEntity);
        final List<Folder> folders = StorageTransformer.transformFolders(folderEntitiess);
        folder.setFolders(folders);

        // Read and set the Files belonging to the Folder
        final List<FileEntity> fileEntities = dao.readFiles(authentication, folderEntity);
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
        final List<FolderEntity> folders = dao.findFolders(externalFolderId);

        // Run the pre-checks, to verify that there is no problems and that the
        // User is permitted to read the Folder.
        if (folders.isEmpty()) {
            // Simple error case, the User have requested a not existing Folder.
            throw new IdentificationException("No Folders were found, matching the Id " + externalFolderId + '.');
        }

        // Very strange error case. The last Folder in the Structure should be
        // the Root folder, if not - then it is an error case, since we then
        // cannot traverse the tree. This specific case must then be corrected
        // in the database!
        if (!ROOT_FOLDER_EID.equals(folders.get(folders.size() - 1).getExternalId())) {
            throw new PersistenceException("Error in the Database, the Folder Tree is incorrect.");
        }

        // Now we just have to run the Permission Check for the Folder, this is
        // done if the size of the Folder Tree found is larger than 1, since a
        // List with only a single Element must be the root.
        if (folders.size() > 1) {
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
        //   If the Folder is not Public, then we need to check the user
        // permissions, meaning that we have to verify that the User is a
        // member of the Group, which the Folder belongs to.
        if (isFolderPrivate(folders) && !isUserGroupMember(authentication, folders)) {
            throw new IWSException(IWSErrors.ILLEGAL_ACTION, "Unauthorized attempt at accessing the Folder " + folders.get(0).getExternalId() + '.');
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
                // Our work is done, we found the member - let's move on
                break;
            }
        }

        return groupMember;
    }

    /**
     * Processes a File, meaning creating/updating or deleting a file within the
     * system.
     *
     * @param authentication User Authentication
     * @param request        User Request Object
     * @return Response with the created/update File and error information
     */
    public FileResponse processFile(final Authentication authentication, final FileRequest request) {
        final FileResponse response;

        if (request.getAction() == Action.DELETE) {
            deleteFile(authentication, request);
            response = new FileResponse();
        } else {
            // It is possible to set the Folder via the API request, this
            // allows Users to create new files in an existing folder, or
            // to move files between folders.
            final String folderId = request.getFile().getFolderId();
            FolderEntity folderEntity = null;

            if (folderId != null) {
                folderEntity = dao.findFolders(folderId).get(0);
            }

            final FileEntity entity = processFile(authentication, request.getFile(), folderEntity);
            final File file = transform(entity);
            response = new FileResponse(file);
        }

        return response;
    }

    /**
     * Retrieves a file from the IWS, based on the User Authentication
     * information and the Fetch File Request Object. If the Request has a
     * GroupId set, then it is assumed that the File is an Attachment, and it
     * will be read out via the Attachment table. If no GroupId is set, then it
     * is assumed that the file is to be read from a Folder, and the logic for
     * working with Folders is applied.
     *
     * @param authentication User Authentication Information
     * @param request        User Fetch File Request Object
     * @return Response Object with File
     * @throws IWSException if an error occurred during processing
     */
    public FetchFileResponse fetchFile(final Authentication authentication, final FetchFileRequest request) {
        final String groupId = request.getGroupId();
        final FiledataEntity entity;

        if (groupId != null) {
            // If the GroupId is not null, then it means that the File is
            // attached to another Object, so we'll read out the data via the
            // attachment table.
            entity = dao.findAttachedFile(request.getFileId(), groupId);
        } else {
            // No GroupId, meaning that the File is fetched the "normal" way,
            // i.e. as if it was shared via a Folder. The reading of the File is
            final FiledataEntity toCheck = dao.readFile(request.getFileId());
            if (toCheck != null) {
                final List<UserGroupEntity> u2gList = dao.findAllUserGroups(authentication.getUser());
                entity = checkFile(authentication, toCheck, u2gList);
            } else {
                throw new IdentificationException("No file with the given Id '" + request.getFileId() + "' could be found.");
            }
        }

        final File file = transform(entity);
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
    private static FiledataEntity checkFile(final Authentication authentication, final FiledataEntity entity, final List<UserGroupEntity> u2gList) {
        final FileEntity toCheck = entity.getFile();
        FiledataEntity file = null;

        // Per the documentation for the Data Privacy rules, which is also
        // part of the JavaDoc for this method. We can either follow the
        // documented rules or reverse the Order checks, and thus have
        // clearer rules. We're doing the latter to make our method easier
        // to read and understand.
        if (toCheck.getUser().getId().equals(authentication.getUser().getId())) {
            // If the current user is also the owner of the file, then there
            // is no need to perform any other checks. Owners can always do
            // whatever they wish with their files.
            file = entity;
        } else if (toCheck.getPrivacy() != Privacy.PROTECTED) {
            // Private files is for owners only. And as the Owners were
            // handled in the first check above - we can focus on files
            // which have either status Public or Protected.
            //   For all non-private files the rule applies, that if the
            // user is member of the Group, which owns the file, then the
            // file can be viewed.
            for (final UserGroupEntity u2g : u2gList) {
                if (u2g.getGroup().getId().equals(toCheck.getGroup().getId())) {
                    file = entity;
                    // Our work is done, we found the Group - let's move on
                    break;
                }
            }

            // If still not allowed, then we're down to the last option,
            // that the file is public and belongs to a public folder.
            // However, first we must check that the Parent Folder exists.
            if ((file == null) && (entity.getFile().getPrivacy() == Privacy.PUBLIC) && (toCheck.getFolder() != null)) {
                // check that the file belongs to a Group with a public Folder.
                final GroupType.FolderType folderType = toCheck.getFolder().getGroup().getGroupType().getFolderType();
                if (folderType == GroupType.FolderType.PUBLIC) {
                    file = entity;
                }
            }
        }

        return file;
    }
}
