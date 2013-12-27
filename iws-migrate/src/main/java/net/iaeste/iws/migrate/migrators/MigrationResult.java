/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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
 * @since   1.7
 */
public final class MigrationResult {

    private final int persisted;
    private final int skipped;
    private final int updated;

    public MigrationResult(final int persisted, final int skipped) {
        this.persisted = persisted;
        this.skipped = skipped;
        this.updated = 0;
    }

    public MigrationResult(final int persisted, final int skipped, final int updated) {
        this.persisted = persisted;
        this.skipped = skipped;
        this.updated = updated;
    }

    public int getPersisted() {
        return persisted;
    }

    public int getSkipped() {
        return skipped;
    }

    public int getUpdated() {
        return updated;
    }
}
