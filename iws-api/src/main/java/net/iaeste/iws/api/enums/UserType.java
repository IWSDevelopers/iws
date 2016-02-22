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
 * The different types of Users we have. From IW3, the values were added as part
 * of release 1.13, and the following was found in the SQL update script:
 * <pre>
 *   ALTER TABLE users ADD COLUMN type VARCHAR(1);
 *   ALTER TABLE users ALTER COLUMN type SET DEFAULT 'v';
 *   CHANGE_NS users SET type =
 *     CASE
 *       WHEN volunteer = 't' THEN 'v'
 *       WHEN volunteer = 'f' THEN 'e'
 *       ELSE 'x'
 *     END;
 *   ALTER TABLE users DROP COLUMN volunteer;
 * </pre>
 * <p>Based on the above SQL changes, it is assumed that we have three types.
 * During the migration, it was discovered that 30 accounts were having type
 * 'x', interpreted as Unknown.</p>
 *
 * <p>In IWS, we've added two more types, student &amp; functional. which
 * complements the previous ones to provide a more suitable range of Accounts,
 * where rules can later be added to these, to improve internal data
 * management.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "userType")
public enum UserType implements Descriptable<UserType> {

    /**
     * By default, all new Accounts are made with type Volunteer. A Volunteer is
     * defined as someone associated (working for) IAESTE, but is not payed for
     * their services.
     */
    VOLUNTEER("Volunteer"),

    /**
     * All users who are payed for their IAESTE work are listed with type
     * Employed.
     */
    EMPLOYED("Employed"),

    /**
     * All User Accounts solely for Students are listed with this type. Students
     * are
     */
    STUDENT("Student"),

    /**
     * Some Accounts are purely used with automated tools, such as cron jobs.
     * These Accounts can be treated differently than other accounts.
     */
    FUNCTIONAL("Functional"),

    /**
     * The fallback type, if nothing else is provided. The Unknown type should
     * never be used.
     */
    UNKNOWN("Unknown");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    UserType(final String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }
}
