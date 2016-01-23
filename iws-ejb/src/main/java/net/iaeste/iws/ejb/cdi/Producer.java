/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.cdi.Producer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb.cdi;

import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.ejb.NotificationManagerLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * CDI Producer Class, which produces most internal services or resources.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public class Producer {

    private static final Logger LOG = LoggerFactory.getLogger(Producer.class);

    // Note, that the IWS is currently only developed and tested under WildFly,
    // the Community Edition Open Source version of RedHat JBoss. If other
    // Application Servers should be supported, then there is nothing in the
    // IWS, which is Application Server dependent except for this path.
    // See https://docs.jboss.org/author/display/WFLY8/Command+line+parameters
    private static final String JBOSS_CONFIG_DIR = "jboss.server.config.dir";

    // The IWS Properties File
    private static final String PROPERTIES_FILE = "iws.properties";

    /**
     * The primary database for the IWS.
     */
    @Produces @IWSBean
    @PersistenceContext(unitName = "iwsDatabase")
    private EntityManager iwsEntityManager = null;

    /**
     * Producer for the IWS Settings. This will create a managed bean that can
     * be used for injections.
     */
    @Produces @IWSBean
    private final Settings settings = prepareSettings();

    @Produces @IWSBean
    @EJB(beanInterface = NotificationManagerLocal.class)
    private NotificationManagerLocal notifications;

    /**
     * The Service Factory is required to create the required Service classes
     * for the Business Logic. This Bean will simply create an instance that can
     * be used generally, so the different Beans need less overhead.
     *
     * @return ServiceFactory Instance
     */
    @Produces @IWSBean
    public ServiceFactory produceServiceFactory() {
        return new ServiceFactory(iwsEntityManager, notifications, settings);
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    /**
     * Loads the Settings from the properties file assumed to be located in the
     * same place as the JBoss (WildFly) configuration.
     *
     * @return IWS Settings
     */
    private static Settings prepareSettings() {
        final String dir = System.getProperty(JBOSS_CONFIG_DIR);
        Settings mySettings;

        if (dir != null) {
            final String file = dir + File.separator + PROPERTIES_FILE;
            LOG.debug("Reading the IWS Properties from '{}'.", file);

            try (InputStream stream = new FileInputStream(file)) {
                final Properties properties = new Properties();
                properties.load(stream);

                mySettings = new Settings(properties);
            } catch (FileNotFoundException e) {
                LOG.warn("Properties file was not found, reverting to default values.", e);
                mySettings = new Settings();
            } catch (IOException e) {
                LOG.warn("Properties file could not be loaded, reverting to default values.", e);
                mySettings = new Settings();
            }
        } else {
            LOG.warn("Application Server Configuration Path cannot be read.");
            mySettings = new Settings();
        }

        return mySettings;
    }
}
