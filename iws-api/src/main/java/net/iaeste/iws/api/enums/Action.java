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
 * <p>When a IWS Processing Request is made, then it can sometimes be hard from
 * the context to guess what needs to be done. And to avoid that we split up the
 * Processing requests in more special parts, an Action is instead assigned, so
 * it is possible to tell what should be done.</p>
 *
 * <p>The Actions described in this Enum is of a more general nature, not all
 * Actions apply to the same Request, which is why all Processing Requests is
 * also having a list of allowed Actions, which the requested Action is checked
 * against.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@XmlType(name = "action")
public enum Action {

    /**
     * Create a new Object, depending on the Context, it can be Group, User or
     * new Committee, i.e. Co-operating Institution or anything else.
     */
    CREATE,

    /**
     * Updating a Committee, i.e. change Institution Name &amp; Abbreviation.
     */
    UPDATE,

    /**
     * Upgrade a Committee from Cooperating Institution to Associate Member,
     * if there is currently only a single Cooperating Institution for the
     * given Country, or upgrades an Associate Member to Full Member.
     */
    UPGRADE,

    /**
     * Processing a record means either Creating a new record or update an
     * existing, based on the current state of the system.
     */
    PROCESS,

    /**
     * Allows a Data Object with location information to be moved, i.e. a Folder
     * or a File can be moved from one Folder to another, provided both have the
     * same Group ownership.
     */
    MOVE,

    /**
     * Change the National Secretary.
     */
    CHANGE_NS,

    /**
     * Upgrades a Committee from Cooperating Institution to Associate Member.
     */
    MERGE,

    /**
     * Activate a currently Suspended Committee.
     */
    ACTIVATE,

    /**
     * Suspend a currently Active Committee.
     */
    SUSPEND,

    /**
     * <p>Deletes a record from the system. For Users, only NEW or SUSPENDED
     * Users can be deleted, otherwise the rule apply that only suspended Users,
     * Groups or Committees can be deleted.</p>
     *
     * <p>Deletion may also be done against other data, where the process
     * of deleting it will determine how much is erased.</p>
     */
    DELETE,

    /**
     * <p>Shares are removed, not deleted. This is used when existing shares
     * should be removed.</p>
     */
    REMOVE
}
