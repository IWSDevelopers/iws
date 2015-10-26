/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.MailReply
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
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
@XmlType(name = "mailReply")
public enum MailReply implements Descriptable<GroupType> {

    REPLY_TO_SENDER("The Sender is receiving replies."),
    REPLY_TO_LIST("Te List is receiving replies."),
    NO_REPLY("Replying is not possible.");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    /**
     * Constructor for this enumerated type.
     *
     * @param description Description
     */
    MailReply(final String description) {
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
