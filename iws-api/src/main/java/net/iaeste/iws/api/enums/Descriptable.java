/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.Descriptable
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
