/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.util.StorageUtil
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.util;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class StorageUtil {

    /**
     * Private Constructor, this is a utility class.
     */
    private StorageUtil() {
    }

    /**
     * Verifies if the existing Checksum of the given byte array is correct or
     * not. The test is used in two different ways, first is to verify that data
     * being read is correct, the second is to verify if a user is performing
     * an update or not.<br />
     *   The method will return true if the previously calculated Checksum for
     * the given array matches, otherwise it returns false.
     *
     * @param array    Byte array to check
     * @param checksum Existing Checksum to verify against
     * @return True if the checksum matches, otherwise false.
     */
    public static boolean verifyChecksum(final byte[] array, final long checksum) {
        return calculateChecksum(array) == checksum;
    }

    /**
     * Calculates the Checksum for a given Byte array. The checksum is a simple
     * value that helps determine if the data has been updated or not. If they
     * have been updated, it must be from a user action, otherwise it is cause
     * for verifying the underlying system to see if data corruption has taken
     * place.
     *
     * @param array Byte array to check
     * @return checksum value
     */
    public static long calculateChecksum(final byte[] array) {
        final long crc;

        if (array != null && array.length > 0) {
            final Checksum checksum = new CRC32();
            checksum.update(array, 0, array.length);

            crc = checksum.getValue();
        } else {
            crc = 0;
        }

        return crc;
    }

}
