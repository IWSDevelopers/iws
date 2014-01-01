/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.IWSEntity
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.entities;

import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface IWSEntity {

    void setId(Long id);

    /**
     * The primary key in all our mergeable tables must always be retrievable
     * with this method.
     *
     * @return  Entity Id
     */
    Long getId();

    void setCreated(Date created);
    Date getCreated();
}
