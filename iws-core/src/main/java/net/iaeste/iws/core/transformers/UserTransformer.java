/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.UserTransformer
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
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class UserTransformer {
    private UserTransformer() {
    }

    public static UserEntity transform(final User user) {
        UserEntity result = null;

        if (user != null) {
            result = new UserEntity();

            result.setId(user.getUserId());
            result.setPassword(user.getPassword());
            result.setUserName(user.getUsername());
        }
        return result;
    }

    public static User transform(final UserEntity user) {
        User result = null;

        if (user != null) {
            result = new User();

            result.setUserId(user.getId());
            result.setPassword(user.getPassword());
            result.setUsername(user.getUserName());
        }
        return result;
    }
}