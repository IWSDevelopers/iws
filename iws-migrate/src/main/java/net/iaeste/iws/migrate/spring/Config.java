/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.spring.Config
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

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;
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
import java.beans.Beans;
import java.io.IOException;
import java.io.InputStream;
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

    private final Object lock = new Object();
    private final Properties properties = new Properties();

    private String readProperty(final String key, final String defaultValue) {
        synchronized (lock) {
            if (properties.isEmpty()) {
                try (InputStream inputStream = Beans.class.getResourceAsStream("migration.properties")) {
                    if (inputStream != null) {
                        properties.load(inputStream);
                    }
                } catch (final IOException e) {
                    throw new IWSException(IWSErrors.FATAL, "Cannot load the Properties.", e);
                }
            }

            return properties.getProperty(key, defaultValue);
        }
    }

    @Bean(name = "dataSourceIW3")
    public DataSource dataSourceIW3() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setServerName(readProperty("datasource.iw3.server", "192.38.77.85"));
        dataSource.setPortNumber(Integer.valueOf(readProperty("datasource.iw3.port", "5432")));
        dataSource.setDatabaseName(readProperty("datasource.iw3.database", "iw3_test"));
        dataSource.setUser(readProperty("datasource.iw3.user", "readonly"));
        final String pwd = readProperty("datasource.iw3.password", "af9v7aq6");
        if ((pwd != null) && !pwd.isEmpty()) {
            dataSource.setPassword(pwd);
        }

        return dataSource;
    }

    @Bean(name = "dataSourceIWS")
    public DataSource dataSourceIWS() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setServerName(readProperty("datasource.iws.server", "localhost"));
        dataSource.setPortNumber(Integer.valueOf(readProperty("datasource.iws.port", "5432")));
        dataSource.setDatabaseName(readProperty("datasource.iws.database", "db_iw4_test"));
        dataSource.setUser(readProperty("datasource.iws.user", "iw4_test_user"));
        final String pwd = readProperty("datasource.iws.password", "jIf6rOAX92niHMFsQJjbuyf0");
        if ((pwd != null) && !pwd.isEmpty()) {
            dataSource.setPassword(pwd);
        }

        return dataSource;
    }

    @Bean(name = "dataSourceMail")
    public DataSource dataSourceMail() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setServerName(readProperty("datasource.mail.server", "localhost"));
        dataSource.setPortNumber(Integer.valueOf(readProperty("datasource.mail.port", "5432")));
        dataSource.setDatabaseName(readProperty("datasource.mail.database", "db_iw4_maillists_test"));
        dataSource.setUser(readProperty("datasource.mail.user", "iw4_test_user"));
        final String pwd = readProperty("datasource.mail.password", "jIf6rOAX92niHMFsQJjbuyf0");
        if ((pwd != null) && !pwd.isEmpty()) {
            dataSource.setPassword(pwd);
        }

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

    @Bean(name = "entityManagerFactoryMailBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryMailBean() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setPackagesToScan("net.iaeste.iws.persistence");
        factoryBean.setDataSource(dataSourceMail());
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setJpaProperties(jpaProperties());
        factoryBean.setPersistenceUnitName("MailPersistenceUnit");

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

    @Bean(name = "transactionManagerMail")
    public PlatformTransactionManager transactionManagerMail() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryMailBean().getObject());

        return transactionManager;
    }

    @Bean(name = "jpaProperties")
    public Properties jpaProperties() {
        final Properties jpaProperties = new Properties();

        // For testing the result, it is helpful to be able to see the queries
        // executed against the database, preferably formatted as well :-)
        jpaProperties.setProperty("hibernate.show_sql", "false");
        jpaProperties.setProperty("hibernate.format_sql", "false");
        // For some braindead reason - someone thought it was a good idea to
        // have the default behaviour being true for the autocommit setting!
        jpaProperties.setProperty("hibernate.connection.autocommit", "false");

        return jpaProperties;
    }
}
