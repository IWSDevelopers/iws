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
import net.iaeste.iws.api.requests.FetchSurveyOfCountryRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.SurveyOfCountryRequest;
import net.iaeste.iws.api.responses.FetchSurveyOfCountryRespose;
import net.iaeste.iws.api.util.Fallible;

/**
 * Control of National Committees and International Groups is done via the
 * functionality provided by this Interface.<br />
 *   The IAESTE Committees consists of several different types, from Full
 * Members, Associate Members to Co-operating Institutions. Once a new member
 * is added, it starts out as a Co-operating Institution, it can then later be
 * upgraded to an Associate Member and later on Full Member. However, for some
 * Countries, it is not possible to make the step directly. The rule that
 * applies, is that a Country can only go from Co-Operating Institution state to
 * Associate Member state, if the Committees in the Country have consolidated,
 * meaning that there can be only one National Committee.<br />
 *   Besides the Groups for Committees, there exists Global Groups in IAESTE,
 * these are referred to as International Groups. International Groups, is any
 * Group, which is not under the control of a single Country, but serves a
 * larger purpose. The standard International Groups includes the SID (Seminar
 * on IAESTE Development) and the IDT (IAESTE Internet Development Team). Both
 * Regional, is designed as an Independent Group. It serves the purpose of
 * are groups under direct control by the Board of IAESTE. It is also possible
 * to create International Groups for other purposes, which may serve regional
 * purposes, for example the CEC (Central European Countries).
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Committees {

    /**
     * Processing a Committee means either creating a new one, altering
     * information about an existing, upgrading it or suspend/delete it.<br />
     *   If there is no Id for the Committee, then the IWS will assume that the
     * request is for creating a new Co-operating Institution. There will be
     * additional checks regarding the current status of the Country, etc. The
     * IWS will also create an NS account for the new Committee.<br />
     *   If the Id is provided, then the committee will be updated. It is
     * possible to change the current status of the Committee, change a country
     * from Co-operating Institution to Associate Member or an Associate Member
     * to Full Member. It is also possible to suspend/delete a Committee, in
     * which case the status of the Country will also be updated to reflect
     * this.<br />
     *   It is also possible as part of an update, to change the current
     * National Secretary, if the former National Secretary did not properly
     * hand over the ownership.<br />
     *   Upgrading a Committee from the current status to the next level, will
     * be done according to the following rules:
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
     *
     *
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
    Fallible processCommittee(AuthenticationToken token, CommitteeRequest request);

    /**
     * Note; A problem was discovered with the Board. Normally, only the owner
     * of an International Group is receiving the public mails, but the Board
     * is a special case, here all members should receive the mails.
     *   2014-10-19; The GroupTypes have been extended with mail settings and
     * the UserGroup relation is also extended with same, so it is possible to
     * have both public and private mailing lists and flags exists to control
     * wether a user is on either and if the user may write to the private
     * list.<br />
     *   For now, please consider this stub a work-in-progress to be finalized
     * as part of IWS Release 1.2 (Scheduled January 2015).
     *
     * @param token   User Authentication Request object
     * @param request International Group Request Object
     * @return Standard Error object
     */
    Fallible processInternationalGroup(AuthenticationToken token, InternationalGroupRequest request);

    /**
     * Retrieves the Survey for a given Country. The survey is a rather
     * comprehensive list of information that must be updated annually by each
     * country. It contains various general purpose information which is
     * important for other countries.
     *
     * @param token   User Authentication Request object
     * @param request Fetch Survey of Country Request Object
     * @return Response Object with the Survey information or error information
     */
    FetchSurveyOfCountryRespose fetchSurveyOfCountry(AuthenticationToken token, FetchSurveyOfCountryRequest request);

    /**
     * Processes the Survey of Countries for the given Country. The processing
     * result is the returned.
     *
     * @param token   User Authentication Request object
     * @param request Survey of Country Request Object
     * @return Standard Error object
     */
    Fallible processSurveyOfCountry(AuthenticationToken token, SurveyOfCountryRequest request);
}
