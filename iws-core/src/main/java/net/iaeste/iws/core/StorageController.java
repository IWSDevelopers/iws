/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.StorageController
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core;

import static net.iaeste.iws.core.util.LogUtil.formatLogMessage;

import net.iaeste.iws.api.Storage;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.FetchFileRequest;
import net.iaeste.iws.api.requests.FileRequest;
import net.iaeste.iws.api.responses.FetchFileResponse;
import net.iaeste.iws.api.responses.FileResponse;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.core.services.StorageService;
import net.iaeste.iws.persistence.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class StorageController extends CommonController implements Storage {

    private static final Logger log = LoggerFactory.getLogger(StorageController.class);

    /**
     * Default Constructor, takes a ServiceFactory as input parameter, and uses
     * this in all the request methods, to get a new Service instance.
     *
     * @param factory The ServiceFactory
     */
    public StorageController(final ServiceFactory factory) {
        super(factory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileResponse processFile(final AuthenticationToken token, final FileRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting processFile()"));
        }
        FileResponse response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.PROCESS_FILE);
            verify(request);

            final StorageService service = factory.prepareStorageService();
            response = service.processFile(authentication, request);
        } catch (IWSException e) {
            response = new FileResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished processFile()"));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchFileResponse fetchFile(final AuthenticationToken token, final FetchFileRequest request) {
        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Starting fetchFile()"));
        }
        FetchFileResponse response;

        try {
            // A user may always fetch their own files, so we make the
            // permission check in the Service class, if the GroupId is defined
            // in the Request. Otherwise, it is assumed that the is private and
            // treated thus.
            final Authentication authentication = verifyPrivateAccess(token);
            verify(request);

            final StorageService service = factory.prepareStorageService();
            response = service.fetchFile(authentication, request);
        } catch (IWSException e) {
            response = new FetchFileResponse(e.getError(), e.getMessage());
        }

        if (log.isTraceEnabled()) {
            log.trace(formatLogMessage(token, "Finished fetchFile()"));
        }
        return response;
    }
}
