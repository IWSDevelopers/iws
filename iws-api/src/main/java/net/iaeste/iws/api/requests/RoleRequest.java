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
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.Role;
import net.iaeste.iws.api.enums.Action;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fileRequest", propOrder = { "role", "roleId" })
public final class RoleRequest extends Actions {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true)  private Role role = null;
    @XmlElement(required = true, nillable = true)  private String roleId = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * <p>Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.</p>
     */
    public RoleRequest() {
        super(EnumSet.of(Action.PROCESS, Action.DELETE), Action.PROCESS);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setRole(final Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRoleId(final String roleId) {
        this.roleId = roleId;
    }

    public String getRoleId() {
        return roleId;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(1);

        isNotNull(validation, "action", action);
        if (action != null) {
            switch (action) {
                case PROCESS:
                    isNotNull(validation, "role", role);
                    break;
                case DELETE:
                    isNotNull(validation, "roleId", roleId);
                    break;
                default:
                    validation.put(FIELD_ACTION, "The Action '" + action + "' is not allowed");
            }
        }

        return validation;
    }
}
