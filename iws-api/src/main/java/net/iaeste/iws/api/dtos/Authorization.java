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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Contains the information about a permission.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authorization", propOrder = { "userGroup" })
public final class Authorization implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = false)
    private UserGroup userGroup = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Authorization() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Default Constructor.
     *
     * @param userGroup UserGroup
     */
    public Authorization(final UserGroup userGroup) {
        setUserGroup(userGroup);
    }

    /**
     * Copy Constructor.
     *
     * @param authorization Authorization Object to copy
     */
    public Authorization(final Authorization authorization) {
        if (authorization == null) {
            throw new IllegalArgumentException("the authorization Object cannot be null.");
        }

        setUserGroup(authorization.userGroup);
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

    /**
     * Please use the #getUserGroup method.
     *
     * @return Group from the UserGroup Object
     * @deprecated please use the userGroup Object instead
     */
    @Deprecated
    public Group getGroup() {
        return new Group(userGroup.getGroup());
    }

    /**
     * Please use the #getUserGroup method.
     *
     * @return Role from the UserGroup Object
     * @deprecated please use the userGroup Object instead
     */
    @Deprecated
    public Role getRole() {
        return new Role(userGroup.getRole());
    }
}
