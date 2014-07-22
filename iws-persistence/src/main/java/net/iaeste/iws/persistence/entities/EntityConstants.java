/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.EntityConstants
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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
 *   Note, JQL cannot reference values that in anyway can be overwritten. Which
 * means that an Interface constant class cannot be derefenced.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class EntityConstants {

    private static final char DELIMITER = '\'';

    /**
     * Private Constructor, this is a utility class.
     */
    private EntityConstants() {
    }

    // =========================================================================
    // Constant Mapping of the Group Type values
    // =========================================================================

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
     * Status value for Suspended; {@link net.iaeste.iws.api.enums.GroupStatus#SUSPENDED}.
     */
    public static final String GROUP_STATUS_SUSPENDED = DELIMITER + "SUSPENDED" + DELIMITER;

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
     * The pre-defined Role Owner; {@link net.iaeste.iws.api.constants.IWSConstants#ROLE_OWNER}.
     */
    public static final int ROLE_OWNER = 1;

    /**
     * The pre-defined Role Owner; {@link net.iaeste.iws.api.constants.IWSConstants#ROLE_MODERATOR}.
     */
    public static final int ROLE_MODERATOR = 2;

    /**
     * The pre-defined Role Owner; {@link net.iaeste.iws.api.constants.IWSConstants#ROLE_MEMBER}.
     */
    public static final int ROLE_MEMBER = 3;

    /**
     * The pre-defined Role Owner; {@link net.iaeste.iws.api.constants.IWSConstants#ROLE_STUDENT}.
     */
    public static final int ROLE_STUDENT = 5;

    /**
     * The name of the Attachement for Applications in the Attachment Table.
     */
    public static final String STUDENT_APPLICATIONS_ATTACHMENT = DELIMITER + "student_applications" + DELIMITER;
}
