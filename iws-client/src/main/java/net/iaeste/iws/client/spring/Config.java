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

import net.iaeste.iws.client.Configuration;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
public class Config {

    private final Configuration configuration = Configuration.getInstance();

    @Bean
    public DataSource dataSource() {
        final Boolean usePG = configuration.getUsePostgreSQLDatabase();
        return usePG ? preparePostgreSQLDataSource() : prepareHSQLDataSource();
    }

    private DataSource prepareHSQLDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("net/iaeste/iws/persistence/hsqldb/init_tables.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/init_views.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/init_data.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/offers-init.sql")
//                .addScript("classpath:/hsqldb/exchange-init.sql")
//                .addScript("classpath:/hsqldb/exchange-views.sql")
//                .addScript("classpath:/hsqldb/exchange-data.sql")
                .build();
    }

    private DataSource preparePostgreSQLDataSource() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName(configuration.getPostgresqlDatabaseName());
        dataSource.setUser(configuration.getPostgresqlUsername());
        dataSource.setPassword(configuration.getPostgresqlPassword());
        dataSource.setServerName(configuration.getPostgresqlServerName());
        dataSource.setPortNumber(configuration.getPostgresqlPort());

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
