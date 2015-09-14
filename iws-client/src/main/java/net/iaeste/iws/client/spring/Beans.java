/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.spring.Beans
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.spring;

import net.iaeste.iws.common.configuration.InternalConstants;
import net.iaeste.iws.common.configuration.Settings;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Configuration
@ComponentScan("net.iaeste.iws.client.spring")
@EnableTransactionManagement
public class Beans {

    // Following is used to configure the Settings
    private static final Integer MAX_ACTIVE_TOKENS = InternalConstants.MAX_ACTIVE_TOKENS;
    private static final Long MAX_IDLE_TIME_FOR_SESSIONS = InternalConstants.MAX_SESSION_IDLE_PERIOD;
    private static final Integer MAX_LOGIN_RETRIES = InternalConstants.MAX_LOGIN_RETRIES;
    private static final long LOGIN_BLOCKED_TIME = InternalConstants.LOGIN_BLOCKING_PERIOD;

    private static final Boolean USE_INMEMORY_DATABASE = true;

    @Bean
    protected DataSource dataSource() {
        return USE_INMEMORY_DATABASE ? hsqldbDataSource() : postgresDataSource();
    }

    private static DataSource postgresDataSource() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("iws");
        dataSource.setUser("iws_user");
        dataSource.setPassword("iws");

        return dataSource;
    }

    private static DataSource hsqldbDataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
                .addScript("net/iaeste/iws/persistence/hsqldb/10-base-tables.sql")
                .addScript("net/iaeste/iws/persistence/15-base-views.sql")
                .addScript("net/iaeste/iws/persistence/19-base-data.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/30-exchange-tables.sql")
                .addScript("net/iaeste/iws/persistence/35-exchange-views.sql")
                // Note, that the EmbeddedDatabase builder in Spring 4+ frowns
                // upon empty files, so we have to comment it out to avoid
                // errors when preparing the fake database for the tests.
                //.addScript("net/iaeste/iws/persistence/39-exchange-data.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/20-mailing-list-tables.sql")
                .addScript("net/iaeste/iws/persistence/90-initial-test-data.sql")
                .build();
    }

    @Bean(name = "entityManagerFactoryBean")
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
    protected Properties jpaProperties() {
        final Properties properties = new Properties();

        // For testing the result, it is helpful to be able to see the queries
        // executed against the database, preferably formatted as well :-)
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.format_sql", "true");

        return properties;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

        return transactionManager;
    }

    public static Settings settings() {
        final Settings settings = new Settings();

        settings.setMaxActiveTokens(MAX_ACTIVE_TOKENS);
        settings.setMaxIdleTimeForSessions(MAX_IDLE_TIME_FOR_SESSIONS);
        settings.setMaxLoginRetries(MAX_LOGIN_RETRIES);
        settings.setLoginBlockedTime(LOGIN_BLOCKED_TIME);

        return settings;
    }
}
