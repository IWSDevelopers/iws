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
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "membership")
public enum Membership {

    /**
     * The list starts with the ordinal value 1, thus this is a placeholder, to
     * ensure that the values are correct. It should never be used!
     */
    UNKNOWN,

    /**
     * Status; Full Member.
     */
    FULL_MEMBER,

    /**
     * Status; Associate Member
     */
    ASSOCIATE_MEMBER,

    /**
     * Status; Co-operating Institution
     */
    COOPERATING_INSTITUTION,

    /**
     * Status; Former Member, i.e. country had earlier Full,Associate or
     * Co-operating membership status.
     */
    FORMER_MEMBER,

    /**
     * List of known countries, which is not having status as either Full,
     * Associate, Co-operating or Former member.
     */
    LISTED,

    /**
     * List of UN countries, which for various reasons is not necessary to list
     * in the ordinary list.
     */
    UNLISTED
}
