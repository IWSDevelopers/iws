/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.AuthenticationToken
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * All requests (with the exception of the initial Authorization request) is
 * made with this Object as the first parameter. The Token contains  enough
 * information to positively identify the user, who initiated a given Request.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class AuthenticationToken extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    // The length of the supported Hashcode algorithms
    private static final int LENGTH_SHA2_512 = 128;
    private static final int LENGTH_SHA2_384 = 96;
    private static final int LENGTH_SHA2_256 = 64;
    private static final int LENGTH_MD5 = 32;

    /** The actual token, stored as an ASCII value. */
    private String token = null;

    /** For Group Authorization, the GroupId must also be provided. */
    private String groupId = null;

    /**
     * The Transfer Ticket is not used in the standard methods, it is
     * independent of the internal logic, and is purely for the purpose of
     * tracing log messages.
     */
    private String traceId = UUID.randomUUID().toString();

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public AuthenticationToken() {
    }

    /**
     * Default Constructor.
     *
     * @param  token  The Token, i.e. currently active Cryptographical Checksum
     * @throws IllegalArgumentException if the token is invalid
     */
    public AuthenticationToken(final String token) throws IllegalArgumentException {
        setToken(token);
    }

    /**
     * Full Constructor, for operations where it is not possible to uniquely
     * identify the Group for the request.
     *
     * @param  token  The Token, i.e. currently active Cryptographical Checksum
     * @param groupId GroupId for the Authorization check
     * @throws IllegalArgumentException if the token is invalid
     */
    public AuthenticationToken(final String token, final String groupId) throws IllegalArgumentException {
        // We use the setters to set the value, since they can properly handle
        // all validation checks
        setToken(token);
        setGroupId(groupId);
    }

    /**
     * Copy Constructor.
     *
     * @param token  AuthenticationToken Object to copy
     * @throws IllegalArgumentException if the token is invalid
     */
    public AuthenticationToken(final AuthenticationToken token) throws IllegalArgumentException {
        // Check the given Object first
        ensureNotNull("token", token);

        // Since the purpose of the Copy Constructor, is to create an identical
        // Object, we're not going to invoke the setters here.
        setToken(token.token);
        groupId = token.groupId;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the users Cryptographical Authentication Token, the token must be
     * valid, i.e. not null and matching one of the supported cryptographical
     * algorithms. If the token is invalid, then the setter will throw an
     * {@code IllegalArgumentException.
     *
     * @param  token  Cryptographical Authentication Token
     * @throws IllegalArgumentException if the token is invalid
     */
    public void setToken(final String token) throws IllegalArgumentException {
        ensureNotNull("token", token);

        // The token should have a length, matching one of the allowed hashing
        // algorithms. If not, then we'll throw an exception.
        switch (token.length()) {
            case LENGTH_SHA2_512:
            case LENGTH_SHA2_384:
            case LENGTH_SHA2_256:
            case LENGTH_MD5:
                this.token = token;
                break;
            default:
                throwIllegalArgumentException("token");
        }
    }

    /**
     * Retrieves the Token from the object.
     *
     * @return Cryptographical Token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the GroupId, for which the user wishes invoke a functionality. This
     * is required, if the functionality cannot be uniquely identified for the
     * user based on the implicit UserId & PermissionId.<br />
     *   If the provided GroupId is not valid, then an
     * {@code IllegalArgumentException} is thrown.
     *
     * @param groupId  GroupId for the Authorization check
     * @throws IllegalArgumentException if the GroupId is invalid
     */
    public void setGroupId(final String groupId) {
        ensureValidId("groupId", groupId);

        this.groupId = groupId;
    }

    /**
     * Retrieves the GroupId, for which the user wishes to invoke a
     * functionality.
     *
     * @return GroupId for the Authorization check
     */
    public String getGroupId() {
        return groupId;
    }

    public void setTraceId(final String traceId) {
        ensureValidId("traceId", traceId);
        this.traceId = traceId;
    }

    public String getTraceId() {
        return traceId;
    }

    // =========================================================================
    // Standard DTO Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        // As the Setters are verifying all given values, we only
        // need to run checks against nonnull fields here
        isNotNull(validation, "token", token);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AuthenticationToken)) {
            return false;
        }

        final AuthenticationToken that = (AuthenticationToken) obj;

        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) {
            return false;
        }

        return !(token != null ? !token.equals(that.token) : that.token != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = IWSConstants.HASHCODE_INITIAL_VALUE;

        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (token != null ? token.hashCode() : 0);
        hash = IWSConstants.HASHCODE_MULTIPLIER * hash + (groupId != null ? groupId.hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "AuthenticationToken{" +
                "token='" + token + '\'' +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
