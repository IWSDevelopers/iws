/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.notifications.IwsFfmqConstants
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.notifications;

/**
 * This class is not needed when NotificationEmailDelayedSender and
 * NotificationEmailSender are moved to EJB
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class IwsFfmqConstants {

    public static final String queueNameForFfmq = "iws-EmailQueue";
    public static final String queueNameForIws = "query/iws-EmailQueue";

    public static final String engineName = "IwsFfmqMessageServer";
    public static final String listenAddr = "0.0.0.0";
    public static final int listenPort = 10002;

    private IwsFfmqConstants() {
    }
}
