/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.AbstractPaginatable
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
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.AbstractVerification;

/**
 * All Requests that allowe sorting and pagination, must extend this class.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public abstract class AbstractPaginatable extends AbstractVerification implements Paginatable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private int pageNumber = Paginatable.FIRST_PAGE;
    private int pageSize = Paginatable.MAX_PAGE_SIZE;
    private boolean ascendingSortOrder = Paginatable.ASCENDING_SORT_ORDER;
    private SortingField sortBy = SortingField.IDENTIFIER;

    /**
     * Sets the Current Page Number to be retrieved. If the Page Number is not
     * allowed, i.e. invalid - then the method throws a
     * {@code VerificationException}.
     *
     * @param pageNumber Current Page Number
     * @see Paginatable#FIRST_PAGE
     */
    public void setPageNumber(final int pageNumber) {
        if (pageNumber < 0) {
            throw new VerificationException("Invalid Page number.");
        }

        this.pageNumber = pageNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int pageNumber() {
        return pageNumber;
    }

    /**
     * Sets the Current Page Size, meaning the (maximum) number of records to
     * be retrieved from the IWS. If the given Page Size is outside of the
     * allowed range, then a {@code VerificationException} is thrown.
     *
     * @param pageSize Current Page Size
     * @see Paginatable#MAX_PAGE_SIZE
     */
    public void setPageSize(final int pageSize) {
        if ((pageSize <= 0) || (pageSize >= Paginatable.MAX_PAGE_SIZE)) {
            throw new VerificationException("Invalid Page Size.");
        }

        this.pageSize = pageSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int pageSize() {
        return pageSize;
    }

    /**
     * Sets the Sort Order for the request. It can be either Ascending (if the
     * given value is true) or Descending (if the given value is false).
     *
     * @param ascendingSortOrder True if sorting should be ascending, otherwise
     *                           false
     * @see Paginatable#ASCENDING_SORT_ORDER
     */
    public void setAscendingSortOrder(final boolean ascendingSortOrder) {
        this.ascendingSortOrder = ascendingSortOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sortAscending() {
        return ascendingSortOrder;
    }

    /**
     * The Field to sort by. Please note, that the available fields are request
     * specific, which means that the values must be checked in actual
     * implementations.
     *
     * @param sortBy Field to sort by
     */
    abstract void setSortBy(SortingField sortBy);

    /**
     * {@inheritDoc}
     */
    @Override
    public SortingField sortBy() {
        return sortBy;
    }
}
