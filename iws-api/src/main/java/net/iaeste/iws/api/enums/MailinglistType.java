/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.MailinglistType
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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
