/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.Exchange
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.exchange.DeleteOfferRequest;
import net.iaeste.iws.api.requests.exchange.FetchEmployerInformationRequest;
import net.iaeste.iws.api.requests.exchange.FetchGroupsForSharingRequest;
import net.iaeste.iws.api.requests.exchange.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishOfferRequest;
import net.iaeste.iws.api.requests.exchange.OfferTemplateRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.PublishGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.responses.exchange.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOfferTemplateResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishGroupResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishOfferResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.util.Fallible;

import javax.ejb.Remote;

/**
 * ToDo by Kim; Add JavaDoc for all requests.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Remote
public interface Exchange {

    /**
     * Get a list of employer information based on a substring of the employer name. This is basically used to provide
     * an auto-completion function when entering offers. This list only contains the latest entered employer information.
     * So the returned result is selected by distinct names of the employer. This also means, if the employer name
     * is changed, an additional entry is generated.
     *
     * @param token The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request Request object contains only a string representing a substring of the employers name for which
     *                all possible results a re aggregated.
     * @return contains a list of {@link net.iaeste.iws.api.dtos.exchange.EmployerInformation}
     */
    FetchEmployerInformationResponse fetchEmployers(AuthenticationToken token, FetchEmployerInformationRequest request);

    /**
     * Creates or updates an Offer, dependent on the {@code id}. If the id is set, an update is assumed, otherwise
     * a create will be performed.
     *
     * On error the {@link OfferResponse} object contains only error information. No information about the Offer is returned.
     *
     * @param token The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request contains a {@link net.iaeste.iws.api.dtos.exchange.Offer}
     * @return the persisted {@link net.iaeste.iws.api.dtos.exchange.Offer} including the generated ID
     */
    OfferResponse processOffer(AuthenticationToken token, ProcessOfferRequest request);

    /**
     * Performs a deletion of the offer.
     *
     * TODO: this should only be under certain circumstances: only if the offer is in state new. Doesn't matter if it
     * was once shared. But it should never be able to be deleted once a student was nominated.
     *
     * @param token The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request contains a field with the RefNo (will be changed to id #359)
     * @return emtpy {@link OfferResponse} on success
     */
    OfferResponse deleteOffer(AuthenticationToken token, DeleteOfferRequest request);

    FetchOffersResponse fetchOffers(AuthenticationToken token, FetchOffersRequest request);

    Fallible processOfferTemplate(AuthenticationToken token, OfferTemplateRequest request);

    FetchOfferTemplateResponse fetchOfferTemplates(AuthenticationToken token, FetchOfferTemplatesRequest request);

    /**
     * Retrieves a list of all national groups. This is mainly needed in the
     * front-end to display a list of groups (countries).
     *
     * @param token     Authentication information about the user invoking the request
     * @param request   Fetch National Groups Request Object
     * @return Response Object with the current national groups ordered by name
     *                  and error information
     */
    FetchGroupsForSharingResponse fetchGroupsForSharing(AuthenticationToken token, FetchGroupsForSharingRequest request);

    Fallible managePublishGroup(AuthenticationToken token, PublishGroupRequest request);

    FetchPublishGroupResponse fetchPublishGroups(AuthenticationToken token, FetchPublishGroupsRequest request);

    PublishOfferResponse processPublishOffer(AuthenticationToken token, PublishOfferRequest request);

    FetchPublishOfferResponse fetchPublishedOffer(AuthenticationToken token, FetchPublishOfferRequest request);
}
