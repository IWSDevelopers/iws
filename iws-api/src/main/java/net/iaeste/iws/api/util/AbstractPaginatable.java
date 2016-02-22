/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.api.util;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.SortingField;
import net.iaeste.iws.api.exceptions.VerificationException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * All Requests that allows sorting and pagination, must extend this class.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "abstractPaginatable", propOrder = { "page" })
public abstract class AbstractPaginatable extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = false)
    protected final Page page = new Page();

    /**
     * Sets the Current Page Number to be retrieved. If the Page Number is not
     * allowed, i.e. invalid - then the method throws a
     * {@code VerificationException}.
     *
     * @param pageNumber Current Page Number
     * @see Paginatable#FIRST_PAGE
     */
    public final void setPageNumber(final int pageNumber) {
        if (pageNumber < Paginatable.FIRST_PAGE) {
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
    public final void setPageSize(final int pageSize) {
        if ((pageSize <= 0) || (pageSize > Paginatable.MAX_PAGE_SIZE)) {
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
    public final void setAscendingSortOrder(final boolean ascendingSortOrder) {
        page.setSortAscending(ascendingSortOrder);
    }

    /**
     * Retrieves all the Paging information required for the request.
     *
     * @return Paginatable Object for this request
     */
    public final Page getPagingInformation() {
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
