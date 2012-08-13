/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.Mergeable
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

/**
 * Classes implementing this interface, are capable updating the current
 * content, with the content of a second Object of the same type.<br />
 *   The primary reason for having this, is to allow a merge of the information
 * that has been persisted with the EDA logic. This is needed, to ensure that
 * the Objects themselves remain the same, but have the updated information.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface Mergeable<T> {

    /**
     * Allows a merge between two objects of the same type. The method updates
     * the current object with the changes from the second.
     */
    void merge(T obj);
}
