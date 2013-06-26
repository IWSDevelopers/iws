/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-client) - net.iaeste.iws.client.notifications.NotificationMessage
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.client.notifications;

import net.iaeste.iws.core.notifications.NotificationMessageGenerator;
import net.iaeste.iws.ejb.notifications.NotificationMessageGeneratorFreemarker;
import net.iaeste.iws.persistence.notification.Notifiable;
import net.iaeste.iws.persistence.notification.NotificationType;

import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class NotificationMessage {

    private final Notifiable notifiable;
    private final NotificationType type;
    private final NotificationMessageGenerator messageGenerator;

    public NotificationMessage(final Notifiable notifiable, final NotificationType type) {
        this.notifiable = notifiable;
        this.type = type;

        messageGenerator = new NotificationMessageGeneratorFreemarker();
//        messageGenerator = new NotificationMessageGeneratorVelocity();
    }

    public Notifiable getNotifiable() {
        return notifiable;
    }

    public NotificationType getType() {
        return type;
    }

    public String getMessage() {
        final Map<String, String> messageTexts = messageGenerator.generateFromTemplate(notifiable, type);
        return messageTexts.get("body");
    }
}
