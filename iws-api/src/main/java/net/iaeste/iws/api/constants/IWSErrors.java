/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
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
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
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
    IWSError VERIFICATION_ERROR = new IWSError(401, "Given data is insufficient to properly handle request");

    /**
     * If the user making the request is not properly authenticated, i.e. no
     * proper user credentials like username/password or session checksum.
     */
    IWSError AUTHENTICATION_ERROR = new IWSError(402, "User Authentication problem");

    /**
     * If the user making the request is not allowed to perform the desired
     * action.
     */
    IWSError AUTHORIZATION_ERROR = new IWSError(403, "User Authorization problem");

    /**
     * If the user is attempting to access/process an Object, without being
     * allowed to do so (missing Group ownership).
     */
    IWSError NOT_PERMITTED = new IWSError(404, "User is not permitted to process the requested Object");

    /**
     * If the User tries to create a new Session, while already having an
     * Active Session, an error should be thrown. This will prevent that users
     * try to log in multiple places, but forget to log out again.
     */
    IWSError SESSION_EXISTS = new IWSError(405, "User can only hold one active Session at the time");

    /**
     * The database is inaccessible.
     */
    IWSError DATABASE_UNREACHABLE = new IWSError(501, "Database unreachable");

    /**
     * If a situation arise, where there exists multiple similar records, which
     * should not be allowed.
     */
    IWSError DATABASE_CONSTRAINT_INCONSISTENCY = new IWSError(502, "Database Constraint Inconsistency");

    /**
     * Unknown Persistency Error.
     */
    IWSError PERSISTENCE_ERROR = new IWSError(503, "Persistency Error");

    /**
     * The Identification Error is passed when trying to access Objects, that
     * either doesn't exist, or where the requesting user do not have sufficient
     * permissions.
     */
    IWSError OBJECT_IDENTIFICATION_ERROR = new IWSError(504, "Object Identification Error");

    /**
     * The processing of the request failed.
     */
    IWSError PROCESSING_FAILURE = new IWSError(505, "Request cannot be processed");

    /**
     * Error reading or writing the monitoring data in serialized form.
     */
    IWSError MONITORING_FAILURE = new IWSError(506, "Monitoring Serialization Error");

    IWSError USER_ACCOUNT_EXISTS = new IWSError(601, "User Account Already exists");

    IWSError NO_USER_ACCOUNT_FOUND = new IWSError(602, "No User Account exists");

    IWSError INVALID_NOTIFICATION = new IWSError(603, "Notification Type is not allowed in this context.");

    IWSError CANNOT_UPDATE_PASSWORD = new IWSError(604, "The provided old Password is invalid.");

    /**
     * The current method is not yet implemented.
     */
    IWSError NOT_IMPLEMENTED = new IWSError(999, "Not Implemented");
}
