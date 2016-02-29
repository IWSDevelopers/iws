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
     * <p>The method returns the compressed serialized data for the given data.
     * The serialization and compression is achieved by using 3 streams, the
     * first (top most, {@code ObjectOutputStream}) will handle the
     * serialization of the data. The second stream {@code GZIPOutputStream}
     * will handle the compression, and the final Stream
     * {@code ByteArrayOutputStream} will convert the compressed data into
     * something we can read into memory and thus sent to the database.</p>
     *
     * <p>If a problem occurs, then a {@code SerializationException} will be
     * thrown, otherwise the serialized and compressed data is returned.</p>
     *
     * <p>If the given data is null or empty, then an null is returned.</p>
     *
     * @param data The data that to be serialized
     * @param <T>  The Serializable Object Type
     * @return Serialized and Compressed Byte Array
     * @throws SerializationException if unable to write the data
     */
    public static <T extends Serializable> byte[] serialize(final T data) {
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
     * <p>The method returns the decompressed de-serialized Object from the
     * given byte array. The deserialization and decompression is achieved by
     * using 3 streams, the first (top most, {@code ByteArrayInputStream}) is
     * used to convert the data from a byte array to a data stream, that can
     * then be used as input for the second stream ({@code GZIPInputStream}) to
     * decompress the data and finally give it to the third stream
     * ({@code ObjectInputStream}) to retrieve the Object that was originally
     * stored.</p>
     *
     * <p>If a problem occurs, then a {@code SerializationException} will be
     * thrown, otherwise the de-serialized and decompressed data is
     * returned.</p>
     *
     * <p>If the given data is null, then an empty array is returned.</p>
     *
     * @param bytes Serialized and Compressed IWSEntity
     * @param <T>   The Serializable Object Type
     * @return Deserialized and Decompressed IWSEntity
     * @throws SerializationException if unable to read the data
     */
    public static <T extends Serializable> T deserialize(final byte[] bytes) {
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
