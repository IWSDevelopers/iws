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

import net.iaeste.iws.api.exceptions.VerificationException;

import java.io.Serializable;
import java.util.Map;

/**
 * All Input Object, or Request Objects, must have a common way to verify if
 * they are containing the expected information. Thus, they all have to
 * implement this Interface.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Verifiable extends Serializable {

    /**
     * Checks the data in the object, to see if they are valid, i.e. sufficient
     * to complete a request. If not valid, then a {@code VerificationException}
     * is thrown.
     *
     * @throws VerificationException if the data is insufficient or invalid
     */
    void verify() throws VerificationException;

    /**
     * <p>Validates that the required information is there for the processing to
     * be able to successfully run.</p>
     *
     * <p>The method collects all errors, and returns a map with them, where the
     * key is the name of the field in the Object, and the value is the error
     * information.</p>
     *
     * @return Map with all errors, if successful then the map is empty
     */
    Map<String, String> validate();
}
