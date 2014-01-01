/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.setup.SpringConfig
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.setup;

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
 * @since   1.7
 */
@Configuration
@EnableTransactionManagement
public class SpringConfig {

    private static final Boolean USE_INMEMORY_DATABASE = true;

    @Bean
    protected DataSource dataSource() {
        return USE_INMEMORY_DATABASE ? hsqldbDataSource() : postgreDataSource();
    }

    private static DataSource postgreDataSource() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("iws");
        dataSource.setUser("kim");
        //dataSource.setPassword("iws");

        return dataSource;
    }

    private static DataSource hsqldbDataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
                .addScript("net/iaeste/iws/persistence/hsqldb/01-base-tables.sql")
                .addScript("net/iaeste/iws/persistence/02-base-views.sql")
                .addScript("net/iaeste/iws/persistence/03-base-data.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/04-exchange-tables.sql")
                .addScript("net/iaeste/iws/persistence/05-exchange-views.sql")
                .addScript("net/iaeste/iws/persistence/06-exchange-data.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/08-mailing-list-tables.sql")
                .addScript("net/iaeste/iws/persistence/90-initial-test-data.sql")
                .build();
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

    @Bean
    protected Properties jpaProperties() {
        final Properties properties = new Properties();

        // For testing the result, it is helpful to be able to see the queries
        // executed against the database, preferably formatted as well :-)
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.format_sql", "false");
        // For some braindead reason - someone thought it was a good idea to
        // have the default behaviour being true for the autocommit setting!
        properties.setProperty("hibernate.connection.autocommit", "false");

        return properties;
    }
}
