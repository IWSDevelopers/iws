/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.Actionable
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.util.Verifiable;

import java.util.Set;

/**
 * <p>To ensure that the Processing methods all share a common ground, they must
 * implement this interface, which will control the Actions that is allowed.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public interface Actionable extends Verifiable {

    /**
     * <p>Retrieves a set of allowed Actions for a given Request.</p>
     *
     * @return Set of allowed Actions for a specific Request.
     */
    Set<Action> allowedActions();

    /**
     * <p>Sets the Action for the Processing Request. By default, it is set to
     * Process, meaning that the IWS will attempt to either create or update
     * the Object in question. However, more options exists, based on the actual
     * context.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the value
     * is set to null, or a non allows value.</p>
     *
     * @param action Current Action
     * @throws IllegalArgumentException if the value is null or not allowed
     */
    void setAction(Action action);

    /**
     * <p>Retrieves the current Action, by default it is always set to
     * Process.</p>
     *
     * @return Current Action
     */
    Action getAction();
}
