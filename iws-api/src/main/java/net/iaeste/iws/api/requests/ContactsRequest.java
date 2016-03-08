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
import net.iaeste.iws.api.enums.ContactsType;
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
@XmlType(name = "contactsRequest", propOrder = { "userId", "groupId", "type" })
public final class ContactsRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private static final String FIELD_USERID = "userId";
    private static final String FIELD_GROUPID = "groupId";

    @XmlElement(required = true, nillable = true) private String userId = null;
    @XmlElement(required = true, nillable = true) private String groupId = null;
    @XmlElement(required = true, nillable = true) private ContactsType type = ContactsType.OTHER;

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the UserId, so the User with a list of all Group associations can
     * be retrieved. This will also set the ContactType to User.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the given
     * UserID is invalid.</p>
     *
     * @param userId Id of the User to fetch details for
     * @throws IllegalArgumentException if the UserId is invalid
     */
    public void setUserId(final String userId) {
        ensureNotNullAndValidId(FIELD_USERID, userId);
        this.type = ContactsType.USER;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    /**
     * <p>Sets the GroupId, so the Group with a list of the associated Users can
     * be retrieved. This will also set the ContactType to Group.</p>
     *
     * <p>The method will throw an {@code IllegalArgumentException} if the given
     * GroupId is invalid.</p>
     *
     * @param groupId Id of the Group to fetch details for
     * @throws IllegalArgumentException if the GroupId is invalid
     */
    public void setGroupId(final String groupId) {
        ensureNotNullAndValidId(FIELD_GROUPID, groupId);
        this.type = ContactsType.GROUP;
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public ContactsType getType() {
        return type;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        if ((type == ContactsType.USER) && (userId == null)) {
            validation.put(FIELD_USERID, "Invalid User Request, " + FIELD_USERID + " is null.");
        } else if ((type == ContactsType.GROUP) && (groupId == null)) {
            validation.put(FIELD_GROUPID, "Invalid Group Request, " + FIELD_GROUPID + " is null.");
        }

        return validation;
    }
}
