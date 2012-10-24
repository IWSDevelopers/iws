/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.UserTransformerTest
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
import net.iaeste.iws.api.dtos.UserTestUtility;
import net.iaeste.iws.persistence.entities.UserEntity;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class UserTransformerTest {
    @Test
    public void testCopyingMinimalOfferToEntity() {
        final User user = UserTestUtility.getMinimalUser();
        final UserEntity entity = UserTransformer.transform(user);

        assertThat(user.getUserId(), is(entity.getId()));
        assertThat(user.getUsername(), is(entity.getUserName()));
        assertThat(user.getPassword(), is(entity.getPassword()));
    }

    @Test
    public void testCopyingMinimalOfferToDto() {
        final UserEntity entity = getMinimalUserEntity();
        final User user = UserTransformer.transform(entity);

        assertThat(user.getUserId(), is(entity.getId()));
        assertThat(user.getUsername(), is(entity.getUserName()));
        assertThat(user.getPassword(), is(entity.getPassword()));
    }

    @Test
    public void testCopyingBackAndForthFromDto() {
        final User user = UserTestUtility.getMinimalUser();
        final UserEntity entity = UserTransformer.transform(user);
        final User newUser = UserTransformer.transform(entity);
        // we rely on equals method
        assertThat(user, is(newUser));
    }

    @Test
    public void testCopyingBackAndForthFromEmptyDto() {
        final User user = new User();
        final UserEntity entity = UserTransformer.transform(user);
        final User newUser = UserTransformer.transform(entity);
        // we rely on equals method
        assertThat(user, is(newUser));
    }

    private UserEntity getMinimalUserEntity() {
        final UserEntity minimalOffer = new UserEntity();
        minimalOffer.setUserName(UserTestUtility.USERNAME);
        minimalOffer.setPassword(UserTestUtility.PASSWORD);
        return minimalOffer;
    }

    private UserEntity getFullUserEntity() {
        final UserEntity user = getMinimalUserEntity();
        return user;
    }
}