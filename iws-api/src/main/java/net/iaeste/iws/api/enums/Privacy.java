/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.Privacy
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

import javax.xml.bind.annotation.XmlType;

/**
 * Data Privacy is a very important topic. The IWS provides a few settings,
 * which is combined with a restriction of fields, to ensure that the user can
 * control all aspects of the System properly.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "privacy")
public enum Privacy {

    /**
     * The users data is made publicly available to all members of the IWS,
     * meaning that anyone can see all information that the user has
     * added.<br />
     *   Please note, that some data fields are considered restricted and their
     * value is filtered before being displayed. Example; the users dateOfBirth
     * is not displayed with the year, only the day and month. If the user has
     * provided Passport information, then this is only displayed in the proper
     * context, meaning to events where it is required, and only for the
     * duration leading up to the event.
     */
    PUBLIC,

    /**
     * The users data is only made available to members of the same Groups as
     * the user. All others will only be allowed to see the name of the
     * user.<br />
     *   Please note, that some data fields are considered restricted and their
     * value is filtered before being displayed. Example; the users dateOfBirth
     * is not displayed with the year, only the day and month. If the user has
     * provided Passport information, then this is only displayed in the proper
     * context, meaning to events where it is required, and only for the
     * duration leading up to the event.
     */
    PROTECTED,

    /**
     * All the users data is restricted, and only partly available - depending
     * on the context.
     */
    PRIVATE
}
