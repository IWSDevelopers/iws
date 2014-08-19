/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.CommitteeBeanTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.CommitteeRequest;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.ejb.util.SettingJndiProperties;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.persistence.EntityManager;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Ignore("The entire test needs rewriting!")
public class CommitteeBeanTest {

    private final EntityManager mockedEntityManager = mock(EntityManager.class);
    private final Notifications notifications = mock(Notifications.class);
    private CommitteeBean bean = null;

    private static SimpleNamingContextBuilder builder;

    @AfterClass
    public static void afterClass() throws Exception {
        builder.clear();
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
        builder.bind("iws-setting", SettingJndiProperties.getProperties());
    }

    @Before
    public void before() {
        final NotificationManagerBean notificationManagerBean = new NotificationManagerBean();
        bean = new CommitteeBean();

        notificationManagerBean.setNotifications(notifications);
        bean.setEntityManager(mockedEntityManager);
        bean.setNotificationManager(notificationManagerBean);
        bean.postConstruct();
    }

    @Test
    public void testDummy() {
        final AuthenticationToken token = null;
        final CommitteeRequest request = null;
        final Fallible response = bean.createCommittee(token, request);
        assertThat(response.isOk(), is(false));
    }
}
