package net.iaeste.iws.core.notifications;

import net.iaeste.iws.persistence.notification.Notifiable;
import net.iaeste.iws.persistence.notification.NotificationType;

import java.util.Map;

/**
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public interface NotificationMessageGenerator {

    /**
     * Generates a message for given object and notification type
     *
     * @param obj   Object to generate message for
     * @param type  Notification type
     * @return  Generated message as String
     */
    Map<String, String> generateFromTemplate(Notifiable obj, NotificationType type);
}
