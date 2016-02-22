/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Traceable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * All requests (with the exception of the initial Authorization request) is
 * made with this Object as the first parameter. The Token contains  enough
 * information to positively identify the user, who initiated a given Request.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authenticationToken", propOrder = { "token", "groupId" })
public final class AuthenticationToken extends AbstractVerification implements Traceable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    // The length of the supported Hashcode algorithms
    private static final int LENGTH_SHA2_512 = 128;
    private static final int LENGTH_SHA2_384 = 96;
    private static final int LENGTH_SHA2_256 = 64;

    /** The actual token, stored as an ASCII value. */
    @XmlElement(required = true, nillable = false)
    private String token = null;

    /** For Group Authorization, the GroupId must also be provided. */
    @XmlElement(required = true, nillable = true)
    private String groupId = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public AuthenticationToken() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Default Constructor.
     *
     * @param  token  The Token, i.e. currently active Cryptographic Checksum
     * @throws IllegalArgumentException if the token is invalid
     */
    public AuthenticationToken(final String token) throws IllegalArgumentException {
        setToken(token);
    }

    /**
     * Full Constructor, for operations where it is not possible to uniquely
     * identify the Group for the request.
     *
     * @param  token  The Token, i.e. currently active Cryptographic Checksum
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
     * Sets the users Cryptographic Authentication Token, the token must be
     * valid, i.e. not null and matching one of the supported cryptographic
     * algorithms. If the token is invalid, then the setter will throw an
     * {@code IllegalArgumentException}.
     *
     * @param  token  Cryptographic Authentication Token
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
                this.token = token;
                break;
            default:
                throwIllegalArgumentException("token");
        }
    }

    /**
     * Retrieves the Token from the object.
     *
     * @return Cryptographic Token
     */
    public String getToken() {
        return token;
    }

    /**
     * <p>Sets the GroupId, for which the user wishes invoke a functionality.
     * This is required, if the functionality cannot be uniquely identified for
     * the user based on the implicit UserId &amp; PermissionId.</p>
     *
     * <p>If the provided GroupId is not valid, then method will throw an
     * {@code IllegalArgumentException}.</p>
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTraceId() {
        return (token != null) ? token.substring(0, 8) : "none";
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
}
