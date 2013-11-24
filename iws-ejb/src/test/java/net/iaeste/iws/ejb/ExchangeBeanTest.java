/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.ExchangeBeanTest
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

import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.exchange.FetchOffersRequest;
import net.iaeste.iws.api.responses.exchange.FetchOffersResponse;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.ejb.util.SettingJndiProperties;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class ExchangeBeanTest {

    private final EntityManager mockedEntityManager = mock(EntityManager.class);
    private final Notifications notifications = mock(Notifications.class);
    private ExchangeBean bean = null;

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
        bean = new ExchangeBean();

        notificationManagerBean.setNotifications(notifications);
        bean.setEntityManager(mockedEntityManager);
        bean.setNotificationManager(notificationManagerBean);
        bean.postConstruct();
    }

    @Test
    public void testDummy() {
        final AuthenticationToken token = null;
        final FetchOffersRequest request = null;
        final FetchOffersResponse response = bean.fetchOffers(token, request);
        assertThat(response.isOk(), is(false));
    }
}
