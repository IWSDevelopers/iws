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
package net.iaeste.iws.api.enums;

/**
 * This interface is for our Enumerated types, where we have the Description
 * field also, which contains as "pretty print" version of the name.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public interface Descriptable<E extends Enum<E>> {

    /**
     * Returns a "Pretty Print" version of the Enumerated name. This way, it is
     * possible to have special characters and upper / lower case letters which
     * follows User requirements, but is not otherwise allowed.
     *
     * @return Pretty Print version of the Enumerated Type
     */
    String getDescription();
}
