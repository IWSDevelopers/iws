/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.spring.SpringConfig
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.spring;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Spring JavaConfig, for Setting up the EntityManager for the Migration.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Configuration
@ComponentScan("net.iaeste.iws.migrate.spring")
@EnableTransactionManagement
public class Config {

    @Bean
    public MigrateService migrateService() {
        return new MigrateService();
    }

    @Bean(name = "dataSourceIW3")
    public DataSource dataSourceIW3() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("iw3");
        dataSource.setUser("kim");

        return dataSource;
    }

    @Bean(name = "dataSourceIWS")
    public DataSource dataSourceIWS() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("iws");
        dataSource.setUser("kim");

        return dataSource;
    }

    @Bean(name = "entityManagerFactoryIW3Bean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryIW3Bean() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setPackagesToScan("net.iaeste.iws.migrate");
        factoryBean.setDataSource(dataSourceIW3());
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setJpaProperties(jpaProperties());
        factoryBean.setPersistenceUnitName("IW3PersistenceUnit");

        return factoryBean;
    }

    @Bean(name = "entityManagerFactoryIWSBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryIWSBean() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setPackagesToScan("net.iaeste.iws.persistence");
        factoryBean.setDataSource(dataSourceIWS());
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setJpaProperties(jpaProperties());
        factoryBean.setPersistenceUnitName("IWSPersistenceUnit");

        return factoryBean;
    }

    @Bean(name = "transactionManagerIW3")
    public PlatformTransactionManager transactionManagerIW3() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryIW3Bean().getObject());

        return transactionManager;
    }

    @Bean(name = "transactionManagerIWS")
    public PlatformTransactionManager transactionManagerIWS() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryIWSBean().getObject());

        return transactionManager;
    }

    @Bean(name = "jpaProperties")
    public Properties jpaProperties() {
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
