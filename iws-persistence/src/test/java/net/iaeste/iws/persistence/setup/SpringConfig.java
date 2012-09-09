/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

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

    private EmbeddedDatabase dataSource = null;

    /**
     * if set to {@code true} then instead of executing {@code audit-init.sql} script
     * the generic build of auditing table will be used
     */
    private static final boolean useGenericSqlForAuditing = true;

    @Bean
    public DataSource dataSource() {
        final EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("net/iaeste/iws/persistence/hsqldb/init_tables.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/init_views.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/init_data.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/exchange-init.sql")
                .addScript("net/iaeste/iws/persistence/hsqldb/exchange-triggers.sql");
                //.addScript("net/iaeste/iws/persistence/hsqldb/exchange-views.sql")
                //.addScript("net/iaeste/iws/persistence/hsqldb/exchange-data.sql");
        if (!useGenericSqlForAuditing) {
            databaseBuilder.addScript("net/iaeste/iws/persistence/hsqldb/audit-init.sql");
            databaseBuilder.addScript("net/iaeste/iws/persistence/hsqldb/audit-triggers.sql");
        }
        dataSource = databaseBuilder.build();

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
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

        if (useGenericSqlForAuditing) {
            getAuditTablesAndTriggers().execute();
        }

        return transactionManager;
    }

    @Bean
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

    /**
     * Configures AuditBuilder and generates auditing tables.
     * Method is used only when {@code useGenericSqlForAuditing = true}.
     */
    private AuditBuilder getAuditTablesAndTriggers() {
        // by default do not log changes for all tables
        final AuditBuilder.IncludeMode includeMode = AuditBuilder.IncludeMode.NONE;
        // add tables for auditing
        final Set<String> includedTables = new HashSet<>(1);
        includedTables.add("offers");
        final Set<String> excludedTables = Collections.emptySet();
        // by default all columns for audited tables will be saved

        final AuditBuilder auditBuilder = new AuditBuilder(dataSource).
                setIncludeMode(includeMode).
                setIncludeTable(includedTables).
                setExcludeTable(excludedTables);

        return auditBuilder;
    }
}
