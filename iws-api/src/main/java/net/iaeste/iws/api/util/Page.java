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
import net.iaeste.iws.api.enums.SortingOrder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "page", propOrder = { "pageNumber", "pageSize", "sortOrder", "sortBy" })
public final class Page implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * The maximum allowed number of objects to be retrieved in a single
     * request.
     */
    private static final int MAX_PAGE_SIZE = 100;

    /**
     * The first page to be read out is starting with 1.
     */
    private static final int FIRST_PAGE = 1;

    private int pageNumber;
    private int pageSize;
    private SortingOrder sortOrder;
    private SortingField sortBy;

    /**
     * Empty Constructor.
     */
    public Page() {
        pageNumber = FIRST_PAGE;
        pageSize = MAX_PAGE_SIZE;

        // Default, we're sorting based on creation in descending order,
        // meaning that the most recently created Object is the first to
        // be returned.
        sortOrder = SortingOrder.DESC;
        sortBy = SortingField.CREATED;
    }

    /**
     * Default Constructor, sets all Paginating fields.
     *
     * @param pageNumber  The current page to fetch, starting from 0 (zero)
     * @param pageSize    The max number of records on each page
     * @param sortOrder   Sorting Order
     * @param sortBy      Sorting Field
     */
    public Page(final int pageNumber, final int pageSize, final SortingOrder sortOrder, final SortingField sortBy) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortOrder = sortOrder;
        this.sortBy = sortBy;
    }

    public void setPageNumber(final int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Retrieves the Current Page Number for this fetch request.
     *
     * @return Current Page number
     */
    public int pageNumber() {
        return pageNumber;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Retrieves the Current Page Size for this fetch request.
     *
     * @return Current Page Size
     */
    public int pageSize() {
        return pageSize;
    }

    /**
     * Sets the Sorting Order for the page.
     *
     * @param sortOrder Sorting Order
     */
    public void setSortOrder(final SortingOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Retrieves the Current Sort Order for this fetch request.
     *
     * @return Sorting Order
     */
    public SortingOrder sortOrder() {
        return sortOrder;
    }

    public void setSortBy(final SortingField sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * Retrieves the Current Sort By Field. Note, that all fields that is
     * allowed to be sorted by is defined in the SortingField enumeration.
     * However, the actually allowed values are request specific, so please
     * check the documentation for the setting to see which fields may be used.
     *
     * @return Current Sort By Field
     */
    public SortingField sortBy() {
        return sortBy;
    }
}
