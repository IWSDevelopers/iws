/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.SerializeUtil
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.util;

import net.iaeste.iws.api.exceptions.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Collection of methods, which can be used on Serializable Objects.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class Serializer {

    /** Private Constructor, this is a Utility Class. */
    private Serializer() {}

    /**
     * The method returns the compressed serialized data for the given data. The
     * serialization and compression is achieved by using 3 streams, the first
     * (top most, {@code ObjectOutputStream}) will handle the serialization of
     * the data. The second stream {@code GZIPOutputStream} will handle the
     * compression, and the final Stream {@code ByteArrayOutputStream} will
     * convert the compressed data into something we can read into memory and
     * thus sent to the database.<br />
     *   If a problem occurs, then a {@code SerializationException} will be
     * thrown, otherwise the serialized and compressed data is returned.<br />
     *   If the given data is null or empty, then an null is returned.
     *
     * @param  data  The data that to be serialized
     * @return Serialized and Compressed Byte Array
     * @throws SerializationException if unable to write the data
     */
    public static <T extends Serializable> byte[] serialize(final T data) throws SerializationException {
        final byte[] result;

        if (data != null) {
            try (final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                 final GZIPOutputStream zipStream = new GZIPOutputStream(byteStream);
                 final ObjectOutputStream objectStream = new ObjectOutputStream(zipStream)) {

                objectStream.writeObject(data);
                objectStream.close();

                result = byteStream.toByteArray();
            } catch (IOException e) {
                throw new SerializationException(e);
            }
        } else {
            result = null;
        }

        return result;
    }

    /**
     * The method returns the decompressed deserialized Object from the given
     * byte array. The deserialization and decompression is achieved by using 3
     * streams, the first (top most, {@code ByteArrayInputStream}) is used to
     * convert the data from a byte array to a data stream, that can then be
     * used as input for the second stream ({@code GZIPInputStream}) to
     * uncompress the data and finally give it to the third stream
     * ({@code ObjectInputStream}) to retrieve the Object that was originally
     * stored.<br />
     *   If a problem occurs, then a {@code SerializationException} will be
     * thrown, otherwise the deserialized and decompressed data is
     * returned.<br />
     *   If the given data is null, then an empty array is returned.
     *
     * @param  bytes  Serialized and Compressed IWSEntity
     * @return Deserialized and Decompressed IWSEntity
     * @throws SerializationException if unable to read the data
     */
    public static <T extends Serializable> T deserialize(final byte[] bytes) throws SerializationException {
        final T result;

        if (bytes != null) {
            try (final ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
                 final GZIPInputStream zipStream = new GZIPInputStream(byteStream);
                 final ObjectInputStream objectStream = new ObjectInputStream(zipStream)) {

                result = (T) objectStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new SerializationException(e);
            }
        } else  {
            result = null;
        }

        return result;
    }
}