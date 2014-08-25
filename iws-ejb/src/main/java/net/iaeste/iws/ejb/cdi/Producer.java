/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.ResourceProducer
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

    private static final Logger log = LoggerFactory.getLogger(Producer.class);
    // See https://docs.jboss.org/author/display/WFLY8/Command+line+parameters
    private static final String JBOSS_CONFIG_DIR = "jboss.server.config.dir";
    private static final String PROPERTIES_FILE = "/iws.properties";

    /**
     * The primary database for the IWS.
     */
    @Produces @IWSBean(IWSBean.Type.PRIMARY)
    @PersistenceContext(unitName = "iwsDatabase")
    private EntityManager iwsEntityManager = null;

    /**
     * The mail database, which is an external resource. This resource is
     * currently being dismantled, and with the completion of the PostgreSQL
     * upgrade (see Trac task #) & PostgreSQL replication (see Trac task #).
     */
    @Produces @IWSBean(IWSBean.Type.SECONDARY)
    @PersistenceContext(unitName = "mailingListDatabase")
    private EntityManager mailEntityManager = null;

    /**
     * Producer for the IWS Settings. This will create a managed bean that can
     * be used for injections.
     */
    @Produces @IWSBean
    private Settings settings = prepareSettings();

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
    private Settings prepareSettings() {
        final String file = System.getProperty(JBOSS_CONFIG_DIR) + PROPERTIES_FILE;
        log.info("Reading the IWS Properties from '" + file + "'.");
        Settings mySettings = null;

        try (InputStream stream = new FileInputStream(file)) {
            final Properties properties = new Properties();
            properties.load(stream);

            mySettings = new Settings(properties);
        } catch (FileNotFoundException e) {
            log.warn("Properties file was not found, reverting to default values.", e);
            mySettings = new Settings();
        } catch (IOException e) {
            log.warn("Properties file could not be loaded, reverting to default values.", e);
            mySettings = new Settings();
        }

        return mySettings;
    }
}
