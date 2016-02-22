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
 * <p>The IAESTE IntraWeb is also offering Mailing Lists and Personal E-mail
 * addresses, both using the IAESTE Domains. Personal e-mail aliases, which
 * is directly forwarded to the users private e-mail address, as well as Lists
 * for Groups, where it is possible to have both Public Lists, where anyone can
 * write, and Private Lists, where it is limited who may write.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public enum MailinglistType {

    /**
     * <p>The Private List, is for internal Mailing Lists, i.e. Lists which is
     * having limitations regarding who may write to them.</p>
     */
    PRIVATE_LIST,

    /**
     * <p>Public Lists, is the standard type of open Mailing Lists, where anyone
     * can and may write to it.</p>
     */
    PUBLIC_LIST,

    /**
     * <p>A Group Alias, is a Public Mailing List, which is normally having a
     * short life-time, as it only exist to ensure that during a transition
     * period, an older address may work.</p>
     *
     * <p>This type of Alias, can exist for both Users (someone got married and
     * changed their surname) and Groups (National Committee changing from
     * Co-operating Institution to Associate Member).</p>
     */
    LIMITED_ALIAS,

    /**
     * <p>All Users of the IAESTE IntraWeb, is having a personal e-mail alias.
     * The List address is the users firstname dot lastname.</p>
     */
    PERSONAL_ALIAS
}
