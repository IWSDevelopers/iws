/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.AccessBeanTest
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
import static org.mockito.Mockito.when;

import net.iaeste.iws.api.responses.FetchPermissionResponse;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.ejb.util.SettingJndiProperties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import java.util.Properties;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class AccessBeanTest {

    private final EntityManager mockedEntityManager = mock(EntityManager.class);
    private final Notifications notifications = mock(Notifications.class);
    private AccessBean bean = null;

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
    public void before() throws NamingException{
        final NotificationManagerBean notificationManagerBean = new NotificationManagerBean();
        bean = new AccessBean();

        notificationManagerBean.setNotifications(notifications);
        bean.setEntityManager(mockedEntityManager);
        bean.setNotificationManager(notificationManagerBean);
        bean.postConstruct();
    }

    @Test
    public void testDummy() {
        final FetchPermissionResponse response = bean.fetchPermissions(null);
        assertThat(response.isOk(), is(false));
    }
}
