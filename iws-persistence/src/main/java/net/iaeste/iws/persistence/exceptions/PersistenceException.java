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
package net.iaeste.iws.persistence.exceptions;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class PersistenceException extends IWSException {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Default Constructor, for the case when throwing a new Exception with just
     * the message describing the error.
     *
     * @param message Specific message, regarding the problem
     */
    public PersistenceException(final String message) {
        super(IWSErrors.PERSISTENCE_ERROR, message);
    }

    /**
     * Default Constructor, for the case where an error condition has arisen,
     * and the only information available is the type of error, and a message
     * describing the error.
     *
     * @param error   IWS Error
     * @param message Specific message, regarding the problem
     */
    public PersistenceException(final IWSError error, final String message) {
        super(error, message);
    }

    /**
     * Default Constructor, for the case where an error condition has arisen,
     * where JPA threw either an exception as there were either non or multiple
     * results, where a single result was expected.
     *
     * @param error   IWS Error
     * @param message Specific message, regarding the problem
     * @param cause    The specific cause of the problem
     */
    protected PersistenceException(final IWSError error, final String message, final Throwable cause) {
        super(error, message, cause);
    }
}
