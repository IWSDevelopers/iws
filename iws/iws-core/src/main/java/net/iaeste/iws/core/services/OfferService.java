/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.OfferService
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

import net.iaeste.iws.api.data.AuthenticationToken;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.OfferRequest;
import net.iaeste.iws.api.requests.OfferTemplateRequest;
import net.iaeste.iws.api.requests.PublishGroupRequest;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.api.responses.OfferTemplateResponse;
import net.iaeste.iws.api.responses.PublishGroupResponse;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.6
 */
public class OfferService {

    public void processOffers(final AuthenticationToken token, final OfferRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public OfferResponse findOffers(final AuthenticationToken token, final OfferRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public void processOfferTemplates(final AuthenticationToken token, final OfferTemplateRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public OfferTemplateResponse findOfferTemplates(final AuthenticationToken token, final OfferTemplateRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public void processPublishGroups(final AuthenticationToken token, final PublishGroupRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public PublishGroupResponse findPublishGroups(final AuthenticationToken token, final PublishGroupRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }
}
