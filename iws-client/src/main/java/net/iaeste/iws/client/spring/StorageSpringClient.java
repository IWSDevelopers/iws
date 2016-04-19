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
package net.iaeste.iws.client.spring;

import net.iaeste.iws.api.Storage;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.FetchFileRequest;
import net.iaeste.iws.api.requests.FetchFolderRequest;
import net.iaeste.iws.api.requests.FileRequest;
import net.iaeste.iws.api.requests.FolderRequest;
import net.iaeste.iws.api.responses.FetchFileResponse;
import net.iaeste.iws.api.responses.FetchFolderResponse;
import net.iaeste.iws.api.responses.FileResponse;
import net.iaeste.iws.api.responses.FolderResponse;
import net.iaeste.iws.client.notifications.NotificationSpy;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.ejb.schedulers.NotificationManagerScheduler;
import net.iaeste.iws.ejb.SessionRequestBean;
import net.iaeste.iws.ejb.StorageBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Transactional
@Repository("storageSpringClient")
public class StorageSpringClient implements Storage {

    private Storage client = null;

    /**
     * Injects the {@code EntityManager} instance required to invoke our
     * transactional DAOs. The EntityManager instance can only be injected into
     * the Spring Beans, we cannot create a Spring Bean for the Committees EJB
     * otherwise.
     *
     * @param entityManager Spring controlled EntityManager instance
     */
    @PersistenceContext
    public void init(final EntityManager entityManager) {
        // Create the Notification Spy, and inject it
        final Notifications notifications = NotificationSpy.getInstance();
        final NotificationManagerScheduler notificationBean = new NotificationManagerScheduler();
        notificationBean.setNotifications(notifications);

        // Create a new SessionRequestBean instance with our entityManager
        final SessionRequestBean sessionRequestBean = new SessionRequestBean();
        sessionRequestBean.setEntityManager(entityManager);
        sessionRequestBean.postConstruct();

        // Create an Committees EJB, and inject the EntityManager & Notification Spy
        final StorageBean storageBean = new StorageBean();
        storageBean.setEntityManager(entityManager);
        storageBean.setNotificationManager(notificationBean);
        storageBean.setSessionRequestBean(sessionRequestBean);
        storageBean.setSettings(Beans.settings());
        storageBean.postConstruct();

        // Set our Committees implementation to the Committees EJB,
        // running withing a "Spring Container".
        client = storageBean;
    }

    // =========================================================================
    // Implementation of methods from Storage in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public FolderResponse processFolder(final AuthenticationToken token, final FolderRequest request) {
        return client.processFolder(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchFolderResponse fetchFolder(final AuthenticationToken token, final FetchFolderRequest request) {
        return client.fetchFolder(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileResponse processFile(final AuthenticationToken token, final FileRequest request) {
        return client.processFile(token, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchFileResponse fetchFile(final AuthenticationToken token, final FetchFileRequest request) {
        return client.fetchFile(token, request);
    }
}
