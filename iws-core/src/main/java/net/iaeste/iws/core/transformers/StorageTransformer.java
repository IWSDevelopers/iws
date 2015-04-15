/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.StorageTransformer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.transformers;

import static net.iaeste.iws.core.transformers.CommonTransformer.convert;

import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.dtos.Folder;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.FolderEntity;

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

    public static Folder transform(FolderEntity entity) {
        Folder result = null;

        if (entity != null) {
            result = new Folder();

            result.setFolderId(entity.getExternalId());
            result.setGroup(CommonTransformer.transform(entity.getGroup()));
            result.setFoldername(entity.getFoldername());
            result.setModified(convert(entity.getModified()));
            result.setCreated(convert(entity.getCreated()));
        }

        return result;
    }

    public static FolderEntity transform(Folder folder) {
        FolderEntity entity = null;

        if (folder != null) {
            entity = new FolderEntity();

            entity.setExternalId(folder.getFolderId());
            entity.setFoldername(folder.getFoldername());
            entity.setGroup(CommonTransformer.transform(folder.getGroup()));
        }

        return entity;
    }
}
