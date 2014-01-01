/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.emails.EmailMessage
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb.emails;

import java.io.Serializable;

/**
 * These classes sind temporarily placed here. We need the functionality, but it
 * should rightly be placed in the EJB module. Until the EJB module is ready,
 * and we have a GlassFish instance to deploy to, we will keep it here.
 *
 * Move together with NotificationEmail(Delayed)Sender to the EJB module.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class EmailMessage implements Serializable {

    private String to = null;
    private String subject = null;
    private String message = null;

    public void setTo(final String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
