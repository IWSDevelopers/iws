/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.mock.ControllerProducer
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

import net.iaeste.iws.api.Access;
import net.iaeste.iws.fe.exceptions.ProjectStageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * Provides implementations of Controllers.
 * For each controller, the class defines a producer method that
 * decides whether a mock or the real implementation of the
 * controller should be used and returns the instance accordingly.
 * <p/>
 * Getters cannot be used directly, instances are returned using
 * dependency injection using the <code>@Inject</code> annotation.
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@ApplicationScoped
public class ControllerProducer {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerProducer.class);

    /* JSF Project stage */
    private final ProjectStage projectStage = FacesContext.getCurrentInstance().getApplication().getProjectStage();

    @Inject
    @Mock
    private Access mockAccessController;

    // TODO inject when ejb module is ready
    private Access accessController;

    /**
     * This method is called each time {@link Access}
     * is injected into the system using @Inject,
     *
     * @return an implementation of the {@link Access} interfaces
     */
    @Produces
    @ApplicationScoped
    private Access getAccessController() {
        LOG.trace("Creating instance of Access");
        return mock() ? mockAccessController : accessController;
    }

    /**
     * Decides whether mock objects should be used based on the {@link ProjectStage}
     * Mock objects are currently used for Development and Unit testing.
     * <p/>
     * For Production and SystemTest, real implementations should be used.
     *
     * @return true if mock objects should be used, otherwise false
     */
    private Boolean mock() {
        switch (projectStage) {
            case Development:
            case UnitTest:
                return true;
            case SystemTest:
            case Production:
                return false;
        }
        LOG.error("Current project stage is {}. Must be one of [Development, UnitTest, SystemTest, Production]", projectStage);
        throw new ProjectStageException("Project Stage not configured or not one of {Development, UnitTest, SystemTest, Production} !");
    }
}
