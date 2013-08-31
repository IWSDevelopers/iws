/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.AdministrationTransformer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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
 * @since   1.7
 */
public final class AdministrationTransformer {

    /**
     * Private Constructor, this is a utility Class.
     */
    private AdministrationTransformer() {}

    public static List<User> transformMembers(final List<UserGroupEntity> members) {
        final List<User> users = new ArrayList<>(members.size());

        for (final UserGroupEntity member : members) {
            users.add(transform(member).getUser());
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

//    public static UserGroup transformUserGroup(final UserGroupEntity entity) {
//        final User user;
//
//        if (entity != null) {
//            user = new User();
//
//            final UserEntity userEntity = transform(entity.getUser());
//            final GroupEntity groupEntity = entity.getGroup();
////            user.setUserId(userEntity.getExternalId());
////            user.setUsername(userEntity.getUserName());
////            user.setAlias(userEntity.getAlias());
////            user.setFirstname(userEntity.getFirstname());
////            user.setLastname(userEntity.getLastname());
////            user.setStatus(userEntity.getStatus());
////            user.setPrivacy(userEntity.getPrivateData());
////            user.setNotifications(userEntity.getNotifications());
////            user.setMemberCountryId(groupEntity.getCountry().getCountryCode());
//
//            // TODO; Implement the Person Object
//            //user.setPerson(transform(entity.getPerson()));
//        } else {
//            user = null;
//        }
//
//        return user;
//    }

    public static User transform(final UserEntity entity) {
        final User user;

        if (entity != null) {
            user = new User();

            user.setUserId(entity.getExternalId());
            user.setUsername(entity.getUsername());
            user.setFirstname(entity.getFirstname());
            user.setLastname(entity.getLastname());
            user.setStatus(entity.getStatus());
            user.setPrivacy(entity.getPrivateData());
            user.setNotifications(entity.getNotifications());

            // TODO; Implement the Person Object
            //user.setPerson(transform(entity.getPerson()));
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
            entity.setPrivateData(user.getPrivacy());
            entity.setNotifications(user.getNotifications());

            // TODO; Implement the Person Object
            //entity.setPerson(transform(user.getPerson()));
        } else {
            entity = null;
        }

        return entity;
    }

    public static UserGroup transform(final UserGroupEntity entity) {
        final UserGroup userGroup;

        if (entity != null) {
            userGroup = new UserGroup();

            userGroup.setId(entity.getExternalId());
            userGroup.setUser(transform(entity.getUser()));
            userGroup.setGroup(CommonTransformer.transform(entity.getGroup()));
            userGroup.setRole(transform(entity.getRole()));
            userGroup.setTitle(entity.getTitle());
            userGroup.setOnPublicList(entity.getOnPublicList());
            userGroup.setOnPrivateList(entity.getOnPrivateList());
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

            entity.setExternalId(userGroup.getId());
            entity.setUser(transform(userGroup.getUser()));
            entity.setGroup(CommonTransformer.transform(userGroup.getGroup()));
            entity.setRole(transform(userGroup.getRole()));
            entity.setTitle(userGroup.getTitle());
            entity.setOnPublicList(userGroup.isOnPublicList());
            entity.setOnPrivateList(userGroup.isOnPrivateList());
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
