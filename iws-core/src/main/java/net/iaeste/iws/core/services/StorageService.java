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
import static net.iaeste.iws.core.util.StorageUtil.calculateChecksum;

import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.requests.FetchFileRequest;
import net.iaeste.iws.api.requests.FileRequest;
import net.iaeste.iws.api.responses.FetchFileResponse;
import net.iaeste.iws.api.responses.FileResponse;
import net.iaeste.iws.common.exceptions.AuthorizationException;
import net.iaeste.iws.core.util.LogUtil;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.StorageDao;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class StorageService {

    private static final Logger log = LoggerFactory.getLogger(StorageService.class);

    private final StorageDao storageDao;
    private final AccessDao accessDao;

    public StorageService(final AccessDao accessDao, final StorageDao storageDao) {
        this.accessDao = accessDao;
        this.storageDao = storageDao;
    }

    public FileResponse processFile(final Authentication authentication, final FileRequest request) {
        final String externalId = request.getFile().getFileId();
        final FileResponse response;
        final FileEntity entity;

        if (externalId == null) {
            entity = transform(request.getFile());
            entity.setChecksum(calculateChecksum(entity.getFiledata()));
            final byte[] data = entity.getFiledata();
            entity.setFilesize(data != null ? data.length : 0);
            entity.setUser(authentication.getUser());
            entity.setGroup(authentication.getGroup());
            storageDao.persist(authentication, entity);
            log.info(LogUtil.formatLogMessage(authentication, "File %s successfully uploaded for the user.", entity.getFilename()));
            response = new FileResponse(transform(entity));
        } else {
            entity = storageDao.findFileByUserAndExternalId(authentication.getUser(), externalId);
            if (entity != null) {
                if (request.getDeleteFile()) {
                    final String filename = entity.getFilename();
                    storageDao.delete(entity);
                    log.info("File %s has been successfully deleted from the IWS.", filename);
                    response = new FileResponse();
                } else {
                    final FileEntity changes = transform(request.getFile());
                    storageDao.persist(authentication, entity, changes);
                    log.info(LogUtil.formatLogMessage(authentication, "File %s successfully updated by the user.", entity.getFilename()));
                    response = new FileResponse(transform(entity));
                }
            } else {
                throw new AuthorizationException("The user is not authorized to process this file.");
            }
        }

        return response;
    }

    public FetchFileResponse fetchFile(final Authentication authentication, final FetchFileRequest request) {
        final FetchFileResponse response;
        final String externalGroupId = request.getGroupId();

        if (externalGroupId == null) {
            final FileEntity entity = storageDao.findFileByUserAndExternalId(authentication.getUser(), request.getFileId());
            response = new FetchFileResponse(transform(entity));
        } else {
            // Check if the user is permitted to fetch files for the group.
            final GroupEntity group = accessDao.findGroupByPermission(authentication.getUser(), externalGroupId, Permission.FETCH_FILE);
            final FileEntity entity = storageDao.findFileByUserGroupAndExternalId(authentication.getUser(), group, request.getFileId());
            response = new FetchFileResponse(transform(entity));
        }

        return response;
    }
}
