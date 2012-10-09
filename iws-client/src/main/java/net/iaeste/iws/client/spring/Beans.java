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

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.Exchange;
import net.iaeste.iws.client.Settings;
import net.iaeste.iws.core.services.ServiceFactory;
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

/**
 * The purpose of the Client module is to provide a very simple self-configured
 * implementation of the IWS, that others can use. This class provides the
 * Spring based configuration options to enable this, and thus load the IWS
 * as a library, rather than via a real Application Server instance.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Configuration
@ComponentScan("net.iaeste.iws.client.spring")
@EnableTransactionManagement
public class Beans {

    private final Settings settings = Settings.getInstance();

    // =========================================================================
    // IWS Controller Beans
    // =========================================================================

    @Bean(name = "access")
    public Access access() {
        return new SpringAccessClient(serviceFactory());
    }

    @Bean(name = "administration")
    public Administration administration() {
        return new SpringAdministrationclient(serviceFactory());
    }

    @Bean(name = "exchange")
    public Exchange exchange() {
        return new SpringExchangeClient(serviceFactory());
    }

    // =========================================================================
    // Database & Services Setup
    // =========================================================================

    @Bean(name = "serviceFactory")
    public ServiceFactory serviceFactory() {
        return new ServiceFactory(entityManagerFactoryBean().getObject().createEntityManager());
    }

    @Bean(name = "entityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setPackagesToScan("net.iaeste.iws.persistence");
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        return factoryBean;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        final Boolean usePG = settings.getUsePostgreSQLDatabase();
        return usePG ? preparePostgreSQLDataSource() : prepareHSQLDataSource();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

        return transactionManager;
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
                .addScript("net/iaeste/iws/persistence/hsqldb/exchange-init.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/exchange-data.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/exchange-views.sql")
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
