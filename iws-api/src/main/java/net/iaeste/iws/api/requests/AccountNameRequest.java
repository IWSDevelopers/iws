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
import net.iaeste.iws.api.dtos.User;
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
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accountNameRequest", propOrder = { "user", "firstname", "lastname" })
public final class AccountNameRequest extends Actions {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true)                   private User user = null;
    @XmlElement(required = true, nillable = true)  private String firstname = null;
    @XmlElement(required = true, nillable = true)  private String lastname = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Default Constructor.
     */
    public AccountNameRequest() {
        super(EnumSet.of(Action.PROCESS), Action.PROCESS);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the User Account to change the name of, i.e. to change either the
     * firstname or lastname of.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the given
     * value is not a valid User Object.</p>
     *
     * @param user User Object
     * @throws IllegalArgumentException if the given value is invalid
     */
    public void setUser(final User user) {
        ensureNotNullAndVerifiable("user", user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setFirstname(final String firstname) {
        ensureNotTooLong("firstname", firstname, 50);
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(final String lastname) {
        ensureNotTooLong("lastname", lastname, 50);
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
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

        isNotNull(validation, "user", user);

        return validation;
    }
}
