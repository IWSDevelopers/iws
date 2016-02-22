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
package net.iaeste.iws.persistence.entities;

import java.util.Date;

/**
 * Classes implementing this interface, are capable of updating the current
 * content, with the content of a second IWSEntity of the same type (and Id).
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Updateable<T> extends IWSEntity {

    /**
     * To check whether an Entity contains any changes that needs persisting or
     * not. To avoid unnecessary merges.<br />
     *   Note, not all fields may be needed for the diff, hence it must not be
     * implemented like a standard equals or hashCode comparison.
     *
     * @param obj Object to compare against
     * @return true of they differ, otherwise false
     */
    boolean diff(T obj);

    /**
     * Allows a merge between two objects of the same type. The method updates
     * the current object with the changes from the second.<br />
     *   Both Objects must be persisted beforehand, i.e. have Id values, and
     * these Id's differ, no merge will take place. Merging is purely intended
     * for updating Objects, where we wish to control which fields are updated.
     *
     * @param obj Object to merge changes from
     */
    void merge(T obj);

    /**
     * To ensure that the field modified is always updated, it must be
     * controlled by the DAO, as part of the update process. Hence, it must be
     * possible to set the Modified field to a given Date.
     *
     * @param modified Date of Modification
     */
    void setModified(Date modified);

    Date getModified();
}
