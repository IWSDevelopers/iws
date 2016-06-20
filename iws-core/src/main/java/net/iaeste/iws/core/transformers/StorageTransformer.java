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
package net.iaeste.iws.core.transformers;

import static net.iaeste.iws.core.transformers.CommonTransformer.convert;

import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.dtos.Folder;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.FiledataEntity;
import net.iaeste.iws.persistence.entities.FolderEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class StorageTransformer {

    /**
     * Private Constructor, this is a utility class.
     */
    private StorageTransformer() {
    }

    /**
     * Transformation of a List of Folder Entities to a List of Folder DTOs.
     *
     * @param entities Entities to transform
     * @return Transformed DTOs
     */
    public static List<Folder> transformFolders(final List<FolderEntity> entities) {
        final List<Folder> result;

        if (entities.isEmpty()) {
            result = new ArrayList<>(0);
        } else {
            result = new ArrayList<>(entities.size());
            for (final FolderEntity entity : entities) {
                result.add(transform(entity));
            }
        }

        return result;
    }

    /**
     * Transformation of a List of File Entities to a List of File DTOs.
     *
     * @param entities Entities to transform
     * @return Transformed DTOs
     */
    public static List<File> transformFiles(final List<FileEntity> entities) {
        final List<File> result;

        if (entities.isEmpty()) {
            result = new ArrayList<>(0);
        } else {
            result = new ArrayList<>(entities.size());
            for (final FileEntity entity : entities) {
                result.add(transform(entity));
            }
        }

        return result;
    }

    /**
     * Transformation of a File Entity to a File DTO.
     *
     * @param entity Entity to transform
     * @return Transformed DTO
     */
    public static File transform(final FileEntity entity) {
        File result = null;

        if (entity != null) {
            result = new File();

            result.setFileId(entity.getExternalId());
            result.setPrivacy(entity.getPrivacy());
            result.setGroup(CommonTransformer.transform(entity.getGroup()));
            result.setUser(AdministrationTransformer.transform(entity.getUser()));
            if (entity.getFolder() != null) {
                result.setFolderId(entity.getFolder().getExternalId());
            }
            result.setFilename(entity.getFilename());
            result.setFilesize(entity.getFilesize());
            result.setMimetype(entity.getMimetype());
            result.setDescription(entity.getDescription());
            result.setKeywords(entity.getKeywords());
            result.setChecksum(entity.getChecksum());
            result.setModified(convert(entity.getModified()));
            result.setCreated(convert(entity.getCreated()));
        }

        return result;
    }

    /**
     * Transformation of a FileData Entity to a File DTO.
     *
     * @param entity Entity to transform
     * @return Transformed DTO
     */
    public static File transform(final FiledataEntity entity) {
        File result = null;

        if (entity != null) {
            result = new File();

            result.setFileId(entity.getFile().getExternalId());
            result.setPrivacy(entity.getFile().getPrivacy());
            result.setGroup(CommonTransformer.transform(entity.getFile().getGroup()));
            result.setUser(AdministrationTransformer.transform(entity.getFile().getUser()));
            if (entity.getFile().getFolder() != null) {
                result.setFolderId(entity.getFile().getFolder().getExternalId());
            }
            result.setFilename(entity.getFile().getFilename());
            result.setFilesize(entity.getFile().getFilesize());
            result.setFiledata(entity.getFileData());
            result.setMimetype(entity.getFile().getMimetype());
            result.setDescription(entity.getFile().getDescription());
            result.setKeywords(entity.getFile().getKeywords());
            result.setChecksum(entity.getFile().getChecksum());
            result.setModified(convert(entity.getFile().getModified()));
            result.setCreated(convert(entity.getCreated()));
        }

        return result;
    }

    /**
     * Transformation of a File DTO to a File Entity. If the File is located
     * within a Folder, the FolderEntity must be provided.
     *
     * @param file   DTO to transform
     * @param folder Folder Entity, which the File is stored in
     * @return Transformed Entity
     */
    public static FileEntity transform(final File file, final FolderEntity folder) {
        FileEntity entity = null;

        if (file != null) {
            entity = new FileEntity();

            entity.setExternalId(file.getFileId());
            entity.setPrivacy(file.getPrivacy());
            entity.setGroup(CommonTransformer.transform(file.getGroup()));
            entity.setUser(AdministrationTransformer.transform(file.getUser()));
            entity.setFolder(folder);
            entity.setFilename(file.getFilename());
            entity.setFilesize(file.getFilesize());
            entity.setMimetype(file.getMimetype());
            entity.setDescription(file.getDescription());
            entity.setKeywords(file.getKeywords());
            entity.setChecksum(file.getChecksum());
        }

        return entity;
    }

    /**
     * Transformation of a Folder Entity to Folder DTO.
     *
     * @param entity Entity to transform
     * @return Transformed DTO
     */
    public static Folder transform(final FolderEntity entity) {
        Folder result = null;

        if (entity != null) {
            result = new Folder();

            result.setFolderId(entity.getExternalId());
            result.setGroup(CommonTransformer.transform(entity.getGroup()));
            result.setFoldername(entity.getFoldername());
            result.setPrivacy(entity.getPrivacy());
            result.setModified(convert(entity.getModified()));
            result.setCreated(convert(entity.getCreated()));
        }

        return result;
    }

    /**
     * Transformation of a Folder DTO to a Folder Entity.
     *
     * @param folder DTO to transform
     * @return Transformed Entity
     */
    public static FolderEntity transform(final Folder folder) {
        FolderEntity entity = null;

        if (folder != null) {
            entity = new FolderEntity();

            entity.setExternalId(folder.getFolderId());
            entity.setFoldername(folder.getFoldername());
            entity.setGroup(CommonTransformer.transform(folder.getGroup()));
            entity.setPrivacy(folder.getPrivacy());
        }

        return entity;
    }
}
