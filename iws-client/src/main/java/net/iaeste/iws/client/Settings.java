/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.Configuration
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads the Properties file, and uses the settings from it, to determine how
 * the Client should be configured. If certain settings are missing, then a
 * default value will be used instead.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection StaticNonFinalField
 */
public class Settings {

    private static final Logger LOG = LoggerFactory.getLogger(Settings.class);
    private static final Object INSTANCE_LOCK = new Object();
    private static Settings instance = null;

    /** Internal Properties object, contains default and overridden settings. */
    private final Object propertyLock = new Object();
    private final Properties properties;

    /** The name of the IWS Client properties file. */
    private static final String PROPERTIES_FILE = "client.properties";

    // Default Property Keys
    private static final String PROPERTY_USE_POSTGRESQL_DATABASE = "iws.client.use.postgresql.database";
    private static final String PROPERTY_POSTGRESQL_DATABASE = "iws.client.postgresql.database";
    private static final String PROPERTY_POSTGRESQL_USERNAME = "iws.client.postgresql.username";
    private static final String PROPERTY_POSTGRESQL_PASSWORD = "iws.client.postgresql.password";
    private static final String PROPERTY_POSTGRESQL_SERVER = "iws.client.postgresql.server";
    private static final String PROPERTY_POSTGRESQL_PORT = "iws.client.postgresql.port";

    // Default Property Values
    private static final Boolean DEFAULT_USE_POSTGRESQL_DATABASE = false;
    private static final String DEFAULT_POSTGRESQL_DATABASE = "iws";
    private static final String DEFAULT_POSTGRESQL_USERNAME = "iws";
    private static final String DEFAULT_POSTGRESQL_PASSWORD = "iws";
    private static final String DEFAULT_POSTGRESQL_SERVER = "localhost";
    private static final Integer DEFAULT_POSTGRESQL_PORT = 5432;

    // =========================================================================
    // Object Instantiation Methods
    // =========================================================================

    /**
     * Default Private Constructor for the class, as it is a Singleton. The
     * Constructor will try to load the Properties, if it fails - the default
     * values will be used, and an error is logged.
     */
    private Settings() {
        properties = new Properties();

        try (InputStream stream = getClass().getResourceAsStream('/' + PROPERTIES_FILE)) {
            if (stream != null) {
                properties.load(stream);
            }
        } catch (IOException e) {
            LOG.error("Cannot load Properties from the file: " + PROPERTIES_FILE + ", will use default values.", e);
        }
    }

    /**
     * Singleton class instantiator.
     *
     * @return Client Settings instance
     */
    public static Settings getInstance() {
        synchronized (INSTANCE_LOCK) {
            if (instance == null) {
                instance = new Settings();
            }

            return instance;
        }
    }

    // =========================================================================
    // Methods, which will read and convert the Property values
    // =========================================================================

    /**
     * Retrieves if the Client should use the PostgreSQL database or not. If no
     * such setting can be read, then a warning is logged. And and the default
     * value is used.
     *
     * @return True if the InMemory database should be used, otherwise false
     * @see #PROPERTY_USE_POSTGRESQL_DATABASE
     * @see #DEFAULT_USE_POSTGRESQL_DATABASE
     */
    public Boolean getUsePostgreSQLDatabase() {
        Boolean result;

        try {
            result = Boolean.valueOf(getProperty(PROPERTY_USE_POSTGRESQL_DATABASE));
        } catch (NumberFormatException e) {
            LOG.warn("The property value for " + PROPERTY_USE_POSTGRESQL_DATABASE + " is invalid, using default <" + DEFAULT_USE_POSTGRESQL_DATABASE + '>', e);
            result = DEFAULT_USE_POSTGRESQL_DATABASE;
        }

        return result;
    }

    /**
     * Retrieves the name of the PostgreSQL database to use. If no such name
     * is set, then a warning is logged, and the default value is used.
     *
     * @return PostgreSQL Database Name
     * @see #PROPERTY_POSTGRESQL_DATABASE
     * @see #DEFAULT_POSTGRESQL_DATABASE
     */
    public String getPostgresqlDatabaseName() {
        String result = getProperty(PROPERTY_POSTGRESQL_DATABASE);

        if (result == null) {
            LOG.warn("The property value for " + PROPERTY_POSTGRESQL_DATABASE + " is invalid, using default <" + DEFAULT_POSTGRESQL_DATABASE + '>');
            result = DEFAULT_POSTGRESQL_DATABASE;
        }

        return result;
    }

    /**
     * Retrieves the username for the PostgreSQL database. If no username is
     * defined, then a warning is logged, and the default value is used.
     *
     * @return PostgreSQL Username
     * @see #PROPERTY_POSTGRESQL_USERNAME
     * @see #DEFAULT_POSTGRESQL_USERNAME
     */
    public String getPostgresqlUsername() {
        String result = getProperty(PROPERTY_POSTGRESQL_USERNAME);

        if (result == null) {
            LOG.warn("The property value for " + PROPERTY_POSTGRESQL_USERNAME + " is invalid, using default <" + DEFAULT_POSTGRESQL_USERNAME + '>');
            result = DEFAULT_POSTGRESQL_USERNAME;
        }

        return result;
    }

    /**
     * Retrieves the password for the PostgreSQL database. If no password is
     * defined, then a warning is logged, and the default is used.<br />
     *   For data protection, the actual password is not written in the log
     * file.
     *
     * @return PostgreSQL Password
     * @see #PROPERTY_POSTGRESQL_PASSWORD
     * @see #DEFAULT_POSTGRESQL_PASSWORD
     */
    public String getPostgresqlPassword() {
        String result = getProperty(PROPERTY_POSTGRESQL_PASSWORD);

        if (result == null) {
            LOG.warn("The property value for " + PROPERTY_POSTGRESQL_PASSWORD + " is invalid, using default.");
            result = DEFAULT_POSTGRESQL_PASSWORD;
        }

        return result;
    }

    /**
     * Retrieves the PostgreSQL Server Name. If no such name is defined, then a
     * warning is logged, and the default is used.
     *
     * @return PostgreSQL Server Name
     * @see #PROPERTY_POSTGRESQL_SERVER
     * @see #DEFAULT_POSTGRESQL_SERVER
     */
    public String getPostgresqlServerName() {
        String result = getProperty(PROPERTY_POSTGRESQL_SERVER);

        if (result == null) {
            LOG.warn("The property value for " + PROPERTY_POSTGRESQL_SERVER + " is invalid, using default <" + DEFAULT_POSTGRESQL_SERVER + '>');
            result = DEFAULT_POSTGRESQL_SERVER;
        }

        return result;
    }

    /**
     * Retrieves the port for the PostgreSQL server. If unable to read it, then
     * a warning is logged, and the default us used.
     *
     * @return PostgreSQL Server Port
     * @see #PROPERTY_POSTGRESQL_PORT
     * @see #DEFAULT_POSTGRESQL_PORT
     */
    public Integer getPostgresqlPort() {
        Integer result;

        try {
            result = Integer.valueOf(getProperty(PROPERTY_POSTGRESQL_PORT));
        } catch (NumberFormatException e) {
            LOG.warn("The property value for " + PROPERTY_POSTGRESQL_PORT + " is invalid, using default <" + DEFAULT_POSTGRESQL_PORT + '>', e);
            result = DEFAULT_POSTGRESQL_PORT;
        }

        return result;
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    /**
     * Reads a property value from the properties instance, and returns the
     * value. If not property value is found, then a null value is returned.
     *
     * @param  propertyName  Property Key
     * @return Property Value or null
     */
    private String getProperty(final String propertyName) {
        final String value;

        synchronized (propertyLock) {
            value = properties.getProperty(propertyName);
        }

        return value != null ? value.trim() : null;
    }
}
