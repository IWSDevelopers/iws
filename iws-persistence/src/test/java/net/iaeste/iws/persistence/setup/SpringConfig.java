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
package net.iaeste.iws.persistence.setup;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Spring JavaConfig, for the Unit testing of the Persistence layer.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Configuration
@EnableTransactionManagement
public class SpringConfig {

    private static final Object LOCK = new Object();
    private enum DBS { PSQL, HSQL, H2 }
    private static final DBS db = DBS.HSQL;
    private static DataSource dataSource = null;

    @Bean
    protected DataSource dataSource() {
        final DataSource source;

        switch (db) {
            case PSQL:
                source = postgreDataSource();
                break;
            case HSQL:
                source = hsqldbDataSource();
                break;
            case H2:
                source = h2DataSource();
                break;
            default:
                throw new IWSException(IWSErrors.FATAL, "Database not supported in this context.");
        }

        return source;
    }

    @Bean
    protected LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setPackagesToScan("net.iaeste.iws.persistence");
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setJpaProperties(jpaProperties());

        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

        return transactionManager;
    }

    // =========================================================================
    // Internal methods to setup DataSources and Properties
    // =========================================================================

    private static DataSource postgreDataSource() {
        synchronized (LOCK) {
            if (dataSource == null) {
                final PGSimpleDataSource pgDataSource = new PGSimpleDataSource();

                pgDataSource.setServerName("localhost");
                pgDataSource.setPortNumber(5432);
                pgDataSource.setDatabaseName("iws");
                pgDataSource.setUser("iws_user");
                pgDataSource.setPassword("iws");

                dataSource = pgDataSource;
            }

            return dataSource;
        }
    }

    private static DataSource hsqldbDataSource() {
        synchronized (LOCK) {
            if (dataSource == null) {
                dataSource = new EmbeddedDatabaseBuilder()
                        .setType(EmbeddedDatabaseType.HSQL)
                        .addScript("net/iaeste/iws/persistence/hsqldb/10-base-tables.sql")
                        .addScript("net/iaeste/iws/persistence/15-base-views.sql")
                        .addScript("net/iaeste/iws/persistence/19-base-data.sql")
                        .addScript("net/iaeste/iws/persistence/hsqldb/30-exchange-tables.sql")
                        .addScript("net/iaeste/iws/persistence/35-exchange-views.sql")
                        .addScript("net/iaeste/iws/persistence/90-initial-test-data.sql")
                        .build();
            }

            return dataSource;
        }
    }

    private static DataSource h2DataSource() {
        synchronized (LOCK) {
            if (dataSource == null) {
                dataSource = new EmbeddedDatabaseBuilder()
                        .setType(EmbeddedDatabaseType.H2)
                        .addScript("net/iaeste/iws/persistence/h2/10-base-tables.sql")
                        .addScript("net/iaeste/iws/persistence/15-base-views.sql")
                        .addScript("net/iaeste/iws/persistence/19-base-data.sql")
                        .addScript("net/iaeste/iws/persistence/h2/30-exchange-tables.sql")
                        .addScript("net/iaeste/iws/persistence/35-exchange-views.sql")
                        .addScript("net/iaeste/iws/persistence/90-initial-test-data.sql")
                        .build();
            }

            return dataSource;
        }
    }

    private static Properties jpaProperties() {
        final Properties properties = new Properties();

        // For testing the result, it is helpful to be able to see the queries
        // executed against the database, preferably formatted as well :-)
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.format_sql", "true");

        return properties;
    }
}
