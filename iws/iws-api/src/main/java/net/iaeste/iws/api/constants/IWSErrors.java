/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.constants.IWSErrors
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.constants;

/**
 * Defined IWS Errors.
 *
 * @author  Kim Jensen
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface IWSErrors {

    /**
     * All requests, which is processed normally, i.e. without any issues should
     * have this as the default error.
     */
    IWSError SUCCESS = new IWSError(0, "SUCCESS");

    /**
     * All requests, where the input data, is unreliable, and will prevent the
     * processing to be properly handled, or in case something internally has
     * happened, which the system managed to recover from via other mechanisms,
     * should return this as the basic error.
     */
    IWSError WARNING = new IWSError(100, "Warning");

    /**
     * All requests, where something happened internally, which prevented the
     * system from properly respond with a valid result, will have this as
     * default error.
     */
    IWSError ERROR = new IWSError(200, "Error");

    /**
     * Unrecoverable internal errors, from which it is not possible to proceed
     * without manual intervention, i.e. database is down or other critical
     * systems is inaccessible, should return an error of this type.
     */
    IWSError FATAL = new IWSError(300, "Fatal Error");

    /**
     * The data required for a given request is insufficient to be properly
     * processed.
     */
    IWSError VERIFICATION_ERROR = new IWSError(101, "Given data is insufficient to properly handle request");

    /**
     * If the user making the request is not properly authenticated, i.e. no
     * proper user credentials like username/password or session checksum.
     */
    IWSError AUTHENTICATION_ERROR = new IWSError(102, "User Authentication problem");

    /**
     * If the user making the request is not allowed to perform the desired
     * action.
     */
    IWSError AUTHORIZATION_ERROR = new IWSError(103, "User Authorization problem");

    /**
     * The database is inaccessible.
     */
    IWSError DATABASE_UNREACHABLE = new IWSError(301, "Database unreachable");

    /**
     * Unknown Persistency Error.
     */
    IWSError PERSISTENCE_ERROR = new IWSError(302, "Persistency Error");

    /**
     * The current method is not yet implemented.
     */
    IWSError NOT_IMPLEMENTED = new IWSError(999, "Not Implemented");
}
