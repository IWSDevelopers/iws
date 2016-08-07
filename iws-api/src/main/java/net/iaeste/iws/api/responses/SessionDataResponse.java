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
package net.iaeste.iws.api.responses;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.api.util.Serializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Arrays;

/**
 * <p>The Session Response Object contains the Session Data belonging to the
 * user's current active Session. The Data is stored internally as a Blob, i.e.
 * a Byte Array (Compressed). And is de-serialized to an Object of the type
 * specified by the Client.</p>
 *
 * <p>Note, that the IWS only allows a single active Session for any user at the
 * time, this is a limitation to avoid data corruption.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sessionDataResponse", propOrder = { "sessionData", "modified", "created" })
public final class SessionDataResponse<T extends Serializable> extends Response {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true) private byte[] sessionData = null;
    @XmlElement(required = true, nillable = true) private DateTime modified = null;
    @XmlElement(required = true, nillable = true) private DateTime created = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public SessionDataResponse() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Error Constructor.
     *
     * @param error    IWS Error Object
     * @param message  Error Message
     */
    public SessionDataResponse(final IWSError error, final String message) {
        super(error, message);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setSessionData(final byte[] sessionData) {
        this.sessionData = Arrays.copyOf(sessionData, sessionData.length);
    }

    public T getSessionData() {
        return Serializer.deserialize(sessionData);
    }

    public void setModified(final DateTime modified) {
        this.modified = modified;
    }

    public DateTime getModified() {
        return modified;
    }

    public void setCreated(final DateTime created) {
        this.created = created;
    }

    public DateTime getCreated() {
        return created;
    }
}
