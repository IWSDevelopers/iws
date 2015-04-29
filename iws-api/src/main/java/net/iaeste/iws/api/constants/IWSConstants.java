/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
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
     * The default encoding used for all processing of strings. The main reason
     * why we're forcing a rather old encoding, which is limited in characters
     * that it can represent is simple. If someone uses special characters, then
     * the recipients of the information may not be able to understand or read
     * it properly.<br />
     *   Also, it happens all too often that data is getting corrupted while
     * being processed, simply because of strange character sets being
     * used.<br />
     *   Latin9 was chosen as default, as it supports the Euro sign. The Euro
     * is one of the largest currencies being used.
     */
    String DEFAULT_ENCODING = "ISO-8859-15";

    /**
     * As IAESTE is an English organization, the default locale is also English.
     */
    Locale DEFAULT_LOCALE = Locale.ENGLISH;

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
    String EMAIL_REGEX = "^[a-zA-Z0-9_\\.\\-\\+ !#\\$%&'\\*/=\\?\\^`\\{\\}\\|~]+@([a-zA-Z0-9_\\-]+\\.)*[a-zA-Z0-9]{2,}$";

    /**
     * The compiled e-mail pattern to use. Note, that the Pattern class is safe
     * to precompile into a Global constant, whereas the Matcher must be
     * generated on a per-thread basis.
     */
    Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * XML files are generally very error tolerant, however - certain characters
     * exists, which is referred to as Control Characters. These can cause
     * problems, and should thus be removed from the XML. According to
     * <a href="http://www.w3.org/TR/REC-xml/#charsets">W3C</a>, the not allowed
     * characters should be stripped. At best Server Side, or even better as
     * part of the data input validation.<br />
     *   Illegal or not-allowed characters can occur in text, when something is
     * copy'n'pasted from programs such as Microsoft Word.<br />
     *   This regular expression will detect invalid space characters, which can
     * then be used by the Sanitizer method, which is part of the IWS
     * Verification framework.<br>
     * <ul>
     *   <li><b>28 (0x1C)</b> FS or File Separator</li>
     *   <li><b>29 (0x1D)</b> GS or Group Separator</li>
     *   <li><b>30 (0x1E)</b> RS or Record Separator</li>
     *   <li><b>31 (0x1F)</b> US or Unit Separator</li>
     * </ul>
     */
    String REGEX_INVALID_SPACES = "[\\x1C\\x1D\\x1E\\x1F]";

    /**
     * The compiled invalid newline pattern to use. Note, that the Pattern class
     * is safe to precompile into a Global constant, whereas the Matcher must be
     * generated on a per-thread basis.
     */
    Pattern PATTERN_INVALID_SPACES = Pattern.compile(REGEX_INVALID_SPACES);

    /**
     * XML files are generally very error tolerant, however - certain characters
     * exists, which is referred to as Control Characters. These can cause
     * problems, and should thus be removed from the XML. According to
     * <a href="http://www.w3.org/TR/REC-xml/#charsets">W3C</a>, the not allowed
     * characters should be stripped. At best Server Side, or even better as
     * part of the data input validation.<br />
     *   Illegal or not-allowed characters can occur in text, when something is
     * copy'n'pasted from programs such as Microsoft Word.<br />
     *   This regular expression will detect invalid newline characters, which
     * can then be used by the Sanitizer method, which is part of the IWS
     * Verification framework.<br>
     * <ul>
     *   <li><b>11 (0x0B)</b> LF or Line Feed</li>
     *   <li><b>12 (0x0C)</b> FF or Form Feed</li>
     * </ul>
     */
    String REGEX_INVALID_NEWLINES = "[\\x0B\\x0C]";

    /**
     * The compiled invalid newline pattern to use. Note, that the Pattern class
     * is safe to precompile into a Global constant, whereas the Matcher must be
     * generated on a per-thread basis.
     */
    Pattern PATTERN_INVALID_NEWLINES = Pattern.compile(REGEX_INVALID_NEWLINES);

    /**
     * XML files are generally very error tolerant, however - certain characters
     * exists, which is referred to as Control Characters. These can cause
     * problems, and should thus be removed from the XML. According to
     * <a href="http://www.w3.org/TR/REC-xml/#charsets">W3C</a>, the not allowed
     * characters should be stripped. At best Server Side, or even better as
     * part of the data input validation.<br />
     *   Illegal or not-allowed characters can occur in text, when something is
     * copy'n'pasted from programs such as Microsoft Word.<br />
     *   This regular expression will detect invalid bell characters, which can
     * then be used by the Sanitizer method, which is part of the IWS
     * Verification framework.<br>
     * <ul>
     *   <li><b>04 (0x04)</b> EOT or End of Transmission</li>
     *   <li><b>07 (0x07)</b> Bell</li>
     *   <li><b>23 (0x17)</b> ETB or End of Transmission Block</li>
     * </ul>
     */
    String REGEX_INVALID_CHARS = "[\\x04\\x08\\x17]";

    /**
     * The compiled invalid bell pattern to use. Note, that the Pattern class
     * is safe to precompile into a Global constant, whereas the Matcher must be
     * generated on a per-thread basis.
     */
    Pattern PATTERN_INVALID_CHARS = Pattern.compile(REGEX_INVALID_CHARS);

    /**
     * The minimal lenghth for a password which the user is choosing must be
     * this long.
     */
    int MINIMAL_PASSWORD_LENGTH = 6;

    /**
     * Passwords must have a minimal length, using whatever characters the user
     * prefers. Although it may make sense to force control of different
     * character groups, we also do not wish to force users to use complex
     * passwords which they'll forget. The online comic
     * <a href="http://xkcd.com/936/">XKCD</a> have a wonderful little cartoon
     * on the matter.<br />
     *   Rainbow attacks will not be possible, regardless of how simple a
     * password a user chooses, since we're salting all incoming Passwords with
     * a two-factor salt. Hence, we trust that users are competent enough at
     * choosing passwords, which will protect their access.
     */
    String PASSWORD_REGEX = "^.{" + MINIMAL_PASSWORD_LENGTH + ",}$";

    /**
     * The compiled password pattern to use. Note, that the Pattern class is
     * safe to precompile into a Global constant, whereas the Matcher must be
     * generated on a per-thread basis.
     */
    Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

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
     * Default IWS Date Format. The format was chosen as it gives the fewest
     * problems for users, regarding understanding it.
     */
    String DATE_FORMAT = "dd-MMM-yyyy";

    /**
     * Default IWS DateTime Format.
     */
    String DATE_TIME_FORMAT = DATE_FORMAT + " HH:mm:ss";

    /**
     * Default IWS Success message.
     */
    String SUCCESS = "OK";
}
