/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.CommitteeController
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

import net.iaeste.iws.api.Committees;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.RegionalGroupRequest;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.api.responses.UserResponse;
import net.iaeste.iws.core.services.CommitteeService;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.persistence.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class CommitteeController extends CommonController implements Committees {

    private static final Logger LOG = LoggerFactory.getLogger(AdministrationController.class);
    private final ServiceFactory factory;

    /**
     * Default Constructor, takes a ServiceFactory as input parameter, and uses
     * this in all the request methods, to get a new Service instance.
     *
     * @param factory  The ServiceFactory
     */
    public CommitteeController(final ServiceFactory factory) {
        super(factory.getAccessDao());

        this.factory = factory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible createCommittee(final AuthenticationToken token, final CommitteeRequest request) {
        LOG.trace("Starting createCommittee()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.CREATE_COMMITTEE);
            verify(request, "Cannot process a null request.");

            final CommitteeService service = factory.prepareCommitteeService();
            response = service.createCommittee(authentication, request);
        } catch (IWSException e) {
            response = new UserResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished createCommittee()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible manageCommittee(final AuthenticationToken token, final CommitteeRequest request) {
        LOG.trace("Starting manageCommittee()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.MANAGE_COMMITTEE);
            verify(request, "Cannot process a null request.");

            final CommitteeService service = factory.prepareCommitteeService();
            response = service.manageCommittee(authentication, request);
        } catch (IWSException e) {
            response = new UserResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished manageCommittee()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible upgradeCommittee(final AuthenticationToken token, final CommitteeRequest request) {
        LOG.trace("Starting upgradeCommittee()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.UPGRADE_COMMITTEE);
            verify(request, "Cannot process a null request.");

            final CommitteeService service = factory.prepareCommitteeService();
            response = service.upgradeCommittee(authentication, request);
        } catch (IWSException e) {
            response = new UserResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished upgradeCommittee()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible manageInternationalGroup(final AuthenticationToken token, final InternationalGroupRequest request) {
        LOG.trace("Starting manageInternationalGroup()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.MANAGE_INTERNATIONAL_GROUP);
            verify(request, "Cannot process a null request.");

            final CommitteeService service = factory.prepareCommitteeService();
            response = service.manageInternationalGroup(authentication, request);
        } catch (IWSException e) {
            response = new UserResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished manageInternationalGroup()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible createRegionalGroup(final AuthenticationToken token, final RegionalGroupRequest request) {
        LOG.trace("Starting createRegionalGroup()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.CREATE_REGIONAL_GROUP);
            verify(request, "Cannot process a null request.");

            final CommitteeService service = factory.prepareCommitteeService();
            response = service.createRegionalGroup(authentication, request);
        } catch (IWSException e) {
            response = new UserResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished createRegionalGroup()");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fallible manageRegionalGroup(final AuthenticationToken token, final RegionalGroupRequest request) {
        LOG.trace("Starting manageRegionalGroup()");
        Fallible response;

        try {
            final Authentication authentication = verifyAccess(token, Permission.MANAGE_REGIONAL_GROUP);
            verify(request, "Cannot process a null request.");

            final CommitteeService service = factory.prepareCommitteeService();
            response = service.manageRegionalGroup(authentication, request);
        } catch (IWSException e) {
            response = new UserResponse(e.getError(), e.getMessage());
        }

        LOG.trace("Finished manageRegionalGroup()");
        return response;
    }
}
