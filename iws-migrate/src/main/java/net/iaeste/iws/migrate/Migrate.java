/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.Migrate
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate;

import net.iaeste.iws.migrate.spring.ContextProvider;
import net.iaeste.iws.migrate.spring.MigrateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class Migrate {

    private static final Logger log = LoggerFactory.getLogger(Migrate.class);
    private static final ContextProvider context = ContextProvider.getInstance();

    private Migrate() {
    }

    public static void main(final String [] args) {
        log.info("Starting Migration of the IW3 Database to IWS.");
        final MigrateService service = context.getMigrateService();

        // Now run the migrations
        service.migrateCountries();
        service.migrateGroups();
        service.migrateUsers();
        service.migrateUserGroups();
        service.migrateOffers();

        log.info("Migration of the IW3 Database to IWS has been completed.");
    }
}
