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
package net.iaeste.iws.api.responses;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.dtos.UserGroup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userGroupResponse", propOrder = "userGroup")
public final class UserGroupResponse extends Response {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The newly created User Object. */
    @XmlElement(required = true, nillable = true)
    private UserGroup userGroup = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public UserGroupResponse() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Default Constructor.
     *
     * @param userGroup Processed User Group relation
     */
    public UserGroupResponse(final UserGroup userGroup) {
        setUserGroup(userGroup);
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public UserGroupResponse(final IWSError error, final String message) {
        super(error, message);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setUserGroup(final UserGroup userGroup) {
        this.userGroup = new UserGroup(userGroup);
    }

    public UserGroup getUserGroup() {
        return new UserGroup(userGroup);
    }
}
