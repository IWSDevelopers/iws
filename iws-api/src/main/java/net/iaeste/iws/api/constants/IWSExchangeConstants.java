package net.iaeste.iws.api.constants;

/**
 * Exchange specific constants for the IW Services.
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public interface IWSExchangeConstants {

    /**
     * Defines the maximum number of {@link net.iaeste.iws.api.enums.FieldOfStudy}
     * for an {@link net.iaeste.iws.api.dtos.Offer}
     */
    int MAX_OFFER_FIELDS_OF_STUDY = 7;

    /**
     * Defines the maximum number of {@link net.iaeste.iws.api.enums.Specialization}
     * for an {@link net.iaeste.iws.api.dtos.Offer}
     */
    int MAX_OFFER_SPECIALIZATIONS = 7;

    /**
     * Defines the maximum number of {@link net.iaeste.iws.api.enums.Language}
     * for an {@link net.iaeste.iws.api.dtos.Offer}
     */
    int MAX_OFFER_LANGUAGES = 3;
}
