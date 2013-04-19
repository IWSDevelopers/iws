/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.callers.ExchangeCaller
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

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.DeleteOfferRequest;
import net.iaeste.iws.api.requests.FacultyRequest;
import net.iaeste.iws.api.requests.FetchEmployerInformationRequest;
import net.iaeste.iws.api.requests.FetchFacultiesRequest;
import net.iaeste.iws.api.requests.FetchGroupsForSharingRequest;
import net.iaeste.iws.api.requests.FetchOfferTemplatesRequest;
import net.iaeste.iws.api.requests.FetchOffersRequest;
import net.iaeste.iws.api.requests.FetchPublishGroupsRequest;
import net.iaeste.iws.api.requests.FetchPublishOfferRequest;
import net.iaeste.iws.api.requests.FetchStudentsRequest;
import net.iaeste.iws.api.requests.OfferTemplateRequest;
import net.iaeste.iws.api.requests.ProcessOfferRequest;
import net.iaeste.iws.api.requests.PublishGroupRequest;
import net.iaeste.iws.api.requests.PublishOfferRequest;
import net.iaeste.iws.api.requests.StudentRequest;
import net.iaeste.iws.api.responses.FetchFacultyResponse;
import net.iaeste.iws.api.responses.FetchEmployerInformationResponse;
import net.iaeste.iws.api.responses.FetchGroupsForSharingResponse;
import net.iaeste.iws.api.responses.FetchOfferTemplateResponse;
import net.iaeste.iws.api.responses.FetchOffersResponse;
import net.iaeste.iws.api.responses.FetchPublishGroupResponse;
import net.iaeste.iws.api.responses.FetchPublishOfferResponse;
import net.iaeste.iws.api.responses.FetchStudentResponse;
import net.iaeste.iws.api.responses.OfferResponse;
import net.iaeste.iws.api.responses.PublishOfferResponse;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.ExchangeClient;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * The IWS FitNesse implementation of the API logic. The Class will attempt to
 * invoke the IWS Client module, and wrap all calls with an Exception check that
 * will throw a new {@code StopTestException} if an error occured - this is the
 * expected behaviour for the FitNesse tests.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class ExchangeCaller implements Exchange {

    // The Client handles the IWS for us, we use use it
    private final Exchange caller = new ExchangeClient();

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchEmployerInformationResponse fetchEmployers(final AuthenticationToken token, final FetchEmployerInformationRequest request) {
        try {
            return caller.fetchEmployers(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible manageFaculties(final AuthenticationToken token, final FacultyRequest request) {
        try {
            return caller.manageFaculties(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchFacultyResponse fetchFaculties(final AuthenticationToken token, final FetchFacultiesRequest request) {
        try {
            return caller.fetchFaculties(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse processOffer(final AuthenticationToken token, final ProcessOfferRequest request) {
        try {
            return caller.processOffer(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfferResponse deleteOffer(final AuthenticationToken token, final DeleteOfferRequest request) {
        try {
            return caller.deleteOffer(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchOffersResponse fetchOffers(final AuthenticationToken token, final FetchOffersRequest request) {
        try {
            return caller.fetchOffers(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible manageOfferTemplate(final AuthenticationToken token, final OfferTemplateRequest request) {
        try {
            return caller.manageOfferTemplate(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchOfferTemplateResponse fetchOfferTemplates(final AuthenticationToken token, final FetchOfferTemplatesRequest request) {
        try {
            return caller.fetchOfferTemplates(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible managePublishGroup(final AuthenticationToken token, final PublishGroupRequest request) {
        try {
            return caller.managePublishGroup(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPublishGroupResponse fetchPublishGroups(final AuthenticationToken token, final FetchPublishGroupsRequest request) {
        try {
            return caller.fetchPublishGroups(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible manageStudent(final AuthenticationToken token, final StudentRequest request) {
        try {
            return caller.manageStudent(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchStudentResponse fetchStudents(final AuthenticationToken token, final FetchStudentsRequest request) {
        try {
            return caller.fetchStudents(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchGroupsForSharingResponse fetchGroupsForSharing(AuthenticationToken token, FetchGroupsForSharingRequest request) {
        try {
            return caller.fetchGroupsForSharing(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublishOfferResponse processPublishOffer(final AuthenticationToken token, final PublishOfferRequest request) {
        try {
            return caller.processPublishOffer(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchPublishOfferResponse fetchPublishedOfferInfo(final AuthenticationToken token, final FetchPublishOfferRequest request) {
        try {
            return caller.fetchPublishedOfferInfo(token, request);
        } catch (Exception e) {
            throw new StopTestException(e);
        }
    }
}
