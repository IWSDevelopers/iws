/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.Authentication
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;

/**
 * For our internal handling, we need to have the live entities for both the
 * user and the group at hand. This Object is created as part of the
 * Authorization mechanism.<br />
 *   The Monitoring mechanism requires that we have some sort of identification
 * at hand, and this Object will provide it.<br />
 *   The Object also contains the traceId, used for all non-trace related
 * logging.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class Authentication {

    private final AuthenticationToken token;
    private final UserEntity user;
    private final GroupEntity group;
    private final String traceId;

    /**
     * Minimal Constructor.
     *
     * @param user    User Entity for the current user
     * @param traceId Trace Id for logging and issue tracking
     */
    public Authentication(final UserEntity user, final String traceId) {
        this.user = user;
        this.traceId = traceId;

        token = null;
        group = null;
    }

    /**
     * Full Constructor.
     *
     * @param token  User Authenticatin Token
     * @param user   User Entity for the current user
     * @param group  Group Entity for the group being worked with
     * @param traceId Trace Id for logging and issue tracking
     */
    public Authentication(final AuthenticationToken token, final UserEntity user, final GroupEntity group, final String traceId) {
        this.token = token;
        this.user = user;
        this.group = group;
        this.traceId = traceId;
    }

    /**
     * Default Constructor, for private access, mening access to personal data
     * where the GroupId is implicit the users private Group.
     *
     * @param token  User Authenticatin Token
     * @param user   User Entity for the current user
     */
    public Authentication(final AuthenticationToken token, final UserEntity user) {
        this.token = token;
        this.user = user;
        this.traceId = token.getTraceId();

        group = null;
    }

    public AuthenticationToken getToken() {
        return token;
    }

    public UserEntity getUser() {
        return user;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public String getTraceId() {
        return traceId;
    }
}
