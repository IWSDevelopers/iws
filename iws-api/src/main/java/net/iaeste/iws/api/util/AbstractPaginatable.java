/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

/**
 * All Requests that allowe sorting and pagination, must extend this class.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public abstract class AbstractPaginatable extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    protected final Page page = new Page();

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

        page.setPageNumber(pageNumber);
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

        page.setPageSize(pageSize);
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
        page.setSortAscending(ascendingSortOrder);
    }

    /**
     * Retrieves all the Paging information required for the request.
     *
     * @return Paginatiable Object for this request
     */
    public Page getPagingInformation() {
        return page;
    }

    /**
     * The Field to sort by. Please note, that the available fields are request
     * specific, which means that the values must be checked in actual
     * implementations.
     *
     * @param sortBy Field to sort by
     */
    public abstract void setSortBy(SortingField sortBy);
}
