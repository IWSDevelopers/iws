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
import net.iaeste.iws.api.requests.exchange.FetchEmployerRequest;
import net.iaeste.iws.api.requests.exchange.FetchGroupsForSharingRequest;
import net.iaeste.iws.api.requests.exchange.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.exchange.FetchPublishedGroupsRequest;
import net.iaeste.iws.api.requests.exchange.OfferTemplateRequest;
import net.iaeste.iws.api.requests.exchange.ProcessEmployerRequest;
import net.iaeste.iws.api.requests.exchange.ProcessOfferRequest;
import net.iaeste.iws.api.requests.exchange.PublishGroupRequest;
import net.iaeste.iws.api.requests.exchange.PublishOfferRequest;
import net.iaeste.iws.api.responses.exchange.EmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.exchange.FetchEmployerResponse;
import net.iaeste.iws.api.responses.exchange.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.exchange.FetchOfferTemplateResponse;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishGroupResponse;
import net.iaeste.iws.api.responses.exchange.FetchPublishedGroupsResponse;
import net.iaeste.iws.api.responses.exchange.OfferResponse;
import net.iaeste.iws.api.responses.exchange.PublishOfferResponse;
import net.iaeste.iws.api.util.Fallible;

import javax.ejb.Remote;

/**
 * Exchange related functionality is covered with this interface. Only exception
 * here, the handling of students is done vai the {@link Student} interface.
 */
@Remote
public interface Exchange {

    /**
     * Processes a given Employer, meaning that if it exists, then it us
     * updated, otherwise it is being created.
     *
     * @param token   User Authentication Token
     * @param request Request Object, with the Employer
     * @return Persisted Employer Object
     */
    EmployerResponse processEmployer(AuthenticationToken token, ProcessEmployerRequest request);

    /**
     * Get a list of employer information based on a substring of the employer
     * name. This is basically used to provide an auto-completion function when
     * entering offers. This list only contains the latest entered employer
     * information. So the returned result is selected by distinct names of the
     * employer. This also means, if the employer name is changed, an additional
     * entry is generated.
     *
     * @param token   The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request Request object contains only a string representing a substring of the employers name for which
     *                all possible results a re aggregated.
     * @return contains a list of {@link net.iaeste.iws.api.dtos.exchange.EmployerInformation}
     * @deprecated please use the other fetchEmployers method
     */
    @Deprecated
    FetchEmployerInformationResponse fetchEmployers(AuthenticationToken token, FetchEmployerInformationRequest request);

    /**
     * Fetches a list of Employers, belonging to the requesting User, i.e. which
     * is associated with the Users National Group. The request can be made
     * either for a single Object (by providing the Id), for a partial list (by
     * providing a partial name) or for all Employers.
     *
     * @param token   User Authentication Token
     * @param request Employer Request Object
     * @return List of requested Employers
     */
    FetchEmployerResponse fetchEmployers(AuthenticationToken token, FetchEmployerRequest request);

    /**
     * Creates or updates an Offer, dependent on the {@code id}. If the id is set, an update is assumed, otherwise
     * a create will be performed.
     *
     * On error the {@link OfferResponse} object contains only error information. No information about the Offer is returned.
     *
     * @param token The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request contains a {@link net.iaeste.iws.api.dtos.exchange.Offer}
     * @return the persisted {@link net.iaeste.iws.api.dtos.exchange.Offer} including the generated Id
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
     * @return emtpy {@link OfferResponse} (offer=null) on success
     */
    OfferResponse deleteOffer(AuthenticationToken token, DeleteOfferRequest request);

    /**
     * Retrieves a list of offers. This can be either the list of owned offers or offers which are shared with your
     * country.
     *
     * <dl>
     *   <dt>{@link net.iaeste.iws.api.enums.FetchType#ALL}</dt>
     *     <dd>Requests all own/domestic offers.</dd>
     *   <dt>{@link net.iaeste.iws.api.enums.FetchType#SHARED}</dt>
     *     <dd>Means that all shared offers are requested.</dd>
     * </dl>
     *
     * TODO: Rename FetchType.ALL to FetchType.DOMESTIC for classifications
     *
     * @param token The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request contains a {@link net.iaeste.iws.api.enums.FetchType} which indicates which type of offers
     *                should be returned
     * @return contains a list of {@link net.iaeste.iws.api.dtos.exchange.Offer}
     */
    FetchOffersResponse fetchOffers(AuthenticationToken token, FetchOffersRequest request);

    /**
     * Not implemented
     *
     * @param token   User Authentication Token
     * @param request Request Object
     * @return Response Object
     */
    Fallible processOfferTemplate(AuthenticationToken token, OfferTemplateRequest request);

    /**
     * Not implemented
     *
     * @param token   User Authentication Token
     * @param request Request Object
     * @return Response Object
     */
    FetchOfferTemplateResponse fetchOfferTemplates(AuthenticationToken token, FetchOfferTemplatesRequest request);

    /**
     * Retrieves a list of all groups to which an offer can be shared to. The group types of the groups are:
     * <ul>
     *     <li>{@link net.iaeste.iws.api.enums.GroupType#NATIONAL}</li>
     *     <li>{@link net.iaeste.iws.api.enums.GroupType#SAR}</li>
     * </ul>
     *
     * This is mainly needed in the front-end to display a list of groups (members) to which a offer can be shared to.
     *
     * @param token The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request   Fetch National Groups Request Object
     * @return Response Object with the current national groups ordered by name
     *                  and error information
     */
    FetchGroupsForSharingResponse fetchGroupsForSharing(AuthenticationToken token, FetchGroupsForSharingRequest request);

    /**
     * Not implemented
     *
     * @param token   User Authentication Token
     * @param request Request Object
     * @return Response Object
     */
    Fallible processPublishGroup(AuthenticationToken token, PublishGroupRequest request);

    /**
     * Not implemented
     *
     * @param token   User Authentication Token
     * @param request Request Object
     * @return Response Object
     */
    FetchPublishGroupResponse fetchPublishGroups(AuthenticationToken token, FetchPublishGroupsRequest request);

    /**
     * Shares a list of offers to a list of members and defines the nomination deadline for
     * the specified offers. From this very moment he offers are visible to the list of
     * members until the {@code PublishOfferRequest#nominationDeadline} is reached. The
     * {@link net.iaeste.iws.api.dtos.exchange.Offer#nominationDeadline} of each specified offer
     * is updated to the specified {@code PublishOfferRequest#nominationDeadline}
     *
     * The list of offers is identified by the {@link net.iaeste.iws.api.dtos.exchange.Offer#offerId}.
     * The list of members are identified by the {@link net.iaeste.iws.api.dtos.Group#groupId}
     *
     * @param token The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request contains a list of offer, a list of members and a nomination deadline
     * @return contains no data
     */
    PublishOfferResponse processPublishOffer(AuthenticationToken token, PublishOfferRequest request);

    /**
     * Retrieves the list of groups to which offers are shared to. A list of offers is
     * submitted and for each offer the groups are returned, to which the offer is shared to.
     *
     * @param token The valid authentication token provided by {@link Access#generateSession(net.iaeste.iws.api.requests.AuthenticationRequest)}
     * @param request contains a list of {@link net.iaeste.iws.api.dtos.exchange.Offer#offerId}s
     * @return contains a map for each requested offer and the list of {@link net.iaeste.iws.api.dtos.exchange.OfferGroup}
     *  to which the offer is shared to
     */
    FetchPublishedGroupsResponse fetchPublishedGroups(AuthenticationToken token, FetchPublishedGroupsRequest request);
}
