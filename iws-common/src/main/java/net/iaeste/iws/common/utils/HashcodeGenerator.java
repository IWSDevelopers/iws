/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class HashcodeGenerator {

    // The Algorithm's, which we'll support
    private static final String HASHCODE_ALGORITHM_MD5 = "MD5";
    private static final String HASHCODE_ALGORITHM_SHA256 = "SHA-256";
    private static final String HASHCODE_ALGORITHM_SHA384 = "SHA-384";
    private static final String HASHCODE_ALGORITHM_SHA512 = "SHA-512";
    private static final Charset CHARSET = Charset.forName(IWSConstants.DEFAULT_ENCODING);

    /**
     * Private Constructor, this is a utility class.
     */
    private HashcodeGenerator() {
    }

    /**
     * Generates a new MD5 hashcode for the given String.
     *
     * @param  str  The string to generate an MD5 HashCode value for
     * @return MD5 Hashcode value
     * @see <a href="http://en.wikipedia.org/wiki/MD5">Wikipedia MD5</a>
     */
    public static String generateMD5(final String str) {
        return generateHashcode(HASHCODE_ALGORITHM_MD5, str);
    }

    /**
     * Generates a new SHA-2 hashcode for the given String.
     *
     * @param  str  The string to generate an SHA-2 HashCode value for
     * @return SHA-2 Hashcode value
     * @see <a href="http://en.wikipedia.org/wiki/Sha-2">Wikipedia SHA-2</a>
     */
    public static String generateSHA256(final String str) {
        return generateHashcode(HASHCODE_ALGORITHM_SHA256, str);
    }

    /**
     * Generates a new SHA-2 hashcode for the given String.
     *
     * @param  str  The string to generate an SHA-2 HashCode value for
     * @return SHA-2 Hashcode value
     * @see <a href="http://en.wikipedia.org/wiki/Sha-2">Wikipedia SHA-2</a>
     */
    public static String generateSHA384(final String str) {
        return generateHashcode(HASHCODE_ALGORITHM_SHA384, str);
    }

    /**
     * Generates a new SHA-2 hashcode for the given String.
     *
     * @param  str  The string to generate an SHA-2 HashCode value for
     * @return SHA-2 Hashcode value
     * @see <a href="http://en.wikipedia.org/wiki/Sha-2">Wikipedia SHA-2</a>
     */
    public static String generateSHA512(final String str) {
        return generateHashcode(HASHCODE_ALGORITHM_SHA512, str);
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    /**
     * Generates a Cryptographical Checksum based on the given algorithm, and
     * the provided string. If the given string is invalid, i.e. null, then the
     * method will also return a null value. Otherwise, it will return the
     * generated checksum.
     *
     * @param  algorithm  The Cryptographical Hash Algorithm to use
     * @param  str        The value to hash
     * @return The Hash value for the given string
     */
    private static String generateHashcode(final String algorithm, final String str) {
        final String result;

        if (str != null) {
            final MessageDigest digest = getDigest(algorithm);
            final byte[] bytes = digest.digest(str.getBytes(CHARSET));
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
