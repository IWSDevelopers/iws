/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.utils.Check
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.utils;

import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.Verifiable;

import java.util.List;

/**
 * Contains simple checks for various Object types, to be used for the Object
 * verification.
 *
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class Check {

    /**
     * Private Constructor, this is a utility class.
     */
    private Check() {
    }

    public static void notNull(final Object obj, final String field) {
        if (obj == null) {
            throw new VerificationException(field + " may not be null.");
        }
    }

    public static void notEmpty(final String str, final String field) {
        notNull(str, field);
        if (str.isEmpty()) {
            throw new VerificationException(field + " may not be empty.");
        }
    }

    public static <T extends Verifiable> void valid(final List<T> verifyList, final String field) {
        notNull(verifyList, field);
        for (final T obj : verifyList) {
            obj.verify();
        }
    }
}
