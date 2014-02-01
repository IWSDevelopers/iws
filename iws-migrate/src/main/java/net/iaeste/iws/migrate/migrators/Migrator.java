/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.migrators.Migrator
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.migrators;

import java.util.List;

/**
 * In order for the Spring Interfaced Proxy injection to work, all our Migrators
 * must implement this. Hence, we have two methods available, one for the
 * migration of IW3 Entities to IWS, and one for migration of mail data to the
 * mail database.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Migrator<T> {

    Integer BLOCK_SIZE = 1000;

    /**
     * This migrator is used by the mail migration.
     *
     * @return Result
     */
    MigrationResult migrate();

    /**
     * This migrator is used by the data migration.
     *
     * @param oldEntities List of Entities to migrate
     * @return Result
     */
    MigrationResult migrate(List<T> oldEntities);
}
