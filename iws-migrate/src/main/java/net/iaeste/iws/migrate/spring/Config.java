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
import org.apache.commons.dbcp.BasicDataSource;
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

    // Hardcoded values for the Test Server
    private static final String PORT = "5432";
    private static final String IW3_SERVER = "192.38.77.85";
    private static final String IW3_DATABASE = "iw3_test";
    private static final String IW3_USERNAME = "readonly";
    private static final String IW3_PASSWORD = "af9v7aq6";
    private static final String IWS_SERVER = "localhost";
    private static final String IWS_DATABASE = "db_iw4_test";
    private static final String IWS_USERNAME = "iw4_test_user";
    private static final String IWS_PASSWORD = "jIf6rOAX92niHMFsQJjbuyf0";
    private static final String MAIL_SERVER = "localhost";
    private static final String MAIL_DATABASE = "db_iw4_maillists_test";
    private static final String MAIL_USERNAME = "iw4_test_user";
    private static final String MAIL_PASSWORD = "jIf6rOAX92niHMFsQJjbuyf0";

    private String readProperty(final String key, final String defaultValue) {
        synchronized (lock) {
            if (properties.isEmpty()) {
                try (InputStream inputStream = Config.class.getResourceAsStream("/migration.properties")) {
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
        final String server = readProperty("datasource.iw3.server", IW3_SERVER);
        final String port = readProperty("datasource.iw3.port", PORT);
        final String database = readProperty("datasource.iw3.database", IW3_DATABASE);
        final String username = readProperty("datasource.iw3.user", IW3_USERNAME);
        final String password = readProperty("datasource.iw3.password", IW3_PASSWORD);

        return preparePooledDataSource(server, port, database, username, password);
    }

    @Bean(name = "dataSourceIWS")
    public DataSource dataSourceIWS() {
        final String server = readProperty("datasource.iws.server", IWS_SERVER);
        final String port = readProperty("datasource.iws.port", PORT);
        final String database = readProperty("datasource.iws.database", IWS_DATABASE);
        final String username = readProperty("datasource.iws.user", IWS_USERNAME);
        final String password = readProperty("datasource.iws.password", IWS_PASSWORD);

        return preparePooledDataSource(server, port, database, username, password);
    }

    @Bean(name = "dataSourceMail")
    public DataSource dataSourceMail() {
        final String server = readProperty("datasource.mail.server", MAIL_SERVER);
        final String port = readProperty("datasource.mail.port", PORT);
        final String database = readProperty("datasource.mail.database", MAIL_DATABASE);
        final String username = readProperty("datasource.mail.user", MAIL_USERNAME);
        final String password = readProperty("datasource.mail.password", MAIL_PASSWORD);

        return preparePooledDataSource(server, port, database, username, password);
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

    /**
     * Preparing a Pooled DataSource, which is currently having hardcoded
     * settings, but this can also be altered with configuration.<br />
     *   The default DataSouce is not useful for multiple Connections, meaning
     * that Transactions are handled sequential, rather than concurrently.<br />
     *   Using a Pooled DataSource, will give us the advantage that migration
     * can be executed in multiple Threads, each handling a subset of the entire
     * block, and thus speed up the overall performance.
     *
     * @param server   Database Server, eg. localhost
     * @param port     Default Port for the DataSource, for PostgreSQL its 5432
     * @param database Name of the Database to connect to
     * @param username User to connect to the Database as
     * @param password Password (optional)
     * @return New Pooled DataSource
     */
    private BasicDataSource preparePooledDataSource(final String server, final String port, final String database, final String username, final String password) {
        // Using a Pooled Database Connection, as I've better experiences with
        // this than with the Spring default DataSource.
        final BasicDataSource dataSource = new BasicDataSource();

        // All Databases uses PostgreSQL, so we can just set it here.
        dataSource.setDriverClassName("org.postgresql.Driver");
        // The URL have PostgreSQL special settings. So we prepare the
        // connection URL here
        dataSource.setUrl("jdbc:postgresql://" + server + ':' + port + '/' + database);

        // Just have to fill in the Username & Optional Password
        dataSource.setUsername(username);
        if ((password != null) && !password.isEmpty()) {
            dataSource.setPassword(password);
        }

        // DataSource Pool Information
        dataSource.setMinIdle(10);
        dataSource.setMaxIdle(10);
        dataSource.setMaxWait(20);
        dataSource.setMaxActive(30);
        dataSource.setValidationQuery("select 1");

        return dataSource;
    }
}
