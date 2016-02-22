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
package net.iaeste.iws.api.enums;

import javax.xml.bind.annotation.XmlType;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "groupStatus")
public enum GroupStatus {

    /**
     * <p>The default value for all Groups, upon creation. It means that the
     * Group can be used and all data accessed without restrictions.</p>
     */
    ACTIVE,

    /**
     * <p>Groups, which are currently suspended, will not be visible or
     * accessible. If the Group is a Members Group, the users from this Group
     * can no longer log into the system. The purpose of this option, is to
     * prevent misuse of the system, without deleting the information. A Group
     * with status Blocked, can always be reactivated.</p>
     */
    SUSPENDED,

    /**
     * <p>Groups, which are Deleted, can no longer be used. Data belonging to
     * the Group will be deleted, and if the Group is a Members Group, then the
     * current members can no longer use the IWS, as this translates to their
     * accounts being deleted as well.</p>
     *
     * <p>Shared information belonging to the Group, will not be deleted from
     * the system.</p>
     *
     * <p><i>Note;</i> This is a non-reversible state.</p>
     */
    DELETED
}
