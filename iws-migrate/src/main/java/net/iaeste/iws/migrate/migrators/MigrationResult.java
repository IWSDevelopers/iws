/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.migrators.MigrationResult
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

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class MigrationResult {

    private final long persisted;
    private final long skipped;

    public MigrationResult() {
        this.persisted = 0;
        this.skipped = 0;
    }

    public MigrationResult(final long persisted, final long skipped) {
        this.persisted = persisted;
        this.skipped = skipped;
    }

    public MigrationResult merge(final MigrationResult result) {
        final long newPersisted = persisted + result.persisted;
        final long newSkipped = skipped + result.skipped;

        return new MigrationResult(newPersisted, newSkipped);
    }

    public long getPersisted() {
        return persisted;
    }

    public long getSkipped() {
        return skipped;
    }
}
