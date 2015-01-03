/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.Page
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.SortingField;

import java.io.Serializable;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class Page implements Paginatable, Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private int pageNumber;
    private int pageSize;
    private boolean sortAscending;
    private SortingField sortBy;

    /**
     * Empty Constructor.
     */
    public Page() {
        pageNumber = Paginatable.FIRST_PAGE;
        pageSize = Paginatable.MAX_PAGE_SIZE;
        sortAscending = Paginatable.ASCENDING_SORT_ORDER;
        sortBy = SortingField.CREATED;
    }

    /**
     * Default Constructor, sets all Paginating fields.
     *
     * @param pageNumber    The current page to fetch, starting from 0 (zero)
     * @param pageSize      The max number of records on each page
     * @param sortAscending Sorting Order
     * @param sortBy        Sorting Field
     */
    public Page(final int pageNumber, final int pageSize, final boolean sortAscending, final SortingField sortBy) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortAscending = sortAscending;
        this.sortBy = sortBy;
    }

    public void setPageNumber(final int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int pageNumber() {
        return pageNumber;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int pageSize() {
        return pageSize;
    }

    public void setSortAscending(final boolean sortAscending) {
        this.sortAscending = sortAscending;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sortAscending() {
        return sortAscending;
    }

    public void setSortBy(final SortingField sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortingField sortBy() {
        return sortBy;
    }
}
