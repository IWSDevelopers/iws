package net.iaeste.iws.core.notifications;

import net.iaeste.iws.persistence.entities.NotificationMessageEntity;

/**
 * The purpose of this interface is to provide a way how to access messages that have to be sent
 * but can't be persisted because they contain confidential data.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface NotificationDirectEmailSender {

    void send(NotificationMessageEntity message);

}
