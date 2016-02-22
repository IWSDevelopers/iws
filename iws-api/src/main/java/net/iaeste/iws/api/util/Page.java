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
@XmlType(name = "page", propOrder = { "pageNumber", "pageSize", "sortAscending", "sortBy" })
public final class Page implements Paginatable, Serializable {

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
