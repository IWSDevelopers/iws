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
