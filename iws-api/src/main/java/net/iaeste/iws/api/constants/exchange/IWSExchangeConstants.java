/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.constants.exchange.IWSExchangeConstants
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.constants.exchange;

import java.util.regex.Pattern;

/**
 * Exchange specific constants for the IW Services.
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface IWSExchangeConstants {

    /**
     * Following a Board decision in the Autumn, 2014 - all Reference Numbers
     * must apply to the following regex. The format was updated as part of Trac
     * ticket #930, which superseeds the format ticket #414. The actual decision
     * points from the Board minutes is still pending.
     */
    String REFNO_FORMAT = "[A-Z]{2}-[0-9]{4}-[A-Z0-9\\-]{1,8}";
    Pattern REFNO_PATTERN = Pattern.compile(REFNO_FORMAT);

    /**
     * Defines the maximum number of {@link net.iaeste.iws.api.enums.exchange.FieldOfStudy}
     * for an {@link net.iaeste.iws.api.dtos.exchange.Offer}
     */
    int MAX_OFFER_FIELDS_OF_STUDY = 3;

    /**
     * Defines the maximum number of {@link net.iaeste.iws.api.enums.exchange.Specialization}
     * for an {@link net.iaeste.iws.api.dtos.exchange.Offer}. Note, that the
     * original value was 3, although it should have been 7. But, it seems that
     * we have Offers with up to 12 in the DB. So rather than enforcing a lower
     * amount of records, we're raising the bar to make sure that existing
     * Offers will work.
     */
    int MAX_OFFER_SPECIALIZATIONS = 12;

    /**
     * Sets are converted into Strings internally, using this constant as
     * delimiter. For this reason, it is not allowed to use this in the String
     * given as it will cause errors.
     */
    String SET_DELIMITER = "|";

    /**
     * Defines the maximum number of {@link net.iaeste.iws.api.enums.Language}
     * for an {@link net.iaeste.iws.api.dtos.exchange.Offer}
     */
    int MAX_OFFER_LANGUAGES = 3;

    /**
     * Defines the maximum length of offer description
     * for an {@link net.iaeste.iws.api.dtos.exchange.Offer}
     */
    int MAX_OFFER_WORK_DESCRIPTION_SIZE = 3000;

    /**
     * Defines the maximum length of other requirements
     * for an {@link net.iaeste.iws.api.dtos.exchange.Offer}
     */
    int MAX_OFFER_OTHER_REQUIREMENTS_SIZE = 4000;

    /**
     * Defines the minimum number of weeks required
     * for an {@link net.iaeste.iws.api.dtos.exchange.Offer}
     */
    int MIN_OFFER_MINIMUM_WEEKS = 4;

    /**
     * Defines the maximum number of additional documents
     * allowed to attach to a {@link net.iaeste.iws.api.dtos.exchange.StudentApplication}
     */
    int MAX_NUMBER_OF_ADDITIONAL_DOCUMENTS = 10;

}
