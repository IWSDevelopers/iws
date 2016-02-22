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
package net.iaeste.iws.client.errorhandling;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Access;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.AccessClient;
import net.iaeste.iws.client.spring.Beans;
import net.iaeste.iws.ejb.AccessBean;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * As we have different levels of error handling, it is good to try to properly
 * test some of these.<br />
 *   First level of Error handling, is our normal logic handling, meaning that
 * all known issues should be wrapped into an IWS Exception, these errors are
 * caught by our Controllers in the Core module.<br />
 *   Second level of Error handling, is in our Beans. The beans are catching all
 * Runtime Exceptions, not previously processed. As Runtime exceptions can be
 * thrown for various reasons, we consider these Fatal Errors, as we have
 * entered a state where the system cannot continue, but the system itself is
 * fine - meaning that the error can be corrected by the administrators.<br />
 *   Third level of Error handling, is for Fatal issues. Here, we're relying on
 * the Appication Server to properly log the issues. As a Fatal issue is
 * normally an unrecoverable state where we may not even be able to instantiate
 * a Bean, it makes little sense to try to cover this also. Errors such as
 * OutOfMemory or internal Application Server issues.<br />
 *   Although it may seem crazy to run these 3 types of tests for all our
 * requests, it is done deliberately, as we expect all our requests to behave
 * the same way regarding error handling. Otherwise, a programmer have made a
 * mistake internally. The type of Exception thrown is done for our Persistency
 * layer, since this is the deepest layer where problems may occur.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {Beans.class})
@Ignore("Test cases needs completion")
public class AccessExceptionHandlingTest {

    private static final AuthenticationToken TOKEN = new AuthenticationToken("9635b22b2ff750832146496b8327dc81ed8d79ddd4c8c7448971eceb4667adef");

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testDeprecateSessionNoToken() {
        final Access client = new AccessClient();
        final Fallible response = client.deprecateSession(null);

        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.VERIFICATION_ERROR));
        assertThat(response.getMessage(), is("Invalid Authentication Token provided. Object may not be null."));
    }

    @Test
    public void testDeprecateSessionWithException() {
        final Access client = prepareNPEAccessInstance();
        final Fallible response = client.deprecateSession(TOKEN);

        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(IWSErrors.ERROR));
        assertThat(response.getMessage(), is("Mocked NPE."));
    }

    @Test(expected = OutOfMemoryError.class)
    public void testDeprecateSessionWithError() {
        final Access client = prepareErrorAccessInstance();
        client.deprecateSession(TOKEN);
    }

    // =========================================================================
    // Internal Methods for setting up test environment
    // =========================================================================

    /**
     * Creates a new Access instance, which will throw a NullPointerException.
     *
     * @return Access instance with built-in NullPointerException
     */
    private static Access prepareNPEAccessInstance() {
        final EntityManager entityManager = prepareErrorInstance(new NullPointerException("Mocked NPE."));

        final AccessBean accessBean = new AccessBean();
        accessBean.setEntityManager(entityManager);
        accessBean.setNotificationManager(null);
        accessBean.postConstruct();

        return accessBean;
    }

    /**
     * Creates a new Access instance, which will throw an OutOfMemory Error.
     *
     * @return Access instance, which will throw an OutOfMemory Error
     */
    private static Access prepareErrorAccessInstance() {
        final EntityManager entityManager = prepareErrorInstance(new OutOfMemoryError());

        final AccessBean accessBean = new AccessBean();
        accessBean.setEntityManager(entityManager);
        accessBean.setNotificationManager(null);
        accessBean.postConstruct();

        return accessBean;
    }

    private static EntityManager prepareErrorInstance(final Throwable throwable) {
        final EntityManager entityManager = Mockito.mock(EntityManager.class);
        Mockito.when(entityManager.createNamedQuery(Matchers.<String>any())).thenThrow(throwable);

        return entityManager;
    }
}
