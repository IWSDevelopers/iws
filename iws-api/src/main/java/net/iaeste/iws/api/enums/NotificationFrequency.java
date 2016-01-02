/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.NotificationFrequency
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums;

import javax.xml.bind.annotation.XmlType;

/**
 * Notification Frequency - when the user wants to receive notification about an
 * IW action.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "notificationFrequency")
public enum NotificationFrequency implements Descriptable<NotificationFrequency> {

    /**
     * The IWS Notification System will send notifications immediately.
     */
    IMMEDIATELY("Immediately"),

    /**
     * The IWS Notification System will collect all Notifications and only send
     * them once a day in a single message.
     */
    DAILY("Daily"),

    /**
     * The IWS Notification System will collect all Notifications and only send
     * them once a week in a single message.
     */
    WEEKLY("Weekly");

    // =========================================================================
    // Internal Enumeration Functionality
    // =========================================================================

    private final String description;

    /**
     * Internal Constructor, for setting the printable description.
     *
     * @param description Printable description of the Enumeration
     */
    NotificationFrequency(final String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }
}
