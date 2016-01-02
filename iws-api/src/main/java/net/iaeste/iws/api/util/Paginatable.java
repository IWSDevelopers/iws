/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.Paginatable
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.util;

import net.iaeste.iws.api.enums.SortingField;

/**
 * For Pagination, our classes must implement this Interface. It contains the
 * necessary information for the lookup in the database.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Paginatable {

    /**
     * The maximum allowed number of objects to be retrieved in a single
     * request.
     */
    int MAX_PAGE_SIZE = 100;

    /**
     * The number for for the first page to be loaded. It is set to start with
     * 1, as this is a
     */
    int FIRST_PAGE = 1;

    /**
     * The default sorting order.
     */
    boolean ASCENDING_SORT_ORDER = true;

    /**
     * Retrieves the Current Page Number for this fetch request.
     *
     * @return Current Page number
     */
    int pageNumber();

    /**
     * Retrieves the Current Page Size for this fetch request.
     *
     * @return Current Page Size
     */
    int pageSize();

    /**
     * Retrieves the Current Sort Order for this fetch request. If the value is
     * true, then the sorting will be made in Ascending Order, if the value is
     * false, then the sorting will be made in Descending Order.
     *
     * @return True for Ascending Sort Order, false for Descending Sort Order
     */
    boolean sortAscending();

    /**
     * Retrieves the Current Sort By Field. Note, that all fields that is
     * allowed to be sorted by is defined in the SortingField enumeration.
     * However, the actually allowed values are request specific, so please
     * check the documentation for the setting to see which fields may be used.
     *
     * @return Current Sort By Field
     */
    SortingField sortBy();
}
