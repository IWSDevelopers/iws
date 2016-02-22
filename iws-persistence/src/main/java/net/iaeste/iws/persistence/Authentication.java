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
package net.iaeste.iws.persistence;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.util.Traceable;
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
 * @since   IWS 1.0
 */
public final class Authentication implements Traceable {

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
     * @param token  User Authentication Token
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
     * Default Constructor, for private access, meaning access to personal data
     * where the GroupId is implicit the users private Group.
     *
     * @param token  User Authentication Token
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTraceId() {
        return traceId;
    }
}
