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

/**
 * The IWS Database is using the String value from the enumerated types, to make
 * it easier to decipher the content of the database, rather than the ordinal
 * values. However, JPA can only read out constant values and the name of an
 * enumerated type is considered a variable, not a fixed constant. And as we
 * wish to prevent more bugs like the Trac #854, all String comparisons in the
 * JPA queries should be made with constant values that is tested and verified
 * against the existing enum types.
 *   If any changes is made to the String value of an enumerated type, then this
 * Constant class must be updated to reflect this, and so must the database,
 * since the three parts must always be synchronized!
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class EntityConstants {

    private static final char DELIMITER = '\'';

    // =========================================================================
    // Constant Mapping of the Group Type values
    // =========================================================================

    /**
     * GroupType value for Members; {@link net.iaeste.iws.api.enums.GroupType#ADMINISTRATION}.
     */
    public static final String GROUPTYPE_ADMINISTRATION = DELIMITER + "ADMINISTRATION" + DELIMITER;

    /**
     * GroupType value for Members; {@link net.iaeste.iws.api.enums.GroupType#MEMBER}.
     */
    public static final String GROUPTYPE_MEMBER = DELIMITER + "MEMBER" + DELIMITER;

    /**
     * GroupType value for International; {@link net.iaeste.iws.api.enums.GroupType#INTERNATIONAL}.
     */
    public static final String GROUPTYPE_INTERNATIONAL = DELIMITER + "INTERNATIONAL" + DELIMITER;

    /**
     * GroupType value for National Committees; {@link net.iaeste.iws.api.enums.GroupType#NATIONAL}.
     */
    public static final String GROUPTYPE_NATIONAL = DELIMITER + "NATIONAL" + DELIMITER;

    /**
     * GroupType value for Local Committees; {@link net.iaeste.iws.api.enums.GroupType#LOCAL}.
     */
    public static final String GROUPTYPE_LOCAL = DELIMITER + "LOCAL" + DELIMITER;

    /**
     * GroupType value for Workgroups; {@link net.iaeste.iws.api.enums.GroupType#WORKGROUP}.
     */
    public static final String GROUPTYPE_WORKGROUP = DELIMITER + "WORKGROUP" + DELIMITER;

    /**
     * GroupType value for Students; {@link net.iaeste.iws.api.enums.GroupType#STUDENT}.
     */
    public static final String GROUPTYPE_STUDENT = DELIMITER + "STUDENT" + DELIMITER;

    // =========================================================================
    // Constant Mapping of the Group Status values
    // =========================================================================

    /**
     * Status value for Active; {@link net.iaeste.iws.api.enums.GroupStatus#ACTIVE}.
     */
    public static final String GROUP_STATUS_ACTIVE = DELIMITER + "ACTIVE" + DELIMITER;

    /**
     * Status value for Deleted; {@link net.iaeste.iws.api.enums.GroupStatus#DELETED}.
     */
    public static final String GROUP_STATUS_DELETED = DELIMITER + "DELETED" + DELIMITER;

    // =========================================================================
    // Constant Mapping of the User Status values
    // =========================================================================

    /**
     * Status value for New; {@link net.iaeste.iws.api.enums.UserStatus#NEW}.
     */
    public static final String USER_STATUS_NEW = DELIMITER + "NEW" + DELIMITER;

    /**
     * Status value for Active; {@link net.iaeste.iws.api.enums.UserStatus#ACTIVE}.
     */
    public static final String USER_STATUS_ACTIVE = DELIMITER + "ACTIVE" + DELIMITER;

    /**
     * Status value for Suspended; {@link net.iaeste.iws.api.enums.UserStatus#SUSPENDED}.
     */
    public static final String USER_STATUS_SUSPENDED = DELIMITER + "SUSPENDED" + DELIMITER;

    /**
     * Status value for Deleted; {@link net.iaeste.iws.api.enums.UserStatus#DELETED}.
     */
    public static final String USER_STATUS_DELETED = DELIMITER + "DELETED" + DELIMITER;

    // =========================================================================
    // Other non-enum constant values
    // =========================================================================

    /**
     * The pre-defined Role Owner; {@see EntityConstants#ROLE_OWNER}.
     */
    public static final int ROLE_OWNER = 1;

    /**
     * The pre-defined Role Owner; {@see EntityConstants#ROLE_MODERATOR}.
     */
    public static final int ROLE_MODERATOR = 2;

    /**
     * The pre-defined Role Owner; {@see EntityConstants#ROLE_MEMBER}.
     */
    public static final int ROLE_MEMBER = 3;

    /**
     * The pre-defined Role Owner; {@see EntityConstants#ROLE_STUDENT}.
     */
    public static final int ROLE_STUDENT = 5;

    /**
     * The name of the Attachment for Applications in the Attachment Table.
     */
    public static final String STUDENT_APPLICATIONS_ATTACHMENT = "student_applications";

    /**
     * Private Constructor, this is a utility class.
     */
    private EntityConstants() {
    }
}
