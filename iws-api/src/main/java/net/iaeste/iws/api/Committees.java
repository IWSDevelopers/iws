/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.requests.FetchCommitteeRequest;
import net.iaeste.iws.api.requests.FetchInternationalGroupRequest;
import net.iaeste.iws.api.requests.FetchSurveyOfCountryRequest;
import net.iaeste.iws.api.requests.InternationalGroupRequest;
import net.iaeste.iws.api.requests.SurveyOfCountryRequest;
import net.iaeste.iws.api.responses.FetchCommitteeResponse;
import net.iaeste.iws.api.responses.FetchInternationalGroupResponse;
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
 * on IAESTE Development) and the IDT (IAESTE Internet Development Team). Other
 * International Groups may exists, which is not under the control of the Board,
 * this would include "Regional" groups, such as the CEC (Central European
 * Countries) or similar Groups.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Committees {

    /**
     * Working with committees involve working with more details than normally
     * allowed. Additionally, it must also be possible to work with both Active
     * and Suspended Committees. This request will facilitate precisely this,
     * reading the additional details needed for all existing Committees.
     *
     * @param token   User Authentication Request object
     * @param request Fetch Committee Request Object
     * @return Fetch Response Object, with standard error information
     */
    FetchCommitteeResponse fetchCommittees(AuthenticationToken token, FetchCommitteeRequest request);

    /**
     * Processing Committees, means create, update, merge/upgrade, activate,
     * suspend or delete National Groups, with their related Member Group as
     * well as control the National Secretary.<br />
     *   The Request is fairly complex, hence the Request Objects takes an
     * Action as a principal argument, which must be defined and the action to
     * be taken will depend on the Action given. The required data needed for
     * the processing also depends on the given Action.
     * <ul>
     *   <li><b>Action: Create</b><br />
     *   This Action is for creating new Cooperating Institutions, meaning
     *   create both a new Members Group and a new Staff. The request requires
     *   that the Country is set as well as Institution. And to ensure that a
     *   new National Secretary can be applied, the first and last name and
     *   username must all be applied. It is not possible to create a new
     *   Cooperating Institution with an existing user. The name of the
     *   Institution will be used to create the Abbreviation, which again serves
     *   as the Staff name and also public mail address.<br />
     *   Example:
     *     <pre>
     *     Country: Elbonia
     *     Institution: Super Tech
     *     </pre>
     *   Result:<br />
     *     <pre>
     *     Staff: Elbonia, ST
     *     Fullname: Elbonia, Super Tech
     *     Mail: Elbonia_ST@iaeste.org
     *     </pre>
     *   Note; Attempting to add a Cooperating Institution to a Country which is
     *   already having status as Associate Member or Full Member will result in
     *   an error.
     *   </li>
     * </ul>
     * <ul>
     *   <li><b>Action: Change NS</b><br />
     *   It is only allowed to change the National Secretary of an active
     *   Committee, as name changes will affect mailing lists and these are used
     *   as part of a Committee's official information.<br />
     *     The National Secretary can either be an existing member of the
     *   Committee, or a new user. Attempting to make a person from either a
     *   different committee or a global member the new National Secretary will
     *   result in an error.
     *   </li>
     * </ul>
     * <ul>
     *   <li><b>Action: Merge</b><br />
     *   To be decided by the Board.
     *   </li>
     * </ul>
     * <ul>
     *   <li><b>Action: Upgrade</b><br />
     *   Upgrades a Committee from Cooperating Institution to Associate Member
     *   (if only a single Committee exists in the Country) or from Associate
     *   Member to Full Member. Attempting to upgrade a Cooperating Institution
     *   where two or more Committees exists or attempting to upgrade a Full
     *   Member will result in an error.
     *   </li>
     * </ul>
     * <ul>
     *   <li><b>Action: Activate</b><br />
     *   Activates a previously Suspended Committee, meaning that both the
     *   Member and National Groups will have their status changed to Active.
     *   Attempting to Activate something else will result in an error.<br />
     *     Note; Activation of a Committee, means that all accounts that belongs
     *   to it, will also be reactivated.
     *   </li>
     * </ul>
     * <ul>
     *   <li><b>Action: Suspend</b><br />
     *   Suspends an already Active Committee, meaning that both the Member and
     *   National Groups will have their status changed to Suspended. Attempting
     *   to Suspend something else will result in an error.<br />
     *     Note; All accounts currently belonging to the Suspended Committee,
     *   will also be suspended. All new accounts will be deleted, as they also
     *   should not be allowed to function.
     *   </li>
     * </ul>
     * <ul>
     *   <li><b>Action: Delete</b><br />
     *   Deletes a previously suspended Committee, meaning that all Members will
     *   be removed, as will all subgroups, and internal data - leaving only
     *   the stubs and already published information that is important for other
     *   Committees, like Offers, and shared Students. Attempting to delete an
     *   Active Committee will result in an error.<br />
     *     All accounts belonging to the Committee will also be deleted from the
     *   system. All sub-groups, like WorkGroups and Local Committees will also
     *   be deleted together with their data.
     *   </li>
     * </ul>
     *
     * @param token   User Authentication Request object
     * @param request Committee Request Object
     * @return Standard Error object
     */
    Fallible processCommittee(AuthenticationToken token, CommitteeRequest request);

    /**
     * Working with International Groups involve working with more details than
     * normally allowed. Additionally, it must also be possible to work with
     * both Active and Suspended International Groups. This request will
     * facilitate precisely this, reading the additional details needed for all
     * existing International Groups.
     *
     * @param token   User Authentication Request object
     * @param request Fetch International Group Request Object
     * @return Fetch Response Object, with standard error information
     */
    FetchInternationalGroupResponse fetchInternationalGroups(AuthenticationToken token, FetchInternationalGroupRequest request);

    /**
     * International Groups are Groups which serve a more global purpose,
     * examples of these is the SID or Seminar on IAESTE Development or the IDT,
     * Internet Development Team, both serve a purpose of supporting the
     * organization to improve it.<br />
     *   With this request, it is possible to create, update, suspend, activate
     * or delete International Groups, as well as setting the Coordinator
     * (Owner), of it.<br />
     *   If the Group is given without an Id, it is assumed that a new Group
     * should be created, and the request will result in an error if either a
     * different group with the same name exists or if no User Object is present
     * in the request, as it is then not possible to set a Coordinator (Owner)
     * of the Group.<br />
     *   If the Id is set, then the Group will be updated, meaning that it is
     * possible to rename the Group or add a new Coordinator (Owner) of the
     * Group.<br />
     *   If the Group is being suspended, then all other changes will be
     * ignored, as it is considered irrelevant to update either Coordinator or
     * Group information for a Group which is suspended. So Coordinator and
     * other Group information is only updated if the Group is either Active or
     * given given the new Status Active, after it was suspended.
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
