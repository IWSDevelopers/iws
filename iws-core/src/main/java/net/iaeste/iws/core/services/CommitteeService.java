/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.CommitteeService
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

import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.requests.FetchSurveyOfCountryRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.SurveyOfCountryRequest;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.api.responses.FetchSurveyOfCountryRespose;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.core.transformers.AdministrationTransformer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.CommitteeDao;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class CommitteeService {

    private static final Logger log = LoggerFactory.getLogger(CommitteeService.class);

    private final CommitteeDao dao;

    public CommitteeService(final CommitteeDao dao) {
        this.dao = dao;
    }

    /**
     * This method will fetch a list of Committees, or National Groups, based on
     * the given parameters in the Request Object. The Request has two mutually
     * exclusive parameters, CountryIds and Membership, meaning that only one of
     * them may be set. If neither is set - we're simply assuming that all
     * Committees matching the required Statuses.<br />
     *   Additionally, the result can be limited by the status values, where it
     * is possible to request either Active Committees. Suspended Committees or
     * both.
     *
     * @param request Request Object
     * @return Response Object with the Committees matching the request
     */
    public FetchCommitteeResponse fetchCommittees(final FetchCommitteeRequest request) {
        final List<String> countryIds = request.getCountryIds();
        final Membership membership = request.getMembership();
        final Set<GroupStatus> statuses = request.getStatuses();

        final List<UserGroupEntity> found;
        if (countryIds != null) {
            found = dao.findCommitteesByContryIds(countryIds, statuses);
        } else if (membership != null) {
            found = dao.findCommitteesByMembership(membership, statuses);
        } else {
            found = dao.findAllCommittees(statuses);
        }

        return new FetchCommitteeResponse(AdministrationTransformer.transformMembers(found));
    }

    public Fallible processCommittee(final Authentication authentication, final CommitteeRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public Fallible manageInternationalGroup(final Authentication authentication, final InternationalGroupRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public FetchSurveyOfCountryRespose fetchSurveyOfCountry(final Authentication authentication, final FetchSurveyOfCountryRequest request) {
        throw new NotImplementedException("Method pending implementation, see Trac #924.");
    }

    public Fallible processSurveyOfCountry(final Authentication authentication, final SurveyOfCountryRequest request) {
        throw new NotImplementedException("Method pending implementation, see Trac #924.");
    }
}
