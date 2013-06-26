package net.iaeste.iws.api.enums.exchange;

import net.iaeste.iws.api.constants.IWSConstants;

/**
 * Defines the possible states for an Offer
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public enum OfferState {

    /**
     * In an offer is not shared
     */
    NEW,

    /**
     * If an offer is shared to multiple countries
     */
    SHARED,

    /**
     * If an offer is shared to one country only
     */
    EXCHANGED,

    /**
     * If an offer has student applications
     */
    APPLICATIONS,

    /**
     * If an offer has student nominations
     */
    NOMINATIONS;

    public String getDescription() {
        return name().substring(0,1) + name().substring(1).toLowerCase(IWSConstants.DEFAULT_LOCALE);
    }
}
