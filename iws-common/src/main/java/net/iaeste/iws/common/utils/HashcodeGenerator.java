/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.utils.HashcodeGenerator
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.common.utils;

import net.iaeste.iws.api.constants.IWSConstants;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Hashcode Generator, will generate a hash value for a given value. A Hash
 * value is a one-way cryptographic method, that is good for storing
 * Passwords.<br />
 *   Since more users will use a simple password, that can easily be found using
 * a "rainbow" table, meaning a lookup in a database with known hash codes and
 * their origin, we're simply adding a salt to it. a Salt is a value that is
 * controlled by the system, and automatically added to both the hashing and
 * also used for the verification.<br />
 *   The IWS uses a two-part salt, one with a hardcoded value to make it harder
 * for hackers to crack passwords. The two parts consists of a user specific
 * salt, and a hardcoded salt.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class HashcodeGenerator {

    /**
     * Hardcoded salt value, to add to the hashing. Note, that changing this
     * salt value, will cause all passwords, sessions and other information
     * where a hash value is needed, to fail!
     */
    private static final String HARDCODED_SALT = "The quick brown fox jumps over the lazy dog <.,:;-_ 1234567890 $%&/()=?>";

    // The Algorithm's, which we'll support
    private static final String HASHCODE_ALGORITHM_MD5 = "MD5";
    private static final String HASHCODE_ALGORITHM_SHA256 = "SHA-256";
    private static final String HASHCODE_ALGORITHM_SHA384 = "SHA-384";
    private static final String HASHCODE_ALGORITHM_SHA512 = "SHA-512";
    private static final Charset CHARSET = Charset.forName(IWSConstants.DEFAULT_ENCODING);
    public static final int HASHCODE_LENGTH = 64;

    /**
     * Private Constructor, this is a utility class.
     */
    private HashcodeGenerator() {
    }

    /**
     * Wrapper for our default Hash algorithm.
     *
     * @param  str      The string to generate an HashCode value for
     * @param  userSalt User specific salt value
     * @return MD5 Hashcode value
     */
    public static String generateHash(final String str, final String... userSalt) {
        return generateSHA256(str, userSalt);
    }

    /**
     * Generates a new MD5 hashcode for the given String.
     *
     * @param  str      The string to generate an MD5 HashCode value for
     * @param  userSalt User specific salt value
     * @return MD5 Hashcode value
     * @see <a href="http://en.wikipedia.org/wiki/MD5">Wikipedia MD5</a>
     */
    public static String generateMD5(final String str, final String... userSalt) {
        //final String salt = prepareSalt(userSalt);
        //return generateHashcode(HASHCODE_ALGORITHM_MD5, str, salt);
        // The following is an attempt to make the Java MD5 calculation the same
        // as the PHP variant. See: http://www.sergiy.ca/how-to-make-java-md5-and-sha-1-hashes-compatible-with-php-or-mysql/
        String result = str;

        if (str != null) {
            final MessageDigest md = getDigest(HASHCODE_ALGORITHM_MD5);
            md.update(str.getBytes());
            final BigInteger hash = new BigInteger(1, md.digest());
            result = hash.toString(16);

            while (result.length() < 32) {
                result = '0' + result;
            }
        }

        return result;
    }

    /**
     * Generates a new SHA-2 hashcode for the given String.
     *
     * @param  str      The string to generate an SHA-2 HashCode value for
     * @param  userSalt User specific salt value
     * @return SHA-2 Hashcode value
     * @see <a href="http://en.wikipedia.org/wiki/Sha-2">Wikipedia SHA-2</a>
     */
    static String generateSHA256(final String str, final String... userSalt) {
        final String salt = prepareSalt(userSalt);
        return generateHashcode(HASHCODE_ALGORITHM_SHA256, str, salt);
    }

    /**
     * Generates a new SHA-2 hashcode for the given String.
     *
     * @param  str      The string to generate an SHA-2 HashCode value for
     * @param  userSalt User specific salt value
     * @return SHA-2 Hashcode value
     * @see <a href="http://en.wikipedia.org/wiki/Sha-2">Wikipedia SHA-2</a>
     */
    static String generateSHA384(final String str, final String... userSalt) {
        final String salt = prepareSalt(userSalt);
        return generateHashcode(HASHCODE_ALGORITHM_SHA384, str, salt);
    }

    /**
     * Generates a new SHA-2 hashcode for the given String.
     *
     * @param  str      The string to generate an SHA-2 HashCode value for
     * @param  userSalt User specific salt value
     * @return SHA-2 Hashcode value
     * @see <a href="http://en.wikipedia.org/wiki/Sha-2">Wikipedia SHA-2</a>
     */
    static String generateSHA512(final String str, final String... userSalt) {
        final String salt = prepareSalt(userSalt);
        return generateHashcode(HASHCODE_ALGORITHM_SHA512, str, salt);
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    /**
     * Prepares the combined salt for the hashcode generating. If a salt is
     * provided from the user, then this is used together with the internal
     * hardcoded salt, otherwise only the hardcoded salt is used.
     *
     * @param userSalt User specific salt (optional)
     * @return salt for the hashcode generation
     */
    private static String prepareSalt(final String... userSalt) {
        final String salt;

        if ((userSalt != null) && (userSalt.length == 1)) {
            salt = userSalt[0];
        } else {
            salt = "";
        }

        return salt + HARDCODED_SALT;
    }

    /**
     * Generates a Cryptographical Checksum based on the given algorithm, and
     * the provided string. If the given string is invalid, i.e. null, then the
     * method will also return a null value. Otherwise, it will return the
     * generated checksum.
     *
     * @param  algorithm  The Cryptographical Hash Algorithm to use
     * @param  str        The value to hash
     * @param  salt       Salt for the hashing
     * @return The Hash value for the given string
     */
    private static String generateHashcode(final String algorithm, final String str, final String salt) {
        final String result;

        if (str != null) {
            final MessageDigest digest = getDigest(algorithm);
            final byte[] bytes = digest.digest((salt + str).getBytes(CHARSET));
            result = convertBytesToHex(bytes);
        } else {
            result = null;
        }

        return result;
    }

    /**
     * Prepares a digest with a givel Algorithm. This Digest can then be used
     * to generate a Hashcode value. The method will return a null value, if
     * an invalid Algorithm is issued. However, since this is an internal
     * method, it is assumed that all provided algorithms are valid. If not,
     * then this will be the cause of potential {@code NullPointerExceptions}.
     *
     * @param  algorithm  The name of the Algorithm to use
     * @return Message Digest for the given Algorithm
     */
    private static MessageDigest getDigest(final String algorithm) {
        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance(algorithm);
        } catch (final NoSuchAlgorithmException ignore) {
            // The MessageDigest method getInstance, if throwing a checked
            // NoSuchAlgorithm Exception. However, as we only use internal
            // values for the Algorithms, then we'll never face this problem.
            // Hence, the exception is ignored
        }

        return digest;
    }

    /**
     * Converts the given byte array to a HEX string.
     *
     * @param  bytes  The Byte Array to convert to Hex
     * @return The HEX value of the Byte Array
     */
    private static String convertBytesToHex(final byte[] bytes) {
        final StringBuilder builder = new StringBuilder(bytes.length * 2);

        for (final byte b : bytes) {
            builder.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
        }

        return builder.toString();
    }
}
