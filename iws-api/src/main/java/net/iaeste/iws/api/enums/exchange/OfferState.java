/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.exchange.OfferState
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums.exchange;

import net.iaeste.iws.api.enums.Descriptable;

import javax.xml.bind.annotation.XmlType;

/**
 * Defines the possible states for an Offer
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "offerState")
public enum OfferState implements Descriptable<OfferState> {

    /**
     * In an offer is not shared
     */
    NEW("New"),

    /**
     * IW3 is referring to Offers as Open, and have been viewed.
     */
    OPEN("Open"),

    /**
     * If an offer is shared to multiple countries
     */
    SHARED("Shared"),

    /**
     * If an offer has student applications
     */
    APPLICATIONS("Applications"),

    /**
     * If an offer has student nominations
     */
    NOMINATIONS("Nominations"),

    /**
     * In IW3, some offers were stored with status 'D' as in deleted.
     */
    DELETED("Deleted"),

    //offer status
    CLOSED("Closed"),
    COMPLETED("Completed"),
    AT_EMPLOYER("At Employer"),
    ACCEPTED("Accepted"),
    EXPIRED("Expired"),
    REJECTED("Rejected"),

    // Following is the missing values from IW3. They are added here, so the
    // API contain both old and new values in an attempt at correcting the
    // Exchange Process.
    CANCELLED("Cancelled"),
    EXCHANGED("Exchanged"),
    NOMINATION_REJECTED("Nomination Rejected"),
    NOT_ACCEPTED("Not Accepted"),
    DECLINED("Declined"),
    SN_COMPLETE("SN Complete"),
    TAKEN("Taken"),
    NOMINATION_ACCEPTED("Nomination Accepted"),
    VIEWED("Viewed"),
    WAITING_SN("Waiting"),
    AC_EXCHANGED("AC Exchanged"),
    UNKNOWN("Unknown");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    OfferState(final String description) {
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
