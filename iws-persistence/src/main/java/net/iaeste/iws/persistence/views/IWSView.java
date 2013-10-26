/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.IWSView
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.views;

import net.iaeste.iws.api.enums.SortingField;

/**
 * When reading multiple records from the system, it must be possible to sort
 * these according to the user requesting it. Since sorting is a field that
 * cannot be set dynamically, the queries must then either be constructed from
 * the ground up using CriteriaBuilder, or we have to move the control into the
 * Service Classes, which should not care for it. Instead, the solution that we
 * implement Comparable, and let it be customized according to the sorting
 * field used, seems to be the best solution, as it means that we have the
 * control in the Entities - where the Query control already resides.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface IWSView<T> extends Comparable<T> {

    /**
     * Sets the field to sort by, by default the listing is sorted according to
     * the creation time, and the sorting order is Ascending.
     *
     * @param sortField     Field to use for sorting
     * @param sortAscending If the sorting should be made Ascending or not
     */
    void setSorting(SortingField sortField, boolean sortAscending);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean equals(Object obj);

    /**
     * {@inheritDoc}
     */
    @Override
    int hashCode();
}
