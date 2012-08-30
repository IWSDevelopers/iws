/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.constants.IWSConstants
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.constants;

import java.util.Locale;

/**
 * Generic constants, for the IW Services.
 *
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public interface IWSConstants {

    /**
     * All serialized classes should use this value. The value reflects the
     * current version of the system. If updates are made in one or more of the
     * serialized classes, it should be updated.<br />
     * If not used, then all classes that are serialized will have a runtime
     * performance overhead while calculating a UID, which matches the current
     * class. See "Effective Java, 2nd Edition" - Item 75.<br />
     * As this is a useless overhead and simple to avoid, it is recommended to
     * do so. Just add the following line in the beginning of all serialized
     * and derived classes:<br /><br />
     *
     * {@code private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;}
     */
    long SERIAL_VERSION_UID = 201201010010000L; // YYYYMMDDvvvnnnn

    /**
     * The default encoding used for all processing of strings.
     */
    String DEFAULT_ENCODING = "ISO-8859-15";

    /**
     * As IAESTE is an English organization, the default locale is also English.
     */
    Locale DEFAULT_LOCALE = Locale.ENGLISH;

    /**
     * A non-zero, odd number used as the initial value, when generating
     * HashCode values. See Item 9, from Effective Java 2nd Edition by
     * Joshua Bloch.
     */
    int HASHCODE_INITIAL_VALUE = 17;

    /**
     * A non-zero, odd number used as the multiplier, when generating
     * HashCode values. See Item 9, from Effective Java 2nd Edition by
     * Joshua Bloch.
     */
    int HASHCODE_MULTIPLIER = 31;

    /**
     * Default IWBase Date Format.
     */
    String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Default IWBase Success message.
     */
    String SUCCESS = "OK";

    /**
     * The e-mail compliance regular expression.
     */
    String EMAIL_REGEX = "^[a-z0-9_\\-]+(\\.[_a-z0-9\\-]+)*@([_a-z0-9\\-]+\\.)+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel|eu|mobi)$";

    /**
     * For the automatic password generator, we need a list of characters, that
     * cannot be misinterpreted when read, i.e. l and 1 should not be in the
     * list, since they can too easily be misstaten for eachother.
     */
    String PASSWORD_GENERATOR_CHARACTERS = "abcdefghjkmnpqrstuvwxzy23456789";

    /**
     * The default length to use for the automatically generated passwords.
     */
    int DEFAULT_PASSWORD_LENGTH = 8;

}
