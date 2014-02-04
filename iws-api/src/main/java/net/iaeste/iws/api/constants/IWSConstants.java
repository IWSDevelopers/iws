/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Generic constants, for the IW Services.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
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
    long SERIAL_VERSION_UID = 201402020010000L; // YYYYMMDDvvvnnnn

    /**
     * The default encoding used for all processing of strings.
     */
    String DEFAULT_ENCODING = "ISO-8859-15";

    /**
     * As IAESTE is an English organization, the default locale is also English.
     */
    Locale DEFAULT_LOCALE = Locale.ENGLISH;

    /**
     * Passwords in the IWS must be at least this long.
     */
    int MINIMAL_PASSWORD_LENGTH = 5;

    /**
     * The maximum number of concurrently active tokens in the system. Meaning
     * that only so many people can be logged in at the same time.<br />
     *   The number is added to prevent that too many simultaneous users may
     * overload the system. The tokens are kept in-memory - since read-only
     * requests cannot update the database.
     */
    int MAX_ACTIVE_TOKENS = 500;

    /**
     * Sessions will time-out after a number of minutes being idle. Meaning that
     * no activity took place using the account. The value is set to 8 hours per
     * default, so inactivity during a normal Office workday shouldn't cause any
     * problems.
     */
    long MAX_SESSION_IDLE_PERIOD = 28800000L;

    /**
     * The maximum number of times a user may attempt to login with incorrect
     * password, before the system will close the account temporarily. The
     * duration for the blocking is specified in {@link #LOGIN_BLOCKING_PERIOD}.
     * Once the duration is over, the count is reset and the user may again
     * attempt at login in.<br />
     *   The maximum retries count is added, to prevent someone from performing
     * Denial Of Server based brute force attacks against the system. All the
     * requests are kept in memory, and nothing is persisted, meaning that only
     * a restart of the system will reset these.
     */
    int MAX_LOGIN_RETRIES = 5;

    /**
     * The amount of minutes that a user account is blocked, if too many invalid
     * requests were made. After this period of time, it is again possible to
     * attempt to login.<br />
     *   The time is specified in milli seconds.
     */
    long LOGIN_BLOCKING_PERIOD = 1800000L;

    /**
     * The year that IAESTE was founded.
     */
    int FOUNDING_YEAR = 1948;

    /**
     * The public e-mail address, is for all public mailinglists and for all
     * user aliases. The address is open for all e-mails by default. If a Group
     * or User is suspended or removed, then the public address will cease to
     * work.
     */
    String PUBLIC_EMAIL_ADDRESS = "iaeste.org";

    /**
     * The private e-mail address, is for all groups. This will allow a group to
     * have a private way to communicate. If a Group is suspended or removed,
     * then the private address will cease to work.
     */
    String PRIVATE_EMAIL_ADDRESS = "iaeste.net";

    String NCS_LIST_NAME = "ncs";

    String BASE_URL = "https://www.iaeste.net/intraweb";
    String IWS_EMAIL_SENDER = "intraweb@iaeste.net";

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
     * Default IWS Date Format.
     */
    String DATE_FORMAT = "dd-MMM-yyyy";

    /**
     * Default IWS DateTime Format.
     */
    String DATE_TIME_FORMAT = DATE_FORMAT + " HH:mm:ss";

    /**
     * DateFormat Object for formatting and parsing Date Object.
     */
    DateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT, DEFAULT_LOCALE);

    /**
     * Default IWS Success message.
     */
    String SUCCESS = "OK";

    /**
     * Passwords must be at least 6 characters long, using whatever characters
     * the user prefers. Although it may make sense to force control of
     * different character groups, we also do not wish to force users to use
     * complex passwords which they'll forget. The online comic
     * <a href="http://xkcd.com/936/">XKCD</a> have a wonderful little cartoon
     * on the matter.<br />
     *   Rainbow attacks will not be possible, regardless of how simple a
     * password a user chooses, since we're salting all incoming Passwords with
     * a two-factor salt. Hence, we trust that users are competent enough at
     * choosing passwords, which will protect their access.
     */
    String PASSWORD_REGEX = "^.{6,}$";
    Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    /**
     * E-mail addresses have changed numerous times over the years. To ensure
     * that they are valid, we need a simple regex. Unfortunately, the constant
     * changes to what is allowed and what isn't means that any regex will
     * eventually fail. For this reason, we've tried to keep it simple yet able
     * to allow at least 99% of all valid addresses.rb />
     *   See <a href="http://en.wikipedia.org/wiki/Email_address">Wikipedia</a>
     * for more information.<br />
     *   This regex does not support the quotation rules, which makes the rule
     * check more complicated, nor does it support IPv6 addresses.
     */
    String EMAIL_REGEX = "^[a-z0-9_\\.\\-\\+ !#\\$%&'\\*/=\\?\\^`\\{\\}\\|~]+@([_a-z0-9\\-]+\\.)*[a-z0-9]{2,}$";
    Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * For the automatic password generator, we need a list of characters, that
     * cannot be misinterpreted when read, i.e. l and 1 should not be in the
     * list, since they can too easily be mistaken for each other.
     */
    String PASSWORD_GENERATOR_CHARACTERS = "abcdefghjkmnpqrstuvwxzy23456789";

    /**
     * The default length to use for the automatically generated passwords.
     */
    int DEFAULT_PASSWORD_LENGTH = 8;

    Long ROLE_OWNER = 1L;
    Long ROLE_MODERATOR = 2L;
    Long ROLE_MEMBER = 3L;
    Long ROLE_STUDENT = 5L;
}
