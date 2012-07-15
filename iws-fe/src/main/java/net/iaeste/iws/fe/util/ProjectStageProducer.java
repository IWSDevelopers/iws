/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.util.ProjectStageProducer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.fe.util;

import net.iaeste.iws.fe.exceptions.ProjectStageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * This class provides the current ProjectStage to the application.
 * It lazily loads the configuration from different sources.
 * <p/>
 * The ProjectStage can be loaded using the static method
 * <code>getProjectStage()</code>, or injected with CDI via
 * <p/>
 * <code>
 * [at]Inject <br/>
 * javax.faces.application.ProjectStage stage; <br/>
 * </code>
 * <p/>
 * First, it tries to load the System property <code>faces.PROJECT_STAGE</code>.
 * If it fails, it tries to load the property from the current FacesContext.
 * If no config can be found, it throws a {@link ProjectStageException}.
 * <p/>
 * The ProjectStage should be configured in the server/servlet container properties.
 * We're not going to assume any default values because a major
 * part of the Frontend behavior depends on it, e.g. whether real services
 * or mock implementations should be used.
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@ApplicationScoped
public class ProjectStageProducer {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectStageProducer.class);
    private static final String PROJECT_STAGE_SYS_PROP = "faces.PROJECT_STAGE";
    private static ProjectStage CURRENT_PROJECT_STAGE = null;

    @Produces
    @Named
    public static ProjectStage getProjectStage() {
        if (CURRENT_PROJECT_STAGE == null) {
            initProjectStage();
        }
        return CURRENT_PROJECT_STAGE;
    }

    private static void initProjectStage() {
        if (CURRENT_PROJECT_STAGE == null) {
            CURRENT_PROJECT_STAGE = getFromSystemProperties();
        }

        if (CURRENT_PROJECT_STAGE == null) {
            CURRENT_PROJECT_STAGE = getFromFacesContext();
        }

        if (CURRENT_PROJECT_STAGE == null) {
            throw new ProjectStageException("No ProjectStage configuration found!");
        }
    }

    private static ProjectStage getFromSystemProperties() {
        LOG.info("Loading ProjectStage from System properties");
        String stageString = System.getProperty(PROJECT_STAGE_SYS_PROP);

        if (stageString != null && !stageString.isEmpty()) {
            return ProjectStage.valueOf(stageString);
        }
        LOG.error("ProjectStage configuration not found in System properties");
        return null;
    }

    private static ProjectStage getFromFacesContext() {
        LOG.info("Loading ProjectStage from FacesContext");
        FacesContext fc = FacesContext.getCurrentInstance();

        if (fc == null) {
            LOG.error("FacesContext is null. Request probably did not go through FacesServlet!");
            return null;
        }

        ProjectStage tmp = fc.getApplication().getProjectStage();
        if (tmp == null) {
            LOG.error("ProjectStage configuration not found in FacesContext");
            return null;
        }

        return tmp;
    }

}
