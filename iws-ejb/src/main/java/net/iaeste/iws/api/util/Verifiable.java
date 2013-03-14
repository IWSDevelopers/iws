/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.Verifiable
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.util;

import net.iaeste.iws.api.exceptions.VerificationException;

import java.io.Serializable;
import java.util.Map;

/**
 * All Input Object, or Request Objects, must have a common way to verify if
 * they are containing the expected information. Thus, they all have to
 * implement this Interface.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface Verifiable extends Serializable {

    /**
     * Checks the data in the object, to see if they are valid, i.e. sufficient
     * to complete a request. If not valid, then a {@code VerificationException}
     * is thrown.
     *
     * @throws net.iaeste.iws.api.exceptions.VerificationException if the data is insufficient or invalid
     */
    void verify() throws VerificationException;

    /**
     * Validates that the required information is there for the processing to be
     * able to successfully run.<br />
     *   The method collects all errors, and returns a map with them, where the
     * key is the name of the field in the Object, and the value is the error
     * information.
     *
     * @return Map with all errors, if successful then the map is empty
     */
    Map<String, String> validate();
}
