/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.StorageBean
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

import net.iaeste.iws.api.Storage;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.api.requests.FetchFileRequest;
import net.iaeste.iws.api.requests.FetchFolderRequest;
import net.iaeste.iws.api.requests.FileRequest;
import net.iaeste.iws.api.requests.FolderRequest;
import net.iaeste.iws.api.responses.FetchFileResponse;
import net.iaeste.iws.api.responses.FetchFolderResponse;
import net.iaeste.iws.api.responses.FileResponse;
import net.iaeste.iws.api.responses.FolderResponse;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.StorageController;
import net.iaeste.iws.core.notifications.Notifications;
import net.iaeste.iws.core.services.ServiceFactory;
import net.iaeste.iws.ejb.cdi.IWSBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Committee Bean, serves as the default EJB for the IWS Committee interface.
 * It uses JNDI instances for the Persistence Context and the Notification
 * Manager Bean.<br />
 *   The default implemenentation will catch any uncaught Exception. However,
 * there are some types of Exceptions that should be handled by the Contained,
 * and not by our error handling. Thus, only Runtime exceptions are caught. If
 * a Checked Exception is discovered that also needs our attention, then the
 * error handling must be extended to also deal with this. But for now, this
 * should suffice.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Stateless
@Remote(Storage.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StorageBean implements Storage {

    private static final Logger log = LoggerFactory.getLogger(StorageBean.class);
    @Inject @IWSBean private EntityManager entityManager;
    @Inject @IWSBean private Notifications notifications;
    @Inject @IWSBean private SessionRequestBean session;
    @Inject @IWSBean private Settings settings;
    private Storage controller = null;

    /**
     * Setter for the JNDI injected persistence context. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param entityManager Transactional Entity Manager instance
     */
    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Setter for the JNDI injected notification bean. This allows us to also
     * test the code, by invoking these setters on the instantited Object.
     *
     * @param notificationManager Notification Manager Bean
     */
    public void setNotificationManager(final NotificationManagerLocal notificationManager) {
        this.notifications = notificationManager;
    }

    /**
     * Setter for the JNDI injected Session Request bean. This allows us to also
     * test the code, by invoking these setters on the instantiated Object.
     *
     * @param sessionRequestBean Session Request Bean
     */
    public void setSessionRequestBean(final SessionRequestBean sessionRequestBean) {
        this.session = sessionRequestBean;
    }

    /**
     * Setter for the JNDI injected Settings bean. This allows us to also test
     * the code, by invoking these setters on the instantiated Object.
     *
     * @param settings Settings Bean
     */
    public void setSettings(final Settings settings) {
        this.settings = settings;
    }

    @PostConstruct
    public void postConstruct() {
        final ServiceFactory factory = new ServiceFactory(entityManager, notifications, settings);
        controller = new StorageController(factory);
    }

    // =========================================================================
    // Implementation of methods from Storage in the API
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public FolderResponse processFolder(final AuthenticationToken token, final FolderRequest request) {
        final long start = System.nanoTime();
        FolderResponse response;

        try {
            response = controller.processFolder(token, request);
            log.info(session.generateLogAndUpdateSession("processFolder", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("processFolder", start, e, token, request), e);
            response = new FolderResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FetchFolderResponse fetchFolder(final AuthenticationToken token, final FetchFolderRequest request) {
        final long start = System.nanoTime();
        FetchFolderResponse response;

        try {
            response = controller.fetchFolder(token, request);
            log.info(session.generateLogAndUpdateSession("fetchFolder", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("fetchFolder", start, e, token, request), e);
            response = new FetchFolderResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public FileResponse processFile(final AuthenticationToken token, final FileRequest request) {
        final long start = System.nanoTime();
        FileResponse response;

        try {
            response = controller.processFile(token, request);
            log.info(session.generateLogAndUpdateSession("processFile", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("processFile", start, e, token, request), e);
            response = new FileResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FetchFileResponse fetchFile(final AuthenticationToken token, final FetchFileRequest request) {
        final long start = System.nanoTime();
        FetchFileResponse response;

        try {
            response = controller.fetchFile(token, request);
            log.info(session.generateLogAndUpdateSession("fetchFile", start, response, token));
        } catch (RuntimeException e) {
            log.error(session.generateLogAndSaveRequest("fetchFile", start, e, token, request), e);
            response = new FetchFileResponse(IWSErrors.ERROR, e.getMessage());
        }

        return response;
    }
}
