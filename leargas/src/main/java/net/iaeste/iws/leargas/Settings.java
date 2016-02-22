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
