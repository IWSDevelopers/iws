/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.Committees
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.RegionalGroupRequest;
import net.iaeste.iws.api.util.Fallible;

import javax.ejb.Remote;

/**
 * Control of National Committees and Regional Groups are done via the
 * functionality provided by this Interface.<br />
 *   The IAESTE Committees consists of several different types, from Full
 * Members, Associate Members to Co-operating Institutions. Once a new member
 * is added, it starts out as a Co-operating Institution, it can then later be
 * upgraded to an Associate Member and later on Full Member. However, for some
 * Countries, it is not possible to make the step directly. The rule that
 * applies, is that a Country can only go from Co-Operating Institution state to
 * Associate Member state, if the Committees in the Country have consolidated,
 * meaning that there can be only one National Committee.<br />
 *   An exception to this rule does apply, which was made for China. The rule
 * states, that the Country can move up in membership, if the other Institutions
 * will accept the status of SAR, Self Administrated Region. A SAR is in itself
 * a complete National Committee, with its own staff, and the rule for the
 * IntraWeb is irrelevant, as a SAR has the same rights as any other
 * Committee.<br />
 *   There exists two types of International Groups for IAESTE, one is referred
 * to as "International", and the other as "Regional". International Groups, are
 * those Groups instituted by IAESTE, this includes SID, Seminar on IAESTE
 * Development, and the IDT, IAESTE Internet Development Team. Both are groups
 * under direct control of the General Secretary, with a budget that is mandated
 * annually by the IAESTE Members during the Annual Conference. The other type,
 * Regional, is designed as an Independent Group. It serves the purpose of
 * allowing some countries to join together with an interest focus. an example;
 * CEC or Central European Countries.<br />
 *   The main purpose for Regional Groups, is to have a forum, which is
 * independent of IAESTE, meaning that the General Secretary and the Board have
 * no influence over it.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Remote
public interface Committees {

    /**
     * Creates a new Co-operating Institution for IAESTE. This requres that the
     * following information is provided:
     * <ul>
     *     <li>National Secretary</li>
     *     <li>Country Name</li>
     *     <li>IAESTE Name</li>
     * </ul>
     *
     * @param token   User Authentication Request object
     * @param request Committee Request Object
     * @return Standard Error object
     */
    Fallible createCommittee(AuthenticationToken token, CommitteeRequest request);

    /**
     * Managing a Committee, means that it is possible to update the current
     * National Secretary, change the Country and, the name.<br />
     *   Note, changing the name of will also mean that the public mailinglists,
     * will also be changed! So this option should no be used lightly.
     *
     * @param token   User Authentication Request object
     * @param request Committee Request Object
     * @return Standard Error object
     */
    Fallible manageCommittee(AuthenticationToken token, CommitteeRequest request);

    /**
     * Upgrades a Committee from the current status to the next level, following
     * these rules:
     * <ul>
     *     <li>Current Status: <b>Co-operating Institution</b><br />
     *       Co-operating Institutions can be upgraded to Associate Members
     *     after a period. However, to become an Associate member, all
     *     Institutions in the Country must merge and agree on a comon
     *     Structure. As there can only be one Associate Member in a Country,
     *     the rest will be either Local Committee's or SAR's.<br />
     *       If SAR is chosen, then the SAR will remain completely indepedent of
     *     the main Committee. Example; China is a Member, and Macao, Hong Kong
     *     and Taiwan are all SAR's.<br />
     *       If Local Committee is chosen, then the control if moved over to the
     *     new National Committee.
     *     </li>
     *     <li>Current Status: <b>Associate Member</b><br />
     *       Associate Members can become Full Members after a few years.
     *     </li>
     * </ul>
     * Note; The IntraWeb does not make any distinctions between members,
     * regarding how and what they may do.
     *
     * @param token   User Authentication Request object
     * @param request Committee Request Object
     * @return Standard Error object
     */
    Fallible upgradeCommittee(AuthenticationToken token, CommitteeRequest request);

    /**
     *
     * @param token   User Authentication Request object
     * @param request International Group Request Object
     * @return Standard Error object
     */
    Fallible manageInternationalGroup(AuthenticationToken token, InternationalGroupRequest request);

    /**
     *
     *
     * @param token   User Authentication Request object
     * @param request Regional Request Object
     * @return Standard Error object
     */
    Fallible createRegionalGroup(AuthenticationToken token, RegionalGroupRequest request);

    /**
     *
     * @param token   User Authentication Request object
     * @param request Regional Request Object
     * @return Standard Error object
     */
    Fallible manageRegionalGroup(AuthenticationToken token, RegionalGroupRequest request);
}
