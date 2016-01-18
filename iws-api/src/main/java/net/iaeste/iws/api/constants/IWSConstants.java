/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
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
 * <p>Generic constants, for the IW Services. Note, that although using a
 * Constant Interface is considered
 * <a href="https://en.wikipedia.org/wiki/Constant_interface">bad practice</a>,
 * it only applies if the classes implements it rather than imports the
 * constants, as you would via a Constant Class. Using the Interface has the
 * advantage, that the constants cannot be mixed up with utility
 * functionality.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class IWSConstants {

    /** Private Constructor, this is a Constants Class. */
    private IWSConstants() {
    }

    /**
     * <p>All serialized classes should use this value. The value reflects the
     * current version of the system. If updates are made in one or more of the
     * serialized classes, it should be updated.</p>
     *
     * <p>If not used, then all classes that are serialized will have a runtime
     * performance overhead while calculating a UID, which matches the current
     * class. See "Effective Java, 2nd Edition" - Item 75.</p>
     *
     * <p>As this is a useless overhead and simple to avoid, it is recommended to
     * do so. Just add the following line in the beginning of all serialized
     * and derived classes:</p>
     *
     * {@code private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;}
     */
    public static final long SERIAL_VERSION_UID = 201510260010000L;//// YYYYMMDDvvvnnnn

    /**
     * <p>The default encoding used for all processing of strings. The main
     * reason why we're forcing a rather old encoding, which is limited in
     * characters that it can represent is simple. If someone uses special
     * characters, then the recipients of the information may not be able to
     * understand or read it properly.</p>
     *
     * <p>Also, it happens all too often that data is getting corrupted while
     * being processed, simply because of strange character sets being used.</p>
     *
     * <p>Latin9 was chosen as default, as it supports the Euro sign. The Euro
     * is one of the largest currencies being used.</p>
     */
    public static final String DEFAULT_ENCODING = "ISO-8859-15";

    /**
     * <p>As IAESTE is an English organization, the default locale is also
     * English.</p>
     */
    public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    /**
     * <p>E-mail addresses have changed numerous times over the years. To ensure
     * that they are valid, we need a simple regex. Unfortunately, the constant
     * changes to what is allowed and what isn't means that any regex will
     * eventually fail. For this reason, we've tried to keep it simple yet able
     * to allow at least 99% of all valid addresses.</p>
     *
     * <p>See <a href="http://en.wikipedia.org/wiki/Email_address">Wikipedia</a>
     * for more information.</p>
     *
     * <p>This regex does not support the quotation rules, which makes the rule
     * check more complicated, nor does it support IPv6 addresses.</p>
     */
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_\\.\\-\\+ !#\\$%&'\\*/=\\?\\^`\\{\\}\\|~]+@([a-zA-Z0-9_\\-]+\\.)*[a-zA-Z0-9]{2,}$";

    /**
     * <p>The compiled e-mail pattern to use. Note, that the Pattern Class is
     * safe to pre-compile into a Global constant, whereas the Matcher must be
     * generated on a per-thread basis.</p>
     */
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * <p>XML files are generally very error tolerant, however - certain
     * characters exists, which is referred to as Control Characters. These can
     * cause problems, and should thus be removed from the XML. According to
     * <a href="http://www.w3.org/TR/REC-xml/#charsets">W3C</a>, the not allowed
     * characters should be stripped. At best Server Side, or even better as
     * part of the data input validation.</p>
     *
     * <p>Illegal or not-allowed characters can occur in text, when something is
     * copy'n'pasted from programs such as Microsoft Word.</p>
     *
     * <p>This regular expression will detect invalid space characters, which can
     * then be used by the Sanitizer method, which is part of the IWS
     * Verification framework.</p>
     * <ul>
     *   <li><b>28 (0x1C)</b> FS or File Separator</li>
     *   <li><b>29 (0x1D)</b> GS or Group Separator</li>
     *   <li><b>30 (0x1E)</b> RS or Record Separator</li>
     *   <li><b>31 (0x1F)</b> US or Unit Separator</li>
     * </ul>
     */
    public static final String REGEX_INVALID_SPACES = "[\\x1C\\x1D\\x1E\\x1F]";

    /**
     * <p>The compiled invalid newline pattern to use. Note, that the Pattern
     * Class is safe to pre-compile into a Global constant, whereas the Matcher
     * must be generated on a per-thread basis.</p>
     */
    public static final Pattern PATTERN_INVALID_SPACES = Pattern.compile(REGEX_INVALID_SPACES);

    /**
     * <p>XML files are generally very error tolerant, however - certain
     * characters exists, which is referred to as Control Characters. These can
     * cause problems, and should thus be removed from the XML. According to
     * <a href="http://www.w3.org/TR/REC-xml/#charsets">W3C</a>, the not allowed
     * characters should be stripped. At best Server Side, or even better as
     * part of the data input validation.</p>
     *
     * <p>Illegal or not-allowed characters can occur in text, when something is
     * copy'n'pasted from programs such as Microsoft Word.</p>
     *
     * <p>This regular expression will detect invalid newline characters, which
     * can then be used by the Sanitizer method, which is part of the IWS
     * Verification framework.</p>
     * <ul>
     *   <li><b>11 (0x0B)</b> LF or Line Feed</li>
     *   <li><b>12 (0x0C)</b> FF or Form Feed</li>
     * </ul>
     */
    public static final String REGEX_INVALID_NEWLINES = "[\\x0B\\x0C]";

    /**
     * <p>The compiled invalid space pattern to use. Note, that the Pattern
     * Class is safe to pre-compile into a Global constant, whereas the Matcher
     * must be generated on a per-thread basis.</p>
     */
    public static final Pattern PATTERN_INVALID_NEWLINES = Pattern.compile(REGEX_INVALID_NEWLINES);

    /**
     * <p>XML files are generally very error tolerant, however - certain
     * characters exists, which is referred to as Control Characters. These can
     * cause problems, and should thus be removed from the XML. According to
     * <a href="http://www.w3.org/TR/REC-xml/#charsets">W3C</a>, the not allowed
     * characters should be stripped. At best Server Side, or even better as
     * part of the data input validation.</p>
     *
     * <p>Illegal or not-allowed characters can occur in text, when something is
     * copy'n'pasted from programs such as Microsoft Word.</p>
     *
     * <p>This regular expression will detect invalid bell characters, which can
     * then be used by the Sanitizer method, which is part of the IWS
     * Verification framework.</p>
     * <ul>
     *   <li><b>04 (0x04)</b> EOT or End of Transmission</li>
     *   <li><b>07 (0x07)</b> Bell</li>
     *   <li><b>23 (0x17)</b> ETB or End of Transmission Block</li>
     * </ul>
     */
    public static final String REGEX_INVALID_CHARS = "[\\x04\\x07\\x17]";

    /**
     * <p>The compiled invalid char pattern to use. Note, that the Pattern Class
     * is safe to pre-compile into a Global constant, whereas the Matcher must be
     * generated on a per-thread basis.</p>
     */
    public static final Pattern PATTERN_INVALID_CHARS = Pattern.compile(REGEX_INVALID_CHARS);

    /**
     * <p>The minimal length for a password which the user is choosing must be
     * this long.</p>
     */
    public static final int MINIMAL_PASSWORD_LENGTH = 6;

    /**
     * <p>Passwords must have a minimal length, using whatever characters the
     * user prefers. Although it may make sense to force control of different
     * character groups, we also do not wish to force users to use complex
     * passwords which they'll forget. The online comic
     * <a href="http://xkcd.com/936/">XKCD</a> have a wonderful little cartoon
     * on the matter.</p>
     *
     * <p>Rainbow attacks will not be possible, regardless of how simple a
     * password a user chooses, since we're salting all incoming Passwords with
     * a two-factor salt. Hence, we trust that users are competent enough at
     * choosing passwords, which will protect their access.</p>
     */
    public static final String PASSWORD_REGEX = "^.{" + MINIMAL_PASSWORD_LENGTH + ",}$";

    /**
     * <p>The compiled password pattern to use. Note, that the Pattern Class is
     * safe to pre-compile into a Global constant, whereas the Matcher must be
     * generated on a per-thread basis.</p>
     */
    public static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    /**
     * <p>The year that IAESTE was founded.</p>
     */
    public static final int FOUNDING_YEAR = 1948;

    /**
     * <p>The public e-mail address, is for all public mailing lists and for all
     * user aliases. The address is open for all e-mails by default. If a Group
     * or User is suspended or removed, then the public address will cease to
     * work.</p>
     */
    public static final String PUBLIC_EMAIL_ADDRESS = "iaeste.org";

    /**
     * <p>The private e-mail address, is for all groups. This will allow a group
     * to have a private way to communicate. If a Group is suspended or removed,
     * then the private address will cease to work.</p>
     */
    public static final String PRIVATE_EMAIL_ADDRESS = "iaeste.net";

    public static final String MAILING_LIST_NCS_NAME = "ncs";
    public static final String MAILING_LIST_ANNOUNCE = "announce";

    public static final String BASE_URL = "https://www.iaeste.net/intraweb";
    public static final String IWS_EMAIL_SENDER = "intraweb@iaeste.net";

    /**
     * <p>A non-zero, odd number used as the initial value, when generating
     * HashCode values. See Item 9, from Effective Java 2nd Edition by
     * Joshua Bloch.</p>
     */
    public static final int HASHCODE_INITIAL_VALUE = 17;

    /**
     * <p>A non-zero, odd number used as the multiplier, when generating
     * HashCode values. See Item 9, from Effective Java 2nd Edition by
     * Joshua Bloch.</p>
     */
    public static final int HASHCODE_MULTIPLIER = 31;

    /**
     * <p>Default IWS Date Format. The format was chosen as it gives the fewest
     * problems for users, regarding understanding it.</p>
     */
    public static final String DATE_FORMAT = "dd-MMM-yyyy";

    /**
     * <p>Default IWS DateTime Format.</p>
     */
    public static final String DATE_TIME_FORMAT = DATE_FORMAT + " HH:mm:ss";

    /**
     * <p>Default IWS Success message.</p>
     */
    public static final String SUCCESS = "OK";
}
