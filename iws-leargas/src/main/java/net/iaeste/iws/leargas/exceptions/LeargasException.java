/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-leargas) - net.iaeste.iws.leargas.exceptions.LeargasException
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.leargas.exceptions;

/**
 * Standard Exception for all thrown Exceptions within this Module. It helps
 * minimizing the Error handling overhead, while still keeping track of
 * problems.
 *
 * @author  Kim Jensen <kim@dawn.dk>
 * @version Leargas 1.0
 * @since   Java 1.8
 */
public final class LeargasException extends RuntimeException {

    private static final long serialVersionUID = -7757385905651868026L;

    public LeargasException(final String message) {
        super(message);
    }

    public LeargasException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
