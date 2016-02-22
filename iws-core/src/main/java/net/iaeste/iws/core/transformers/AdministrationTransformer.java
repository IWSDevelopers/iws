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
package net.iaeste.iws.core.transformers;

import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Role;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.UserGroup;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class AdministrationTransformer {

    /**
     * Private Constructor, this is a utility Class.
     */
    private AdministrationTransformer() {}

    public static List<UserGroup> transformMembers(final List<UserGroupEntity> members) {
        final List<UserGroup> users = new ArrayList<>(members.size());

        for (final UserGroupEntity member : members) {
            users.add(transform(member));
        }

        return users;
    }

    public static Role transform(final RoleEntity entity) {
        final Role role;

        if (entity != null) {
            role = new Role();

            role.setRoleId(entity.getExternalId());
            role.setRoleName(entity.getRole());
        } else {
            role = null;
        }

        return role;
    }

    public static RoleEntity transform(final Role role) {
        final RoleEntity entity;

        if (role != null) {
            entity = new RoleEntity();

            entity.setExternalId(role.getRoleId());
            entity.setRole(role.getRoleName());
        } else {
            entity = null;
        }

        return entity;
    }

    public static List<RoleEntity> transformRoles(final List<Role> roles) {
        final List<RoleEntity> entities = new ArrayList<>(roles.size());

        for (final Role role : roles) {
            entities.add(transform(role));
        }

        return entities;
    }

    public static List<Role> transformRoleEntities(final List<RoleEntity> entities) {
        final List<Role> roles = new ArrayList<>(entities.size());

        for (final RoleEntity entity : entities) {
            roles.add(transform(entity));
        }

        return roles;
    }

    public static User transform(final UserEntity entity) {
        final User user;

        if (entity != null) {
            user = new User();

            user.setUserId(entity.getExternalId());
            user.setUsername(entity.getUsername());
            user.setFirstname(entity.getFirstname());
            user.setLastname(entity.getLastname());
            user.setAlias(entity.getAlias());
            user.setStatus(entity.getStatus());
            user.setType(entity.getType());
            user.setPrivacy(entity.getPrivateData());
            user.setNotifications(entity.getNotifications());
            user.setPerson(CommonTransformer.transform(entity.getPerson()));
        } else {
            user = null;
        }

        return user;
    }

    public static UserEntity transform(final User user) {
        final UserEntity entity;

        if (user != null) {
            entity = new UserEntity();

            entity.setExternalId(user.getUserId());
            entity.setUsername(user.getUsername());
            entity.setFirstname(user.getFirstname());
            entity.setLastname(user.getLastname());
            entity.setStatus(user.getStatus());
            entity.setType(user.getType());
            entity.setPrivateData(user.getPrivacy());
            entity.setNotifications(user.getNotifications());
            //entity.setPerson(CommonTransformer.transform(user.getPerson()));
        } else {
            entity = null;
        }

        return entity;
    }

    public static UserGroup transform(final UserGroupEntity entity) {
        final UserGroup userGroup;

        if (entity != null) {
            userGroup = new UserGroup();

            userGroup.setUserGroupId(entity.getExternalId());
            userGroup.setUser(transform(entity.getUser()));
            userGroup.setGroup(CommonTransformer.transform(entity.getGroup()));
            userGroup.setRole(transform(entity.getRole()));
            userGroup.setTitle(entity.getTitle());
            userGroup.setOnPublicList(entity.getOnPublicList());
            userGroup.setOnPrivateList(entity.getOnPrivateList());
            userGroup.setWriteToPrivateList(entity.getWriteToPrivateList());
            userGroup.setMemberSince(CommonTransformer.convert(entity.getCreated()));
        } else {
            userGroup = null;
        }

        return userGroup;
    }

    public static UserGroupEntity transform(final UserGroup userGroup) {
        final UserGroupEntity entity;

        if (userGroup != null) {
            entity = new UserGroupEntity();

            entity.setExternalId(userGroup.getUserGroupId());
            entity.setUser(transform(userGroup.getUser()));
            entity.setGroup(CommonTransformer.transform(userGroup.getGroup()));
            entity.setRole(transform(userGroup.getRole()));
            entity.setTitle(userGroup.getTitle());
            entity.setOnPublicList(userGroup.isOnPublicList());
            entity.setOnPrivateList(userGroup.isOnPrivateList());
            entity.setWriteToPrivateList(userGroup.mayWriteToPrivateList());
        } else {
            entity = new UserGroupEntity();
        }

        return entity;
    }

    public static List<Group> transform(final List<GroupEntity> entities) {
        final List<Group> list = new ArrayList<>(entities.size());

        for (final GroupEntity entity : entities) {
            list.add(CommonTransformer.transform(entity));
        }

        return list;
    }
}
