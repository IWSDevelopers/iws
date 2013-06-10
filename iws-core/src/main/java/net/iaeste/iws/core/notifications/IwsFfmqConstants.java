package net.iaeste.iws.core.notifications;

/**
 * This class is not needed when NotificationEmailDelayedSender and NotificationEmailSender are moved to EJB
 *
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class IwsFfmqConstants {
    public static final String queueNameForFfmq = "iws-EmailQueue";
    public static final String queueNameForIws = "query/iws-EmailQueue";

    public static final String engineName = "IwsFfmqMessageServer";
    public static final String listenAddr = "0.0.0.0";
    public static final int listenPort = 10002;

    private IwsFfmqConstants() {}
}
