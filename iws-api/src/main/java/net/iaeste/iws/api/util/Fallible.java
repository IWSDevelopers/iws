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

import net.iaeste.iws.api.constants.IWSError;

import java.io.Serializable;

/**
 * All Result Object must implement this functionality, since it contains the
 * error information.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Fallible extends Serializable {

    /**
     * Returns true of the result was successfully completed, otherwise if an
     * error occurred, a false is returned.
     *
     * @return True if success, otherwise false
     */
    boolean isOk();

    /**
     * Returns the current {@code IWSError} object, if an error occurred,
     * otherwise it will return a null.
     *
     * @return Error object or null.
     */
    IWSError getError();

    /**
     * Returns the more detailed message for the current error, if an error
     * occurred, otherwise it will return a null.
     *
     * @return Error message or null.
     */
    String getMessage();

    /**
     * Returns contact information to report errors.
     *
     * @return Contact information
     */
    String getContact();

    /**
     * {@inheritDoc}
     */
    @Override
    String toString();
}
