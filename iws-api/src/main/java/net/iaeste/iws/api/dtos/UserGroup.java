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
import net.iaeste.iws.api.util.Verifications;
import net.iaeste.iws.api.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>This Object contains the information about a User in a Group relation. It
 * is used for fetching user information. If a user has chosen to allow private
 * information to be displayed, then it is also set - otherwise it isn't.</p>
 *
 * <p>Note, this Object is purely for reading information about a User, it does
 * not provide details about Permissions, or anything else.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userGroup", propOrder = { "userGroupId", "user", "group", "role", "title", "onPublicList", "onPrivateList", "writeToPrivateList", "memberSince" })
public final class UserGroup extends Verifications {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true)  private String userGroupId = null;
    @XmlElement(required = true)                   private User user = null;
    @XmlElement(required = true)                   private Group group = null;
    @XmlElement(required = true)                   private Role role = null;
    @XmlElement(required = true, nillable = true)  private String title = null;
    @XmlElement(required = true)                   private boolean onPublicList = false;
    @XmlElement(required = true)                   private boolean onPrivateList = false;
    @XmlElement(required = true)                   private boolean writeToPrivateList = false;
    @XmlElement(required = true, nillable = true)  private Date memberSince = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public UserGroup() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Copy Constructor.
     *
     * @param userGroup User Group Object to copy
     */
    public UserGroup(final UserGroup userGroup) {
        if (userGroup != null) {
            userGroupId = userGroup.userGroupId;
            user = new User(userGroup.user);
            group = new Group(userGroup.group);
            role = new Role(userGroup.role);
            title = userGroup.title;
            onPublicList = userGroup.onPublicList;
            onPrivateList = userGroup.onPrivateList;
            writeToPrivateList = userGroup.writeToPrivateList;
            memberSince = userGroup.memberSince;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the UserGroup Id, which is the internally generated key for this
     * Object. Note, that the presence of the value will determine if the IWS
     * should process this record as if it exist or not. If the Id is set, but
     * no record exists, then the system will reply with an error. Likewise, if
     * no Id is provided, but the record exists, the system will reply with an
     * error.</p>
     *
     * <p>The value must be a valid Id, otherwise the method will throw an
     * {@code IllegalArgumentException}.</p>
     *
     * @param userGroupId UserGroup Id
     * @throws IllegalArgumentException if the Id is set but invalid
     * @see Verifications#UUID_FORMAT
     */
    public void setUserGroupId(final String userGroupId) {
        ensureValidId("userGroupId", userGroupId);
        this.userGroupId = userGroupId;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    /**
     * Sets the User, which should have a Group relation. The User must be
     * defined, otherwise the method will throw an
     * {@code IllegalArgumentException}.
     *
     * @param user The User to create / manage a Group relation for
     * @throws IllegalArgumentException If the User is undefined
     */
    public void setUser(final User user) {
        ensureNotNullAndVerifiable("user", user);
        this.user = new User(user);
    }

    public User getUser() {
        return new User(user);
    }

    /**
     * Sets the Group, which the User is / should be related to. The Group must
     * be defined, otherwise the method will throw an
     * {@code IllegalArgumentException}.
     *
     * @param group The Group, which the User is / should be related to
     * @throws IllegalArgumentException If the Group is invalid
     */
    public void setGroup(final Group group) {
        ensureNotNullAndVerifiable("group", group);
        this.group = new Group(group);
    }

    public Group getGroup() {
        return new Group(group);
    }

    /**
     * <p>Sets the Users Role within a Group. The user must have a role, to what
     * and how the user may interact with the data belonging to the Group.</p>
     *
     * <p>The User may have any valid Role, however there can be only one User
     * who have the role "Owner" within a Group.</p>
     *
     * <p>The value is mandatory, and if not set, then the method will thrown
     * an {@code IllegalArgumentException}.</p>
     *
     * @param role User Role within the Group
     * @throws IllegalArgumentException if the Role is invalid.
     */
    public void setRole(final Role role) {
        ensureNotNullAndVerifiable("role", role);
        this.role = new Role(role);
    }

    public Role getRole() {
        return role;
    }

    /**
     * Sets the Users custom title within the Group. The value may not exceed
     * the maximum allowed value of 50 characters, if it exceeds this - then the
     * method will throw an {@code IllegalArgumentException}.
     *
     * @param title Custom title for the user
     * @throws IllegalArgumentException if the value exceeds 50 characters
     */
    public void setTitle(final String title) {
        ensureNotTooLong("title", title, 50);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    /**
     * If the User should be on the public mailinglist, i.e. the @iaeste.org
     * mailinglist. Note, that only if the Group allows public mailingLists will
     * this have an affect for the user.
     *
     * @param onPublicList True if the user should be on, otherwise false
     */
    public void setOnPublicList(final boolean onPublicList) {
        this.onPublicList = onPublicList;
    }

    public boolean isOnPublicList() {
        return onPublicList;
    }

    /**
     * If the User should be on the private mailinglist, i.e. the @iaeste.net
     * mailinglist. Note, that only if the Group allows private mailingLists
     * will this have an effect for the user.
     *
     * @param onPrivateList True if the user should be on, otherwise false
     */
    public void setOnPrivateList(final boolean onPrivateList) {
        this.onPrivateList = onPrivateList;
    }

    public boolean isOnPrivateList() {
        return onPrivateList;
    }

    /**
     * If the user may write to the private mailing list, i.e. the @iaeste.net
     * mailinglist. Note that this requires that the user is also on the private
     * mailinglist, otherwise the field is ignored.
     *
     * @param writeToPrivateList True if user may write to private list, otherwise false
     */
    public void setWriteToPrivateList(final boolean writeToPrivateList) {
        this.writeToPrivateList = writeToPrivateList;
    }

    public boolean mayWriteToPrivateList() {
        return writeToPrivateList;
    }

    /**
     * The timestamp, for when the User was added to the Group. Note, that this
     * value is controlled by the IWS, meaning that changing it will not have
     * any effect.
     *
     * @param memberSince Date when the User was added to the Group
     */
    public void setMemberSince(final Date memberSince) {
        this.memberSince = memberSince;
    }

    public Date getMemberSince() {
        return memberSince;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNullAndVerifiable(validation, "user", user);
        isNotNullAndVerifiable(validation, "group", group);
        isNotNull(validation, "role", role);

        return validation;
    }
}
