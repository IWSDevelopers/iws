/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.ExchangeCSVService
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

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.exchange.OfferCSVDownloadRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVUploadRequest;
import net.iaeste.iws.api.responses.exchange.OfferCSVDownloadResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVUploadResponse;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.ViewsDao;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public class ExchangeCSVService {

    private final ExchangeDao dao;
    private final ViewsDao viewsDao;

    public ExchangeCSVService(final ExchangeDao dao, final ViewsDao viewsDao) {
        this.dao = dao;
        this.viewsDao = viewsDao;
    }

    public OfferCSVUploadResponse uploadOffers(final Authentication authentication, final OfferCSVUploadRequest request) {
        throw new IWSException(IWSErrors.NOT_IMPLEMENTED, "Fetch Offers as CSV is not yet implemented, see Trac Ticket #920.");
    }

    public OfferCSVDownloadResponse downloadOffers(final Authentication authentication, final OfferCSVDownloadRequest request) {
        throw new IWSException(IWSErrors.NOT_IMPLEMENTED, "Fetch Offers as CSV is not yet implemented, see Trac Ticket #453.");
    }
}
