/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-leargas) - net.iaeste.iws.leargas.Settings
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.leargas;

import java.util.Properties;

/**
 * The Settings Class, is containing a set of methods to map over the values
 * from the Properties file.
 *
 * @author  Kim Jensen <kim@dawn.dk>
 * @version Leargas 1.0
 * @since   Java 1.8
 */
public final class Settings {

    private final Properties properties;

    public Settings(Properties properties) {
        this.properties = properties;
    }

    public String readIWSUrl() {
        return properties.getProperty("iws.webservice.host");
    }

    public String readIWSUsername() {
        return properties.getProperty("iws.username");
    }

    public String readIWSPassword() {
        return properties.getProperty("iws.password");
    }

    public String readDatabaseVendor() {
        return properties.getProperty("leargas.database.vendor");
    }

    public String readDatabaseHost() {
        return properties.getProperty("leargas.database.host");
    }

    public String readDatabasePort() {
        return properties.getProperty("leargas.database.port");
    }

    public String readDatabaseName() {
        return properties.getProperty("leargas.database.name");
    }

    public String readDatabaseUsername() {
        return properties.getProperty("leargas.database.username");
    }

    public String readDatabasePassword() {
        return properties.getProperty("leargas.database.password");
    }

    public String readBooleanTrueMapping() {
        return properties.getProperty("leargas.boolean.true");
    }

    public String readBooleanFalseMapping() {
        return properties.getProperty("leargas.boolean.false");
    }

    public String readDateFormatMapping() {
        return properties.getProperty("leargas.date.format");
    }

    public String readDateTimeFormatMapping() {
        return properties.getProperty("leargas.datetime.format");
    }
}
