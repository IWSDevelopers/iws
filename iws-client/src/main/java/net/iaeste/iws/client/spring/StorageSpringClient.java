/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.spring.StorageSpringClient
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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
import net.iaeste.iws.ejb.NotificationManagerBean;
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
     * transactional daos. The EntityManager instance can only be injected into
     * the Spring Beans, we cannot create a Spring Bean for the Committees EJB
     * otherwise.
     *
     * @param entityManager Spring controlled EntityManager instance
     */
    @PersistenceContext
    public void init(final EntityManager entityManager) {
        // Create the Notification Spy, and inject it
        final Notifications notitications = NotificationSpy.getInstance();
        final NotificationManagerBean notificationBean = new NotificationManagerBean();
        notificationBean.setNotifications(notitications);

        // Create an Committees EJB, and inject the EntityManager & Notification Spy
        final StorageBean storageBean = new StorageBean();
        storageBean.setEntityManager(entityManager);
        storageBean.setNotificationManager(notificationBean);
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
