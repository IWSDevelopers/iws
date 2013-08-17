/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.notifications.consumers.NotificationSystemAdministration
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb.notifications.consumers;

import net.iaeste.iws.api.enums.NotificationDeliveryMode;
import net.iaeste.iws.api.enums.NotificationMessageStatus;
import net.iaeste.iws.common.utils.Observable;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.entities.NotificationMessageEntity;

import java.util.List;

/**
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection ObjectAllocationInLoop
 */
public class NotificationSystemAdministration implements Observer {

    private final NotificationDao dao;

    public NotificationSystemAdministration(final NotificationDao dao) {
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Observable subject) {
        processMessages();
    }

    private void processMessages() {
//        List<NotificationMessageEntity> messages = dao.findNotificationMessages(NotificationDeliveryMode.EMAIL, NotificationMessageStatus.NEW, new java.util.Date());
//        for(NotificationMessageEntity message : messages) {
//            dao.updateNotificationMessageStatus(message, NotificationMessageStatus.PROCESSING);
//        }
    }

}
