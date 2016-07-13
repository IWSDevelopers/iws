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
package net.iaeste.iws.persistence.views;

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
 * @since   IWS 1.0
 */
public interface IWSView {

    /**
     * The transformation of a View to an Entity ot DTO may have to be done via
     * a different process, rather than using an InstanceOf to handle this, it
     * should be done by a process controlled within the Views. This method will
     * return the desired Transformation Process to be used.
     *
     * @return Preferable Transformation Method
     */
    Transfomer getTransformer();

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

    /**
     * For the Transformation of the Views to other Objects, different
     * transformation methods may be needed. This enum is present to let the
     * individual views determine this themselves and then leave it to the
     * Transformation Process to deal with the actual implementation.
     */
    enum Transfomer {
        DEFAULT,
        OFFER,
        SHAREDOFFER
    }
}
