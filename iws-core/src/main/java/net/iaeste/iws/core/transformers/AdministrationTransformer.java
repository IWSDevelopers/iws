/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.persistence.entities.UserEntity;

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
}
