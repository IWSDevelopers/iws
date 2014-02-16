/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.enums.StorageType;
import net.iaeste.iws.api.requests.FetchFileRequest;
import net.iaeste.iws.api.requests.FileRequest;
import net.iaeste.iws.api.responses.FetchFileResponse;
import net.iaeste.iws.api.responses.FileResponse;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class StorageService extends CommonService<AccessDao> {

    private static final Logger log = LoggerFactory.getLogger(StorageService.class);

    public StorageService(final Settings settings, final AccessDao accessDao) {
        super(settings, accessDao);
    }

    public FileResponse processFile(final Authentication authentication, final FileRequest request) {
        final FileResponse response;

        if (request.getDeleteFile()) {
            deleteFile(authentication, request.getFile());
            response = new FileResponse();
        } else {
            final FileEntity entity = processFile(authentication, request.getFile());
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
