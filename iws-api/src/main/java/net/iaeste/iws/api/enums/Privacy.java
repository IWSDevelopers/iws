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

import javax.xml.bind.annotation.XmlType;

/**
 * <p>Data Privacy is a very important topic. The IWS provides a few settings,
 * which is combined with a restriction of fields, to ensure that the user can
 * control all aspects of the System properly.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "privacy")
public enum Privacy {

    /**
     * <p>The users data is made publicly available to all members of the IWS,
     * meaning that anyone can see all information that the user has
     * added.</p>
     *
     * <p>Please note, that some data fields are considered restricted and their
     * value is filtered before being displayed. Example; the users dateOfBirth
     * is not displayed with the year, only the day and month. If the user has
     * provided Passport information, then this is only displayed in the proper
     * context, meaning to events where it is required, and only for the
     * duration leading up to the event.</p>
     */
    PUBLIC,

    /**
     * <p>The users data is only made available to members of the same Groups as
     * the user. All others will only be allowed to see the name of the
     * user.</p>
     *
     * <p>Please note, that some data fields are considered restricted and their
     * value is filtered before being displayed. Example; the users dateOfBirth
     * is not displayed with the year, only the day and month. If the user has
     * provided Passport information, then this is only displayed in the proper
     * context, meaning to events where it is required, and only for the
     * duration leading up to the event.</p>
     */
    PROTECTED,

    /**
     * <p>All the users data is restricted, and only partly available -
     * depending on the context.</p>
     */
    PRIVATE
}
