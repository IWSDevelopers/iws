/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.spring.Config
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

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class Config {

    /**
     * Must be replaced by a proper properties setup, but until we actually have
     * a real database working, this will suffice.
     */
    private static final Boolean USE_INMEMORY_DATABASE = true;

    @Bean
    public DataSource dataSource() {
        return USE_INMEMORY_DATABASE ? prepareHSQLDataSource() : preparePostgreSQLDataSource();
    }

    private DataSource prepareHSQLDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("hsqldb/init_tables.sql")
                .addScript("hsqldb/init_views.sql")
                .addScript("hsqldb/init_data.sql")
                .addScript("hsqldb/exchange-init.sql")
                .addScript("hsqldb/exchange-views.sql")
                .addScript("hsqldb/exchange-data.sql")
                .build();
    }

    private DataSource preparePostgreSQLDataSource() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName("IWS");
        dataSource.setUser("postgres");
        dataSource.setPassword("password");
        dataSource.setServerName("localhost");
        dataSource.setPortNumber(5432);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setPackagesToScan("net.iaeste.iws.persistence");
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

        return transactionManager;
    }
}
