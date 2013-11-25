/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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
import net.iaeste.iws.persistence.entities.FileEntity;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
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
            result.setGroup(CommonTransformer.transform(entity.getGroup()));
            result.setUser(AdministrationTransformer.transform(entity.getUser()));
            result.setFilename(entity.getFilename());
            // File Data is omitted here, since we do not wish to overload
            //result.setFiletype(entity.getFiletype());
            result.setFilesize(entity.getFilesize());
            result.setMimetype(entity.getMimetype());
            // ToDo 2013-11-21 by Kim; We need to add a mapping of the Folder!
            result.setDescription(entity.getDescription());
            result.setKeywords(entity.getKeywords());
            // ToDo 2013-11-21 by Kim: We need to verify the Checksum.
            result.setChecksum(entity.getChecksum());
            //result.setAvailability(entity.getAvailability());
            result.setModified(convert(entity.getModified()));
            result.setCreated(convert(entity.getCreated()));
        }

        return result;
    }

    public static FileEntity transform(final File file) {
        FileEntity entity = null;

        if (file != null) {
            entity = new FileEntity();

            entity.setGroup(CommonTransformer.transform(file.getGroup()));
            entity.setUser(AdministrationTransformer.transform(file.getUser()));
            entity.setFilename(file.getFilename());
            // ToDo 2013-11-21 by Kim; We need to properly compress the file
            entity.setFiledata(file.getFiledata());
            entity.setFilesize(file.getFilesize());
            entity.setMimetype(file.getMimetype());
            // ToDo 2013-11-21 by Kim; We need to add a mapping of the Folder!
            entity.setDescription(file.getDescription());
            entity.setKeywords(file.getKeywords());
            // ToDo 2013-11-21 by Kim: We need to generate the Checksum.
            entity.setChecksum(file.getChecksum());
            //entity.setAvailability(file.getAvailability());
        }

        return entity;
    }
}
