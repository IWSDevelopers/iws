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

import static net.iaeste.iws.api.constants.IWSConstants.CONTACT_EMAIL;
import static net.iaeste.iws.api.constants.IWSConstants.CONTACT_URL;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.api.util.ReflectiveStandardMethods;
import net.iaeste.iws.api.util.StandardMethods;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Default Response Object, for those methods, that only return the error
 * information from IWS.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fallibleResponse", propOrder = { "error", "message", "contact" })
public class FallibleResponse implements Fallible {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(name = "error", required = true)    private final IWSError error;
    @XmlElement(name = "message", required = true)  private final String message;
    @StandardMethods(StandardMethods.For.NONE)
    @XmlElement(name = "contact", required = true)  private final String contact;

    /**
     * Default Constructor.
     */
    public FallibleResponse() {
        error = IWSErrors.SUCCESS;
        message = IWSConstants.SUCCESS;
        contact = CONTACT_EMAIL + "; " + CONTACT_URL;
    }

    /**
     * Error Constructor, for all Result Objects.
     *
     * @param error    IWS Error Object
     * @param message  Error Message
     */
    public FallibleResponse(final IWSError error, final String message) {
        this.error = error;
        this.message = message;
        contact = CONTACT_EMAIL + "; " + CONTACT_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isOk() {
        return IWSErrors.SUCCESS.getError() == error.getError();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final IWSError getError() {
        return error;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getMessage() {
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getContact() {
        return contact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        return ReflectiveStandardMethods.reflectiveEquals(this, obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        return ReflectiveStandardMethods.reflectiveHashCode(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return ReflectiveStandardMethods.reflectiveToString(this);
    }
}
