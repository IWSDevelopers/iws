/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.ejb.cdi;

import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.ejb.NotificationManager;
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
    private EntityManager iwsEntityManager;

    /**
     * Producer for the IWS Settings. This will create a managed bean that can
     * be used for injections.
     */
    @Produces @IWSBean
    private final Settings settings = prepareSettings();

    @Produces @IWSBean
    @EJB(beanInterface = NotificationManager.class)
    private NotificationManager notifications;

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
