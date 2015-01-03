/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.callers.StorageCaller
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fitnesse.callers;

import net.iaeste.iws.api.Storage;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.FetchFileRequest;
import net.iaeste.iws.api.requests.FileRequest;
import net.iaeste.iws.api.responses.FetchFileResponse;
import net.iaeste.iws.api.responses.FileResponse;
import net.iaeste.iws.client.StorageClient;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * The IWS FitNesse implementation of the API logic. The Class will attempt to
 * invoke the IWS Client module, and wrap all calls with an Exception check that
 * will throw a new {@code StopTestException} if an error occured - this is the
 * expected behaviour for the FitNesse tests.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class StorageCaller implements Storage {

    // The Client handles the IWS for us, we use use it
    private final Storage caller = new StorageClient();

    // =========================================================================
    // Implementation of methods from Storage in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public FileResponse processFile(final AuthenticationToken token, final FileRequest request) {
        try {
            return caller.processFile(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchFileResponse fetchFile(final AuthenticationToken token, final FetchFileRequest request) {
        try {
            return caller.fetchFile(token, request);
        } catch (RuntimeException e) {
            throw new StopTestException(e);
        }
    }
}
