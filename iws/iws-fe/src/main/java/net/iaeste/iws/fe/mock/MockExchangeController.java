/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.mock.MockExchangeController
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fe.mock;

import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.data.AuthenticationToken;
import net.iaeste.iws.api.requests.*;
import net.iaeste.iws.api.responses.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

/**
 * Mock implementation of {@link Exchange}
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@Mock
@ApplicationScoped
public class MockExchangeController implements Exchange {

    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;
    private static final Logger LOG = LoggerFactory.getLogger(MockExchangeController.class);

    @Override
    public Fallible processFaculties(AuthenticationToken token, FacultyRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FacultyResponse fetchFaculties(AuthenticationToken token, FetchFacultiesRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Fallible processOffers(AuthenticationToken token, OfferRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public OfferResponse fetchOffers(AuthenticationToken token, FetchOffersRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Fallible processOfferTemplates(AuthenticationToken token, OfferTemplateRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public OfferTemplateResponse fetchOfferTemplates(AuthenticationToken token, FetchOfferTemplatesRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Fallible processPublishGroups(AuthenticationToken token, PublishGroupRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PublishGroupResponse fetchPublishGroups(AuthenticationToken token, FetchPublishGroupsRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Fallible processStudents(AuthenticationToken token, StudentRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StudentResponse fetchStudents(AuthenticationToken token, FetchStudentsRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
