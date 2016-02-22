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
package net.iaeste.iws.api.exceptions;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;

/**
 * Basic Exception, which all other exceptions in the system should inherit
 * from. It may not be thrown directly.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class IWSException extends RuntimeException {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** Given base error. */
    private final IWSError error;

    /**
     * Default Constructor, for the case where an error condition has arisen,
     * and the only information available is the type of error, and a message
     * describing the error.
     *
     * @param error    IWS Error
     * @param message  Specific message, regarding the problem
     */
    public IWSException(final IWSError error, final String message) {
        super(message);

        this.error = error;
    }

    /**
     * Default Constructor, for the case where an error condition has arisen,
     * caused by an underlying Exception. In this case, this Exception serves
     * as a wrapper around the underlying Exception, to avoid that higher
     * layers has to deal with more specific problems.
     *
     * @param error    IWS Error
     * @param cause    The specific cause of the problem
     */
    public IWSException(final IWSError error, final Throwable cause) {
        super(cause);

        this.error = error;
    }

    /**
     * Default Constructor, for the case where an error condition has arisen,
     * which is caused by an underlying Exception, but more information is
     * available to improve the error handling. In this case, this Exception
     * serves as a wrapper around the underlying Exception, to avoid that
     * higher layers has to deal with more specific problems.
     *
     * @param error    IWS Error
     * @param message  Specific message, regarding the problem
     * @param cause    The specific cause of the problem
     */
    public IWSException(final IWSError error, final String message, final Throwable cause) {
        super(message, cause);

        this.error = error;
    }

    /**
     * Returns the IWS Error.
     *
     * @return IWS Error
     */
    public IWSError getError() {
        return error;
    }
}
