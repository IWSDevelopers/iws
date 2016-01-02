/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-leargas) - net.iaeste.iws.leargas.Leargas
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.leargas;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import net.iaeste.iws.leargas.exceptions.LeargasException;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Properties;

/**
 * Main Class for the Leargas IWS Communication tool. The Main class is there
 * to provide the CLI functionality, which means ensuring that the Properties
 * is correctly loaded, as the Properties is controlling the rest.
 *
 * @author  Kim Jensen <kim@dawn.dk>
 * @version Leargas 1.0
 * @since   Java 1.8
 */
public final class Leargas {

    private static final Logger LOG = LoggerFactory.getLogger(Leargas.class);

    /** Private Constructor, this is just a Main class. */
    private Leargas() {}

    public static void main(final String[] args) {
        if (args.length == 1) {
            Connection connection = null;
            try (InputStream inputStream = new FileInputStream(args[0])) {
                // First, load the Properties file, which contain the
                // information we need to communicate with IWS as well
                // as settings for the Mapping and details regarding
                // the IWS Database.
                final Properties properties = new Properties();
                properties.load(inputStream);
                final Settings settings = new Settings(properties);
                connection = prepareConnection(settings);
                final Processor leargas = new Processor(settings, connection);

                // Now we can start the comminication with IWS and updating
                // the Leargas Database accordingly
                final Long start = System.nanoTime();
                LOG.info("Starting IWS Communication.");
                final State state = leargas.start();
                LOG.info("Completed IWS Communication in {} ms, {}.", calculateDuration(start), state);
            } catch (LeargasException | IOException e) {
                LOG.error(e.getMessage(), e);
            } finally {
                close(connection);
            }
        } else {
            final String msg =
                    "Leargas IWS Communication Tool version 1.0\n\n" +
                    "Usage:\n" +
                    "$ java -jar leargas.jar <Path To the \"leargas.properties\" file>\n";
            System.out.print(msg);
            System.exit(1);
        }
    }

    private static Connection prepareConnection(final Settings settings) {
        try {
            final String vendor = settings.readDatabaseVendor();
            final String lowerVendor = vendor.toLowerCase(Locale.ENGLISH);
            final DataSource dataSource;

            if ("mysql".equals(lowerVendor)) {
                dataSource = prepareMySQL(settings);
            } else if ("postgresql".equals(lowerVendor)) {
                dataSource = preparePostgreSQL(settings);
            } else {
                throw new LeargasException("Unknown Database Vendor: " + vendor);
            }

            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new LeargasException(e.getMessage(), e);
        }
    }

    private static DataSource prepareMySQL(final Settings settings) {
        try {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setServerName(settings.readDatabaseHost());
            dataSource.setPortNumber(Integer.valueOf(settings.readDatabasePort()));
            dataSource.setDatabaseName(settings.readDatabaseName());
            dataSource.setUser(settings.readDatabaseUsername());
            dataSource.setPassword(settings.readDatabasePassword());

            return dataSource;
        } catch (SecurityException e) {
            throw new LeargasException(e.getMessage(), e);
        }
    }

    private static DataSource preparePostgreSQL(final Settings settings) {
        try {
            PGSimpleDataSource dataSource = new PGSimpleDataSource();
            dataSource.setServerName(settings.readDatabaseHost());
            dataSource.setPortNumber(Integer.valueOf(settings.readDatabasePort()));
            dataSource.setDatabaseName(settings.readDatabaseName());
            dataSource.setUser(settings.readDatabaseUsername());
            dataSource.setPassword(settings.readDatabasePassword());

            return dataSource;
        } catch (SecurityException e) {
            throw new LeargasException(e.getMessage(), e);
        }
    }

    private static void close(final Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private static String calculateDuration(final Long start) {
        final DecimalFormat format = new DecimalFormat("###,###.##");
        return format.format((double) (System.nanoTime() - start) / 1000000);
    }
}
