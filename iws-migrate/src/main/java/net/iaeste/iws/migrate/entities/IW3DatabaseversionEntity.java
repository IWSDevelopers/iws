/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.entities.IW3DatabaseversionEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Entity
@Table(name = "databaseversion")
public class IW3DatabaseversionEntity {

    @Id
    @Column(name = "version_116", length = 10)
    private Integer version116 = null;

    // =========================================================================
    // IW3 Entity Setters & Getters
    // =========================================================================

    public void setVersion116(final Integer version116) {
        this.version116 = version116;
    }

    public Integer getVersion116() {
        return version116;
    }
}
