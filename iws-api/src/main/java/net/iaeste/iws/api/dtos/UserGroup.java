/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.UserGroup
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Date;

import java.util.HashMap;
import java.util.Map;

/**
 * This Object contains the information about a User in a Group relation. It is
 * used for fetching user information. If a user has chosen to allow private
 * information to be displayed, then it is also set - otherwise it isn't.<br />
 *   Note, this Object is purely for reading information about a User, it does
 * not provide details about Permissions, or anything else.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection OverlyComplexMethod
 */
public final class UserGroup extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String userGroupId = null;
    private User user = null;
    private Group group = null;
    private Role role = null;
    private String title = null;
    private boolean onPublicList = false;
    private boolean onPrivateList = false;
    private Date memberSince = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public UserGroup() {
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
            memberSince = userGroup.memberSince;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setUserGroupId(final String userGroupId) {
        ensureValidId("userGroupId", userGroupId);
        this.userGroupId = userGroupId;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUser(final User user) {
        ensureNotNullAndVerifiable("user", user);
        this.user = new User(user);
    }

    public User getUser() {
        return new User(user);
    }

    public void setGroup(final Group group) {
        ensureNotNullAndVerifiable("group", group);
        this.group = new Group(group);
    }

    public Group getGroup() {
        return new Group(group);
    }

    public void setRole(final Role role) {
        ensureNotNullAndVerifiable("role", role);
        this.role = new Role(role);
    }

    public Role getRole() {
        return role;
    }

    public void setTitle(final String title) {
        ensureNotTooLong("title", title, 50);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setOnPublicList(final boolean onPublicList) {
        this.onPublicList = onPublicList;
    }

    public boolean isOnPublicList() {
        return onPublicList;
    }

    public void setOnPrivateList(final boolean onPrivateList) {
        this.onPrivateList = onPrivateList;
    }

    public boolean isOnPrivateList() {
        return onPrivateList;
    }

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

        isNotNull(validation, "user", user);
        isNotNull(validation, "group", group);
        isNotNull(validation, "role", role);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserGroup)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }

        final UserGroup userGroup = (UserGroup) obj;

        if (userGroupId != null ? !userGroupId.equals(userGroup.userGroupId) : userGroup.userGroupId != null) {
            return false;
        }
        if (user != null ? !user.equals(userGroup.user) : userGroup.user != null) {
            return false;
        }
        if (group != null ? !group.equals(userGroup.group) : userGroup.group != null) {
            return false;
        }
        if (role != null ? !role.equals(userGroup.role) : userGroup.role != null) {
            return false;
        }
        if (title != null ? !title.equals(userGroup.title) : userGroup.title != null) {
            return false;
        }
        if (onPublicList != userGroup.onPublicList) {
            return false;
        }
        if (onPrivateList != userGroup.onPrivateList) {
            return false;
        }

        return !(memberSince != null ? !memberSince.equals(userGroup.memberSince) : userGroup.memberSince != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (userGroupId != null ? userGroupId.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (user != null ? user.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (group != null ? group.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (role != null ? role.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (title != null ? title.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (onPublicList ? 1 : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (onPrivateList ? 1 : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (memberSince != null ? memberSince.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "UserGroup{" +
                "userGroupId='" + userGroupId + '\'' +
                ", user=" + user +
                ", group=" + group +
                ", role=" + role +
                ", title='" + title + '\'' +
                ", onPublicList=" + onPublicList +
                ", onPrivateList=" + onPrivateList +
                ", memberSince=" + memberSince +
                '}';
    }
}
