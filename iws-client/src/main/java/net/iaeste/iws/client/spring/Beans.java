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

import net.iaeste.iws.client.Settings;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * The purpose of the Client module is to facilitate a very simple self
 * configured implementation of the IWS, that others can use. This class
 * provides the Spring based configuration options to enable this, and thus load
 * the IWS as a library, rather than via a real Application Server instance.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Configuration
@ComponentScan("net.iaeste.iws.client.spring")
@EnableTransactionManagement
public class Beans {

    // Need a JavaConfig equivalent for <tx:annotation-config/>
    // see: http://blog.springsource.com/2011/02/17/spring-3-1-m1-featurespec/

    private final Settings settings = Settings.getInstance();

    // =========================================================================
    // Database & Services Setup
    // =========================================================================

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setPackagesToScan("net.iaeste.iws.persistence");
        factoryBean.setPersistenceUnitName("client");
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaDialect(new HibernateJpaDialect());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return factoryBean;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setDataSource(dataSource());
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        transactionManager.setPersistenceUnitName("client");
        transactionManager.afterPropertiesSet();

        return transactionManager;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        final Boolean usePG = settings.getUsePostgreSQLDatabase();
        return usePG ? preparePostgreSQLDataSource() : prepareHSQLDataSource();
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private DataSource prepareHSQLDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("net/iaeste/iws/persistence/hsqldb/init_tables.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/init_views.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/init_data.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/exchange_tables.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/exchange_data.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/exchange_views.sql")
                .build();
    }

    private DataSource preparePostgreSQLDataSource() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName(settings.getPostgresqlDatabaseName());
        dataSource.setUser(settings.getPostgresqlUsername());
        dataSource.setPassword(settings.getPostgresqlPassword());
        dataSource.setServerName(settings.getPostgresqlServerName());
        dataSource.setPortNumber(settings.getPostgresqlPort());

        return dataSource;
    }
}
