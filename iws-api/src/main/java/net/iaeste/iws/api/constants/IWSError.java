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
package net.iaeste.iws.api.constants;

import net.iaeste.iws.api.util.ReflectiveStandardMethods;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * All error codes, which are sent externally accessible, should be of this
 * type.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "iwsError")
public final class IWSError implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true) private final int error;
    @XmlElement(required = true) private final String description;

    /**
     * Empty Constructor, required for WebServices to work.
     */
    public IWSError() {
        this.error = IWSErrors.SUCCESS.error;
        this.description = IWSErrors.SUCCESS.description;
    }

    /**
     * Default Constructor.
     *
     * @param error       IWS Error code
     * @param description IWS Error description
     */
    public IWSError(final int error, final String description) {
        this.error = error;
        this.description = description;
    }

    /**
     * Returns the Error Code.
     *
     * @return Error Code
     */
    public int getError() {
        return error;
    }

    /**
     * Returns the Error Description.
     *
     * @return Error Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        return ReflectiveStandardMethods.reflectiveEquals(this, obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return ReflectiveStandardMethods.reflectiveHashCode(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ReflectiveStandardMethods.reflectiveToString(this);
    }
}
