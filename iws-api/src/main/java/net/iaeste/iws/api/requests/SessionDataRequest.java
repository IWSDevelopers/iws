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
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Serializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>IWS Clients can work with Sessions in many different ways. The Data they
 * may wish to store as part of a Session can also take on many types and
 * shapes. Hence, it is possible to set the data to the desired data type. It
 * will, upon being set, be converted to a Compressed Byte Array, that can be
 * stored as a Blob. The converting takes place upon setting the data.</p>
 *
 * <p>Please note, that the SessionData is being Compressed upon converting to a
 * Serialized Byte array. This means, that the actual size of data allowed may
 * differ from the size provided, depending on how much the data can be
 * compressed. It is the size of the Compressed Data, which is being verified
 * against the allowed max size.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sessionDataRequest", propOrder = { "sessionData" })
public final class SessionDataRequest<T extends Serializable> extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * <p>The maximum allowed size to be stored for the current Session. The
     * value is used against the Compressed Data Array, so even if the original
     * Object is bigger, it may still be allowed.</p>
     */
    public static final int MAX_SIZE = 16384;

    /**
     * <p>The Session Data, to be stored for the current Session. The data is
     * internally converted to a Compressed Byte Array, which may not exceed
     * 16 KB.</p>
     */
    @XmlElement(required = true, nillable = true)
    private byte[] sessionData = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * <p>Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.</p>
     */
    public SessionDataRequest() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * <p>Default Constructor, takes an arbitrary data type as session data, and
     * stores it internally as a Byte Array.</p>
     *
     * @param sessionData Client specific Session Data
     */
    public SessionDataRequest(final T sessionData) {
        this.sessionData = Serializer.serialize(sessionData);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the Session Data, the Data can be null - which will remove any
     * data currently stored for this Session or defined. Only condition is that
     * the compressed data does not exceed the maximum allowed size, defined by
     * #MAX_SIZE. If it does exceeds this after compression, then the method
     * will throw an IllegalArgument Exception.</p>
     *
     * @param sessionData Session Data or null to remove
     * @throws IllegalArgumentException if the Compressed Session Data exceeds 16 KB
     * @see #MAX_SIZE
     */
    public void setSessionData(final T sessionData) {
        final byte[] tmp = Serializer.serialize(sessionData);
        ensureNotTooLong("sessionData", tmp, MAX_SIZE);
        this.sessionData = tmp;
    }

    public byte[] getSessionData() {
        return sessionData;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        if ((sessionData != null) && (sessionData.length > MAX_SIZE)) {
            validation.put("sessionData", "The Session Data cannot exceed " + (MAX_SIZE / 1024) + "KB.");
        }

        return validation;
    }
}
