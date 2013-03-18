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
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.persistence.entities.GroupEntity;
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

    public static User transform(final UserGroupEntity entity) {
        final User user;
        if (entity != null) {
            user = new User();

            final UserEntity userEntity = entity.getUser();
            final GroupEntity groupEntity = entity.getGroup();
            user.setUserId(userEntity.getExternalId());
            user.setFirstname(userEntity.getFirstname());
            user.setLastname(userEntity.getLastname());
            user.setStatus(userEntity.getStatus());
            user.setPrivacy(userEntity.getPrivateData());
            user.setNotifications(userEntity.getNotifications());
            user.setMemberCountryId(groupEntity.getCountry().getCountryId());

            // TODO; Implement the Person Object
            //user.setPerson(transform(entity.getPerson()));
        } else {
            user = null;
        }
        return user;
    }

    public static User transform(final UserEntity entity) {
        final User user;
        if (entity != null) {
            user = new User();

            user.setUserId(entity.getExternalId());
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

    public static Group transform(final GroupEntity entity) {
        final Group group;

        if (entity != null) {
            group = new Group();

            group.setGroupId(entity.getExternalId());
            group.setGroupName(entity.getGroupName());
            group.setGroupType(entity.getGroupType().getGrouptype());
            group.setCountryId(entity.getCountry().getCountryId());
            group.setDescription(entity.getDescription());
        } else {
            group = null;
        }

        return group;
    }

    public static List<Group> transform(List<GroupEntity> entities) {
        final List<Group> list = new ArrayList<>(entities.size());

        for (final GroupEntity entity : entities) {
            list.add(transform(entity));
        }

        return list;
    }

    public static GroupEntity transform(final Group group) {
        final GroupEntity entity;

        if (group != null) {
            entity = new GroupEntity();

            entity.setDescription(group.getDescription());
        } else {
            entity = null;
        }

        return entity;
    }
}
