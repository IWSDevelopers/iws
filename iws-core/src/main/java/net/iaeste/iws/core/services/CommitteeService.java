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

import net.iaeste.iws.api.exceptions.NotImplementedException;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.requests.FetchSurveyOfCountryRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.SurveyOfCountryRequest;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.api.responses.FetchSurveyOfCountryRespose;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.persistence.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class CommitteeService {

    private static final Logger log = LoggerFactory.getLogger(CommitteeService.class);

    public FetchCommitteeResponse fetchCommittees(final Authentication authentication, final FetchCommitteeRequest request) {
        throw new NotImplementedException("Method pending implementation.");
    }

    public Fallible createCommittee(final Authentication authentication, final CommitteeRequest request) {
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
