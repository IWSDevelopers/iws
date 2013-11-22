/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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

import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.FetchFileRequest;
import net.iaeste.iws.api.requests.FileRequest;
import net.iaeste.iws.api.responses.FetchFileResponse;
import net.iaeste.iws.api.responses.FileResponse;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.StorageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class StorageService {

    private static final Logger log = LoggerFactory.getLogger(StorageService.class);

    private final StorageDao dao;

    public StorageService(final StorageDao dao) {
        this.dao = dao;
    }

    public FileResponse processFile(final Authentication authentication, final FileRequest request) {
        final String externalId = request.getFile().getFileId();

        if (externalId != null) {
            throw new NotImplementedException("Method pending implementation.");
        } else {
            throw new NotImplementedException("Method pending implementation.");
        }
    }

    public FetchFileResponse fetchFile(final Authentication authentication, final FetchFileRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }
}
