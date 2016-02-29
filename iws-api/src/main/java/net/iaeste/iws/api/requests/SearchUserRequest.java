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
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.util.AbstractVerification;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchUserRequest", propOrder = { "group", "name" })
public final class SearchUserRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true)  private Group group = null;
    @XmlElement(required = true, nillable = false) private String name = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * <p>Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.</p>
     */
    public SearchUserRequest() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * <p>Default Constructor.</p>
     *
     * @param group The Group to search within
     * @param name  The Partial name of the user to search for
     */
    public SearchUserRequest(final Group group, final String name) {
        setGroup(group);
        setName(name);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the Member Group to search for members of. If the Group is not a
     * Members Group, then the result will be empty. If no Group is defined,
     * then the search is made among all Users.</p>
     *
     * @param group Member Group to search in
     * @throws IllegalArgumentException if not a valid Group
     */
    public void setGroup(final Group group) {
        ensureVerifiable("group", group);
        this.group = new Group(group);
    }

    public Group getGroup() {
        return new Group(group);
    }

    /**
     * <p>Sets the (partial) name of the user to search for. If the name only
     * consists of one part (no spaces), then the search will look for matching
     * first and last names. If the name is two part (separated by space), then
     * the search will use the first part as the potential firstname and second
     * part as potential lastname. If the name consists of more blocks, then
     * these will be ignored.</p>
     *
     * <p>If nothing is provided, i.e. if the field is set to null or an empty
     * String, then the method will throw an
     * {@code IllegalArgumentException}.</p>
     *
     * @param name Partial name to search for
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setName(final String name) {
        ensureNotNullOrEmpty("name", name);
        this.name = name;
    }

    public String getName() {
        return name;
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

        isNotNull(validation, "name", name);

        return validation;
    }
}
